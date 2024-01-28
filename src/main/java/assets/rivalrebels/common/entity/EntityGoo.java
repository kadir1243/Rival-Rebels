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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityGoo extends EntityInanimate
{
	private boolean	isGore	= true;

    public EntityGoo(EntityType<? extends EntityGoo> type, World world) {
        super(type, world);
    }

	public EntityGoo(World par1World)
	{
		this(RREntities.GOO, par1World);
	}

	public EntityGoo(World par1World, EntityGore bloodEmitter)
	{
		this(par1World);
		refreshPositionAndAngles(bloodEmitter.getX(), bloodEmitter.getY(), bloodEmitter.getZ(), 0, 0);
		setPosition(getX(), getY(), getZ());
		shoot(0.1f);
		isGore = true;
	}

	public EntityGoo(World par1World, double x, double y, double z)
	{
		this(par1World);
		refreshPositionAndAngles(x, y, z, 0, 0);
		setPosition(getX(), getY(), getZ());
		shoot(0f);
		isGore = false;
	}

    public void shoot(float force) {
        setVelocity(random.nextGaussian() * force,
            random.nextGaussian() * force,
            random.nextGaussian() * force);
    }

	@Override
	public void tick()
	{
		super.tick();

		++age;

		Vec3d vec31 = getPos().add(getVelocity());

		if (isInsideWaterOrBubbleColumn() || (age == 20 && isGore)) kill();

        setPos(vec31.getX(), vec31.getY(), vec31.getZ());

        setVelocity(getVelocity().multiply(0.99F));
        addVelocity(0, -0.03F, 0);
		setPosition(getX(), getY(), getZ());
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean shouldRender(double range)
	{
		return range < 256;
	}
}
