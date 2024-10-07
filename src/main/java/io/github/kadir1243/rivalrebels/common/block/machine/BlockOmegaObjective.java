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
import io.github.kadir1243.rivalrebels.common.tileentity.OmegaObjectiveBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BlockOmegaObjective extends BaseEntityBlock {
    public static final MapCodec<BlockOmegaObjective> CODEC = simpleCodec(BlockOmegaObjective::new);
	public BlockOmegaObjective(Properties settings) {
		super(settings);
	}

    @Override
    protected MapCodec<BlockOmegaObjective> codec() {
        return CODEC;
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
		if (!pos.equals(RivalRebels.round.omegaData.objPos())) {
			world.setBlockAndUpdate(RivalRebels.round.omegaData.objPos(), RRBlocks.plasmaexplosion.get().defaultBlockState());
			RivalRebels.round.omegaData.objPos = pos;
			if (world.getBlockState(RivalRebels.round.sigmaData.objPos()).is(RRBlocks.sigmaobj))
				RivalRebels.round.roundManualStart();
		}
	}

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        super.onRemove(state, world, pos, newState, moved);

        if (!newState.is(RRBlocks.plasmaexplosion) && !newState.is(this) && !newState.is(RRBlocks.sigmaobj)) {
            world.setBlockAndUpdate(pos, state);
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        level.playSound(player, pos, RRSounds.GUI_UNKNOWN4.get(), SoundSource.PLAYERS);

		return InteractionResult.sidedSuccess(level.isClientSide());
	}

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new OmegaObjectiveBlockEntity(pos, state);
	}
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> ((Tickable) blockEntity).tick();
    }
}
