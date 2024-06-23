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

import assets.rivalrebels.common.entity.EntityHotPotato;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandHotPotato {
    public static BlockPos pos = BlockPos.ORIGIN;
	public static World world = null;
	public static boolean roundinprogress = false;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("rrhotpotato")
            .requires(arg -> arg.hasPermissionLevel(3))
            .then(CommandManager.argument("numberOfRounds", IntegerArgumentType.integer())
                .executes(context -> execute(context.getSource(), IntegerArgumentType.getInteger(context, "numberOfRounds"))))
            .then(CommandManager.literal("stop")
                .executes(context -> execute(context.getSource(), -1))
            )
        );
    }

    private static int execute(ServerCommandSource source, int numberOfRounds) {
        if (numberOfRounds == -1) {
            roundinprogress = false;
            source.sendFeedback(() -> Text.of("§cRound stopped."), true);
        } else {
            if (roundinprogress) {
                source.sendError(Text.of("§cRound already in progress! Do /rrhotpotato stop to end the current round."));
                return 0;
            }
            source.sendFeedback(() -> Text.of("§cLet the Hot Potato games begin! " + numberOfRounds + " rounds."), true);
            EntityHotPotato tsar = new EntityHotPotato(source.getWorld(),pos.getX(),pos.getY(), pos.getZ(), numberOfRounds);
            source.getWorld().spawnEntity(tsar);
            roundinprogress = true;
        }
        return 0;
    }
}
