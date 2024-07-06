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

import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.block.autobuilds.BlockRhodesScaffold;
import assets.rivalrebels.common.entity.EntityRhodes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityRhodesActivator extends TileEntityMachineBase {
	private int charge = 0;
	public TileEntityRhodesActivator(BlockPos pos, BlockState state) {
        super(RRTileEntities.RHODES_ACTIVATOR, pos, state);
        pInM = 40;
		pInR = 20;
        EntityRhodes.BLOCK_ENTITIES.put(pos, this);
	}

	@Override
	public float powered(float power, float distance)
	{
		if (!level.isClientSide())
		{
			if (charge == 100)
			{
				// all 4 main charge points are valid
				boolean buildrhodes = true;
				boolean buildrhodes1 = true;
				boolean buildrhodes2 = true;
                BlockPos pos = getBlockPos();
                int x = pos.getX();
				int y = pos.getY();
				int z = pos.getZ();
				for (int i = 0; i < 31*9; i++)
				{
					byte b = BlockRhodesScaffold.binimg[i];
					int fy = 2-(i/9);
					int fx1 = -10+(i%9);
					int fx2 = 7-(i%9);
					Block s1 = level.getBlockState(pos.offset(fx1, fy, 0)).getBlock();
					Block s2 = level.getBlockState(pos.offset(fx2, fy, 0)).getBlock();
					if (b == 1 && (s1 != RRBlocks.conduit || s2 != RRBlocks.conduit))
					{
						buildrhodes = false;
						break;
					}
					if (b == 2 && (s1 != RRBlocks.rhodesactivator || s2 != RRBlocks.rhodesactivator))
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
					Block s1 = level.getBlockState(pos.offset(fx1, fy, 0)).getBlock();
					Block s2 = level.getBlockState(pos.offset(fx2, fy, 0)).getBlock();
					if (b == 1 && (s1 != RRBlocks.conduit || s2 != RRBlocks.conduit))
					{
						buildrhodes1 = false;
						break;
					}
					if (b == 2 && (s1 != RRBlocks.rhodesactivator || s2 != RRBlocks.rhodesactivator))
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
					Block s1 = level.getBlockState(pos.offset(fx1, fy, 0)).getBlock();
					Block s2 = level.getBlockState(pos.offset(fx2, fy, 0)).getBlock();
					if (b == 1 && (s1 != RRBlocks.conduit || s2 != RRBlocks.conduit))
					{
						buildrhodes2 = false;
						break;
					}
					if (b == 2 && (s1 != RRBlocks.rhodesactivator || s2 != RRBlocks.rhodesactivator))
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
							setBlockToAir(pos.offset(fx1, fy, 0));
							setBlockToAir(pos.offset(fx2, fy, 0));
						}
					}
					EntityRhodes er = new EntityRhodes(level, x-1f, y-13, z, 1);
					if (getBlockPos().getZ() > this.worldPosition.getZ()) er.bodyyaw = 180;
					level.addFreshEntity(er);
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
							setBlockToAir(pos.offset(fx1, fy, 0));
							setBlockToAir(pos.offset(fx2, fy, 0));
							setBlockToAir(pos.offset(fx1, fy, 0).east());
							setBlockToAir(pos.offset(fx2, fy, 0).east());
							setBlockToAir(pos.offset(fx1, fy, 0).above());
							setBlockToAir(pos.offset(fx2, fy, 0).above());
							setBlockToAir(pos.offset(fx1, fy, 0).east().above());
							setBlockToAir(pos.offset(fx2, fy, 0).east().above());
						}
					}
					EntityRhodes er = new EntityRhodes(level, x-2f, y-26, z, 2);
					if (getBlockPos().getZ() > this.worldPosition.getZ()) er.bodyyaw = 180;
					level.addFreshEntity(er);
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
							setBlockToAir(pos.offset(fx1, fy, 0));
							setBlockToAir(pos.offset(fx2, fy, 0));
							setBlockToAir(pos.offset(fx1, fy, 0).east());
							setBlockToAir(pos.offset(fx2, fy, 0).east());
							setBlockToAir(pos.offset(fx1, fy, 0).east(2));
							setBlockToAir(pos.offset(fx2, fy, 0).east(2));
							setBlockToAir(pos.offset(fx1, fy, 0).above());
							setBlockToAir(pos.offset(fx2, fy, 0).above());
							setBlockToAir(pos.offset(fx1, fy, 0).east().above());
							setBlockToAir(pos.offset(fx2, fy, 0).east().above());
							setBlockToAir(pos.offset(fx1, fy, 0).east(2).above());
							setBlockToAir(pos.offset(fx2, fy, 0).east(2).above());
							setBlockToAir(pos.offset(fx1, fy, 0).above(2));
							setBlockToAir(pos.offset(fx2, fy, 0).above(2));
							setBlockToAir(pos.offset(fx1, fy, 0).east().above(2));
							setBlockToAir(pos.offset(fx2, fy, 0).east().above(2));
							setBlockToAir(pos.offset(fx1, fy, 0).east(2).above(2));
							setBlockToAir(pos.offset(fx2, fy, 0).east(2).above(2));
						}
					}
					EntityRhodes er = new EntityRhodes(level, x-3f, y-39, z, 3);
					if (getBlockPos().getZ() > this.worldPosition.getZ()) er.bodyyaw = 180;
					level.addFreshEntity(er);
				}
				else if (level.getBlockState(pos.above()).is(RRBlocks.conduit)
					&& level.getBlockState(pos.west()).is(RRBlocks.steel)
					&& level.getBlockState(pos.east()).is(RRBlocks.steel)
					&& level.getBlockState(pos.west().above()).is(RRBlocks.steel)
					&& level.getBlockState(pos.east().above()).is(RRBlocks.steel)
					&& level.getBlockState(pos.west().above(3)).is(RRBlocks.steel)
					&& level.getBlockState(pos.east().above(3)).is(RRBlocks.steel)
					&& level.getBlockState(pos.above(3)).is(RRBlocks.steel)
					&& level.getBlockState(pos.above(2)).is(RRBlocks.conduit)
					&& level.getBlockState(pos.west().above(2)).is(RRBlocks.steel)
					&& level.getBlockState(pos.east().above(2)).is(RRBlocks.steel))
				{
					setBlockToAir(pos.above());
					setBlockToAir(pos.above(2));
					setBlockToAir(pos.above(3));
					EntityRhodes er = new EntityRhodes(level, x+0.5f, y+2.5f, z+0.5f, 0.0666666666666f);
					er.wakeX = x;
					er.wakeY = y;
					er.wakeZ = z;
					if (getBlockPos().getZ() > this.worldPosition.getZ()) er.bodyyaw = 180;
					level.addFreshEntity(er);
				}
				else if (level.getBlockState(pos.above()).is(RRBlocks.conduit)
					&& level.getBlockState(pos.west()).is(RRBlocks.steel)
					&& level.getBlockState(pos.east()).is(RRBlocks.steel)
					&& level.getBlockState(pos.west().above()).is(RRBlocks.steel)
					&& level.getBlockState(pos.east().above()).is(RRBlocks.steel)
					&& level.getBlockState(pos.west().above(2)).is(RRBlocks.steel)
					&& level.getBlockState(pos.east().above(2)).is(RRBlocks.steel)
					&& level.getBlockState(pos.above(2)).is(RRBlocks.steel))
				{
					setBlockToAir(pos.above());
					setBlockToAir(pos.above(2));
					EntityRhodes er = new EntityRhodes(level, x+0.5f, y+1.5f, z+0.5f, 0.0333333333333f);
					er.wakeX = x;
					er.wakeY = y;
					er.wakeZ = z;
					if (getBlockPos().getZ() > this.worldPosition.getZ()) er.bodyyaw = 180;
					level.addFreshEntity(er);
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

    private void setBlockToAir(BlockPos pos) {
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        EntityRhodes.BLOCK_ENTITIES.remove(getBlockPos());
    }
}
