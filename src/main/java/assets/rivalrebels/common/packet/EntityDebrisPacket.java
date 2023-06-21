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
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class EntityDebrisPacket implements IMessage {
	private int id = 0;
    private IBlockState blockState;

    public EntityDebrisPacket() {
    }

	public EntityDebrisPacket(EntityDebris ed) {
		id = ed.getEntityId();
        blockState = ed.blockState;
    }

	@Override
	public void fromBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
		id = buffer.readInt();
        this.blockState = Block.getStateById(buffer.readVarIntFromBuffer());
    }

	@Override
	public void toBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
		buffer.writeInt(id);
        buffer.writeVarIntToBuffer(Block.getStateId(blockState));
	}

	public static class Handler implements IMessageHandler<EntityDebrisPacket, IMessage> {
		@Override
		public IMessage onMessage(EntityDebrisPacket m, MessageContext ctx) {
            for (Entity e : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                if (e.getEntityId() == m.id && e instanceof EntityDebris ed) {
                    ed.blockState = m.blockState;
                    break;
                }
            }
			return null;
		}
	}
}
