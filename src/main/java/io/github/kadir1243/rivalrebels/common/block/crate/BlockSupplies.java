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
			player.displayClientMessage(Component.translatable("RivalRebels.Inventory"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.armyshovel.get().getDescription() + ". §9(" + "Ideal for special blocks." + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRBlocks.jump.get().getName() + ". §9(" + "Use at your own risk." + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRBlocks.quicksand.get().getName() + ". §9(" + "Sand that is quick" + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRBlocks.mario.get().getName() + ". §9(" + "For trap making." + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRBlocks.loader.get().getName() + ". §9(" + "Modular item container." + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRBlocks.steel.get().getName() + ". §9(" + "Climbable and blast resistant." + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.expill.get().getDescription() + ". §9(" + "Take at your own risk." + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.safepill.get().getDescription() + ". §9(" + "Restores health." + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRBlocks.breadbox.get().getName() + ". §9(" + "Unlimited toast! You don't say..." + ")"), false);
		}
		if (!level.isClientSide())
		{
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
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			if (level.random.nextInt(5) == 0)
			{
                Containers.dropItemStack(level, x, y, z, RRItems.NUCLEAR_ROD.toStack());
				player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.NUCLEAR_ROD.asItem().getDescription() + ". §9" + "(Used in nuclear weapons)"), false);
			}
			return InteractionResult.sidedSuccess(level.isClientSide());
		}
		return InteractionResult.PASS;
	}
}
