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

import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.item.RRItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.function.Predicate;

public class SlotRR extends Slot
{
	private final int	maxStack;
    private final Predicate<ItemStack> stackLock;
    boolean acceptsTrollFace = false;
	boolean acceptsTimedBomb = false;
	public boolean locked = false;

	public SlotRR(Inventory inv, int id, int x, int y, int mstack, Class<?> only) {
		this(inv, id, x, y, mstack, stack -> only.isAssignableFrom(stack.getItem().getClass()) || (stack.getItem() instanceof BlockItem itemBlock && only.isAssignableFrom(itemBlock.getBlock().getClass())));
	}

    public SlotRR(Inventory inv, int id, int x, int y, int mstack, Item only) {
        this(inv, id, x, y, mstack, stack -> stack.getItem() == only);
    }

    public SlotRR(Inventory inv, int id, int x, int y, int mstack, Predicate<ItemStack> stackLock) {
        super(inv, id, x, y);
        this.maxStack = mstack;
        this.stackLock = stackLock;
    }

    @Override
    public boolean canTakeItems(PlayerEntity player) {
        return !locked;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
		if (locked) return false;
		if (stack.isEmpty()) return false;
		boolean trollface = acceptsTrollFace && (stack.getItem() == RRItems.trollmask);
		boolean timedbomb = acceptsTimedBomb && (stack.getItem() == RRBlocks.timedbomb.asItem());
        return stackLock.test(stack) || trollface || timedbomb;
    }

    @Override
    public int getMaxItemCount() {
        return maxStack;
    }

    public SlotRR setAcceptsTrollface(boolean t)
	{
		acceptsTrollFace = t;

		return this;
	}

	public SlotRR setAcceptsTimedBomb(boolean t)
	{
		acceptsTimedBomb = t;

		return this;
	}
}
