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
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record GuiSpawnPacket() implements CustomPacketPayload {
    public static final GuiSpawnPacket INSTANCE = new GuiSpawnPacket();
    public static final StreamCodec<ByteBuf, GuiSpawnPacket> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final Type<GuiSpawnPacket> TYPE = new Type<>(RRIdentifiers.create("gui_spawn"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void onMessage(GuiSpawnPacket packet, ClientPlayNetworking.Context context) {
        if (RivalRebels.round.rrplayerlist.getForGameProfile(context.player().getGameProfile()).isreset) {
            RivalRebels.proxy.guiClass();
        } else {
            RivalRebels.proxy.guiSpawn();
        }
	}
}
