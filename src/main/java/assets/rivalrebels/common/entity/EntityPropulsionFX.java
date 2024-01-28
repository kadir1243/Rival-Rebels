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

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.world.World;

public class EntityPropulsionFX extends ThrownEntity
{
	private int	ticksInAir;

    public EntityPropulsionFX(EntityType<? extends EntityPropulsionFX> type, World world) {
        super(type, world);
    }

	public EntityPropulsionFX(World par1World)
	{
		this(RREntities.PROPULSION_FX, par1World);
		ticksInAir = 0;
	}

	public EntityPropulsionFX(World par1World, double par2, double par4, double par6)
	{
		this(par1World);
        par4 -= 0.2;
		setPosition(par2, par4, par6);
	}

	public EntityPropulsionFX(World world2, double x, double y, double z, double mX, double mY, double mZ)
	{
		this(world2);
		setPosition(x, y, z);
		setVelocity(mX, mY, mZ);
	}

	@Override
	public float getBrightnessAtEyes()
	{
		return 1000F;
	}

	@Override
	public boolean shouldRender(double distance)
	{
		return true;
	}

    @Override
    protected void initDataTracker() {
    }

    @Override
	public void tick()
	{
		super.tick();
		ticksInAir++;
		if ((ticksInAir >= 5 && world.random.nextInt(2) == 1) || this.isInsideWaterOrBubbleColumn())
		{
			kill();
		}
        setPos(getX() + getVelocity().getX() + (world.random.nextDouble() - 0.5) * 0.07,
            getY() + getVelocity().getY() + (world.random.nextDouble() - 0.5) * 0.07 + 0.005,
            getZ() + getVelocity().getZ() + (world.random.nextDouble() - 0.5) * 0.07);
		setPosition(getX(), getY(), getZ());
	}

	@Override
	protected float getGravity()
	{
		return 0F;
	}
}
