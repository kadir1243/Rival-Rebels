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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.explosion.NuclearExplosion;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SpongeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import java.util.List;
import java.util.Optional;

public class EntityB83 extends ThrowableProjectile
{
	public int	ticksInAir	= 0;

	public EntityB83(EntityType<? extends EntityB83> entityType, Level par1World) {
		super(entityType, par1World);
	}

	public EntityB83(Level par1World, double x, double y, double z, float yaw, float pitch) {
		this(RREntities.B83, par1World);
		moveTo(x, y, z, yaw, pitch);
        setDeltaMovement(-(-Mth.sin(yaw / 180.0F * (float) Math.PI) * Mth.cos(pitch / 180.0F * (float) Math.PI)),
            (Mth.cos(yaw / 180.0F * (float) Math.PI) * Mth.cos(pitch / 180.0F * (float) Math.PI)),
            (-Mth.sin(pitch / 180.0F * (float) Math.PI)));
    }

	public EntityB83(Level par1World, double x, double y, double z, float yaw, float pitch, float strength)
	{
        this(RREntities.B83, par1World);
		moveTo(x, y, z, yaw, pitch);
		setDeltaMovement(-(-Mth.sin(yaw / 180.0F * (float) Math.PI) * Mth.cos(pitch / 180.0F * (float) Math.PI)) * strength,
		(Mth.cos(yaw / 180.0F * (float) Math.PI) * Mth.cos(pitch / 180.0F * (float) Math.PI)) * strength,
		(-Mth.sin(pitch / 180.0F * (float) Math.PI)) * strength);
	}
	public EntityB83(Level par1World, double x, double y,double z, double mx, double my, double mz)
	{
        this(RREntities.B83, par1World);
		setPos(x,y,z);
		setAnglesMotion(mx, my, mz);
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
	public void tick() {
		if (ticksInAir == - 100 || getY() < 0 || getY() > 256) explode();
		++this.ticksInAir;

		Vec3 var15 = position();
		Vec3 var2 = position().add(this.getDeltaMovement());
		HitResult var3 = (this.level().clip(new ClipContext(var15, var2, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, CollisionContext.empty())));

		if (var3 != null)
		{
			var2 = var3.getLocation();
		}

		if (!this.level().isClientSide)
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

        if (var3 != null) {
            this.onHit(var3);
        }

        Vec3 add = this.position().add(this.getDeltaMovement());
        this.setPosRaw(add.x(), add.y(), add.z());
		if (this.isPassenger())
		{
		double var16 = Math.sqrt(this.getDeltaMovement().x() * this.getDeltaMovement().x() + this.getDeltaMovement().z() * this.getDeltaMovement().z());
		this.setYRot((float) (Math.atan2(getDeltaMovement().x(), getDeltaMovement().z()) * 180.0D / Math.PI));

		for (this.setXRot((float) (Math.atan2(getDeltaMovement().y(), var16) * 180.0D / Math.PI)); this.getXRot() - this.xRotO < -180.0F; this.xRotO -= 360.0F)
		{
			;
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
		}
		float var17 = 0.9f;
		float var18 = (float) this.getGravity();

        setDeltaMovement(getDeltaMovement().scale(var17));
        setDeltaMovement(getDeltaMovement().subtract(0, var18, 0));
		this.setPos(this.getX(), this.getY(), this.getZ());
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double distance)
	{
		return true;
	}

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        BlockState state = level().getBlockState(blockHitResult.getBlockPos());
        Block b = state.getBlock();
        if (state.is(BlockTags.LEAVES) || state.is(BlockTags.FLOWERS) || state.is(BlockTags.CROPS) || state.is(Blocks.CAKE) || state.getBlock().getExplosionResistance() < 1 || state.is(BlockTags.WOOL) || state.is(Blocks.SNOW_BLOCK) || state.is(ConventionalBlockTags.GLASS_BLOCKS) || state.is(ConventionalBlockTags.GLASS_BLOCKS) || state.is(BlockTags.SAND) || b instanceof SnowLayerBlock || state.ignitedByLava() || state.canBeReplaced() || state.getFluidState().is(FluidTags.WATER) || b instanceof SpongeBlock || state.is(BlockTags.ICE)) {
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
		new NuclearExplosion(level(), (int) getX(), (int) getY(), (int) getZ(), RivalRebels.b83Strength);
		level().addFreshEntity(new EntityTsarBlast(level(), getX(), getY(), getZ(), RivalRebels.b83Strength * 1.333333333f).setTime());
		this.kill();
	}
}
