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
import assets.rivalrebels.common.entity.EntitySeekB83;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ItemSeekM202 extends ToolItem
{
	public ItemSeekM202() {
		super(ToolMaterials.DIAMOND, new Settings().maxCount(1));
	}

	@Override
	public UseAction getUseAction(ItemStack par1ItemStack)
	{
		return UseAction.BOW;
	}

    public int getMaxUseTime(ItemStack stack)
    {
        return 90;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        ItemStack itemStack = ItemUtil.getItemStack(player, RRItems.rocket);
        if (player.getAbilities().invulnerable || !itemStack.isEmpty() || RivalRebels.infiniteAmmo)
		{
			player.setCurrentHand(hand);
			if (!player.getAbilities().invulnerable && !RivalRebels.infiniteAmmo)
			{
                itemStack.decrement(1);
				if (itemStack.isEmpty())
                    player.getInventory().removeOne(itemStack);
			}
			RivalRebelsSoundPlayer.playSound(player, 23, 2, 0.4f);
			if (!world.isClient)
			{
				if (!stack.hasEnchantments())
				{
					world.spawnEntity(new EntitySeekB83(world, player, 0.1F));
				}
				else
				{
					world.spawnEntity(new EntitySeekB83(world, player, 0.1F, 15.0f));
					world.spawnEntity(new EntitySeekB83(world, player, 0.1F, 7.5f));
					world.spawnEntity(new EntitySeekB83(world, player, 0.1F));
					world.spawnEntity(new EntitySeekB83(world, player, 0.1F, -7.5f));
					world.spawnEntity(new EntitySeekB83(world, player, 0.1F, -15.0f));
				}
			}
		}
		else if (!world.isClient)
		{
			player.sendMessage(Text.of("§cOut of ammunition"), false);
		}
		return TypedActionResult.success(stack, world.isClient);
	}
}
