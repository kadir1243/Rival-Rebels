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

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockLight extends Block
{
	public int rendertype;

	public BlockLight(Properties settings, int EntityRenderer)
	{
		super(settings);
		rendertype = EntityRenderer;
	}

    @Override
    public RenderShape getRenderShape(BlockState state) {
        if (this == RRBlocks.light2)
            return RenderShape.INVISIBLE;
		return super.getRenderShape(state);
	}

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
		if (this == RRBlocks.light) {
			world.scheduleTick(pos, this, 10);
		}
	}

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
    }
}
