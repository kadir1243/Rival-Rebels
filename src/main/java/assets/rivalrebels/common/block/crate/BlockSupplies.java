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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSupplies extends Block
{
	public BlockSupplies(Settings settings)
	{
		super(settings);
	}

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

		if (world.isClient)
		{
			player.sendMessage(Text.translatable("RivalRebels.Inventory"), false);
			player.sendMessage(Text.of("§a" + RRItems.armyshovel.getName() + ". §9(" + "Ideal for special blocks." + ")"), false);
			player.sendMessage(Text.of("§a" + RRBlocks.jump.getName() + ". §9(" + "Use at your own risk." + ")"), false);
			player.sendMessage(Text.of("§a" + RRBlocks.quicksand.getName() + ". §9(" + "Sand that is quick" + ")"), false);
			player.sendMessage(Text.of("§a" + RRBlocks.mario.getName() + ". §9(" + "For trap making." + ")"), false);
			player.sendMessage(Text.of("§a" + RRBlocks.loader.getName() + ". §9(" + "Modular item container." + ")"), false);
			player.sendMessage(Text.of("§a" + RRBlocks.steel.getName() + ". §9(" + "Climbable and blast resistant." + ")"), false);
			player.sendMessage(Text.of("§a" + RRItems.expill.getName() + ". §9(" + "Take at your own risk." + ")"), false);
			player.sendMessage(Text.of("§a" + RRItems.safepill.getName() + ". §9(" + "Restores health." + ")"), false);
			player.sendMessage(Text.of("§a" + RRBlocks.breadbox.getName() + ". §9(" + "Unlimited toast! You don't say..." + ")"), false);
		}
		if (!world.isClient)
		{
			ItemEntity ei = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRBlocks.breadbox));
			ItemEntity ei1 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRItems.armyshovel));
			ItemEntity ei2 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRBlocks.jump, 4));
			ItemEntity ei3 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRBlocks.quicksandtrap, 4));
			ItemEntity ei4 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRBlocks.steel, 32));
			ItemEntity ei5 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRBlocks.loader, 2));
			ItemEntity ei6 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(Items.BUCKET, 2));
			ItemEntity ei7 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRBlocks.mariotrap, 4));
			ItemEntity ei8 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRItems.expill, 6));
			ItemEntity ei9 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRItems.safepill, 3));
			world.spawnEntity(ei);
			world.spawnEntity(ei1);
			world.spawnEntity(ei2);
			world.spawnEntity(ei3);
			world.spawnEntity(ei4);
			world.spawnEntity(ei5);
			world.spawnEntity(ei6);
			world.spawnEntity(ei7);
			world.spawnEntity(ei8);
			world.spawnEntity(ei9);
            world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState());
			if (world.random.nextInt(5) == 0)
			{
				world.spawnEntity(new ItemEntity(world, x + .5, y + .5, z + .5, RRItems.nuclearelement.getDefaultStack()));
				player.sendMessage(Text.of("§a" + RRItems.nuclearelement.getName() + ". §9" + "(Used in nuclear weapons)"), false);
			}
			return ActionResult.success(world.isClient);
		}
		return ActionResult.PASS;
	}
}
