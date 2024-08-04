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
package assets.rivalrebels.common.block;

import assets.rivalrebels.common.util.ModBlockTags;
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
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (getBlockState(world, x + 1, y, z).is(BlockTags.SNOW) || getBlockState(world, x - 1, y, z).is(BlockTags.SNOW) || getBlockState(world, x, y, z - 1).is(BlockTags.SNOW) || getBlockState(world, x, y, z + 1).is(BlockTags.SNOW)) {
            setBlock(world, pos, RRBlocks.camo3);
        } else {
            if (getBlockState(world, pos.below()).is(BlockTags.DIRT)) {
                setBlock(world, pos, RRBlocks.camo1);
            } else {
                if (getBlockState(world, pos.below()).is(BlockTags.SAND) || getBlockState(world, pos.below()).is(ModBlockTags.SANDSTONE_BLOCKS)) {
                    setBlock(world, pos, RRBlocks.camo2);
                } else {
                    if (getBlockState(world, pos.below()).is(BlockTags.BASE_STONE_OVERWORLD) || getBlockState(world, pos.below()).is(Blocks.GRAVEL) || getBlock(world, pos.below()) == Blocks.BEDROCK || getBlockState(world, pos.below()).is(Blocks.COBBLESTONE)) {
                        setBlock(world, pos, RRBlocks.camo3);
                    } else {
                        if (getBlock(world, pos.below()) == RRBlocks.camo2 || getBlock(world, x + 1, y, z) == RRBlocks.camo2 || getBlock(world, x - 1, y, z) == RRBlocks.camo2 || getBlock(world, x, y, z + 1) == RRBlocks.camo2 || getBlock(world, x, y, z - 1) == RRBlocks.camo2 || getBlock(world, x, y + 1, z) == RRBlocks.camo2) {
                            setBlock(world, pos, RRBlocks.camo2);
                        } else {
                            if (getBlock(world, pos.below()) == RRBlocks.camo3 || getBlock(world, x + 1, y, z) == RRBlocks.camo3 || getBlock(world, x - 1, y, z) == RRBlocks.camo3 || getBlock(world, x, y, z + 1) == RRBlocks.camo3 || getBlock(world, x, y, z - 1) == RRBlocks.camo3 || getBlock(world, x, y + 1, z) == RRBlocks.camo3) {
                                setBlock(world, pos, RRBlocks.camo3);
                            } else {
                                setBlock(world, pos, RRBlocks.camo1);
                            }
                        }
                    }
                }
            }
        }
    }

    private static Block getBlock(Level world, int x, int y, int z) {
        return getBlockState(world, x, y, z).getBlock();
    }

    private static BlockState getBlockState(Level world, int x, int y, int z) {
        return world.getBlockState(new BlockPos(x, y, z));
    }

    private static void setBlock(Level world, BlockPos pos, Block block) {
        world.setBlockAndUpdate(pos, block.defaultBlockState());
    }

    private static Block getBlock(Level world, BlockPos pos) {
        return getBlockState(world, pos).getBlock();
    }

    private static BlockState getBlockState(Level world, BlockPos pos) {
        return world.getBlockState(pos);
    }
}
