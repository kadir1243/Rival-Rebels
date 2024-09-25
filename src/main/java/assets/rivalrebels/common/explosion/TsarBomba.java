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
import assets.rivalrebels.common.block.trap.BlockPetrifiedStone;
import assets.rivalrebels.common.block.trap.BlockPetrifiedWood;
import assets.rivalrebels.common.entity.EntityTsarBlast;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class TsarBomba
{
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

	public TsarBomba(int x, int y, int z, Level world, int rad)
	{
		posX = x;
		posY = y;
		posZ = z;
        this.world = world;
		radius = rad;
		//int radiussmaller = (radius / 4) + 45;
		nlimit = ((radius + 25) * (radius + 25)) * 4;
		if (world.isClientSide()) return;
		int clamprad = radius; //Mth.clamp(radius, radiussmaller, 50);

        BlockPos.betweenClosedStream(-clamprad, world.getMinBuildHeight(), -clamprad, clamprad, posY + 70, clamprad)
            .map(BlockPos::immutable)
            .filter(blockPos -> Vec3.atLowerCornerOf(blockPos).horizontalDistanceSqr() < radius * radius)
            .map(pos -> pos.offset(x, 0, z))
            .forEach(offset -> {
                BlockState state = world.getBlockState(offset);
                if (!state.getFluidState().isEmpty() && !state.is(BlockTags.FEATURES_CANNOT_REPLACE)) {
                    world.destroyBlock(offset, false);
                }
            });
	}

	public void tick(EntityTsarBlast tsarblast)
	{
		if (n > 0 && n < nlimit)
		{
			boolean repeat = processChunk(lastposX, lastposZ);

			shell = Mth.floor((Mth.sqrt(n) + 1) / 2);
			int shell2 = 2 * shell;
			leg = Mth.floor((n - (shell2 - 1) * (shell2 - 1)) / shell2);
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
			tsarblast.bomb = null;
			tsarblast.kill();
		}
	}

	private boolean processChunk(int x, int z) {
		double dist = x * x + z * z;
		if (dist < radius * radius) {
			dist = Math.sqrt(dist);
			int y = getTopBlock(x + posX, z + posZ, dist);
			float yele = posY + (y - posY) * 0.5f;
			if (RRConfig.SERVER.isElevation()) yele = y;
			int ylimit = Mth.floor(yele - ((radius - dist) / 2) + (Math.sin(dist * 0.5) * 1.15));

			for (int Y = y; Y > ylimit; Y--) {
				if (Y == world.getMinBuildHeight()) break;
                BlockPos pos = new BlockPos(x + posX, Y, z + posZ);
                BlockState state = world.getBlockState(pos);
				if (state.is(RRBlocks.omegaobj)) RivalRebels.round.winSigma();
				else if (state.is(RRBlocks.sigmaobj)) RivalRebels.round.winOmega();
				world.destroyBlock(pos, false);
			}

			double limit = (radius / 2) + world.random.nextInt(radius / 4) + 7.5;
			if (dist < limit) {
				int blockType = world.random.nextInt(4) + 1;
				if (x >= 0 && z < 0) blockType = 1;
				if (x > 0 && z >= 0) blockType = 2;
				if (x <= 0 && z > 0) blockType = 3;
				if (x < 0 && z <= 0) blockType = 4;
				int metadata = Mth.ceil((16d / limit) * dist);
				metadata -= (radius / 10) - 1;
				if (metadata < 0) metadata = -metadata;
				metadata++;
				if (metadata > 15) metadata = 15;
				for (int Y = ylimit; Y > ylimit - (world.random.nextInt(5) + 2); Y--) {
					if (Y == world.getMinBuildHeight()) break;
                    BlockPos pos = new BlockPos(x + posX, Y, z + posZ);
                    Block block = world.getBlockState(pos).getBlock();
					if (block == RRBlocks.omegaobj) RivalRebels.round.winSigma();
					else if (block == RRBlocks.sigmaobj) RivalRebels.round.winOmega();
					if (blockType == 1) world.setBlockAndUpdate(pos, RRBlocks.petrifiedstone1.defaultBlockState().setValue(BlockPetrifiedStone.META, metadata));
					else if (blockType == 2) world.setBlockAndUpdate(pos, RRBlocks.petrifiedstone2.defaultBlockState().setValue(BlockPetrifiedStone.META, metadata));
					else if (blockType == 3) world.setBlockAndUpdate(pos, RRBlocks.petrifiedstone3.defaultBlockState().setValue(BlockPetrifiedStone.META, metadata));
					else world.setBlockAndUpdate(pos, RRBlocks.petrifiedstone4.defaultBlockState().setValue(BlockPetrifiedStone.META, metadata));
				}
			}

			if (isTree) {
				isTree = false;
				int metadata = Mth.floor((16d / radius) * dist);
				if (metadata < 0) metadata = 0;
				metadata++;
				if (metadata > 15) metadata = 15;
				for (int Y = ylimit; Y > ylimit - treeHeight; Y--) {
					if (Y == world.getMinBuildHeight()) break;
					world.setBlockAndUpdate(new BlockPos(x + posX, Y, z + posZ), RRBlocks.petrifiedwood.defaultBlockState().setValue(BlockPetrifiedWood.META, metadata));
				}
			}

			return true;
		}
		else if (dist <= radius * radius * 1.3125 * 1.3125)
		{
			dist = Math.sqrt(dist);
			int y = getTopBlock(x + posX, z + posZ, dist);
			int ylimit = Mth.ceil(Math.sin((dist - radius - (radius / 16)) * radius * 0.001875) * (radius / 16));
			if (dist >= radius + 5)
			{
				int metadata = Mth.floor((16d / radius) * dist);
				if (metadata < 0) metadata = 0;
				metadata++;
				if (metadata > 15) metadata = 15;
				for (int Y = ylimit; Y >= world.getMinBuildHeight(); Y--) {
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
				BlockState state = world.getBlockState(new BlockPos(x + posX, y, z + posZ));
				if (state.is(Blocks.BEDROCK))
				;
				else if (!state.canOcclude()) world.setBlockAndUpdate(new BlockPos(x + posX, y, z + posZ), Blocks.AIR.defaultBlockState());
				if (isTree) {
					isTree = false;
					int metadata = Mth.floor((16d / radius) * dist);
					if (metadata < 0) metadata = 0;
					metadata++;
					if (metadata > 15) metadata = 15;
					for (int Y = ylimit; Y > ylimit - treeHeight; Y--)
					{
						world.setBlockAndUpdate(new BlockPos(x + posX, Y, z + posZ), RRBlocks.petrifiedwood.defaultBlockState().setValue(BlockPetrifiedWood.META, metadata));
					}
				}
			}
			return true;
		}
		return false;
	}

	private int getTopBlock(int x, int z, double dist)
	{
		int foundY = world.getMinBuildHeight();
		boolean found = false;
		for (int y = world.getMaxBuildHeight(); y > world.getMinBuildHeight(); y--)
		{
            BlockPos pos = new BlockPos(x, y, z);
            BlockState state = world.getBlockState(pos);
			if (!state.isAir())
			{
				if (state.is(RRBlocks.omegaobj)) RivalRebels.round.winSigma();
				else if (state.is(RRBlocks.sigmaobj)) RivalRebels.round.winOmega();
				if (state.is(RRBlocks.reactive))
				{
					for (int i = 0; i < (1 - (dist / radius)) * 16 + world.random.nextDouble() * 2; i++)
					{
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
