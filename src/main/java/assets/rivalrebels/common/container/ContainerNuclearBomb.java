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
import assets.rivalrebels.common.item.ItemChip;
import assets.rivalrebels.common.item.ItemFuse;
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

public class ContainerNuclearBomb extends ScreenHandler
{
	protected Inventory nuclearBomb;
    private final PropertyDelegate propertyDelegate;

    public ContainerNuclearBomb(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(36), new ArrayPropertyDelegate(4));
    }

    public ContainerNuclearBomb(int syncId, PlayerInventory inventoryPlayer, Inventory nuclearBomb, PropertyDelegate propertyDelegate) {
        super(RivalRebelsGuiHandler.NUCLEAR_BOMB_SCREEN_HANDLER_TYPE, syncId);
		this.nuclearBomb = nuclearBomb;
        this.propertyDelegate = propertyDelegate;
        addSlot(new SlotRR(nuclearBomb, 0, 16, 34, 1, ItemFuse.class));
		for (int i = 0; i <= 4; i++)
		{
			addSlot(new SlotRR(nuclearBomb, i + 1, 38 + i * 18, 25, 1, ItemRodNuclear.class).setAcceptsTrollface(true));
			addSlot(new SlotRR(nuclearBomb, i + 6, 38 + i * 18, 43, 1, ItemRodNuclear.class).setAcceptsTrollface(true));
		}
		addSlot(new SlotRR(nuclearBomb, 11, 133, 34, 1, BlockTimedBomb.class));
		addSlot(new SlotRR(nuclearBomb, 12, 152, 34, 1, ItemChip.class));
		bindPlayerInventory(inventoryPlayer);
	}

	@Override
	public boolean canUse(PlayerEntity player)
	{
		return nuclearBomb.canPlayerUse(player);
	}

	protected void bindPlayerInventory(PlayerInventory inventoryPlayer)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlot(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlot(new Slot(inventoryPlayer, i, 8 + i * 18, 139));
		}
	}

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
		return ItemStack.EMPTY;
	}

    public int getCountDown() {
        return propertyDelegate.get(0);
    }

    public int getAmountOfCharges() {
        return propertyDelegate.get(1);
    }

    public boolean hasTrollFace() {
        return propertyDelegate.get(2) == 1;
    }

    public boolean isArmed() {
        return propertyDelegate.get(3) == 1;
    }
}
