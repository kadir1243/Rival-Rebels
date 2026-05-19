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

public class BlockAmmunition extends Block {
    public BlockAmmunition(Properties settings) {
        super(settings);
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
            player.sendSystemMessage(RRItems.rocket.toStack().getItemName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(parenthesized(Translations.AMMUNITION.translate(RRItems.rpg.toStack().getItemName().copy().withStyle(ChatFormatting.BLUE)))));
            player.sendSystemMessage(RRItems.battery.toStack().getItemName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(parenthesized(Translations.AMMUNITION.translate(RRItems.tesla.toStack().getItemName().copy().withStyle(ChatFormatting.BLUE)))));
            player.sendSystemMessage(RRItems.hydrod.toStack().getItemName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(parenthesized(Translations.AMMUNITION.translate(RRItems.plasmacannon.toStack().getItemName().copy().withStyle(ChatFormatting.BLUE)))));
            player.sendSystemMessage(RRItems.fuel.toStack().getItemName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(parenthesized(Translations.AMMUNITION.translate(RRItems.flamethrower.toStack().getItemName().copy().withStyle(ChatFormatting.BLUE)))));
            player.sendSystemMessage(RRItems.redrod.toStack().getItemName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(parenthesized(Translations.AMMUNITION.translate(RRItems.einsten.toStack().getItemName().copy().withStyle(ChatFormatting.BLUE)))));
            player.sendSystemMessage(RRItems.gasgrenade.toStack().getItemName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(parenthesized(Translations.CHEMICAL_WEAPON.translate())));
        } else {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            Containers.dropItemStack(level, x, y, z, RRItems.rocket.toStack(32));
            Containers.dropItemStack(level, x, y, z, RRItems.battery.toStack(16));
            Containers.dropItemStack(level, x, y, z, RRItems.hydrod.toStack());
            Containers.dropItemStack(level, x, y, z, RRItems.hydrod.toStack());
            Containers.dropItemStack(level, x, y, z, RRItems.hydrod.toStack());
            Containers.dropItemStack(level, x, y, z, RRItems.hydrod.toStack());
            Containers.dropItemStack(level, x, y, z, RRItems.fuel.toStack(64));
            Containers.dropItemStack(level, x, y, z, RRItems.gasgrenade.toStack(6));
            Containers.dropItemStack(level, x, y, z, RRItems.redrod.toStack());
            Containers.dropItemStack(level, x, y, z, RRItems.redrod.toStack());
            Containers.dropItemStack(level, x, y, z, RRItems.redrod.toStack());
            Containers.dropItemStack(level, x, y, z, RRItems.redrod.toStack());
            if (level.getRandom().nextInt(3) == 0) {
                Containers.dropItemStack(level, x, y, z, RRItems.NUCLEAR_ROD.toStack());
                player.sendSystemMessage(RRItems.NUCLEAR_ROD.toStack().getItemName().copy().withStyle(ChatFormatting.GREEN).append(". ").append(parenthesized(Component.literal("Used in nuclear weapons"))));
            }
        }
        return InteractionResult.SUCCESS;
    }
}
