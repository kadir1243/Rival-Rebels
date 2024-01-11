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
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ADSUpdatePacket implements IMessage {
    private BlockPos pos;
	int range;
	boolean mob;
	boolean chip;
	boolean player;
	boolean haswep;
	String user;

	public ADSUpdatePacket() {
	}

	public ADSUpdatePacket(BlockPos pos, int r, boolean m, boolean c, boolean p, boolean h, String u) {
		this.pos = pos;
		range = r;
		mob = m;
		chip = c;
		player = p;
		haswep = h;
		user = u;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        pos = buffer.readBlockPos();

		range=buf.readInt();
		mob=buf.readBoolean();
		chip=buf.readBoolean();
		player=buf.readBoolean();
		haswep=buf.readBoolean();
		user = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        buffer.writeBlockPos(pos);

        buf.writeInt(range);
		buf.writeBoolean(mob);
		buf.writeBoolean(chip);
		buf.writeBoolean(player);
		buf.writeBoolean(haswep);
        ByteBufUtils.writeUTF8String(buf, user);
	}

	public static class Handler implements IMessageHandler<ADSUpdatePacket, IMessage> {
		@Override
		public IMessage onMessage(ADSUpdatePacket m, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntity te = Minecraft.getMinecraft().world.getTileEntity(m.pos);

                if (te instanceof TileEntityReciever ter) {
                    ter.yawLimit = m.range;
                    ter.kMobs = m.mob;
                    ter.kTeam = m.chip;
                    ter.kPlayers = m.player;
                    ter.hasWeapon = m.haswep;
                    ter.username = m.user;
                }});
            return null;
		}
	}
}
