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

import io.github.kadir1243.rivalrebels.client.gui.GuiReactor;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsPlayerList;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsRound;
import io.github.kadir1243.rivalrebels.common.tileentity.RRTileEntities;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityLaptop;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class PacketDispatcher {
    public static void init(IEventBus bus) {
        bus.addListener(PacketDispatcher::registerPayloads);
    }

    private static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(ItemUpdate.TYPE, ItemUpdate.STREAM_CODEC, ItemUpdate::onMessage);
        registrar.playToServer(JoinTeamPacket.PACKET_TYPE, JoinTeamPacket.STREAM_CODEC, JoinTeamPacket::onMessage);
        registrar.playToServer(ResetPacket.PACKET_TYPE, ResetPacket.STREAM_CODEC, ResetPacket::onMessage);
        registrar.playToServer(VotePacket.PACKET_TYPE, VotePacket.STREAM_CODEC, VotePacket::onMessage);
        registrar.playToServer(LaptopEngagePacket.PACKET_TYPE, LaptopEngagePacket.STREAM_CODEC, LaptopEngagePacket::onMessage);
        registrar.playToServer(RhodesJumpPacket.PACKET_TYPE, RhodesJumpPacket.STREAM_CODEC, RhodesJumpPacket::onMessage);
        registrar.playToServer(LaptopPressPacket.TYPE, LaptopPressPacket.STREAM_CODEC, (payload, context) -> context.player().level().getBlockEntity(payload.pos(), RRTileEntities.LAPTOP.get()).ifPresent(TileEntityLaptop::onGoButtonPressed));
        registrar.playToServer(ReactorStatePacket.TYPE, ReactorStatePacket.STREAM_CODEC, (payload, context) -> context.player().level().getBlockEntity(payload.pos(), RRTileEntities.REACTOR.get()).ifPresent(tileEntityReactor -> {
            if (payload.packetType() == ReactorStatePacket.Type.EJECT_CORE) {
                tileEntityReactor.ejectCore();
            } else if (payload.packetType() == ReactorStatePacket.Type.TOGGLE_ON) {
                tileEntityReactor.toggleOn();
            }
        }));

        registrar.playToClient(GuiSpawnPacket.TYPE, GuiSpawnPacket.STREAM_CODEC, GuiSpawnPacket::onMessage);
        registrar.playToClient(RhodesPacket.PACKET_TYPE, RhodesPacket.STREAM_CODEC, RhodesPacket::onMessage);
        registrar.playToClient(RivalRebelsPlayerList.PACKET_TYPE, RivalRebelsPlayerList.STREAM_CODEC, RivalRebelsPlayerList::onMessage);
        registrar.playToClient(RivalRebelsRound.PACKET_TYPE, RivalRebelsRound.STREAM_CODEC, RivalRebelsRound::onMessage);
        registrar.playBidirectional(ReactorMachinesPacket.TYPE, ReactorMachinesPacket.STREAM_CODEC, new DirectionalPayloadHandler<>(GuiReactor::onMachinesPacket, (payload, context) -> context.player().level().getBlockEntity(payload.reactorPos(), RRTileEntities.REACTOR.get()).ifPresent(tileEntityReactor -> {
            tileEntityReactor.entries.clear();
            for (ReactorMachinesPacket.MachineEntry machine : payload.machines()) {
                tileEntityReactor.entries.put(machine.pos(), machine);
            }
        })));
    }
}
