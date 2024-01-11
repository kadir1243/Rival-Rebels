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
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Locale;

public class ModListPacket implements IMessage
{
	public static EntityPlayerMP asker;
	String text;

	public ModListPacket()
	{

	}

	public ModListPacket(String t)
	{
		text = t;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
        text = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
        ByteBufUtils.writeUTF8String(buf, text);
	}

	public static class Handler implements IMessageHandler<ModListPacket, IMessage>
	{
		@Override
		public IMessage onMessage(ModListPacket m, MessageContext ctx)
		{
			String[] d = m.text.split(",");
			StringBuilder illegalmods = new StringBuilder("§6[§eRival Rebels§6]\n§4Please remove these mods to play on this server:\n§4");
			boolean isillegal = false;
			StringBuilder str = new StringBuilder("-t§e======[§6RRInspect§e]======\n§6");
			str.append(d[0]);
			str.append(" has:\n§6");
			for (int i = 1; i < d.length; i++)
			{
				str.append(d[i]);
				if (i+1 < d.length) str.append(",\n§6");
				for (int j = 0; j < RivalRebels.modblacklist.length && RivalRebels.enforceblacklist; j++)
				{
					if (d[i].toLowerCase(Locale.ROOT).contains(RivalRebels.modblacklist[j]))
					{
						isillegal = true;
						illegalmods.append(d[i]);
						illegalmods.append(",\n§4");
						break;
					}
				}
			}
			String s = str.toString();
            boolean finalIsillegal = isillegal;
            ctx.getServerHandler().player.getServer().addScheduledTask(() -> {
                if (finalIsillegal) ctx.getServerHandler().disconnect(new TextComponentString(illegalmods.toString()));
                if (asker != null) PacketDispatcher.packetsys.sendTo(new TextPacket(s), asker);
            });
			return null;
		}
	}
}
