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
package assets.rivalrebels.common.block.trap;

import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPetrifiedStone extends Block {
    public static final PropertyInteger RADIOACTIVITY = PropertyInteger.create("radioactivity", 0, 15);

	public BlockPetrifiedStone() {
		super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(RADIOACTIVITY, 7));
	}

    @Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor()
	{
		return 0x444444;
	}

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (world.rand.nextInt(2) == 0) {
            entity.attackEntityFrom(RivalRebelsDamageSource.radioactivepoisoning, ((float) (16 - state.getValue(RADIOACTIVITY)) / 2) + world.rand.nextInt(3) - 1);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderColor(IBlockState state) {
        return 0x444444;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (player.capabilities.isCreativeMode)
		{
            world.setBlockState(pos, state.withProperty(RADIOACTIVITY, state.getValue(RADIOACTIVITY) + 1));
			return true;
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
    @Override
    public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass) {
        return world.getBlockState(pos).getValue(RADIOACTIVITY) * 0x111111;
    }
}
