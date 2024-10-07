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
        add(Translations.CREATIVE_TAB.toLanguageKey(), "Rival Rebels");
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

        addDamage(RRDamageTypes.ELECTRICITY, "%1s is Now Electric-Man");
        addDamage(RRDamageTypes.CHARGE, "%1s Charged");

        add(ItemFlameThrower.OUT_OF_FUEL.toLanguageKey(), "Out Of Fuel");
        add(Translations.OVERHEAT_TRANSLATION.toLanguageKey(), "Reactor is Overheating");
        add(Translations.WARNING_TRANSLATION.toLanguageKey(), "WARNING");
        add(Translations.ORDERS_TRANSLATION.toLanguageKey(), "ORDERS");
        add(Translations.STATUS_TRANSLATION.toLanguageKey(), "STATUS");
        add(Translations.DEFUSE_TRANSLATION.toLanguageKey(), "DEFUSE");
        add(Translations.USE_PLIERS_TO_BUILD_TRANSLATION.toLanguageKey(), "Use pliers to build.");
        add(Translations.USE_PLIERS_TO_OPEN_TRANSLATION.toLanguageKey(), "Use pliers to open.");
        add(Translations.AMMUNITION_TRANSLATION.toLanguageKey(), "ammunition");
        add(Translations.NUKE_TRANSLATION.toLanguageKey(), "Nuclear Bomb");
        add(Translations.SHIFT_CLICK.toLanguageKey(), "Shift-Click (Sneak).");
        add(Translations.BOMB_TIMER.toLanguageKey(), "Timer");
        add(Translations.UNBALANCED_BOMB.toLanguageKey(), "Unbalanced");
        add(Translations.BOMB_MEGATONS.toLanguageKey(), "Megatons");
        add(Translations.BOMB_ARMED.toLanguageKey(), "Armed");
        add(Translations.LAPTOP_B2_SPIRIT.toLanguageKey(), "B-2 Spirit");
        add(Translations.BUILDING_TOKAMAK.toLanguageKey(), "Constructing ToKaMaK %s");
        add(Translations.BUILDING.toLanguageKey(), "Constructing %s");
        add(Translations.SPAWN_RESET_WARNING.toLanguageKey(), "Warning: Resetting will clear your inventory");
        add(Translations.RHODES_IS_ARMED.toLanguageKey(), "%s Armed");
        add(Translations.TSAR_NAME.toLanguageKey(), "Tsar Bomb");

        add(RRIdentifiers.MODID + ".warning_bomb_will_explode_line_1", "Radiological Alarm.");
        add(RRIdentifiers.MODID + ".warning_bomb_will_explode_line_2", "Nuclear weapon armed.");
        add(RRIdentifiers.MODID + ".warning_bomb_will_explode_line_3", "10 seconds left.");

        add(RRIdentifiers.MODID + ".warning_meltdown", "Meltdown");

        add(RivalRebelsClass.NONE.getDescriptionTranslationKey(), "None");
        add(RivalRebelsClass.REBEL.getDescriptionTranslationKey(), "Front line hero, heavy warrior, super soldier, half human half war machine, tank. Team value: essential to achieve victory by holding the enemy and protecting the team. Armor: Heavy, long durability. Speed: +1");
        add(RivalRebelsClass.NUKER.getDescriptionTranslationKey(), "Demolition expert, tactical nuking, heavy technician, mass destruction, booby trapping. Team value: essential to achieve victory by nuking the enemy's objective. Armor: Ultra heavy, medium durability. Speed: Normal");
        add(RivalRebelsClass.INTEL.getDescriptionTranslationKey(), "Expert in intelligence gathering, sabotage, infiltration, trap making, spying, attacks plotting. Team value: essential to locate targets and disrupt enemy offensives. Armor: Light, medium durability. Speed: +2");
        add(RivalRebelsClass.HACKER.getDescriptionTranslationKey(), "Tactical defense, cyber spying, sabotage, communications jamming, code breaking, technology expert, machinist. Team value: essential to protect the objective and boost weaponry production. Armor: Ultra light, long durability. Speed: +2");

        add(RivalRebelsClass.NONE.getMiniDescriptionTranslationKey(), "None");
        add(RivalRebelsClass.REBEL.getMiniDescriptionTranslationKey(), "Heavy Weapon Specialist");
        add(RivalRebelsClass.NUKER.getMiniDescriptionTranslationKey(), "Explosives Specialist");
        add(RivalRebelsClass.INTEL.getMiniDescriptionTranslationKey(), "Special Forces");
        add(RivalRebelsClass.HACKER.getMiniDescriptionTranslationKey(), "Cyber War Specialist");

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
        add("death.attack."+ RRIdentifiers.MODID + "." + resourceKey.location().getPath(), translation);
    }
}
