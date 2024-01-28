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
import assets.rivalrebels.common.round.RivalRebelsPlayerList;
import assets.rivalrebels.common.round.RivalRebelsRound;
import net.minecraft.util.Identifier;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketDispatcher {
    public static final SimpleChannel packetsys = NetworkRegistry.newSimpleChannel(new Identifier(RivalRebels.MODID, "network"), () -> "1", string -> true, string -> true);

    public static void registerPackets() {
        int packetCount = 0;
        packetsys.registerMessage(packetCount++, ItemUpdate.class, ItemUpdate::toBytes, ItemUpdate::fromBytes, ItemUpdate::onMessage);
        packetsys.registerMessage(packetCount++, RivalRebelsRound.class, RivalRebelsRound::toBytes, RivalRebelsRound::fromBytes, RivalRebelsRound::onMessage);
        packetsys.registerMessage(packetCount++, RivalRebelsPlayerList.class, RivalRebelsPlayerList::toBytes, RivalRebelsPlayerList::fromBytes, RivalRebelsPlayerList::onMessage);
        packetsys.registerMessage(packetCount++, GuiSpawnPacket.class, GuiSpawnPacket::toBytes, GuiSpawnPacket::fromBytes, GuiSpawnPacket::onMessage);
        packetsys.registerMessage(packetCount++, JoinTeamPacket.class, JoinTeamPacket::toBytes, JoinTeamPacket::fromBytes, JoinTeamPacket::onMessage);
        packetsys.registerMessage(packetCount++, ResetPacket.class, ResetPacket::toBytes, ResetPacket::fromBytes, ResetPacket::onMessage);
        packetsys.registerMessage(packetCount++, TextPacket.class, TextPacket::toBytes, TextPacket::fromBytes, TextPacket::onMessage);
        packetsys.registerMessage(packetCount++, VotePacket.class, VotePacket::toBytes, VotePacket::fromBytes, VotePacket::onMessage);
        packetsys.registerMessage(packetCount++, LaptopEngagePacket.class, LaptopEngagePacket::toBytes, LaptopEngagePacket::fromBytes, LaptopEngagePacket::onMessage);
        packetsys.registerMessage(packetCount++, RhodesPacket.class, RhodesPacket::toBytes, RhodesPacket::fromBytes, RhodesPacket::onMessage);
        packetsys.registerMessage(packetCount++, RhodesJumpPacket.class, RhodesJumpPacket::toBytes, RhodesJumpPacket::fromBytes, RhodesJumpPacket::onMessage);
    }
}
