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
package io.github.kadir1243.rivalrebels.common.block.crate;

import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BlockWeapons extends Block {
    public BlockWeapons(Properties settings) {
        super(settings);
    }

    private static Component parenthesized(MutableComponent a, Component b) {
        return Component.literal("(").withStyle(ChatFormatting.BLUE).append(a.withStyle(ChatFormatting.BLUE)).append(" ").append(b.copy().withStyle(ChatFormatting.BLUE)).append(Component.literal(")").withStyle(ChatFormatting.BLUE));
    }

    private static Component parenthesized(MutableComponent a) {
        return Component.literal("(").withStyle(ChatFormatting.BLUE).append(a.withStyle(ChatFormatting.BLUE)).append(Component.literal(")").withStyle(ChatFormatting.BLUE));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (level.isClientSide()) {
            player.sendSystemMessage(Translations.inventory());
            player.sendSystemMessage(RRItems.rpg.toStack().getItemName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(parenthesized(Translations.REQUIRES.translate(), RRItems.rocket.toStack().getItemName())));
            player.sendSystemMessage(RRItems.tesla.toStack().getItemName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(parenthesized(Translations.REQUIRES.translate(), RRItems.hydrod.toStack().getItemName())));
            player.sendSystemMessage(RRItems.flamethrower.toStack().getItemName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(parenthesized(Translations.REQUIRES.translate(), RRItems.fuel.toStack().getItemName())));
            player.sendSystemMessage(RRItems.plasmacannon.toStack().getItemName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(parenthesized(Translations.REQUIRES.translate(), RRItems.battery.toStack().getItemName())));
            player.sendSystemMessage(RRItems.einsten.toStack().getItemName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(parenthesized(Translations.REQUIRES.translate(), RRItems.redrod.toStack().getItemName())));
            player.sendSystemMessage(RRItems.roddisk.toStack().getItemName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(parenthesized(Translations.USE_MESSAGE.translate(), Component.literal("/rr"))));
            // player.sendMessage(Text.literal("§a" + I18n.translate(RivalRebels.bastion.getTranslationKey() + ".name") + ". §9(" +
            // I18n.translate("RivalRebels.build") + " " + I18n.translate(RivalRebels.barricade.getTranslationKey() + ".name") + ")");
            // player.sendMessage(Text.literal("§a" + I18n.translate(RivalRebels.tower.getTranslationKey() + ".name") + ". §9(" +
            // I18n.translate("RivalRebels.build") + " " + I18n.translate(RivalRebels.tower.getTranslationKey() + ".name") + ")");
            player.sendSystemMessage(RRItems.knife.toStack().getItemName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(parenthesized(Translations.OPS_KNIFE.translate())));
            player.sendSystemMessage(RRItems.gasgrenade.toStack().getItemName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(parenthesized(Translations.CHEMICAL_WEAPON.translate())));
            player.sendSystemMessage(Translations.orders().append(" ").append(Translations.EQUIP_WEAPONS_MESSAGE.translate()));
        } else {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            Containers.dropItemStack(level, x, y, z, RRItems.rpg.toStack());
            Containers.dropItemStack(level, x, y, z, RRItems.tesla.toStack());
            Containers.dropItemStack(level, x, y, z, RRItems.plasmacannon.toStack());
            Containers.dropItemStack(level, x, y, z, RRItems.flamethrower.toStack());
            Containers.dropItemStack(level, x, y, z, RRItems.roddisk.toStack());
            // Containers.dropItemStack(level, x, y, z, new ItemStack(RivalRebels.barricade, 6));
            // Containers.dropItemStack(level, x, y, z, new ItemStack(RivalRebels.tower, 3));
            Containers.dropItemStack(level, x, y, z, RRItems.knife.toStack(10));
            Containers.dropItemStack(level, x, y, z, RRItems.gasgrenade.toStack(6));
            Containers.dropItemStack(level, x, y, z, RRItems.einsten.toStack());
        }
        return InteractionResult.SUCCESS;
    }

}
