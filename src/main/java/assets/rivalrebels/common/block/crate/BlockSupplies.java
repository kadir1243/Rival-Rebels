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
			player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.armyshovel.getDescription() + ". §9(" + "Ideal for special blocks." + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRBlocks.jump.getName() + ". §9(" + "Use at your own risk." + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRBlocks.quicksand.getName() + ". §9(" + "Sand that is quick" + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRBlocks.mario.getName() + ". §9(" + "For trap making." + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRBlocks.loader.getName() + ". §9(" + "Modular item container." + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRBlocks.steel.getName() + ". §9(" + "Climbable and blast resistant." + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.expill.getDescription() + ". §9(" + "Take at your own risk." + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.safepill.getDescription() + ". §9(" + "Restores health." + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRBlocks.breadbox.getName() + ". §9(" + "Unlimited toast! You don't say..." + ")"), false);
		}
		if (!level.isClientSide())
		{
            Containers.dropItemStack(level, x, y, z, new ItemStack(RRBlocks.breadbox));
			Containers.dropItemStack(level, x, y, z, new ItemStack(RRItems.armyshovel));
			Containers.dropItemStack(level, x, y, z, new ItemStack(RRBlocks.jump, 4));
			Containers.dropItemStack(level, x, y, z, new ItemStack(RRBlocks.quicksandtrap, 4));
			Containers.dropItemStack(level, x, y, z, new ItemStack(RRBlocks.steel, 32));
			Containers.dropItemStack(level, x, y, z, new ItemStack(RRBlocks.loader, 2));
			Containers.dropItemStack(level, x, y, z, new ItemStack(Items.BUCKET, 2));
			Containers.dropItemStack(level, x, y, z, new ItemStack(RRBlocks.mariotrap, 4));
			Containers.dropItemStack(level, x, y, z, new ItemStack(RRItems.expill, 6));
			Containers.dropItemStack(level, x, y, z, new ItemStack(RRItems.safepill, 3));
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			if (level.random.nextInt(5) == 0)
			{
                Containers.dropItemStack(level, x, y, z, RRItems.NUCLEAR_ROD.getDefaultInstance());
				player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.NUCLEAR_ROD.getDescription() + ". §9" + "(Used in nuclear weapons)"), false);
			}
			return InteractionResult.sidedSuccess(level.isClientSide());
		}
		return InteractionResult.PASS;
	}
}
