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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.tileentity.TileEntityOmegaObjective;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockOmegaObjective extends BlockContainer
{
	public BlockOmegaObjective()
	{
		super(Material.iron);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (!pos.equals(RivalRebels.round.oObj))
		{
			world.setBlockState(RivalRebels.round.oObj, RivalRebels.plasmaexplosion.getDefaultState());
            RivalRebels.round.oObj = pos;
			if (world.getBlockState(RivalRebels.round.oObj).getBlock() == RivalRebels.sigmaobj)
				RivalRebels.round.roundManualStart();
		}
	}

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        super.breakBlock(world, pos, state);
        if (world.getBlockState(pos).getBlock() != RivalRebels.plasmaexplosion) {
            world.setBlockState(pos, state);
        }
    }

    @Override
    public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return player.capabilities.isCreativeMode || super.canHarvestBlock(world, pos, player);
    }

    /**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether or not to render the shared face of two adjacent blocks and also whether the player can attach torches, redstone wire,
	 * etc to this block.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean isFullCube()
	{
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return -1;
	}

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        RivalRebelsSoundPlayer.playSound(worldIn, 10, 3, pos);

        return true;
    }

    /**
	 * Returns a new instance of a block's tile entity class. Called on placing the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World par1World, int var)
	{
		return new TileEntityOmegaObjective();
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
		icon = iconregister.registerIcon("RivalRebels:ba");
	}*/
}
