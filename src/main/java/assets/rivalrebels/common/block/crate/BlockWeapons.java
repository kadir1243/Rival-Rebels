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
package assets.rivalrebels.common.block.crate;

import assets.rivalrebels.common.item.RRItems;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BlockWeapons extends Block
{
	public BlockWeapons(Properties settings)
	{
		super(settings);
	}

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (level.isClientSide) {
            player.displayClientMessage(Component.translatable("RivalRebels.Inventory"), false);
            player.displayClientMessage(Component.literal("§a" + RRItems.rpg.getDescription() + ". §9(" + I18n.get("RivalRebels.consume") + " " + (RRItems.rocket.getDescription()) + ")"), false);
            player.displayClientMessage(Component.literal("§a" + RRItems.tesla.getDescription() + ". §9(" + I18n.get("RivalRebels.consume") + " " + (RRItems.hydrod.getDescription()) + ")"), false);
            player.displayClientMessage(Component.literal("§a" + (RRItems.flamethrower.getDescription()) + ". §9(" + I18n.get("RivalRebels.consume") + " " + (RRItems.fuel.getDescription()) + ")"), false);
            player.displayClientMessage(Component.literal("§a" + (RRItems.plasmacannon.getDescription()) + ". §9(" + I18n.get("RivalRebels.consume") + " " + (RRItems.battery.getDescription()) + ")"), false);
            player.displayClientMessage(Component.literal("§a" + (RRItems.einsten.getDescription()) + ". §9(" + I18n.get("RivalRebels.consume") + " " + (RRItems.redrod.getDescription()) + ")"), false);
            player.displayClientMessage(Component.literal("§a" + (RRItems.roddisk.getDescription()) + ". §9(" + I18n.get("RivalRebels.message.use") + " /rr)"), false);
            // player.sendMessage(Text.literal("§a" + I18n.translate(RivalRebels.bastion.getTranslationKey() + ".name") + ". §9(" +
            // I18n.translate("RivalRebels.build") + " " + I18n.translate(RivalRebels.barricade.getTranslationKey() + ".name") + ")");
            // player.sendMessage(Text.literal("§a" + I18n.translate(RivalRebels.tower.getTranslationKey() + ".name") + ". §9(" +
            // I18n.translate("RivalRebels.build") + " " + I18n.translate(RivalRebels.tower.getTranslationKey() + ".name") + ")");
            player.displayClientMessage(Component.literal("§a" + (RRItems.knife.getDescription()) + ". §9(" + I18n.get("RivalRebels.opknife") + ")"), false);
            player.displayClientMessage(Component.literal("§a" + (RRItems.gasgrenade.getDescription()) + ". §9(" + I18n.get("RivalRebels.chemicalweapon") + ")"), false);
            player.displayClientMessage(Component.literal(I18n.get("RivalRebels.Orders") + " " + I18n.get("RivalRebels.equipweapons")), false);
        }
        if (!level.isClientSide)
        {
            ItemEntity ei = new ItemEntity(level, x + .5, y + .5, z + .5, RRItems.rpg.getDefaultInstance());
            ItemEntity ei1 = new ItemEntity(level, x + .5, y + .5, z + .5, RRItems.tesla.getDefaultInstance());
            ItemEntity ei2 = new ItemEntity(level, x + .5, y + .5, z + .5, RRItems.plasmacannon.getDefaultInstance());
            ItemEntity ei3 = new ItemEntity(level, x + .5, y + .5, z + .5, RRItems.flamethrower.getDefaultInstance());
            ItemEntity ei4 = new ItemEntity(level, x + .5, y + .5, z + .5, RRItems.roddisk.getDefaultInstance());
            // ItemEntity ei5 = new ItemEntity(level, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.barricade, 6));
            // ItemEntity ei6 = new ItemEntity(level, x + .5, y + .5, z + .5, new ItemStack(RivalRebels.tower, 3));
            ItemEntity ei7 = new ItemEntity(level, x + .5, y + .5, z + .5, new ItemStack(RRItems.knife, 10));
            ItemEntity ei8 = new ItemEntity(level, x + .5, y + .5, z + .5, new ItemStack(RRItems.gasgrenade, 6));
            ItemEntity ei9 = new ItemEntity(level, x + .5, y + .5, z + .5, RRItems.einsten.getDefaultInstance());
            level.addFreshEntity(ei);
            level.addFreshEntity(ei1);
            level.addFreshEntity(ei2);
            level.addFreshEntity(ei3);
            level.addFreshEntity(ei4);
            // level.spawnEntity(ei5);
            // level.spawnEntity(ei6);
            level.addFreshEntity(ei7);
            level.addFreshEntity(ei8);
            level.addFreshEntity(ei9);
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
        return InteractionResult.PASS;
    }

}
