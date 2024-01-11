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

import assets.rivalrebels.common.tileentity.TileEntityReactor;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ReactorGUIPacket implements IMessage {
    private BlockPos pos;
    byte type;

	public ReactorGUIPacket() {
	}

	public ReactorGUIPacket(BlockPos pos, byte b) {
        this.pos = pos;
        type = b;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
        PacketBuffer buffer = new PacketBuffer(buf);
        pos = buffer.readBlockPos();
		type = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
        PacketBuffer buffer = new PacketBuffer(buf);
        buffer.writeBlockPos(pos);
		buf.writeByte(type);
	}

	public static class Handler implements IMessageHandler<ReactorGUIPacket, IMessage>
	{
		@Override
		public IMessage onMessage(ReactorGUIPacket m, MessageContext ctx)
		{
            EntityPlayerMP player = ctx.getServerHandler().player;
            player.getServer().addScheduledTask(() -> {
                if (player.getDistanceSq(m.pos) < 100) {
                    TileEntity te = player.world.getTileEntity(m.pos);
                    if (te instanceof TileEntityReactor ter) {
                        if (m.type == 0) ter.toggleOn();
                        if (m.type == 1) ter.ejectCore();
                    }
                }
            });
			return null;
		}
	}
}
