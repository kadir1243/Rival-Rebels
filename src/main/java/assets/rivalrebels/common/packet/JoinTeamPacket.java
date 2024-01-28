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
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.round.RivalRebelsClass;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class JoinTeamPacket {
	private final RivalRebelsClass rrclass;
	private final RivalRebelsTeam rrteam;

    public JoinTeamPacket(RivalRebelsClass rrclass, RivalRebelsTeam rrteam) {
        this.rrclass = rrclass;
        this.rrteam = rrteam;
    }

    public static JoinTeamPacket fromBytes(PacketByteBuf buf) {
        return new JoinTeamPacket(buf.readEnumConstant(RivalRebelsClass.class), buf.readEnumConstant(RivalRebelsTeam.class));
	}

	public static void toBytes(JoinTeamPacket packet, PacketByteBuf buf) {
		buf.writeEnumConstant(packet.rrclass);
		buf.writeEnumConstant(packet.rrteam);
	}

    public static void onMessage(JoinTeamPacket m, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        ServerPlayerEntity player = context.getSender();
        RivalRebelsPlayer p = RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile());

        context.enqueueWork(() -> {
            if (p.isreset) {
                p.isreset = false;
                p.rrclass = m.rrclass;
                p.rrteam = m.rrteam;
                Scoreboard scrb = RivalRebels.round.world.getScoreboard();
                {
                    Team playerTeam = scrb.getPlayerTeam(p.getUsername());
                    if (playerTeam != null) {
                        scrb.removePlayerFromTeam(p.getUsername(), playerTeam);
                    }
                }
                scrb.addPlayerToTeam(p.getUsername(), new Team(player.getEntityWorld().getScoreboard(), p.rrteam.toString()));

                for (ItemStack stack : m.rrclass.inventory) {
                    player.getInventory().insertStack(stack.copy());
                }

                if (m.rrteam == RivalRebelsTeam.OMEGA) {
                    switch (m.rrclass) {
                        case REBEL -> {
                            player.equipStack(EquipmentSlot.FEET, RRItems.orebelboots.getDefaultStack());
                            player.equipStack(EquipmentSlot.LEGS, RRItems.orebelpants.getDefaultStack());
                            player.equipStack(EquipmentSlot.CHEST, RRItems.orebelchest.getDefaultStack());
                            player.equipStack(EquipmentSlot.HEAD, RRItems.orebelhelmet.getDefaultStack());
                        }
                        case NUKER -> {
                            player.equipStack(EquipmentSlot.FEET, RRItems.onukerboots.getDefaultStack());
                            player.equipStack(EquipmentSlot.LEGS, RRItems.onukerpants.getDefaultStack());
                            player.equipStack(EquipmentSlot.CHEST, RRItems.onukerchest.getDefaultStack());
                            player.equipStack(EquipmentSlot.HEAD, RRItems.onukerhelmet.getDefaultStack());
                        }
                        case INTEL -> {
                            player.equipStack(EquipmentSlot.FEET, RRItems.ointelboots.getDefaultStack());
                            player.equipStack(EquipmentSlot.LEGS, RRItems.ointelpants.getDefaultStack());
                            player.equipStack(EquipmentSlot.CHEST, RRItems.ointelchest.getDefaultStack());
                            player.equipStack(EquipmentSlot.HEAD, RRItems.ointelhelmet.getDefaultStack());
                        }
                        case HACKER -> {
                            player.equipStack(EquipmentSlot.FEET, RRItems.ohackerboots.getDefaultStack());
                            player.equipStack(EquipmentSlot.LEGS, RRItems.ohackerpants.getDefaultStack());
                            player.equipStack(EquipmentSlot.CHEST, RRItems.ohackerchest.getDefaultStack());
                            player.equipStack(EquipmentSlot.HEAD, RRItems.ohackerhelmet.getDefaultStack());
                        }
                        case NONE -> {
                        }
                    }
                } else if (m.rrteam == RivalRebelsTeam.SIGMA) {
                    switch (m.rrclass) {
                        case REBEL -> {
                            player.equipStack(EquipmentSlot.FEET, RRItems.srebelboots.getDefaultStack());
                            player.equipStack(EquipmentSlot.LEGS, RRItems.srebelpants.getDefaultStack());
                            player.equipStack(EquipmentSlot.CHEST, RRItems.srebelchest.getDefaultStack());
                            player.equipStack(EquipmentSlot.HEAD, RRItems.srebelhelmet.getDefaultStack());
                        }
                        case NUKER -> {
                            player.equipStack(EquipmentSlot.FEET, RRItems.snukerboots.getDefaultStack());
                            player.equipStack(EquipmentSlot.LEGS, RRItems.snukerpants.getDefaultStack());
                            player.equipStack(EquipmentSlot.CHEST, RRItems.snukerchest.getDefaultStack());
                            player.equipStack(EquipmentSlot.HEAD, RRItems.snukerhelmet.getDefaultStack());
                        }
                        case INTEL -> {
                            player.equipStack(EquipmentSlot.FEET, RRItems.sintelboots.getDefaultStack());
                            player.equipStack(EquipmentSlot.LEGS, RRItems.sintelpants.getDefaultStack());
                            player.equipStack(EquipmentSlot.CHEST, RRItems.sintelchest.getDefaultStack());
                            player.equipStack(EquipmentSlot.HEAD, RRItems.sintelhelmet.getDefaultStack());
                        }
                        case HACKER -> {
                            player.equipStack(EquipmentSlot.FEET, RRItems.shackerboots.getDefaultStack());
                            player.equipStack(EquipmentSlot.LEGS, RRItems.shackerpants.getDefaultStack());
                            player.equipStack(EquipmentSlot.CHEST, RRItems.shackerchest.getDefaultStack());
                            player.equipStack(EquipmentSlot.HEAD, RRItems.shackerhelmet.getDefaultStack());
                        }
                        case NONE -> {
                        }
                    }
                }

                RivalRebels.round.rrplayerlist.refreshForWorld(player.world);
            }
            if (m.rrteam == RivalRebelsTeam.OMEGA) {
                double sx = RivalRebels.round.omegaObjPos.getX() + (RivalRebels.round.world.random.nextInt(2) - 0.5) * 30 + 0.5f;
                double sy = RivalRebels.round.omegaObjPos.getY() + 1;
                double sz = RivalRebels.round.omegaObjPos.getZ() + (RivalRebels.round.world.random.nextInt(2) - 0.5) * 30 + 0.5f;
                player.setPos(sx, sy, sz);
            } else if (m.rrteam == RivalRebelsTeam.SIGMA) {
                double sx = RivalRebels.round.sigmaObjPos.getX() + (RivalRebels.round.world.random.nextInt(2) - 0.5) * 30 + 0.5f;
                double sy = RivalRebels.round.sigmaObjPos.getY() + 1;
                double sz = RivalRebels.round.sigmaObjPos.getZ() + (RivalRebels.round.world.random.nextInt(2) - 0.5) * 30 + 0.5f;
                player.setPos(sx, sy, sz);
            }
        });
    }
}
