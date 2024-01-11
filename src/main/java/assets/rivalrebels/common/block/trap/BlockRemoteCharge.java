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
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityRoddiskLeader;
import assets.rivalrebels.common.entity.EntityRoddiskOfficer;
import assets.rivalrebels.common.entity.EntityRoddiskRebel;
import assets.rivalrebels.common.entity.EntityRoddiskRegular;
import assets.rivalrebels.common.explosion.Explosion;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BlockRemoteCharge extends BlockFalling {
    public static final PropertyInteger META = PropertyInteger.create("meta", 0, 15);
	public BlockRemoteCharge()
	{
		super(Material.CLOTH);
		setTickRandomly(true);
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
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		int i = state.getValue(META);
		float f = 0.0625F;
		float f1 = (1 + i * 2) / 16F;
		float f2 = 0.5F;
		return new AxisAlignedBB(f1, 0.0F, f, 1.0F - f, f2, 1.0F - f);
	}

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
		int meta = state.getValue(META);
		float f = 0.0625F;
		float f1 = (1 + meta * 2) / 16F;
		float f2 = 0.5F;
		return new AxisAlignedBB(x + f1, y, z + f, (x + 1) - f, (y + f2) - f, (z + 1) - f);
	}

    @Override
	public int tickRate(World world)
	{
		return 1;
	}

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        int i = state.getValue(META);
		float f = 0.0625F;
		float f1 = (1 + i * 2) / 16F;
		float f2 = 0.5F;
		return new AxisAlignedBB(x + f1, y, z + f, (x + 1) - f, y + f2, (z + 1) - f);
	}

    @Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

    @Override
    public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state) {
        explode(worldIn, pos);
    }

    @Override
    public void onExplosionDestroy(World worldIn, BlockPos pos, net.minecraft.world.Explosion explosionIn) {
		explode(worldIn, pos);
	}

	public boolean boom = false;

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		world.scheduleBlockUpdate(pos, this, this.tickRate(world), 1);
	}

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        for (EnumFacing facing : EnumFacing.VALUES) {
            if (world.getBlockState(pos.offset(facing)).getBlock() == Blocks.FIRE) {
                explode(world, pos);
            }
        }

		if (boom)
		{
			explode(world, pos);
			boom = false;
		}
		world.scheduleBlockUpdate(pos, this, this.tickRate(world), 1);
	}

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
		if (entity instanceof EntityRoddiskRegular || entity instanceof EntityRoddiskRebel || entity instanceof EntityRoddiskOfficer || entity instanceof EntityRoddiskLeader)
		{
			explode(world, pos);
		}
	}

	public static void explode(World world, BlockPos pos)
	{
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        world.setBlockToAir(pos);
		new Explosion(world, x + 0.5f, y + 0.5f, z + 0.5f, RivalRebels.chargeExplodeSize, false, false, RivalRebelsDamageSource.charge);
		RivalRebelsSoundPlayer.playSound(world, 22, 0, x, y, z, 1f, 0.3f);
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
		icon1 = iconregister.registerIcon("RivalRebels:ag"); // BOTTOM
		icon2 = iconregister.registerIcon("RivalRebels:ag"); // TOP
		icon3 = iconregister.registerIcon("RivalRebels:af"); // SIDE N
		icon4 = iconregister.registerIcon("RivalRebels:af"); // SIDE S
		icon5 = iconregister.registerIcon("RivalRebels:af"); // SIDE W
		icon6 = iconregister.registerIcon("RivalRebels:af"); // SIDE E
	}*/

    @Override
    public void onEndFalling(World worldIn, BlockPos pos, IBlockState fallingState, IBlockState hitState) {
        explode(worldIn, pos);
    }
}
