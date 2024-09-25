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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;

public class ItemTrollHelmet extends ArmorItem {
    public ItemTrollHelmet() {
		super(RRItems.TROLL_MATERIAL, Type.HELMET, new Properties().durability(5000));
	}

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionHand hand = context.getHand();
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();
        Player player = context.getPlayer();
        Direction facing = context.getClickedFace();

        ItemStack stack = player.getItemInHand(hand);
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (state.is(BlockTags.SNOW) && state.getValue(SnowLayerBlock.LAYERS) < 1) {
            facing = Direction.UP;
        } else if (block != Blocks.VINE && block != Blocks.TALL_GRASS && block != Blocks.DEAD_BUSH && !state.canBeReplaced(new BlockPlaceContext(context))) {
            pos = pos.relative(facing);
        }
        if (stack.isEnchanted() || !player.mayUseItemAt(pos, facing, stack)) {
            return InteractionResult.FAIL;
        }
        if (world.isUnobstructed(RRBlocks.trollFlag.defaultBlockState(), pos, CollisionContext.of(player))) {
            BlockState flagState = RRBlocks.trollFlag.getStateForPlacement(new BlockPlaceContext(player, hand, stack, new BlockHitResult(context.getClickLocation(), facing, pos, false)));
            world.setBlockAndUpdate(pos, flagState);
            world.playLocalSound(pos, RRBlocks.trollFlag.defaultBlockState().getSoundType().getStepSound(), SoundSource.PLAYERS, (RRBlocks.trollFlag.defaultBlockState().getSoundType().getVolume() + 1.0F) / 2.0F, RRBlocks.trollFlag.defaultBlockState().getSoundType().getPitch() * 0.8F, false);
            stack.consume(1, context.getPlayer());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
