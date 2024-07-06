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

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.common.block.autobuilds.BlockAutoTemplate;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.tileentity.Tickable;
import assets.rivalrebels.common.tileentity.TileEntityNuclearBomb;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BlockNuclearBomb extends BaseEntityBlock {
    public static final MapCodec<BlockNuclearBomb> CODEC = simpleCodec(BlockNuclearBomb::new);
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

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
        BlockPos pos = ctx.getClickedPos();
        Player placer = ctx.getPlayer();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
		if (Mth.abs((float) placer.getX() - x) < 2.0F && Mth.abs((float) placer.getZ() - z) < 2.0F)
		{
			double var5 = placer.getY() + 1.82D - placer.getVehicleAttachmentPoint(placer).y();

			if (var5 - y > 2.0D)
			{
				return super.getStateForPlacement(ctx).setValue(FACING, Direction.UP);
			}

			if (y - var5 > 0.0D)
			{
                return super.getStateForPlacement(ctx).setValue(FACING, Direction.DOWN);
			}
		}
		return super.getStateForPlacement(ctx).setValue(FACING, ctx.getHorizontalDirection());
	}

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (!player.isShiftKeyDown()) {
            if (!stack.isEmpty() && stack.is(RRItems.pliers)) {
                player.openMenu((MenuProvider) level.getBlockEntity(pos));
                return ItemInteractionResult.sidedSuccess(level.isClientSide());
			} else if (!level.isClientSide()) {
				player.displayClientMessage(RRIdentifiers.orders().append(" ").append(Component.translatable(BlockAutoTemplate.USE_PLIERS_TO_OPEN_TRANSLATION.toLanguageKey()).withStyle(ChatFormatting.RED)), true);
                return ItemInteractionResult.FAIL;
			}
		}
		return ItemInteractionResult.FAIL;
	}

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityNuclearBomb(pos, state);
	}

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> ((Tickable) blockEntity).tick();
    }
}
