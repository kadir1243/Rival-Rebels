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
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.tileentity.Tickable;
import assets.rivalrebels.common.tileentity.TileEntityOmegaObjective;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlockOmegaObjective extends BlockWithEntity {
	public BlockOmegaObjective(Settings settings) {
		super(settings);
	}

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (!pos.equals(RivalRebels.round.omegaObjPos)) {
			world.setBlockState(RivalRebels.round.omegaObjPos, RRBlocks.plasmaexplosion.getDefaultState());
			RivalRebels.round.omegaObjPos = pos;
			if (world.getBlockState(RivalRebels.round.sigmaObjPos).getBlock() == RRBlocks.sigmaobj)
				RivalRebels.round.roundManualStart();
		}
	}

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);

        if (state.getBlock() != RRBlocks.plasmaexplosion) {
            world.setBlockState(pos, state);
        }
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockView world, BlockPos pos, PlayerEntity player) {
        return player.getAbilities().creativeMode || super.canHarvestBlock(state, world, pos, player);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		RivalRebelsSoundPlayer.playSound(world, 10, 3, pos);

		return ActionResult.success(world.isClient);
	}

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityOmegaObjective(pos, state);
	}
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> ((Tickable) blockEntity).tick();
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
		icon = iconregister.registerIcon("RivalRebels:ba");
	}*/
}
