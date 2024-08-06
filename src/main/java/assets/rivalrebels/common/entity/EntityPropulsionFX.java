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

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;

public class EntityPropulsionFX extends ThrowableProjectile
{
	private int	ticksInAir;

    public EntityPropulsionFX(EntityType<? extends EntityPropulsionFX> type, Level world) {
        super(type, world);
        setNoGravity(true);
    }

	public EntityPropulsionFX(Level par1World)
	{
		this(RREntities.PROPULSION_FX, par1World);
		ticksInAir = 0;
	}

	public EntityPropulsionFX(Level par1World, double par2, double par4, double par6)
	{
		this(par1World);
        par4 -= 0.2;
		setPos(par2, par4, par6);
	}

	public EntityPropulsionFX(Level world2, double x, double y, double z, double mX, double mY, double mZ)
	{
		this(world2);
		setPos(x, y, z);
		setDeltaMovement(mX, mY, mZ);
	}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
	public void tick()
	{
		super.tick();
		ticksInAir++;
		if ((ticksInAir >= 5 && level().random.nextInt(2) == 1) || this.isInWaterOrBubble())
		{
			kill();
		}
        setPosRaw(getX() + getDeltaMovement().x() + (level().random.nextDouble() - 0.5) * 0.07,
            getY() + getDeltaMovement().y() + (level().random.nextDouble() - 0.5) * 0.07 + 0.005,
            getZ() + getDeltaMovement().z() + (level().random.nextDouble() - 0.5) * 0.07);
		setPos(getX(), getY(), getZ());
	}
}
