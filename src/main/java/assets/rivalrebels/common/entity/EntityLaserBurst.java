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
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class EntityLaserBurst extends EntityInanimate
{
	private EntityPlayer	shooter;

	public EntityLaserBurst(World par1World)
	{
		super(par1World);
		setSize(0.5F, 0.5F);
	}

	public EntityLaserBurst(World par1World, EntityPlayer player)
	{
		super(par1World);
		shooter = player;
		setSize(0.5F, 0.5F);
		setLocationAndAngles(player.posX, player.posY + player.getEyeHeight(), player.posZ, player.rotationYaw, player.rotationPitch);
		posX -= (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.2F);
		posY -= 0.12D;
		posZ -= (MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.2F);
		setPosition(posX, posY, posZ);
		motionX = (-MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionZ = (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionY = (-MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI));
		setAccurateHeading(motionX, motionY, motionZ, 4F, 0.075f);
	}

	public EntityLaserBurst(World par1World, EntityPlayer player, boolean accurate)
	{
		super(par1World);
		shooter = player;
		setSize(0.5F, 0.5F);
		setLocationAndAngles(player.posX, player.posY + player.getEyeHeight(), player.posZ, player.rotationYaw, player.rotationPitch);
		posX -= (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.2F);
		posY -= 0.12D;
		posZ -= (MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.2F);
		setPosition(posX, posY, posZ);
		motionX = (-MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionZ = (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI));
		motionY = (-MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI));
		setAccurateHeading(motionX, motionY, motionZ, 4F * (float)rand.nextDouble() + 1.0F, accurate?0.001F:0.075F);
	}
	public EntityLaserBurst(World par1World, double x, double y,double z, double mx, double my, double mz)
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

	public EntityLaserBurst(World par1World, double x, double y, double z, double mx, double my, double mz, EntityPlayer player)
	{
		super(par1World);
		shooter = player;
		setSize(0.5F, 0.5F);
		setPosition(x, y, z);
		motionX = mx;
		motionZ = mz;
		motionY = my;
	}

	public void setAccurateHeading(double par1, double par3, double par5, float par7, float par8)
	{
		float var9 = MathHelper.sqrt(par1 * par1 + par3 * par3 + par5 * par5);
		par1 /= var9;
		par3 /= var9;
		par5 /= var9;
		par1 += rand.nextGaussian() * par8;
		par3 += rand.nextGaussian() * par8;
		par5 += rand.nextGaussian() * par8;
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

		++ticksExisted;
		if (ticksExisted > 60) setDead();

		Vec3d var15 = new Vec3d(posX, posY, posZ);
		Vec3d var2 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
		RayTraceResult var3 = world.rayTraceBlocks(var15, var2);
		var15 = new Vec3d(posX, posY, posZ);
		var2 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);

		if (var3 != null)
		{
			var2 = var3.hitVec;
		}

		if (!world.isRemote)
		{
			Entity var4 = null;
			List<Entity> var5 = world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(motionX, motionY, motionZ).grow(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;

            for (Entity var9 : var5) {
                if (var9.canBeCollidedWith() && var9 != shooter) {
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
			if (var3.entityHit == null)
			{
				Block blockID = world.getBlockState(var3.getBlockPos()).getBlock();
				if (blockID == Blocks.TNT)
				{
					if (!world.isRemote)
					{
						EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, (var3.getBlockPos().getX() + 0.5F), (var3.getBlockPos().getY() + 0.5F), (var3.getBlockPos().getZ() + 0.5F), shooter);
						entitytntprimed.setFuse(world.rand.nextInt(entitytntprimed.getFuse() / 4) + entitytntprimed.getFuse() / 8);
						world.spawnEntity(entitytntprimed);
						world.setBlockToAir(var3.getBlockPos());
					}
				}
				if (blockID == RivalRebels.remotecharge)
				{
					RivalRebels.remotecharge.onBlockExploded(world, var3.getBlockPos(), null);
				}
				if (blockID == RivalRebels.timedbomb)
				{
					RivalRebels.timedbomb.onBlockExploded(world, var3.getBlockPos(), null);
				}
				setDead();
			}
			else
			{
				if (var3.entityHit instanceof EntityPlayer player && shooter != var3.entityHit) {
                    NonNullList<ItemStack> armorSlots = player.inventory.armorInventory;
					int i = world.rand.nextInt(4);
					if (!armorSlots.get(i).isEmpty() && !world.isRemote)
					{
						armorSlots.get(i).damageItem(24, player);
					}
					player.attackEntityFrom(RivalRebelsDamageSource.laserburst, 16);
					if (player.getHealth() < 3 && player.isEntityAlive())
					{
						player.attackEntityFrom(RivalRebelsDamageSource.laserburst, 2000000);
						player.deathTime = 0;
						world.spawnEntity(new EntityGore(world, var3.entityHit, 0, 0));
						world.spawnEntity(new EntityGore(world, var3.entityHit, 1, 0));
						world.spawnEntity(new EntityGore(world, var3.entityHit, 2, 0));
						world.spawnEntity(new EntityGore(world, var3.entityHit, 2, 0));
						world.spawnEntity(new EntityGore(world, var3.entityHit, 3, 0));
						world.spawnEntity(new EntityGore(world, var3.entityHit, 3, 0));
					}
					setDead();
				}
				else if ((var3.entityHit instanceof EntityLivingBase
						&& !(var3.entityHit instanceof EntityAnimal)
						&& !(var3.entityHit instanceof EntityBat)
						&& !(var3.entityHit instanceof EntityVillager)
						&& !(var3.entityHit instanceof EntitySquid)))
				{
					EntityLivingBase entity = (EntityLivingBase) var3.entityHit;
					entity.attackEntityFrom(RivalRebelsDamageSource.laserburst, 6);
					if (entity.getHealth() < 3)
					{
						int legs = -1;
						int arms = -1;
						int mobs = -1;
						entity.setDead();
						RivalRebelsSoundPlayer.playSound(this, 2, 1, 4);
						if (entity instanceof EntityZombie && !(entity instanceof EntityPigZombie))
						{
							legs = 2;
							arms = 2;
							mobs = 1;
						}
						else if (entity instanceof EntityPigZombie)
						{
							legs = 2;
							arms = 2;
							mobs = 2;
						}
						else if (entity instanceof EntitySkeleton)
						{
							legs = 2;
							arms = 2;
							mobs = 3;
						}
						else if (entity instanceof EntityEnderman)
						{
							legs = 2;
							arms = 2;
							mobs = 4;
						}
						else if (entity instanceof EntityCreeper)
						{
							legs = 4;
							arms = 0;
							mobs = 5;
						}
						else if (entity instanceof EntitySlime && !(entity instanceof EntityMagmaCube))
						{
							legs = 0;
							arms = 0;
							mobs = 6;
						}
						else if (entity instanceof EntityMagmaCube)
						{
							legs = 0;
							arms = 0;
							mobs = 7;
						}
						else if (entity instanceof EntitySpider && !(entity instanceof EntityCaveSpider))
						{
							legs = 8;
							arms = 0;
							mobs = 8;
						}
						else if (entity instanceof EntityCaveSpider)
						{
							legs = 8;
							arms = 0;
							mobs = 9;
						}
						else if (entity instanceof EntityGhast)
						{
							legs = 9;
							arms = 0;
							mobs = 10;
						}
						else
						{
							legs = (int) (entity.getEntityBoundingBox().getAverageEdgeLength() * 2);
							arms = (int) (entity.getEntityBoundingBox().getAverageEdgeLength() * 2);
							mobs = 11;
						}
						world.spawnEntity(new EntityGore(world, var3.entityHit, 0, mobs));
						world.spawnEntity(new EntityGore(world, var3.entityHit, 1, mobs));
						for (int i = 0; i < arms; i++)
							world.spawnEntity(new EntityGore(world, var3.entityHit, 2, mobs));
						for (int i = 0; i < legs; i++)
							world.spawnEntity(new EntityGore(world, var3.entityHit, 3, mobs));
					}
					setDead();
				}
				else if((var3.entityHit instanceof EntityRhodesHead
					  || var3.entityHit instanceof EntityRhodesLeftLowerArm
				      || var3.entityHit instanceof EntityRhodesLeftLowerLeg
				      || var3.entityHit instanceof EntityRhodesLeftUpperArm
				      || var3.entityHit instanceof EntityRhodesLeftUpperLeg
				      || var3.entityHit instanceof EntityRhodesRightLowerArm
				      || var3.entityHit instanceof EntityRhodesRightLowerLeg
				      || var3.entityHit instanceof EntityRhodesRightUpperArm
				      || var3.entityHit instanceof EntityRhodesRightUpperLeg
				      || var3.entityHit instanceof EntityRhodesTorso))
				{
					var3.entityHit.attackEntityFrom(RivalRebelsDamageSource.laserburst, 6);
				}
			}
		}

		posX += motionX;
		posY += motionY;
		posZ += motionZ;
		float var16 = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);

		for (rotationPitch = (float) (Math.atan2(motionY, var16) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
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
		setPosition(posX, posY, posZ);
	}
}
