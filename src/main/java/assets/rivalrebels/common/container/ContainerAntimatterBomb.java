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

public class ContainerAntimatterBomb extends ScreenHandler
{
	protected Inventory antimatter;
    private final PropertyDelegate propertyDelegate;

    public ContainerAntimatterBomb(int syncId, PlayerInventory inv) {
        this(syncId, inv, new SimpleInventory(36), new ArrayPropertyDelegate(4));
    }

    public ContainerAntimatterBomb(int syncId, PlayerInventory inv, Inventory antimatter, PropertyDelegate propertyDelegate) {
        super(RivalRebelsGuiHandler.ANTIMATTER_SCREEN_HANDLER_TYPE, syncId);
		this.antimatter = antimatter;
        this.propertyDelegate = propertyDelegate;
        addSlot(new SlotRR(antimatter, 0, 18, 48, 1, ItemFuse.class));
		addSlot(new SlotRR(antimatter, 1, 40, 59, 1, ItemAntenna.class));
		addSlot(new SlotRR(antimatter, 2, 40, 37, 1, ItemAntenna.class));
		for (int i = 0; i <= 3; i++)
		{
			addSlot(new SlotRR(antimatter, i + 3, 62 + i * 18, 19, 1, ItemRodNuclear.class).setAcceptsTrollface(true));
			addSlot(new SlotRR(antimatter, i + 7, 62 + i * 18, 37, 1, ItemRodNuclear.class).setAcceptsTrollface(true));
			addSlot(new SlotRR(antimatter, i + 11, 62 + i * 18, 59, 1, ItemRodRedstone.class).setAcceptsTrollface(true));
			addSlot(new SlotRR(antimatter, i + 15, 62 + i * 18, 77, 1, ItemRodRedstone.class).setAcceptsTrollface(true));
		}
		addSlot(new SlotRR(antimatter, 19, 138, 48, 1, BlockTimedBomb.class));
		addSlot(new SlotRR(antimatter, 20, 98, 99, 1, ItemChip.class));
		bindPlayerInventory(inv);
	}

	@Override
	public boolean canUse(PlayerEntity player)
	{
		return antimatter.canPlayerUse(player);
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
	public ItemStack transferSlot(PlayerEntity player, int index)
	{
		ItemStack var3 = ItemStack.EMPTY;
		Slot var4 = this.getSlot(index);

		if (var4 != null && var4.hasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if (index <= 19)
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
