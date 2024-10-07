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
package io.github.kadir1243.rivalrebels.common.entity;

import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource;
import io.github.kadir1243.rivalrebels.common.explosion.AntimatterBomb;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntityAntimatterBombBlast extends AbstractBlastEntity<AntimatterBomb> {
	public EntityAntimatterBombBlast(EntityType<? extends EntityAntimatterBombBlast> entityType, Level level) {
		super(entityType, level);
		noCulling = true;
	}

    public EntityAntimatterBombBlast(Level level) {
        super(RREntities.ANTIMATTER_BOMB_BLAST.get(), level);
        noCulling = true;
    }

	public EntityAntimatterBombBlast(Level level, float x, float y, float z, AntimatterBomb tsarBomba, int rad)
	{
		this(level);
		noCulling = true;
		bomb = tsarBomba;
		radius = rad;
		setDeltaMovement(Math.sqrt(radius - RRConfig.SERVER.getTsarBombaStrength()) / 10, getDeltaMovement().y(), getDeltaMovement().z());
		setPos(x, y, z);
	}

	public EntityAntimatterBombBlast(Level level, double x, double y, double z, float rad)
	{
		this(level);
		noCulling = true;
		radius = rad;
		setDeltaMovement(Math.sqrt(rad - RRConfig.SERVER.getTsarBombaStrength()) / 10, getDeltaMovement().y(), getDeltaMovement().z());
		setPos(x, y, z);
	}

	@Override
	public void tick()
	{
		super.tick();

		if (random.nextInt(30) == 0) {
            this.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 10.0F, 0.5F);
		} else {
			if (random.nextInt(30) == 0) this.playSound(RRSounds.MANDELEED.get(), 100, 0.8f);
		}

		tickCount++;

		if (!level().isClientSide())
		{
			if (bomb == null && tickCount > 1200) kill();
			if (tickCount % 20 == 0) updateEntityList();
			if (tickCount < 1200 && tickCount % 5 == 0) pushAndHurtEntities();
			for (int i = 0; i < RRConfig.SERVER.getTsarBombaSpeed() * 2; i++)
			{
				if (bomb != null)
				{
					bomb.tick(this);
					/*if (bomb.tick())
					{
						bomb = null;
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
        List<Entity> otherEntities = level().getEntities(this, AABB.of(BoundingBox.infinite()), e -> !((e instanceof Player && ((Player) e).isCreative()) || e instanceof EntityNuclearBlast || e instanceof EntityAntimatterBombBlast));
        for (Entity e : otherEntities) {
            double dist = e.distanceToSqr(getX(), getY(), getZ());
            if (dist < ldist) {
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
			if (!e.isAlive() || e.isInvulnerable())
			{
				remove.add(e);
				continue;
			}
            Vec3 vec3 = e.position().subtract(position());
			float dist = (float) vec3.length();
			float rsqrt = 1.0f / (dist + 0.0001f);
            vec3 = vec3.scale(rsqrt);
			double f = 40.0f * (1.0f - dist * invrad) * ((e instanceof EntityB83 || e instanceof EntityHackB83) ? -1.0f : 1.0f);
			if (e instanceof EntityRhodes)
			{
				e.hurt(RivalRebelsDamageSource.nuclearBlast(level()), (int) (radius*f*0.025f));
			}
			else
			{
				e.hurt(RivalRebelsDamageSource.nuclearBlast(level()), (int) (f * f * 2.0f * radius + 20.0f));
                e.setDeltaMovement(e.getDeltaMovement().subtract(vec3.scale(f)));
			}
		}
		for (Entity e : remove)
		{
			entitylist.remove(e);
		}
	}

    public EntityAntimatterBombBlast setTime() {
		tickCount = 920;
		return this;
	}
}
