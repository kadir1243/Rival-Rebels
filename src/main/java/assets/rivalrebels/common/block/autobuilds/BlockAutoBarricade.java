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
package assets.rivalrebels.common.block.autobuilds;

import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.block.machine.BlockReciever;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class BlockAutoBarricade extends BlockAutoTemplate {
    public static final MapCodec<BlockAutoBarricade> CODEC = simpleCodec(BlockAutoBarricade::new);

    public BlockAutoBarricade(Properties settings)
	{
		super(settings);
	}

    @Override
    protected MapCodec<BlockAutoBarricade> codec() {
        return CODEC;
    }

    @Override
	public void build(Level level, int x, int y, int z) {
		super.build(level, x, y, z);

		placeBlockCarefully(level, x, y, z, Blocks.AIR);
		placeBlockCarefully(level, x + 1, y, z + 1, RRBlocks.reactive);
		placeBlockCarefully(level, x + 1, y, z - 1, RRBlocks.reactive);
		placeBlockCarefully(level, x - 1, y, z - 1, RRBlocks.reactive);
		placeBlockCarefully(level, x - 1, y, z + 1, RRBlocks.reactive);

		placeBlockCarefully(level, x + 1, y + 1, z + 1, RRBlocks.reactive);
		placeBlockCarefully(level, x + 1, y + 1, z - 1, RRBlocks.reactive);
		placeBlockCarefully(level, x - 1, y + 1, z - 1, RRBlocks.reactive);
		placeBlockCarefully(level, x - 1, y + 1, z + 1, RRBlocks.reactive);

		placeBlockCarefully(level, x + 1, y + 2, z + 1, RRBlocks.reactive);
		placeBlockCarefully(level, x + 1, y + 2, z - 1, RRBlocks.reactive);
		placeBlockCarefully(level, x - 1, y + 2, z - 1, RRBlocks.reactive);
		placeBlockCarefully(level, x - 1, y + 2, z + 1, RRBlocks.reactive);
		placeBlockCarefully(level, x, y + 2, z + 1, RRBlocks.steel);
		placeBlockCarefully(level, x + 1, y + 2, z, RRBlocks.steel);
		placeBlockCarefully(level, x, y + 2, z - 1, RRBlocks.steel);
		placeBlockCarefully(level, x - 1, y + 2, z, RRBlocks.steel);
		placeBlockCarefully(level, x, y + 2, z + 2, RRBlocks.reactive);
		placeBlockCarefully(level, x + 2, y + 2, z, RRBlocks.reactive);
		placeBlockCarefully(level, x, y + 2, z - 2, RRBlocks.reactive);
		placeBlockCarefully(level, x - 2, y + 2, z, RRBlocks.reactive);

		placeBlockCarefully(level, x + 1, y + 3, z + 1, RRBlocks.reactive);
		placeBlockCarefully(level, x + 1, y + 3, z - 1, RRBlocks.reactive);
		placeBlockCarefully(level, x - 1, y + 3, z - 1, RRBlocks.reactive);
		placeBlockCarefully(level, x - 1, y + 3, z + 1, RRBlocks.reactive);
		placeBlockCarefully(level, x, y + 3, z + 1, RRBlocks.steel);
		placeBlockCarefully(level, x + 1, y + 3, z, RRBlocks.steel);
		placeBlockCarefully(level, x, y + 3, z - 1, RRBlocks.steel);
		placeBlockCarefully(level, x - 1, y + 3, z, RRBlocks.steel);
		placeBlockCarefully(level, x, y + 3, z - 2, RRBlocks.ffreciever.defaultBlockState().setValue(BlockReciever.FACING, Direction.NORTH));
		placeBlockCarefully(level, x, y + 3, z + 2, RRBlocks.ffreciever.defaultBlockState().setValue(BlockReciever.FACING, Direction.SOUTH));
		placeBlockCarefully(level, x - 2, y + 3, z, RRBlocks.ffreciever.defaultBlockState().setValue(BlockReciever.FACING, Direction.WEST));
		placeBlockCarefully(level, x + 2, y + 3, z, RRBlocks.ffreciever.defaultBlockState().setValue(BlockReciever.FACING, Direction.EAST));

		// int h = 1;
		// int s = 1;
		// placeBlockCarefully(level, x, y, z, 0);
		// placeBlockCarefully(level, x + s, y + h, z + s, RivalRebels.smartcamo);
		// placeBlockCarefully(level, x + s, y + h, z - s, RivalRebels.smartcamo);
		// placeBlockCarefully(level, x - s, y + h, z - s, RivalRebels.smartcamo);
		// placeBlockCarefully(level, x - s, y + h, z + s, RivalRebels.smartcamo);
		// placeBlockCarefully(level, x + (2 * s), y + h, z + (2 * s), RivalRebels.smartcamo);
		// placeBlockCarefully(level, x + (2 * s), y + h, z - (2 * s), RivalRebels.smartcamo);
		// placeBlockCarefully(level, x - (2 * s), y + h, z - (2 * s), RivalRebels.smartcamo);
		// placeBlockCarefully(level, x - (2 * s), y + h, z + (2 * s), RivalRebels.smartcamo);
		// placeBlockCarefully(level, x + s, y + h + s, z, RivalRebels.smartcamo);
		// placeBlockCarefully(level, x + s, y + h - s, z, RivalRebels.smartcamo);
		// placeBlockCarefully(level, x - s, y + h - s, z, RivalRebels.smartcamo);
		// placeBlockCarefully(level, x - s, y + h + s, z, RivalRebels.smartcamo);
		// placeBlockCarefully(level, x + (2 * s), y + h + (2 * s), z, RivalRebels.smartcamo);
		// placeBlockCarefully(level, x + (2 * s), y + h - (2 * s), z, RivalRebels.smartcamo);
		// placeBlockCarefully(level, x - (2 * s), y + h - (2 * s), z, RivalRebels.smartcamo);
		// placeBlockCarefully(level, x - (2 * s), y + h + (2 * s), z, RivalRebels.smartcamo);
		// placeBlockCarefully(level, x, y + h + s, z + s, RivalRebels.smartcamo);
		// placeBlockCarefully(level, x, y + h - s, z - s, RivalRebels.smartcamo);
		// placeBlockCarefully(level, x, y + h + s, z - s, RivalRebels.smartcamo);
		// placeBlockCarefully(level, x, y + h - s, z + s, RivalRebels.smartcamo);
		// placeBlockCarefully(level, x, y + h + (2 * s), z + (2 * s), RivalRebels.smartcamo);
		// placeBlockCarefully(level, x, y + h - (2 * s), z - (2 * s), RivalRebels.smartcamo);
		// placeBlockCarefully(level, x, y + h + (2 * s), z - (2 * s), RivalRebels.smartcamo);
		// placeBlockCarefully(level, x, y + h - (2 * s), z + (2 * s), RivalRebels.smartcamo);
		// h = 2;
		// placeBlockCarefully(level, x + s, y + h, z, RivalRebels.reactive);
		// placeBlockCarefully(level, x + s, y + h, z - s, RivalRebels.reactive);
		// placeBlockCarefully(level, x + s, y + h, z + s, RivalRebels.reactive);
		// placeBlockCarefully(level, x - s, y + h, z, RivalRebels.reactive);
		// placeBlockCarefully(level, x - s, y + h, z - s, RivalRebels.reactive);
		// placeBlockCarefully(level, x - s, y + h, z + s, RivalRebels.reactive);
		// placeBlockCarefully(level, x, y + h, z + s, RivalRebels.reactive);
		// placeBlockCarefully(level, x, y + h, z - s, RivalRebels.reactive);
		// h = 4;
		// s = 2;
		// placeBlockCarefully(level, x + s, y + h, z, RivalRebels.conduit);
		// placeBlockCarefully(level, x - s, y + h, z, RivalRebels.conduit);
		// placeBlockCarefully(level, x, y + h, z + s, RivalRebels.conduit);
		// placeBlockCarefully(level, x, y + h, z - s, RivalRebels.conduit);
		// h = 2;
		// placeBlockCarefully(level, x + s, y + h, z, RivalRebels.conduit);
		// placeBlockCarefully(level, x - s, y + h, z, RivalRebels.conduit);
		// placeBlockCarefully(level, x, y + h, z + s, RivalRebels.conduit);
		// placeBlockCarefully(level, x, y + h, z - s, RivalRebels.conduit);
		// placeBlockCarefully(level, x + s, y + h, z + s, RivalRebels.flare);
		// placeBlockCarefully(level, x - s, y + h, z - s, RivalRebels.flare);
		// placeBlockCarefully(level, x - s, y + h, z + s, RivalRebels.flare);
		// placeBlockCarefully(level, x + s, y + h, z - s, RivalRebels.flare);
		// h = 1;
		// s = 3;
		// placeBlockCarefully(level, x, y - h, z, RivalRebels.smartcamo);
		// placeBlockCarefully(level, x + s, y - h, z + 1, RivalRebels.jump);
		// placeBlockCarefully(level, x - s, y - h, z - 1, RivalRebels.jump);
		// placeBlockCarefully(level, x + s, y - h, z - 1, RivalRebels.jump);
		// placeBlockCarefully(level, x - s, y - h, z + 1, RivalRebels.jump);
		// placeBlockCarefully(level, x - 1, y - h, z + s, RivalRebels.jump);
		// placeBlockCarefully(level, x - 1, y - h, z - s, RivalRebels.jump);
		// placeBlockCarefully(level, x + 1, y - h, z + s, RivalRebels.jump);
		// placeBlockCarefully(level, x + 1, y - h, z - s, RivalRebels.jump);
	}
}
