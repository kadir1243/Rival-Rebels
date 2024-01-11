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

import assets.rivalrebels.common.entity.EntityGore;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class EntityGorePacket implements IMessage
{
	byte	mob			= 0;
	byte	type		= 0;
	int		id			= 0;
	boolean	green		= false;
	String	username	= "Steve";
	float	size		= -1;

	public EntityGorePacket()
	{
	}

	public EntityGorePacket(EntityGore eg)
	{
		mob = (byte) eg.mob;
		type = (byte) eg.type;
		id = eg.getEntityId();
		green = eg.greenblood;
		username = eg.username;
		size = (float) eg.size;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		id = buf.readInt();
		mob = buf.readByte();
		type = buf.readByte();
		green = buf.readBoolean();
		if (mob == 0)
		{
            username = ByteBufUtils.readUTF8String(buf);
		}
		if (mob == 11) size = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(id);
		buf.writeByte(mob);
		buf.writeByte(type);
		buf.writeBoolean(green);
		if (mob == 0)
		{
            ByteBufUtils.writeUTF8String(buf, username);
		}
		if (mob == 11) buf.writeFloat(size);
	}

	public static class Handler implements IMessageHandler<EntityGorePacket, IMessage>
	{
		@Override
		public IMessage onMessage(EntityGorePacket m, MessageContext ctx)
		{
            Minecraft.getMinecraft().addScheduledTask(() -> {
                Entity entity = Minecraft.getMinecraft().world.getEntityByID(m.id);
                if (entity instanceof EntityGore eg) {
                    eg.mob = m.mob;
                    eg.type = m.type;
                    eg.greenblood = m.green;
                    if (m.mob == 0) eg.username = m.username;
                    if (m.mob == 11) eg.size = m.size;
                }
            });

			return null;
		}
	}
}
