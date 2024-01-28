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

import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.tileentity.Tickable;
import assets.rivalrebels.common.tileentity.TileEntityNuclearBomb;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlockNuclearBomb extends BlockWithEntity {
    public static final IntProperty META = IntProperty.of("meta", 0, 15);
	public BlockNuclearBomb(Settings settings)
	{
		super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(META, 0));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(META);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        PlayerEntity placer = ctx.getPlayer();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
		if (MathHelper.abs((float) placer.getX() - x) < 2.0F && MathHelper.abs((float) placer.getZ() - z) < 2.0F)
		{
			double var5 = placer.getY() + 1.82D - placer.getHeightOffset();

			if (var5 - y > 2.0D)
			{
				return super.getPlacementState(ctx).with(META, 1);
			}

			if (y - var5 > 0.0D)
			{
                return super.getPlacementState(ctx).with(META, 0);
			}
		}
		int var7 = MathHelper.floor((ctx.getPlayerYaw() * 4.0F / 360.0F) + 0.5D) & 3;
		return super.getPlacementState(ctx).with(META, var7 == 0 ? 2 : (var7 == 1 ? 5 : (var7 == 2 ? 3 : (var7 == 3 ? 4 : 0))));
	}

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!player.isSneaking()) {
            ItemStack stack = player.getStackInHand(hand);
            if (!stack.isEmpty() && stack.getItem() == RRItems.pliers) {
                player.openHandledScreen((NamedScreenHandlerFactory) world.getBlockEntity(pos));
                return ActionResult.SUCCESS;
			} else if (!world.isClient) {
				player.sendMessage(Text.of("§7[§4Orders§7] §cUse pliers to open."), true);
                return ActionResult.FAIL;
			}
		}
		return ActionResult.PASS;
	}

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityNuclearBomb(pos, state);
	}
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> ((Tickable) blockEntity).tick();
    }
}
