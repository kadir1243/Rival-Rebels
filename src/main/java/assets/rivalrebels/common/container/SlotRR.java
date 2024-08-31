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
import java.util.function.Predicate;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SlotRR extends Slot {
	private final int maxStack;
    private final Predicate<ItemStack> stackLock;
    boolean acceptsTrollFace = false;
	boolean acceptsTimedBomb = false;
	public boolean locked = false;

	public SlotRR(Container inv, int id, int x, int y, int mstack, Class<?> only) {
		this(inv, id, x, y, mstack, stack -> only.isAssignableFrom(stack.getItem().getClass()) || (stack.getItem() instanceof BlockItem itemBlock && only.isAssignableFrom(itemBlock.getBlock().getClass())));
	}

    public SlotRR(Container inv, int id, int x, int y, int mstack, Item only) {
        this(inv, id, x, y, mstack, stack -> stack.is(only));
    }

    public SlotRR(Container inv, int id, int x, int y, int mstack, Predicate<ItemStack> stackLock) {
        super(inv, id, x, y);
        this.maxStack = mstack;
        this.stackLock = stackLock;
    }

    public SlotRR(Container inv, int id, int x, int y, int mstack, DataComponentType<?> only) {
        this(inv, id, x, y, mstack, stack -> stack.has(only));
    }


    @Override
    public boolean mayPickup(Player player) {
        return !locked;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
		if (locked) return false;
		if (stack.isEmpty()) return false;
		boolean trollface = acceptsTrollFace && (stack.is(RRItems.trollmask));
		boolean timedbomb = acceptsTimedBomb && (stack.is(RRBlocks.timedbomb.asItem()));
        return stackLock.test(stack) || trollface || timedbomb;
    }

    @Override
    public int getMaxStackSize() {
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
