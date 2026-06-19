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
import io.github.kadir1243.rivalrebels.common.block.trap.BlockAntimatterBomb;
import io.github.kadir1243.rivalrebels.common.container.ContainerAntimatterBomb;
import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.entity.EntityAntimatterBomb;
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

public class TileEntityAntimatterBomb extends AbstractBombBlockEntity {
	public int				nuclear			= 0;
	public int				hydrogen		= 0;
	public boolean			hasAntennae		= false;

    public TileEntityAntimatterBomb(BlockPos pos, BlockState state) {
        super(RRTileEntities.ANTIMATTER_BOMB.get(), pos, state);
    }

    @Override
	public int getContainerSize()
	{
		return 21;
	}

    @Override
	public boolean stillValid(Player player)
	{
        return Container.stillValidBlockEntity(this, player, 64);
	}

    @Override
    public void setChanged() {
        super.setChanged();

        nuclear = 0;
        hydrogen = 0;
        for (int i = 3; i <= 18; i++) {
            ItemStack is = getItem(i);
            if (!is.isEmpty() && is.isEnchanted()) {
                if (i < 11 && is.is(RRItems.NUCLEAR_ROD)) {
                    nuclear++;
                } else if (i > 10 && is.is(RRItems.redrod)) {
                    hydrogen++;
                }
                if (is.is(RRItems.trollmask)) {
                    hasTrollface = true;
                }
            }
        }
        if (nuclear == hydrogen) megaton = nuclear * 6.25f;

        hasChip = getItem(20).is(RRItems.chip);
        if (hasChip && getItem(20).has(RRComponents.CHIP_DATA)) {
            ChipData chipData = getItem(20).get(RRComponents.CHIP_DATA);
            rrteam = chipData.team();
            player = chipData.gameProfile();
        }

        hasAntennae = getItem(1).is(RRItems.antenna) && getItem(2).is(RRItems.antenna);

        hasExplosive = getItem(19).is(RRBlocks.timedbomb.asItem());
    }
    public static void tick(Level level, BlockPos blockPos, BlockState blockState, TileEntityAntimatterBomb blockEntity) {
        boolean sp;
        if (level.isClientSide()) {
            sp = Minecraft.getInstance().isLocalServer();
        } else {
            MinecraftServer server = level.getServer();
            sp = server.getPlayerCount() == 1;
        }

		if (blockEntity.hasFuse && blockEntity.hasExplosive && blockEntity.nuclear == blockEntity.hydrogen && blockEntity.hasAntennae && blockEntity.hasChip)
		{
			double dist = 1000000;

			if (!sp || RRConfig.SERVER.isStopSelfnukeinSP())
			{
				if (blockEntity.rrteam == RivalRebelsTeam.OMEGA)
				{
					dist = blockPos.distToLowCornerSqr(RivalRebels.round.omegaData.objPos().getX(), blockPos.getY(), RivalRebels.round.omegaData.objPos().getZ());
				}
				if (blockEntity.rrteam == RivalRebelsTeam.SIGMA)
				{
					dist = blockPos.distToLowCornerSqr(RivalRebels.round.sigmaData.objPos().getX(), blockPos.getY(), RivalRebels.round.sigmaData.objPos().getZ());
				}
			}
			if (dist > (RRConfig.SERVER.getTsarBombaStrength() + (blockEntity.nuclear * blockEntity.hydrogen) + 29) * (RRConfig.SERVER.getTsarBombaStrength() + (blockEntity.nuclear * blockEntity.hydrogen) + 29))
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
			if (RRConfig.SERVER.getNuclearBombCountdown() == 0) blockEntity.countdown = 10;
		}

		if (blockEntity.countdown == 200 && !level.isClientSide() && RRConfig.SERVER.getNuclearBombCountdown() > 10)
		{
            Translations.sendWarningBombWillExplodeMessageToPlayers(level);
		}

		if (blockEntity.countdown % 20 == 0 && blockEntity.countdown <= 200 && RRConfig.SERVER.getNuclearBombCountdown() > 10) level.playSound(null, blockPos, RRSounds.NUKE.get(), SoundSource.BLOCKS, 100, 1);

		if (blockEntity.countdown == 0 && blockEntity.nuclear != 0 && blockEntity.hydrogen != 0 && !level.isClientSide() && blockEntity.nuclear == blockEntity.hydrogen)
		{
            level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL | Block.UPDATE_SKIP_BLOCK_ENTITY_SIDEEFFECTS);
			level.setSkyFlashTime(2);
			float pitch = 0;
			float yaw = blockState.getValue(BlockAntimatterBomb.FACING).toYRot();

            EntityAntimatterBomb tsar = new EntityAntimatterBomb(level, blockPos.getX()+0.5f, blockPos.getY()+1f, blockPos.getZ()+0.5f, yaw, pitch, blockEntity.hydrogen, blockEntity.hasTrollface);
			level.addFreshEntity(tsar);
		}

		if (blockEntity.countdown == 0 && blockEntity.nuclear == 0 && blockEntity.hydrogen == 0)
		{
            explodeEmpty(level, blockPos);
		}
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new ContainerAntimatterBomb(containerId, inventory, this, propertyDelegate);
    }

    private final ContainerData propertyDelegate = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> countdown;
                case 1 -> nuclear != hydrogen ? 1 : 0;
                case 2 -> hasExplosive && hasFuse && hasAntennae ? 1 : 0;
                case 3 -> (int) (megaton * 100);
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> countdown = value;
                case 3 -> megaton = value / 100F;
                default -> {}
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

}
