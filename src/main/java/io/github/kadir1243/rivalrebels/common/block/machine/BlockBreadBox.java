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

import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BlockBreadBox extends Block
{
	public BlockBreadBox(Properties settings)
	{
		super(settings);
	}

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (!player.isShiftKeyDown())
        {
            ItemEntity ei = new ItemEntity(level, x + .5, y + 1, z + .5, Items.BREAD.getDefaultInstance());
            if (!level.isClientSide())
            {
                level.addFreshEntity(ei);
                if (level.random.nextInt(64) == 0) player.displayClientMessage(Translations.orders().append(" ").append(Component.literal("Shift-click (Sneak) to pack up toaster.").withStyle(ChatFormatting.RED)), false);
            }
        }
        else
        {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            ItemEntity ei = new ItemEntity(level, x + .5, y + .5, z + .5, this.asItem().getDefaultInstance());
            if (!level.isClientSide())
            {
                level.addFreshEntity(ei);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
