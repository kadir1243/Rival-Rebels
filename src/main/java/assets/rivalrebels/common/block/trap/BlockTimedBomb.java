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
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
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
    public void onExplosionDestroy(World world, BlockPos pos, net.minecraft.world.Explosion explosion) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        world.setBlockToAir(pos);
		new Explosion(world, x + 0.5f, y + 0.5f, z + 0.5f, RivalRebels.timedbombExplodeSize, false, true, RivalRebelsDamageSource.timebomb);
		RivalRebelsSoundPlayer.playSound(world, 26, 0, x + 0.5f, y + 0.5f, z + 0.5f, 2f, 0.3f);
	}

    @Override
    public void onPlayerDestroy(World world, BlockPos pos, IBlockState state) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
		world.setBlockToAir(pos);
		new Explosion(world, x + 0.5f, y + 0.5f, z + 0.5f, RivalRebels.timedbombExplodeSize, false, true, RivalRebelsDamageSource.timebomb);
		RivalRebelsSoundPlayer.playSound(world, 26, 0, x + 0.5f, y + 0.5f, z + 0.5f, 2f, 0.3f);
	}

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        ticksSincePlaced = 0;
        world.scheduleBlockUpdate(pos, this, 8, 0);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
		world.scheduleBlockUpdate(pos, this, 8, 0);
		ticksSincePlaced += 1;
		if (ticksSincePlaced >= RivalRebels.timedbombTimer * 2.5)
		{
			world.setBlockToAir(pos);
			new Explosion(world, x + 0.5f, y + 0.5f, z + 0.5f, RivalRebels.timedbombExplodeSize, false, true, RivalRebelsDamageSource.timebomb);
			RivalRebelsSoundPlayer.playSound(world, 26, 0, x + 0.5f, y + 0.5f, z + 0.5f, 2f, 0.3f);
		}
		if (ticksSincePlaced == 100)
		{
			ticksSincePlaced = 0;
		}
		if (world.getBlockState(pos.up()).getBlock() == RivalRebels.light && ticksSincePlaced <= 93)
		{
			world.setBlockToAir(pos.up());
			world.playSound(x + 0.5, y + 0.5, z + 0.5, SoundEvents.UI_BUTTON_CLICK, SoundCategory.BLOCKS, 1, 1, true);
		}
		else
		{
			if (ticksSincePlaced <= 93)
			{
				world.setBlockState(pos.up(), RivalRebels.light.getDefaultState());
				world.playSound(x + 0.5, y + 0.5, z + 0.5, SoundEvents.UI_BUTTON_CLICK, SoundCategory.BLOCKS, 1, 0.7F, true);
			}
			else
			{
				world.playSound(x + 0.5, y + 0.5, z + 0.5, SoundEvents.UI_BUTTON_CLICK, SoundCategory.BLOCKS, 2F, 2F, true);
			}
		}
	}

	/*@SideOnly(Side.CLIENT)
	IIcon	icon1;
	@SideOnly(Side.CLIENT)
	IIcon	icon2;
	@SideOnly(Side.CLIENT)
	IIcon	icon3;
	@SideOnly(Side.CLIENT)
	IIcon	icon4;
	@SideOnly(Side.CLIENT)
	IIcon	icon5;
	@SideOnly(Side.CLIENT)
	IIcon	icon6;

	@SideOnly(Side.CLIENT)
	@Override
	public final IIcon getIcon(int side, int meta)
	{
		if (side == 0) return icon1;
		if (side == 1) return icon2;
		if (side == 2) return icon3;
		if (side == 3) return icon4;
		if (side == 4) return icon5;
		if (side == 5) return icon6;
		return icon1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister)
	{
		icon1 = iconregister.registerIcon("RivalRebels:ac"); // BOTTOM
		icon2 = iconregister.registerIcon("RivalRebels:ae"); // TOP
		icon3 = iconregister.registerIcon("RivalRebels:ac"); // SIDE N
		icon4 = iconregister.registerIcon("RivalRebels:ac"); // SIDE S
		icon5 = iconregister.registerIcon("RivalRebels:ab"); // SIDE W
		icon6 = iconregister.registerIcon("RivalRebels:ab"); // SIDE E
	}*/

	/*public void onFinishFalling(World par1World, int par2, int par3, int par4, int par5)
	{
		par1World.setBlock(par2, par3, par4, Blocks.AIR);
		new Explosion(par1World, par2 + 0.5f, par3 + 0.5f, par4 + 0.5f, RivalRebels.timedbombExplodeSize, false, true, RivalRebelsDamageSource.timebomb);
		RivalRebelsSoundPlayer.playSound(par1World, 26, 0, par2 + 0.5f, par3 + 0.5f, par4 + 0.5f, 2f, 0.3f);
	}*/
}
