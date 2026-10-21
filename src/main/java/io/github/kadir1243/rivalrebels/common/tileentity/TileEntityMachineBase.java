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

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class TileEntityMachineBase extends BlockEntity {
    public static final Table<ResourceKey<Level>, BlockPos, TileEntityMachineBase> BLOCK_ENTITIES = HashBasedTable.create();
	public float	pInM		= 0;
	public float	pInR		= 0;
	public float	edist		= 0;
	public float	decay		= 0;
	public float	powerGiven	= 0;
    public BlockPos worldPosition = BlockPos.ZERO;

    public TileEntityMachineBase(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void setLevel(Level level) {
        super.setLevel(level);

        if (!level.isClientSide()) {
            BLOCK_ENTITIES.put(level.dimension(), getBlockPos(), this);
        }
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, TileEntityMachineBase blockEntity) {
        if (blockEntity.pInR > 0) blockEntity.pInR = blockEntity.powered(blockEntity.pInR, blockEntity.edist);
        blockEntity.pInR -= blockEntity.decay;
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        valueInput.getLong("rpos").map(BlockPos::of).ifPresent(pos -> worldPosition = pos);
        edist = valueInput.getFloatOr("edist", 0);
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);

        valueOutput.putLong("rpos", worldPosition.asLong());
		valueOutput.putFloat("edist", edist);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (getLevel() != null) {
            BlockEntity connectedTo = getLevel().getBlockEntity(worldPosition);
            if (connectedTo instanceof TileEntityReactor) ((TileEntityReactor)connectedTo).machines.remove(this);
            if (!getLevel().isClientSide()) {
                BLOCK_ENTITIES.remove(getLevel().dimension(), getBlockPos());
            }
        }
	}

	public abstract float powered(float power, float distance);
}
