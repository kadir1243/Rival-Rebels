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
import assets.rivalrebels.common.round.RivalRebelsClass;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import assets.rivalrebels.common.round.RivalRebelsTeam;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class JoinTeamPacket implements IMessage
{
	RivalRebelsClass rrclass;
	RivalRebelsTeam rrteam;

	public JoinTeamPacket()
	{

	}

	public JoinTeamPacket(RivalRebelsTeam rrt, RivalRebelsClass rrc)
	{
		rrclass = rrc;
		rrteam = rrt;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		rrclass = RivalRebelsClass.getForID(buf.readByte());
		rrteam = RivalRebelsTeam.getForID(buf.readByte());
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte(rrclass.id);
		buf.writeByte(rrteam.id);
	}

	public static class Handler implements IMessageHandler<JoinTeamPacket, IMessage>
	{
		@Override
		public IMessage onMessage(JoinTeamPacket m, MessageContext ctx)
		{
            EntityPlayerMP player = ctx.getServerHandler().player;
            RivalRebelsPlayer p = RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile());

			if (p.isreset)
			{
				p.isreset = false;
				p.rrclass=m.rrclass;
				p.rrteam=m.rrteam;
				Scoreboard scrb = RivalRebels.round.world.getScoreboard();
				scrb.removePlayerFromTeams(p.getUsername());
				scrb.addPlayerToTeam(p.getUsername(), p.rrteam.toString());
				ItemStack[] inventory = m.rrclass.inventory;

                for (ItemStack stack : inventory) {
                    player.inventory.addItemStackToInventory(stack.copy());
                }

				if (m.rrteam == RivalRebelsTeam.OMEGA)
				{
                    switch (m.rrclass) {
                        case REBEL -> {
                            player.setItemStackToSlot(EntityEquipmentSlot.FEET, RivalRebels.orebelboots.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.LEGS, RivalRebels.orebelpants.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.CHEST, RivalRebels.orebelchest.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.HEAD, RivalRebels.orebelhelmet.getDefaultInstance());
                        }
                        case NUKER -> {
                            player.setItemStackToSlot(EntityEquipmentSlot.FEET, RivalRebels.onukerboots.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.LEGS, RivalRebels.onukerpants.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.CHEST, RivalRebels.onukerchest.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.HEAD, RivalRebels.onukerhelmet.getDefaultInstance());
                        }
                        case INTEL -> {
                            player.setItemStackToSlot(EntityEquipmentSlot.FEET, RivalRebels.ointelboots.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.LEGS, RivalRebels.ointelpants.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.CHEST, RivalRebels.ointelchest.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.HEAD, RivalRebels.ointelhelmet.getDefaultInstance());
                        }
                        case HACKER -> {
                            player.setItemStackToSlot(EntityEquipmentSlot.FEET, RivalRebels.ohackerboots.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.LEGS, RivalRebels.ohackerpants.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.CHEST,RivalRebels.ohackerchest.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.HEAD, RivalRebels.ohackerhelmet.getDefaultInstance());
                        }
                        case NONE -> {
                        }
                    }
				}
				else if (m.rrteam == RivalRebelsTeam.SIGMA)
				{
                    switch (m.rrclass) {
                        case REBEL -> {
                            player.setItemStackToSlot(EntityEquipmentSlot.FEET, RivalRebels.srebelboots.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.LEGS, RivalRebels.srebelpants.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.CHEST,RivalRebels.srebelchest.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.HEAD, RivalRebels.srebelhelmet.getDefaultInstance());
                        }
                        case NUKER -> {
                            player.setItemStackToSlot(EntityEquipmentSlot.FEET, RivalRebels.snukerboots.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.LEGS, RivalRebels.snukerpants.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.CHEST,RivalRebels.snukerchest.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.HEAD, RivalRebels.snukerhelmet.getDefaultInstance());
                        }
                        case INTEL -> {
                            player.setItemStackToSlot(EntityEquipmentSlot.FEET, RivalRebels.sintelboots.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.LEGS, RivalRebels.sintelpants.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.CHEST,RivalRebels.sintelchest.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.HEAD, RivalRebels.sintelhelmet.getDefaultInstance());
                        }
                        case HACKER -> {
                            player.setItemStackToSlot(EntityEquipmentSlot.FEET, RivalRebels.shackerboots.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.LEGS, RivalRebels.shackerpants.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.CHEST,RivalRebels.shackerchest.getDefaultInstance());
                            player.setItemStackToSlot(EntityEquipmentSlot.HEAD, RivalRebels.shackerhelmet.getDefaultInstance());
                        }
                        case NONE -> {
                        }
                    }
				}
				PacketDispatcher.packetsys.sendToAll(RivalRebels.round.rrplayerlist);
			}
			if (m.rrteam == RivalRebelsTeam.OMEGA)
			{
				double sx = RivalRebels.round.omegaObjPos.getX() + (RivalRebels.round.world.rand.nextInt(2)-0.5)*30+0.5f;
				double sy = RivalRebels.round.omegaObjPos.getY() + 1;
				double sz = RivalRebels.round.omegaObjPos.getZ() + (RivalRebels.round.world.rand.nextInt(2)-0.5)*30+0.5f;
				player.connection.setPlayerLocation(sx, sy, sz,0,0);
			}
			else if (m.rrteam == RivalRebelsTeam.SIGMA)
			{
				double sx = RivalRebels.round.sigmaObjPos.getX() + (RivalRebels.round.world.rand.nextInt(2)-0.5)*30+0.5f;
				double sy = RivalRebels.round.sigmaObjPos.getY() + 1;
				double sz = RivalRebels.round.sigmaObjPos.getZ() + (RivalRebels.round.world.rand.nextInt(2)-0.5)*30+0.5f;
				player.connection.setPlayerLocation(sx, sy, sz,0,0);
			}
			return null;
		}
	}
}
