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
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.entity.RhodesType;
import assets.rivalrebels.common.entity.RhodesTypes;
import com.google.common.hash.Hashing;
import com.mojang.brigadier.CommandDispatcher;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceOrIdArgument;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

public class CommandRobot {
    public static final byte[] hash = {27, 26, -85, -32, -10, 40, 0, 60, 13, 127, -10, -95, 119, -128, 126, 99, -104, -113, -106, -24, 77, 90, -97, 18, 27, -109, -28, -14, -22, 111, -63, 35,};
    public static boolean rhodesExit = true;
    public static boolean rhodesHold;

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext) {
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
                            try {
                                EntityRhodes er = new EntityRhodes(source.getLevel(), cc.x, cc.y, cc.z, scale / 30.0f);
                                source.getLevel().addFreshEntity(er);
                            } catch (Throwable e) {
                                RivalRebels.LOGGER.error("Unexpected error:", e);
                                throw e;
                            }
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
                .then(Commands.argument("type", RhodesTypeArgumentType.argumentType(commandBuildContext))
                    .executes(context -> {
                        Holder<RhodesType> type = RhodesTypeArgumentType.get(context, "type");
                        EntityRhodes.forcecolor = type;
                        context.getSource().sendSuccess(() -> Component.literal("Next Rhodes: ").append(type.value().getName()).withStyle(ChatFormatting.RED), true);

                        return 0;
                    })
                )
                .executes(context -> {
                    EntityRhodes.forcecolor = RivalRebels.RHODES_TYPE_REGISTRY.wrapAsHolder(RhodesTypes.Rhodes);
                    context.getSource().sendSuccess(() -> Component.literal("Rhodes Type Forcing Has Been Reset"), true);
                    context.getSource().sendSuccess(() -> Component.literal("Next Rhodes: ").append(RRConfig.SERVER.getRhodesTeams()[EntityRhodes.lastct].getName()).withStyle(ChatFormatting.RED), true);
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

    public static class RhodesTypeArgumentType extends ResourceOrIdArgument<RhodesType> {
        private static final Codec<Holder<RhodesType>> codec = RivalRebels.RHODES_TYPE_REGISTRY.holderByNameCodec();

        protected RhodesTypeArgumentType(CommandBuildContext context) {
            super(context, RivalRebels.RHODES_TYPE_REGISTRY_KEY, codec);
        }

        public static RhodesTypeArgumentType argumentType(CommandBuildContext context) {
            return new RhodesTypeArgumentType(context);
        }

        public static Holder<RhodesType> get(final CommandContext<CommandSourceStack> context, final String name) {
            return getResource(context, name);
        }

        @SuppressWarnings("unchecked")
        private static <T> Holder<T> getResource(CommandContext<CommandSourceStack> context, String name) {
            return context.getArgument(name, Holder.class);
        }

        @Override
        public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
            return SharedSuggestionProvider.suggestResource(RivalRebels.RHODES_TYPE_REGISTRY.stream(), builder, RivalRebels.RHODES_TYPE_REGISTRY::getKey, rhodesType -> RivalRebels.RHODES_TYPE_REGISTRY.getKey(rhodesType)::toString);
        }
    }
}
