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

import assets.rivalrebels.common.entity.EntityRhodes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RhodesPacket {
    public float bodyyaw;
    int id = 0;
    boolean forcefield = false;
    float scale = 0;
    float headyaw = 0;
    float headpitch = 0;
    float leftarmyaw = 0;
    float leftarmpitch = 0;
    float rightarmyaw = 0;
    float rightarmpitch = 0;
    float leftthighpitch = 0;
    float rightthighpitch = 0;
    float leftshinpitch = 0;
    float rightshinpitch = 0;
    int health;
    byte laserOn;
    byte colorType;
    int b2energy;
    int riderid;
    int pass1id;
    int pass2id;
    int rocketcount;
    int energy;
    int flamecount;
    int nukecount;
    String texloc;
    int texfolder;

    public RhodesPacket() {
    }

    public RhodesPacket(EntityRhodes er) {
        id = er.getId();
        bodyyaw = er.bodyyaw;
        headyaw = er.headyaw;
        headpitch = er.headpitch;
        leftarmyaw = er.leftarmyaw;
        leftarmpitch = er.leftarmpitch;
        rightarmyaw = er.rightarmyaw;
        rightarmpitch = er.rightarmpitch;
        leftthighpitch = er.leftthighpitch;
        rightthighpitch = er.rightthighpitch;
        leftshinpitch = er.leftshinpitch;
        rightshinpitch = er.rightshinpitch;
        health = er.health;
        laserOn = er.laserOn;
        forcefield = er.forcefield;
        colorType = er.colorType;
        b2energy = er.b2energy;
        riderid = er.rider != null ? er.rider.getId() : -1;
        pass1id = er.passenger1 != null ? er.passenger1.getId() : -1;
        pass2id = er.passenger2 != null ? er.passenger2.getId() : -1;
        rocketcount = er.rocketcount;
        energy = er.energy;
        flamecount = er.flamecount;
        nukecount = er.nukecount;
        texloc = er.itexloc;
        texfolder = er.itexfolder;
        scale = er.scale;
    }

    public static RhodesPacket fromBytes(PacketByteBuf buf) {
        RhodesPacket packet = new RhodesPacket();
        packet.id = buf.readInt();
        packet.forcefield = buf.readBoolean();
        packet.bodyyaw = buf.readFloat();
        packet.headyaw = buf.readFloat();
        packet.headpitch = buf.readFloat();
        packet.leftarmyaw = buf.readFloat();
        packet.leftarmpitch = buf.readFloat();
        packet.rightarmyaw = buf.readFloat();
        packet.rightarmpitch = buf.readFloat();
        packet.leftthighpitch = buf.readFloat();
        packet.rightthighpitch = buf.readFloat();
        packet.leftshinpitch = buf.readFloat();
        packet.rightshinpitch = buf.readFloat();
        packet.health = buf.readInt();
        packet.laserOn = buf.readByte();
        packet.colorType = buf.readByte();
        packet.b2energy = buf.readInt();
        packet.riderid = buf.readInt();
        packet.pass1id = buf.readInt();
        packet.pass2id = buf.readInt();
        packet.scale = buf.readFloat();
        packet.rocketcount = buf.readInt();
        packet.energy = buf.readInt();
        packet.flamecount = buf.readInt();
        packet.nukecount = buf.readByte();
        packet.texfolder = buf.readByte();
        if (packet.texfolder != 0) {
            packet.texloc = buf.readString();
            packet.texfolder -= packet.texfolder % 10;
            packet.texfolder /= 10;
        } else {
            packet.texfolder = -1;
        }
        return packet;
    }

    public static void onMessage(RhodesPacket m, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            Entity e = MinecraftClient.getInstance().world.getEntityById(m.id);
            if (e instanceof EntityRhodes er) {
                er.lastbodyyaw = er.bodyyaw;
                er.lastheadyaw = er.headyaw;
                er.lastheadpitch = er.headpitch;
                er.lastleftarmyaw = er.leftarmyaw;
                er.lastleftarmpitch = er.leftarmpitch;
                er.lastrightarmyaw = er.rightarmyaw;
                er.lastrightarmpitch = er.rightarmpitch;
                er.lastleftthighpitch = er.leftthighpitch;
                er.lastrightthighpitch = er.rightthighpitch;
                er.lastleftshinpitch = er.leftshinpitch;
                er.lastrightshinpitch = er.rightshinpitch;
                if (Math.abs(er.bodyyaw - m.bodyyaw) > 90) {
                    er.lastbodyyaw = m.bodyyaw;
                }
                if (Math.abs(er.rightarmyaw - m.rightarmyaw) > 90) {
                    er.lastrightarmyaw = m.rightarmyaw;
                }
                if (Math.abs(er.leftarmyaw - m.leftarmyaw) > 90) {
                    er.lastleftarmyaw = m.leftarmyaw;
                }
                er.bodyyaw = m.bodyyaw;
                er.headyaw = m.headyaw;
                er.headpitch = m.headpitch;
                er.leftarmyaw = m.leftarmyaw;
                er.leftarmpitch = m.leftarmpitch;
                er.rightarmyaw = m.rightarmyaw;
                er.rightarmpitch = m.rightarmpitch;
                er.leftthighpitch = m.leftthighpitch;
                er.rightthighpitch = m.rightthighpitch;
                er.leftshinpitch = m.leftshinpitch;
                er.rightshinpitch = m.rightshinpitch;
                er.health = m.health;
                er.laserOn = m.laserOn;
                er.forcefield = m.forcefield;
                er.colorType = m.colorType;
                er.b2energy = m.b2energy;
                er.ticksSinceLastPacket = 0;
                er.rocketcount = m.rocketcount;
                er.energy = m.energy;
                er.flamecount = m.flamecount;
                er.nukecount = m.nukecount;
                er.itexloc = m.texloc;
                er.itexfolder = m.texfolder;
                er.scale = m.scale;
                if (er.health <= 0 && er.rider != null) {
                    er.rider.setPosition(er.getX() + 5, er.getY() - 12, er.getZ());
                    er.rider.getAbilities().invulnerable = false;
                    er.rider = null;
                } else {
                    er.rider = m.riderid == -1 ? null : (PlayerEntity) er.world.getEntityById(m.riderid);
                    er.passenger1 = m.pass1id == -1 ? null : (PlayerEntity) er.world.getEntityById(m.pass1id);
                    er.passenger2 = m.pass2id == -1 ? null : (PlayerEntity) er.world.getEntityById(m.pass2id);
                }
            }
        });
    }

    public static void toBytes(RhodesPacket packet, PacketByteBuf buf) {
        buf.writeInt(packet.id);
        buf.writeBoolean(packet.forcefield);
        buf.writeFloat(packet.bodyyaw);
        buf.writeFloat(packet.headyaw);
        buf.writeFloat(packet.headpitch);
        buf.writeFloat(packet.leftarmyaw);
        buf.writeFloat(packet.leftarmpitch);
        buf.writeFloat(packet.rightarmyaw);
        buf.writeFloat(packet.rightarmpitch);
        buf.writeFloat(packet.leftthighpitch);
        buf.writeFloat(packet.rightthighpitch);
        buf.writeFloat(packet.leftshinpitch);
        buf.writeFloat(packet.rightshinpitch);
        buf.writeInt(packet.health);
        buf.writeByte(packet.laserOn);
        buf.writeByte(packet.colorType);
        buf.writeInt(packet.b2energy);
        buf.writeInt(packet.riderid);
        buf.writeInt(packet.pass1id);
        buf.writeInt(packet.pass2id);
        buf.writeFloat(packet.scale);
        buf.writeInt(packet.rocketcount);
        buf.writeInt(packet.energy);
        buf.writeInt(packet.flamecount);
        buf.writeByte(packet.nukecount);
        if (packet.texfolder == -1) {
            buf.writeByte(0);
        } else {
            buf.writeByte(packet.texfolder * 10 + packet.texloc.length());
            buf.writeString(packet.texloc);
        }
    }
}
