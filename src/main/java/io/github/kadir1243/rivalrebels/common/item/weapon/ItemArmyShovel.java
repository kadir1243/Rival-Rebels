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
package io.github.kadir1243.rivalrebels.common.item.weapon;

import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
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
        init();
        return blocksEffectiveAgainst.contains(state.getBlock());
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        init();
        return blocksEffectiveAgainst.contains(state.getBlock()) ? Tiers.DIAMOND.getSpeed() : 1.0f;
    }

    private static void init() {
        if (blocksEffectiveAgainst.isEmpty()) {
            blocksEffectiveAgainst.add(RRBlocks.barricade.get());
            blocksEffectiveAgainst.add(RRBlocks.reactive.get());
            blocksEffectiveAgainst.add(RRBlocks.conduit.get());
            blocksEffectiveAgainst.add(RRBlocks.tower.get());
            blocksEffectiveAgainst.add(RRBlocks.steel.get());
            blocksEffectiveAgainst.add(RRBlocks.rhodesactivator.get());
            blocksEffectiveAgainst.add(RRBlocks.camo1.get());
            blocksEffectiveAgainst.add(RRBlocks.camo2.get());
            blocksEffectiveAgainst.add(RRBlocks.camo3.get());
            blocksEffectiveAgainst.add(RRBlocks.jump.get());
            blocksEffectiveAgainst.add(RRBlocks.landmine.get());
            blocksEffectiveAgainst.add(RRBlocks.alandmine.get());
            blocksEffectiveAgainst.add(RRBlocks.quicksand.get());
            blocksEffectiveAgainst.add(RRBlocks.aquicksand.get());
            blocksEffectiveAgainst.add(RRBlocks.mario.get());
            blocksEffectiveAgainst.add(RRBlocks.amario.get());
            blocksEffectiveAgainst.add(RRBlocks.loader.get());
            blocksEffectiveAgainst.add(RRBlocks.reactor.get());
            blocksEffectiveAgainst.add(RRBlocks.radioactivedirt.get());
            blocksEffectiveAgainst.add(RRBlocks.radioactivesand.get());
            blocksEffectiveAgainst.add(RRBlocks.petrifiedstone1.get());
            blocksEffectiveAgainst.add(RRBlocks.petrifiedstone2.get());
            blocksEffectiveAgainst.add(RRBlocks.petrifiedstone3.get());
            blocksEffectiveAgainst.add(RRBlocks.petrifiedstone4.get());
            blocksEffectiveAgainst.add(RRBlocks.petrifiedwood.get());
        }
    }
}
