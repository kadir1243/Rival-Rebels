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

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.block.trap.BlockPetrifiedWood;
import assets.rivalrebels.common.entity.EntityTachyonBombBlast;
import assets.rivalrebels.common.noise.RivalRebelsSimplexNoise;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TachyonBomb {
    private final RivalRebelsSimplexNoise noise;
	public int		posX;
	public int		posY;
	public int		posZ;
	public int		lastposX = 0;
	public int		lastposZ = 0;
	public int		radius;
	public Level	world;
	private int		n = 1;
	private int		nlimit;
	private int		shell;
	private int		leg;
	private int		element;
	private int		repeatCount	= 0;
	private boolean isTree;
	private int 	treeHeight;
	public int processedchunks = 0;

	public TachyonBomb(int x, int y, int z, Level world, int rad) {
        noise = new RivalRebelsSimplexNoise(world.random);
		posX = x;
		posY = y;
		posZ = z;
        this.world = world;
		radius = rad;
		//int radiussmaller = (radius >> 2) + 45;
		//if (radiussmaller < radius) radius = radiussmaller;
		nlimit = ((radius + 25) * (radius + 25)) * 4;
		rad = rad*rad/2;
		if (world.isClientSide()) return;
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
                        BlockState state = world.getBlockState(new BlockPos(x + posX, Y, z + posZ));
						if (!state.getFluidState().isEmpty()) {
							world.setBlockAndUpdate(new BlockPos(x + posX, Y, z + posZ), Blocks.AIR.defaultBlockState());
						}
					}
				}
			}
		}
	}

	public void tick(EntityTachyonBombBlast tsarblast)
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
				if (repeatCount < RRConfig.SERVER.getTsarBombaSpeed() * 2) tick(tsarblast);
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
			dist = Math.sqrt(dist) + noise.noise(x*0.05,z*0.05) * 10.0;
			int y = getTopBlock(x + posX, z + posZ, dist);
			float yele = posY + (y - posY) * 0.5f;
			if (RRConfig.SERVER.isElevation()) yele = y;
			int ylimit = (int) Math.floor(yele - (radius - dist));

			for (int Y = y; Y > ylimit; Y--)
			{
				if (Y == 0) break;
				Block block = world.getBlockState(new BlockPos(x + posX, Y, z + posZ)).getBlock();
				if (block == RRBlocks.omegaobj) RivalRebels.round.winSigma();
				else if (block == RRBlocks.sigmaobj) RivalRebels.round.winOmega();
				world.setBlockAndUpdate(new BlockPos(x + posX, Y, z + posZ), Blocks.AIR.defaultBlockState());
			}

			double limit = (radius / 2) + world.random.nextInt(radius / 4) + 7.5;
			if (dist < limit)
			{
				for (int Y = ylimit; Y > ylimit - (world.random.nextInt(5) + 2); Y--)
				{
					if (Y == 0) break;
					Block block = world.getBlockState(new BlockPos(x + posX, Y, z + posZ)).getBlock();
					if (block == RRBlocks.omegaobj) RivalRebels.round.winSigma();
					else if (block == RRBlocks.sigmaobj) RivalRebels.round.winOmega();
					world.setBlockAndUpdate(new BlockPos(x + posX, Y, z + posZ), Blocks.OBSIDIAN.defaultBlockState());
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
					Block blockID = world.getBlockState(new BlockPos(x + posX, yy, z + posZ)).getBlock();
					if (blockID == RRBlocks.omegaobj) RivalRebels.round.winSigma();
					else if (blockID == RRBlocks.sigmaobj) RivalRebels.round.winOmega();
					else if (!isTree)
					{
						BlockState state = world.getBlockState(new BlockPos(x + posX, yy - ylimit, z + posZ));
						world.setBlockAndUpdate(new BlockPos(x + posX, yy, z + posZ), state);
					}
					else
					{
						isTree = false;
						for (int Yy = 0; Yy >= -treeHeight; Yy--)
						{
							world.setBlockAndUpdate(new BlockPos(x + posX, yy + Yy, z + posZ), RRBlocks.petrifiedwood.defaultBlockState().setValue(BlockPetrifiedWood.META, metadata));
						}
						break;
					}
				}
			}
			else
			{
				BlockState block = world.getBlockState(new BlockPos(x + posX, y, z + posZ));
				if (!block.canOcclude()) world.setBlockAndUpdate(new BlockPos(x + posX, y, z + posZ), Blocks.AIR.defaultBlockState());
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
            Block block = state.getBlock();
			if (!world.isEmptyBlock(pos)) {
				if (block == RRBlocks.omegaobj) RivalRebels.round.winSigma();
				else if (block == RRBlocks.sigmaobj) RivalRebels.round.winOmega();
				if (block == RRBlocks.reactive) {
					for (int i = 0; i < (1 - (dist / radius)) * 16 + world.random.nextDouble() * 2; i++) {
						world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
					}
				}
				if (!state.canOcclude() || state.is(BlockTags.LOGS))
				{
					world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
					if (dist > radius / 2 && state.is(BlockTags.LOGS) && world.getBlockState(pos.below()).is(BlockTags.LOGS)) isTree = true;
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
