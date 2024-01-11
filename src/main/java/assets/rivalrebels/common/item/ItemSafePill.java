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
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemSafePill extends Item
{
	public ItemSafePill()
	{
		super();
		setMaxStackSize(6);
		setCreativeTab(RivalRebels.rralltab);
	}

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		player.setActiveHand(hand);
		if (!world.isRemote)
		{
			player.sendMessage(new TextComponentString("§7[§6Status§7]§e Regenerating..."));
			RivalRebelsSoundPlayer.playSound(player, 15, 1);
			RivalRebelsSoundPlayer.playSound(player, 28, 18);
			world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_MAGMACUBE_JUMP, SoundCategory.PLAYERS, 1.0F, 1.0F);
			world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_GHAST_SCREAM, SoundCategory.PLAYERS, 1.0F, 1.0F);
			player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 10, 20));
			player.getFoodStats().addStats(10, 200);
			player.heal(10);
            ItemStack stack = player.getHeldItem(hand);
            stack.shrink(1);
            if (stack.isEmpty()) stack = ItemStack.EMPTY;
            player.setHeldItem(hand, stack);
		}
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.EAT;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 32;
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:ak");
	}*/
}
