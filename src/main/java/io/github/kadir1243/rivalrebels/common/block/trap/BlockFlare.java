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
package io.github.kadir1243.rivalrebels.common.block.trap;

import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource;
import io.github.kadir1243.rivalrebels.common.explosion.Explosion;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockFlare extends WallTorchBlock {
	public BlockFlare(Properties settings) {
		super(ParticleTypes.LAVA, settings);
	}

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return canSupportCenter(world, pos.below(), Direction.UP);
	}

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
		world.addParticle(ParticleTypes.LAVA, x + .5, y + .6, z + .5, 0, 0.5, 0);
		world.addParticle(ParticleTypes.LAVA, x + .5, y + .8, z + .5, 0, 0.5, 0);
		world.addParticle(ParticleTypes.LAVA, x + .5, y + 1, z + .5, 0, 0.5, 0);
		world.addParticle(ParticleTypes.FLAME, x + .5, y + 1.2, z + .5, (-0.5 + random.nextFloat()) * 0.1, 0.5 + random.nextFloat() * 0.5, (-0.5 + random.nextFloat()) * 0.1);
		world.addParticle(ParticleTypes.FLAME, x + .5, y + 1.4, z + .5, (-0.5 + random.nextFloat()) * 0.1, 0.5 + random.nextFloat() * 0.5, (-0.5 + random.nextFloat()) * 0.1);
		world.addParticle(ParticleTypes.SMOKE, x + .5, y + 1.6, z + .5, (-0.5 + random.nextFloat()) * 0.1, 0.5 + random.nextFloat() * 0.5, (-0.5 + random.nextFloat()) * 0.1);
		world.playLocalSound(pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 3F, 2, true);
	}

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(world, pos, state, player);
		if (RRConfig.SERVER.isFlareExplodeOnBreak()) {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			new Explosion(world, x, y, z, 3, true, false, RivalRebelsDamageSource.flare(world));
			world.playLocalSound(pos, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 0.5f, 0.3f, true);
		}
        return state;
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
		entity.hurt(RivalRebelsDamageSource.flare(world), 1);
		entity.igniteForSeconds(5);
	}
    @Nullable
    private String translationKey;

    @Override
    public String getDescriptionId() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeDescriptionId("block", BuiltInRegistries.BLOCK.getKey(this));
        }

        return this.translationKey; // Direct copy of Block.getTranslationKey
    }
}
