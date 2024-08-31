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

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.entity.RhodesType;
import com.google.common.hash.Hashing;
import com.mojang.brigadier.CommandDispatcher;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

public class CommandRobot {
    public static final byte[] hash = {27, 26, -85, -32, -10, 40, 0, 60, 13, 127, -10, -95, 119, -128, 126, 99, -104, -113, -106, -24, 77, 90, -97, 18, 27, -109, -28, -14, -22, 111, -63, 35,};
    public static boolean rhodesExit = true;
    public static boolean rhodesHold;

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("rrrobot")
            .requires(arg -> arg.hasPermission(3))
            .then(Commands.literal("spawn")
                    .executes(context -> {
                        CommandSourceStack source = context.getSource();
                        Vec3 cc = source.getPosition();
                        EntityRhodes er = new EntityRhodes(source.getLevel(), cc.x, cc.y, cc.z, 1);
                        source.getLevel().addFreshEntity(er);
                        return 0;
                     })
                    .then(Commands.argument("scale", FloatArgumentType.floatArg())
                        .executes(context -> {
                            CommandSourceStack source = context.getSource();
                            float scale = FloatArgumentType.getFloat(context, "scale");
                            Vec3 cc = source.getPosition();
                            EntityRhodes er = new EntityRhodes(source.getLevel(), cc.x, cc.y, cc.z, scale / 30.0f);
                            source.getLevel().addFreshEntity(er);
                            return 0;
                        })))
            .then(Commands.literal("exit")
                .then(Commands.argument("enabled", BoolArgumentType.bool())
                    .executes(context -> {
                        boolean enabled = BoolArgumentType.getBool(context, "enabled");
                        rhodesExit = enabled;
                        context.getSource().sendSuccess(() -> Component.literal("Rhodes Exitting set to " + enabled).withStyle(ChatFormatting.RED), true);

                        return 0;
                    })
                )
            )
            .then(Commands.literal("stop")
                .then(Commands.argument("password", StringArgumentType.string())
                    .executes(context -> {
                        String password = StringArgumentType.getString(context, "password");

                        byte[] digest = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).asBytes();

                        boolean good = true;

                        for (int i = 0; i < digest.length; i++) {
                            if (digest[i] != hash[i]) {
                                good = false;
                                break;
                            }
                        }
                        if (good) {
                            rhodesHold = !rhodesHold;
                            context.getSource().sendSuccess(() -> Component.literal("Rhodes Stop set to " + rhodesHold).withStyle(ChatFormatting.RED), true);
                        } else {
                            context.getSource().sendFailure(Component.literal("Usage: /rrrobot stop [password]").withStyle(ChatFormatting.RED));
                        }

                        return 0;
                    })
                )
            )
            .then(Commands.literal("model")
                .then(Commands.argument("type", RhodesTypeArgumentType.argumentType())
                    .executes(context -> {
                        RhodesType type = RhodesTypeArgumentType.get(context, "type");
                        EntityRhodes.forcecolor = type;
                        context.getSource().sendSuccess(() -> Component.literal("Next Rhodes: " + type.getSerializedName()).withStyle(ChatFormatting.RED), true);

                        return 0;
                    })
                )
                .executes(context -> {
                    EntityRhodes.forcecolor = RhodesType.Rhodes;
                    context.getSource().sendSuccess(() -> Component.literal("Next Rhodes: " + RRConfig.SERVER.getRhodesTeams()[EntityRhodes.lastct].getSerializedName()).withStyle(ChatFormatting.RED), true);
                    return 0;
                })
            )
            .then(Commands.literal("logo")
                .then(Commands.argument("pathToTexture", StringArgumentType.string())
                    .executes(context -> {
                        String pathToTexture = StringArgumentType.getString(context, "pathToTexture");
                        EntityRhodes.texloc = pathToTexture;
                        context.getSource().sendSuccess(() -> Component.nullToEmpty("§cNext Rhodes Flag is " + pathToTexture), true);
                        return 0;
                    })
                )
            )
        );
    }

    public static class RhodesTypeArgumentType implements ArgumentType<RhodesType> {
        public static final DynamicCommandExceptionType ERROR_INVALID_VALUE = new DynamicCommandExceptionType(
            o -> Component.translatableEscape("argument.id.unknown", o)
        );
        private static final List<String> STRINGS = Arrays.stream(RhodesType.values()).map(Enum::toString).toList();

        public static RhodesTypeArgumentType argumentType() {
            return new RhodesTypeArgumentType();
        }

        public static RhodesType get(final CommandContext<?> context, final String name) {
            return context.getArgument(name, RhodesType.class);
        }

        @Override
        public RhodesType parse(StringReader reader) throws CommandSyntaxException {
            String string = reader.readUnquotedString();
            RhodesType rhodesType = Arrays.stream(RhodesType.values()).filter(type -> type.getSerializedName().equalsIgnoreCase(string)).findFirst().orElse(null);
            if (rhodesType != null) {
                return rhodesType;
            } else {
                throw ERROR_INVALID_VALUE.createWithContext(reader, string);
            }
        }

        @Override
        public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
            return SharedSuggestionProvider.suggest(STRINGS, builder);
        }
    }
}
