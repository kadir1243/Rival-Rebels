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
package assets.rivalrebels.common.item;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.item.components.ChipData;
import assets.rivalrebels.common.item.components.RRComponents;

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
		super(new Properties().stacksTo(1).component(RRComponents.CHIP_DATA, ChipData.DEFAULT));
	}

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (RivalRebels.round.isStarted() && !stack.get(RRComponents.CHIP_DATA).isReady() && entity instanceof Player player) {
            stack.set(RRComponents.CHIP_DATA, new ChipData(player.getGameProfile(), RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile()).rrteam, true));
		}
	}

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();
		if (!world.isClientSide()) {
			if (world.getBlockState(pos).is(RRBlocks.buildrhodes)) {
				world.setBlockAndUpdate(pos.west(), RRBlocks.steel.defaultBlockState());
				world.setBlockAndUpdate(pos.east(), RRBlocks.steel.defaultBlockState());
				world.setBlockAndUpdate(pos.above(), RRBlocks.conduit.defaultBlockState());
				world.setBlockAndUpdate(pos.west().above(), RRBlocks.steel.defaultBlockState());
				world.setBlockAndUpdate(pos.east().above(), RRBlocks.steel.defaultBlockState());
				world.setBlockAndUpdate(pos.above(2), RRBlocks.steel.defaultBlockState());
				world.setBlockAndUpdate(pos.west().above(2), RRBlocks.steel.defaultBlockState());
				world.setBlockAndUpdate(pos.east().above(2), RRBlocks.steel.defaultBlockState());
				if (world.getBlockState(pos.below()).is(RRBlocks.buildrhodes)) {
					world.setBlockAndUpdate(pos, RRBlocks.conduit.defaultBlockState());
					world.setBlockAndUpdate(pos.below(), RRBlocks.rhodesactivator.defaultBlockState());
					world.setBlockAndUpdate(pos.west().below(), RRBlocks.steel.defaultBlockState());
					world.setBlockAndUpdate(pos.east().below(), RRBlocks.steel.defaultBlockState());
				} else {
					world.setBlockAndUpdate(pos, RRBlocks.rhodesactivator.defaultBlockState());
				}
				return InteractionResult.sidedSuccess(world.isClientSide());
			}
		}
		return InteractionResult.PASS;
	}

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        ChipData chipData = stack.get(RRComponents.CHIP_DATA);
        if (chipData.isReady()) {
			tooltipComponents.add(Component.nullToEmpty(chipData.team().name()));
            tooltipComponents.add(Component.literal("Player with name " + chipData.gameProfile().getName() + ", and uuid " + chipData.gameProfile().getId()));
		}
	}
}
