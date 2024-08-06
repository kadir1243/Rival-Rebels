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

import assets.rivalrebels.ClientProxy;
import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.common.command.CommandRobot;
import assets.rivalrebels.common.entity.EntityRhodes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public record RhodesJumpPacket(int id, boolean jump,
                               boolean rocket,
                               boolean laser,
                               boolean fire,
                               boolean forcefield,
                               boolean plasma,
                               boolean nuke,
                               boolean stop,
                               boolean b2spirit,
                               boolean guard) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, RhodesJumpPacket> STREAM_CODEC = StreamCodec.ofMember(RhodesJumpPacket::toBytes, RhodesJumpPacket::fromBytes);
    public static final Type<RhodesJumpPacket> PACKET_TYPE = new Type<>(RRIdentifiers.create("rhodesjump"));

    @Environment(EnvType.CLIENT)
    public RhodesJumpPacket(int id) {
        this(id,
            ClientProxy.RHODES_JUMP_KEY.isDown(),
            ClientProxy.RHODES_ROCKET_KEY.isDown(),
            ClientProxy.RHODES_LASER_KEY.isDown(),
            ClientProxy.RHODES_FIRE_KEY.isDown(),
            ClientProxy.RHODES_FORCE_FIELD_KEY.isDown(),
            ClientProxy.RHODES_PLASMA_KEY.isDown(),
            ClientProxy.RHODES_NUKE_KEY.isDown(),
            ClientProxy.RHODES_STOP_KEY.isDown(),
            ClientProxy.RHODES_B2SPIRIT_KEY.isDown(),
            ClientProxy.RHODES_GUARD_KEY.isDown()
        );
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
            if (m.guard && CommandRobot.rhodesExit) {
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
