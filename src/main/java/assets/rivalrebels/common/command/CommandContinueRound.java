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
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class CommandContinueRound {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("rrstartround")
            .requires(arg -> arg.hasPermissionLevel(3))
            .executes(commandContext -> execute(commandContext.getSource()))
        );
    }

    private static int execute(ServerCommandSource source) {
		RivalRebels.round.stopRounds();
		RivalRebels.round.newRound();
		source.sendFeedback(() -> Text.of("The current round has been restarted."), true);
        return 0;
	}
}
