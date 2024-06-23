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

import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityHackB83;
import assets.rivalrebels.common.explosion.Explosion;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;

public class ItemHackM202 extends TieredItem
{
	public ItemHackM202()
	{
		super(Tiers.DIAMOND, new Properties().stacksTo(1));
	}

	@Override
	public int getEnchantmentValue()
	{
		return 100;
	}

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
		user.setItemInHand(hand, ItemStack.EMPTY);
		if (!world.isClientSide)
		{
			world.addFreshEntity(new EntityHackB83(world, user.getX(), user.getY(), user.getZ(), -user.yHeadRot, user.getXRot(), stack.isEnchanted()));
		}
		RivalRebelsSoundPlayer.playSound(user, 23, 2, 0.4f);
		new Explosion(world, user.getX(), user.getY(), user.getZ(), 2, true, false, RivalRebelsDamageSource.flare(world));
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide);
	}
}
