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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityLightningLink extends EntityInanimate {
    public EntityLightningLink(EntityType<? extends EntityLightningLink> type, World world) {
        super(type, world);
    }

	public EntityLightningLink(World par1World) {
		this(RREntities.LIGHTNING_LINK, par1World);
		ignoreCameraFrustum = true;
		age = 0;
	}

	public EntityLightningLink(World par1World, Entity player, double distance) {
		this(par1World);
        setVelocity(distance / 100, getVelocity().getY(), getVelocity().getZ());
		refreshPositionAndAngles(player.getX(), player.getY(), player.getZ(), player.getYaw(), player.getPitch());
        Vec3d vec3d = getPos().subtract(
            (MathHelper.cos(getYaw() / 180.0F * (float) Math.PI) * 0.16F),
            0.12,
            (MathHelper.sin(getYaw() / 180.0F * (float) Math.PI) * 0.16F)
        );
        setPos(vec3d.getX(), vec3d.getY(), vec3d.getZ());
		setPosition(getX(), getY(), getZ());
	}

	public EntityLightningLink(World par1World, double x, double y, double z, float yaw, float pitch, double distance) {
		this(par1World);
		refreshPositionAndAngles(x, y, z, yaw, pitch);
        setVelocity(distance / 100, getVelocity().getY(), getVelocity().getZ());
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
	public void tick()
	{
		super.tick();
		if (age > 1) kill();
		age++;
	}
}
