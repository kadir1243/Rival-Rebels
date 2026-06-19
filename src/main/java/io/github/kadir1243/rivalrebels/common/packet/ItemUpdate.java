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
package io.github.kadir1243.rivalrebels.common.packet;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.RivalRebels;
import io.github.kadir1243.rivalrebels.common.item.components.FlameThrowerMode;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import io.github.kadir1243.rivalrebels.common.item.weapon.ItemFlameThrower;
import io.github.kadir1243.rivalrebels.common.item.weapon.ItemTesla;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

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

    public static void onMessage(ItemUpdate message, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (message.item() > context.player().getInventory().getContainerSize() || message.item() < 0) throw new UnsupportedOperationException("Item is out of bounds");
            ItemStack stack = context.player().getInventory().getItem(message.item());
            if (stack.getItem() instanceof ItemTesla) {
                stack.set(RRComponents.TESLA_DIAL, message.value());
            }
            if (stack.getItem() instanceof ItemFlameThrower) {
                stack.set(RRComponents.FLAME_THROWER_MODE, new FlameThrowerMode(message.value()));
            }
        }).exceptionally(throwable -> {
            context.disconnect(Component.literal("Unknown packet with exception " + throwable.getLocalizedMessage()));
            RivalRebels.LOGGER.error("Unknown packet with exception", throwable);
            return null;
        });
    }
}
