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
package assets.rivalrebels.common.tileentity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityNuclearBlast;
import assets.rivalrebels.common.entity.EntityPlasmoid;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.entity.EntityTsarBlast;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityPlasmaExplosion extends TileEntity implements ITickable
{
	public float	size		= 0;
	float			increment	= 0.3f;
	float			prevsize	= 0;

	public TileEntityPlasmaExplosion()
	{

	}

	/**
	 * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count ticks and creates a new spawn inside its implementation.
	 */
	@Override
	public void update()
	{
		prevsize = size;
		size += increment;
		if (prevsize == 0)
		{
			RivalRebelsSoundPlayer.playSound(world, 16, 0, getPos(), 4);
		}
		if (size > 3.1f)
		{
			size = 0f;
			world.setBlockToAir(getPos());
			this.invalidate();
		}

		double fsize = Math.sin(size) * 5.9 * 2;
		double fsqr = fsize * fsize;
		List<Entity> l = this.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(getPos().getX() - fsize + 0.5, getPos().getY() - fsize + 0.5, getPos().getZ() - fsize + 0.5, getPos().getX() + fsize + 0.5, getPos().getY() + fsize + 0.5, getPos().getZ() + fsize + 0.5));
        for (Entity e : l) {
            double var15 = e.posX - getPos().getX();
            double var17 = e.posY + e.getEyeHeight() - getPos().getY() + 1.5f;
            double var19 = e.posZ - getPos().getZ();
            double dist = 0.5f / (MathHelper.sqrt(var15 * var15 + var17 * var17 + var19 * var19) + 0.01f);
            if (dist <= 0.5f && !(e instanceof EntityNuclearBlast) && !(e instanceof EntityPlasmoid) && !(e instanceof EntityTsarBlast) && !(e instanceof EntityRhodes)) {
                e.attackEntityFrom(RivalRebelsDamageSource.plasmaexplosion, 2);
                e.motionX += var15 * dist;
                e.motionY += var17 * dist;
                e.motionZ += var19 * dist;
            }
        }
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return new AxisAlignedBB(getPos().add(-2, -2, -2), getPos().add(3, 3, 3));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 16384.0D;
	}
}
