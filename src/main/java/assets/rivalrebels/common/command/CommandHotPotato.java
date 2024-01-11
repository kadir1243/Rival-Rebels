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
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class CommandHotPotato extends CommandBase {
    public static BlockPos pos = BlockPos.ORIGIN;
	public static World world = null;
	public static boolean roundinprogress = false;
	@Override
	public String getName()
	{
		return "rrhotpotato";
	}

	@Override
	public String getUsage(ICommandSender par1ICommandSender)
	{
		return "/" + getName();
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
		if (world == null)
		{
			sender.sendMessage(new TextComponentString("§cPlace a jump block and use pliers on it to set the hot potato drop point."));
			return;
		}
		if (args.length == 1)
		{
			String str = args[0];
			if ("stop".equals(str))
			{
				roundinprogress = false;
				sender.sendMessage(new TextComponentString("§cRound stopped."));
            }
			else
			{
				if (roundinprogress)
				{
					sender.sendMessage(new TextComponentString("§cRound already in progress! Do /rrhotpotato stop to end the current round."));
					return;
				}
				int n = Integer.parseInt(args[0]);
				sender.sendMessage(new TextComponentString("§cLet the Hot Potato games begin! " + n + " rounds."));
				EntityHotPotato tsar = new EntityHotPotato(world,pos.getX(),pos.getY(), pos.getZ(),n);
				world.spawnEntity(tsar);
				roundinprogress = true;
            }
            return;
        }
		sender.sendMessage(new TextComponentString("§cUsage: /rrhotpotato [number of rounds]"));
	}

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		return Collections.singletonList("nuketime");
    }
}
