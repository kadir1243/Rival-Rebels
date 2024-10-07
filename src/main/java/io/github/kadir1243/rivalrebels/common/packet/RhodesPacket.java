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
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class RhodesPacket implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, RhodesPacket> STREAM_CODEC = StreamCodec.ofMember(RhodesPacket::write, RhodesPacket::fromBytes);

    public static final Type<RhodesPacket> PACKET_TYPE = new Type<>(RRIdentifiers.create("rhodes_packet"));
    public float bodyyaw;
    int id = 0;
    float headpitch = 0;
    float leftarmyaw = 0;
    float leftarmpitch = 0;
    float rightarmyaw = 0;
    float rightarmpitch = 0;
    float leftthighpitch = 0;
    float rightthighpitch = 0;
    float leftshinpitch = 0;
    float rightshinpitch = 0;
    int riderid;
    int pass1id;
    int pass2id;

    public RhodesPacket() {
    }

    public RhodesPacket(EntityRhodes er) {
        id = er.getId();
        bodyyaw = er.bodyyaw;
        headpitch = er.headpitch;
        leftarmyaw = er.leftarmyaw;
        leftarmpitch = er.leftarmpitch;
        rightarmyaw = er.rightarmyaw;
        rightarmpitch = er.rightarmpitch;
        leftthighpitch = er.leftthighpitch;
        rightthighpitch = er.rightthighpitch;
        leftshinpitch = er.leftshinpitch;
        rightshinpitch = er.rightshinpitch;
        riderid = er.rider != null ? er.rider.getId() : -1;
        pass1id = er.passenger1 != null ? er.passenger1.getId() : -1;
        pass2id = er.passenger2 != null ? er.passenger2.getId() : -1;
    }

    public static RhodesPacket fromBytes(FriendlyByteBuf buf) {
        RhodesPacket packet = new RhodesPacket();
        packet.id = buf.readInt();
        packet.bodyyaw = buf.readFloat();
        packet.headpitch = buf.readFloat();
        packet.leftarmyaw = buf.readFloat();
        packet.leftarmpitch = buf.readFloat();
        packet.rightarmyaw = buf.readFloat();
        packet.rightarmpitch = buf.readFloat();
        packet.leftthighpitch = buf.readFloat();
        packet.rightthighpitch = buf.readFloat();
        packet.leftshinpitch = buf.readFloat();
        packet.rightshinpitch = buf.readFloat();
        packet.riderid = buf.readInt();
        packet.pass1id = buf.readInt();
        packet.pass2id = buf.readInt();

        return packet;
    }

    public static void onMessage(RhodesPacket m, IPayloadContext context) {
        Entity e = context.player().level().getEntity(m.id);
        if (e instanceof EntityRhodes er) {
            er.lastbodyyaw = er.bodyyaw;
            er.lastheadpitch = er.headpitch;
            er.lastleftarmyaw = er.leftarmyaw;
            er.lastleftarmpitch = er.leftarmpitch;
            er.lastrightarmyaw = er.rightarmyaw;
            er.lastrightarmpitch = er.rightarmpitch;
            er.lastleftthighpitch = er.leftthighpitch;
            er.lastrightthighpitch = er.rightthighpitch;
            er.lastleftshinpitch = er.leftshinpitch;
            er.lastrightshinpitch = er.rightshinpitch;
            if (Mth.abs(er.bodyyaw - m.bodyyaw) > 90) {
                er.lastbodyyaw = m.bodyyaw;
            }
            if (Mth.abs(er.rightarmyaw - m.rightarmyaw) > 90) {
                er.lastrightarmyaw = m.rightarmyaw;
            }
            if (Mth.abs(er.leftarmyaw - m.leftarmyaw) > 90) {
                er.lastleftarmyaw = m.leftarmyaw;
            }
            er.bodyyaw = m.bodyyaw;
            er.headpitch = m.headpitch;
            er.leftarmyaw = m.leftarmyaw;
            er.leftarmpitch = m.leftarmpitch;
            er.rightarmyaw = m.rightarmyaw;
            er.rightarmpitch = m.rightarmpitch;
            er.leftthighpitch = m.leftthighpitch;
            er.rightthighpitch = m.rightthighpitch;
            er.leftshinpitch = m.leftshinpitch;
            er.rightshinpitch = m.rightshinpitch;
            er.ticksSinceLastPacket = 0;
            if (er.getHealth() <= 0 && er.rider != null) {
                er.rider.setPos(er.getX() + 5, er.getY() - 12, er.getZ());
                er.rider.getAbilities().invulnerable = false;
                er.rider = null;
            } else {
                er.rider = m.riderid == -1 ? null : (Player) er.level().getEntity(m.riderid);
                er.passenger1 = m.pass1id == -1 ? null : (Player) er.level().getEntity(m.pass1id);
                er.passenger2 = m.pass2id == -1 ? null : (Player) er.level().getEntity(m.pass2id);
            }
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeFloat(bodyyaw);
        buf.writeFloat(headpitch);
        buf.writeFloat(leftarmyaw);
        buf.writeFloat(leftarmpitch);
        buf.writeFloat(rightarmyaw);
        buf.writeFloat(rightarmpitch);
        buf.writeFloat(leftthighpitch);
        buf.writeFloat(rightthighpitch);
        buf.writeFloat(leftshinpitch);
        buf.writeFloat(rightshinpitch);
        buf.writeInt(riderid);
        buf.writeInt(pass1id);
        buf.writeInt(pass2id);
    }
}
