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

import io.github.kadir1243.rivalrebels.RivalRebels;
import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.item.ItemChip;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsTeam;
import io.github.kadir1243.rivalrebels.common.tileentity.Tickable;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityForceFieldNode;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class BlockForceFieldNode extends BaseEntityBlock {
    public static final MapCodec<BlockForceFieldNode> CODEC = simpleCodec(BlockForceFieldNode::new);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
	public BlockForceFieldNode(Properties settings)
	{
		super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<BlockForceFieldNode> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		BlockEntity te = level.getBlockEntity(pos);
		if (te instanceof TileEntityForceFieldNode teffn && !level.isClientSide()) {
			if (stack.is(RRItems.chip) && teffn.owner == null && teffn.rrteam == RivalRebelsTeam.NONE)
			{
				teffn.rrteam = RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile()).rrteam;
				if (teffn.rrteam == RivalRebelsTeam.NONE) {
					teffn.owner = ResolvableProfile.createResolved(player.getGameProfile());
				}

                player.playSound(RRSounds.GUI_UNKNOWN6.get());
			}
		}
		return InteractionResult.SUCCESS;
	}

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return super.getStateForPlacement(ctx).setValue(FACING, ctx.getHorizontalDirection());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> ((Tickable) blockEntity).tick();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityForceFieldNode(pos, state);
	}
}
