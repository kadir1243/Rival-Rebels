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
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EntityDebris extends EntityInanimate {
    public static final EntityDataAccessor<Optional<BlockState>> STATE = SynchedEntityData.defineId(EntityDebris.class, EntityDataSerializers.OPTIONAL_BLOCK_STATE);
    public BlockState state;
    public CompoundTag	tileEntityData;

	public EntityDebris(EntityType<? extends EntityDebris> type, Level w) {
		super(type, w);
	}

    public EntityDebris(Level w) {
        super(RREntities.DEBRIS, w);
    }

	public EntityDebris(Level w, int x, int y, int z)
	{
		this(w);
        setState(w.getBlockState(new BlockPos(x, y, z)));
		w.setBlockAndUpdate(new BlockPos(x, y, z), Blocks.AIR.defaultBlockState());
		setPos(x + 0.5f, y + 0.5f, z + 0.5f);
        xo = x + 0.5f;
        yo = y + 0.5f;
        zo = z + 0.5f;
	}

    public EntityDebris(Level w, double x, double y, double z, double mx, double my, double mz, Block b)
	{
		this(w);
        setState(b.defaultBlockState());
		setPos(x, y, z);
        xo = x;
        yo = y;
        zo = z;
        setDeltaMovement(mx, my, mz);
	}

    public BlockState getState() {
        return entityData.get(STATE).orElse(null);
    }

    public void setState(BlockState state) {
        entityData.set(STATE, Optional.ofNullable(state));
    }

    @Override
	public void tick() {
		xo = getX();
		yo = getY();
		zo = getZ();
		++tickCount;
        setDeltaMovement(getDeltaMovement().subtract(0, -0.04, 0));
        setDeltaMovement(getDeltaMovement().scale(0.98));
        Vec3 add = getDeltaMovement().add(position());
        setPosRaw(add.x(), add.y(), add.z());

		if (!level().isClientSide && level().getBlockState(this.blockPosition()).canOcclude()) die(xo, yo, zo);
	}

	public void die(double x, double y, double z) {
		kill();
        BlockPos pos = new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z));
        level().setBlockAndUpdate(pos, getState());
		if (tileEntityData != null && getState().hasBlockEntity()) {
			BlockEntity blockEntity = level().getBlockEntity(pos);
			if (blockEntity != null) {
				CompoundTag nbt = blockEntity.saveWithoutMetadata(level().registryAccess());
                for (String s : tileEntityData.getAllKeys()) {
                    Tag nbtbase = tileEntityData.get(s);
                    if (!s.equals("x") && !s.equals("y") && !s.equals("z")) {
                        nbt.put(s, nbtbase.copy());
                    }
                }
				blockEntity.loadWithComponents(nbt, level().registryAccess());
				blockEntity.setChanged();
			}
		}
	}

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        if (getState() != null) {
            nbt.put("Block", NbtUtils.writeBlockState(getState()));
        }
		nbt.putInt("Age", tickCount);
		if (tileEntityData != null) nbt.put("TileEntityData", tileEntityData);
	}

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        if (nbt.contains("Block")) {
            setState(NbtUtils.readBlockState(level().holderLookup(Registries.BLOCK), nbt.getCompound("Block")));
        }
        tickCount = nbt.getInt("Age");
		if (nbt.contains("TileEntityData", Tag.TAG_COMPOUND)) tileEntityData = nbt.getCompound("TileEntityData");
	}

    @Override
    public void fillCrashReportCategory(CrashReportCategory section) {
        super.fillCrashReportCategory(section);
        if (getState() != null) {
            section.setDetail("Immitating BlockState", this.getState().toString());
        }
	}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(STATE, Optional.empty());
    }
}
