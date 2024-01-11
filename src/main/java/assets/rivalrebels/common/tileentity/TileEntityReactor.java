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
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.explosion.Explosion;
import assets.rivalrebels.common.item.ItemCore;
import assets.rivalrebels.common.item.ItemRod;
import assets.rivalrebels.common.item.ItemRodNuclear;
import assets.rivalrebels.common.item.ItemRodRedstone;
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.packet.ReactorUpdatePacket;
import assets.rivalrebels.common.packet.TextPacket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class TileEntityReactor extends TileEntity implements IInventory, ITickable {
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
	public List<TileEntity>	machines			= new ArrayList<>();
	public int				tick				= 0;
	public long				lastPacket			= System.currentTimeMillis();

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		int c = par1NBTTagCompound.getInteger("core");
		int f = par1NBTTagCompound.getInteger("fuel");
		if (c != 0) core = Item.getItemById(c).getDefaultInstance();
		if (f != 0) fuel = Item.getItemById(f).getDefaultInstance();
		consumed = par1NBTTagCompound.getInteger("consumed");
		on = par1NBTTagCompound.getBoolean("on");
		int i = 0;
		while (par1NBTTagCompound.hasKey("mx" + i))
		{
            if (hasWorld()) {
				TileEntity te = world.getTileEntity(BlockPos.fromLong(par1NBTTagCompound.getLong("mpos" + i)));
				if (te instanceof TileEntityMachineBase)
				{
					machines.add(te);
				}
			}
			i++;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		if (!core.isEmpty()) par1NBTTagCompound.setInteger("core", Item.getIdFromItem(core.getItem()));
		if (!fuel.isEmpty()) par1NBTTagCompound.setInteger("fuel", Item.getIdFromItem(fuel.getItem()));
		par1NBTTagCompound.setInteger("consumed", (int) consumed);
		par1NBTTagCompound.setBoolean("on", on);
		if (on) for (int i = 0; i < machines.size(); i++)
		{
			TileEntityMachineBase te = (TileEntityMachineBase) machines.get(i);
			if (te == null || te instanceof TileEntityReactive) continue;
			par1NBTTagCompound.setLong("mpos" + i, te.getPos().toLong());
		}
        return par1NBTTagCompound;
    }

	@Override
	public void update() {
        if (world.isRemote)
		{
			slide = (Math.cos(test) + 1) * 45;
            boolean flag = false;
            for (EntityPlayer player : world.playerEntities) {
                if (player.getDistanceSq(getPos().getX() + 0.5f, getPos().getY() + 0.5f, getPos().getZ() + 0.5f) <= 9) {
                    flag = true;
                    break;
                }
            }
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
						world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5, world.rand.nextDouble() - 0.5, world.rand.nextDouble() / 2, world.rand.nextDouble() - 0.5);
					}
				}
				else
				{
					melt = false;
					meltTick = 0;
					on = false;
				}
			}
			prevOn = on;
		}
		else {
			if (eject)
			{
				if (!core.isEmpty())
				{
					consumed = 0;
					lasttickconsumed = 0;
					world.spawnEntity(new EntityItem(world, getPos().getX() + 0.5, getPos().getY() + 1, getPos().getZ() + 0.5, core));
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
					else if (meltTick == 1) PacketDispatcher.packetsys.sendToAll(new TextPacket("RivalRebels.WARNING RivalRebels.meltdown"));
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
                if (!fuel.hasTagCompound()) fuel.setTagCompound(new NBTTagCompound());
				float power = ((r.power * c.timemult) - fuel.getTagCompound().getInteger("fuelLeft"));
				float temp = power;
                for (TileEntity te : world.loadedTileEntityList) {
                    if (te instanceof TileEntityMachineBase temb) {
                        if (world.getTileEntity(temb.pos) == null) {
                            double dist = temb.getDistanceSq(getPos().getX(), getPos().getY(), getPos().getZ());
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
				if (fuel.getTagCompound().hasKey("fuelLeft"))
				{
					fuel.getTagCompound().setInteger("fuelLeft", (int) consumed);

					double fuelLeft = fuel.getTagCompound().getInteger("fuelLeft");
					double fuelPercentage = (fuelLeft / temp);

					if (r instanceof ItemRodNuclear)
					{
						double f2 = fuelPercentage * fuelPercentage;
						double f4 = f2 * f2;
						double f8 = f4 * f4;
						if (world.rand.nextFloat() < f8)
						{
							melt = true;
						}
					}
				}
				else fuel.getTagCompound().setInteger("fuelLeft", 0);
				if (fuel.getTagCompound().getInteger("fuelLeft") >= temp)
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
			if (System.currentTimeMillis() - lastPacket > 1000)
			{
				lastPacket = System.currentTimeMillis();
				PacketDispatcher.packetsys.sendToAll(new ReactorUpdatePacket(getPos(), (float) consumed, (float) lasttickconsumed, melt, eject, on, machines));
			}
		}
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
					int rand = world.rand.nextInt(4);
					if (rand == 0) for (int i = 0; i < 16; i++)
						world.setBlock(getPos().getX() + x, getPos().getY() - 1, getPos().getZ() + z, RivalRebels.petrifiedstone1, (int) (dist * 2f) + 1, 2);
					if (rand == 1) for (int i = 0; i < 16; i++)
						world.setBlock(getPos().getX() + x, getPos().getY() - 1, getPos().getZ() + z, RivalRebels.petrifiedstone2, (int) (dist * 2f) + 1, 2);
					if (rand == 2) for (int i = 0; i < 16; i++)
						world.setBlock(getPos().getX() + x, getPos().getY() - 1, getPos().getZ() + z, RivalRebels.petrifiedstone3, (int) (dist * 2f) + 1, 2);
					if (rand == 3) for (int i = 0; i < 16; i++)
						world.setBlock(getPos().getX() + x, getPos().getY() - 1, getPos().getZ() + z, RivalRebels.petrifiedstone4, (int) (dist * 2f) + 1, 2);

					world.setBlock(getPos().getX() + x, getPos().getY() - 2, getPos().getZ() + z, RivalRebels.radioactivedirt);
				}
				else if (dist < radius)
				{
					world.setBlock(getPos().getX() + x, getPos().getY() - 2, getPos().getZ() + z, RivalRebels.radioactivedirt);
				}
			}
		}*/
		world.setBlockState(getPos(), RivalRebels.meltdown.getDefaultState());
		new Explosion(world, getPos().getX(), getPos().getY() - 2, getPos().getZ(), 4, false, false, RivalRebelsDamageSource.rocket);
	}

	@Override
	public int getSizeInventory()
	{
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		if (i == 0) return fuel;
		else if (i == 1) return core;
		else return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if (i == 0)
		{
			fuel.shrink(j);
			return fuel;
		}
		else if (i == 1)
		{
			core.shrink(j);
			return core;
		}
		else return ItemStack.EMPTY;
	}

    @Override
    public ItemStack removeStackFromSlot(int index) {
		if (index == 0) return fuel;
		else if (index == 1) return core;
		else return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if (index == 0) fuel = stack;
		else if (index == 1) core = stack;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		if (itemstack.isEmpty() || !(itemstack.getItem() instanceof ItemRod) || !(itemstack.getItem() instanceof ItemCore)) return false;
		if (i == 0)
		{
			return fuel.isEmpty() || !on;
		}
		if (i == 1)
		{
			return !on;
		}
		return false;
	}

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 16384.0D;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return new AxisAlignedBB(getPos().add(-100, -100, -100), getPos().add(100, 100, 100));
	}

	public float getPower()
	{
		if (!core.isEmpty() && !fuel.isEmpty())
		{
			ItemCore c = (ItemCore) core.getItem();
			ItemRod r = (ItemRod) fuel.getItem();
			if (!fuel.hasTagCompound()) fuel.setTagCompound(new NBTTagCompound());
			return ((r.power * c.timemult) - fuel.getTagCompound().getInteger("fuelLeft"));
		}
		return 0;
	}

	@Override
	public String getName()
	{
		return "Tokamak";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
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
}
