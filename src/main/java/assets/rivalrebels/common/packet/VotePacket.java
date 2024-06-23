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
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class VotePacket implements FabricPacket {
   public static final PacketType<VotePacket> PACKET_TYPE = PacketType.create(new Identifier(RivalRebels.MODID, "vote_packet"), VotePacket::fromBytes);
    private final boolean newround;

    public VotePacket(boolean vote) {
        newround = vote;
    }

    public static VotePacket fromBytes(PacketByteBuf buf) {
        return new VotePacket(buf.readBoolean());
    }

    public static void onMessage(VotePacket m, PlayerEntity player) {
        RivalRebelsPlayer p = RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile());
        if (!p.voted) {
            p.voted = true;
            if (m.newround) RivalRebels.round.newBattleVotes++;
            else RivalRebels.round.waitVotes++;
        }
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBoolean(newround);
    }

    @Override
    public PacketType<?> getType() {
        return PACKET_TYPE;
    }
}
