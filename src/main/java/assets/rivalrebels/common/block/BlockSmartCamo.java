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
package assets.rivalrebels.common.block;

import assets.rivalrebels.RivalRebels;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockSmartCamo extends Block
{
	public BlockSmartCamo()
	{
		super(Material.iron);
	}

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        Block eastBlock = world.getBlockState(pos.east()).getBlock();
        Block westBlock = world.getBlockState(pos.west()).getBlock();
        Block northBlock = world.getBlockState(pos.north()).getBlock();
        Block southBlock = world.getBlockState(pos.south()).getBlock();
        if (eastBlock == Blocks.snow_layer || westBlock == Blocks.snow_layer || northBlock == Blocks.snow_layer || southBlock == Blocks.snow_layer)
		{
			world.setBlockState(pos, RivalRebels.camo3.getDefaultState());
		}
		else
		{
            Block downBlock = world.getBlockState(pos.down()).getBlock();
            if (downBlock == Blocks.grass || downBlock == Blocks.dirt) {
				world.setBlockState(pos, RivalRebels.camo1.getDefaultState());
			} else {
				if (downBlock == Blocks.sand || downBlock == Blocks.sandstone) {
					world.setBlockState(pos, RivalRebels.camo2.getDefaultState());
				} else {
					if (downBlock == Blocks.stone || downBlock == Blocks.gravel || downBlock == Blocks.bedrock || downBlock == Blocks.cobblestone) {
						world.setBlockState(pos, RivalRebels.camo3.getDefaultState());
					} else {
                        Block upBlock = world.getBlockState(pos.up()).getBlock();
                        if (downBlock == RivalRebels.camo2 || eastBlock == RivalRebels.camo2 || westBlock == RivalRebels.camo2 || southBlock == RivalRebels.camo2 || northBlock == RivalRebels.camo2 || upBlock == RivalRebels.camo2) {
							world.setBlockState(pos, RivalRebels.camo2.getDefaultState());
						} else {
							if (downBlock == RivalRebels.camo3 || eastBlock == RivalRebels.camo3 || westBlock == RivalRebels.camo3 || southBlock == RivalRebels.camo3 || northBlock == RivalRebels.camo3 || upBlock == RivalRebels.camo3) {
								world.setBlockState(pos, RivalRebels.camo3.getDefaultState());
							} else {
								world.setBlockState(pos, RivalRebels.camo1.getDefaultState());
							}
						}
					}
				}
			}
		}
	}
}
