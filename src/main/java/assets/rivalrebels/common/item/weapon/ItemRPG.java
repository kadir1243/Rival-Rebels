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
import assets.rivalrebels.client.itemrenders.RocketLauncherRenderer;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityBomb;
import assets.rivalrebels.common.entity.EntityRocket;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public class ItemRPG extends ToolItem
{
	public ItemRPG() {
		super(ToolMaterials.DIAMOND, new Settings().maxCount(1).group(RRItems.rralltab));
	}
    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public BuiltinModelItemRenderer getItemStackRenderer() {
                return new RocketLauncherRenderer(MinecraftClient.getInstance().getBlockEntityRenderDispatcher(), MinecraftClient.getInstance().getEntityModelLoader());
            }
        });
    }
	@Override
	public UseAction getUseAction(ItemStack stack)
	{
		return UseAction.BOW;
	}

	@Override
	public int getMaxUseTime(ItemStack stack)
	{
		return 144;
	}

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        ItemStack itemStack = ItemUtil.getItemStack(player, RRItems.rocket);
        if (player.getAbilities().invulnerable || !itemStack.isEmpty() || RivalRebels.infiniteAmmo)
		{
			player.setCurrentHand(hand);
			if (!world.isClient && !player.getAbilities().invulnerable && !RivalRebels.infiniteAmmo)
			{
                itemStack.decrement(1);
                if (itemStack.isEmpty())
                    player.getInventory().removeOne(itemStack);
			}
			if (!stack.hasEnchantments()) RivalRebelsSoundPlayer.playSound(player, 23, 2, 0.4f);
			else RivalRebelsSoundPlayer.playSound(player, 10, 4, 1.0f);
			if (!world.isClient)
			{
				if (!stack.hasEnchantments()) world.spawnEntity(new EntityRocket(world, player, 0.1F));
				else world.spawnEntity(new EntityBomb(world, player, 0.1F));
			}
		}
		else if (!world.isClient)
		{
			player.sendMessage(Text.of("§cOut of ammunition"), false);
		}
		return TypedActionResult.success(stack, world.isClient);
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:aq");
	}*/
}
