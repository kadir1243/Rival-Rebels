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
package io.github.kadir1243.rivalrebels.common.block.trap;

import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BlockPetrifiedWood extends Block {
    public static final IntegerProperty META = IntegerProperty.create("meta", 0, 15);
	public BlockPetrifiedWood(Properties settings)
	{
		super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(META, 0));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(META);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (player.isCreative()) {
			level.setBlockAndUpdate(pos, state.setValue(META, state.getValue(META) + 1));
			return InteractionResult.sidedSuccess(level.isClientSide());
		}
		return InteractionResult.PASS;
	}

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
		if (world.random.nextInt(2) == 0) {
			entity.hurt(RivalRebelsDamageSource.radioactivePoisoning(world), ((16 - world.getBlockState(pos).getValue(META)) / 2) + world.random.nextInt(3) - 1);
		}
	}

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (placer instanceof Player) {
			world.setBlockAndUpdate(pos, state.setValue(META, 7));
		}
	}
}
