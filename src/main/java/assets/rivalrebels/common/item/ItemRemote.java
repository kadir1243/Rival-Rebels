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
package assets.rivalrebels.common.item;

import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.block.trap.BlockRemoteCharge;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemRemote extends Item {
	public ItemRemote() {
		super(new Settings().maxCount(1));
	}

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Hand hand = context.getHand();
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        NbtCompound tag = context.getStack().getOrCreateNbt();
        BlockPos RCpos = tag.contains("RCpos") ? BlockPos.fromLong(tag.getLong("RCpos")) : BlockPos.ORIGIN;
        if (player.getWorld().getBlockState(RCpos.up()).getBlock() == RRBlocks.remotecharge && player.isSneaking()) {
			player.swingHand(hand);
			RivalRebelsSoundPlayer.playSound(player, 22, 3);
			BlockRemoteCharge.explode(world, RCpos.up());
		}
		return super.useOnBlock(context);
	}

    //@Override
    public ActionResult onItemUseFirst(ItemStack stack, ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();

        ItemStack itemStack = ItemUtil.getItemStack(player, RRBlocks.remotecharge.asItem());
        if (((player.getAbilities().invulnerable && world.isAir(pos.up()) || !itemStack.isEmpty() && world.isAir(pos.up()))) && !player.isSneaking()) {
			RivalRebelsSoundPlayer.playSound(player, 22, 2);
			player.swingHand(hand);
			if (!world.isClient) {
				player.sendMessage(Text.of("§7[§4Orders§7] §cShift-click (Sneak) to detonate."), false);
			}
            itemStack.getOrCreateNbt().putLong("RCpos", pos.asLong());
            itemStack.decrement(1);
            if (itemStack.isEmpty()) {
                player.getInventory().removeOne(itemStack);
            }
			world.setBlockState(pos.up(), RRBlocks.remotecharge.getDefaultState());
        }
        return ActionResult.FAIL;
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = super.getDefaultStack();
        stack.getOrCreateNbt().putLong("RCpos", BlockPos.ORIGIN.asLong());
        return stack;
    }
}
