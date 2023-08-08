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

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import assets.rivalrebels.common.core.BlackList;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;

public class BlockAutoTemplate extends BlockFalling
{
	public int		time	= 15;
	public String	name	= "building";

	public BlockAutoTemplate()
	{
		super();
	}

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
        if (!world.isRemote) {
            player.addChatMessage(new ChatComponentText("§4[§cWarning§4]§c Use pliers to build."));
        }
    }

	public void build(World world, int x, int y, int z)
	{
		RivalRebelsSoundPlayer.playSound(world, 1, 0, x, y, z, 10, 1);
	}

	public void placeBlockCarefully(World world, int i, int j, int z, Block block) {
        BlockPos pos = new BlockPos(i, j, z);
        if (!BlackList.autobuild(world.getBlockState(pos).getBlock())) {
			world.setBlockState(pos, block.getDefaultState());
		}
	}

	public void placeBlockCarefully(World world, int i, int j, int z, Block block, int m) {
        BlockPos pos = new BlockPos(i, j, z);
        if (!BlackList.autobuild(world.getBlockState(pos).getBlock())) {
			world.setBlockState(pos, block.getStateFromMeta(m));
		}
	}

    public void placeBlockCarefully(World world, int i, int j, int z, IBlockState state) {
        BlockPos pos = new BlockPos(i, j, z);
        if (!BlackList.autobuild(world.getBlockState(pos).getBlock())) {
            world.setBlockState(pos, state);
        }
    }

    public void placeAir(World world, int x, int y, int z) {
        world.setBlockToAir(new BlockPos(x, y, z));
    }

	@Override
	public void onEndFalling(World world, BlockPos pos) {
		if (!world.isRemote) build(world, pos.getX(), pos.getY(), pos.getZ());
	}
}
