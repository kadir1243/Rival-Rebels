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
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

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
			RivalRebelsSoundPlayer.playSound(level, 16, 0, getBlockPos(), 4);
		}
		if (size > 9.3f)
		{
			size = 0f;
			level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
			this.setRemoved();
		}

		float fsize = Mth.sin(size) * 5.9F;
		fsize *= 2F;
		List<Entity> l = this.level.getEntities(null, new AABB(getBlockPos().getX() - fsize + 0.5, getBlockPos().getY() - fsize + 0.5, getBlockPos().getZ() - fsize + 0.5, getBlockPos().getX() + fsize + 0.5, getBlockPos().getY() + fsize + 0.5, getBlockPos().getZ() + fsize + 0.5));
        for (Entity e : l) {
            double var13 = Math.sqrt(e.distanceToSqr(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ())) / fsize;

            if (var13 <= 1.0D) {
                double var15 = e.getX() - getBlockPos().getX();
                double var17 = e.getY() + e.getEyeHeight(e.getPose()) - getBlockPos().getY();
                double var19 = e.getZ() - getBlockPos().getZ();
                double var33 = Math.sqrt(var15 * var15 + var17 * var17 + var19 * var19);

                if (var33 != 0.0D) {
                    var15 /= var33;
                    var17 /= var33;
                    var19 /= var33;
                    double var32 = net.minecraft.world.level.Explosion.getSeenPercent(Vec3.atLowerCornerOf(getBlockPos()), e);
                    double var34 = (1.0D - var13) * var32;
                    if (!(e instanceof EntityNuclearBlast) && !(e instanceof EntityPlasmoid) && !(e instanceof EntityRhodes)) {
                        e.hurt(RivalRebelsDamageSource.plasmaExplosion(level), (int) ((var34 * var34 + var34) / 16.0D * fsize + 1.0D));
                        e.push(
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
