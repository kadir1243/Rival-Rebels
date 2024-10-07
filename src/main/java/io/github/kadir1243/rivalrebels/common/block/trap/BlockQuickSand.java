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
package io.github.kadir1243.rivalrebels.common.block.trap;

import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockQuickSand extends Block
{
	public BlockQuickSand(Properties settings)
	{
		super(settings);
	}

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
		entity.fallDistance = 0.0F;
        entity.setDeltaMovement(entity.getDeltaMovement().multiply(1, 0.005, 1));
		if (world.random.nextFloat() > 0.95) world.playSound(entity, pos, RRSounds.QUICK_SAND.get(), SoundSource.BLOCKS, 0.2F, 1);
	}

	/*@Override
	public final IIcon getIcon(BlockGetter world, int x, int y, int z, int s)
	{
		if (this == RivalRebels.quicksand) return Blocks.GRASS.getIcon(world, x, y, z, s);
		Block[] n = new Block[6];
		n[0] = world.getBlock(x + 1, y, z);
		n[1] = world.getBlock(x - 1, y, z);
		n[2] = world.getBlock(x, y + 1, z);
		n[3] = world.getBlock(x, y - 1, z);
		n[4] = world.getBlock(x, y, z + 1);
		n[5] = world.getBlock(x, y, z - 1);

		int popularity1 = 0;
		int popularity2 = 0;
		Block mode = Blocks.SAND;
		Block array_item = null;
		for (int i = 0; i < 6; i++)
		{
			array_item = n[i];
			if (array_item == null || !array_item.isOpaqueCube() || array_item == RivalRebels.landmine || array_item == RivalRebels.alandmine || array_item == RivalRebels.mario || array_item == RivalRebels.amario || array_item == RivalRebels.quicksand || array_item == RivalRebels.aquicksand) continue;
			for (int j = 0; j < n.length; j++)
			{
				if (array_item == n[j]) popularity1++;
				if (popularity1 >= popularity2)
				{
					mode = array_item;
					popularity2 = popularity1;
				}
			}
			popularity1 = 0;
		}
		return mode.getIcon(world, x, y, z, s);
	}

	@Override
	public void onBlockAdded(Level world, int x, int y, int z)
	{
		Block[] n = new Block[6];
		n[0] = world.getBlock(x + 1, y, z);
		n[1] = world.getBlock(x - 1, y, z);
		n[2] = world.getBlock(x, y + 1, z);
		n[3] = world.getBlock(x, y - 1, z);
		n[4] = world.getBlock(x, y, z + 1);
		n[5] = world.getBlock(x, y, z - 1);

		int popularity1 = 0;
		int popularity2 = 0;
		Block mode = Blocks.SAND;
		Block array_item = null;
		for (int i = 0; i < 6; i++)
		{
			array_item = n[i];
			if (array_item == null || !array_item.isOpaqueCube() || array_item == RivalRebels.landmine || array_item == RivalRebels.alandmine || array_item == RivalRebels.mario || array_item == RivalRebels.amario || array_item == RivalRebels.quicksand || array_item == RivalRebels.aquicksand) continue;
            for (Block block : n) {
                if (array_item == block) popularity1++;
                if (popularity1 >= popularity2) {
                    mode = array_item;
                    popularity2 = popularity1;
                }
            }
			popularity1 = 0;
		}
		if (mode == Blocks.GRASS) world.setBlock(x, y, z, RivalRebels.quicksand);
	}*/

    @Override
    public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state) {
        return RRBlocks.aquicksand.toStack();
    }

	/*@Override
	public final IIcon getIcon(int side, int meta)
	{
		if (this == RivalRebels.aquicksand)
		{
			return Blocks.SAND.getIcon(side, meta);
		}
		else
		{
			return Blocks.GRASS.getIcon(side, meta);
		}
	}*/
}
