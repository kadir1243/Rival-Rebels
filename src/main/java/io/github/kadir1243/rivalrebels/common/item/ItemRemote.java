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

import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.block.trap.BlockRemoteCharge;
import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import io.github.kadir1243.rivalrebels.common.util.ItemUtil;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class ItemRemote extends Item {
	public ItemRemote(Properties properties) {
		super(properties.stacksTo(1).component(RRComponents.REMOTE_CONTROLLED_BOMB_POS, BlockPos.ZERO));
	}

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level world = context.getLevel();
        BlockPos RCpos = context.getItemInHand().get(RRComponents.REMOTE_CONTROLLED_BOMB_POS);
        if (player.level().getBlockState(RCpos.above()).is(RRBlocks.remotecharge) && player.isShiftKeyDown()) {
            player.playSound(RRSounds.REMOTE_EXPLODE.get());
			BlockRemoteCharge.explode(world, RCpos.above());
            return InteractionResult.SUCCESS;
		}
		return super.useOn(context);
	}

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.getPlayer();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();

        ItemStack itemStack = ItemUtil.getItemStack(player, RRBlocks.remotecharge.asItem());
        if (((player.getAbilities().invulnerable && world.isEmptyBlock(pos.above()) || !itemStack.isEmpty() && world.isEmptyBlock(pos.above()))) && !player.isShiftKeyDown()) {
            player.playSound(RRSounds.REMOTE_PLANT.get());
            player.displayClientMessage(Translations.orders().append(" ").append(Component.literal("Shift-click (Sneak) to detonate.").withStyle(ChatFormatting.RED)), false);
            itemStack.set(RRComponents.REMOTE_CONTROLLED_BOMB_POS, pos);
            itemStack.consume(1, context.getPlayer());
			world.setBlockAndUpdate(pos.above(), RRBlocks.remotecharge.get().defaultBlockState());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
