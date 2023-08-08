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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;

public class TileEntityLoader extends TileEntity implements IInventory, ITickable
{
	private ItemStack[]		chestContents	= new ItemStack[64];

	public double			slide			= 0;
	double					test			= Math.PI;
	int						counter;

	public TileEntityList	machines		= new TileEntityList();

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
		return new AxisAlignedBB(pos.add(-5, -1, -5), pos.add(6, 2, 6));
	}

	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return this.chestContents[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (this.chestContents[par1] != null)
		{
			ItemStack var3;

			if (this.chestContents[par1].stackSize <= par2)
			{
				var3 = this.chestContents[par1];
				this.chestContents[par1] = null;

            }
			else
			{
				var3 = this.chestContents[par1].splitStack(par2);

				if (this.chestContents[par1].stackSize == 0)
				{
					this.chestContents[par1] = null;
				}

            }
            return var3;
        }
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int par1)
	{
		if (this.chestContents[par1] != null)
		{
			ItemStack var2 = this.chestContents[par1];
			this.chestContents[par1] = null;
			return var2;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		this.chestContents[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		this.chestContents = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbt1 = nbttaglist.getCompoundTagAt(i);
			int j = nbt1.getByte("Slot") & 255;

			if (j >= 0 && j < this.chestContents.length)
			{
				this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.chestContents.length; ++i)
		{
			if (this.chestContents[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("Slot", (byte) i);
				this.chestContents[i].writeToNBT(nbt1);
				nbttaglist.appendTag(nbt1);
			}
		}

		nbt.setTag("Items", nbttaglist);
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return this.worldObj.getTileEntity(this.pos) == this && par1EntityPlayer.getDistanceSq(this.pos.add(0.5D, 0.5D, 0.5D)) <= 64.0D;
	}

	@Override
	public void update() {
		slide = (Math.cos(test) + 1) / 32 * 14;

        boolean thereIsAPlayerInDistance = false;
        List<EntityPlayer> players = worldObj.playerEntities;
        for (EntityPlayer player : players) {
            if (player.getDistanceSq(pos.add(0.5F, 0.5F, 0.5F)) <= 9) {
                thereIsAPlayerInDistance = true;
            }
        }
		if (thereIsAPlayerInDistance)
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
				TileEntity te = worldObj.getTileEntity(pos.east(x));
				if (te instanceof TileEntityReactor || te instanceof TileEntityReciever) {
					machines.add(te);
				}
				te = worldObj.getTileEntity(pos.west(x));
				if (te instanceof TileEntityReactor || te instanceof TileEntityReciever) {
					machines.add(te);
				}
				te = worldObj.getTileEntity(pos.south(x));
				if (te instanceof TileEntityReactor || te instanceof TileEntityReciever) {
					machines.add(te);
				}
				te = worldObj.getTileEntity(pos.north(x));
				if (te instanceof TileEntityReactor || te instanceof TileEntityReciever) {
					machines.add(te);
				}
			}
			for (int index = 0; index < machines.size(); index++)
			{
				TileEntity te = machines.get(index);
				if (te != null && !te.isInvalid())
				{
					if (te instanceof TileEntityReactor)
					{
						TileEntityReactor ter = (TileEntityReactor) te;
						if (ter.on)
						{
							for (int q = 0; q < chestContents.length; q++)
							{
								if (ter.fuel == null)
								{
									if (chestContents[q] != null && chestContents[q].getItem() instanceof ItemRod && chestContents[q].getItem() != RivalRebels.emptyrod)
									{
										ter.fuel = chestContents[q];
										chestContents[q] = new ItemStack(RivalRebels.emptyrod, 1);
									}
								}
								else
								{
									break;
								}
							}
						}
					}
					if (te instanceof TileEntityReciever)
					{
						TileEntityReciever ter = (TileEntityReciever) te;
						for (int q = 0; q < chestContents.length; q++)
						{
							if (chestContents[q] != null && chestContents[q].getItem() == RivalRebels.fuel)
							{
								int amount = chestContents[q].stackSize;
								if (ter.chestContents[0] == null || ter.chestContents[0].stackSize < 64)
								{
									if (ter.chestContents[0] == null)
									{
										ter.chestContents[0] = chestContents[q].copy();
										ter.chestContents[0].stackSize = amount;
									}
									else ter.chestContents[0].stackSize += amount;
									amount = 0;
									if (ter.chestContents[0].stackSize > 64)
									{
										amount = ter.chestContents[0].stackSize - 64;
										ter.chestContents[0].stackSize = 64;
									}
								}
								else if (ter.chestContents[1] == null || ter.chestContents[1].stackSize < 64)
								{
									if (ter.chestContents[1] == null)
									{
										ter.chestContents[1] = chestContents[q].copy();
										ter.chestContents[1].stackSize = amount;
									}
									else ter.chestContents[1].stackSize += amount;
									amount = 0;
									if (ter.chestContents[1].stackSize > 64)
									{
										amount = ter.chestContents[1].stackSize - 64;
										ter.chestContents[1].stackSize = 64;
									}
								}
								else if (ter.chestContents[2] == null || ter.chestContents[2].stackSize < 64)
								{
									if (ter.chestContents[2] == null)
									{
										ter.chestContents[2] = chestContents[q].copy();
										ter.chestContents[2].stackSize = amount;
									}
									else ter.chestContents[2].stackSize += amount;
									amount = 0;
									if (ter.chestContents[2].stackSize > 64)
									{
										amount = ter.chestContents[2].stackSize - 64;
										ter.chestContents[2].stackSize = 64;
									}
								}
								chestContents[q].stackSize = amount;
								if (chestContents[q].stackSize == 0) chestContents[q] = null;
							}
							if (chestContents[q] != null && chestContents[q].getItem() == RivalRebels.battery)
							{
								int amount = chestContents[q].stackSize;
								if (ter.chestContents[3] == null || ter.chestContents[3].stackSize < 16)
								{
									if (ter.chestContents[3] == null)
									{
										ter.chestContents[3] = chestContents[q].copy();
										ter.chestContents[3].stackSize = amount;
									}
									else ter.chestContents[3].stackSize += amount;
									amount = 0;
									if (ter.chestContents[3].stackSize > 16)
									{
										amount = ter.chestContents[3].stackSize - 16;
										ter.chestContents[3].stackSize = 16;
									}
								}
								else if (ter.chestContents[4] == null || ter.chestContents[4].stackSize < 16)
								{
									if (ter.chestContents[4] == null)
									{
										ter.chestContents[4] = chestContents[q].copy();
										ter.chestContents[4].stackSize = amount;
									}
									else ter.chestContents[4].stackSize += amount;
									amount = 0;
									if (ter.chestContents[4].stackSize > 16)
									{
										amount = ter.chestContents[4].stackSize - 16;
										ter.chestContents[4].stackSize = 16;
									}
								}
								else if (ter.chestContents[5] == null || ter.chestContents[5].stackSize < 16)
								{
									if (ter.chestContents[5] == null)
									{
										ter.chestContents[5] = chestContents[q].copy();
										ter.chestContents[5].stackSize = amount;
									}
									else ter.chestContents[5].stackSize += amount;
									amount = 0;
									if (ter.chestContents[5].stackSize > 16)
									{
										amount = ter.chestContents[5].stackSize - 16;
										ter.chestContents[5].stackSize = 16;
									}
								}
								chestContents[q].stackSize = amount;
								if (chestContents[q].stackSize == 0) chestContents[q] = null;
							}
						}
					}
				}
				else
				{
					machines.remove(index);
				}
			}
			TileEntity te = worldObj.getTileEntity(pos.down());
			if (te instanceof TileEntityLoader)
			{
                TileEntityLoader tel = (TileEntityLoader) te;
                for (int q = 0; q < chestContents.length; q++)
				{
					if (chestContents[q] != null)
					{
						for (int j = 0; j < tel.chestContents.length; j++)
						{
							if (tel.chestContents[j] == null)
							{
								tel.chestContents[j] = chestContents[q];
								chestContents[q] = null;
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
	public void openInventory(EntityPlayer player)
	{

	}

	@Override
	public void closeInventory(EntityPlayer player)
	{

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
    public void clear() {
        Arrays.fill(chestContents, null);
    }

    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(getName());
    }
}
