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
import assets.rivalrebels.common.item.weapon.ItemBinoculars;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class TileEntityLaptop extends BlockEntity implements Inventory, Tickable, NamedScreenHandlerFactory
{
    public RivalRebelsTeam	rrteam			= null;
	private final DefaultedList<ItemStack> chestContents = DefaultedList.ofSize(14, ItemStack.EMPTY);

	public double			slide			= 0;
	double					test			= Math.PI;
	public int				b2spirit		= 0;
	public int				b2carpet		= 0;

    public TileEntityLaptop(BlockPos pos, BlockState state) {
        super(RRTileEntities.LAPTOP, pos, state);
    }

    @Override
	public int size()
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
	public ItemStack getStack(int index)
	{
		return this.chestContents.get(index);
	}

	@Override
	public ItemStack removeStack(int index, int count)
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
    public ItemStack removeStack(int index) {
		if (!this.chestContents.get(index).isEmpty()) {
			ItemStack var2 = this.chestContents.get(index);
			this.chestContents.set(index, ItemStack.EMPTY);
			return var2;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void setStack(int index, ItemStack stack)
	{
		this.chestContents.set(index, stack);

		if (!stack.isEmpty() && stack.getCount() > this.getMaxCountPerStack())
		{
			stack.setCount(this.getMaxCountPerStack());
		}
	}

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        Inventories.readNbt(nbt, this.chestContents);
		b2spirit = nbt.getInt("b2spirit");
		b2carpet = nbt.getInt("b2carpet");
	}

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        Inventories.writeNbt(nbt, this.chestContents);
		nbt.putInt("b2spirit", b2spirit);
		nbt.putInt("b2carpet", b2carpet);
    }

    @Override
	public boolean canPlayerUse(PlayerEntity player) {
		return this.world.getBlockEntity(this.getPos()) == this && player.squaredDistanceTo(this.getPos().getX() + 0.5D, this.getPos().getY() + 0.5D, this.getPos().getZ() + 0.5D) <= 64.0D;
	}

	public void onGoButtonPressed()
	{
		if (isReady())
		{
			for (int j = 0; j < 3; j++)
			{
				if (!getStack(j + 6).isEmpty() && !getStack(j + 11).isEmpty())
				{
					if (getStack(j + 6).getItem() == RRItems.nuclearelement
							&& getStack(j + 11).getItem() == RRItems.hydrod) {
						b2spirit++;
						setStack(j+6, ItemStack.EMPTY);
						setStack(j+11, ItemStack.EMPTY);
					} else if (getStack(j + 6).getItem() == RRBlocks.timedbomb.asItem()
                        && getStack(j + 11).getItem() == RRBlocks.timedbomb.asItem()) {
						b2carpet++;
						setStack(j+6, ItemStack.EMPTY);
						setStack(j+11, ItemStack.EMPTY);
					}
				}
			}
			setStack(4, ItemStack.EMPTY);
			setStack(5, ItemStack.EMPTY);
			setStack(9, ItemStack.EMPTY);
			setStack(10, ItemStack.EMPTY);
		}
		if (RivalRebels.freeb83nukes) {b2spirit += 10;b2carpet += 10;}
	}

	public boolean hasChips()
	{
		boolean r = true;
		rrteam = RivalRebelsTeam.NONE;
		for (int j = 0; j < 4; j++)
		{
			if (getStack(j).isEmpty()) r = false;
			else {
				if (rrteam == RivalRebelsTeam.NONE) rrteam = RivalRebelsTeam.getForID(getStack(j).getOrCreateNbt().getInt("team"));
				else if (rrteam != RivalRebelsTeam.getForID(getStack(j).getOrCreateNbt().getInt("team"))) r = false;
			}
		}
		return r;
	}

    @Nullable
    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbt = new NbtCompound();
        writeNbt(nbt);
        return nbt;
    }

    @Override
	public void tick() {
		slide = (Math.cos(test) + 1) * 45;

        ItemBinoculars.add(this);
        boolean i = world.isPlayerInRange(getPos().getX() + 0.5f, getPos().getY() + 0.5f, getPos().getZ() + 0.5f, 9);
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
    public void markRemoved() {
        super.markRemoved();
		ItemBinoculars.remove(this);
	}

    @Override
    public void clear() {
        this.chestContents.clear();
    }

    @Override
    public Text getDisplayName() {
        return Text.of("Laptop");
    }

    public boolean isReady()
	{
		return hasChips()
				&& !getStack(4).isEmpty()
				&& !getStack(5).isEmpty()
				&& !getStack(9).isEmpty()
				&& !getStack(10).isEmpty();
	}

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ContainerLaptop(syncId, inv, this, propertyDelegate);
    }

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
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
        public int size() {
            return 4;
        }
    };
}
