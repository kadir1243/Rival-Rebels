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
import assets.rivalrebels.common.tileentity.TileEntityLaptop;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockLaptop extends BlockContainer
{

    public BlockLaptop()
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
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		int l = MathHelper.floor((placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        int metaS = meta;
		if (l == 0) {
			metaS = 2;
		} else if (l == 1) {
			metaS = 5;
		} else if (l == 2) {
			metaS = 3;
		} else if (l == 3) {
            metaS = 4;
		}
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, metaS, placer, hand);
	}

    @Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity entity = world.getTileEntity(pos);
        if (!(entity instanceof TileEntityLaptop)) {
            entity = null;
        }

        world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(RivalRebels.controller, 1)));

        entity.invalidate();
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		((TileEntityLaptop) world.getTileEntity(pos)).refreshTasks();
		if (!world.isRemote) {
			player.openGui(RivalRebels.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
		}
		RivalRebelsSoundPlayer.playSound(world, 10, 3, pos);

		return true;
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World par1World, int var)
	{
		return new TileEntityLaptop();
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
		icon = iconregister.registerIcon("RivalRebels:dc");
	}*/
}
