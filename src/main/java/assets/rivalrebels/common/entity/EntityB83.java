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
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class EntityB83 extends ThrownEntity
{
	public int	ticksInAir	= 0;

	public EntityB83(EntityType<? extends EntityB83> entityType, World par1World) {
		super(entityType, par1World);
	}

	public EntityB83(World par1World, double x, double y, double z, float yaw, float pitch) {
		this(RREntities.B83, par1World);
		refreshPositionAndAngles(x, y, z, yaw, pitch);
        setVelocity(-(-MathHelper.sin(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI)),
            (MathHelper.cos(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI)),
            (-MathHelper.sin(pitch / 180.0F * (float) Math.PI)));
    }

	public EntityB83(World par1World, double x, double y, double z, float yaw, float pitch, float strength)
	{
        this(RREntities.B83, par1World);
		refreshPositionAndAngles(x, y, z, yaw, pitch);
		setVelocity(-(-MathHelper.sin(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI)) * strength,
		(MathHelper.cos(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI)) * strength,
		(-MathHelper.sin(pitch / 180.0F * (float) Math.PI)) * strength);
	}
	public EntityB83(World par1World, double x, double y,double z, double mx, double my, double mz)
	{
        this(RREntities.B83, par1World);
		setPosition(x,y,z);
		setAnglesMotion(mx, my, mz);
	}

	public void setAnglesMotion(double mx, double my, double mz)
	{
        setVelocity(mx, my, mz);
        setYaw(prevYaw = (float) (Math.atan2(mx, mz) * 180.0D / Math.PI));
		setPitch(prevPitch = (float) (Math.atan2(my, Math.sqrt(mx * mx + mz * mz)) * 180.0D / Math.PI));
	}

    @Override
    protected void initDataTracker() {
    }

	@Override
	public void tick() {
		if (ticksInAir == - 100 || getY() < 0 || getY() > 256) explode();
		++this.ticksInAir;

		Vec3d var15 = getPos();
		Vec3d var2 = getPos().add(this.getVelocity());
		HitResult var3 = (this.getWorld().raycast(new RaycastContext(var15, var2, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.ANY, ShapeContext.absent())));

		if (var3 != null)
		{
			var2 = var3.getPos();
		}

		if (!this.getWorld().isClient)
		{
			Entity var4 = null;
			List<Entity> var5 = this.getWorld().getOtherEntities(this, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;

            for (Entity var9 : var5) {
                if (var9.isCollidable()) {
                    float var10 = 0.3F;
                    Box var11 = var9.getBoundingBox().expand(var10, var10, var10);
                    Optional<Vec3d> var12 = var11.raycast(var15, var2);

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
            this.onCollision(var3);
        }

        Vec3d add = this.getPos().add(this.getVelocity());
        this.setPos(add.getX(), add.getY(), add.getZ());
		if (this.hasVehicle())
		{
		double var16 = Math.sqrt(this.getVelocity().getX() * this.getVelocity().getX() + this.getVelocity().getZ() * this.getVelocity().getZ());
		this.setYaw((float) (Math.atan2(getVelocity().getX(), getVelocity().getZ()) * 180.0D / Math.PI));

		for (this.setPitch((float) (Math.atan2(getVelocity().getY(), var16) * 180.0D / Math.PI)); this.getPitch() - this.prevPitch < -180.0F; this.prevPitch -= 360.0F)
		{
			;
		}

		while (this.getPitch() - this.prevPitch >= 180.0F)
		{
			this.prevPitch += 360.0F;
		}

		while (this.getYaw() - this.prevYaw < -180.0F)
		{
			this.prevYaw -= 360.0F;
		}

		while (this.getYaw() - this.prevYaw >= 180.0F)
		{
			this.prevYaw += 360.0F;
		}

		this.setPitch(this.prevPitch + (this.getPitch() - this.prevPitch) * 0.2F);
		this.setYaw(this.prevYaw + (this.getYaw() - this.prevYaw) * 0.2F);
		}
		float var17 = 0.9f;
		float var18 = this.getGravity();

        setVelocity(getVelocity().multiply(var17));
        setVelocity(getVelocity().subtract(0, var18, 0));
		this.setPosition(this.getX(), this.getY(), this.getZ());
	}

	@Override
	public boolean shouldRender(double distance)
	{
		return true;
	}

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        BlockState state = getWorld().getBlockState(blockHitResult.getBlockPos());
        Block b = state.getBlock();
        if (state.isIn(BlockTags.LEAVES) || state.isIn(BlockTags.FLOWERS) || state.isIn(BlockTags.CROPS) || state.isOf(Blocks.CAKE) || state.getBlock().getBlastResistance() < 1 || state.isIn(BlockTags.WOOL) || state.isOf(Blocks.SNOW_BLOCK) || state.isIn(ConventionalBlockTags.GLASS_BLOCKS) || state.isIn(ConventionalBlockTags.GLASS_BLOCKS) || state.isIn(BlockTags.SAND) || b instanceof SnowBlock || state.isBurnable() || state.isReplaceable() || state.getFluidState().isIn(FluidTags.WATER) || b instanceof SpongeBlock || state.isIn(BlockTags.ICE)) {
            getWorld().setBlockState(blockHitResult.getBlockPos(), Blocks.AIR.getDefaultState());
            return;
        }
		explode();
	}

	@Override
	protected float getGravity()
	{
		return 0.1F;
	}

	public void explode()
	{
		new NuclearExplosion(getWorld(), (int) getX(), (int) getY(), (int) getZ(), RivalRebels.b83Strength);
		getWorld().spawnEntity(new EntityTsarBlast(getWorld(), getX(), getY(), getZ(), RivalRebels.b83Strength * 1.333333333f).setTime());
		this.kill();
	}
}
