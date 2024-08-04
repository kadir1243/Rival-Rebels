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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityLightningLink extends EntityInanimate {
    public EntityLightningLink(EntityType<? extends EntityLightningLink> type, Level world) {
        super(type, world);
    }

	public EntityLightningLink(Level par1World) {
		this(RREntities.LIGHTNING_LINK, par1World);
		noCulling = true;
		tickCount = 0;
	}

	public EntityLightningLink(Level par1World, Entity player, double distance) {
		this(par1World);
        setDeltaMovement(distance / 100, getDeltaMovement().y(), getDeltaMovement().z());
		moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
        Vec3 vec3d = position().subtract(
            (Mth.cos(getYRot() / 180.0F * Mth.PI) * 0.16F),
            0.12,
            (Mth.sin(getYRot() / 180.0F * Mth.PI) * 0.16F)
        );
        setPosRaw(vec3d.x(), vec3d.y(), vec3d.z());
		setPos(getX(), getY(), getZ());
	}

	public EntityLightningLink(Level par1World, double x, double y, double z, float yaw, float pitch, double distance) {
		this(par1World);
		moveTo(x, y, z, yaw, pitch);
        setDeltaMovement(distance / 100, getDeltaMovement().y(), getDeltaMovement().z());
		setPos(getX(), getY(), getZ());
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double distance)
	{
		return true;
	}

	@Override
	public float getLightLevelDependentMagicValue()
	{
		return 1000F;
	}

	@Override
	public void tick()
	{
		super.tick();
		if (tickCount > 1) kill();
		tickCount++;
	}
}
