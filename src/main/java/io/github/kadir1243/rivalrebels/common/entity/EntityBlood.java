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

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityBlood extends EntityInanimate
{
	private boolean	isGore	= true;

	public EntityBlood(EntityType<? extends EntityBlood> type, Level level) {
		super(type, level);
	}

    public EntityBlood(Level level) {
        this(RREntities.BLOOD.get(), level);
    }

	public EntityBlood(Level level, EntityGore bloodEmitter) {
		this(level);
        setPos(bloodEmitter.position());
		shoot(0.1f);
		isGore = true;
	}

	public EntityBlood(Level level, double x, double y, double z)
	{
		this(level);
		setPos(x, y, z);
		shoot(0f);
		isGore = false;
	}

	public void shoot(float force)
	{
		setDeltaMovement(
            random.nextGaussian() * force,
            random.nextGaussian() * force,
            random.nextGaussian() * force);
	}

	@Override
	public void tick()
	{
		super.tick();

		++tickCount;

		if (isInWaterOrBubble() || (tickCount == 20 && isGore)) kill();

        Vec3 vec3d = position().add(getDeltaMovement());
        setDeltaMovement(vec3d.x(), vec3d.y(), vec3d.z());

        setDeltaMovement(getDeltaMovement().scale(0.99F));
        push(0, -0.03F, 0);
        reapplyPosition();
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double distance) {
		return distance < 256;
	}

}
