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
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public class BlockForceShield extends Block {
	public BlockForceShield(Properties settings)
	{
		super(settings.pushReaction(PushReaction.BLOCK));
	}

    @Override
    public void onBlockStateChange(LevelReader readOnlyLevel, BlockPos pos, BlockState oldState, BlockState newState) {
        super.onBlockStateChange(readOnlyLevel, pos, oldState, newState);

        if (!(readOnlyLevel instanceof Level level)) return;
        if (newState.is(RRBlocks.fshield) || newState.is(RRBlocks.omegaobj) || newState.is(RRBlocks.sigmaobj) || newState.is(RRBlocks.reactive)) {
            level.setBlockAndUpdate(pos, oldState);
        }
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
        return super.canHarvestBlock(state, level, pos, player) && player.isCreative() && player.isShiftKeyDown();
    }
}
