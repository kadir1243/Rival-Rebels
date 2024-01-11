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
import assets.rivalrebels.common.tileentity.TileEntityLoader;
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

public class BlockLoader extends BlockContainer {
    public BlockLoader() {
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
        TileEntityLoader var7 = null;
        try
        {
            var7 = (TileEntityLoader) world.getTileEntity(pos);
        }
        catch (Exception e)
        {
            // no error message ;]
        }

        int x = pos.getX();
        int y= pos.getY();
        int z =pos.getZ();
        world.spawnEntity(new EntityItem(world, x, y, z, new ItemStack(RivalRebels.loader)));
        if (var7 != null)
        {
            for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8)
            {
                ItemStack var9 = var7.getStackInSlot(var8);

                if (var9 != null)
                {
                    float var10 = world.rand.nextFloat() * 0.8F + 0.1F;
                    float var11 = world.rand.nextFloat() * 0.8F + 0.1F;
                    EntityItem var14;

                    for (float var12 = world.rand.nextFloat() * 0.8F + 0.1F; var9.getCount() > 0; world.spawnEntity(var14))
                    {
                        int var13 = world.rand.nextInt(21) + 10;

                        if (var13 > var9.getCount())
                        {
                            var13 = var9.getCount();
                        }

                        var9.shrink(var13);
                        var14 = new EntityItem(world, (x + var10), (y + var11), (z + var12), new ItemStack(var9.getItem(), var13, var9.getItemDamage()));
                        float var15 = 0.05F;
                        var14.motionX = ((float) world.rand.nextGaussian() * var15);
                        var14.motionY = ((float) world.rand.nextGaussian() * var15 + 0.2F);
                        var14.motionZ = ((float) world.rand.nextGaussian() * var15);

                        if (var9.hasTagCompound())
                        {
                            var14.getItem().setTagCompound(var9.getTagCompound().copy());
                        }
                    }
                }
            }
            var7.invalidate();
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
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
		return new TileEntityLoader();
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
		icon = iconregister.registerIcon("RivalRebels:av");
	}*/
}
