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
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource;
import io.github.kadir1243.rivalrebels.common.explosion.NuclearExplosion;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntityNuclearBlast extends EntityInanimate {
    int			time;
    int			Strength;

    public EntityNuclearBlast(EntityType<? extends EntityNuclearBlast> type, Level world) {
        super(type, world);
    }

	public EntityNuclearBlast(Level level)
	{
		this(RREntities.NUCLEAR_BLAST.get(), level);
		noCulling = true;
		tickCount = 0;
		time = 0;
	}

	public EntityNuclearBlast(Level level, double x, double y, double z, int s, boolean hasTroll)
	{
		this(level);
        setDeltaMovement(hasTroll ? 1 : 0, Strength = s, getDeltaMovement().z());
		setPos(x, y, z);
	}

    @Override
	public void tick()
	{
		super.tick();
		if (!level().isClientSide())
		{
			if (tickCount == 0)
			{
				level().explode(null, getX(), getY() - 5, getZ(), 4, Level.ExplosionInteraction.BLOCK);
			}
			if (tickCount % 20 == 0 && tickCount > 60)
			{
				time++;
				if (time <= Strength)
				{
					new NuclearExplosion(level(), (int) getX(), (int) getY() - 5, (int) getZ(), (time * time) / 2 + RRConfig.SERVER.getNuclearBombStrength());
				}
			}
			if (tickCount % 2 == 0 && tickCount < 400) pushAndHurtEntities();
		}
		if (tickCount < 30)
		{
			level().playLocalSound(getX(), getY() + tickCount - 5, getZ(), SoundEvents.GENERIC_EXPLODE.value(), getSoundSource(), 4.0f, random.nextFloat() * 0.1f + 0.9f, true);
		}
		if (tickCount % 3 == 0 && tickCount < 40 && tickCount > 30)
		{
			for (int i = 0; i < 21; i++)
			{
				level().playLocalSound(getX() + Mth.sin(i) * (i / 0.5), getY() + 17, getZ() + Mth.cos(i) * (i / 0.5), SoundEvents.GENERIC_EXPLODE.value(), getSoundSource(), 4.0f, random.nextFloat() + 1.0f, true);
			}
		}
		if (tickCount < 600)
		{
			if (tickCount % 5 == random.nextInt(5))
			{
                for (Player p : level().players()) {
                    p.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 10.0F, 0.50F);
                    p.playSound(SoundEvents.GENERIC_EXPLODE.value(), 5.0F, 0.10F);
                }
			}
		}
		else
		{
			kill();
		}

		tickCount++;
	}

	private void pushAndHurtEntities()
	{
		int radius = Strength * RRConfig.SERVER.getNuclearBombStrength();
		if (radius > 80) radius = 80;
        AABB aabb = new AABB(getX(), getY(), getZ(), getX(), getY(), getZ()).inflate(radius + 1, -(radius + 1), radius + 1);
		List<Entity> var9 = level().getEntities(this, aabb);

        for (Entity entity : var9) {
            double var13 = Math.sqrt(entity.distanceToSqr(getX(), getY(), getZ())) / radius;

            if (var13 <= 1.0D) {
                Vec3 vec3 = entity.getEyePosition().subtract(position());

                if (vec3.length() != 0.0D) {
                    vec3 = vec3.normalize();
                    if (!(entity instanceof EntityNuclearBlast) && !(entity instanceof EntityTsarBlast)) {
                        if (entity instanceof FallingBlockEntity) entity.kill();
                        else {
                            if (entity instanceof Player && entity.isInvulnerable())
                                continue;
                            entity.hurt(RivalRebelsDamageSource.nuclearBlast(level()), 16 * radius);
                            entity.setDeltaMovement(getDeltaMovement().subtract(vec3.scale(8)));
                        }
                    }
                }
            }
        }
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt)
	{
		tickCount = nbt.getInt("age");
		time = nbt.getInt("time");
        setDeltaMovement(nbt.getBoolean("troll") ? 1 : 0, Strength = nbt.getInt("charges"), getDeltaMovement().z());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt)
	{
		nbt.putInt("age", tickCount);
		nbt.putInt("time", time);
		nbt.putInt("charges", Strength);
	}

}
