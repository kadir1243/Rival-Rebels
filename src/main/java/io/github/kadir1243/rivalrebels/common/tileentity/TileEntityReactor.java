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
package io.github.kadir1243.rivalrebels.common.tileentity;

import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.container.ContainerReactor;
import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodes;
import io.github.kadir1243.rivalrebels.common.explosion.Explosion;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import io.github.kadir1243.rivalrebels.common.packet.ReactorMachinesPacket;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityReactor extends BaseContainerBlockEntity {
    public double			slide				= 90;
	private float test = Mth.PI;
    private NonNullList<ItemStack> items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
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
        super(RRTileEntities.REACTOR.get(), pos, state);

        EntityRhodes.BLOCK_ENTITIES.put(pos, this);
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        ContainerHelper.loadAllItems(valueInput, items);
		consumed = valueInput.getDoubleOr("consumed", 0);
		on = valueInput.getBooleanOr("on", false);
        lasttickconsumed = valueInput.getDoubleOr("lasttickconsumed", 0D);
        melt = valueInput.getBooleanOr("melt", false);
        eject = valueInput.getBooleanOr("eject", false);
		int i = 0;
		while (valueInput.child("mpos" + i).isPresent()) {
            if (hasLevel()) {
				BlockEntity te = level.getBlockEntity(BlockPos.of(valueInput.getLongOr("mpos" + i, 0L)));
                if (te instanceof TileEntityMachineBase machineBase) {
                    machineBase.powerGiven = valueInput.getFloatOr("mpowerGiven" + i, 0F);
                    machineBase.pInR = valueInput.getFloatOr("mpInR" + i, 0F);
                    machineBase.worldPosition = getBlockPos();
                    machineBase.edist = (float) Math.sqrt(machineBase.getBlockPos().distSqr(getBlockPos()));
                    machines.add(machineBase);
				}
			}
			i++;
		}
	}

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);

        ContainerHelper.saveAllItems(valueOutput, items);
		valueOutput.putDouble("consumed", consumed);
		valueOutput.putBoolean("on", on);
        valueOutput.putDouble("lasttickconsumed", lasttickconsumed);
        valueOutput.putBoolean("melt", melt);
        valueOutput.putBoolean("eject", eject);
		if (on) {
            for (int i = 0; i < machines.size(); i++) {
                TileEntityMachineBase te = machines.get(i);
                if (te == null || te instanceof TileEntityReactive) continue;
                valueOutput.putLong("mpos" + i, te.getBlockPos().asLong());
                valueOutput.putFloat("mpowerGiven" + i, te.powerGiven);
                valueOutput.putFloat("mpInR" + i, te.pInR);
            }
        }
    }

    public static void clientTick(Level level, BlockPos blockPos, BlockState blockState, TileEntityReactor blockEntity) {
        blockEntity.slide = (Mth.cos(blockEntity.test) + 1) * 45;
        boolean flag = level.hasNearbyAlivePlayer(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f, 9);
        if (flag) {
            if (blockEntity.slide < 89.995) blockEntity.test += 0.05F;
        } else {
            if (blockEntity.slide > 0.004) blockEntity.test -= 0.05F;
        }
        if (blockEntity.getCore().isEmpty()) {
            blockEntity.on = false;
            blockEntity.consumed = 0;
            blockEntity.lasttickconsumed = 0;
            blockEntity.melt = false;
            blockEntity.meltTick = 0;
        }

        if (blockEntity.eject) {
            blockEntity.consumed = 0;
            blockEntity.lasttickconsumed = 0;
            blockEntity.clearContent();
            blockEntity.melt = false;
            blockEntity.meltTick = 0;
            blockEntity.on = false;
            blockEntity.eject = false;
        }

        blockEntity.prevOn = blockEntity.on;
    }
    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, TileEntityReactor blockEntity) {
        if (blockEntity.eject) {
            if (!blockEntity.getCore().isEmpty()) {
                blockEntity.consumed = 0;
                blockEntity.lasttickconsumed = 0;
                level.addFreshEntity(new ItemEntity(level, blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, blockEntity.getCore()));
                blockEntity.clearContent();
                blockEntity.melt = false;
                blockEntity.meltTick = 0;
                blockEntity.on = false;
            }
        }

        if (blockEntity.melt) {
            if (!blockEntity.getCore().isEmpty()) {
                if (blockEntity.meltTick % 20 == 0) level.playLocalSound(blockPos, RRSounds.REACTOR_RUNNING.get(), SoundSource.BLOCKS, 1, 1, true);
                blockEntity.on = true;
                blockEntity.meltTick++;
                if (blockEntity.meltTick == 300) blockEntity.meltDown(10);
                else if (blockEntity.meltTick == 1) {
                    Component text = Translations.warning().append(" ").append(Translations.WARNING_MELTDOWN.translate().withStyle(ChatFormatting.RED));
                    for (Player player : level.players()) {
                        player.sendSystemMessage(text);
                    }
                }
                for (int i = 0; i < 4; i++) {
                    level.addParticle(ParticleTypes.SMOKE, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, level.getRandom().nextDouble() - 0.5, level.getRandom().nextDouble() / 2, level.getRandom().nextDouble() - 0.5);
                }
            } else {
                blockEntity.melt = false;
                blockEntity.meltTick = 0;
                blockEntity.on = false;
            }
        }

        if (blockEntity.getFuel().isEmpty() && blockEntity.tickssincelastrod != 0) {
            blockEntity.tickssincelastrod++;
            if (blockEntity.tickssincelastrod >= 100) {
                if (blockEntity.lastrodwasredstone) blockEntity.on = false;
                else blockEntity.melt = true;
            }
            if (blockEntity.tickssincelastrod == 20 && !blockEntity.lastrodwasredstone) {
                for (Player player : level.players()) {
                    player.sendSystemMessage(Translations.warning().append(" ").append(Translations.OVERHEAT_TRANSLATION.translate().withStyle(ChatFormatting.RED)));
                }
            }
        } else {
            blockEntity.tickssincelastrod = 0;
        }

        if (blockEntity.melt) {
            blockEntity.machines.clear();
        }

        if (blockEntity.getCore().isEmpty()) {
            blockEntity.on = false;
            blockEntity.consumed = 0;
            blockEntity.lasttickconsumed = 0;
            blockEntity.melt = false;
            blockEntity.meltTick = 0;
        }

        if (blockEntity.on && blockEntity.getCore().has(RRComponents.CORE_TIME_MULTIPLIER) && blockEntity.getFuel().has(RRComponents.ROD_POWER))
        {
            if (!blockEntity.prevOn && blockEntity.on) level.playLocalSound(blockPos, RRSounds.REACTOR_DISABLING.get(), SoundSource.BLOCKS, 1, 1, true);
            else
            {
                blockEntity.tick++;
                if (blockEntity.on && blockEntity.tick % 39 == 0) level.playLocalSound(blockPos, RRSounds.REACTOR_RUNNING_2.get(), SoundSource.BLOCKS, 0.9f, 0.77f, true);
            }
            float power = ((blockEntity.getFuel().get(RRComponents.ROD_POWER) * blockEntity.getCore().get(RRComponents.CORE_TIME_MULTIPLIER)) - blockEntity.getFuel().getOrDefault(RRComponents.REACTOR_FUEL_LEFT, 0));
            float temp = power;
            for (BlockEntity te : TileEntityMachineBase.BLOCK_ENTITIES.values()) {
                if (te instanceof TileEntityMachineBase temb) {
                    if (level.getBlockEntity(temb.worldPosition) == null) {
                        double dist = temb.getBlockPos().distSqr(blockPos);
                        if (dist < 1024) {
                            temb.worldPosition = blockPos;
                            temb.edist = (float) Math.sqrt(dist);
                            blockEntity.machines.add(temb);
                        }
                    }
                    if (temb.worldPosition.equals(blockPos)) {
                        blockEntity.machines.add(temb);
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
            blockEntity.lasttickconsumed = temp - power;
            blockEntity.consumed += blockEntity.lasttickconsumed;
            if (blockEntity.getFuel().has(RRComponents.REACTOR_FUEL_LEFT)) {
                blockEntity.getFuel().set(RRComponents.REACTOR_FUEL_LEFT, (int) blockEntity.consumed);

                double fuelLeft = (int) blockEntity.consumed;
                double fuelPercentage = (fuelLeft / temp);

                if (blockEntity.getFuel().is(RRItems.NUCLEAR_ROD)) {
                    double f2 = fuelPercentage * fuelPercentage;
                    double f4 = f2 * f2;
                    double f8 = f4 * f4;
                    if (level.getRandom().nextFloat() < f8) {
                        blockEntity.melt = true;
                    }
                }
            }
            else blockEntity.getFuel().set(RRComponents.REACTOR_FUEL_LEFT, 0);
            if (blockEntity.getFuel().getOrDefault(RRComponents.REACTOR_FUEL_LEFT, 0) >= temp) {
                blockEntity.lastrodwasredstone = blockEntity.getFuel().is(RRItems.redrod); // meltdown if not redrod
                blockEntity.consumed = 0;
                blockEntity.lasttickconsumed = 0;
                blockEntity.tickssincelastrod = 1;
                blockEntity.setFuel(ItemStack.EMPTY);
            }
        }
        else
        {
            blockEntity.machines.clear();
        }
        blockEntity.eject = false;
        blockEntity.prevOn = blockEntity.on;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
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
		level.setBlockAndUpdate(getBlockPos(), RRBlocks.meltdown.get().defaultBlockState());
		new Explosion(level, getBlockPos().getX(), getBlockPos().getY() - 2, getBlockPos().getZ(), 4, false, false, RivalRebelsDamageSource.rocket(getLevel()));
	}

	@Override
	public int getContainerSize() {
		return 2;
	}

    public ItemStack getCore() {
        return this.items.getFirst();
    }

    public void setCore(ItemStack core) {
        this.setItem(0, core);
    }

    public ItemStack getFuel() {
        return this.items.get(1);
    }

    public void setFuel(ItemStack fuel) {
        this.setItem(1, fuel);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void setItems(NonNullList<ItemStack> items) {
        this.items = items;
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
		if (slot == 0 && stack.has(RRComponents.CORE_TIME_MULTIPLIER)) {
			return getFuel().isEmpty() || !on;
		} else if (slot == 1 && stack.has(RRComponents.ROD_POWER)) {
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

	public float getPower() {
		if (getCore().has(RRComponents.CORE_TIME_MULTIPLIER) && getFuel().has(RRComponents.ROD_POWER)) {
			return ((getFuel().get(RRComponents.ROD_POWER) * getCore().get(RRComponents.CORE_TIME_MULTIPLIER)) - getFuel().getOrDefault(RRComponents.REACTOR_FUEL_LEFT, 0));
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
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new ContainerReactor(containerId, inventory, this, propertyDelegate);
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

    @Override
    protected Component getDefaultName() {
        return Component.literal("Reactor");
    }
}
