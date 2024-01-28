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
import assets.rivalrebels.common.entity.EntityRhodes;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RhodesJumpPacket {
    int id;
    boolean jump;
    boolean rocket;
    boolean laser;
    boolean fire;
    boolean forcefield;
    boolean plasma;
    boolean nuke;
    boolean stop;
    boolean b2spirit;
    boolean guard;

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

    public static RhodesJumpPacket fromBytes(PacketByteBuf buf) {
        return new RhodesJumpPacket(buf.readInt(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean());
    }

    public static void toBytes(RhodesJumpPacket packet, PacketByteBuf buf) {
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

    public static void onMessage(RhodesJumpPacket m, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            Entity e = context.getSender().world.getEntityById(m.id);
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
        });
    }
}
