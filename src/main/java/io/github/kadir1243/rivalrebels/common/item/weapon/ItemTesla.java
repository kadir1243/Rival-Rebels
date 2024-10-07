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
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource;
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsSoundPlayer;
import io.github.kadir1243.rivalrebels.common.entity.EntityRaytrace;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import io.github.kadir1243.rivalrebels.common.util.ItemUtil;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemTesla extends TieredItem {
	public ItemTesla() {
		super(Tiers.DIAMOND, new Properties().stacksTo(1).component(RRComponents.TESLA_DIAL, 0));
	}

	@Override
	public int getEnchantmentValue()
	{
		return 100;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack)
	{
		return UseAnim.NONE;
	}

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
		return 20;
	}

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
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
			player.displayClientMessage(Component.nullToEmpty("§cOut of batteries"), false);
		}
		if (message) {
			player.displayClientMessage(Translations.orders().append(" ").append(Component.translatable("RivalRebels.message.use")).append(" [R]."), false);
			message = false;
		}
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}
	boolean message = true;

    @OnlyIn(Dist.CLIENT)
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (selected && level.isClientSide()) {
            if (RRClient.USE_KEY.isDown() && Minecraft.getInstance().screen == null) {
                Minecraft.getInstance().setScreen(new GuiTesla(getDegree(stack)));
            }
        }
    }

    @Override
    public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		if (user.isInWaterRainOrBubble() && !user.isInvulnerableTo(RivalRebelsDamageSource.electricity(world))) {
			user.hurt(RivalRebelsDamageSource.electricity(world), 2);
		}
		if (user.getRandom().nextInt(10) == 0) RivalRebelsSoundPlayer.playSound(user, 25, 1);

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
