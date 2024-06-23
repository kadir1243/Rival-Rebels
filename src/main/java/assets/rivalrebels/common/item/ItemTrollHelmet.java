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
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ItemTrollHelmet extends ArmorItem {
    public ItemTrollHelmet() {
		super(RRItems.TROLL_MATERIAL, Type.HELMET, new Settings().maxDamage(5000));
	}

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Hand hand = context.getHand();
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        Direction facing = context.getSide();

        ItemStack stack = player.getStackInHand(hand);
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block == Blocks.SNOW && state.get(SnowBlock.LAYERS) < 1) {
            facing = Direction.UP;
        } else if (block != Blocks.VINE && block != Blocks.TALL_GRASS && block != Blocks.DEAD_BUSH && !state.canReplace(new ItemPlacementContext(context))) {
            pos = pos.offset(facing);
        }
        if (stack.hasEnchantments() || !player.canPlaceOn(pos, facing, stack)) {
            return ActionResult.FAIL;
        }
        if (world.canPlace(RRBlocks.flag2.getDefaultState(), pos, ShapeContext.of(player))) {
            BlockState flagState = RRBlocks.flag2.getPlacementState(new ItemPlacementContext(player, hand, stack, new BlockHitResult(context.getHitPos(), facing, pos, false)));
            world.setBlockState(pos, flagState);
            world.playSound((float) pos.getX() + 0.5F, (float) pos.getY() + 0.5F, (float) pos.getZ() + 0.5F, RRBlocks.flag2.getDefaultState().getSoundGroup().getStepSound(), SoundCategory.PLAYERS, (RRBlocks.flag2.getDefaultState().getSoundGroup().getVolume() + 1.0F) / 2.0F, RRBlocks.flag2.getDefaultState().getSoundGroup().getPitch() * 0.8F, false);
            stack.decrement(1);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
