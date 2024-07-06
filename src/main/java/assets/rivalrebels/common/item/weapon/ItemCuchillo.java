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

import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.entity.EntityCuchillo;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemCuchillo extends TieredItem
{
	public ItemCuchillo()
	{
		super(Tiers.IRON, new Properties().stacksTo(5));
	}

    @Override
	public UseAnim getUseAnimation(ItemStack par1ItemStack)
	{
		return UseAnim.BOW;
	}

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
		return 72000;
	}

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof Player player)) return;

        ItemStack itemStack = ItemUtil.getItemStack(player, RRItems.knife);
        if (player.hasInfiniteMaterials() || !itemStack.isEmpty())
		{
			float f = (getUseDuration(stack, player) - remainingUseTicks) / 20.0F;
			f = (f * f + f * 2) * 0.3333f;
			if (f < 0.1D) return;
			if (f > 1.0F) f = 1.0F;
            stack.consume(1, player);
            player.playSound(RRSounds.CUCHILLO_UNKNOWN3);
			if (!world.isClientSide()) world.addFreshEntity(new EntityCuchillo(world, player, 0.5f + f));
		}
	}

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        user.startUsingItem(hand);
        return super.use(world, user, hand);
    }
}
