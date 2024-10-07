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
package io.github.kadir1243.rivalrebels.common.block.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockForceField extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public BlockForceField(Properties settings) {
		super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.DOWN));
	}

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    //@Override
    public AABB getBoundingBox(BlockState state, BlockGetter source, BlockPos pos) {
        float var6 = 0.4375f;

        return switch (state.getValue(FACING).getAxis()) {
            case X -> new AABB(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6);
            case Z -> new AABB(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F);
            default -> new AABB(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6);
        };
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        float var6 = 0.4375f;

        return switch (state.getValue(FACING).getAxis()) {
            case X -> Shapes.create(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6);
            case Z -> Shapes.create(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F);
            default -> super.getCollisionShape(state, world, pos, context);
        };
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        float var6 = 0.4375f;

        return switch (state.getValue(FACING).getAxis()) {
            case X -> Shapes.create(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6);
            case Z -> Shapes.create(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F);
            default -> super.getShape(state, world, pos, context);
        };
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }
}
