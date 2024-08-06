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

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.explosion.TachyonBomb;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.Shapes;

public class EntityTachyonBombBlast extends EntityInanimate
{
	public TachyonBomb	tsar		= null;
	public double		radius;

    public EntityTachyonBombBlast(EntityType<? extends EntityTachyonBombBlast> type, Level par1World) {
        super(type, par1World);
    }

	public EntityTachyonBombBlast(Level par1World) {
		this(RREntities.TACHYON_BOMB_BLAST, par1World);
		noCulling = true;
	}

	public EntityTachyonBombBlast(Level par1World, float x, float y, float z, TachyonBomb tsarBomba, int rad) {
		this(par1World);
		tsar = tsarBomba;
		radius = rad;
		setDeltaMovement(Math.sqrt(radius - RRConfig.SERVER.getTsarBombaStrength()) / 10, getDeltaMovement().y(), getDeltaMovement().z());
		setPos(x, y, z);
	}

	public EntityTachyonBombBlast(Level par1World, double x, double y, double z, float rad) {
		this(par1World);
		radius = rad;
		setDeltaMovement(Math.sqrt(rad - RRConfig.SERVER.getTsarBombaStrength()) / 10, getDeltaMovement().y(), getDeltaMovement().z());
		setPos(x, y, z);
	}

	@Override
	public void tick()
	{
		super.tick();

		if (level().random.nextInt(30) == 0)
		{
			level().playLocalSound(getX(), getY(), getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.MASTER, 10.0F, 0.5F, true);
		}
		else
		{
			if (level().random.nextInt(30) == 0) RivalRebelsSoundPlayer.playSound(this, 13, 0, 100, 0.8f);
		}

		tickCount++;

		if (!level().isClientSide())
		{
			if (tsar == null && tickCount > 1200) kill();
			if (tickCount % 20 == 0) updateEntityList();
			if (tickCount < 1200 && tickCount % 5 == 0) pushAndHurtEntities();
			for (int i = 0; i < RRConfig.SERVER.getTsarBombaSpeed() * 2; i++)
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
		for (int i = 0; i < level().getEntities(this, Shapes.INFINITY.bounds()).size(); i++)
		{
			Entity e = level().getEntities(this, Shapes.INFINITY.bounds()).get(i);
			double dist = e.distanceToSqr(getX(),getY(),getZ());
			if (dist < ldist)
			{
				if ((e instanceof Player && ((Player) e).isCreative()) || e instanceof EntityNuclearBlast || e instanceof EntityTachyonBombBlast || e == this) continue;
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
			if (!e.isAlive() || e.isInvulnerableTo(RivalRebelsDamageSource.nuclearBlast(level())))
			{
				remove.add(e);
				continue;
			}
			float dx = (float) (e.getX() - getX());
			float dy = (float) (e.getY() - getY());
			float dz = (float) (e.getZ() - getZ());
			float dist = Mth.sqrt(dx * dx + dy * dy + dz * dz);
			float rsqrt = 1.0f / (dist + 0.0001f);
			dx *= rsqrt;
			dy *= rsqrt;
			dz *= rsqrt;
			double f = 40.0f * (1.0f - dist * invrad) * ((e instanceof EntityB83 || e instanceof EntityHackB83) ? -1.0f : 1.0f);
			if (e instanceof EntityRhodes)
			{
				e.hurt(RivalRebelsDamageSource.nuclearBlast(level()), (int) (radius*f*0.025f));
			}
			else
			{
				e.hurt(RivalRebelsDamageSource.nuclearBlast(level()), (int) (f * f * 2.0f * radius + 20.0f));
                e.setDeltaMovement(e.getDeltaMovement().subtract(
                    dx * f,
                    dy * f,
                    dz * f
                ));
            }
		}
		for (Entity e : remove)
		{
			entitylist.remove(e);
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt) {
		radius = nbt.getFloat("radius");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt) {
		nbt.putFloat("radius", (float) radius);
	}

    public EntityTachyonBombBlast setTime()
	{
		tickCount = 920;
		return this;
	}
}
