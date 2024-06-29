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
package assets.rivalrebels.common.item.weapon;

import assets.rivalrebels.common.block.RRBlocks;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ItemArmyShovel extends DiggerItem {
	private static final Set<Block> blocksEffectiveAgainst = new HashSet<>();

	public ItemArmyShovel() {
		super(Tiers.DIAMOND, BlockTags.MINEABLE_WITH_SHOVEL, new Properties().component(DataComponents.UNBREAKABLE, new Unbreakable(true)));
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return blocksEffectiveAgainst.contains(state.getBlock());
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return blocksEffectiveAgainst.contains(state.getBlock()) ? Tiers.DIAMOND.getSpeed() : 1.0f;
    }

    static {
        blocksEffectiveAgainst.add(RRBlocks.barricade);
		blocksEffectiveAgainst.add(RRBlocks.reactive);
		blocksEffectiveAgainst.add(RRBlocks.conduit);
		blocksEffectiveAgainst.add(RRBlocks.tower);
		blocksEffectiveAgainst.add(RRBlocks.steel);
		blocksEffectiveAgainst.add(RRBlocks.rhodesactivator);
		blocksEffectiveAgainst.add(RRBlocks.camo1);
		blocksEffectiveAgainst.add(RRBlocks.camo2);
		blocksEffectiveAgainst.add(RRBlocks.camo3);
		blocksEffectiveAgainst.add(RRBlocks.jump);
		blocksEffectiveAgainst.add(RRBlocks.landmine);
		blocksEffectiveAgainst.add(RRBlocks.alandmine);
		blocksEffectiveAgainst.add(RRBlocks.quicksand);
		blocksEffectiveAgainst.add(RRBlocks.aquicksand);
		blocksEffectiveAgainst.add(RRBlocks.mario);
		blocksEffectiveAgainst.add(RRBlocks.amario);
		blocksEffectiveAgainst.add(RRBlocks.loader);
		blocksEffectiveAgainst.add(RRBlocks.reactor);
		blocksEffectiveAgainst.add(RRBlocks.radioactivedirt);
		blocksEffectiveAgainst.add(RRBlocks.radioactivesand);
		blocksEffectiveAgainst.add(RRBlocks.petrifiedstone1);
		blocksEffectiveAgainst.add(RRBlocks.petrifiedstone2);
		blocksEffectiveAgainst.add(RRBlocks.petrifiedstone3);
		blocksEffectiveAgainst.add(RRBlocks.petrifiedstone4);
		blocksEffectiveAgainst.add(RRBlocks.petrifiedwood);
	}
}
