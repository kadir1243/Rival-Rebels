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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.item.ItemChip;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import assets.rivalrebels.common.tileentity.Tickable;
import assets.rivalrebels.common.tileentity.TileEntityForceFieldNode;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
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

public class BlockForceFieldNode extends BaseEntityBlock {
    public static final MapCodec<BlockForceFieldNode> CODEC = simpleCodec(BlockForceFieldNode::new);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
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
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		BlockEntity te = level.getBlockEntity(pos);
		if (te instanceof TileEntityForceFieldNode teffn && !level.isClientSide()) {
			if (!stack.isEmpty() && stack.getItem() instanceof ItemChip && teffn.uuid == null && (teffn.rrteam == null || teffn.rrteam == RivalRebelsTeam.NONE))
			{
				teffn.rrteam = RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile()).rrteam;
				if (teffn.rrteam == RivalRebelsTeam.NONE || teffn.rrteam == null)
				{
					teffn.uuid = player.getGameProfile().getId();
					teffn.rrteam = null;
				}
				RivalRebelsSoundPlayer.playSound(level, 10, 5, pos);
			}
		}
		return ItemInteractionResult.sidedSuccess(level.isClientSide());
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

	/*@Environment(EnvType.CLIENT)
	IIcon	icon;
	@Environment(EnvType.CLIENT)
	IIcon	icon2;
	@Environment(EnvType.CLIENT)
	IIcon	icontop1;
	@Environment(EnvType.CLIENT)
	IIcon	icontop2;
	@Environment(EnvType.CLIENT)
	IIcon	icontop3;
	@Environment(EnvType.CLIENT)
	IIcon	icontop4;

	@Override
	public final IIcon getIcon(int side, int meta)
	{
		if (meta == 0)
		{
			if (side == 0 || side == 1) return icontop2;
			if (side == 4) return icon2;
			return icon;
		}
		if (side == 0 || side == 1) return meta == 3 ? icontop1 : meta == 4 ? icontop2 : meta == 2 ? icontop3 : icontop4;
		if (side == meta) return icon2;
		return icon;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconregister)
	{
		icon = iconregister.registerIcon("RivalRebels:cf");
		icon2 = iconregister.registerIcon("RivalRebels:cg");
		icontop1 = iconregister.registerIcon("RivalRebels:cj");
		icontop2 = iconregister.registerIcon("RivalRebels:ck");
		icontop3 = iconregister.registerIcon("RivalRebels:cl");
		icontop4 = iconregister.registerIcon("RivalRebels:cm");
	}*/
}
