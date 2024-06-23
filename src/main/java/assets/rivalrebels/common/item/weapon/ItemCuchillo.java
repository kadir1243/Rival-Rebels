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

import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityCuchillo;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ItemCuchillo extends ToolItem
{
	public ItemCuchillo()
	{
		super(ToolMaterials.IRON, new Settings().maxCount(5));
	}

    @Override
	public UseAction getUseAction(ItemStack par1ItemStack)
	{
		return UseAction.BOW;
	}

    @Override
	public int getMaxUseTime(ItemStack par1ItemStack)
	{
		return 72000;
	}

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity player)) return;

        ItemStack itemStack = ItemUtil.getItemStack(player, RRItems.knife);
        if (player.getAbilities().invulnerable || !itemStack.isEmpty())
		{
			float f = (getMaxUseTime(stack) - remainingUseTicks) / 20.0F;
			f = (f * f + f * 2) * 0.3333f;
			if (f < 0.1D) return;
			if (f > 1.0F) f = 1.0F;
			if (!player.getAbilities().invulnerable) stack.decrement(1);
			RivalRebelsSoundPlayer.playSound(player, 4, 3);
			if (!world.isClient) world.spawnEntity(new EntityCuchillo(world, player, 0.5f + f));
		}
	}

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return super.use(world, user, hand);
    }
}
