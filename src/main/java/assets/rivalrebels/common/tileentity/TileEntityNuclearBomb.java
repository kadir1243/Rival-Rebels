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
import assets.rivalrebels.common.entity.EntityNuke;
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.packet.TextPacket;
import assets.rivalrebels.common.round.RivalRebelsTeam;
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

public class TileEntityNuclearBomb extends TileEntity implements IInventory, ITickable
{
	public String			username		= null;
	public RivalRebelsTeam	rrteam			= null;
	private final NonNullList<ItemStack> chestContents	= NonNullList.withSize(36, ItemStack.EMPTY);

    public int				Countdown		= RivalRebels.nuclearBombCountdown * 20;

	public int				AmountOfCharges	= 0;
	public boolean			hasTrollface	= false;
	public boolean			hasExplosive	= false;
	public boolean			hasFuse			= false;
	public boolean			hasChip			= false;

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory()
	{
		return 13;
	}

	/**
	 * Returns the stack in slot i
	 */
	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return this.chestContents.get(par1);
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a new stack.
	 */
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
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);

        ItemStackHelper.loadAllItems(par1NBTTagCompound, this.chestContents);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
        ItemStackHelper.saveAllItems(par1NBTTagCompound, this.chestContents);
        return par1NBTTagCompound;
    }

    @Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

    @Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.getPos().getX() + 0.5D, this.getPos().getY() + 0.5D, this.getPos().getZ() + 0.5D) <= 64.0D;
	}

	/**
	 * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count ticks and creates a new spawn inside its implementation.
	 */
	@Override
	public void update()
	{
		AmountOfCharges = 0;
		hasTrollface = false;
		for (int i = 1; i <= 10; i++)
		{
			if (!getStackInSlot(i).isEmpty() && getStackInSlot(i).getItem() == RivalRebels.nuclearelement)
			{
				AmountOfCharges++;
			}
			if (!getStackInSlot(i).isEmpty() && getStackInSlot(i).getItem() == RivalRebels.trollmask)
			{
				hasTrollface = true;
			}
		}

		if (!getStackInSlot(0).isEmpty()) {
			hasFuse = getStackInSlot(0).getItem() == RivalRebels.fuse;
		} else {
			hasFuse = false;
		}

		if (!getStackInSlot(12).isEmpty())
		{
			hasChip = getStackInSlot(12).getItem() == RivalRebels.chip;
			if (hasChip)
			{
				rrteam = RivalRebelsTeam.getForID(getStackInSlot(12).getTagCompound().getInteger("team"));
				username = getStackInSlot(12).getTagCompound().getString("username");
			}
		}
		else
		{
			hasChip = false;
		}

		if (!getStackInSlot(11).isEmpty()) {
			hasExplosive = true;// getStackInSlot(11).func_150998_b(RivalRebels.timedbomb);
		} else {
			hasExplosive = false;
		}

        boolean sp = world.isRemote || (!world.isRemote && (world.getMinecraftServer().isSinglePlayer() || world.getMinecraftServer().getCurrentPlayerCount() == 1));

		if (hasFuse && hasExplosive && hasChip)
		{
			double dist = 10000000;
			if (!sp || RivalRebels.stopSelfnukeinSP)
			{
				if (rrteam == RivalRebelsTeam.OMEGA)
				{
					dist = getDistanceSq(RivalRebels.round.omegaObjPos.getX(), getPos().getY(), RivalRebels.round.omegaObjPos.getZ());
				}
				if (rrteam == RivalRebelsTeam.SIGMA)
				{
					dist = getDistanceSq(RivalRebels.round.sigmaObjPos.getX(), getPos().getY(), RivalRebels.round.sigmaObjPos.getZ());
				}
			}
			if (dist > (RivalRebels.nuclearBombStrength + (AmountOfCharges * AmountOfCharges) + 29) * (RivalRebels.nuclearBombStrength + (AmountOfCharges * AmountOfCharges) + 29))
			{
				if (Countdown > 0) Countdown--;
			}
			else if (!world.isRemote)
			{
				this.chestContents.set(0, ItemStack.EMPTY);
				PacketDispatcher.packetsys.sendToAll(new TextPacket("RivalRebels.WARNING " + username));
				PacketDispatcher.packetsys.sendToAll(new TextPacket("RivalRebels.Status " + (rrteam == RivalRebelsTeam.OMEGA ? RivalRebels.omegaobj.getTranslationKey() : rrteam == RivalRebelsTeam.SIGMA ? RivalRebels.sigmaobj.getTranslationKey() : "NONE") + ".name RivalRebels.Defuse RivalRebels.nuke.name"));
				//ChatMessageComponent.createFromText(I18n.translateToLocal("RivalRebels.spawn.join" + rrteam.name().toLowerCase()) + " " +
				//I18n.translateToLocal("RivalRebels.nukedefuse")));
			}
		}
		else
		{
			Countdown = RivalRebels.nuclearBombCountdown * 20;
		}

		if (Countdown == 200 && !world.isRemote && RivalRebels.nuclearBombCountdown > 10)
		{
			PacketDispatcher.packetsys.sendToAll(new TextPacket("RivalRebels.WARNING RivalRebels.warning1"));
			PacketDispatcher.packetsys.sendToAll(new TextPacket("RivalRebels.WARNING RivalRebels.warning2"));
			PacketDispatcher.packetsys.sendToAll(new TextPacket("RivalRebels.WARNING RivalRebels.warning3"));
		}

		if (Countdown == 0 && AmountOfCharges != 0 && !world.isRemote)
		{
			world.setLastLightningBolt(2);
			float pitch = 0;
			float yaw = 0;
			switch(this.getBlockMetadata())
			{
			default:
				pitch = -90;
				break;
			case 1:
				pitch = 90;
				break;
			case 2:
				yaw = 180;
				break;
			case 3:
				yaw = 0;
				break;
			case 4:
				yaw = 270;
				break;
			case 5:
				yaw = 90;
				break;
			}

			world.spawnEntity(new EntityNuke(world, getPos().getX() + 0.5f, getPos().getY() + 0.5f, getPos().getZ() + 0.5f, yaw, pitch, AmountOfCharges, hasTrollface));
			world.setBlockToAir(getPos());
		}

		if (Countdown == 0 && AmountOfCharges == 0)
		{
			world.createExplosion(null, getPos().getX(), getPos().getY(), getPos().getZ(), 4, true);
			world.setBlockToAir(getPos());
		}
    }

	/**
	 * invalidates a tile entity
	 */
	@Override
	public void invalidate()
	{
		this.updateContainingBlockInfo();
		super.invalidate();
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return new AxisAlignedBB(getPos().add(-1, -1, -1), getPos().add(2, 2, 2));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
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
		return "Nuclear Bomb";
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
