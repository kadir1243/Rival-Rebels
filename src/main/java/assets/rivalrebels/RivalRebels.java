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
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.command.*;
import assets.rivalrebels.common.core.RivalRebelsGuiHandler;
import assets.rivalrebels.common.core.RivalRebelsSoundEventHandler;
import assets.rivalrebels.common.entity.RREntities;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.round.RivalRebelsRound;
import assets.rivalrebels.common.tileentity.RRTileEntities;
import assets.rivalrebels.datagen.DataGen;
import com.mojang.logging.LogUtils;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.TagKey;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.slf4j.Logger;

import java.util.List;
import java.util.function.BiConsumer;

@Mod(RivalRebels.MODID)
public class RivalRebels {
    public static final Logger LOGGER = LogUtils.getLogger();
	public static final String MODID = "rivalrebels";
	public static final String rrname = "Rival Rebels";
	public static CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

	public static RivalRebels instance;

    public static RivalRebelsRound round;

    // TODO: Move all config things to RRConfig
    public static int						teleportDist;
	public static boolean					flareExplode;
	public static boolean					infiniteAmmo;
	public static boolean					infiniteNukes;
	public static boolean					infiniteGrenades;
	public static int						landmineExplodeSize;
	public static int						chargeExplodeSize;
	public static int						timedbombExplodeSize;
	public static int						rpgExplodeSize;
	public static int						flamethrowerDecay;
	public static int						rpgDecay;
	public static int						teslaDecay;
	public static int						timedbombTimer;
	public static int						nuclearBombCountdown;
	public static int						nuclearBombStrength;
	public static int						tsarBombaStrength;
	public static int						b83Strength;
	public static int						resetMax;
	public static int						spawnradius;
	public static int						bunkerradius;
	public static int						objectivedistance;
	public static float						knifeThrowStrengthDecay;
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
	public static boolean 					rhodesFF;
	public static boolean					rhodesAI;
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

    public static RivalRebelsRenderOverlay	rrro;

    public RivalRebels() {
        instance = this;
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(DataGen::onData);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            bus.addListener(this::registerClientSide);
            MinecraftForge.EVENT_BUS.addListener(RRBlocks::registerBlockColors);
            bus.addListener(this::registerRenderers);
        }

        MinecraftForge.EVENT_BUS.register(this);
        bus.addGenericListener(Block.class, this::registerBlocks);
        bus.addGenericListener(Item.class, this::registerItems);
        bus.addGenericListener(SoundEvent.class, RivalRebelsSoundEventHandler::onSoundLoad);
        bus.addGenericListener(EntityType.class, this::registerEntityTypes);
        bus.addGenericListener(BlockEntityType.class, this::registerBlockEntityTypes);
        bus.addGenericListener(ScreenHandlerType.class, this::registerScreenHandlerTypes);

