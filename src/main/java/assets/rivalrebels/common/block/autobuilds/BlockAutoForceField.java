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
import com.mojang.serialization.MapCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class BlockAutoForceField extends BlockAutoTemplate {
    public static final MapCodec<BlockAutoForceField> CODEC = simpleCodec(BlockAutoForceField::new);

    public BlockAutoForceField(Properties settings)
	{
		super(settings);
	}

    @Override
    protected MapCodec<BlockAutoForceField> codec() {
        return CODEC;
    }

    @Override
	public void build(Level world, int x, int y, int z)
	{
		super.build(world, x, y, z);
		if (!world.isClientSide())
		{
			int r = 2;
			int h = 6;

			for (int y1 = 0; y1 <= h; y1++)
			{
				for (int x1 = -r; x1 <= r; x1++)
				{
					for (int z1 = -r; z1 <= r; z1++)
					{
						if (((Mth.abs(x1) == r || Mth.abs(z1) == r) && (y1 != 3 || (Mth.abs(x1) != 0 && Mth.abs(z1) != 0))) || y1 == 0 || y1 == h)
						{
							placeBlockCarefully(world, x + x1, y + y1, z + z1, RRBlocks.reactive);
						}
						else
						{
							placeBlockCarefully(world, x + x1, y + y1, z + z1, Blocks.AIR);
						}
					}
				}
				if (y1 != 3)
				{
					placeBlockCarefully(world, x + r + 1, y + y1, z, RRBlocks.reactive);
					placeBlockCarefully(world, x - r - 1, y + y1, z, RRBlocks.reactive);
					placeBlockCarefully(world, x, y + y1, z + r + 1, RRBlocks.reactive);
					placeBlockCarefully(world, x, y + y1, z - r - 1, RRBlocks.reactive);
				}
				else
				{
					for (int z1 = -r; z1 <= r; z1++)
					{
						placeBlockCarefully(world, x, y + y1, z + z1, RRBlocks.conduit);
					}

					for (int x1 = -r; x1 <= r; x1++)
					{
						placeBlockCarefully(world, x + x1, y + y1, z, RRBlocks.conduit);
					}
				}
			}
		}
	}

}
