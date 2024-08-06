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

import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.util.Translations;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BlockAmmunition extends Block
{
	public BlockAmmunition(Properties settings)
	{
		super(settings);
	}

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
		if (level.isClientSide()) {
			player.displayClientMessage(Component.translatable("RivalRebels.Inventory"), false);
            player.displayClientMessage(RRItems.rocket.getDescription().copy().withStyle(ChatFormatting.GREEN).append(". ").append(RRItems.rpg.getDescription().copy().withStyle(ChatFormatting.BLUE)).append(" ").append(Translations.ammunition()).append(")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.battery.getDescription() + ". §9(" + RRItems.tesla.getDescription() + " " + Translations.ammunition() + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.hydrod.getDescription() + ". §9(" + RRItems.plasmacannon.getDescription() + " " + Translations.ammunition() + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.fuel.getDescription() + ". §9(" + RRItems.flamethrower.getDescription() + " " + Translations.ammunition() + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.redrod.getDescription() + ". §9(" + RRItems.einsten.getDescription() + " " + Translations.ammunition() + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.gasgrenade.getDescription() + ". §9(" + Component.translatable("RivalRebels.chemicalweapon") + ")"), false);
		} else {
            Containers.dropItemStack(level, x, y, z, new ItemStack(RRItems.rocket, 32));
			Containers.dropItemStack(level, x, y, z, new ItemStack(RRItems.battery, 16));
			Containers.dropItemStack(level, x, y, z, RRItems.hydrod.getDefaultInstance());
			Containers.dropItemStack(level, x, y, z, RRItems.hydrod.getDefaultInstance());
			Containers.dropItemStack(level, x, y, z, RRItems.hydrod.getDefaultInstance());
			Containers.dropItemStack(level, x, y, z, RRItems.hydrod.getDefaultInstance());
			Containers.dropItemStack(level, x, y, z, new ItemStack(RRItems.fuel, 64));
			Containers.dropItemStack(level, x, y, z, new ItemStack(RRItems.gasgrenade, 6));
			Containers.dropItemStack(level, x, y, z, RRItems.redrod.getDefaultInstance());
			Containers.dropItemStack(level, x, y, z, RRItems.redrod.getDefaultInstance());
			Containers.dropItemStack(level, x, y, z, RRItems.redrod.getDefaultInstance());
			Containers.dropItemStack(level, x, y, z, RRItems.redrod.getDefaultInstance());
			level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			if (level.random.nextInt(3) == 0) {
				Containers.dropItemStack(level, x, y, z, RRItems.NUCLEAR_ROD.getDefaultInstance());
				player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.NUCLEAR_ROD.getDescription() + ". §9(" + "Used in nuclear weapons" + ")"), false);
			}
		}
		return InteractionResult.sidedSuccess(level.isClientSide());
	}
}
