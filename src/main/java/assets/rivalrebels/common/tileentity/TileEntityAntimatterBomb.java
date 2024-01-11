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
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityAntimatterBomb;
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.packet.TextPacket;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityAntimatterBomb extends TileEntity implements IInventory, ITickable
{
	public String			username		= null;
	public RivalRebelsTeam	rrteam			= null;
	private NonNullList<ItemStack> chestContents	= NonNullList.withSize(36, ItemStack.EMPTY);

	public int				countdown		= RivalRebels.nuclearBombCountdown * 20;
	public int				nuclear			= 0;
	public int				hydrogen		= 0;
	public boolean			hasAntennae		= false;
	public boolean			hasExplosive	= false;
	public boolean			hasFuse			= false;
	public boolean			hasChip			= false;
	public boolean			hasTrollface	= false;
	public float			megaton			= 0;

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory()
	{
		return 21;
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
		else
		{
			return ItemStack.EMPTY;
		}
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

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		this.chestContents.set(par1, par2ItemStack);

		if (!par2ItemStack.isEmpty() && par2ItemStack.getCount() > this.getInventoryStackLimit())
		{
			par2ItemStack.setCount(this.getInventoryStackLimit());
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

	/**
	 * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't this more of a set than a get?*
	 */
	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes with Container
	 */
	@Override
	public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return this.world.getTileEntity(this.getPos()) == this && par1EntityPlayer.getDistanceSq(this.getPos().getX() + 0.5D, this.getPos().getY() + 0.5D, this.getPos().getZ() + 0.5D) <= 64.0D;
	}

	/**
	 * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count ticks and creates a new spawn inside its implementation.
	 */
	@Override
	public void update() {
		nuclear = 0;
		hydrogen = 0;
		for (int i = 3; i <= 18; i++)
		{
			ItemStack is = getStackInSlot(i);
			if (!is.isEmpty() && is.isItemEnchanted())
			{
				Item item = is.getItem();
				if (i < 11 && item == RivalRebels.nuclearelement)
				{
					nuclear++;
				}
				else if (i > 10 && item == RivalRebels.redrod)
				{
					hydrogen++;
				}
				if (item == RivalRebels.trollmask)
				{
					hasTrollface = true;
				}
			}
		}
		if (nuclear == hydrogen) megaton = nuclear * 6.25f;

		if (!getStackInSlot(0).isEmpty())
		{
			hasFuse = getStackInSlot(0).getItem() == RivalRebels.fuse;
		}
		else
		{
			hasFuse = false;
		}

		if (!getStackInSlot(20).isEmpty())
		{
			hasChip = getStackInSlot(20).getItem() == RivalRebels.chip;
			if (hasChip)
			{
				rrteam = RivalRebelsTeam.getForID(getStackInSlot(20).getTagCompound().getInteger("team"));
				username = getStackInSlot(20).getTagCompound().getString("username");
			}
		}
		else
		{
			hasChip = false;
		}

		if (!getStackInSlot(1).isEmpty() && !getStackInSlot(2).isEmpty())
		{
			hasAntennae = getStackInSlot(1).getItem() == RivalRebels.antenna && getStackInSlot(2).getItem() == RivalRebels.antenna;
		}
		else
		{
			hasAntennae = false;
		}

		if (!getStackInSlot(19).isEmpty())
		{
			hasExplosive = true;// getStackInSlot(19).func_150998_b(RivalRebels.timedbomb);
		}
		else
		{
			hasExplosive = false;
		}

		boolean sp;
        if (world.isRemote) {
            sp = Minecraft.getMinecraft().isSingleplayer();
        } else {
            MinecraftServer server = world.getMinecraftServer();
            sp = server.getCurrentPlayerCount() == 1;
        }

		if (hasFuse && hasExplosive && nuclear == hydrogen && hasAntennae && hasChip)
		{
			double dist = 1000000;

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
			if (dist > (RivalRebels.tsarBombaStrength + (nuclear * hydrogen) + 29) * (RivalRebels.tsarBombaStrength + (nuclear * hydrogen) + 29))
			{
				if (countdown > 0) countdown--;
			}
			else if (!world.isRemote)
			{
				this.chestContents.set(0, ItemStack.EMPTY);
				PacketDispatcher.packetsys.sendToAll(new TextPacket("RivalRebels.WARNING " + username));
				PacketDispatcher.packetsys.sendToAll(new TextPacket("RivalRebels.Status " + (rrteam == RivalRebelsTeam.OMEGA ? RivalRebels.omegaobj.getTranslationKey() : rrteam == RivalRebelsTeam.SIGMA ? RivalRebels.sigmaobj.getTranslationKey() : "NONE") + ".name RivalRebels.Defuse RivalRebels.tsar.tsar"));
				// ChatMessageComponent.createFromText(I18n.translateToLocal("RivalRebels.spawn.join" + rrteam.name().toLowerCase()) + " " +
				// I18n.translateToLocal("RivalRebels.nukedefuse")));
			}
		}
		else
		{
			countdown = RivalRebels.nuclearBombCountdown * 20;
			if (RivalRebels.nuclearBombCountdown == 0) countdown = 10;
		}

		if (countdown == 200 && !world.isRemote && RivalRebels.nuclearBombCountdown > 10)
		{
			PacketDispatcher.packetsys.sendToAll(new TextPacket("RivalRebels.WARNING RivalRebels.warning1"));
			PacketDispatcher.packetsys.sendToAll(new TextPacket("RivalRebels.WARNING RivalRebels.warning2"));
			PacketDispatcher.packetsys.sendToAll(new TextPacket("RivalRebels.WARNING RivalRebels.warning3"));
		}

		if (countdown % 20 == 0 && countdown <= 200 && RivalRebels.nuclearBombCountdown > 10) RivalRebelsSoundPlayer.playSound(world, 14, 0, getPos(), 100);

		if (countdown == 0 && nuclear != 0 && hydrogen != 0 && !world.isRemote && nuclear == hydrogen)
		{
			world.setBlockToAir(getPos());
			world.setLastLightningBolt(2);
			float pitch = 0;
			float yaw = switch (this.getBlockMetadata()) {
                case 2 -> 180;
                case 3 -> 0;
                case 4 -> 270;
                case 5 -> 90;
                default -> 0;
            };

            EntityAntimatterBomb tsar = new EntityAntimatterBomb(world, getPos().getX()+0.5f, getPos().getY()+1f, getPos().getZ()+0.5f, yaw, pitch, hydrogen, hasTrollface);
			world.spawnEntity(tsar);
		}

		if (countdown == 0 && nuclear == 0 && hydrogen == 0)
		{
			world.setBlockToAir(getPos());
			world.createExplosion(null, getPos().getX(), getPos().getY(), getPos().getZ(), 4, false);
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
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(getPos().add(-5, 0, -5), getPos().add(6, 2, 6));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 16384.0D;
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
    public String getName() {
		return "Antimatter Bomb";
	}

    @Override
    public boolean hasCustomName() {
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
