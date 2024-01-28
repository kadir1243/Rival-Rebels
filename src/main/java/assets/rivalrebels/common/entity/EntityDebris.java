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

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;

public class EntityDebris extends EntityInanimate {
    public static final TrackedData<Optional<BlockState>> STATE = DataTracker.registerData(EntityDebris.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_STATE);
    public BlockState state;
    public NbtCompound	tileEntityData;

	public EntityDebris(EntityType<? extends EntityDebris> type, World w) {
		super(type, w);
	}

    public EntityDebris(World w) {
        super(RREntities.DEBRIS, w);
    }

	public EntityDebris(World w, int x, int y, int z)
	{
		this(w);
        setState(w.getBlockState(new BlockPos(x, y, z)));
		w.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState());
		setPosition(x + 0.5f, y + 0.5f, z + 0.5f);
        prevX = x + 0.5f;
        prevY = y + 0.5f;
        prevZ = z + 0.5f;
	}

    @Override
    public double getHeightOffset() {
        return 0.5F;
    }

    public EntityDebris(World w, double x, double y, double z, double mx, double my, double mz, Block b)
	{
		this(w);
        setState(b.getDefaultState());
		setPosition(x, y, z);
        prevX = x;
        prevY = y;
        prevZ = z;
        setVelocity(mx, my, mz);
	}

    public BlockState getState() {
        return dataTracker.get(STATE).orElse(null);
    }

    public void setState(BlockState state) {
        dataTracker.set(STATE, Optional.ofNullable(state));
    }

    @Override
	public void tick() {
		prevX = getX();
		prevY = getY();
		prevZ = getZ();
		++age;
        setVelocity(getVelocity().subtract(0, -0.04, 0));
        setVelocity(getVelocity().multiply(0.98));
        Vec3d add = getVelocity().add(getPos());
        setPos(add.getX(), add.getY(), add.getZ());

		if (!world.isClient && world.getBlockState(this.getBlockPos()).isOpaque()) die(prevX, prevY, prevZ);
	}

	public void die(double x, double y, double z) {
		kill();
        BlockPos pos = new BlockPos(x, y, z);
        world.setBlockState(pos, getState());
		if (tileEntityData != null && getState().hasBlockEntity()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity != null) {
				NbtCompound nbt = blockEntity.createNbt();
                for (String s : tileEntityData.getKeys()) {
                    NbtElement nbtbase = tileEntityData.get(s);
                    if (!s.equals("x") && !s.equals("y") && !s.equals("z")) {
                        nbt.put(s, nbtbase.copy());
                    }
                }
				blockEntity.readNbt(nbt);
				blockEntity.markDirty();
			}
		}
	}

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        if (getState() != null) {
            nbt.put("Block", NbtHelper.fromBlockState(getState()));
        }
		nbt.putInt("Age", age);
		if (tileEntityData != null) nbt.put("TileEntityData", tileEntityData);
	}

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("Block")) {
            setState(NbtHelper.toBlockState(nbt.getCompound("Block")));
        }
        age = nbt.getInt("Age");
		if (nbt.contains("TileEntityData", NbtElement.COMPOUND_TYPE)) tileEntityData = nbt.getCompound("TileEntityData");
	}

    @Override
    public void populateCrashReport(CrashReportSection section) {
        super.populateCrashReport(section);
        if (getState() != null) {
            section.add("Immitating BlockState", this.getState().toString());
        }
	}

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(STATE, Optional.empty());
    }
}
