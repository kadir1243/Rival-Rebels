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
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

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
			RivalRebelsSoundPlayer.playSound(level, 16, 0, getBlockPos(), 4);
		}
		if (size > 3.1f)
		{
			size = 0f;
			level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
			this.setRemoved();
		}

		float fsize = Mth.sin(size) * 5.9F * 2F;
		double fsqr = fsize * fsize;
		List<Entity> l = this.level.getEntities(null, new AABB(getBlockPos().getX() - fsize + 0.5, getBlockPos().getY() - fsize + 0.5, getBlockPos().getZ() - fsize + 0.5, getBlockPos().getX() + fsize + 0.5, getBlockPos().getY() + fsize + 0.5, getBlockPos().getZ() + fsize + 0.5));
        for (Entity e : l) {
            double var15 = e.getZ() - getBlockPos().getX();
            double var17 = e.getEyeY() - getBlockPos().getY() + 1.5f;
            double var19 = e.getZ() - getBlockPos().getZ();
            double dist = 0.5f / (Math.sqrt(var15 * var15 + var17 * var17 + var19 * var19) + 0.01f);
            if (dist <= 0.5f && !(e instanceof EntityNuclearBlast) && !(e instanceof EntityPlasmoid) && !(e instanceof EntityTsarBlast) && !(e instanceof EntityRhodes)) {
                e.hurt(RivalRebelsDamageSource.plasmaExplosion(level), 2);
                e.push(
                    var15 * dist,
                    var17 * dist,
                    var19 * dist);
            }
        }
	}
}
