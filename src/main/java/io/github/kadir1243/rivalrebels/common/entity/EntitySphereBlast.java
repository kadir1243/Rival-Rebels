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
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsSoundPlayer;
import java.util.List;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntitySphereBlast extends EntityTsarBlast {
    public EntitySphereBlast(EntityType<? extends EntitySphereBlast> type, Level world) {
        super(type, world);
    }

	public EntitySphereBlast(Level level) {
		this(RREntities.SPHERE_BLAST.get(), level);
		noCulling = true;
	}

	public EntitySphereBlast(Level level, double x, double y, double z, float rad) {
		this(level);
		radius = rad;
		setDeltaMovement(Math.sqrt(rad - RRConfig.SERVER.getTsarBombaStrength()) / 10, getDeltaMovement().y(), getDeltaMovement().z());
		setPos(x, y, z);
	}

	@Override
	public void tick()
	{
		if (random.nextInt(10) == 0)
		{
			this.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 10.0F, 0.50F);
		}
		else
		{
			if (random.nextInt(5) == 0) RivalRebelsSoundPlayer.playSound(this, 26, 0, 100, 0.7f);
		}

		if (random.nextBoolean()&&random.nextBoolean()) pushAndHurtEntities();

		tickCount++;

		if (tickCount > 400) kill();
	}

	@Override
	public void pushAndHurtEntities() {
        AABB aabb = new AABB(getX(), getY(), getZ(), getX(), getY(), getZ()).inflate(radius + 1, -(radius + 1), radius + 1);
		List<Entity> var9 = level().getEntities(this, aabb);

        for (Entity entity : var9) {
            if (entity instanceof LivingEntity) {
                if (entity instanceof Player && ((Player) entity).isCreative()) continue;

                double var13 = Math.sqrt(entity.distanceToSqr(getX(), getY(), getZ())) / radius;

                if (var13 <= 1.0D) {
                    Vec3 vec3 = entity.getEyePosition().subtract(position());

                    if (vec3.length() != 0.0D) {
                        vec3 = vec3.normalize();
                        double var34 = (1.0D - var13);
                        entity.hurt(RivalRebelsDamageSource.nuclearBlast(level()), (int) ((var34 * var34 + var34) * 20 * radius + 20) * 200);
                        entity.setDeltaMovement(
                            entity.getDeltaMovement().subtract(vec3.scale(var34 * 8))
                        );
                    }
                }
            }
            if (entity instanceof EntityRhodes) {
                entity.hurt(RivalRebelsDamageSource.nuclearBlast(level()), 30);
            }
        }
	}
}
