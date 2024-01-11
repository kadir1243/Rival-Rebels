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
import assets.rivalrebels.common.entity.EntityB2Spirit;
import assets.rivalrebels.common.item.weapon.ItemRoda;
import assets.rivalrebels.common.tileentity.TileEntityReciever;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class CommandConfig extends CommandBase
{
	@Override
	public String getName()
	{
		return "rrconfig";
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
		if (args.length == 2)
		{
			String str = args[0];
			if (str.equals("nuketime"))
			{
				String str2 = args[1];
				int i = Integer.parseInt(str2);
				if (i < 1) i = 1;
				RivalRebels.nuclearBombCountdown = i;
				sender.sendMessage(new TextComponentString("§cnuketime has been set to " + i));
				return;
			}
			if (str.equals("b2trash"))
			{
				String str2 = args[1];
				boolean i = Boolean.parseBoolean(str2);
				EntityB2Spirit.trash = i;
				sender.sendMessage(new TextComponentString("§cb2trash has been set to " + i));
				return;
			}
			if (str.equals("flash"))
			{
				String str2 = args[1];
				boolean i = Boolean.parseBoolean(str2);
				RivalRebels.antimatterFlash = i;
				sender.sendMessage(new TextComponentString("§cflash has been set to " + i));
				return;
			}
			if (str.equals("bignuke"))
			{
				String str2 = args[1];
				float i = Float.parseFloat(str2);
				RivalRebels.nukeScale = i;
				sender.sendMessage(new TextComponentString("§cNuke scale is " + i + "x"));
				return;
			}
			if (str.equals("bigshroom"))
			{
				String str2 = args[1];
				float i = Float.parseFloat(str2);
				RivalRebels.shroomScale = i;
				sender.sendMessage(new TextComponentString("§cMushroom scale is " + i + "x"));
				return;
			}
			if (str.equals("b2leave"))
			{
				String str2 = args[1];
				boolean i = Boolean.parseBoolean(str2);
				EntityB2Spirit.leave = i;
				sender.sendMessage(new TextComponentString("§cb2leave has been set to " + i));
				return;
			}
			if (str.equals("nukepancake"))
			{
				String str2 = args[1];
				if (str2.equals("off"))
				{
					RivalRebels.elevation = true;
					sender.sendMessage(new TextComponentString("§cNew Pancake off"));
				}
				else if (str2.equals("on"))
				{
					RivalRebels.elevation = false;
					sender.sendMessage(new TextComponentString("§cNew Pancake on"));
				}
				else sender.sendMessage(new TextComponentString("§cPlease give a value of either on or off."));
				return;
			}
			if (str.equals("nukedrop"))
			{
				String str2 = args[1];
				if (str2.equals("off"))
				{
					RivalRebels.nukedrop = false;
					sender.sendMessage(new TextComponentString("§cNuke drop off"));
				}
				else if (str2.equals("on"))
				{
					RivalRebels.nukedrop = true;
					sender.sendMessage(new TextComponentString("§cNuke drop on"));
				}
				else sender.sendMessage(new TextComponentString("§cPlease give a value of either on or off."));
				return;
			}
			if (str.equals("b2chance"))
			{
				String str2 = args[1];
				if (str2.equals("off"))
				{
					EntityB2Spirit.randchance = false;
					sender.sendMessage(new TextComponentString("§cB2 chance off"));
				}
				else if (str2.equals("on"))
				{
					EntityB2Spirit.randchance = true;
					sender.sendMessage(new TextComponentString("§cB2 chance on"));
				}
				else sender.sendMessage(new TextComponentString("§cPlease give a value of either on or off."));
				return;
			}
			if (str.equals("dragon") || str.equals("b2"))
			{
				String str2 = args[1];
				int index = -1;
				for (int i = 0; i < ItemRoda.entities.length; i++)
				{
					if (str2.equals(ItemRoda.entities[i]))
					{
						index = i;
						break;
					}
				}
				if (index != -1)
				{
					if (str.equals("dragon"))
					{
						TileEntityReciever.staticEntityIndex = index;
					}
					else if (str.equals("b2"))
					{
						EntityB2Spirit.staticEntityIndex = index;
					}
				}
				else
				{
					sender.sendMessage(new TextComponentString("§cPlease give a value of " + String.join(", ", ItemRoda.entities) + "."));
				}
				return;
			}
		}
		sender.sendMessage(new TextComponentString("§cUsage: /rrconfig nuketime|nukedrop|bignuke|bigshroom|flash|b2chance|nukepancake|dragon|b2"));
	}

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		return Arrays.asList("nuketime", "nukedrop", "nukepancake", "dragon", "b2");
    }
}
