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

import assets.rivalrebels.common.container.ContainerLoader;
import assets.rivalrebels.common.item.ItemRod;
import assets.rivalrebels.common.item.RRItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityLoader extends BaseContainerBlockEntity implements Tickable {
	private NonNullList<ItemStack> items = NonNullList.withSize(64, ItemStack.EMPTY);

	public float slide = 0;
	private float test = Mth.PI;
	private int counter;

	public List<BlockEntity> machines		= new ArrayList<>();

    public TileEntityLoader(BlockPos pos, BlockState state) {
        super(RRTileEntities.LOADER, pos, state);
    }

    @Override
	public int getContainerSize()
	{
		return 60;
	}

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);

        ContainerHelper.loadAllItems(nbt, this.items, provider);
	}

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(nbt, provider);

        ContainerHelper.saveAllItems(nbt, this.items, provider);
    }

    @Override
	public boolean stillValid(Player player)
	{
        return Container.stillValidBlockEntity(this, player, 64);
	}

	@Override
	public void tick() {
		slide = (Mth.cos(test) + 1) / 32 * 14;

        if (level.hasNearbyAlivePlayer(getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 9)) {
			if (slide < 0.871) test += 0.05F;
		} else {
			if (slide > 0.004) test -= 0.05F;
		}
		counter++;
		if (counter % 10 == 0)
		{
			for (int x = 1; x < 7; x++)
			{
				BlockEntity te = level.getBlockEntity(getBlockPos().east(x));
				if ((te instanceof TileEntityReactor || te instanceof TileEntityReciever))
				{
					machines.add(te);
				}
				te = level.getBlockEntity(getBlockPos().west(x));
				if ((te instanceof TileEntityReactor || te instanceof TileEntityReciever))
				{
					machines.add(te);
				}
				te = level.getBlockEntity(getBlockPos().south(x));
				if ((te instanceof TileEntityReactor || te instanceof TileEntityReciever))
				{
					machines.add(te);
				}
				te = level.getBlockEntity(getBlockPos().north(x));
				if ((te instanceof TileEntityReactor || te instanceof TileEntityReciever))
				{
					machines.add(te);
				}
			}
			for (int index = 0; index < machines.size(); index++)
			{
				BlockEntity te = machines.get(index);
				if (te != null && !te.isRemoved())
				{
					if (te instanceof TileEntityReactor ter)
					{
                        if (ter.on) {
                            for (int q = 0; q < items.size(); q++) {
                                if (ter.fuel.isEmpty()) {
                                    if (!getItem(q).isEmpty() && getItem(q).getItem() instanceof ItemRod && getItem(q).getItem() != RRItems.emptyrod) {
                                        ter.fuel = getItem(q);
                                        setItem(q, RRItems.emptyrod.getDefaultInstance());
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
					}
					if (te instanceof TileEntityReciever ter)
					{
                        transferItemsToReciever(ter);
                    }
				}
				else
				{
					machines.remove((int) index);
				}
			}
			BlockEntity te = level.getBlockEntity(getBlockPos().below());
			if (te instanceof TileEntityLoader tel)
			{
                for (int q = 0; q < items.size(); q++)
				{
					if (!getItem(q).isEmpty())
					{
						for (int j = 0; j < tel.items.size(); j++)
						{
							if (tel.getItem(j).isEmpty())
							{
								tel.setItem(j, getItem(q));
								setItem(q, ItemStack.EMPTY);
								return;
							}
						}
					}
				}
			}
		}
	}

    private void transferItemsToReciever(TileEntityReciever ter) {
        for (ItemStack item : getItems()) {
            if (item.isEmpty()) continue;
            if (item.is(RRItems.fuel)) {
                int amount = item.getCount();
                for (int slot : new int[] {0, 1, 2}) {
                    if (ter.getItem(slot).isEmpty() || ter.getItem(slot).getCount() < 64) {
                        if (ter.getItem(slot).isEmpty()) {
                            ter.setItem(slot, item.copyWithCount(amount));
                        } else ter.getItem(slot).grow(amount);
                        amount = 0;
                        if (ter.getItem(slot).getCount() > 64) {
                            amount = ter.getItem(slot).getCount() - 64;
                            ter.getItem(slot).setCount(64);
                        }
                        break;
                    }
                }
                item.setCount(amount);
            } else if (item.is(RRItems.battery)) {
                int amount = item.getCount();
                for (int slot : new int[] {3, 4, 5}) {
                    if (ter.getItem(slot).isEmpty() || ter.getItem(slot).getCount() < 16) {
                        if (ter.getItem(slot).isEmpty()) {
                            ter.setItem(slot, item.copyWithCount(amount));
                        } else ter.getItem(slot).grow(amount);
                        amount = 0;
                        if (ter.getItem(slot).getCount() > 16) {
                            amount = ter.getItem(slot).getCount() - 16;
                            ter.getItem(slot).setCount(16);
                        }
                        break;
                    }
                }
                item.setCount(amount);
            }
        }
    }

    @Override
    protected Component getDefaultName() {
        return Component.literal("Loader");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new ContainerLoader(containerId, inventory, this);
    }
}
