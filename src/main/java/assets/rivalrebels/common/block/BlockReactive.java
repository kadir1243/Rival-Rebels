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

import assets.rivalrebels.common.tileentity.TileEntityReactive;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class BlockReactive extends BlockContainer
{
	public BlockReactive()
	{
		super(Material.iron);
	}

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var)
	{
		return new TileEntityReactive();
	}
}
