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
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ResetPacket() implements CustomPacketPayload {
    public static final ResetPacket INSTANCE = new ResetPacket();
    public static final StreamCodec<FriendlyByteBuf, ResetPacket> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final Type<ResetPacket> PACKET_TYPE = new Type<>(RRIdentifiers.create("reset"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }

    public static void onMessage(ResetPacket packet, IPayloadContext context) {
        Player player = context.player();
        context.enqueueWork(() -> {
            RivalRebelsPlayer p = RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile());
            if (!p.isreset && p.resets > 0) {
                p.isreset = true;
                p.resets--;
                player.getInventory().clearContent();
                RivalRebels.round.rrplayerlist.refreshForWorld(player.level());
            }
        });
    }
}
