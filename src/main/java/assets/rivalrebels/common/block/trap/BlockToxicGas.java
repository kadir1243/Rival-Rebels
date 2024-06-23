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

import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockToxicGas extends Block {
	public BlockToxicGas(Properties settings) {
		super(settings);

        ((FireBlock) Blocks.FIRE).setFlammable(this, 60, 100);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity living) {
			living.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0));
			living.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
			living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80, 0));
			living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 0));
		}
		if (entity instanceof PathfinderMob || entity instanceof Animal || entity instanceof Player) {
			entity.hurt(RivalRebelsDamageSource.gasGrenade(world), 1);
		}
	}

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
		world.scheduleTick(pos, this, 8);
	}

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		world.scheduleTick(pos, this, 8);
		if (random.nextInt(25) == 1) {
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		}
	}

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        world.addParticle(ParticleTypes.SMOKE, x + 0.5, y + 0.5, z + 0.5, (random.nextFloat() - 0.5) * 0.1, (random.nextFloat() - 0.5) * 0.1, (random.nextFloat() - 0.5) * 0.1);
		world.addParticle(ParticleTypes.ENCHANT, x + 0.5, y + 0.5, z + 0.5, (random.nextFloat() - 0.5) * 0.1, (random.nextFloat() - 0.5) * 0.1, (random.nextFloat() - 0.5) * 0.1);
	}
}
