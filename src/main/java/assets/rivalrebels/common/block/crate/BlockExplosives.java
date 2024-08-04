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
import assets.rivalrebels.common.item.RRItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BlockExplosives extends Block
{
	public BlockExplosives(Properties settings)
	{
		super(settings);
	}

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
		if (!level.isClientSide()) {
            player.displayClientMessage(Component.translatable("RivalRebels.Inventory"), false);
            player.displayClientMessage(RRBlocks.timedbomb.getName().withStyle(ChatFormatting.GREEN).append(". ").append(Component.literal("(1 minute countdown.)").withStyle(ChatFormatting.BLUE)), false);
            player.displayClientMessage(RRItems.pliers.getDescription().copy().withStyle(ChatFormatting.GREEN).append(". ").append(Component.literal("§9(to defuse explosives.)").withStyle(ChatFormatting.BLUE)), false);
            player.displayClientMessage(RRBlocks.remotecharge.getName().withStyle(ChatFormatting.GREEN).append(". ").append(Component.literal("(Remote charge.)").withStyle(ChatFormatting.BLUE)), false);
            player.displayClientMessage(RRItems.remote.getDescription().copy().withStyle(ChatFormatting.GREEN).append(". ").append(Component.literal("(Set and detonate charge.)").withStyle(ChatFormatting.BLUE)), false);
            player.displayClientMessage(RRBlocks.minetrap.getName().withStyle(ChatFormatting.GREEN).append(". ").append(Component.literal("(Handle with care.)").withStyle(ChatFormatting.BLUE)), false);
            player.displayClientMessage(RRBlocks.flare.getName().withStyle(ChatFormatting.GREEN).append(". ").append(Component.literal("(Incendiary defense.)").withStyle(ChatFormatting.BLUE)), false);
            Containers.dropItemStack(level, x, y, z, RRBlocks.timedbomb.asItem().getDefaultInstance());
            Containers.dropItemStack(level, x, y, z, new ItemStack(RRBlocks.remotecharge, 8));
			Containers.dropItemStack(level, x, y, z, new ItemStack(RRBlocks.minetrap, 16));
			Containers.dropItemStack(level, x, y, z, new ItemStack(RRBlocks.flare, 8));
			Containers.dropItemStack(level, x, y, z, RRItems.remote.getDefaultInstance());
			Containers.dropItemStack(level, x, y, z, RRItems.pliers.getDefaultInstance());
			level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		}
		return InteractionResult.sidedSuccess(level.isClientSide());
	}

}
