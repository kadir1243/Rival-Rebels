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
import assets.rivalrebels.common.block.trap.BlockRemoteCharge;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemRemote extends Item {
    private BlockPos RCpos;

	public ItemRemote()
	{
		super();
		setMaxStackSize(1);
		setCreativeTab(RivalRebels.rralltab);
	}

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (player.world.getBlockState(RCpos.up()).getBlock() == RivalRebels.remotecharge && player.isSneaking())
		{
			player.swingArm(hand);
			RivalRebelsSoundPlayer.playSound(player, 22, 3);
			BlockRemoteCharge.explode(world, RCpos.up());
		}
		return super.onItemRightClick(world, player, hand);
	}

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		if (((player.capabilities.isCreativeMode && world.isAirBlock(pos.up()) || player.inventory.hasItemStack(Item.getItemFromBlock(RivalRebels.remotecharge).getDefaultInstance()) && world.isAirBlock(pos.up()))) && !player.isSneaking())
		{
			RivalRebelsSoundPlayer.playSound(player, 22, 2);
			player.swingArm(hand);
			if (!world.isRemote) {
				player.sendMessage(new TextComponentString("§7[§4Orders§7] §cShift-click (Sneak) to detonate."));
			}
            RCpos = pos;
			player.inventory.deleteStack(Item.getItemFromBlock(RivalRebels.remotecharge).getDefaultInstance());
			world.setBlockState(pos.up(), RivalRebels.remotecharge.getDefaultState());
        }
        return EnumActionResult.FAIL;
    }

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:am");
	}*/
}
