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
import net.minecraft.command.CommandException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class CommandResetGame {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("rrreset")
            .requires(arg -> arg.hasPermissionLevel(3))
            .then(CommandManager.literal("all")
                    .executes(context -> execute(context.getSource(), null))
            )
            .then(CommandManager.argument("player", EntityArgumentType.player())
                    .executes(context -> execute(context.getSource(), EntityArgumentType.getPlayer(context, "player")))
            )
        );
    }

    private static int execute(ServerCommandSource source, PlayerEntity player) throws CommandException {
		if (player == null) {
			RivalRebels.round.rrplayerlist.clearTeam();
			RivalRebels.round.rrplayerlist.refreshForWorld(source.getWorld());
			source.sendFeedback(Text.of("§7All players have been reset."), true);
		} else if (RivalRebels.round.rrplayerlist.contains(player.getGameProfile())) {
			RivalRebels.round.rrplayerlist.getForGameProfile(player.getGameProfile()).clearTeam();
			RivalRebels.round.rrplayerlist.refreshForWorld(source.getWorld());
			source.sendFeedback(Text.of("§7Player successfully reset."), true);
		} else {
			source.sendError(Text.of("§7No player by that name."));
		}
        return 0;
	}
}
