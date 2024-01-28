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
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ItemGasGrenade extends Item
{
	public ItemGasGrenade() {
		super(new Settings().group(RRItems.rralltab).maxCount(6));
	}

    @Override
	public UseAction getUseAction(ItemStack stack)
	{
		return UseAction.BOW;
	}

    @Override
	public int getMaxUseTime(ItemStack stack)
	{
		return 75;
	}

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity player)) return;

        ItemStack itemStack = ItemUtil.getItemStack(player, RRItems.gasgrenade);
        if (player.getAbilities().invulnerable || !itemStack.isEmpty() || RivalRebels.infiniteGrenades)
		{
			float f = (getMaxUseTime(stack) - remainingUseTicks) / 20.0F;
			f = (f * f + f * 2) * 0.3333f;
			if (f > 1.0F) f = 1.0F;
			EntityGasGrenade entitysuperarrow = new EntityGasGrenade(world, player, 0.3f + f * 0.5f);
			if (!player.getAbilities().invulnerable)
			{
                itemStack.decrement(1);
                if (itemStack.isEmpty())
                    player.getInventory().removeOne(itemStack);
			}
			RivalRebelsSoundPlayer.playSound(player, 4, 3, 1, 0.9f);
			if (!world.isClient)
			{
				world.spawnEntity(entitysuperarrow);
				entitysuperarrow.setPosition(entitysuperarrow.getX(), entitysuperarrow.getY() - 0.05, entitysuperarrow.getZ());
			}
		}
	}

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		user.setCurrentHand(hand);
		world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SLIME_ATTACK, SoundCategory.PLAYERS, 1.0F, 1.0F);
		return super.use(world, user, hand);
	}

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity entityLiving, int count) {
		int time = 75 - count;
		if (time == 15 || time == 30 || time == 45 || time == 60)
		{
			entityLiving.world.playSound(entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), SoundEvents.BLOCK_NOTE_BLOCK_SNARE, SoundCategory.PLAYERS, 1.0F, 1.0F, true);
		}
		if (time == 75)
		{
			entityLiving.world.playSound(entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), SoundEvents.BLOCK_NOTE_BLOCK_SNARE, SoundCategory.PLAYERS, 1.0F, 1.0F, true);
			entityLiving.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 80, 1));
			entityLiving.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0));
			entityLiving.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 80, 0));
			entityLiving.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 80, 0));
			if (entityLiving instanceof PlayerEntity player && !player.getAbilities().invulnerable) {
                ItemStack itemStack = ItemUtil.getItemStack(player, RRItems.gasgrenade);
                itemStack.decrement(1);
                if (itemStack.isEmpty())
                    player.getInventory().removeOne(itemStack);
			}
		}
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:ah");
	}*/
}
