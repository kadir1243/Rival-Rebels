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
package assets.rivalrebels.common.tileentity;

import assets.rivalrebels.common.block.BlockReactive;
import assets.rivalrebels.common.block.RRBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class TileEntityReactive extends TileEntityMachineBase
{
	private int	cooldown	= 0;

	public TileEntityReactive(BlockPos pos, BlockState state) {
        super(RRTileEntities.REACTIVE, pos, state);
		pInM = 1;
	}

	@Override
	public float powered(float power, float distance)
	{
		int metadata = this.getCachedState().get(BlockReactive.META);
		if (metadata > 0)
		{
			if (cooldown <= 0)
			{
				world.setBlockState(getPos(), RRBlocks.reactive.getDefaultState().with(BlockReactive.META, metadata - 1), 2);
				cooldown = 10;
				return power - 1;
			}
			cooldown--;
		}
		return power;
	}
}
