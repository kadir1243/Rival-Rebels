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
import assets.rivalrebels.client.itemrenders.TeslaRenderer;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityRaytrace;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public class ItemTesla extends ToolItem {
	public ItemTesla() {
		super(ToolMaterials.DIAMOND, new Settings().maxCount(1).group(RRItems.rralltab));
	}
    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public BuiltinModelItemRenderer getItemStackRenderer() {
                return new TeslaRenderer(MinecraftClient.getInstance().getBlockEntityRenderDispatcher(), MinecraftClient.getInstance().getEntityModelLoader());
            }
        });
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
		return 20;
	}

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);
        int degree = getDegree(stack);
		float chance = Math.abs(degree - 90) / 90f;
        ItemStack battery = ItemUtil.getItemStack(player, RRItems.battery);
        if (player.getAbilities().invulnerable || !battery.isEmpty() || RivalRebels.infiniteAmmo)
		{
			if (!player.getAbilities().invulnerable && !RivalRebels.infiniteAmmo)
			{
                battery.decrement(1);
				if (chance > 0.33333) battery.decrement(1);
				if (chance > 0.66666) battery.decrement(1);
                if (battery.isEmpty()) {
                    player.getInventory().removeOne(battery);
                }
            }
			player.setCurrentHand(hand);
		}
		else if (!world.isClient)
		{
			player.sendMessage(Text.of("§cOut of batteries"), false);
		}
		if (message && world.isClient)
		{
			player.sendMessage(new TranslatableText("RivalRebels.Orders").append(" ").append(new TranslatableText("RivalRebels.message.use")).append(" [R]."), false);
			message = false;
		}
		return TypedActionResult.success(stack, world.isClient);
	}
	boolean message = true;

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) {
            if (ClientProxy.USE_KEY.isPressed() && selected && MinecraftClient.getInstance().currentScreen == null) {
                RivalRebels.proxy.teslaGui(getDegree(stack));
            }
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
		if (player.isWet() && !player.isInvulnerableTo(RivalRebelsDamageSource.electricity)) {
			player.damage(RivalRebelsDamageSource.electricity, 2);
		}
		World world = player.world;
		if (player.world.random.nextInt(10) == 0) RivalRebelsSoundPlayer.playSound(player, 25, 1);

		int degree = getDegree(stack);
		float chance = Math.abs(degree - 90) / 90f;
		if (degree - 90 > 0) chance /= 10f;

		float dist = 7 + (1 - (degree / 180f)) * 73;

		float randomness = degree / 720f;

		int num = (degree / 25) + 1;

		if (!world.isClient) for (int i = 0; i < num; i++)
			world.spawnEntity(new EntityRaytrace(world, player, dist, randomness, chance, !stack.hasEnchantments()));
	}

	public int getDegree(ItemStack item)
	{
		if (!item.hasNbt()) return 0;
		else return item.getNbt().getInt("dial") + 90;
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:ax");
	}*/
}
