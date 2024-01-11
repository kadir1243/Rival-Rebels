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
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class EntityHackB83 extends EntityThrowable
{
	public int	ticksInAir	= 0;
	double mmx = 0;
	double mmy = 0;
	double mmz = 0;
	boolean straight;

	public EntityHackB83(World par1World)
	{
		super(par1World);
		this.setSize(0.5F, 0.5F);
	}

	public EntityHackB83(World par1World, double x, double y, double z, float yaw, float pitch, boolean flystraight)
	{
		super(par1World);
		setSize(0.5F, 0.5F);
		straight = flystraight;
		setLocationAndAngles(x, y, z, yaw, pitch);
		motionX = -(-MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionZ = (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionY = (-MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI));
	}
	public EntityHackB83(World par1World, double x, double y,double z, double mx, double my, double mz, boolean flystraight)
	{
		super(par1World);
		setSize(0.5F, 0.5F);
		setPosition(x,y,z);
		setAnglesMotion(mx, my, mz);
		straight = flystraight;
	}

	public void setAnglesMotion(double mx, double my, double mz)
	{
		motionX = mx;
		motionY = my;
		motionZ = mz;
		prevRotationYaw = rotationYaw = (float) (Math.atan2(mx, mz) * 180.0D / Math.PI);
		prevRotationPitch = rotationPitch = (float) (Math.atan2(my, MathHelper.sqrt(mx * mx + mz * mz)) * 180.0D / Math.PI);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;
		if (ticksInAir == - 100 || posY < 0 || posY > 256) explode();
		++this.ticksInAir;
		if (!straight && !world.isRemote)
		{
			mmx += world.rand.nextGaussian()*0.4;
			mmy += world.rand.nextGaussian()*0.4;
			mmz += world.rand.nextGaussian()*0.4;
			double dist = 1/Math.sqrt(mmx*mmx + mmy*mmy + mmz*mmz);
			mmx *= dist;
			mmy *= dist;
			mmz *= dist;
			if (ticksInAir > 35)
			{
				motionX += mmx*0.2f;
				motionY += mmy*0.2f;
				motionZ += mmz*0.2f;
			}
			else
			{
				motionX += mmx*0.2f;
				motionY += Math.abs(mmy)*0.2f;
				motionZ += mmz*0.2f;
			}
		}

		if (world.isRemote && !inWater)
		{
			world.spawnEntity(new EntityPropulsionFX(world, posX, posY, posZ, -motionX, -motionY, -motionZ));
			world.spawnEntity(new EntityPropulsionFX(world, posX, posY, posZ, -motionX*0.8f, -motionY*0.8f, -motionZ*0.8f));
			world.spawnEntity(new EntityPropulsionFX(world, posX, posY, posZ, -motionX*0.6f, -motionY*0.6f, -motionZ*0.6f));
			world.spawnEntity(new EntityPropulsionFX(world, posX, posY, posZ, -motionX*0.4f, -motionY*0.4f, -motionZ*0.4f));
			world.spawnEntity(new EntityPropulsionFX(world, posX, posY, posZ, -motionX*0.2f, -motionY*0.2f, -motionZ*0.2f));
		}

		Vec3d var15 = new Vec3d(this.posX, this.posY, this.posZ);
		Vec3d var2 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		RayTraceResult var3 = this.world.rayTraceBlocks(var15, var2);
		var15 = new Vec3d(this.posX, this.posY, this.posZ);
		var2 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		if (var3 != null)
		{
			var2 = var3.hitVec;
		}

		if (!this.world.isRemote && this.ticksInAir > 3)
		{
			Entity var4 = null;
			List<Entity> var5 = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;

            for (Entity var9 : var5) {
                if (var9.canBeCollidedWith()) {
                    float var10 = 0.3F;
                    AxisAlignedBB var11 = var9.getEntityBoundingBox().grow(var10, var10, var10);
                    RayTraceResult var12 = var11.calculateIntercept(var15, var2);

                    if (var12 != null) {
                        double var13 = var15.distanceTo(var12.hitVec);

                        if (var13 < var6 || var6 == 0.0D) {
                            var4 = var9;
                            var6 = var13;
                        }
                    }
                }
            }

			if (var4 != null)
			{
				var3 = new RayTraceResult(var4);
			}
		}

		if (var3 != null)
		{
			this.onImpact(var3);
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		float var16 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

		for (this.rotationPitch = (float) (Math.atan2(this.motionY, var16) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
		{
			;
		}

		while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
		{
			this.prevRotationPitch += 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw < -180.0F)
		{
			this.prevRotationYaw -= 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
		{
			this.prevRotationYaw += 360.0F;
		}

		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
		float var17 = 0.9f;
		if (!straight && !world.isRemote)
		{
			this.motionX *= var17;
			this.motionY *= var17;
			this.motionZ *= var17;
		}
		this.setPosition(this.posX, this.posY, this.posZ);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{

	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{

	}

	@Override
	public boolean isInRangeToRenderDist(double par1)
	{
		return true;
	}

	@Override
	protected void onImpact(RayTraceResult var1)
	{
		if (var1.entityHit == null)
		{
			Material m = world.getBlockState(var1.getBlockPos()).getMaterial();
			if (m == Material.LEAVES || m == Material.CLAY || m == Material.GROUND || m == Material.PLANTS || m == Material.CAKE || m == Material.CIRCUITS || m == Material.CACTUS || m == Material.CLOTH || m == Material.CRAFTED_SNOW || m == Material.GLASS || m == Material.GRASS || m == Material.SAND || m == Material.SNOW || m == Material.WOOD || m == Material.VINE || m == Material.WATER || m == Material.SPONGE || m == Material.ICE)
			{
				world.setBlockToAir(var1.getBlockPos());
				return;
			}
		}
		explode();
	}

	public void explode()
	{
		new NuclearExplosion(world, (int) posX, (int) posY, (int) posZ, RivalRebels.b83Strength, false);
		world.spawnEntity(new EntityTsarBlast(world, posX, posY, posZ, RivalRebels.b83Strength * 1.333333333f).setTime());
		this.setDead();
	}
}
