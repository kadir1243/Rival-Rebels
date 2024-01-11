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

import assets.rivalrebels.common.tileentity.TileEntityLaptop;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class LaptopRefreshPacket implements IMessage {
    private BlockPos pos;
	int tasks;
	int carpet;

	public LaptopRefreshPacket() {
	}

	public LaptopRefreshPacket(BlockPos pos, int T, int C)
	{
		this.pos = pos;
		tasks = T;
		carpet = C;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
        PacketBuffer buffer = new PacketBuffer(buf);
        pos = buffer.readBlockPos();
		tasks = buf.readInt();
		carpet = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
        PacketBuffer buffer = new PacketBuffer(buf);
        buffer.writeBlockPos(pos);
		buf.writeInt(tasks);
		buf.writeInt(carpet);
	}

	public static class Handler implements IMessageHandler<LaptopRefreshPacket, IMessage>
	{
		@Override
		public IMessage onMessage(LaptopRefreshPacket m, MessageContext ctx)
		{
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntity te = Minecraft.getMinecraft().world.getTileEntity(m.pos);
                if (te instanceof TileEntityLaptop laptop) {
                    laptop.b2spirit = m.tasks;
                    laptop.b2carpet = m.carpet;
                }
            });
			return null;
		}
	}
}
