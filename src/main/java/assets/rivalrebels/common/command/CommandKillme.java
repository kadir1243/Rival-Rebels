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

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;

public class CommandKillme extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "killme";
	}

	@Override
	public String getCommandUsage(ICommandSender par1ICommandSender)
	{
		return "/" + getCommandName();
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender par1)
	{
		return true;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] array) throws PlayerNotFoundException {
		EntityPlayer person = getCommandSenderAsPlayer(sender);
		person.attackEntityFrom(RivalRebelsDamageSource.cyanide, 10000);
		sender.addChatMessage(new ChatComponentText("Do you think the cyanide tasted good?"));
	}

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return args.length >= 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}
