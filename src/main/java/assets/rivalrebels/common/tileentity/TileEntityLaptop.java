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
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TileEntityLaptop extends BaseContainerBlockEntity implements Tickable {
    public RivalRebelsTeam	rrteam			= null;
	private NonNullList<ItemStack> items = NonNullList.withSize(14, ItemStack.EMPTY);

	public double			slide			= 0;
	private float test = Mth.PI;
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
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);

        ContainerHelper.loadAllItems(nbt, this.items, provider);
		b2spirit = nbt.getInt("b2spirit");
		b2carpet = nbt.getInt("b2carpet");
	}

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(nbt, provider);

        ContainerHelper.saveAllItems(nbt, this.items, provider);
		nbt.putInt("b2spirit", b2spirit);
		nbt.putInt("b2carpet", b2carpet);
    }

    @Override
	public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player, 64);
	}

	public void onGoButtonPressed()
	{
		if (isReady())
		{
			for (int j = 0; j < 3; j++)
			{
				if (!getItem(j + 6).isEmpty() && !getItem(j + 11).isEmpty())
				{
					if (getItem(j + 6).getItem() == RRItems.NUCLEAR_ROD
							&& getItem(j + 11).getItem() == RRItems.hydrod) {
						b2spirit++;
						setItem(j+6, ItemStack.EMPTY);
						setItem(j+11, ItemStack.EMPTY);
					} else if (getItem(j + 6).is(RRBlocks.timedbomb.asItem())
                        && getItem(j + 11).is(RRBlocks.timedbomb.asItem())) {
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
		if (RRConfig.SERVER.isFreeb83nukes()) {b2spirit += 10;b2carpet += 10;}
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
		slide = (Mth.cos(test) + 1) * 45;

        ItemBinoculars.add(this);
        boolean i = level.hasNearbyAlivePlayer(getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 9);
		if (i)
		{
			if (slide < 89.995) test += 0.05F;
		}
		else
		{
			if (slide > 0.004) test -= 0.05F;
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
    protected Component getDefaultName() {
        return Component.literal("Laptop");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    public boolean isReady()
	{
		return hasChips()
				&& !getItem(4).isEmpty()
				&& !getItem(5).isEmpty()
				&& !getItem(9).isEmpty()
				&& !getItem(10).isEmpty();
	}

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new ContainerLaptop(containerId, inventory, this, propertyDelegate);
    }

    private final ContainerData propertyDelegate = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> isReady() ? 1 : 0;
                case 1 -> hasChips() ? 1 : 0;
                case 2 -> b2spirit;
                case 3 -> b2carpet;
                case 4 -> getBlockPos().getX();
                case 5 -> getBlockPos().getY();
                case 6 -> getBlockPos().getZ();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 2 -> b2spirit = value;
                case 3 -> b2carpet = value;
                default -> {}
            }
        }

        @Override
        public int getCount() {
            return 7;
        }
    };
}
