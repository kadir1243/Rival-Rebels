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
import assets.rivalrebels.client.model.RenderLibrary;
import assets.rivalrebels.common.block.*;
import assets.rivalrebels.common.block.autobuilds.*;
import assets.rivalrebels.common.block.crate.*;
import assets.rivalrebels.common.block.machine.*;
import assets.rivalrebels.common.block.trap.*;
import assets.rivalrebels.common.command.*;
import assets.rivalrebels.common.core.RivalRebelsGuiHandler;
import assets.rivalrebels.common.core.RivalRebelsRecipes;
import assets.rivalrebels.common.core.RivalRebelsSoundEventHandler;
import assets.rivalrebels.common.core.RivalRebelsTab;
import assets.rivalrebels.common.entity.*;
import assets.rivalrebels.common.item.*;
import assets.rivalrebels.common.item.weapon.*;
import assets.rivalrebels.common.packet.PacketDispatcher;
import assets.rivalrebels.common.round.RivalRebelsRound;
import assets.rivalrebels.common.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.function.BiConsumer;

@Mod(modid = RivalRebels.MODID, name = RivalRebels.rrname, version = RivalRebels.rrversion)
public class RivalRebels {
    public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = Constants.MODID;
	public static final String rrname = Constants.MODNAME;
    public static final String rrversion = Constants.VERSION;
	public static final String packagename = "assets.rivalrebels.";
	@SidedProxy(clientSide = packagename+"ClientProxy", serverSide = packagename+"CommonProxy")
	public static CommonProxy proxy;

	@Mod.Instance(MODID)
	public static RivalRebels instance;

	public Configuration config;

    public static RivalRebelsTab			rralltab;
	public static RivalRebelsTab			rrarmortab;

	public static RivalRebelsRound			round;

