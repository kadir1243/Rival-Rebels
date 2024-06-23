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

import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityNuclearBlast;
import assets.rivalrebels.common.entity.EntityPlasmoid;
import assets.rivalrebels.common.entity.EntityRhodes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class TileEntityMeltDown extends BlockEntity implements Tickable
{
	public float	size		= 0;
	float			increment	= 0.075f;
	float			prevsize	= 0;

    public TileEntityMeltDown(BlockPos pos, BlockState state) {
        super(RRTileEntities.MELT_DOWN, pos, state);
    }

    @Override
	public void tick()
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
			world.setBlockState(getPos(), Blocks.AIR.getDefaultState());
			this.markRemoved();
		}

		double fsize = Math.sin(size) * 5.9;
		fsize *= 2;
		List<Entity> l = this.world.getOtherEntities(null, new Box(getPos().getX() - fsize + 0.5, getPos().getY() - fsize + 0.5, getPos().getZ() - fsize + 0.5, getPos().getX() + fsize + 0.5, getPos().getY() + fsize + 0.5, getPos().getZ() + fsize + 0.5));
        for (Entity e : l) {
            double var13 = Math.sqrt(e.squaredDistanceTo(getPos().getX(), getPos().getY(), getPos().getZ())) / fsize;

            if (var13 <= 1.0D) {
                double var15 = e.getX() - getPos().getX();
                double var17 = e.getY() + e.getEyeHeight(e.getPose()) - getPos().getY();
                double var19 = e.getZ() - getPos().getZ();
                double var33 = Math.sqrt(var15 * var15 + var17 * var17 + var19 * var19);

                if (var33 != 0.0D) {
                    var15 /= var33;
                    var17 /= var33;
                    var19 /= var33;
                    double var32 = net.minecraft.world.explosion.Explosion.getExposure(Vec3d.of(getPos()), e);
                    double var34 = (1.0D - var13) * var32;
                    if (!(e instanceof EntityNuclearBlast) && !(e instanceof EntityPlasmoid) && !(e instanceof EntityRhodes)) {
                        e.damage(RivalRebelsDamageSource.plasmaExplosion(world), (int) ((var34 * var34 + var34) / 16.0D * fsize + 1.0D));
                        e.addVelocity(
                            var15 * var34 * 4,
                            var17 * var34 * 4,
                            var19 * var34 * 4
                        );
                    }
                }
            }
        }
	}
}
