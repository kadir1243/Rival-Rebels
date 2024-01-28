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
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class BlockToxicGas extends Block
{
	public BlockToxicGas(Settings settings) {
		super(settings);
	}

    @Override
    public int getFlammability(BlockState state, BlockView world, BlockPos pos, Direction face) {
        return 300;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(new Box(pos, pos));
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity living) {
			living.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 200, 0));
			living.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0));
			living.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 80, 0));
			living.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 80, 0));
		}
		if (entity instanceof PathAwareEntity || entity instanceof AnimalEntity || entity instanceof PlayerEntity) {
			entity.damage(RivalRebelsDamageSource.gasgrenade, 1);
		}
	}

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		world.createAndScheduleBlockTick(pos, this, 8);
	}

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		world.createAndScheduleBlockTick(pos, this, 8);
		if (random.nextInt(25) == 1) {
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        world.addParticle(ParticleTypes.SMOKE, x + 0.5, y + 0.5, z + 0.5, (random.nextFloat() - 0.5) * 0.1, (random.nextFloat() - 0.5) * 0.1, (random.nextFloat() - 0.5) * 0.1);
		world.addParticle(ParticleTypes.ENCHANT, x + 0.5, y + 0.5, z + 0.5, (random.nextFloat() - 0.5) * 0.1, (random.nextFloat() - 0.5) * 0.1, (random.nextFloat() - 0.5) * 0.1);
	}
}
