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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Arrays;

public class BlockMario extends Block
{
	public BlockMario(Properties settings) {
		super(settings);
	}

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		float f = 0.0625F;
		return Shapes.create(0, 0, 0, 1, 1 - f, 1);
	}

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier applier, boolean intersects) {
		if (entity instanceof Player || entity instanceof Mob) {
			level.setBlockAndUpdate(pos, Blocks.GRAVEL.defaultBlockState());
		}
	}

	/*@Override
	public final IIcon getIcon(BlockGetter world, int x, int y, int z, int s)
	{
		if (this == RivalRebels.mario) return Blocks.GRASS.getIcon(world, x, y, z, s);
		Block[] n = new Block[6];
		n[0] = world.getBlock(x + 1, y, z);
		n[1] = world.getBlock(x - 1, y, z);
		n[2] = world.getBlock(x, y + 1, z);
		n[3] = world.getBlock(x, y - 1, z);
		n[4] = world.getBlock(x, y, z + 1);
		n[5] = world.getBlock(x, y, z - 1);

		int popularity1 = 0;
		int popularity2 = 0;
		Block mode = Blocks.GRAVEL;
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
	}*/

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        BlockState[] n = Arrays.stream(Direction.values()).map(pos::relative).map(level::getBlockState).toArray(BlockState[]::new);

        int popularity1 = 0;
        int popularity2 = 0;
        BlockState mode = Blocks.GRAVEL.defaultBlockState();
        BlockState array_item;
        for (int i = 0; i < 6; i++) {
            array_item = n[i];
            if (array_item.isAir() || !array_item.isSolid() || array_item.is(RRBlocks.landmine) || array_item.is(RRBlocks.alandmine) || array_item.is(RRBlocks.mario) || array_item.is(RRBlocks.amario) || array_item.is(RRBlocks.quicksand) || array_item.is(RRBlocks.aquicksand)) continue;
            for (BlockState blockState : n) {
                if (array_item == blockState) popularity1++;
                if (popularity1 >= popularity2) {
                    mode = array_item;
                    popularity2 = popularity1;
                }
            }
            popularity1 = 0;
        }
        if (mode.is(Blocks.GRASS_BLOCK)) level.setBlock(pos, RRBlocks.mario.get().defaultBlockState(), 3);
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        return RRBlocks.amario.toStack();
    }

    /*@Override
	public final IIcon getIcon(int side, int meta)
	{
		if (this == RivalRebels.amario)
		{
			return Blocks.GRAVEL.getIcon(side, meta);
		}
		else
		{
			return Blocks.GRASS.getIcon(side, meta);
		}
	}*/
}
