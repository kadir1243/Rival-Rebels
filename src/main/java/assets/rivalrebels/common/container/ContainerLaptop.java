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
import assets.rivalrebels.common.item.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ContainerLaptop extends AbstractContainerMenu {
	protected Container laptop;
    private final ContainerData containerData;

    public ContainerLaptop(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(14), new SimpleContainerData(7));
    }

    public ContainerLaptop(int syncId, Inventory inventoryPlayer, Container laptop, ContainerData containerData) {
        super(RivalRebelsGuiHandler.LAPTOP_SCREEN_HANDLER_TYPE, syncId);
        this.laptop = laptop;
        this.containerData = containerData;
        addSlot(new SlotRR(laptop, 0, 80, 23, 1, ItemChip.class));
		addSlot(new SlotRR(laptop, 1, 50, 40, 1, ItemChip.class));
		addSlot(new SlotRR(laptop, 2, 111, 40, 1, ItemChip.class));
		addSlot(new SlotRR(laptop, 3, 80, 48, 1, ItemChip.class));
		addSlot(new SlotRR(laptop, 4, 26, 76, 1, BlockNukeCrate.class));
		addSlot(new SlotRR(laptop, 5, 44, 76, 1, BlockNukeCrate.class));
		addSlot(new SlotRR(laptop, 6, 62, 76, 1, ItemRodNuclear.class).setAcceptsTimedBomb(true));
		addSlot(new SlotRR(laptop, 7, 80, 76, 1, ItemRodNuclear.class).setAcceptsTimedBomb(true));
		addSlot(new SlotRR(laptop, 8, 98, 76, 1, ItemRodNuclear.class).setAcceptsTimedBomb(true));
		addSlot(new SlotRR(laptop, 9, 26, 94, 1, RRItems.antenna));
		addSlot(new SlotRR(laptop, 10, 44, 94, 1, BlockRemoteCharge.class));
		addSlot(new SlotRR(laptop, 11, 62, 94, 1, ItemRodHydrogen.class).setAcceptsTimedBomb(true));
		addSlot(new SlotRR(laptop, 12, 80, 94, 1, ItemRodHydrogen.class).setAcceptsTimedBomb(true));
		addSlot(new SlotRR(laptop, 13, 98, 94, 1, ItemRodHydrogen.class).setAcceptsTimedBomb(true));
		bindPlayerInventory(inventoryPlayer);
        this.addDataSlots(containerData);
	}

	@Override
	public boolean stillValid(Player player)
	{
		return laptop.stillValid(player);
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
		return ItemStack.EMPTY;
	}

    public boolean isReady() {
        return containerData.get(0) == 1;
    }

    public boolean hasChips() {
        return containerData.get(1) == 1;
    }

    public int getB2spirit() {
        return containerData.get(2);
    }

    public int getB2carpet() {
        return containerData.get(3);
    }

    public BlockPos getLaptopPos() {
        return new BlockPos(containerData.get(4), containerData.get(5), containerData.get(6));
    }
}
