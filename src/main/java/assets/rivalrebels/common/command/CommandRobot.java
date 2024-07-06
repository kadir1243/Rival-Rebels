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

import com.mojang.brigadier.arguments.*;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

public class CommandRobot {
    public static final byte[] hash = {27, 26, -85, -32, -10, 40, 0, 60, 13, 127, -10, -95, 119, -128, 126, 99, -104, -113, -106, -24, 77, 90, -97, 18, 27, -109, -28, -14, -22, 111, -63, 35,};

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("rrrobot")
            .requires(arg -> arg.hasPermission(3))
            .then(Commands.literal("spawn")
                    .then(Commands.argument("scale", FloatArgumentType.floatArg())
                        .executes(context -> {
                            CommandSourceStack source = context.getSource();
                            float scale = FloatArgumentType.getFloat(context, "scale");
                            Vec3 cc = source.getPosition();
                            EntityRhodes er = new EntityRhodes(source.getLevel(), cc.x, cc.y, cc.z, scale / 30.0f);
                            source.getLevel().addFreshEntity(er);
                            return 0;
                        })))
            .then(Commands.literal("exit")
                .then(Commands.argument("enabled", BoolArgumentType.bool())
                    .executes(context -> {
                        boolean enabled = BoolArgumentType.getBool(context, "enabled");
                        RivalRebels.rhodesExit = enabled;
                        context.getSource().sendSuccess(() -> Component.literal("Rhodes Exitting set to " + enabled).withStyle(ChatFormatting.RED), true);

                        return 0;
                    })
                )
            )
            .then(Commands.literal("stop")
                .then(Commands.argument("password", StringArgumentType.string())
                    .executes(context -> {
                        String password = StringArgumentType.getString(context, "password");

                        byte[] digest = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).asBytes();

                        boolean good = true;

                        for (int i = 0; i < digest.length; i++) {
                            if (digest[i] != hash[i]) {
                                good = false;
                                break;
                            }
                        }
                        if (good) {
                            RivalRebels.rhodesHold = !RivalRebels.rhodesHold;
                            context.getSource().sendSuccess(() -> Component.literal("Rhodes Stop " + RivalRebels.rhodesHold).withStyle(ChatFormatting.RED), true);
                        } else {
                            context.getSource().sendFailure(Component.literal("Usage: /rrrobot stop [password]").withStyle(ChatFormatting.RED));
                        }

                        return 0;
                    })
                )
            )
            .then(Commands.literal("model")
                .then(Commands.argument("color", IntegerArgumentType.integer(0, EntityRhodes.names.length))
                    .executes(context -> {
                        int color = IntegerArgumentType.getInteger(context, "color");
                        EntityRhodes.forcecolor = color;
                        context.getSource().sendSuccess(() -> Component.literal("Next Rhodes: " + EntityRhodes.names[color]).withStyle(ChatFormatting.RED), true);

                        return 0;
                    })
                )
                .executes(context -> {
                    EntityRhodes.forcecolor = -1;
                    context.getSource().sendSuccess(() -> Component.literal("Next Rhodes: " + EntityRhodes.names[EntityRhodes.lastct]).withStyle(ChatFormatting.RED), true);
                    return 0;
                })
            )
            .then(Commands.literal("logo")
                .then(Commands.argument("pathToTexture", StringArgumentType.string())
                    .executes(context -> {
                        String pathToTexture = StringArgumentType.getString(context, "pathToTexture");
                        EntityRhodes.texfolder = -1;
                        int i;
                        if (pathToTexture.startsWith("blocks/")) i = 0;
                        else if (pathToTexture.startsWith("entity/")) i = 1;
                        else if (pathToTexture.startsWith("items/")) i = 2;
                        else i = 3;
                        if (!pathToTexture.contains("/") && pathToTexture.length() < 11) {
                            EntityRhodes.texfolder = i;
                            EntityRhodes.texloc = pathToTexture;
                            context.getSource().sendSuccess(() -> Component.nullToEmpty("§cNext Rhodes Flag is " + pathToTexture), true);
                        } else {
                            String toFirstSlash = pathToTexture.substring(pathToTexture.indexOf("/") + 1);
                            if (!toFirstSlash.contains("/") && toFirstSlash.length() < 11) {
                                EntityRhodes.texfolder = i;
                                EntityRhodes.texloc = toFirstSlash;
                                context.getSource().sendSuccess(() -> Component.nullToEmpty("§cNext Rhodes Flag is " + pathToTexture), true);
                            } else {
                                context.getSource().sendFailure(Component.nullToEmpty("§cUsage: /rrrobot logo [flags|blocks|items|entity]/{texturename}"));
                            }
                        }
                        return 0;
                    })
                )
            )
        );
    }
}
