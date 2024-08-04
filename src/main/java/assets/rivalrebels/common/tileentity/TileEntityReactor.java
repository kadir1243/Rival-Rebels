/*******************************************************************************
 * Copyright (c) 2012, 2016 Rodol Phito.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Mozilla Public License Version 2.0
 * which accompanies this distribution, and is available at
 * https://www.mozilla.org/en-US/MPL/2.0/
 *
 * Rival Rebels Mod. All code, art, and design by Rodol Phito.
 *
 * http://RivalRebels.com/
 *******************************************************************************/
package assets.rivalrebels.common.tileentity;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.container.ContainerReactor;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.explosion.Explosion;
import assets.rivalrebels.common.item.ItemCore;
import assets.rivalrebels.common.item.ItemRod;
import assets.rivalrebels.common.item.ItemRodNuclear;
import assets.rivalrebels.common.item.ItemRodRedstone;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.packet.ReactorMachinesPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.MenuConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityReactor extends BlockEntity implements Container, Tickable, MenuConstructor {
    public static final ResourceLocation OVERHEAT_TRANSLATION = RRIdentifiers.create("overheat");
	public double			slide				= 90;
	private float test = Mth.PI;
    public ItemStack		core = ItemStack.EMPTY;
	public ItemStack		fuel = ItemStack.EMPTY;
	public boolean			on					= false;
	public boolean			prevOn				= false;
	public boolean			melt				= false;
	public int				meltTick			= 0;
	public boolean			eject				= false;
	public double			consumed			= 0;
	public double			lasttickconsumed	= 0;
	public int				tickssincelastrod	= 0;
	public boolean			lastrodwasredstone	= false;
    public final Map<BlockPos, ReactorMachinesPacket.MachineEntry> entries = new HashMap<>();
	public List<TileEntityMachineBase>	machines = new ArrayList<>();
	public int				tick				= 0;

    public TileEntityReactor(BlockPos pos, BlockState state) {
        super(RRTileEntities.REACTOR, pos, state);

        EntityRhodes.BLOCK_ENTITIES.put(pos, this);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
		if (nbt.contains("core")) core = ItemStack.parseOptional(provider, nbt.getCompound("core"));
		if (nbt.contains("fuel")) fuel = ItemStack.parseOptional(provider, nbt.getCompound("fuel"));
		consumed = nbt.getDouble("consumed");
		on = nbt.getBoolean("on");
        lasttickconsumed = nbt.getDouble("lasttickconsumed");
        melt = nbt.getBoolean("melt");
        eject = nbt.getBoolean("eject");
		int i = 0;
		while (nbt.contains("mpos" + i)) {
            if (hasLevel()) {
				BlockEntity te = level.getBlockEntity(BlockPos.of(nbt.getLong("mpos" + i)));
                if (te instanceof TileEntityMachineBase machineBase) {
                    machineBase.powerGiven = nbt.getFloat("mpowerGiven" + i);
                    machineBase.pInR = nbt.getFloat("mpInR" + i);
                    machineBase.worldPosition = getBlockPos();
                    machineBase.edist = (float) Math.sqrt(machineBase.getBlockPos().distSqr(getBlockPos()));
                    machines.add(machineBase);
				}
			}
			i++;
		}
	}

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
		if (!core.isEmpty()) nbt.put("core", core.save(provider));
		if (!fuel.isEmpty()) nbt.put("fuel", fuel.save(provider));
		nbt.putDouble("consumed", consumed);
		nbt.putBoolean("on", on);
        nbt.putDouble("lasttickconsumed", lasttickconsumed);
        nbt.putBoolean("melt", melt);
        nbt.putBoolean("eject", eject);
		if (on) {
            for (int i = 0; i < machines.size(); i++) {
                TileEntityMachineBase te = machines.get(i);
                if (te == null || te instanceof TileEntityReactive) continue;
                nbt.putLong("mpos" + i, te.getBlockPos().asLong());
                nbt.putFloat("mpowerGiven" + i, te.powerGiven);
                nbt.putFloat("mpInR" + i, te.pInR);
            }
        }
    }

    @Override
    public void clientTick() {
        slide = (Mth.cos(test) + 1) * 45;
        boolean flag = level.hasNearbyAlivePlayer(getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 9);
        if (flag) {
            if (slide < 89.995) test += 0.05F;
        } else {
            if (slide > 0.004) test -= 0.05F;
        }
        if (core.isEmpty()) {
            on = false;
            consumed = 0;
            lasttickconsumed = 0;
            melt = false;
            meltTick = 0;
        }

        if (eject) {
            consumed = 0;
            lasttickconsumed = 0;
            fuel = ItemStack.EMPTY;
            core = ItemStack.EMPTY;
            melt = false;
            meltTick = 0;
            on = false;
            eject = false;
        }

        prevOn = on;
    }

    @Override
    public void serverTick() {
        if (eject) {
            if (!core.isEmpty()) {
                consumed = 0;
                lasttickconsumed = 0;
                level.addFreshEntity(new ItemEntity(level, getBlockPos().getX() + 0.5, getBlockPos().getY() + 1, getBlockPos().getZ() + 0.5, core));
                fuel = ItemStack.EMPTY;
                core = ItemStack.EMPTY;
                melt = false;
                meltTick = 0;
                on = false;
            }
        }

        if (melt) {
            if (!core.isEmpty()) {
                if (meltTick % 20 == 0) RivalRebelsSoundPlayer.playSound(level, 21, 1, getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5);
                on = true;
                meltTick++;
                if (meltTick == 300) meltDown(10);
                else if (meltTick == 1) {
                    Component text = RRIdentifiers.warning().append(" ").append(Component.translatable(RRIdentifiers.MODID + ".warning_meltdown").withStyle(ChatFormatting.RED));
                    for (Player player : level.players()) {
                        player.displayClientMessage(text, false);
                    }
                }
                for (int i = 0; i < 4; i++) {
                    level.addParticle(ParticleTypes.SMOKE, getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5, level.random.nextDouble() - 0.5, level.random.nextDouble() / 2, level.random.nextDouble() - 0.5);
                }
            } else {
                melt = false;
                meltTick = 0;
                on = false;
            }
        }

        if (fuel.isEmpty() && tickssincelastrod != 0) {
            tickssincelastrod++;
            if (tickssincelastrod >= 100) {
                if (lastrodwasredstone) on = false;
                else melt = true;
            }
            if (tickssincelastrod == 20 && !lastrodwasredstone) {
                for (Player player : getLevel().players()) {
                    player.displayClientMessage(RRIdentifiers.warning().append(" ").append(Component.translatable(OVERHEAT_TRANSLATION.toLanguageKey()).withStyle(ChatFormatting.RED)), false);
                }
            }
        } else {
            tickssincelastrod = 0;
        }

        if (melt) {
            machines.clear();
        }

        if (core.isEmpty()) {
            on = false;
            consumed = 0;
            lasttickconsumed = 0;
            melt = false;
            meltTick = 0;
        }

        if (on && !core.isEmpty() && !fuel.isEmpty() && core.getItem() instanceof ItemCore c && fuel.getItem() instanceof ItemRod r)
        {
            if (!prevOn && on) RivalRebelsSoundPlayer.playSound(level, 21, 3, getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5);
            else
            {
                tick++;
                if (on && tick % 39 == 0) RivalRebelsSoundPlayer.playSound(level, 21, 2, getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5, 0.9f, 0.77f);
            }
            float power = ((r.power * c.timemult) - fuel.getOrDefault(RRComponents.REACTOR_FUEL_LEFT, 0));
            float temp = power;
            for (BlockEntity te : TileEntityMachineBase.BLOCK_ENTITIES.values()) {
                if (te instanceof TileEntityMachineBase temb) {
                    if (level.getBlockEntity(temb.worldPosition) == null) {
                        double dist = temb.getBlockPos().distSqr(getBlockPos());
                        if (dist < 1024) {
                            temb.worldPosition = getBlockPos();
                            temb.edist = (float) Math.sqrt(dist);
                            machines.add(temb);
                        }
                    }
                    if (temb.worldPosition.equals(getBlockPos())) {
                        machines.add(temb);
                        temb.powerGiven = power;
                        if (power > temb.pInM - temb.pInR) {
                            power -= temb.pInM - temb.pInR;
                            temb.pInR = temb.pInM;
                        } else {
                            temb.pInR += power;
                            power = 0;
                        }
                        temb.powerGiven -= power;
                    }
                }
            }
            lasttickconsumed = temp - power;
            consumed += lasttickconsumed;
            if (fuel.has(RRComponents.REACTOR_FUEL_LEFT))
            {
                fuel.set(RRComponents.REACTOR_FUEL_LEFT, (int) consumed);

                double fuelLeft = (int) consumed;
                double fuelPercentage = (fuelLeft / temp);

                if (r instanceof ItemRodNuclear)
                {
                    double f2 = fuelPercentage * fuelPercentage;
                    double f4 = f2 * f2;
                    double f8 = f4 * f4;
                    if (level.random.nextFloat() < f8)
                    {
                        melt = true;
                    }
                }
            }
            else fuel.set(RRComponents.REACTOR_FUEL_LEFT, 0);
            if (fuel.getOrDefault(RRComponents.REACTOR_FUEL_LEFT, 0) >= temp)
            {
                lastrodwasredstone = r instanceof ItemRodRedstone; // meltdown if not redrod
                consumed = 0;
                lasttickconsumed = 0;
                tickssincelastrod = 1;
                fuel = ItemStack.EMPTY;
            }
        }
        else
        {
            machines.clear();
        }
        eject = false;
        prevOn = on;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag nbt = new CompoundTag();
        saveAdditional(nbt, registries);
        return nbt;
    }

    public void meltDown(int radius)
	{
		/*for (int x = -radius; x < radius; x++)
		{
			for (int z = -radius; z < radius; z++)
			{
				double dist = Math.sqrt(x * x + z * z);
				if (dist < radius - 1)
				{
					int random = world.random.nextInt(4);
					if (random == 0) for (int i = 0; i < 16; i++)
						world.setBlock(getPos().getX() + x, getPos().getY() - 1, getPos().getZ() + z, RivalRebels.petrifiedstone1, (int) (dist * 2f) + 1, 2);
					if (random == 1) for (int i = 0; i < 16; i++)
						world.setBlock(getPos().getX() + x, getPos().getY() - 1, getPos().getZ() + z, RivalRebels.petrifiedstone2, (int) (dist * 2f) + 1, 2);
					if (random == 2) for (int i = 0; i < 16; i++)
						world.setBlock(getPos().getX() + x, getPos().getY() - 1, getPos().getZ() + z, RivalRebels.petrifiedstone3, (int) (dist * 2f) + 1, 2);
					if (random == 3) for (int i = 0; i < 16; i++)
						world.setBlock(getPos().getX() + x, getPos().getY() - 1, getPos().getZ() + z, RivalRebels.petrifiedstone4, (int) (dist * 2f) + 1, 2);

					world.setBlock(getPos().getX() + x, getPos().getY() - 2, getPos().getZ() + z, RivalRebels.radioactivedirt);
				}
				else if (dist < radius)
				{
					world.setBlock(getPos().getX() + x, getPos().getY() - 2, getPos().getZ() + z, RivalRebels.radioactivedirt);
				}
			}
		}*/
		level.setBlockAndUpdate(getBlockPos(), RRBlocks.meltdown.defaultBlockState());
		new Explosion(level, getBlockPos().getX(), getBlockPos().getY() - 2, getBlockPos().getZ(), 4, false, false, RivalRebelsDamageSource.rocket(getLevel()));
	}

	@Override
	public int getContainerSize() {
		return 2;
	}

	@Override
	public ItemStack getItem(int slot) {
		if (slot == 0) return fuel;
		else if (slot == 1) return core;
		else return ItemStack.EMPTY;
	}

    @Override
    public ItemStack removeItem(int slot, int amount) {
		if (slot == 0) {
			fuel.shrink(amount);
			return fuel;
		} else if (slot == 1) {
			core.shrink(amount);
			return core;
		}
		else return ItemStack.EMPTY;
	}

    @Override
    public ItemStack removeItemNoUpdate(int index) {
		if (index == 0) return fuel;
		else if (index == 1) return core;
		else return ItemStack.EMPTY;
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		if (slot == 0) fuel = stack;
		else if (slot == 1) core = stack;
	}

	@Override
	public int getMaxStackSize()
	{
		return 1;
	}

	@Override
	public boolean stillValid(Player player)
	{
		return true;
	}

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
		if (stack.isEmpty() || !(stack.getItem() instanceof ItemRod) || !(stack.getItem() instanceof ItemCore)) return false;
		if (slot == 0) {
			return fuel.isEmpty() || !on;
		}
		if (slot == 1) {
			return !on;
		}
		return false;
	}

    @Override
    public void setRemoved() {
        super.setRemoved();
        on = false;
        for (TileEntityMachineBase machine : machines) {
            if (machine.isRemoved()) continue;
            machine.worldPosition = BlockPos.ZERO;
            machine.edist = 0;
        }
        EntityRhodes.BLOCK_ENTITIES.remove(getBlockPos());
    }

	public float getPower()
	{
		if (!core.isEmpty() && !fuel.isEmpty())
		{
			ItemCore c = (ItemCore) core.getItem();
			ItemRod r = (ItemRod) fuel.getItem();
			return ((r.power * c.timemult) - fuel.getOrDefault(RRComponents.REACTOR_FUEL_LEFT, 0));
		}
		return 0;
	}

	public void toggleOn()
	{
		on = !on;
	}

	public void ejectCore() {
		eject = true;
	}

    @Override
    public boolean isEmpty() {
        return this.core.isEmpty() && this.fuel.isEmpty();
    }

    @Override
    public void clearContent() {
        this.core = ItemStack.EMPTY;
        this.fuel = ItemStack.EMPTY;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new ContainerReactor(syncId, inv, this, propertyDelegate);
    }

    private final ContainerData propertyDelegate = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> on ? 1 : 0;
                case 1 -> (int) (getPower() * 100);
                case 2 -> (int) consumed;
                case 3 -> melt ? 1 : 0;
                case 4 -> getBlockPos().getX();
                case 5 -> getBlockPos().getY();
                case 6 -> getBlockPos().getZ();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> on = value == 1;
                case 2 -> consumed = value;
                case 3 -> melt = value == 1;
                default -> {}
            }
        }

        @Override
        public int getCount() {
            return 7;
        }
    };
}
