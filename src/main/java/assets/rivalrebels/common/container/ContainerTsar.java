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

import assets.rivalrebels.common.block.trap.BlockTimedBomb;
import assets.rivalrebels.common.core.RivalRebelsGuiHandler;
import assets.rivalrebels.common.item.*;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ContainerTsar extends AbstractContainerMenu {
	protected Container tsarBomb;
    private final ContainerData propertyDelegate;

    public ContainerTsar(int syncId, Inventory inv) {
        this(syncId, inv, new SimpleContainer(36), new SimpleContainerData(0));
    }

	public ContainerTsar(int syncId, Inventory inventoryPlayer, Container tsarBomb, ContainerData propertyDelegate) {
        super(RivalRebelsGuiHandler.TSAR_SCREEN_HANDLER_TYPE, syncId);
        this.tsarBomb = tsarBomb;
        this.propertyDelegate = propertyDelegate;
        addSlot(new SlotRR(tsarBomb, 0, 18, 48, 1, ItemFuse.class));
		addSlot(new SlotRR(tsarBomb, 1, 40, 59, 1, RRItems.antenna));
		addSlot(new SlotRR(tsarBomb, 2, 40, 37, 1, RRItems.antenna));
		for (int i = 0; i <= 3; i++)
		{
			addSlot(new SlotRR(tsarBomb, i + 3, 62 + i * 18, 19, 1, ItemRodNuclear.class).setAcceptsTrollface(true));
			addSlot(new SlotRR(tsarBomb, i + 7, 62 + i * 18, 37, 1, ItemRodNuclear.class).setAcceptsTrollface(true));
			addSlot(new SlotRR(tsarBomb, i + 11, 62 + i * 18, 59, 1, ItemRodHydrogen.class).setAcceptsTrollface(true));
			addSlot(new SlotRR(tsarBomb, i + 15, 62 + i * 18, 77, 1, ItemRodHydrogen.class).setAcceptsTrollface(true));
		}
		addSlot(new SlotRR(tsarBomb, 19, 138, 48, 1, BlockTimedBomb.class));
		addSlot(new SlotRR(tsarBomb, 20, 98, 99, 1, ItemChip.class));
		bindPlayerInventory(inventoryPlayer);
	}

    @Override
    public boolean stillValid(Player player) {
		return tsarBomb.stillValid(player);
	}

	protected void bindPlayerInventory(Inventory inventoryPlayer)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlot(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 119 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlot(new Slot(inventoryPlayer, i, 8 + i * 18, 175));
		}
	}

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
		ItemStack var3 = ItemStack.EMPTY;
		Slot var4 = this.slots.get(slot);

		if (var4 != null && var4.hasItem())
		{
			ItemStack var5 = var4.getItem();
			var3 = var5.copy();

			if (slot <= 19)
			{
				if (!this.moveItemStackTo(var5, 19, this.slots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.moveItemStackTo(var5, 0, 19, false))
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

    public int getCountdown() {
        return this.propertyDelegate.get(0);
    }

    public boolean isUnbalanced() {
        return this.propertyDelegate.get(1) == 1;
    }

    public boolean isArmed() {
        return this.propertyDelegate.get(2) == 1;
    }

    public float getMegaton() {
        return Float.intBitsToFloat(this.propertyDelegate.get(3));
    }
}
