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
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class BlockRhodesScaffold extends BlockAutoTemplate {
    public static final MapCodec<BlockRhodesScaffold> CODEC = simpleCodec(BlockRhodesScaffold::new);

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
	public BlockRhodesScaffold(Properties settings)
	{
		super(settings);
	}

    @Override
    protected MapCodec<BlockRhodesScaffold> codec() {
        return CODEC;
    }

    @Override
	public void build(Level world, int x, int y, int z)
	{
		super.build(world, x, y, z);
		if (!world.isClientSide)
		{
			int scale = 1;
			if (world.getBlockState(new BlockPos(x, y-1, z)).getBlock() == RRBlocks.buildrhodes && world.getBlockState(new BlockPos(x, y-2, z)).getBlock() == RRBlocks.buildrhodes)
			{
				if (world.getBlockState(new BlockPos(x, y-3, z)).getBlock() == RRBlocks.buildrhodes && world.getBlockState(new BlockPos(x, y-4, z)).getBlock() == RRBlocks.buildrhodes) scale = 3;
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

	private void place(Level world, int x, int y, int z, int fy, int fx1, byte b)
	{
		if (b == 0)
		{
			world.setBlockAndUpdate(new BlockPos(x+fx1, y+fy, z-4), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(new BlockPos(x+fx1, y+fy, z-3), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(new BlockPos(x+fx1, y+fy, z-2), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(new BlockPos(x+fx1, y+fy, z-1), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(new BlockPos(x+fx1, y+fy, z), RRBlocks.steel.defaultBlockState());
			world.setBlockAndUpdate(new BlockPos(x+fx1, y+fy, z+1), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(new BlockPos(x+fx1, y+fy, z+2), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(new BlockPos(x+fx1, y+fy, z+3), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(new BlockPos(x+fx1, y+fy, z+4), Blocks.AIR.defaultBlockState());
		}
		if (b == 1)
		{
			setBlock(world, x+fx1, y+fy, z-4, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z-3, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z-2, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z-1, Blocks.AIR);
			if (RivalRebels.prefillrhodes) setBlock(world, x+fx1, y+fy, z, RRBlocks.conduit);
			else setBlock(world, x+fx1, y+fy, z, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z+1, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z+2, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z+3, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z+4, Blocks.AIR);
		}
		if (b == 2)
		{
			setBlock(world, x+fx1, y+fy, z-4, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z-3, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z-2, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z-1, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z, RRBlocks.rhodesactivator);
			setBlock(world, x+fx1, y+fy, z+1, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z+2, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z+3, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z+4, Blocks.AIR);
		}
		if (b == 3)
		{
			setBlock(world, x+fx1, y+fy, z-4, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z-3, RRBlocks.steel);
			setBlock(world, x+fx1, y+fy, z-2, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z-1, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z, RRBlocks.steel);
			setBlock(world, x+fx1, y+fy, z+1, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z+2, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z+3, RRBlocks.steel);
			setBlock(world, x+fx1, y+fy, z+4, Blocks.AIR);
		}
		if (b == 4)
		{
			setBlock(world, x+fx1, y+fy, z-4, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z-3, RRBlocks.steel);
			setBlock(world, x+fx1, y+fy, z-2, RRBlocks.steel);
			setBlock(world, x+fx1, y+fy, z-1, RRBlocks.steel);
			setBlock(world, x+fx1, y+fy, z, RRBlocks.steel);
			setBlock(world, x+fx1, y+fy, z+1, RRBlocks.steel);
			setBlock(world, x+fx1, y+fy, z+2, RRBlocks.steel);
			setBlock(world, x+fx1, y+fy, z+3, RRBlocks.steel);
			setBlock(world, x+fx1, y+fy, z+4, Blocks.AIR);
		}
		if (b == 5)
		{
			setBlock(world, x+fx1, y+fy, z-4, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z-3, RRBlocks.steel);
			setBlock(world, x+fx1, y+fy, z-2, RRBlocks.conduit);
			setBlock(world, x+fx1, y+fy, z-1, RRBlocks.steel);
			setBlock(world, x+fx1, y+fy, z, RRBlocks.steel);
			setBlock(world, x+fx1, y+fy, z+1, RRBlocks.steel);
			setBlock(world, x+fx1, y+fy, z+2, RRBlocks.conduit);
			setBlock(world, x+fx1, y+fy, z+3, RRBlocks.steel);
			setBlock(world, x+fx1, y+fy, z+4, Blocks.AIR);
		}
		if (b == 6)
		{
			setBlock(world, x+fx1, y+fy, z-4, RRBlocks.reactive);
			setBlock(world, x+fx1, y+fy, z-3, RRBlocks.reactive);
			setBlock(world, x+fx1, y+fy, z-2, RRBlocks.reactive);
			setBlock(world, x+fx1, y+fy, z-1, RRBlocks.reactive);
			setBlock(world, x+fx1, y+fy, z, RRBlocks.reactive);
			setBlock(world, x+fx1, y+fy, z+1, RRBlocks.reactive);
			setBlock(world, x+fx1, y+fy, z+2, RRBlocks.reactive);
			setBlock(world, x+fx1, y+fy, z+3, RRBlocks.reactive);
			setBlock(world, x+fx1, y+fy, z+4, RRBlocks.reactive);
		}
		if (b == 7)
		{
			setBlock(world, x+fx1, y+fy, z-4, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z-3, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z-2, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z-1, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z+1, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z+2, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z+3, Blocks.AIR);
			setBlock(world, x+fx1, y+fy, z+4, Blocks.AIR);
		}
	}

    private static void setBlock(Level world, int x, int y, int z, Block block) {
        world.setBlockAndUpdate(new BlockPos(x, y, z), block.defaultBlockState());
    }

	/*@Environment(EnvType.CLIENT)
	IIcon	icon1;
	@Environment(EnvType.CLIENT)
	IIcon	icon2;
	@Environment(EnvType.CLIENT)
	IIcon	icon3;
	@Environment(EnvType.CLIENT)
	IIcon	icon4;
	@Environment(EnvType.CLIENT)
	IIcon	icon5;
	@Environment(EnvType.CLIENT)
	IIcon	icon6;

	@Environment(EnvType.CLIENT)
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

	@Environment(EnvType.CLIENT)
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
