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
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BlockSteel extends Block {
	public BlockSteel(Settings settings)
	{
		super(settings);
	}

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        float f = 0.0625F;
        return VoxelShapes.cuboid(new Box(x + f, y + f, z + f, (x + 1) - f, (float) y + 1, (z + 1) - f));
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (entity.isSneaking() && !entity.horizontalCollision)
		{
            entity.setVelocity(entity.getVelocity().getX(), 0.08, entity.getVelocity().getZ());
			entity.fallDistance = 0;
		}
		else if (entity.horizontalCollision)
		{
            entity.setVelocity(entity.getVelocity().getX(), 0.25, entity.getVelocity().getZ());
			entity.fallDistance = 0;
		}
		else if (entity.isOnGround())
		{
		}
		else if (entity.verticalCollision)
		{
			entity.setVelocity(entity.getVelocity().getX(), 0.08, entity.getVelocity().getZ());
			entity.fallDistance = 0;
		}
		else
		{
            entity.setVelocity(entity.getVelocity().getX(), -0.1, entity.getVelocity().getZ());
			entity.fallDistance = 0;
		}
	}
}
