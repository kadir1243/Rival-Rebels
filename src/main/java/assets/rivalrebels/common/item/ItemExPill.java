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
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemExPill extends Item
{
	public ItemExPill()
	{
		super();
		setMaxStackSize(6);
		setCreativeTab(RivalRebels.rralltab);
	}

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
		RivalRebelsSoundPlayer.playSound(player, 15, 0);
		RivalRebelsSoundPlayer.playSound(player, 28, 18, 1.0f, 0.6f);
		player.setActiveHand(hand);
		if (!world.isRemote)
		{
			int rand = world.rand.nextInt(100);
			if (rand >= 40)
			{
				player.sendMessage(new TextComponentString("§7[§6Status§7]§e The experiment turned out a success."));
				world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_MAGMACUBE_JUMP, SoundCategory.PLAYERS, 1.0F, 1.0F);
				world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_GHAST_SCREAM, SoundCategory.PLAYERS, 1.0F, 1.0F);
				player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 30, 20));
				player.getFoodStats().addStats(20, 200);
				player.heal(20);
			}
			else if (rand >= 30)
			{
				player.sendMessage(new TextComponentString("§7[§6Status§7]§e Begrüßen Sie den Uber-Soldat."));
				world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_MAGMACUBE_JUMP, SoundCategory.PLAYERS, 1.0F, 1.0F);
				world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_GHAST_SCREAM, SoundCategory.PLAYERS, 1.0F, 1.0F);
				player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 30, 20));
				player.getFoodStats().addStats(20, 200);
				player.heal(20);
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 450, 20));
				player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 500, 20));
				player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 300, 2));
				player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 500, 2));
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 550, 2));
				player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 800, 20));
				player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 800, 20));
			}
			else if (rand >= 20)
			{
				player.sendMessage(new TextComponentString("§7[§6Status§7]§e The test subject has perished."));
				world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_MAGMACUBE_JUMP, SoundCategory.PLAYERS, 1.0F, 1.0F);
				world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_GHAST_SCREAM, SoundCategory.PLAYERS, 1.0F, 1.0F);
				player.attackEntityFrom(RivalRebelsDamageSource.cyanide, 2000);
			}
			else
			{
				player.sendMessage(new TextComponentString("§7[§6Status§7]§e Unexpected results have occurred."));
				world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_MAGMACUBE_JUMP, SoundCategory.PLAYERS, 1.0F, 1.0F);
				world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_GHAST_SCREAM, SoundCategory.PLAYERS, 1.0F, 1.0F);
				player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 1500, 20));
				player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 1500, 20));
				player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1500, 20));
				player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 1500, 20));
				player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 1500, 20));
			}
			stack.shrink(1);
            return ActionResult.newResult(EnumActionResult.PASS, stack);
        }
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
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
		itemIcon = iconregister.registerIcon("RivalRebels:ai");
	}*/
}
