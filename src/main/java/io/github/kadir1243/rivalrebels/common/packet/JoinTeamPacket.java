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
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsClass;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsPlayer;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsTeam;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record JoinTeamPacket(RivalRebelsClass rrclass, RivalRebelsTeam rrteam) implements CustomPacketPayload {
    public static final StreamCodec<ByteBuf, JoinTeamPacket> STREAM_CODEC = StreamCodec.composite(
        RivalRebelsClass.STREAM_CODEC,
        JoinTeamPacket::rrclass,
        RivalRebelsTeam.STREAM_CODEC,
        JoinTeamPacket::rrteam,
        JoinTeamPacket::new
    );
    public static final Type<JoinTeamPacket> PACKET_TYPE = new Type<>(RRIdentifiers.create("join_team"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }

    public static void onMessage(JoinTeamPacket m, IPayloadContext context) {
        Player player = context.player();
        RivalRebelsPlayer p = RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile());

        if (p.isreset) {
            p.isreset = false;
            p.rrclass = m.rrclass;
            p.rrteam = m.rrteam;
            Scoreboard scrb = RivalRebels.round.world.getScoreboard();
            scrb.resetAllPlayerScores(player);
            scrb.addPlayerToTeam(player.getScoreboardName(), new PlayerTeam(player.getCommandSenderWorld().getScoreboard(), p.rrteam.toString()));

            for (ItemStack stack : m.rrclass.getInventory()) {
                player.getInventory().add(stack.copy());
            }

            if (m.rrteam == RivalRebelsTeam.OMEGA) {
                switch (m.rrclass) {
                    case REBEL -> {
                        player.setItemSlot(EquipmentSlot.FEET, RRItems.orebelboots.toStack());
                        player.setItemSlot(EquipmentSlot.LEGS, RRItems.orebelpants.toStack());
                        player.setItemSlot(EquipmentSlot.CHEST, RRItems.orebelchest.toStack());
                        player.setItemSlot(EquipmentSlot.HEAD, RRItems.orebelhelmet.toStack());
                    }
                    case NUKER -> {
                        player.setItemSlot(EquipmentSlot.FEET, RRItems.onukerboots.toStack());
                        player.setItemSlot(EquipmentSlot.LEGS, RRItems.onukerpants.toStack());
                        player.setItemSlot(EquipmentSlot.CHEST, RRItems.onukerchest.toStack());
                        player.setItemSlot(EquipmentSlot.HEAD, RRItems.onukerhelmet.toStack());
                    }
                    case INTEL -> {
                        player.setItemSlot(EquipmentSlot.FEET, RRItems.ointelboots.toStack());
                        player.setItemSlot(EquipmentSlot.LEGS, RRItems.ointelpants.toStack());
                        player.setItemSlot(EquipmentSlot.CHEST, RRItems.ointelchest.toStack());
                        player.setItemSlot(EquipmentSlot.HEAD, RRItems.ointelhelmet.toStack());
                    }
                    case HACKER -> {
                        player.setItemSlot(EquipmentSlot.FEET, RRItems.ohackerboots.toStack());
                        player.setItemSlot(EquipmentSlot.LEGS, RRItems.ohackerpants.toStack());
                        player.setItemSlot(EquipmentSlot.CHEST, RRItems.ohackerchest.toStack());
                        player.setItemSlot(EquipmentSlot.HEAD, RRItems.ohackerhelmet.toStack());
                    }
                    case NONE -> {
                    }
                }
            } else if (m.rrteam == RivalRebelsTeam.SIGMA) {
                switch (m.rrclass) {
                    case REBEL -> {
                        player.setItemSlot(EquipmentSlot.FEET, RRItems.srebelboots.toStack());
                        player.setItemSlot(EquipmentSlot.LEGS, RRItems.srebelpants.toStack());
                        player.setItemSlot(EquipmentSlot.CHEST, RRItems.srebelchest.toStack());
                        player.setItemSlot(EquipmentSlot.HEAD, RRItems.srebelhelmet.toStack());
                    }
                    case NUKER -> {
                        player.setItemSlot(EquipmentSlot.FEET, RRItems.snukerboots.toStack());
                        player.setItemSlot(EquipmentSlot.LEGS, RRItems.snukerpants.toStack());
                        player.setItemSlot(EquipmentSlot.CHEST, RRItems.snukerchest.toStack());
                        player.setItemSlot(EquipmentSlot.HEAD, RRItems.snukerhelmet.toStack());
                    }
                    case INTEL -> {
                        player.setItemSlot(EquipmentSlot.FEET, RRItems.sintelboots.toStack());
                        player.setItemSlot(EquipmentSlot.LEGS, RRItems.sintelpants.toStack());
                        player.setItemSlot(EquipmentSlot.CHEST, RRItems.sintelchest.toStack());
                        player.setItemSlot(EquipmentSlot.HEAD, RRItems.sintelhelmet.toStack());
                    }
                    case HACKER -> {
                        player.setItemSlot(EquipmentSlot.FEET, RRItems.shackerboots.toStack());
                        player.setItemSlot(EquipmentSlot.LEGS, RRItems.shackerpants.toStack());
                        player.setItemSlot(EquipmentSlot.CHEST, RRItems.shackerchest.toStack());
                        player.setItemSlot(EquipmentSlot.HEAD, RRItems.shackerhelmet.toStack());
                    }
                    case NONE -> {
                    }
                }
            }

            RivalRebels.round.rrplayerlist.refreshForWorld(player.level());
        }
        if (m.rrteam == RivalRebelsTeam.OMEGA) {
            double sx = RivalRebels.round.omegaData.objPos().getX() + (RivalRebels.round.world.random.nextInt(2) - 0.5) * 30 + 0.5f;
            double sy = RivalRebels.round.omegaData.objPos().getY() + 1;
            double sz = RivalRebels.round.omegaData.objPos().getZ() + (RivalRebels.round.world.random.nextInt(2) - 0.5) * 30 + 0.5f;
            player.setPosRaw(sx, sy, sz);
        } else if (m.rrteam == RivalRebelsTeam.SIGMA) {
            double sx = RivalRebels.round.sigmaData.objPos().getX() + (RivalRebels.round.world.random.nextInt(2) - 0.5) * 30 + 0.5f;
            double sy = RivalRebels.round.sigmaData.objPos().getY() + 1;
            double sz = RivalRebels.round.sigmaData.objPos().getZ() + (RivalRebels.round.world.random.nextInt(2) - 0.5) * 30 + 0.5f;
            player.setPosRaw(sx, sy, sz);
        }
    }
}
