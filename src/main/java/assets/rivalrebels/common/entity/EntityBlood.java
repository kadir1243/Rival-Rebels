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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class EntityBlood extends EntityInanimate
{
	private boolean	isGore	= true;

	public EntityBlood(EntityType<? extends EntityBlood> type, World par1World) {
		super(type, par1World);
	}

    public EntityBlood(World world) {
        this(RREntities.BLOOD, world);
    }

	public EntityBlood(World par1World, EntityGore bloodEmitter) {
		this(par1World);
		refreshPositionAndAngles(bloodEmitter.getX(), bloodEmitter.getY(), bloodEmitter.getZ(), 0, 0);
		setPosition(getX(), getY(), getZ());
		shoot(0.1f);
		isGore = true;
	}

	public EntityBlood(World par1World, double x, double y, double z)
	{
		this(par1World);
		refreshPositionAndAngles(x, y, z, 0, 0);
		setPosition(getX(), getY(), getZ());
		shoot(0f);
		isGore = false;
	}

	public void shoot(float force)
	{
		setVelocity(
            random.nextGaussian() * force,
            random.nextGaussian() * force,
            random.nextGaussian() * force);
	}

	@Override
	public void tick()
	{
		super.tick();

		++age;

		if (isInsideWaterOrBubbleColumn() || (age == 20 && isGore)) kill();

        Vec3d vec3d = getPos().add(getVelocity());
        setVelocity(vec3d.getX(), vec3d.getY(), vec3d.getZ());

        setVelocity(getVelocity().multiply(0.99F));
        addVelocity(0, -0.03F, 0);
		setPosition(getX(), getY(), getZ());
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance)
	{
		return distance < 256;
	}


}
