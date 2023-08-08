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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Iterator;
import java.util.List;

public class TileEntityReactor extends TileEntity implements IInventory, ITickable
{
	public double			slide				= 90;
	private double			test				= Math.PI;
	public ItemStack		core;
	public ItemStack		fuel;
	public boolean			on					= false;
	public boolean			prevOn				= false;
	public boolean			melt				= false;
	public int				meltTick			= 0;
	public boolean			eject				= false;
	public double			consumed			= 0;
	public double			lasttickconsumed	= 0;
	public int				tickssincelastrod	= 0;
	public boolean			lastrodwasredstone	= false;
	public TileEntityList	machines			= new TileEntityList();
	public int				tick				= 0;
	public long				lastPacket			= System.currentTimeMillis();

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		int c = par1NBTTagCompound.getInteger("core");
		int f = par1NBTTagCompound.getInteger("fuel");
		if (c != 0) core = new ItemStack(Item.getItemById(c));
		if (f != 0) fuel = new ItemStack(Item.getItemById(f));
		consumed = par1NBTTagCompound.getInteger("consumed");
		on = par1NBTTagCompound.getBoolean("on");
        TileEntityList list = TileEntityList.readWithOnlyPositions(worldObj, par1NBTTagCompound);
        list.removeIf(tileEntity -> !(tileEntity instanceof TileEntityMachineBase));
        machines.addAll(list);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		if (core != null) par1NBTTagCompound.setInteger("core", Item.getIdFromItem(core.getItem()));
		if (fuel != null) par1NBTTagCompound.setInteger("fuel", Item.getIdFromItem(fuel.getItem()));
		par1NBTTagCompound.setInteger("consumed", (int) consumed);
		par1NBTTagCompound.setBoolean("on", on);
		if (on) {
            machines.writeOnlyPositionsWithInclude(par1NBTTagCompound, tileEntity -> !(tileEntity instanceof TileEntityReactive));
        }
	}

	@Override
	public void update() {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.CLIENT)
		{
			slide = (Math.cos(test) + 1) * 45;
			List<EntityPlayer> players = worldObj.playerEntities;
			Iterator<EntityPlayer> iter = players.iterator();
			boolean flag = false;
			while (iter.hasNext())
			{
				EntityPlayer player = iter.next();
				if (player.getDistanceSq(pos.add(0.5f, 0.5f, 0.5f)) <= 9)
				{
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
			if (core == null)
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
				fuel = null;
				core = null;
				melt = false;
				meltTick = 0;
				on = false;
				eject = false;
			}

			if (melt)
			{
				if (core != null)
				{
					for (int i = 0; i < 4; i++)
					{
						worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, Math.random() - 0.5, Math.random() / 2, Math.random() - 0.5);
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
		else if (side == Side.SERVER)
		{
			if (eject)
			{
				if (core != null)
				{
					consumed = 0;
					lasttickconsumed = 0;
					worldObj.spawnEntityInWorld(new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, core));
					fuel = null;
					core = null;
					melt = false;
					meltTick = 0;
					on = false;
				}
			}

			if (melt)
			{
				if (core != null)
				{
					if (meltTick % 20 == 0) RivalRebelsSoundPlayer.playSound(worldObj, 21, 1, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
					on = true;
					meltTick++;
					if (meltTick == 300) meltDown(10);
					else if (meltTick == 1) {
                        for (EntityPlayer player : worldObj.playerEntities) {
                            if (player.getPosition().distanceSq(this.getPos()) < 50) {
                                player.addChatMessage(new ChatComponentTranslation("RivalRebels.reactor.meltdown"));
                            }
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

			if (fuel == null && tickssincelastrod != 0)
			{
				tickssincelastrod++;
				if (tickssincelastrod >= 100)
				{
					if (lastrodwasredstone) on = false;
					else melt = true;
				}
				if (tickssincelastrod == 20 && !lastrodwasredstone) {
                    for (EntityPlayer player : worldObj.playerEntities) {
                        player.addChatMessage(new ChatComponentTranslation("RivalRebels.overheat"));
                    }
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

			if (core == null)
			{
				on = false;
				consumed = 0;
				lasttickconsumed = 0;
				melt = false;
				meltTick = 0;
			}

			if (on && core != null && fuel != null && core.getItem() instanceof ItemCore && fuel.getItem() instanceof ItemRod)
			{
                ItemCore c = (ItemCore) core.getItem();
                ItemRod r = (ItemRod) fuel.getItem();
                if (!prevOn && on) RivalRebelsSoundPlayer.playSound(worldObj, 21, 3, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
				else
				{
					tick++;
					if (on && tick % 39 == 0) RivalRebelsSoundPlayer.playSound(worldObj, 21, 2, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.9f, 0.77f);
				}
                if (!fuel.hasTagCompound()) fuel.setTagCompound(new NBTTagCompound());
				float power = ((r.power * c.timemult) - fuel.getTagCompound().getInteger("fuelLeft"));
				float temp = power;
                for (TileEntity te : worldObj.loadedTileEntityList) {
                    if (te instanceof TileEntityMachineBase) {
                        TileEntityMachineBase temb = (TileEntityMachineBase) te;
                        if (worldObj.getTileEntity(temb.rpos) == null) {
                            double dist = temb.getDistanceSq(pos.getX(), pos.getY(), pos.getZ());
                            if (dist < 1024) {
                                temb.rpos = getPos();
                                temb.edist = (float) Math.sqrt(dist);
                                machines.add(temb);
                            }
                        }
                        if (getPos().equals(temb.rpos)) {
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
						if (worldObj.rand.nextFloat() < f8)
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
					fuel = null;
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
					int rand = worldObj.rand.nextInt(4);
					if (rand == 0) for (int i = 0; i < 16; i++)
						worldObj.setBlock(pos.getX() + x, pos.getY() - 1, pos.getZ() + z, RivalRebels.petrifiedstone1, (int) (dist * 2f) + 1, 2);
					if (rand == 1) for (int i = 0; i < 16; i++)
						worldObj.setBlock(pos.getX() + x, pos.getY() - 1, pos.getZ() + z, RivalRebels.petrifiedstone2, (int) (dist * 2f) + 1, 2);
					if (rand == 2) for (int i = 0; i < 16; i++)
						worldObj.setBlock(pos.getX() + x, pos.getY() - 1, pos.getZ() + z, RivalRebels.petrifiedstone3, (int) (dist * 2f) + 1, 2);
					if (rand == 3) for (int i = 0; i < 16; i++)
						worldObj.setBlock(pos.getX() + x, pos.getY() - 1, pos.getZ() + z, RivalRebels.petrifiedstone4, (int) (dist * 2f) + 1, 2);

					worldObj.setBlock(pos.getX() + x, pos.getY() - 2, pos.getZ() + z, RivalRebels.radioactivedirt);
				}
				else if (dist < radius)
				{
					worldObj.setBlock(pos.getX() + x, pos.getY() - 2, pos.getZ() + z, RivalRebels.radioactivedirt);
				}
			}
		}*/
		worldObj.setBlockState(pos, RivalRebels.meltdown.getDefaultState());
		new Explosion(worldObj, pos.getX(), pos.getY() - 2, pos.getZ(), 4, false, false, RivalRebelsDamageSource.rocket);
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
		else return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if (i == 0)
		{
			fuel.stackSize -= j;
			return fuel;
		}
		else if (i == 1)
		{
			core.stackSize -= j;
			return core;
		}
		else return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int i)
	{
		if (i == 0) return fuel;
		else if (i == 1) return core;
		else return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		if (i == 0) fuel = itemstack;
		else if (i == 1) core = itemstack;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		if (itemstack == null || !(itemstack.getItem() instanceof ItemRod) || !(itemstack.getItem() instanceof ItemCore)) return false;
		if (i == 0)
		{
			return fuel == null || !on;
		}
		if (i == 1)
		{
			return !on;
		}
		return false;
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
		return new AxisAlignedBB(pos.getX()-100, pos.getY()-100, pos.getZ()-100, pos.getX()+100, pos.getY()+100, pos.getZ()+100);
	}

	public float getPower()
	{
		if (core != null && fuel != null)
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
    public int getField(int id) {
        return 0;
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public void clear() {

    }

    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(getName());
    }
}
