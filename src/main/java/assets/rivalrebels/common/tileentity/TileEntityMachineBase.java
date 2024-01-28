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

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

public abstract class TileEntityMachineBase extends BlockEntity implements Tickable {
    public static final Map<BlockPos, TileEntityMachineBase> BLOCK_ENTITIES = new HashMap<>();
	public float	pInM		= 0;
	public float	pInR		= 0;
	public float	edist		= 0;
	public float	decay		= 0;
	public float	powerGiven	= 0;
    public BlockPos pos = BlockPos.ORIGIN;

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
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        pos = BlockPos.fromLong(nbt.getLong("rpos"));
        edist = nbt.getFloat("edist");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
        nbt.putLong("rpos", pos.asLong());
		nbt.putFloat("edist", edist);
    }

    @Override
    public void markRemoved() {
        super.markRemoved();
		BlockEntity connectedTo = world.getBlockEntity(pos);
		if (connectedTo instanceof TileEntityReactor) ((TileEntityReactor)connectedTo).machines.remove(this);
        BLOCK_ENTITIES.remove(getPos());
	}

	abstract public float powered(float power, float distance);
}
