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

import assets.rivalrebels.ClientProxy;
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.client.itemrenders.FlamethrowerRenderer;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityFlameBall;
import assets.rivalrebels.common.entity.EntityFlameBall1;
import assets.rivalrebels.common.entity.EntityFlameBall2;
import assets.rivalrebels.common.entity.EntityFlameBallGreen;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.resource.language.I18n;
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
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public class ItemFlameThrower extends ToolItem {
	public ItemFlameThrower() {
		super(ToolMaterials.DIAMOND, new Settings().maxCount(1).group(RRItems.rralltab));
	}
    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public BuiltinModelItemRenderer getItemStackRenderer() {
                return new FlamethrowerRenderer(MinecraftClient.getInstance().getBlockEntityRenderDispatcher(), MinecraftClient.getInstance().getEntityModelLoader());
            }
        });
    }
    @Override
    public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}

    @Override
    public int getMaxUseTime(ItemStack stack) {
		return 64;
	}

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        ItemStack itemStack = ItemUtil.getItemStack(user, RRItems.fuel);
        if (user.getAbilities().creativeMode || !itemStack.isEmpty() || RivalRebels.infiniteAmmo)
		{
			user.setCurrentHand(hand);
			if (!user.getAbilities().invulnerable && !RivalRebels.infiniteAmmo)
			{
				itemStack.decrement(1);
				if (getMode(stack) != 2) itemStack.decrement(1);
				if (getMode(stack) != 2) itemStack.decrement(1);
				if (getMode(stack) == 0) itemStack.decrement(1);
				if (getMode(stack) == 0) itemStack.decrement(1);
                if (itemStack.isEmpty())
                    user.getInventory().removeOne(itemStack);
			}
			if (stack.hasEnchantments() && !world.isClient)
			{
				world.spawnEntity(new EntityFlameBallGreen(world, user, world.random.nextFloat() + 1.0f));
			}
		}
		else if (!world.isClient)
		{
			user.sendMessage(Text.of("§cOut of fuel"), false);
		}
		if (message && world.isClient)
		{
			user.sendMessage(Text.of(I18n.translate("RivalRebels.Orders")+" "+I18n.translate("RivalRebels.message.use")+" [R]."), false);
			message = false;
		}
		return TypedActionResult.pass(stack);
	}
	boolean message = true;

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
		World world = player.world;
		if (!world.isClient)
		{
            if (world.random.nextInt(10) == 0 && !player.isInsideWaterOrBubbleColumn()) {
                RivalRebelsSoundPlayer.playSound(player, 8, 0, 0.03f);
                if (world.random.nextInt(3) == 0 && !player.isInsideWaterOrBubbleColumn()) {
                    RivalRebelsSoundPlayer.playSound(player, 8, 1, 0.1f);
                }
            }
            if (!stack.hasEnchantments()) {
                switch (getMode(stack)) {
                    case 0:
                        for (int i = 0; i < 4; i++)
                            world.spawnEntity(new EntityFlameBall2(world, player, world.random.nextFloat() + 0.5f));
                        break;
                    case 1:
                        world.spawnEntity(new EntityFlameBall1(world, player, 1));
                        break;
                    case 2:
                        world.spawnEntity(new EntityFlameBall(world, player, 1));
                        break;
                }
            }
        }
	}

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (!stack.getOrCreateNbt().getBoolean("isReady"))
		{
			stack.getNbt().putBoolean("isReady", true);
			stack.getNbt().putInt("mode", 2);
		}
		if (entity instanceof PlayerEntity player) {
            if (selected && world.random.nextInt(10) == 0 && !player.isInsideWaterOrBubbleColumn()) {
                RivalRebelsSoundPlayer.playSound(player, 8, 0, 0.03f);
            }
		}
        if (world.isClient) openGui(stack);
	}

	public void openGui(ItemStack item) {
		if (ClientProxy.USE_KEY.isPressed() && MinecraftClient.getInstance().currentScreen == null)
		{
			RivalRebels.proxy.flamethrowerGui(getMode(item));
		}
	}

	public int getMode(ItemStack item) {
        return item.getOrCreateNbt().getInt("mode");
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:ae");
	}*/
}
