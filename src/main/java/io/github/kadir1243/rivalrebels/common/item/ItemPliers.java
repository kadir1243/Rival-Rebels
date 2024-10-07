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
package io.github.kadir1243.rivalrebels.common.item;

import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.block.autobuilds.BlockAutoTemplate;
import io.github.kadir1243.rivalrebels.common.command.CommandHotPotato;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ItemPliers extends Item
{
	private int	i = 0;

	public ItemPliers()
	{
		super(new Properties().stacksTo(1));
	}

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return this.getDefaultInstance();
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (!level.isClientSide()) {
            Block block = level.getBlockState(pos).getBlock();
            if (block == RRBlocks.jump.get() && player.isCreative()) {
                CommandHotPotato.pos = pos.above(400);
				player.displayClientMessage(Component.nullToEmpty("Hot Potato drop point set. Use /rrhotpotato to start a round."), false);
			}
			if (block == RRBlocks.remotecharge.get()) {
				int t = 25;
				i = i + 1;
				player.displayClientMessage(Translations.defuse().append(" %" + i * 100 / t), false);
				if (i >= t) {
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), RRBlocks.remotecharge.toStack());
					level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
					i = 0;
                    return InteractionResult.sidedSuccess(level.isClientSide());
				}
			}
			if (block == RRBlocks.timedbomb.get()) {
				int t = 25;
				i = i + 1;
				player.displayClientMessage(Translations.defuse().append(" %" + i * 100 / t), false);
				if (i >= t) {
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), RRBlocks.timedbomb.toStack());
                    level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
					level.setBlockAndUpdate(pos.above(), Blocks.AIR.defaultBlockState());
					i = 0;
                    return InteractionResult.sidedSuccess(level.isClientSide());
				}
			}
			if (block instanceof BlockAutoTemplate worldBlock) {
                i = i + 1;
				player.displayClientMessage(Translations.status().append(" ").append(Component.translatable(Translations.BUILDING.toLanguageKey(), i * 100 / worldBlock.time)), false);
				if (i >= worldBlock.time) {
                    level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
					worldBlock.build(level, pos.getX(), pos.getY(), pos.getZ());
					i = 0;
                    return InteractionResult.sidedSuccess(level.isClientSide());
				}
			}
			if (block == RRBlocks.supplies.get() && level.getBlockState(pos.below()).is(RRBlocks.supplies))
			{
				i++;
				player.displayClientMessage(Translations.status().append(" ").append(Component.translatable(Translations.BUILDING_TOKAMAK.toLanguageKey(), i * 100 / 15)), false);
				if (i >= 15)
				{
                    level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
					level.setBlockAndUpdate(pos.below(), RRBlocks.reactor.get().defaultBlockState());
					i = 0;
                    return InteractionResult.sidedSuccess(level.isClientSide());
				}
			}
		}
		return InteractionResult.PASS;
	}

}
