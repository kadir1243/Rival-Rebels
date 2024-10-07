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

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class EntityLaserLink extends Projectile {
    public EntityLaserLink(EntityType<? extends EntityLaserLink> type, Level world) {
        super(type, world);
    }

	public EntityLaserLink(Level level) {
		this(RREntities.LASER_LINK.get(), level);
		noCulling = true;
	}

	public EntityLaserLink(Level level, Entity entity, double distance) {
		this(level);
        this.setOwner(entity);
		tickCount = 0;
        setDeltaMovement(distance / 100f, getDeltaMovement().y(), getDeltaMovement().z());
		moveTo(entity.getEyePosition(), entity.getYRot(), entity.getXRot());
        setPos(getX() - (Mth.cos(getYRot() * Mth.DEG_TO_RAD) * 0.2F),
		getY() - 0.08,
		getZ() - (Mth.sin(getYRot() * Mth.DEG_TO_RAD) * 0.2F));
	}

	public EntityLaserLink(Level level, double x, double y, double z, float yaw, float pitch, double distance) {
		this(level);
		moveTo(x, y, z, yaw, pitch);
        setDeltaMovement(distance / 100f, getDeltaMovement().y(), getDeltaMovement().z());
		tickCount = 0;
	}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
	public void tick() {
        super.tick();
        if (tickCount == 1) kill();
        tickCount++;
    }
}
