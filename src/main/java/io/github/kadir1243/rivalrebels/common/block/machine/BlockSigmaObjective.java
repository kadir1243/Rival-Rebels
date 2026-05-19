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
package io.github.kadir1243.rivalrebels.common.block.machine;

import io.github.kadir1243.rivalrebels.RivalRebels;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.tileentity.Tickable;
import io.github.kadir1243.rivalrebels.common.tileentity.SigmaObjectiveBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class BlockSigmaObjective extends BaseEntityBlock {
    public static final MapCodec<BlockSigmaObjective> CODEC = simpleCodec(BlockSigmaObjective::new);
    public BlockSigmaObjective(Properties settings)
	{
		super(settings);
	}

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean movedByPiston) {
		if (!pos.equals(RivalRebels.round.sigmaData.objPos())) {
			world.setBlockAndUpdate(RivalRebels.round.sigmaData.objPos(), RRBlocks.plasmaexplosion.get().defaultBlockState());
			RivalRebels.round.sigmaData.objPos = pos;
			if (world.getBlockState(RivalRebels.round.omegaData.objPos()).is(RRBlocks.omegaobj))
				RivalRebels.round.roundManualStart();
		}
	}

    @Override
    public void onBlockStateChange(LevelReader readOnlyLevel, BlockPos pos, BlockState oldState, BlockState newState) {
        super.onBlockStateChange(readOnlyLevel, pos, oldState, newState);

        if (!(readOnlyLevel instanceof Level level)) return;
        if (!newState.is(RRBlocks.plasmaexplosion) && !newState.is(this) && !newState.is(RRBlocks.omegaobj)) {
            level.setBlockAndUpdate(pos, oldState);
		}
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        level.playSound(player, pos, RRSounds.GUI_UNKNOWN4.get(), SoundSource.PLAYERS);

		return InteractionResult.SUCCESS;
	}

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SigmaObjectiveBlockEntity(pos, state);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> ((Tickable) blockEntity).tick();
    }
}
