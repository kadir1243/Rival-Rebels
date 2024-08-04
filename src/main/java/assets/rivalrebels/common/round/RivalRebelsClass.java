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
package assets.rivalrebels.common.round;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.item.RRItems;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;

public enum RivalRebelsClass implements StringRepresentable {
	NONE(0, 0xFFFFFF, "NONE", RRIdentifiers.guitrivalrebels),

	REBEL(1, 0xFF0000, "REBEL", RRIdentifiers.guitrebel,
        RRItems.rpg.getDefaultInstance(),
        RRItems.einsten.getDefaultInstance(),
        new ItemStack(RRItems.expill, 3),
        RRItems.roddisk.getDefaultInstance(),
        //new ItemStack(RivalRebels.binoculars),
        RRItems.pliers.getDefaultInstance(),
        RRItems.armyshovel.getDefaultInstance(),
        new ItemStack(RRItems.knife, 5),
        new ItemStack(RRItems.gasgrenade, 6),
        RRItems.chip.getDefaultInstance(),
        //new ItemStack(RivalRebels.bastion, 2),
        new ItemStack(RRBlocks.tower, 2),
        new ItemStack(RRBlocks.barricade, 2),
        new ItemStack(RRBlocks.quicksandtrap, 2),
        RRBlocks.explosives.asItem().getDefaultInstance(),
        //new ItemStack(RivalRebels.ammunition),
        RRBlocks.bunker.asItem().getDefaultInstance(),
        new ItemStack(RRItems.rocket, 64),
        RRItems.redrod.getDefaultInstance(),
        RRItems.redrod.getDefaultInstance(),
        new ItemStack(RRBlocks.jump, 4)),

	NUKER(2, 0xFFFF00, "NUKER", RRIdentifiers.guitnuker,
        RRItems.flamethrower.getDefaultInstance(),
        new ItemStack(RRItems.safepill, 3),
        RRItems.roddisk.getDefaultInstance(),
        //new ItemStack(RivalRebels.binoculars),
        RRItems.pliers.getDefaultInstance(),
        RRItems.armyshovel.getDefaultInstance(),
        RRItems.chip.getDefaultInstance(),
        RRBlocks.loader.asItem().getDefaultInstance(),
        new ItemStack(RRBlocks.bunker, 2),
        new ItemStack(RRBlocks.minetrap, 2),
        RRBlocks.nukeCrateTop.asItem().getDefaultInstance(),
        RRBlocks.nukeCrateBottom.asItem().getDefaultInstance(),
        RRItems.NUCLEAR_ROD.getDefaultInstance(),
        new ItemStack(RRBlocks.explosives, 2),
        new ItemStack(RRBlocks.tower, 2),
        //new ItemStack(RivalRebels.ammunition),
        new ItemStack(RRItems.fuel, 64),
        new ItemStack(RRBlocks.jump, 4)
        //new ItemStack(RivalRebels.steel, 16),
    ),

	INTEL(3, 0x00FFBB, "INTEL", RRIdentifiers.guitintel,
        RRItems.tesla.getDefaultInstance(),
        new ItemStack(RRItems.safepill, 3),
        RRItems.roddisk.getDefaultInstance(),
        //new ItemStack(RivalRebels.binoculars),
        RRItems.pliers.getDefaultInstance(),
        RRItems.armyshovel.getDefaultInstance(),
        new ItemStack(RRItems.knife, 5),
        new ItemStack(RRItems.gasgrenade, 6),
        RRItems.chip.getDefaultInstance(),
        //new ItemStack(RivalRebels.controller),
        RRItems.remote.getDefaultInstance(),
        new ItemStack(RRBlocks.remotecharge, 8),
        //new ItemStack(RivalRebels.core1),
        new ItemStack(RRBlocks.barricade, 2),
        new ItemStack(RRBlocks.tower, 4),
        new ItemStack(RRBlocks.quicksandtrap, 4),
        new ItemStack(RRBlocks.mariotrap, 4),
        new ItemStack(RRBlocks.minetrap, 4),
        //new ItemStack(RivalRebels.supplies, 2),
        new ItemStack(RRItems.battery, 64),
        new ItemStack(RRItems.battery, 64),
        new ItemStack(RRBlocks.steel, 16),
        new ItemStack(RRBlocks.jump, 8)),

	HACKER(4, 0x00FF00, "HACKER", RRIdentifiers.guithacker,
        RRItems.plasmacannon.getDefaultInstance(),
        new ItemStack(RRItems.expill, 3),
        RRItems.roddisk.getDefaultInstance(),
        //new ItemStack(RivalRebels.binoculars),
        RRItems.pliers.getDefaultInstance(),
        RRItems.armyshovel.getDefaultInstance(),
        RRItems.chip.getDefaultInstance(),
        //new ItemStack(RivalRebels.controller),
        RRBlocks.loader.asItem().getDefaultInstance(),
        RRBlocks.breadbox.asItem().getDefaultInstance(),
        //new ItemStack(RivalRebels.remote),
        RRItems.fuse.getDefaultInstance(),
        RRItems.antenna.getDefaultInstance(),
        //new ItemStack(RivalRebels.forcefieldnode, 2),
        //new ItemStack(RivalRebels.ffreciever, 2),
        new ItemStack(RRBlocks.bastion, 4),
        new ItemStack(RRBlocks.barricade, 4),
        new ItemStack(RRBlocks.bunker, 4),
        //new ItemStack(RivalRebels.mariotrap, 2),
        new ItemStack(RRBlocks.ammunition, 3),
        RRItems.hydrod.getDefaultInstance(),
        new ItemStack(RRBlocks.quicksandtrap, 4),
        new ItemStack(RRBlocks.mariotrap, 4),
        new ItemStack(RRBlocks.steel, 32),
        new ItemStack(RRBlocks.jump, 8));

    public static final Codec<RivalRebelsClass> CODEC = StringRepresentable.fromValues(RivalRebelsClass::values);
    public static final StreamCodec<ByteBuf, RivalRebelsClass> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

	public final ItemStack[] inventory;
	public final ResourceLocation resource;
	public final String name;
	public final int color;
	public final int id;

	RivalRebelsClass(int id, int color, String name, ResourceLocation resource, ItemStack... inventory) {
		this.id = id;
		this.color = color;
		this.name = name;
		this.resource = resource;
		this.inventory = inventory;
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

    public static RivalRebelsClass getForID(int i) {
        return switch (i) {
            case 1 -> REBEL;
            case 2 -> NUKER;
            case 3 -> INTEL;
            case 4 -> HACKER;
            default -> NONE;
        };
    }
}
