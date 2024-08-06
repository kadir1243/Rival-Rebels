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
package assets.rivalrebels;

import assets.rivalrebels.client.gui.RivalRebelsRenderOverlay;
import assets.rivalrebels.client.itemrenders.*;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.command.*;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsGuiHandler;
import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.entity.RREntities;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.round.RivalRebelsRound;
import assets.rivalrebels.common.tileentity.RRTileEntities;
import com.google.common.base.Supplier;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import fuzs.forgeconfigapiport.fabric.api.forge.v4.ForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;

public class RivalRebels implements ModInitializer, ClientModInitializer {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static CommonProxy proxy;

    public static RivalRebelsRound round;

    @Environment(EnvType.CLIENT)
    public static RivalRebelsRenderOverlay rrro;

    @Override
    public void onInitialize() {
        proxy = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT ? new ClientProxy() : new CommonProxy();
        PacketDispatcher.registerPackets();

        ForgeConfigRegistry.INSTANCE.register(RRIdentifiers.MODID, ModConfig.Type.COMMON, RRConfig.COMMON_SPEC);
        ForgeConfigRegistry.INSTANCE.register(RRIdentifiers.MODID, ModConfig.Type.CLIENT, RRConfig.CLIENT_SPEC);
        ForgeConfigRegistry.INSTANCE.register(RRIdentifiers.MODID, ModConfig.Type.SERVER, RRConfig.SERVER_SPEC);

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            round.setRoundDistances(RRConfig.SERVER.getSpawnDomeRadius(), RRConfig.SERVER.getTeleportDistance(), RRConfig.SERVER.getObjectiveDistance());
        });
        round = new RivalRebelsRound();
        round.init();

        RRSounds.init();
        RivalRebelsGuiHandler.init();
        RRTileEntities.register();
        RivalRebelsDamageSource.RRDamageTypes.init();
        registerCommand();
        RRBlocks.init();
        RRComponents.init();
        RRItems.init();
        RREntities.TYPES.forEach((name, type) -> Registry.register(BuiltInRegistries.ENTITY_TYPE, RRIdentifiers.create(name), type));
    }

    @Override
    public void onInitializeClient() {
        PacketDispatcher.registerClientPackets();
        round.initClient();
        rrro = new RivalRebelsRenderOverlay();
        rrro.init();
        RivalRebelsGuiHandler.registerClientGuiBinds();
        ClientProxy.registerRenderInformation();
        ClientProxy.registerKeyBinding();
        RRBlocks.registerBlockColors();
        registerCustomRenderers();
    }

    private void registerCommand() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            CommandResetGame.register(dispatcher);
            CommandPassword.register(dispatcher);
            CommandStopRounds.register(dispatcher);
            CommandContinueRound.register(dispatcher);
            CommandMotD.register(dispatcher);
            CommandRobot.register(dispatcher);
            CommandHotPotato.register(dispatcher);
        });
    }

    public static List<Block> getBlocks(TagKey<Block> tagKey) {
        return BuiltInRegistries.BLOCK.asTagAddingLookup().get(tagKey).map(HolderSet.ListBacked::stream).map(s -> s.map(Holder::value).toList()).orElse(Collections.emptyList());
    }

    private static void addItemRenderer(ItemLike item, Supplier<DynamicItemRenderer> renderer) {
        BuiltinItemRendererRegistry.INSTANCE.register(item, new BuiltinItemRendererRegistry.DynamicItemRenderer() {
            private final DynamicItemRenderer itemRenderer = renderer.get();
            @Override
            public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
                itemRenderer.render(stack, mode, matrices, vertexConsumers, light, overlay);
            }
        });
    }

    private static void registerCustomRenderers() {
        addItemRenderer(RRItems.NUCLEAR_ROD, NuclearRodRenderer::new);
        addItemRenderer(RRItems.tesla, TeslaRenderer::new);
        addItemRenderer(RRItems.einsten, AstroBlasterRenderer::new);
        addItemRenderer(RRItems.battery, BatteryRenderer::new);
        addItemRenderer(RRItems.binoculars, BinocularsRenderer::new);
        addItemRenderer(RRItems.emptyrod, EmptyRodRenderer::new);
        addItemRenderer(RRItems.flamethrower, FlamethrowerRenderer::new);
        addItemRenderer(RRItems.fuel, GasRenderer::new);
        addItemRenderer(RRItems.hackm202, HackRocketLauncherRenderer::new);
        addItemRenderer(RRItems.hydrod, HydrogenRodRenderer::new);
        addItemRenderer(RRBlocks.controller, LaptopRenderer::new);
        addItemRenderer(RRBlocks.loader, LoaderRenderer::new);
        addItemRenderer(RRItems.plasmacannon, PlasmaCannonRenderer::new);
        addItemRenderer(RRBlocks.reactor, ReactorRenderer::new);
        addItemRenderer(RRItems.redrod, RedstoneRodRenderer::new);
        addItemRenderer(RRItems.rpg, RocketLauncherRenderer::new);
        addItemRenderer(RRItems.rocket, RocketRenderer::new);
        addItemRenderer(RRItems.roda, RodaRenderer::new);
        addItemRenderer(RRItems.roddisk, RodDiskRenderer::new);
        addItemRenderer(RRItems.seekm202, SeekRocketLauncherRenderer::new);
    }
}
