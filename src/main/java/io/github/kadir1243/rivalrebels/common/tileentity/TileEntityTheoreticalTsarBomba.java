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
package io.github.kadir1243.rivalrebels.common.tileentity;

import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.RivalRebels;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.block.trap.BlockTheoreticalTsarBomba;
import io.github.kadir1243.rivalrebels.common.container.ContainerTheoreticalTsar;
import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.entity.EntityTheoreticalTsar;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.item.components.ChipData;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsTeam;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class TileEntityTheoreticalTsarBomba extends AbstractBombBlockEntity {
    public int nuclear = 0;
    public boolean hasAntennae = false;

    public TileEntityTheoreticalTsarBomba(BlockPos pos, BlockState state) {
        super(RRTileEntities.THEORETICAL_TSAR_BOMB.get(), pos, state);
    }

    @Override
    public int getContainerSize() {
        return 21;
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player, 64);
    }

    public List<ItemStack> getRods() {
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 3; i <= 18; i++) {
            stacks.add(getItem(i));
        }
        return stacks;
    }

    @Override
    public void setChanged() {
        super.setChanged();

        hasTrollface = false;
        nuclear = 0;
        for (ItemStack rod : getRods()) {
            if (rod.is(RRItems.NUCLEAR_ROD)) {
                nuclear++;
            }
            hasTrollface |= rod.is(RRItems.trollmask);
        }

        megaton = nuclear * 6.25F;

        ItemStack chipSlotStack = getItem(20);
        hasChip = chipSlotStack.is(RRItems.chip);
        if (hasChip && chipSlotStack.has(RRComponents.CHIP_DATA)) {
            ChipData chipData = chipSlotStack.get(RRComponents.CHIP_DATA);
            rrteam = chipData.team();
            player = chipData.gameProfile();
        }

        hasAntennae = getItem(1).is(RRItems.antenna) && getItem(2).is(RRItems.antenna);

        hasExplosive = getItem(19).is(RRBlocks.timedbomb.asItem());
    }
    public static void tick(Level level, BlockPos blockPos, BlockState blockState, TileEntityTheoreticalTsarBomba blockEntity) {
        boolean sp;
        if (level.isClientSide()) {
            sp = Minecraft.getInstance().isLocalServer();
        } else {
            MinecraftServer server = level.getServer();
            sp = server.getPlayerCount() == 1;
        }

        if (blockEntity.hasFuse && blockEntity.hasExplosive && blockEntity.hasAntennae && blockEntity.hasChip) {
            double dist = 1000000;

            if (!sp || RRConfig.SERVER.isStopSelfnukeinSP()) {
                if (blockEntity.rrteam == RivalRebelsTeam.OMEGA) {
                    dist = blockPos.distToLowCornerSqr(RivalRebels.round.omegaData.objPos().getX(), blockPos.getY(), RivalRebels.round.omegaData.objPos().getZ());
                }
                if (blockEntity.rrteam == RivalRebelsTeam.SIGMA) {
                    dist = blockPos.distToLowCornerSqr(RivalRebels.round.sigmaData.objPos().getX(), blockPos.getY(), RivalRebels.round.sigmaData.objPos().getZ());
                }
            }
            if (dist > (RRConfig.SERVER.getTsarBombaStrength() + (blockEntity.nuclear * blockEntity.nuclear) + 29) * (RRConfig.SERVER.getTsarBombaStrength() + (blockEntity.nuclear * blockEntity.nuclear) + 29)) {
                if (blockEntity.countdown > 0) blockEntity.countdown--;
            } else if (!level.isClientSide()) {
                blockEntity.setItem(0, ItemStack.EMPTY);
                for (Player player : level.players()) {
                    player.sendSystemMessage(Translations.warning().append(" ").append(level.getPlayerByUUID(blockEntity.player.id()).getName().copy().withStyle(ChatFormatting.RED)));
                    player.sendSystemMessage(Translations.status().append(" ").append(blockEntity.rrteam.getBlockName()).append(" ").append(Translations.defuse()).append(blockEntity.getDefaultName()));
                }
            }
        } else {
            blockEntity.countdown = RRConfig.SERVER.getNuclearBombCountdown() * 20;
            if (RRConfig.SERVER.getNuclearBombCountdown() == 0) blockEntity.countdown = 10;
        }

        if (blockEntity.countdown == 200 && !level.isClientSide() && RRConfig.SERVER.getNuclearBombCountdown() > 10) {
            Translations.sendWarningBombWillExplodeMessageToPlayers(level);
        }

        if (blockEntity.countdown % 20 == 0 && blockEntity.countdown <= 200 && RRConfig.SERVER.getNuclearBombCountdown() > 10)
            level.playSound(null, blockPos, RRSounds.NUKE.get(), SoundSource.BLOCKS, 100, 1);

        if (blockEntity.countdown == 0 && blockEntity.nuclear != 0 && !level.isClientSide()) {
            level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL | Block.UPDATE_SKIP_BLOCK_ENTITY_SIDEEFFECTS);
            level.setSkyFlashTime(2);
            float pitch = 0;
            float yaw = blockState.getValue(BlockTheoreticalTsarBomba.FACING).toYRot();

            EntityTheoreticalTsar tsar = new EntityTheoreticalTsar(level, blockPos.getX() + 0.5f, blockPos.getY() + 1f, blockPos.getZ() + 0.5f, yaw, pitch, blockEntity.nuclear, blockEntity.hasTrollface);
            level.addFreshEntity(tsar);
        }

        if (blockEntity.countdown == 0 && blockEntity.nuclear == 0) {
            explodeEmpty(level, blockPos);
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new ContainerTheoreticalTsar(containerId, inventory, this, containerData);
    }

    private final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> countdown;
                case 1 -> (int) (megaton * 100);
                case 2 -> hasExplosive ? 1 : 0;
                case 3 -> hasFuse ? 1 : 0;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> countdown = value;
                case 1 -> megaton = value / 100F;
                case 2 -> hasExplosive = value == 1;
                case 3 -> hasFuse = value == 1;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

}
