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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.item.ItemStack;
import assets.rivalrebels.common.item.ItemCore;
import assets.rivalrebels.common.item.ItemRod;
import net.minecraft.util.math.BlockPos;

public class ContainerReactor extends ScreenHandler
{
	protected Inventory reactor;
	public SlotRR fuel;
	public SlotRR core;
    private final PropertyDelegate propertyDelegate;

    public ContainerReactor(int syncId, PlayerInventory playerInv) {
        this(syncId, playerInv, new SimpleInventory(2), new ArrayPropertyDelegate(7));
    }

    public ContainerReactor(int syncId, PlayerInventory inv, Inventory reactor, PropertyDelegate propertyDelegate)
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
	public boolean canUse(PlayerEntity player)
	{
		return reactor.canPlayerUse(player);
	}

	protected void bindPlayerInventory(Inventory inventoryPlayer) {
		for (int i = 0; i < 9; i++) {
			addSlot(new Slot(inventoryPlayer, i, 8 + i * 18, 172));
		}
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int index)
	{
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
