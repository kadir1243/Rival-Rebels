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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;

public class EntityPropulsionFX extends ThrowableProjectile
{
	private int	ticksInAir;

    public EntityPropulsionFX(EntityType<? extends EntityPropulsionFX> type, Level level) {
        super(type, level);
        setNoGravity(true);
    }

	public EntityPropulsionFX(Level level) {
		this(RREntities.PROPULSION_FX.get(), level);
		ticksInAir = 0;
	}

	public EntityPropulsionFX(Level level, double x, double y, double z) {
		this(level);
		setPos(x, y - 0.2, z);
	}

	public EntityPropulsionFX(Level level, double x, double y, double z, double mX, double mY, double mZ) {
		this(level);
		setPos(x, y, z);
		setDeltaMovement(mX, mY, mZ);
	}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
	public void tick() {
		super.tick();
		ticksInAir++;
		if ((ticksInAir >= 5 && random.nextInt(2) == 1) || this.isInWaterOrBubble()) {
			kill();
		}
        setPos(getX() + getDeltaMovement().x() + (random.nextDouble() - 0.5) * 0.07,
            getY() + getDeltaMovement().y() + (random.nextDouble() - 0.5) * 0.07 + 0.005,
            getZ() + getDeltaMovement().z() + (random.nextDouble() - 0.5) * 0.07);
	}
}
