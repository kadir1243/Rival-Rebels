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

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import net.minecraft.network.PacketByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class VotePacket {
    private final boolean newround;

    public VotePacket(boolean vote) {
        newround = vote;
    }

    public static VotePacket fromBytes(PacketByteBuf buf) {
        return new VotePacket(buf.readBoolean());
    }

    public static void onMessage(VotePacket m, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            RivalRebelsPlayer p = RivalRebels.round.rrplayerlist.getForGameProfile(context.getSender().getGameProfile());
            if (!p.voted) {
                p.voted = true;
                if (m.newround) RivalRebels.round.newBattleVotes++;
                else RivalRebels.round.waitVotes++;
            }
        });
    }

    public static void toBytes(VotePacket packet, PacketByteBuf buf) {
        buf.writeBoolean(packet.newround);
    }

}
