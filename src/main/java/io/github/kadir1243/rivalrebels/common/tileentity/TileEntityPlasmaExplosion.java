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
package io.github.kadir1243.rivalrebels.common.tileentity;

import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource;
import io.github.kadir1243.rivalrebels.common.entity.EntityNuclearBlast;
import io.github.kadir1243.rivalrebels.common.entity.EntityPlasmoid;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodes;
import io.github.kadir1243.rivalrebels.common.entity.EntityTsarBlast;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TileEntityPlasmaExplosion extends BlockEntity {
	public float	size		= 0;
	private static final float increment = 0.3f;
	public float prevsize = 0;

	public TileEntityPlasmaExplosion(BlockPos pos, BlockState state) {
        super(RRTileEntities.PLASMA_EXPLOSION.get(), pos, state);
	}

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, TileEntityPlasmaExplosion blockEntity) {
		blockEntity.prevsize = blockEntity.size;
		blockEntity.size += increment;
		if (blockEntity.prevsize == 0)
		{
            level.playSound(null, blockPos, RRSounds.PLASMA.get(), SoundSource.BLOCKS, 4, 1);
		}
		if (blockEntity.size > 3.1f)
		{
            blockEntity.size = 0f;
			level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
            blockEntity.setRemoved();
		}

		float fsize = Mth.sin(blockEntity.size) * 5.9F * 2F;
		double fsqr = fsize * fsize;
		List<Entity> l = level.getEntities(null, new AABB(blockPos.getX() - fsize + 0.5, blockPos.getY() - fsize + 0.5, blockPos.getZ() - fsize + 0.5, blockPos.getX() + fsize + 0.5, blockPos.getY() + fsize + 0.5, blockPos.getZ() + fsize + 0.5));
        for (Entity e : l) {
            double var15 = e.getZ() - blockPos.getX();
            double var17 = e.getEyeY() - blockPos.getY() + 1.5f;
            double var19 = e.getZ() - blockPos.getZ();
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
