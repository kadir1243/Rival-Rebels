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

import io.github.kadir1243.rivalrebels.common.core.RivalRebelsGuiHandler;
import io.github.kadir1243.rivalrebels.common.item.ItemRod;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
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

public class ContainerReactor extends AbstractContainerMenu
{
	protected Container reactor;
	public SlotRR fuel;
	public SlotRR core;
    private final ContainerData containerData;

    public ContainerReactor(int syncId, Inventory playerInv) {
        this(syncId, playerInv, new SimpleContainer(2), new SimpleContainerData(7));
    }

    public ContainerReactor(int syncId, Inventory inv, Container reactor, ContainerData containerData) {
        super(RivalRebelsGuiHandler.REACTOR_SCREEN_HANDLER_TYPE.get(), syncId);
        this.reactor = reactor;
		fuel = new SlotRR(reactor, 0, 58, 139, 1, ItemRod.class);
		core = new SlotRR(reactor, 1, 103, 139, 1, RRComponents.CORE_TIME_MULTIPLIER.get());
        this.containerData = containerData;
        addSlot(fuel);
		addSlot(core);
		bindPlayerInventory(inv);
        addDataSlots(containerData);
	}

    @Override
	public boolean stillValid(Player player)
	{
		return reactor.stillValid(player);
	}

	protected void bindPlayerInventory(Container inventoryPlayer) {
		for (int i = 0; i < 9; i++) {
			addSlot(new Slot(inventoryPlayer, i, 8 + i * 18, 172));
		}
	}

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
	}

    public boolean isOn() {
        return containerData.get(0) == 1;
    }

    public float getPower() {
        return containerData.get(1) * 100F;
    }

    public int getConsumed() {
        return containerData.get(2);
    }

    public int getLastTickConsumed() {
        return containerData.get(2);
    }

    public boolean isMelt() {
        return containerData.get(3) == 1;
    }

    public BlockPos getPos() {
        return new BlockPos(containerData.get(4), containerData.get(5), containerData.get(6));
    }

}
