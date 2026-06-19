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
import io.github.kadir1243.rivalrebels.common.block.trap.BlockNuclearBomb;
import io.github.kadir1243.rivalrebels.common.container.ContainerNuclearBomb;
import io.github.kadir1243.rivalrebels.common.entity.EntityNuke;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.item.components.ChipData;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsTeam;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
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

public class TileEntityNuclearBomb extends AbstractBombBlockEntity {
    public TileEntityNuclearBomb(BlockPos pos, BlockState state) {
        super(RRTileEntities.NUCLEAR_BOMB.get(), pos, state);
    }

    @Override
	public int getContainerSize()
	{
		return 13;
	}

    public List<ItemStack> getRods() {
        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            list.add(getItem(i + 1));
            list.add(getItem(i + 6));
        }
        return list;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        hasTrollface = false;
        megaton = 0;
        for (ItemStack rod : getRods()) {
            hasTrollface |= rod.is(RRItems.trollmask);
            if (rod.is(RRItems.NUCLEAR_ROD)) {
                megaton += 2.5F;
            }
        }

        hasChip = getItem(12).is(RRItems.chip);
        if (hasChip && getItem(12).has(RRComponents.CHIP_DATA)) {
            ChipData chipData = getItem(12).get(RRComponents.CHIP_DATA);
            rrteam = chipData.team();
            player = chipData.gameProfile();
        }
        hasExplosive = getItem(11).is(RRBlocks.timedbomb.asItem());
    }

    @Override
	public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player, 64);
	}

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, TileEntityNuclearBomb blockEntity) {
        boolean sp = level.isClientSide() || (!level.isClientSide() && (level.getServer().isSingleplayer() || level.getServer().getPlayerCount() == 1));

		if (blockEntity.hasFuse && blockEntity.hasExplosive && blockEntity.hasChip)
		{
			double dist = 10000000;
			if (!sp || RRConfig.SERVER.isStopSelfnukeinSP()) {
				if (blockEntity.rrteam == RivalRebelsTeam.OMEGA) {
					dist = blockPos.distToLowCornerSqr(RivalRebels.round.omegaData.objPos().getX(), blockPos.getY(), RivalRebels.round.omegaData.objPos().getZ());
				} else if (blockEntity.rrteam == RivalRebelsTeam.SIGMA) {
					dist = blockPos.distToLowCornerSqr(RivalRebels.round.sigmaData.objPos().getX(), blockPos.getY(), RivalRebels.round.sigmaData.objPos().getZ());
				}
			}
            float squareOfCharges = Mth.square(blockEntity.megaton / 2.5F);
			if (dist > (RRConfig.SERVER.getNuclearBombStrength() + squareOfCharges + 29) * (RRConfig.SERVER.getNuclearBombStrength() + squareOfCharges + 29))
			{
				if (blockEntity.countdown > 0) blockEntity.countdown--;
			}
			else if (!level.isClientSide())
			{
                blockEntity.setItem(0, ItemStack.EMPTY);
                for (Player player : level.players()) {
                    player.sendSystemMessage(Translations.warning().append(" ").append(level.getPlayerByUUID(blockEntity.player.id()).getName().copy().withStyle(ChatFormatting.RED)));
                    player.sendSystemMessage(Translations.status().append(" ").append(blockEntity.rrteam.getBlockName()).append(" ").append(Translations.defuse()).append(blockEntity.getDefaultName()));
                }
			}
		}
		else
		{
            blockEntity.countdown = RRConfig.SERVER.getNuclearBombCountdown() * 20;
		}

		if (blockEntity.countdown == 200 && !level.isClientSide() && RRConfig.SERVER.getNuclearBombCountdown() > 10) {
            Translations.sendWarningBombWillExplodeMessageToPlayers(level);
		}

		if (blockEntity.countdown == 0 && blockEntity.megaton != 0 && !level.isClientSide())
		{
			level.setSkyFlashTime(2);
            Direction facing = blockState.getValue(BlockNuclearBomb.FACING);
            float pitch = facing.getAxis().isVertical() ? facing.toYRot() : 0;
			float yaw = facing.getAxis().isHorizontal() ? facing.toYRot() : 0;

            level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL | Block.UPDATE_SKIP_BLOCK_ENTITY_SIDEEFFECTS);
			level.addFreshEntity(new EntityNuke(level, blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f, yaw, pitch, (int) (blockEntity.megaton / 2.5), blockEntity.hasTrollface));
		}

		if (blockEntity.countdown == 0 && blockEntity.megaton == 0)
		{
            explodeEmpty(level, blockPos);
		}
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new ContainerNuclearBomb(containerId, inventory, this, containerData);
    }

    private final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> countdown;
                case 1 -> (int) (megaton * 100);
                case 2 -> hasTrollface ? 1 : 0;
                case 3 -> hasExplosive ? 1 : 0;
                case 4 -> hasFuse ? 1 : 0;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> countdown = value;
                case 1 -> megaton = value / 100F;
                case 2 -> hasTrollface = value == 1;
                case 3 -> hasExplosive = value == 1;
                case 4 -> hasFuse = value == 1;
                default -> {}
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    };

}
