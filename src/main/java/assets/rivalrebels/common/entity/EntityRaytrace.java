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
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class EntityRaytrace extends EntityInanimate
{
	public Entity	shootingEntity;
	private int		ticksInAir	= 0;
	private float	range		= 0;
	private float	c;

	public EntityRaytrace(World par1World)
	{
		super(par1World);
		setSize(0.5F, 0.5F);
	}

	public EntityRaytrace(World par1World, double x, double y,double z, double mx, double my, double mz)
	{
		super(par1World);
		setSize(0.5F, 0.5F);
		setPosition(x,y,z);
		setAnglesMotion(mx, my, mz);
		c = 1.0f;
		range = MathHelper.sqrt(mx*mx+my*my+mz*mz);
	}

	public void setAnglesMotion(double mx, double my, double mz)
	{
		motionX = mx;
		motionY = my;
		motionZ = mz;
		prevRotationYaw = rotationYaw = (float) (Math.atan2(mx, mz) * 180.0D / Math.PI);
		prevRotationPitch = rotationPitch = (float) (Math.atan2(my, MathHelper.sqrt(mx * mx + mz * mz)) * 180.0D / Math.PI);
	}

	public EntityRaytrace(World par1World, Entity player, float distance, float randomness, float chance, boolean shift)
	{
		super(par1World);
		c = chance;
		range = distance;
		shootingEntity = player;
		setSize(0.5F, 0.5F);
		setLocationAndAngles(player.posX, player.posY + player.getEyeHeight(), player.posZ, player.rotationYaw, player.rotationPitch);
		setPosition(posX, posY, posZ);
		motionX = (-MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionZ = (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionY = (-MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI));
		if (shift)
		{
			posX -= (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
			posZ -= (MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
			posX += motionX;
			posY += motionY;
			posZ += motionZ;
		}
		else
		{
			posY-=0.05f;
			posX -= (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.3F);
			posZ -= (MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.3F);
		}
		setArrowHeading(motionX, motionY, motionZ, range, randomness);
	}

    public void setArrowHeading(double par1, double par3, double par5, float par7, float par8)
	{
		float var9 = MathHelper.sqrt(par1 * par1 + par3 * par3 + par5 * par5);
		par1 /= var9;
		par3 /= var9;
		par5 /= var9;
		par1 += world.rand.nextGaussian() * par8;
		par3 += world.rand.nextGaussian() * par8;
		par5 += world.rand.nextGaussian() * par8;
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

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		Vec3d vec31 = new Vec3d(posX, posY, posZ);
		Vec3d vec3 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
		RayTraceResult MOP = world.rayTraceBlocks(vec31, vec3, false, true, false);
		vec31 = new Vec3d(posX, posY, posZ);
		if (MOP != null) vec3 = MOP.hitVec;
		else vec3 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);

		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(motionX, motionY, motionZ).grow(1.0D, 30.0D, 1.0D));
		double d0 = Double.MAX_VALUE;
        for (Entity entity : list) {
            if ((entity.canBeCollidedWith() || entity instanceof EntityRhodes) && entity != shootingEntity) {
                RayTraceResult mop1 = entity.getEntityBoundingBox().grow(0.5f, 0.5f, 0.5f).calculateIntercept(vec31, vec3);
                if (mop1 != null) {
                    double d1 = vec31.squareDistanceTo(mop1.hitVec);
                    if (d1 < d0) {
                        MOP = new RayTraceResult(entity, mop1.hitVec);
                        d0 = d1;
                    }
                }
            }
        }
		if (MOP != null)
		{
			if (MOP.entityHit == null)
			{
				if (!world.isRemote) world.spawnEntity(new EntityLightningLink(world, this, getDistance(MOP.hitVec.x, MOP.hitVec.y, MOP.hitVec.z)));
				// world.spawnEntity(new EntityNuclearBlast(world, MOP.blockX, MOP.getBlockPos().getY(), MOP.getBlockPos().getZ(), 5, false));
				Block BlockHit = world.getBlockState(MOP.getBlockPos()).getBlock();
				float r = world.rand.nextFloat();
				if (BlockHit == RivalRebels.camo1 || BlockHit == RivalRebels.camo2 || BlockHit == RivalRebels.camo3)
				{
					if (r * 10 <= c)
					{
						if (!world.isRemote) world.setBlockToAir(MOP.getBlockPos());
						for (int i = 0; i < 4; i++)
						{
							world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, MOP.getBlockPos().getX(), MOP.getBlockPos().getY() - 1 + i * 0.5, MOP.getBlockPos().getZ(), (world.rand.nextFloat() - 0.5F) * 0.1, world.rand.nextFloat() * 0.05, (world.rand.nextFloat() - 0.5F) * 0.1);
						}
					}
				}
				else if (BlockHit == RivalRebels.reactive)
				{
					if (r * 15 <= c)
					{
						if (!world.isRemote) world.setBlockToAir(MOP.getBlockPos());
						for (int i = 0; i < 4; i++)
						{
							world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, MOP.getBlockPos().getX(), MOP.getBlockPos().getY() - 1 + i * 0.5, MOP.getBlockPos().getZ(), (world.rand.nextFloat() - 0.5F) * 0.1, world.rand.nextFloat() * 0.05, (world.rand.nextFloat() - 0.5F) * 0.1);
						}
					}
				}
				else if (!BlackList.tesla(BlockHit) && r <= c)
				{
					if (!world.isRemote) world.setBlockToAir(MOP.getBlockPos());
					for (int i = 0; i < 4; i++)
					{
						world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, MOP.getBlockPos().getX(), MOP.getBlockPos().getY() - 1 + i * 0.5, MOP.getBlockPos().getZ(), (world.rand.nextFloat() - 0.5F) * 0.1, world.rand.nextFloat() * 0.05, (world.rand.nextFloat() - 0.5F) * 0.1);
					}
				}
			}
			else
			{
				if (!world.isRemote) world.spawnEntity(new EntityLightningLink(world, this, getDistance(MOP.entityHit)));
				if (MOP.entityHit instanceof EntityPlayer)
				{
					EntityPlayer entityPlayerHit = (EntityPlayer) MOP.entityHit;
					NonNullList<ItemStack> armorSlots = entityPlayerHit.inventory.armorInventory;
					int i = world.rand.nextInt(4);
					if (!armorSlots.get(i).isEmpty())
					{
						armorSlots.get(i).damageItem(14, entityPlayerHit);
						entityPlayerHit.attackEntityFrom(RivalRebelsDamageSource.electricity, 1);
					}
					else
					{
						entityPlayerHit.attackEntityFrom(RivalRebelsDamageSource.electricity, (RivalRebels.teslaDecay / ((int) MOP.entityHit.getDistance(this) + 1) / (i + 1)));
					}
				}
				else if (MOP.entityHit instanceof EntityB2Spirit)
				{
					MOP.entityHit.attackEntityFrom(RivalRebelsDamageSource.electricity, (RivalRebels.teslaDecay / 1.5f) / ((int) MOP.entityHit.getDistance(this) + 1));
				}
				else
				{
					MOP.entityHit.attackEntityFrom(RivalRebelsDamageSource.electricity, RivalRebels.teslaDecay / ((int) MOP.entityHit.getDistance(this) + 1));
				}
			}
		}
		else
		{
			if (!world.isRemote) world.spawnEntity(new EntityLightningLink(world, this, range));
		}
		setDead();
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
	}
}
