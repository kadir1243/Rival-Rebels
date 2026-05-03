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
package io.github.kadir1243.rivalrebels.common.block.autobuilds;

import io.github.kadir1243.rivalrebels.common.core.BlackList;
import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.CommonColors;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.registries.DeferredBlock;

public abstract class BlockAutoTemplate extends FallingBlock {
    public int		time	= 15;
	public String	name	= "building";

    public BlockAutoTemplate(Properties settings) {
        super(settings);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            if (!stack.is(RRItems.pliers)){
                player.sendSystemMessage(Translations.warning().append(" ").append(Translations.USE_PLIERS_TO_BUILD_TRANSLATION.translate()));
                return InteractionResult.PASS;
            }
            return InteractionResult.SUCCESS;
		}
        return InteractionResult.PASS;
	}

	public void build(Level world, int x, int y, int z) {
        world.playLocalSound(x, y, z, RRSounds.AUTO_BUILD.get(), SoundSource.BLOCKS, 10, 1, false);
	}

	public void placeBlockCarefully(Level world, int x, int y, int z, Block block) {
		placeBlockCarefully(world, x, y, z, block.defaultBlockState());
	}

    public void placeBlockCarefully(Level world, int x, int y, int z, DeferredBlock<Block> block) {
        placeBlockCarefully(world, x, y, z, block.get());
    }

    public void placeBlockCarefully(Level world, int x, int y, int z, BlockState state) {
        placeBlockCarefully(world, new BlockPos(x, y, z), state);
    }

    public void placeBlockCarefully(Level world, BlockPos pos, Block block) {
        placeBlockCarefully(world, pos, block.defaultBlockState());
    }

    public void placeBlockCarefully(Level world, BlockPos pos, BlockState state) {
        if (!BlackList.autobuild(world.getBlockState(pos))) {
            world.setBlockAndUpdate(pos, state);
        }
    }

    @Override
    public void onLand(Level world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity) {
		if (!world.isClientSide()) build(world, pos.getX(), pos.getY(), pos.getZ());
	}

    @Override
    public int getDustColor(BlockState state, BlockGetter level, BlockPos pos) {
        return CommonColors.WHITE;
    }
}
