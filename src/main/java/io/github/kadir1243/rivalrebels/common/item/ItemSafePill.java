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
package io.github.kadir1243.rivalrebels.common.item;

import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;

public class ItemSafePill extends Item
{
	public ItemSafePill(Properties properties) {
		super(properties);
	}

    @Override
    public InteractionResult use(Level world, Player player, InteractionHand hand) {
		player.startUsingItem(hand);
		if (!world.isClientSide()) {
			player.sendSystemMessage(Translations.status().append(" ").append(Component.literal("Regenerating...").withStyle(ChatFormatting.YELLOW)));
            player.playSound(RRSounds.PILL2.get());
            player.playSound(RRSounds.VOICE_18.get());
			player.playSound(SoundEvents.MAGMA_CUBE_JUMP, 1.0F, 1.0F);
			player.playSound(SoundEvents.GHAST_SCREAM, 1.0F, 1.0F);
			player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 10, 20));
			player.getFoodData().eat(10, 200);
			player.heal(10);
            ItemStack stack = player.getItemInHand(hand);
            stack.consume(1, player);
            return InteractionResult.SUCCESS;
		}
		return super.use(world, player, hand);
	}

	@Override
	public ItemUseAnimation getUseAnimation(ItemStack stack)
	{
		return ItemUseAnimation.EAT;
	}

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
		return 32;
	}
}
