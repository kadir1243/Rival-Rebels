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
import assets.rivalrebels.common.round.RivalRebelsTeam;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TileEntityTheoreticalTsarBomba extends BlockEntity implements Inventory, Tickable, NamedScreenHandlerFactory
{
	public String			username		= null;
	public RivalRebelsTeam	rrteam			= null;
	private final DefaultedList<ItemStack> chestContents	= DefaultedList.ofSize(36, ItemStack.EMPTY);
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
	public int size()
	{
		return 21;
	}

    @Override
	public ItemStack getStack(int slot)
	{
		return this.chestContents.get(slot);
	}

	@Override
	public ItemStack removeStack(int index, int count) {
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
		else
		{
			return ItemStack.EMPTY;
		}
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
	public boolean canPlayerUse(@NotNull PlayerEntity par1EntityPlayer)
	{
		return this.world.getBlockEntity(this.getPos()) == this && par1EntityPlayer.squaredDistanceTo(this.getPos().getX() + 0.5D, this.getPos().getY() + 0.5D, this.getPos().getZ() + 0.5D) <= 64.0D;
	}

    @Override
	public void tick()
	{
		nuclear = 0;
		for (int i = 3; i <= 18; i++)
		{
			if (!getStack(i).isEmpty())
			{
				Item item = getStack(i).getItem();
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

		if (!getStack(0).isEmpty())
		{
			hasFuse = getStack(0).getItem() == RRItems.fuse;
		}
		else
		{
			hasFuse = false;
		}

		if (!getStack(20).isEmpty()) {
			hasChip = getStack(20).isOf(RRItems.chip);
			if (hasChip && getStack(20).hasNbt()) {
				rrteam = RivalRebelsTeam.getForID(getStack(20).getNbt().getInt("team"));
				username = getStack(20).getNbt().getString("username");
			}
		} else {
			hasChip = false;
		}

		if (!getStack(1).isEmpty() && !getStack(2).isEmpty()) {
			hasAntennae = getStack(1).getItem() == RRItems.antenna && getStack(2).getItem() == RRItems.antenna;
		} else {
			hasAntennae = false;
		}

		if (!getStack(19).isEmpty()) {
			hasExplosive = true;// getStack(19).func_150998_b(RivalRebels.timedbomb);
		} else {
			hasExplosive = false;
		}

		boolean sp;
        if (world.isClient) {
            sp = MinecraftClient.getInstance().isInSingleplayer();
        } else {
            MinecraftServer server = world.getServer();
            sp = server.getCurrentPlayerCount() == 1;
        }

		if (hasFuse && hasExplosive && hasAntennae && hasChip)
		{
			double dist = 1000000;

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
			if (dist > (RivalRebels.tsarBombaStrength + (nuclear * nuclear) + 29) * (RivalRebels.tsarBombaStrength + (nuclear * nuclear) + 29))
			{
				if (countdown > 0) countdown--;
			}
			else if (!world.isClient)
			{
				this.chestContents.set(0, ItemStack.EMPTY);
                for (PlayerEntity player : world.getPlayers()) {
                    player.sendMessage(Text.translatable(RivalRebels.MODID + ".warning_to_specific_player", username), false);
                    player.sendMessage(Text.translatable(RivalRebels.MODID + ".tsar_bomb_defuse", rrteam == RivalRebelsTeam.OMEGA ? RRBlocks.omegaobj.getName() : rrteam == RivalRebelsTeam.SIGMA ? RRBlocks.sigmaobj.getName() : Text.of("NONE")), false);
                }
			}
		}
		else
		{
			countdown = RivalRebels.nuclearBombCountdown * 20;
			if (RivalRebels.nuclearBombCountdown == 0) countdown = 10;
		}

		if (countdown == 200 && !world.isClient && RivalRebels.nuclearBombCountdown > 10)
		{
            MutableText line1 = Text.translatable(RivalRebels.MODID + ".rivalrebels.warning_bomb_will_explode_line_1");
            MutableText line2 = Text.translatable(RivalRebels.MODID + ".rivalrebels.warning_bomb_will_explode_line_2");
            MutableText line3 = Text.translatable(RivalRebels.MODID + ".rivalrebels.warning_bomb_will_explode_line_3");
            for (PlayerEntity player : world.getPlayers()) {
                player.sendMessage(line1, false);
                player.sendMessage(line2, false);
                player.sendMessage(line3, false);
            }
		}

		if (countdown % 20 == 0 && countdown <= 200 && RivalRebels.nuclearBombCountdown > 10) RivalRebelsSoundPlayer.playSound(world, 14, 0, getPos(), 100);

		if (countdown == 0 && nuclear != 0 && !world.isClient)
		{
            world.setBlockState(getPos(), Blocks.AIR.getDefaultState());
			world.setLightningTicksLeft(2);
			float pitch = 0;
			float yaw = switch (this.getCachedState().get(BlockTheoreticalTsarBomba.META)) {
                case 2 -> 180;
                case 3 -> 0;
                case 4 -> 270;
                case 5 -> 90;
                default -> 0;
            };

            EntityTheoreticalTsar tsar = new EntityTheoreticalTsar(world, getPos().getX()+0.5f, getPos().getY()+1f, getPos().getZ()+0.5f, yaw, pitch, nuclear, hasTrollface);
			world.spawnEntity(tsar);
		}

		if (countdown == 0 && nuclear == 0)
		{
            world.setBlockState(getPos(), Blocks.AIR.getDefaultState());
			world.createExplosion(null, getPos().getX(), getPos().getY(), getPos().getZ(), 4, World.ExplosionSourceType.NONE);
		}
    }

    @Override
    public Text getDisplayName() {
        return Text.of("Theoretical Tsar Bomba");
    }

    @Override
    public void clear() {
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
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ContainerTheoreticalTsar(syncId, inv, this, propertyDelegate);
    }

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
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
        public int size() {
            return 3;
        }
    };

}
