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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityLightningLink extends EntityInanimate {
	public EntityLightningLink(World par1World)
	{
		super(par1World);
		ignoreFrustumCheck = true;
		setSize(0.5F, 0.5F);
		ticksExisted = 0;
	}

	public EntityLightningLink(World par1World, Entity player, double distance)
	{
		super(par1World);
		ignoreFrustumCheck = true;
		motionX = distance / 100;
		setLocationAndAngles(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
		posX -= (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		posY -= 0.12;
		posZ -= (MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		setPosition(posX, posY, posZ);
		setSize(0.5F, 0.5F);
		ticksExisted = 0;
	}

	public EntityLightningLink(World par1World, double x, double y, double z, float yaw, float pitch, double distance)
	{
		super(par1World);
		ignoreFrustumCheck = true;
		setLocationAndAngles(x, y, z, yaw, pitch);
		motionX = distance / 100;
		setPosition(posX, posY, posZ);
		setSize(0.5F, 0.5F);
		ticksExisted = 0;
	}

	@Override
	public boolean isInRangeToRenderDist(double par1)
	{
		return true;
	}

	@Override
	public int getBrightnessForRender()
	{
		return 1000;
	}

	@Override
	public float getBrightness()
	{
		return 1000F;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if (ticksExisted > 1) setDead();
		ticksExisted++;
	}
}
