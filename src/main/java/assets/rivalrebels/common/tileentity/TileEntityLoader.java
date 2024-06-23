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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityLoader extends BlockEntity implements Container, Tickable, MenuProvider
{
	private final NonNullList<ItemStack> chestContents	= NonNullList.withSize(64, ItemStack.EMPTY);

	public double			slide			= 0;
	double					test			= Math.PI;
	int						counter;

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
	public ItemStack getItem(int slot)
	{
		return this.chestContents.get(slot);
	}

	@Override
	public ItemStack removeItem(int slot, int amount)
	{
		if (!this.chestContents.get(slot).isEmpty())
		{
			ItemStack var3;

			if (this.chestContents.get(slot).getCount() <= amount)
			{
				var3 = this.chestContents.get(slot);
				this.chestContents.set(slot, ItemStack.EMPTY);
            }
			else
			{
				var3 = this.chestContents.get(slot).split(amount);

				if (this.chestContents.get(slot).isEmpty())
				{
					this.chestContents.set(slot, ItemStack.EMPTY);
				}

            }
            return var3;
        }
		return ItemStack.EMPTY;
	}

    @Override
    public ItemStack removeItemNoUpdate(int index) {
		if (!this.chestContents.get(index).isEmpty())
		{
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
	}

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(nbt, provider);

        ContainerHelper.saveAllItems(nbt, this.chestContents, provider);
    }

    @Override
	public boolean stillValid(Player player)
	{
		return this.level.getBlockEntity(this.getBlockPos()) == this && player.distanceToSqr(this.getBlockPos().getX() + 0.5D, this.getBlockPos().getY() + 0.5D, this.getBlockPos().getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void tick() {
		slide = (Math.cos(test) + 1) / 32 * 14;

        if (level.hasNearbyAlivePlayer(getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 9)) {
			if (slide < 0.871) test += 0.05;
		} else {
			if (slide > 0.004) test -= 0.05;
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
                        if (ter.on)
						{
							for (int q = 0; q < chestContents.size(); q++)
							{
								if (ter.fuel.isEmpty())
								{
									if (!chestContents.get(q).isEmpty() && chestContents.get(q).getItem() instanceof ItemRod && chestContents.get(q).getItem() != RRItems.emptyrod)
									{
										ter.fuel = chestContents.get(q);
										chestContents.set(q, RRItems.emptyrod.getDefaultInstance());
									}
								}
								else
								{
									break;
								}
							}
						}
					}
					if (te instanceof TileEntityReciever ter)
					{
                        for (int q = 0; q < chestContents.size(); q++)
						{
							if (!chestContents.get(q).isEmpty() && chestContents.get(q).getItem() == RRItems.fuel)
							{
								int amount = chestContents.get(q).getCount();
								if (ter.chestContents.get(0).isEmpty() || ter.chestContents.get(0).getCount() < 64)
								{
									if (ter.chestContents.get(0).isEmpty())
									{
										ter.chestContents.set(0, chestContents.get(q).copyWithCount(amount));
									}
									else ter.chestContents.get(0).grow(amount);
									amount = 0;
									if (ter.chestContents.get(0).getCount() > 64)
									{
										amount = ter.chestContents.get(0).getCount() - 64;
										ter.chestContents.get(0).setCount(64);
									}
								}
								else if (ter.chestContents.get(1).isEmpty() || ter.chestContents.get(1).getCount() < 64)
								{
									if (ter.chestContents.get(1).isEmpty())
									{
										ter.chestContents.set(1, chestContents.get(q).copyWithCount(amount));
									}
									else ter.chestContents.get(1).grow(amount);
									amount = 0;
									if (ter.chestContents.get(1).getCount() > 64)
									{
										amount = ter.chestContents.get(1).getCount() - 64;
										ter.chestContents.get(1).setCount(64);
									}
								}
								else if (ter.chestContents.get(2).isEmpty() || ter.chestContents.get(2).getCount() < 64)
								{
									if (ter.chestContents.get(2).isEmpty())
									{
										ter.chestContents.set(2, chestContents.get(q).copyWithCount(amount));
									}
									else ter.chestContents.get(2).grow(amount);
									amount = 0;
									if (ter.chestContents.get(2).getCount() > 64)
									{
										amount = ter.chestContents.get(2).getCount() - 64;
										ter.chestContents.get(2).setCount(64);
									}
								}
								chestContents.get(q).setCount(amount);
								if (chestContents.get(q).isEmpty()) chestContents.set(q, ItemStack.EMPTY);
							}
							if (!chestContents.get(q).isEmpty() && chestContents.get(q).getItem() == RRItems.battery)
							{
								int amount = chestContents.get(q).getCount();
								if (ter.chestContents.get(3).isEmpty() || ter.chestContents.get(3).getCount() < 16)
								{
									if (ter.chestContents.get(3).isEmpty())
									{
										ter.chestContents.set(3, chestContents.get(q).copyWithCount(amount));
									}
									else ter.chestContents.get(3).grow(amount);
									amount = 0;
									if (ter.chestContents.get(3).getCount() > 16)
									{
										amount = ter.chestContents.get(3).getCount() - 16;
										ter.chestContents.get(3).setCount(16);
									}
								}
								else if (ter.chestContents.get(4).isEmpty() || ter.chestContents.get(4).getCount() < 16)
								{
									if (ter.chestContents.get(4).isEmpty())
									{
										ter.chestContents.set(4, chestContents.get(q).copyWithCount(amount));
									}
									else ter.chestContents.get(4).grow(amount);
									amount = 0;
									if (ter.chestContents.get(4).getCount() > 16)
									{
										amount = ter.chestContents.get(4).getCount() - 16;
										ter.chestContents.get(4).setCount(16);
									}
								}
								else if (ter.chestContents.get(5).isEmpty() || ter.chestContents.get(5).getCount() < 16)
								{
									if (ter.chestContents.get(5).isEmpty())
									{
										ter.chestContents.set(5, chestContents.get(q).copyWithCount(amount));
									}
									else ter.chestContents.get(5).grow(amount);
									amount = 0;
									if (ter.chestContents.get(5).getCount() > 16)
									{
										amount = ter.chestContents.get(5).getCount() - 16;
										ter.chestContents.get(5).setCount(16);
									}
								}
								chestContents.get(q).setCount(amount);
								if (chestContents.get(q).isEmpty()) chestContents.set(q, ItemStack.EMPTY);
							}
						}
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
                for (int q = 0; q < chestContents.size(); q++)
				{
					if (!chestContents.get(q).isEmpty())
					{
						for (int j = 0; j < tel.chestContents.size(); j++)
						{
							if (tel.chestContents.get(j).isEmpty())
							{
								tel.chestContents.set(j, chestContents.get(q));
								chestContents.set(q, ItemStack.EMPTY);
								return;
							}
						}
					}
				}
			}
		}
	}

    @Override
    public Component getDisplayName() {
        return Component.nullToEmpty("Loader");
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
    public void clearContent() {
        this.chestContents.clear();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new ContainerLoader(syncId, inv, this);
    }
}
