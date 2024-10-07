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
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TileEntityMeltDown extends BlockEntity implements Tickable {
    private static final float INCREMENT_AMOUNT = 0.075f;
	public float	size		= 0;

    public TileEntityMeltDown(BlockPos pos, BlockState state) {
        super(RRTileEntities.MELT_DOWN.get(), pos, state);
    }

    @Override
	public void tick() {
        if (size == 0) {
            level.playSound(null, getBlockPos(), RRSounds.PLASMA.get(), SoundSource.BLOCKS, 4, 1);
        }
		size += INCREMENT_AMOUNT;

		if (size > 9.3f) {
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
                Vec3 vec3 = e.getEyePosition().subtract(Vec3.atLowerCornerOf(getBlockPos()));

                if (vec3.length() != 0.0D) {
                    vec3 = vec3.normalize();
                    double var32 = net.minecraft.world.level.Explosion.getSeenPercent(Vec3.atLowerCornerOf(getBlockPos()), e);
                    double var34 = (1.0D - var13) * var32;
                    if (!(e instanceof EntityNuclearBlast) && !(e instanceof EntityPlasmoid) && !(e instanceof EntityRhodes)) {
                        e.hurt(RivalRebelsDamageSource.plasmaExplosion(level), (int) ((var34 * var34 + var34) / 16.0D * fsize + 1.0D));
                        e.push(vec3.scale(var34 * 4));
                    }
                }
            }
        }
	}
}
