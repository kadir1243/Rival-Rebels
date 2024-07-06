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
import assets.rivalrebels.common.entity.EntitySeekB83;
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

public class ItemSeekM202 extends TieredItem
{
	public ItemSeekM202() {
		super(Tiers.DIAMOND, new Properties().stacksTo(1));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack par1ItemStack)
	{
		return UseAnim.BOW;
	}

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
        return 90;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        ItemStack itemStack = ItemUtil.getItemStack(player, RRItems.rocket);
        if (player.hasInfiniteMaterials() || !itemStack.isEmpty() || RRConfig.SERVER.isInfiniteAmmo()) {
			player.startUsingItem(hand);
			if (!RRConfig.SERVER.isInfiniteAmmo()) {
                itemStack.consume(1, player);
			}
			RivalRebelsSoundPlayer.playSound(player, 23, 2, 0.4f);
			if (!world.isClientSide())
			{
				if (!stack.isEnchanted())
				{
					world.addFreshEntity(new EntitySeekB83(world, player, 0.1F));
				}
				else
				{
					world.addFreshEntity(new EntitySeekB83(world, player, 0.1F, 15.0f));
					world.addFreshEntity(new EntitySeekB83(world, player, 0.1F, 7.5f));
					world.addFreshEntity(new EntitySeekB83(world, player, 0.1F));
					world.addFreshEntity(new EntitySeekB83(world, player, 0.1F, -7.5f));
					world.addFreshEntity(new EntitySeekB83(world, player, 0.1F, -15.0f));
				}
			}
		}
		else if (!world.isClientSide())
		{
			player.displayClientMessage(Component.nullToEmpty("§cOut of ammunition"), false);
		}
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}
}
