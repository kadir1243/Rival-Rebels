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

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockForceShield extends Block
{
	public BlockForceShield(Settings settings)
	{
		super(settings);
	}

	boolean	Destroy	= false;

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		Block id = state.getBlock();
		if (!Destroy && id != RRBlocks.fshield && id != RRBlocks.omegaobj && id != RRBlocks.sigmaobj && id != RRBlocks.reactive) {
			world.setBlockState(pos, state);
		}
		Destroy = false;
	}

    //@Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!world.isClient && player.getAbilities().invulnerable && player.isSneaking())
		{
			Destroy = true;
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
		else
		{
			Destroy = false;
			world.setBlockState(pos, state);
		}
	}

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }
}
