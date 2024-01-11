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
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.UUID;

public class TileEntityForceFieldNode extends TileEntityMachineBase
{
    public UUID uuid;
	public RivalRebelsTeam	rrteam		= null;
	public int				level		= 0;

	public TileEntityForceFieldNode()
	{
		pInM = 345;
		pInR = 345;
	}

	@Override
	public void update()
	{
		if (pInR > 0) pInR = powered(pInR, edist);
		else turnOff();
		pInR -= decay;
	}

	public void turnOff()
	{
		if (level > 0)
		{
			int meta = this.getBlockMetadata();

			level--;
			for (int y = 0; y < 7; y++)
			{
				switch (meta)
				{
					case 2:
						if (world.getBlockState(new BlockPos(getPos().getX(), getPos().getY() + (y - 3), getPos().getZ() - level - 1)).getBlock() == RivalRebels.forcefield)
						{
							world.setBlockToAir(new BlockPos(getPos().getX(), getPos().getY() + (y - 3), getPos().getZ() - level - 1));
						}
					break;

					case 3:
						if (world.getBlockState(getPos().add(0, y, level).down(3).south()).getBlock() == RivalRebels.forcefield)
						{
							world.setBlockToAir(getPos().add(0, y, level).down(3).south());
						}
					break;

					case 4:
						if (world.getBlockState(getPos().add(-level, y, 0).down(3).west()).getBlock() == RivalRebels.forcefield)
						{
							world.setBlockToAir(getPos().add(-level, y, 0).down(3).west());
						}
					break;

					case 5:
						if (world.getBlockState(getPos().add(level, y, 0).down(3).east()).getBlock() == RivalRebels.forcefield)
						{
							world.setBlockToAir(getPos().add(level, y, 0).down(3).east());
						}
					break;
				}
			}
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		float t = 0.0625f;
		float l = 35f;
		float h = 3.5f;
        return switch (this.getBlockMetadata()) {
            case 2 ->
                    new AxisAlignedBB(getPos().getX() + 0.5f - t, getPos().getY() + 0.5f - h, getPos().getZ() - l, getPos().getX() + 0.5f + t, getPos().getY() + 0.5f + h, getPos().getZ());
            case 3 ->
                    new AxisAlignedBB(getPos().getX() + 0.5f - t, getPos().getY() + 0.5f - h, getPos().getZ() + 1f, getPos().getX() + 0.5f + t, getPos().getY() + 0.5f + h, getPos().getZ() + 1f + l);
            case 4 ->
                    new AxisAlignedBB(getPos().getX() - l, getPos().getY() + 0.5f - h, getPos().getZ() + 0.5f - t, getPos().getX(), getPos().getY() + 0.5f + h, getPos().getZ() + 0.5f + t);
            case 5 ->
                    new AxisAlignedBB(getPos().getX() + 1f, getPos().getY() + 0.5f - h, getPos().getZ() + 0.5f - t, getPos().getX() + 1f + l, getPos().getY() + 0.5f + h, getPos().getZ() + 0.5f + t);
            default -> new AxisAlignedBB(getPos(), getPos());
        };
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 16384.0D;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		rrteam = RivalRebelsTeam.getForID(par1NBTTagCompound.getInteger("rrteam"));
		if (rrteam == RivalRebelsTeam.NONE) rrteam = null;
		if (rrteam == null) uuid = par1NBTTagCompound.getUniqueId("uuid");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		if (rrteam != null) par1NBTTagCompound.setInteger("rrteam", rrteam.ordinal());
		if (uuid != null) par1NBTTagCompound.setUniqueId("uuid", uuid);
        return par1NBTTagCompound;
    }

	@Override
	public float powered(float power, float distance)
	{
		float hits = world.rand.nextFloat();
		int meta = this.getBlockMetadata();

		double randomness = 0.1;
		float thickness = 0.5f;
		float length = 35f;
		float height = 3.52f;
		double speed = 2;

		if (meta == 2)
		{
			AxisAlignedBB aabb = new AxisAlignedBB(getPos().getX() + 0.5f - thickness, getPos().getY() + 0.5f - height, getPos().getZ() - length, getPos().getX() + 0.5f + thickness, getPos().getY() + 0.5f + height, getPos().getZ());
			List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, aabb);
            for (Entity e : list) {
                boolean shouldContinue = true;
                if (e instanceof EntityPlayer p) {
                    RivalRebelsPlayer player = RivalRebels.round.rrplayerlist.getForGameProfile(p.getGameProfile());
                    if (p.getGameProfile().getId().equals(uuid) || (player != null && player.rrteam == rrteam)) {
                        shouldContinue = false;
                        hits++;
                        p.setPositionAndUpdate(p.posX + (p.posX > (getPos().getX() + 0.5) ? -2 : 2), p.posY, p.posZ);
                    }
                }
                if (shouldContinue && e != null) {
                    double cpx = getPos().getX() + 0.5f - e.posX;
                    double cpy = e.posY + e.getEyeHeight() - e.posY;
                    double cpz = e.posZ - e.posZ;

                    double dist = Math.sqrt(cpx * cpx + cpy * cpy + cpz * cpz) / speed;

                    cpx /= dist;
                    cpy /= dist;
                    cpz /= dist;

                    cpx += world.rand.nextGaussian() * randomness;
                    cpy += world.rand.nextGaussian() * randomness;
                    cpz += world.rand.nextGaussian() * randomness;

                    e.motionX = -cpx - e.motionX;
                    e.motionY = -cpy - e.motionY;
                    e.motionZ = -cpz - e.motionZ;
                    RivalRebelsSoundPlayer.playSound(e, 10, 7, 1, 2f);
                    if (e.getEntityBoundingBox() != null) hits += e.getEntityBoundingBox().getAverageEdgeLength();
                }
            }
			if (level < length)
			{
				placeBlockCarefully(world, getPos().getX(), getPos().getY(), (int) (getPos().getZ() - length - 1), RivalRebels.reactive);
				for (int y = 0; y < 7; y++)
				{
					if (world.getBlockState(new BlockPos(getPos().getX(), getPos().getY() + (y - 3), getPos().getZ() - level - 1)) != RivalRebels.forcefield)
					{
						world.setBlockState(new BlockPos(getPos().getX(), getPos().getY() + (y - 3), getPos().getZ() - level - 1), RivalRebels.forcefield.getStateFromMeta(meta));
						hits++;
					}
				}
				level++;
			}
		}

		if (meta == 3)
		{
			AxisAlignedBB aabb = new AxisAlignedBB(getPos().getX() + 0.5f - thickness, getPos().getY() + 0.5f - height, getPos().getZ() + 1f, getPos().getX() + 0.5f + thickness, getPos().getY() + 0.5f + height, getPos().getZ() + 1f + length);
			List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, aabb);
            for (Entity e : list) {
                boolean shouldContinue = true;
                if (e instanceof EntityPlayer && e != null) {
                    EntityPlayer p = (EntityPlayer) e;
                    RivalRebelsPlayer player = RivalRebels.round.rrplayerlist.getForGameProfile(p.getGameProfile());
                    if (p.getGameProfile().getId().equals(uuid) || (player != null && player.rrteam == rrteam)) {
                        shouldContinue = false;
                        hits++;
                        p.setPositionAndUpdate(p.posX + (p.posX > (getPos().getX() + 0.5) ? -2 : 2), p.posY, p.posZ);
                    }
                }
                if (shouldContinue && e != null) {
                    double cpx = getPos().getX() + 0.5f - e.posX;
                    double cpy = e.posY + e.getEyeHeight() - e.posY;
                    double cpz = e.posZ - e.posZ;

                    double dist = Math.sqrt(cpx * cpx + cpy * cpy + cpz * cpz) / speed;

                    cpx /= dist;
                    cpy /= dist;
                    cpz /= dist;

                    cpx += world.rand.nextGaussian() * randomness;
                    cpy += world.rand.nextGaussian() * randomness;
                    cpz += world.rand.nextGaussian() * randomness;

                    e.motionX = -cpx - e.motionX;
                    e.motionY = -cpy - e.motionY;
                    e.motionZ = -cpz - e.motionZ;
                    RivalRebelsSoundPlayer.playSound(e, 10, 7, 1, 2f);
                    if (e.getEntityBoundingBox() != null) hits += e.getEntityBoundingBox().getAverageEdgeLength();
                }
            }
			if (level < length)
			{
				placeBlockCarefully(world, getPos().getX(), getPos().getY(), (int) (getPos().getZ() + length + 1), RivalRebels.reactive);
				for (int y = 0; y < 7; y++)
				{
					if (world.getBlockState(getPos().add(0, y, level).down(3).south()).getBlock() != RivalRebels.forcefield)
					{
						world.setBlockState(getPos().add(0, y, level).down(3).south(), RivalRebels.forcefield.getStateFromMeta(meta));
						hits++;
					}
				}
				level++;
			}
		}

