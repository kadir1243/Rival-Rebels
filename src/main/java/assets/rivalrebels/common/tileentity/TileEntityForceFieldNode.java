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
import assets.rivalrebels.common.block.machine.BlockForceField;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class TileEntityForceFieldNode extends TileEntityMachineBase
{
	public String			username	= null;
	public RivalRebelsTeam	rrteam		= null;
	public int				level		= 0;
    private IBlockState blockState;

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

	public void turnOff() {
		if (level > 0) {
			level--;
			for (int y = 0; y < 7; y++)
			{
                switch (getState().getValue(BlockForceField.SHAPE)) {
                    case 2:
                        if (worldObj.getBlockState(pos.add(0, y - 3, -level - 1)).getBlock() == RivalRebels.forcefield) {
                            worldObj.setBlockToAir(pos.add(0, y - 3, -level - 1));
                        }
                        break;
                    case 3:
                        if (worldObj.getBlockState(pos.add(0, y - 3, level + 1)).getBlock() == RivalRebels.forcefield) {
                            worldObj.setBlockToAir(pos.add(0, y - 3, level + 1));
                        }
                        break;
                    case 4:
                        if (worldObj.getBlockState(pos.add(-level - 1, y - 3, 0)).getBlock() == RivalRebels.forcefield) {
                            worldObj.setBlockToAir(pos.add(-level - 1, y - 3, 0));
                        }
                        break;
                    case 5:
                        if (worldObj.getBlockState(pos.add(level + 1, y - 3, 0)).getBlock() == RivalRebels.forcefield) {
                            worldObj.setBlockToAir(pos.add(level + 1, y - 3, 0));
                        }
                        break;
                }
			}
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		float t = 0.0625f;
		float l = 35f;
		float h = 3.5f;
        int xCoord = getPos().getX();
        int yCoord = getPos().getY();
        int zCoord = getPos().getZ();
        switch (getState().getValue(BlockForceField.SHAPE)) {
            case 2:
                return new AxisAlignedBB(xCoord + 0.5f - t, yCoord + 0.5f - h, zCoord - l, xCoord + 0.5f + t, yCoord + 0.5f + h, zCoord);
            case 3:
                return new AxisAlignedBB(xCoord + 0.5f - t, yCoord + 0.5f - h, zCoord + 1f, xCoord + 0.5f + t, yCoord + 0.5f + h, zCoord + 1f + l);
            case 4:
                return new AxisAlignedBB(xCoord - l, yCoord + 0.5f - h, zCoord + 0.5f - t, xCoord, yCoord + 0.5f + h, zCoord + 0.5f + t);
            case 5:
                return new AxisAlignedBB(xCoord + 1f, yCoord + 0.5f - h, zCoord + 0.5f - t, xCoord + 1f + l, yCoord + 0.5f + h, zCoord + 0.5f + t);
            default:
                return new AxisAlignedBB(getPos(), getPos());
        }
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
		if (rrteam == null) username = par1NBTTagCompound.getString("username");
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		if (rrteam != null) par1NBTTagCompound.setInteger("rrteam", rrteam.ordinal());
		if (username != null) par1NBTTagCompound.setString("username", username);
	}

    public IBlockState getState() {
        if (blockState == null && worldObj != null) {
            blockState = worldObj.getBlockState(pos);
        }

        return blockState;
    }

    @Override
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        blockState = null;
    }

    @Override
	public float powered(float power, float distance)
	{
		float hits = (float) Math.random();
        IBlockState state = getState();
        int shape = state.getValue(BlockForceField.SHAPE);

        double randomness = 0.1;
		float thickness = 0.5f;
		float length = 35f;
		float height = 3.52f;
		double speed = 2;

        AxisAlignedBB aabb;
        List<Entity> list;
        switch (shape) {
            case 2:
                aabb = new AxisAlignedBB(pos.add(0.5F - thickness, 0.5F - height, -length), pos.add(0.5F + thickness, 0.5F + height, 0));
                list = worldObj.getEntitiesWithinAABBExcludingEntity(null, aabb);
                for (Entity e : list) {
                    boolean shouldContinue = true;
                    if (e instanceof EntityPlayer) {
                        EntityPlayer p = (EntityPlayer) e;
                        RivalRebelsPlayer player = RivalRebels.round.rrplayerlist.getForName(p.getName());
                        if (p.getName().equals(username) || (player != null && player.rrteam == rrteam)) {
                            shouldContinue = false;
                            hits++;
                            p.setPositionAndUpdate(p.posX + (p.posX > (this.pos.getX() + 0.5) ? -2 : 2), p.posY, p.posZ);
                        }
                    }
                    if (shouldContinue && e != null) {
                        double cpx = this.pos.getX() + 0.5f - e.posX;
                        double cpy = e.posY + e.getEyeHeight() - e.posY;
                        double cpz = e.posZ - e.posZ;

                        double dist = Math.sqrt(cpx * cpx + cpy * cpy + cpz * cpz) / speed;

                        cpx /= dist;
                        cpy /= dist;
                        cpz /= dist;

                        cpx += worldObj.rand.nextGaussian() * randomness;
                        cpy += worldObj.rand.nextGaussian() * randomness;
                        cpz += worldObj.rand.nextGaussian() * randomness;

                        e.motionX = -cpx - e.motionX;
                        e.motionY = -cpy - e.motionY;
                        e.motionZ = -cpz - e.motionZ;
                        RivalRebelsSoundPlayer.playSound(e, 10, 7, 1, 2f);
                        if (!e.getEntityBoundingBox().equals(new AxisAlignedBB(0D, 0D, 0D, 0D, 0D, 0D)))
                            hits += e.getEntityBoundingBox().getAverageEdgeLength();
                    }
                }
                if (level < length) {
                    placeBlockCarefully(worldObj, pos.add(0, 0, -length - 1), RivalRebels.reactive);
                    for (int y = 0; y < 7; y++) {
                        if (worldObj.getBlockState(pos.add(0, y - 3, -level - 1)).getBlock() != RivalRebels.forcefield) {
                            worldObj.setBlockState(pos.add(0, y - 3, -level - 1), state);
                            hits++;
                        }
                    }
                    level++;
                }
                break;
            case 3:
                aabb = new AxisAlignedBB(this.pos.getX() + 0.5f - thickness, this.pos.getY() + 0.5f - height, this.pos.getZ() + 1f, this.pos.getX() + 0.5f + thickness, this.pos.getY() + 0.5f + height, this.pos.getZ() + 1f + length);
                list = worldObj.getEntitiesWithinAABBExcludingEntity(null, aabb);
                for (Entity e : list) {
                    boolean shouldContinue = true;
                    if (e instanceof EntityPlayer) {
                        EntityPlayer p = (EntityPlayer) e;
                        RivalRebelsPlayer player = RivalRebels.round.rrplayerlist.getForName(p.getName());
                        if (p.getName().equals(username) || (player != null && player.rrteam == rrteam)) {
                            shouldContinue = false;
                            hits++;
                            p.setPositionAndUpdate(p.posX + (p.posX > (this.pos.getX() + 0.5) ? -2 : 2), p.posY, p.posZ);
                        }
                    }
                    if (shouldContinue && e != null) {
                        double cpx = this.pos.getX() + 0.5f - e.posX;
                        double cpy = e.posY + e.getEyeHeight() - e.posY;
                        double cpz = e.posZ - e.posZ;

                        double dist = Math.sqrt(cpx * cpx + cpy * cpy + cpz * cpz) / speed;

                        cpx /= dist;
                        cpy /= dist;
                        cpz /= dist;

                        cpx += worldObj.rand.nextGaussian() * randomness;
                        cpy += worldObj.rand.nextGaussian() * randomness;
                        cpz += worldObj.rand.nextGaussian() * randomness;

                        e.motionX = -cpx - e.motionX;
                        e.motionY = -cpy - e.motionY;
                        e.motionZ = -cpz - e.motionZ;
                        RivalRebelsSoundPlayer.playSound(e, 10, 7, 1, 2f);
                        if (!e.getEntityBoundingBox().equals(new AxisAlignedBB(0D, 0D, 0D, 0D, 0D, 0D)))
                            hits += e.getEntityBoundingBox().getAverageEdgeLength();
                    }
                }
                if (level < length) {
                    placeBlockCarefully(worldObj, this.pos.getX(), this.pos.getY(), (int) (this.pos.getZ() + length + 1), RivalRebels.reactive);
                    for (int y = 0; y < 7; y++) {
                        if (worldObj.getBlockState(this.pos.add(0, y - 3, level + 1)).getBlock() != RivalRebels.forcefield) {
                            worldObj.setBlockState(this.pos.add(0, y - 3, level + 1), state);
                            hits++;
                        }
                    }
                    level++;
                }
                break;
            case 4:
                aabb = new AxisAlignedBB(this.pos.getX() - length, this.pos.getY() + 0.5f - height, this.pos.getZ() + 0.5f - thickness, this.pos.getX(), this.pos.getY() + 0.5f + height, this.pos.getZ() + 0.5f + thickness);
                list = worldObj.getEntitiesWithinAABBExcludingEntity(null, aabb);
                for (Entity e : list) {
                    boolean shouldContinue = true;
                    if (e instanceof EntityPlayer) {
                        EntityPlayer p = (EntityPlayer) e;
                        RivalRebelsPlayer player = RivalRebels.round.rrplayerlist.getForName(p.getName());
                        if (p.getName().equals(username) || (player != null && player.rrteam == rrteam)) {
                            shouldContinue = false;
                            hits++;
                            p.setPositionAndUpdate(p.posX, p.posY, p.posZ + (p.posZ > (this.pos.getZ() + 0.5) ? -2 : 2));
                        }
                    }
                    if (shouldContinue && e != null) {
                        double cpx = e.posX - e.posX;
                        double cpy = e.posY + e.getEyeHeight() - e.posY;
                        double cpz = this.pos.getZ() + 0.5f - e.posZ;

                        double dist = Math.sqrt(cpx * cpx + cpy * cpy + cpz * cpz) / speed;

                        cpx /= dist;
                        cpy /= dist;
                        cpz /= dist;

                        cpx += worldObj.rand.nextGaussian() * randomness;
                        cpy += worldObj.rand.nextGaussian() * randomness;
                        cpz += worldObj.rand.nextGaussian() * randomness;

                        e.motionX = -cpx - e.motionX;
                        e.motionY = -cpy - e.motionY;
                        e.motionZ = -cpz - e.motionZ;
                        RivalRebelsSoundPlayer.playSound(e, 10, 7, 1, 2f);
                        if (!e.getEntityBoundingBox().equals(new AxisAlignedBB(0D, 0D, 0D, 0D, 0D, 0D)))
                            hits += e.getEntityBoundingBox().getAverageEdgeLength();
                    }
                }
                if (level < length) {
                    placeBlockCarefully(worldObj, pos.add(-length - 1, 0, 0), RivalRebels.reactive);
                    for (int y = 0; y < 7; y++) {
                        if (worldObj.getBlockState(pos.add(-level - 1, y - 3, 0)).getBlock() != RivalRebels.forcefield) {
                            worldObj.setBlockState(pos.add(-level - 1, y - 3, 0), state);
                            hits++;
                        }
                    }
                    level++;
                }
                break;
            case 5:
                aabb = new AxisAlignedBB(this.pos.getX() + 1f, this.pos.getY() + 0.5f - height, this.pos.getZ() + 0.5f - thickness, this.pos.getX() + 1f + length, this.pos.getY() + 0.5f + height, this.pos.getZ() + 0.5f + thickness);
                list = worldObj.getEntitiesWithinAABBExcludingEntity(null, aabb);
                for (Entity e : list) {
                    boolean shouldContinue = true;
                    if (e instanceof EntityPlayer) {
                        EntityPlayer p = (EntityPlayer) e;
                        RivalRebelsPlayer player = RivalRebels.round.rrplayerlist.getForName(p.getName());
                        if (p.getName().equals(username) || (player != null && player.rrteam == rrteam)) {
                            shouldContinue = false;
                            hits++;
                            p.setPositionAndUpdate(p.posX, p.posY, p.posZ + (p.posZ > (this.pos.getZ() + 0.5) ? -2 : 2));
                        }
                    }
                    if (shouldContinue && e != null) {
                        double cpx = e.posX - e.posX;
                        double cpy = e.posY + e.getEyeHeight() - e.posY;
                        double cpz = this.pos.getZ() + 0.5f - e.posZ;

                        double dist = Math.sqrt(cpx * cpx + cpy * cpy + cpz * cpz) / speed;

                        cpx /= dist;
                        cpy /= dist;
                        cpz /= dist;

                        cpx += worldObj.rand.nextGaussian() * randomness;
                        cpy += worldObj.rand.nextGaussian() * randomness;
                        cpz += worldObj.rand.nextGaussian() * randomness;

                        e.motionX = -cpx - e.motionX;
                        e.motionY = -cpy - e.motionY;
                        e.motionZ = -cpz - e.motionZ;
                        RivalRebelsSoundPlayer.playSound(e, 10, 7, 1, 2f);
                        if (!e.getEntityBoundingBox().equals(new AxisAlignedBB(0D, 0D, 0D, 0D, 0D, 0D)))
                            hits += e.getEntityBoundingBox().getAverageEdgeLength();
                    }
                }
                if (level < length) {
                    placeBlockCarefully(worldObj, (int) (this.pos.getX() + length + 1), this.pos.getY(), this.pos.getZ(), RivalRebels.reactive);
                    for (int y = 0; y < 7; y++) {
                        if (worldObj.getBlockState(this.pos.add(level + 1, y - 3, 0)).getBlock() != RivalRebels.forcefield) {
                            worldObj.setBlockState(this.pos.add(level + 1, y - 3, 0), state);
                            hits++;
                        }
                    }
                    level++;
                }
                break;
        }
		return (power - (hits * 16));
	}

	public void placeBlockCarefully(World world, int i, int j, int z, Block blockID) {
        BlockPos pos = new BlockPos(i, j, z);
        if (world.getBlockState(pos).getBlock() != RivalRebels.reactive && world.getBlockState(pos).getBlock() != RivalRebels.fshield && world.getBlockState(pos).getBlock() != RivalRebels.omegaobj && world.getBlockState(pos).getBlock() != RivalRebels.sigmaobj) {
			world.setBlockState(pos, blockID.getDefaultState());
		}
	}

    public void placeBlockCarefully(World world, BlockPos pos, Block blockID) {
        if (world.getBlockState(pos).getBlock() != RivalRebels.reactive && world.getBlockState(pos).getBlock() != RivalRebels.fshield && world.getBlockState(pos).getBlock() != RivalRebels.omegaobj && world.getBlockState(pos).getBlock() != RivalRebels.sigmaobj) {
            world.setBlockState(pos, blockID.getDefaultState());
        }
    }
}
