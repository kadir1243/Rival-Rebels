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

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityLightningLink extends EntityInanimate {
    public EntityLightningLink(EntityType<? extends EntityLightningLink> type, Level world) {
        super(type, world);
    }

	public EntityLightningLink(Level level) {
		this(RREntities.LIGHTNING_LINK.get(), level);
		noCulling = true;
		tickCount = 0;
	}

	public EntityLightningLink(Level level, Entity player, double distance) {
		this(level);
        setDeltaMovement(distance / 100, getDeltaMovement().y(), getDeltaMovement().z());
		moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
        Vec3 vec3d = position().subtract(
            (Mth.cos(getYRot() * Mth.DEG_TO_RAD) * 0.16F),
            0.12,
            (Mth.sin(getYRot() * Mth.DEG_TO_RAD) * 0.16F)
        );
        setPos(vec3d.x(), vec3d.y(), vec3d.z());
	}

	public EntityLightningLink(Level level, double x, double y, double z, float yaw, float pitch, double distance) {
		this(level);
		moveTo(x, y, z, yaw, pitch);
        setDeltaMovement(distance / 100, getDeltaMovement().y(), getDeltaMovement().z());
	}

	@Override
	public void tick() {
		super.tick();
		if (tickCount > 1) kill();
		tickCount++;
	}
}
