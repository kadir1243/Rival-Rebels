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
package io.github.kadir1243.rivalrebels.common.block;

import io.github.kadir1243.rivalrebels.common.util.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BlockSmartCamo extends Block
{
	public BlockSmartCamo(Properties settings)
	{
		super(settings);
	}

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        if (getBlockState(world, pos.east()).is(BlockTags.SNOW) || getBlockState(world, pos.west()).is(BlockTags.SNOW) || getBlockState(world, pos.north()).is(BlockTags.SNOW) || getBlockState(world, pos.south()).is(BlockTags.SNOW)) {
            setBlock(world, pos, RRBlocks.camo3.get());
        } else {
            if (getBlockState(world, pos.below()).is(BlockTags.DIRT)) {
                setBlock(world, pos, RRBlocks.camo1.get());
            } else {
                if (getBlockState(world, pos.below()).is(BlockTags.SAND) || getBlockState(world, pos.below()).is(ModBlockTags.SANDSTONE_BLOCKS)) {
                    setBlock(world, pos, RRBlocks.camo2.get());
                } else {
                    if (getBlockState(world, pos.below()).is(BlockTags.BASE_STONE_OVERWORLD) || getBlockState(world, pos.below()).is(Blocks.GRAVEL) || getBlockState(world, pos.below()).is(Blocks.BEDROCK) || getBlockState(world, pos.below()).is(Blocks.COBBLESTONE)) {
                        setBlock(world, pos, RRBlocks.camo3.get());
                    } else {
                        if (getBlockState(world, pos.below()).is(RRBlocks.camo2) || getBlockState(world, pos.east()).is(RRBlocks.camo2) || getBlockState(world, pos.west()).is(RRBlocks.camo2) || getBlockState(world, pos.south()).is(RRBlocks.camo2) || getBlockState(world, pos.north()).is(RRBlocks.camo2) || getBlockState(world, pos.above()).is(RRBlocks.camo2)) {
                            setBlock(world, pos, RRBlocks.camo2.get());
                        } else {
                            if (getBlockState(world, pos.below()).is(RRBlocks.camo3) || getBlockState(world, pos.east()).is(RRBlocks.camo3) || getBlockState(world, pos.west()).is(RRBlocks.camo3) || getBlockState(world, pos.south()).is(RRBlocks.camo3) || getBlockState(world, pos.north()).is(RRBlocks.camo3) || getBlockState(world, pos.above()).is(RRBlocks.camo3)) {
                                setBlock(world, pos, RRBlocks.camo3.get());
                            } else {
                                setBlock(world, pos, RRBlocks.camo1.get());
                            }
                        }
                    }
                }
            }
        }
    }

    private static void setBlock(Level world, BlockPos pos, Block block) {
        world.setBlockAndUpdate(pos, block.defaultBlockState());
    }

    private static BlockState getBlockState(Level world, BlockPos pos) {
        return world.getBlockState(pos);
    }
}
