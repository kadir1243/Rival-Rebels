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
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPetrifiedStone extends Block {
    public static final PropertyInteger META = PropertyInteger.create("meta", 0, 15);
	public String type;
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
	public BlockPetrifiedStone(String type)
	{
		super(Material.ROCK);
		this.type = type;
        this.setDefaultState(this.getBlockState().getBaseState().withProperty(META, 0));
    }

    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
		if (world.rand.nextInt(2) == 0)
		{
			entity.attackEntityFrom(RivalRebelsDamageSource.radioactivepoisoning, ((16 - world.getBlockState(pos).getValue(META)) / 2) + world.rand.nextInt(3) - 1);
		}
	}

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player.capabilities.isCreativeMode)
		{
			world.setBlockState(pos, state.withProperty(META, state.getValue(META) + 1), 3);
			return true;
		}
		return false;
	}

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (placer instanceof EntityPlayer)
		{
			world.setBlockState(pos, state.withProperty(META, 7));
		}
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
	@Override
	public final IIcon getIcon(int side, int meta)
	{
		if (side == 0) return icon1;
		if (side == 1) return icon2;
		if (side == 2) return icon3;
		if (side == 3) return icon4;
		if (side == 4) return icon5;
		if (side == 5) return icon6;
		return icon1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister)
	{
		icon1 = iconregister.registerIcon("RivalRebels:b" + type); // BOTTOM
		icon2 = iconregister.registerIcon("RivalRebels:b" + type); // TOP
		icon3 = iconregister.registerIcon("RivalRebels:bb"); // SIDE N
		icon4 = iconregister.registerIcon("RivalRebels:bb"); // SIDE S
		icon5 = iconregister.registerIcon("RivalRebels:bb"); // SIDE W
		icon6 = iconregister.registerIcon("RivalRebels:bb"); // SIDE E
	}*/
}
