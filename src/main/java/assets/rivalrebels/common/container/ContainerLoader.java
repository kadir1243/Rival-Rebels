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
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ContainerLoader extends AbstractContainerMenu
{
	private final Container loader;
	private final Container playerInventory;

    public ContainerLoader(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(64));
    }

	public ContainerLoader(int syncId, Inventory playerInventory, Container loader)
	{
        super(RivalRebelsGuiHandler.LOADER_SCREEN_HANDLER_TYPE, syncId);
        this.loader = loader;
		this.playerInventory = playerInventory;
		addSlots();
	}

	@Override
	public boolean stillValid(Player player)
	{
		return this.loader.stillValid(player);
	}

    public int size() {
        return this.loader.getContainerSize();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
		ItemStack var3 = ItemStack.EMPTY;
		Slot var4 = this.slots.get(slot);

		if (var4 != null && var4.hasItem())
		{
			ItemStack var5 = var4.getItem();
			var3 = var5.copy();

			if (slot < 60)
			{
				if (!this.moveItemStackTo(var5, 60, this.slots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.moveItemStackTo(var5, 0, 60, false))
			{
				return ItemStack.EMPTY;
			}

			if (var5.isEmpty())
			{
				var4.setByPlayer(ItemStack.EMPTY);
			}
			else
			{
				var4.setChanged();
			}
		}

		return var3;
	}

	public void clearSlots()
	{
		this.slots.clear();
	}

	public void addSlots() {
		for (int var4 = 0; var4 < 6; ++var4)
		{
			for (int var5 = 0; var5 < 2; ++var5)
			{
				this.addSlot(new Slot(loader, var5 + var4 * 2, 10 + var5 * 18, 73 + var4 * 18));
			}
		}

		for (int var4 = 0; var4 < 6; ++var4)
		{
			for (int var5 = 0; var5 < 2; ++var5)
			{
				this.addSlot(new Slot(loader, 12 + var5 + var4 * 2, 212 + var5 * 18, 73 + var4 * 18));
			}
		}

		for (int var4 = 0; var4 < 4; ++var4)
		{
			for (int var5 = 0; var5 < 9; ++var5)
			{
				this.addSlot(new Slot(loader, 24 + var5 + var4 * 9, 48 + var5 * 18, 48 + var4 * 18));
			}
		}

		for (int var4 = 0; var4 < 3; ++var4)
		{
			for (int var5 = 0; var5 < 9; ++var5)
			{
				this.addSlot(new Slot(playerInventory, var5 + var4 * 9 + 9, 48 + var5 * 18, 127 + var4 * 18));
			}
		}

		for (int hotbar = 0; hotbar < 9; ++hotbar) {
			this.addSlot(new Slot(playerInventory, hotbar, 48 + hotbar * 18, 183));
		}
	}
}
