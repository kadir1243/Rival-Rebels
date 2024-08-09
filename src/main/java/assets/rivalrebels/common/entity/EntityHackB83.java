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
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityHackB83 extends ThrowableProjectile
{
	public int	ticksInAir	= 0;
    private Vec3 mmPos = Vec3.ZERO;
	boolean straight;

    public EntityHackB83(EntityType<? extends EntityHackB83> entityType, Level world) {
        super(entityType, world);
    }

	public EntityHackB83(Level par1World)
	{
		this(RREntities.HACK_B83, par1World);
	}

	public EntityHackB83(Level par1World, double x, double y, double z, float yaw, float pitch, boolean flystraight)
	{
		this(par1World);
		straight = flystraight;
		moveTo(x, y, z, yaw, pitch);
        setDeltaMovement(-(-Mth.sin(yaw / 180.0F * Mth.PI) * Mth.cos(pitch / 180.0F * Mth.PI)),
            (Mth.cos(yaw / 180.0F * Mth.PI) * Mth.cos(pitch / 180.0F * Mth.PI)),
            (-Mth.sin(pitch / 180.0F * Mth.PI)));
    }
	public EntityHackB83(Level par1World, double x, double y,double z, double mx, double my, double mz, boolean flystraight)
	{
		this(par1World);
		setPos(x,y,z);
		setAnglesMotion(mx, my, mz);
		straight = flystraight;
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
	public void tick()
	{
		if (ticksInAir == - 100 || getY() < level().getMinBuildHeight() || getY() > level().getMaxBuildHeight()) explode();
		++this.ticksInAir;
		if (!straight && !level().isClientSide())
		{
			mmPos = mmPos.add(level().random.nextGaussian()*0.4,
                level().random.nextGaussian()*0.4,
                level().random.nextGaussian()*0.4);
            mmPos = mmPos.normalize();
			if (ticksInAir > 35) {
				setDeltaMovement(getDeltaMovement().add(mmPos.scale(0.2)));
			} else {
                setDeltaMovement(getDeltaMovement().add(
                    mmPos.x()*0.2f,
                    Mth.abs((float) mmPos.y())*0.2f,
                    mmPos.z()*0.2f
                ));
			}
		}

		if (level().isClientSide && !isInWaterOrBubble())
		{
			level().addFreshEntity(new EntityPropulsionFX(level(), getX(), getY(), getZ(), -getDeltaMovement().x(), -getDeltaMovement().y(), -getDeltaMovement().z()));
			level().addFreshEntity(new EntityPropulsionFX(level(), getX(), getY(), getZ(), -getDeltaMovement().x()*0.8f, -getDeltaMovement().y()*0.8f, -getDeltaMovement().z()*0.8f));
			level().addFreshEntity(new EntityPropulsionFX(level(), getX(), getY(), getZ(), -getDeltaMovement().x()*0.6f, -getDeltaMovement().y()*0.6f, -getDeltaMovement().z()*0.6f));
			level().addFreshEntity(new EntityPropulsionFX(level(), getX(), getY(), getZ(), -getDeltaMovement().x()*0.4f, -getDeltaMovement().y()*0.4f, -getDeltaMovement().z()*0.4f));
			level().addFreshEntity(new EntityPropulsionFX(level(), getX(), getY(), getZ(), -getDeltaMovement().x()*0.2f, -getDeltaMovement().y()*0.2f, -getDeltaMovement().z()*0.2f));
		}

		HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);

		if (hitResult.getType() != HitResult.Type.MISS) {
			this.onHit(hitResult);
		}

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());

        this.updateRotation();
		float var17 = 0.9f;
		if (!straight && !level().isClientSide())
		{
            setDeltaMovement(getDeltaMovement().scale(var17));
		}
        this.reapplyPosition();
	}

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        BlockState state = level().getBlockState(blockHitResult.getBlockPos());
        MapColor color = state.getMapColor(level(), blockHitResult.getBlockPos());
        if (state.is(BlockTags.LEAVES) || color == MapColor.COLOR_GREEN || color == MapColor.DIRT || state.is(BlockTags.FLOWERS) || state.is(BlockTags.CROPS) || state.is(Blocks.CAKE) || state.getBlock().getExplosionResistance() < 1 || state.is(BlockTags.WOOL) || state.is(Blocks.SNOW_BLOCK) || state.is(ModBlockTags.GLASS_BLOCKS) || state.is(BlockTags.SAND) || state.is(BlockTags.SNOW) || state.ignitedByLava() || state.canBeReplaced() || state.getFluidState().is(FluidTags.WATER) || state.is(Blocks.SPONGE) || state.is(BlockTags.ICE))
        {
            level().setBlockAndUpdate(blockHitResult.getBlockPos(), Blocks.AIR.defaultBlockState());
            return;
        }
        explode();
	}

	public void explode()
	{
		new NuclearExplosion(level(), (int) getX(), (int) getY(), (int) getZ(), RRConfig.SERVER.getB83Strength(), false);
		level().addFreshEntity(new EntityTsarBlast(level(), getX(), getY(), getZ(), RRConfig.SERVER.getB83Strength() * 1.333333333f).setTime());
		this.kill();
	}
}
