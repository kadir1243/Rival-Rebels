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
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemChip extends Item
{
	public ItemChip()
	{
		super();
		setMaxStackSize(1);
		setCreativeTab(RivalRebels.rralltab);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int count, boolean flag)
	{
		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		if (RivalRebels.round.isStarted() && !stack.getTagCompound().getBoolean("isReady") && entity instanceof EntityPlayer player)
		{
            stack.getTagCompound().setString("username", player.getName());
			stack.getTagCompound().setInteger("team", RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile()).rrteam.ordinal());
			stack.getTagCompound().setBoolean("isReady", true);
		}
	}

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		player.swingArm(hand);
		if (!world.isRemote)
		{
			if (world.getBlockState(pos).getBlock() == RivalRebels.buildrhodes)
			{
				world.setBlockState(pos.west(), RivalRebels.steel.getDefaultState());
				world.setBlockState(pos.east(), RivalRebels.steel.getDefaultState());
				world.setBlockState(pos.up(), RivalRebels.conduit.getDefaultState());
				world.setBlockState(pos.west().up(), RivalRebels.steel.getDefaultState());
				world.setBlockState(pos.east().up(), RivalRebels.steel.getDefaultState());
				world.setBlockState(pos.up(2), RivalRebels.steel.getDefaultState());
				world.setBlockState(pos.west().up(2), RivalRebels.steel.getDefaultState());
				world.setBlockState(pos.east().up(2), RivalRebels.steel.getDefaultState());
				if (world.getBlockState(pos.down()).getBlock() == RivalRebels.buildrhodes)
				{
					world.setBlockState(pos, RivalRebels.conduit.getDefaultState());
					world.setBlockState(pos.down(), RivalRebels.rhodesactivator.getDefaultState());
					world.setBlockState(pos.west().down(), RivalRebels.steel.getDefaultState());
					world.setBlockState(pos.east().down(), RivalRebels.steel.getDefaultState());
				}
				else
				{
					world.setBlockState(pos, RivalRebels.rhodesactivator.getDefaultState());
				}
				return EnumActionResult.SUCCESS;
			}
		}
		return EnumActionResult.PASS;
	}

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.hasTagCompound())
		{
			tooltip.add(RivalRebelsTeam.getForID(stack.getTagCompound().getInteger("team")).name());
			tooltip.add(stack.getTagCompound().getString("username"));
		}
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:bd");
	}*/
}
