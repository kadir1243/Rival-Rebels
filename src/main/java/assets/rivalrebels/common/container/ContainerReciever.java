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

import assets.rivalrebels.common.block.crate.BlockWeapons;
import assets.rivalrebels.common.core.RivalRebelsGuiHandler;
import assets.rivalrebels.common.item.ItemChip;
import assets.rivalrebels.common.item.ItemFuel;
import assets.rivalrebels.common.item.RRItems;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ContainerReciever extends AbstractContainerMenu {
	protected Container reciever;
    private final ContainerData propertyDelegate;

    public ContainerReciever(int syncId, Inventory playerInv) {
        this(syncId, playerInv, new SimpleContainer(9), new SimpleContainerData(7));
    }

	public ContainerReciever(int syncId, Inventory inventoryPlayer, Container reciever, ContainerData propertyDelegate) {
        super(RivalRebelsGuiHandler.RECIEVER_SCREEN_HANDLER_TYPE, syncId);
        this.reciever = reciever;
        this.propertyDelegate = propertyDelegate;
        addSlot(new SlotRR(reciever, 0, 8, 76, 64, ItemFuel.class));
		addSlot(new SlotRR(reciever, 1, 26, 76, 64, ItemFuel.class));
		addSlot(new SlotRR(reciever, 2, 44, 76, 64, ItemFuel.class));
		addSlot(new SlotRR(reciever, 3, 8, 94, 16, RRItems.battery));
		addSlot(new SlotRR(reciever, 4, 26, 94, 16, RRItems.battery));
		addSlot(new SlotRR(reciever, 5, 44, 94, 16, RRItems.battery));
		addSlot(new SlotRR(reciever, 6, 116, 94, 1, ItemChip.class));
		addSlot(new SlotRR(reciever, 7, 134, 94, 1, BlockWeapons.class));
		addSlot(new SlotRR(reciever, 8, 152, 94, 1, BlockWeapons.class));
		bindPlayerInventory(inventoryPlayer);
	}

	@Override
	public boolean stillValid(Player player)
	{
		return reciever.stillValid(player);
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

    public int getYawLimit() {
        return propertyDelegate.get(0);
    }

    public void setYawLimit(int yawLimit) {
        propertyDelegate.set(0, yawLimit);
    }

    public boolean getKTeam() {
        return propertyDelegate.get(1) == 1;
    }

    public boolean getKPlayers() {
        return propertyDelegate.get(2) == 1;
    }

    public boolean getKMobs() {
        return propertyDelegate.get(3) == 1;
    }

    public void setKTeam(boolean kTeam) {
        propertyDelegate.set(1, kTeam ? 1 : 0);
    }

    public void setKPlayers(boolean kPlayers) {
        propertyDelegate.set(2, kPlayers ? 1 : 0);
    }

    public void setKMobs(boolean kMobs) {
        propertyDelegate.set(3, kMobs ? 1 : 0);
    }

    public boolean hasWeapon() {
        return propertyDelegate.get(4) == 1;
    }

    public boolean hasWepReqs() {
        return propertyDelegate.get(5) == 1;
    }

    public void setWep(boolean wep) {
        propertyDelegate.set(5, wep ? 1 : 0);
    }

    public float getPInR() {
        return Float.intBitsToFloat(propertyDelegate.get(6));
    }
}