        ModLoadingContext context = ModLoadingContext.get();
        context.registerConfig(ModConfig.Type.COMMON, RRConfig.COMMON_SPEC);
        context.registerConfig(ModConfig.Type.CLIENT, RRConfig.CLIENT_SPEC);
        context.registerConfig(ModConfig.Type.SERVER, RRConfig.SERVER_SPEC);
    }

	public void setup(FMLCommonSetupEvent event) {
		/*Configuration config = new Configuration(event.getSuggestedConfigurationFile());
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
		knifeThrowStrengthDecay = (config.get("decay", "KnifeThrowStrengthDecay", 91).getInt()) / 100F;
		config.addCustomCategoryComment("decay", "Measured in ticks of existence. Tesla is in blocks.");

		timedbombTimer = config.get("timing", "TimedBombSeconds", 25).getInt();
		nuclearBombCountdown = config.get("timing", "NuclearBombCountdown", 25).getInt();
		config.addCustomCategoryComment("timing", "Measured in seconds.");

		config.save();
		if (safemode) limitConfigValues();*/

		PacketDispatcher.registerPackets();

		MinecraftForge.EVENT_BUS.register(round = new RivalRebelsRound(spawnradius,teleportDist,objectivedistance));
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (event.getWorld() instanceof World)
            round.load((World) event.getWorld());
    }

	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event) {
        if (event.getWorld() instanceof World)
            round.save((World) event.getWorld());
	}

    @SubscribeEvent
    public void onWorldSave(WorldEvent.Save event) {
        if (event.getWorld() instanceof World)
            round.save((World) event.getWorld());
    }

	public void limitConfigValues()
	{
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
		if (knifeThrowStrengthDecay <= 0.8F) knifeThrowStrengthDecay = 0.8F;
		if (knifeThrowStrengthDecay >= 1F) knifeThrowStrengthDecay = 1F;
		if (tsarBombaSpeed <= 4) tsarBombaSpeed = 4;
		if (rhodesHealth < 15000) rhodesHealth = 15000;
	}

    public void registerClientSide(FMLClientSetupEvent event) {
        ClientProxy.registerKeyBinding();
		rrro = new RivalRebelsRenderOverlay();
		MinecraftForge.EVENT_BUS.register(rrro);
        RivalRebelsGuiHandler.registerClientGuiBinds();
    }

    @OnlyIn(Dist.CLIENT)
    public void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ClientProxy.registerRenderInformation(event);
    }

    @SubscribeEvent
    public void registerCommand(RegisterCommandsEvent event) {
        CommandResetGame.register(event.getDispatcher());
        CommandPassword.register(event.getDispatcher());
        CommandPlaySound.register(event.getDispatcher());
        CommandStopRounds.register(event.getDispatcher());
        CommandContinueRound.register(event.getDispatcher());
        CommandMotD.register(event.getDispatcher());
        CommandRobot.register(event.getDispatcher());
        CommandHotPotato.register(event.getDispatcher());
    }

    public void registerBlocks(RegistryEvent.Register<Block> register) {
        for (Block block : RRBlocks.BLOCKS) {
            register.getRegistry().register(block);
        }
    }

    public void registerScreenHandlerTypes(RegistryEvent.Register<ScreenHandlerType<?>> register) {
        for (ScreenHandlerType<?> type : RivalRebelsGuiHandler.SCREEN_HANDLER_TYPES) {
            register.getRegistry().register(type);
        }
    }

    public void registerItems(RegistryEvent.Register<Item> event) {
        for (Item item : RRItems.ITEMS) {
            event.getRegistry().register(item);
        }

        for (Item item : RRBlocks.BLOCK_ITEMS) {
            event.getRegistry().register(item);
        }

        IForgeRegistry<Item> registry = event.getRegistry();

        BiConsumer<Item, String> consumer = (item, string) -> {
            item.setRegistryName(MODID, string);
            registry.register(item);
        };

        consumer.accept(RRItems.orebelhelmet, "orebelhelmet");
		consumer.accept(RRItems.orebelchest, "orebelchest");
		consumer.accept(RRItems.orebelpants, "orebelpants");
		consumer.accept(RRItems.orebelboots, "orebelboots");
		consumer.accept(RRItems.onukerhelmet, "onukerhelmet");
		consumer.accept(RRItems.onukerchest, "onukerchest");
		consumer.accept(RRItems.onukerpants, "onukerpants");
		consumer.accept(RRItems.onukerboots, "onukerboots");
		consumer.accept(RRItems.ointelhelmet, "ointelhelmet");
		consumer.accept(RRItems.ointelchest, "ointelchest");
		consumer.accept(RRItems.ointelpants, "ointelpants");
		consumer.accept(RRItems.ointelboots, "ointelboots");
		consumer.accept(RRItems.ohackerhelmet, "ohackerhelmet");
		consumer.accept(RRItems.ohackerchest, "ohackerchest");
		consumer.accept(RRItems.ohackerpants, "ohackerpants");
		consumer.accept(RRItems.ohackerboots, "ohackerboots");
		consumer.accept(RRItems.srebelhelmet, "srebelhelmet");
		consumer.accept(RRItems.srebelchest, "srebelchest");
		consumer.accept(RRItems.srebelpants, "srebelpants");
		consumer.accept(RRItems.srebelboots, "srebelboots");
		consumer.accept(RRItems.snukerhelmet, "snukerhelmet");
		consumer.accept(RRItems.snukerchest, "snukerchest");
		consumer.accept(RRItems.snukerpants, "snukerpants");
		consumer.accept(RRItems.snukerboots, "snukerboots");
		consumer.accept(RRItems.sintelhelmet, "sintelhelmet");
		consumer.accept(RRItems.sintelchest, "sintelchest");
		consumer.accept(RRItems.sintelpants, "sintelpants");
		consumer.accept(RRItems.sintelboots, "sintelboots");
		consumer.accept(RRItems.shackerhelmet, "shackerhelmet");
		consumer.accept(RRItems.shackerchest, "shackerchest");
		consumer.accept(RRItems.shackerpants, "shackerpants");
		consumer.accept(RRItems.shackerboots, "shackerboots");
		consumer.accept(RRItems.camohat, "camohat");
		consumer.accept(RRItems.camoshirt, "camoshirt");
		consumer.accept(RRItems.camopants, "camopants");
		consumer.accept(RRItems.camoshoes, "camoshoes");
		consumer.accept(RRItems.camohat2, "camohat2");
		consumer.accept(RRItems.camoshirt2, "camoshirt2");
		consumer.accept(RRItems.camopants2, "camopants2");
		consumer.accept(RRItems.camoshoes2, "camoshoes2");
	}

    public void registerEntityTypes(RegistryEvent.Register<EntityType<?>> event) {
        for (EntityType<?> type : RREntities.TYPES) {
            event.getRegistry().register(type);
        }
    }

    public void registerBlockEntityTypes(RegistryEvent.Register<BlockEntityType<?>> event) {
        RRTileEntities.register(event.getRegistry());
    }

	/*private void registerEntities() {
        int nextNum = -1;
        EntityRegistry.registerModEntity(new Identifier(MODID, "gas_grenade"), EntityGasGrenade.class, "gas_grenade", ++nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "cuchillo"), EntityCuchillo.class, "cuchillo", ++nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "propulsion_fx"), EntityPropulsionFX.class, "propulsion_fx", ++nextNum, this, 250, 9, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "passive_fire"), EntityPassiveFire.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 9, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "rocket"), EntityRocket.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "plasmoid"), EntityPlasmoid.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 400, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "raytrace"), EntityRaytrace.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "lightning_link"), EntityLightningLink.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "nuclear_blast"), EntityNuclearBlast.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "flameball"), EntityFlameBall.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "flameball_1"), EntityFlameBall1.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "flameball_2"), EntityFlameBall2.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "laptop"), EntityLaptop.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "roddisk_regular"), EntityRoddiskRegular.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "roddisk_rebel"), EntityRoddiskRebel.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "roddisk_officer"), EntityRoddiskOfficer.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "roddisk_leader"), EntityRoddiskLeader.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "tsar_blast"), EntityTsarBlast.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "laser_link"), EntityLaserLink.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "gore"), EntityGore.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "blood"), EntityBlood.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 4, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "goo"), EntityGoo.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 4, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "laser_burst"), EntityLaserBurst.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 400, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "b83"), EntityB83.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "b2_spirit"), EntityB2Spirit.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 400, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "b2_frag"), EntityB2Frag.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "debris"), EntityDebris.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 3, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "hack_b83"), EntityHackB83.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "seek_b83"), EntitySeekB83.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "rhodes"), EntityRhodes.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 400, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "rhodes_head"), EntityRhodesHead.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "rhodes_torso"), EntityRhodesTorso.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "rhodes_left_upper_arm"), EntityRhodesLeftUpperArm.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "rhodes_right_upper_arm"), EntityRhodesRightUpperArm.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "rhodes_left_lower_arm"), EntityRhodesLeftLowerArm.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "rhodes_right_lower_arm"), EntityRhodesRightLowerArm.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "rhodes_left_upper_leg"), EntityRhodesLeftUpperLeg.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "rhodes_right_upper_leg"), EntityRhodesRightUpperLeg.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "rhodes_left_lower_leg"), EntityRhodesLeftLowerLeg.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "rhodes_right_lower_leg"), EntityRhodesRightLowerLeg.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "b83_no_shroom"), EntityB83NoShroom.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "sphere_blast"), EntitySphereBlast.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "nuke"), EntityNuke.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "tsar"), EntityTsar.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "roddiskrep"), EntityRoddiskRep.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "hot_potato"), EntityHotPotato.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "bomb"), EntityBomb.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "theoretical_tsar"), EntityTheoreticalTsar.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "theoretical_tsar_blast"), EntityTheoreticalTsarBlast.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "flame_ball_green"), EntityFlameBallGreen.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "antimatter_bomb"), EntityAntimatterBomb.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "antimatter_bomb_blast"), EntityAntimatterBombBlast.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "tachyon_bomb_blast"), EntityTachyonBombBlast.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new Identifier(MODID, "tachyon_bomb"), EntityTachyonBomb.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
	}*/

    public static List<Block> getBlocks(TagKey<Block> tagKey) {
        return ForgeRegistries.BLOCKS.tags().getTag(tagKey).stream().toList();
    }
}
