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

import io.github.kadir1243.rivalrebels.common.entity.EntityBlood;
import io.github.kadir1243.rivalrebels.common.entity.EntityGoo;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityGore;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
import org.jetbrains.annotations.Nullable;

public class BlockGore extends BaseEntityBlock {
    public static final MapCodec<BlockGore> CODEC = simpleCodec(BlockGore::new);
    public static final IntegerProperty META = IntegerProperty.create("meta", 0, 5);
    public static final BooleanProperty IS_UP_FULL = BooleanProperty.create("is_up_full");
    public static final BooleanProperty IS_DOWN_FULL = BooleanProperty.create("is_down_full");
    public static final BooleanProperty IS_NORTH_FULL = BooleanProperty.create("is_north_full");
    public static final BooleanProperty IS_SOUTH_FULL = BooleanProperty.create("is_south_full");
    public static final BooleanProperty IS_WEST_FULL = BooleanProperty.create("is_west_full");
    public static final BooleanProperty IS_EAST_FULL = BooleanProperty.create("is_east_full");

	public BlockGore(Properties settings) {
		super(settings);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(IS_UP_FULL, false)
                .setValue(IS_DOWN_FULL, false)
                .setValue(IS_NORTH_FULL, false)
                .setValue(IS_SOUTH_FULL, false)
                .setValue(IS_WEST_FULL, false)
                .setValue(IS_EAST_FULL, false)
            .setValue(META, 0));
    }

    @Override
    protected MapCodec<BlockGore> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(META, IS_UP_FULL,
            IS_DOWN_FULL,
            IS_NORTH_FULL,
            IS_SOUTH_FULL,
            IS_WEST_FULL,
            IS_EAST_FULL);
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

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!Block.canSupportCenter(world, pos.below(), Direction.UP) ||
            !Block.canSupportCenter(world, pos.east(), Direction.WEST) ||
            !Block.canSupportCenter(world, pos.west(), Direction.EAST) ||
            !Block.canSupportCenter(world, pos.south(), Direction.NORTH) ||
            !Block.canSupportCenter(world, pos.north(), Direction.SOUTH) ||
            !Block.canSupportCenter(world, pos.above(), Direction.DOWN)) {
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		}
	}

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityGore(pos, state);
	}

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return updateState(context.getLevel(), context.getClickedPos(), super.getStateForPlacement(context));
    }

    private static BlockState updateState(BlockGetter level, BlockPos pos, BlockState oldState) {
        return oldState
            .setValue(IS_NORTH_FULL, isFaceFull(level, pos, Direction.NORTH))
            .setValue(IS_SOUTH_FULL, isFaceFull(level, pos, Direction.SOUTH))
            .setValue(IS_UP_FULL, isFaceFull(level, pos, Direction.UP))
            .setValue(IS_DOWN_FULL, isFaceFull(level, pos, Direction.DOWN))
            .setValue(IS_EAST_FULL, isFaceFull(level, pos, Direction.EAST))
            .setValue(IS_WEST_FULL, isFaceFull(level, pos, Direction.WEST));
    }

    private static boolean isFaceFull(BlockGetter world, BlockPos pos, Direction direction) {
        return world.getBlockState(pos.relative(direction)).isFaceSturdy(world, pos.relative(direction), direction.getOpposite());
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, orientation, movedByPiston);
        BlockState updatedState = updateState(level, pos, state);
        if (!updatedState.toString().equals(state.toString())) {
            level.setBlockAndUpdate(pos, updatedState);
        }
    }

    /*@OnlyIn(Dist.CLIENT)
	IIcon	icon;
	@OnlyIn(Dist.CLIENT)
	IIcon	icon2;
	@OnlyIn(Dist.CLIENT)
	IIcon	icon3;
	@OnlyIn(Dist.CLIENT)
	IIcon	icon4;
	@OnlyIn(Dist.CLIENT)
	IIcon	icon5;
	@OnlyIn(Dist.CLIENT)
	IIcon	icon6;

	@Override
	public final IIcon getIcon(int side, int meta)
	{
		if (meta == 0) return icon;
		if (meta == 1) return icon2;
		if (meta == 2) return icon3;
		if (meta == 3) return icon4;
		if (meta == 4) return icon5;
		if (meta == 5) return icon6;
		else
		{
			return icon;
		}
	}

	@Override
	public void registerBlockIcons(IIconRegister iconregister)
	{
		icon = iconregister.registerIcon("RivalRebels:br");
		icon2 = iconregister.registerIcon("RivalRebels:bs");
		icon3 = iconregister.registerIcon("RivalRebels:bt");
		icon4 = iconregister.registerIcon("RivalRebels:bu");
		icon5 = iconregister.registerIcon("RivalRebels:bv");
		icon6 = iconregister.registerIcon("RivalRebels:bw");
	}*/
}
