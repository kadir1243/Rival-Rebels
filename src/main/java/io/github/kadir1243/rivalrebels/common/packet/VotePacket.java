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
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record VotePacket(boolean newRound) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, VotePacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.BOOL,
        VotePacket::newRound,
        VotePacket::new
    );
    public static final Type<VotePacket> PACKET_TYPE = new Type<>(RRIdentifiers.create("vote_packet"));

    public static void onMessage(VotePacket m, IPayloadContext context) {
        RivalRebelsPlayer p = RivalRebels.round.rrplayerlist.getForGameProfile(context.player().getGameProfile());
        if (!p.voted) {
            p.voted = true;
            if (m.newRound) RivalRebels.round.newBattleVotes++;
            else RivalRebels.round.waitVotes++;
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }
}
