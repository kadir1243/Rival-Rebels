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
import assets.rivalrebels.common.tileentity.TileEntityNuclearBomb;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ContainerNuclearBomb extends AbstractContainerMenu
{
	protected Container nuclearBomb;
    private final TileEntityNuclearBomb.BombData bombData;

    public ContainerNuclearBomb(int syncId, Inventory playerInventory, TileEntityNuclearBomb.BombData bombData) {
        this(syncId, playerInventory, new SimpleContainer(36), bombData);
    }

    public ContainerNuclearBomb(int syncId, Inventory inventoryPlayer, Container nuclearBomb, TileEntityNuclearBomb.BombData bombData) {
        super(RivalRebelsGuiHandler.NUCLEAR_BOMB_SCREEN_HANDLER_TYPE, syncId);
		this.nuclearBomb = nuclearBomb;
        this.bombData = bombData;
        addSlot(new SlotRR(nuclearBomb, 0, 16, 34, 1, ItemFuse.class));
		for (int i = 0; i <= 4; i++) {
			addSlot(new SlotRR(nuclearBomb, i + 1, 38 + i * 18, 25, 1, ItemRodNuclear.class).setAcceptsTrollface(true));
			addSlot(new SlotRR(nuclearBomb, i + 6, 38 + i * 18, 43, 1, ItemRodNuclear.class).setAcceptsTrollface(true));
		}
		addSlot(new SlotRR(nuclearBomb, 11, 133, 34, 1, BlockTimedBomb.class));
		addSlot(new SlotRR(nuclearBomb, 12, 152, 34, 1, ItemChip.class));
		bindPlayerInventory(inventoryPlayer);
	}

	@Override
	public boolean stillValid(Player player)
	{
		return nuclearBomb.stillValid(player);
	}

	protected void bindPlayerInventory(Inventory inventoryPlayer)
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
    public ItemStack quickMoveStack(Player player, int slot) {
		return ItemStack.EMPTY;
	}

    public int getCountDown() {
        return bombData.countdown();
    }

    public int getAmountOfCharges() {
        return bombData.amountOfCharges();
    }

    public boolean hasTrollFace() {
        return bombData.hasTrollFace();
    }

    public boolean isArmed() {
        return bombData.isArmed();
    }
}
