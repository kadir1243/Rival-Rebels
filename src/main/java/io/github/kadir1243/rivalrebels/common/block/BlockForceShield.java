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
package io.github.kadir1243.rivalrebels.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public class BlockForceShield extends Block {
	public BlockForceShield(Properties settings)
	{
		super(settings.pushReaction(PushReaction.BLOCK));
	}

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (newState.is(RRBlocks.fshield) || newState.is(RRBlocks.omegaobj) || newState.is(RRBlocks.sigmaobj) || newState.is(RRBlocks.reactive)) {
            level.setBlockAndUpdate(pos, state);
            return;
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
        return super.canHarvestBlock(state, level, pos, player) && player.hasInfiniteMaterials() && player.isShiftKeyDown();
    }
}
