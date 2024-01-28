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

import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ItemExPill extends Item
{
	public ItemExPill() {
		super(new Settings().maxCount(6).group(RRItems.rralltab));
	}

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
		RivalRebelsSoundPlayer.playSound(player, 15, 0);
		RivalRebelsSoundPlayer.playSound(player, 28, 18, 1.0f, 0.6f);
		player.setCurrentHand(hand);
		if (!world.isClient)
		{
			int random = world.random.nextInt(100);
			if (random >= 40)
			{
				player.sendMessage(Text.of("§7[§6Status§7]§e The experiment turned out a success."), true);
				world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_MAGMA_CUBE_JUMP, SoundCategory.PLAYERS, 1.0F, 1.0F);
				world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_GHAST_SCREAM, SoundCategory.PLAYERS, 1.0F, 1.0F);
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 30, 20));
				player.getHungerManager().add(20, 200);
				player.heal(20);
			}
			else if (random >= 30)
			{
				player.sendMessage(Text.of("§7[§6Status§7]§e Begrüßen Sie den Uber-Soldat."), true);
				world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_MAGMA_CUBE_JUMP, SoundCategory.PLAYERS, 1.0F, 1.0F);
				world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_GHAST_SCREAM, SoundCategory.PLAYERS, 1.0F, 1.0F);
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 30, 20));
				player.getHungerManager().add(20, 200);
				player.heal(20);
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 450, 20));
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 500, 20));
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 300, 2));
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 500, 2));
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 550, 2));
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 800, 20));
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 20));
			}
			else if (random >= 20)
			{
				player.sendMessage(Text.of("§7[§6Status§7]§e The test subject has perished."), true);
				world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_MAGMA_CUBE_JUMP, SoundCategory.PLAYERS, 1.0F, 1.0F);
				world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_GHAST_SCREAM, SoundCategory.PLAYERS, 1.0F, 1.0F);
				player.damage(RivalRebelsDamageSource.cyanide, 2000);
			}
			else
			{
				player.sendMessage(Text.of("§7[§6Status§7]§e Unexpected results have occurred."), true);
				world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_MAGMA_CUBE_JUMP, SoundCategory.PLAYERS, 1.0F, 1.0F);
				world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_GHAST_SCREAM, SoundCategory.PLAYERS, 1.0F, 1.0F);
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 1500, 20));
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 1500, 20));
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1500, 20));
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 1500, 20));
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 1500, 20));
			}
			stack.decrement(1);
        }
		return TypedActionResult.success(stack, world.isClient);
	}

	@Override
	public UseAction getUseAction(ItemStack stack)
	{
		return UseAction.EAT;
	}

    @Override
    public int getMaxUseTime(ItemStack stack) {
		return 32;
	}
}
