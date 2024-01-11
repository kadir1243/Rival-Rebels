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
import assets.rivalrebels.common.explosion.AntimatterBomb;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class EntityAntimatterBombBlast extends EntityInanimate
{
	public AntimatterBomb	tsar		= null;
	public double		radius;
	public int			time		= 0;

	public EntityAntimatterBombBlast(World par1World)
	{
		super(par1World);
		ignoreFrustumCheck = true;
	}

	public EntityAntimatterBombBlast(World par1World, float x, float y, float z, AntimatterBomb tsarBomba, int rad)
	{
		super(par1World);
		ignoreFrustumCheck = true;
		tsar = tsarBomba;
		radius = rad;
		motionX = Math.sqrt(radius - RivalRebels.tsarBombaStrength) / 10;
		setPosition(x, y, z);
	}

	public EntityAntimatterBombBlast(World par1World, double x, double y, double z, float rad)
	{
		super(par1World);
		ignoreFrustumCheck = true;
		radius = rad;
		motionX = Math.sqrt(rad - RivalRebels.tsarBombaStrength) / 10;
		setPosition(x, y, z);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (world.rand.nextInt(30) == 0)
		{
			world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.AMBIENT, 10.0F, 0.5F, false);
		}
		else
		{
			if (world.rand.nextInt(30) == 0) RivalRebelsSoundPlayer.playSound(this, 13, 0, 100, 0.8f);
		}

		ticksExisted++;

		if (!world.isRemote)
		{
			if (tsar == null && ticksExisted > 1200) setDead();
			if (ticksExisted % 20 == 0) updateEntityList();
			if (ticksExisted < 1200 && ticksExisted % 5 == 0) pushAndHurtEntities();
			for (int i = 0; i < RivalRebels.tsarBombaSpeed * 2; i++)
			{
				if (tsar != null)
				{
					tsar.update(this);
					/*if (tsar.update())
					{
						tsar = null;
					}*/
				}
				else
				{
					return;
				}
			}
		}
	}

	List<Entity> entitylist = new ArrayList<>();

	public void updateEntityList()
	{
		entitylist.clear();
		double ldist = radius*radius;
		for (int i = 0; i < world.loadedEntityList.size(); i++)
		{
			Entity e = world.loadedEntityList.get(i);
			double dist = e.getDistanceSq(posX,posY,posZ);
			if (dist < ldist)
			{
				if ((e instanceof EntityPlayer && ((EntityPlayer) e).capabilities.isCreativeMode) || e instanceof EntityNuclearBlast || e instanceof EntityAntimatterBombBlast || e == this) continue;
				entitylist.add(e);
			}
		}
	}

	public void pushAndHurtEntities()
	{
		List<Entity> remove = new ArrayList<>();
		float invrad = 1.0f / (float) radius;
		for (Entity e : entitylist)
		{
			if (e.isDead || e.getIsInvulnerable())
			{
				remove.add(e);
				continue;
			}
			float dx = (float) (e.posX - posX);
			float dy = (float) (e.posY - posY);
			float dz = (float) (e.posZ - posZ);
			float dist = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
			float rsqrt = 1.0f / (dist + 0.0001f);
			dx *= rsqrt;
			dy *= rsqrt;
			dz *= rsqrt;
			double f = 40.0f * (1.0f - dist * invrad) * ((e instanceof EntityB83 || e instanceof EntityHackB83) ? -1.0f : 1.0f);
			if (e instanceof EntityRhodes)
			{
				e.attackEntityFrom(RivalRebelsDamageSource.nuclearblast, (int) (radius*f*0.025f));
			}
			else
			{
				e.attackEntityFrom(RivalRebelsDamageSource.nuclearblast, (int) (f * f * 2.0f * radius + 20.0f));
				e.motionX -= dx * f;
				e.motionY -= dy * f;
				e.motionZ -= dz * f;
			}
		}
		for (Entity e : remove)
		{
			entitylist.remove(e);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		motionX = nbt.getFloat("size");
		radius = nbt.getFloat("radius");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		nbt.setFloat("size", (float) motionX);
		nbt.setFloat("radius", (float) radius);
	}

    @Override
    public float getBrightness() {
        return 1000F;
    }

    @Override
    public int getBrightnessForRender() {
        return 1000;
    }

	@Override
	public boolean isInRangeToRenderDist(double par1)
	{
		return true;
	}

    public EntityAntimatterBombBlast setTime()
	{
		ticksExisted = 920;
		return this;
	}
}
