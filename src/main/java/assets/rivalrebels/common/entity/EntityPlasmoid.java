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
import assets.rivalrebels.common.core.BlackList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class EntityPlasmoid extends EntityInanimate
{
	private Entity thrower;
	public int				rotation	= 45;
	public int				size		= 3;
	public float			slide		= 0;
	boolean gravity = false;

	public EntityPlasmoid(World par1World)
	{
		super(par1World);
		setSize(0.5F, 0.5F);
		size = 3;
	}

	public EntityPlasmoid(World par1World, double par2, double par4, double par6)
	{
		super(par1World);
		setSize(0.5F, 0.5F);
		setPosition(par2, par4, par6);
		size = 3;
	}

	public EntityPlasmoid(World par1World, EntityPlayer thrower, EntityLiving par3EntityLiving, float par4, float par5)
	{
		super(par1World);
		this.thrower = thrower;

		posY = thrower.posY + thrower.getEyeHeight() - 0.10000000149011612D;
		double var6 = par3EntityLiving.posX - thrower.posX;
		double var8 = par3EntityLiving.posY + par3EntityLiving.getEyeHeight() - 0.699999988079071D - posY;
		double var10 = par3EntityLiving.posZ - thrower.posZ;
		double var12 = MathHelper.sqrt(var6 * var6 + var10 * var10);

		if (var12 >= 1.0E-7D)
		{
			float var14 = (float) (Math.atan2(var10, var6) * 180.0D / Math.PI) - 90.0F;
			float var15 = (float) (-(Math.atan2(var8, var12) * 180.0D / Math.PI));
			double var16 = var6 / var12;
			double var18 = var10 / var12;
			setLocationAndAngles(thrower.posX + var16, posY, thrower.posZ + var18, var14, var15);
			float var20 = (float) var12 * 0.2F;
			setAccurateHeading(var6, var8 + var20, var10, par4, par5);
		}
		size = 3;
	}

	public EntityPlasmoid(World par1World, Entity thrower, float par3, boolean drop)
	{
		super(par1World);
		par3 *= (gravity ? 3 : 1);
		gravity = drop;
		this.thrower = thrower;
		setSize(0.5F, 0.5F);
		setLocationAndAngles(thrower.posX, thrower.posY + thrower.getEyeHeight(), thrower.posZ, thrower.rotationYaw, thrower.rotationPitch);
		posX -= (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		posY -= 0.0D;
		posZ -= (MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		setPosition(posX, posY, posZ);
		motionX = (-MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionZ = (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionY = (-MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI));
		setAccurateHeading(motionX, motionY, motionZ, par3 * 1.5F, 1.0F);
	}

	public EntityPlasmoid(World world, double px, double py, double pz, double x, double y, double z, float d)
	{
		super(world);
		setSize(0.5F, 0.5F);
		double f = d/MathHelper.sqrt(x*x+y*y+z*z);
		setPosition(px+x*f, py+y*f, pz+z*f);
		size = 3;
		motionX = x;
		motionY = y;
		motionZ = z;
		float var10 = MathHelper.sqrt(x * x + z * z);
		prevRotationYaw = rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
		prevRotationPitch = rotationPitch = (float) (Math.atan2(y, var10) * 180.0D / Math.PI);
	}

	public void setAccurateHeading(double par1, double par3, double par5, float par7, float par8)
	{
		float var9 = MathHelper.sqrt(par1 * par1 + par3 * par3 + par5 * par5);
		par1 /= var9;
		par3 /= var9;
		par5 /= var9;
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
	public int getBrightnessForRender()
	{
		return 1000;
	}

	@Override
	public float getBrightness()
	{
		return 1000F;
	}

	@Override
	public boolean isInRangeToRenderDist(double par1)
	{
		return true;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if (ticksExisted == 0)
		{
			rotation = world.rand.nextInt(360);
			slide = world.rand.nextInt(21) - 10;
		}
		if (gravity)
		{
			motionY -= 0.05f;
		}
		++ticksExisted;
		rotation += (int) slide;
		slide *= 0.9;
		if (ticksExisted >= RivalRebels.plasmoidDecay * (gravity ? 3 : 1)) explode();

		Vec3d vec31 = new Vec3d(posX, posY, posZ);
		Vec3d vec3 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
		RayTraceResult mop = world.rayTraceBlocks(vec31, vec3, false, true, false);
		vec31 = new Vec3d(posX, posY, posZ);
		if (mop != null) vec3 = mop.hitVec;
		else vec3 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);

		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(motionX, motionY, motionZ).grow(1.0D, 1.0D, 1.0D));
		double d0 = Double.MAX_VALUE;
        for (Entity entity : list) {
            if (entity.canBeCollidedWith() && (entity != thrower || ticksExisted >= 5)) {
                RayTraceResult mop1 = entity.getEntityBoundingBox().grow(0.5f, 0.5f, 0.5f).calculateIntercept(vec31, vec3);
                if (mop1 != null) {
                    double d1 = vec31.squareDistanceTo(mop1.hitVec);
                    if (d1 < d0) {
                        mop = new RayTraceResult(entity, mop1.hitVec);
                        d0 = d1;
                    }
                }
            }
        }
		if (mop != null) explode();

		posX += motionX;
		posY += motionY;
		posZ += motionZ;
		float var16 = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
		for (rotationPitch = (float) (Math.atan2(motionY, var16) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
			;
		while (rotationPitch - prevRotationPitch >= 180.0F)
			prevRotationPitch += 360.0F;
		while (rotationYaw - prevRotationYaw < -180.0F)
			prevRotationYaw -= 360.0F;
		while (rotationYaw - prevRotationYaw >= 180.0F)
			prevRotationYaw += 360.0F;
		rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
		rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
		setPosition(posX, posY, posZ);
	}

	protected void explode()
	{
		if (!world.isRemote)
		{
			setDead();
			IBlockState block = Blocks.STONE.getDefaultState();
			int i = -1;
            BlockPos pos = new BlockPos((int) (posX - motionX * i), (int) (posY - motionY * i), (int) (posZ - motionZ * i));
            while ((block.isOpaqueCube() || BlackList.plasmaExplosion(block.getBlock())) && i < 4)
			{
				++i;
				block = world.getBlockState(pos);
			}
			world.setBlockState(pos, RivalRebels.plasmaexplosion.getDefaultState());
		}
	}

}
