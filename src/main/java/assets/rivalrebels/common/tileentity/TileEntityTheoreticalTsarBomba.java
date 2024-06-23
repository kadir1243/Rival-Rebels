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
import assets.rivalrebels.common.block.trap.BlockTheoreticalTsarBomba;
import assets.rivalrebels.common.container.ContainerTheoreticalTsar;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityTheoreticalTsar;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TileEntityTheoreticalTsarBomba extends BlockEntity implements Container, Tickable, MenuProvider
{
	public String			username		= null;
	public RivalRebelsTeam	rrteam			= null;
	private final NonNullList<ItemStack> chestContents	= NonNullList.withSize(36, ItemStack.EMPTY);
	public int				countdown		= RivalRebels.nuclearBombCountdown * 20;
	public int				nuclear			= 0;
	public boolean			hasAntennae		= false;
	public boolean			hasExplosive	= false;
	public boolean			hasFuse			= false;
	public boolean			hasChip			= false;
	public boolean			hasTrollface	= false;
	public float			megaton			= 0;

    public TileEntityTheoreticalTsarBomba(BlockPos pos, BlockState state) {
        super(RRTileEntities.THEORETICAL_TSAR_BOMB, pos, state);
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
	public ItemStack removeItem(int index, int count) {
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
		if (!this.chestContents.get(index).isEmpty())
		{
			ItemStack var2 = this.chestContents.get(index);
			this.chestContents.set(index, ItemStack.EMPTY);
			return var2;
		}
		else
		{
			return ItemStack.EMPTY;
		}
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
	public int getMaxStackSize()
	{
		return 1;
	}

    @Override
	public boolean stillValid(@NotNull Player par1EntityPlayer)
	{
		return this.level.getBlockEntity(this.getBlockPos()) == this && par1EntityPlayer.distanceToSqr(this.getBlockPos().getX() + 0.5D, this.getBlockPos().getY() + 0.5D, this.getBlockPos().getZ() + 0.5D) <= 64.0D;
	}

    @Override
	public void tick()
	{
		nuclear = 0;
		for (int i = 3; i <= 18; i++)
		{
			if (!getItem(i).isEmpty())
			{
				Item item = getItem(i).getItem();
				if (item == RRItems.nuclearelement)
				{
					nuclear++;
				}
				if (item == RRItems.trollmask)
				{
					hasTrollface = true;
				}
			}
		}
		megaton = nuclear * 6.25f;

		if (!getItem(0).isEmpty())
		{
			hasFuse = getItem(0).getItem() == RRItems.fuse;
		}
		else
		{
			hasFuse = false;
		}

		if (!getItem(20).isEmpty()) {
			hasChip = getItem(20).is(RRItems.chip);
			if (hasChip && getItem(20).has(RRComponents.CHIP_DATA)) {
                ChipData chipData = getItem(20).get(RRComponents.CHIP_DATA);
                rrteam = chipData.team();
				username = chipData.username();
			}
		} else {
			hasChip = false;
		}

		if (!getItem(1).isEmpty() && !getItem(2).isEmpty()) {
			hasAntennae = getItem(1).getItem() == RRItems.antenna && getItem(2).getItem() == RRItems.antenna;
		} else {
			hasAntennae = false;
		}

		if (!getItem(19).isEmpty()) {
			hasExplosive = true;// getStack(19).func_150998_b(RivalRebels.timedbomb);
		} else {
			hasExplosive = false;
		}

		boolean sp;
        if (level.isClientSide) {
            sp = Minecraft.getInstance().isLocalServer();
        } else {
            MinecraftServer server = level.getServer();
            sp = server.getPlayerCount() == 1;
        }

		if (hasFuse && hasExplosive && hasAntennae && hasChip)
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
			if (dist > (RivalRebels.tsarBombaStrength + (nuclear * nuclear) + 29) * (RivalRebels.tsarBombaStrength + (nuclear * nuclear) + 29))
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

		if (countdown == 0 && nuclear != 0 && !level.isClientSide)
		{
            level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
			level.setSkyFlashTime(2);
			float pitch = 0;
			float yaw = switch (this.getBlockState().getValue(BlockTheoreticalTsarBomba.META)) {
                case 2 -> 180;
                case 3 -> 0;
                case 4 -> 270;
                case 5 -> 90;
                default -> 0;
            };

            EntityTheoreticalTsar tsar = new EntityTheoreticalTsar(level, getBlockPos().getX()+0.5f, getBlockPos().getY()+1f, getBlockPos().getZ()+0.5f, yaw, pitch, nuclear, hasTrollface);
			level.addFreshEntity(tsar);
		}

		if (countdown == 0 && nuclear == 0)
		{
            level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
			level.explode(null, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), 4, Level.ExplosionInteraction.NONE);
		}
    }

    @Override
    public Component getDisplayName() {
        return Component.nullToEmpty("Theoretical Tsar Bomba");
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
        return new ContainerTheoreticalTsar(syncId, inv, this, propertyDelegate);
    }

    private final ContainerData propertyDelegate = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> countdown;
                case 1 -> hasTrollface ? 1 : 0;
                case 2 -> hasExplosive && hasFuse ? 1 : 0;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {

        }

        @Override
        public int getCount() {
            return 3;
        }
    };

}
