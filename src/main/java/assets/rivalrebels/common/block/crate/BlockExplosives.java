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
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockExplosives extends Block
{
	public BlockExplosives(Settings settings)
	{
		super(settings);
	}

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
		if (!world.isClient)
		{
            player.sendMessage(Text.translatable("RivalRebels.Inventory"), false);
            player.sendMessage(Text.of("§a" + RRBlocks.timedbomb.getName() + ". §9(" + "1 minute countdown." + ")"), false);
            player.sendMessage(Text.of("§a" + RRItems.pliers.getName() + ". §9(" + "to defuse explosives." + ")"), false);
            player.sendMessage(Text.of("§a" + RRBlocks.remotecharge.getName() + ". §9(" + "Remote charge." + ")"), false);
            player.sendMessage(Text.of("§a" + RRItems.remote.getName() + ". §9(" + "Set and detonate charge." + ")"), false);
            player.sendMessage(Text.of("§a" + RRBlocks.minetrap.getName() + ". §9(" + "Handle with care." + ")"), false);
            player.sendMessage(Text.of("§a" + RRBlocks.flare.getName() + ". §9(" + "Incendiary defense." + ")"), false);
            ItemEntity ei = new ItemEntity(world, x + .5, y + .5, z + .5, RRBlocks.timedbomb.asItem().getDefaultStack());
			ItemEntity ei1 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRBlocks.remotecharge, 8));
			ItemEntity ei2 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRBlocks.minetrap, 16));
			ItemEntity ei3 = new ItemEntity(world, x + .5, y + .5, z + .5, new ItemStack(RRBlocks.flare, 8));
			ItemEntity ei4 = new ItemEntity(world, x + .5, y + .5, z + .5, RRItems.remote.getDefaultStack());
			ItemEntity ei5 = new ItemEntity(world, x + .5, y + .5, z + .5, RRItems.pliers.getDefaultStack());
			world.spawnEntity(ei);
			world.spawnEntity(ei1);
			world.spawnEntity(ei2);
			world.spawnEntity(ei3);
			world.spawnEntity(ei4);
			world.spawnEntity(ei5);
			world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState());
		}
		return ActionResult.success(world.isClient);
	}

}
