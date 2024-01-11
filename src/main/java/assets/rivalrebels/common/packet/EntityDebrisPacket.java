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

import assets.rivalrebels.common.entity.EntityDebris;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class EntityDebrisPacket implements IMessage
{
    private IBlockState state;
	int id = 0;

	public EntityDebrisPacket() {
	}

	public EntityDebrisPacket(EntityDebris ed) {
		id = ed.getEntityId();
		state = ed.state;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		id = buf.readInt();
        state = Block.getBlockFromName(ByteBufUtils.readUTF8String(buf)).getStateFromMeta(buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(id);
        ByteBufUtils.writeUTF8String(buf, Block.REGISTRY.getNameForObject(state.getBlock()).toString());
		buf.writeInt(state.getBlock().getMetaFromState(state));
	}

	public static class Handler implements IMessageHandler<EntityDebrisPacket, IMessage>
	{
		@Override
		public IMessage onMessage(EntityDebrisPacket m, MessageContext ctx)
		{
            Minecraft.getMinecraft().addScheduledTask(() -> {
                WorldClient world = Minecraft.getMinecraft().world;
                Entity entity = world.getEntityByID(m.id);
                if (entity instanceof EntityDebris ed) {
                    ed.state = m.state;
                }
            });
            return null;
		}
	}
}
