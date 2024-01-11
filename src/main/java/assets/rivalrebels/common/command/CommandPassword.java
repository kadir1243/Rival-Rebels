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
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import assets.rivalrebels.common.round.RivalRebelsRank;
import com.google.common.hash.Hashing;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class CommandPassword extends CommandBase
{
	@Override
	public String getName()
	{
		return "rr";
	}

	@Override
	public String getUsage(ICommandSender par1ICommandSender)
	{
		return "/" + getName() + " <code> [player]";
	}

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    String[] rhashes = new String[]{
			"23742137371982715120014159120241255637172",
			"1518918615824625494170109603025017352201241"
	};
	String[] ohashes = new String[]{
			"127254246888283236291831726971211129135192",
			"17314923891217222431372172462419922385201191"
	};
	String[] lhashes = new String[]{
			"612401057716617559272422511992314614422575",
			"99188249382921446717719913013762206120"
	};
	String[] shashes = new String[]{
			"222152281681152820718419502273648223209",
			"107170188164102246158207236028166217204217177"
	};

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		ITextComponent message = new TextComponentString("nope.");
		if (args.length == 0)
		{
			sender.sendMessage(message);
			return;
		}
		EntityPlayer person = getCommandSenderAsPlayer(sender);
		String code = args[0];
		String encrypted = encrypt(code);

		RivalRebelsRank rank = RivalRebelsRank.REGULAR;
		if (rhashes[0].equals(encrypted)||rhashes[1].equals(encrypted))
		{
			rank = RivalRebelsRank.REBEL;
			message = new TextComponentString("Welcome, rebel!");
		}
		else if (ohashes[0].equals(encrypted)||ohashes[1].equals(encrypted))
		{
			rank = RivalRebelsRank.OFFICER;
			message = new TextComponentString("Welcome, officer!");
		}
		else if (lhashes[0].equals(encrypted)||lhashes[1].equals(encrypted))
		{
			rank = RivalRebelsRank.LEADER;
			message = new TextComponentString("Welcome, leader!");
		}
		else if (shashes[0].equals(encrypted)||shashes[1].equals(encrypted))
		{
			rank = RivalRebelsRank.REP;
			message = new TextComponentString("Welcome, representative!");
		}
		RivalRebelsPlayer p = RivalRebels.round.rrplayerlist.getForGameProfile(person.getGameProfile());
		if (p.rrrank != rank || rank == RivalRebelsRank.REGULAR)
		{
			p.rrrank = rank;
			RivalRebelsSoundPlayer.playSound(person, 28, rank.snf);
			PacketDispatcher.packetsys.sendToAll(RivalRebels.round.rrplayerlist);
			sender.sendMessage(message);
		}
	}

	public String encrypt(String source)
	{
        return Hashing.md5().hashString(source, StandardCharsets.UTF_8).toString();
	}

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		return args.length >= 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();
	}
}
