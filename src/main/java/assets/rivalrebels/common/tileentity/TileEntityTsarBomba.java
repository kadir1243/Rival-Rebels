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
import assets.rivalrebels.common.block.trap.BlockTsarBomba;
import assets.rivalrebels.common.container.ContainerTsar;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityTsar;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.item.components.ChipData;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TileEntityTsarBomba extends BlockEntity implements Container, Tickable, MenuProvider
{
	public String			username		= null;
	public RivalRebelsTeam	rrteam			= null;
	private final NonNullList<ItemStack> chestContents	= NonNullList.withSize(36, ItemStack.EMPTY);
    public int				countdown		= RivalRebels.nuclearBombCountdown * 20;
	public int				nuclear			= 0;
	public int				hydrogen		= 0;
	public boolean			hasAntennae		= false;
	public boolean			hasExplosive	= false;
	public boolean			hasFuse			= false;
	public boolean			hasChip			= false;
	public boolean			hasTrollface	= false;
	public float			megaton			= 0;

    public TileEntityTsarBomba(BlockPos pos, BlockState state) {
        super(RRTileEntities.TSAR_BOMB, pos, state);
    }

    @Override
	public int getContainerSize()
	{
		return 21;
	}

    @Override
	public ItemStack getItem(int slot)
	{
		return this.chestContents.get(slot);
	}

    @Override
    public ItemStack removeItem(int slot, int amount) {
		if (!this.chestContents.get(slot).isEmpty()) {
			ItemStack var3;

			if (this.chestContents.get(slot).getCount() <= amount) {
				var3 = this.chestContents.get(slot);
				this.chestContents.set(slot, ItemStack.EMPTY);
            } else {
				var3 = this.chestContents.get(slot).split(amount);

				if (this.chestContents.get(slot).isEmpty()) {
					this.chestContents.set(slot, ItemStack.EMPTY);
				}
            }
            return var3;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
		if (!this.chestContents.get(slot).isEmpty())
		{
			ItemStack var2 = this.chestContents.get(slot);
			this.chestContents.set(slot, ItemStack.EMPTY);
			return var2;
		}
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
		this.chestContents.set(slot, stack);

		if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize())
		{
			stack.setCount(this.getMaxStackSize());
		}

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
		return this.level.getBlockEntity(this.getBlockPos()) == this && player.distanceToSqr(this.getBlockPos().getX() + 0.5D, this.getBlockPos().getY() + 0.5D, this.getBlockPos().getZ() + 0.5D) <= 64.0D;
	}

    @Override
	public void tick() {
		nuclear = 0;
		hydrogen = 0;
		for (int i = 3; i <= 18; i++)
		{
			if (!getItem(i).isEmpty())
			{
				Item item = getItem(i).getItem();
				if (i < 11 && item == RRItems.nuclearelement)
				{
					nuclear++;
				}
				else if (i > 10 && item == RRItems.hydrod)
				{
					hydrogen++;
				}
				if (item == RRItems.trollmask)
				{
					hasTrollface = true;
				}
			}
		}
		if (nuclear == hydrogen) megaton = nuclear * 6.25f;

		if (!getItem(0).isEmpty())
		{
			hasFuse = getItem(0).getItem() == RRItems.fuse;
		}
		else
		{
			hasFuse = false;
		}

        ItemStack chipSlotStack = getItem(20);
        if (!chipSlotStack.isEmpty())
		{
			hasChip = chipSlotStack.getItem() == RRItems.chip;
			if (hasChip && chipSlotStack.has(RRComponents.CHIP_DATA)) {
                ChipData chipData = chipSlotStack.get(RRComponents.CHIP_DATA);
                rrteam = chipData.team();
				username = chipData.username();
			}
		}
		else
		{
			hasChip = false;
		}

		if (!getItem(1).isEmpty() && !getItem(2).isEmpty())
		{
			hasAntennae = getItem(1).getItem() == RRItems.antenna && getItem(2).getItem() == RRItems.antenna;
		}
		else
		{
			hasAntennae = false;
		}

		if (!getItem(19).isEmpty())
		{
			hasExplosive = true;// getStack(19).func_150998_b(RivalRebels.timedbomb);
		}
		else
		{
			hasExplosive = false;
		}

		boolean sp;
        if (level.isClientSide) {
            sp = Minecraft.getInstance().isLocalServer();
        } else {
            MinecraftServer server = level.getServer();
            sp = server.getPlayerCount() == 1;
        }

		if (hasFuse && hasExplosive && nuclear == hydrogen && hasAntennae && hasChip)
		{
			double dist = 1000000;

			if (!sp || RivalRebels.stopSelfnukeinSP)
			{
				if (rrteam == RivalRebelsTeam.OMEGA)
				{
					dist = getBlockPos().distToLowCornerSqr(RivalRebels.round.omegaObjPos.getX(), getBlockPos().getY(), RivalRebels.round.omegaObjPos.getZ());
				}
				if (rrteam == RivalRebelsTeam.SIGMA)
				{
					dist = getBlockPos().distToLowCornerSqr(RivalRebels.round.sigmaObjPos.getX(), getBlockPos().getY(), RivalRebels.round.sigmaObjPos.getZ());
				}
			}
			if (dist > (RivalRebels.tsarBombaStrength + (nuclear * hydrogen) + 29) * (RivalRebels.tsarBombaStrength + (nuclear * hydrogen) + 29))
			{
				if (countdown > 0) countdown--;
			}
			else if (!level.isClientSide)
			{
				this.chestContents.set(0, ItemStack.EMPTY);
                for (Player player : level.players()) {
                    player.displayClientMessage(Component.translatable(RivalRebels.MODID + ".warning_to_specific_player", username), false);
                    player.displayClientMessage(Component.translatable(RivalRebels.MODID + ".tsar_bomb_defuse", rrteam == RivalRebelsTeam.OMEGA ? RRBlocks.omegaobj.getName() : rrteam == RivalRebelsTeam.SIGMA ? RRBlocks.sigmaobj.getName() : Component.nullToEmpty("NONE")), false);
                }
			}
		}
		else
		{
			countdown = RivalRebels.nuclearBombCountdown * 20;
			if (RivalRebels.nuclearBombCountdown == 0) countdown = 10;
		}

		if (countdown == 200 && !level.isClientSide && RivalRebels.nuclearBombCountdown > 10)
		{
            MutableComponent line1 = Component.translatable(RivalRebels.MODID + ".rivalrebels.warning_bomb_will_explode_line_1");
            MutableComponent line2 = Component.translatable(RivalRebels.MODID + ".rivalrebels.warning_bomb_will_explode_line_2");
            MutableComponent line3 = Component.translatable(RivalRebels.MODID + ".rivalrebels.warning_bomb_will_explode_line_3");
            for (Player player : level.players()) {
                player.displayClientMessage(line1, false);
                player.displayClientMessage(line2, false);
                player.displayClientMessage(line3, false);
            }
		}

		if (countdown % 20 == 0 && countdown <= 200 && RivalRebels.nuclearBombCountdown > 10) RivalRebelsSoundPlayer.playSound(level, 14, 0, getBlockPos(), 100);

		if (countdown == 0 && nuclear != 0 && hydrogen != 0 && !level.isClientSide && nuclear == hydrogen)
		{
			level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
			level.setSkyFlashTime(2);
			float pitch = 0;
			float yaw = switch (this.getBlockState().getValue(BlockTsarBomba.META)) {
                case 2 -> 180;
                case 3 -> 0;
                case 4 -> 270;
                case 5 -> 90;
                default -> 0;
            };

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
    public Component getDisplayName() {
        return Component.nullToEmpty("Tsar Bomba");
    }

    @Override
    public void clearContent() {
        this.chestContents.clear();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.chestContents) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new ContainerTsar(syncId, inv, this, propertyDelegate);
    }

    private final ContainerData propertyDelegate = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> countdown;
                case 1 -> nuclear != hydrogen ? 1 : 0;
                case 2 -> hasExplosive && hasFuse && hasAntennae ? 1 : 0;
                case 3 -> Float.floatToIntBits(megaton);
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> countdown = value;
                default -> {}
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };
}
