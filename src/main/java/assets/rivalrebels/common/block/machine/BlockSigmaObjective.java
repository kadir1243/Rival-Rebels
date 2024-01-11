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
import assets.rivalrebels.common.tileentity.TileEntitySigmaObjective;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSigmaObjective extends BlockContainer
{
	public BlockSigmaObjective()
	{
		super(Material.IRON);
		this.setCreativeTab(CreativeTabs.DECORATIONS);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (!pos.equals(RivalRebels.round.sigmaObjPos)) {
			world.setBlockState(RivalRebels.round.sigmaObjPos, RivalRebels.plasmaexplosion.getDefaultState());
			RivalRebels.round.sigmaObjPos = pos;
			if (world.getBlockState(RivalRebels.round.omegaObjPos) == RivalRebels.omegaobj)
				RivalRebels.round.roundManualStart();
		}
	}

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        super.breakBlock(world, pos, state);

		if (world.getBlockState(pos).getBlock() != RivalRebels.plasmaexplosion)
		{
			world.setBlockState(pos, state);
		}
	}

    @Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		RivalRebelsSoundPlayer.playSound(world, 10, 3, pos);

		return true;
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntitySigmaObjective();
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
		icon = iconregister.registerIcon("RivalRebels:bp");
	}*/
}
