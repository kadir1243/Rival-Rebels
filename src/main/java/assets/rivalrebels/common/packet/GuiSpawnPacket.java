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
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GuiSpawnPacket {
	public static GuiSpawnPacket fromBytes(PacketByteBuf buf) {
        return new GuiSpawnPacket();
	}

	public static void toBytes(GuiSpawnPacket packet, PacketByteBuf buf) {
    }

	public static void onMessage(GuiSpawnPacket m, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            if (RivalRebels.round.rrplayerlist.getForGameProfile(MinecraftClient.getInstance().player.getGameProfile()).isreset) {
                RivalRebels.proxy.guiClass();
            } else {
                RivalRebels.proxy.guiSpawn();
            }
        });
	}
}
