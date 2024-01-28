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

import assets.rivalrebels.client.itemrenders.HackRocketLauncherRenderer;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityHackB83;
import assets.rivalrebels.common.explosion.Explosion;
import assets.rivalrebels.common.item.RRItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public class ItemHackM202 extends ToolItem
{
	public ItemHackM202()
	{
		super(ToolMaterials.DIAMOND, new Settings().maxCount(1).group(RRItems.rralltab));
	}
    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public BuiltinModelItemRenderer getItemStackRenderer() {
                return new HackRocketLauncherRenderer(MinecraftClient.getInstance().getBlockEntityRenderDispatcher(), MinecraftClient.getInstance().getEntityModelLoader());
            }
        });
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
		new Explosion(world, user.getX(), user.getY(), user.getZ(), 2, true, false, RivalRebelsDamageSource.flare);
		return TypedActionResult.success(stack, world.isClient);
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:bg");
	}*/
}
