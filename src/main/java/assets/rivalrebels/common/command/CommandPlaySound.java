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

import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.CommandException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Vec3d;

public class CommandPlaySound {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("rrsoundsystem")
            .then(CommandManager.argument("dir", IntegerArgumentType.integer())
                .then(CommandManager.argument("num", IntegerArgumentType.integer())
                    .then(CommandManager.argument("volume", FloatArgumentType.floatArg())
                        .then(CommandManager.argument("pitch", FloatArgumentType.floatArg())
                            .executes(context -> execute(context.getSource(), IntegerArgumentType.getInteger(context, "dir"), IntegerArgumentType.getInteger(context, "num"), FloatArgumentType.getFloat(context, "volume"), FloatArgumentType.getFloat(context, "pitch")))
                        )
                    )
                )
            )
        );
    }

    private static int execute(ServerCommandSource source, int dir, int num, float volume, float pitch) throws CommandException {
        Vec3d cc = source.getPosition();
        RivalRebelsSoundPlayer.playSound(source.getWorld(), dir, num, cc.x, cc.y, cc.z, volume, pitch);
		return 0;
	}
}
