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
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ResetPacket {
    public static ResetPacket fromBytes(PacketByteBuf buf) {
        return new ResetPacket();
    }

    public static void toBytes(ResetPacket packet, PacketByteBuf buf) {
    }

    public static void onMessage(ResetPacket m, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        ServerPlayerEntity player = context.getSender();
        context.enqueueWork(() -> {
            RivalRebelsPlayer p = RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile());
            if (!p.isreset && p.resets > 0) {
                p.isreset = true;
                p.resets--;
                player.getInventory().clear();
                RivalRebels.round.rrplayerlist.refreshForWorld(player.getWorld());
            }
        });
    }
}
