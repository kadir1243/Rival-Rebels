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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class EntityLaserLink extends EntityInanimate
{
	public Random		rand		= new Random();
	public int			ticksExisted;

	public EntityPlayer	shooter;

	public EntityLaserLink(World par1World)
	{
		super(par1World);
		ignoreFrustumCheck = true;
	}

	public EntityLaserLink(World par1World, EntityPlayer player, double distance)
	{
		this(par1World);
		shooter = player;
		ticksExisted = 0;
		motionX = distance / 100f;
		setLocationAndAngles(shooter.posX, shooter.posY + shooter.getEyeHeight(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
		posX -= (MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.2F);
		posY -= 0.08;
		posZ -= (MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.2F);
		setPosition(posX, posY, posZ);
		setSize(0.5F, 0.5F);
	}

	public EntityLaserLink(World par1World, double x, double y, double z, float yaw, float pitch, double distance)
	{
		this(par1World);
		setLocationAndAngles(x, y, z, yaw, pitch);
		motionX = distance/100f;
		ticksExisted = 0;
		setPosition(posX, posY, posZ);
		setSize(0.5F, 0.5F);
	}

	@Override
	public boolean isInRangeToRenderDist(double par1)
	{
		return true;
	}

	@Override
	public int getBrightnessForRender(float par1)
	{
		return 1000;
	}

	@Override
	public float getBrightness(float par1)
	{
		return 1000F;
	}

    @Override
	public void onUpdate() {
		super.onUpdate();
		if (ticksExisted == 1) setDead();
		ticksExisted++;
	}
}
