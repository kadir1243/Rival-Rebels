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
import net.minecraft.util.math.Vec3d;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityNuclearBlast;
import assets.rivalrebels.common.entity.EntityPlasmoid;
import assets.rivalrebels.common.entity.EntityRhodes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMeltDown extends TileEntity implements ITickable
{
	public float	size		= 0;
	float			increment	= 0.075f;
	float			prevsize	= 0;

    @Override
	public void update()
	{
		prevsize = size;
		size += increment;
		if (prevsize == 0)
		{
			RivalRebelsSoundPlayer.playSound(world, 16, 0, getPos(), 4);
		}
		if (size > 9.3f)
		{
			size = 0f;
			world.setBlockToAir(getPos());
			this.invalidate();
		}

		double fsize = Math.sin(size) * 5.9;
		fsize *= 2;
		List<Entity> l = this.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(getPos().getX() - fsize + 0.5, getPos().getY() - fsize + 0.5, getPos().getZ() - fsize + 0.5, getPos().getX() + fsize + 0.5, getPos().getY() + fsize + 0.5, getPos().getZ() + fsize + 0.5));
        for (Entity e : l) {
            double var13 = e.getDistance(getPos().getX(), getPos().getY(), getPos().getZ()) / fsize;

            if (var13 <= 1.0D) {
                double var15 = e.posX - getPos().getX();
                double var17 = e.posY + e.getEyeHeight() - getPos().getY();
                double var19 = e.posZ - getPos().getZ();
                double var33 = MathHelper.sqrt(var15 * var15 + var17 * var17 + var19 * var19);

                if (var33 != 0.0D) {
                    var15 /= var33;
                    var17 /= var33;
                    var19 /= var33;
                    double var32 = world.getBlockDensity(new Vec3d(getPos()), e.getEntityBoundingBox());
                    double var34 = (1.0D - var13) * var32;
                    if (!(e instanceof EntityNuclearBlast) && !(e instanceof EntityPlasmoid) && !(e instanceof EntityRhodes)) {
                        e.attackEntityFrom(RivalRebelsDamageSource.plasmaexplosion, (int) ((var34 * var34 + var34) / 16.0D * fsize + 1.0D));
                        e.motionX += var15 * var34 * 4;
                        e.motionY += var17 * var34 * 4;
                        e.motionZ += var19 * var34 * 4;
                    }
                }
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
