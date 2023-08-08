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
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRemoteCharge extends Block {
    // I don't know where is it set
    public static final PropertyInteger SHAPE = PropertyInteger.create("shape", 0, 15);
	public BlockRemoteCharge() {
		super(Material.cloth);
		setTickRandomly(true);
		float f = 0.0625F;
		float f1 = 0.0625f;
		float f2 = 0.5F;
		setBlockBounds(f1, 0.0F, f, 1.0F - f, f2, 1.0F - f);
        this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, 0));
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
        int i = world.getBlockState(pos).getValue(SHAPE);
        float f = 0.0625F;
        float f1 = (1 + i * 2) / 16F;
        float f2 = 0.5F;
        setBlockBounds(f1, 0.0F, f, 1.0F - f, f2, 1.0F - f);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		int i = state.getValue(SHAPE);
		float f = 0.0625F;
		float f1 = (1 + i * 2) / 16F;
		float f2 = 0.5F;
		return new AxisAlignedBB(pos.getX() + f1, pos.getY(), pos.getZ() + f, (pos.getX() + 1) - f, (pos.getY() + f2) - f, (pos.getZ() + 1) - f);
	}

	/**
	 * How many world ticks before ticking
	 */
	@Override
	public int tickRate(World par1World) {
		return 1;
	}

    @Override
    public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos pos) {
        int i = world.getBlockState(pos).getValue(SHAPE);
        float f = 0.0625F;
        float f1 = (1 + i * 2) / 16F;
        float f2 = 0.5F;
        return new AxisAlignedBB(pos.getX() + f1, pos.getY(), pos.getZ() + f, (pos.getX() + 1) - f, (pos.getY() + f2) - f, (pos.getZ() + 1) - f);
    }

    /**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean isFullCube()
	{
		return false;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether or not to render the shared face of two adjacent blocks and also whether the player can attach torches, redstone wire,
	 * etc to this block.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

    @Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        explode(worldIn, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, net.minecraft.world.Explosion explosionIn) {
        explode(worldIn, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		world.scheduleBlockUpdate(pos, this, this.tickRate(world), 1);
	}

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        for (EnumFacing facing : EnumFacing.VALUES) {
            if (world.getBlockState(pos.offset(facing)).getBlock() == Blocks.fire) {
                explode(world, pos.getX(), pos.getY(), pos.getZ());
                break;
            }
        }

		world.scheduleBlockUpdate(pos, this, this.tickRate(world), 1);
	}

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity) {
        if (entity instanceof EntityRoddiskRegular || entity instanceof EntityRoddiskRebel || entity instanceof EntityRoddiskOfficer || entity instanceof EntityRoddiskLeader) {
            explode(world, pos.getX(), pos.getY(), pos.getZ());
        }
    }

    public static void explode(World world, int x, int y, int z)
	{
		world.setBlockToAir(new BlockPos(x, y, z));
		new Explosion(world, x + 0.5f, y + 0.5f, z + 0.5f, RivalRebels.chargeExplodeSize, false, false, RivalRebelsDamageSource.charge);
		RivalRebelsSoundPlayer.playSound(world, 22, 0, x, y, z, 1f, 0.3f);
	}

	/**
	 * Called when the falling block entity for this block hits the ground and turns back into a block
	 */
	public void onFinishFalling(World par1World, int par2, int par3, int par4, int par5)
	{
		explode(par1World, par2, par3, par4);
	}

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, SHAPE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(SHAPE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(SHAPE, meta);
    }
}
