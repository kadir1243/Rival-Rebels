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

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockSteel extends Block {
	public BlockSteel(Properties settings)
	{
		super(settings);
	}

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        float f = 0.0625F;
        return Shapes.create(f, f, f, 1 - f, 1, 1 - f);
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
		if (entity.isShiftKeyDown() && !entity.horizontalCollision)
		{
            entity.setDeltaMovement(entity.getDeltaMovement().x(), 0.08, entity.getDeltaMovement().z());
			entity.fallDistance = 0;
		}
		else if (entity.horizontalCollision)
		{
            entity.setDeltaMovement(entity.getDeltaMovement().x(), 0.25, entity.getDeltaMovement().z());
			entity.fallDistance = 0;
		}
		else if (entity.onGround())
		{
		}
		else if (entity.verticalCollision)
		{
			entity.setDeltaMovement(entity.getDeltaMovement().x(), 0.08, entity.getDeltaMovement().z());
			entity.fallDistance = 0;
		}
		else
		{
            entity.setDeltaMovement(entity.getDeltaMovement().x(), -0.1, entity.getDeltaMovement().z());
			entity.fallDistance = 0;
		}
	}
}
