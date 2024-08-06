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
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.round.RivalRebelsClass;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;

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

    public static void onMessage(JoinTeamPacket m, Player player) {
        RivalRebelsPlayer p = RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile());

        if (p.isreset) {
            p.isreset = false;
            p.rrclass = m.rrclass;
            p.rrteam = m.rrteam;
            Scoreboard scrb = RivalRebels.round.world.getScoreboard();
            scrb.resetAllPlayerScores(player);
            scrb.addPlayerToTeam(player.getScoreboardName(), new PlayerTeam(player.getCommandSenderWorld().getScoreboard(), p.rrteam.toString()));

            for (ItemStack stack : m.rrclass.inventory) {
                player.getInventory().add(stack.copy());
            }

            if (m.rrteam == RivalRebelsTeam.OMEGA) {
                switch (m.rrclass) {
                    case REBEL -> {
                        player.setItemSlot(EquipmentSlot.FEET, RRItems.orebelboots.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.LEGS, RRItems.orebelpants.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.CHEST, RRItems.orebelchest.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.HEAD, RRItems.orebelhelmet.getDefaultInstance());
                    }
                    case NUKER -> {
                        player.setItemSlot(EquipmentSlot.FEET, RRItems.onukerboots.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.LEGS, RRItems.onukerpants.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.CHEST, RRItems.onukerchest.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.HEAD, RRItems.onukerhelmet.getDefaultInstance());
                    }
                    case INTEL -> {
                        player.setItemSlot(EquipmentSlot.FEET, RRItems.ointelboots.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.LEGS, RRItems.ointelpants.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.CHEST, RRItems.ointelchest.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.HEAD, RRItems.ointelhelmet.getDefaultInstance());
                    }
                    case HACKER -> {
                        player.setItemSlot(EquipmentSlot.FEET, RRItems.ohackerboots.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.LEGS, RRItems.ohackerpants.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.CHEST, RRItems.ohackerchest.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.HEAD, RRItems.ohackerhelmet.getDefaultInstance());
                    }
                    case NONE -> {
                    }
                }
            } else if (m.rrteam == RivalRebelsTeam.SIGMA) {
                switch (m.rrclass) {
                    case REBEL -> {
                        player.setItemSlot(EquipmentSlot.FEET, RRItems.srebelboots.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.LEGS, RRItems.srebelpants.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.CHEST, RRItems.srebelchest.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.HEAD, RRItems.srebelhelmet.getDefaultInstance());
                    }
                    case NUKER -> {
                        player.setItemSlot(EquipmentSlot.FEET, RRItems.snukerboots.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.LEGS, RRItems.snukerpants.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.CHEST, RRItems.snukerchest.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.HEAD, RRItems.snukerhelmet.getDefaultInstance());
                    }
                    case INTEL -> {
                        player.setItemSlot(EquipmentSlot.FEET, RRItems.sintelboots.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.LEGS, RRItems.sintelpants.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.CHEST, RRItems.sintelchest.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.HEAD, RRItems.sintelhelmet.getDefaultInstance());
                    }
                    case HACKER -> {
                        player.setItemSlot(EquipmentSlot.FEET, RRItems.shackerboots.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.LEGS, RRItems.shackerpants.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.CHEST, RRItems.shackerchest.getDefaultInstance());
                        player.setItemSlot(EquipmentSlot.HEAD, RRItems.shackerhelmet.getDefaultInstance());
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
