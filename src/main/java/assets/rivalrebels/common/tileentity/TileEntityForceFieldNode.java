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
package assets.rivalrebels.common.tileentity;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.block.machine.BlockForceField;
import assets.rivalrebels.common.block.machine.BlockForceFieldNode;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class TileEntityForceFieldNode extends TileEntityMachineBase
{
    public UUID uuid;
	public RivalRebelsTeam	rrteam		= null;
	public int				level		= 0;

	public TileEntityForceFieldNode(BlockPos pos, BlockState state)
	{
        super(RRTileEntities.FORCE_FIELD_NODE, pos, state);
        pInM = 345;
		pInR = 345;
	}

	@Override
	public void tick()
	{
		if (pInR > 0) pInR = powered(pInR, edist);
		else turnOff();
		pInR -= decay;
	}

	public void turnOff()
	{
		if (level > 0)
		{
			int meta = this.getCachedState().get(BlockForceFieldNode.META);

			level--;
			for (int y = 0; y < 7; y++)
			{
				switch (meta)
				{
					case 2:
						if (world.getBlockState(new BlockPos(getPos().getX(), getPos().getY() + (y - 3), getPos().getZ() - level - 1)).getBlock() == RRBlocks.forcefield)
						{
							world.setBlockState(new BlockPos(getPos().getX(), getPos().getY() + (y - 3), getPos().getZ() - level - 1), Blocks.AIR.getDefaultState());
						}
					break;

					case 3:
						if (world.getBlockState(getPos().add(0, y, level).down(3).south()).getBlock() == RRBlocks.forcefield)
						{
							world.setBlockState(getPos().add(0, y, level).down(3).south(), Blocks.AIR.getDefaultState());
						}
					break;

					case 4:
						if (world.getBlockState(getPos().add(-level, y, 0).down(3).west()).getBlock() == RRBlocks.forcefield)
						{
							world.setBlockState(getPos().add(-level, y, 0).down(3).west(), Blocks.AIR.getDefaultState());
						}
					break;

					case 5:
						if (world.getBlockState(getPos().add(level, y, 0).down(3).east()).getBlock() == RRBlocks.forcefield)
						{
							world.setBlockState(getPos().add(level, y, 0).down(3).east(), Blocks.AIR.getDefaultState());
						}
					break;
				}
			}
		}
	}

	@Override
	public Box getRenderBoundingBox() {
		float t = 0.0625f;
		float l = 35f;
		float h = 3.5f;
        return switch (this.getCachedState().get(BlockForceFieldNode.META)) {
            case 2 ->
                    new Box(getPos().getX() + 0.5f - t, getPos().getY() + 0.5f - h, getPos().getZ() - l, getPos().getX() + 0.5f + t, getPos().getY() + 0.5f + h, getPos().getZ());
            case 3 ->
                    new Box(getPos().getX() + 0.5f - t, getPos().getY() + 0.5f - h, getPos().getZ() + 1f, getPos().getX() + 0.5f + t, getPos().getY() + 0.5f + h, getPos().getZ() + 1f + l);
            case 4 ->
                    new Box(getPos().getX() - l, getPos().getY() + 0.5f - h, getPos().getZ() + 0.5f - t, getPos().getX(), getPos().getY() + 0.5f + h, getPos().getZ() + 0.5f + t);
            case 5 ->
                    new Box(getPos().getX() + 1f, getPos().getY() + 0.5f - h, getPos().getZ() + 0.5f - t, getPos().getX() + 1f + l, getPos().getY() + 0.5f + h, getPos().getZ() + 0.5f + t);
            default -> new Box(getPos(), getPos());
        };
	}


    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

		rrteam = RivalRebelsTeam.getForID(nbt.getInt("rrteam"));
		if (rrteam == RivalRebelsTeam.NONE) rrteam = null;
		if (rrteam == null) uuid = nbt.getUuid("uuid");
	}

    @Override
    public void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		if (rrteam != null) nbt.putInt("rrteam", rrteam.ordinal());
		if (uuid != null) nbt.putUuid("uuid", uuid);
    }

	@Override
	public float powered(float power, float distance)
	{
		float hits = world.random.nextFloat();
		int meta = this.getCachedState().get(BlockForceFieldNode.META);

		double randomness = 0.1;
		float thickness = 0.5f;
		float length = 35f;
		float height = 3.52f;
		double speed = 2;

		if (meta == 2)
		{
			Box aabb = new Box(getPos().getX() + 0.5f - thickness, getPos().getY() + 0.5f - height, getPos().getZ() - length, getPos().getX() + 0.5f + thickness, getPos().getY() + 0.5f + height, getPos().getZ());
			List<Entity> list = world.getOtherEntities(null, aabb);
            for (Entity e : list) {
                boolean shouldContinue = true;
                if (e instanceof PlayerEntity p) {
                    RivalRebelsPlayer player = RivalRebels.round.rrplayerlist.getForGameProfile(p.getGameProfile());
                    if (p.getGameProfile().getId().equals(uuid) || (player != null && player.rrteam == rrteam)) {
                        shouldContinue = false;
                        hits++;
                        p.setPos(p.getX() + (p.getX() > (getPos().getX() + 0.5) ? -2 : 2), p.getY(), p.getZ());
                    }
                }
                if (shouldContinue && e != null) {
                    double cpx = getPos().getX() + 0.5f - e.getPos().x;
                    double cpy = e.getY() + e.getEyeHeight(e.getPose()) - e.getY();
                    double cpz = e.getZ() - e.getZ();

                    double dist = Math.sqrt(cpx * cpx + cpy * cpy + cpz * cpz) / speed;

                    cpx /= dist;
                    cpy /= dist;
                    cpz /= dist;

                    cpx += world.random.nextGaussian() * randomness;
                    cpy += world.random.nextGaussian() * randomness;
                    cpz += world.random.nextGaussian() * randomness;

                    e.setVelocity(e.getVelocity().negate().subtract(cpx, cpy, cpz));
                    RivalRebelsSoundPlayer.playSound(e, 10, 7, 1, 2f);
                    if (e.getBoundingBox() != null) hits += e.getBoundingBox().getAverageSideLength();
                }
            }
			if (level < length)
			{
				placeBlockCarefully(world, getPos().getX(), getPos().getY(), (int) (getPos().getZ() - length - 1), RRBlocks.reactive);
				for (int y = 0; y < 7; y++)
				{
					if (world.getBlockState(new BlockPos(getPos().getX(), getPos().getY() + (y - 3), getPos().getZ() - level - 1)).getBlock() != RRBlocks.forcefield)
					{
						world.setBlockState(new BlockPos(getPos().getX(), getPos().getY() + (y - 3), getPos().getZ() - level - 1), RRBlocks.forcefield.getDefaultState().with(BlockForceField.META, meta));
						hits++;
					}
				}
				level++;
			}
		}

		if (meta == 3)
		{
			Box aabb = new Box(getPos().getX() + 0.5f - thickness, getPos().getY() + 0.5f - height, getPos().getZ() + 1f, getPos().getX() + 0.5f + thickness, getPos().getY() + 0.5f + height, getPos().getZ() + 1f + length);
			List<Entity> list = world.getOtherEntities(null, aabb);
            for (Entity e : list) {
                boolean shouldContinue = true;
                if (e instanceof PlayerEntity p) {
                    RivalRebelsPlayer player = RivalRebels.round.rrplayerlist.getForGameProfile(p.getGameProfile());
                    if (p.getGameProfile().getId().equals(uuid) || (player != null && player.rrteam == rrteam)) {
                        shouldContinue = false;
                        hits++;
                        p.setPos(p.getX() + (p.getX() > (getPos().getX() + 0.5) ? -2 : 2), p.getY(), p.getZ());
                    }
                }
                if (shouldContinue && e != null) {
                    double cpx = getPos().getX() + 0.5f - e.getX();
                    double cpy = e.getY() + e.getEyeHeight(e.getPose()) - e.getY();
                    double cpz = e.getZ() - e.getZ();

                    double dist = Math.sqrt(cpx * cpx + cpy * cpy + cpz * cpz) / speed;

                    cpx /= dist;
                    cpy /= dist;
                    cpz /= dist;

                    cpx += world.random.nextGaussian() * randomness;
                    cpy += world.random.nextGaussian() * randomness;
                    cpz += world.random.nextGaussian() * randomness;

                    e.setVelocity(e.getVelocity().negate().subtract(cpx, cpy, cpz));
                    RivalRebelsSoundPlayer.playSound(e, 10, 7, 1, 2f);
                    if (e.getBoundingBox() != null) hits += e.getBoundingBox().getAverageSideLength();
                }
            }
			if (level < length)
			{
				placeBlockCarefully(world, getPos().getX(), getPos().getY(), (int) (getPos().getZ() + length + 1), RRBlocks.reactive);
				for (int y = 0; y < 7; y++)
				{
					if (world.getBlockState(getPos().add(0, y, level).down(3).south()).getBlock() != RRBlocks.forcefield)
					{
						world.setBlockState(getPos().add(0, y, level).down(3).south(), RRBlocks.forcefield.getDefaultState().with(BlockForceField.META, meta));
						hits++;
					}
				}
				level++;
			}
		}

		if (meta == 4)
		{
			Box aabb = new Box(getPos().getX() - length, getPos().getY() + 0.5f - height, getPos().getZ() + 0.5f - thickness, getPos().getX(), getPos().getY() + 0.5f + height, getPos().getZ() + 0.5f + thickness);
			List<Entity> list = world.getOtherEntities(null, aabb);
            for (Entity e : list) {
                boolean shouldContinue = true;
                if (e instanceof PlayerEntity p) {
                    RivalRebelsPlayer player = RivalRebels.round.rrplayerlist.getForGameProfile(p.getGameProfile());
                    if (p.getGameProfile().getId().equals(uuid) || (player != null && player.rrteam == rrteam)) {
                        shouldContinue = false;
                        hits++;
                        p.setPos(p.getX(), p.getY(), p.getZ() + (p.getZ() > (getPos().getZ() + 0.5) ? -2 : 2));
                    }
                }
                if (shouldContinue && e != null) {
                    double cpx = e.getX() - e.getX();
                    double cpy = e.getY() + e.getEyeHeight(e.getPose()) - e.getY();
                    double cpz = getPos().getZ() + 0.5f - e.getZ();

                    double dist = Math.sqrt(cpx * cpx + cpy * cpy + cpz * cpz) / speed;

                    cpx /= dist;
                    cpy /= dist;
                    cpz /= dist;

                    cpx += world.random.nextGaussian() * randomness;
                    cpy += world.random.nextGaussian() * randomness;
                    cpz += world.random.nextGaussian() * randomness;

                    e.setVelocity(e.getVelocity().negate().subtract(cpx, cpy, cpz));
                    RivalRebelsSoundPlayer.playSound(e, 10, 7, 1, 2f);
                    if (e.getBoundingBox() != null) hits += e.getBoundingBox().getAverageSideLength();
                }
            }
			if (level < length)
			{
				placeBlockCarefully(world, (int) (getPos().getX() - length - 1), getPos().getY(), getPos().getZ(), RRBlocks.reactive);
				for (int y = 0; y < 7; y++)
				{
					if (world.getBlockState(getPos().add(-level, y, 0).down(3).west()).getBlock() != RRBlocks.forcefield)
					{
						world.setBlockState(getPos().add(-level, y, 0).down(3).west(), RRBlocks.forcefield.getDefaultState().with(BlockForceField.META, meta));
						hits++;
					}
				}
				level++;
			}
		}

		if (meta == 5)
		{
			Box aabb = new Box(getPos().getX() + 1f, getPos().getY() + 0.5f - height, getPos().getZ() + 0.5f - thickness, getPos().getX() + 1f + length, getPos().getY() + 0.5f + height, getPos().getZ() + 0.5f + thickness);
			List<Entity> list = world.getOtherEntities(null, aabb);
            for (Entity e : list) {
                boolean shouldContinue = true;
                if (e instanceof PlayerEntity p) {
                    RivalRebelsPlayer player = RivalRebels.round.rrplayerlist.getForGameProfile(p.getGameProfile());
                    if (p.getGameProfile().getId().equals(uuid) || (player != null && player.rrteam == rrteam)) {
                        shouldContinue = false;
                        hits++;
                        p.setPos(p.getX(), p.getY(), p.getZ() + (p.getZ() > (getPos().getZ() + 0.5) ? -2 : 2));
                    }
                }
                if (shouldContinue && e != null) {
                    double cpx = e.getX() - e.getX();
                    double cpy = e.getY() + e.getEyeHeight(e.getPose()) - e.getY();
                    double cpz = getPos().getZ() + 0.5f - e.getZ();

                    double dist = Math.sqrt(cpx * cpx + cpy * cpy + cpz * cpz) / speed;

                    cpx /= dist;
                    cpy /= dist;
                    cpz /= dist;

                    cpx += world.random.nextGaussian() * randomness;
                    cpy += world.random.nextGaussian() * randomness;
                    cpz += world.random.nextGaussian() * randomness;

                    e.setVelocity(e.getVelocity().negate().subtract(cpx, cpy, cpz));
                    RivalRebelsSoundPlayer.playSound(e, 10, 7, 1, 2f);
                    if (e.getBoundingBox() != null) hits += e.getBoundingBox().getAverageSideLength();
                }
            }
			if (level < length)
			{
				placeBlockCarefully(world, (int) (getPos().getX() + length + 1), getPos().getY(), getPos().getZ(), RRBlocks.reactive);
				for (int y = 0; y < 7; y++)
				{
					if (world.getBlockState(getPos().add(level, y, 0).down(3).east()).getBlock() != RRBlocks.forcefield)
					{
						world.setBlockState(getPos().add(level, y, 0).down(3).east(), RRBlocks.forcefield.getDefaultState().with(BlockForceField.META, meta));
						hits++;
					}
				}
				level++;
			}
		}
		return (power - (hits * 16));
	}

	public void placeBlockCarefully(World world, int i, int j, int z, Block block)
	{
        BlockPos pos = new BlockPos(i, j, z);
        if (world.getBlockState(pos).getBlock() != RRBlocks.reactive &&
            world.getBlockState(pos).getBlock() != RRBlocks.fshield &&
            world.getBlockState(pos).getBlock() != RRBlocks.omegaobj &&
            world.getBlockState(pos).getBlock() != RRBlocks.sigmaobj)
		{
			world.setBlockState(pos, block.getDefaultState());
		}
	}
}
