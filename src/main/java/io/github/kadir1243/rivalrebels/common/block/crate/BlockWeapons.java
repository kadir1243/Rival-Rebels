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
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
        if (level.isClientSide()) {
            player.displayClientMessage(Component.translatable("RivalRebels.Inventory"), false);
            player.displayClientMessage(Component.literal(RRItems.rpg.get().getDescription().copy().withStyle(ChatFormatting.GREEN) + ". §9(" + Component.translatable("RivalRebels.consume") + " " + (RRItems.rocket.get().getDescription()) + ")"), false);
            player.displayClientMessage(Component.literal(RRItems.tesla.get().getDescription().copy().withStyle(ChatFormatting.GREEN) + ". §9(" + Component.translatable("RivalRebels.consume") + " " + (RRItems.hydrod.get().getDescription()) + ")"), false);
            player.displayClientMessage(Component.literal((RRItems.flamethrower.get().getDescription().copy().withStyle(ChatFormatting.GREEN)) + ". §9(" + Component.translatable("RivalRebels.consume") + " " + (RRItems.fuel.asItem().getDescription()) + ")"), false);
            player.displayClientMessage(Component.literal((RRItems.plasmacannon.get().getDescription().copy().withStyle(ChatFormatting.GREEN)) + ". §9(" + Component.translatable("RivalRebels.consume") + " " + (RRItems.battery.asItem().getDescription()) + ")"), false);
            player.displayClientMessage(Component.literal((RRItems.einsten.get().getDescription().copy().withStyle(ChatFormatting.GREEN)) + ". §9(" + Component.translatable("RivalRebels.consume") + " " + (RRItems.redrod.asItem().getDescription()) + ")"), false);
            player.displayClientMessage(Component.literal((RRItems.roddisk.get().getDescription().copy().withStyle(ChatFormatting.GREEN)) + ". §9(" + Component.translatable("RivalRebels.message.use") + " /rr)"), false);
            // player.sendMessage(Text.literal("§a" + I18n.translate(RivalRebels.bastion.getTranslationKey() + ".name") + ". §9(" +
            // I18n.translate("RivalRebels.build") + " " + I18n.translate(RivalRebels.barricade.getTranslationKey() + ".name") + ")");
            // player.sendMessage(Text.literal("§a" + I18n.translate(RivalRebels.tower.getTranslationKey() + ".name") + ". §9(" +
            // I18n.translate("RivalRebels.build") + " " + I18n.translate(RivalRebels.tower.getTranslationKey() + ".name") + ")");
            player.displayClientMessage((RRItems.knife.get().getDescription().copy().withStyle(ChatFormatting.GREEN).append(". §9(").append(Component.translatable("RivalRebels.opknife")).append(")")), false);
            player.displayClientMessage(Component.literal((RRItems.gasgrenade.get().getDescription().copy().withStyle(ChatFormatting.GREEN)) + ". §9(" + Component.translatable("RivalRebels.chemicalweapon") + ")"), false);
            player.displayClientMessage(Translations.orders().append(" ").append(Component.translatable("RivalRebels.equipweapons")), false);
        }
        if (!level.isClientSide())
        {
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
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
        return InteractionResult.PASS;
    }

}
