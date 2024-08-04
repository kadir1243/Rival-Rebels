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
import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.block.trap.BlockAntimatterBomb;
import assets.rivalrebels.common.container.ContainerAntimatterBomb;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityAntimatterBomb;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.item.components.ChipData;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import com.mojang.authlib.GameProfile;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityAntimatterBomb extends BaseContainerBlockEntity implements Tickable {
	public GameProfile player = null;
	public RivalRebelsTeam	rrteam			= null;
	private NonNullList<ItemStack> items = NonNullList.withSize(36, ItemStack.EMPTY);

	public int				countdown		= RRConfig.SERVER.getNuclearBombCountdown() * 20;
	public int				nuclear			= 0;
	public int				hydrogen		= 0;
	public boolean			hasAntennae		= false;
	public boolean			hasExplosive	= false;
	public boolean			hasFuse			= false;
	public boolean			hasChip			= false;
	public boolean			hasTrollface	= false;
	public float			megaton			= 0;

    public TileEntityAntimatterBomb(BlockPos pos, BlockState state) {
        super(RRTileEntities.ANTIMATTER_BOMB, pos, state);
    }

    @Override
	public int getContainerSize()
	{
		return 21;
	}

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
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
	public int getMaxStackSize()
	{
		return 1;
	}

    @Override
	public boolean stillValid(Player player)
	{
        return Container.stillValidBlockEntity(this, player, 64);
	}

    @Override
    public void setChanged() {
        super.setChanged();

        nuclear = 0;
        hydrogen = 0;
        for (int i = 3; i <= 18; i++) {
            ItemStack is = getItem(i);
            if (!is.isEmpty() && is.isEnchanted()) {
                Item item = is.getItem();
                if (i < 11 && item == RRItems.NUCLEAR_ROD) {
                    nuclear++;
                } else if (i > 10 && item == RRItems.redrod) {
                    hydrogen++;
                }
                if (item == RRItems.trollmask) {
                    hasTrollface = true;
                }
            }
        }
        if (nuclear == hydrogen) megaton = nuclear * 6.25f;

        hasFuse = getItem(0).is(RRItems.fuse);

        hasChip = getItem(20).is(RRItems.chip);
        if (hasChip && getItem(20).has(RRComponents.CHIP_DATA)) {
            ChipData chipData = getItem(20).get(RRComponents.CHIP_DATA);
            rrteam = chipData.team();
            player = chipData.gameProfile();
        }

        hasAntennae = getItem(1).is(RRItems.antenna) && getItem(2).is(RRItems.antenna);

        hasExplosive = getItem(19).is(RRBlocks.timedbomb.asItem());
    }

    @Override
	public void tick() {
        boolean sp;
        if (level.isClientSide()) {
            sp = Minecraft.getInstance().isLocalServer();
        } else {
            MinecraftServer server = level.getServer();
            sp = server.getPlayerCount() == 1;
        }

		if (hasFuse && hasExplosive && nuclear == hydrogen && hasAntennae && hasChip)
		{
			double dist = 1000000;

			if (!sp || RRConfig.SERVER.isStopSelfnukeinSP())
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
			if (dist > (RRConfig.SERVER.getTsarBombaStrength() + (nuclear * hydrogen) + 29) * (RRConfig.SERVER.getTsarBombaStrength() + (nuclear * hydrogen) + 29))
			{
				if (countdown > 0) countdown--;
			}
			else if (!level.isClientSide())
			{
				this.setItem(0, ItemStack.EMPTY);
                for (Player player : level.players()) {
                    player.displayClientMessage(RRIdentifiers.warning().append(" ").append(level.getPlayerByUUID(player.getUUID()).getName().copy().withStyle(ChatFormatting.RED)), false);
                    player.displayClientMessage(Component.translatable(RRIdentifiers.MODID + ".tsar_bomb_defuse", rrteam == RivalRebelsTeam.OMEGA ? RRBlocks.omegaobj.getName() : rrteam == RivalRebelsTeam.SIGMA ? RRBlocks.sigmaobj.getName() : Component.nullToEmpty("NONE")), false);
                }
			}
		}
		else
		{
			countdown = RRConfig.SERVER.getNuclearBombCountdown() * 20;
			if (RRConfig.SERVER.getNuclearBombCountdown() == 0) countdown = 10;
		}

		if (countdown == 200 && !level.isClientSide && RRConfig.SERVER.getNuclearBombCountdown() > 10)
		{
            RRIdentifiers.sendWarningBombWillExplodeMessageToPlayers(getLevel());
		}

		if (countdown % 20 == 0 && countdown <= 200 && RRConfig.SERVER.getNuclearBombCountdown() > 10) RivalRebelsSoundPlayer.playSound(level, 14, 0, getBlockPos(), 100);

		if (countdown == 0 && nuclear != 0 && hydrogen != 0 && !level.isClientSide && nuclear == hydrogen)
		{
			level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
			level.setSkyFlashTime(2);
			float pitch = 0;
			float yaw = this.getBlockState().getValue(BlockAntimatterBomb.FACING).toYRot();

            EntityAntimatterBomb tsar = new EntityAntimatterBomb(level, getBlockPos().getX()+0.5f, getBlockPos().getY()+1f, getBlockPos().getZ()+0.5f, yaw, pitch, hydrogen, hasTrollface);
			level.addFreshEntity(tsar);
		}

		if (countdown == 0 && nuclear == 0 && hydrogen == 0)
		{
			level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
			level.explode(null, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), 4, Level.ExplosionInteraction.NONE);
		}
    }

    @Override
    protected Component getDefaultName() {
        return Component.nullToEmpty("Antimatter Bomb");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new ContainerAntimatterBomb(containerId, inventory, this, propertyDelegate);
    }

    private final ContainerData propertyDelegate = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> countdown;
                case 1 -> nuclear != hydrogen ? 1 : 0;
                case 2 -> hasExplosive && hasFuse && hasAntennae ? 1 : 0;
                case 3 -> (int) (megaton * 100);
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> countdown = value;
                case 3 -> megaton = value / 100F;
                default -> {}
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

}
