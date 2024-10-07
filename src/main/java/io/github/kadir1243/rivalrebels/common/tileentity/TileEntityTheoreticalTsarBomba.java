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
import io.github.kadir1243.rivalrebels.RRIdentifiers;
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
import com.mojang.authlib.GameProfile;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TileEntityTheoreticalTsarBomba extends BaseContainerBlockEntity implements Tickable {
	public GameProfile player = null;
	public RivalRebelsTeam	rrteam			= null;
	private NonNullList<ItemStack> chestContents	= NonNullList.withSize(36, ItemStack.EMPTY);
	public int				countdown		= RRConfig.SERVER.getNuclearBombCountdown() * 20;
	public int				nuclear			= 0;
	public boolean			hasAntennae		= false;
	public boolean			hasExplosive	= false;
	public boolean			hasFuse			= false;
	public boolean			hasChip			= false;
	public boolean			hasTrollface	= false;
	public float			megaton			= 0;

    public TileEntityTheoreticalTsarBomba(BlockPos pos, BlockState state) {
        super(RRTileEntities.THEORETICAL_TSAR_BOMB.get(), pos, state);
    }

    @Override
	public int getContainerSize()
	{
		return 21;
	}

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);

        ContainerHelper.loadAllItems(nbt, this.chestContents, provider);
	}

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(nbt, provider);
		ContainerHelper.saveAllItems(nbt, this.chestContents, provider);
    }

    @Override
	public int getMaxStackSize()
	{
		return 1;
	}

    @Override
	public boolean stillValid(@NotNull Player player)
	{
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

        hasFuse = getItem(0).is(RRItems.fuse);

        hasChip = getItem(20).is(RRItems.chip);
        if (hasChip && getItem(20).has(RRComponents.CHIP_DATA)) {
            ChipData chipData = getItem(20).get(RRComponents.CHIP_DATA);
            rrteam = chipData.team();
            player = chipData.gameProfile();
        }

        hasAntennae = getItem(1).is(RRItems.antenna) && getItem(2).is(RRItems.antenna);

        hasExplosive = !getItem(19).is(RRBlocks.timedbomb.asItem());
    }

    @Override
	public void tick() {
		boolean sp;
        if (level.isClientSide()) {
            sp = Minecraft.getInstance().isLocalServer();
        } else {
            MinecraftServer server = level.getServer();
            sp = server.getPlayerCount() == 1;
        }

		if (hasFuse && hasExplosive && hasAntennae && hasChip)
		{
			double dist = 1000000;

			if (!sp || RRConfig.SERVER.isStopSelfnukeinSP())
			{
				if (rrteam == RivalRebelsTeam.OMEGA)
				{
					dist = getBlockPos().distToLowCornerSqr(RivalRebels.round.omegaData.objPos().getX(), getBlockPos().getY(), RivalRebels.round.omegaData.objPos().getZ());
				}
				if (rrteam == RivalRebelsTeam.SIGMA)
				{
					dist = getBlockPos().distToLowCornerSqr(RivalRebels.round.sigmaData.objPos().getX(), getBlockPos().getY(), RivalRebels.round.sigmaData.objPos().getZ());
				}
			}
			if (dist > (RRConfig.SERVER.getTsarBombaStrength() + (nuclear * nuclear) + 29) * (RRConfig.SERVER.getTsarBombaStrength() + (nuclear * nuclear) + 29))
			{
				if (countdown > 0) countdown--;
			}
			else if (!level.isClientSide())
			{
				this.setItem(0, ItemStack.EMPTY);
                for (Player player : level.players()) {
                    player.displayClientMessage(Translations.warning().append(" ").append(getLevel().getPlayerByUUID(this.player.getId()).getName().copy().withStyle(ChatFormatting.RED)), false);
                    player.displayClientMessage(Component.translatable(RRIdentifiers.MODID + ".tsar_bomb_defuse", rrteam == RivalRebelsTeam.OMEGA ? RRBlocks.omegaobj.get().getName() : rrteam == RivalRebelsTeam.SIGMA ? RRBlocks.sigmaobj.get().getName() : Component.nullToEmpty("NONE")), false);
                }
			}
		}
		else
		{
			countdown = RRConfig.SERVER.getNuclearBombCountdown() * 20;
			if (RRConfig.SERVER.getNuclearBombCountdown() == 0) countdown = 10;
		}

		if (countdown == 200 && !level.isClientSide && RRConfig.SERVER.getNuclearBombCountdown() > 10)
		{
            Translations.sendWarningBombWillExplodeMessageToPlayers(getLevel());
		}

		if (countdown % 20 == 0 && countdown <= 200 && RRConfig.SERVER.getNuclearBombCountdown() > 10) level.playSound(null, getBlockPos(), RRSounds.NUKE.get(), SoundSource.BLOCKS, 100, 1);

		if (countdown == 0 && nuclear != 0 && !level.isClientSide())
		{
            level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
			level.setSkyFlashTime(2);
			float pitch = 0;
			float yaw = this.getBlockState().getValue(BlockTheoreticalTsarBomba.FACING).toYRot();

            EntityTheoreticalTsar tsar = new EntityTheoreticalTsar(level, getBlockPos().getX()+0.5f, getBlockPos().getY()+1f, getBlockPos().getZ()+0.5f, yaw, pitch, nuclear, hasTrollface);
			level.addFreshEntity(tsar);
		}

		if (countdown == 0 && nuclear == 0)
		{
            level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
			level.explode(null, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), 4, Level.ExplosionInteraction.NONE);
		}
    }

    @Override
    protected Component getDefaultName() {
        return Component.literal("Theoretical Tsar Bomba");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.chestContents;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.chestContents = items;
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
