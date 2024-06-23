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
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.round.RivalRebelsPlayer;
import assets.rivalrebels.common.round.RivalRebelsRank;
import com.google.common.hash.Hashing;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.nio.charset.StandardCharsets;

public class CommandPassword {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("rr")
            .then(CommandManager.argument("code", StringArgumentType.greedyString())
                .executes(commandContext -> execute(commandContext.getSource(), StringArgumentType.getString(commandContext, "code")))
            )
        );
    }

    private static final String[] rhashes = new String[]{
			"23742137371982715120014159120241255637172",
			"1518918615824625494170109603025017352201241"
	};
    private static final String[] ohashes = new String[]{
			"127254246888283236291831726971211129135192",
			"17314923891217222431372172462419922385201191"
	};
    private static final String[] lhashes = new String[]{
			"612401057716617559272422511992314614422575",
			"99188249382921446717719913013762206120"
	};
    private static final String[] shashes = new String[]{
			"222152281681152820718419502273648223209",
			"107170188164102246158207236028166217204217177"
	};

    private static int execute(ServerCommandSource source, String code) {
		Text message;
        String encrypted = encrypt(code);

		RivalRebelsRank rank;
        if (rhashes[0].equals(encrypted)||rhashes[1].equals(encrypted))
		{
			rank = RivalRebelsRank.REBEL;
			message = Text.of("Welcome, rebel!");
		}
		else if (ohashes[0].equals(encrypted)||ohashes[1].equals(encrypted))
		{
			rank = RivalRebelsRank.OFFICER;
			message = Text.of("Welcome, officer!");
		}
		else if (lhashes[0].equals(encrypted)||lhashes[1].equals(encrypted))
		{
			rank = RivalRebelsRank.LEADER;
			message = Text.of("Welcome, leader!");
		}
		else if (shashes[0].equals(encrypted)||shashes[1].equals(encrypted))
		{
			rank = RivalRebelsRank.REP;
			message = Text.of("Welcome, representative!");
		} else {
            rank = RivalRebelsRank.REGULAR;
            message = Text.of("nope.");
        }
		RivalRebelsPlayer p = RivalRebels.round.rrplayerlist.getForGameProfile(source.getPlayer().getGameProfile());
		if (p.rrrank != rank || rank == RivalRebelsRank.REGULAR)
		{
			p.rrrank = rank;
			RivalRebelsSoundPlayer.playSound(source.getWorld(), 28, rank.snf, source.getPosition());
			RivalRebels.round.rrplayerlist.refreshForWorld(source.getWorld());
			source.sendFeedback(() -> message, true);
		}
        return 0;
	}

	public static String encrypt(String source) {
        return Hashing.md5().hashString(source, StandardCharsets.UTF_8).toString();
	}
}
