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
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class RhodesJumpPacket implements FabricPacket {
    public static final PacketType<RhodesJumpPacket> PACKET_TYPE = PacketType.create(new Identifier(RivalRebels.MODID, "rhodesjump"), RhodesJumpPacket::fromBytes);
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

    public static RhodesJumpPacket fromBytes(PacketByteBuf buf) {
        return new RhodesJumpPacket(buf.readInt(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean());
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(id);
        buf.writeBoolean(jump);
        buf.writeBoolean(rocket);
        buf.writeBoolean(laser);
        buf.writeBoolean(fire);
        buf.writeBoolean(forcefield);
        buf.writeBoolean(plasma);
        buf.writeBoolean(nuke);
        buf.writeBoolean(stop);
        buf.writeBoolean(b2spirit);
        buf.writeBoolean(guard);
    }

    @Override
    public PacketType<?> getType() {
        return PACKET_TYPE;
    }

    public static void onMessage(RhodesJumpPacket m, PlayerEntity player) {
        Entity e = player.getWorld().getEntityById(m.id);
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
