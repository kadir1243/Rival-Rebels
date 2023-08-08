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
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockRhodesScaffold extends BlockAutoTemplate
{
	public static final byte[] binimg = {
		0,0,0,0,0,0,0,7,7,
		0,1,1,1,1,0,0,0,0,
		0,1,1,1,0,0,0,2,1,
		0,1,1,1,1,1,0,1,1,
		0,1,1,1,1,1,0,0,0,
		0,1,1,1,0,1,1,1,0,
		0,0,1,1,0,1,1,1,1,
		0,0,1,1,0,1,1,1,1,
		0,0,1,1,0,0,0,1,1,
		0,0,1,1,0,0,0,1,1,
		0,0,1,1,4,5,4,1,1,
		0,1,1,0,0,0,2,1,1,
		0,1,1,2,0,0,1,1,1,
		0,1,1,1,0,0,0,0,1,
		0,0,1,0,0,0,1,0,1,
		0,1,1,0,0,1,1,1,1,
		0,1,1,0,1,1,1,0,1,
		0,1,1,0,1,1,1,0,0,
		0,0,0,0,1,1,1,0,0,
		0,0,0,0,1,1,1,1,0,
		0,0,0,1,1,1,1,1,0,
		0,0,0,1,1,1,1,0,0,
		0,0,0,0,1,1,1,0,0,
		4,5,4,0,1,1,1,2,0,
		0,3,0,1,1,1,1,1,0,
		0,3,0,1,1,1,1,1,0,
		0,3,0,1,1,1,1,0,0,
		0,3,0,0,1,1,1,0,0,
		0,3,0,0,1,1,1,0,0,
		0,3,0,0,1,1,1,0,0,
		0,3,0,0,0,1,0,0,0,
		6,6,6,6,6,6,6,6,6,
	};
	public BlockRhodesScaffold()
	{
		super();
	}

	@Override
	public void build(World world, int x, int y, int z)
	{
		super.build(world, x, y, z);
		if (!world.isRemote)
		{
			int scale = 1;
			if (world.getBlockState(new BlockPos(x, y-1, z)).getBlock() == RivalRebels.buildrhodes && world.getBlockState(new BlockPos(x, y-2, z)).getBlock() == RivalRebels.buildrhodes)
			{
				if (world.getBlockState(new BlockPos(x, y-3, z)).getBlock() == RivalRebels.buildrhodes && world.getBlockState(new BlockPos(x, y-4, z)).getBlock() == RivalRebels.buildrhodes) scale = 3;
				else scale = 2;
			}
			for (int i = 0; i < 32*9; i++)
			{
				int fy = 30 -(i/9);
				int fx1 = -8+(i%9);
				int fx2 = 9 -(i%9);
				fy *= scale;
				fx1 *= scale;
				fx2 *= scale;
				byte b = binimg[i];
				if (scale == 1)
				{
					place(world,x,y,z,fy,fx1,b);
					place(world,x,y,z,fy,fx2,b);
				}
				else if (scale == 2)
				{
					place(world,x,y,z,fy,fx1,b);
					place(world,x,y,z,fy,fx2,b);
					place(world,x,y,z,fy,fx1+1,b);
					place(world,x,y,z,fy,fx2+1,b);
					place(world,x,y,z,fy+1,fx1,b);
					place(world,x,y,z,fy+1,fx2,b);
					place(world,x,y,z,fy+1,fx1+1,b);
					place(world,x,y,z,fy+1,fx2+1,b);
				}
				else if (scale == 3)
				{
					place(world,x,y,z,fy,fx1,b);
					place(world,x,y,z,fy,fx2,b);
					place(world,x,y,z,fy,fx1+1,b);
					place(world,x,y,z,fy,fx2+1,b);
					place(world,x,y,z,fy,fx1+2,b);
					place(world,x,y,z,fy,fx2+2,b);
					place(world,x,y,z,fy+1,fx1,b);
					place(world,x,y,z,fy+1,fx2,b);
					place(world,x,y,z,fy+1,fx1+1,b);
					place(world,x,y,z,fy+1,fx2+1,b);
					place(world,x,y,z,fy+1,fx1+2,b);
					place(world,x,y,z,fy+1,fx2+2,b);
					place(world,x,y,z,fy+2,fx1,b);
					place(world,x,y,z,fy+2,fx2,b);
					place(world,x,y,z,fy+2,fx1+1,b);
					place(world,x,y,z,fy+2,fx2+1,b);
					place(world,x,y,z,fy+2,fx1+2,b);
					place(world,x,y,z,fy+2,fx2+2,b);
				}
			}
		}
	}

	private void place(World world, int x, int y, int z, int fy, int fx1, byte b)
	{
		if (b == 0)
		{
			placeAir(world, x+fx1, y+fy, z-4);
			placeAir(world, x+fx1, y+fy, z-3);
			placeAir(world, x+fx1, y+fy, z-2);
			placeAir(world, x+fx1, y+fy, z-1);
			placeBlockCarefully(world, x+fx1, y+fy, z, RivalRebels.steel);
			placeAir(world, x+fx1, y+fy, z+1);
			placeAir(world, x+fx1, y+fy, z+2);
			placeAir(world, x+fx1, y+fy, z+3);
			placeAir(world, x+fx1, y+fy, z+4);
		}
		if (b == 1)
		{
			placeAir(world, x+fx1, y+fy, z-4);
			placeAir(world, x+fx1, y+fy, z-3);
			placeAir(world, x+fx1, y+fy, z-2);
			placeAir(world, x+fx1, y+fy, z-1);
			if (RivalRebels.prefillrhodes) placeBlockCarefully(world, x+fx1, y+fy, z, RivalRebels.conduit);
			else placeAir(world, x+fx1, y+fy, z);
			placeAir(world, x+fx1, y+fy, z+1);
			placeAir(world, x+fx1, y+fy, z+2);
			placeAir(world, x+fx1, y+fy, z+3);
			placeAir(world, x+fx1, y+fy, z+4);
		}
		if (b == 2)
		{
			placeAir(world, x+fx1, y+fy, z-4);
			placeAir(world, x+fx1, y+fy, z-3);
			placeAir(world, x+fx1, y+fy, z-2);
			placeAir(world, x+fx1, y+fy, z-1);
			placeBlockCarefully(world, x+fx1, y+fy, z, RivalRebels.rhodesactivator);
			placeAir(world, x+fx1, y+fy, z+1);
			placeAir(world, x+fx1, y+fy, z+2);
			placeAir(world, x+fx1, y+fy, z+3);
			placeAir(world, x+fx1, y+fy, z+4);
		}
		if (b == 3)
		{
			placeAir(world, x+fx1, y+fy, z-4);
			placeBlockCarefully(world, x+fx1, y+fy, z-3, RivalRebels.steel);
			placeAir(world, x+fx1, y+fy, z-2);
			placeAir(world, x+fx1, y+fy, z-1);
			placeBlockCarefully(world, x+fx1, y+fy, z, RivalRebels.steel);
			placeAir(world, x+fx1, y+fy, z+1);
			placeAir(world, x+fx1, y+fy, z+2);
			placeBlockCarefully(world, x+fx1, y+fy, z+3, RivalRebels.steel);
			placeAir(world, x+fx1, y+fy, z+4);
		}
		if (b == 4)
		{
			placeAir(world, x+fx1, y+fy, z-4);
			placeBlockCarefully(world, x+fx1, y+fy, z-3, RivalRebels.steel);
			placeBlockCarefully(world, x+fx1, y+fy, z-2, RivalRebels.steel);
			placeBlockCarefully(world, x+fx1, y+fy, z-1, RivalRebels.steel);
			placeBlockCarefully(world, x+fx1, y+fy, z, RivalRebels.steel);
			placeBlockCarefully(world, x+fx1, y+fy, z+1, RivalRebels.steel);
			placeBlockCarefully(world, x+fx1, y+fy, z+2, RivalRebels.steel);
			placeBlockCarefully(world, x+fx1, y+fy, z+3, RivalRebels.steel);
			placeAir(world, x+fx1, y+fy, z+4);
		}
		if (b == 5)
		{
			placeAir(world, x+fx1, y+fy, z-4);
			placeBlockCarefully(world, x+fx1, y+fy, z-3, RivalRebels.steel);
			placeBlockCarefully(world, x+fx1, y+fy, z-2, RivalRebels.conduit);
			placeBlockCarefully(world, x+fx1, y+fy, z-1, RivalRebels.steel);
			placeBlockCarefully(world, x+fx1, y+fy, z, RivalRebels.steel);
			placeBlockCarefully(world, x+fx1, y+fy, z+1, RivalRebels.steel);
			placeBlockCarefully(world, x+fx1, y+fy, z+2, RivalRebels.conduit);
			placeBlockCarefully(world, x+fx1, y+fy, z+3, RivalRebels.steel);
			placeAir(world, x+fx1, y+fy, z+4);
		}
		if (b == 6)
		{
			placeBlockCarefully(world, x+fx1, y+fy, z-4, RivalRebels.reactive);
			placeBlockCarefully(world, x+fx1, y+fy, z-3, RivalRebels.reactive);
			placeBlockCarefully(world, x+fx1, y+fy, z-2, RivalRebels.reactive);
			placeBlockCarefully(world, x+fx1, y+fy, z-1, RivalRebels.reactive);
			placeBlockCarefully(world, x+fx1, y+fy, z, RivalRebels.reactive);
			placeBlockCarefully(world, x+fx1, y+fy, z+1, RivalRebels.reactive);
			placeBlockCarefully(world, x+fx1, y+fy, z+2, RivalRebels.reactive);
			placeBlockCarefully(world, x+fx1, y+fy, z+3, RivalRebels.reactive);
			placeBlockCarefully(world, x+fx1, y+fy, z+4, RivalRebels.reactive);
		}
		if (b == 7)
		{
			placeAir(world, x+fx1, y+fy, z-4);
			placeAir(world, x+fx1, y+fy, z-3);
			placeAir(world, x+fx1, y+fy, z-2);
			placeAir(world, x+fx1, y+fy, z-1);
			placeAir(world, x+fx1, y+fy, z);
			placeAir(world, x+fx1, y+fy, z+1);
			placeAir(world, x+fx1, y+fy, z+2);
			placeAir(world, x+fx1, y+fy, z+3);
			placeAir(world, x+fx1, y+fy, z+4);
		}
	}

	/*@SideOnly(Side.CLIENT)
	IIcon	icon1;
	@SideOnly(Side.CLIENT)
	IIcon	icon2;
	@SideOnly(Side.CLIENT)
	IIcon	icon3;
	@SideOnly(Side.CLIENT)
	IIcon	icon4;
	@SideOnly(Side.CLIENT)
	IIcon	icon5;
	@SideOnly(Side.CLIENT)
	IIcon	icon6;

	@SideOnly(Side.CLIENT)
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

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister)
	{
		icon1 = iconregister.registerIcon("RivalRebels:cz"); // BOTTOM
		icon2 = iconregister.registerIcon("RivalRebels:da"); // TOP
		icon3 = iconregister.registerIcon("RivalRebels:dk"); // SIDE N
		icon4 = iconregister.registerIcon("RivalRebels:dk"); // SIDE S
		icon5 = iconregister.registerIcon("RivalRebels:dk"); // SIDE W
		icon6 = iconregister.registerIcon("RivalRebels:dk"); // SIDE E
	}*/
}
