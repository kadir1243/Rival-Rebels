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
package io.github.kadir1243.rivalrebels.common.tileentity;

import io.github.kadir1243.rivalrebels.RivalRebels;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.block.machine.BlockForceField;
import io.github.kadir1243.rivalrebels.common.block.machine.BlockForceFieldNode;
import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsPlayer;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsTeam;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TileEntityForceFieldNode extends TileEntityMachineBase
{
    public UUID uuid;
	public RivalRebelsTeam	rrteam		= RivalRebelsTeam.NONE;
	public int				level		= 0;

	public TileEntityForceFieldNode(BlockPos pos, BlockState state)
	{
        super(RRTileEntities.FORCE_FIELD_NODE.get(), pos, state);
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
			Direction meta = this.getBlockState().getValue(BlockForceFieldNode.FACING);

			level--;
			for (int y = 0; y < 7; y++)
			{
                if (meta == Direction.NORTH) {
                    if (getLevel().getBlockState(new BlockPos(getBlockPos().getX(), getBlockPos().getY() + (y - 3), getBlockPos().getZ() - level - 1)).is(RRBlocks.forcefield)) {
                        getLevel().setBlockAndUpdate(new BlockPos(getBlockPos().getX(), getBlockPos().getY() + (y - 3), getBlockPos().getZ() - level - 1), Blocks.AIR.defaultBlockState());
                    }
                } else {
                    if (getLevel().getBlockState(getBlockPos().offset(0, y, level).below(3).relative(meta)).is(RRBlocks.forcefield)) {
                        getLevel().setBlockAndUpdate(getBlockPos().offset(0, y, level).below(3).relative(meta), Blocks.AIR.defaultBlockState());
                    }
                }
			}
		}
	}

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);

		rrteam = RivalRebelsTeam.getForID(nbt.getInt("rrteam"));
		if (rrteam == RivalRebelsTeam.NONE) uuid = nbt.getUUID("uuid");
	}

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
		super.saveAdditional(nbt, provider);
		if (rrteam != RivalRebelsTeam.NONE) nbt.putInt("rrteam", rrteam.ordinal());
		if (uuid != null) nbt.putUUID("uuid", uuid);
    }

	@Override
	public float powered(float power, float distance)
	{
		float hits = getLevel().random.nextFloat();
		Direction meta = this.getBlockState().getValue(BlockForceFieldNode.FACING);

		double randomness = 0.1;
		float thickness = 0.5f;
		float length = 35f;
		float height = 3.52f;
		double speed = 2;

		if (meta == Direction.NORTH)
		{
			AABB aabb = new AABB(getBlockPos().getX() + 0.5f - thickness, getBlockPos().getY() + 0.5f - height, getBlockPos().getZ() - length, getBlockPos().getX() + 0.5f + thickness, getBlockPos().getY() + 0.5f + height, getBlockPos().getZ());
			List<Entity> list = getLevel().getEntities(null, aabb);
            for (Entity e : list) {
                boolean shouldContinue = true;
                if (e instanceof Player p) {
                    RivalRebelsPlayer player = RivalRebels.round.rrplayerlist.getForGameProfile(p.getGameProfile());
                    if (p.getGameProfile().getId().equals(uuid) || (player != null && player.rrteam == rrteam)) {
                        shouldContinue = false;
                        hits++;
                        p.setPosRaw(p.getX() + (p.getX() > (getBlockPos().getX() + 0.5) ? -2 : 2), p.getY(), p.getZ());
                    }
                }
                if (shouldContinue && e != null) {
                    double cpx = getBlockPos().getX() + 0.5f - e.getX();
                    double cpy = e.getEyeY() - e.getY();
                    double cpz = e.getZ() - e.getZ();

                    double dist = Math.sqrt(cpx * cpx + cpy * cpy + cpz * cpz) / speed;

                    cpx /= dist;
                    cpy /= dist;
                    cpz /= dist;

                    cpx += getLevel().random.nextGaussian() * randomness;
                    cpy += getLevel().random.nextGaussian() * randomness;
                    cpz += getLevel().random.nextGaussian() * randomness;

                    e.setDeltaMovement(e.getDeltaMovement().reverse().subtract(cpx, cpy, cpz));
                    e.playSound(RRSounds.GUI_UNKNOWN8.get(), 1, 2);
                    if (e.getBoundingBox() != null) hits += e.getBoundingBox().getSize();
                }
            }
			if (level < length)
			{
				placeBlockCarefully(getLevel(), getBlockPos().getX(), getBlockPos().getY(), (int) (getBlockPos().getZ() - length - 1), RRBlocks.reactive.get());
				for (int y = 0; y < 7; y++)
				{
					if (!getLevel().getBlockState(new BlockPos(getBlockPos().getX(), getBlockPos().getY() + (y - 3), getBlockPos().getZ() - level - 1)).is(RRBlocks.forcefield))
					{
						getLevel().setBlockAndUpdate(new BlockPos(getBlockPos().getX(), getBlockPos().getY() + (y - 3), getBlockPos().getZ() - level - 1), RRBlocks.forcefield.get().defaultBlockState().setValue(BlockForceField.FACING, meta));
						hits++;
					}
				}
				level++;
			}
		}

		if (meta == Direction.SOUTH)
		{
			AABB aabb = new AABB(getBlockPos().getX() + 0.5f - thickness, getBlockPos().getY() + 0.5f - height, getBlockPos().getZ() + 1f, getBlockPos().getX() + 0.5f + thickness, getBlockPos().getY() + 0.5f + height, getBlockPos().getZ() + 1f + length);
			List<Entity> list = getLevel().getEntities(null, aabb);
            for (Entity e : list) {
                boolean shouldContinue = true;
                if (e instanceof Player p) {
                    RivalRebelsPlayer player = RivalRebels.round.rrplayerlist.getForGameProfile(p.getGameProfile());
                    if (p.getGameProfile().getId().equals(uuid) || (player != null && player.rrteam == rrteam)) {
                        shouldContinue = false;
                        hits++;
                        p.setPosRaw(p.getX() + (p.getX() > (getBlockPos().getX() + 0.5) ? -2 : 2), p.getY(), p.getZ());
                    }
                }
                if (shouldContinue && e != null) {
                    double cpx = getBlockPos().getX() + 0.5f - e.getX();
                    double cpy = e.getEyeY() - e.getY();
                    double cpz = e.getZ() - e.getZ();

                    double dist = Math.sqrt(cpx * cpx + cpy * cpy + cpz * cpz) / speed;

                    cpx /= dist;
                    cpy /= dist;
                    cpz /= dist;

                    cpx += getLevel().random.nextGaussian() * randomness;
                    cpy += getLevel().random.nextGaussian() * randomness;
                    cpz += getLevel().random.nextGaussian() * randomness;

                    e.setDeltaMovement(e.getDeltaMovement().reverse().subtract(cpx, cpy, cpz));
                    e.playSound(RRSounds.GUI_UNKNOWN8.get(), 1, 2);
                    if (e.getBoundingBox() != null) hits += e.getBoundingBox().getSize();
                }
            }
			if (level < length)
			{
				placeBlockCarefully(getLevel(), getBlockPos().getX(), getBlockPos().getY(), (int) (getBlockPos().getZ() + length + 1), RRBlocks.reactive.get());
				for (int y = 0; y < 7; y++)
				{
					if (!getLevel().getBlockState(getBlockPos().offset(0, y, level).below(3).south()).is(RRBlocks.forcefield))
					{
						getLevel().setBlockAndUpdate(getBlockPos().offset(0, y, level).below(3).south(), RRBlocks.forcefield.get().defaultBlockState().setValue(BlockForceField.FACING, meta));
						hits++;
					}
				}
				level++;
			}
		}

		if (meta == Direction.WEST)
		{
			AABB aabb = new AABB(getBlockPos().getX() - length, getBlockPos().getY() + 0.5f - height, getBlockPos().getZ() + 0.5f - thickness, getBlockPos().getX(), getBlockPos().getY() + 0.5f + height, getBlockPos().getZ() + 0.5f + thickness);
			List<Entity> list = getLevel().getEntities(null, aabb);
            for (Entity e : list) {
                boolean shouldContinue = true;
                if (e instanceof Player p) {
                    RivalRebelsPlayer player = RivalRebels.round.rrplayerlist.getForGameProfile(p.getGameProfile());
                    if (p.getGameProfile().getId().equals(uuid) || (player != null && player.rrteam == rrteam)) {
                        shouldContinue = false;
                        hits++;
                        p.setPosRaw(p.getX(), p.getY(), p.getZ() + (p.getZ() > (getBlockPos().getZ() + 0.5) ? -2 : 2));
                    }
                }
                if (shouldContinue && e != null) {
                    double cpx = e.getX() - e.getX();
                    double cpy = e.getEyeY() - e.getY();
                    double cpz = getBlockPos().getZ() + 0.5f - e.getZ();

                    double dist = Math.sqrt(cpx * cpx + cpy * cpy + cpz * cpz) / speed;

                    cpx /= dist;
                    cpy /= dist;
                    cpz /= dist;

                    cpx += getLevel().random.nextGaussian() * randomness;
                    cpy += getLevel().random.nextGaussian() * randomness;
                    cpz += getLevel().random.nextGaussian() * randomness;

                    e.setDeltaMovement(e.getDeltaMovement().reverse().subtract(cpx, cpy, cpz));
                    e.playSound(RRSounds.GUI_UNKNOWN8.get(), 1, 2);
                    if (e.getBoundingBox() != null) hits += e.getBoundingBox().getSize();
                }
            }
			if (level < length)
			{
				placeBlockCarefully(getLevel(), (int) (getBlockPos().getX() - length - 1), getBlockPos().getY(), getBlockPos().getZ(), RRBlocks.reactive.get());
				for (int y = 0; y < 7; y++)
				{
					if (!getLevel().getBlockState(getBlockPos().offset(-level, y, 0).below(3).west()).is(RRBlocks.forcefield))
					{
						getLevel().setBlockAndUpdate(getBlockPos().offset(-level, y, 0).below(3).west(), RRBlocks.forcefield.get().defaultBlockState().setValue(BlockForceField.FACING, meta));
						hits++;
					}
				}
				level++;
			}
		}

		if (meta == Direction.EAST)
		{
			AABB aabb = new AABB(getBlockPos().getX() + 1f, getBlockPos().getY() + 0.5f - height, getBlockPos().getZ() + 0.5f - thickness, getBlockPos().getX() + 1f + length, getBlockPos().getY() + 0.5f + height, getBlockPos().getZ() + 0.5f + thickness);
			List<Entity> list = getLevel().getEntities(null, aabb);
            for (Entity e : list) {
                boolean shouldContinue = true;
                if (e instanceof Player p) {
                    RivalRebelsPlayer player = RivalRebels.round.rrplayerlist.getForGameProfile(p.getGameProfile());
                    if (p.getGameProfile().getId().equals(uuid) || (player != null && player.rrteam == rrteam)) {
                        shouldContinue = false;
                        hits++;
                        p.setPosRaw(p.getX(), p.getY(), p.getZ() + (p.getZ() > (getBlockPos().getZ() + 0.5) ? -2 : 2));
                    }
                }
                if (shouldContinue && e != null) {
                    double cpx = e.getX() - e.getX();
                    double cpy = e.getEyeY() - e.getY();
                    double cpz = getBlockPos().getZ() + 0.5f - e.getZ();

                    double dist = Math.sqrt(cpx * cpx + cpy * cpy + cpz * cpz) / speed;

                    cpx /= dist;
                    cpy /= dist;
                    cpz /= dist;

                    cpx += getLevel().random.nextGaussian() * randomness;
                    cpy += getLevel().random.nextGaussian() * randomness;
                    cpz += getLevel().random.nextGaussian() * randomness;

                    e.setDeltaMovement(e.getDeltaMovement().reverse().subtract(cpx, cpy, cpz));
                    e.playSound(RRSounds.GUI_UNKNOWN8.get(), 1, 2);
                    if (e.getBoundingBox() != null) hits += e.getBoundingBox().getSize();
                }
            }
			if (level < length)
			{
				placeBlockCarefully(getLevel(), (int) (getBlockPos().getX() + length + 1), getBlockPos().getY(), getBlockPos().getZ(), RRBlocks.reactive.get());
				for (int y = 0; y < 7; y++)
				{
					if (!getLevel().getBlockState(getBlockPos().offset(level, y, 0).below(3).east()).is(RRBlocks.forcefield.get()))
					{
						getLevel().setBlockAndUpdate(getBlockPos().offset(level, y, 0).below(3).east(), RRBlocks.forcefield.get().defaultBlockState().setValue(BlockForceField.FACING, meta));
						hits++;
					}
				}
				level++;
			}
		}
		return (power - (hits * 16));
	}

	public void placeBlockCarefully(Level world, int x, int y, int z, Block block) {
        BlockPos pos = new BlockPos(x, y, z);
        BlockState state = world.getBlockState(pos);
        if (!state.is(RRBlocks.reactive) &&
            !state.is(RRBlocks.fshield ) &&
            !state.is(RRBlocks.omegaobj) &&
            !state.is(RRBlocks.sigmaobj)) {
            world.setBlockAndUpdate(pos, block.defaultBlockState());
		}
	}
}
