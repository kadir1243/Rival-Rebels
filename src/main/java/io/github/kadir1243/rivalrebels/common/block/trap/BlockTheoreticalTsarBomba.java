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

import io.github.kadir1243.rivalrebels.common.tileentity.RRTileEntities;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityTheoreticalTsarBomba;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public class BlockTheoreticalTsarBomba extends AbstractBombBlock {
    public static final MapCodec<BlockTheoreticalTsarBomba> CODEC = simpleCodec(BlockTheoreticalTsarBomba::new);

    public BlockTheoreticalTsarBomba(Properties settings) {
		super(settings);
    }

    @Override
    protected MapCodec<BlockTheoreticalTsarBomba> codec() {
        return CODEC;
    }

    private static final Map<Direction, VoxelShape> SHAPE = Shapes.rotateHorizontal(Shapes.create(0, 0, -2, 1, 2, 3));
    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
    }

    @Override
    protected VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getShape(level, pos);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityTheoreticalTsarBomba(pos, state);
	}

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, RRTileEntities.THEORETICAL_TSAR_BOMB.get(), TileEntityTheoreticalTsarBomba::tick);
    }
}
