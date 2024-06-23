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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class BlockAutoEaster extends BlockAutoTemplate {
    public static final MapCodec<BlockAutoEaster> CODEC = simpleCodec(BlockAutoEaster::new);

    public BlockAutoEaster(Properties settings) {
		super(settings);
	}

    @Override
    protected MapCodec<BlockAutoEaster> codec() {
        return CODEC;
    }

    @Override
	public void build(Level par1World, int x, int y, int z)
	{
		super.build(par1World, x, y, z);
		int h = 0;
		placeBlockCarefully(par1World, x, y, z, Blocks.AIR);
		placeBlockCarefully(par1World, x + 1, y + h, z, RRBlocks.jump);
		placeBlockCarefully(par1World, x + 2, y + h, z, RRBlocks.jump);
		placeBlockCarefully(par1World, x + 3, y + h, z, RRBlocks.jump);
		placeBlockCarefully(par1World, x + 4, y + h, z, RRBlocks.jump);
		placeBlockCarefully(par1World, x + 1, y + h, z + 1, RRBlocks.jump);
		placeBlockCarefully(par1World, x + 2, y + h, z + 1, RRBlocks.jump);
		placeBlockCarefully(par1World, x + 3, y + h, z + 1, RRBlocks.jump);
		placeBlockCarefully(par1World, x + 4, y + h, z + 1, RRBlocks.jump);
		placeBlockCarefully(par1World, x + 1, y + h, z + 2, RRBlocks.jump);
		placeBlockCarefully(par1World, x + 2, y + h, z + 2, RRBlocks.jump);
		placeBlockCarefully(par1World, x + 3, y + h, z + 2, RRBlocks.jump);
		placeBlockCarefully(par1World, x + 4, y + h, z + 2, RRBlocks.jump);
		placeBlockCarefully(par1World, x + 1, y + h, z + 3, RRBlocks.jump);
		placeBlockCarefully(par1World, x + 2, y + h, z + 3, RRBlocks.jump);
		placeBlockCarefully(par1World, x + 3, y + h, z + 3, RRBlocks.jump);
		placeBlockCarefully(par1World, x + 4, y + h, z + 3, RRBlocks.jump);
		h = h + 1;
		placeBlockCarefully(par1World, x + 1, y + h, z, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 2, y + h, z, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 3, y + h, z, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 4, y + h, z, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 1, y + h, z + 1, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 2, y + h, z + 1, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 3, y + h, z + 1, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 4, y + h, z + 1, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 1, y + h, z + 2, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 2, y + h, z + 2, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 3, y + h, z + 2, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 4, y + h, z + 2, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 1, y + h, z + 3, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 2, y + h, z + 3, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 3, y + h, z + 3, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 4, y + h, z + 3, Blocks.SNOW);
		h = h + 1;
		placeBlockCarefully(par1World, x + 1, y + h, z, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 2, y + h, z, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 3, y + h, z, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 4, y + h, z, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 1, y + h, z + 1, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 2, y + h, z + 1, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 3, y + h, z + 1, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 4, y + h, z + 1, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 1, y + h, z + 2, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 2, y + h, z + 2, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 3, y + h, z + 2, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 4, y + h, z + 2, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 1, y + h, z + 3, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 2, y + h, z + 3, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 3, y + h, z + 3, Blocks.SNOW);
		placeBlockCarefully(par1World, x + 4, y + h, z + 3, Blocks.SNOW);
		h = h + 1;
		placeBlockCarefully(par1World, x + 1, y + h, z, Blocks.PUMPKIN);
		placeBlockCarefully(par1World, x + 2, y + h, z, Blocks.PUMPKIN);
		placeBlockCarefully(par1World, x + 3, y + h, z, Blocks.PUMPKIN);
		placeBlockCarefully(par1World, x + 4, y + h, z, Blocks.PUMPKIN);
		placeBlockCarefully(par1World, x + 1, y + h, z + 1, Blocks.PUMPKIN);
		placeBlockCarefully(par1World, x + 2, y + h, z + 1, Blocks.PUMPKIN);
		placeBlockCarefully(par1World, x + 3, y + h, z + 1, Blocks.PUMPKIN);
		placeBlockCarefully(par1World, x + 4, y + h, z + 1, Blocks.PUMPKIN);
		placeBlockCarefully(par1World, x + 1, y + h, z + 2, Blocks.PUMPKIN);
		placeBlockCarefully(par1World, x + 2, y + h, z + 2, Blocks.PUMPKIN);
		placeBlockCarefully(par1World, x + 3, y + h, z + 2, Blocks.PUMPKIN);
		placeBlockCarefully(par1World, x + 4, y + h, z + 2, Blocks.PUMPKIN);
		placeBlockCarefully(par1World, x + 1, y + h, z + 3, Blocks.PUMPKIN);
		placeBlockCarefully(par1World, x + 2, y + h, z + 3, Blocks.PUMPKIN);
		placeBlockCarefully(par1World, x + 3, y + h, z + 3, Blocks.PUMPKIN);
		placeBlockCarefully(par1World, x + 4, y + h, z + 3, Blocks.PUMPKIN);
	}

}
