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

import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class BlockAutoBunker extends BlockAutoTemplate {
    public static final MapCodec<BlockAutoBunker> CODEC = simpleCodec(BlockAutoBunker::new);

    public BlockAutoBunker(Properties settings)
	{
		super(settings);
	}

    @Override
    protected MapCodec<BlockAutoBunker> codec() {
        return CODEC;
    }

    @Override
	public void build(Level world, int x, int y, int z)
	{
		super.build(world, x, y, z);
		if (!world.isClientSide())
		{
			int r = RRConfig.SERVER.getBunkerRadius();

			for (int x1 = -r; x1 <= r; x1++)
			{
				for (int y1 = 0; y1 <= 5; y1++)
				{
					for (int z1 = -r; z1 <= r; z1++)
					{
						placeBlockCarefully(world, x + x1, y + y1, z + z1, Blocks.AIR);
					}
				}
			}

			for (int a = -r; a <= r; a++)
			{
				for (int c = -r; c <= r; c++)
				{
					placeBlockCarefully(world, x + a, y - 1, z + c, RRBlocks.smartcamo);
					if (a < -(r - 1) || c < -(r - 1) || a > (r - 1) || c > (r - 1))
					{
						placeBlockCarefully(world, x + a, y, z + c, RRBlocks.smartcamo);
					}
				}
			}
			y = y + 3;
			r = r - 2;
			for (int a = -(r - 1); a <= (r - 1); a++)
			{
				for (int c = -(r - 1); c <= (r - 1); c++)
				{
					if ((a == -(r - 1) && c == -(r - 1)) || (a == -(r - 1) && c == +(r - 1)) || (a == +(r - 1) && c == -(r - 1)) || (a == +(r - 1) && c == +(r - 1)))
					{
						placeBlockCarefully(world, x + a, y - 3, z + c, RRBlocks.smartcamo);
						placeBlockCarefully(world, x + a, y - 2, z + c, RRBlocks.smartcamo);
						placeBlockCarefully(world, x + a, y - 1, z + c, RRBlocks.smartcamo);
					}
					if (a < -(r - 2) || c < -(r - 2) || a > (r - 2) || c > (r - 2))
					{
						placeBlockCarefully(world, x + a, y - 3, z + c, RRBlocks.smartcamo);
						placeBlockCarefully(world, x + a, y, z + c, RRBlocks.smartcamo);
						placeBlockCarefully(world, x + a, y + 1, z + c, RRBlocks.smartcamo);
					}

				}
			}
			for (int a = -r; a <= r; a++)
			{
				for (int c = -r; c <= r; c++)
				{
					if (a < -(r - 1) || c < -(r - 1) || a > (r - 1) || c > (r - 1))
					{
						placeBlockCarefully(world, x + a, y, z + c, RRBlocks.smartcamo);
						placeBlockCarefully(world, x + a, y - 3, z + c, RRBlocks.smartcamo);
					}
				}
			}

			r = r - 2;
			for (int a = -r; a <= r; a++)
			{
				for (int c = -r; c <= r; c++)
				{
					placeBlockCarefully(world, x + a, y + 1, z + c, RRBlocks.smartcamo);
					placeBlockCarefully(world, x + a, y + 2, z + c, RRBlocks.smartcamo);
				}
			}
			y = y - 3;
			r = r + 4;
			for (int a = -(r - 1); a <= (r - 1); a++)
			{
				for (int c = -(r - 1); c <= (r - 1); c++)
				{
					if (a < -(r - 2) || c < -(r - 2) || a > (r - 2) || c > (r - 2))
					{
						placeBlockCarefully(world, x + a, y, z + c, RRBlocks.smartcamo);
						placeBlockCarefully(world, x + a, y + 1, z + c, RRBlocks.smartcamo);
					}
				}
			}
			placeBlockCarefully(world, x - r + 5, y + 3, z, RRBlocks.light2);
			placeBlockCarefully(world, x + r - 5, y + 3, z, RRBlocks.light2);
			placeBlockCarefully(world, x, y + 3, z + r - 5, RRBlocks.light2);
			placeBlockCarefully(world, x, y + 3, z - r + 5, RRBlocks.light2);
		}
	}
}