	static ArmorMaterial					armorCamo		= EnumHelper.addArmorMaterial("Camo", "", 50, new int[] { 2, 9, 5, 2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1);
	static ArmorMaterial					armorCamo2		= EnumHelper.addArmorMaterial("Camo2", "", 50, new int[] { 2, 9, 5, 2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1);
	static ArmorMaterial					orebelarmor		= EnumHelper.addArmorMaterial("rebelo", "", 150, new int[] { 6, 18, 6, 6 }, 6, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1);
	static ArmorMaterial					onukerarmor		= EnumHelper.addArmorMaterial("nukero", "", 100, new int[] { 8, 20, 8, 6 }, 2, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1);
	static ArmorMaterial					ointelarmor		= EnumHelper.addArmorMaterial("intelo", "", 80, new int[] { 4, 11, 6, 5 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1);
	static ArmorMaterial					ohackerarmor	= EnumHelper.addArmorMaterial("hackero", "", 60, new int[] { 2, 11, 6, 5 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1);
	static ArmorMaterial					srebelarmor		= EnumHelper.addArmorMaterial("rebels", "", 150, new int[] { 6, 18, 6, 6 }, 6, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1);
	static ArmorMaterial					snukerarmor		= EnumHelper.addArmorMaterial("nukers", "", 100, new int[] { 8, 20, 8, 6 }, 2, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1);
	static ArmorMaterial					sintelarmor		= EnumHelper.addArmorMaterial("intels", "", 80, new int[] { 4, 11, 6, 5 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1);
	static ArmorMaterial					shackerarmor	= EnumHelper.addArmorMaterial("hackers", "", 60, new int[] { 2, 11, 6, 5 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1);

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
	public static boolean					bzoom;
    public static int						teslasegments;
	public static boolean					goreEnabled;
	public static boolean					stopSelfnukeinSP;
	public static boolean 					freeb83nukes;
	public static boolean					scoreboardenabled;
	public static boolean 					prefillrhodes;
	public static boolean 					altRkey;
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
	public static boolean					rhodesRoundsBase;
	public static boolean					rhodesScaleSpeed;
	public static float						rhodesSpeedScale;
	public static float						rhodesBlockBreak;
	public static boolean					nukedrop = true;
	public static boolean 					elevation = true;
	public static int						nametagrange = 7;
	public static String					bombertype = "b2";
	public static float						nukeScale = 1.0f;
	public static float						shroomScale = 1.0f;
	public static boolean 					antimatterFlash = true;

	public static Block						amario;
	public static Block						aquicksand;
	public static Block						barricade;
	public static Block						tower;
	public static Block						easteregg;
	public static Block						bunker;
	public static Block						smartcamo;
	public static Block						camo1;
	public static Block						camo2;
	public static Block						camo3;
	public static Block						steel;
	public static Block						flag1;
	public static Block						flag2;
	public static Block						flag3;
	public static Block						flag4;
	public static Block						flag5;
	public static Block						flag6;
	public static Block						flag7;
	public static Block						flagbox1;
	public static Block						flagbox5;
	public static Block						flagbox6;
	public static Block						flagbox3;
	public static Block						flagbox4;
	public static Block						flagbox7;
	public static Block						sigmaarmor;
	public static Block						omegaarmor;
	public static Block						weapons;
	public static Block						ammunition;
	public static Block						explosives;
	public static Block						supplies;
	public static Block						mario;
	public static Block						jump;
	public static Block						quicksand;
	public static Block						landmine;
	public static Block						remotecharge;
	public static Block						timedbomb;
	public static Block						flare;
	public static Block						light;
	public static Block						light2;
	public static Block						cycle;
	public static Block						toxicgas;
	public static Block						fshield;
	public static Block						gamestart;
	public static Block						breadbox;
	public static Block						alandmine;
	public static Block						nuclearBomb;
	public static Block						nukeCrateTop;
	public static Block						nukeCrateBottom;
	public static Block						radioactivedirt;
	public static Block						radioactivesand;
	public static Block						plasmaexplosion;
	public static Block						reactor;
	public static Block						loader;
	public static Block						omegaobj;
	public static Block						sigmaobj;
	public static Block						petrifiedwood;
	public static Block						petrifiedstone1;
	public static Block						petrifiedstone2;
	public static Block						petrifiedstone3;
	public static Block						petrifiedstone4;
	public static Block						tsarbombablock;
	public static Block						forcefieldnode;
	public static Block						goreblock;
	public static Block						reactive;
	public static Block						bastion;
	public static Block						conduit;
	public static Block						controller;
	public static Block						mariotrap;
	public static Block						minetrap;
	public static Block						quicksandtrap;
	public static Block						forcefield;
	public static Block						meltdown;
	public static Block						ffreciever;
	public static Block						buildrhodes;
	public static Block						rhodesactivator;
	public static Block						theoreticaltsarbombablock;
	public static Block						antimatterbombblock;
	public static Block						tachyonbombblock;

	public static Item						rpg;
	public static Item						flamethrower;
	public static Item						tesla;
	public static Item						einsten;
	public static Item						rocket;
	public static Item						fuel;
	public static Item						battery;
	public static Item						redrod;
	public static Item						pliers;
	public static Item						armyshovel;
	public static Item						knife;
	public static Item						gasgrenade;
	public static Item						safepill;
	public static Item						expill;
	public static Item						remote;
	public static Item						fuse;
	public static Item						nuclearelement;
	public static Item						hydrod;
	public static Item						plasmacannon;
	public static Item						roddisk;
	public static Item						antenna;
	public static Item						emptyrod;
	public static Item						core1;
	public static Item						core2;
	public static Item						core3;
	public static Item						binoculars;
	public static Item						camera;
	public static Item						b2spirit;
	public static Item						chip;
	public static Item						roda;
    public static Item						trollmask;
	public static Item						hackm202;
	public static Item						seekm202;

	public static Item						orebelhelmet;
	public static Item						orebelchest;
	public static Item						orebelpants;
	public static Item						orebelboots;
	public static Item						onukerhelmet;
	public static Item						onukerchest;
	public static Item						onukerpants;
	public static Item						onukerboots;
	public static Item						ointelhelmet;
	public static Item						ointelchest;
	public static Item						ointelpants;
	public static Item						ointelboots;
	public static Item						ohackerhelmet;
	public static Item						ohackerchest;
	public static Item						ohackerpants;
	public static Item						ohackerboots;
	public static Item						srebelhelmet;
	public static Item						srebelchest;
	public static Item						srebelpants;
	public static Item						srebelboots;
	public static Item						snukerhelmet;
	public static Item						snukerchest;
	public static Item						snukerpants;
	public static Item						snukerboots;
	public static Item						sintelhelmet;
	public static Item						sintelchest;
	public static Item						sintelpants;
	public static Item						sintelboots;
	public static Item						shackerhelmet;
	public static Item						shackerchest;
	public static Item						shackerpants;
	public static Item						shackerboots;

	public static Item						camohat;
	public static Item						camoshirt;
	public static Item						camopants;
	public static Item						camoshoes;
	public static Item						camohat2;
	public static Item						camoshirt2;
	public static Item						camopants2;
	public static Item						camoshoes2;

	public static ResourceLocation			guitrivalrebels;
	public static ResourceLocation			guitbutton;
	public static ResourceLocation			guitspawn;
	public static ResourceLocation			guitclass;
	public static ResourceLocation			guitrebel;
	public static ResourceLocation			guitnuker;
	public static ResourceLocation			guitintel;
	public static ResourceLocation			guithacker;
	public static ResourceLocation			guitnuke;
	public static ResourceLocation			guittsar;
	public static ResourceLocation			guitwarning0;
	public static ResourceLocation			guitwarning1;
	public static ResourceLocation			guitloader;
	public static ResourceLocation			guittokamak;
	public static ResourceLocation			guibinoculars;
	public static ResourceLocation			guibinocularsoverlay;
	public static ResourceLocation			guilaptopnuke;
	public static ResourceLocation			guitesla;
	public static ResourceLocation			guitray;
	public static ResourceLocation			guiflamethrower;
	public static ResourceLocation			guirhodesline;
	public static ResourceLocation			guirhodesout;
	public static ResourceLocation			guirhodeshelp;
	public static ResourceLocation			guicarpet;
	public static ResourceLocation			guitheoreticaltsar;
	public static ResourceLocation			guitantimatterbomb;
	public static ResourceLocation			guitachyonbomb;

	public static ResourceLocation			etdisk0;
	public static ResourceLocation			etdisk1;
	public static ResourceLocation			etdisk2;
	public static ResourceLocation			etdisk3;
	public static ResourceLocation			etrocket;
	public static ResourceLocation			etfire;
	public static ResourceLocation			etflame;
	public static ResourceLocation			etgasgrenade;
	public static ResourceLocation			etknife;
	public static ResourceLocation			etloader;
	public static ResourceLocation			etomegaobj;
	public static ResourceLocation			etsigmaobj;
	public static ResourceLocation			etplasmacannon;
	public static ResourceLocation			ethydrod;
	public static ResourceLocation			etradrod;
	public static ResourceLocation			etredrod;
	public static ResourceLocation			ettroll;
	public static ResourceLocation			etreactor;
	public static ResourceLocation			etlaptop;
	public static ResourceLocation			etubuntu;
	public static ResourceLocation			etscreen;
	public static ResourceLocation			ettsarshell;
	public static ResourceLocation			ettsarfins;
	public static ResourceLocation			ettsarflame;
	public static ResourceLocation			etnuke;
	public static ResourceLocation			etradiation;
	public static ResourceLocation			eteinstenbarrel;
	public static ResourceLocation			eteinstenback;
	public static ResourceLocation			eteinstenhandle;
	public static ResourceLocation			etblood;
	public static ResourceLocation			etgoo;
	public static ResourceLocation			etemptyrod;
    public static ResourceLocation			etrocketlauncherbody;
	public static ResourceLocation			etrocketlauncherhandle;
	public static ResourceLocation			etrocketlaunchertube;
	public static ResourceLocation			etbinoculars;
	public static ResourceLocation			etelectrode;
	public static ResourceLocation			etb83;
	public static ResourceLocation			etb2spirit;
	public static ResourceLocation			etrust;
	public static ResourceLocation			etreciever;
	public static ResourceLocation			ettesla;
	public static ResourceLocation			etbattery;
	public static ResourceLocation			etflamethrower;
	public static ResourceLocation			ettube;
	public static ResourceLocation			etadsdragon;
	public static ResourceLocation			etflamecone;
	public static ResourceLocation			etflameball;
	public static ResourceLocation			etflamebluered;
	public static ResourceLocation			etflameblue;
	public static ResourceLocation			ethack202;
	public static ResourceLocation			etseek202;
	public static ResourceLocation			etrocketseek202;
	public static ResourceLocation			etrocketseekhandle202;
	public static ResourceLocation			etrocketseektube202;
	public static ResourceLocation			ettheoreticaltsarshell1;
	public static ResourceLocation			ettheoreticaltsarshell2;
	public static ResourceLocation			etblacktsar;
	public static ResourceLocation			etwacknuke;
	public static ResourceLocation			ettupolev;
	public static ResourceLocation			etbooster;
	public static ResourceLocation			etflameballgreen;
	public static ResourceLocation			etantimatterbomb;
	public static ResourceLocation			etantimatterblast;
	public static ResourceLocation			ettachyonbomb;

	public static ResourceLocation			btcrate;
	public static ResourceLocation			btnuketop;
	public static ResourceLocation			btnukebottom;
	public static ResourceLocation			btsteel;
	public static ResourceLocation			btsplash1;
	public static ResourceLocation			btsplash2;
	public static ResourceLocation			btsplash3;
	public static ResourceLocation			btsplash4;
	public static ResourceLocation			btsplash5;
	public static ResourceLocation			btsplash6;

	public static ResourceLocation			ittaskb83;

	public static ResourceLocation			banner;

    public static RivalRebelsRenderOverlay	rrro;

	public static boolean					optiFineWarn	= false;
	public static String[] modblacklist;
	public static boolean enforceblacklist = true;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		if (!config.get("Version", "RRVersion", rrversion).getString().equals(rrversion))
		{
			config.save();
			File file = new File("config/rivalrebels.cfg");
			file.delete();
			config = new Configuration(event.getSuggestedConfigurationFile());
			config.load();
			config.get("Version", "RRVersion", rrversion);
		}

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
		bzoom = config.get("misc", "BKeyZoom", true).getBoolean(true);
		teslasegments = config.get("misc", "teslasegments", 2).getInt();
		goreEnabled = config.get("misc", "goreEnabled", true).getBoolean(true);
		stopSelfnukeinSP = config.get("misc", "stopSelfnukeinSinglePlayer", false).getBoolean(false);
		freeb83nukes = config.get("misc", "freeb83nukes", false).getBoolean(false);
		scoreboardenabled = config.get("misc", "scoreboardEnabled", true).getBoolean(true);
		prefillrhodes = config.get("misc", "prefillrhodes", true).getBoolean(true);
		altRkey = config.get("misc", "useFkeyInsteadofRkey", false).getBoolean(false);
		rhodesChance = (config.get("misc", "rhodesInRoundsChance", 0).getInt()) / 100F;
		rhodesRoundsBase = config.get("misc", "rhodesInRoundsBase", true).getBoolean(false);
		rhodesHealth = (config.get("misc", "rhodesHealth", 15000).getInt());
		rhodesNukes = (config.get("misc", "rhodesNukes", 8).getInt());
		rhodesTeams = (config.get("misc", "rhodesTeams", new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}, "Range: 0-15. Repeat the numbers for multiple occurences of the same rhodes. 0:Rhodes 1:Magnesium 2:Arsenic 3:Vanadium 4:Aurum 5:Iodine 6:Iron 7:Astatine 8:Cobalt 9:Strontium 10:Bismuth 11:Zinc 12:Osmium 13:Neon 14:Argent, 15:Wolfram").getIntList());
		rhodesRandom = (config.getFloat("rhodesRandom", "misc", 1, 0, 5, "Multiplies the Rhodes' random ammo bonus. Set to 0 to disable bonus."));
		rhodesRandomSeed = (config.get("misc", "rhodesRandomSeed", 2168344).getInt());
		rhodesFF = config.get("misc", "rhodesFriendlyFire", true).getBoolean(true);
		rhodesAI = config.get("misc", "rhodesAIEnabled", true).getBoolean(true);
		rhodesCC = config.get("misc", "rhodesTeamFriendlyFire", true).getBoolean(true);
		rhodesPromode = config.get("misc", "rhodesPromode", false).getBoolean(false);
		bombertype = config.get("misc", "BomberType", "b2", "For the B2 bomber, set to 'b2', for Warfare Shuttle 'sh', for Tupolev-95 'tu'.").getString();
		objectiveHealth = (config.get("misc", "objectiveHealth", 15000).getInt());
		freeDragonAmmo = config.get("misc", "freeDragonAmmo", false).getBoolean(false);
		bombDamageToRhodes = (config.get("misc", "bombDamageToRhodes", 20).getInt());
		rhodesScaleSpeed = config.get("misc", "rhodesScaleSpeed", false).getBoolean(false);
		rhodesSpeedScale = (float)config.get("misc", "rhodesSpeedScale", 1.0f).getDouble(1.0f);
		rhodesBlockBreak = (float)config.get("misc", "rhodesBlockBreak", 1.0f).getDouble(1.0f);
		nametagrange = (config.get("misc", "nametagrange", 7).getInt());
		if (nametagrange > 7) nametagrange = 7;
		config.addCustomCategoryComment("misc", "Miscellaneous.");

		String tempstring = config.get("modblacklist", "modblacklist", "").getString().toLowerCase(Locale.ROOT);
		modblacklist = tempstring.split(",");
		enforceblacklist = !tempstring.isEmpty();
		config.addCustomCategoryComment("modblacklist", "Write illegal mods in comma separated format. Not case sensitive, use nonplural for more matches eg. hack instead of hacks. Do not put spaces unless intended for matching the spaces in mod names. Example: hack,xray,x-ray,x ray,cheat,fly,wireframe,wire-frame,wire frame");

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
		antimatterFlash = config.get("explosionsize", "antimatterFlash", true).getBoolean(true);
		nukeScale = (float) config.get("explosionsize", "nukeScale", 1f).getDouble(1f);
		shroomScale = (float) config.get("explosionsize", "shroomScale", 1f).getDouble(1f);
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
		if (safemode) limitConfigValues();

		instance = this;
		rralltab = new RivalRebelsTab(rrname, 0);
		rrarmortab = new RivalRebelsTab(rrname, 1);

		registerTextures();
		registerEntities();
		registerGuis();
		registerClientSide(event);
		PacketDispatcher.registerPackets();

		MinecraftForge.EVENT_BUS.register(round = new RivalRebelsRound(spawnradius,teleportDist,objectivedistance));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        round.load(event.getWorld());
    }

	@EventHandler
	public void onWorldUnload(WorldEvent.Unload event) {
        round.save(event.getWorld());
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

	private void registerTextures() {
		guitbutton = new ResourceLocation("rivalrebels:textures/gui/a.png");
		guitspawn = new ResourceLocation("rivalrebels:textures/gui/b.png");
		guitclass = new ResourceLocation("rivalrebels:textures/gui/c.png");
		guitrebel = new ResourceLocation("rivalrebels:textures/gui/d.png");
		guitnuker = new ResourceLocation("rivalrebels:textures/gui/e.png");
		guitintel = new ResourceLocation("rivalrebels:textures/gui/f.png");
		guithacker = new ResourceLocation("rivalrebels:textures/gui/g.png");
		guitrivalrebels = new ResourceLocation("rivalrebels:textures/gui/h.png");
		guitloader = new ResourceLocation("rivalrebels:textures/gui/i.png");
		guitnuke = new ResourceLocation("rivalrebels:textures/gui/j.png");
		guittsar = new ResourceLocation("rivalrebels:textures/gui/k.png");
		guitwarning0 = new ResourceLocation("rivalrebels:textures/gui/l.png");
		guitwarning1 = new ResourceLocation("rivalrebels:textures/gui/m.png");
		guittokamak = new ResourceLocation("rivalrebels:textures/gui/n.png");
		guibinoculars = new ResourceLocation("rivalrebels:textures/gui/o.png");
		guibinocularsoverlay = new ResourceLocation("rivalrebels:textures/gui/p.png");
		guilaptopnuke = new ResourceLocation("rivalrebels:textures/gui/q.png");
		guitesla = new ResourceLocation("rivalrebels:textures/gui/r.png");
		guitray = new ResourceLocation("rivalrebels:textures/gui/s.png");
		guiflamethrower = new ResourceLocation("rivalrebels:textures/gui/u.png");
		guirhodesline = new ResourceLocation("rivalrebels:textures/gui/rhodes-gui_line.png");
		guirhodesout = new ResourceLocation("rivalrebels:textures/gui/rhodes-gui_out.png");
		guirhodeshelp = new ResourceLocation("rivalrebels:textures/gui/rhodes-gui_help.png");
		guicarpet = new ResourceLocation("rivalrebels:textures/gui/v.png");
		guitheoreticaltsar = new ResourceLocation("rivalrebels:textures/gui/w.png");
		guitantimatterbomb = new ResourceLocation("rivalrebels:textures/gui/x.png");
		guitachyonbomb = new ResourceLocation("rivalrebels:textures/gui/y.png");

		etdisk0 = new ResourceLocation("rivalrebels:textures/entity/ba.png");
		etdisk1 = new ResourceLocation("rivalrebels:textures/entity/bb.png");
		etdisk2 = new ResourceLocation("rivalrebels:textures/entity/bc.png");
		etdisk3 = new ResourceLocation("rivalrebels:textures/entity/bd.png");
		etrocket = new ResourceLocation("rivalrebels:textures/entity/az.png");
		etfire = new ResourceLocation("rivalrebels:textures/entity/ar.png");
		etflame = new ResourceLocation("rivalrebels:textures/entity/as.png");
		etgasgrenade = new ResourceLocation("rivalrebels:textures/entity/bf.png");
		etknife = new ResourceLocation("rivalrebels:textures/entity/be.png");
		etloader = new ResourceLocation("rivalrebels:textures/entity/ac.png");
		etomegaobj = new ResourceLocation("rivalrebels:textures/entity/aa.png");
		etsigmaobj = new ResourceLocation("rivalrebels:textures/entity/ab.png");
		etplasmacannon = new ResourceLocation("rivalrebels:textures/entity/ay.png");
		ethydrod = new ResourceLocation("rivalrebels:textures/entity/ao.png");
		etradrod = new ResourceLocation("rivalrebels:textures/entity/an.png");
		etredrod = new ResourceLocation("rivalrebels:textures/entity/ap.png");
		etemptyrod = new ResourceLocation("rivalrebels:textures/entity/aq.png");
		ettroll = new ResourceLocation("rivalrebels:textures/entity/am.png");
		etreactor = new ResourceLocation("rivalrebels:textures/entity/ad.png");
		etlaptop = new ResourceLocation("rivalrebels:textures/entity/ah.png");
		etubuntu = new ResourceLocation("rivalrebels:textures/entity/aj.png");
		etscreen = new ResourceLocation("rivalrebels:textures/entity/ai.png");
		ettsarshell = new ResourceLocation("rivalrebels:textures/entity/af.png");
		ettsarfins = new ResourceLocation("rivalrebels:textures/entity/ag.png");
		ettsarflame = new ResourceLocation("rivalrebels:textures/entity/al.png");
		etnuke = new ResourceLocation("rivalrebels:textures/entity/ae.png");
		etradiation = new ResourceLocation("rivalrebels:textures/entity/ak.png");
		eteinstenbarrel = new ResourceLocation("rivalrebels:textures/entity/av.png");
		eteinstenback = new ResourceLocation("rivalrebels:textures/entity/aw.png");
		eteinstenhandle = new ResourceLocation("rivalrebels:textures/entity/ax.png");
		etblood = new ResourceLocation("rivalrebels:textures/entity/at.png");
		etgoo = new ResourceLocation("rivalrebels:textures/entity/au.png");
		etrocketlauncherbody = new ResourceLocation("rivalrebels:textures/entity/bh.png");
		etrocketlauncherhandle = new ResourceLocation("rivalrebels:textures/entity/bg.png");
		etrocketlaunchertube = new ResourceLocation("rivalrebels:textures/entity/bi.png");
		etbinoculars = new ResourceLocation("rivalrebels:textures/entity/bj.png");
		etelectrode = new ResourceLocation("rivalrebels:textures/entity/bk.png");
		etb83 = new ResourceLocation("rivalrebels:textures/entity/bl.png");
		etb2spirit = new ResourceLocation("rivalrebels:textures/entity/bm.png");
		etrust = new ResourceLocation("rivalrebels:textures/entity/bn.png");
		etreciever = new ResourceLocation("rivalrebels:textures/entity/bo.png");
		ettesla = new ResourceLocation("rivalrebels:textures/entity/bp.png");
		etbattery = new ResourceLocation("rivalrebels:textures/entity/bq.png");
		ettube = new ResourceLocation("rivalrebels:textures/entity/br.png");
		etadsdragon = new ResourceLocation("rivalrebels:textures/entity/bs.png");
		etflamethrower = new ResourceLocation("rivalrebels:textures/entity/bt.png");
		etflamecone = new ResourceLocation("rivalrebels:textures/entity/bu.png");
		etflameball = new ResourceLocation("rivalrebels:textures/entity/bv.png");
		etflameblue = new ResourceLocation("rivalrebels:textures/entity/bw.png");
		etflamebluered = new ResourceLocation("rivalrebels:textures/entity/bx.png");
		ethack202 = new ResourceLocation("rivalrebels:textures/entity/by.png");
		etseek202 = new ResourceLocation("rivalrebels:textures/entity/bz.png");
		etrocketseek202 = new ResourceLocation("rivalrebels:textures/entity/ca.png");
		etrocketseekhandle202 = new ResourceLocation("rivalrebels:textures/entity/cb.png");
		etrocketseektube202 = new ResourceLocation("rivalrebels:textures/entity/cc.png");
		ettheoreticaltsarshell1 = new ResourceLocation("rivalrebels:textures/entity/cd.png");
		ettheoreticaltsarshell2 = new ResourceLocation("rivalrebels:textures/entity/ce.png");
		etblacktsar = new ResourceLocation("rivalrebels:textures/entity/cf.png");
		etwacknuke = new ResourceLocation("rivalrebels:textures/entity/cg.png");
		etflameballgreen = new ResourceLocation("rivalrebels:textures/entity/ch.png");
		etantimatterbomb = new ResourceLocation("rivalrebels:textures/entity/ci.png");
		ettupolev = new ResourceLocation("rivalrebels:textures/entity/tupolev.png");
		etbooster = new ResourceLocation("rivalrebels:textures/entity/booster.png");
		etantimatterblast = new ResourceLocation("rivalrebels:textures/entity/cj.png");
		ettachyonbomb = new ResourceLocation("rivalrebels:textures/entity/ck.png");

		btcrate = new ResourceLocation("rivalrebels:textures/blocks/ah.png");
		btnuketop = new ResourceLocation("rivalrebels:textures/blocks/ay.png");
		btnukebottom = new ResourceLocation("rivalrebels:textures/blocks/ax.png");
		btsteel = new ResourceLocation("rivalrebels:textures/blocks/bx.png");
		btsplash1 = new ResourceLocation("rivalrebels:textures/blocks/br.png");
		btsplash2 = new ResourceLocation("rivalrebels:textures/blocks/bs.png");
		btsplash3 = new ResourceLocation("rivalrebels:textures/blocks/bt.png");
		btsplash4 = new ResourceLocation("rivalrebels:textures/blocks/bu.png");
		btsplash5 = new ResourceLocation("rivalrebels:textures/blocks/bv.png");
		btsplash6 = new ResourceLocation("rivalrebels:textures/blocks/bw.png");

		ittaskb83 = new ResourceLocation("rivalrebels:textures/items/bc.png");
	}

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (event.getSide().isClient()) {
            roddisk.setTileEntityItemStackRenderer(new RodDiskRenderer());
            plasmacannon.setTileEntityItemStackRenderer(new PlasmaCannonRenderer());
            einsten.setTileEntityItemStackRenderer(new AstroBlasterRenderer());
            hydrod.setTileEntityItemStackRenderer(new HydrogenRodRenderer());
            redrod.setTileEntityItemStackRenderer(new RedstoneRodRenderer());
            nuclearelement.setTileEntityItemStackRenderer(new NuclearRodRenderer());
            emptyrod.setTileEntityItemStackRenderer(new EmptyRodRenderer());
            Item.getItemFromBlock(reactor).setTileEntityItemStackRenderer(new ReactorRenderer());
            Item.getItemFromBlock(loader).setTileEntityItemStackRenderer(new LoaderRenderer());
            Item.getItemFromBlock(controller).setTileEntityItemStackRenderer(new LaptopRenderer());
            rpg.setTileEntityItemStackRenderer(new RocketLauncherRenderer());
            binoculars.setTileEntityItemStackRenderer(new BinocularsRenderer());
            tesla.setTileEntityItemStackRenderer(new TeslaRenderer());
            battery.setTileEntityItemStackRenderer(new BatteryRenderer());
            flamethrower.setTileEntityItemStackRenderer(new FlamethrowerRenderer());
            fuel.setTileEntityItemStackRenderer(new GasRenderer());
            roda.setTileEntityItemStackRenderer(new RodaRenderer());
            hackm202.setTileEntityItemStackRenderer(new HackRocketLauncherRenderer());
            seekm202.setTileEntityItemStackRenderer(new SeekRocketLauncherRenderer());
            rocket.setTileEntityItemStackRenderer(new RocketRenderer());
        }
    }

	private void registerClientSide(FMLPreInitializationEvent event) {
		if (event.getSide().isClient()) {
            OBJLoader.INSTANCE.addDomain(MODID);
			optiFineWarn = FMLClientHandler.instance().hasOptifine();
			ClientProxy.registerRenderInformation();
			MinecraftForge.EVENT_BUS.register(new RivalRebelsSoundEventHandler());
			rrro = new RivalRebelsRenderOverlay();
			RenderLibrary rl = new RenderLibrary();
			rl.init();
			MinecraftForge.EVENT_BUS.register(rrro);
		}
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandResetGame());
        event.registerServerCommand(new CommandPassword());
        event.registerServerCommand(new CommandKillme());
        event.registerServerCommand(new CommandPlaySound());
        event.registerServerCommand(new CommandStopRounds());
        event.registerServerCommand(new CommandContinueRound());
        event.registerServerCommand(new CommandMotD());
        event.registerServerCommand(new CommandKillAll());
        event.registerServerCommand(new CommandRobot());
        event.registerServerCommand(new CommandConfig());
        event.registerServerCommand(new CommandHotPotato());
    }

    private static final Set<Item> blockItems = new HashSet<>();

    @SubscribeEvent
    public void registerBlockEvent(RegistryEvent.Register<Block> register) {
        IForgeRegistry<Block> registry = register.getRegistry();
		amario = (new BlockMario()).setCreativeTab(rralltab).setHardness(1.5F).setResistance(10F);
		aquicksand = (new BlockQuickSand()).setCreativeTab(rralltab).setHardness(0.5F);
		barricade = (new BlockAutoBarricade()).setHardness(0.7F).setCreativeTab(rralltab);
		tower = (new BlockAutoTower()).setHardness(0.7F).setCreativeTab(rralltab);
		easteregg = (new BlockAutoEaster()).setHardness(0.5F).setCreativeTab(rralltab);
		bunker = (new BlockAutoBunker()).setHardness(0.5F).setCreativeTab(rralltab);
		smartcamo = (new BlockSmartCamo()).setCreativeTab(rralltab);
		camo1 = (new BlockCamo1()).setHardness(2F).setResistance(25F).setCreativeTab(rralltab);
		camo2 = (new BlockCamo2()).setHardness(2F).setResistance(25F).setCreativeTab(rralltab);
		camo3 = (new BlockCamo3()).setHardness(2F).setResistance(25F).setCreativeTab(rralltab);
		steel = (new BlockSteel()).setHardness(1.0F).setResistance(10F).setCreativeTab(rralltab);
		flag1 = (new BlockFlag("bi"));
		flag2 = (new BlockFlag("dd"));
		flag3 = (new BlockFlag("ar"));
		flag4 = (new BlockFlag("aw"));
		flag5 = (new BlockFlag("aq"));
		flag6 = (new BlockFlag("al"));
		flag7 = (new BlockFlag("aj"));
		flagbox1 = (new BlockFlagBox1()).setBlockUnbreakable().setCreativeTab(rrarmortab);
		flagbox5 = (new BlockFlagBox5()).setBlockUnbreakable().setCreativeTab(rrarmortab);
		flagbox6 = (new BlockFlagBox6()).setBlockUnbreakable().setCreativeTab(rrarmortab);
		flagbox3 = (new BlockFlagBox3()).setBlockUnbreakable().setCreativeTab(rrarmortab);
		flagbox4 = (new BlockFlagBox4()).setBlockUnbreakable().setCreativeTab(rrarmortab);
		flagbox7 = (new BlockFlagBox7()).setBlockUnbreakable().setCreativeTab(rrarmortab);
		sigmaarmor = (new BlockSigmaArmor()).setHardness(0.5F).setResistance(10F).setCreativeTab(rrarmortab);
		omegaarmor = (new BlockOmegaArmor()).setHardness(0.5F).setResistance(10F).setCreativeTab(rrarmortab);
		weapons = (new BlockWeapons()).setHardness(0.5F).setResistance(10F).setCreativeTab(rralltab);
		ammunition = (new BlockAmmunition()).setHardness(0.5F).setResistance(10F).setCreativeTab(rralltab);
		explosives = (new BlockExplosives()).setHardness(0.5F).setResistance(10F).setCreativeTab(rralltab);
		supplies = (new BlockSupplies()).setHardness(0.5F).setResistance(10F).setCreativeTab(rralltab);
		mario = (new BlockMario()).setHardness(1.5F).setResistance(10F);
		jump = (new BlockJump()).setHardness(2.0F).setResistance(5F).setCreativeTab(rralltab);
		quicksand = (new BlockQuickSand()).setHardness(0.5F);
		landmine = (new BlockLandMine()).setResistance(1F).setHardness(0.6F);
		remotecharge = (new BlockRemoteCharge()).setHardness(0.5F).setCreativeTab(rralltab);
		timedbomb = (new BlockTimedBomb()).setCreativeTab(rralltab);
		flare = (new BlockFlare()).setLightLevel(0.5F).setCreativeTab(rralltab);
		light = (new BlockLight(23)).setLightLevel(1.0F);
		light2 = (new BlockLight(-1)).setLightLevel(1.0F);
		cycle = (new BlockCycle()).setCreativeTab(rrarmortab);
		toxicgas = (new BlockToxicGas()).setBlockUnbreakable();
		fshield = (new BlockForceShield()).setHardness(0.2F).setResistance(10F).setLightOpacity(0).setLightLevel(0.25F).setCreativeTab(rralltab);
		gamestart = (new BlockGameStart()).setCreativeTab(rralltab);
		breadbox = (new BlockBreadBox()).setCreativeTab(rralltab).setBlockUnbreakable();
		alandmine = (new BlockLandMine()).setCreativeTab(rralltab).setResistance(1F).setHardness(0.6F);
		nuclearBomb = (new BlockNuclearBomb()).setHardness(5.0F);
		nukeCrateTop = (new BlockNukeCrate()).setCreativeTab(rralltab).setHardness(0.5F);
		nukeCrateBottom = (new BlockNukeCrate()).setCreativeTab(rralltab).setHardness(0.5F);
		radioactivedirt = (new BlockRadioactiveDirt()).setCreativeTab(rralltab).setHardness(0.5F);
		radioactivesand = (new BlockRadioactiveSand()).setCreativeTab(rralltab).setHardness(0.5F);
		plasmaexplosion = (new BlockPlasmaExplosion()).setLightLevel(1.0F);
		reactor = (new BlockReactor()).setHardness(1.0F).setResistance(10F).setLightLevel(1.0F);
		loader = (new BlockLoader()).setHardness(2.0F).setResistance(10F).setCreativeTab(rralltab);
		omegaobj = (new BlockOmegaObjective()).setLightLevel(1.0F).setHardness(1.0F).setResistance(10F).setCreativeTab(rralltab);
		sigmaobj = (new BlockSigmaObjective()).setLightLevel(1.0F).setHardness(1.0F).setResistance(10F).setCreativeTab(rralltab);
		petrifiedwood = (new BlockPetrifiedWood()).setHardness(1.25F).setResistance(10F).setCreativeTab(rralltab);
		petrifiedstone1 = (new BlockPetrifiedStone("c")).setHardness(1.5F).setResistance(10F).setCreativeTab(rralltab);
		petrifiedstone2 = (new BlockPetrifiedStone("d")).setHardness(1.5F).setResistance(10F).setCreativeTab(rralltab);
		petrifiedstone3 = (new BlockPetrifiedStone("e")).setHardness(1.5F).setResistance(10F).setCreativeTab(rralltab);
		petrifiedstone4 = (new BlockPetrifiedStone("f")).setHardness(1.5F).setResistance(10F).setCreativeTab(rralltab);
		tsarbombablock = (new BlockTsarBomba()).setHardness(5.0F);
		forcefieldnode = (new BlockForceFieldNode()).setHardness(5.0F).setCreativeTab(rralltab);
		goreblock = (new BlockGore()).setHardness(0.0F).setCreativeTab(rrarmortab);
		reactive = (new BlockReactive()).setHardness(2F).setResistance(100F).setCreativeTab(rralltab);
		bastion = (new BlockAutoForceField()).setHardness(0.5F).setResistance(10F).setCreativeTab(rralltab);
		conduit = (new BlockConduit()).setHardness(0.5F).setResistance(10F).setCreativeTab(rralltab);
		controller = (new BlockLaptop()).setHardness(0.3F).setResistance(1F).setCreativeTab(rralltab);
		mariotrap = (new BlockAutoMarioTrap()).setHardness(0.5F).setResistance(10F).setCreativeTab(rralltab);
		minetrap = (new BlockAutoMineTrap()).setHardness(0.5F).setResistance(10F).setCreativeTab(rralltab);
		quicksandtrap = (new BlockAutoQuickSandTrap()).setHardness(0.5F).setResistance(10F).setCreativeTab(rralltab);
		forcefield = (new BlockForceField()).setHardness(1000000F).setResistance(1000000F).setLightLevel(0.5F);
		meltdown = (new BlockMeltDown()).setHardness(0.5F).setResistance(10F);
		ffreciever = (new BlockReciever()).setHardness(2F).setResistance(100F).setCreativeTab(rralltab);
		buildrhodes = (new BlockRhodesScaffold()).setHardness(2F).setResistance(100F).setCreativeTab(rralltab);
		rhodesactivator = (new BlockRhodesActivator()).setHardness(0.1F).setResistance(100F).setCreativeTab(rralltab);
		theoreticaltsarbombablock = (new BlockTheoreticalTsarBomba()).setHardness(5.0F).setLightLevel(0.4F);
		antimatterbombblock = (new BlockAntimatterBomb()).setHardness(5.0F).setLightLevel(0.4F);
		tachyonbombblock = (new BlockTachyonBomb()).setHardness(5.0F).setLightLevel(0.4F);

        BiConsumer<Block, String> consumer = (block, string) -> {
            block.setRegistryName(MODID, string);
            block.setTranslationKey(MODID + "." + string);
            registry.register(block);
            ItemBlock item = new ItemBlock(block);
            item.setRegistryName(MODID, string);
            blockItems.add(item);
        };
		consumer.accept(amario, "amario");
		consumer.accept(aquicksand, "aquicksand");
		consumer.accept(barricade, "barricade");
		consumer.accept(tower, "tower");
		consumer.accept(easteregg, "easteregg");
		consumer.accept(bunker, "bunker");
		consumer.accept(flag1, "flag1");
		consumer.accept(flag2, "flag2");
		consumer.accept(flag3, "flag3");
		consumer.accept(flag4, "flag4");
		consumer.accept(flag5, "flag5");
		consumer.accept(flag6, "flag6");
		consumer.accept(flag7, "flag7");
		consumer.accept(flagbox1, "flagbox1");
		consumer.accept(flagbox5, "flagbox5");
		consumer.accept(flagbox6, "flagbox6");
		consumer.accept(flagbox3, "flagbox3");
		consumer.accept(flagbox4, "flagbox4");
		consumer.accept(flagbox7, "flagbox7");
		consumer.accept(smartcamo, "smartcamo");
		consumer.accept(camo1, "camo1");
		consumer.accept(camo2, "camo2");
		consumer.accept(camo3, "camo3");
		consumer.accept(steel, "steel");
		consumer.accept(sigmaarmor, "sigmaarmor");
		consumer.accept(omegaarmor, "omegaarmor");
		consumer.accept(weapons, "weapons");
		consumer.accept(ammunition, "ammunition");
		consumer.accept(explosives, "explosives");
		consumer.accept(supplies, "supplies");
		consumer.accept(mario, "mario");
		consumer.accept(jump, "jump");
		consumer.accept(quicksand, "quicksand");
		consumer.accept(landmine, "landmine");
		consumer.accept(remotecharge, "remotecharge");
		consumer.accept(timedbomb, "timedbomb");
		consumer.accept(flare, "flare");
		consumer.accept(light, "light");
		consumer.accept(cycle, "cycle");
		consumer.accept(light2, "light2");
		consumer.accept(toxicgas, "toxicgas");
		consumer.accept(fshield, "fshield");
		consumer.accept(gamestart, "gamestart");
		consumer.accept(breadbox, "breadbox");
		consumer.accept(alandmine, "alandmine");
		consumer.accept(nuclearBomb, "nuclearBomb" );
		consumer.accept(nukeCrateTop, "nukeCrateTop");
		consumer.accept(nukeCrateBottom, "nukeCrateBottom");
		consumer.accept(radioactivedirt, "radioactivedirt");
		consumer.accept(radioactivesand, "radioactivesand");
		consumer.accept(plasmaexplosion, "plasmaexplosion");
		consumer.accept(reactor, "reactor");
		consumer.accept(loader, "loader");
		consumer.accept(omegaobj, "omegaobj");
		consumer.accept(sigmaobj, "sigmaobj");
		consumer.accept(petrifiedwood, "petrifiedwood");
		consumer.accept(petrifiedstone1, "petrifiedstone1");
		consumer.accept(petrifiedstone2, "petrifiedstone2");
		consumer.accept(petrifiedstone3, "petrifiedstone3");
		consumer.accept(petrifiedstone4, "petrifiedstone4");
		consumer.accept(tsarbombablock, "tsarbombablock");
		consumer.accept(forcefieldnode, "forcefieldnode");
		//consumer.accept(goreblock, "goreblock");
		consumer.accept(reactive, "reactive");
		consumer.accept(ffreciever, "ffreciever");
		consumer.accept(bastion, "bastion");
		consumer.accept(conduit, "conduit");
		consumer.accept(controller, "controller");
		consumer.accept(mariotrap, "mariotrap");
		consumer.accept(minetrap, "minetrap");
		consumer.accept(quicksandtrap, "quicksandtrap");
		consumer.accept(forcefield, "forcefield");
		consumer.accept(meltdown, "meltdown");
		consumer.accept(buildrhodes, "buildrhodes");
		consumer.accept(rhodesactivator, "rhodesactivator");
		consumer.accept(theoreticaltsarbombablock, "theoreticaltsarbombablock");
		consumer.accept(antimatterbombblock, "antimatterbombblock");
		consumer.accept(tachyonbombblock, "tachyonbombblock");
	}

    @SubscribeEvent
    public void registerBlockColor(ColorHandlerEvent.Block event) {
        BlockColors blockColors = event.getBlockColors();
        blockColors.registerBlockColorHandler((state, worldIn, pos, tintIndex) -> {
            BlockCycle block = (BlockCycle) state.getBlock();
            block.phase += block.phaseadd;
            int r = (int) ((Math.sin(block.phase + block.pShiftR) + 1f) * 128f);
            int g = (int) ((Math.sin(block.phase + block.pShiftG) + 1f) * 128f);
            int b = (int) ((Math.sin(block.phase + block.pShiftB) + 1f) * 128f);
            return (r & 0xff) << 16 | (g & 0xff) << 8 | b & 0xff;
        }, cycle);
        blockColors.registerBlockColorHandler((state, worldIn, pos, tintIndex) -> state.getValue(BlockPetrifiedStone.META) * 1118481, petrifiedstone1, petrifiedstone2, petrifiedstone3, petrifiedstone4);
        blockColors.registerBlockColorHandler((state, worldIn, pos, tintIndex) -> state.getValue(BlockPetrifiedWood.META) * 1118481, petrifiedwood);
        blockColors.registerBlockColorHandler((state, worldIn, pos, tintIndex) -> blockColors.getColor(Blocks.GRASS.getDefaultState(), Minecraft.getMinecraft().world, pos), mario, landmine, quicksand);
    }

    @SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipe> register) {
        RivalRebelsRecipes.registerRecipes(register.getRegistry());
    }

    @SubscribeEvent
    public void registerItemEvent(RegistryEvent.Register<Item> register) {
        IForgeRegistry<Item> registry = register.getRegistry();
        rpg = (new ItemRPG());
		flamethrower = (new ItemFlameThrower());
		tesla = (new ItemTesla());
		rocket = (new ItemRocket());
		fuel = (new ItemFuel());
		battery = (new ItemBattery());
		pliers = (new ItemPliers());
		armyshovel = (new ItemArmyShovel());
		knife = (new ItemCuchillo());
		gasgrenade = (new ItemGasGrenade());
		safepill = (new ItemSafePill());
		expill = (new ItemExPill());
		remote = (new ItemRemote());
		fuse = (new ItemFuse());
		nuclearelement = (new ItemRodNuclear());
		hydrod = (new ItemRodHydrogen());
		plasmacannon = (new ItemPlasmaCannon());
		roddisk = (new ItemRodDisk());
		antenna = (new ItemAntenna());
		einsten = (new ItemAstroBlaster());
		redrod = (new ItemRodRedstone());
		emptyrod = (new ItemRodEmpty());
		core1 = (new ItemCoreCopper());
		core2 = (new ItemCoreTungsten());
		core3 = (new ItemCoreTitanium());
		binoculars = (new ItemBinoculars());
		chip = (new ItemChip());
		roda = (new ItemRoda());
		trollmask = (new ItemTrollHelmet());
		hackm202 = (new ItemHackM202());
		seekm202 = (new ItemSeekM202());
		camera = (new ItemCamera());

        BiConsumer<Item, String> consumer = (item, string) -> {
            item.setRegistryName(MODID, string);
            item.setTranslationKey(MODID + "." + string);
            registry.register(item);
        };

		consumer.accept(rpg, "rpg");
		consumer.accept(flamethrower, "flamethrower");
		consumer.accept(tesla, "tesla");
		consumer.accept(rocket, "rocket");
		consumer.accept(fuel, "fuel");
		consumer.accept(battery, "battery");
		consumer.accept(pliers, "pliers");
		consumer.accept(armyshovel, "armyshovel");
		consumer.accept(knife, "knife");
		consumer.accept(gasgrenade, "gasgrenade");
		consumer.accept(safepill, "safepill");
		consumer.accept(expill, "expill");
		consumer.accept(remote, "remote");
		consumer.accept(fuse, "fuse");
		consumer.accept(nuclearelement, "nuclearrod");
		consumer.accept(hydrod, "hydrod");
		consumer.accept(plasmacannon, "plasmacannon");
		consumer.accept(roddisk, "roddisk");
		consumer.accept(antenna, "antenna");
		consumer.accept(einsten, "einsten");
		consumer.accept(redrod, "redrod");
		consumer.accept(emptyrod, "emptyrod");
		consumer.accept(binoculars, "binoculars");
		consumer.accept(core1, "core1");
		consumer.accept(core2, "core2");
		consumer.accept(core3, "core3");
		consumer.accept(chip, "chip");
		consumer.accept(roda, "roda");
		consumer.accept(trollmask, "trollmask");
		consumer.accept(hackm202, "hackm202");
		consumer.accept(seekm202, "seekm202");
		consumer.accept(camera, "camera");

        blockItems.forEach(registry::register);

        orebelhelmet = (new ItemClassArmor(orebelarmor, EntityEquipmentSlot.HEAD, 0, 0));
		orebelchest = (new ItemClassArmor(orebelarmor, EntityEquipmentSlot.CHEST, 0, 0));
		orebelpants = (new ItemClassArmor(orebelarmor, EntityEquipmentSlot.LEGS, 0, 0));
		orebelboots = (new ItemClassArmor(orebelarmor, EntityEquipmentSlot.FEET, 0, 0));
		onukerhelmet = (new ItemClassArmor(onukerarmor, EntityEquipmentSlot.HEAD, 0, 1));
		onukerchest = (new ItemClassArmor(onukerarmor, EntityEquipmentSlot.CHEST, 0, 1));
		onukerpants = (new ItemClassArmor(onukerarmor, EntityEquipmentSlot.LEGS, 0, 1));
		onukerboots = (new ItemClassArmor(onukerarmor, EntityEquipmentSlot.FEET, 0, 1));
		ointelhelmet = (new ItemClassArmor(ointelarmor, EntityEquipmentSlot.HEAD, 0, 2));
		ointelchest = (new ItemClassArmor(ointelarmor, EntityEquipmentSlot.CHEST, 0, 2));
		ointelpants = (new ItemClassArmor(ointelarmor, EntityEquipmentSlot.LEGS, 0, 2));
		ointelboots = (new ItemClassArmor(ointelarmor, EntityEquipmentSlot.FEET, 0, 2));
		ohackerhelmet = (new ItemClassArmor(ohackerarmor, EntityEquipmentSlot.HEAD, 0, 3));
		ohackerchest = (new ItemClassArmor(ohackerarmor, EntityEquipmentSlot.CHEST, 0, 3));
		ohackerpants = (new ItemClassArmor(ohackerarmor, EntityEquipmentSlot.LEGS, 0, 3));
		ohackerboots = (new ItemClassArmor(ohackerarmor, EntityEquipmentSlot.FEET, 0, 3));
		srebelhelmet = (new ItemClassArmor(srebelarmor, EntityEquipmentSlot.HEAD, 1, 0));
		srebelchest = (new ItemClassArmor(srebelarmor, EntityEquipmentSlot.CHEST, 1, 0));
		srebelpants = (new ItemClassArmor(srebelarmor, EntityEquipmentSlot.LEGS, 1, 0));
		srebelboots = (new ItemClassArmor(srebelarmor, EntityEquipmentSlot.FEET, 1, 0));
		snukerhelmet = (new ItemClassArmor(snukerarmor, EntityEquipmentSlot.HEAD, 1, 1));
		snukerchest = (new ItemClassArmor(snukerarmor, EntityEquipmentSlot.CHEST, 1, 1));
		snukerpants = (new ItemClassArmor(snukerarmor, EntityEquipmentSlot.LEGS, 1, 1));
		snukerboots = (new ItemClassArmor(snukerarmor, EntityEquipmentSlot.FEET, 1, 1));
		sintelhelmet = (new ItemClassArmor(sintelarmor, EntityEquipmentSlot.HEAD, 1, 2));
		sintelchest = (new ItemClassArmor(sintelarmor, EntityEquipmentSlot.CHEST, 1, 2));
		sintelpants = (new ItemClassArmor(sintelarmor, EntityEquipmentSlot.LEGS, 1, 2));
		sintelboots = (new ItemClassArmor(sintelarmor, EntityEquipmentSlot.FEET, 1, 2));
		shackerhelmet = (new ItemClassArmor(shackerarmor, EntityEquipmentSlot.HEAD, 1, 3));
		shackerchest = (new ItemClassArmor(shackerarmor, EntityEquipmentSlot.CHEST, 1, 3));
		shackerpants = (new ItemClassArmor(shackerarmor, EntityEquipmentSlot.LEGS, 1, 3));
		shackerboots = (new ItemClassArmor(shackerarmor, EntityEquipmentSlot.FEET, 1, 3));

		camohat = (new ItemArmorCamo(armorCamo, EntityEquipmentSlot.HEAD, 0));
		camoshirt = (new ItemArmorCamo(armorCamo, EntityEquipmentSlot.CHEST, 0));
		camopants = (new ItemArmorCamo(armorCamo, EntityEquipmentSlot.LEGS, 0));
		camoshoes = (new ItemArmorCamo(armorCamo, EntityEquipmentSlot.FEET, 0));
		camohat2 = (new ItemArmorCamo(armorCamo2, EntityEquipmentSlot.HEAD, 1));
		camoshirt2 = (new ItemArmorCamo(armorCamo2, EntityEquipmentSlot.CHEST, 1));
		camopants2 = (new ItemArmorCamo(armorCamo2, EntityEquipmentSlot.LEGS, 1));
		camoshoes2 = (new ItemArmorCamo(armorCamo2, EntityEquipmentSlot.FEET, 1));

        consumer.accept(orebelhelmet, "orebelhelmet");
		consumer.accept(orebelchest, "orebelchest");
		consumer.accept(orebelpants, "orebelpants");
		consumer.accept(orebelboots, "orebelboots");
		consumer.accept(onukerhelmet, "onukerhelmet");
		consumer.accept(onukerchest, "onukerchest");
		consumer.accept(onukerpants, "onukerpants");
		consumer.accept(onukerboots, "onukerboots");
		consumer.accept(ointelhelmet, "ointelhelmet");
		consumer.accept(ointelchest, "ointelchest");
		consumer.accept(ointelpants, "ointelpants");
		consumer.accept(ointelboots, "ointelboots");
		consumer.accept(ohackerhelmet, "ohackerhelmet");
		consumer.accept(ohackerchest, "ohackerchest");
		consumer.accept(ohackerpants, "ohackerpants");
		consumer.accept(ohackerboots, "ohackerboots");
		consumer.accept(srebelhelmet, "srebelhelmet");
		consumer.accept(srebelchest, "srebelchest");
		consumer.accept(srebelpants, "srebelpants");
		consumer.accept(srebelboots, "srebelboots");
		consumer.accept(snukerhelmet, "snukerhelmet");
		consumer.accept(snukerchest, "snukerchest");
		consumer.accept(snukerpants, "snukerpants");
		consumer.accept(snukerboots, "snukerboots");
		consumer.accept(sintelhelmet, "sintelhelmet");
		consumer.accept(sintelchest, "sintelchest");
		consumer.accept(sintelpants, "sintelpants");
		consumer.accept(sintelboots, "sintelboots");
		consumer.accept(shackerhelmet, "shackerhelmet");
		consumer.accept(shackerchest, "shackerchest");
		consumer.accept(shackerpants, "shackerpants");
		consumer.accept(shackerboots, "shackerboots");
		consumer.accept(camohat, "camohat");
		consumer.accept(camoshirt, "camoshirt");
		consumer.accept(camopants, "camopants");
		consumer.accept(camoshoes, "camoshoes");
		consumer.accept(camohat2, "camohat2");
		consumer.accept(camoshirt2, "camoshirt2");
		consumer.accept(camopants2, "camopants2");
		consumer.accept(camoshoes2, "camoshoes2");
	}

	private void registerEntities()
	{
        GameRegistry.registerTileEntity(TileEntityNukeCrate.class, new ResourceLocation(MODID, "nuke_crate"));
		GameRegistry.registerTileEntity(TileEntityJumpBlock.class, new ResourceLocation(MODID, "jump_block"));
		GameRegistry.registerTileEntity(TileEntityNuclearBomb.class, new ResourceLocation(MODID, "nuclear_bomb"));
		GameRegistry.registerTileEntity(TileEntityPlasmaExplosion.class, new ResourceLocation(MODID, "plasma_explosion"));
		GameRegistry.registerTileEntity(TileEntityReactor.class, new ResourceLocation(MODID, "reactor"));
		GameRegistry.registerTileEntity(TileEntityLoader.class, new ResourceLocation(MODID, "loader"));
		GameRegistry.registerTileEntity(TileEntityOmegaObjective.class, new ResourceLocation(MODID, "omega_objective"));
		GameRegistry.registerTileEntity(TileEntitySigmaObjective.class, new ResourceLocation(MODID, "sigma_objective"));
		GameRegistry.registerTileEntity(TileEntityTsarBomba.class, new ResourceLocation(MODID, "tsar_bomba"));
		GameRegistry.registerTileEntity(TileEntityForceFieldNode.class, new ResourceLocation(MODID, "force_field_node"));
		GameRegistry.registerTileEntity(TileEntityGore.class, new ResourceLocation(MODID, "gore"));
		GameRegistry.registerTileEntity(TileEntityLaptop.class, new ResourceLocation(MODID, "laptop"));
		GameRegistry.registerTileEntity(TileEntityReciever.class, new ResourceLocation(MODID, "reciever"));
		GameRegistry.registerTileEntity(TileEntityReactive.class, new ResourceLocation(MODID, "reactive"));
		GameRegistry.registerTileEntity(TileEntityMeltDown.class, new ResourceLocation(MODID, "meltdown"));
		GameRegistry.registerTileEntity(TileEntityRhodesActivator.class, new ResourceLocation(MODID, "rhodes_activator"));
		GameRegistry.registerTileEntity(TileEntityTheoreticalTsarBomba.class, new ResourceLocation(MODID, "theoretical_tsar_bomba"));
		GameRegistry.registerTileEntity(TileEntityAntimatterBomb.class, new ResourceLocation(MODID, "antimatter_bomb"));
		GameRegistry.registerTileEntity(TileEntityTachyonBomb.class, new ResourceLocation(MODID, "tachyon_bomb"));
        int nextNum = -1;
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "gas_grenade"), EntityGasGrenade.class, "gas_grenade", ++nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "cuchillo"), EntityCuchillo.class, "cuchillo", ++nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "propulsion_fx"), EntityPropulsionFX.class, "propulsion_fx", ++nextNum, this, 250, 9, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "passive_fire"), EntityPassiveFire.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 9, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "rocket"), EntityRocket.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "plasmoid"), EntityPlasmoid.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 400, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "raytrace"), EntityRaytrace.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "lightning_link"), EntityLightningLink.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "nuclear_blast"), EntityNuclearBlast.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "flameball"), EntityFlameBall.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "flameball_1"), EntityFlameBall1.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "flameball_2"), EntityFlameBall2.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "laptop"), EntityLaptop.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "roddisk_regular"), EntityRoddiskRegular.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "roddisk_rebel"), EntityRoddiskRebel.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "roddisk_officer"), EntityRoddiskOfficer.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "roddisk_leader"), EntityRoddiskLeader.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "tsar_blast"), EntityTsarBlast.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "laser_link"), EntityLaserLink.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "gore"), EntityGore.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "blood"), EntityBlood.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 4, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "goo"), EntityGoo.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 4, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "laser_burst"), EntityLaserBurst.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 400, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "b83"), EntityB83.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "b2_spirit"), EntityB2Spirit.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 400, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "b2_frag"), EntityB2Frag.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "debris"), EntityDebris.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 3, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "hack_b83"), EntityHackB83.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "seek_b83"), EntitySeekB83.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "rhodes"), EntityRhodes.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 400, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "rhodes_head"), EntityRhodesHead.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "rhodes_torso"), EntityRhodesTorso.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "rhodes_left_upper_arm"), EntityRhodesLeftUpperArm.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "rhodes_right_upper_arm"), EntityRhodesRightUpperArm.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "rhodes_left_lower_arm"), EntityRhodesLeftLowerArm.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "rhodes_right_lower_arm"), EntityRhodesRightLowerArm.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "rhodes_left_upper_leg"), EntityRhodesLeftUpperLeg.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "rhodes_right_upper_leg"), EntityRhodesRightUpperLeg.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "rhodes_left_lower_leg"), EntityRhodesLeftLowerLeg.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "rhodes_right_lower_leg"), EntityRhodesRightLowerLeg.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "target"), EntityTarget.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "b83_no_shroom"), EntityB83NoShroom.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "sphere_blast"), EntitySphereBlast.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "nuke"), EntityNuke.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "tsar"), EntityTsar.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "roddiskrep"), EntityRoddiskRep.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "hot_potato"), EntityHotPotato.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "bomb"), EntityBomb.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "theoretical_tsar"), EntityTheoreticalTsar.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "theoretical_tsar_blast"), EntityTheoreticalTsarBlast.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "flame_ball_green"), EntityFlameBallGreen.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "antimatter_bomb"), EntityAntimatterBomb.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "antimatter_bomb_blast"), EntityAntimatterBombBlast.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "tachyon_bomb_blast"), EntityTachyonBombBlast.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(MODID, "tachyon_bomb"), EntityTachyonBomb.class, "rivalrebelsentity" + ++nextNum, nextNum, this, 250, 1, true);
	}

	private void registerGuis()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new RivalRebelsGuiHandler());
	}
}
