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

import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.block.trap.BlockRemoteCharge;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.util.ItemUtil;
import assets.rivalrebels.common.util.Translations;
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
	public ItemRemote() {
		super(new Properties().stacksTo(1).component(RRComponents.REMOTE_CONTROLLED_BOMB_POS, BlockPos.ZERO));
	}

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level world = context.getLevel();
        BlockPos RCpos = context.getItemInHand().get(RRComponents.REMOTE_CONTROLLED_BOMB_POS);
        if (player.level().getBlockState(RCpos.above()).is(RRBlocks.remotecharge) && player.isShiftKeyDown()) {
			RivalRebelsSoundPlayer.playSound(player, 22, 3);
			BlockRemoteCharge.explode(world, RCpos.above());
            return InteractionResult.sidedSuccess(world.isClientSide());
		}
		return super.useOn(context);
	}

    //@Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.getPlayer();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();

        ItemStack itemStack = ItemUtil.getItemStack(player, RRBlocks.remotecharge.asItem());
        if (((player.getAbilities().invulnerable && world.isEmptyBlock(pos.above()) || !itemStack.isEmpty() && world.isEmptyBlock(pos.above()))) && !player.isShiftKeyDown()) {
			RivalRebelsSoundPlayer.playSound(player, 22, 2);
            player.displayClientMessage(Translations.orders().append(" ").append(Component.literal("Shift-click (Sneak) to detonate.").withStyle(ChatFormatting.RED)), false);
            itemStack.set(RRComponents.REMOTE_CONTROLLED_BOMB_POS, pos);
            itemStack.consume(1, context.getPlayer());
			world.setBlockAndUpdate(pos.above(), RRBlocks.remotecharge.defaultBlockState());
            return InteractionResult.sidedSuccess(world.isClientSide());
        }
        return InteractionResult.FAIL;
    }
}
