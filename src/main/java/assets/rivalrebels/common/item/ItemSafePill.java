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
package assets.rivalrebels.common.item;

import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ItemSafePill extends Item
{
	public ItemSafePill() {
		super(new Settings().maxCount(6));
	}

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		player.setCurrentHand(hand);
		if (!world.isClient)
		{
			player.sendMessage(Text.of("§7[§6Status§7]§e Regenerating..."), true);
			RivalRebelsSoundPlayer.playSound(player, 15, 1);
			RivalRebelsSoundPlayer.playSound(player, 28, 18);
			world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_MAGMA_CUBE_JUMP, SoundCategory.PLAYERS, 1.0F, 1.0F);
			world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_GHAST_SCREAM, SoundCategory.PLAYERS, 1.0F, 1.0F);
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 10, 20));
			player.getHungerManager().add(10, 200);
			player.heal(10);
            ItemStack stack = player.getStackInHand(hand);
            stack.decrement(1);
            if (stack.isEmpty()) stack = ItemStack.EMPTY;
            player.setStackInHand(hand, stack);
		}
		return super.use(world, player, hand);
	}

	@Override
	public UseAction getUseAction(ItemStack par1ItemStack)
	{
		return UseAction.EAT;
	}

    @Override
	public int getMaxUseTime(ItemStack par1ItemStack)
	{
		return 32;
	}
}
