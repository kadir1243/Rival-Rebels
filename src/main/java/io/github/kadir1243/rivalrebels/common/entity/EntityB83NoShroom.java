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
import io.github.kadir1243.rivalrebels.common.explosion.NuclearExplosion;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class EntityB83NoShroom extends EntityB83 {
	public EntityB83NoShroom(EntityType<? extends EntityB83NoShroom> entityType, Level level) {
		super(entityType, level);
	}

	public EntityB83NoShroom(Level level, double x, double y, double z, double mx, double my, double mz) {
		this(RREntities.B83_NO_SHROOM.get(), level);
		setPos(x, y, z);
        shoot(mx, my, mz, 5, 1);
	}

	@Override
	public void tick() {
        setDeltaMovement(getDeltaMovement().scale(0.99f/0.9f));
		super.tick();
	}

	public void explode()
	{
		new NuclearExplosion(level(), (int) getX(), (int) getY(), (int) getZ(), RRConfig.SERVER.getB83Strength()/2);
		EntitySphereBlast etb = new EntitySphereBlast(level(), getX(), getY(), getZ(), RRConfig.SERVER.getB83Strength() * 1.333333333f);
		etb.tickCount = -920;
		level().addFreshEntity(etb);
		this.kill();
	}
}
