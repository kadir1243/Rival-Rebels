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
package io.github.kadir1243.rivalrebels.common.block.crate;

import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BlockSigmaArmor extends Block
{
	public BlockSigmaArmor(Properties settings)
	{
		super(settings);
	}

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

		if (!level.isClientSide()) {
			player.displayClientMessage(Component.nullToEmpty("§7[§2Inventory§7]"), false);
			player.displayClientMessage(Component.nullToEmpty("§aArmor. §9(Sigma's color armor.)"), false);
			player.displayClientMessage(Translations.orders().append(" ").append(Component.literal("Equip your set of armor.").withStyle(ChatFormatting.RED)), false);
			ItemEntity ei7 = new ItemEntity(level, x + .5, y + .5, z + .5, RRItems.camohat2.toStack());
			ItemEntity ei8 = new ItemEntity(level, x + .5, y + .5, z + .5, RRItems.camoshirt2.toStack());
			ItemEntity ei9 = new ItemEntity(level, x + .5, y + .5, z + .5, RRItems.camopants2.toStack());
			ItemEntity ei10 = new ItemEntity(level, x + .5, y + .5, z + .5, RRItems.camoshoes2.toStack());
			level.addFreshEntity(ei7);
			level.addFreshEntity(ei8);
			level.addFreshEntity(ei9);
			level.addFreshEntity(ei10);
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		}
		return InteractionResult.sidedSuccess(level.isClientSide());
	}
}
