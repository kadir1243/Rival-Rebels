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
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemRemote extends Item {
    private BlockPos remoteChargePos;

	public ItemRemote()
	{
		super();
		maxStackSize = 1;
		setCreativeTab(RivalRebels.rralltab);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
	{
		if (player.worldObj.getBlockState(remoteChargePos.up()).getBlock() == RivalRebels.remotecharge && player.isSneaking())
		{
			player.swingItem();
			RivalRebelsSoundPlayer.playSound(player, 22, 3);
			BlockRemoteCharge.explode(world, remoteChargePos.getX(), remoteChargePos.getY() + 1, remoteChargePos.getZ());
		}
		return item;
	}

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (((player.capabilities.isCreativeMode && world.isAirBlock(pos.up()) || player.inventory.hasItem(Item.getItemFromBlock(RivalRebels.remotecharge)) && world.isAirBlock(pos.up()))) && !player.isSneaking()) {
			RivalRebelsSoundPlayer.playSound(player, 22, 2);
			player.swingItem();
			if (!world.isRemote)
			{
				player.addChatMessage(new ChatComponentText("§7[§4Orders§7] §cShift-click (Sneak) to detonate."));
			}
            remoteChargePos = pos;
			player.inventory.consumeInventoryItem(Item.getItemFromBlock(RivalRebels.remotecharge));
			world.setBlockState(pos.up(), RivalRebels.remotecharge.getDefaultState());
        }
        return false;
    }

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:am");
	}*/
}
