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
package io.github.kadir1243.rivalrebels.common.container;

import io.github.kadir1243.rivalrebels.common.block.trap.BlockTimedBomb;
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsGuiHandler;
import io.github.kadir1243.rivalrebels.common.item.*;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ContainerTachyonBomb extends AbstractContainerMenu implements BombContainer {
	protected Container bomb;
    private final ContainerData containerData;

    public ContainerTachyonBomb(int syncId, Inventory inventoryPlayer) {
        this(syncId, inventoryPlayer, new SimpleContainer(36), new SimpleContainerData(4));
    }

    public ContainerTachyonBomb(int syncId, Inventory inventoryPlayer, Container bomb, ContainerData containerData) {
        super(RivalRebelsGuiHandler.TACHYON_SCREEN_HANDLER_TYPE.get(), syncId);
        this.bomb = bomb;
        this.containerData = containerData;
        addSlot(new SlotRR(bomb, 0, 18, 48, 1, RRItems.fuse));
		addSlot(new SlotRR(bomb, 1, 40, 59, 1, RRItems.antenna.asItem()));
		addSlot(new SlotRR(bomb, 2, 40, 37, 1, RRItems.antenna.asItem()));
		for (int i = 0; i <= 3; i++)
		{
			addSlot(new SlotRR(bomb, i + 3, 62 + i * 18, 19, 1, RRItems.NUCLEAR_ROD).setAcceptsTrollface(true));
			addSlot(new SlotRR(bomb, i + 7, 62 + i * 18, 37, 1, RRItems.NUCLEAR_ROD).setAcceptsTrollface(true));
			addSlot(new SlotRR(bomb, i + 11, 62 + i * 18, 59, 1, RRItems.hydrod).setAcceptsTrollface(true));
			addSlot(new SlotRR(bomb, i + 15, 62 + i * 18, 77, 1, RRItems.hydrod).setAcceptsTrollface(true));
		}
		addSlot(new SlotRR(bomb, 19, 138, 48, 1, BlockTimedBomb.class));
		addSlot(new SlotRR(bomb, 20, 98, 99, 1, ItemChip.class));
		bindPlayerInventory(inventoryPlayer);
        addDataSlots(containerData);
	}

	@Override
	public boolean stillValid(Player player)
	{
		return bomb.stillValid(player);
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
		ItemStack itemStack = ItemStack.EMPTY;
		Slot var4 = this.slots.get(slot);

		if (var4 != null && var4.hasItem())
		{
			ItemStack var5 = var4.getItem();
			itemStack = var5.copy();

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

		return itemStack;
	}

    public int getCountdown() {
        return this.containerData.get(0);
    }

    public boolean isUnbalanced() {
        return this.containerData.get(1) == 1;
    }

    public boolean isArmed() {
        return this.containerData.get(2) == 1;
    }

    public float getMegaton() {
        return this.containerData.get(3) / 100F;
    }

}
