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
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import java.util.List;
import java.util.Optional;

public class EntityHackB83 extends ThrowableProjectile
{
	public int	ticksInAir	= 0;
	double mmx = 0;
	double mmy = 0;
	double mmz = 0;
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
        setDeltaMovement(-(-Mth.sin(yaw / 180.0F * (float) Math.PI) * Mth.cos(pitch / 180.0F * (float) Math.PI)),
            (Mth.cos(yaw / 180.0F * (float) Math.PI) * Mth.cos(pitch / 180.0F * (float) Math.PI)),
            (-Mth.sin(pitch / 180.0F * (float) Math.PI)));
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
		setYRot(yRotO = (float) (Math.atan2(mx, mz) * 180.0D / Math.PI));
		setXRot(xRotO = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * 180.0D / Math.PI));
	}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
	public void tick()
	{
		if (ticksInAir == - 100 || getY() < 0 || getY() > 256) explode();
		++this.ticksInAir;
		if (!straight && !level().isClientSide())
		{
			mmx += level().random.nextGaussian()*0.4;
			mmy += level().random.nextGaussian()*0.4;
			mmz += level().random.nextGaussian()*0.4;
			double dist = 1/Math.sqrt(mmx*mmx + mmy*mmy + mmz*mmz);
			mmx *= dist;
			mmy *= dist;
			mmz *= dist;
			if (ticksInAir > 35) {
				setDeltaMovement(getDeltaMovement().add(mmx * 0.2f,
                    mmy * 0.2f,
                    mmz * 0.2f));
			} else {
                setDeltaMovement(getDeltaMovement().add(
                    mmx*0.2f,
                    Math.abs(mmy)*0.2f,
                    mmz*0.2f
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

		Vec3 var15 = position();
		Vec3 var2 = position().add(getDeltaMovement());
		HitResult var3 = this.level().clip(new ClipContext(var15, var2, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
		var15 = position();
		var2 = position().add(getDeltaMovement());

		if (var3 != null)
		{
			var2 = var3.getLocation();
		}

		if (!this.level().isClientSide && this.ticksInAir > 3)
		{
			Entity var4 = null;
			List<Entity> var5 = this.level().getEntities(this, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;

            for (Entity var9 : var5) {
                if (var9.canBeCollidedWith()) {
                    float var10 = 0.3F;
                    AABB var11 = var9.getBoundingBox().inflate(var10, var10, var10);
                    Optional<Vec3> var12 = var11.clip(var15, var2);

                    if (var12.isPresent()) {
                        double var13 = var15.distanceTo(var12.get());

                        if (var13 < var6 || var6 == 0.0D) {
                            var4 = var9;
                            var6 = var13;
                        }
                    }
                }
            }

			if (var4 != null)
			{
				var3 = new EntityHitResult(var4);
			}
		}

		if (var3 != null)
		{
			this.onHit(var3);
		}

        setPosRaw(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
		float var16 = Mth.sqrt((float) (this.getDeltaMovement().x() * this.getDeltaMovement().x() + this.getDeltaMovement().z() * this.getDeltaMovement().z()));
		this.setYRot((float) (Math.atan2(getDeltaMovement().x(), getDeltaMovement().z()) * 180.0D / Math.PI));

		for (this.setXRot((float) (Math.atan2(getDeltaMovement().y(), var16) * 180.0D / Math.PI)); this.getXRot() - this.xRotO < -180.0F; this.xRotO -= 360.0F)
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

		this.setXRot(this.xRotO + (this.getXRot() - this.xRotO) * 0.2F);
		this.setYRot(this.yRotO + (this.getYRot() - this.yRotO) * 0.2F);
		float var17 = 0.9f;
		if (!straight && !level().isClientSide())
		{
            setDeltaMovement(getDeltaMovement().scale(var17));
		}
		this.setPos(this.getX(), this.getY(), this.getZ());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag par1NBTTagCompound)
	{

	}

	@Override
	public void readAdditionalSaveData(CompoundTag par1NBTTagCompound)
	{

	}

	@Override
	public boolean shouldRenderAtSqrDistance(double distance)
	{
		return true;
	}

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        BlockState state = level().getBlockState(blockHitResult.getBlockPos());
        MapColor color = state.getMapColor(level(), blockHitResult.getBlockPos());
        if (state.is(BlockTags.LEAVES) || color == MapColor.COLOR_GREEN || color == MapColor.DIRT || state.is(BlockTags.FLOWERS) || state.is(BlockTags.CROPS) || state.is(Blocks.CAKE) || state.getBlock().getExplosionResistance() < 1 || state.is(BlockTags.WOOL) || state.is(Blocks.SNOW_BLOCK) || state.is(ConventionalBlockTags.GLASS_BLOCKS) || state.is(BlockTags.SAND) || state.is(BlockTags.SNOW) || state.ignitedByLava() || state.canBeReplaced() || state.getFluidState().is(FluidTags.WATER) || state.is(Blocks.SPONGE) || state.is(BlockTags.ICE))
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
