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
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.block.trap.BlockPetrifiedStone;
import assets.rivalrebels.common.block.trap.BlockPetrifiedWood;
import assets.rivalrebels.common.entity.EntityTsarBlast;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
		if (world.isClient) return;
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
                        BlockState state = world.getBlockState(new BlockPos(X + posX, Y, Z + posZ));
						if (!state.getFluidState().isEmpty()) {
							world.setBlockState(new BlockPos(X + posX, Y, Z + posZ), Blocks.AIR.getDefaultState());
						}
					}
				}
			}
		}
	}

	public void tick(EntityTsarBlast tsarblast)
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
				if (repeatCount < RivalRebels.tsarBombaSpeed * 2) tick(tsarblast);
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
			tsarblast.kill();
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
				if (block == RRBlocks.omegaobj) RivalRebels.round.winSigma();
				else if (block == RRBlocks.sigmaobj) RivalRebels.round.winOmega();
				world.setBlockState(pos, Blocks.AIR.getDefaultState());
			}

			double limit = (radius / 2) + world.random.nextInt(radius / 4) + 7.5;
			if (dist < limit)
			{
				int blockType = world.random.nextInt(4) + 1;
				if (x >= 0 && z < 0) blockType = 1;
				if (x > 0 && z >= 0) blockType = 2;
				if (x <= 0 && z > 0) blockType = 3;
				if (x < 0 && z <= 0) blockType = 4;
				int metadata = (int) Math.ceil((16d / limit) * dist);
				metadata -= (radius / 10) - 1;
				if (metadata < 0) metadata = -metadata;
				metadata++;
				if (metadata > 15) metadata = 15;
				for (int Y = ylimit; Y > ylimit - (world.random.nextInt(5) + 2); Y--)
				{
					if (Y == 0) break;
                    BlockPos pos = new BlockPos(x + posX, Y, z + posZ);
                    Block block = world.getBlockState(pos).getBlock();
					if (block == RRBlocks.omegaobj) RivalRebels.round.winSigma();
					else if (block == RRBlocks.sigmaobj) RivalRebels.round.winOmega();
					if (blockType == 1) world.setBlockState(pos, RRBlocks.petrifiedstone1.getDefaultState().with(BlockPetrifiedStone.META, metadata));
					else if (blockType == 2) world.setBlockState(pos, RRBlocks.petrifiedstone2.getDefaultState().with(BlockPetrifiedStone.META, metadata));
					else if (blockType == 3) world.setBlockState(pos, RRBlocks.petrifiedstone3.getDefaultState().with(BlockPetrifiedStone.META, metadata));
					else world.setBlockState(pos, RRBlocks.petrifiedstone4.getDefaultState().with(BlockPetrifiedStone.META, metadata));
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
					world.setBlockState(new BlockPos(x + posX, Y, z + posZ), RRBlocks.petrifiedwood.getDefaultState().with(BlockPetrifiedWood.META, metadata));
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
					if (blockID == RRBlocks.omegaobj) RivalRebels.round.winSigma();
					else if (blockID == RRBlocks.sigmaobj) RivalRebels.round.winOmega();
					else if (!isTree)
					{
						BlockState state = world.getBlockState(new BlockPos(x + posX, yy - ylimit, z + posZ));
						world.setBlockState(new BlockPos(x + posX, yy, z + posZ), state);
					}
					else
					{
						isTree = false;
						for (int Yy = 0; Yy >= -treeHeight; Yy--)
						{
							world.setBlockState(new BlockPos(x + posX, yy + Yy, z + posZ), RRBlocks.petrifiedwood.getDefaultState().with(BlockPetrifiedWood.META, metadata));
						}
						break;
					}
				}
			}
			else
			{
				BlockState state = world.getBlockState(new BlockPos(x + posX, y, z + posZ));
				if (state.getBlock() == Blocks.BEDROCK)
				;
				else if (!state.isOpaque()) world.setBlockState(new BlockPos(x + posX, y, z + posZ), Blocks.AIR.getDefaultState());
				if (isTree)
				{
					isTree = false;
					int metadata = (int) Math.floor((16d / radius) * dist);
					if (metadata < 0) metadata = 0;
					metadata++;
					if (metadata > 15) metadata = 15;
					for (int Y = ylimit; Y > ylimit - treeHeight; Y--)
					{
						world.setBlockState(new BlockPos(x + posX, Y, z + posZ), RRBlocks.petrifiedwood.getDefaultState().with(BlockPetrifiedWood.META, metadata));
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
            BlockState state = world.getBlockState(pos);
			if (state.getBlock() != Blocks.AIR)
			{
				if (state.getBlock() == RRBlocks.omegaobj) RivalRebels.round.winSigma();
				else if (state.getBlock() == RRBlocks.sigmaobj) RivalRebels.round.winOmega();
				if (state.getBlock() == RRBlocks.reactive)
				{
					for (int i = 0; i < (1 - (dist / radius)) * 16 + world.random.nextDouble() * 2; i++)
					{
						world.setBlockState(pos, Blocks.AIR.getDefaultState());
					}
				}
				if (!state.isOpaque() || state.isIn(BlockTags.LOGS))
				{
					world.setBlockState(pos, Blocks.AIR.getDefaultState());
					if (dist > radius / 2 && state.isIn(BlockTags.LOGS) && world.getBlockState(pos.down()).isIn(BlockTags.LOGS)) isTree = true;
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
