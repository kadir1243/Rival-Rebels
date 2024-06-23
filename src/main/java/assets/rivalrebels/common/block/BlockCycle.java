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

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraft.util.math.random.Random;

public class BlockCycle extends Block
{
	public float	phase		= 0;
	public float	phaseadd	= (float) (((Math.PI * 2) / 360) * 10);
	public float	pShiftR		= (float) (((Math.PI * 2f) / 3f) * 0f);
	public float	pShiftG		= (float) (((Math.PI * 2f) / 3f) * 1f);
	public float	pShiftB		= (float) (((Math.PI * 2f) / 3f) * 2f);

	public BlockCycle(Settings settings)
	{
		super(settings);
	}

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleBlockTick(pos, this, 1);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.scheduleBlockTick(pos, this, 1);
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
        world.setBlockState(pos, state);
    }
}
