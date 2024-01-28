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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.world.World;

public class BlockAutoBunker extends BlockAutoTemplate
{
	public BlockAutoBunker(Settings settings)
	{
		super(settings);
	}

	@Override
	public void build(World world, int x, int y, int z)
	{
		super.build(world, x, y, z);
		if (!world.isClient)
		{
			int r = RivalRebels.bunkerradius;

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
		icon2 = iconregister.registerIcon("RivalRebels:ah"); // TOP
		icon3 = iconregister.registerIcon("RivalRebels:bl"); // SIDE N
		icon4 = iconregister.registerIcon("RivalRebels:bl"); // SIDE S
		icon5 = iconregister.registerIcon("RivalRebels:bl"); // SIDE W
		icon6 = iconregister.registerIcon("RivalRebels:bl"); // SIDE E
	}*/
}
