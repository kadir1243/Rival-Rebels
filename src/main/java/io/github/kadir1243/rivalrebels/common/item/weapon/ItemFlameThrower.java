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
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.gui.GuiFlameThrower;
import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.entity.EntityFlameBall;
import io.github.kadir1243.rivalrebels.common.entity.EntityFlameBall1;
import io.github.kadir1243.rivalrebels.common.entity.EntityFlameBall2;
import io.github.kadir1243.rivalrebels.common.entity.EntityFlameBallGreen;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.item.components.FlameThrowerMode;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import io.github.kadir1243.rivalrebels.common.util.ItemUtil;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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

public class ItemFlameThrower extends TieredItem {
	public static final ResourceLocation OUT_OF_FUEL = RRIdentifiers.create("out_of_fuel");
    public ItemFlameThrower() {
		super(Tiers.DIAMOND, new Properties().stacksTo(1).component(RRComponents.FLAME_THROWER_MODE, FlameThrowerMode.DEFAULT));
	}

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BOW;
	}

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
		return 64;
	}

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);

        ItemStack itemStack = ItemUtil.getItemStack(user, RRItems.fuel.asItem());
        if (user.hasInfiniteMaterials() || !itemStack.isEmpty() || RRConfig.SERVER.isInfiniteAmmo())
		{
			user.startUsingItem(hand);
			if (!user.hasInfiniteMaterials() && !RRConfig.SERVER.isInfiniteAmmo())
			{
				itemStack.consume(1, user);
				if (getMode(stack) != 2) ItemUtil.findAndConsumeItem(user, RRItems.fuel.asItem());
				if (getMode(stack) != 2) ItemUtil.findAndConsumeItem(user, RRItems.fuel.asItem());
				if (getMode(stack) == 0) ItemUtil.findAndConsumeItem(user, RRItems.fuel.asItem());
				if (getMode(stack) == 0) ItemUtil.findAndConsumeItem(user, RRItems.fuel.asItem());
			}
			if (stack.isEnchanted() && !world.isClientSide())
			{
				world.addFreshEntity(new EntityFlameBallGreen(world, user, world.random.nextFloat() + 1.0f));
			}
		} else {
			user.displayClientMessage(Component.translatable(OUT_OF_FUEL.toLanguageKey()).withStyle(ChatFormatting.RED), false);
		}
		if (message) {
			user.displayClientMessage(Translations.orders().append(" ").append(Component.translatable("RivalRebels.message.use")).append(" [R]."), false);
			message = false;
		}
		return InteractionResultHolder.pass(stack);
	}
	boolean message = true;

    @Override
    public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		if (!world.isClientSide()) {
            if (world.random.nextInt(10) == 0 && !user.isInWaterOrBubble()) {
                user.playSound(RRSounds.FLAME_THROWER_USE.get(), 0.03f, 1F);
                if (world.random.nextInt(3) == 0 && !user.isInWaterOrBubble()) {
                    user.playSound(RRSounds.FLAME_THROWER_EXTINGUISH.get(), 0.1F, 1F);
                }
            }
            if (!stack.isEnchanted()) {
                switch (getMode(stack)) {
                    case 0:
                        for (int i = 0; i < 4; i++)
                            world.addFreshEntity(new EntityFlameBall2(world, user, world.random.nextFloat() + 0.5f));
                        break;
                    case 1:
                        world.addFreshEntity(new EntityFlameBall1(world, user, 1));
                        break;
                    case 2:
                        world.addFreshEntity(new EntityFlameBall(world, user, 1));
                        break;
                }
            }
        }
	}

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (entity instanceof Player) {
            if (selected && world.random.nextInt(10) == 0 && !entity.isInWaterOrBubble()) {
                entity.playSound(RRSounds.FLAME_THROWER_USE.get(), 0.03f, 1F);
            }
        }
        if (world.isClientSide()) {
            openGui(stack);
        }
	}

    @OnlyIn(Dist.CLIENT)
    public void openGui(ItemStack item) {
		if (RRClient.USE_KEY.isDown() && Minecraft.getInstance().screen == null) {
            Minecraft.getInstance().setScreen(new GuiFlameThrower(getMode(item)));
		}
	}

	public int getMode(ItemStack item) {
        return item.getOrDefault(RRComponents.FLAME_THROWER_MODE, FlameThrowerMode.DEFAULT).mode();
	}
}
