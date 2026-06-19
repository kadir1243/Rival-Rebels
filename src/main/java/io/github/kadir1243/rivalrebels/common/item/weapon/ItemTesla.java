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

import io.github.kadir1243.rivalrebels.RRClient;
import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.client.gui.GuiTesla;
import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource;
import io.github.kadir1243.rivalrebels.common.entity.EntityRaytrace;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import io.github.kadir1243.rivalrebels.common.util.ItemUtil;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public class ItemTesla extends Item {
	public ItemTesla(Properties properties) {
		super(properties);
	}

	@Override
	public ItemUseAnimation getUseAnimation(ItemStack stack)
	{
		return ItemUseAnimation.NONE;
	}

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
		return 20;
	}

    @Override
    public InteractionResult use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
        int degree = getDegree(stack);
		float chance = Mth.abs(degree - 90) / 90f;
        ItemStack battery = ItemUtil.getItemStack(player, RRItems.battery.asItem());
        if (player.hasInfiniteMaterials() || !battery.isEmpty() || RRConfig.SERVER.isInfiniteAmmo()) {
			if (!RRConfig.SERVER.isInfiniteAmmo()) {
                battery.consume(1, player);
				if (chance > 0.33333) {
                    if (battery.isEmpty()) {
                        battery = ItemUtil.getItemStack(player, RRItems.battery.asItem());
                    }
                    battery.consume(1, player);
                }
				if (chance > 0.66666) {
                    if (battery.isEmpty()) {
                        battery = ItemUtil.getItemStack(player, RRItems.battery.asItem());
                    }
                    battery.consume(1, player);
                }
            }
			player.startUsingItem(hand);
		} else {
			player.sendSystemMessage(Component.literal("§cOut of batteries"));
		}
		if (message) {
			player.sendSystemMessage(Translations.orders().append(" ").append(Component.translatable("RivalRebels.message.use")).append(" [R]."));
			message = false;
		}
		return InteractionResult.SUCCESS;
	}
	boolean message = true;

    @OnlyIn(Dist.CLIENT)
    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
        if (slot != null && level.isClientSide()) {
            if (RRClient.USE_KEY.isDown() && Minecraft.getInstance().screen == null) {
                Minecraft.getInstance().setScreen(new GuiTesla(getDegree(stack)));
            }
        }
    }

    @Override
    public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		if (user.isInWaterOrRain() && !user.isInvulnerableTo(((ServerLevel) world), RivalRebelsDamageSource.electricity(world))) {
			user.hurt(RivalRebelsDamageSource.electricity(world), 2);
		}
		if (user.getRandom().nextInt(10) == 0) user.playSound(RRSounds.TESLA.get());

		int degree = getDegree(stack);
		float chance = Mth.abs(degree - 90) / 90f;
		if (degree - 90 > 0) chance /= 10f;

		float dist = 7 + (1 - (degree / 180f)) * 73;

		float randomness = degree / 720f;

		int num = (degree / 25) + 1;

		if (!world.isClientSide())
            for (int i = 0; i < num; i++)
                world.addFreshEntity(new EntityRaytrace(world, user, dist, randomness, chance, !stack.isEnchanted()));
	}

	public static int getDegree(ItemStack item) {
		if (!item.has(RRComponents.TESLA_DIAL)) return 0;
		else return item.get(RRComponents.TESLA_DIAL) + 90;
	}
}
