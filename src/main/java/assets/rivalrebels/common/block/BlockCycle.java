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
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockCycle extends Block
{
	float	phase		= 0;
	float	phaseadd	= (float) (((Math.PI * 2) / 360) * 10);
	float	pShiftR		= (float) (((Math.PI * 2f) / 3f) * 0f);
	float	pShiftG		= (float) (((Math.PI * 2f) / 3f) * 1f);
	float	pShiftB		= (float) (((Math.PI * 2f) / 3f) * 2f);

	public BlockCycle()
	{
		super(Material.iron);
	}

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        world.scheduleBlockUpdate(pos, this, 1, 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass) {
		phase += phaseadd;
		int r = (int) ((Math.sin(phase + pShiftR) + 1f) * 128f);
		int g = (int) ((Math.sin(phase + pShiftG) + 1f) * 128f);
		int b = (int) ((Math.sin(phase + pShiftB) + 1f) * 128f);
		return (r & 0xff) << 16 | (g & 0xff) << 8 | b & 0xff;
	}

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		world.scheduleBlockUpdate(pos, this, 1, 1);
		world.setBlockToAir(pos);
		world.setBlockState(pos, this.getDefaultState());
	}
}
