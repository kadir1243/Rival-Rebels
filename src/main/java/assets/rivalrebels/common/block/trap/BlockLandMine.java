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
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityRoddiskLeader;
import assets.rivalrebels.common.entity.EntityRoddiskOfficer;
import assets.rivalrebels.common.entity.EntityRoddiskRebel;
import assets.rivalrebels.common.entity.EntityRoddiskRegular;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockLandMine extends BlockFalling {
    public static final PropertyBool EXPLODE = PropertyBool.create("explode");
	public BlockLandMine() {
		super();

        this.setDefaultState(this.blockState.getBaseState().withProperty(EXPLODE, false));
	}

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(RivalRebels.alandmine);
    }

    @Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		if (this == RivalRebels.landmine) {
			return Blocks.grass.getBlockColor();
		}
		return 0xFFFFFF;
	}

	@SideOnly(Side.CLIENT)
    @Override
    public int getRenderColor(IBlockState state) {
        return Blocks.grass.getRenderColor(state);
    }

	@SideOnly(Side.CLIENT)
    @Override
    public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass) {
        if (this == RivalRebels.landmine) {
            return Blocks.grass.colorMultiplier(world, pos, renderPass);
        }

        return 0xFFFFFF;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if (world.getBlockState(pos).getValue(EXPLODE)) {
			world.setBlockToAir(pos);
			if (!world.isRemote) world.createExplosion(null, pos.getX(), pos.getY() + 2.5f, pos.getZ(), RivalRebels.landmineExplodeSize, true);
		}
	}

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
        return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1 - 0.01F, pos.getZ() + 1);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
        return new AxisAlignedBB(pos, pos.add(1, 1, 1));
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (entity instanceof EntityPlayer || entity instanceof EntityAnimal || entity instanceof EntityMob || entity instanceof EntityRoddiskRegular || entity instanceof EntityRoddiskRebel || entity instanceof EntityRoddiskOfficer || entity instanceof EntityRoddiskLeader) {
            world.setBlockState(pos, state.withProperty(EXPLODE, true));
            world.scheduleBlockUpdate(pos, this, 5, 1);
            RivalRebelsSoundPlayer.playSound(world, 11, 1, pos.getX(), pos.getY(), pos.getZ(), 3, 2);
        }
    }

    @Override
    public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion explosionIn) {
        if (!world.isRemote) world.createExplosion(null, pos.getX(), pos.getY() + 2.5f, pos.getZ(), RivalRebels.landmineExplodeSize, true);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		Block[] n = new Block[6];
        for (EnumFacing facing : EnumFacing.VALUES) {
            n[facing.getIndex()] = world.getBlockState(pos.offset(facing)).getBlock();
        }

		int popularity1 = 0;
		int popularity2 = 0;
		Block mode = Blocks.grass;
		Block array_item;
		for (int i = 0; i < 6; i++)
		{
			array_item = n[i];
			if (array_item == null || !array_item.isOpaqueCube() || array_item == RivalRebels.landmine || array_item == RivalRebels.alandmine || array_item == RivalRebels.mario || array_item == RivalRebels.amario || array_item == RivalRebels.quicksand || array_item == RivalRebels.aquicksand) continue;
            for (Block block : n) {
                if (array_item == block) popularity1++;
                if (popularity1 >= popularity2) {
                    mode = array_item;
                    popularity2 = popularity1;
                }
            }
			popularity1 = 0;
		}
		if (mode == Blocks.grass) world.setBlockState(pos, RivalRebels.landmine.getDefaultState());
	}

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(RivalRebels.alandmine);
    }

    /**
	 * Called when the falling block entity for this block hits the ground and turns back into a block
	 */
	public void onFinishFalling(World par1World, int x, int y, int z, int par5)
	{
		if (!par1World.isRemote) par1World.createExplosion(null, x, y + 2.5f, z, RivalRebels.landmineExplodeSize, true);
		// new Explosion(par1World, x + 0.5, y + 2.5, z + 0.5, RivalRebels.landmineExplodeSize, false, false, RivalRebelsDamageSource.landmine, "landmine.explode");
	}

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(EXPLODE, meta == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(EXPLODE) ? 1 : 0;
    }
}
