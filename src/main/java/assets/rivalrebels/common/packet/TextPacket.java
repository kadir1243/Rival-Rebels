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

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TextPacket implements IMessage
{
	private String text;

	public TextPacket() {
	}

	public TextPacket(String t)
	{
		text = t;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
        text = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, text);
	}

	public static class Handler implements IMessageHandler<TextPacket, IMessage>
	{
		@Override
		public IMessage onMessage(TextPacket m, MessageContext ctx)
		{
            Minecraft.getMinecraft().addScheduledTask(() -> {
                if (m.text.startsWith("-t")) {
                    String[] str = m.text.substring(2).split("\n");
                    for (String string : str)
                        Minecraft.getMinecraft().player.sendMessage(new TextComponentString(string));
                } else {
                    String[] s = m.text.split(" ");
                    StringBuilder strb = new StringBuilder();
                    for (String string : s) {
                        strb.append(I18n.format(string));
                        strb.append(" ");
                    }
                    Minecraft.getMinecraft().player.sendMessage(new TextComponentString(strb.toString()));
                }
            });
			return null;
		}
	}
}
