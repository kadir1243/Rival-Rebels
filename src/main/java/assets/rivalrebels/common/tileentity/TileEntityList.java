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
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.function.Predicate;

public class TileEntityList extends HashSet<TileEntity> {

    public TileEntityList() {
        super();
	}

	public int getFFSize() {
		int result = 0;
        for (TileEntity tileEntity : this) {
            if (tileEntity != null && !(tileEntity instanceof TileEntityReactive)) result++;
        }
		return result;
	}

    public TileEntity get(int i) {
        return toArray(new TileEntity[0])[i];
    }

    public int get(TileEntity tile) {
		for (int i = 0; i < size(); i++) {
			if (toArray()[i].equals(tile)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public String toString() {
		StringBuilder strB = new StringBuilder();
		strB.append("tileentities:\n");
        this.forEach(tileEntity -> {
            strB.append(tileEntity.getPos().getX()).append(", ").append(tileEntity.getPos().getY()).append(", ").append(tileEntity.getPos().getZ()).append(", ").append(tileEntity);
            strB.append(",\n");
        });
		return strB.toString();
	}

    public void remove(int i) {
        remove(get(i));
    }

    public void writeOnlyPositionsWithInclude(NBTTagCompound nbt, Predicate<TileEntity> predicate) {
        nbt.setInteger("machine_size", size());
        for (int i = 0; i < this.size(); i++) {
            TileEntity tileEntity = this.get(i);
            if (!predicate.test(tileEntity)) {
                nbt.setBoolean("machine_" + i + "_skipped", true);
                continue;
            }
            nbt.setBoolean("machine_" + i + "_skipped", false);
            if (tileEntity == null) {
                nbt.setBoolean("machine_" + i + "_null", true);
                continue;
            }
            nbt.setBoolean("machine_" + i + "_null", false);
            nbt.setLong("machine_" + i + "_pos", tileEntity.getPos().toLong());
        }
    }

    public static TileEntityList readWithOnlyPositions(World world, NBTTagCompound nbt) {
        TileEntityList list = new TileEntityList();
        int machineSize = nbt.getInteger("machine_size");
        for (int i = 0; i < machineSize; i++) {
            boolean skipped = nbt.getBoolean("machine_" + i + "_skipped");
            if (skipped) continue;
            boolean isNullMachine = nbt.getBoolean("machine_" + i + "_null");
            if (isNullMachine) {
                list.add(null);
                continue;
            }
            BlockPos pos = BlockPos.fromLong(nbt.getLong("machine_" + i + "_pos"));
            list.add(world.getTileEntity(pos));
        }
        return list;
    }
}
