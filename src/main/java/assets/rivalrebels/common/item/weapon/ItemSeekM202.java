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
import assets.rivalrebels.common.entity.EntitySeekB83;
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

public class ItemSeekM202 extends ItemTool
{
	public ItemSeekM202()
	{
		super(1, 1, ToolMaterial.DIAMOND, new HashSet<>());
		setMaxStackSize(1);
		setCreativeTab(RivalRebels.rralltab);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.BOW;
	}

	@Override
	public boolean isFull3D()
	{
		return true;
	}

	/**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 90;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        ItemStack itemStack = ItemUtil.getItemStack(player, RivalRebels.rocket);
        if (player.capabilities.isCreativeMode || !itemStack.isEmpty() || RivalRebels.infiniteAmmo)
		{
			player.setActiveHand(hand);
			if (!player.capabilities.isCreativeMode && !RivalRebels.infiniteAmmo)
			{
                itemStack.shrink(1);
				if (itemStack.isEmpty())
                    player.inventory.deleteStack(itemStack);
			}
			RivalRebelsSoundPlayer.playSound(player, 23, 2, 0.4f);
			if (!world.isRemote)
			{
				if (!stack.isItemEnchanted())
				{
					world.spawnEntity(new EntitySeekB83(world, player, 0.1F));
				}
				else
				{
					world.spawnEntity(new EntitySeekB83(world, player, 0.1F, 15.0f));
					world.spawnEntity(new EntitySeekB83(world, player, 0.1F, 7.5f));
					world.spawnEntity(new EntitySeekB83(world, player, 0.1F));
					world.spawnEntity(new EntitySeekB83(world, player, 0.1F, -7.5f));
					world.spawnEntity(new EntitySeekB83(world, player, 0.1F, -15.0f));
				}
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
		itemIcon = iconregister.registerIcon("RivalRebels:bh");
	}*/
}
