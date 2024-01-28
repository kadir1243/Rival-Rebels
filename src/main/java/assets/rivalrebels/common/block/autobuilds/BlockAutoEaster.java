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
import net.minecraft.block.Blocks;
import net.minecraft.world.World;

public class BlockAutoEaster extends BlockAutoTemplate
{
	public BlockAutoEaster(Settings settings) {
		super(settings);
	}

	@Override
	public void build(World par1World, int x, int y, int z)
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

	/*@OnlyIn(Dist.CLIENT)
	IIcon	icon1;
	@OnlyIn(Dist.CLIENT)
	IIcon	icon2;
	@OnlyIn(Dist.CLIENT)
	IIcon	icon3;
	@OnlyIn(Dist.CLIENT)
	IIcon	icon4;
	@OnlyIn(Dist.CLIENT)
	IIcon	icon5;
	@OnlyIn(Dist.CLIENT)
	IIcon	icon6;

	@OnlyIn(Dist.CLIENT)
	@Override
	public final IIcon getIcon(int side, int meta)
	{
		if (side == 0) return icon1;
		if (side == 1) return icon2;
		if (side == 2) return icon3;
		if (side == 3) return icon4;
		if (side == 4) return icon5;
		if (side == 5) return icon6;
		return icon1;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister)
	{
		icon1 = iconregister.registerIcon("RivalRebels:ah"); // BOTTOM
		icon2 = iconregister.registerIcon("RivalRebels:ai"); // TOP
		icon3 = iconregister.registerIcon("RivalRebels:ah"); // SIDE N
		icon4 = iconregister.registerIcon("RivalRebels:ah"); // SIDE S
		icon5 = iconregister.registerIcon("RivalRebels:ah"); // SIDE W
		icon6 = iconregister.registerIcon("RivalRebels:ah"); // SIDE E
	}*/
}
