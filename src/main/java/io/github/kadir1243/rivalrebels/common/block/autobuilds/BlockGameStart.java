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
package io.github.kadir1243.rivalrebels.common.block.autobuilds;

import io.github.kadir1243.rivalrebels.RivalRebels;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class BlockGameStart extends BlockAutoTemplate {
    public static final MapCodec<BlockGameStart> CODEC = simpleCodec(BlockGameStart::new);
	public BlockGameStart(Properties settings) {
		super(settings);
	}

    @Override
    protected MapCodec<BlockGameStart> codec() {
        return CODEC;
    }

    @Override
	public void build(Level world, int x, int y, int z)
	{
		super.build(world, x, y, z);
		RivalRebels.round.startRound(x, z);
		placeBlockCarefully(world, x, y, z, Blocks.AIR);
	}
}
