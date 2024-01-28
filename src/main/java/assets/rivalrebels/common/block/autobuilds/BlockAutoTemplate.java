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
package assets.rivalrebels.common.block.autobuilds;

import assets.rivalrebels.common.core.BlackList;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAutoTemplate extends FallingBlock {
	public int		time	= 15;
	public String	name	= "building";

	public BlockAutoTemplate() {
		super(Settings.of(Material.WOOD));
	}

    public BlockAutoTemplate(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			player.sendMessage(Text.of("§4[§cWarning§4]§c Use pliers to build."), true);
		}
        return ActionResult.success(world.isClient);
	}

	public void build(World world, int x, int y, int z)
	{
		RivalRebelsSoundPlayer.playSound(world, 1, 0, x, y, z, 10, 1);
	}

	public void placeBlockCarefully(World world, int x, int y, int z, Block block) {
		if (!BlackList.autobuild(world.getBlockState(new BlockPos(x, y, z)).getBlock())) {
			world.setBlockState(new BlockPos(x, y, z), block.getDefaultState());
		}
	}

    public void placeBlockCarefully(World world, int x, int y, int z, BlockState state) {
        if (!BlackList.autobuild(world.getBlockState(new BlockPos(x, y, z)).getBlock())) {
            world.setBlockState(new BlockPos(x, y, z), state);
        }
    }

    @Override
    public void onLanding(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity) {
		if (!world.isClient) build(world, pos.getX(), pos.getY(), pos.getZ());
	}

}
