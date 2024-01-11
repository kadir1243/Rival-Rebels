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
package assets.rivalrebels.common.block.machine;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BlockForceField extends Block {
    public static final PropertyInteger META = PropertyInteger.create("meta", 0, 6);
	public BlockForceField() {
		super(Material.GLASS);
        this.setDefaultState(this.getBlockState().getBaseState().withProperty(META, 0));
	}
    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(META);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(META, meta);
    }    @Override
    public BlockStateContainer getBlockState() {
        return new BlockStateContainer(this, META);
    }
	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		int var5 = state.getValue(META);
		float var6 = 0.4375f;

		if (var5 == 4)
		{
			return new AxisAlignedBB(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6);
		}

		if (var5 == 5)
		{
			return new AxisAlignedBB(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6);
		}

		if (var5 == 2)
		{
			return new AxisAlignedBB(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F);
		}

		if (var5 == 3)
		{
			return new AxisAlignedBB(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F);
		}

        return new AxisAlignedBB(0.0F, 0.0F, 0.4375f, 1.0F, 1.0F, 1.0F - 0.4375f);
	}

	// @Override
	// public void onBlockAdded(World world, int x, int y, int z)
	// {
	// world.scheduleBlockUpdate(x, y, z, blockID, world.rand.nextInt(10)+10);
	// }
	//
	// @Override
	// public void updateTick(World par1World, int x, int y, int z, Random par5Random)
	// {
	// if (par1World.rand.nextInt(20) == 0) par1World.setBlock(x, y, z, 0);
	// else par1World.scheduleBlockUpdate(x, y, z, blockID, par1World.rand.nextInt(10)+10);
	// }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		int var5 = state.getValue(META);
		float var6 = 0.4375f;

		if (var5 == 4)
		{
			return new AxisAlignedBB(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6);
		}

		if (var5 == 5)
		{
			return new AxisAlignedBB(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6);
		}

		if (var5 == 2)
		{
            return new AxisAlignedBB(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F);
		}

		if (var5 == 3)
		{
            return new AxisAlignedBB(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F);
		}

		return super.getCollisionBoundingBox(state, world, pos);
	}

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		int var5 = state.getValue(META);
		float var6 = 0.4375f;

		if (var5 == 4)
		{
			return new AxisAlignedBB(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6);
		}

		if (var5 == 5)
		{
            return new AxisAlignedBB(0.0F, 0.0F, var6, 1.0F, 1.0F, 1.0F - var6);
		}

		if (var5 == 2)
		{
            return new AxisAlignedBB(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F);
		}

		if (var5 == 3)
		{
            return new AxisAlignedBB(var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F);
		}

		return super.getSelectedBoundingBox(state, worldIn, pos);
	}

    @Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
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
		icon = iconregister.registerIcon("RivalRebels:di");
	}*/
}
