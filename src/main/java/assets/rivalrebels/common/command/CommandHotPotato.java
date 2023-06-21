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

import assets.rivalrebels.common.entity.EntityHotPotato;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

public class CommandHotPotato extends CommandBase
{
    public static BlockPos pos = new BlockPos(-1, -1, -1);
	public static World world = null;
	public static boolean roundinprogress = false;
	@Override
	public String getCommandName()
	{
		return "rrhotpotato";
	}

	@Override
	public String getCommandUsage(ICommandSender par1ICommandSender)
	{
		return "/" + getCommandName();
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
	public void processCommand(ICommandSender sender, String[] array)
	{
		if (world == null)
		{
			sender.addChatMessage(new ChatComponentText("§cPlace a jump block and use pliers on it to set the hot potato drop point."));
			return;
		}
		if (array.length == 1)
		{
			String str = array[0];
			if ("stop".equals(str))
			{
				roundinprogress = false;
				sender.addChatMessage(new ChatComponentText("§cRound stopped."));
            }
			else
			{
				if (roundinprogress)
				{
					sender.addChatMessage(new ChatComponentText("§cRound already in progress! Do /rrhotpotato stop to end the current round."));
					return;
				}
				int n = Integer.parseInt(array[0]);
				sender.addChatMessage(new ChatComponentText("§cLet the Hot Potato games begin! " + n + " rounds."));
				EntityHotPotato tsar = new EntityHotPotato(world, pos.getX(), pos.getY(), pos.getZ(),n);
				world.spawnEntityInWorld(tsar);
				roundinprogress = true;
            }
            return;
        }
		sender.addChatMessage(new ChatComponentText("§cUsage: /rrhotpotato [number of rounds]"));
	}

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return Collections.singletonList("nuketime");
    }
}
