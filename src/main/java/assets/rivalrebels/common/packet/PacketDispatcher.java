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

import assets.rivalrebels.common.round.RivalRebelsPlayerList;
import assets.rivalrebels.common.round.RivalRebelsRound;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class PacketDispatcher {
    public static void registerPackets() {
        ServerPlayNetworking.registerGlobalReceiver(ItemUpdate.TYPE, (packet, player, responseSender) -> ItemUpdate.onMessage(packet, player));
        ServerPlayNetworking.registerGlobalReceiver(JoinTeamPacket.PACKET_TYPE, (packet, player, responseSender) -> JoinTeamPacket.onMessage(packet, player));
        ServerPlayNetworking.registerGlobalReceiver(ResetPacket.PACKET_TYPE, (packet, player, responseSender) -> ResetPacket.onMessage(player));
        ServerPlayNetworking.registerGlobalReceiver(VotePacket.PACKET_TYPE, (packet, player, responseSender) -> VotePacket.onMessage(packet, player));
        ServerPlayNetworking.registerGlobalReceiver(LaptopEngagePacket.PACKET_TYPE, (packet, player, responseSender) -> LaptopEngagePacket.onMessage(packet, player));
        ServerPlayNetworking.registerGlobalReceiver(RhodesJumpPacket.PACKET_TYPE, (packet, player, responseSender) -> RhodesJumpPacket.onMessage(packet, player));
    }

    @Environment(EnvType.CLIENT)
    public static void registerClientPackets() {
        ClientPlayNetworking.registerGlobalReceiver(GuiSpawnPacket.TYPE, GuiSpawnPacket::onMessage);
        ClientPlayNetworking.registerGlobalReceiver(RhodesPacket.PACKET_TYPE, RhodesPacket::onMessage);
        ClientPlayNetworking.registerGlobalReceiver(RivalRebelsPlayerList.PACKET_TYPE, RivalRebelsPlayerList::onMessage);
        ClientPlayNetworking.registerGlobalReceiver(RivalRebelsRound.PACKET_TYPE, RivalRebelsRound::onMessage);
    }
}
