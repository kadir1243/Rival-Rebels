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

import assets.rivalrebels.common.tileentity.TileEntityMeltDown;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockMeltDown extends BlockContainer {
    public static final PropertyInteger POS = PropertyInteger.create("position", 0, 14);
	public BlockMeltDown() {
		super(Material.portal);
        this.setDefaultState(this.blockState.getBaseState().withProperty(POS, 0));
	}

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
        return null;
    }

    @Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var)
	{
		return new TileEntityMeltDown();
	}

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        worldIn.scheduleBlockUpdate(pos, this, 1, 1);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		int position = state.getValue(POS);
		if (position < 14) {
			world.setBlockState(pos.up(2), state.withProperty(POS, position + 1));
		}
	}

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(POS, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(POS);
    }
}
