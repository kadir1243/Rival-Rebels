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
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class CommandResetGame {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("rrreset")
            .requires(arg -> arg.hasPermission(3))
            .then(Commands.literal("all")
                    .executes(context -> execute(context.getSource(), null))
            )
            .then(Commands.argument("player", EntityArgument.player())
                    .executes(context -> execute(context.getSource(), EntityArgument.getPlayer(context, "player")))
            )
        );
    }

    private static int execute(CommandSourceStack source, Player player) {
		if (player == null) {
			RivalRebels.round.rrplayerlist.clearTeam();
			RivalRebels.round.rrplayerlist.refreshForWorld(source.getLevel());
			source.sendSuccess(() -> Component.nullToEmpty("§7All players have been reset."), true);
		} else if (RivalRebels.round.rrplayerlist.contains(player.getGameProfile())) {
			RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile()).clearTeam();
			RivalRebels.round.rrplayerlist.refreshForWorld(source.getLevel());
			source.sendSuccess(() -> Component.nullToEmpty("§7Player successfully reset."), true);
		} else {
			source.sendFailure(Component.nullToEmpty("§7No player by that name."));
		}
        return 0;
	}
}
