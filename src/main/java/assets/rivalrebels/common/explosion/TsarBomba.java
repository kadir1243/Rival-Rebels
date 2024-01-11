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
package assets.rivalrebels.common.explosion;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.entity.EntityTsarBlast;

public class TsarBomba
{
	public int		posX;
	public int		posY;
	public int		posZ;
	public int		lastposX = 0;
	public int		lastposZ = 0;
	public int		radius;
	public World	world;
	private int		n = 1;
	private int		nlimit;
	private int		shell;
	private int		leg;
	private int		element;
	private int		repeatCount	= 0;
	private boolean isTree;
	private int 	treeHeight;
	public int processedchunks = 0;

	public TsarBomba(int x, int y, int z, World world, int rad)
	{
		posX = x;
		posY = y;
		posZ = z;
        this.world = world;
		radius = rad;
		//int radiussmaller = (radius >> 2) + 45;
		//if (radiussmaller < radius) radius = radiussmaller;
		nlimit = ((radius + 25) * (radius + 25)) * 4;
		rad = rad*rad;
		if (world.isRemote) return;
		int clamprad = radius;
		//if (clamprad > 50) clamprad = 50;
		for (int X = -clamprad; X < clamprad; X++)
		{
			int x2 = X * X;
			for (int Z = -clamprad; Z < clamprad; Z++)
			{
				if (x2 + Z * Z < rad)
				{
					for (int Y = 70; Y > 0; Y--)
					{
						Block block = world.getBlockState(new BlockPos(X + posX, Y, Z + posZ)).getBlock();
						if (block == Blocks.WATER || block == Blocks.LAVA || block == Blocks.FLOWING_WATER || block == Blocks.FLOWING_LAVA)
						{
							world.setBlockToAir(new BlockPos(X + posX, Y, Z + posZ));
						}
					}
				}
			}
		}
	}

	public void update(EntityTsarBlast tsarblast)
	{
		if (n > 0 && n < nlimit)
		{
			boolean repeat = processChunk(lastposX, lastposZ);

			shell = (int) Math.floor((Math.sqrt(n) + 1) / 2);
			int shell2 = 2 * shell;
			leg = (int) Math.floor((n - (shell2 - 1) * (shell2 - 1)) / shell2);
			element = (n - (shell2 - 1) * (shell2 - 1)) - shell2 * leg - shell + 1;
			lastposX = leg == 0 ? shell : leg == 1 ? -element : leg == 2 ? -shell : element;
			lastposZ = leg == 0 ? element : leg == 1 ? shell : leg == 2 ? -element : -shell;
			n++;
			if (!repeat)
			{
				repeatCount++;
				if (repeatCount < RivalRebels.tsarBombaSpeed * 2) update(tsarblast);
				else
				{
					repeatCount = 0;
					return;
				}
			}
		}
		else
		{
			tsarblast.tsar = null;
			tsarblast.setDead();
		}
	}

