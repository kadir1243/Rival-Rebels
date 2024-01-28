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
import assets.rivalrebels.client.itemrenders.AstroBlasterRenderer;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityLaserBurst;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
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

public class ItemAstroBlaster extends ToolItem {
	boolean	isA	= true;

	public ItemAstroBlaster() {
		super(ToolMaterials.DIAMOND, new Settings().group(RRItems.rralltab).maxCount(1));
	}

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public BuiltinModelItemRenderer getItemStackRenderer() {
                return new AstroBlasterRenderer(MinecraftClient.getInstance().getBlockEntityRenderDispatcher(), MinecraftClient.getInstance().getEntityModelLoader());
            }
        });
    }

	@Override
	public int getEnchantability()
	{
		return 100;
	}

	@Override
	public UseAction getUseAction(ItemStack par1ItemStack)
	{
		return UseAction.BOW;
	}

	@Override
	public int getMaxUseTime(ItemStack par1ItemStack)
	{
		return 2000;
	}

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        ItemStack itemStack = ItemUtil.getItemStack(player, RRItems.redrod);
        if (player.getAbilities().invulnerable || !itemStack.isEmpty() || RivalRebels.infiniteAmmo) {
			if (player.world.isClient) stack.setRepairCost(1);
			player.setCurrentHand(hand);
			RivalRebelsSoundPlayer.playSound(player, 12, 0, 0.7f, 0.7f);
		} else if (!world.isClient) {
			player.sendMessage(Text.of("§cNot enough redstone rods"), false);
		}
		return TypedActionResult.success(stack, world.isClient);
	}

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity user, int count) {
        if (!(user instanceof PlayerEntity player)) return;

        World world = user.world;
        if (count < 1980 && !world.isClient) {
			if (!player.getAbilities().invulnerable && !RivalRebels.infiniteAmmo) {
                ItemStack redrodStack = ItemUtil.getItemStack(player, RRItems.redrod);
                if (!redrodStack.isEmpty()) {
					redrodStack.damage(1, player, player1 -> {});
					if (redrodStack.getDamage() == redrodStack.getMaxDamage()) {
						redrodStack.decrement(1);
                        if (redrodStack.isEmpty()) {
                            player.getInventory().removeOne(redrodStack);
                        }
						player.getInventory().insertStack(RRItems.emptyrod.getDefaultStack());
					}
				} else {
					return;
				}
			}

			if (isA) RivalRebelsSoundPlayer.playSound(player, 2, 2, 0.5f, 0.3f);
			else RivalRebelsSoundPlayer.playSound(player, 2, 3, 0.4f, 1.7f);

			isA = !isA;
			world.spawnEntity(new EntityLaserBurst(world, player, stack.hasEnchantments()));
		}
		else if (world.isClient)
		{
			stack.setRepairCost((2000 - count) + 1);
		}
	}

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (world.isClient) stack.setRepairCost(0);
	}

	/*@Override
	public void registerIcons(IIconRegister iconregister)
	{
		itemIcon = iconregister.registerIcon("RivalRebels:ab");
	}*/
}
