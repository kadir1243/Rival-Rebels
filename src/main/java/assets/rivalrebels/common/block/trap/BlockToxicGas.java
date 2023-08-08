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
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockToxicGas extends Block
{
	public BlockToxicGas() {
		super(Material.cactus);
		setTickRandomly(true);
	}

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 300;
    }

    @Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
        return null;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
        return new AxisAlignedBB(pos, pos);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity) {
		if (entity instanceof EntityPlayer) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 200, 0));
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 0));
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.blindness.id, 80, 0));
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 80, 0));
		}
		if (entity instanceof EntityMob || entity instanceof EntityAnimal || entity instanceof EntityPlayer) {
			entity.attackEntityFrom(RivalRebelsDamageSource.gasgrenade, 1);
		}
	}

	@Override
	public int getRenderType()
	{
		return -1;
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        worldIn.scheduleBlockUpdate(pos, this, 8, 1);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		world.scheduleBlockUpdate(pos, this, 8, 1);
		if (rand.nextInt(25) == 1)
		{
			world.setBlockToAir(pos);
		}
	}

    @Override
    public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random r) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + 0.5, y + 0.5, z + 0.5, (r.nextFloat() - 0.5) * 0.1, (r.nextFloat() - 0.5) * 0.1, (r.nextFloat() - 0.5) * 0.1);
		world.spawnParticle(EnumParticleTypes.SPELL, x + 0.5, y + 0.5, z + 0.5, (r.nextFloat() - 0.5) * 0.1, (r.nextFloat() - 0.5) * 0.1, (r.nextFloat() - 0.5) * 0.1);
	}
}
