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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlockSteel extends Block
{
	public BlockSteel()
	{
		super(Material.IRON);
	}

	/*@Override
	public int getRenderType()
	{
		return 485;
	}*/

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        float f = 0.0625F;
        return new AxisAlignedBB(x + f, y + f, z + f, (x + 1) - f, (float) y + 1, (z + 1) - f);
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
		if (entity.isSneaking() && !entity.collidedHorizontally)
		{
			entity.motionY = 0.08;
			entity.fallDistance = 0;
		}
		else if (entity.collidedHorizontally)
		{
			entity.motionY = 0.25;
			entity.fallDistance = 0;
		}
		else if (entity.onGround)
		{
		}
		else if (entity.collidedVertically)
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
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
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
		icon = iconregister.registerIcon("RivalRebels:bx");
	}*/
}
