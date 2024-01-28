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

import assets.rivalrebels.RivalRebels;
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
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TileEntityReactor extends BlockEntity implements Inventory, Tickable, NamedScreenHandlerFactory {
	public double			slide				= 90;
	private double			test				= Math.PI;
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
	public List<TileEntityMachineBase>	machines			= new ArrayList<>();
	public int				tick				= 0;

    public TileEntityReactor(BlockPos pos, BlockState state) {
        super(RRTileEntities.REACTOR, pos, state);

        EntityRhodes.BLOCK_ENTITIES.put(pos, this);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
		if (nbt.contains("core")) core = ItemStack.fromNbt(nbt.getCompound("core"));
		if (nbt.contains("fuel")) fuel = ItemStack.fromNbt(nbt.getCompound("fuel"));
		consumed = nbt.getDouble("consumed");
		on = nbt.getBoolean("on");
        lasttickconsumed = nbt.getDouble("lasttickconsumed");
        melt = nbt.getBoolean("melt");
        eject = nbt.getBoolean("eject");
		int i = 0;
		while (nbt.contains("mpos" + i)) {
            if (hasWorld()) {
				BlockEntity te = world.getBlockEntity(BlockPos.fromLong(nbt.getLong("mpos" + i)));
                if (te instanceof TileEntityMachineBase machineBase) {
                    machineBase.powerGiven = nbt.getFloat("mpowerGiven" + i);
                    machineBase.pInR = nbt.getFloat("mpInR" + i);
                    machineBase.pos = getPos();
                    machineBase.edist = (float) Math.sqrt(machineBase.getPos().getSquaredDistance(getPos()));
                    machines.add(machineBase);
				}
			}
			i++;
		}
	}

    @Override
    public void writeNbt(NbtCompound nbt) {
		if (!core.isEmpty()) nbt.put("core", core.writeNbt(new NbtCompound()));
		if (!fuel.isEmpty()) nbt.put("fuel", fuel.writeNbt(new NbtCompound()));
		nbt.putDouble("consumed", consumed);
		nbt.putBoolean("on", on);
        nbt.putDouble("lasttickconsumed", lasttickconsumed);
        nbt.putBoolean("melt", melt);
        nbt.putBoolean("eject", eject);
		if (on) {
            for (int i = 0; i < machines.size(); i++) {
                TileEntityMachineBase te = machines.get(i);
                if (te == null || te instanceof TileEntityReactive) continue;
                nbt.putLong("mpos" + i, te.getPos().asLong());
                nbt.putFloat("mpowerGiven" + i, te.powerGiven);
                nbt.putFloat("mpInR" + i, te.pInR);
            }
        }
    }

	@Override
	public void tick() {
        if (world.isClient)
		{
			slide = (Math.cos(test) + 1) * 45;
            boolean flag = world.isPlayerInRange(getPos().getX() + 0.5f, getPos().getY() + 0.5f, getPos().getZ() + 0.5f, 9);
			if (flag)
			{
				if (slide < 89.995) test += 0.05;
			}
			else
			{
				if (slide > 0.004) test -= 0.05;
			}
			if (core.isEmpty())
			{
				on = false;
				consumed = 0;
				lasttickconsumed = 0;
				melt = false;
				meltTick = 0;
			}

			if (eject)
			{
				consumed = 0;
				lasttickconsumed = 0;
				fuel = ItemStack.EMPTY;
				core = ItemStack.EMPTY;
				melt = false;
				meltTick = 0;
				on = false;
				eject = false;
			}

			if (melt)
			{
				if (!core.isEmpty())
				{
					for (int i = 0; i < 4; i++)
					{
						world.addParticle(ParticleTypes.SMOKE, getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5, world.random.nextDouble() - 0.5, world.random.nextDouble() / 2, world.random.nextDouble() - 0.5);
					}
				}
				else
				{
					melt = false;
					meltTick = 0;
					on = false;
				}
			}
        }
		else {
			if (eject)
			{
				if (!core.isEmpty())
				{
					consumed = 0;
					lasttickconsumed = 0;
					world.spawnEntity(new ItemEntity(world, getPos().getX() + 0.5, getPos().getY() + 1, getPos().getZ() + 0.5, core));
					fuel = ItemStack.EMPTY;
					core = ItemStack.EMPTY;
					melt = false;
					meltTick = 0;
					on = false;
				}
			}

			if (melt)
			{
				if (!core.isEmpty())
				{
					if (meltTick % 20 == 0) RivalRebelsSoundPlayer.playSound(world, 21, 1, getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5);
					on = true;
					meltTick++;
					if (meltTick == 300) meltDown(10);
					else if (meltTick == 1) {
                        Text text = new TranslatableText(RivalRebels.MODID + ".warning_meltdown");
                        for (PlayerEntity player : world.getPlayers()) {
                            player.sendMessage(text, false);
                        }
                    }
				}
				else
				{
					melt = false;
					meltTick = 0;
					on = false;
				}
			}

			if (fuel.isEmpty() && tickssincelastrod != 0)
			{
				tickssincelastrod++;
				if (tickssincelastrod >= 100)
				{
					if (lastrodwasredstone) on = false;
					else melt = true;
				}
				if (tickssincelastrod == 20 && !lastrodwasredstone)
				{
					//RivalRebelsServerPacketHandler.sendChatToAll("RivalRebels.WARNING RivalRebels.overheat", 0, world);
				}
			}
			else
			{
				tickssincelastrod = 0;
			}

			if (melt)
			{
				machines.clear();
			}

			if (core.isEmpty())
			{
				on = false;
				consumed = 0;
				lasttickconsumed = 0;
				melt = false;
				meltTick = 0;
			}

			if (on && !core.isEmpty() && !fuel.isEmpty() && core.getItem() instanceof ItemCore c && fuel.getItem() instanceof ItemRod r)
			{
				if (!prevOn && on) RivalRebelsSoundPlayer.playSound(world, 21, 3, getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5);
				else
				{
					tick++;
					if (on && tick % 39 == 0) RivalRebelsSoundPlayer.playSound(world, 21, 2, getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5, 0.9f, 0.77f);
				}
				float power = ((r.power * c.timemult) - fuel.getOrCreateNbt().getInt("fuelLeft"));
				float temp = power;
                for (BlockEntity te : TileEntityMachineBase.BLOCK_ENTITIES.values()) {
                    if (te instanceof TileEntityMachineBase temb) {
                        if (world.getBlockEntity(temb.pos) == null) {
                            double dist = temb.getPos().getSquaredDistance(getPos());
                            if (dist < 1024) {
                                temb.pos = getPos();
                                temb.edist = (float) Math.sqrt(dist);
                                machines.add(temb);
                            }
                        }
                        if (temb.pos.equals(getPos())) {
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
				if (fuel.getNbt().contains("fuelLeft"))
				{
					fuel.getNbt().putInt("fuelLeft", (int) consumed);

					double fuelLeft = fuel.getNbt().getInt("fuelLeft");
					double fuelPercentage = (fuelLeft / temp);

					if (r instanceof ItemRodNuclear)
					{
						double f2 = fuelPercentage * fuelPercentage;
						double f4 = f2 * f2;
						double f8 = f4 * f4;
						if (world.random.nextFloat() < f8)
						{
							melt = true;
						}
					}
				}
				else fuel.getNbt().putInt("fuelLeft", 0);
				if (fuel.getNbt().getInt("fuelLeft") >= temp)
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
        }
        prevOn = on;
    }

    @Nullable
    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbt = new NbtCompound();
        writeNbt(nbt);
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
		world.setBlockState(getPos(), RRBlocks.meltdown.getDefaultState());
		new Explosion(world, getPos().getX(), getPos().getY() - 2, getPos().getZ(), 4, false, false, RivalRebelsDamageSource.rocket);
	}

	@Override
	public int size() {
		return 2;
	}

	@Override
	public ItemStack getStack(int slot) {
		if (slot == 0) return fuel;
		else if (slot == 1) return core;
		else return ItemStack.EMPTY;
	}

    @Override
    public ItemStack removeStack(int slot, int amount) {
		if (slot == 0) {
			fuel.decrement(amount);
			return fuel;
		} else if (slot == 1) {
			core.decrement(amount);
			return core;
		}
		else return ItemStack.EMPTY;
	}

    @Override
    public ItemStack removeStack(int index) {
		if (index == 0) return fuel;
		else if (index == 1) return core;
		else return ItemStack.EMPTY;
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		if (slot == 0) fuel = stack;
		else if (slot == 1) core = stack;
	}

	@Override
	public int getMaxCountPerStack()
	{
		return 1;
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player)
	{
		return true;
	}

    @Override
    public boolean isValid(int slot, ItemStack stack) {
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
    public void markRemoved() {
        super.markRemoved();
        on = false;
        for (TileEntityMachineBase machine : machines) {
            if (machine.isRemoved()) continue;
            machine.pos = BlockPos.ORIGIN;
            machine.edist = 0;
        }
        EntityRhodes.BLOCK_ENTITIES.remove(getPos());
    }

    @Override
	public Box getRenderBoundingBox()
	{
		return new Box(getPos().add(-100, -100, -100), getPos().add(100, 100, 100));
	}

	public float getPower()
	{
		if (!core.isEmpty() && !fuel.isEmpty())
		{
			ItemCore c = (ItemCore) core.getItem();
			ItemRod r = (ItemRod) fuel.getItem();
			return ((r.power * c.timemult) - fuel.getOrCreateNbt().getInt("fuelLeft"));
		}
		return 0;
	}

    @Override
    public Text getDisplayName() {
        return Text.of("Tokamak");
    }

	public void toggleOn()
	{
		on = !on;
	}

	public void ejectCore()
	{
		eject = true;
	}

    @Override
    public boolean isEmpty() {
        return this.core.isEmpty() && this.fuel.isEmpty();
    }

    @Override
    public void clear() {
        this.core = ItemStack.EMPTY;
        this.fuel = ItemStack.EMPTY;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ContainerReactor(syncId, inv, this, propertyDelegate);
    }

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> on ? 1 : 0;
                case 1 -> Float.floatToIntBits(getPower());
                case 2 -> (int) consumed;
                case 3 -> melt ? 1 : 0;
                case 4 -> getPos().getX();
                case 5 -> getPos().getY();
                case 6 -> getPos().getZ();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 4 -> toggleOn();
                case 5 -> ejectCore();
                default -> {}
            }
        }

        @Override
        public int size() {
            return 7;
        }
    };
}
