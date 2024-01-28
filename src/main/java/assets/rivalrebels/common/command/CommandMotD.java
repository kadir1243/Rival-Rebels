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
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class CommandMotD {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("rrmotd")
            .requires(arg -> arg.hasPermissionLevel(3))
                .executes(context -> execute(context.getSource(), null))
            .then(CommandManager.argument("motd", StringArgumentType.greedyString())
                .executes(context -> execute(context.getSource(), StringArgumentType.getString(context, "motd"))))
        );
    }

    private static int execute(ServerCommandSource source, String motd) throws CommandException {
        if (motd == null) source.sendFeedback(Text.of(RivalRebels.round.getMotD()), true);
        else RivalRebels.round.setMotD(motd);

        return 0;
    }

}
