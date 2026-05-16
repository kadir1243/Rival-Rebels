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
package io.github.kadir1243.rivalrebels.common.item.weapon;

import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.entity.EntityPlasmoid;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.util.ItemUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;

public class ItemPlasmaCannon extends Item
{
	public ItemPlasmaCannon(Properties properties) {
		super(properties);
	}

	@Override
	public ItemUseAnimation getUseAnimation(ItemStack stack)
	{
		return ItemUseAnimation.BOW;
	}

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
		return 64;
	}

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        ItemStack hydrodStack = ItemUtil.getItemStack(user, RRItems.hydrod.asItem());
        if (user.hasInfiniteMaterials() || !hydrodStack.isEmpty() || RRConfig.SERVER.isInfiniteAmmo()) {
			user.startUsingItem(hand);
			if (!user.hasInfiniteMaterials() && !RRConfig.SERVER.isInfiniteAmmo()) {
				if (!hydrodStack.isEmpty())
				{
					hydrodStack.hurtAndBreak(1, user, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
					if (hydrodStack.getDamageValue() == hydrodStack.getMaxDamage())
					{
						hydrodStack.consume(1, user);
						user.getInventory().add(RRItems.emptyrod.toStack());
					}
					user.startUsingItem(hand);
				}
				else
				{
					return InteractionResult.SUCCESS;
				}
			}
            user.playSound(RRSounds.PLASMA2.get(), 0.25F, 1);
		}
		else if (!world.isClientSide())
		{
			user.sendSystemMessage(Component.literal("§cOut of Hydrogen"));
		}
		return InteractionResult.SUCCESS;
	}

    @Override
    public boolean releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
		if (!world.isClientSide()) {
            float f = (getUseDuration(stack, user) - remainingUseTicks) / 20.0F;
			f = (f * f + f * 2) * 0.3333f;
			if (f > 1.0F) f = 1.0F;
			f+=0.2f;
            user.playSound(RRSounds.PLASMA3.get());
			Entity entity = new EntityPlasmoid(world, user, f+0.5f, stack.isEnchanted());
			world.addFreshEntity(entity);
		}
        return false;
    }
}
