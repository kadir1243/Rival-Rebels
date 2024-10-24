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

import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.explosion.TsarBomba;
import io.github.kadir1243.rivalrebels.common.util.ModBlockTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class EntityTsar extends ThrowableProjectile
{
	public int	ticksInAir	= 0;
	public int aoc = 0;
	public boolean hasTrollface;

    public EntityTsar(EntityType<? extends EntityTsar> type, Level world) {
        super(type, world);
    }

	public EntityTsar(Level level) {
		this(RREntities.TSAR.get(), level);
	}

	public EntityTsar(Level level, double x, double y, double z, float yaw, float pitch, int charges, boolean troll)
	{
		this(level);
		moveTo(x, y, z, yaw, pitch);
		this.setYRot(yRotO = yaw);
		this.setXRot(xRotO = pitch);
		aoc = charges;
		hasTrollface = troll;
		if (!RRConfig.SERVER.isNukedrop())
		{
			explode();
		}
	}

	public EntityTsar(Level level, float px, float py, float pz, float f, float g, float h)
	{
		this(level);
		setPos(px, py, pz);
        setDeltaMovement(f, g, h);
		aoc = 5;
		hasTrollface = true;
	}
	public EntityTsar(Level level, double mx, double my, double mz, int charges) {
		this(level);
		aoc = charges;
        if (mx * mx + my * my + mz * mz <0.01) return;
        setYRot(yRotO = (float) (Math.atan2(mx, mz) * Mth.RAD_TO_DEG));
        setXRot(xRotO = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * Mth.RAD_TO_DEG));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

	@Override
	public void tick()
	{
		if (!level().isClientSide())
		{
			if (ticksInAir == - 100) explode();
			++this.ticksInAir;

			HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);

			if (hitResult.getType() != HitResult.Type.MISS) {
				this.onHit(hitResult);
			}
		}

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
		if (getY() < level().getMinBuildHeight()) kill();

		if (this.isPassenger())
		{
		this.setYRot((float) (Math.atan2(getDeltaMovement().x(), getDeltaMovement().z()) * Mth.RAD_TO_DEG));
		float var16 = (float) this.getDeltaMovement().horizontalDistance();

        this.setXRot((float) (Math.atan2(getDeltaMovement().y(), var16) * Mth.RAD_TO_DEG));
        while (this.getXRot() - this.xRotO < -180.0F) {
            this.xRotO -= 360.0F;
        }

        while (this.getXRot() - this.xRotO >= 180.0F) {
			this.xRotO += 360.0F;
		}

		while (this.getYRot() - this.yRotO < -180.0F) {
			this.yRotO -= 360.0F;
		}

		while (this.getYRot() - this.yRotO >= 180.0F) {
			this.yRotO += 360.0F;
		}

		this.setXRot(this.xRotO + (this.getXRot() - this.xRotO) * 0.05F);
		this.setYRot(this.yRotO + (this.getYRot() - this.yRotO) * 0.05F);
		}
		float var17 = 0.98f;

        setDeltaMovement(getDeltaMovement().scale(var17));
        applyGravity();
        this.reapplyPosition();
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("charge", aoc);
		nbt.putBoolean("troll", hasTrollface);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        aoc = nbt.getInt("charge");
		hasTrollface = nbt.getBoolean("troll");
		setYRot(yRotO = nbt.getFloat("rot"));
	}

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        BlockState state = level().getBlockState(blockHitResult.getBlockPos());
        MapColor color = state.getMapColor(level(), blockHitResult.getBlockPos());
        if (state.is(RRBlocks.jump) || state.is(BlockTags.ICE)) {
            setDeltaMovement(getDeltaMovement().x(), Math.max(-getDeltaMovement().y(), 0.2F), getDeltaMovement().z());
        } else if (hasTrollface && random.nextInt(10)!=0) {
            setDeltaMovement(getDeltaMovement().x(), Math.max(-getDeltaMovement().y(), 0.2F), getDeltaMovement().z());
        } else if (!hasTrollface && (state.is(BlockTags.LEAVES) || color == MapColor.COLOR_GREEN || color == MapColor.DIRT || state.is(BlockTags.FLOWERS) || state.is(BlockTags.CROPS) || state.is(Blocks.CAKE) || state.getBlock().getExplosionResistance() < 1 || state.is(BlockTags.WOOL) || state.is(Blocks.SNOW_BLOCK) || state.is(ModBlockTags.GLASS_BLOCKS) || state.is(BlockTags.SAND) || state.is(BlockTags.SNOW) || state.ignitedByLava() || state.canBeReplaced() || state.getFluidState().is(FluidTags.WATER) || state.is(Blocks.SPONGE) || state.is(BlockTags.ICE))) {
            level().setBlockAndUpdate(blockHitResult.getBlockPos(), Blocks.AIR.defaultBlockState());
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);

		explode();
	}

    public void explode()
	{
		if (!level().isClientSide())
		{
			TsarBomba tsar = new TsarBomba((int)getX(), (int)getY(), (int)getZ(), level(), (int) ((RRConfig.SERVER.getTsarBombaStrength() + (aoc * aoc)) * 0.8f));
			EntityTsarBlast tsarblast = new EntityTsarBlast(level(), (int)getX(), (int)getY(), (int)getZ(), tsar, RRConfig.SERVER.getTsarBombaStrength() + (aoc * aoc));
			level().addFreshEntity(tsarblast);
			this.kill();
		}
	}
}
