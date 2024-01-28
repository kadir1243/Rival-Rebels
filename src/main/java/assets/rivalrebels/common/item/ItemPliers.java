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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.block.autobuilds.BlockAutoTemplate;
import assets.rivalrebels.common.command.CommandHotPotato;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemPliers extends Item
{
	private int	i = 0;

	public ItemPliers()
	{
		super(new Settings().maxCount(1).group(RRItems.rralltab));
	}

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        return this.getDefaultStack();
    }

    @Override
    public ActionResult onItemUseFirst(ItemStack stack, ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();
		player.swingHand(hand);
		if (!world.isClient)
		{
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            Block block = world.getBlockState(pos).getBlock();
            if (block == RRBlocks.jump && player.getAbilities().invulnerable) {
                CommandHotPotato.pos = pos.up(400);
				player.sendMessage(Text.of("Hot Potato drop point set. Use /rrhotpotato to start a round."), false);
			}
			if (block == RRBlocks.remotecharge)
			{
				int t = 25;
				i = i + 1;
				player.sendMessage(new TranslatableText(RivalRebels.MODID + ".defuse", i * 100 / t), false);
				if (i >= t)
				{
					ItemEntity ei = new ItemEntity(world, x + .5, y + .5, z + .5, RRBlocks.remotecharge.asItem().getDefaultStack());
					world.spawnEntity(ei);
					world.setBlockState(pos, Blocks.AIR.getDefaultState());
					i = 0;
					return ActionResult.SUCCESS;
				}
			}
			if (block == RRBlocks.timedbomb)
			{
				int t = 25;
				i = i + 1;
				player.sendMessage(new TranslatableText(RivalRebels.MODID + ".defuse", i * 100 / t), false);
				if (i >= t)
				{
					ItemEntity ei = new ItemEntity(world, x + .5, y + .5, z + .5, RRBlocks.timedbomb.asItem().getDefaultStack());
					world.spawnEntity(ei);
					world.setBlockState(pos, Blocks.AIR.getDefaultState());
					world.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
					i = 0;
					return ActionResult.SUCCESS;
				}
			}
			if (block instanceof BlockAutoTemplate worldBlock)
			{
                i = i + 1;
				player.sendMessage(new TranslatableText(RivalRebels.MODID + ".building", i * 100 / worldBlock.time), false);
				if (i >= worldBlock.time)
				{
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
					worldBlock.build(world, x, y, z);
					i = 0;
					return ActionResult.SUCCESS;
				}
			}
			if (block == RRBlocks.supplies && world.getBlockState(pos.down()).getBlock() == RRBlocks.supplies)
			{
				i++;
				player.sendMessage(new TranslatableText(RivalRebels.MODID + ".building.tokamak ", i * 100 / 15), false);
				if (i >= 15)
				{
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
					world.setBlockState(pos.down(), RRBlocks.reactor.getDefaultState());
					i = 0;
					return ActionResult.SUCCESS;
				}
			}
		}
		return ActionResult.PASS;
	}

}
