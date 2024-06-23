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
import net.minecraft.nbt.NbtCompound;
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

public class EntityHackB83 extends ThrownEntity
{
	public int	ticksInAir	= 0;
	double mmx = 0;
	double mmy = 0;
	double mmz = 0;
	boolean straight;

    public EntityHackB83(EntityType<? extends EntityHackB83> entityType, World world) {
        super(entityType, world);
    }

	public EntityHackB83(World par1World)
	{
		this(RREntities.HACK_B83, par1World);
	}

	public EntityHackB83(World par1World, double x, double y, double z, float yaw, float pitch, boolean flystraight)
	{
		this(par1World);
		straight = flystraight;
		refreshPositionAndAngles(x, y, z, yaw, pitch);
        setVelocity(-(-MathHelper.sin(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI)),
            (MathHelper.cos(yaw / 180.0F * (float) Math.PI) * MathHelper.cos(pitch / 180.0F * (float) Math.PI)),
            (-MathHelper.sin(pitch / 180.0F * (float) Math.PI)));
    }
	public EntityHackB83(World par1World, double x, double y,double z, double mx, double my, double mz, boolean flystraight)
	{
		this(par1World);
		setPosition(x,y,z);
		setAnglesMotion(mx, my, mz);
		straight = flystraight;
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
	public void tick()
	{
		if (ticksInAir == - 100 || getY() < 0 || getY() > 256) explode();
		++this.ticksInAir;
		if (!straight && !getWorld().isClient)
		{
			mmx += getWorld().random.nextGaussian()*0.4;
			mmy += getWorld().random.nextGaussian()*0.4;
			mmz += getWorld().random.nextGaussian()*0.4;
			double dist = 1/Math.sqrt(mmx*mmx + mmy*mmy + mmz*mmz);
			mmx *= dist;
			mmy *= dist;
			mmz *= dist;
			if (ticksInAir > 35) {
				setVelocity(getVelocity().add(mmx * 0.2f,
                    mmy * 0.2f,
                    mmz * 0.2f));
			} else {
                setVelocity(getVelocity().add(
                    mmx*0.2f,
                    Math.abs(mmy)*0.2f,
                    mmz*0.2f
                ));
			}
		}

		if (getWorld().isClient && !isInsideWaterOrBubbleColumn())
		{
			getWorld().spawnEntity(new EntityPropulsionFX(getWorld(), getX(), getY(), getZ(), -getVelocity().getX(), -getVelocity().getY(), -getVelocity().getZ()));
			getWorld().spawnEntity(new EntityPropulsionFX(getWorld(), getX(), getY(), getZ(), -getVelocity().getX()*0.8f, -getVelocity().getY()*0.8f, -getVelocity().getZ()*0.8f));
			getWorld().spawnEntity(new EntityPropulsionFX(getWorld(), getX(), getY(), getZ(), -getVelocity().getX()*0.6f, -getVelocity().getY()*0.6f, -getVelocity().getZ()*0.6f));
			getWorld().spawnEntity(new EntityPropulsionFX(getWorld(), getX(), getY(), getZ(), -getVelocity().getX()*0.4f, -getVelocity().getY()*0.4f, -getVelocity().getZ()*0.4f));
			getWorld().spawnEntity(new EntityPropulsionFX(getWorld(), getX(), getY(), getZ(), -getVelocity().getX()*0.2f, -getVelocity().getY()*0.2f, -getVelocity().getZ()*0.2f));
		}

		Vec3d var15 = getPos();
		Vec3d var2 = getPos().add(getVelocity());
		HitResult var3 = this.getWorld().raycast(new RaycastContext(var15, var2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
		var15 = getPos();
		var2 = getPos().add(getVelocity());

		if (var3 != null)
		{
			var2 = var3.getPos();
		}

		if (!this.getWorld().isClient && this.ticksInAir > 3)
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

		if (var3 != null)
		{
			this.onCollision(var3);
		}

        setPos(getX() + getVelocity().getX(), getY() + getVelocity().getY(), getZ() + getVelocity().getZ());
		float var16 = MathHelper.sqrt((float) (this.getVelocity().getX() * this.getVelocity().getX() + this.getVelocity().getZ() * this.getVelocity().getZ()));
		this.setYaw((float) (Math.atan2(getVelocity().getX(), getVelocity().getZ()) * 180.0D / Math.PI));

		for (this.setPitch((float) (Math.atan2(getVelocity().getY(), var16) * 180.0D / Math.PI)); this.getPitch() - this.prevPitch < -180.0F; this.prevPitch -= 360.0F)
		{
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
		float var17 = 0.9f;
		if (!straight && !getWorld().isClient)
		{
            setVelocity(getVelocity().multiply(var17));
		}
		this.setPosition(this.getX(), this.getY(), this.getZ());
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound par1NBTTagCompound)
	{

	}

	@Override
	public void readCustomDataFromNbt(NbtCompound par1NBTTagCompound)
	{

	}

	@Override
	public boolean shouldRender(double distance)
	{
		return true;
	}

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        BlockState state = getWorld().getBlockState(blockHitResult.getBlockPos());
        MapColor color = state.getMapColor(getWorld(), blockHitResult.getBlockPos());
        Block b = state.getBlock();
        if (state.isIn(BlockTags.LEAVES) || color == MapColor.GREEN || color == MapColor.DIRT_BROWN || state.isIn(BlockTags.FLOWERS) || state.isIn(BlockTags.CROPS) || state.isOf(Blocks.CAKE) || state.getBlock().getBlastResistance() < 1 || state.isIn(BlockTags.WOOL) || state.isOf(Blocks.SNOW_BLOCK) || state.isIn(ConventionalBlockTags.GLASS_BLOCKS) || state.isIn(BlockTags.SAND) || b instanceof SnowBlock || state.isBurnable() || state.isReplaceable() || state.getFluidState().isIn(FluidTags.WATER) || b instanceof SpongeBlock || state.isIn(BlockTags.ICE))
        {
            getWorld().setBlockState(blockHitResult.getBlockPos(), Blocks.AIR.getDefaultState());
            return;
        }
        explode();
	}

	public void explode()
	{
		new NuclearExplosion(getWorld(), (int) getX(), (int) getY(), (int) getZ(), RivalRebels.b83Strength, false);
		getWorld().spawnEntity(new EntityTsarBlast(getWorld(), getX(), getY(), getZ(), RivalRebels.b83Strength * 1.333333333f).setTime());
		this.kill();
	}
}
