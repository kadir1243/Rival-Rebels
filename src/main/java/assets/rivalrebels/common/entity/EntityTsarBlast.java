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
import assets.rivalrebels.common.explosion.TsarBomba;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;

public class EntityTsarBlast extends EntityInanimate
{
	public TsarBomba	tsar		= null;
	public double		radius;

    public EntityTsarBlast(EntityType<? extends EntityTsarBlast> type, Level world) {
        super(type, world);
    }

	public EntityTsarBlast(Level level)
	{
		this(RREntities.TSAR_BLAST, level);
		noCulling = true;
	}

	public EntityTsarBlast(Level level, float x, float y, float z, TsarBomba tsarBomba, int rad) {
		this(level);
		tsar = tsarBomba;
		radius = rad;
		setDeltaMovement(Math.sqrt(radius - RRConfig.SERVER.getTsarBombaStrength()) / 10, getDeltaMovement().y(), getDeltaMovement().z());
		setPos(x, y, z);
	}

	public EntityTsarBlast(Level level, double x, double y, double z, float rad) {
		this(level);
		radius = rad;
		setDeltaMovement(Math.sqrt(rad - RRConfig.SERVER.getTsarBombaStrength()) / 10, getDeltaMovement().y(), getDeltaMovement().z());
        setPos(x, y, z);
	}

	@Override
	public void tick()
	{
		super.tick();

		if (random.nextInt(10) == 0)
		{
			level().playLocalSound(getX(), getY(), getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.AMBIENT, 10.0F, 0.50F, true);
		}
		else
		{
			if (random.nextInt(5) == 0) RivalRebelsSoundPlayer.playSound(this, 26, 0, 100, 0.7f);
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
				if ((e instanceof Player && ((Player) e).isCreative()) || e instanceof EntityNuclearBlast || e instanceof EntityTsarBlast || e == this) continue;
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
            Vec3 dpos = e.position().subtract(position());
			float dist = (float) dpos.length();
			float rsqrt = 1.0f / (dist + 0.0001f);
            dpos = dpos.scale(rsqrt);
			double f = 40.0f * (1.0f - dist * invrad) * ((e instanceof EntityB83 || e instanceof EntityHackB83) ? -1.0f : 1.0f);
			if (e instanceof EntityRhodes)
			{
				e.hurt(RivalRebelsDamageSource.nuclearBlast(level()), (int) (radius*f*0.025f));
			}
			else
			{
				e.hurt(RivalRebelsDamageSource.nuclearBlast(level()), (int) (f * f * 2.0f * radius + 20.0f));
                e.setDeltaMovement(getDeltaMovement().subtract(dpos.scale(f)));
			}
		}
		for (Entity e : remove)
		{
			entitylist.remove(e);
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt)
	{
		radius = nbt.getFloat("radius");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt)
	{
		nbt.putFloat("radius", (float) radius);
	}

    public EntityTsarBlast setTime()
	{
		tickCount = 920;
		return this;
	}
}
