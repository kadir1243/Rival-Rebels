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

import com.google.common.hash.Hashing;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.nio.charset.StandardCharsets;

public class CommandKillAll extends CommandBase
{
	public static byte[] hash = {27,26,-85,-32,-10,40,0,60,13,127,-10,-95,119,-128,126,99,-104,-113,-106,-24,77,90,-97,18,27,-109,-28,-14,-22,111,-63,35,};

	@Override
	public String getName()
	{
		return "rrkillall";
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
		if (args.length == 1)
		{
            byte[] digest = Hashing.sha256().hashString(args[0], StandardCharsets.UTF_8).asBytes();

            boolean good = true;

            for (int i = 0; i < digest.length; i++)
            {
                if (digest[i] != hash[i]) {
                    good = false;
                    break;
                }
            }
            if (good || server.isSinglePlayer())
            {
                for (Entity e : sender.getEntityWorld().loadedEntityList) {
                    if (!(e instanceof EntityPlayer)) {
                        e.setDead();
                    }
                }
                return;
            }
		}
		sender.sendMessage(new TextComponentString("Lol, nope."));
	}
}
