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

import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EntityLaserLink extends EntityInanimate {
	public Player	shooter;

    public EntityLaserLink(EntityType<? extends EntityLaserLink> type, Level world) {
        super(type, world);
    }

	public EntityLaserLink(Level par1World) {
		this(RREntities.LASER_LINK, par1World);
		noCulling = true;
	}

	public EntityLaserLink(Level par1World, Player player, double distance)
	{
		this(par1World);
		shooter = player;
		tickCount = 0;
        setDeltaMovement(distance / 100f, getDeltaMovement().y(), getDeltaMovement().z());
		moveTo(shooter.getEyePosition(), shooter.getYRot(), shooter.getXRot());
        setPosRaw(getX() - (Mth.cos(getYRot() / 180.0F * Mth.PI) * 0.2F),
		getY() - 0.08,
		getZ() - (Mth.sin(getYRot() / 180.0F * Mth.PI) * 0.2F));
		setPos(getX(), getY(), getZ());
	}

	public EntityLaserLink(Level par1World, double x, double y, double z, float yaw, float pitch, double distance)
	{
		this(par1World);
		moveTo(x, y, z, yaw, pitch);
        setDeltaMovement(distance / 100f, getDeltaMovement().y(), getDeltaMovement().z());
		tickCount = 0;
		setPos(getX(), getY(), getZ());
	}

	@Override
	public void tick() {
        super.tick();
        if (tickCount == 1) kill();
        tickCount++;
    }
}
