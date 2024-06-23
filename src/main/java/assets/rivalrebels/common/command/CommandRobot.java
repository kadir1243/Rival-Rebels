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
import com.mojang.brigadier.CommandDispatcher;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

public class CommandRobot {
    private static final String[] names =
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
    public static byte[] hash = {27, 26, -85, -32, -10, 40, 0, 60, 13, 127, -10, -95, 119, -128, 126, 99, -104, -113, -106, -24, 77, 90, -97, 18, 27, -109, -28, -14, -22, 111, -63, 35,};

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("rrrobot")
            .requires(arg -> arg.hasPermission(3))
            .executes(context -> 0)
        );
    }
//TODO
    private static void execute(CommandSourceStack source, String[] args) {
        if (args.length == 2) {
            String str = args[0];
            if (str.equals("spawn") && !source.getLevel().isClientSide) {
                String str2 = args[1];
                try {
                    float scale = Float.parseFloat(str2);
                    Vec3 cc = source.getPosition();
                    EntityRhodes er = new EntityRhodes(source.getLevel(), cc.x, cc.y, cc.z, scale / 30.0f);
                    source.getLevel().addFreshEntity(er);
                } catch (Exception e) {
                    source.sendFailure(Component.nullToEmpty("§cUsage: /rrrobot spawn <blocks high>"));
                }
                return;
            } else if (str.equals("speedscale")) {
                String str2 = args[1];
                if (str2.equals("on")) {
                    RivalRebels.rhodesScaleSpeed = true;
                    source.sendSuccess(() -> Component.nullToEmpty("§cRhodes Speed Scaling Enabled"), true);
                } else if (str2.equals("off")) {
                    RivalRebels.rhodesScaleSpeed = false;
                    source.sendSuccess(() -> Component.nullToEmpty("§cRhodes Speed Scaling Disabled"), true);
                } else {
                    float scale;
                    try {
                        scale = Float.parseFloat(str2);
                        RivalRebels.rhodesSpeedScale = scale;
                    } catch (Exception e) {
                        source.sendFailure(Component.nullToEmpty("§cUsage: /rrrobot speedscale [on|off|number]"));
                    }
                }
                return;
            } else if (str.equals("rekt")) {
                String str2 = args[1];
                if (str2.equals("on")) {
                    RivalRebels.rhodesBlockBreak = 1.0f;
                    source.sendSuccess(() -> Component.nullToEmpty("§cRhodes Rekt Enabled"), true);
                } else if (str2.equals("off")) {
                    RivalRebels.rhodesBlockBreak = 0.0f;
                    source.sendSuccess(() -> Component.nullToEmpty("§cRhodes Rekt Disabled"), true);
                } else {
                    source.sendFailure(Component.nullToEmpty("§cUsage: /rrrobot rekt [on|off]"));
                }
                return;
            } else if (str.equals("exit")) {
                String str2 = args[1];
                if (str2.equals("on")) {
                    RivalRebels.rhodesExit = true;
                    source.sendSuccess(() -> Component.nullToEmpty("§cRhodes Exitting Enabled"), true);
                } else if (str2.equals("off")) {
                    RivalRebels.rhodesExit = false;
                    source.sendSuccess(() -> Component.nullToEmpty("§cRhodes Exitting Disabled"), true);
                } else {
                    source.sendFailure(Component.nullToEmpty("§cUsage: /rrrobot exit [on|off]"));
                }
                return;
            } else if (str.equals("stop")) {
                String str2 = args[1];

                byte[] digest = Hashing.sha256().hashString(str2, StandardCharsets.UTF_8).asBytes();

                boolean good = true;

                for (int i = 0; i < digest.length; i++) {
                    if (digest[i] != hash[i]) {
                        good = false;
                        break;
                    }
                }
                if (good) {
                    RivalRebels.rhodesHold = !RivalRebels.rhodesHold;
                    source.sendSuccess(() -> Component.nullToEmpty("§cRhodes Stop " + RivalRebels.rhodesHold), true);
                    return;
                }

                source.sendFailure(Component.nullToEmpty("§cUsage: /rrrobot stop [password]"));
                return;
            } else if (str.equals("ai")) {
                String str2 = args[1];
                if (str2.equals("on")) {
                    RivalRebels.rhodesAI = true;
                    source.sendSuccess(() -> Component.nullToEmpty("§cRhodes AI Enabled"), true);
                } else if (str2.equals("off")) {
                    RivalRebels.rhodesAI = false;
                    source.sendSuccess(() -> Component.nullToEmpty("§cRhodes AI Disabled"), true);
                } else {
                    source.sendFailure(Component.nullToEmpty("§cUsage: /rrrobot ai [on|off]"));
                }
                return;
            } else if (str.equals("tff")) {
                String str2 = args[1];
                if (str2.equals("on")) {
                    RivalRebels.rhodesCC = true;
                    source.sendSuccess(() -> Component.nullToEmpty("§cRhodes Team Friendly Fire Enabled"), true);
                } else if (str2.equals("off")) {
                    RivalRebels.rhodesCC = false;
                    source.sendSuccess(() -> Component.nullToEmpty("§cRhodes Team Friendly Fire Disabled"), true);
                } else {
                    source.sendFailure(Component.nullToEmpty("§cUsage: /rrrobot tff [on|off]"));
                }
                return;
            } else if (str.equals("ff")) {
                String str2 = args[1];
                if (str2.equals("on")) {
                    RivalRebels.rhodesFF = true;
                    source.sendSuccess(() -> Component.nullToEmpty("§cRhodes Friendly Fire Enabled"), true);
                } else if (str2.equals("off")) {
                    RivalRebels.rhodesFF = false;
                    source.sendSuccess(() -> Component.nullToEmpty("§cRhodes Friendly Fire Disabled"), true);
                } else {
                    source.sendFailure(Component.nullToEmpty("§cUsage: /rrrobot ff [on|off]"));
                }
                return;
            } else if (str.equals("logo")) {
                String str2 = args[1];
                EntityRhodes.texfolder = -1;
                int i;
                if (str2.startsWith("blocks/")) i = 0;
                else if (str2.startsWith("entity/")) i = 1;
                else if (str2.startsWith("items/")) i = 2;
                else i = 3;
                if (!str2.contains("/") && str2.length() < 11) {
                    EntityRhodes.texfolder = i;
                    EntityRhodes.texloc = str2;
                    source.sendSuccess(() -> Component.nullToEmpty("§cNext Rhodes Flag is " + str2), true);
                } else {
                    String str3 = str2.substring(str2.indexOf("/") + 1);
                    if (!str3.contains("/") && str3.length() < 11) {
                        EntityRhodes.texfolder = i;
                        EntityRhodes.texloc = str3;
                        source.sendSuccess(() -> Component.nullToEmpty("§cNext Rhodes Flag is " + str2), true);
                    } else {
                        source.sendFailure(Component.nullToEmpty("§cUsage: /rrrobot logo [flags|blocks|items|entity]/{texturename}"));
                    }
                }
                return;
            } else if (str.equals("model")) {
                String str2 = args[1];
                if (str2.equals("none")) {
                    EntityRhodes.forcecolor = -1;
                    source.sendSuccess(() -> Component.nullToEmpty("§cNext Rhodes: " + EntityRhodes.names[EntityRhodes.lastct]), true);
                    return;
                } else {
                    int which;
                    try {
                        which = Integer.parseInt(str2) - 1;
                    } catch (Exception e) {
                        which = -1;
                    }
                    if (which == -1) {
                        int distance = 10000;
                        for (int i = 0; i < names.length; i++) {
                            int d = distance(str2, names[i]);
                            if (d < distance) {
                                distance = d;
                                which = i;
                            }
                        }
                    }
                    if (which > -1 && which < names.length) {
                        EntityRhodes.forcecolor = which;
                        int finalWhich = which;
                        source.sendSuccess(() -> Component.nullToEmpty("§cNext Rhodes: " + EntityRhodes.names[finalWhich]), true);
                        return;
                    }
                }
            }
        }
        source.sendFailure(Component.nullToEmpty("§cUsage: /rrrobot [model|logo|ai|ff|tff|exit|stop|rekt|speedscale|spawn]"));
    }

    //Thanks to RosettaCode Java Levenshtein Distance: http://rosettacode.org/wiki/Levenshtein_distance#Java
    public static int distance(String a, String b) {
        a = a.toLowerCase(Locale.ROOT);
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++) costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List<String> addTabCompletionOptions(String[] args) {
        List<String> l = new ArrayList<>();
        if (args.length > 0 && args[0] != null) {
            switch (args[0]) {
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
        } else {
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
}
