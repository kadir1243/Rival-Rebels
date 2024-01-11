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
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockConduit extends Block
{
    public static final PropertyInteger VARIANT = PropertyInteger.create("variant", 0, 8);
	public BlockConduit()
	{
		super(Material.IRON);
        this.setDefaultState(this.getBlockState().getBaseState().withProperty(VARIANT, 0));
    }
    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(VARIANT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, meta);
    }
    @Override
    public BlockStateContainer getBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }@Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(VARIANT, world.rand.nextInt(9));
    }

    /*@SideOnly(Side.CLIENT)
	IIcon	icon1;
	@SideOnly(Side.CLIENT)
	IIcon	icon2;
	@SideOnly(Side.CLIENT)
	IIcon	icon3;
	@SideOnly(Side.CLIENT)
	IIcon	icon4;
	@SideOnly(Side.CLIENT)
	IIcon	icon5;
	@SideOnly(Side.CLIENT)
	IIcon	icon6;
	@SideOnly(Side.CLIENT)
	IIcon	icon7;
	@SideOnly(Side.CLIENT)
	IIcon	icon8;
	@SideOnly(Side.CLIENT)
	IIcon	icon9;

	@Override
	public final IIcon getIcon(int side, int meta)
	{
		if (meta == 1) return icon1;
		if (meta == 2) return icon2;
		if (meta == 3) return icon3;
		if (meta == 4) return icon4;
		if (meta == 5) return icon5;
		if (meta == 6) return icon6;
		if (meta == 7) return icon7;
		if (meta == 8) return icon8;
		if (meta == 9) return icon9;
		return icon1;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconregister)
	{
		icon1 = iconregister.registerIcon("RivalRebels:co");
		icon2 = iconregister.registerIcon("RivalRebels:cp");
		icon3 = iconregister.registerIcon("RivalRebels:cq");
		icon4 = iconregister.registerIcon("RivalRebels:cr");
		icon5 = iconregister.registerIcon("RivalRebels:cs");
		icon6 = iconregister.registerIcon("RivalRebels:ct");
		icon7 = iconregister.registerIcon("RivalRebels:cu");
		icon8 = iconregister.registerIcon("RivalRebels:cv");
		icon9 = iconregister.registerIcon("RivalRebels:cw");
	}*/
}
