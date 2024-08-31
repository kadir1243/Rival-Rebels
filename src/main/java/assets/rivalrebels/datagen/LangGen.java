package assets.rivalrebels.datagen;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.core.RivalRebelsDamageSource.RRDamageTypes;
import assets.rivalrebels.common.item.RRItems;
import assets.rivalrebels.common.item.weapon.ItemFlameThrower;
import assets.rivalrebels.common.round.RivalRebelsClass;
import assets.rivalrebels.common.util.Translations;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

import java.util.concurrent.CompletableFuture;

public class LangGen extends FabricLanguageProvider { // TODO: Add Every Translation to here
    private TranslationBuilder translationBuilder;

    public LangGen(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder lang) {
        this.translationBuilder = lang;

        lang.add(Translations.CREATIVE_TAB, "Rival Rebels");
        lang.add(RRBlocks.radioactivesand, "Radioactive Sand");
        lang.add(RRBlocks.radioactivedirt, "Radioactive Dirt");
        lang.add(RRBlocks.timedbomb, "Timed Bomb");
        lang.add(RRBlocks.barricade, "Barricade");
        lang.add(RRBlocks.supplies, "Supplies");
        lang.add(RRBlocks.loader, "Loader");
        lang.add(RRBlocks.reactive, "Reactive");
        lang.add(RRBlocks.quicksand, "Quicksand");
        lang.add(RRBlocks.aquicksand, "Quicksand");
        lang.add(RRBlocks.quicksandtrap, "Quicksand Trap");
        lang.add(RRBlocks.ammunition, "Ammunition");
        lang.add(RRBlocks.explosives, "Explosives");
        lang.add(RRBlocks.camo1, "Camo");
        lang.add(RRBlocks.camo2, "Camo");
        lang.add(RRBlocks.camo3, "Camo");
        lang.add(RRBlocks.smartcamo, "Smart Camo");
        lang.add(RRBlocks.toxicgas, "Toxic Gas");
        lang.add(RRBlocks.jump, "Jump Block");
        lang.add(RRBlocks.nukeCrateBottom, "Nuke Bottom");
        lang.add(RRBlocks.nukeCrateTop, "Nuke Top");
        lang.add(RRBlocks.weapons, "Weapons");
        lang.add(RRBlocks.tower, "Tower");
        lang.add(RRBlocks.mario, "Mario");
        lang.add(RRBlocks.amario, "Mario");
        lang.add(RRBlocks.mariotrap, "Mario Trap");
        lang.add(RRBlocks.conduit, "Conduit");
        lang.add(RRBlocks.omegaarmor, "Omega Armor");
        lang.add(RRBlocks.omegaobj, "Omega Objective");
        lang.add(RRBlocks.sigmaarmor, "Sigma Armor");
        lang.add(RRBlocks.sigmaobj, "Sigma Objective");
        lang.add(RRBlocks.bunker, "Bunker");
        lang.add(RRBlocks.steel, "Steel");
        lang.add(RRBlocks.petrifiedwood, "Petrified Wood");
        lang.add(RRBlocks.petrifiedstone1, "Scorched Stone");
        lang.add(RRBlocks.petrifiedstone2, "Scorched Stone");
        lang.add(RRBlocks.petrifiedstone3, "Scorched Stone");
        lang.add(RRBlocks.petrifiedstone4, "Scorched Stone");
        lang.add(RRBlocks.controller, "Laptop");
        lang.add(RRBlocks.reactor, "Reactor");
        lang.add(RRBlocks.flare, "Flare");
        lang.add(RRBlocks.buildrhodes, "Rhodes Supply Crate");


        lang.add(RRItems.knife, "Cuchillo");
        lang.add(RRItems.hydrod, "Hydrogen Rod");
        lang.add(RRItems.pliers, "Pliers");
        lang.add(RRItems.redrod, "Redstone Rod");
        lang.add(RRItems.NUCLEAR_ROD, "Nuclear Rod");
        lang.add(RRItems.fuse, "Fuse");
        lang.add(RRItems.tesla, "Tesla");
        lang.add(RRItems.core1, "Cu-Core");
        lang.add(RRItems.core2, "W-Core");
        lang.add(RRItems.core3, "Ti-Core");
        lang.add(RRItems.rocket, "Rocket");
        lang.add(RRItems.fuel, "Fuel");
        lang.add(RRItems.gasgrenade, "Gas Grenade");
        lang.add(RRItems.antenna, "Antenna");
        lang.add(RRItems.roddisk, "Rod Disk");
        lang.add(RRItems.einsten, "Ein-Sten");
        lang.add(RRItems.trollmask, "Troll");
        lang.add(RRItems.chip, "Chip");
        lang.add(RRItems.flamethrower, "Flamethrower");
        lang.add(RRItems.battery, "Battery");
        lang.add(RRItems.plasmacannon, "Plasma Cannon");
        lang.add(RRItems.hackm202, "M202-Hack");
        lang.add(RRItems.emptyrod, "Empty Rod");
        lang.add(RRItems.roda, "Roda");
        lang.add(RRItems.armyshovel, "Sapper Shovel");

        addDamage(RRDamageTypes.ELECTRICITY, "%1s is Now Electric-Man");
        addDamage(RRDamageTypes.CHARGE, "%1s Charged");

        lang.add(ItemFlameThrower.OUT_OF_FUEL, "Out Of Fuel");
        lang.add(Translations.OVERHEAT_TRANSLATION, "Reactor is Overheating");
        lang.add(Translations.WARNING_TRANSLATION, "WARNING");
        lang.add(Translations.ORDERS_TRANSLATION, "ORDERS");
        lang.add(Translations.STATUS_TRANSLATION, "STATUS");
        lang.add(Translations.DEFUSE_TRANSLATION, "DEFUSE");
        lang.add(Translations.USE_PLIERS_TO_BUILD_TRANSLATION, "Use pliers to build.");
        lang.add(Translations.USE_PLIERS_TO_OPEN_TRANSLATION, "Use pliers to open.");
        lang.add(Translations.AMMUNITION_TRANSLATION, "ammunition");
        lang.add(Translations.NUKE_TRANSLATION, "Nuclear Bomb");
        lang.add(Translations.SHIFT_CLICK, "Shift-Click (Sneak).");
        lang.add(Translations.BOMB_TIMER, "Timer");
        lang.add(Translations.UNBALANCED_BOMB, "Unbalanced");
        lang.add(Translations.BOMB_MEGATONS, "Megatons");
        lang.add(Translations.BOMB_ARMED, "Armed");
        lang.add(Translations.LAPTOP_B2_SPIRIT, "B-2 Spirit");
        lang.add(Translations.BUILDING_TOKAMAK, "Constructing ToKaMaK %s");
        lang.add(Translations.BUILDING, "Constructing %s");
        lang.add(Translations.SPAWN_RESET_WARNING, "Warning: Resetting will clear your inventory");
        lang.add(Translations.RHODES_IS_ARMED, "%s Armed");
        lang.add(Translations.TSAR_NAME, "Tsar Bomb");

        lang.add(RRIdentifiers.MODID + ".warning_bomb_will_explode_line_1", "Radiological Alarm.");
        lang.add(RRIdentifiers.MODID + ".warning_bomb_will_explode_line_2", "Nuclear weapon armed.");
        lang.add(RRIdentifiers.MODID + ".warning_bomb_will_explode_line_3", "10 seconds left.");

        lang.add(RRIdentifiers.MODID + ".warning_meltdown", "Meltdown");

        lang.add(RivalRebelsClass.NONE.getDescriptionTranslationKey(), "None");
        lang.add(RivalRebelsClass.REBEL.getDescriptionTranslationKey(), "Front line hero, heavy warrior, super soldier, half human half war machine, tank. Team value: essential to achieve victory by holding the enemy and protecting the team. Armor: Heavy, long durability. Speed: +1");
        lang.add(RivalRebelsClass.NUKER.getDescriptionTranslationKey(), "Demolition expert, tactical nuking, heavy technician, mass destruction, booby trapping. Team value: essential to achieve victory by nuking the enemy's objective. Armor: Ultra heavy, medium durability. Speed: Normal");
        lang.add(RivalRebelsClass.INTEL.getDescriptionTranslationKey(), "Expert in intelligence gathering, sabotage, infiltration, trap making, spying, attacks plotting. Team value: essential to locate targets and disrupt enemy offensives. Armor: Light, medium durability. Speed: +2");
        lang.add(RivalRebelsClass.HACKER.getDescriptionTranslationKey(), "Tactical defense, cyber spying, sabotage, communications jamming, code breaking, technology expert, machinist. Team value: essential to protect the objective and boost weaponry production. Armor: Ultra light, long durability. Speed: +2");

        lang.add(RivalRebelsClass.NONE.getMiniDescriptionTranslationKey(), "None");
        lang.add(RivalRebelsClass.REBEL.getMiniDescriptionTranslationKey(), "Heavy Weapon Specialist");
        lang.add(RivalRebelsClass.NUKER.getMiniDescriptionTranslationKey(), "Explosives Specialist");
        lang.add(RivalRebelsClass.INTEL.getMiniDescriptionTranslationKey(), "Special Forces");
        lang.add(RivalRebelsClass.HACKER.getMiniDescriptionTranslationKey(), "Cyber War Specialist");

        lang.add(RRItems.onukerhelmet, "Nuker Helmet O");
        lang.add(RRItems.snukerhelmet, "Nuker Helmet S");
        lang.add(RRItems.onukerboots, "Nuker Boots O");
        lang.add(RRItems.snukerboots, "Nuker Boots S");
        lang.add(RRItems.onukerpants, "Nuker Pants O");
        lang.add(RRItems.snukerpants, "Nuker Pants S");
        lang.add(RRItems.onukerchest, "Nuker Vest O");
        lang.add(RRItems.snukerchest, "Nuker Vest S");

        lang.add(RRItems.ointelboots, "Intel Boots O");
        lang.add(RRItems.sintelboots, "Intel Boots S");
        lang.add(RRItems.ointelpants, "Intel Pants O");
        lang.add(RRItems.sintelpants, "Intel Pants S");
        lang.add(RRItems.ointelchest, "Intel Vest O");
        lang.add(RRItems.sintelchest, "Intel Vest S");
        lang.add(RRItems.ointelhelmet, "Intel Helmet O");
        lang.add(RRItems.sintelhelmet, "Intel Helmet S");

        lang.add(RRItems.ohackerboots, "Hacker Boots O");
        lang.add(RRItems.shackerboots, "Hacker Boots S");
        lang.add(RRItems.ohackerpants, "Hacker Pants O");
        lang.add(RRItems.shackerpants, "Hacker Pants S");
        lang.add(RRItems.ohackerchest, "Hacker Vest O");
        lang.add(RRItems.shackerchest, "Hacker Vest S");
        lang.add(RRItems.ohackerhelmet, "Hacker Helmet O");
        lang.add(RRItems.shackerhelmet, "Hacker Helmet S");

        lang.add(RRItems.orebelboots, "Rebel Boots O");
        lang.add(RRItems.srebelboots, "Rebel Boots S");
        lang.add(RRItems.orebelpants, "Rebel Pants O");
        lang.add(RRItems.srebelpants, "Rebel Pants S");
        lang.add(RRItems.orebelchest, "Rebel Vest O");
        lang.add(RRItems.srebelchest, "Rebel Vest S");
        lang.add(RRItems.orebelhelmet, "Rebel Helmet O");
        lang.add(RRItems.srebelhelmet, "Rebel Helmet S");

        lang.add(RRItems.camohat, "Helmet");
        lang.add(RRItems.camoshirt, "Vest");
        lang.add(RRItems.camopants, "Pants");
        lang.add(RRItems.camoshoes, "Boots");
        lang.add(RRItems.camohat2, "Helmet");
        lang.add(RRItems.camoshirt2, "Vest");
        lang.add(RRItems.camopants2, "Pants");
        lang.add(RRItems.camoshoes2, "Boots");

    }

    public void addDamage(ResourceKey<DamageType> resourceKey, String translation) {
        translationBuilder.add("death.attack."+ RRIdentifiers.MODID + "." + resourceKey.location().getPath(), translation);
    }
}
