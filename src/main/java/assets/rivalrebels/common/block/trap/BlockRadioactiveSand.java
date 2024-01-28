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
package assets.rivalrebels.common.block.trap;

import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRadioactiveSand extends Block
{
	public BlockRadioactiveSand(Settings settings)
	{
		super(settings);
	}

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (world.random.nextInt(2) == 0) {
			entity.damage(RivalRebelsDamageSource.radioactivepoisoning, world.random.nextInt(2));
		}
	}

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		world.addParticle(DustParticleEffect.DEFAULT, pos.getX() + random.nextFloat(), pos.getY() + 1.1 + random.nextFloat() * 0.1, pos.getZ() + random.nextFloat(), 0.3F, 6F, 0.5F);
	}

	/*@Override
	public final IIcon getIcon(int side, int meta)
	{
		return Blocks.SAND.getIcon(side, meta);
	}*/
}
