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
package assets.rivalrebels.common.item.weapon;

import assets.rivalrebels.ClientProxy;
import assets.rivalrebels.RRConfig;
import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.gui.GuiFlameThrower;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityFlameBall;
import assets.rivalrebels.common.entity.EntityFlameBall1;
import assets.rivalrebels.common.entity.EntityFlameBall2;
import assets.rivalrebels.common.entity.EntityFlameBallGreen;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.item.components.FlameThrowerMode;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.util.ItemUtil;
import assets.rivalrebels.common.util.Translations;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

        ItemStack itemStack = ItemUtil.getItemStack(user, RRItems.fuel);
        if (user.hasInfiniteMaterials() || !itemStack.isEmpty() || RRConfig.SERVER.isInfiniteAmmo())
		{
			user.startUsingItem(hand);
			if (!user.hasInfiniteMaterials() && !RRConfig.SERVER.isInfiniteAmmo())
			{
				itemStack.consume(1, user);
				if (getMode(stack) != 2) ItemUtil.findAndConsumeItem(user, RRItems.fuel);
				if (getMode(stack) != 2) ItemUtil.findAndConsumeItem(user, RRItems.fuel);
				if (getMode(stack) == 0) ItemUtil.findAndConsumeItem(user, RRItems.fuel);
				if (getMode(stack) == 0) ItemUtil.findAndConsumeItem(user, RRItems.fuel);
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
                RivalRebelsSoundPlayer.playSound(user, 8, 0, 0.03f);
                if (world.random.nextInt(3) == 0 && !user.isInWaterOrBubble()) {
                    RivalRebelsSoundPlayer.playSound(user, 8, 1, 0.1f);
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
		if (!stack.getOrDefault(RRComponents.FLAME_THROWER_MODE, FlameThrowerMode.DEFAULT).isReady()) {
			stack.set(RRComponents.FLAME_THROWER_MODE, new FlameThrowerMode(2, true));
		}
		if (entity instanceof Player) {
            if (selected && world.random.nextInt(10) == 0 && !entity.isInWaterOrBubble()) {
                RivalRebelsSoundPlayer.playSound(entity, 8, 0, 0.03f);
            }
        }
        if (world.isClientSide()) {
            openGui(stack);
        }
	}

    @Environment(EnvType.CLIENT)
    public void openGui(ItemStack item) {
		if (ClientProxy.USE_KEY.isDown() && Minecraft.getInstance().screen == null) {
            Minecraft.getInstance().setScreen(new GuiFlameThrower(getMode(item)));
		}
	}

	public int getMode(ItemStack item) {
        return item.getOrDefault(RRComponents.FLAME_THROWER_MODE, FlameThrowerMode.DEFAULT).mode();
	}
}
