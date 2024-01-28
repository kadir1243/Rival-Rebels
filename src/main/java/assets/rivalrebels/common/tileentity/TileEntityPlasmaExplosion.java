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
import assets.rivalrebels.common.entity.EntityTsarBlast;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.List;

public class TileEntityPlasmaExplosion extends BlockEntity implements Tickable
{
	public float	size		= 0;
	float			increment	= 0.3f;
	float			prevsize	= 0;

	public TileEntityPlasmaExplosion(BlockPos pos, BlockState state) {
        super(RRTileEntities.PLASMA_EXPLOSION, pos, state);
	}

    @Override
	public void tick() {
		prevsize = size;
		size += increment;
		if (prevsize == 0)
		{
			RivalRebelsSoundPlayer.playSound(world, 16, 0, getPos(), 4);
		}
		if (size > 3.1f)
		{
			size = 0f;
			world.setBlockState(getPos(), Blocks.AIR.getDefaultState());
			this.markRemoved();
		}

		double fsize = Math.sin(size) * 5.9 * 2;
		double fsqr = fsize * fsize;
		List<Entity> l = this.world.getOtherEntities(null, new Box(getPos().getX() - fsize + 0.5, getPos().getY() - fsize + 0.5, getPos().getZ() - fsize + 0.5, getPos().getX() + fsize + 0.5, getPos().getY() + fsize + 0.5, getPos().getZ() + fsize + 0.5));
        for (Entity e : l) {
            double var15 = e.getZ() - getPos().getX();
            double var17 = e.getZ() + e.getEyeHeight(e.getPose()) - getPos().getY() + 1.5f;
            double var19 = e.getZ() - getPos().getZ();
            double dist = 0.5f / (Math.sqrt(var15 * var15 + var17 * var17 + var19 * var19) + 0.01f);
            if (dist <= 0.5f && !(e instanceof EntityNuclearBlast) && !(e instanceof EntityPlasmoid) && !(e instanceof EntityTsarBlast) && !(e instanceof EntityRhodes)) {
                e.damage(RivalRebelsDamageSource.plasmaexplosion, 2);
                e.addVelocity(
                    var15 * dist,
                    var17 * dist,
                    var19 * dist);
            }
        }
	}

	@Override
	public Box getRenderBoundingBox()
	{
		return new Box(getPos().add(-2, -2, -2), getPos().add(3, 3, 3));
	}

}