		if (meta == 4)
		{
			AxisAlignedBB aabb = new AxisAlignedBB(getPos().getX() - length, getPos().getY() + 0.5f - height, getPos().getZ() + 0.5f - thickness, getPos().getX(), getPos().getY() + 0.5f + height, getPos().getZ() + 0.5f + thickness);
			List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, aabb);
            for (Entity e : list) {
                boolean shouldContinue = true;
                if (e instanceof EntityPlayer p) {
                    RivalRebelsPlayer player = RivalRebels.round.rrplayerlist.getForGameProfile(p.getGameProfile());
                    if (p.getGameProfile().getId().equals(uuid) || (player != null && player.rrteam == rrteam)) {
                        shouldContinue = false;
                        hits++;
                        p.setPositionAndUpdate(p.posX, p.posY, p.posZ + (p.posZ > (getPos().getZ() + 0.5) ? -2 : 2));
                    }
                }
                if (shouldContinue && e != null) {
                    double cpx = e.posX - e.posX;
                    double cpy = e.posY + e.getEyeHeight() - e.posY;
                    double cpz = getPos().getZ() + 0.5f - e.posZ;

                    double dist = Math.sqrt(cpx * cpx + cpy * cpy + cpz * cpz) / speed;

                    cpx /= dist;
                    cpy /= dist;
                    cpz /= dist;

                    cpx += world.rand.nextGaussian() * randomness;
                    cpy += world.rand.nextGaussian() * randomness;
                    cpz += world.rand.nextGaussian() * randomness;

                    e.motionX = -cpx - e.motionX;
                    e.motionY = -cpy - e.motionY;
                    e.motionZ = -cpz - e.motionZ;
                    RivalRebelsSoundPlayer.playSound(e, 10, 7, 1, 2f);
                    if (e.getEntityBoundingBox() != null) hits += e.getEntityBoundingBox().getAverageEdgeLength();
                }
            }
			if (level < length)
			{
				placeBlockCarefully(world, (int) (getPos().getX() - length - 1), getPos().getY(), getPos().getZ(), RivalRebels.reactive);
				for (int y = 0; y < 7; y++)
				{
					if (world.getBlockState(getPos().add(-level, y, 0).down(3).west()).getBlock() != RivalRebels.forcefield)
					{
						world.setBlockState(getPos().add(-level, y, 0).down(3).west(), RivalRebels.forcefield.getStateFromMeta(meta));
						hits++;
					}
				}
				level++;
			}
		}

		if (meta == 5)
		{
			AxisAlignedBB aabb = new AxisAlignedBB(getPos().getX() + 1f, getPos().getY() + 0.5f - height, getPos().getZ() + 0.5f - thickness, getPos().getX() + 1f + length, getPos().getY() + 0.5f + height, getPos().getZ() + 0.5f + thickness);
			List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, aabb);
            for (Entity e : list) {
                boolean shouldContinue = true;
                if (e instanceof EntityPlayer p) {
                    RivalRebelsPlayer player = RivalRebels.round.rrplayerlist.getForGameProfile(p.getGameProfile());
                    if (p.getGameProfile().getId().equals(uuid) || (player != null && player.rrteam == rrteam)) {
                        shouldContinue = false;
                        hits++;
                        p.setPositionAndUpdate(p.posX, p.posY, p.posZ + (p.posZ > (getPos().getZ() + 0.5) ? -2 : 2));
                    }
                }
                if (shouldContinue && e != null) {
                    double cpx = e.posX - e.posX;
                    double cpy = e.posY + e.getEyeHeight() - e.posY;
                    double cpz = getPos().getZ() + 0.5f - e.posZ;

                    double dist = Math.sqrt(cpx * cpx + cpy * cpy + cpz * cpz) / speed;

                    cpx /= dist;
                    cpy /= dist;
                    cpz /= dist;

                    cpx += world.rand.nextGaussian() * randomness;
                    cpy += world.rand.nextGaussian() * randomness;
                    cpz += world.rand.nextGaussian() * randomness;

                    e.motionX = -cpx - e.motionX;
                    e.motionY = -cpy - e.motionY;
                    e.motionZ = -cpz - e.motionZ;
                    RivalRebelsSoundPlayer.playSound(e, 10, 7, 1, 2f);
                    if (e.getEntityBoundingBox() != null) hits += e.getEntityBoundingBox().getAverageEdgeLength();
                }
            }
			if (level < length)
			{
				placeBlockCarefully(world, (int) (getPos().getX() + length + 1), getPos().getY(), getPos().getZ(), RivalRebels.reactive);
				for (int y = 0; y < 7; y++)
				{
					if (world.getBlockState(getPos().add(level, y, 0).down(3).east()).getBlock() != RivalRebels.forcefield)
					{
						world.setBlockState(getPos().add(level, y, 0).down(3).east(), RivalRebels.forcefield.getStateFromMeta(meta));
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
        if (world.getBlockState(pos).getBlock() != RivalRebels.reactive &&
            world.getBlockState(pos).getBlock() != RivalRebels.fshield &&
            world.getBlockState(pos).getBlock() != RivalRebels.omegaobj &&
            world.getBlockState(pos).getBlock() != RivalRebels.sigmaobj)
		{
			world.setBlockState(pos, block.getDefaultState());
		}
	}
}
