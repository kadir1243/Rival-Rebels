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

import assets.rivalrebels.common.entity.EntityBlood;
import assets.rivalrebels.common.entity.EntityGoo;
import assets.rivalrebels.common.tileentity.TileEntityGore;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BlockGore extends BlockContainer {
    public static final PropertyInteger META = PropertyInteger.create("meta", 0, 5);
	public BlockGore()
	{
		super(Material.CAKE);
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
	public int quantityDropped(Random random)
	{
		return 0;
	}

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player.capabilities.isCreativeMode)
		{
			int meta = state.getValue(META) + 1;
			if (meta >= 6) meta = 0;
			world.setBlockState(pos, state.withProperty(META, meta));
			return true;
		}
		return false;
	}

    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (world.isAirBlock(pos.down()) && state.getValue(META) < 2) world.spawnEntity(new EntityBlood(world, pos.getX() + rand.nextDouble(), pos.getY() + 0.9f, pos.getZ() + rand.nextDouble()));
		else if (world.isAirBlock(pos.down()) && state.getValue(META) < 4) world.spawnEntity(new EntityGoo(world, pos.getX() + rand.nextDouble(), pos.getY() + 0.9f, pos.getZ() + rand.nextDouble()));
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return null;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return new AxisAlignedBB(BlockPos.ORIGIN, BlockPos.ORIGIN);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (world.isBlockNormalCube(pos.down(), false) ||
            world.isBlockNormalCube(pos.east(), false) ||
            world.isBlockNormalCube(pos.west(), false) ||
            world.isBlockNormalCube(pos.south(), false) ||
            world.isBlockNormalCube(pos.north(), false) ||
            world.isBlockNormalCube(pos.up(), false)) {
			world.setBlockToAir(pos);
		}
	}

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
	public TileEntity createNewTileEntity(World var1, int var)
	{
		return new TileEntityGore();
	}

	/*@SideOnly(Side.CLIENT)
	IIcon	icon;
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

	@Override
	public final IIcon getIcon(int side, int meta)
	{
		if (meta == 0) return icon;
		if (meta == 1) return icon2;
		if (meta == 2) return icon3;
		if (meta == 3) return icon4;
		if (meta == 4) return icon5;
		if (meta == 5) return icon6;
		else
		{
			return icon;
		}
	}

	@Override
	public void registerBlockIcons(IIconRegister iconregister)
	{
		icon = iconregister.registerIcon("RivalRebels:br");
		icon2 = iconregister.registerIcon("RivalRebels:bs");
		icon3 = iconregister.registerIcon("RivalRebels:bt");
		icon4 = iconregister.registerIcon("RivalRebels:bu");
		icon5 = iconregister.registerIcon("RivalRebels:bv");
		icon6 = iconregister.registerIcon("RivalRebels:bw");
	}*/
}
