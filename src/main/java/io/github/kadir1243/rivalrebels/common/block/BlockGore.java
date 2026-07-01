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
package io.github.kadir1243.rivalrebels.common.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import io.github.kadir1243.rivalrebels.common.entity.EntityBlood;
import io.github.kadir1243.rivalrebels.common.entity.EntityGoo;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityGore;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.function.Function;

public class BlockGore extends BaseEntityBlock {
    public static final MapCodec<BlockGore> CODEC = simpleCodec(BlockGore::new);
    public static final IntegerProperty META = IntegerProperty.create("meta", 0, 5);
    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    public static final BooleanProperty UP = PipeBlock.UP;
    public static final BooleanProperty DOWN = PipeBlock.DOWN;
    public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = ImmutableMap.copyOf(
        Maps.newEnumMap(
            Map.of(Direction.NORTH, NORTH, Direction.EAST, EAST, Direction.SOUTH, SOUTH, Direction.WEST, WEST, Direction.UP, UP, Direction.DOWN, DOWN)
        )
    );
    private final Function<BlockState, VoxelShape> shapes;

	public BlockGore(Properties settings) {
		super(settings);
        this.registerDefaultState(this.getStateDefinition().any()
            .setValue(UP, false)
            .setValue(DOWN, false)
            .setValue(NORTH, false)
            .setValue(SOUTH, false)
            .setValue(WEST, false)
            .setValue(EAST, false)
            .setValue(META, 0));
        this.shapes = this.makeShapes();
    }

    @Override
    protected MapCodec<BlockGore> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(META, UP, DOWN, NORTH, SOUTH, WEST, EAST);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (player.isCreative()) {
			int meta = state.getValue(META) + 1;
			if (meta >= 6) meta = 0;
			level.setBlockAndUpdate(pos, state.setValue(META, meta));
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.FAIL;
	}

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (world.isEmptyBlock(pos.below()) && state.getValue(META) < 2) world.addFreshEntity(new EntityBlood(world, pos.getX() + random.nextDouble(), pos.getY() + 0.9f, pos.getZ() + random.nextDouble()));
		else if (world.isEmptyBlock(pos.below()) && state.getValue(META) < 4) world.addFreshEntity(new EntityGoo(world, pos.getX() + random.nextDouble(), pos.getY() + 0.9f, pos.getZ() + random.nextDouble()));
	}

    public static boolean isAcceptableNeighbour(BlockGetter level, BlockPos neighbourPos, Direction directionToNeighbour) {
        return MultifaceBlock.canAttachTo(level, directionToNeighbour, neighbourPos, level.getBlockState(neighbourPos));
    }

    private Function<BlockState, VoxelShape> makeShapes() {
        Map<Direction, VoxelShape> shapes = Shapes.rotateAll(Block.boxZ(16.0, 0.0, 1.0));
        return this.getShapeForEachState(state -> {
            VoxelShape shape = Shapes.empty();

            for (Map.Entry<Direction, BooleanProperty> entry : PROPERTY_BY_DIRECTION.entrySet()) {
                if (state.getValue(entry.getValue())) {
                    shape = Shapes.or(shape, shapes.get(entry.getKey()));
                }
            }

            return shape.isEmpty() ? Shapes.block() : shape;
        });
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.shapes.apply(state);
    }

    private BlockState getUpdatedState(BlockState state, BlockGetter level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BooleanProperty property = getPropertyForFace(direction);
            boolean canSupport = isAcceptableNeighbour(level, pos.relative(direction), direction);

            state = state.setValue(property, canSupport);
        }

        return state;
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, block, orientation, movedByPiston);
        level.setBlockAndUpdate(pos, this.getUpdatedState(state, level, pos));
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return this.hasFaces(this.getUpdatedState(state, level, pos));
    }

    private boolean hasFaces(BlockState blockState) {
        return this.countFaces(blockState) > 0;
    }

    private int countFaces(BlockState blockState) {
        int count = 0;

        for (BooleanProperty property : PROPERTY_BY_DIRECTION.values()) {
            if (blockState.getValue(property)) {
                count++;
            }
        }

        return count;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityGore(pos, state);
	}

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.getUpdatedState(super.getStateForPlacement(context), context.getLevel(), context.getClickedPos());
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    public static BooleanProperty getPropertyForFace(Direction direction) {
        return PROPERTY_BY_DIRECTION.get(direction);
    }
}
