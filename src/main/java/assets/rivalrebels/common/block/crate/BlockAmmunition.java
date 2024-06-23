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
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
		if (level.isClientSide) {
			player.displayClientMessage(Component.translatable("RivalRebels.Inventory"), false);
            player.displayClientMessage(RRItems.rocket.getDescription().copy().withStyle(ChatFormatting.GREEN).append(". ").append(RRItems.rpg.getDescription().copy().withStyle(ChatFormatting.BLUE)).append(" ").append(Component.translatable("RivalRebels.ammunition")).append(")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.battery.getDescription() + ". §9(" + RRItems.tesla.getDescription() + " " + Component.translatable("RivalRebels.ammunition") + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.hydrod.getDescription() + ". §9(" + RRItems.plasmacannon.getDescription() + " " + Component.translatable("RivalRebels.ammunition") + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.fuel.getDescription() + ". §9(" + RRItems.flamethrower.getDescription() + " " + Component.translatable("RivalRebels.ammunition") + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.redrod.getDescription() + ". §9(" + RRItems.einsten.getDescription() + " " + Component.translatable("RivalRebels.ammunition") + ")"), false);
			player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.gasgrenade.getDescription() + ". §9(" + Component.translatable("RivalRebels.chemicalweapon") + ")"), false);
		} else {
			ItemEntity ei = new ItemEntity(level, x + .5, y + .5, z + .5, new ItemStack(RRItems.rocket, 32));
			ItemEntity ei1 = new ItemEntity(level, x + .5, y + .5, z + .5, new ItemStack(RRItems.battery, 16));
			ItemEntity ei2 = new ItemEntity(level, x + .5, y + .5, z + .5, RRItems.hydrod.getDefaultInstance());
			ItemEntity ei3 = new ItemEntity(level, x + .5, y + .5, z + .5, RRItems.hydrod.getDefaultInstance());
			ItemEntity ei4 = new ItemEntity(level, x + .5, y + .5, z + .5, RRItems.hydrod.getDefaultInstance());
			ItemEntity ei5 = new ItemEntity(level, x + .5, y + .5, z + .5, RRItems.hydrod.getDefaultInstance());
			ItemEntity ei10 = new ItemEntity(level, x + .5, y + .5, z + .5, new ItemStack(RRItems.fuel, 64));
			ItemEntity ei11 = new ItemEntity(level, x + .5, y + .5, z + .5, new ItemStack(RRItems.gasgrenade, 6));
			ItemEntity ei12 = new ItemEntity(level, x + .5, y + .5, z + .5, RRItems.redrod.getDefaultInstance());
			ItemEntity ei13 = new ItemEntity(level, x + .5, y + .5, z + .5, RRItems.redrod.getDefaultInstance());
			ItemEntity ei14 = new ItemEntity(level, x + .5, y + .5, z + .5, RRItems.redrod.getDefaultInstance());
			ItemEntity ei15 = new ItemEntity(level, x + .5, y + .5, z + .5, RRItems.redrod.getDefaultInstance());
			level.addFreshEntity(ei);
			level.addFreshEntity(ei1);
			level.addFreshEntity(ei2);
			level.addFreshEntity(ei3);
			level.addFreshEntity(ei4);
			level.addFreshEntity(ei5);
			level.addFreshEntity(ei10);
			level.addFreshEntity(ei11);
			level.addFreshEntity(ei12);
			level.addFreshEntity(ei13);
			level.addFreshEntity(ei14);
			level.addFreshEntity(ei15);
			level.setBlockAndUpdate(new BlockPos(x, y, z), Blocks.AIR.defaultBlockState());
			if (level.random.nextInt(3) == 0) {
				level.addFreshEntity(new ItemEntity(level, x + .5, y + .5, z + .5, new ItemStack(RRItems.nuclearelement, 1)));
				player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.nuclearelement.getDescription() + ". §9(" + "Used in nuclear weapons" + ")"), false);
			}
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	}
}
