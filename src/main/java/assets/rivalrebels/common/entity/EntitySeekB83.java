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
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.explosion.Explosion;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class EntitySeekB83 extends EntityInanimate implements IProjectile
{
	private EntityLivingBase	thrower;
	public boolean				fins			= false;
	public int					rotation		= 45;
	public float				slide			= 0;
	private boolean				inwaterprevtick	= false;
	private int					soundfile		= 0;

	public EntitySeekB83(World par1World)
	{
		super(par1World);
		setSize(0.5F, 0.5F);
	}

	public EntitySeekB83(World par1World, double par2, double par4, double par6)
	{
		super(par1World);
		setSize(0.5F, 0.5F);
		setPosition(par2, par4, par6);
	}

	public EntitySeekB83(World par1World, EntityPlayer entity2, float par3)
	{
		super(par1World);
		thrower = entity2;
		fins = false;
		setSize(0.5F, 0.5F);
		setLocationAndAngles(entity2.posX, entity2.posY + entity2.getEyeHeight(), entity2.posZ, entity2.rotationYaw, entity2.rotationPitch);
		posX -= (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		posY -= 0.0D;
		posZ -= (MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		setPosition(posX, posY, posZ);
		motionX = (-MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionZ = (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionY = (-MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI));
		shoot(motionX, motionY, motionZ, 0.5f, 1f);
	}

	public EntitySeekB83(World par1World, EntityPlayer entity2, float par3, float yawdelta)
	{
		super(par1World);
		thrower = entity2;
		fins = false;
		setSize(0.5F, 0.5F);
		setLocationAndAngles(entity2.posX, entity2.posY + entity2.getEyeHeight(), entity2.posZ, entity2.rotationYaw + yawdelta, entity2.rotationPitch);
		posX -= (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		posY -= 0.0D;
		posZ -= (MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		setPosition(posX, posY, posZ);
		motionX = (-MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionZ = (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionY = (-MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI));
		shoot(motionX, motionY, motionZ, 0.5f, 1f);
	}

	public EntitySeekB83(World w, double x, double y, double z, double mx, double my, double mz)
	{
		super(w);
		setSize(0.5F, 0.5F);
		setPosition(x+mx*16, y+my*16, z+mz*16);
		fins = false;
		shoot(mx, my, mz, 0.5f, 0.1f);
	}

	@Override
	public void shoot(double mx, double my, double mz, float speed, float randomness)
	{
		float f2 = MathHelper.sqrt(mx * mx + my * my + mz * mz);
		mx /= f2;
		my /= f2;
		mz /= f2;
		mx += rand.nextGaussian() * 0.0075 * randomness;
		my += rand.nextGaussian() * 0.0075 * randomness;
		mz += rand.nextGaussian() * 0.0075 * randomness;
		mx *= speed;
		my *= speed;
		mz *= speed;
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
		super.onUpdate();

		if (ticksExisted == 0)
		{
			rotation = world.rand.nextInt(360);
			slide = world.rand.nextInt(21) - 10;
			for (int i = 0; i < 10; i++)
			{
				world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, posX - motionX * 2, posY - motionY * 2, posZ - motionZ * 2, -motionX + (world.rand.nextFloat() - 0.5f) * 0.1f, -motionY + (world.rand.nextFloat() - 0.5) * 0.1f, -motionZ + (world.rand.nextFloat() - 0.5f) * 0.1f);
			}
		}
		rotation += (int) slide;
		slide *= 0.9;

		if (ticksExisted >= 800)
		{
			explode(null);
		}
		// world.spawnEntity(new EntityLightningLink(world, posX, posY, posZ, rotationYaw, rotationPitch, 100));

		if (world.isRemote && ticksExisted >= 5 && !inWater && ticksExisted <= 100)
		{
			world.spawnEntity(new EntityPropulsionFX(world, posX, posY, posZ, -motionX * 0.5, -motionY * 0.5 - 0.1, -motionZ * 0.5));
		}
		Vec3d vec31 = new Vec3d(posX, posY, posZ);
		Vec3d vec3 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
		RayTraceResult mop = world.rayTraceBlocks(vec31, vec3, false, true, false);
		if (!world.isRemote)
		{
			vec31 = new Vec3d(posX, posY, posZ);
			if (mop != null) vec3 = mop.hitVec;
			else vec3 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);

			List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(motionX, motionY, motionZ).grow(1.0D, 1.0D, 1.0D));
			double d0 = Double.MAX_VALUE;
            for (Entity entity : list) {
                if ((entity.canBeCollidedWith() && ticksExisted >= 7 && entity != thrower) || entity instanceof EntityHackB83 || entity instanceof EntityB83) {
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
		}
		if (mop != null) explode(mop);

		Iterator<Entity> iter = world.loadedEntityList.iterator();
		double ddx = motionX;
		double ddy = motionY;
		double ddz = motionZ;
		double dist = 1000000;
		while (iter.hasNext())
		{
			Entity e = iter.next();
			if (e instanceof EntityB83 || e instanceof EntityHackB83)
			{
				double dx = e.posX - posX;
				double dy = e.posY - posY;
				double dz = e.posZ - posZ;
				double temp = dx*dx+dy*dy+dz*dz;
				if (temp < dist)
				{
					if (dx*motionX+dy*motionY+dz*motionZ>0f)
					{
						dist = temp;
						temp = Math.sqrt(temp)*0.9f;
						ddx = dx / temp;
						ddy = dy / temp;
						ddz = dz / temp;
					}
				}
			}
		}
		motionX = ddx;
		motionY = ddy;
		motionZ = ddz;

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
		float var17 = 1.1f;
		if (ticksExisted > 25) var17 = 0.9999F;

		if (isInWater())
		{
			for (int var7 = 0; var7 < 4; ++var7)
			{
				world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * 0.25F, posY - motionY * 0.25F, posZ - motionZ * 0.25F, motionX, motionY, motionZ);
			}
			if (!inwaterprevtick)
			{
				RivalRebelsSoundPlayer.playSound(this, 23, 4, 0.5F, 0.5F);
			}
			soundfile = 3;
			var17 = 0.8F;
			inwaterprevtick = true;
		}
		else
		{
			soundfile = 0;
		}

		motionX *= var17;
		motionY *= var17;
		motionZ *= var17;
		if (ticksExisted == 3)
		{
			fins = true;
			rotationPitch += 22.5;
		}
		setPosition(posX, posY, posZ);
		++ticksExisted;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{

	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{

	}

	public void explode(RayTraceResult mop)
	{
		if (mop != null)
		{
			if (mop.entityHit != null)
			{
				if (mop.entityHit instanceof EntityHackB83)
				{
					mop.entityHit.setDead();
					world.setBlockState(getPosition(), RivalRebels.plasmaexplosion.getDefaultState());
					setDead();
				}
				else if (mop.entityHit instanceof EntityPlayer player)
				{
                    for (ItemStack armorSlot : player.inventory.armorInventory) {
                        if (!armorSlot.isEmpty()) {
                            armorSlot.damageItem(48, player);
                        }
                    }
					RivalRebelsSoundPlayer.playSound(this, 23, soundfile, 5F, 0.3F);
					new Explosion(world, posX, posY, posZ, RivalRebels.rpgExplodeSize, false, false, RivalRebelsDamageSource.rocket);
					setDead();
				}
				else
				{
					new Explosion(world, posX, posY, posZ, RivalRebels.rpgExplodeSize, false, false, RivalRebelsDamageSource.rocket);
					setDead();
				}
			}
			else
			{
				Block block = world.getBlockState(mop.getBlockPos()).getBlock();
				if (block == Blocks.GLASS || block == Blocks.GLASS_PANE || block == Blocks.STAINED_GLASS || block == Blocks.STAINED_GLASS_PANE)
				{
					world.setBlockToAir(mop.getBlockPos());
					RivalRebelsSoundPlayer.playSound(this, 4, 0, 5F, 0.3F);
				}
				else
				{
					RivalRebelsSoundPlayer.playSound(this, 23, soundfile, 5F, 0.3F);
					new Explosion(world, posX, posY, posZ, RivalRebels.rpgExplodeSize, false, false, RivalRebelsDamageSource.rocket);
					setDead();
				}
			}
		}
		else
		{
			RivalRebelsSoundPlayer.playSound(this, 23, soundfile, 5F, 0.3F);
			new Explosion(world, posX, posY, posZ, RivalRebels.rpgExplodeSize, false, false, RivalRebelsDamageSource.rocket);
			setDead();
		}
	}


	@Override
	public boolean isInRangeToRenderDist(double par1)
	{
		return true;
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

}
