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
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class TileEntityOmegaObjective extends BlockEntity implements Container, Tickable {
	private final NonNullList<ItemStack> chestContents = NonNullList.withSize(16, ItemStack.EMPTY);

	public double		slide			= 0;
	double				test			= Math.PI - 0.05;

    private final BaseCommandBlock commandExecutor = new BaseCommandBlock() {
        @Override
        public void setCommand(String command) {
            super.setCommand(command);
            TileEntityOmegaObjective.this.setChanged();
        }

        @Override
        public ServerLevel getLevel() {
            return (ServerLevel)level;
        }

        @Override
        public void onUpdated() {
            BlockState lv = level.getBlockState(worldPosition);
            this.getLevel().sendBlockUpdated(worldPosition, lv, lv, 3);
        }

        @Environment(EnvType.CLIENT)
        @Override
        public Vec3 getPosition() {
            return Vec3.atCenterOf(worldPosition);
        }

        @Override
        public CommandSourceStack createCommandSourceStack() {
            return new CommandSourceStack(
                this,
                getPosition(),
                Vec2.ZERO,
                this.getLevel(),
                2,
                this.getName().getString(),
                this.getName(),
                this.getLevel().getServer(),
                null
            );
        }

        @Override
        public boolean isValid() {
            return !isRemoved();
        }
    };


    public TileEntityOmegaObjective(BlockPos pos, BlockState state) {
        super(RRTileEntities.OMEGA_OBJECTIVE, pos, state);
    }

    @Override
	public int getContainerSize()
	{
		return 16;
	}

    @Override
	public ItemStack getItem(int par1)
	{
		return this.chestContents.get(par1);
	}

    @Override
	public ItemStack removeItem(int index, int count)
	{
		if (!this.getItem(index).isEmpty())
		{
			ItemStack var3;

			if (this.getItem(index).getCount() <= count)
			{
				var3 = this.getItem(index);
				this.setItem(index, ItemStack.EMPTY);
            }
			else
			{
				var3 = this.getItem(index).split(count);

				if (this.getItem(index).isEmpty())
				{
					this.setItem(index, ItemStack.EMPTY);
				}
            }
            return var3;
        }
		return ItemStack.EMPTY;
	}

    @Override
    public ItemStack removeItemNoUpdate(int index) {
		if (!this.getItem(index).isEmpty())
		{
			ItemStack var2 = this.getItem(index);
			this.setItem(index, ItemStack.EMPTY);
			return var2;
		}
		return ItemStack.EMPTY;
	}

    @Override
	public void setItem(int index, ItemStack stack)
	{
		this.chestContents.set(index, stack);

        stack.limitSize(this.getMaxStackSize(stack));
	}

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);

        ContainerHelper.loadAllItems(nbt, this.chestContents, provider);
        commandExecutor.load(nbt, provider);
	}

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
		super.saveAdditional(nbt, provider);

        ContainerHelper.saveAllItems(nbt, this.chestContents, provider);
        commandExecutor.save(nbt, provider);
    }

	@Override
	public boolean stillValid(Player player)
	{
        return Container.stillValidBlockEntity(this, player, 64);
	}

    @Override
	public void tick() {
		slide = (Math.cos(test) + 1) / 32 * 10;

        boolean i = level.hasNearbyAlivePlayer(getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, 9);
		if (i)
		{
			if (slide < 0.621) test += 0.05;
		}
		else
		{
			if (slide > 0.004) test -= 0.05;
		}

		if (!level.getBlockState(getBlockPos()).is(RRBlocks.omegaobj)) {
			this.setRemoved();
		}
	}

    @Override
    public boolean isEmpty() {
        return this.chestContents.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public void clearContent() {
        this.chestContents.clear();
    }
}
