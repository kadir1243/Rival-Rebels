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
import net.minecraft.block.Blocks;
import net.minecraft.world.World;

public class BlockAutoForceField extends BlockAutoTemplate {
    public static final MapCodec<BlockAutoForceField> CODEC = createCodec(BlockAutoForceField::new);

    public BlockAutoForceField(Settings settings)
	{
		super(settings);
	}

    @Override
    protected MapCodec<BlockAutoForceField> getCodec() {
        return CODEC;
    }

    @Override
	public void build(World world, int x, int y, int z)
	{
		super.build(world, x, y, z);
		if (!world.isClient)
		{
			int r = 2;
			int h = 6;

			for (int y1 = 0; y1 <= h; y1++)
			{
				for (int x1 = -r; x1 <= r; x1++)
				{
					for (int z1 = -r; z1 <= r; z1++)
					{
						if (((Math.abs(x1) == r || Math.abs(z1) == r) && (y1 != 3 || (Math.abs(x1) != 0 && Math.abs(z1) != 0))) || y1 == 0 || y1 == h)
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
