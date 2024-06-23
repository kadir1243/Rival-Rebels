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
package assets.rivalrebels.common.tileentity;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TileEntityMachineBase extends BlockEntity implements Tickable {
    public static final Map<BlockPos, TileEntityMachineBase> BLOCK_ENTITIES = new HashMap<>();
	public float	pInM		= 0;
	public float	pInR		= 0;
	public float	edist		= 0;
	public float	decay		= 0;
	public float	powerGiven	= 0;
    public BlockPos worldPosition = BlockPos.ZERO;

    public TileEntityMachineBase(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        BLOCK_ENTITIES.put(pos, this);
    }

    @Override
	public void tick() {
		if (pInR > 0) pInR = powered(pInR, edist);
		pInR -= decay;
	}

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        worldPosition = BlockPos.of(nbt.getLong("rpos"));
        edist = nbt.getFloat("edist");
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
		super.saveAdditional(nbt, provider);
        nbt.putLong("rpos", worldPosition.asLong());
		nbt.putFloat("edist", edist);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
		BlockEntity connectedTo = level.getBlockEntity(worldPosition);
		if (connectedTo instanceof TileEntityReactor) ((TileEntityReactor)connectedTo).machines.remove(this);
        BLOCK_ENTITIES.remove(getBlockPos());
	}

	abstract public float powered(float power, float distance);
}
