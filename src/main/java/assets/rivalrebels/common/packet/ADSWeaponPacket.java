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

import assets.rivalrebels.common.tileentity.TileEntityReciever;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ADSWeaponPacket implements IMessage
{
    private BlockPos pos;
	int wep;

	public ADSWeaponPacket()
	{

	}

	public ADSWeaponPacket(BlockPos pos, int w)
	{
		this.pos = pos;
		wep = w;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
        PacketBuffer buffer = new PacketBuffer(buf);
        pos = buffer.readBlockPos();
		wep = buffer.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
        PacketBuffer buffer = new PacketBuffer(buf);
		buffer.writeBlockPos(pos);
		buffer.writeInt(wep);
	}

	public static class Handler implements IMessageHandler<ADSWeaponPacket, IMessage>
	{
		@Override
		public IMessage onMessage(ADSWeaponPacket m, MessageContext ctx)
		{
			TileEntity te = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(m.pos);
			if (te instanceof TileEntityReciever)
			{
                TileEntityReciever ter = (TileEntityReciever) te;
                if (ter.hasWepReqs())
				{
					ter.setWep(m.wep);
				}
			}
			return null;
		}
	}
}
