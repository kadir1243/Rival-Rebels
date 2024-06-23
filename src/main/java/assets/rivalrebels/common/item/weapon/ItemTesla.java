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
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityRaytrace;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
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
		return UseAnim.BOW;
	}

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
		return 20;
	}

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
        int degree = getDegree(stack);
		float chance = Math.abs(degree - 90) / 90f;
        ItemStack battery = ItemUtil.getItemStack(player, RRItems.battery);
        if (player.getAbilities().invulnerable || !battery.isEmpty() || RivalRebels.infiniteAmmo)
		{
			if (!player.getAbilities().invulnerable && !RivalRebels.infiniteAmmo)
			{
                battery.shrink(1);
				if (chance > 0.33333) battery.shrink(1);
				if (chance > 0.66666) battery.shrink(1);
                if (battery.isEmpty()) {
                    player.getInventory().removeItem(battery);
                }
            }
			player.startUsingItem(hand);
		}
		else if (!world.isClientSide)
		{
			player.displayClientMessage(Component.nullToEmpty("§cOut of batteries"), false);
		}
		if (message && world.isClientSide)
		{
			player.displayClientMessage(Component.translatable("RivalRebels.Orders").append(" ").append(Component.translatable("RivalRebels.message.use")).append(" [R]."), false);
			message = false;
		}
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide);
	}
	boolean message = true;

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (world.isClientSide) {
            if (ClientProxy.USE_KEY.isDown() && selected && Minecraft.getInstance().screen == null) {
                RivalRebels.proxy.teslaGui(getDegree(stack));
            }
        }
    }

    @Override
    public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		if (user.isInWaterRainOrBubble() && !user.isInvulnerableTo(RivalRebelsDamageSource.electricity(world))) {
			user.hurt(RivalRebelsDamageSource.electricity(world), 2);
		}
		if (user.level().random.nextInt(10) == 0) RivalRebelsSoundPlayer.playSound(user, 25, 1);

		int degree = getDegree(stack);
		float chance = Math.abs(degree - 90) / 90f;
		if (degree - 90 > 0) chance /= 10f;

		float dist = 7 + (1 - (degree / 180f)) * 73;

		float randomness = degree / 720f;

		int num = (degree / 25) + 1;

		if (!world.isClientSide) for (int i = 0; i < num; i++)
			world.addFreshEntity(new EntityRaytrace(world, user, dist, randomness, chance, !stack.isEnchanted()));
	}

	public static int getDegree(ItemStack item)
	{
		if (!item.has(RRComponents.TESLA_DIAL)) return 0;
		else return item.get(RRComponents.TESLA_DIAL) + 90;
	}
}
