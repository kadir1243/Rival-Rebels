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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ContainerTachyonBomb extends ScreenHandler {
	protected Inventory bomb;
    private final PropertyDelegate propertyDelegate;

    public ContainerTachyonBomb(int syncId, PlayerInventory inventoryPlayer) {
        this(syncId, inventoryPlayer, new SimpleInventory(36), new ArrayPropertyDelegate(4));
    }

    public ContainerTachyonBomb(int syncId, PlayerInventory inventoryPlayer, Inventory bomb, PropertyDelegate propertyDelegate) {
        super(RivalRebelsGuiHandler.TACHYON_SCREEN_HANDLER_TYPE, syncId);
        this.bomb = bomb;
        this.propertyDelegate = propertyDelegate;
        addSlot(new SlotRR(bomb, 0, 18, 48, 1, ItemFuse.class));
		addSlot(new SlotRR(bomb, 1, 40, 59, 1, RRItems.antenna));
		addSlot(new SlotRR(bomb, 2, 40, 37, 1, RRItems.antenna));
		for (int i = 0; i <= 3; i++)
		{
			addSlot(new SlotRR(bomb, i + 3, 62 + i * 18, 19, 1, ItemRodNuclear.class).setAcceptsTrollface(true));
			addSlot(new SlotRR(bomb, i + 7, 62 + i * 18, 37, 1, ItemRodNuclear.class).setAcceptsTrollface(true));
			addSlot(new SlotRR(bomb, i + 11, 62 + i * 18, 59, 1, ItemRodHydrogen.class).setAcceptsTrollface(true));
			addSlot(new SlotRR(bomb, i + 15, 62 + i * 18, 77, 1, ItemRodHydrogen.class).setAcceptsTrollface(true));
		}
		addSlot(new SlotRR(bomb, 19, 138, 48, 1, BlockTimedBomb.class));
		addSlot(new SlotRR(bomb, 20, 98, 99, 1, ItemChip.class));
		bindPlayerInventory(inventoryPlayer);
	}

	@Override
	public boolean canUse(PlayerEntity player)
	{
		return bomb.canPlayerUse(player);
	}

	protected void bindPlayerInventory(PlayerInventory inventoryPlayer)
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
    public ItemStack quickMove(PlayerEntity player, int slot) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot var4 = this.slots.get(slot);

		if (var4 != null && var4.hasStack())
		{
			ItemStack var5 = var4.getStack();
			itemStack = var5.copy();

			if (slot <= 19)
			{
				if (!this.insertItem(var5, 19, this.slots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.insertItem(var5, 0, 19, false))
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

		return itemStack;
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
