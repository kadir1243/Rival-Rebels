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
package assets.rivalrebels.common.command;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.packet.PacketDispatcher;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class CommandResetGame extends CommandBase
{
	@Override
	public String getName()
	{
		return "rrreset";
	}

	@Override
	public String getUsage(ICommandSender par1ICommandSender)
	{
		return "/" + getName() + " <player>";
	}

	/**
	 * Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel()
	{
		return 3;
	}

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		if (args.length == 1 && !args[0].isEmpty())
		{
			if (args[0].equals("all"))
			{
				RivalRebels.round.rrplayerlist.clearTeam();
				PacketDispatcher.packetsys.sendToAll(RivalRebels.round.rrplayerlist);
				player.sendMessage(new TextComponentString("§7All players have been reset."));
			}
			else if (RivalRebels.round.rrplayerlist.contains(server.getPlayerList().getPlayerByUsername(args[0]).getGameProfile()))
			{
				RivalRebels.round.rrplayerlist.getForGameProfile(server.getPlayerList().getPlayerByUsername(args[0]).getGameProfile()).clearTeam();
				PacketDispatcher.packetsys.sendToAll(RivalRebels.round.rrplayerlist);
				player.sendMessage(new TextComponentString("§7Player successfully reset."));
			}
			else
			{
				player.sendMessage(new TextComponentString("§7No player by that name."));
			}
		}
	}

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		return args.length >= 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();
	}
}
