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
import assets.rivalrebels.common.block.autobuilds.BlockRhodesScaffold;
import assets.rivalrebels.common.entity.EntityRhodes;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class TileEntityRhodesActivator extends TileEntityMachineBase
{
	int charge = 0;
	public TileEntityRhodesActivator()
	{
		pInM = 40;
		pInR = 20;
	}

	@Override
	public float powered(float power, float distance)
	{
		if (!world.isRemote)
		{
			if (charge == 100)
			{
				// all 4 main charge points are valid
				boolean buildrhodes = true;
				boolean buildrhodes1 = true;
				boolean buildrhodes2 = true;
                BlockPos pos = getPos();
                int x = pos.getX();
				int y = pos.getY();
				int z = pos.getZ();
				for (int i = 0; i < 31*9; i++)
				{
					byte b = BlockRhodesScaffold.binimg[i];
					int fy = 2-(i/9);
					int fx1 = -10+(i%9);
					int fx2 = 7-(i%9);
					Block s1 = world.getBlockState(pos.add(fx1, fy, 0)).getBlock();
					Block s2 = world.getBlockState(pos.add(fx2, fy, 0)).getBlock();
					if (b == 1 && (s1 != RivalRebels.conduit || s2 != RivalRebels.conduit))
					{
						buildrhodes = false;
						break;
					}
					if (b == 2 && (s1 != RivalRebels.rhodesactivator || s2 != RivalRebels.rhodesactivator))
					{
						buildrhodes = false;
						break;
					}
				}
				if (!buildrhodes)
				for (int i = 0; i < 31*9; i++)
				{
					byte b = BlockRhodesScaffold.binimg[i];
					int fy = 2-(i/9);
					int fx1 = -10+(i%9);
					int fx2 = 7 -(i%9);
					fy *= 2;
					fx1 *= 2;
					fx2 *= 2;
					Block s1 = world.getBlockState(pos.add(fx1, fy, 0)).getBlock();
					Block s2 = world.getBlockState(pos.add(fx2, fy, 0)).getBlock();
					if (b == 1 && (s1 != RivalRebels.conduit || s2 != RivalRebels.conduit))
					{
						buildrhodes1 = false;
						break;
					}
					if (b == 2 && (s1 != RivalRebels.rhodesactivator || s2 != RivalRebels.rhodesactivator))
					{
						buildrhodes1 = false;
						break;
					}
				}
				if (!buildrhodes && !buildrhodes1)
				for (int i = 0; i < 31*9; i++)
				{
					byte b = BlockRhodesScaffold.binimg[i];
					int fy = 2-(i/9);
					int fx1 = -10+(i%9);
					int fx2 = 7 -(i%9);
					fy *= 3;
					fx1 *= 3;
					fx2 *= 3;
					Block s1 = world.getBlockState(pos.add(fx1, fy, 0)).getBlock();
					Block s2 = world.getBlockState(pos.add(fx2, fy, 0)).getBlock();
					if (b == 1 && (s1 != RivalRebels.conduit || s2 != RivalRebels.conduit))
					{
						buildrhodes2 = false;
						break;
					}
					if (b == 2 && (s1 != RivalRebels.rhodesactivator || s2 != RivalRebels.rhodesactivator))
					{
						buildrhodes2 = false;
						break;
					}
				}
				if (buildrhodes)
				{
					for (int i = 0; i < 31*9; i++)
					{
						byte b = BlockRhodesScaffold.binimg[i];
						if (b == 1)
						{
							int fy = 2-(i/9);
							int fx1 = -10+(i%9);
							int fx2 = 7 -(i%9);
							world.setBlockToAir(pos.add(fx1, fy, 0));
							world.setBlockToAir(pos.add(fx2, fy, 0));
						}
					}
					EntityRhodes er = new EntityRhodes(world, x-1f, y-13, z, 1);
					if (getPos().getZ() > this.pos.getZ()) er.bodyyaw = 180;
					world.spawnEntity(er);
				}
				else if (buildrhodes1)
				{
					for (int i = 0; i < 31*9; i++)
					{
						byte b = BlockRhodesScaffold.binimg[i];
						if (b == 1)
						{
							int fy = 2-(i/9);
							int fx1 = -10+(i%9);
							int fx2 = 7 -(i%9);
							fy *= 2;
							fx1 *= 2;
							fx2 *= 2;
							world.setBlockToAir(pos.add(fx1, fy, 0));
							world.setBlockToAir(pos.add(fx2, fy, 0));
							world.setBlockToAir(pos.add(fx1, fy, 0).east());
							world.setBlockToAir(pos.add(fx2, fy, 0).east());
							world.setBlockToAir(pos.add(fx1, fy, 0).up());
							world.setBlockToAir(pos.add(fx2, fy, 0).up());
							world.setBlockToAir(pos.add(fx1, fy, 0).east().up());
							world.setBlockToAir(pos.add(fx2, fy, 0).east().up());
						}
					}
					EntityRhodes er = new EntityRhodes(world, x-2f, y-26, z, 2);
					if (getPos().getZ() > this.pos.getZ()) er.bodyyaw = 180;
					world.spawnEntity(er);
				}
				else if (buildrhodes2)
				{
					for (int i = 0; i < 31*9; i++)
					{
						byte b = BlockRhodesScaffold.binimg[i];
						if (b == 1)
						{
							int fy = 2-(i/9);
							int fx1 = -10+(i%9);
							int fx2 = 7 -(i%9);
							fy *= 3;
							fx1 *= 3;
							fx2 *= 3;
							world.setBlockToAir(pos.add(fx1, fy, 0));
							world.setBlockToAir(pos.add(fx2, fy, 0));
							world.setBlockToAir(pos.add(fx1, fy, 0).east());
							world.setBlockToAir(pos.add(fx2, fy, 0).east());
							world.setBlockToAir(pos.add(fx1, fy, 0).east(2));
							world.setBlockToAir(pos.add(fx2, fy, 0).east(2));
							world.setBlockToAir(pos.add(fx1, fy, 0).up());
							world.setBlockToAir(pos.add(fx2, fy, 0).up());
							world.setBlockToAir(pos.add(fx1, fy, 0).east().up());
							world.setBlockToAir(pos.add(fx2, fy, 0).east().up());
							world.setBlockToAir(pos.add(fx1, fy, 0).east(2).up());
							world.setBlockToAir(pos.add(fx2, fy, 0).east(2).up());
							world.setBlockToAir(pos.add(fx1, fy, 0).up(2));
							world.setBlockToAir(pos.add(fx2, fy, 0).up(2));
							world.setBlockToAir(pos.add(fx1, fy, 0).east().up(2));
							world.setBlockToAir(pos.add(fx2, fy, 0).east().up(2));
							world.setBlockToAir(pos.add(fx1, fy, 0).east(2).up(2));
							world.setBlockToAir(pos.add(fx2, fy, 0).east(2).up(2));
						}
					}
					EntityRhodes er = new EntityRhodes(world, x-3f, y-39, z, 3);
					if (getPos().getZ() > this.pos.getZ()) er.bodyyaw = 180;
					world.spawnEntity(er);
				}
				else if (world.getBlockState(pos.up()).getBlock() == RivalRebels.conduit
					&& world.getBlockState(pos.west()).getBlock() == RivalRebels.steel
					&& world.getBlockState(pos.east()).getBlock() == RivalRebels.steel
					&& world.getBlockState(pos.west().up()).getBlock() == RivalRebels.steel
					&& world.getBlockState(pos.east().up()).getBlock() == RivalRebels.steel
					&& world.getBlockState(pos.west().up(3)).getBlock() == RivalRebels.steel
					&& world.getBlockState(pos.east().up(3)).getBlock() == RivalRebels.steel
					&& world.getBlockState(pos.up(3)).getBlock() == RivalRebels.steel
					&& world.getBlockState(pos.up(2)).getBlock() == RivalRebels.conduit
					&& world.getBlockState(pos.west().up(2)).getBlock() == RivalRebels.steel
					&& world.getBlockState(pos.east().up(2)).getBlock() == RivalRebels.steel)
				{
					world.setBlockToAir(pos.up());
					world.setBlockToAir(pos.up(2));
					world.setBlockToAir(pos.up(3));
					EntityRhodes er = new EntityRhodes(world, x+0.5f, y+2.5f, z+0.5f, 0.0666666666666f);
					er.wakeX = x;
					er.wakeY = y;
					er.wakeZ = z;
					if (getPos().getZ() > this.pos.getZ()) er.bodyyaw = 180;
					world.spawnEntity(er);
				}
				else if (world.getBlockState(pos.up()).getBlock() == RivalRebels.conduit
					&& world.getBlockState(pos.west()).getBlock() == RivalRebels.steel
					&& world.getBlockState(pos.east()).getBlock() == RivalRebels.steel
					&& world.getBlockState(pos.west().up()).getBlock() == RivalRebels.steel
					&& world.getBlockState(pos.east().up()).getBlock() == RivalRebels.steel
					&& world.getBlockState(pos.west().up(2)) == RivalRebels.steel
					&& world.getBlockState(pos.east().up(2)) == RivalRebels.steel
					&& world.getBlockState(pos.up(2)).getBlock() == RivalRebels.steel)
				{
					world.setBlockToAir(pos.up());
					world.setBlockToAir(pos.up(2));
					EntityRhodes er = new EntityRhodes(world, x+0.5f, y+1.5f, z+0.5f, 0.0333333333333f);
					er.wakeX = x;
					er.wakeY = y;
					er.wakeZ = z;
					if (getPos().getZ() > this.pos.getZ()) er.bodyyaw = 180;
					world.spawnEntity(er);
				}
				return power*0.5f;
			}
			else
			{
				charge++;
				return 0;
			}
		}
		return power;
	}
}
