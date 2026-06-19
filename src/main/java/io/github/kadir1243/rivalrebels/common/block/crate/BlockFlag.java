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
package io.github.kadir1243.rivalrebels.common.block.crate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;
import java.util.Map;
import java.util.function.Function;

public class BlockFlag extends Block {
    public static final BooleanProperty UP = PipeBlock.UP;
    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    private final Function<BlockState, VoxelShape> shapes;

    public BlockFlag(Properties settings) {
        super(settings);
        this.registerDefaultState(
            this.stateDefinition
                .any()
                .setValue(UP, false)
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
        );
        this.shapes = this.makeShapes();
    }

    private Function<BlockState, VoxelShape> makeShapes() {
        Map<Direction, VoxelShape> shapes = Shapes.rotateAll(Block.boxZ(16.0, 0.0, 1.0));
        return this.getShapeForEachState(state -> {
            VoxelShape shape = Shapes.empty();

            for (Map.Entry<Direction, BooleanProperty> entry : VineBlock.PROPERTY_BY_DIRECTION.entrySet()) {
                if (state.getValue(entry.getValue())) {
                    shape = Shapes.or(shape, shapes.get(entry.getKey()));
                }
            }

            return shape.isEmpty() ? Shapes.block() : shape;
        });
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return this.hasFaces(this.getUpdatedState(state, world, pos));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.shapes.apply(state);
    }

    private boolean canSupportAtFace(BlockGetter level, BlockPos pos, Direction direction) {
        if (direction == Direction.DOWN) {
            return false;
        } else {
            BlockPos relative = pos.relative(direction);
            if (VineBlock.isAcceptableNeighbour(level, relative, direction)) {
                return true;
            } else if (direction.getAxis() == Direction.Axis.Y) {
                return false;
            } else {
                BooleanProperty property = VineBlock.PROPERTY_BY_DIRECTION.get(direction);
                BlockState aboveState = level.getBlockState(pos.above());
                return aboveState.is(this) && aboveState.getValue(property);
            }
        }
    }

    private BlockState getUpdatedState(BlockState state, BlockGetter level, BlockPos pos) {
        BlockPos abovePos = pos.above();
        if (state.getValue(UP)) {
            state = state.setValue(UP, VineBlock.isAcceptableNeighbour(level, abovePos, Direction.DOWN));
        }

        BlockState aboveState = null;

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BooleanProperty property = VineBlock.getPropertyForFace(direction);
            if (state.getValue(property)) {
                boolean canSupport = this.canSupportAtFace(level, pos, direction);
                if (!canSupport) {
                    if (aboveState == null) {
                        aboveState = level.getBlockState(abovePos);
                    }

                    canSupport = aboveState.is(this) && aboveState.getValue(property);
                }

                state = state.setValue(property, canSupport);
            }
        }

        return state;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        if (directionToNeighbour == Direction.DOWN) {
            return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
        } else {
            BlockState blockState = this.getUpdatedState(state, level, pos);
            return !hasFaces(blockState) ? Blocks.AIR.defaultBlockState() : blockState;
        }
    }

    private boolean hasFaces(BlockState blockState) {
        return this.countFaces(blockState) > 0;
    }

    private int countFaces(BlockState blockState) {
        int count = 0;

        for (BooleanProperty property : VineBlock.PROPERTY_BY_DIRECTION.values()) {
            if (blockState.getValue(property)) {
                count++;
            }
        }

        return count;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState clickedState = context.getLevel().getBlockState(context.getClickedPos());
        boolean clickedFlag = clickedState.is(this);
        BlockState result = clickedFlag ? clickedState : this.defaultBlockState();

        for(Direction direction : context.getNearestLookingDirections()) {
            if (direction != Direction.DOWN) {
                BooleanProperty face = VineBlock.getPropertyForFace(direction);
                boolean faceOccupied = clickedFlag && clickedState.getValue(face);
                if (!faceOccupied && this.canSupportAtFace(context.getLevel(), context.getClickedPos(), direction)) {
                    return result.setValue(face, true);
                }
            }
        }

        return clickedFlag ? result : null;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        switch (rotation) {
            case CLOCKWISE_180:
                return state.setValue(NORTH, state.getValue(SOUTH)).setValue(EAST, state.getValue(WEST)).setValue(SOUTH, state.getValue(NORTH)).setValue(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return state.setValue(NORTH, state.getValue(EAST)).setValue(EAST, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(WEST)).setValue(WEST, state.getValue(NORTH));
            case CLOCKWISE_90:
                return state.setValue(NORTH, state.getValue(WEST)).setValue(EAST, state.getValue(NORTH)).setValue(SOUTH, state.getValue(EAST)).setValue(WEST, state.getValue(SOUTH));
            default:
                return state;
        }
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        switch (mirror) {
            case LEFT_RIGHT:
                return state.setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH));
            case FRONT_BACK:
                return state.setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST));
            default:
                return super.mirror(state, mirror);
        }
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return true;
    }
}
