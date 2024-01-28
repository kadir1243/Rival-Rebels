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
import assets.rivalrebels.common.explosion.TsarBomba;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeBlockEntity;

import java.util.ArrayList;
import java.util.List;

public class EntityTsarBlast extends EntityInanimate
{
	public TsarBomba	tsar		= null;
	public double		radius;
	public int			time		= 0;

    public EntityTsarBlast(EntityType<? extends EntityTsarBlast> type, World world) {
        super(type, world);
    }

	public EntityTsarBlast(World par1World)
	{
		this(RREntities.TSAR_BLAST, par1World);
		ignoreCameraFrustum = true;
	}

	public EntityTsarBlast(World par1World, float x, float y, float z, TsarBomba tsarBomba, int rad) {
		this(par1World);
		tsar = tsarBomba;
		radius = rad;
		setVelocity(Math.sqrt(radius - RivalRebels.tsarBombaStrength) / 10, getVelocity().getY(), getVelocity().getZ());
		setPosition(x, y, z);
	}

	public EntityTsarBlast(World par1World, double x, double y, double z, float rad) {
		this(par1World);
		radius = rad;
		setVelocity(Math.sqrt(rad - RivalRebels.tsarBombaStrength) / 10, getVelocity().getY(), getVelocity().getZ());
        setPosition(x, y, z);
	}

	@Override
	public void tick()
	{
		super.tick();

		if (world.random.nextInt(10) == 0)
		{
			world.playSound(getX(), getY(), getZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.AMBIENT, 10.0F, 0.50F, true);
		}
		else
		{
			if (world.random.nextInt(5) == 0) RivalRebelsSoundPlayer.playSound(this, 26, 0, 100, 0.7f);
		}

		age++;

		if (!world.isClient)
		{
			if (tsar == null && age > 1200) kill();
			if (age % 20 == 0) updateEntityList();
			if (age < 1200 && age % 5 == 0) pushAndHurtEntities();
			for (int i = 0; i < RivalRebels.tsarBombaSpeed * 2; i++)
			{
				if (tsar != null)
				{
					tsar.tick(this);
					/*if (tsar.tick())
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
		for (int i = 0; i < world.getOtherEntities(this, IForgeBlockEntity.INFINITE_EXTENT_AABB).size(); i++)
		{
			Entity e = world.getOtherEntities(this, IForgeBlockEntity.INFINITE_EXTENT_AABB).get(i);
			double dist = e.squaredDistanceTo(getX(),getY(),getZ());
			if (dist < ldist)
			{
				if ((e instanceof PlayerEntity && ((PlayerEntity) e).getAbilities().invulnerable) || e instanceof EntityNuclearBlast || e instanceof EntityTsarBlast || e == this) continue;
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
			if (!e.isAlive() || e.isInvulnerableTo(RivalRebelsDamageSource.nuclearblast))
			{
				remove.add(e);
				continue;
			}
            Vec3d dpos = e.getPos().subtract(getPos());
			float dist = (float) dpos.length();
			float rsqrt = 1.0f / (dist + 0.0001f);
            dpos = dpos.multiply(rsqrt);
			double f = 40.0f * (1.0f - dist * invrad) * ((e instanceof EntityB83 || e instanceof EntityHackB83) ? -1.0f : 1.0f);
			if (e instanceof EntityRhodes)
			{
				e.damage(RivalRebelsDamageSource.nuclearblast, (int) (radius*f*0.025f));
			}
			else
			{
				e.damage(RivalRebelsDamageSource.nuclearblast, (int) (f * f * 2.0f * radius + 20.0f));
                e.setVelocity(getVelocity().subtract(dpos.multiply(f)));
			}
		}
		for (Entity e : remove)
		{
			entitylist.remove(e);
		}
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt)
	{
		radius = nbt.getFloat("radius");
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt)
	{
		nbt.putFloat("radius", (float) radius);
	}

	@Override
	public float getBrightnessAtEyes()
	{
		return 1000F;
	}

	@Override
	public boolean shouldRender(double distance)
	{
		return true;
	}

    public EntityTsarBlast setTime()
	{
		age = 920;
		return this;
	}
}
