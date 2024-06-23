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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityGasGrenade;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemGasGrenade extends Item
{
	public ItemGasGrenade() {
		super(new Properties().stacksTo(6));
	}

    @Override
	public UseAnim getUseAnimation(ItemStack stack)
	{
		return UseAnim.BOW;
	}

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
		return 75;
	}

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof Player player)) return;

        ItemStack itemStack = ItemUtil.getItemStack(player, RRItems.gasgrenade);
        if (player.getAbilities().invulnerable || !itemStack.isEmpty() || RivalRebels.infiniteGrenades)
		{
			float f = (getUseDuration(stack, player) - remainingUseTicks) / 20.0F;
			f = (f * f + f * 2) * 0.3333f;
			if (f > 1.0F) f = 1.0F;
			EntityGasGrenade entitysuperarrow = new EntityGasGrenade(world, player, 0.3f + f * 0.5f);
			if (!player.getAbilities().invulnerable)
			{
                itemStack.shrink(1);
                if (itemStack.isEmpty())
                    player.getInventory().removeItem(itemStack);
			}
			RivalRebelsSoundPlayer.playSound(player, 4, 3, 1, 0.9f);
			if (!world.isClientSide)
			{
				world.addFreshEntity(entitysuperarrow);
				entitysuperarrow.setPos(entitysuperarrow.getX(), entitysuperarrow.getY() - 0.05, entitysuperarrow.getZ());
			}
		}
	}

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		user.startUsingItem(hand);
		world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.SLIME_ATTACK, SoundSource.PLAYERS, 1.0F, 1.0F);
		return super.use(world, user, hand);
	}

    @Override
    public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		int time = 75 - remainingUseTicks;
		if (time == 15 || time == 30 || time == 45 || time == 60)
		{
			world.playLocalSound(user.getX(), user.getY(), user.getZ(), SoundEvents.NOTE_BLOCK_SNARE.value(), SoundSource.PLAYERS, 1.0F, 1.0F, true);
		}
		if (time == 75)
		{
			world.playLocalSound(user.getX(), user.getY(), user.getZ(), SoundEvents.NOTE_BLOCK_SNARE.value(), SoundSource.PLAYERS, 1.0F, 1.0F, true);
			user.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 1));
			user.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
			user.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80, 0));
			user.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 0));
			if (user instanceof Player player && !player.getAbilities().invulnerable) {
                ItemStack itemStack = ItemUtil.getItemStack(player, RRItems.gasgrenade);
                itemStack.shrink(1);
                if (itemStack.isEmpty())
                    player.getInventory().removeItem(itemStack);
			}
		}
	}
}
