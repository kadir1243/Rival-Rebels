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

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityBomb;
import assets.rivalrebels.common.entity.EntityRocket;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemRPG extends TieredItem
{
	public ItemRPG() {
		super(Tiers.DIAMOND, new Properties().stacksTo(1));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack)
	{
		return UseAnim.BOW;
	}

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
		return 144;
	}

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ItemStack itemStack = ItemUtil.getItemStack(player, RRItems.rocket);
        if (!itemStack.isEmpty() || RRConfig.SERVER.isInfiniteAmmo()) {
			player.startUsingItem(hand);
			if (!world.isClientSide && !RRConfig.SERVER.isInfiniteAmmo()) {
                itemStack.consume(1, player);
			}
			if (!stack.isEnchanted()) RivalRebelsSoundPlayer.playSound(player, 23, 2, 0.4f);
			else RivalRebelsSoundPlayer.playSound(player, 10, 4, 1.0f);
			if (!world.isClientSide()) {
				if (!stack.isEnchanted()) world.addFreshEntity(new EntityRocket(world, player, 0.1F));
				else world.addFreshEntity(new EntityBomb(world, player, 0.1F));
			}
		}
		else if (!world.isClientSide())
		{
			player.displayClientMessage(Component.nullToEmpty("§cOut of ammunition"), false);
		}
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}
}
