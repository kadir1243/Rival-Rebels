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
import assets.rivalrebels.common.entity.EntityB2Spirit;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import assets.rivalrebels.common.tileentity.TileEntityLaptop;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class LaptopEngagePacket implements IMessage
{
    private BlockPos tpos;
    private BlockPos lpos;
	boolean carpet = false;

	public LaptopEngagePacket()
	{

	}

	public LaptopEngagePacket(BlockPos tpos, BlockPos lpos, boolean carpet)
	{
        this.tpos = tpos;
        this.lpos = lpos;
		this.carpet = carpet;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
        PacketBuffer buffer = new PacketBuffer(buf);
        tpos = buffer.readBlockPos();
        lpos = buffer.readBlockPos();
		carpet = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
        PacketBuffer buffer = new PacketBuffer(buf);
        buffer.writeBlockPos(tpos);
		buffer.writeBlockPos(lpos);
		buf.writeBoolean(carpet);
	}

	public static class Handler implements IMessageHandler<LaptopEngagePacket, IMessage>
	{
		@Override
		public IMessage onMessage(LaptopEngagePacket m, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			World world = player.world;
            player.getServer().addScheduledTask(() ->{
                if (player.getDistanceSq(m.lpos) < 100) {
                    TileEntity te = world.getTileEntity(m.lpos);
                    if (te instanceof TileEntityLaptop tel) {
                        if (!m.carpet && tel.b2spirit > 0) {
                            int XX = 11;
                            int ZZ = 10;
                            if (tel.rrteam == RivalRebelsTeam.OMEGA) {
                                XX = (m.tpos.getX() - RivalRebels.round.omegaObjPos.getX());
                                ZZ = (m.tpos.getZ() - RivalRebels.round.omegaObjPos.getZ());
                            }
                            if (tel.rrteam == RivalRebelsTeam.SIGMA) {
                                XX = (m.tpos.getX() - RivalRebels.round.sigmaObjPos.getX());
                                ZZ = (m.tpos.getZ() - RivalRebels.round.sigmaObjPos.getZ());
                            }
                            int xx = m.tpos.getX() - m.lpos.getX();
                            int zz = m.tpos.getZ() - m.lpos.getZ();
                            if (xx * xx + zz * zz > 625 && XX * XX + ZZ * ZZ > 200) {
                                tel.b2spirit--;
                                world.spawnEntity(new EntityB2Spirit(world, m.tpos.getX(), m.tpos.getY(), m.tpos.getZ(), player.posX, player.posY, player.posZ, false, player.isSneaking()));
                            }
                        }
                        if (m.carpet && tel.b2carpet > 0) {
                            int XX = 11;
                            int ZZ = 10;
                            int xx = m.tpos.getX() - m.lpos.getX();
                            int zz = m.tpos.getZ() - m.lpos.getZ();
                            if (xx * xx + zz * zz > 625 && XX * XX + ZZ * ZZ > 200) {
                                tel.b2carpet--;
                                world.spawnEntity(new EntityB2Spirit(world, m.tpos.getX(), m.tpos.getY(), m.tpos.getZ(), player.posX, player.posY, player.posZ, true, false));
                            }
                        }
                    }
                }
            });
			return null;
		}
	}
}
