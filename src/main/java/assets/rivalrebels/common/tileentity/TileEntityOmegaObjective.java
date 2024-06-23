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

import assets.rivalrebels.common.block.RRBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.CommandBlockExecutor;

public class TileEntityOmegaObjective extends BlockEntity implements Inventory, Tickable {
	private final DefaultedList<ItemStack> chestContents = DefaultedList.ofSize(16, ItemStack.EMPTY);

	public double		slide			= 0;
	double				test			= Math.PI - 0.05;

    private final CommandBlockExecutor commandExecutor = new CommandBlockExecutor() {
        @Override
        public void setCommand(String command) {
            super.setCommand(command);
            TileEntityOmegaObjective.this.markDirty();
        }

        @Override
        public ServerWorld getWorld() {
            return (ServerWorld)world;
        }

        @Override
        public void markDirty() {
            BlockState lv = world.getBlockState(pos);
            this.getWorld().updateListeners(pos, lv, lv, 3);
        }

        @Environment(EnvType.CLIENT)
        @Override
        public Vec3d getPos() {
            return Vec3d.ofCenter(pos);
        }

        @Override
        public ServerCommandSource getSource() {
            return new ServerCommandSource(
                this,
                getPos(),
                Vec2f.ZERO,
                this.getWorld(),
                2,
                this.getCustomName().getString(),
                this.getCustomName(),
                this.getWorld().getServer(),
                null
            );
        }

        @Override
        public boolean isEditable() {
            return !isRemoved();
        }
    };


    public TileEntityOmegaObjective(BlockPos pos, BlockState state) {
        super(RRTileEntities.OMEGA_OBJECTIVE, pos, state);
    }

    @Override
	public int size()
	{
		return 16;
	}

    @Override
	public ItemStack getStack(int par1)
	{
		return this.chestContents.get(par1);
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
		if (!this.chestContents.get(index).isEmpty())
		{
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
        commandExecutor.readNbt(nbt);
	}

    @Override
    public void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);

        Inventories.writeNbt(nbt, this.chestContents);
        commandExecutor.writeNbt(nbt);
    }

	@Override
	public boolean canPlayerUse(PlayerEntity player)
	{
		return this.world.getBlockEntity(this.getPos()) == this && player.squaredDistanceTo(this.getPos().getX() + 0.5D, this.getPos().getY() + 0.5D, this.getPos().getZ() + 0.5D) <= 64.0D;
	}

    @Override
	public void tick() {
		slide = (Math.cos(test) + 1) / 32 * 10;

        boolean i = world.isPlayerInRange(getPos().getX() + 0.5f, getPos().getY() + 0.5f, getPos().getZ() + 0.5f, 9);
		if (i)
		{
			if (slide < 0.621) test += 0.05;
		}
		else
		{
			if (slide > 0.004) test -= 0.05;
		}

		if (world.getBlockState(getPos()).getBlock() != RRBlocks.omegaobj) {
			this.markRemoved();
		}
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
    public void clear() {
        this.chestContents.clear();
    }
}
