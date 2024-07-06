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
		if (!level.isClientSide())
		{
            player.displayClientMessage(Component.translatable("RivalRebels.Inventory"), false);
            player.displayClientMessage(Component.nullToEmpty("§a" + RRBlocks.timedbomb.getName() + ". §9(" + "1 minute countdown." + ")"), false);
            player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.pliers.getDescription() + ". §9(" + "to defuse explosives." + ")"), false);
            player.displayClientMessage(Component.nullToEmpty("§a" + RRBlocks.remotecharge.getName() + ". §9(" + "Remote charge." + ")"), false);
            player.displayClientMessage(Component.nullToEmpty("§a" + RRItems.remote.getDescription() + ". §9(" + "Set and detonate charge." + ")"), false);
            player.displayClientMessage(Component.nullToEmpty("§a" + RRBlocks.minetrap.getName() + ". §9(" + "Handle with care." + ")"), false);
            player.displayClientMessage(Component.nullToEmpty("§a" + RRBlocks.flare.getName() + ". §9(" + "Incendiary defense." + ")"), false);
            ItemEntity ei = new ItemEntity(level, x + .5, y + .5, z + .5, RRBlocks.timedbomb.asItem().getDefaultInstance());
			ItemEntity ei1 = new ItemEntity(level, x + .5, y + .5, z + .5, new ItemStack(RRBlocks.remotecharge, 8));
			ItemEntity ei2 = new ItemEntity(level, x + .5, y + .5, z + .5, new ItemStack(RRBlocks.minetrap, 16));
			ItemEntity ei3 = new ItemEntity(level, x + .5, y + .5, z + .5, new ItemStack(RRBlocks.flare, 8));
			ItemEntity ei4 = new ItemEntity(level, x + .5, y + .5, z + .5, RRItems.remote.getDefaultInstance());
			ItemEntity ei5 = new ItemEntity(level, x + .5, y + .5, z + .5, RRItems.pliers.getDefaultInstance());
			level.addFreshEntity(ei);
			level.addFreshEntity(ei1);
			level.addFreshEntity(ei2);
			level.addFreshEntity(ei3);
			level.addFreshEntity(ei4);
			level.addFreshEntity(ei5);
			level.setBlockAndUpdate(new BlockPos(x, y, z), Blocks.AIR.defaultBlockState());
		}
		return InteractionResult.sidedSuccess(level.isClientSide());
	}

}
