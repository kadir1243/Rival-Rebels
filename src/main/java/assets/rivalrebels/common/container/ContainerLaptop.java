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

import assets.rivalrebels.common.block.crate.BlockNukeCrate;
import assets.rivalrebels.common.block.trap.BlockRemoteCharge;
import assets.rivalrebels.common.core.RivalRebelsGuiHandler;
import assets.rivalrebels.common.item.ItemAntenna;
import assets.rivalrebels.common.item.ItemChip;
import assets.rivalrebels.common.item.ItemRodHydrogen;
import assets.rivalrebels.common.item.ItemRodNuclear;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ContainerLaptop extends ScreenHandler {
	protected Inventory laptop;
    private final PropertyDelegate propertyDelegate;

    public ContainerLaptop(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(14), new ArrayPropertyDelegate(4));
    }

    public ContainerLaptop(int syncId, PlayerInventory inventoryPlayer, Inventory laptop, PropertyDelegate propertyDelegate) {
        super(RivalRebelsGuiHandler.LAPTOP_SCREEN_HANDLER_TYPE, syncId);
        this.laptop = laptop;
        this.propertyDelegate = propertyDelegate;
        addSlot(new SlotRR(laptop, 0, 80, 23, 1, ItemChip.class));
		addSlot(new SlotRR(laptop, 1, 50, 40, 1, ItemChip.class));
		addSlot(new SlotRR(laptop, 2, 111, 40, 1, ItemChip.class));
		addSlot(new SlotRR(laptop, 3, 80, 48, 1, ItemChip.class));
		addSlot(new SlotRR(laptop, 4, 26, 76, 1, BlockNukeCrate.class));
		addSlot(new SlotRR(laptop, 5, 44, 76, 1, BlockNukeCrate.class));
		addSlot(new SlotRR(laptop, 6, 62, 76, 1, ItemRodNuclear.class).setAcceptsTimedBomb(true));
		addSlot(new SlotRR(laptop, 7, 80, 76, 1, ItemRodNuclear.class).setAcceptsTimedBomb(true));
		addSlot(new SlotRR(laptop, 8, 98, 76, 1, ItemRodNuclear.class).setAcceptsTimedBomb(true));
		addSlot(new SlotRR(laptop, 9, 26, 94, 1, ItemAntenna.class));
		addSlot(new SlotRR(laptop, 10, 44, 94, 1, BlockRemoteCharge.class));
		addSlot(new SlotRR(laptop, 11, 62, 94, 1, ItemRodHydrogen.class).setAcceptsTimedBomb(true));
		addSlot(new SlotRR(laptop, 12, 80, 94, 1, ItemRodHydrogen.class).setAcceptsTimedBomb(true));
		addSlot(new SlotRR(laptop, 13, 98, 94, 1, ItemRodHydrogen.class).setAcceptsTimedBomb(true));
		bindPlayerInventory(inventoryPlayer);
	}

	@Override
	public boolean canUse(PlayerEntity player)
	{
		return laptop.canPlayerUse(player);
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
		return ItemStack.EMPTY;
	}

    public boolean isReady() {
        return propertyDelegate.get(0) == 1;
    }

    public boolean hasChips() {
        return propertyDelegate.get(1) == 1;
    }

    public int getB2spirit() {
        return propertyDelegate.get(2);
    }

    public int getB2carpet() {
        return propertyDelegate.get(3);
    }

    public void onGoButtonPressed() {
        propertyDelegate.set(0, 0);
    }
}
