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

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
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
import org.jetbrains.annotations.Nullable;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BlockFlag extends Block {
    public static final BooleanProperty UP = PipeBlock.UP;
    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    private static final VoxelShape UP_SHAPE = Block.box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape EAST_SHAPE = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape WEST_SHAPE = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape SOUTH_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape NORTH_SHAPE = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private final Map<BlockState, VoxelShape> field_26659;
    String texpath = "rivalrebels:";

    public BlockFlag(Properties settings, String name) {
        super(settings);
        texpath += name;
        this.registerDefaultState(
            this.stateDefinition
                .any()
                .setValue(UP, false)
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
        );
        this.field_26659 = ImmutableMap.copyOf(
            this.stateDefinition
                .getPossibleStates()
                .stream()
                .collect(Collectors.toMap(Function.identity(), BlockFlag::method_31018))
        );
    }

    private static VoxelShape method_31018(BlockState arg) {
        VoxelShape voxelshape = Shapes.empty();
        if (arg.getValue(UP)) {
            voxelshape = UP_SHAPE;
        }

        if (arg.getValue(NORTH)) {
            voxelshape = Shapes.or(voxelshape, SOUTH_SHAPE);
        }

        if (arg.getValue(SOUTH)) {
            voxelshape = Shapes.or(voxelshape, NORTH_SHAPE);
        }

        if (arg.getValue(EAST)) {
            voxelshape = Shapes.or(voxelshape, WEST_SHAPE);
        }

        if (arg.getValue(WEST)) {
            voxelshape = Shapes.or(voxelshape, EAST_SHAPE);
        }

        return voxelshape;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP, NORTH, EAST, SOUTH, WEST);
    }

	/*@Override
	public void setBlockBoundsForItemRender()
	{
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public int getRenderType()
	{
		return 20;
	}*/

    /*@Override
    public Box getBoundingBox(BlockState state, BlockView worldIn, BlockPos pos) {
        int l = state.get(META);
        float f1 = 1.0F;
        float f2 = 1.0F;
        float f3 = 1.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        float f6 = 0.0F;
        boolean flag = l > 0;

        if ((l & 2) != 0) {
            f4 = Math.max(f4, 0.0625F);
            f1 = 0.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            f3 = 0.0F;
            f6 = 1.0F;
            flag = true;
        }

        if ((l & 8) != 0) {
            f1 = Math.min(f1, 0.9375F);
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            f3 = 0.0F;
            f6 = 1.0F;
            flag = true;
        }

        if ((l & 4) != 0) {
            f6 = Math.max(f6, 0.0625F);
            f3 = 0.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            flag = true;
        }

        if ((l & 1) != 0) {
            f3 = Math.min(f3, 0.9375F);
            f6 = 1.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            flag = true;
        }

        if (!flag && this.func_150093_a(worldIn.getBlockState(pos.up()))) {
            f2 = Math.min(f2, 0.9375F);
            f5 = 1.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            f3 = 0.0F;
            f6 = 1.0F;
        }

        return new Box(f1, f2, f3, f4, f5, f6);
    }*/

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return this.hasAdjacentBlocks(this.getPlacementShape(state, world, pos));
    }

    /*private boolean func_150093_a(BlockState p_150093_1_) {
        return p_150093_1_.isFullBlock();
    }*/
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.field_26659.get(state);
    }

    private boolean shouldHaveSide(BlockGetter world, BlockPos pos, Direction side) {
        if (side == Direction.DOWN) {
            return false;
        } else {
            BlockPos blockpos = pos.relative(side);
            if (VineBlock.isAcceptableNeighbour(world, blockpos, side)) {
                return true;
            } else if (side.getAxis() == Direction.Axis.Y) {
                return false;
            } else {
                BooleanProperty booleanproperty = VineBlock.PROPERTY_BY_DIRECTION.get(side);
                BlockState blockstate = world.getBlockState(pos.above());
                return blockstate.is(this) && blockstate.getValue(booleanproperty);
            }
        }
    }

    private BlockState getPlacementShape(BlockState state, BlockGetter world, BlockPos pos) {
        BlockPos blockpos = pos.above();
        if (state.getValue(UP)) {
            state = state.setValue(UP, VineBlock.isAcceptableNeighbour(world, blockpos, Direction.DOWN));
        }

        BlockState blockstate = null;

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BooleanProperty booleanproperty = VineBlock.getPropertyForFace(direction);
            if (state.getValue(booleanproperty)) {
                boolean flag = this.shouldHaveSide(world, pos, direction);
                if (!flag) {
                    if (blockstate == null) {
                        blockstate = world.getBlockState(blockpos);
                    }

                    flag = blockstate.is(this) && blockstate.getValue(booleanproperty);
                }

                state = state.setValue(booleanproperty, flag);
            }
        }

        return state;
    }

    @Override
    public BlockState updateShape(
        BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos
    ) {
        if (direction == Direction.DOWN) {
            return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
        } else {
            BlockState blockstate = this.getPlacementShape(state, world, pos);
            return !this.hasAdjacentBlocks(blockstate) ? Blocks.AIR.defaultBlockState() : blockstate;
        }
    }

    private boolean hasAdjacentBlocks(BlockState state) {
        return this.getAdjacentBlockCount(state) > 0;
    }

    private int getAdjacentBlockCount(BlockState state) {
        int i = 0;

        for (BooleanProperty booleanproperty : VineBlock.PROPERTY_BY_DIRECTION.values()) {
            if (state.getValue(booleanproperty)) {
                ++i;
            }
        }

        return i;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockstate = ctx.getLevel().getBlockState(ctx.getClickedPos());
        boolean flag = blockstate.is(this);
        BlockState blockstate1 = flag ? blockstate : this.defaultBlockState();

        for(Direction direction : ctx.getNearestLookingDirections()) {
            if (direction != Direction.DOWN) {
                BooleanProperty booleanproperty = VineBlock.getPropertyForFace(direction);
                boolean flag1 = flag && blockstate.getValue(booleanproperty);
                if (!flag1 && this.shouldHaveSide(ctx.getLevel(), ctx.getClickedPos(), direction)) {
                    return blockstate1.setValue(booleanproperty, true);
                }
            }
        }

        return flag ? blockstate1 : null;
    }

	/*@OnlyIn(Dist.CLIENT)
	IIcon	icon;

	@Override
	public final IIcon getIcon(int side, int meta)
	{
		return icon;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconregister)
	{
		icon = iconregister.registerIcon(texpath);
	}*/

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
}
