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

public class BlockAutoMarioTrap extends BlockAutoTemplate {
    public static final MapCodec<BlockAutoMarioTrap> CODEC = createCodec(BlockAutoMarioTrap::new);

    public BlockAutoMarioTrap(Settings settings)
	{
		super(settings);
	}

    @Override
    protected MapCodec<BlockAutoMarioTrap> getCodec() {
        return CODEC;
    }

    @Override
	public void build(World world, int x, int y, int z)
	{
		super.build(world, x, y, z);
		if (!world.isClient)
		{
			placeBlockCarefully(world, x, y, z, Blocks.AIR);
			int r = 2;
			for (int z1 = -r; z1 <= r; z1++)
			{
				for (int x1 = -r; x1 <= r; x1++)
				{
					placeBlockCarefully(world, x + x1, y - 1, z + z1, RRBlocks.amario);
					placeBlockCarefully(world, x + x1, y - 2, z + z1, Blocks.AIR);
					placeBlockCarefully(world, x + x1, y - 3, z + z1, Blocks.AIR);
				}
			}
		}
	}

}
