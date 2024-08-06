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
package assets.rivalrebels.common.item;

import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.block.autobuilds.BlockAutoTemplate;
import assets.rivalrebels.common.command.CommandHotPotato;
import assets.rivalrebels.common.util.Translations;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
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
    public ItemStack getRecipeRemainder(ItemStack stack) {
        return this.getDefaultInstance();
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();
        Player player = context.getPlayer();
        if (!world.isClientSide()) {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            Block block = world.getBlockState(pos).getBlock();
            if (block == RRBlocks.jump && player.isCreative()) {
                CommandHotPotato.pos = pos.above(400);
				player.displayClientMessage(Component.nullToEmpty("Hot Potato drop point set. Use /rrhotpotato to start a round."), false);
			}
			if (block == RRBlocks.remotecharge)
			{
				int t = 25;
				i = i + 1;
				player.displayClientMessage(Translations.defuse().append(" %" + i * 100 / t), false);
				if (i >= t)
				{
					ItemEntity ei = new ItemEntity(world, x + .5, y + .5, z + .5, RRBlocks.remotecharge.asItem().getDefaultInstance());
					world.addFreshEntity(ei);
					world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
					i = 0;
                    return InteractionResult.sidedSuccess(world.isClientSide());
				}
			}
			if (block == RRBlocks.timedbomb)
			{
				int t = 25;
				i = i + 1;
				player.displayClientMessage(Translations.defuse().append(" %" + i * 100 / t), false);
				if (i >= t)
				{
					ItemEntity ei = new ItemEntity(world, x + .5, y + .5, z + .5, RRBlocks.timedbomb.asItem().getDefaultInstance());
					world.addFreshEntity(ei);
					world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
					world.setBlockAndUpdate(pos.above(), Blocks.AIR.defaultBlockState());
					i = 0;
                    return InteractionResult.sidedSuccess(world.isClientSide());
				}
			}
			if (block instanceof BlockAutoTemplate worldBlock)
			{
                i = i + 1;
				player.displayClientMessage(Translations.status().append(" ").append(Component.translatable(Translations.BUILDING.toLanguageKey(), i * 100 / worldBlock.time)), false);
				if (i >= worldBlock.time)
				{
                    world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
					worldBlock.build(world, x, y, z);
					i = 0;
                    return InteractionResult.sidedSuccess(world.isClientSide());
				}
			}
			if (block == RRBlocks.supplies && world.getBlockState(pos.below()).is(RRBlocks.supplies))
			{
				i++;
				player.displayClientMessage(Translations.status().append(" ").append(Component.translatable(Translations.BUILDING_TOKAMAK.toLanguageKey(), i * 100 / 15)), false);
				if (i >= 15)
				{
                    world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
					world.setBlockAndUpdate(pos.below(), RRBlocks.reactor.defaultBlockState());
					i = 0;
                    return InteractionResult.sidedSuccess(world.isClientSide());
				}
			}
		}
		return InteractionResult.PASS;
	}

}
