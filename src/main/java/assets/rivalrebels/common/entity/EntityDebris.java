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
package assets.rivalrebels.common.entity;

import assets.rivalrebels.common.packet.EntityDebrisPacket;
import assets.rivalrebels.common.packet.PacketDispatcher;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class EntityDebris extends EntityInanimate
{
    public IBlockState blockState;
    public int ticksExisted;
    public NBTTagCompound tileEntityData;

	public EntityDebris(World w)
	{
		super(w);
	}

	public EntityDebris(World w, int x, int y, int z)
	{
		super(w);
        blockState = w.getBlockState(new BlockPos(x, y, z));
		w.setBlockToAir(new BlockPos(x, y, z));
		setSize(1F, 1F);
		setPosition(x + 0.5f, y + 0.5f, z + 0.5f);
		prevPosX = x + 0.5f;
		prevPosY = y + 0.5f;
		prevPosZ = z + 0.5f;
	}
	public EntityDebris(World w, double x, double y, double z, double mx, double my, double mz, Block b)
	{
		super(w);
        blockState = b.getDefaultState();
		setSize(1F, 1F);
		setPosition(x, y, z);
		prevPosX = x;
		prevPosY = y;
		prevPosZ = z;
		motionX = mx;
		motionY = my;
		motionZ = mz;
	}

    @Override
    public double getYOffset() {
        return 0.5D;
    }

    @Override
	public void onUpdate()
	{
		if (ticksExisted == 0 && FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) PacketDispatcher.packetsys.sendToAll(new EntityDebrisPacket(this));
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		++ticksExisted;
		motionY -= 0.04;
		motionX *= 0.98;
		motionY *= 0.98;
		motionZ *= 0.98;
		posX += motionX;
		posY += motionY;
		posZ += motionZ;

		if (!worldObj.isRemote && worldObj.getBlockState(getPosition()).getBlock().isOpaqueCube()) die(prevPosX, prevPosY, prevPosZ);
	}

	public void die(double x, double y, double z)
	{
        BlockPos pos = new BlockPos(x, y, z);
		setDead();
		worldObj.setBlockState(pos, blockState);
		if (blockState.getBlock() instanceof BlockFalling) ((BlockFalling) blockState.getBlock()).onEndFalling(worldObj, pos);
		if (tileEntityData != null && blockState.getBlock() instanceof ITileEntityProvider) {
			TileEntity tileentity = worldObj.getTileEntity(pos);
			if (tileentity != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				tileentity.writeToNBT(nbttagcompound);
                for (String key : tileEntityData.getKeySet()) {
                    NBTBase tag = tileEntityData.getTag(key);
                    if (!key.equals("x") && !key.equals("y") && !key.equals("z")) {
                        nbttagcompound.setTag(key, tag.copy());
                    }
                }
				tileentity.readFromNBT(nbttagcompound);
				tileentity.markDirty();
			}
		}
	}

	@Override
    public void writeEntityToNBT(NBTTagCompound nbt)
	{
        nbt.setString("Block", Block.blockRegistry.getNameForObject(blockState.getBlock()).toString());
		nbt.setByte("Data", (byte) blockState.getBlock().getMetaFromState(blockState));
		nbt.setByte("Time", (byte) ticksExisted);
		if (tileEntityData != null) nbt.setTag("TileEntityData", tileEntityData);
	}

	@Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        int metaData = nbt.getByte("Data") & 255;
        blockState = Block.getBlockFromName(nbt.getString("Block")).getStateFromMeta(metaData);
		ticksExisted = nbt.getByte("Time") & 255;
		if (nbt.hasKey("TileEntityData", 10)) tileEntityData = nbt.getCompoundTag("TileEntityData");
	}

	@Override
	public void addEntityCrashInfo(CrashReportCategory crash)
	{
		super.addEntityCrashInfo(crash);
		crash.addCrashSection("Immitating block ID", Block.getIdFromBlock(blockState.getBlock()));
		crash.addCrashSection("Immitating block data", blockState.getBlock().getMetaFromState(blockState));
	}
}
