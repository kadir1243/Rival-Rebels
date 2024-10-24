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
import io.github.kadir1243.rivalrebels.common.entity.EntityLaserBurst;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.util.ItemUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemAstroBlaster extends TieredItem {
	boolean	isA	= true;

	public ItemAstroBlaster() {
		super(Tiers.DIAMOND, new Properties().stacksTo(1));
	}

	@Override
	public int getEnchantmentValue()
	{
		return 100;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack)
	{
		return UseAnim.BOW;
	}

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
		return 2000;
	}

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        ItemStack itemStack = ItemUtil.getItemStack(player, RRItems.redrod.asItem());
        if (player.hasInfiniteMaterials() || !itemStack.isEmpty() || RRConfig.SERVER.isInfiniteAmmo()) {
			if (world.isClientSide()) stack.set(DataComponents.REPAIR_COST, 1);
			player.startUsingItem(hand);
            player.playSound(RRSounds.LAPTOP.get(), 0.7F, 0.7F);
		} else if (!world.isClientSide()) {
			player.displayClientMessage(Component.nullToEmpty("§cNot enough redstone rods"), false);
		}
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}

    @Override
    public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!(user instanceof Player player)) return;

        if (remainingUseTicks < 1980 && !world.isClientSide()) {
			if (!RRConfig.SERVER.isInfiniteAmmo()) {
                ItemStack redrodStack = ItemUtil.getItemStack(user, RRItems.redrod.asItem());
                if (!redrodStack.isEmpty()) {
					redrodStack.hurtAndBreak(1, (ServerLevel) world, (ServerPlayer) player, item -> {});
					if (redrodStack.getDamageValue() == redrodStack.getMaxDamage()) {
						redrodStack.consume(1, user);
						player.addItem(RRItems.emptyrod.toStack());
					}
				} else {
					return;
				}
			}

			if (isA) user.playSound(RRSounds.BLASTER_MESSAGE_FROM_OTHER_PLANETS.get(), 0.5f, 0.3f);
			else user.playSound(RRSounds.BLASTER_MESSAGE_FROM_OTHER_PLANETS2.get(), 0.4f, 1.7f);

			isA = !isA;
			world.addFreshEntity(new EntityLaserBurst(world, user, stack.isEnchanted()));
		}
		else if (world.isClientSide())
		{
			stack.set(DataComponents.REPAIR_COST, (2000 - remainingUseTicks) + 1);
		}
	}

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
		if (world.isClientSide()) stack.set(DataComponents.REPAIR_COST, 0);
	}
}
