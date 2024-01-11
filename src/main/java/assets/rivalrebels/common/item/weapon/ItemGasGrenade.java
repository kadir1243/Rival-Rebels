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
import assets.rivalrebels.common.entity.EntityGasGrenade;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.world.World;

public class ItemGasGrenade extends Item
{
	public ItemGasGrenade()
	{
		super();
		setMaxStackSize(6);
		setCreativeTab(RivalRebels.rralltab);
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.BOW;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 75;
	}

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft) {
        EntityPlayer player;
        if (entityLiving instanceof EntityPlayer) player = (EntityPlayer) entityLiving;
        else return;

        ItemStack itemStack = ItemUtil.getItemStack(player, RivalRebels.gasgrenade);
        if (player.capabilities.isCreativeMode || !itemStack.isEmpty() || RivalRebels.infiniteGrenades)
		{
			float f = (getMaxItemUseDuration(stack) - timeLeft) / 20.0F;
			f = (f * f + f * 2) * 0.3333f;
			if (f > 1.0F) f = 1.0F;
			EntityGasGrenade entitysuperarrow = new EntityGasGrenade(world, player, 0.3f + f * 0.5f);
			if (!player.capabilities.isCreativeMode)
			{
                itemStack.shrink(1);
                if (itemStack.isEmpty())
                    player.inventory.deleteStack(itemStack);
			}
			RivalRebelsSoundPlayer.playSound(player, 4, 3, 1, 0.9f);
			if (!world.isRemote)
			{
				world.spawnEntity(entitysuperarrow);
				entitysuperarrow.setPosition(entitysuperarrow.posX, entitysuperarrow.posY - 0.05, entitysuperarrow.posZ);
			}
		}
	}

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		player.setActiveHand(hand);
		world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_SLIME_ATTACK, SoundCategory.PLAYERS, 1.0F, 1.0F);
		return super.onItemRightClick(world, player, hand);
	}

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase entityLiving, int count) {
		int time = 75 - count;
		if (time == 15 || time == 30 || time == 45 || time == 60)
		{
			entityLiving.world.playSound(entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.BLOCK_NOTE_SNARE, SoundCategory.PLAYERS, 1.0F, 1.0F, true);
		}
		if (time == 75)
		{
			entityLiving.world.playSound(entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.BLOCK_NOTE_SNARE, SoundCategory.PLAYERS, 1.0F, 1.0F, true);
			entityLiving.addPotionEffect(new PotionEffect(MobEffects.POISON, 80, 1));
			entityLiving.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));
			entityLiving.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 80, 0));
			entityLiving.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 80, 0));
			if (entityLiving instanceof EntityPlayer player && !player.capabilities.isCreativeMode) {
                ItemStack itemStack = ItemUtil.getItemStack(player, RivalRebels.gasgrenade);
                itemStack.shrink(1);
                if (itemStack.isEmpty())
                    player.inventory.deleteStack(itemStack);
			}
		}
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:ah");
	}*/
}
