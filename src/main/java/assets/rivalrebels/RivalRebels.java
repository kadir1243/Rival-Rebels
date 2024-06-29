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
import com.google.common.base.Suppliers;
import com.mojang.logging.LogUtils;
import fuzs.forgeconfigapiport.fabric.api.forge.v4.ForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;

public class RivalRebels implements ModInitializer, ClientModInitializer {
    public static final Logger LOGGER = LogUtils.getLogger();
	public static final String MODID = "rivalrebels";
	public static CommonProxy proxy = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT ? new ClientProxy() : new CommonProxy();
    public static RivalRebelsRound round;

    // TODO: Move all config things to RRConfig
    public static int						teleportDist = 150;
	public static boolean					flareExplode = true;
	public static boolean					infiniteAmmo;
	public static boolean					infiniteNukes;
	public static boolean					infiniteGrenades;
	public static int						landmineExplodeSize = 2;
	public static int						chargeExplodeSize = 5;
	public static int						timedbombExplodeSize = 6;
	public static int						rpgExplodeSize = 4;
	public static int						flamethrowerDecay = 64;
	public static int						rpgDecay = 200;
	public static int						teslaDecay = 250;
	public static int						timedbombTimer = 25;
	public static int						nuclearBombCountdown = 25;
	public static int						nuclearBombStrength = 10;
	public static int						tsarBombaStrength = 24;
	public static int						b83Strength = 15;
	public static int						resetMax = 2;
	public static int						spawnradius = 20;
	public static int						bunkerradius;
	public static int						objectivedistance;
	public static int						plasmoidDecay;
	public static int						tsarBombaSpeed;
	public static int						b2spirithealth;
	public static boolean					rtarget;
	public static boolean					lctarget;
    public static int						teslasegments;
	public static boolean					stopSelfnukeinSP;
	public static boolean 					freeb83nukes;
	public static boolean					scoreboardenabled;
	public static boolean 					prefillrhodes;
	public static float						rhodesChance;
	public static int						rhodesHealth;
	public static int						rhodesNukes;
	public static float						rhodesRandom;
	public static int						rhodesRandomSeed;
	public static int[]						rhodesTeams;
	public static boolean					rhodesPromode;
	public static boolean 					rhodesFF = true;
	public static boolean					rhodesAI = true;
	public static boolean					rhodesExit = true;
	public static boolean					rhodesHold;
	public static boolean					rhodesCC;
	public static int						objectiveHealth;
	public static boolean					freeDragonAmmo;
	public static int						bombDamageToRhodes;
	public static boolean					rhodesScaleSpeed;
	public static float						rhodesSpeedScale;
	public static float						rhodesBlockBreak;
	public static boolean					nukedrop = true;
	public static boolean 					elevation = true;
    @Environment(EnvType.CLIENT)
    public static RivalRebelsRenderOverlay	rrro;

