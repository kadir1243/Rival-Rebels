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
package assets.rivalrebels.common.block.machine;

import assets.rivalrebels.RivalRebels;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockForceField extends Block {
    public static final PropertyInteger SHAPE = PropertyInteger.create("shape", 2, 5);

	public BlockForceField() {
		super(Material.glass);
		setBlockBounds(0.0F, 0.0F, 0.4375f, 1.0F, 1.0F, 1.0F - 0.4375f);

        this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, 4));
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
        switch (world.getBlockState(pos).getValue(SHAPE)) {
            case 4:
            case 5:
                setBlockBounds(0.0F, 0.0F, 0.4375f, 1.0F, 1.0F, 1.0F - 0.4375f);
                break;
            case 2:
            case 3:
                setBlockBounds(0.4375f, 0.0F, 0.0F, 1.0F - 0.4375f, 1.0F, 1.0F);
                break;
        }
	}

	/**
	 * Sets the block's bounds for rendering it as an item
	 */
	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.0F, 0.0F, 0.4375f, 1.0F, 1.0F, 1.0F - 0.4375f);
	}

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state) {
        switch (world.getBlockState(pos).getValue(SHAPE)) {
            case 4:
            case 5:
                setBlockBounds(0.0F, 0.0F, 0.4375f, 1.0F, 1.0F, 1.0F - 0.4375f);
                break;
            case 2:
            case 3:
                setBlockBounds(0.4375f, 0.0F, 0.0F, 1.0F - 0.4375f, 1.0F, 1.0F);
                break;
        }

		return super.getCollisionBoundingBox(world, pos, state);
	}

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos pos) {
        switch (world.getBlockState(pos).getValue(SHAPE)) {
            case 4:
            case 5:
                setBlockBounds(0.0F, 0.0F, 0.4375f, 1.0F, 1.0F, 1.0F - 0.4375f);
                break;
            case 2:
            case 3:
                setBlockBounds(0.4375f, 0.0F, 0.0F, 1.0F - 0.4375f, 1.0F, 1.0F);
                break;
        }

        return super.getSelectedBoundingBox(world, pos);
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
		return RivalRebels.goodRender ? -1 : 0;
	}
}
