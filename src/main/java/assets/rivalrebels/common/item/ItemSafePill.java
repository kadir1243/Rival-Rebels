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

import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

public class ItemSafePill extends Item
{
	public ItemSafePill() {
		super(new Properties().stacksTo(6));
	}

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		player.startUsingItem(hand);
		if (!world.isClientSide)
		{
			player.displayClientMessage(Component.nullToEmpty("§7[§6Status§7]§e Regenerating..."), true);
			RivalRebelsSoundPlayer.playSound(player, 15, 1);
			RivalRebelsSoundPlayer.playSound(player, 28, 18);
			world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.MAGMA_CUBE_JUMP, SoundSource.PLAYERS, 1.0F, 1.0F);
			world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.GHAST_SCREAM, SoundSource.PLAYERS, 1.0F, 1.0F);
			player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 10, 20));
			player.getFoodData().eat(10, 200);
			player.heal(10);
            ItemStack stack = player.getItemInHand(hand);
            stack.shrink(1);
            if (stack.isEmpty()) stack = ItemStack.EMPTY;
            player.setItemInHand(hand, stack);
		}
		return super.use(world, player, hand);
	}

	@Override
	public UseAnim getUseAnimation(ItemStack par1ItemStack)
	{
		return UseAnim.EAT;
	}

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
		return 32;
	}
}
