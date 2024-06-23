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
package assets.rivalrebels.common.block.trap;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.explosion.Explosion;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class BlockFlare extends WallTorchBlock {
	public BlockFlare(Settings settings) {
		super(ParticleTypes.LAVA, settings);
	}

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return sideCoversSmallSquare(world, pos.down(), Direction.UP);
	}

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
		world.addParticle(ParticleTypes.LAVA, x + .5, y + .6, z + .5, 0, 0.5, 0);
		world.addParticle(ParticleTypes.LAVA, x + .5, y + .8, z + .5, 0, 0.5, 0);
		world.addParticle(ParticleTypes.LAVA, x + .5, y + 1, z + .5, 0, 0.5, 0);
		world.addParticle(ParticleTypes.FLAME, x + .5, y + 1.2, z + .5, (-0.5 + random.nextFloat()) * 0.1, 0.5 + random.nextFloat() * 0.5, (-0.5 + random.nextFloat()) * 0.1);
		world.addParticle(ParticleTypes.FLAME, x + .5, y + 1.4, z + .5, (-0.5 + random.nextFloat()) * 0.1, 0.5 + random.nextFloat() * 0.5, (-0.5 + random.nextFloat()) * 0.1);
		world.addParticle(ParticleTypes.SMOKE, x + .5, y + 1.6, z + .5, (-0.5 + random.nextFloat()) * 0.1, 0.5 + random.nextFloat() * 0.5, (-0.5 + random.nextFloat()) * 0.1);
		world.playSound(x, y, z, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 3F, 2, true);
	}

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
		if (RivalRebels.flareExplode) {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
			new Explosion(world, x, y, z, 3, true, false, RivalRebelsDamageSource.flare(world));
			world.playSound(x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 0.5f, 0.3f, true);
		}
        return state;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		entity.damage(RivalRebelsDamageSource.flare(world), 1);
		entity.setOnFireFor(5);
	}
    @Nullable
    private String translationKey;

    @Override
    public String getTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.createTranslationKey("block", Registries.BLOCK.getId(this));
        }

        return this.translationKey; // Direct copy of Block.getTranslationKey
    }
}
