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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ItemHackM202 extends ToolItem
{
	public ItemHackM202()
	{
		super(ToolMaterials.DIAMOND, new Settings().maxCount(1));
	}

	@Override
	public int getEnchantability()
	{
		return 100;
	}

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
		user.setStackInHand(hand, ItemStack.EMPTY);
		if (!world.isClient)
		{
			world.spawnEntity(new EntityHackB83(world, user.getX(), user.getY(), user.getZ(), -user.headYaw, user.getPitch(), stack.hasEnchantments()));
		}
		RivalRebelsSoundPlayer.playSound(user, 23, 2, 0.4f);
		new Explosion(world, user.getX(), user.getY(), user.getZ(), 2, true, false, RivalRebelsDamageSource.flare(world));
		return TypedActionResult.success(stack, world.isClient);
	}
}
