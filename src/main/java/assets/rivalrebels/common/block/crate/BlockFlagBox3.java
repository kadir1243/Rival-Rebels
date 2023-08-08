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
package assets.rivalrebels.common.block.crate;

import assets.rivalrebels.RivalRebels;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFlagBox3 extends Block
{
	public BlockFlagBox3()
	{
		super(Material.wood);
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        blockActivated(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn);
    }

    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player)
	{
		if (player.isSneaking() && !world.isRemote)
		{
			EntityItem ei = new EntityItem(world, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.flag3, 10));
			world.spawnEntityInWorld(ei);
			world.setBlockToAir(new BlockPos(x, y, z));
			return false;
		}
		if (!player.isSneaking() && !world.isRemote)
		{
			player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("RivalRebels.Orders") + " " + StatCollector.translateToLocal("RivalRebels.sneak")));
			world.setBlockState(new BlockPos(x, y, z), RivalRebels.flagbox4.getDefaultState());
			return false;
		}
		return false;
	}
}
