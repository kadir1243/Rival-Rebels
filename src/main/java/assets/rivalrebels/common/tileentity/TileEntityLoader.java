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
import assets.rivalrebels.common.item.ItemRod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class TileEntityLoader extends TileEntity implements IInventory, ITickable
{
	private final NonNullList<ItemStack> chestContents	= NonNullList.withSize(64, ItemStack.EMPTY);

	public double			slide			= 0;
	double					test			= Math.PI;
	int						counter;

	public List<TileEntity> machines		= new ArrayList<>();

	@Override
	public int getSizeInventory()
	{
		return 60;
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
		return new AxisAlignedBB(getPos().add(-5, -1, -5), getPos().add(6, 2, 6));
	}

	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return this.chestContents.get(par1);
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (!this.chestContents.get(par1).isEmpty())
		{
			ItemStack var3;

			if (this.chestContents.get(par1).getCount() <= par2)
			{
				var3 = this.chestContents.get(par1);
				this.chestContents.set(par1, ItemStack.EMPTY);
            }
			else
			{
				var3 = this.chestContents.get(par1).splitStack(par2);

				if (this.chestContents.get(par1).isEmpty())
				{
					this.chestContents.set(par1, ItemStack.EMPTY);
				}

            }
            return var3;
        }
		return ItemStack.EMPTY;
	}

    @Override
    public ItemStack removeStackFromSlot(int index) {
		if (!this.chestContents.get(index).isEmpty())
		{
			ItemStack var2 = this.chestContents.get(index);
			this.chestContents.set(index, ItemStack.EMPTY);
			return var2;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.chestContents.set(index, stack);

		if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
        ItemStackHelper.loadAllItems(nbt, this.chestContents);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

        ItemStackHelper.saveAllItems(nbt, this.chestContents);
        return nbt;
    }

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.getPos().getX() + 0.5D, this.getPos().getY() + 0.5D, this.getPos().getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void update() {
		slide = (Math.cos(test) + 1) / 32 * 14;

        boolean i = false;
        for (EntityPlayer player : world.playerEntities) {
            if (player.getDistanceSq(getPos().getX() + 0.5f, getPos().getY() + 0.5f, getPos().getZ() + 0.5f) <= 9) {
                i = true;
            }
        }
		if (i)
		{
			if (slide < 0.871) test += 0.05;
		}
		else
		{
			if (slide > 0.004) test -= 0.05;
		}
		counter++;
		if (counter % 10 == 0)
		{
			for (int x = 1; x < 7; x++)
			{
				TileEntity te = world.getTileEntity(getPos().east(x));
				if ((te instanceof TileEntityReactor || te instanceof TileEntityReciever))
				{
					machines.add(te);
				}
				te = world.getTileEntity(getPos().west(x));
				if ((te instanceof TileEntityReactor || te instanceof TileEntityReciever))
				{
					machines.add(te);
				}
				te = world.getTileEntity(getPos().south(x));
				if ((te instanceof TileEntityReactor || te instanceof TileEntityReciever))
				{
					machines.add(te);
				}
				te = world.getTileEntity(getPos().north(x));
				if ((te instanceof TileEntityReactor || te instanceof TileEntityReciever))
				{
					machines.add(te);
				}
			}
			for (int index = 0; index < machines.size(); index++)
			{
				TileEntity te = machines.get(index);
				if (te != null && !te.isInvalid())
				{
					if (te instanceof TileEntityReactor ter)
					{
                        if (ter.on)
						{
							for (int q = 0; q < chestContents.size(); q++)
							{
								if (ter.fuel.isEmpty())
								{
									if (!chestContents.get(q).isEmpty() && chestContents.get(q).getItem() instanceof ItemRod && chestContents.get(q).getItem() != RivalRebels.emptyrod)
									{
										ter.fuel = chestContents.get(q);
										chestContents.set(q, new ItemStack(RivalRebels.emptyrod, 1));
									}
								}
								else
								{
									break;
								}
							}
						}
					}
					if (te instanceof TileEntityReciever ter)
					{
                        for (int q = 0; q < chestContents.size(); q++)
						{
							if (!chestContents.get(q).isEmpty() && chestContents.get(q).getItem() == RivalRebels.fuel)
							{
								int amount = chestContents.get(q).getCount();
								if (ter.chestContents.get(0).isEmpty() || ter.chestContents.get(0).getCount() < 64)
								{
									if (ter.chestContents.get(0).isEmpty())
									{
										ter.chestContents.set(0, chestContents.get(q).copy());
										ter.chestContents.get(0).setCount(amount);
									}
									else ter.chestContents.get(0).grow(amount);
									amount = 0;
									if (ter.chestContents.get(0).getCount() > 64)
									{
										amount = ter.chestContents.get(0).getCount() - 64;
										ter.chestContents.get(0).setCount(64);
									}
								}
								else if (ter.chestContents.get(1).isEmpty() || ter.chestContents.get(1).getCount() < 64)
								{
									if (ter.chestContents.get(1).isEmpty())
									{
										ter.chestContents.set(1, chestContents.get(q).copy());
										ter.chestContents.get(1).setCount(amount);
									}
									else ter.chestContents.get(1).grow(amount);
									amount = 0;
									if (ter.chestContents.get(1).getCount() > 64)
									{
										amount = ter.chestContents.get(1).getCount() - 64;
										ter.chestContents.get(1).setCount(64);
									}
								}
								else if (ter.chestContents.get(2).isEmpty() || ter.chestContents.get(2).getCount() < 64)
								{
									if (ter.chestContents.get(2).isEmpty())
									{
										ter.chestContents.set(2, chestContents.get(q).copy());
										ter.chestContents.get(2).setCount(amount);
									}
									else ter.chestContents.get(2).grow(amount);
									amount = 0;
									if (ter.chestContents.get(2).getCount() > 64)
									{
										amount = ter.chestContents.get(2).getCount() - 64;
										ter.chestContents.get(2).setCount(64);
									}
								}
								chestContents.get(q).setCount(amount);
								if (chestContents.get(q).isEmpty()) chestContents.set(q, ItemStack.EMPTY);
							}
							if (!chestContents.get(q).isEmpty() && chestContents.get(q).getItem() == RivalRebels.battery)
							{
								int amount = chestContents.get(q).getCount();
								if (ter.chestContents.get(3).isEmpty() || ter.chestContents.get(3).getCount() < 16)
								{
									if (ter.chestContents.get(3).isEmpty())
									{
										ter.chestContents.set(3, chestContents.get(q).copy());
										ter.chestContents.get(3).setCount(amount);
									}
									else ter.chestContents.get(3).grow(amount);
									amount = 0;
									if (ter.chestContents.get(3).getCount() > 16)
									{
										amount = ter.chestContents.get(3).getCount() - 16;
										ter.chestContents.get(3).setCount(16);
									}
								}
								else if (ter.chestContents.get(4).isEmpty() || ter.chestContents.get(4).getCount() < 16)
								{
									if (ter.chestContents.get(4).isEmpty())
									{
										ter.chestContents.set(4, chestContents.get(q).copy());
										ter.chestContents.get(4).setCount(amount);
									}
									else ter.chestContents.get(4).grow(amount);
									amount = 0;
									if (ter.chestContents.get(4).getCount() > 16)
									{
										amount = ter.chestContents.get(4).getCount() - 16;
										ter.chestContents.get(4).setCount(16);
									}
								}
								else if (ter.chestContents.get(5).isEmpty() || ter.chestContents.get(5).getCount() < 16)
								{
									if (ter.chestContents.get(5).isEmpty())
									{
										ter.chestContents.set(5, chestContents.get(q).copy());
										ter.chestContents.get(5).setCount(amount);
									}
									else ter.chestContents.get(5).grow(amount);
									amount = 0;
									if (ter.chestContents.get(5).getCount() > 16)
									{
										amount = ter.chestContents.get(5).getCount() - 16;
										ter.chestContents.get(5).setCount(16);
									}
								}
								chestContents.get(q).setCount(amount);
								if (chestContents.get(q).isEmpty()) chestContents.set(q, ItemStack.EMPTY);
							}
						}
					}
				}
				else
				{
					machines.remove((int) index);
				}
			}
			TileEntity te = world.getTileEntity(getPos().down());
			if (te instanceof TileEntityLoader tel)
			{
                for (int q = 0; q < chestContents.size(); q++)
				{
					if (!chestContents.get(q).isEmpty())
					{
						for (int j = 0; j < tel.chestContents.size(); j++)
						{
							if (tel.chestContents.get(j).isEmpty())
							{
								tel.chestContents.set(j, chestContents.get(q));
								chestContents.set(q, ItemStack.EMPTY);
								return;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void invalidate()
	{
		super.invalidate();
		this.updateContainingBlockInfo();
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return true;
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
	public String getName()
	{
		return "Loader";
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

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.chestContents) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        this.chestContents.clear();
    }
}
