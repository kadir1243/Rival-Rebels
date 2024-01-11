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
import assets.rivalrebels.common.item.weapon.ItemBinoculars;
import assets.rivalrebels.common.packet.LaptopRefreshPacket;
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

public class TileEntityLaptop extends TileEntity implements IInventory, ITickable
{
	public String			username		= null;
	public RivalRebelsTeam	rrteam			= null;
	private final NonNullList<ItemStack> chestContents = NonNullList.withSize(14, ItemStack.EMPTY);

	public double			slide			= 0;
	double					test			= Math.PI;
	public int				b2spirit		= 0;
	public int				b2carpet		= 0;

    @Override
	public int getSizeInventory()
	{
		return 14;
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
	public ItemStack getStackInSlot(int index)
	{
		return this.chestContents.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if (!this.chestContents.get(index).isEmpty())
		{
			ItemStack var3;

			if (this.chestContents.get(index).getCount() <= count)
			{
				var3 = this.chestContents.get(index);
				this.chestContents.set(index, ItemStack.EMPTY);
            }
			else
			{
				var3 = this.chestContents.get(index).splitStack(count);

				if (this.chestContents.get(index).isEmpty())
				{
					this.chestContents.set(index, ItemStack.EMPTY);
				}

            }
            return var3;
        }
		return ItemStack.EMPTY;
	}

    @Override
    public ItemStack removeStackFromSlot(int index) {
		if (!this.chestContents.get(index).isEmpty()) {
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

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

        ItemStackHelper.loadAllItems(nbt, this.chestContents);
		b2spirit = nbt.getInteger("b2spirit");
		b2carpet = nbt.getInteger("b2carpet");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

        ItemStackHelper.saveAllItems(nbt, this.chestContents);
		nbt.setInteger("b2spirit", b2spirit);
		nbt.setInteger("b2carpet", b2carpet);
        return nbt;
    }

	/**
	 * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't this more of a set than a get?*
	 */
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes with Container
	 */
	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.getPos().getX() + 0.5D, this.getPos().getY() + 0.5D, this.getPos().getZ() + 0.5D) <= 64.0D;
	}

	public void onGoButtonPressed()
	{
		if (isReady())
		{
			for (int j = 0; j < 3; j++)
			{
				if (!getStackInSlot(j + 6).isEmpty() && !getStackInSlot(j + 11).isEmpty())
				{
					if (getStackInSlot(j + 6).getItem() == RivalRebels.nuclearelement
							&& getStackInSlot(j + 11).getItem() == RivalRebels.hydrod)
					{
						b2spirit++;
						setInventorySlotContents(j+6, ItemStack.EMPTY);
						setInventorySlotContents(j+11, ItemStack.EMPTY);
					}
					else if (getStackInSlot(j + 6).getItem() == Item.getItemFromBlock(RivalRebels.timedbomb)
							&& getStackInSlot(j + 11).getItem() == Item.getItemFromBlock(RivalRebels.timedbomb))
					{
						b2carpet++;
						setInventorySlotContents(j+6, ItemStack.EMPTY);
						setInventorySlotContents(j+11, ItemStack.EMPTY);
					}
				}
			}
			setInventorySlotContents(4, ItemStack.EMPTY);
			setInventorySlotContents(5, ItemStack.EMPTY);
			setInventorySlotContents(9, ItemStack.EMPTY);
			setInventorySlotContents(10, ItemStack.EMPTY);
		}
		if (RivalRebels.freeb83nukes) {b2spirit += 10;b2carpet += 10;}
		refreshTasks();
	}

	public boolean hasChips()
	{
		boolean r = true;
		rrteam = RivalRebelsTeam.NONE;
		for (int j = 0; j < 4; j++)
		{
			if (getStackInSlot(j).isEmpty()) r = false;
			else
			{
				if (!getStackInSlot(j).hasTagCompound()) getStackInSlot(j).setTagCompound(new NBTTagCompound());
				if (rrteam == RivalRebelsTeam.NONE) rrteam = RivalRebelsTeam.getForID(getStackInSlot(j).getTagCompound().getInteger("team"));
				else if (rrteam != RivalRebelsTeam.getForID(getStackInSlot(j).getTagCompound().getInteger("team"))) r = false;
			}
		}
		return r;
	}

    @Override
	public void update() {
		slide = (Math.cos(test) + 1) * 45;

        ItemBinoculars.add(this);
        boolean i = false;
        for (EntityPlayer player : world.playerEntities) {
            if (player.getDistanceSq(getPos().getX() + 0.5f, getPos().getY() + 0.5f, getPos().getZ() + 0.5f) <= 9) {
                i = true;
            }
        }
		if (i)
		{
			if (slide < 89.995) test += 0.05;
		}
		else
		{
			if (slide > 0.004) test -= 0.05;
		}

		if (b2spirit > 0 && !hasChips())
		{
			b2spirit--;
			refreshTasks();
		}
	}

    @Override
	public void invalidate()
	{
		super.invalidate();
		this.updateContainingBlockInfo();
		ItemBinoculars.remove(this);
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
    public void clear() {
        this.chestContents.clear();
    }

    @Override
	public String getName()
	{
		return "Laptop";
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

	public void refreshTasks() {
		PacketDispatcher.packetsys.sendToAll(new LaptopRefreshPacket(getPos(), b2spirit, b2carpet));
	}

	public boolean isReady()
	{
		return hasChips()
				&& !getStackInSlot(4).isEmpty()
				&& !getStackInSlot(5).isEmpty()
				&& !getStackInSlot(9).isEmpty()
				&& !getStackInSlot(10).isEmpty();
	}
}
