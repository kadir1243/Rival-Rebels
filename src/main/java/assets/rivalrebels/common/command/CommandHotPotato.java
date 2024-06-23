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
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public class CommandHotPotato {
    public static BlockPos pos = BlockPos.ZERO;
	public static Level world = null;
	public static boolean roundinprogress = false;

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("rrhotpotato")
            .requires(arg -> arg.hasPermission(3))
            .then(Commands.argument("numberOfRounds", IntegerArgumentType.integer())
                .executes(context -> execute(context.getSource(), IntegerArgumentType.getInteger(context, "numberOfRounds"))))
            .then(Commands.literal("stop")
                .executes(context -> execute(context.getSource(), -1))
            )
        );
    }

    private static int execute(CommandSourceStack source, int numberOfRounds) {
        if (numberOfRounds == -1) {
            roundinprogress = false;
            source.sendSuccess(() -> Component.nullToEmpty("§cRound stopped."), true);
        } else {
            if (roundinprogress) {
                source.sendFailure(Component.nullToEmpty("§cRound already in progress! Do /rrhotpotato stop to end the current round."));
                return 0;
            }
            source.sendSuccess(() -> Component.nullToEmpty("§cLet the Hot Potato games begin! " + numberOfRounds + " rounds."), true);
            EntityHotPotato tsar = new EntityHotPotato(source.getLevel(),pos.getX(),pos.getY(), pos.getZ(), numberOfRounds);
            source.getLevel().addFreshEntity(tsar);
            roundinprogress = true;
        }
        return 0;
    }
}
