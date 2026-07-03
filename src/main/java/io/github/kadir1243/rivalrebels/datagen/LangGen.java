package io.github.kadir1243.rivalrebels.datagen;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource.RRDamageTypes;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import io.github.kadir1243.rivalrebels.common.item.weapon.ItemFlameThrower;
import io.github.kadir1243.rivalrebels.common.round.RivalRebelsClass;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LangGen extends LanguageProvider { // TODO: Add Every Translation to here
    public LangGen(PackOutput dataOutput) {
        super(dataOutput, RRIdentifiers.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(Translations.CREATIVE_TAB, "Rival Rebels");
        addBlock(RRBlocks.radioactivesand, "Radioactive Sand");
        addBlock(RRBlocks.radioactivedirt, "Radioactive Dirt");
        addBlock(RRBlocks.timedbomb, "Timed Bomb");
        addBlock(RRBlocks.barricade, "Barricade");
        addBlock(RRBlocks.supplies, "Supplies");
        addBlock(RRBlocks.loader, "Loader");
        addBlock(RRBlocks.reactive, "Reactive");
        addBlock(RRBlocks.quicksand, "Quicksand");
        addBlock(RRBlocks.aquicksand, "Quicksand");
        addBlock(RRBlocks.quicksandtrap, "Quicksand Trap");
        addBlock(RRBlocks.ammunition, "Ammunition");
        addBlock(RRBlocks.explosives, "Explosives");
        addBlock(RRBlocks.camo1, "Camo");
        addBlock(RRBlocks.camo2, "Camo");
        addBlock(RRBlocks.camo3, "Camo");
        addBlock(RRBlocks.smartcamo, "Smart Camo");
        addBlock(RRBlocks.toxicgas, "Toxic Gas");
        addBlock(RRBlocks.jump, "Jump Block");
        addBlock(RRBlocks.nukeCrateBottom, "Nuke Bottom");
        addBlock(RRBlocks.nukeCrateTop, "Nuke Top");
        addBlock(RRBlocks.weapons, "Weapons");
        addBlock(RRBlocks.tower, "Tower");
        addBlock(RRBlocks.mario, "Mario");
        addBlock(RRBlocks.amario, "Mario");
        addBlock(RRBlocks.mariotrap, "Mario Trap");
        addBlock(RRBlocks.conduit, "Conduit");
        addBlock(RRBlocks.omegaarmor, "Omega Armor");
        addBlock(RRBlocks.omegaobj, "Omega Objective");
        addBlock(RRBlocks.sigmaarmor, "Sigma Armor");
        addBlock(RRBlocks.sigmaobj, "Sigma Objective");
        addBlock(RRBlocks.bunker, "Bunker");
        addBlock(RRBlocks.steel, "Steel");
        addBlock(RRBlocks.petrifiedwood, "Petrified Wood");
        addBlock(RRBlocks.petrifiedstone1, "Scorched Stone");
        addBlock(RRBlocks.petrifiedstone2, "Scorched Stone");
        addBlock(RRBlocks.petrifiedstone3, "Scorched Stone");
        addBlock(RRBlocks.petrifiedstone4, "Scorched Stone");
        addBlock(RRBlocks.controller, "Laptop");
        addBlock(RRBlocks.reactor, "Reactor");
        addBlock(RRBlocks.flare, "Flare");
        addBlock(RRBlocks.buildrhodes, "Rhodes Supply Crate");
        addBlock(RRBlocks.light, "Tick");
        addBlock(RRBlocks.light2, "Light");
        addBlock(RRBlocks.nuclearBomb, "Nuclear Bomb");
        addBlock(RRBlocks.minetrap, "Landmine Trap");
        addBlock(RRBlocks.landmine, "Landmine");
        addBlock(RRBlocks.alandmine, "Landmine");
        addBlock(RRBlocks.plasmaexplosion, "Plasma");
        addBlock(RRBlocks.remotecharge, "Plastic explosives");
        addBlock(RRBlocks.antimatterbombblock, "Antimatter Block");
        addBlock(RRBlocks.bastion, "Bastion");
        addBlock(RRBlocks.gamestart, "Game Starter");
        addBlock(RRBlocks.tsarbombablock, "Tsar Bomb");
        addBlock(RRBlocks.theoreticaltsarbombablock, "Tsar Bomb");
        addBlock(RRBlocks.tachyonbombblock, "Tachyon");
        addBlock(RRBlocks.easteregg, "Easter Egg");
        addBlock(RRBlocks.flag1, "Quake");
        addBlock(RRBlocks.trollFlag, "Troll");
        addBlock(RRBlocks.flag3, "Ghost");
        addBlock(RRBlocks.flag4, "Nuclear");
        addBlock(RRBlocks.flag5, "Gasmask");
        addBlock(RRBlocks.flag6, "Deadmau5");
        addBlock(RRBlocks.flag7, "Creeper");
        addBlock(RRBlocks.flagbox1, "Quake Flags");
        addBlock(RRBlocks.flagbox5, "Gasmask Flags");
        addBlock(RRBlocks.flagbox6, "Deadmau5 Flags");
        addBlock(RRBlocks.flagbox3, "Ghost Flags");
        addBlock(RRBlocks.flagbox4, "Nuclear Flags");
        addBlock(RRBlocks.flagbox7, "Creeper Flags");
        addBlock(RRBlocks.breadbox, "Toaster");
        addBlock(RRBlocks.fshield, "unbreakable");
        addBlock(RRBlocks.cycle, "Spectrum");
        addBlock(RRBlocks.forcefieldnode, "Emitter");
        addBlock(RRBlocks.goreblock, "Gore Block");
        addBlock(RRBlocks.ffreciever, "ADS");
        addBlock(RRBlocks.rhodesactivator, "Assembler");

        addItem(RRItems.knife, "Cuchillo");
        addItem(RRItems.hydrod, "Hydrogen Rod");
        addItem(RRItems.pliers, "Pliers");
        addItem(RRItems.redrod, "Redstone Rod");
        addItem(RRItems.NUCLEAR_ROD, "Nuclear Rod");
        addItem(RRItems.fuse, "Fuse");
        addItem(RRItems.tesla, "Tesla");
        addItem(RRItems.core1, "Cu-Core");
        addItem(RRItems.core2, "W-Core");
        addItem(RRItems.core3, "Ti-Core");
        addItem(RRItems.rocket, "Rocket");
        addItem(RRItems.fuel, "Fuel");
        addItem(RRItems.gasgrenade, "Gas Grenade");
        addItem(RRItems.antenna, "Antenna");
        addItem(RRItems.roddisk, "Rod Disk");
        addItem(RRItems.einsten, "Ein-Sten");
        addItem(RRItems.trollmask, "Troll");
        addItem(RRItems.chip, "Chip");
        addItem(RRItems.flamethrower, "Flamethrower");
        addItem(RRItems.battery, "Battery");
        addItem(RRItems.plasmacannon, "Plasma Cannon");
        addItem(RRItems.hackm202, "M202-Hack");
        addItem(RRItems.emptyrod, "Empty Rod");
        addItem(RRItems.roda, "Roda");
        addItem(RRItems.armyshovel, "Sapper Shovel");
        addItem(RRItems.remote, "Detonator");
        addItem(RRItems.expill, "Nuclear Lemonade");
        addItem(RRItems.safepill, "Lord Vertice Lemonade");
        addItem(RRItems.rpg, "M202-RR");
        addItem(RRItems.seekm202, "Seeker");
        addItem(RRItems.camera, "SLR");
        addItem(RRItems.binoculars, "LTD-RR");

        addDamage(RRDamageTypes.ELECTRICITY, "%1s is Now Electric-Man");
        addDamage(RRDamageTypes.CHARGE, "%1s Charged");

        add(ItemFlameThrower.OUT_OF_FUEL, "Out Of Fuel");
        add(Translations.OVERHEAT_TRANSLATION, "Reactor is Overheating");
        add(Translations.WARNING_TRANSLATION, "WARNING");
        add(Translations.ORDERS_TRANSLATION, "ORDERS");
        add(Translations.STATUS_TRANSLATION, "STATUS");
        add(Translations.DEFUSE_TRANSLATION, "DEFUSE");
        add(Translations.INVENTORY_TRANSLATION, "Inventory");
        add(Translations.USE_PLIERS_TO_BUILD_TRANSLATION, "Use pliers to build.");
        add(Translations.USE_PLIERS_TO_OPEN_TRANSLATION, "Use pliers to open.");
        add(Translations.AMMUNITION, "%s ammunition");
        add(Translations.SHIFT_CLICK, "Shift-Click (Sneak).");
        add(Translations.BOMB_TIMER, "Timer: -%s:%s");
        add(Translations.UNBALANCED_BOMB, "Unbalanced");
        add(Translations.BOMB_MEGATONS, "%s Megatons");
        add(Translations.BOMB_ARMED, "Armed");
        add(Translations.LAPTOP_B2_SPIRIT, "B-2 Spirit");
        add(Translations.BUILDING_TOKAMAK, "Constructing ToKaMaK %s");
        add(Translations.BUILDING, "Constructing %s");
        add(Translations.SPAWN_RESET_WARNING, "Warning: Resetting will clear your inventory");
        add(Translations.RHODES_IS_ARMED, "%s Armed");
        add(Translations.ANTIMATTER_BOMB_CONTAINER_NAME, "Antimatter");
        add(Translations.NEXT_BATTLE_TITLE, "Battle Over");
        add(Translations.NEXT_BATTLE_SUBTITLE, "Next Battle?");
        add(Translations.NEXT_BATTLE_QUESTION, "Are you ready for next battle?");
        add(Translations.NEXT_BATTLE_YES, "Yes");
        add(Translations.NEXT_BATTLE_NO, "No");
        add(Translations.BINOCULARS_WEST, "west");
        add(Translations.BINOCULARS_EAST, "east");
        add(Translations.BINOCULARS_NORTH, "north");
        add(Translations.BINOCULARS_SOUTH, "south");
        add(Translations.BINOCULARS_TARGET, "target acquired");
        add(Translations.BINOCULARS_COORDINATES, "coordinates");
        add(Translations.USE_MESSAGE, "Use");
        add(Translations.SIGMA_WIN_TITLE, "Team Sigma wins");
        add(Translations.SIGMA_WIN_SUBTITLE, "Omega Objective Destroyed");
        add(Translations.OMEGA_WIN_TITLE, "Team Omega wins");
        add(Translations.OMEGA_WIN_SUBTITLE, "Sigma Objective Destroyed");
        add(Translations.TEAM_OMEGA, "Team Omega");
        add(Translations.TEAM_SIGMA, "Team Sigma");
        add(Translations.JOIN_OMEGA, "Omega");
        add(Translations.JOIN_SIGMA, "Sigma");
        add(Translations.SPAWN_RESET, "Reset");
        add(Translations.SELECT_CLASS_TITLE, "Class");
        add(Translations.CLASS_DESCRIPTION, "Description");
        add(Translations.CLASS_READY, "Ready");
        add(Translations.CLASS_NEXT, "Next");
        add(Translations.WARNING_MELTDOWN, "Meltdown");
        add(Translations.CHEMICAL_WEAPON, "Chemical weapon");
        add(Translations.WELCOME_REBEL, "Welcome, rebel!");
        add(Translations.WELCOME_OFFICER, "Welcome, officer!");
        add(Translations.WELCOME_LEADER, "Welcome, leader!");
        add(Translations.WELCOME_REPRESENTATIVE, "Welcome, representative!");
        add(Translations.WELCOME_REGULAR, "nope.");
        add(Translations.ADS_TRAY, "ADS");
        add(Translations.EQUIP_WEAPONS_MESSAGE, "Equip your weapons.");
        add(Translations.ADS_DRAGON, "Dragon");
        add(Translations.CONTROLLER_OUT_OF_RANGE, "Out of Range");
        add(Translations.CONTROLLER_B83, "B-83");
        add(Translations.OPS_KNIFE, "Ops knife");
        add(Translations.REQUIRES, "Requires");
        add(Translations.TACTICAL_NUKE_NAME, "Tactical Nuke");
        add(Translations.PRESS_TO_SELECT_BOMB_TYPE, "Press C to select bomb type");

        add(RRIdentifiers.MODID + ".warning_bomb_will_explode_line_1", "Radiological Alarm.");
        add(RRIdentifiers.MODID + ".warning_bomb_will_explode_line_2", "Nuclear weapon armed.");
        add(RRIdentifiers.MODID + ".warning_bomb_will_explode_line_3", "10 seconds left.");

        add(RivalRebelsClass.NONE.getDescription(), "None");
        add(RivalRebelsClass.REBEL.getDescription(), "Front line hero, heavy warrior, super soldier, half human half war machine, tank. Team value: essential to achieve victory by holding the enemy and protecting the team. Armor: Heavy, long durability. Speed: +1");
        add(RivalRebelsClass.NUKER.getDescription(), "Demolition expert, tactical nuking, heavy technician, mass destruction, booby trapping. Team value: essential to achieve victory by nuking the enemy's objective. Armor: Ultra heavy, medium durability. Speed: Normal");
        add(RivalRebelsClass.INTEL.getDescription(), "Expert in intelligence gathering, sabotage, infiltration, trap making, spying, attacks plotting. Team value: essential to locate targets and disrupt enemy offensives. Armor: Light, medium durability. Speed: +2");
        add(RivalRebelsClass.HACKER.getDescription(), "Tactical defense, cyber spying, sabotage, communications jamming, code breaking, technology expert, machinist. Team value: essential to protect the objective and boost weaponry production. Armor: Ultra light, long durability. Speed: +2");

        add(RivalRebelsClass.NONE.getMiniDescription(), "None");
        add(RivalRebelsClass.REBEL.getMiniDescription(), "Heavy Weapon Specialist");
        add(RivalRebelsClass.NUKER.getMiniDescription(), "Explosives Specialist");
        add(RivalRebelsClass.INTEL.getMiniDescription(), "Special Forces");
        add(RivalRebelsClass.HACKER.getMiniDescription(), "Cyber War Specialist");

        addItem(RRItems.onukerhelmet, "Nuker Helmet O");
        addItem(RRItems.snukerhelmet, "Nuker Helmet S");
        addItem(RRItems.onukerboots, "Nuker Boots O");
        addItem(RRItems.snukerboots, "Nuker Boots S");
        addItem(RRItems.onukerpants, "Nuker Pants O");
        addItem(RRItems.snukerpants, "Nuker Pants S");
        addItem(RRItems.onukerchest, "Nuker Vest O");
        addItem(RRItems.snukerchest, "Nuker Vest S");

        addItem(RRItems.ointelboots, "Intel Boots O");
        addItem(RRItems.sintelboots, "Intel Boots S");
        addItem(RRItems.ointelpants, "Intel Pants O");
        addItem(RRItems.sintelpants, "Intel Pants S");
        addItem(RRItems.ointelchest, "Intel Vest O");
        addItem(RRItems.sintelchest, "Intel Vest S");
        addItem(RRItems.ointelhelmet, "Intel Helmet O");
        addItem(RRItems.sintelhelmet, "Intel Helmet S");

        addItem(RRItems.ohackerboots, "Hacker Boots O");
        addItem(RRItems.shackerboots, "Hacker Boots S");
        addItem(RRItems.ohackerpants, "Hacker Pants O");
        addItem(RRItems.shackerpants, "Hacker Pants S");
        addItem(RRItems.ohackerchest, "Hacker Vest O");
        addItem(RRItems.shackerchest, "Hacker Vest S");
        addItem(RRItems.ohackerhelmet, "Hacker Helmet O");
        addItem(RRItems.shackerhelmet, "Hacker Helmet S");

        addItem(RRItems.orebelboots, "Rebel Boots O");
        addItem(RRItems.srebelboots, "Rebel Boots S");
        addItem(RRItems.orebelpants, "Rebel Pants O");
        addItem(RRItems.srebelpants, "Rebel Pants S");
        addItem(RRItems.orebelchest, "Rebel Vest O");
        addItem(RRItems.srebelchest, "Rebel Vest S");
        addItem(RRItems.orebelhelmet, "Rebel Helmet O");
        addItem(RRItems.srebelhelmet, "Rebel Helmet S");

        addItem(RRItems.camohat, "Helmet");
        addItem(RRItems.camoshirt, "Vest");
        addItem(RRItems.camopants, "Pants");
        addItem(RRItems.camoshoes, "Boots");
        addItem(RRItems.camohat2, "Helmet");
        addItem(RRItems.camoshirt2, "Vest");
        addItem(RRItems.camopants2, "Pants");
        addItem(RRItems.camoshoes2, "Boots");
    }

    public void addDamage(ResourceKey<DamageType> resourceKey, String translation) {
        add("death.attack."+ RRIdentifiers.MODID + "." + resourceKey.identifier().getPath(), translation);
    }

    public void add(Translations.TranslationKey key, String translation) {
        add(key.key(), translation);
    }
}
