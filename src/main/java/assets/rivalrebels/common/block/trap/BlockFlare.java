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
import assets.rivalrebels.common.explosion.Explosion;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFlare extends Block {
    public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class, input -> input == EnumFacing.UP);
	public BlockFlare()
	{
		super(Material.wood);
	}

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
        return null;
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

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean isFullCube()
	{
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return 2;
	}

	private boolean canPlaceOn(World world, BlockPos pos) {
        if (World.doesBlockHaveSolidTopSurface(world, pos)) {
            return true;
        } else {
            Block block = world.getBlockState(pos).getBlock();
            return block.canPlaceTorchOnTop(world, pos);
        }
	}

    private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
        BlockPos blockpos = pos.offset(facing.getOpposite());
        boolean flag = facing.getAxis().isHorizontal();
        return flag && worldIn.isSideSolid(blockpos, facing, true) || facing.equals(EnumFacing.UP) && this.canPlaceOn(worldIn, blockpos);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        for (EnumFacing enumfacing : FACING.getAllowedValues()) {
            if (this.canPlaceAt(worldIn, pos, enumfacing)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if (this.canPlaceAt(worldIn, pos, facing)) {
            return this.getDefaultState().withProperty(FACING, facing);
        }
        else
        {
            for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
            {
                if (worldIn.isSideSolid(pos.offset(enumfacing.getOpposite()), enumfacing, true))
                {
                    return this.getDefaultState().withProperty(FACING, enumfacing);
                }
            }

            return this.getDefaultState();
        }
	}

    @Override
    public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
		world.spawnParticle(EnumParticleTypes.LAVA, x + .5, y + .6, z + .5, 0, 0.5, 0);
		world.spawnParticle(EnumParticleTypes.LAVA, x + .5, y + .8, z + .5, 0, 0.5, 0);
		world.spawnParticle(EnumParticleTypes.LAVA, x + .5, y + 1, z + .5, 0, 0.5, 0);
		world.spawnParticle(EnumParticleTypes.FLAME, x + .5, y + 1.2, z + .5, (-0.5 + world.rand.nextFloat()) * 0.1, 0.5 + world.rand.nextFloat() * 0.5, (-0.5 + world.rand.nextFloat()) * 0.1);
		world.spawnParticle(EnumParticleTypes.FLAME, x + .5, y + 1.4, z + .5, (-0.5 + world.rand.nextFloat()) * 0.1, 0.5 + world.rand.nextFloat() * 0.5, (-0.5 + world.rand.nextFloat()) * 0.1);
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + .5, y + 1.6, z + .5, (-0.5 + world.rand.nextFloat()) * 0.1, 0.5 + world.rand.nextFloat() * 0.5, (-0.5 + world.rand.nextFloat()) * 0.1);
		world.playSoundEffect(x, y, z, "random.fizz", 3F, 2);
	}

    @Override
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
        if (this.checkForDrop(worldIn, pos, state)) {
            EnumFacing enumfacing = state.getValue(FACING);
            EnumFacing.Axis axis = enumfacing.getAxis();
            EnumFacing opposite = enumfacing.getOpposite();
            boolean flag = false;

            if (axis.isHorizontal() && !worldIn.isSideSolid(pos.offset(opposite), enumfacing, true)) {
                flag = true;
            } else if (axis.isVertical() && !this.canPlaceOn(worldIn, pos.offset(opposite))) {
                flag = true;
            }

            if (flag) {
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }
        }
    }

    protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
        if (state.getBlock() == this && this.canPlaceAt(worldIn, pos, state.getValue(FACING))) {
            return true;
        } else {
            if (worldIn.getBlockState(pos).getBlock() == this) {
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }

            return false;
        }
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state) {
		if (RivalRebels.flareExplode) {
            world.setBlockToAir(pos);
			new Explosion(world, pos.getX(), pos.getY(), pos.getZ(), 3, true, false, RivalRebelsDamageSource.flare);
			world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "random.explode", 0.5f, 0.3f);
		}
	}

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entity) {
		entity.attackEntityFrom(RivalRebelsDamageSource.flare, 1);
		entity.setFire(5);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.VALUES[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }
}
