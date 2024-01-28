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
package assets.rivalrebels.common.container;

import assets.rivalrebels.common.core.RivalRebelsGuiHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLoader extends ScreenHandler
{
	private final Inventory loader;
	private final Inventory playerInventory;

    public ContainerLoader(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(64));
    }

	public ContainerLoader(int syncId, PlayerInventory playerInventory, Inventory loader)
	{
        super(RivalRebelsGuiHandler.LOADER_SCREEN_HANDLER_TYPE, syncId);
        this.loader = loader;
		this.playerInventory = playerInventory;
		addSlots();
	}

	@Override
	public boolean canUse(PlayerEntity par1EntityPlayer)
	{
		return this.loader.canPlayerUse(par1EntityPlayer);
	}

    public int size() {
        return this.loader.size();
    }

    @Override
	public ItemStack transferSlot(PlayerEntity par1EntityPlayer, int par2)
	{
		ItemStack var3 = ItemStack.EMPTY;
		Slot var4 = this.slots.get(par2);

		if (var4 != null && var4.hasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if (par2 < 60)
			{
				if (!this.insertItem(var5, 60, this.slots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.insertItem(var5, 0, 60, false))
			{
				return ItemStack.EMPTY;
			}

			if (var5.isEmpty())
			{
				var4.setStack(ItemStack.EMPTY);
			}
			else
			{
				var4.markDirty();
			}
		}

		return var3;
	}

	public void clearSlots()
	{
		this.slots.clear();
	}

	public void addSlots()
	{
		int var4;
		int var5;

		for (var4 = 0; var4 < 6; ++var4)
		{
			for (var5 = 0; var5 < 2; ++var5)
			{
				this.addSlot(new Slot(loader, var5 + var4 * 2, 10 + var5 * 18, 73 + var4 * 18));
			}
		}

		for (var4 = 0; var4 < 6; ++var4)
		{
			for (var5 = 0; var5 < 2; ++var5)
			{
				this.addSlot(new Slot(loader, 12 + var5 + var4 * 2, 212 + var5 * 18, 73 + var4 * 18));
			}
		}

		for (var4 = 0; var4 < 4; ++var4)
		{
			for (var5 = 0; var5 < 9; ++var5)
			{
				this.addSlot(new Slot(loader, 24 + var5 + var4 * 9, 48 + var5 * 18, 48 + var4 * 18));
			}
		}

		for (var4 = 0; var4 < 3; ++var4)
		{
			for (var5 = 0; var5 < 9; ++var5)
			{
				this.addSlot(new Slot(playerInventory, var5 + var4 * 9 + 9, 48 + var5 * 18, 127 + var4 * 18));
			}
		}

		for (var4 = 0; var4 < 9; ++var4)
		{
			this.addSlot(new Slot(playerInventory, var4, 48 + var4 * 18, 183));
		}
	}
}
