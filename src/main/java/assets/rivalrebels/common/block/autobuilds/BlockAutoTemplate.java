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
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

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
			player.sendMessage(new TextComponentString("§4[§cWarning§4]§c Use pliers to build."));
		}
	}

	public void build(World world, int x, int y, int z)
	{
		RivalRebelsSoundPlayer.playSound(world, 1, 0, x, y, z, 10, 1);
	}

	public void placeBlockCarefully(World world, int i, int j, int z, Block block)
	{
		if (!BlackList.autobuild(world.getBlockState(new BlockPos(i, j, z)).getBlock()))
		{
			world.setBlockState(new BlockPos(i, j, z), block.getDefaultState());
		}
	}

	public void placeBlockCarefully(World world, int i, int j, int z, Block block, int m, int f)
	{
        if (!BlackList.autobuild(world.getBlockState(new BlockPos(i, j, z)).getBlock()))
		{
            world.setBlockState(new BlockPos(i, j, z), block.getStateFromMeta(m), f);
		}
	}

    public void placeBlockCarefully(World world, int i, int j, int z, Block block, int m)
    {
        if (!BlackList.autobuild(world.getBlockState(new BlockPos(i, j, z)).getBlock()))
        {
            world.setBlockState(new BlockPos(i, j, z), block.getStateFromMeta(m));
        }
    }

    @Override
    public void onEndFalling(World world, BlockPos pos, IBlockState fallingState, IBlockState hitState) {
		if (!world.isRemote) build(world, pos.getX(), pos.getY(), pos.getZ());
	}

}
