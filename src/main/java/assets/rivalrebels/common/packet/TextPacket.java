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

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TextPacket {
    private final String text;

    public TextPacket(String t) {
        text = t;
    }

    public static TextPacket fromBytes(PacketByteBuf buf) {
        return new TextPacket(buf.readString());
    }

    public static void toBytes(TextPacket packet, PacketByteBuf buf) {
        buf.writeString(packet.text);
    }

    public static void onMessage(TextPacket m, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            if (m.text.startsWith("-t")) {
                String[] str = m.text.substring(2).split("\n");
                for (String string : str)
                    MinecraftClient.getInstance().player.sendMessage(Text.of(string), false);
            } else {
                String[] s = m.text.split(" ");
                StringBuilder strb = new StringBuilder();
                for (String string : s) {
                    strb.append(I18n.translate(string));
                    strb.append(" ");
                }
                MinecraftClient.getInstance().player.sendMessage(Text.of(strb.toString()), false);
            }
        });
    }
}
