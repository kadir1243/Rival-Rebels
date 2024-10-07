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
import io.github.kadir1243.rivalrebels.client.gui.GuiClass;
import io.github.kadir1243.rivalrebels.client.gui.GuiSpawn;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsPlayer;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record GuiSpawnPacket() implements CustomPacketPayload {
    public static final GuiSpawnPacket INSTANCE = new GuiSpawnPacket();
    public static final StreamCodec<ByteBuf, GuiSpawnPacket> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final Type<GuiSpawnPacket> TYPE = new Type<>(RRIdentifiers.create("gui_spawn"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void onMessage(GuiSpawnPacket packet, IPayloadContext context) {
        RivalRebelsPlayer player = RivalRebels.round.rrplayerlist.getForGameProfile(context.player().getGameProfile());
        if (player.isreset) {
            Minecraft.getInstance().setScreen(new GuiClass(player.rrclass));
        } else {
            Minecraft.getInstance().setScreen(new GuiSpawn(player.rrclass));
        }
	}
}
