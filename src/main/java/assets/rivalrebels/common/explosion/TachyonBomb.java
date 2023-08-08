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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.trap.BlockPetrifiedWood;
import assets.rivalrebels.common.entity.EntityTachyonBombBlast;
import assets.rivalrebels.common.noise.RivalRebelsSimplexNoise;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class TachyonBomb
{
	public int		posX;
	public int		posY;
	public int		posZ;
	public int		lastposX = 0;
	public int		lastposZ = 0;
	public int		radius;
	public World	worldObj;
	private int		n = 1;
	private int		nlimit;
	private int		shell;
	private int		leg;
	private int		element;
	private int		repeatCount	= 0;
	private boolean isTree;
	private int 	treeHeight;
	public int processedchunks = 0;

	public TachyonBomb(int x, int y, int z, World world, int rad)
	{
		posX = x;
		posY = y;
		posZ = z;
		worldObj = world;
		radius = rad;
		//int radiussmaller = (radius >> 2) + 45;
		//if (radiussmaller < radius) radius = radiussmaller;
		nlimit = ((radius + 25) * (radius + 25)) * 4;
		rad = rad*rad/2;
		if (world.isRemote) return;
		System.out.println("radius:" + radius);
		System.out.println("Nlimit:" + nlimit);
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
                        BlockPos pos = new BlockPos(X + posX, Y, Z + posZ);
                        Block block = worldObj.getBlockState(pos).getBlock();
						if (block == Blocks.water || block == Blocks.lava || block == Blocks.flowing_water || block == Blocks.flowing_lava)
						{
							worldObj.setBlockToAir(pos);
						}
					}
				}
			}
		}
	}

	public void update(EntityTachyonBombBlast tsarblast)
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
		//System.out.println("processedchunks:" + processedchunks);
		double dist = x * x + z * z;
		if (dist < radius * radius)
		{
			dist = Math.sqrt(dist) + RivalRebelsSimplexNoise.noise(x*0.05,z*0.05) * 10.0;
			int y = getTopBlock(x + posX, z + posZ, dist);
			float yele = posY + (y - posY) * 0.5f;
			if (RivalRebels.elevation) yele = y;
			int ylimit = (int) Math.floor(yele - (radius - dist));

			for (int Y = y; Y > ylimit; Y--)
			{
				if (Y == 0) break;
                BlockPos pos = new BlockPos(x + posX, Y, z + posZ);
                Block block = worldObj.getBlockState(pos).getBlock();
				if (block == RivalRebels.omegaobj) RivalRebels.round.winSigma();
				else if (block == RivalRebels.sigmaobj) RivalRebels.round.winOmega();
				worldObj.setBlockToAir(pos);
			}

			double limit = (radius / 2) + worldObj.rand.nextInt(radius / 4) + 7.5;
			if (dist < limit)
			{
				for (int Y = ylimit; Y > ylimit - (worldObj.rand.nextInt(5) + 2); Y--)
				{
					if (Y == 0) break;
                    BlockPos pos = new BlockPos(x + posX, Y, z + posZ);
                    Block block = worldObj.getBlockState(pos).getBlock();
					if (block == RivalRebels.omegaobj) RivalRebels.round.winSigma();
					else if (block == RivalRebels.sigmaobj) RivalRebels.round.winOmega();
					worldObj.setBlockState(pos, Blocks.obsidian.getDefaultState());
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
					if (Y == 0) continue;
					int yy = Y + y;
					Block block = worldObj.getBlockState(new BlockPos(x + posX, yy, z + posZ)).getBlock();
					if (block == RivalRebels.omegaobj) RivalRebels.round.winSigma();
					else if (block == RivalRebels.sigmaobj) RivalRebels.round.winOmega();
					else if (!isTree) {
						worldObj.setBlockState(new BlockPos(x + posX, yy, z + posZ), worldObj.getBlockState(new BlockPos(x + posX, yy - ylimit, z + posZ)));
					} else {
						isTree = false;
						for (int Yy = 0; Yy >= -treeHeight; Yy--) {
							worldObj.setBlockState(new BlockPos(x + posX, yy + Yy, z + posZ), RivalRebels.petrifiedwood.getDefaultState().withProperty(BlockPetrifiedWood.RADIOACTIVITY, metadata));
						}
						break;
					}
				}
			}
			else
			{
				Block block = worldObj.getBlockState(new BlockPos(x + posX, y, z + posZ)).getBlock();
				if (!block.isOpaqueCube()) worldObj.setBlockToAir(new BlockPos(x + posX, y, z + posZ));
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
			Block block = worldObj.getBlockState(pos).getBlock();
			if (block != Blocks.air)
			{
				if (block == RivalRebels.omegaobj) RivalRebels.round.winSigma();
				else if (block == RivalRebels.sigmaobj) RivalRebels.round.winOmega();
				if (block == RivalRebels.reactive)
				{
					for (int i = 0; i < (1 - (dist / radius)) * 16 + Math.random() * 2; i++)
					{
						worldObj.setBlockToAir(pos);
					}
				}
				if (!block.isOpaqueCube() || block == Blocks.log)
				{
					worldObj.setBlockToAir(pos);
					if (dist > radius / 2 && block == Blocks.log && worldObj.getBlockState(pos.down()).getBlock() == Blocks.log) isTree = true;
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
