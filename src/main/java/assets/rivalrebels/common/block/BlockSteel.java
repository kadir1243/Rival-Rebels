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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockSteel extends Block
{
	public BlockSteel()
	{
		super(Material.iron);
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return 485;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state) {
        return new AxisAlignedBB(pos.getX() + 0.0625F, pos.getY() + 0.0625F, pos.getZ() + 0.0625F, (pos.getX() + 1) - 0.0625F, (float) pos.getY() + 1, (pos.getZ() + 1) - 0.0625F);
	}

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entity) {
		if (entity.isSneaking() && !entity.isCollidedHorizontally)
		{
			entity.motionY = 0.08;
			entity.fallDistance = 0;
		}
		else if (entity.isCollidedHorizontally)
		{
			entity.motionY = 0.25;
			entity.fallDistance = 0;
		}
		else if (entity.onGround)
		{
		}
		else if (entity.isCollidedVertically)
		{
			entity.motionY = 0.08;
			entity.fallDistance = 0;
		}
		else
		{
			entity.motionY = -0.1;
			entity.fallDistance = 0;
		}
	}

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }
}
