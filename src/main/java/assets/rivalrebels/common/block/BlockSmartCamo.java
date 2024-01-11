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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSmartCamo extends Block
{
	public BlockSmartCamo()
	{
		super(Material.IRON);
	}

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (getBlock(world, x + 1, y, z) == Blocks.SNOW_LAYER || getBlock(world, x - 1, y, z) == Blocks.SNOW_LAYER || getBlock(world, x, y, z - 1) == Blocks.SNOW_LAYER || getBlock(world, x, y, z + 1) == Blocks.SNOW_LAYER)
        {
            setBlock(world, x, y, z, RivalRebels.camo3);
        }
        else
        {
            if (getBlock(world, x, y - 1, z) == Blocks.GRASS || getBlock(world, x, y - 1, z) == Blocks.DIRT)
            {
                setBlock(world, x, y, z, RivalRebels.camo1);
            }
            else
            {
                if (getBlock(world, x, y - 1, z) == Blocks.SAND || getBlock(world, x, y - 1, z) == Blocks.SANDSTONE)
                {
                    setBlock(world, x, y, z, RivalRebels.camo2);
                }
                else
                {
                    if (getBlock(world, x, y - 1, z) == Blocks.STONE || getBlock(world, x, y - 1, z) == Blocks.GRAVEL || getBlock(world, x, y - 1, z) == Blocks.BEDROCK || getBlock(world, x, y - 1, z) == Blocks.COBBLESTONE)
                    {
                        setBlock(world, x, y, z, RivalRebels.camo3);
                    }
                    else
                    {
                        if (getBlock(world, x, y - 1, z) == RivalRebels.camo2 || getBlock(world, x + 1, y, z) == RivalRebels.camo2 || getBlock(world, x - 1, y, z) == RivalRebels.camo2 || getBlock(world, x, y, z + 1) == RivalRebels.camo2 || getBlock(world, x, y, z - 1) == RivalRebels.camo2 || getBlock(world, x, y + 1, z) == RivalRebels.camo2)
                        {
                            setBlock(world, x, y, z, RivalRebels.camo2);
                        }
                        else
                        {
                            if (getBlock(world, x, y - 1, z) == RivalRebels.camo3 || getBlock(world, x + 1, y, z) == RivalRebels.camo3 || getBlock(world, x - 1, y, z) == RivalRebels.camo3 || getBlock(world, x, y, z + 1) == RivalRebels.camo3 || getBlock(world, x, y, z - 1) == RivalRebels.camo3 || getBlock(world, x, y + 1, z) == RivalRebels.camo3)
                            {
                                setBlock(world, x, y, z, RivalRebels.camo3);
                            }
                            else
                            {
                                setBlock(world, x, y, z, RivalRebels.camo1);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void setBlock(World world, int x, int y, int z, Block block) {
        world.setBlockState(new BlockPos(x, y, z), block.getDefaultState());
    }

    private static Block getBlock(World world, int x, int y, int z) {
        return world.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

	/*@SideOnly(Side.CLIENT)
	IIcon	icon;

	@Override
	public final IIcon getIcon(int side, int meta)
	{
		return icon;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconregister)
	{
		icon = iconregister.registerIcon("RivalRebels:bq");
	}*/
}
