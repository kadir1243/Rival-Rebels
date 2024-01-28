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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.explosion.NuclearExplosion;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class EntityB83NoShroom extends EntityB83
{
	public int	ticksInAir	= 0;

	public EntityB83NoShroom(EntityType<? extends EntityB83NoShroom> entityType, World par1World)
	{
		super(entityType, par1World);
	}

	public EntityB83NoShroom(World par1World, double x, double y, double z, double mx, double my, double mz) {
		this(RREntities.B83_NO_SHROOM, par1World);
		setPosition(x, y, z);
        setVelocity(mx, my, mz, 5, 1);
	}

	@Override
	public void tick() {
        setVelocity(getVelocity().multiply(0.99f/0.9f));
		super.tick();
	}

	public void explode()
	{
		new NuclearExplosion(world, (int) getX(), (int) getY(), (int) getZ(), RivalRebels.b83Strength/2);
		EntitySphereBlast etb = new EntitySphereBlast(world, getX(), getY(), getZ(), RivalRebels.b83Strength * 1.333333333f);
		etb.time = -920;
		world.spawnEntity(etb);
		this.kill();
	}
}
