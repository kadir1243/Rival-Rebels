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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

public class CommandKillAll extends CommandBase
{
	public static byte[] hash = {27,26,-85,-32,-10,40,0,60,13,127,-10,-95,119,-128,126,99,-104,-113,-106,-24,77,90,-97,18,27,-109,-28,-14,-22,111,-63,35,};

	@Override
	public String getCommandName()
	{
		return "rrkillall";
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
		if (array.length == 1)
		{
			MessageDigest md;
			try
			{
				md = MessageDigest.getInstance("SHA-256");
				md.update(array[0].getBytes(StandardCharsets.UTF_8));
				byte[] digest = md.digest();

				boolean good = true;

				for (int i = 0; i < digest.length; i++)
				{
                    if (digest[i] != hash[i]) {
                        good = false;
                        break;
                    }
				}
				if (good || MinecraftServer.getServer().isSinglePlayer())
				{
					List<Entity> l = MinecraftServer.getServer().worldServers[0].loadedEntityList;
					for (int i = 0; i < l.size(); i++)
					{
						Entity e = l.get(i);
                        if (!(e instanceof EntityPlayer)) {
                            e.setDead();
                        }
                    }
					return;
				}
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
		sender.addChatMessage(new ChatComponentText("Lol, nope."));
	}
}
