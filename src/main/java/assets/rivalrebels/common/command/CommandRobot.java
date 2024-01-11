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
import assets.rivalrebels.common.entity.EntityRhodes;
import com.google.common.hash.Hashing;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CommandRobot extends CommandBase
{
	@Override
	public String getName()
	{
		return "rrrobot";
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
			if (str.equals("spawn") && !sender.getEntityWorld().isRemote)
			{
				String str2 = args[1];
				try
				{
					float scale = Float.parseFloat(str2);
					Vec3d cc = sender.getPositionVector();
					EntityRhodes er = new EntityRhodes(sender.getEntityWorld(), cc.x, cc.y, cc.z, scale / 30.0f);
					sender.getEntityWorld().spawnEntity(er);
				}
				catch(Exception e)
				{
					sender.sendMessage(new TextComponentString("§cUsage: /rrrobot spawn <blocks high>"));
				}
				return;
			}
			if (str.equals("speedscale"))
			{
				String str2 = args[1];
				if (str2.equals("on"))
				{
					RivalRebels.rhodesScaleSpeed = true;
					sender.sendMessage(new TextComponentString("§cRhodes Speed Scaling Enabled"));
				}
				else if (str2.equals("off"))
				{
					RivalRebels.rhodesScaleSpeed = false;
					sender.sendMessage(new TextComponentString("§cRhodes Speed Scaling Disabled"));
				}
				else
				{
					float scale;
					try
					{
						scale = Float.parseFloat(str2);
						RivalRebels.rhodesSpeedScale = scale;
					}
					catch(Exception e)
					{
						sender.sendMessage(new TextComponentString("§cUsage: /rrrobot speedscale [on|off|number]"));
					}
				}
				return;
			}
			if (str.equals("rekt"))
			{
				String str2 = args[1];
				if (str2.equals("on"))
				{
					RivalRebels.rhodesBlockBreak = 1.0f;
					sender.sendMessage(new TextComponentString("§cRhodes Rekt Enabled"));
				}
				else if (str2.equals("off"))
				{
					RivalRebels.rhodesBlockBreak = 0.0f;
					sender.sendMessage(new TextComponentString("§cRhodes Rekt Disabled"));
				}
				else
				{
					sender.sendMessage(new TextComponentString("§cUsage: /rrrobot rekt [on|off]"));
				}
				return;
			}
			if (str.equals("exit"))
			{
				String str2 = args[1];
				if (str2.equals("on"))
				{
					RivalRebels.rhodesExit = true;
					sender.sendMessage(new TextComponentString("§cRhodes Exitting Enabled"));
				}
				else if (str2.equals("off"))
				{
					RivalRebels.rhodesExit = false;
					sender.sendMessage(new TextComponentString("§cRhodes Exitting Disabled"));
				}
				else
				{
					sender.sendMessage(new TextComponentString("§cUsage: /rrrobot exit [on|off]"));
				}
				return;
			}
			if (str.equals("stop"))
			{
				String str2 = args[1];

                byte[] digest = Hashing.sha256().hashString(str2, StandardCharsets.UTF_8).asBytes();

                boolean good = true;

                for (int i = 0; i < digest.length; i++)
                {
                    if (digest[i] != CommandKillAll.hash[i]) {
                        good = false;
                        break;
                    }
                }
                if (good)
                {
                    RivalRebels.rhodesHold = !RivalRebels.rhodesHold;
                    sender.sendMessage(new TextComponentString("§cRhodes Stop " + RivalRebels.rhodesHold));
                    return;
                }

				sender.sendMessage(new TextComponentString("§cUsage: /rrrobot stop [password]"));
				return;
			}
			if (str.equals("ai"))
			{
				String str2 = args[1];
				if (str2.equals("on"))
				{
					RivalRebels.rhodesAI = true;
					sender.sendMessage(new TextComponentString("§cRhodes AI Enabled"));
				}
				else if (str2.equals("off"))
				{
					RivalRebels.rhodesAI = false;
					sender.sendMessage(new TextComponentString("§cRhodes AI Disabled"));
				}
				else
				{
					sender.sendMessage(new TextComponentString("§cUsage: /rrrobot ai [on|off]"));
				}
				return;
			}
			if (str.equals("tff"))
			{
				String str2 = args[1];
				if (str2.equals("on"))
				{
					RivalRebels.rhodesCC = true;
					sender.sendMessage(new TextComponentString("§cRhodes Team Friendly Fire Enabled"));
				}
				else if (str2.equals("off"))
				{
					RivalRebels.rhodesCC = false;
					sender.sendMessage(new TextComponentString("§cRhodes Team Friendly Fire Disabled"));
				}
				else
				{
					sender.sendMessage(new TextComponentString("§cUsage: /rrrobot tff [on|off]"));
				}
				return;
			}
			if (str.equals("ff"))
			{
				String str2 = args[1];
				if (str2.equals("on"))
				{
					RivalRebels.rhodesFF = true;
					sender.sendMessage(new TextComponentString("§cRhodes Friendly Fire Enabled"));
				}
				else if (str2.equals("off"))
				{
					RivalRebels.rhodesFF = false;
					sender.sendMessage(new TextComponentString("§cRhodes Friendly Fire Disabled"));
				}
				else
				{
					sender.sendMessage(new TextComponentString("§cUsage: /rrrobot ff [on|off]"));
				}
				return;
			}
			if (str.equals("logo"))
			{
				String str2 = args[1];
				EntityRhodes.texfolder = -1;
				int i;
				if (str2.startsWith("blocks/")) i = 0;
				else if (str2.startsWith("entity/")) i = 1;
				else if (str2.startsWith("items/")) i = 2;
				else i = 3;
				if (!str2.contains("/") && str2.length() < 11)
				{
					EntityRhodes.texfolder = i;
					EntityRhodes.texloc = str2;
					sender.sendMessage(new TextComponentString("§cNext Rhodes Flag is " + str2));
                }
				else
				{
					String str3 = str2.substring(str2.indexOf("/")+1);
					if (!str3.contains("/") && str3.length() < 11)
					{
						EntityRhodes.texfolder = i;
						EntityRhodes.texloc = str3;
						sender.sendMessage(new TextComponentString("§cNext Rhodes Flag is " + str2));
                    }
					else
					{
						sender.sendMessage(new TextComponentString("§cUsage: /rrrobot logo [flags|blocks|items|entity]/{texturename}"));
						sender.sendMessage(new TextComponentString("§cOpen up the jar and see for yourself which textures are available!"));
                    }
                }
                return;
            }
			if (str.equals("model"))
			{
				String str2 = args[1];
				if (str2.equals("none"))
				{
					EntityRhodes.forcecolor = -1;
					sender.sendMessage(new TextComponentString("§cNext Rhodes: " + EntityRhodes.names[EntityRhodes.lastct]));
					return;
				}
				else
				{
					int which;
					try
					{
						which = Integer.parseInt(str2)-1;
					}
					catch (Exception e)
					{
						which = -1;
					}
					if (which == -1)
					{
						int distance = 10000;
						for (int i = 0; i < names.length; i++)
						{
							int d = distance(str2, names[i]);
							if (d < distance)
							{
								distance = d;
								which = i;
							}
						}
					}
					if (which > -1 && which < names.length)
					{
						EntityRhodes.forcecolor = which;
						sender.sendMessage(new TextComponentString("§cNext Rhodes: " + EntityRhodes.names[which]));
						return;
					}
				}
			}
		}
		sender.sendMessage(new TextComponentString("§cUsage: /rrrobot [model|logo|ai|ff|tff|exit|stop|rekt|speedscale|spawn]"));
	}
	/**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List<String> addTabCompletionOptions(ICommandSender p, String[] s)
    {
    	List<String> l = new ArrayList<>();
    	if (s.length > 0 && s[0] != null)
    	{
            switch (s[0]) {
                case "model" -> l.addAll(Arrays.asList(names));
                case "logo" -> {
                    l.add("flags/");
                    l.add("items/");
                    l.add("blocks/");
                    l.add("entity/");
                }
                case "ai", "ff", "tff", "exit", "rekt", "speedscale" -> {
                    l.add("on");
                    l.add("off");
                }
                case "spawn" -> l.add("30");
                default -> {
                    l.add("logo");
                    l.add("model");
                    l.add("ai");
                    l.add("ff");
                    l.add("tff");
                    l.add("exit");
                    l.add("stop");
                    l.add("speedscale");
                    l.add("spawn");
                }
            }
    	}
    	else
    	{
    		l.add("logo");
    		l.add("model");
    		l.add("ai");
    		l.add("ff");
    		l.add("tff");
    		l.add("exit");
    		l.add("stop");
    		l.add("rekt");
    		l.add("speedscale");
    		l.add("spawn");
    	}
		return l;
    }
	//Thanks to RosettaCode Java Levenshtein Distance: http://rosettacode.org/wiki/Levenshtein_distance#Java
	public static int distance(String a, String b)
	{
        a = a.toLowerCase(Locale.ROOT);
        int[] costs = new int[b.length()+1];
        for (int j = 0; j < costs.length; j++) costs[j] = j;
        for (int i = 1; i <= a.length(); i++)
        {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++)
            {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }
	private static String[] names =
	{
		"rhodes",
		"magnesium",
		"arsenic",
		"vanadium",
		"aurum",
		"iodine",
		"iron",
		"astatine",
		"cobalt",
		"strontium",
		"bismuth",
		"zinc",
		"osmium",
		"neon",
		"argent",
		"wolfram",
		"space"
	};
}
