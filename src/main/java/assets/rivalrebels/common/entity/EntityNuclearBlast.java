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
import assets.rivalrebels.common.explosion.NuclearExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class EntityNuclearBlast extends EntityInanimate
{
	public int	ticksExisted;
	int			time;
	int			Strength;

	public EntityNuclearBlast(World par1World)
	{
		super(par1World);
		ignoreFrustumCheck = true;
		ticksExisted = 0;
		time = 0;
		setSize(0.5F, 0.5F);
	}

	public EntityNuclearBlast(World par1World, double par2, double par4, double par6, int s, boolean hasTroll)
	{
		super(par1World);
		ignoreFrustumCheck = true;
		ticksExisted = 0;
		time = 0;
		motionY = Strength = s;
		if (hasTroll)
		{
			motionX = 1;
		}
		else
		{
			motionX = 0;
		}
		setSize(0.5F, 0.5F);
		setPosition(par2, par4, par6);
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
		if (!world.isRemote)
		{
			if (ticksExisted == 0)
			{
				world.createExplosion(null, posX, posY - 5, posZ, 4, true);
			}
			if (ticksExisted % 20 == 0 && ticksExisted > 60)
			{
				time++;
				if (time <= Strength)
				{
					new NuclearExplosion(world, (int) posX, (int) posY - 5, (int) posZ, (time * time) / 2 + RivalRebels.nuclearBombStrength);
				}
			}
			if (ticksExisted % 2 == 0 && ticksExisted < 400) pushAndHurtEntities();
		}
		if (ticksExisted < 30)
		{
			world.playSound(posX, posY + ticksExisted - 5, posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, getSoundCategory(), 4.0f, world.rand.nextFloat() * 0.1f + 0.9f, true);
		}
		if (ticksExisted % 3 == 0 && ticksExisted < 40 && ticksExisted > 30)
		{
			for (int i = 0; i < 21; i++)
			{
				world.playSound(posX + Math.sin(i) * (i / 0.5), posY + 17, posZ + Math.cos(i) * (i / 0.5), SoundEvents.ENTITY_GENERIC_EXPLODE, getSoundCategory(), 4.0f, world.rand.nextFloat() + 1.0f, true);
			}
		}
		if (ticksExisted < 600)
		{
			if (ticksExisted % 5 == world.rand.nextInt(5))
			{
                for (EntityPlayer p : world.playerEntities) {
                    world.playSound(p, p.posX, p.posY, p.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.MASTER, 10.0F, 0.50F);
                    world.playSound(p, p.posX, p.posY, p.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 5.0F, 0.10F);
                }
			}
		}
		else
		{
			setDead();
		}

		ticksExisted++;
	}

	private void pushAndHurtEntities()
	{
		int radius = Strength * RivalRebels.nuclearBombStrength * 1;
		if (radius > 80) radius = 80;
		int var3 = MathHelper.floor(posX - radius - 1.0D);
		int var4 = MathHelper.floor(posX + radius + 1.0D);
		int var5 = MathHelper.floor(posY - radius - 1.0D);
		int var28 = MathHelper.floor(posY + radius + 1.0D);
		int var7 = MathHelper.floor(posZ - radius - 1.0D);
		int var29 = MathHelper.floor(posZ + radius + 1.0D);
		List<Entity> var9 = world.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(var3, var5, var7, var4, var28, var29));
		Vec3d var30 = new Vec3d(posX, posY, posZ);

        for (Entity entity : var9) {
            double var13 = entity.getDistance(posX, posY, posZ) / radius;

            if (var13 <= 1.0D) {
                double var15 = entity.posX - posX;
                double var17 = entity.posY + entity.getEyeHeight() - posY;
                double var19 = entity.posZ - posZ;
                double var33 = MathHelper.sqrt(var15 * var15 + var17 * var17 + var19 * var19);

                if (var33 != 0.0D) {
                    var15 /= var33;
                    var17 /= var33;
                    var19 /= var33;
                    if (!(entity instanceof EntityNuclearBlast) && !(entity instanceof EntityTsarBlast)) {
                        if (entity instanceof EntityFallingBlock) entity.setDead();
                        else {
                            if (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode)
                                continue;
                            entity.attackEntityFrom(RivalRebelsDamageSource.nuclearblast, 16 * radius);
                            entity.motionX -= var15 * 8;
                            entity.motionY -= var17 * 8;
                            entity.motionZ -= var19 * 8;
                        }
                    }
                }
            }
        }
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound var1)
	{
		ticksExisted = var1.getInteger("ticksExisted");
		time = var1.getInteger("time");
		motionY = Strength = var1.getInteger("charges");
		motionX = var1.getBoolean("troll") ? 1.0f : 0.0f;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound var1)
	{
		var1.setInteger("ticksExisted", ticksExisted);
		var1.setInteger("time", time);
		var1.setInteger("charges", Strength);
	}

}
