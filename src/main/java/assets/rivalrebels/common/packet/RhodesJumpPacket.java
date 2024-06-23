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
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.entity.EntityRhodes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class RhodesJumpPacket implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, RhodesJumpPacket> STREAM_CODEC = StreamCodec.ofMember(RhodesJumpPacket::toBytes, RhodesJumpPacket::fromBytes);
    public static final Type<RhodesJumpPacket> PACKET_TYPE = new Type<>(RRIdentifiers.create("rhodesjump"));
    private final int id;
    private final boolean jump;
    private final boolean rocket;
    private final boolean laser;
    private final boolean fire;
    private final boolean forcefield;
    private final boolean plasma;
    private final boolean nuke;
    private final boolean stop;
    private final boolean b2spirit;
    private final boolean guard;

    public RhodesJumpPacket(int id, boolean jump, boolean rocket, boolean laser, boolean fire, boolean forcefield, boolean plasma, boolean nuke, boolean stop, boolean b2spirit, boolean guard) {
        this.id = id;
        this.jump = jump;
        this.rocket = rocket;
        this.laser = laser;
        this.fire = fire;
        this.forcefield = forcefield;
        this.plasma = plasma;
        this.nuke = nuke;
        this.stop = stop;
        this.b2spirit = b2spirit;
        this.guard = guard;
    }

    public static RhodesJumpPacket fromBytes(FriendlyByteBuf buf) {
        return new RhodesJumpPacket(buf.readInt(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean());
    }

    public static void toBytes(RhodesJumpPacket packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.id);
        buf.writeBoolean(packet.jump);
        buf.writeBoolean(packet.rocket);
        buf.writeBoolean(packet.laser);
        buf.writeBoolean(packet.fire);
        buf.writeBoolean(packet.forcefield);
        buf.writeBoolean(packet.plasma);
        buf.writeBoolean(packet.nuke);
        buf.writeBoolean(packet.stop);
        buf.writeBoolean(packet.b2spirit);
        buf.writeBoolean(packet.guard);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }

    public static void onMessage(RhodesJumpPacket m, Player player) {
        Entity e = player.level().getEntity(m.id);
        if (e instanceof EntityRhodes er) {
            er.stop ^= m.stop;
            er.rocket = m.rocket;
            er.laser = m.laser;
            er.flame = m.fire;
            er.forcefield = m.forcefield;
            er.setPlasma(er.isPlasma() ^ m.plasma);
            er.bomb = m.nuke;
            er.jet = m.jump;
            er.b2spirit = m.b2spirit;
            er.guard = m.guard;
            if (m.guard && RivalRebels.rhodesExit) {
                if (er.rider != null) {
                    er.rider.getAbilities().invulnerable = false;
                    er.rider = null;
                }
            }
            if (m.jump && !er.stop) {
                er.flying = 2;
            }
        }

    }
}
