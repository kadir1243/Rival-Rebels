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
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockForceShield extends Block
{
	public BlockForceShield()
	{
		super(Material.IRON);
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	boolean	Destroy	= false;

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
		Block id = world.getBlockState(pos).getBlock();
		if (!Destroy && id != RivalRebels.fshield && id != RivalRebels.omegaobj && id != RivalRebels.sigmaobj && id != RivalRebels.reactive)
		{
			world.setBlockState(pos, state);
		}
		Destroy = false;
	}

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (!world.isRemote && player.capabilities.isCreativeMode && player.isSneaking())
		{
			Destroy = true;
			world.setBlockToAir(pos);
		}
		else
		{
			Destroy = false;
			world.setBlockState(pos, state);
		}
	}

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumPushReaction getPushReaction(IBlockState state) {
        return EnumPushReaction.BLOCK;
    }

    /*@SideOnly(Side.CLIENT)
	IIcon	icon1;
	IIcon	icon2;

	@Override
	public final IIcon getIcon(int side, int meta)
	{
		if (side == 0 || side == 1) return icon2;
		return icon1;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconregister)
	{
		icon1 = iconregister.registerIcon("RivalRebels:ao");
		icon2 = iconregister.registerIcon("RivalRebels:ap");
	}*/
}
