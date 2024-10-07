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
package io.github.kadir1243.rivalrebels.common.command;

import io.github.kadir1243.rivalrebels.RivalRebels;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class CommandStopRounds {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("rrstopround")
            .requires(arg -> arg.hasPermission(3))
            .executes(context -> execute(context.getSource()))
        );
    }

    private static int execute(CommandSourceStack source) {
        RivalRebels.round.stopRounds();
        source.sendSuccess(() -> Component.nullToEmpty("The current round has been successfully stopped."), true);
        return 0;
    }
}
