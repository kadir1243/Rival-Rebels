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
import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityLaserBurst;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ItemAstroBlaster extends ToolItem {
	boolean	isA	= true;

	public ItemAstroBlaster() {
		super(ToolMaterials.DIAMOND, new Settings().maxCount(1));
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
			if (world.isClient) stack.setRepairCost(1);
			player.setCurrentHand(hand);
			RivalRebelsSoundPlayer.playSound(player, 12, 0, 0.7f, 0.7f);
		} else if (!world.isClient) {
			player.sendMessage(Text.of("§cNot enough redstone rods"), false);
		}
		return TypedActionResult.success(stack, world.isClient);
	}

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity player)) return;

        if (remainingUseTicks < 1980 && !world.isClient) {
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

			if (isA) world.playSoundFromEntity(player, RRSounds.BLASTER_MESSAGE_FROM_OTHER_PLANETS, SoundCategory.PLAYERS, 0.5f, 0.3f);
			else world.playSoundFromEntity(player, RRSounds.BLASTER_MESSAGE_FROM_OTHER_PLANETS2, SoundCategory.PLAYERS, 0.4f, 1.7f);

			isA = !isA;
			world.spawnEntity(new EntityLaserBurst(world, player, stack.hasEnchantments()));
		}
		else if (world.isClient)
		{
			stack.setRepairCost((2000 - remainingUseTicks) + 1);
		}
	}

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (world.isClient) stack.setRepairCost(0);
	}
}
