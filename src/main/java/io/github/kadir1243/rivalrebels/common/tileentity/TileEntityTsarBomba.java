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
import io.github.kadir1243.rivalrebels.common.block.trap.BlockTsarBomba;
import io.github.kadir1243.rivalrebels.common.container.ContainerTsar;
import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.entity.EntityTsar;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityTsarBomba extends BaseContainerBlockEntity implements Tickable {
	public GameProfile player = null;
	public RivalRebelsTeam	rrteam			= null;
	private NonNullList<ItemStack> chestContents = NonNullList.withSize(36, ItemStack.EMPTY);
    public int				countdown		= RRConfig.SERVER.getNuclearBombCountdown() * 20;
	public int				nuclear			= 0;
	public int				hydrogen		= 0;
	public boolean			hasAntennae		= false;
	public boolean			hasExplosive	= false;
	public boolean			hasFuse			= false;
	public boolean			hasChip			= false;
	public boolean			hasTrollface	= false;
	public float			megaton			= 0;

    public TileEntityTsarBomba(BlockPos pos, BlockState state) {
        super(RRTileEntities.TSAR_BOMB.get(), pos, state);
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
            if (!getItem(i).isEmpty()) {
                Item item = getItem(i).getItem();
                if (i < 11 && item == RRItems.NUCLEAR_ROD.asItem()) {
                    nuclear++;
                } else if (i > 10 && item == RRItems.hydrod.asItem()) {
                    hydrogen++;
                }
                if (item == RRItems.trollmask.asItem()) {
                    hasTrollface = true;
                }
            }
        }
        if (nuclear == hydrogen) megaton = nuclear * 6.25f;

        hasFuse = getItem(0).is(RRItems.fuse);

        ItemStack chipSlotStack = getItem(20);
        hasChip = chipSlotStack.is(RRItems.chip);
        if (hasChip && chipSlotStack.has(RRComponents.CHIP_DATA)) {
            ChipData chipData = chipSlotStack.get(RRComponents.CHIP_DATA);
            rrteam = chipData.team();
            player = chipData.gameProfile();
        }

        hasAntennae = getItem(1).is(RRItems.antenna) && getItem(2).is(RRItems.antenna);

        hasExplosive = !getItem(19).isEmpty();// getStack(19).func_150998_b(RivalRebels.timedbomb);
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

		if (hasFuse && hasExplosive && nuclear == hydrogen && hasAntennae && hasChip)
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
			if (dist > (RRConfig.SERVER.getTsarBombaStrength() + (nuclear * hydrogen) + 29) * (RRConfig.SERVER.getTsarBombaStrength() + (nuclear * hydrogen) + 29))
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

		if (countdown == 200 && !level.isClientSide() && RRConfig.SERVER.getNuclearBombCountdown() > 10)
		{
            Translations.sendWarningBombWillExplodeMessageToPlayers(getLevel());
		}

		if (countdown % 20 == 0 && countdown <= 200 && RRConfig.SERVER.getNuclearBombCountdown() > 10) level.playSound(null, getBlockPos(), RRSounds.NUKE.get(), SoundSource.BLOCKS, 100, 1);

		if (countdown == 0 && nuclear != 0 && hydrogen != 0 && !level.isClientSide() && nuclear == hydrogen)
		{
			level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
			level.setSkyFlashTime(2);
			float pitch = 0;
			float yaw = this.getBlockState().getValue(BlockTsarBomba.FACING).toYRot();

            EntityTsar tsar = new EntityTsar(level, getBlockPos().getX()+0.5f, getBlockPos().getY()+1f, getBlockPos().getZ()+0.5f, yaw, pitch, hydrogen, hasTrollface);
			level.addFreshEntity(tsar);
		}

		if (countdown == 0 && nuclear == 0 && hydrogen == 0)
		{
			level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
			level.explode(null, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), 4, Level.ExplosionInteraction.NONE);
		}
    }

    @Override
    protected Component getDefaultName() {
        return Component.literal("Tsar Bomba");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new ContainerTsar(containerId, inventory, this, propertyDelegate);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.chestContents;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.chestContents = items;
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
