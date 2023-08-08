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
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockMario extends Block {
	public BlockMario() {
		super(Material.rock);
	}

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(RivalRebels.amario);
    }

    @Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor()
	{
		if (this == RivalRebels.mario) return Blocks.grass.getBlockColor();
		return 0xFFFFFF;
	}

	@SideOnly(Side.CLIENT)
    @Override
    public int getRenderColor(IBlockState state) {
        if (this == RivalRebels.mario) return Blocks.grass.getRenderColor(state);
        return 0xFFFFFF;
    }

	@SideOnly(Side.CLIENT)
    @Override
    public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass) {
        if (this == RivalRebels.mario) return Blocks.grass.colorMultiplier(world, pos, renderPass);
        return 0xFFFFFF;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
        return new AxisAlignedBB(pos, pos.add(1, 1 - 0.0625F, 1));
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
        return new AxisAlignedBB(pos, pos.add(1, 1, 1));
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity) {
		if (entity instanceof EntityPlayer || entity instanceof EntityAnimal || entity instanceof EntityMob)
		{
			world.setBlockState(pos, Blocks.gravel.getDefaultState());
		}
	}

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		Block[] n = new Block[6];
        for (EnumFacing facing : EnumFacing.VALUES) {
            n[facing.getIndex()] = world.getBlockState(pos.offset(facing)).getBlock();
        }

		int popularity1 = 0;
		int popularity2 = 0;
		Block mode = Blocks.gravel;
		Block array_item;
        for (int i = 0; i < 6; i++)
		{
			array_item = n[i];
			if (!array_item.isOpaqueCube() || array_item == RivalRebels.landmine || array_item == RivalRebels.alandmine || array_item == RivalRebels.mario || array_item == RivalRebels.amario || array_item == RivalRebels.quicksand || array_item == RivalRebels.aquicksand) continue;
            for (Block block : n) {
                if (array_item == block) popularity1++;
                if (popularity1 >= popularity2) {
                    mode = array_item;
                    popularity2 = popularity1;
                }
            }
			popularity1 = 0;
		}
		if (mode == Blocks.grass) world.setBlockState(pos, RivalRebels.mario.getDefaultState());
	}

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(RivalRebels.amario);
    }
}
