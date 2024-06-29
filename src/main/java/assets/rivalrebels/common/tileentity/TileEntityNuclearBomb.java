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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.block.trap.BlockNuclearBomb;
import assets.rivalrebels.common.container.ContainerNuclearBomb;
import assets.rivalrebels.common.entity.EntityNuke;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.item.components.ChipData;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TileEntityNuclearBomb extends BlockEntity implements Container, Tickable, ExtendedScreenHandlerFactory<TileEntityNuclearBomb.BombData> {
	public String			username		= null;
	public RivalRebelsTeam	rrteam			= null;
	private final NonNullList<ItemStack> chestContents	= NonNullList.withSize(36, ItemStack.EMPTY);

    public int				Countdown		= RivalRebels.nuclearBombCountdown * 20;

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

		if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
			stack.setCount(this.getMaxStackSize());
		}
        setChanged();
	}

    @Override
    public void setChanged() {
        super.setChanged();
        hasTrollface = false;
        AmountOfCharges = 0;
        for (ItemStack rod : getRods()) {
            hasTrollface |= rod.is(RRItems.trollmask);
            if (rod.is(RRItems.nuclearelement)) {
                AmountOfCharges++;
            }
        }
        hasFuse = getItem(0).is(RRItems.fuse);
        hasChip = getItem(12).is(RRItems.chip);
        if (hasChip) {
            ChipData chipData = getItem(12).get(RRComponents.CHIP_DATA);
            rrteam = chipData.team();
            username = chipData.username();
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
			if (!sp || RivalRebels.stopSelfnukeinSP) {
				if (rrteam == RivalRebelsTeam.OMEGA) {
					dist = getBlockPos().distToLowCornerSqr(RivalRebels.round.omegaObjPos.getX(), getBlockPos().getY(), RivalRebels.round.omegaObjPos.getZ());
				} else if (rrteam == RivalRebelsTeam.SIGMA) {
					dist = getBlockPos().distToLowCornerSqr(RivalRebels.round.sigmaObjPos.getX(), getBlockPos().getY(), RivalRebels.round.sigmaObjPos.getZ());
				}
			}
			if (dist > (RivalRebels.nuclearBombStrength + (AmountOfCharges * AmountOfCharges) + 29) * (RivalRebels.nuclearBombStrength + (AmountOfCharges * AmountOfCharges) + 29))
			{
				if (Countdown > 0) Countdown--;
			}
			else if (!level.isClientSide)
			{
				this.setItem(0, ItemStack.EMPTY);
                for (Player player : level.players()) {
                    player.displayClientMessage(Component.translatable(RivalRebels.MODID + ".warning_to_specific_player", username), false);
                    player.displayClientMessage(Component.translatable(RivalRebels.MODID + ".nuke_bomb_defuse", rrteam == RivalRebelsTeam.OMEGA ? RRBlocks.omegaobj.getName() : rrteam == RivalRebelsTeam.SIGMA ? RRBlocks.sigmaobj.getName() : Component.nullToEmpty("NONE")), false);
                }
			}
		}
		else
		{
			Countdown = RivalRebels.nuclearBombCountdown * 20;
		}

		if (Countdown == 200 && !level.isClientSide && RivalRebels.nuclearBombCountdown > 10) {
            MutableComponent line1 = Component.translatable(RivalRebels.MODID + ".warning_bomb_will_explode_line_1");
            MutableComponent line2 = Component.translatable(RivalRebels.MODID + ".warning_bomb_will_explode_line_2");
            MutableComponent line3 = Component.translatable(RivalRebels.MODID + ".warning_bomb_will_explode_line_3");
            for (Player player : level.players()) {
                player.displayClientMessage(line1, false);
                player.displayClientMessage(line2, false);
                player.displayClientMessage(line3, false);
            }
		}

		if (Countdown == 0 && AmountOfCharges != 0 && !level.isClientSide)
		{
			level.setSkyFlashTime(2);
			float pitch = 0;
			float yaw = 0;
			switch(this.getBlockState().getValue(BlockNuclearBomb.META))
			{
			default:
				pitch = -90;
				break;
			case 1:
				pitch = 90;
				break;
			case 2:
				yaw = 180;
				break;
			case 3:
				yaw = 0;
				break;
			case 4:
				yaw = 270;
				break;
			case 5:
				yaw = 90;
				break;
			}

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
        return new ContainerNuclearBomb(syncId, inv, this, getScreenOpeningData(null));
    }

    public record BombData(int countdown, int amountOfCharges, boolean hasTrollFace, boolean isArmed) {
        public static final StreamCodec<FriendlyByteBuf, BombData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, BombData::countdown,
            ByteBufCodecs.INT, BombData::amountOfCharges,
            ByteBufCodecs.BOOL, BombData::hasTrollFace,
            ByteBufCodecs.BOOL, BombData::isArmed,
            BombData::new
        );
    }

    @Override
    public BombData getScreenOpeningData(ServerPlayer player) {
        return new BombData(Countdown, AmountOfCharges, hasTrollface, hasExplosive && hasFuse);
    }
}
