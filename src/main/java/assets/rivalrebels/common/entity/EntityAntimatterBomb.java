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

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.explosion.AntimatterBomb;
import assets.rivalrebels.common.util.ModBlockTags;
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

public class EntityAntimatterBomb extends ThrowableProjectile {
	public int	ticksInAir	= 0;
	public int aoc = 0;
	public boolean hasTrollface;

	public EntityAntimatterBomb(EntityType<? extends EntityAntimatterBomb> entityType, Level level) {
		super(entityType, level);
	}

    public EntityAntimatterBomb(Level level) {
        this(RREntities.ANTIMATTER_BOMB, level);
    }

	public EntityAntimatterBomb(Level level, double x, double y, double z, float yaw, float pitch, int charges, boolean troll)
	{
		this(level);
		moveTo(x, y, z, yaw, pitch);
		yRotO = yaw;
		xRotO = pitch;
		aoc = charges;
		hasTrollface = troll;
		if (!RRConfig.SERVER.isNukedrop() && !level.isClientSide())
		{
			explode();
		}
	}

	public EntityAntimatterBomb(Level level, float px, float py, float pz, float dx, float dy, float dz)
	{
		this(level);
		setPos(px, py, pz);
        setDeltaMovement(dx, dy, dz);
		aoc = 5;
		hasTrollface = true;
	}
	public EntityAntimatterBomb(Level level, double x, double y,double z, double mx, double my, double mz, int charges)
	{
		this(level);
		setPos(x,y,z);
		aoc = charges;
		setAnglesMotion(mx, my, mz);
	}

    public void setAnglesMotion(double mx, double my, double mz) {
        setDeltaMovement(mx, my, mz);
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
		double var16 = this.getDeltaMovement().horizontalDistance();
		this.setYRot((float) (Math.atan2(getDeltaMovement().x(), getDeltaMovement().z()) * Mth.RAD_TO_DEG));

		for (this.setXRot((float) (Math.atan2(getDeltaMovement().y(), var16) * Mth.RAD_TO_DEG)); this.getXRot() - this.xRotO < -180.0F; this.xRotO -= 360.0F)
		{
        }

		while (this.getXRot() - this.xRotO >= 180.0F)
		{
			this.xRotO += 360.0F;
		}

		while (this.getYRot() - this.yRotO < -180.0F)
		{
			this.yRotO -= 360.0F;
		}

		while (this.getYRot() - this.yRotO >= 180.0F)
		{
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
	}

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        BlockState state = level().getBlockState(blockHitResult.getBlockPos());
        MapColor color = state.getMapColor(level(), blockHitResult.getBlockPos());
        if (state.is(RRBlocks.jump) || state.is(BlockTags.ICE)) {
            setDeltaMovement(getDeltaMovement().x(), Math.max(-getDeltaMovement().y(), 0.2F), getDeltaMovement().z());
            return;
        }
        if (hasTrollface && random.nextInt(10)!=0)
        {
            setDeltaMovement(getDeltaMovement().x(), Math.max(-getDeltaMovement().y(), 0.2F), getDeltaMovement().z());
            return;
        }
        else if (!hasTrollface &&
            (state.is(BlockTags.LEAVES) ||
                color == MapColor.COLOR_GREEN ||
                color == MapColor.DIRT ||
                state.is(BlockTags.FLOWERS) ||
                state.is(BlockTags.CROPS) ||
                state.is(Blocks.CAKE) ||
                state.getBlock().getExplosionResistance() < 1 ||
                state.is(BlockTags.WOOL) ||
                state.is(BlockTags.SNOW) ||
                state.is(ModBlockTags.GLASS_BLOCKS) ||
                state.is(BlockTags.SAND) ||
                state.is(Blocks.SNOW_BLOCK) ||
                state.ignitedByLava() ||
                state.canBeReplaced() ||
                state.getFluidState().is(FluidTags.WATER) ||
                state.is(Blocks.SPONGE) ||
                state.is(BlockTags.ICE))) {
            level().setBlockAndUpdate(blockHitResult.getBlockPos(), Blocks.AIR.defaultBlockState());
            return;
        }
        explode();
	}

    public void explode()
	{
		if (!level().isClientSide())
		{
			AntimatterBomb tsar = new AntimatterBomb((int)getX(), (int)getY(), (int)getZ(), level(), (int) ((RRConfig.SERVER.getTsarBombaStrength() + (aoc * aoc)) * 0.8f));
			EntityAntimatterBombBlast tsarblast = new EntityAntimatterBombBlast(level(), (int)getX(), (int)getY(), (int)getZ(), tsar, RRConfig.SERVER.getTsarBombaStrength() + (aoc * aoc));
			level().addFreshEntity(tsarblast);
			this.kill();
		}
	}
}
