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
import assets.rivalrebels.common.entity.EntityPlasmoid;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ItemPlasmaCannon extends ToolItem
{
	public ItemPlasmaCannon() {
		super(ToolMaterials.DIAMOND, new Settings().maxCount(1));
	}

	@Override
	public int getEnchantability()
	{
		return 100;
	}

	@Override
	public UseAction getUseAction(ItemStack stack)
	{
		return UseAction.BOW;
	}

	@Override
	public int getMaxUseTime(ItemStack stack)
	{
		return 64;
	}

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        ItemStack hydrodStack = ItemUtil.getItemStack(user, RRItems.hydrod);
        if (user.getAbilities().invulnerable || !hydrodStack.isEmpty() || RivalRebels.infiniteAmmo) {
			user.setCurrentHand(hand);
			if (!user.getAbilities().invulnerable && !RivalRebels.infiniteAmmo) {
				if (!hydrodStack.isEmpty())
				{
					hydrodStack.damage(1, user, player -> player.sendToolBreakStatus(hand));
					if (hydrodStack.getDamage() == hydrodStack.getMaxDamage())
					{
						hydrodStack.decrement(1);
                        if (hydrodStack.isEmpty())
                            user.getInventory().removeOne(hydrodStack);
						user.getInventory().insertStack(RRItems.emptyrod.getDefaultStack());
					}
					user.setCurrentHand(hand);
				}
				else
				{
					return TypedActionResult.success(stack, world.isClient);
				}
			}
			RivalRebelsSoundPlayer.playSound(user, 16, 1, 0.25f);
		}
		else if (!world.isClient)
		{
			user.sendMessage(Text.of("§cOut of Hydrogen"), true);
		}
		return TypedActionResult.success(stack, world.isClient);
	}

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (!world.isClient) {
            float f = (getMaxUseTime(stack) - remainingUseTicks) / 20.0F;
			f = (f * f + f * 2) * 0.3333f;
			if (f > 1.0F) f = 1.0F;
			f+=0.2f;
			RivalRebelsSoundPlayer.playSound(user, 16, 2);
			Entity entity = new EntityPlasmoid(world, user, f+0.5f, stack.hasEnchantments());
			world.spawnEntity(entity);
		}
	}
}
