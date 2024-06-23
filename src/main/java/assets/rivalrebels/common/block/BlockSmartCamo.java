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

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSmartCamo extends Block
{
	public BlockSmartCamo(Settings settings)
	{
		super(settings);
	}

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (getBlock(world, x + 1, y, z) == Blocks.SNOW || getBlock(world, x - 1, y, z) == Blocks.SNOW || getBlock(world, x, y, z - 1) == Blocks.SNOW || getBlock(world, x, y, z + 1) == Blocks.SNOW) {
            setBlock(world, pos, RRBlocks.camo3);
        } else {
            if (getBlockState(world, pos.down()).isIn(BlockTags.DIRT)) {
                setBlock(world, pos, RRBlocks.camo1);
            } else {
                if (getBlockState(world, pos.down()).isIn(BlockTags.SAND) || getBlockState(world, pos.down()).isIn(ConventionalBlockTags.SANDSTONE_BLOCKS)) {
                    setBlock(world, pos, RRBlocks.camo2);
                } else {
                    if (getBlockState(world, pos.down()).isIn(BlockTags.BASE_STONE_OVERWORLD) || getBlockState(world, pos.down()).isOf(Blocks.GRAVEL) || getBlock(world, pos.down()) == Blocks.BEDROCK || getBlockState(world, pos.down()).isOf(Blocks.COBBLESTONE)) {
                        setBlock(world, pos, RRBlocks.camo3);
                    } else {
                        if (getBlock(world, pos.down()) == RRBlocks.camo2 || getBlock(world, x + 1, y, z) == RRBlocks.camo2 || getBlock(world, x - 1, y, z) == RRBlocks.camo2 || getBlock(world, x, y, z + 1) == RRBlocks.camo2 || getBlock(world, x, y, z - 1) == RRBlocks.camo2 || getBlock(world, x, y + 1, z) == RRBlocks.camo2) {
                            setBlock(world, pos, RRBlocks.camo2);
                        } else {
                            if (getBlock(world, pos.down()) == RRBlocks.camo3 || getBlock(world, x + 1, y, z) == RRBlocks.camo3 || getBlock(world, x - 1, y, z) == RRBlocks.camo3 || getBlock(world, x, y, z + 1) == RRBlocks.camo3 || getBlock(world, x, y, z - 1) == RRBlocks.camo3 || getBlock(world, x, y + 1, z) == RRBlocks.camo3) {
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

    private static Block getBlock(World world, int x, int y, int z) {
        return getBlockState(world, x, y, z).getBlock();
    }

    private static BlockState getBlockState(World world, int x, int y, int z) {
        return world.getBlockState(new BlockPos(x, y, z));
    }

    private static void setBlock(World world, BlockPos pos, Block block) {
        world.setBlockState(pos, block.getDefaultState());
    }

    private static Block getBlock(World world, BlockPos pos) {
        return getBlockState(world, pos).getBlock();
    }

    private static BlockState getBlockState(World world, BlockPos pos) {
        return world.getBlockState(pos);
    }
}
