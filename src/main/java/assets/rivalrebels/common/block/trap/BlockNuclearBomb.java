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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.tileentity.TileEntityNuclearBomb;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.Random;

public class BlockNuclearBomb extends BlockContainer {
    public static final PropertyInteger META = PropertyInteger.create("meta", 0, 15);
	public BlockNuclearBomb()
	{
		super(Material.IRON);
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
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	public static int determineOrientation(World world, int x, int y, int z, EntityPlayer entity)
	{
		if (MathHelper.abs((float) entity.posX - x) < 2.0F && MathHelper.abs((float) entity.posZ - z) < 2.0F)
		{
			double var5 = entity.posY + 1.82D - entity.getYOffset();

			if (var5 - y > 2.0D)
			{
				return 1;
			}

			if (y - var5 > 0.0D)
			{
				return 0;
			}
		}

		int var7 = MathHelper.floor((entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		return var7 == 0 ? 2 : (var7 == 1 ? 5 : (var7 == 2 ? 3 : (var7 == 3 ? 4 : 0)));
	}

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
		if (MathHelper.abs((float) placer.posX - x) < 2.0F && MathHelper.abs((float) placer.posZ - z) < 2.0F)
		{
			double var5 = placer.posY + 1.82D - placer.getYOffset();

			if (var5 - y > 2.0D)
			{
				world.setBlockState(pos, state.withProperty(META, 1));
				return;
			}

			if (y - var5 > 0.0D)
			{
                world.setBlockState(pos, state.withProperty(META, 0));
				return;
			}
		}
		int var7 = MathHelper.floor((placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		world.setBlockState(pos, state.withProperty(META, var7 == 0 ? 2 : (var7 == 1 ? 5 : (var7 == 2 ? 3 : (var7 == 3 ? 4 : 0)))));
	}

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!player.isSneaking())
		{
            ItemStack stack = player.getHeldItem(hand);
            if (!stack.isEmpty() && stack.getItem() == RivalRebels.pliers)
			{
				player.openGui(RivalRebels.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
			}
			else if (!world.isRemote)
			{
				player.sendMessage(new TextComponentString("§7[§4Orders§7] §cUse pliers to open."));
			}
		}
		return false;
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	/**
	 * each class overrides this to return a new <className>
	 */
	@Override
	public TileEntity createNewTileEntity(World par1World, int var)
	{
		return new TileEntityNuclearBomb();
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
