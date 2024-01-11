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
package assets.rivalrebels.common.item.weapon;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityBomb;
import assets.rivalrebels.common.entity.EntityRocket;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.HashSet;

public class ItemRPG extends ItemTool
{
	public ItemRPG()
	{
		super(1, 1, ToolMaterial.DIAMOND, new HashSet<>());
		setMaxStackSize(1);
		setCreativeTab(RivalRebels.rralltab);
	}

	@Override
	public boolean isFull3D()
	{
		return true;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 144;
	}

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        ItemStack itemStack = ItemUtil.getItemStack(player, RivalRebels.rocket);
        if (player.capabilities.isCreativeMode || !itemStack.isEmpty() || RivalRebels.infiniteAmmo)
		{
			player.setActiveHand(hand);
			if (!world.isRemote && !player.capabilities.isCreativeMode && !RivalRebels.infiniteAmmo)
			{
                itemStack.shrink(1);
                if (itemStack.isEmpty())
                    player.inventory.deleteStack(itemStack);
			}
			if (!stack.isItemEnchanted()) RivalRebelsSoundPlayer.playSound(player, 23, 2, 0.4f);
			else RivalRebelsSoundPlayer.playSound(player, 10, 4, 1.0f);
			if (!world.isRemote)
			{
				if (!stack.isItemEnchanted()) world.spawnEntity(new EntityRocket(world, player, 0.1F));
				else world.spawnEntity(new EntityBomb(world, player, 0.1F));
			}
		}
		else if (!world.isRemote)
		{
			player.sendMessage(new TextComponentString("§cOut of ammunition"));
		}
		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:aq");
	}*/
}
