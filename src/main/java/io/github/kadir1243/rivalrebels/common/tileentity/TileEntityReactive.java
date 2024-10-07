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
package io.github.kadir1243.rivalrebels.common.tileentity;

import io.github.kadir1243.rivalrebels.common.block.BlockReactive;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityReactive extends TileEntityMachineBase
{
	private int	cooldown	= 0;

	public TileEntityReactive(BlockPos pos, BlockState state) {
        super(RRTileEntities.REACTIVE.get(), pos, state);
		pInM = 1;
	}

	@Override
	public float powered(float power, float distance)
	{
		int metadata = this.getBlockState().getValue(BlockReactive.META);
		if (metadata > 0) {
			if (cooldown <= 0) {
				level.setBlock(getBlockPos(), RRBlocks.reactive.get().defaultBlockState().setValue(BlockReactive.META, metadata - 1), Block.UPDATE_CLIENTS);
				cooldown = 10;
				return power - 1;
			}
			cooldown--;
		}
		return power;
	}
}
