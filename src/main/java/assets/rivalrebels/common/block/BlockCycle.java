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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCycle extends Block
{
	public float	phase		= 0;
	public float	phaseadd	= (float) (((Math.PI * 2) / 360) * 10);
	public float	pShiftR		= (float) (((Math.PI * 2f) / 3f) * 0f);
	public float	pShiftG		= (float) (((Math.PI * 2f) / 3f) * 1f);
	public float	pShiftB		= (float) (((Math.PI * 2f) / 3f) * 2f);

	public BlockCycle()
	{
		super(Material.IRON);
	}

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        world.scheduleBlockUpdate(pos, this, 1, 1);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        world.scheduleBlockUpdate(pos, this, 1, 1);
        world.setBlockToAir(pos);
        world.setBlockState(pos, state);
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
		icon = iconregister.registerIcon("RivalRebels:ak");
	}*/
}
