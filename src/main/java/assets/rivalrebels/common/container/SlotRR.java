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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import assets.rivalrebels.RivalRebels;

import java.util.function.Predicate;

public class SlotRR extends Slot
{
	private int	maxStack;
    private final Predicate<ItemStack> stackLock;
    boolean acceptsTrollFace = false;
	boolean acceptsTimedBomb = false;
	public boolean locked = false;

	public SlotRR(IInventory inv, int id, int x, int y, int mstack, Class<?> only) {
		this(inv, id, x, y, mstack, stack -> only.isAssignableFrom(stack.getItem().getClass()) || (stack.getItem() instanceof ItemBlock itemBlock && only.isAssignableFrom(itemBlock.getBlock().getClass())));
	}

    public SlotRR(IInventory inv, int id, int x, int y, int mstack, Item only) {
        this(inv, id, x, y, mstack, stack -> stack.getItem() == only);
    }

    public SlotRR(IInventory inv, int id, int x, int y, int mstack, Predicate<ItemStack> stackLock) {
        super(inv, id, x, y);
        this.maxStack = mstack;
        this.stackLock = stackLock;
    }

	@Override
	public boolean canTakeStack(EntityPlayer player)
	{
		return !locked;
	}

	@Override
	public boolean isItemValid(ItemStack item)
	{
		if (locked) return false;
		if (item.isEmpty()) return false;
		boolean isblock = item.getItem() instanceof ItemBlock;
		boolean trollface = acceptsTrollFace && (item.getItem() == RivalRebels.trollmask);
		boolean timedbomb = acceptsTimedBomb && (item.getItem() == Item.getItemFromBlock(RivalRebels.timedbomb));
        return stackLock.test(item) || trollface || timedbomb;
    }

	@Override
	public int getSlotStackLimit()
	{
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
