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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ItemChip extends Item {
	public ItemChip()
	{
		super(new Settings().maxCount(1));
	}

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (RivalRebels.round.isStarted() && !stack.getOrCreateNbt().getBoolean("isReady") && entity instanceof PlayerEntity player) {
            stack.getNbt().putUuid("player", player.getUuid());
            stack.getNbt().putString("username", player.getName().getString());
			stack.getNbt().putInt("team", RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile()).rrteam.ordinal());
			stack.getNbt().putBoolean("isReady", true);
		}
	}

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        Hand hand = context.getHand();
		player.swingHand(hand);
		if (!world.isClient)
		{
			if (world.getBlockState(pos).getBlock() == RRBlocks.buildrhodes)
			{
				world.setBlockState(pos.west(), RRBlocks.steel.getDefaultState());
				world.setBlockState(pos.east(), RRBlocks.steel.getDefaultState());
				world.setBlockState(pos.up(), RRBlocks.conduit.getDefaultState());
				world.setBlockState(pos.west().up(), RRBlocks.steel.getDefaultState());
				world.setBlockState(pos.east().up(), RRBlocks.steel.getDefaultState());
				world.setBlockState(pos.up(2), RRBlocks.steel.getDefaultState());
				world.setBlockState(pos.west().up(2), RRBlocks.steel.getDefaultState());
				world.setBlockState(pos.east().up(2), RRBlocks.steel.getDefaultState());
				if (world.getBlockState(pos.down()).getBlock() == RRBlocks.buildrhodes)
				{
					world.setBlockState(pos, RRBlocks.conduit.getDefaultState());
					world.setBlockState(pos.down(), RRBlocks.rhodesactivator.getDefaultState());
					world.setBlockState(pos.west().down(), RRBlocks.steel.getDefaultState());
					world.setBlockState(pos.east().down(), RRBlocks.steel.getDefaultState());
				}
				else
				{
					world.setBlockState(pos, RRBlocks.rhodesactivator.getDefaultState());
				}
				return ActionResult.PASS;
			}
		}
		return ActionResult.success(world.isClient);
	}

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if (stack.hasNbt()) {
			tooltip.add(Text.of(RivalRebelsTeam.getForID(stack.getNbt().getInt("team")).name()));
            UUID player = stack.getNbt().getUuid("player");
            tooltip.add(Text.of("Player Of UUID: " + player.toString()));
		}
	}
}
