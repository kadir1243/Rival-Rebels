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
package io.github.kadir1243.rivalrebels.common.block.trap;

import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.tileentity.RRTileEntities;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityNuclearBomb;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.*;
import org.joml.Vector3f;
import org.jspecify.annotations.Nullable;

public class BlockNuclearBomb extends BaseEntityBlock {
    public static final MapCodec<BlockNuclearBomb> CODEC = simpleCodec(BlockNuclearBomb::new);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;

	public BlockNuclearBomb(Properties settings) {
		super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.DOWN));
    }

    @Override
    protected MapCodec<BlockNuclearBomb> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return super.getStateForPlacement(ctx).setValue(FACING, ctx.getNearestLookingDirection());
	}

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        Vector3f step = direction.step();
        float stepX = Mth.abs(step.x());
        float stepY = Mth.abs(step.y());
        float stepZ = Mth.abs(step.z());
        return Shapes.create(-stepX, -stepY, -stepZ, 1 + stepX, 1 + stepY, 1 + stepZ);
    }

    @Override
    protected VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getShape(level, pos);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (!player.isShiftKeyDown()) {
            if (stack.is(RRItems.pliers)) {
                if (!level.isClientSide()) {
                    player.openMenu(state.getMenuProvider(level, pos));
                }
                return InteractionResult.SUCCESS;
            } else {
                if (!level.isClientSide()) {
                    player.sendSystemMessage(Translations.orders().append(" ").append(Translations.USE_PLIERS_TO_OPEN_TRANSLATION.translate().withStyle(ChatFormatting.RED)));
                }
                return InteractionResult.FAIL;
            }
		}
		return InteractionResult.PASS;
	}

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityNuclearBomb(pos, state);
	}

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, RRTileEntities.NUCLEAR_BOMB.get(), TileEntityNuclearBomb::tick);
    }
}
