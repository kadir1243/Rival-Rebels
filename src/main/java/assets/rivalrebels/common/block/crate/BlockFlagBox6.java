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

import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.util.Translations;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BlockFlagBox6 extends Block
{
	public BlockFlagBox6(Properties settings)
	{
		super(settings);
	}

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

		if (player.isShiftKeyDown() && !level.isClientSide())
		{
			ItemEntity ei = new ItemEntity(level, x + .5, y + .5, z + .5, new ItemStack(RRBlocks.flag6, 10));
			level.addFreshEntity(ei);
            level.setBlockAndUpdate(new BlockPos(x, y, z), Blocks.AIR.defaultBlockState());
			return InteractionResult.PASS;
		}
		if (!player.isShiftKeyDown() && !level.isClientSide())
		{
            player.displayClientMessage(Translations.orders().append(" ").append(Component.translatable(Translations.SHIFT_CLICK.toLanguageKey())), false);
			level.setBlockAndUpdate(new BlockPos(x, y, z), RRBlocks.flagbox1.defaultBlockState());
			return InteractionResult.PASS;
		}
		return InteractionResult.PASS;
	}
}