    @Override
    public void onInitialize() {
        PacketDispatcher.registerPackets();
        round = new RivalRebelsRound(spawnradius,teleportDist,objectivedistance);
        round.init();

        ForgeConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.COMMON, RRConfig.COMMON_SPEC);
        ForgeConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.CLIENT, RRConfig.CLIENT_SPEC);
        ForgeConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.SERVER, RRConfig.SERVER_SPEC);
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
        renderer = Suppliers.memoize(renderer);
        Supplier<DynamicItemRenderer> finalRenderer = renderer;
        BuiltinItemRendererRegistry.INSTANCE.register(item, (stack, mode, matrices, vertexConsumers, light, overlay) -> finalRenderer.get().render(stack, mode, matrices, vertexConsumers, light, overlay));
    }

    private static void registerCustomRenderers() {
        addItemRenderer(RRItems.nuclearelement, NuclearRodRenderer::new);
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

        /*public void setup(FMLCommonSetupEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		teleportDist = config.get("buildsize", "TeleportDistance", 150).getInt();
		spawnradius = config.get("buildsize", "SpawnDomeRadius", 20).getInt();
		bunkerradius = config.get("buildsize", "BunkerRadius", 10).getInt();
		objectivedistance = config.get("buildsize", "objectivedistance", 200).getInt();
		config.addCustomCategoryComment("buildsize", "Measured in blocks.");

		flareExplode = config.get("misc", "FlareExplodeOnBreak", true).getBoolean(true);
		infiniteAmmo = config.get("misc", "InfiniteAmmo", false).getBoolean(false);
		infiniteNukes = config.get("misc", "InfiniteNukes", false).getBoolean(false);
		infiniteGrenades = config.get("misc", "InfiniteGrenades", false).getBoolean(false);
		boolean safemode = config.get("misc", "safemode", true).getBoolean(true);
		resetMax = config.get("misc", "MaximumResets", 2).getInt();
		b2spirithealth = config.get("misc", "B2SpiritHealth", 1000).getInt();
		rtarget = config.get("misc", "RKeyTarget", true).getBoolean(true);
		lctarget = config.get("misc", "LeftClickTarget", true).getBoolean(true);
		teslasegments = config.get("misc", "teslasegments", 2).getInt();
		stopSelfnukeinSP = config.get("misc", "stopSelfnukeinSinglePlayer", false).getBoolean(false);
		freeb83nukes = config.get("misc", "freeb83nukes", false).getBoolean(false);
		scoreboardenabled = config.get("misc", "scoreboardEnabled", true).getBoolean(true);
		prefillrhodes = config.get("misc", "prefillrhodes", true).getBoolean(true);
		rhodesChance = (config.get("misc", "rhodesInRoundsChance", 0).getInt()) / 100F;
		rhodesHealth = (config.get("misc", "rhodesHealth", 15000).getInt());
		rhodesNukes = (config.get("misc", "rhodesNukes", 8).getInt());
		rhodesTeams = (config.get("misc", "rhodesTeams", new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}, "Range: 0-15. Repeat the numbers for multiple occurences of the same rhodes. 0:Rhodes 1:Magnesium 2:Arsenic 3:Vanadium 4:Aurum 5:Iodine 6:Iron 7:Astatine 8:Cobalt 9:Strontium 10:Bismuth 11:Zinc 12:Osmium 13:Neon 14:Argent, 15:Wolfram").getIntList());
		rhodesRandom = (config.getFloat("rhodesRandom", "misc", 1, 0, 5, "Multiplies the Rhodes' random ammo bonus. Set to 0 to disable bonus."));
		rhodesRandomSeed = (config.get("misc", "rhodesRandomSeed", 2168344).getInt());
		rhodesFF = config.get("misc", "rhodesFriendlyFire", true).getBoolean(true);
		rhodesAI = config.get("misc", "rhodesAIEnabled", true).getBoolean(true);
		rhodesCC = config.get("misc", "rhodesTeamFriendlyFire", true).getBoolean(true);
		rhodesPromode = config.get("misc", "rhodesPromode", false).getBoolean(false);
		objectiveHealth = (config.get("misc", "objectiveHealth", 15000).getInt());
		freeDragonAmmo = config.get("misc", "freeDragonAmmo", false).getBoolean(false);
		bombDamageToRhodes = (config.get("misc", "bombDamageToRhodes", 20).getInt());
		rhodesScaleSpeed = config.get("misc", "rhodesScaleSpeed", false).getBoolean(false);
		rhodesSpeedScale = (float)config.get("misc", "rhodesSpeedScale", 1.0f).getDouble(1.0f);
		rhodesBlockBreak = (float)config.get("misc", "rhodesBlockBreak", 1.0f).getDouble(1.0f);
		config.addCustomCategoryComment("misc", "Miscellaneous.");

		landmineExplodeSize = config.get("explosionsize", "LandmineExplosionSize", 2).getInt();
		chargeExplodeSize = config.get("explosionsize", "ChargeExplosionSize", 5).getInt();
		timedbombExplodeSize = config.get("explosionsize", "TimedBombExplosionSize", 6).getInt();
		rpgExplodeSize = config.get("explosionsize", "RocketExplosionSize", 4).getInt();
		nuclearBombStrength = config.get("explosionsize", "NuclearBombStrength", 10).getInt();
		tsarBombaStrength = config.get("explosionsize", "tsarBombaStrength", 24).getInt();
		b83Strength = config.get("explosionsize", "b83Strength", 15).getInt();
		tsarBombaSpeed = config.get("explosionsize", "tsarBombaSpeed", 8).getInt();
		elevation = config.get("explosionsize", "elevation", true).getBoolean(true);
		nukedrop = config.get("explosionsize", "nukedrop", true).getBoolean(true);
		config.addCustomCategoryComment("explosionsize", "Measured in blocks. Nuclear bomb just adds the specified number to its calculation.");

		flamethrowerDecay = config.get("decay", "FlamethrowerDecay", 64).getInt();
		rpgDecay = config.get("decay", "RPGDecay", 200).getInt();
		plasmoidDecay = config.get("decay", "plasmoidDecay", 70).getInt();
		teslaDecay = config.get("decay", "TeslaDecay", 250).getInt();
		config.addCustomCategoryComment("decay", "Measured in ticks of existence. Tesla is in blocks.");

		timedbombTimer = config.get("timing", "TimedBombSeconds", 25).getInt();
		nuclearBombCountdown = config.get("timing", "NuclearBombCountdown", 25).getInt();
		config.addCustomCategoryComment("timing", "Measured in seconds.");

		config.save();
		if (safemode) limitConfigValues();
    }

	public void limitConfigValues() {
		if (teleportDist <= 70) teleportDist = 70;
		if (teleportDist >= 500) teleportDist = 500;
		if (landmineExplodeSize <= 1) landmineExplodeSize = 1;
		if (landmineExplodeSize >= 15) landmineExplodeSize = 15;
		if (chargeExplodeSize <= 1) chargeExplodeSize = 1;
		if (chargeExplodeSize >= 15) chargeExplodeSize = 15;
		if (timedbombExplodeSize <= 1) timedbombExplodeSize = 1;
		if (timedbombExplodeSize >= 20) timedbombExplodeSize = 20;
		if (rpgExplodeSize <= 1) rpgExplodeSize = 1;
		if (rpgExplodeSize >= 10) rpgExplodeSize = 10;
		if (teslaDecay <= 20) teslaDecay = 20;
		if (teslaDecay >= 400) teslaDecay = 400;
		if (timedbombTimer <= 10) timedbombTimer = 10;
		if (timedbombTimer >= 300) timedbombTimer = 300;
		if (nuclearBombCountdown <= 1) nuclearBombCountdown = 1;
		if (nuclearBombStrength <= 2) nuclearBombStrength = 2;
		if (nuclearBombStrength >= 30) nuclearBombStrength = 30;
		if (tsarBombaStrength <= 0) tsarBombaStrength = 0;
		if (tsarBombaStrength >= 50) tsarBombaStrength = 50;
		if (b83Strength <= 0) b83Strength = 0;
		if (b83Strength >= 50) b83Strength = 50;
		if (resetMax >= 100) resetMax = 100;
		if (resetMax <= 0) resetMax = 0;
		if (spawnradius >= 30) spawnradius = 30;
		if (spawnradius <= 10) spawnradius = 10;
		if (bunkerradius >= 15) bunkerradius = 15;
		if (bunkerradius <= 7) bunkerradius = 7;
		if (tsarBombaSpeed <= 4) tsarBombaSpeed = 4;
		if (rhodesHealth < 15000) rhodesHealth = 15000;
	}*/

}
