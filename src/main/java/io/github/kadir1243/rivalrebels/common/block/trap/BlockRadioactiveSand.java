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

import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockRadioactiveSand extends Block
{
	public BlockRadioactiveSand(Properties settings)
	{
		super(settings);
	}

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
		if (world.random.nextInt(2) == 0) {
			entity.hurt(RivalRebelsDamageSource.radioactivePoisoning(world), world.random.nextInt(2));
		}
	}

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
		world.addParticle(DustParticleOptions.REDSTONE, pos.getX() + random.nextFloat(), pos.getY() + 1.1 + random.nextFloat() * 0.1, pos.getZ() + random.nextFloat(), 0.3F, 6F, 0.5F);
	}
}
