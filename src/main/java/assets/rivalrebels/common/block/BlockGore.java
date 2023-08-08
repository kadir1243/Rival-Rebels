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

import assets.rivalrebels.common.entity.EntityBlood;
import assets.rivalrebels.common.entity.EntityGoo;
import assets.rivalrebels.common.tileentity.TileEntityGore;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.Random;

public class BlockGore extends BlockContainer {
    public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 5);
	public BlockGore()
	{
		super(Material.cake);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (player.capabilities.isCreativeMode) {
			int type = state.getValue(TYPE) + 1;
			if (type >= 6) type = 0;
			world.setBlockState(pos, state.withProperty(TYPE, type));
			return true;
		}
		return false;
	}

    @Override
    public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (world.isAirBlock(pos.down())) {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            if (state.getValue(TYPE) < 2) {
                world.spawnEntityInWorld(new EntityBlood(world, x + Math.random(), y + 0.9f, z + Math.random()));
            } else if (state.getValue(TYPE) < 4) {
                world.spawnEntityInWorld(new EntityGoo(world, x + Math.random(), y + 0.9f, z + Math.random()));
            }
        }
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
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
        return null;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
        return null;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        for (EnumFacing facing : EnumFacing.VALUES) {
            if (worldIn.isBlockNormalCube(pos.offset(facing), false)) {
                worldIn.setBlockToAir(pos);
                break;
            }
        }
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
	public TileEntity createNewTileEntity(World var1, int var)
	{
		return new TileEntityGore();
	}

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE);
    }
}
