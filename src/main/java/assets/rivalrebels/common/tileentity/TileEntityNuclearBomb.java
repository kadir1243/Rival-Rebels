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
package assets.rivalrebels.common.tileentity;

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.block.trap.BlockNuclearBomb;
import assets.rivalrebels.common.container.ContainerNuclearBomb;
import assets.rivalrebels.common.entity.EntityNuke;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.item.components.ChipData;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import assets.rivalrebels.common.util.Translations;
import com.mojang.authlib.GameProfile;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TileEntityNuclearBomb extends BlockEntity implements Container, Tickable, MenuProvider {
	public GameProfile player = null;
	public RivalRebelsTeam	rrteam			= null;
	private final NonNullList<ItemStack> chestContents	= NonNullList.withSize(36, ItemStack.EMPTY);

    public int				Countdown		= RRConfig.SERVER.getNuclearBombCountdown() * 20;

	public int				AmountOfCharges	= 0;
	public boolean			hasTrollface	= false;
	public boolean			hasExplosive	= false;
	public boolean			hasFuse			= false;
	public boolean			hasChip			= false;

    public TileEntityNuclearBomb(BlockPos pos, BlockState state) {
        super(RRTileEntities.NUCLEAR_BOMB, pos, state);
    }

    @Override
	public int getContainerSize()
	{
		return 13;
	}

    @Override
	public ItemStack getItem(int slot) {
		return this.chestContents.get(slot);
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
	public ItemStack removeItem(int slot, int amount) {
        if (!this.getItem(slot).isEmpty()) {
			ItemStack stack;

			if (this.getItem(slot).getCount() <= amount) {
				stack = this.getItem(slot);
				this.setItem(slot, ItemStack.EMPTY);
            } else {
				stack = this.getItem(slot).split(amount);

				if (this.getItem(slot).isEmpty()) {
					this.setItem(slot, ItemStack.EMPTY);
				}
            }
            return stack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
		if (!this.getItem(index).isEmpty()) {
			ItemStack oldStack = this.getItem(index);
			this.setItem(index, ItemStack.EMPTY);
			return oldStack;
		}
        return ItemStack.EMPTY;
	}

	@Override
	public void setItem(int index, ItemStack stack) {
		this.chestContents.set(index, stack);

        stack.limitSize(this.getMaxStackSize(stack));
        setChanged();
	}

    @Override
    public void setChanged() {
        super.setChanged();
        hasTrollface = false;
        AmountOfCharges = 0;
        for (ItemStack rod : getRods()) {
            hasTrollface |= rod.is(RRItems.trollmask);
            if (rod.is(RRItems.NUCLEAR_ROD)) {
                AmountOfCharges++;
            }
        }
        hasFuse = getItem(0).is(RRItems.fuse);
        hasChip = getItem(12).is(RRItems.chip);
        if (hasChip) {
            ChipData chipData = getItem(12).get(RRComponents.CHIP_DATA);
            rrteam = chipData.team();
            player = chipData.gameProfile();
        }
        hasExplosive = getItem(11).is(RRBlocks.timedbomb.asItem());
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
	public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player, 64);
	}

    @Override
	public void tick() {
        boolean sp = level.isClientSide || (!level.isClientSide && (level.getServer().isSingleplayer() || level.getServer().getPlayerCount() == 1));

		if (hasFuse && hasExplosive && hasChip)
		{
			double dist = 10000000;
			if (!sp || RRConfig.SERVER.isStopSelfnukeinSP()) {
				if (rrteam == RivalRebelsTeam.OMEGA) {
					dist = getBlockPos().distToLowCornerSqr(RivalRebels.round.omegaData.objPos().getX(), getBlockPos().getY(), RivalRebels.round.omegaData.objPos().getZ());
				} else if (rrteam == RivalRebelsTeam.SIGMA) {
					dist = getBlockPos().distToLowCornerSqr(RivalRebels.round.sigmaData.objPos().getX(), getBlockPos().getY(), RivalRebels.round.sigmaData.objPos().getZ());
				}
			}
			if (dist > (RRConfig.SERVER.getNuclearBombStrength() + (AmountOfCharges * AmountOfCharges) + 29) * (RRConfig.SERVER.getNuclearBombStrength() + (AmountOfCharges * AmountOfCharges) + 29))
			{
				if (Countdown > 0) Countdown--;
			}
			else if (!level.isClientSide())
			{
				this.setItem(0, ItemStack.EMPTY);
                for (Player player : level.players()) {
                    player.displayClientMessage(Translations.warning().append(" ").append(level.getPlayerByUUID(player.getUUID()).getName().copy().withStyle(ChatFormatting.RED)), false);
                    player.displayClientMessage(Component.translatable(RRIdentifiers.MODID + ".nuke_bomb_defuse", rrteam == RivalRebelsTeam.OMEGA ? RRBlocks.omegaobj.getName() : rrteam == RivalRebelsTeam.SIGMA ? RRBlocks.sigmaobj.getName() : Component.nullToEmpty("NONE")), false);
                }
			}
		}
		else
		{
			Countdown = RRConfig.SERVER.getNuclearBombCountdown() * 20;
		}

		if (Countdown == 200 && !level.isClientSide && RRConfig.SERVER.getNuclearBombCountdown() > 10) {
            Translations.sendWarningBombWillExplodeMessageToPlayers(getLevel());
		}

		if (Countdown == 0 && AmountOfCharges != 0 && !level.isClientSide())
		{
			level.setSkyFlashTime(2);
            Direction facing = this.getBlockState().getValue(BlockNuclearBomb.FACING);
            float pitch = facing.getAxis().isVertical() ? facing.toYRot() : 0;
			float yaw = facing.getAxis().isHorizontal() ? facing.toYRot() : 0;

			level.addFreshEntity(new EntityNuke(level, getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, yaw, pitch, AmountOfCharges, hasTrollface));
			level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
		}

		if (Countdown == 0 && AmountOfCharges == 0)
		{
			level.explode(null, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), 4, Level.ExplosionInteraction.BLOCK);
			level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
		}
    }

    @Override
    public Component getDisplayName() {
        return Component.nullToEmpty("Nuclear Bomb");
    }

    @Override
    public boolean isEmpty() {
        return this.chestContents.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public void clearContent() {
        this.chestContents.clear();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new ContainerNuclearBomb(syncId, inv, this, containerData);
    }

    private final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> Countdown;
                case 1 -> AmountOfCharges;
                case 2 -> hasTrollface ? 1 : 0;
                case 3 -> hasExplosive ? 1 : 0;
                case 4 -> hasFuse ? 1 : 0;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> Countdown = value;
                case 1 -> AmountOfCharges = value;
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
