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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAmmunition extends Block
{
	public BlockAmmunition(Settings settings)
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
            player.sendMessage(RRItems.rocket.getName().copy().formatted(Formatting.GREEN).append(". ").append(RRItems.rpg.getName().copy().formatted(Formatting.BLUE)).append(" ").append(Text.translatable("RivalRebels.ammunition")).append(")"), false);
			player.sendMessage(Text.of("§a" + RRItems.battery.getName() + ". §9(" + RRItems.tesla.getName() + " " + Text.translatable("RivalRebels.ammunition") + ")"), false);
			player.sendMessage(Text.of("§a" + RRItems.hydrod.getName() + ". §9(" + RRItems.plasmacannon.getName() + " " + Text.translatable("RivalRebels.ammunition") + ")"), false);
			player.sendMessage(Text.of("§a" + RRItems.fuel.getName() + ". §9(" + RRItems.flamethrower.getName() + " " + Text.translatable("RivalRebels.ammunition") + ")"), false);
			player.sendMessage(Text.of("§a" + RRItems.redrod.getName() + ". §9(" + RRItems.einsten.getName() + " " + Text.translatable("RivalRebels.ammunition") + ")"), false);
			player.sendMessage(Text.of("§a" + RRItems.gasgrenade.getName() + ". §9(" + Text.translatable("RivalRebels.chemicalweapon") + ")"), false);
		} else {
			ItemEntity ei = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRItems.rocket, 32));
			ItemEntity ei1 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRItems.battery, 16));
			ItemEntity ei2 = new ItemEntity(world, x + .5, y + .5, z + .5, RRItems.hydrod.getDefaultStack());
			ItemEntity ei3 = new ItemEntity(world, x + .5, y + .5, z + .5, RRItems.hydrod.getDefaultStack());
			ItemEntity ei4 = new ItemEntity(world, x + .5, y + .5, z + .5, RRItems.hydrod.getDefaultStack());
			ItemEntity ei5 = new ItemEntity(world, x + .5, y + .5, z + .5, RRItems.hydrod.getDefaultStack());
			ItemEntity ei10 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRItems.fuel, 64));
			ItemEntity ei11 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRItems.gasgrenade, 6));
			ItemEntity ei12 = new ItemEntity(world, x + .5, y + .5, z + .5, RRItems.redrod.getDefaultStack());
			ItemEntity ei13 = new ItemEntity(world, x + .5, y + .5, z + .5, RRItems.redrod.getDefaultStack());
			ItemEntity ei14 = new ItemEntity(world, x + .5, y + .5, z + .5, RRItems.redrod.getDefaultStack());
			ItemEntity ei15 = new ItemEntity(world, x + .5, y + .5, z + .5, RRItems.redrod.getDefaultStack());
			world.spawnEntity(ei);
			world.spawnEntity(ei1);
			world.spawnEntity(ei2);
			world.spawnEntity(ei3);
			world.spawnEntity(ei4);
			world.spawnEntity(ei5);
			world.spawnEntity(ei10);
			world.spawnEntity(ei11);
			world.spawnEntity(ei12);
			world.spawnEntity(ei13);
			world.spawnEntity(ei14);
			world.spawnEntity(ei15);
			world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState());
			if (world.random.nextInt(3) == 0)
			{
				world.spawnEntity(new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRItems.nuclearelement, 1)));
				player.sendMessage(Text.of("§a" + RRItems.nuclearelement.getName() + ". §9(" + "Used in nuclear weapons" + ")"), false);
			}
		}
		return ActionResult.success(world.isClient);
	}
}
