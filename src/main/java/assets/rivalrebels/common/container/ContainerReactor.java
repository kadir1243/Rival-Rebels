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
import assets.rivalrebels.common.item.ItemCore;
import assets.rivalrebels.common.item.ItemRod;
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
    private final ContainerData propertyDelegate;

    public ContainerReactor(int syncId, Inventory playerInv) {
        this(syncId, playerInv, new SimpleContainer(2), new SimpleContainerData(7));
    }

    public ContainerReactor(int syncId, Inventory inv, Container reactor, ContainerData propertyDelegate)
	{
        super(RivalRebelsGuiHandler.REACTOR_SCREEN_HANDLER_TYPE, syncId);
        this.reactor = reactor;
		fuel = new SlotRR(reactor, 0, 58, 139, 1, ItemRod.class);
		core = new SlotRR(reactor, 1, 103, 139, 1, ItemCore.class);
        this.propertyDelegate = propertyDelegate;
        addSlot(fuel);
		addSlot(core);
		bindPlayerInventory(inv);
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
    public ItemStack quickMoveStack(Player player, int slot) {
		return ItemStack.EMPTY;
	}

    public boolean isOn() {
        return propertyDelegate.get(0) == 1;
    }

    public float getPower() {
        return Float.intBitsToFloat(propertyDelegate.get(1));
    }

    public int getConsumed() {
        return propertyDelegate.get(2);
    }

    public int getLastTickConsumed() {
        return propertyDelegate.get(2);
    }

    public boolean isMelt() {
        return propertyDelegate.get(3) == 1;
    }

    public BlockPos getPos() {
        return new BlockPos(propertyDelegate.get(4), propertyDelegate.get(5), propertyDelegate.get(6));
    }

    public void toggleOn() {
        propertyDelegate.set(4, 0);
    }

    public void ejectCore() {
        propertyDelegate.set(5, 0);
    }
}
