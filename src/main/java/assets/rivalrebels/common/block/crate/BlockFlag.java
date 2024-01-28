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
package assets.rivalrebels.common.block.crate;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BlockFlag extends Block {
    public static final BooleanProperty UP = ConnectingBlock.UP;
    public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
    public static final BooleanProperty EAST = ConnectingBlock.EAST;
    public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
    public static final BooleanProperty WEST = ConnectingBlock.WEST;
    private static final VoxelShape UP_SHAPE = Block.createCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private final Map<BlockState, VoxelShape> field_26659;
    String texpath = "rivalrebels:";

    public BlockFlag(Settings settings, String name) {
        super(settings);
        texpath += name;
        this.setDefaultState(
            this.stateManager
                .getDefaultState()
                .with(UP, false)
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false)
        );
        this.field_26659 = ImmutableMap.copyOf(
            this.stateManager
                .getStates()
                .stream()
                .collect(Collectors.toMap(Function.identity(), BlockFlag::method_31018))
        );
    }

    private static VoxelShape method_31018(BlockState arg) {
        VoxelShape voxelshape = VoxelShapes.empty();
        if (arg.get(UP)) {
            voxelshape = UP_SHAPE;
        }

        if (arg.get(NORTH)) {
            voxelshape = VoxelShapes.union(voxelshape, SOUTH_SHAPE);
        }

        if (arg.get(SOUTH)) {
            voxelshape = VoxelShapes.union(voxelshape, NORTH_SHAPE);
        }

        if (arg.get(EAST)) {
            voxelshape = VoxelShapes.union(voxelshape, WEST_SHAPE);
        }

        if (arg.get(WEST)) {
            voxelshape = VoxelShapes.union(voxelshape, EAST_SHAPE);
        }

        return voxelshape;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
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
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return this.hasAdjacentBlocks(this.getPlacementShape(state, world, pos));
    }

    /*private boolean func_150093_a(BlockState p_150093_1_) {
        return p_150093_1_.isFullBlock();
    }*/
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.field_26659.get(state);
    }

    private boolean shouldHaveSide(BlockView world, BlockPos pos, Direction side) {
        if (side == Direction.DOWN) {
            return false;
        } else {
            BlockPos blockpos = pos.offset(side);
            if (VineBlock.shouldConnectTo(world, blockpos, side)) {
                return true;
            } else if (side.getAxis() == Direction.Axis.Y) {
                return false;
            } else {
                BooleanProperty booleanproperty = VineBlock.FACING_PROPERTIES.get(side);
                BlockState blockstate = world.getBlockState(pos.up());
                return blockstate.isOf(this) && blockstate.get(booleanproperty);
            }
        }
    }

    private BlockState getPlacementShape(BlockState state, BlockView world, BlockPos pos) {
        BlockPos blockpos = pos.up();
        if (state.get(UP)) {
            state = state.with(UP, VineBlock.shouldConnectTo(world, blockpos, Direction.DOWN));
        }

        BlockState blockstate = null;

        for (Direction direction : Direction.Type.HORIZONTAL) {
            BooleanProperty booleanproperty = VineBlock.getFacingProperty(direction);
            if (state.get(booleanproperty)) {
                boolean flag = this.shouldHaveSide(world, pos, direction);
                if (!flag) {
                    if (blockstate == null) {
                        blockstate = world.getBlockState(blockpos);
                    }

                    flag = blockstate.isOf(this) && blockstate.get(booleanproperty);
                }

                state = state.with(booleanproperty, flag);
            }
        }

        return state;
    }

    @Override
    public BlockState getStateForNeighborUpdate(
        BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos
    ) {
        if (direction == Direction.DOWN) {
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        } else {
            BlockState blockstate = this.getPlacementShape(state, world, pos);
            return !this.hasAdjacentBlocks(blockstate) ? Blocks.AIR.getDefaultState() : blockstate;
        }
    }

    private boolean hasAdjacentBlocks(BlockState state) {
        return this.getAdjacentBlockCount(state) > 0;
    }

    private int getAdjacentBlockCount(BlockState state) {
        int i = 0;

        for (BooleanProperty booleanproperty : VineBlock.FACING_PROPERTIES.values()) {
            if (state.get(booleanproperty)) {
                ++i;
            }
        }

        return i;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockstate = ctx.getWorld().getBlockState(ctx.getBlockPos());
        boolean flag = blockstate.isOf(this);
        BlockState blockstate1 = flag ? blockstate : this.getDefaultState();

        for(Direction direction : ctx.getPlacementDirections()) {
            if (direction != Direction.DOWN) {
                BooleanProperty booleanproperty = VineBlock.getFacingProperty(direction);
                boolean flag1 = flag && blockstate.get(booleanproperty);
                if (!flag1 && this.shouldHaveSide(ctx.getWorld(), ctx.getBlockPos(), direction)) {
                    return blockstate1.with(booleanproperty, true);
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
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        switch (rotation) {
            case CLOCKWISE_180:
                return state.with(NORTH, state.get(SOUTH)).with(EAST, state.get(WEST)).with(SOUTH, state.get(NORTH)).with(WEST, state.get(EAST));
            case COUNTERCLOCKWISE_90:
                return state.with(NORTH, state.get(EAST)).with(EAST, state.get(SOUTH)).with(SOUTH, state.get(WEST)).with(WEST, state.get(NORTH));
            case CLOCKWISE_90:
                return state.with(NORTH, state.get(WEST)).with(EAST, state.get(NORTH)).with(SOUTH, state.get(EAST)).with(WEST, state.get(SOUTH));
            default:
                return state;
        }
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        switch (mirror) {
            case LEFT_RIGHT:
                return state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
            case FRONT_BACK:
                return state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
            default:
                return super.mirror(state, mirror);
        }
    }
}
