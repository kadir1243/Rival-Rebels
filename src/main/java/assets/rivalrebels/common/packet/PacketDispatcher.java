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

import assets.rivalrebels.client.gui.GuiReactor;
import assets.rivalrebels.common.round.RivalRebelsPlayerList;
import assets.rivalrebels.common.round.RivalRebelsRound;
import assets.rivalrebels.common.tileentity.RRTileEntities;
import assets.rivalrebels.common.tileentity.TileEntityLaptop;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class PacketDispatcher {
    public static void registerPackets() {
        PayloadTypeRegistry.playC2S().register(ItemUpdate.TYPE, ItemUpdate.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(JoinTeamPacket.PACKET_TYPE, JoinTeamPacket.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(ResetPacket.PACKET_TYPE, ResetPacket.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(VotePacket.PACKET_TYPE, VotePacket.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(LaptopEngagePacket.PACKET_TYPE, LaptopEngagePacket.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(RhodesJumpPacket.PACKET_TYPE, RhodesJumpPacket.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(LaptopPressPacket.TYPE, LaptopPressPacket.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(ReactorStatePacket.TYPE, ReactorStatePacket.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(ReactorMachinesPacket.TYPE, ReactorMachinesPacket.STREAM_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(ItemUpdate.TYPE, (packet, context) -> ItemUpdate.onMessage(packet, context.player()));
        ServerPlayNetworking.registerGlobalReceiver(JoinTeamPacket.PACKET_TYPE, (packet, context) -> JoinTeamPacket.onMessage(packet, context.player()));
        ServerPlayNetworking.registerGlobalReceiver(ResetPacket.PACKET_TYPE, (packet, context) -> ResetPacket.onMessage(context.player()));
        ServerPlayNetworking.registerGlobalReceiver(VotePacket.PACKET_TYPE, (packet, context) -> VotePacket.onMessage(packet, context.player()));
        ServerPlayNetworking.registerGlobalReceiver(LaptopEngagePacket.PACKET_TYPE, (packet, context) -> LaptopEngagePacket.onMessage(packet, context.player()));
        ServerPlayNetworking.registerGlobalReceiver(RhodesJumpPacket.PACKET_TYPE, (packet, context) -> RhodesJumpPacket.onMessage(packet, context.player()));
        ServerPlayNetworking.registerGlobalReceiver(LaptopPressPacket.TYPE, (payload, context) -> context.player().level().getBlockEntity(payload.pos(), RRTileEntities.LAPTOP).ifPresent(TileEntityLaptop::onGoButtonPressed));
        ServerPlayNetworking.registerGlobalReceiver(ReactorStatePacket.TYPE, (payload, context) -> context.player().level().getBlockEntity(payload.pos(), RRTileEntities.REACTOR).ifPresent(tileEntityReactor -> {
            if (payload.packetType() == ReactorStatePacket.Type.EJECT_CORE) {
                tileEntityReactor.ejectCore();
            } else if (payload.packetType() == ReactorStatePacket.Type.TOGGLE_ON) {
                tileEntityReactor.toggleOn();
            }
        }));
        ServerPlayNetworking.registerGlobalReceiver(ReactorMachinesPacket.TYPE, (payload, context) -> context.player().level().getBlockEntity(payload.reactorPos(), RRTileEntities.REACTOR).ifPresent(tileEntityReactor -> {
            tileEntityReactor.entries.clear();
            for (ReactorMachinesPacket.MachineEntry machine : payload.machines()) {
                tileEntityReactor.entries.put(machine.pos(), machine);
            }
        }));
    }

    @Environment(EnvType.CLIENT)
    public static void registerClientPackets() {
        PayloadTypeRegistry.playS2C().register(GuiSpawnPacket.TYPE, GuiSpawnPacket.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(RhodesPacket.PACKET_TYPE, RhodesPacket.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(RivalRebelsPlayerList.PACKET_TYPE, RivalRebelsPlayerList.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(RivalRebelsRound.PACKET_TYPE, RivalRebelsRound.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(ReactorMachinesPacket.TYPE, ReactorMachinesPacket.STREAM_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(GuiSpawnPacket.TYPE, GuiSpawnPacket::onMessage);
        ClientPlayNetworking.registerGlobalReceiver(RhodesPacket.PACKET_TYPE, RhodesPacket::onMessage);
        ClientPlayNetworking.registerGlobalReceiver(RivalRebelsPlayerList.PACKET_TYPE, RivalRebelsPlayerList::onMessage);
        ClientPlayNetworking.registerGlobalReceiver(RivalRebelsRound.PACKET_TYPE, RivalRebelsRound::onMessage);
        ClientPlayNetworking.registerGlobalReceiver(ReactorMachinesPacket.TYPE, GuiReactor::onMachinesPacket);
    }
}
