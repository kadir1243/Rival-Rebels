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
package io.github.kadir1243.rivalrebels.common.entity;

import java.util.Optional;

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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EntityDebris extends EntityInanimate {
    public static final EntityDataAccessor<Optional<BlockState>> STATE = SynchedEntityData.defineId(EntityDebris.class, EntityDataSerializers.OPTIONAL_BLOCK_STATE);
    public static final EntityDataAccessor<CompoundTag> TILE_ENTITY_DATA = SynchedEntityData.defineId(EntityDebris.class, EntityDataSerializers.COMPOUND_TAG);

    public EntityDebris(EntityType<? extends EntityDebris> type, Level level) {
		super(type, level);
	}

    public EntityDebris(Level level) {
        super(RREntities.DEBRIS.get(), level);
    }

	public EntityDebris(Level level, BlockPos pos) {
		this(level);
        setState(level.getBlockState(pos));
        {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity != null) {
                setTileEntityData(blockEntity.saveWithFullMetadata(level.registryAccess()));
            }
        }
		level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		setPos(Vec3.atLowerCornerOf(pos).add(0.5, 0.5, 0.5));
        setOldPosAndRot();
	}

    public EntityDebris(Level level, Block block) {
		this(level);
        setState(block.defaultBlockState());
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
