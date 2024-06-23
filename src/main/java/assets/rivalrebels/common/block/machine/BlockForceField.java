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

import net.minecraft.block.*;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class BlockForceField extends Block {
    public static final IntProperty META = IntProperty.of("meta", 0, 6);

    public BlockForceField(Settings settings) {
		super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(META, 0));
	}

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(META);
    }

    //@Override
    public Box getBoundingBox(BlockState state, BlockView source, BlockPos pos) {
		int var5 = state.get(META);
		float var6 = 0.4375f;

		if (var5 == 4) {
			return new Box(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6);
		} else if (var5 == 5) {
			return new Box(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6);
		} else if (var5 == 2) {
			return new Box(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F);
		} else if (var5 == 3) {
			return new Box(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F);
		}

        return new Box(0.0F, 0.0F, 0.4375f, 1.0F, 1.0F, 1.0F - 0.4375f);
	}

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        float var6 = 0.4375f;

        return switch (state.get(META)) {
            case 4 -> VoxelShapes.cuboid(new Box(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6));
            case 5 -> VoxelShapes.cuboid(new Box(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6));
            case 2 -> VoxelShapes.cuboid(new Box(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F));
            case 3 -> VoxelShapes.cuboid(new Box(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F));
            default -> super.getCollisionShape(state, world, pos, context);
        };
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		int var5 = state.get(META);
		float var6 = 0.4375f;

        return switch (var5) {
            case 4 -> VoxelShapes.cuboid(new Box(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6));
            case 5 -> VoxelShapes.cuboid(new Box(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6));
            case 2 -> VoxelShapes.cuboid(new Box(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F));
            case 3 -> VoxelShapes.cuboid(new Box(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F));
            default -> super.getOutlineShape(state, world, pos, context);
        };
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }
}
