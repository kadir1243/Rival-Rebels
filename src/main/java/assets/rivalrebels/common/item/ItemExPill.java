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

import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.util.Translations;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemExPill extends Item
{
	public ItemExPill() {
		super(new Properties().stacksTo(6));
	}

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.playSound(RRSounds.PILL);
		RivalRebelsSoundPlayer.playSound(player, 28, 18, 1.0f, 0.6f);
		player.startUsingItem(hand);
		if (!world.isClientSide())
		{
			int random = world.random.nextInt(100);
			if (random >= 40)
			{
				player.displayClientMessage(Translations.status().append(" ").append(Component.literal("The experiment turned out a success.").withStyle(ChatFormatting.YELLOW)), true);
				player.playSound(SoundEvents.MAGMA_CUBE_JUMP, 1.0F, 1.0F);
				player.playSound(SoundEvents.GHAST_SCREAM, 1.0F, 1.0F);
				player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 30, 20));
				player.getFoodData().eat(20, 200);
				player.heal(20);
			}
			else if (random >= 30)
			{
				player.displayClientMessage(Translations.status().append(" ").append(Component.literal("Begrüßen Sie den Uber-Soldat.").withStyle(ChatFormatting.YELLOW)), true);
                player.playSound(SoundEvents.MAGMA_CUBE_JUMP, 1.0F, 1.0F);
				player.playSound(SoundEvents.GHAST_SCREAM, 1.0F, 1.0F);
				player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 30, 20));
				player.getFoodData().eat(20, 200);
				player.heal(20);
				player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 450, 20));
				player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 500, 20));
				player.addEffect(new MobEffectInstance(MobEffects.JUMP, 300, 2));
				player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 500, 2));
				player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 550, 2));
				player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 800, 20));
				player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 20));
			}
			else if (random >= 20)
			{
				player.displayClientMessage(Translations.status().append(" ").append(Component.literal("The test subject has perished.").withStyle(ChatFormatting.YELLOW)), true);
				player.playSound(SoundEvents.MAGMA_CUBE_JUMP, 1.0F, 1.0F);
				player.playSound(SoundEvents.GHAST_SCREAM, 1.0F, 1.0F);
				player.hurt(RivalRebelsDamageSource.cyanide(world), 2000);
			}
			else
			{
				player.displayClientMessage(Translations.status().append(" ").append(Component.literal("Unexpected results have occurred.").withStyle(ChatFormatting.YELLOW)), true);
				player.playSound(SoundEvents.MAGMA_CUBE_JUMP, 1.0F, 1.0F);
				player.playSound(SoundEvents.GHAST_SCREAM, 1.0F, 1.0F);
				player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1500, 20));
				player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 1500, 20));
				player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1500, 20));
				player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 1500, 20));
				player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 1500, 20));
			}
			stack.consume(1, player);
        }
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack)
	{
		return UseAnim.EAT;
	}

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
		return 32;
	}
}
