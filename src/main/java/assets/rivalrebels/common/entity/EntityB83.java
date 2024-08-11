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
import assets.rivalrebels.common.explosion.NuclearExplosion;
import assets.rivalrebels.common.util.ModBlockTags;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityB83 extends ThrowableProjectile
{
	public int	ticksInAir	= 0;

	public EntityB83(EntityType<? extends EntityB83> entityType, Level level) {
		super(entityType, level);
	}

	public EntityB83(Level level, double x, double y, double z, float yaw, float pitch) {
		this(RREntities.B83, level);
		moveTo(x, y, z, yaw, pitch);
        setDeltaMovement(-(-Mth.sin(yaw / 180.0F * Mth.PI) * Mth.cos(pitch / 180.0F * Mth.PI)),
            (-Mth.sin(pitch / 180.0F * Mth.PI)),
            (Mth.cos(yaw / 180.0F * Mth.PI) * Mth.cos(pitch / 180.0F * Mth.PI)));
    }

	public EntityB83(Level level, double x, double y, double z, float yaw, float pitch, float strength)
	{
        this(RREntities.B83, level);
		moveTo(x, y, z, yaw, pitch);
		setDeltaMovement(-(-Mth.sin(yaw / 180.0F * Mth.PI) * Mth.cos(pitch / 180.0F * Mth.PI)) * strength,
            (-Mth.sin(pitch / 180.0F * Mth.PI)) * strength,
            (Mth.cos(yaw / 180.0F * Mth.PI) * Mth.cos(pitch / 180.0F * Mth.PI)) * strength);
	}
	public EntityB83(Level level, double x, double y,double z, double mx, double my, double mz)
	{
        this(RREntities.B83, level);
		setPos(x,y,z);
		setAnglesMotion(mx, my, mz);
	}

	public void setAnglesMotion(double mx, double my, double mz)
	{
        setDeltaMovement(mx, my, mz);
        setYRot(yRotO = (float) (Math.atan2(mx, mz) * Mth.RAD_TO_DEG));
		setXRot(xRotO = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * Mth.RAD_TO_DEG));
	}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

	@Override
	public void tick() {
		if (ticksInAir == - 100 || getY() < level().getMinBuildHeight() || getY() > level().getMaxBuildHeight()) explode();
		++this.ticksInAir;

		HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);

        if (hitResult.getType() != HitResult.Type.MISS) {
            this.onHit(hitResult);
        }

        Vec3 add = this.position().add(this.getDeltaMovement());
        this.setPosRaw(add.x(), add.y(), add.z());
		if (this.isPassenger()) {
            this.updateRotation();
		}
		float var17 = 0.9f;

        setDeltaMovement(getDeltaMovement().scale(var17));
        applyGravity();
        this.reapplyPosition();
	}

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        BlockState state = level().getBlockState(blockHitResult.getBlockPos());
        if (state.is(BlockTags.LEAVES) || state.is(BlockTags.FLOWERS) || state.is(BlockTags.CROPS) || state.is(Blocks.CAKE) || state.getBlock().getExplosionResistance() < 1 || state.is(BlockTags.WOOL) || state.is(Blocks.SNOW_BLOCK) || state.is(ModBlockTags.GLASS_BLOCKS) || state.is(ModBlockTags.GLASS_PANES) || state.is(BlockTags.SAND) || state.is(BlockTags.SNOW) || state.ignitedByLava() || state.canBeReplaced() || state.getFluidState().is(FluidTags.WATER) || state.is(Blocks.SPONGE) || state.is(BlockTags.ICE)) {
            level().setBlockAndUpdate(blockHitResult.getBlockPos(), Blocks.AIR.defaultBlockState());
            return;
        }
		explode();
	}

    @Override
    protected double getDefaultGravity() {
		return 0.1F;
	}

	public void explode()
	{
		new NuclearExplosion(level(), (int) getX(), (int) getY(), (int) getZ(), RRConfig.SERVER.getB83Strength());
		level().addFreshEntity(new EntityTsarBlast(level(), getX(), getY(), getZ(), RRConfig.SERVER.getB83Strength() * 1.333333333f).setTime());
		this.kill();
	}
}
