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
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ADSClosePacket implements IMessage
{
    private BlockPos pos;
	boolean mobs;
	boolean chip;
	boolean player;
	int range;

	public ADSClosePacket() {
	}

	public ADSClosePacket(BlockPos pos, boolean m, boolean c, boolean p, int r) {
        this.pos = pos;
		mobs = m;
		chip = c;
		player = p;
		range = r;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
        PacketBuffer buffer = new PacketBuffer(buf);
        pos = buffer.readBlockPos();
		mobs = buf.readBoolean();
		chip = buf.readBoolean();
		player = buf.readBoolean();
		range = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
        PacketBuffer buffer = new PacketBuffer(buf);
        buffer.writeBlockPos(pos);

		buf.writeBoolean(mobs);
		buf.writeBoolean(chip);
		buf.writeBoolean(player);
		buf.writeInt(range);
	}

	public static class Handler implements IMessageHandler<ADSClosePacket, IMessage>
	{
		@Override
		public IMessage onMessage(ADSClosePacket m, MessageContext ctx) {
            ctx.getServerHandler().player.getServer().addScheduledTask(() -> {
                TileEntity te = ctx.getServerHandler().player.world.getTileEntity(m.pos);
                if (te instanceof TileEntityReciever ter && ctx.getServerHandler().player.getDistanceSq(m.pos) < 100) {
                    ter.kMobs = m.mobs;
                    ter.kTeam = m.chip;
                    ter.kPlayers = m.player;
                    ter.yawLimit = m.range;
                }
            });
			return null;
		}
	}
}
