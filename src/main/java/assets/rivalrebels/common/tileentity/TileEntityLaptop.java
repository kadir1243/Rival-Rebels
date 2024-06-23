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
import assets.rivalrebels.common.container.ContainerLaptop;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.item.components.ChipData;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.item.weapon.ItemBinoculars;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TileEntityLaptop extends BlockEntity implements Container, Tickable, MenuProvider
{
    public RivalRebelsTeam	rrteam			= null;
	private final NonNullList<ItemStack> chestContents = NonNullList.withSize(14, ItemStack.EMPTY);

	public double			slide			= 0;
	double					test			= Math.PI;
	public int				b2spirit		= 0;
	public int				b2carpet		= 0;

    public TileEntityLaptop(BlockPos pos, BlockState state) {
        super(RRTileEntities.LAPTOP, pos, state);
    }

    @Override
	public int getContainerSize()
	{
		return 14;
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

    @Override
	public ItemStack getItem(int index)
	{
		return this.chestContents.get(index);
	}

	@Override
	public ItemStack removeItem(int index, int count)
	{
		if (!this.chestContents.get(index).isEmpty())
		{
			ItemStack var3;

			if (this.chestContents.get(index).getCount() <= count)
			{
				var3 = this.chestContents.get(index);
				this.chestContents.set(index, ItemStack.EMPTY);
            }
			else
			{
				var3 = this.chestContents.get(index).split(count);

				if (this.chestContents.get(index).isEmpty())
				{
					this.chestContents.set(index, ItemStack.EMPTY);
				}

            }
            return var3;
        }
		return ItemStack.EMPTY;
	}

    @Override
    public ItemStack removeItemNoUpdate(int index) {
		if (!this.chestContents.get(index).isEmpty()) {
			ItemStack var2 = this.chestContents.get(index);
			this.chestContents.set(index, ItemStack.EMPTY);
			return var2;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void setItem(int index, ItemStack stack)
	{
		this.chestContents.set(index, stack);

		if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize())
		{
			stack.setCount(this.getMaxStackSize());
		}
	}

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);

        ContainerHelper.loadAllItems(nbt, this.chestContents, provider);
		b2spirit = nbt.getInt("b2spirit");
		b2carpet = nbt.getInt("b2carpet");
	}

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(nbt, provider);

        ContainerHelper.saveAllItems(nbt, this.chestContents, provider);
		nbt.putInt("b2spirit", b2spirit);
		nbt.putInt("b2carpet", b2carpet);
    }

    @Override
	public boolean stillValid(Player player) {
		return this.level.getBlockEntity(this.getBlockPos()) == this && player.distanceToSqr(this.getBlockPos().getX() + 0.5D, this.getBlockPos().getY() + 0.5D, this.getBlockPos().getZ() + 0.5D) <= 64.0D;
	}

	public void onGoButtonPressed()
	{
		if (isReady())
		{
			for (int j = 0; j < 3; j++)
			{
				if (!getItem(j + 6).isEmpty() && !getItem(j + 11).isEmpty())
				{
					if (getItem(j + 6).getItem() == RRItems.nuclearelement
							&& getItem(j + 11).getItem() == RRItems.hydrod) {
						b2spirit++;
						setItem(j+6, ItemStack.EMPTY);
						setItem(j+11, ItemStack.EMPTY);
					} else if (getItem(j + 6).getItem() == RRBlocks.timedbomb.asItem()
                        && getItem(j + 11).getItem() == RRBlocks.timedbomb.asItem()) {
						b2carpet++;
						setItem(j+6, ItemStack.EMPTY);
						setItem(j+11, ItemStack.EMPTY);
					}
				}
			}
			setItem(4, ItemStack.EMPTY);
			setItem(5, ItemStack.EMPTY);
			setItem(9, ItemStack.EMPTY);
			setItem(10, ItemStack.EMPTY);
		}
		if (RivalRebels.freeb83nukes) {b2spirit += 10;b2carpet += 10;}
	}

	public boolean hasChips()
	{
		boolean r = true;
		rrteam = RivalRebelsTeam.NONE;
		for (int j = 0; j < 4; j++)
		{
            ItemStack stack = getItem(j);
            if (stack.isEmpty()) {
                r = false;
            } else if (stack.has(RRComponents.CHIP_DATA)) {
                ChipData chipData = stack.get(RRComponents.CHIP_DATA);
                if (rrteam == RivalRebelsTeam.NONE) rrteam = chipData.team();
				else if (rrteam != chipData.team()) r = false;
			}
		}
		return r;
	}

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag nbt = new CompoundTag();
        saveAdditional(nbt, registries);
        return nbt;
    }

    @Override
	public void tick() {
		slide = (Math.cos(test) + 1) * 45;

        ItemBinoculars.add(this);
        boolean i = level.hasNearbyAlivePlayer(getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 9);
		if (i)
		{
			if (slide < 89.995) test += 0.05;
		}
		else
		{
			if (slide > 0.004) test -= 0.05;
		}

		if (b2spirit > 0 && !hasChips())
		{
			b2spirit--;
		}
	}

    @Override
    public void setRemoved() {
        super.setRemoved();
		ItemBinoculars.remove(this);
	}

    @Override
    public void clearContent() {
        this.chestContents.clear();
    }

    @Override
    public Component getDisplayName() {
        return Component.nullToEmpty("Laptop");
    }

    public boolean isReady()
	{
		return hasChips()
				&& !getItem(4).isEmpty()
				&& !getItem(5).isEmpty()
				&& !getItem(9).isEmpty()
				&& !getItem(10).isEmpty();
	}

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new ContainerLaptop(syncId, inv, this, propertyDelegate);
    }

    private final ContainerData propertyDelegate = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> isReady() ? 1 : 0;
                case 1 -> hasChips() ? 1 : 0;
                case 2 -> b2spirit;
                case 3 -> b2carpet;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) onGoButtonPressed();
        }

        @Override
        public int getCount() {
            return 4;
        }
    };
}
