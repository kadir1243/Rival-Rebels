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
package assets.rivalrebels.common.block.trap;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.explosion.Explosion;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTimedBomb extends BlockFalling
{
	int	ticksSincePlaced;

	public BlockTimedBomb()
	{
		super();
		ticksSincePlaced = 0;
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

    @Override
    public void onBlockDestroyedByExplosion(World world, BlockPos pos, net.minecraft.world.Explosion explosionIn) {
        world.setBlockToAir(pos);
        new Explosion(world, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, RivalRebels.timedbombExplodeSize, false, true, RivalRebelsDamageSource.timebomb);
        RivalRebelsSoundPlayer.playSound(world, 26, 0, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, 2f, 0.3f);
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state) {
		world.setBlockToAir(pos);
		new Explosion(world, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, RivalRebels.timedbombExplodeSize, false, true, RivalRebelsDamageSource.timebomb);
		RivalRebelsSoundPlayer.playSound(world, 26, 0, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, 2f, 0.3f);
	}

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		ticksSincePlaced = 0;
		worldIn.scheduleBlockUpdate(pos, this, 8, 1);
	}

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		world.scheduleBlockUpdate(pos, this, 8, 1);
		ticksSincePlaced += 1;
		if (ticksSincePlaced >= RivalRebels.timedbombTimer * 2.5)
		{
			world.setBlockToAir(pos);
			new Explosion(world, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, RivalRebels.timedbombExplodeSize, false, true, RivalRebelsDamageSource.timebomb);
			RivalRebelsSoundPlayer.playSound(world, 26, 0, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, 2f, 0.3f);
		}
		if (ticksSincePlaced == 100)
		{
			ticksSincePlaced = 0;
		}
		if (world.getBlockState(pos.up()).getBlock() == RivalRebels.light && ticksSincePlaced <= 93) {
			world.setBlockToAir(pos.up());
			world.playSoundEffect(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, "random.click", 1, 1);
		}
		else
		{
			if (ticksSincePlaced <= 93)
			{
				world.setBlockState(pos.up(), RivalRebels.light.getDefaultState());
				world.playSoundEffect(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, "random.click", 1, 0.7F);
			}
			else
			{
				world.playSoundEffect(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, "random.click", 2F, 2F);
			}
		}
	}

    @Override
    public void onEndFalling(World world, BlockPos pos) {
        world.setBlockToAir(pos);
        new Explosion(world, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, RivalRebels.timedbombExplodeSize, false, true, RivalRebelsDamageSource.timebomb);
        RivalRebelsSoundPlayer.playSound(world, 26, 0, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, 2f, 0.3f);
    }
}
