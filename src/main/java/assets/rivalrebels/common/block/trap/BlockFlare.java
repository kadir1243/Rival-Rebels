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
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BlockFlare extends Block {
    public static final PropertyInteger META = PropertyInteger.create("meta", 0, 15);
	public BlockFlare()
	{
		super(Material.WOOD);
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
    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return null;
    }

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

    /*@Override
	public int getRenderType()
	{
		return 2;
	}*/

	private boolean canPlaceTorchOn(World world, BlockPos pos)
	{
		if (world.isBlockNormalCube(pos, true))
		{
			return true;
		}

        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

		if (block == Blocks.OAK_FENCE || block == Blocks.NETHER_BRICK_FENCE || block == Blocks.GLASS)
		{
			return true;
		}

		if (block instanceof BlockStairs) {
			int j = block.getMetaFromState(state);

            return (4 & j) != 0;
		}

		return false;
	}

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return canPlaceTorchOn(world, pos.down());
	}

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        int i = world.getBlockState(pos).getValue(META);

        if (facing == EnumFacing.UP && canPlaceTorchOn(world, pos.down()))
        {
            i = 5;
        }

        if (facing == EnumFacing.NORTH && world.isBlockNormalCube(pos.south(), true))
        {
            i = 4;
        }

        if (facing == EnumFacing.SOUTH && world.isBlockNormalCube(pos.north(), true))
        {
            i = 3;
        }

        if (facing == EnumFacing.WEST && world.isBlockNormalCube(pos.east(), true))
        {
            i = 2;
        }

        if (facing == EnumFacing.EAST && world.isBlockNormalCube(pos.west(), true))
        {
            i = 1;
        }
        return getDefaultState().withProperty(META, i);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        worldIn.scheduleBlockUpdate(pos, this, 1, 1);
    }

    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        int x = pos.getX();
        int y= pos.getY();
        int z = pos.getZ();
		world.spawnParticle(EnumParticleTypes.LAVA, x + .5, y + .6, z + .5, 0, 0.5, 0);
		world.spawnParticle(EnumParticleTypes.LAVA, x + .5, y + .8, z + .5, 0, 0.5, 0);
		world.spawnParticle(EnumParticleTypes.LAVA, x + .5, y + 1, z + .5, 0, 0.5, 0);
		world.spawnParticle(EnumParticleTypes.FLAME, x + .5, y + 1.2, z + .5, (-0.5 + rand.nextFloat()) * 0.1, 0.5 + rand.nextFloat() * 0.5, (-0.5 + rand.nextFloat()) * 0.1);
		world.spawnParticle(EnumParticleTypes.FLAME, x + .5, y + 1.4, z + .5, (-0.5 + rand.nextFloat()) * 0.1, 0.5 + rand.nextFloat() * 0.5, (-0.5 + rand.nextFloat()) * 0.1);
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + .5, y + 1.6, z + .5, (-0.5 + rand.nextFloat()) * 0.1, 0.5 + rand.nextFloat() * 0.5, (-0.5 + rand.nextFloat()) * 0.1);
		world.playSound(x, y, z, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 3F, 2, true);
	}

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		world.scheduleBlockUpdate(pos, this, 1, 1);
		if (world.getBlockState(pos.east()).getBlock() == Blocks.WATER ||
            world.getBlockState(pos.west()).getBlock() == Blocks.WATER ||
            world.getBlockState(pos.south()).getBlock() == Blocks.WATER ||
            world.getBlockState(pos.north()).getBlock() == Blocks.WATER)
		{
			world.setBlockToAir(pos);
			world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(RivalRebels.flare)));
		}
	}

    @Override
    public void onPlayerDestroy(World world, BlockPos pos, IBlockState state) {
		if (RivalRebels.flareExplode)
		{
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            world.setBlockToAir(pos);
			new Explosion(world, x, y, z, 3, true, false, RivalRebelsDamageSource.flare);
			world.playSound(x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 0.5f, 0.3f, true);
		}
	}

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
		entity.attackEntityFrom(RivalRebelsDamageSource.flare, 1);
		entity.setFire(5);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
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
		icon = iconregister.registerIcon("RivalRebels:an");
	}*/
}
