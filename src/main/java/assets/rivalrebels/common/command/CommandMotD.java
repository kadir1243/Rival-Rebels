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
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class CommandMotD {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("rrmotd")
            .requires(arg -> arg.hasPermission(3))
                .executes(context -> execute(context.getSource(), null))
            .then(Commands.argument("motd", StringArgumentType.greedyString())
                .executes(context -> execute(context.getSource(), StringArgumentType.getString(context, "motd"))))
        );
    }

    private static int execute(CommandSourceStack source, String motd) {
        if (motd == null) source.sendSuccess(() -> Component.nullToEmpty(RivalRebels.round.getMotD()), true);
        else RivalRebels.round.setMotD(motd);

        return 0;
    }

}
