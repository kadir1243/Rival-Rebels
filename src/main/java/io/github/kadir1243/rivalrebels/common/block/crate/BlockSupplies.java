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

import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BlockSupplies extends Block
{
	public BlockSupplies(Properties settings)
	{
		super(settings);
	}

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

		if (level.isClientSide())
		{
			player.sendSystemMessage(Translations.inventory());
			player.sendSystemMessage(RRItems.armyshovel.toStack().getItemName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(Component.literal("(" + "Ideal for special blocks." + ")").withStyle(ChatFormatting.BLUE)));
			player.sendSystemMessage(RRBlocks.jump.get().getName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(Component.literal("(" + "Use at your own risk." + ")").withStyle(ChatFormatting.BLUE)));
			player.sendSystemMessage(RRBlocks.quicksand.get().getName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(Component.literal("(" + "Sand that is quick" + ")").withStyle(ChatFormatting.BLUE)));
			player.sendSystemMessage(RRBlocks.mario.get().getName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(Component.literal("(" + "For trap making." + ")").withStyle(ChatFormatting.BLUE)));
			player.sendSystemMessage(RRBlocks.loader.get().getName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(Component.literal("(" + "Modular item container." + ")").withStyle(ChatFormatting.BLUE)));
			player.sendSystemMessage(RRBlocks.steel.get().getName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(Component.literal("(" + "Climbable and blast resistant." + ")").withStyle(ChatFormatting.BLUE)));
			player.sendSystemMessage(RRItems.expill.toStack().getItemName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(Component.literal("(" + "Take at your own risk." + ")").withStyle(ChatFormatting.BLUE)));
			player.sendSystemMessage(RRItems.safepill.toStack().getItemName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(Component.literal("(" + "Restores health." + ")").withStyle(ChatFormatting.BLUE)));
			player.sendSystemMessage(RRBlocks.breadbox.get().getName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(Component.literal("(" + "Unlimited toast! You don't say..." + ")").withStyle(ChatFormatting.BLUE)));
		}
		if (!level.isClientSide())
		{
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            Containers.dropItemStack(level, x, y, z, RRBlocks.breadbox.toStack());
			Containers.dropItemStack(level, x, y, z, RRItems.armyshovel.toStack());
			Containers.dropItemStack(level, x, y, z, RRBlocks.jump.toStack(4));
			Containers.dropItemStack(level, x, y, z, RRBlocks.quicksandtrap.toStack(4));
			Containers.dropItemStack(level, x, y, z, RRBlocks.steel.toStack(32));
			Containers.dropItemStack(level, x, y, z, RRBlocks.loader.toStack(2));
			Containers.dropItemStack(level, x, y, z, new ItemStack(Items.BUCKET, 2));
			Containers.dropItemStack(level, x, y, z, RRBlocks.mariotrap.toStack(4));
			Containers.dropItemStack(level, x, y, z, RRItems.expill.toStack(6));
			Containers.dropItemStack(level, x, y, z, RRItems.safepill.toStack(3));
			if (level.getRandom().nextInt(5) == 0)
			{
                Containers.dropItemStack(level, x, y, z, RRItems.NUCLEAR_ROD.toStack());
				player.sendSystemMessage(RRItems.NUCLEAR_ROD.toStack().getItemName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(Component.literal("(Used in nuclear weapons)").withStyle(ChatFormatting.BLUE)));
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
