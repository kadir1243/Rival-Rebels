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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public class BlockForceShield extends Block
{
	public BlockForceShield(Properties settings)
	{
		super(settings.pushReaction(PushReaction.BLOCK));
	}

	boolean	Destroy	= false;

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
		Block id = state.getBlock();
		if (!Destroy && id != RRBlocks.fshield && id != RRBlocks.omegaobj && id != RRBlocks.sigmaobj && id != RRBlocks.reactive) {
			world.setBlockAndUpdate(pos, state);
		}
		Destroy = false;
        return state;
    }

    //@Override
    public void onBlockHarvested(Level world, BlockPos pos, BlockState state, Player player) {
		if (!world.isClientSide && player.getAbilities().invulnerable && player.isShiftKeyDown())
		{
			Destroy = true;
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		}
		else
		{
			Destroy = false;
			world.setBlockAndUpdate(pos, state);
		}
	}
}
