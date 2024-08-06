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

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityGoo extends EntityInanimate
{
	private boolean	isGore	= true;

    public EntityGoo(EntityType<? extends EntityGoo> type, Level world) {
        super(type, world);
    }

	public EntityGoo(Level par1World)
	{
		this(RREntities.GOO, par1World);
	}

	public EntityGoo(Level par1World, EntityGore bloodEmitter)
	{
		this(par1World);
		moveTo(bloodEmitter.getX(), bloodEmitter.getY(), bloodEmitter.getZ(), 0, 0);
		setPos(getX(), getY(), getZ());
		shoot(0.1f);
		isGore = true;
	}

	public EntityGoo(Level par1World, double x, double y, double z)
	{
		this(par1World);
		moveTo(x, y, z, 0, 0);
		setPos(getX(), getY(), getZ());
		shoot(0f);
		isGore = false;
	}

    public void shoot(float force) {
        setDeltaMovement(random.nextGaussian() * force,
            random.nextGaussian() * force,
            random.nextGaussian() * force);
    }

	@Override
	public void tick()
	{
		super.tick();

		++tickCount;

		Vec3 vec31 = position().add(getDeltaMovement());

		if (isInWaterOrBubble() || (tickCount == 20 && isGore)) kill();

        setPosRaw(vec31.x(), vec31.y(), vec31.z());

        setDeltaMovement(getDeltaMovement().scale(0.99F));
        push(0, -0.03F, 0);
		setPos(getX(), getY(), getZ());
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double range) {
		return range < 256;
	}
}
