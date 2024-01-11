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
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class EntityCuchillo extends EntityInanimate
{
	public Entity	shootingEntity;
	private boolean	inGround;
	private int		ticksInGround;

	public EntityCuchillo(World par1World)
	{
		super(par1World);
		setSize(0.5F, 0.5F);
	}

	public EntityCuchillo(World par1World, EntityPlayer player, float par3)
	{
		super(par1World);
		shootingEntity = player;
		setSize(0.5F, 0.5F);
		setLocationAndAngles(player.posX, player.posY + player.getEyeHeight(), player.posZ, player.rotationYaw, player.rotationPitch);
		posX -= (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		posY -= 0.1f;
		posZ -= (MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		setPosition(posX, posY, posZ);
		motionX = (-MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI)) * par3;
		motionZ = (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI)) * par3;
		motionY = (-MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI)) * par3;
	}
	public EntityCuchillo(World par1World, double x, double y,double z, double mx, double my, double mz)
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

    @Override
	public void onUpdate()
	{
		super.onUpdate();
		ticksExisted++;
		if (!inGround)
		{
			Vec3d vec31 = new Vec3d(posX, posY, posZ);
			Vec3d vec3 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
			RayTraceResult mop = world.rayTraceBlocks(vec31, vec3, false, true, false);
			vec31 = new Vec3d(posX, posY, posZ);
			if (mop != null) vec3 = mop.hitVec;
			else vec3 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);

			List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(motionX, motionY, motionZ).grow(1.0D, 1.0D, 1.0D));
			double d0 = Double.MAX_VALUE;
            for (Entity entity : list) {
                if (entity.canBeCollidedWith() && (ticksExisted >= 10 || entity != shootingEntity)) {
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

			if (mop != null)
			{
				if (!world.isRemote)
				{
					if (mop.entityHit != null)
					{
						mop.entityHit.attackEntityFrom(RivalRebelsDamageSource.cuchillo, 7);
						setDead();
					}
					else
					{
                        IBlockState state = world.getBlockState(mop.getBlockPos());
                        Block block = state.getBlock();
						Material hit = state.getMaterial();
						if (block == Blocks.GLASS || block == Blocks.GLASS_PANE || block == Blocks.STAINED_GLASS || block == Blocks.STAINED_GLASS_PANE)
						{
							world.setBlockToAir(mop.getBlockPos());
							RivalRebelsSoundPlayer.playSound(this, 4, 0, 5F, 0.3F);
							return;
						}
						else if (hit == Material.ROCK)
						{
							world.spawnEntity(new EntityItem(world, posX, posY, posZ, new ItemStack(RivalRebels.knife, 1)));
							setDead();
						}
						else
						{
							inGround = true;
						}
					}
				}
			}
			else
			{
				posX += motionX;
				posY += motionY;
				posZ += motionZ;
				rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
				while (rotationYaw - prevRotationYaw < -180.0F)
					prevRotationYaw -= 360.0F;
				while (rotationYaw - prevRotationYaw >= 180.0F)
					prevRotationYaw += 360.0F;
				rotationPitch -= 30;
				rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;

				float friction = 0.98f;

				if (isInWater())
				{
					for (int var26 = 0; var26 < 4; ++var26)
						world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * 0.25F, posY - motionY * 0.25F, posZ - motionZ * 0.25F, motionX, motionY, motionZ);
					friction = 0.8F;
				}
				motionX *= friction;
				motionY *= friction;
				motionZ *= friction;
				motionY -= 0.026F;
				setPosition(posX, posY, posZ);
			}
		}
		else
		{
			motionX *= 0.2f;
			motionY *= 0.2f;
			motionZ *= 0.2f;
			ticksInGround++;
			if (ticksInGround == 60)
			{
				world.spawnEntity(new EntityItem(world, posX, posY, posZ, new ItemStack(RivalRebels.knife, 1)));
				setDead();
			}
		}
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
	{
		if (!world.isRemote && inGround)
		{
			if (!par1EntityPlayer.capabilities.isCreativeMode) par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(RivalRebels.knife, 1));
			world.playSound(this.posX, this.posY, this.posZ, SoundEvents.BLOCK_LAVA_POP, SoundCategory.NEUTRAL, 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F, true);
			setDead();
		}
	}

	@Override
	public boolean canBeAttackedWithItem()
	{
		return false;
	}
}
