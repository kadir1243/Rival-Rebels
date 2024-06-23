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
package assets.rivalrebels.common.block.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockForceField extends Block {
    public static final IntegerProperty META = IntegerProperty.create("meta", 0, 6);

    public BlockForceField(Properties settings) {
		super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(META, 0));
	}

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(META);
    }

    //@Override
    public AABB getBoundingBox(BlockState state, BlockGetter source, BlockPos pos) {
		int var5 = state.getValue(META);
		float var6 = 0.4375f;

		if (var5 == 4) {
			return new AABB(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6);
		} else if (var5 == 5) {
			return new AABB(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6);
		} else if (var5 == 2) {
			return new AABB(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F);
		} else if (var5 == 3) {
			return new AABB(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F);
		}

        return new AABB(0.0F, 0.0F, 0.4375f, 1.0F, 1.0F, 1.0F - 0.4375f);
	}

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        float var6 = 0.4375f;

        return switch (state.getValue(META)) {
            case 4 -> Shapes.create(new AABB(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6));
            case 5 -> Shapes.create(new AABB(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6));
            case 2 -> Shapes.create(new AABB(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F));
            case 3 -> Shapes.create(new AABB(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F));
            default -> super.getCollisionShape(state, world, pos, context);
        };
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		int var5 = state.getValue(META);
		float var6 = 0.4375f;

        return switch (var5) {
            case 4 -> Shapes.create(new AABB(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6));
            case 5 -> Shapes.create(new AABB(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6));
            case 2 -> Shapes.create(new AABB(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F));
            case 3 -> Shapes.create(new AABB(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F));
            default -> super.getShape(state, world, pos, context);
        };
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }
}
