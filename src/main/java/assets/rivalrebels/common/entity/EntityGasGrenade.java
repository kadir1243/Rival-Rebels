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
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityGasGrenade extends EntityInanimate
{
	private boolean	inGround	= false;

	public Entity	shootingEntity;
	private int		ticksInAir	= 0;

	public EntityGasGrenade(World par1World)
	{
		super(par1World);
		setSize(0.5F, 0.5F);
	}

	public EntityGasGrenade(World par1World, double par2, double par4, double par6)
	{
		super(par1World);
		setSize(0.5F, 0.5F);
		setPosition(par2, par4, par6);
	}

	public EntityGasGrenade(World par1World, double x, double y,double z, double mx, double my, double mz)
	{
		super(par1World);
		setSize(0.5F, 0.5F);
		setPosition(x,y,z);
		setAnglesMotion(mx, my, mz);
	}

	public void setAnglesMotion(double mx, double my, double mz)
	{
		motionX = mx;
		motionY = my;
		motionZ = mz;
		prevRotationYaw = rotationYaw = (float) (Math.atan2(mx, mz) * 180.0D / Math.PI);
		prevRotationPitch = rotationPitch = (float) (Math.atan2(my, MathHelper.sqrt(mx * mx + mz * mz)) * 180.0D / Math.PI);
	}

	public EntityGasGrenade(World par1World, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving, float par4, float par5)
	{
		super(par1World);
		shootingEntity = par2EntityLiving;

		posY = par2EntityLiving.posY + par2EntityLiving.getEyeHeight() - 0.10000000149011612D;
		double var6 = par3EntityLiving.posX - par2EntityLiving.posX;
		double var8 = par3EntityLiving.posY + par3EntityLiving.getEyeHeight() - 0.699999988079071D - posY;
		double var10 = par3EntityLiving.posZ - par2EntityLiving.posZ;
		double var12 = MathHelper.sqrt(var6 * var6 + var10 * var10);

		if (var12 >= 1.0E-7D)
		{
			float var14 = (float) (Math.atan2(var10, var6) * 180.0D / Math.PI) - 90.0F;
			float var15 = (float) (-(Math.atan2(var8, var12) * 180.0D / Math.PI));
			double var16 = var6 / var12;
			double var18 = var10 / var12;
			setLocationAndAngles(par2EntityLiving.posX + var16, posY, par2EntityLiving.posZ + var18, var14, var15);
			float var20 = (float) var12 * 0.2F;
			setArrowHeading(var6, var8 + var20, var10, par4, par5);
		}
	}

	public EntityGasGrenade(World par1World, EntityPlayer player, float par3)
	{
		super(par1World);
		shootingEntity = player;

		setSize(0.5F, 0.5F);
		setLocationAndAngles(player.posX, player.posY + player.getEyeHeight(), player.posZ, player.rotationYaw, player.rotationPitch);
		posX -= (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		posY -= 0.10000000149011612D;
		posZ -= (MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		setPosition(posX, posY, posZ);
		motionX = (-MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionZ = (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionY = (-MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI));
		setArrowHeading(motionX, motionY, motionZ, par3 * 1.5F, 1.0F);
	}

	/**
	 * Uses the provided coordinates as a heading and determines the velocity from it with the set force and random variance. Args: x, y, z, force, forceVariation
	 */
	public void setArrowHeading(double par1, double par3, double par5, float par7, float par8)
	{
		float var9 = MathHelper.sqrt(par1 * par1 + par3 * par3 + par5 * par5);
		par1 /= var9;
		par3 /= var9;
		par5 /= var9;
		par1 += rand.nextGaussian() * 0.007499999832361937D * par8;
		par3 += rand.nextGaussian() * 0.007499999832361937D * par8;
		par5 += rand.nextGaussian() * 0.007499999832361937D * par8;
		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
		motionX = par1;
		motionY = par3;
		motionZ = par5;
		float var10 = MathHelper.sqrt(par1 * par1 + par5 * par5);
		prevRotationYaw = rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
		prevRotationPitch = rotationPitch = (float) (Math.atan2(par3, var10) * 180.0D / Math.PI);
	}

    @Override
	@SideOnly(Side.CLIENT)
    public void setVelocity(double par1, double par3, double par5)
	{
		motionX = par1;
		motionY = par3;
		motionZ = par5;

		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
		{
			float var7 = MathHelper.sqrt(par1 * par1 + par5 * par5);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float) (Math.atan2(par3, var7) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch;
			prevRotationYaw = rotationYaw;
			setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
		{
			float var1 = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float) (Math.atan2(motionY, var1) * 180.0D / Math.PI);
		}
		++ticksInAir;
		Vec3d var17 = new Vec3d(posX, posY, posZ);
		Vec3d var3 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
		RayTraceResult var4 = world.rayTraceBlocks(var17, var3, false, true, false);
		var17 = new Vec3d(posX, posY, posZ);
		var3 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);

		if (var4 != null)
		{
			var3 = var4.hitVec;
		}

		Entity var5 = null;
		List<Entity> var6 = world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(motionX, motionY, motionZ).grow(1.0D, 1.0D, 1.0D));
		double var7 = 0.0D;

		if (!world.isRemote)
		{
            for (Entity var10 : var6) {
                if (var10.canBeCollidedWith() && (var10 != shootingEntity || ticksInAir >= 5)) {
                    AxisAlignedBB var12 = var10.getEntityBoundingBox().grow(0.3f, 0.3f, 0.3f);
                    RayTraceResult var13 = var12.calculateIntercept(var17, var3);

                    if (var13 != null) {
                        double var14 = var17.distanceTo(var13.hitVec);

                        if (var14 < var7 || var7 == 0.0D) {
                            var5 = var10;
                            var7 = var14;
                        }
                    }
                }
            }
		}

		if (var5 != null)
		{
			var4 = new RayTraceResult(var5);
		}

		float var20;

		if (var4 != null)
		{
			pop();
			setDead();
		}

		posX += motionX;
		posY += motionY;
		posZ += motionZ;
		var20 = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);

		for (rotationPitch = (float) (Math.atan2(motionY, var20) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
		{
        }

		while (rotationPitch - prevRotationPitch >= 180.0F)
		{
			prevRotationPitch += 360.0F;
		}

		while (rotationYaw - prevRotationYaw < -180.0F)
		{
			prevRotationYaw -= 360.0F;
		}

		while (rotationYaw - prevRotationYaw >= 180.0F)
		{
			prevRotationYaw += 360.0F;
		}

		rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
		rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
		rotationPitch = rotationPitch + 90;
		if (rotationPitch <= -270)
		{
			rotationPitch = 90;
		}
		float var23 = 0.9999F;

		if (isInWater())
		{
			for (int var26 = 0; var26 < 4; ++var26)
			{
				float var27 = 0.25F;
				world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * var27, posY - motionY * var27, posZ - motionZ * var27, motionX, motionY, motionZ);
			}

			var23 = 0.8F;
		}

		motionX *= var23;
		motionY *= var23;
		motionZ *= var23;
		motionY -= 0.03f;
		setPosition(posX, posY, posZ);
        doBlockCollisions();
	}

	/**
	 * If returns false, the item will not inflict any damage against entities.
	 */
	@Override
	public boolean canBeAttackedWithItem()
	{
		return false;
	}

    private void pop()
	{
		RivalRebelsSoundPlayer.playSound(this, 9, 1);
		for (int x = -4; x <= 4; x++)
		{
			for (int y = -4; y <= 4; y++)
			{
				for (int z = -4; z <= 4; z++)
				{
					if (world.getBlockState(new BlockPos((int) posX + x, (int) posY + y, (int) posZ + z)) == Blocks.AIR)
					{
						world.setBlockState(new BlockPos((int) posX + x, (int) posY + y, (int) posZ + z), RivalRebels.toxicgas.getDefaultState());
					}
				}
			}
		}
	}
}
