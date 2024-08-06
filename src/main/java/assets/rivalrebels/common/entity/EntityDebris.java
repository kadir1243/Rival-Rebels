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
package assets.rivalrebels.common.entity;

import java.util.Optional;

import assets.rivalrebels.mixin.BlockEntityAccessor;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EntityDebris extends EntityInanimate {
    public static final EntityDataAccessor<Optional<BlockState>> STATE = SynchedEntityData.defineId(EntityDebris.class, EntityDataSerializers.OPTIONAL_BLOCK_STATE);
    public static final EntityDataAccessor<CompoundTag> TILE_ENTITY_DATA = SynchedEntityData.defineId(EntityDebris.class, EntityDataSerializers.COMPOUND_TAG);

    public EntityDebris(EntityType<? extends EntityDebris> type, Level w) {
		super(type, w);
	}

    public EntityDebris(Level w) {
        super(RREntities.DEBRIS, w);
    }

	public EntityDebris(Level w, BlockPos pos) {
		this(w);
        setState(w.getBlockState(pos));
        {
            BlockEntity blockEntity = w.getBlockEntity(pos);
            if (blockEntity != null) {
                setTileEntityData(blockEntity.saveWithFullMetadata(w.registryAccess()));
            }
        }
		w.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		setPos(Vec3.atLowerCornerOf(pos).add(0.5, 0.5, 0.5));
        setOldPosAndRot();
	}

    public EntityDebris(Level w, double x, double y, double z, double mx, double my, double mz, Block b) {
		this(w);
        setState(b.defaultBlockState());
		setPos(x, y, z);
        setOldPosAndRot();
        setDeltaMovement(mx, my, mz);
	}

    public BlockState getState() {
        return entityData.get(STATE).orElse(null);
    }

    public void setState(BlockState state) {
        entityData.set(STATE, Optional.ofNullable(state));
    }

    public CompoundTag getTileEntityData() {
        return entityData.get(TILE_ENTITY_DATA);
    }

    public void setTileEntityData(CompoundTag tileEntityData) {
        entityData.set(TILE_ENTITY_DATA, tileEntityData);
    }

    private BlockEntity blockEntityCache;
    private boolean hasBlockEntityCache = true;

    public BlockEntity getBlockEntity() {
        if (blockEntityCache == null && hasBlockEntityCache) {
            CompoundTag tileEntityData = getTileEntityData();
            BlockState state = getState();
            if (!tileEntityData.isEmpty() &&
                state.hasBlockEntity() &&
                state.getBlock() instanceof EntityBlock entityBlock
            ) {
                BlockEntity blockEntity = entityBlock.newBlockEntity(blockPosition(), state);
                if (blockEntity != null) {
                    CompoundTag tag = new CompoundTag();
                    ((BlockEntityAccessor) blockEntity).callSaveMetadata(tag);
                    tileEntityData.merge(tag);
                    blockEntity.loadWithComponents(tileEntityData, level().registryAccess());
                    blockEntityCache = blockEntity;
                } else {
                    hasBlockEntityCache = false;
                }
            }
        }
        if (blockEntityCache != null) {
            blockEntityCache.setChanged();
        }
        return blockEntityCache;
    }

    @Override
	public void tick() {
        setOldPosAndRot();
		++tickCount;
        applyGravity();
        setDeltaMovement(getDeltaMovement().scale(0.98));
        Vec3 add = getDeltaMovement().add(position());
        setPosRaw(add.x(), add.y(), add.z());

		if (!level().isClientSide && level().getBlockState(this.blockPosition()).canOcclude()) die(xo, yo, zo);
	}

    @Override
    protected double getDefaultGravity() {
        return 0.04;
    }

    public void die(double x, double y, double z) {
		kill();
        BlockPos pos = BlockPos.containing(x, y, z);
        level().setBlockAndUpdate(pos, getState());
        BlockEntity blockEntity = getBlockEntity();
        if (blockEntity != null) {
            level().setBlockEntity(blockEntity);
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        if (getState() != null) {
            nbt.put("Block", NbtUtils.writeBlockState(getState()));
        }
		nbt.putInt("Age", tickCount);
		if (!getTileEntityData().isEmpty()) nbt.put("TileEntityData", getTileEntityData());
	}

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        if (nbt.contains("Block")) {
            setState(NbtUtils.readBlockState(level().holderLookup(Registries.BLOCK), nbt.getCompound("Block")));
        }
        tickCount = nbt.getInt("Age");
		if (nbt.contains("TileEntityData", Tag.TAG_COMPOUND)) setTileEntityData(nbt.getCompound("TileEntityData"));
	}

    @Override
    public void fillCrashReportCategory(CrashReportCategory section) {
        super.fillCrashReportCategory(section);
        if (getState() != null) {
            section.setDetail("Immitating BlockState", this.getState().toString());
        }
        if (!getTileEntityData().isEmpty()) {
            section.setDetail("Immitating Block Entity Data", getTileEntityData().toString());
        }
	}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(STATE, Optional.empty());
        builder.define(TILE_ENTITY_DATA, new CompoundTag());
    }
}
