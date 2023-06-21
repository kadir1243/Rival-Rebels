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

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.explosion.Explosion;

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
		yOffset = 0.0F;
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
		yOffset = 0.0F;
		motionX = (-MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionZ = (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionY = (-MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI));
		setThrowableHeading(motionX, motionY, motionZ, 0.5f, 1f);
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
		yOffset = 0.0F;
		motionX = (-MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionZ = (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionY = (-MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI));
		setThrowableHeading(motionX, motionY, motionZ, 0.5f, 1f);
	}

	public EntitySeekB83(World w, double x, double y, double z, double mx, double my, double mz)
	{
		super(w);
		setSize(0.5F, 0.5F);
		setPosition(x+mx*16, y+my*16, z+mz*16);
		yOffset = 0.0F;
		fins = false;
		setThrowableHeading(mx, my, mz, 0.5f, 0.1f);
	}

	@Override
	public void setThrowableHeading(double mx, double my, double mz, float speed, float randomness)
	{
		float f2 = MathHelper.sqrt_double(mx * mx + my * my + mz * mz);
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
		prevRotationPitch = rotationPitch = (float) (Math.atan2(my, MathHelper.sqrt_double(mx * mx + mz * mz)) * 180.0D / Math.PI);
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
			rotation = worldObj.rand.nextInt(360);
			slide = worldObj.rand.nextInt(21) - 10;
			for (int i = 0; i < 10; i++)
			{
				worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, posX - motionX * 2, posY - motionY * 2, posZ - motionZ * 2, -motionX + (worldObj.rand.nextFloat() - 0.5f) * 0.1f, -motionY + (worldObj.rand.nextFloat() - 0.5) * 0.1f, -motionZ + (worldObj.rand.nextFloat() - 0.5f) * 0.1f);
			}
		}
		rotation += (int) slide;
		slide *= 0.9;

		if (ticksExisted >= 800)
		{
			explode(null);
		}
		// worldObj.spawnEntityInWorld(new EntityLightningLink(worldObj, posX, posY, posZ, rotationYaw, rotationPitch, 100));

		if (worldObj.isRemote && ticksExisted >= 5 && !inWater && ticksExisted <= 100)
		{
			worldObj.spawnEntityInWorld(new EntityPropulsionFX(worldObj, posX, posY, posZ, -motionX * 0.5, -motionY * 0.5 - 0.1, -motionZ * 0.5));
		}
		Vec3 vec31 = new Vec3(posX, posY, posZ);
		Vec3 vec3 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
		MovingObjectPosition mop = worldObj.rayTraceBlocks(vec31, vec3, false, true, false);
		if (!worldObj.isRemote)
		{
			vec31 = new Vec3(posX, posY, posZ);
			if (mop != null) vec3 = new Vec3(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
			else vec3 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);

			List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			double d0 = Double.MAX_VALUE;
			for (int i = 0; i < list.size(); ++i)
			{
				Entity entity = list.get(i);
				if ((entity.canBeCollidedWith() && ticksExisted >= 7 && entity != thrower) || entity instanceof EntityHackB83 || entity instanceof EntityB83)
				{
					MovingObjectPosition mop1 = entity.getEntityBoundingBox().expand(0.5f, 0.5f, 0.5f).calculateIntercept(vec31, vec3);
					if (mop1 != null)
					{
						double d1 = vec31.squareDistanceTo(mop1.hitVec);
						if (d1 < d0)
						{
							mop = new MovingObjectPosition(entity, mop1.hitVec);
							d0 = d1;
						}
					}
				}
			}
		}
		if (mop != null) explode(mop);

		Iterator<Entity> iter = worldObj.loadedEntityList.iterator();
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
		float var16 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
        rotationPitch = (float) (Math.atan2(motionY, var16) * 180.0D / Math.PI);
        while (rotationPitch - prevRotationPitch < -180.0F) {
            prevRotationPitch -= 360.0F;
        }
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
				worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * 0.25F, posY - motionY * 0.25F, posZ - motionZ * 0.25F, motionX, motionY, motionZ);
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

	public void explode(MovingObjectPosition mop)
	{
		if (mop != null)
		{
			if (mop.entityHit != null)
			{
				if (mop.entityHit instanceof EntityHackB83)
				{
					mop.entityHit.setDead();
					worldObj.setBlockState(getPosition(), RivalRebels.plasmaexplosion.getDefaultState());
					setDead();
				}
				else if (mop.entityHit instanceof EntityPlayer player)
				{
                    ItemStack[] armorSlots = player.inventory.armorInventory;
					if (armorSlots[0] != null) armorSlots[0].damageItem(48, player);
					if (armorSlots[1] != null) armorSlots[1].damageItem(48, player);
					if (armorSlots[2] != null) armorSlots[2].damageItem(48, player);
					if (armorSlots[3] != null) armorSlots[3].damageItem(48, player);
					RivalRebelsSoundPlayer.playSound(this, 23, soundfile, 5F, 0.3F);
					new Explosion(worldObj, posX, posY, posZ, RivalRebels.rpgExplodeSize, false, false, RivalRebelsDamageSource.rocket);
					setDead();
				}
				else
				{
					new Explosion(worldObj, posX, posY, posZ, RivalRebels.rpgExplodeSize, false, false, RivalRebelsDamageSource.rocket);
					setDead();
				}
			}
			else
			{
				Block block = worldObj.getBlockState(mop.getBlockPos()).getBlock();
				if (block == Blocks.glass || block == Blocks.glass_pane || block == Blocks.stained_glass || block == Blocks.stained_glass_pane)
				{
					worldObj.setBlockToAir(mop.getBlockPos());
					RivalRebelsSoundPlayer.playSound(this, 4, 0, 5F, 0.3F);
				}
				else
				{
					RivalRebelsSoundPlayer.playSound(this, 23, soundfile, 5F, 0.3F);
					new Explosion(worldObj, posX, posY, posZ, RivalRebels.rpgExplodeSize, false, false, RivalRebelsDamageSource.rocket);
					setDead();
				}
			}
		}
		else
		{
			RivalRebelsSoundPlayer.playSound(this, 23, soundfile, 5F, 0.3F);
			new Explosion(worldObj, posX, posY, posZ, RivalRebels.rpgExplodeSize, false, false, RivalRebelsDamageSource.rocket);
			setDead();
		}
	}


	@Override
	public boolean isInRangeToRenderDist(double par1)
	{
		return true;
	}

	@Override
	public int getBrightnessForRender(float par1)
	{
		return 1000;
	}

	@Override
	public float getBrightness(float par1)
	{
		return 1000F;
	}
}
