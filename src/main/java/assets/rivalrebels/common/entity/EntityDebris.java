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
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityDebris extends EntityInanimate {
    public IBlockState state;
	public int				ticksExisted;
    public NBTTagCompound	tileEntityData;

	public EntityDebris(World w)
	{
		super(w);
	}

	public EntityDebris(World w, int x, int y, int z)
	{
		super(w);
        state = w.getBlockState(new BlockPos(x, y, z));
		w.setBlockToAir(new BlockPos(x, y, z));
		setSize(1F, 1F);
		setPosition(x + 0.5f, y + 0.5f, z + 0.5f);
		prevPosX = x + 0.5f;
		prevPosY = y + 0.5f;
		prevPosZ = z + 0.5f;
	}

    @Override
    public double getYOffset() {
        return 0.5F;
    }

    public EntityDebris(World w, double x, double y, double z, double mx, double my, double mz, Block b)
	{
		super(w);
		state = b.getDefaultState();
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
	public void onUpdate()
	{
		if (ticksExisted == 0 && !world.isRemote) PacketDispatcher.packetsys.sendToAll(new EntityDebrisPacket(this));
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

		if (!world.isRemote && world.getBlockState(new BlockPos(MathHelper.floor(posX), MathHelper.floor(posY), MathHelper.floor(posZ))).isOpaqueCube()) die(prevPosX, prevPosY, prevPosZ);
	}

	public void die(double X, double Y, double Z)
	{
		int x = MathHelper.floor(X);
		int y = MathHelper.floor(Y);
		int z = MathHelper.floor(Z);
		setDead();
		world.setBlockState(new BlockPos(x, y, z), state);
		//if (block instanceof BlockFalling) ((BlockFalling) block).playSoundWhenFallen(world, x, y, z, metadata);
		if (tileEntityData != null && state.getBlock() instanceof ITileEntityProvider)
		{
			TileEntity tileentity = world.getTileEntity(new BlockPos(x, y, z));
			if (tileentity != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				tileentity.writeToNBT(nbttagcompound);
                for (String s : tileEntityData.getKeySet()) {
                    NBTBase nbtbase = tileEntityData.getTag(s);
                    if (!s.equals("x") && !s.equals("y") && !s.equals("z")) {
                        nbttagcompound.setTag(s, nbtbase.copy());
                    }
                }
				tileentity.readFromNBT(nbttagcompound);
				tileentity.markDirty();
			}
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{
        nbt.setString("Block", Block.REGISTRY.getNameForObject(state.getBlock()).toString());
		nbt.setInteger("Data", state.getBlock().getMetaFromState(state));
		nbt.setByte("Time", (byte) ticksExisted);
		if (tileEntityData != null) nbt.setTag("TileEntityData", tileEntityData);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt)
	{
        Block block = Block.getBlockFromName(nbt.getString("Block"));
		int metadata = nbt.getInteger("Data");
		state = block.getStateFromMeta(metadata);
        ticksExisted = nbt.getByte("Time") & 255;
		if (nbt.hasKey("TileEntityData", 10)) tileEntityData = nbt.getCompoundTag("TileEntityData");
	}

	@Override
	public void addEntityCrashInfo(CrashReportCategory crash)
	{
		super.addEntityCrashInfo(crash);
        if (state != null) {
            Block block = state.getBlock();
            crash.addCrashSection("Immitating block ID", Block.getIdFromBlock(block));
            crash.addCrashSection("Immitating block data", block.getMetaFromState(this.state));
        }
	}
}
