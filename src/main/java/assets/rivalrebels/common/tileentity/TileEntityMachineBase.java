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

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;

public abstract class TileEntityMachineBase extends TileEntity implements ITickable
{
	public float	pInM		= 0;
	public float	pInR		= 0;
	public float	edist		= 0;
	public float	decay		= 0;
	public float	powerGiven	= 0;
    public BlockPos rpos = BlockPos.ORIGIN;

	@Override
	public void update() {
		if (pInR > 0) pInR = powered(pInR, edist);
		pInR -= decay;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
        rpos = BlockPos.fromLong(nbt.getLong("rpos"));
		edist = nbt.getFloat("edist");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
        nbt.setLong("rpos", rpos.toLong());
		nbt.setFloat("edist", edist);
	}

	@Override
	public void invalidate()
	{
		super.invalidate();
		TileEntity connectedTo = worldObj.getTileEntity(rpos);
		if (connectedTo instanceof TileEntityReactor) ((TileEntityReactor)connectedTo).machines.remove(this);
	}

	abstract public float powered(float power, float distance);
}
