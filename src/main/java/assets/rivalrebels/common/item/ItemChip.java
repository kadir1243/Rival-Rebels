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
package assets.rivalrebels.common.item;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.List;

public class ItemChip extends Item
{
	public ItemChip()
	{
		super();
		maxStackSize = 1;
		setCreativeTab(RivalRebels.rralltab);
	}

	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int count, boolean flag) {
		if (!item.hasTagCompound()) item.setTagCompound(new NBTTagCompound());
		if (RivalRebels.round.isStarted() && !item.getTagCompound().getBoolean("isReady") && entity instanceof EntityPlayer player)
		{
            item.getTagCompound().setString("username", player.getName());
			item.getTagCompound().setInteger("team", RivalRebels.round.rrplayerlist.getForName(player.getName()).rrteam.ordinal());
			item.getTagCompound().setBoolean("isReady", true);
		}
	}

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		player.swingItem();
		if (!world.isRemote)
		{
			if (world.getBlockState(pos).getBlock() == RivalRebels.buildrhodes)
			{
				world.setBlockState(pos.west(), RivalRebels.steel.getDefaultState());
				world.setBlockState(pos.east(), RivalRebels.steel.getDefaultState());
				world.setBlockState(pos.up(), RivalRebels.conduit.getDefaultState());
				world.setBlockState(pos.up().west(), RivalRebels.steel.getDefaultState());
				world.setBlockState(pos.up().east(), RivalRebels.steel.getDefaultState());
				world.setBlockState(pos.up(2), RivalRebels.steel.getDefaultState());
				world.setBlockState(pos.up(2).west(), RivalRebels.steel.getDefaultState());
				world.setBlockState(pos.up(2).east(), RivalRebels.steel.getDefaultState());
				if (world.getBlockState(pos.down()).getBlock() == RivalRebels.buildrhodes)
				{
					world.setBlockState(pos, RivalRebels.conduit.getDefaultState());
					world.setBlockState(pos.down(), RivalRebels.rhodesactivator.getDefaultState());
					world.setBlockState(pos.down().west(), RivalRebels.steel.getDefaultState());
					world.setBlockState(pos.down().east(), RivalRebels.steel.getDefaultState());
				}
				else
				{
					world.setBlockState(pos, RivalRebels.rhodesactivator.getDefaultState());
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List<String> list, boolean par4)
	{
		if (item.hasTagCompound())
		{
			list.add(RivalRebelsTeam.getForID(item.getTagCompound().getInteger("team")).name());
			list.add(item.getTagCompound().getString("username"));
		}
	}

	@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:bd");
	}
}
