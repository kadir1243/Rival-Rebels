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
package io.github.kadir1243.rivalrebels.common.round;

import com.google.common.base.Suppliers;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public enum RivalRebelsClass implements StringRepresentable {
	NONE(0, 0xFFFFFF, "NONE", RRIdentifiers.guitrivalrebels),
	REBEL(1, 0xFF0000, "REBEL", RRIdentifiers.guitrebel),
	NUKER(2, 0xFFFF00, "NUKER", RRIdentifiers.guitnuker),
	INTEL(3, 0x00FFBB, "INTEL", RRIdentifiers.guitintel),
	HACKER(4, 0x00FF00, "HACKER", RRIdentifiers.guithacker);

    public static final Codec<RivalRebelsClass> CODEC = StringRepresentable.fromValues(RivalRebelsClass::values);
    public static final StreamCodec<ByteBuf, RivalRebelsClass> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

	private final Supplier<ItemStack[]> inventory;
	public final ResourceLocation resource;
	public final String name;
	public final int color;
	public final int id;

	RivalRebelsClass(int id, int color, String name, ResourceLocation resource) {
		this.id = id;
		this.color = color;
		this.name = name;
		this.resource = resource;
		this.inventory = Suppliers.memoize(getItems(this)::get);
	}

    public List<ItemStack> getInventory() {
        return Arrays.asList(inventory.get());
    }

    public String getDescriptionTranslationKey() {
        return RRIdentifiers.MODID + ".class." + name + ".description";
    }

    public String getMiniDescriptionTranslationKey() {
        return RRIdentifiers.MODID + ".class." + name + ".minidesc";
    }

    public Component getDescriptionTranslation() {
        return Component.translatable(getDescriptionTranslationKey());
    }

    public Component getMiniDescriptionTranslation() {
        return Component.translatable(getMiniDescriptionTranslationKey());
    }

    @Override
    public String getSerializedName() {
        return name;
    }

    private static Supplier<ItemStack[]> getItems(RivalRebelsClass playerClass) {
        return () -> switch (playerClass) {
            case NONE -> new ItemStack[0];
            case INTEL -> new ItemStack[]{
                RRItems.tesla.toStack(),
                RRItems.safepill.toStack(3),
                RRItems.roddisk.toStack(),
                //new ItemStack(RivalRebels.binoculars),
                RRItems.pliers.toStack(),
                RRItems.armyshovel.toStack(),
                RRItems.knife.toStack(5),
                RRItems.gasgrenade.toStack(6),
                RRItems.chip.toStack(),
                //new ItemStack(RivalRebels.controller),
                RRItems.remote.toStack(),
                new ItemStack(RRBlocks.remotecharge, 8),
                //new ItemStack(RivalRebels.core1),
                RRBlocks.barricade.toStack(2),
                RRBlocks.tower.toStack(4),
                RRBlocks.quicksandtrap.toStack(4),
                RRBlocks.mariotrap.toStack(4),
                RRBlocks.minetrap.toStack(4),
                //new ItemStack(RivalRebels.supplies, 2),
                RRItems.battery.toStack(64),
                RRItems.battery.toStack(64),
                new ItemStack(RRBlocks.steel, 16),
                new ItemStack(RRBlocks.jump, 8)
            };
            case REBEL -> new ItemStack[]{
                RRItems.rpg.toStack(),
                RRItems.einsten.toStack(),
                RRItems.expill.toStack(3),
                RRItems.roddisk.toStack(),
                //new ItemStack(RivalRebels.binoculars),
                RRItems.pliers.toStack(),
                RRItems.armyshovel.toStack(),
                RRItems.knife.toStack(5),
                RRItems.gasgrenade.toStack(6),
                RRItems.chip.toStack(),
                //new ItemStack(RivalRebels.bastion, 2),
                RRBlocks.tower.toStack(2),
                RRBlocks.barricade.toStack(2),
                RRBlocks.quicksandtrap.toStack(2),
                RRBlocks.explosives.toStack(),
                //new ItemStack(RivalRebels.ammunition),
                RRBlocks.bunker.toStack(),
                RRItems.rocket.toStack(64),
                RRItems.redrod.toStack(),
                RRItems.redrod.toStack(),
                new ItemStack(RRBlocks.jump, 4)
            };
            case NUKER -> new ItemStack[]{
                RRItems.flamethrower.toStack(),
                RRItems.safepill.toStack(3),
                RRItems.roddisk.toStack(),
                //new ItemStack(RivalRebels.binoculars),
                RRItems.pliers.toStack(),
                RRItems.armyshovel.toStack(),
                RRItems.chip.toStack(),
                RRBlocks.loader.toStack(),
                RRBlocks.bunker.toStack(2),
                RRBlocks.minetrap.toStack(2),
                RRBlocks.nukeCrateTop.toStack(),
                RRBlocks.nukeCrateBottom.toStack(),
                RRItems.NUCLEAR_ROD.toStack(),
                new ItemStack(RRBlocks.explosives, 2),
                new ItemStack(RRBlocks.tower, 2),
                //new ItemStack(RivalRebels.ammunition),
                RRItems.fuel.toStack(64),
                RRBlocks.jump.toStack(4)
                //new ItemStack(RivalRebels.steel, 16),
            };
            case HACKER -> new ItemStack[]{
                RRItems.plasmacannon.toStack(),
                RRItems.expill.toStack(3),
                RRItems.roddisk.toStack(),
                //new ItemStack(RivalRebels.binoculars),
                RRItems.pliers.toStack(),
                RRItems.armyshovel.toStack(),
                RRItems.chip.toStack(),
                //new ItemStack(RivalRebels.controller),
                RRBlocks.loader.toStack(),
                RRBlocks.breadbox.toStack(),
                //new ItemStack(RivalRebels.remote),
                RRItems.fuse.toStack(),
                RRItems.antenna.toStack(),
                //new ItemStack(RivalRebels.forcefieldnode, 2),
                //new ItemStack(RivalRebels.ffreciever, 2),
                new ItemStack(RRBlocks.bastion, 4),
                new ItemStack(RRBlocks.barricade, 4),
                new ItemStack(RRBlocks.bunker, 4),
                //new ItemStack(RivalRebels.mariotrap, 2),
                new ItemStack(RRBlocks.ammunition, 3),
                RRItems.hydrod.toStack(),
                new ItemStack(RRBlocks.quicksandtrap, 4),
                new ItemStack(RRBlocks.mariotrap, 4),
                new ItemStack(RRBlocks.steel, 32),
                new ItemStack(RRBlocks.jump, 8)
            };
        };
    }
}
