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
import assets.rivalrebels.common.round.RivalRebelsTeam;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TileEntityNuclearBomb extends BlockEntity implements Inventory, Tickable, NamedScreenHandlerFactory
{
	public String			username		= null;
	public RivalRebelsTeam	rrteam			= null;
	private final DefaultedList<ItemStack> chestContents	= DefaultedList.ofSize(36, ItemStack.EMPTY);

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
	public int size()
	{
		return 13;
	}

    @Override
	public ItemStack getStack(int par1)
	{
		return this.chestContents.get(par1);
	}

    @Override
	public ItemStack removeStack(int slot, int amount)
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
	}

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.chestContents);
    }

    @Override
	public int getMaxCountPerStack()
	{
		return 1;
	}

    @Override
	public boolean canPlayerUse(PlayerEntity player)
	{
		return this.world.getBlockEntity(this.getPos()) == this && player.squaredDistanceTo(this.getPos().getX() + 0.5D, this.getPos().getY() + 0.5D, this.getPos().getZ() + 0.5D) <= 64.0D;
	}

    @Override
	public void tick() {
		AmountOfCharges = 0;
		hasTrollface = false;
		for (int i = 1; i <= 10; i++) {
			if (!getStack(i).isEmpty() && getStack(i).getItem() == RRItems.nuclearelement) {
				AmountOfCharges++;
			}
			if (!getStack(i).isEmpty() && getStack(i).getItem() == RRItems.trollmask) {
				hasTrollface = true;
			}
		}

		if (!getStack(0).isEmpty()) {
			hasFuse = getStack(0).getItem() == RRItems.fuse;
		} else {
			hasFuse = false;
		}

		if (!getStack(12).isEmpty())
		{
			hasChip = getStack(12).getItem() == RRItems.chip;
			if (hasChip)
			{
				rrteam = RivalRebelsTeam.getForID(getStack(12).getNbt().getInt("team"));
				username = getStack(12).getNbt().getString("username");
			}
		}
		else
		{
			hasChip = false;
		}

		if (!getStack(11).isEmpty()) {
			hasExplosive = true;// getStack(11).func_150998_b(RivalRebels.timedbomb);
		} else {
			hasExplosive = false;
		}

        boolean sp = world.isClient || (!world.isClient && (world.getServer().isSingleplayer() || world.getServer().getCurrentPlayerCount() == 1));

		if (hasFuse && hasExplosive && hasChip)
		{
			double dist = 10000000;
			if (!sp || RivalRebels.stopSelfnukeinSP)
			{
				if (rrteam == RivalRebelsTeam.OMEGA)
				{
					dist = getPos().getSquaredDistance(RivalRebels.round.omegaObjPos.getX(), getPos().getY(), RivalRebels.round.omegaObjPos.getZ());
				}
				if (rrteam == RivalRebelsTeam.SIGMA)
				{
					dist = getPos().getSquaredDistance(RivalRebels.round.sigmaObjPos.getX(), getPos().getY(), RivalRebels.round.sigmaObjPos.getZ());
				}
			}
			if (dist > (RivalRebels.nuclearBombStrength + (AmountOfCharges * AmountOfCharges) + 29) * (RivalRebels.nuclearBombStrength + (AmountOfCharges * AmountOfCharges) + 29))
			{
				if (Countdown > 0) Countdown--;
			}
			else if (!world.isClient)
			{
				this.chestContents.set(0, ItemStack.EMPTY);
                for (PlayerEntity player : world.getPlayers()) {
                    player.sendMessage(Text.translatable(RivalRebels.MODID + ".warning_to_specific_player", username), false);
                    player.sendMessage(Text.translatable(RivalRebels.MODID + ".nuke_bomb_defuse", rrteam == RivalRebelsTeam.OMEGA ? RRBlocks.omegaobj.getName() : rrteam == RivalRebelsTeam.SIGMA ? RRBlocks.sigmaobj.getName() : Text.of("NONE")), false);
                }
			}
		}
		else
		{
			Countdown = RivalRebels.nuclearBombCountdown * 20;
		}

		if (Countdown == 200 && !world.isClient && RivalRebels.nuclearBombCountdown > 10) {
            MutableText line1 = Text.translatable(RivalRebels.MODID + ".rivalrebels.warning_bomb_will_explode_line_1");
            MutableText line2 = Text.translatable(RivalRebels.MODID + ".rivalrebels.warning_bomb_will_explode_line_2");
            MutableText line3 = Text.translatable(RivalRebels.MODID + ".rivalrebels.warning_bomb_will_explode_line_3");
            for (PlayerEntity player : world.getPlayers()) {
                player.sendMessage(line1, false);
                player.sendMessage(line2, false);
                player.sendMessage(line3, false);
            }
		}

		if (Countdown == 0 && AmountOfCharges != 0 && !world.isClient)
		{
			world.setLightningTicksLeft(2);
			float pitch = 0;
			float yaw = 0;
			switch(this.getCachedState().get(BlockNuclearBomb.META))
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

			world.spawnEntity(new EntityNuke(world, getPos().getX() + 0.5f, getPos().getY() + 0.5f, getPos().getZ() + 0.5f, yaw, pitch, AmountOfCharges, hasTrollface));
			world.setBlockState(getPos(), Blocks.AIR.getDefaultState());
		}

		if (Countdown == 0 && AmountOfCharges == 0)
		{
			world.createExplosion(null, getPos().getX(), getPos().getY(), getPos().getZ(), 4, World.ExplosionSourceType.BLOCK);
			world.setBlockState(getPos(), Blocks.AIR.getDefaultState());
		}
    }

    @Override
    public Text getDisplayName() {
        return Text.of("Nuclear Bomb");
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

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ContainerNuclearBomb(syncId, inv, this, propertyDelegate);
    }

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> Countdown;
                case 1 -> AmountOfCharges;
                case 2 -> hasTrollface ? 1 : 0;
                case 3 -> hasExplosive && hasFuse ? 1 : 0;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {

        }

        @Override
        public int size() {
            return 4;
        }
    };
}
