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
package io.github.kadir1243.rivalrebels.common.item;

import io.github.kadir1243.rivalrebels.RivalRebels;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.item.components.ChipData;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class ItemChip extends Item {
	public ItemChip()
	{
		super(new Properties().stacksTo(1));
	}

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (RivalRebels.round.isStarted() && !stack.has(RRComponents.CHIP_DATA) && entity instanceof Player player) {
            stack.set(RRComponents.CHIP_DATA, new ChipData(player.getGameProfile(), RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile()).rrteam));
		}
	}

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();
		if (!world.isClientSide()) {
			if (world.getBlockState(pos).is(RRBlocks.buildrhodes)) {
				world.setBlockAndUpdate(pos.west(), RRBlocks.steel.get().defaultBlockState());
				world.setBlockAndUpdate(pos.east(), RRBlocks.steel.get().defaultBlockState());
				world.setBlockAndUpdate(pos.above(), RRBlocks.conduit.get().defaultBlockState());
				world.setBlockAndUpdate(pos.west().above(), RRBlocks.steel.get().defaultBlockState());
				world.setBlockAndUpdate(pos.east().above(), RRBlocks.steel.get().defaultBlockState());
				world.setBlockAndUpdate(pos.above(2), RRBlocks.steel.get().defaultBlockState());
				world.setBlockAndUpdate(pos.west().above(2), RRBlocks.steel.get().defaultBlockState());
				world.setBlockAndUpdate(pos.east().above(2), RRBlocks.steel.get().defaultBlockState());
				if (world.getBlockState(pos.below()).is(RRBlocks.buildrhodes)) {
					world.setBlockAndUpdate(pos, RRBlocks.conduit.get().defaultBlockState());
					world.setBlockAndUpdate(pos.below(), RRBlocks.rhodesactivator.get().defaultBlockState());
					world.setBlockAndUpdate(pos.west().below(), RRBlocks.steel.get().defaultBlockState());
					world.setBlockAndUpdate(pos.east().below(), RRBlocks.steel.get().defaultBlockState());
				} else {
					world.setBlockAndUpdate(pos, RRBlocks.rhodesactivator.get().defaultBlockState());
				}
				return InteractionResult.sidedSuccess(world.isClientSide());
			}
		}
		return InteractionResult.PASS;
	}

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (stack.has(RRComponents.CHIP_DATA)) {
            ChipData chipData = stack.get(RRComponents.CHIP_DATA);
            tooltipComponents.add(Component.literal(chipData.team().name()));
            tooltipComponents.add(Component.literal("Player with name " + chipData.gameProfile().getName() + ", and uuid " + chipData.gameProfile().getId()));
		}
	}
}
