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
package assets.rivalrebels.common.packet;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.common.item.components.FlameThrowerMode;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.item.weapon.ItemFlameThrower;
import assets.rivalrebels.common.item.weapon.ItemTesla;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public record ItemUpdate(int item, int value) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, ItemUpdate> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT,
        ItemUpdate::item,
        ByteBufCodecs.INT,
        ItemUpdate::value,
        ItemUpdate::new
    );
    public static final Type<ItemUpdate> TYPE = new Type<>(RRIdentifiers.create("item_update"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void onMessage(ItemUpdate message, Player player) {
        ItemStack itemstack = player.getInventory().getItem(message.item);
        if (itemstack.getItem() instanceof ItemTesla) {
            itemstack.set(RRComponents.TESLA_DIAL, message.value());
        }
        if (itemstack.getItem() instanceof ItemFlameThrower) {
            itemstack.set(RRComponents.FLAME_THROWER_MODE, new FlameThrowerMode(message.value(), itemstack.getOrDefault(RRComponents.FLAME_THROWER_MODE, FlameThrowerMode.DEFAULT).isReady()));
        }
    }
}
