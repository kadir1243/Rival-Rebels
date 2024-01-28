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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityLaserLink extends EntityInanimate {
	public PlayerEntity	shooter;

    public EntityLaserLink(EntityType<? extends EntityLaserLink> type, World world) {
        super(type, world);
    }

	public EntityLaserLink(World par1World) {
		this(RREntities.LASER_LINK, par1World);
		ignoreCameraFrustum = true;
	}

	public EntityLaserLink(World par1World, PlayerEntity player, double distance)
	{
		this(par1World);
		shooter = player;
		age = 0;
        setVelocity(distance / 100f, getVelocity().getY(), getVelocity().getZ());
		refreshPositionAndAngles(shooter.getX(), shooter.getY() + shooter.getEyeHeight(shooter.getPose()), shooter.getZ(), shooter.getYaw(), shooter.getPitch());
        setPos(getX() - (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * 0.2F),
		getY() - 0.08,
		getZ() - (MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * 0.2F));
		setPosition(getX(), getY(), getZ());
	}

	public EntityLaserLink(World par1World, double x, double y, double z, float yaw, float pitch, double distance)
	{
		this(par1World);
		refreshPositionAndAngles(x, y, z, yaw, pitch);
        setVelocity(distance / 100f, getVelocity().getY(), getVelocity().getZ());
		age = 0;
		setPosition(getX(), getY(), getZ());
	}

	@Override
	public boolean shouldRender(double distance)
	{
		return true;
	}

	@Override
	public float getBrightnessAtEyes()
	{
		return 1000F;
	}

	@Override
	public void tick() {
        super.tick();
        if (age == 1) kill();
        age++;
    }
}
