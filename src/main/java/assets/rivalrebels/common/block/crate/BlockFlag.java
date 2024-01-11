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
package assets.rivalrebels.common.block.crate;

import assets.rivalrebels.RivalRebels;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BlockFlag extends Block {
    public static final PropertyInteger META = PropertyInteger.create("meta", 0, 15);
	String	texpath	= "rivalrebels:";

	public BlockFlag(String name)
	{
		super(Material.CLOTH);
		texpath += name;
		//this.setCreativeTab(RivalRebels.rrarmortab);
        this.setDefaultState(this.getBlockState().getBaseState().withProperty(META, 0));
	}

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(META);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(META, meta);
    }

    @Override
    public BlockStateContainer getBlockState() {
        return new BlockStateContainer(this, META);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if (this == RivalRebels.flag2) return RivalRebels.trollmask;
        return Item.getItemFromBlock(this);
    }

	/*@Override
	public void setBlockBoundsForItemRender()
	{
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public int getRenderType()
	{
		return 20;
	}*/

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		int l = state.getValue(META);
		float f1 = 1.0F;
		float f2 = 1.0F;
		float f3 = 1.0F;
		float f4 = 0.0F;
		float f5 = 0.0F;
		float f6 = 0.0F;
		boolean flag = l > 0;

		if ((l & 2) != 0)
		{
			f4 = Math.max(f4, 0.0625F);
			f1 = 0.0F;
			f2 = 0.0F;
			f5 = 1.0F;
			f3 = 0.0F;
			f6 = 1.0F;
			flag = true;
		}

		if ((l & 8) != 0)
		{
			f1 = Math.min(f1, 0.9375F);
			f4 = 1.0F;
			f2 = 0.0F;
			f5 = 1.0F;
			f3 = 0.0F;
			f6 = 1.0F;
			flag = true;
		}

		if ((l & 4) != 0)
		{
			f6 = Math.max(f6, 0.0625F);
			f3 = 0.0F;
			f1 = 0.0F;
			f4 = 1.0F;
			f2 = 0.0F;
			f5 = 1.0F;
			flag = true;
		}

		if ((l & 1) != 0)
		{
			f3 = Math.min(f3, 0.9375F);
			f6 = 1.0F;
			f1 = 0.0F;
			f4 = 1.0F;
			f2 = 0.0F;
			f5 = 1.0F;
			flag = true;
		}

		if (!flag && this.func_150093_a(worldIn.getBlockState(pos.up())))
		{
			f2 = Math.min(f2, 0.9375F);
			f5 = 1.0F;
			f1 = 0.0F;
			f4 = 1.0F;
			f3 = 0.0F;
			f6 = 1.0F;
		}

		return new AxisAlignedBB(f1, f2, f3, f4, f5, f6);
	}

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return null;
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
        if (side == EnumFacing.DOWN) return false;
        return this.func_150093_a(worldIn.getBlockState(pos.offset(side)));
	}

	private boolean func_150093_a(IBlockState p_150093_1_)
	{
		return p_150093_1_.isFullBlock();
	}

	private boolean func_150094_e(IBlockState state, World world, BlockPos pos)
	{
		int l = state.getValue(META);
		int i1 = l;

		if (l > 0)
		{
			for (int j1 = 0; j1 <= 3; ++j1)
			{
				int k1 = 1 << j1;
                EnumFacing facing = EnumFacing.byIndex(j1);

                if ((l & k1) != 0 && !this.func_150093_a(world.getBlockState(pos.offset(facing))) && (world.getBlockState(pos.up()).getBlock() != this || (world.getBlockState(pos.up()).getBlock().getMetaFromState(world.getBlockState(pos.up())) & k1) == 0))
				{
					i1 &= ~k1;
				}
			}
		}

		if (i1 == 0 && !this.func_150093_a(world.getBlockState(pos.up())))
		{
			return false;
		}
		else
		{
			if (i1 != l)
			{
				world.setBlockState(pos, state.withProperty(META, i1), 2);
			}

			return true;
		}
	}

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!worldIn.isRemote && !this.func_150094_e(state, worldIn, pos))
		{
			this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
			worldIn.setBlockToAir(pos);
		}
	}

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        byte m = switch (facing) {
            case NORTH -> 1;
            case SOUTH -> 4;
            case EAST -> 8;
            case WEST -> 2;
            default -> 0;
        };
        return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, m != 0 ? m : meta, placer);
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
		icon = iconregister.registerIcon(texpath);
	}*/
}