	private boolean processChunk(int x, int z)
	{
		processedchunks++;
		double dist = x * x + z * z;
		if (dist < radius * radius)
		{
			dist = Math.sqrt(dist);
			int y = getTopBlock(x + posX, z + posZ, dist);
			float yele = posY + (y - posY) * 0.5f;
			if (RivalRebels.elevation) yele = y;
			int ylimit = (int) Math.floor(yele - ((radius - dist) / 2) + (Math.sin(dist * 0.5) * 1.15));

			for (int Y = y; Y > ylimit; Y--)
			{
				if (Y == 0) break;
                BlockPos pos = new BlockPos(x + posX, Y, z + posZ);
                Block block = world.getBlockState(pos).getBlock();
				if (block == RivalRebels.omegaobj) RivalRebels.round.winSigma();
				else if (block == RivalRebels.sigmaobj) RivalRebels.round.winOmega();
				world.setBlockToAir(pos);
			}

			double limit = (radius / 2) + world.rand.nextInt(radius / 4) + 7.5;
			if (dist < limit)
			{
				int blockType = world.rand.nextInt(4) + 1;
				if (x >= 0 && z < 0) blockType = 1;
				if (x > 0 && z >= 0) blockType = 2;
				if (x <= 0 && z > 0) blockType = 3;
				if (x < 0 && z <= 0) blockType = 4;
				int metadata = (int) Math.ceil((16d / limit) * dist);
				metadata -= (radius / 10) - 1;
				if (metadata < 0) metadata = -metadata;
				metadata++;
				if (metadata > 15) metadata = 15;
				for (int Y = ylimit; Y > ylimit - (world.rand.nextInt(5) + 2); Y--)
				{
					if (Y == 0) break;
                    BlockPos pos = new BlockPos(x + posX, Y, z + posZ);
                    Block block = world.getBlockState(pos).getBlock();
					if (block == RivalRebels.omegaobj) RivalRebels.round.winSigma();
					else if (block == RivalRebels.sigmaobj) RivalRebels.round.winOmega();
					if (blockType == 1) world.setBlockState(pos, RivalRebels.petrifiedstone1.getStateFromMeta(metadata));
					else if (blockType == 2) world.setBlockState(pos, RivalRebels.petrifiedstone2.getStateFromMeta(metadata));
					else if (blockType == 3) world.setBlockState(pos, RivalRebels.petrifiedstone3.getStateFromMeta(metadata));
					else world.setBlockState(pos, RivalRebels.petrifiedstone4.getStateFromMeta(metadata));
				}
			}

			if (isTree)
			{
				isTree = false;
				int metadata = (int) Math.floor((16d / radius) * dist);
				if (metadata < 0) metadata = 0;
				metadata++;
				if (metadata > 15) metadata = 15;
				for (int Y = ylimit; Y > ylimit - treeHeight; Y--)
				{
					if (Y == 0) break;
					world.setBlockState(new BlockPos(x + posX, Y, z + posZ), RivalRebels.petrifiedwood.getStateFromMeta(metadata));
				}
			}

			return true;
		}
		else if (dist <= radius * radius * 1.3125 * 1.3125)
		{
			dist = Math.sqrt(dist);
			int y = getTopBlock(x + posX, z + posZ, dist);
			int ylimit = (int) Math.ceil(Math.sin((dist - radius - (radius / 16)) * radius * 0.001875) * (radius / 16));
			if (dist >= radius + 5)
			{
				int metadata = (int) Math.floor((16d / radius) * dist);
				if (metadata < 0) metadata = 0;
				metadata++;
				if (metadata > 15) metadata = 15;
				for (int Y = ylimit; Y >= 0; Y--)
				{
					if (Y == 0) break;
					int yy = Y + y;
					Block blockID = world.getBlockState(new BlockPos(x + posX, yy, z + posZ)).getBlock();
					if (blockID == RivalRebels.omegaobj) RivalRebels.round.winSigma();
					else if (blockID == RivalRebels.sigmaobj) RivalRebels.round.winOmega();
					else if (!isTree)
					{
						IBlockState state = world.getBlockState(new BlockPos(x + posX, yy - ylimit, z + posZ));
						world.setBlockState(new BlockPos(x + posX, yy, z + posZ), state);
					}
					else
					{
						isTree = false;
						for (int Yy = 0; Yy >= -treeHeight; Yy--)
						{
							world.setBlockState(new BlockPos(x + posX, yy + Yy, z + posZ), RivalRebels.petrifiedwood.getStateFromMeta(metadata));
						}
						break;
					}
				}
			}
			else
			{
				IBlockState blockID = world.getBlockState(new BlockPos(x + posX, y, z + posZ));
				if (blockID.getBlock() == Blocks.BEDROCK)
				;
				else if (!blockID.isOpaqueCube()) world.setBlockToAir(new BlockPos(x + posX, y, z + posZ));
				if (isTree)
				{
					isTree = false;
					int metadata = (int) Math.floor((16d / radius) * dist);
					if (metadata < 0) metadata = 0;
					metadata++;
					if (metadata > 15) metadata = 15;
					for (int Y = ylimit; Y > ylimit - treeHeight; Y--)
					{
						world.setBlockState(new BlockPos(x + posX, Y, z + posZ), RivalRebels.petrifiedwood.getStateFromMeta(metadata));
					}
				}
			}
			return true;
		}
		return false;
	}

	private int getTopBlock(int x, int z, double dist)
	{
		int foundY = 0;
		boolean found = false;
		for (int y = 256; y > 0; y--)
		{
            BlockPos pos = new BlockPos(x, y, z);
            IBlockState state = world.getBlockState(pos);
			if (state.getBlock() != Blocks.AIR)
			{
				if (state.getBlock() == RivalRebels.omegaobj) RivalRebels.round.winSigma();
				else if (state.getBlock() == RivalRebels.sigmaobj) RivalRebels.round.winOmega();
				if (state.getBlock() == RivalRebels.reactive)
				{
					for (int i = 0; i < (1 - (dist / radius)) * 16 + world.rand.nextDouble() * 2; i++)
					{
						world.setBlockToAir(pos);
					}
				}
				if (!state.isOpaqueCube() || state.getBlock() == Blocks.LOG)
				{
					world.setBlockToAir(pos);
					if (dist > radius / 2 && state.getBlock() == Blocks.LOG && world.getBlockState(new BlockPos(x, y - 1, z)) == Blocks.LOG) isTree = true;
					if (!found && isTree)
					{
						foundY = y;
						found = true;
					}
                }
				else
				{
					if (!found) return y;
					else
					{
						treeHeight = foundY - y;
						return foundY;
					}
				}
			}
		}
		return foundY;
	}
}
