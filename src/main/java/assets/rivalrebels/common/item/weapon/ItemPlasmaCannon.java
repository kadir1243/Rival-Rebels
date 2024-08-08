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
import assets.rivalrebels.common.entity.EntityPlasmoid;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemPlasmaCannon extends TieredItem
{
	public ItemPlasmaCannon() {
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
		return 64;
	}

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        ItemStack hydrodStack = ItemUtil.getItemStack(user, RRItems.hydrod);
        if (user.hasInfiniteMaterials() || !hydrodStack.isEmpty() || RRConfig.SERVER.isInfiniteAmmo()) {
			user.startUsingItem(hand);
			if (!user.hasInfiniteMaterials() && !RRConfig.SERVER.isInfiniteAmmo()) {
				if (!hydrodStack.isEmpty())
				{
					hydrodStack.hurtAndBreak(1, user, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
					if (hydrodStack.getDamageValue() == hydrodStack.getMaxDamage())
					{
						hydrodStack.consume(1, user);
						user.getInventory().add(RRItems.emptyrod.getDefaultInstance());
					}
					user.startUsingItem(hand);
				}
				else
				{
					return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
				}
			}
			RivalRebelsSoundPlayer.playSound(user, 16, 1, 0.25f);
		}
		else if (!world.isClientSide())
		{
			user.displayClientMessage(Component.nullToEmpty("§cOut of Hydrogen"), true);
		}
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
		if (!world.isClientSide()) {
            float f = (getUseDuration(stack, user) - remainingUseTicks) / 20.0F;
			f = (f * f + f * 2) * 0.3333f;
			if (f > 1.0F) f = 1.0F;
			f+=0.2f;
			RivalRebelsSoundPlayer.playSound(user, 16, 2);
			Entity entity = new EntityPlasmoid(world, user, f+0.5f, stack.isEnchanted());
			world.addFreshEntity(entity);
		}
	}
}
