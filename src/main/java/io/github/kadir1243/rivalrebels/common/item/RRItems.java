package io.github.kadir1243.rivalrebels.common.item;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.RivalRebels;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import io.github.kadir1243.rivalrebels.common.item.weapon.*;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.*;

public class RRItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RRIdentifiers.MODID);
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RRIdentifiers.MODID);
    private static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, RRIdentifiers.MODID);

    public static final Holder<ArmorMaterial> orebelarmor = registerMaterial("rebelo", new int[]{6, 18, 6, 6}, 6);
    public static final Holder<ArmorMaterial> onukerarmor = registerMaterial("nukero", new int[]{8, 20, 8, 6}, 2);
    public static final Holder<ArmorMaterial> ointelarmor = registerMaterial("intelo", new int[]{4, 11, 6, 5}, 10);
    public static final Holder<ArmorMaterial> ohackerarmor = registerMaterial("hackero", new int[]{2, 11, 6, 5}, 10);
    public static final Holder<ArmorMaterial> srebelarmor = registerMaterial("rebels", new int[]{6, 18, 6, 6}, 6);
    public static final Holder<ArmorMaterial> snukerarmor = registerMaterial("nukers", new int[]{8, 20, 8, 6}, 2);
    public static final Holder<ArmorMaterial> sintelarmor = registerMaterial("intels", new int[]{4, 11, 6, 5}, 10);
    public static final Holder<ArmorMaterial> shackerarmor = registerMaterial("hackers", new int[]{2, 11, 6, 5}, 10);
    public static final Holder<ArmorMaterial> armorCamo = registerMaterial("camo", new int[]{2, 9, 5, 2}, 10);
    public static final Holder<ArmorMaterial> armorCamo2 = registerMaterial("camo2", new int[]{2, 9, 5, 2}, 10);
    public static final Holder<ArmorMaterial> TROLL_MATERIAL = registerMaterial("troll", new int[] {0, 0, 0, 0}, 1000);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("creative_tab", () -> CreativeModeTab.builder()
        .icon(RRItems.NUCLEAR_ROD::toStack)
        .title(Component.translatable(Translations.CREATIVE_TAB.toLanguageKey()))
        .displayItems((displayContext, entries) -> entries.acceptAll(ITEMS.getEntries().stream().map(DeferredHolder::get).map(Item::getDefaultInstance).toList())
    ).build());
    public static final DeferredItem<Item> rpg = ITEMS.register("rpg", ItemRPG::new);
    public static final DeferredItem<Item> flamethrower = ITEMS.register("flamethrower", ItemFlameThrower::new);
    public static final DeferredItem<Item> tesla = ITEMS.register("tesla", ItemTesla::new);
    public static final DeferredItem<Item> einsten = ITEMS.register("astro_blaster", ItemAstroBlaster::new);
    public static final DeferredItem<Item> rocket = ITEMS.registerSimpleItem("rocket");
    public static final DeferredItem<Item> fuel = ITEMS.registerSimpleItem("fuel");
    public static final DeferredItem<Item> battery = ITEMS.registerSimpleItem("battery");
    public static final DeferredItem<Item> redrod = ITEMS.register("redstone_rod", () -> new Item(new Item.Properties().durability(256).component(RRComponents.ROD_POWER.get(), 300000)));
    public static final DeferredItem<Item> pliers = ITEMS.register("pliers", ItemPliers::new);
    public static final DeferredItem<Item> armyshovel = ITEMS.registerItem("army_shovel", properties -> new DiggerItem(Tiers.DIAMOND, RivalRebels.MINEABLE_WITH_ARMY_SHOVEL, properties), new Item.Properties().component(DataComponents.UNBREAKABLE, new Unbreakable(true)));
    public static final DeferredItem<Item> knife = ITEMS.register("knife", ItemCuchillo::new);
    public static final DeferredItem<Item> gasgrenade = ITEMS.register("gas_grenade", ItemGasGrenade::new);
    public static final DeferredItem<Item> safepill = ITEMS.register("safe_pill", ItemSafePill::new);
    public static final DeferredItem<Item> expill = ITEMS.register("expill", ItemExPill::new);
    public static final DeferredItem<Item> remote = ITEMS.register("remote", ItemRemote::new);
    public static final DeferredItem<Item> fuse = ITEMS.registerSimpleItem("fuse", new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> NUCLEAR_ROD = ITEMS.register("nuclear_rod", () -> new ItemRodNuclear(new Item.Properties().durability(32).component(RRComponents.ROD_POWER, 3000000)));
    public static final DeferredItem<Item> hydrod = ITEMS.register("hydrogen_rod", () -> new Item(new Item.Properties().durability(32).component(RRComponents.ROD_POWER, 250000)));
    public static final DeferredItem<Item> plasmacannon = ITEMS.register("plasma_cannon", ItemPlasmaCannon::new);
    public static final DeferredItem<Item> roddisk = ITEMS.register("rod_disk", ItemRodDisk::new);
    public static final DeferredItem<Item> antenna = ITEMS.registerSimpleItem("antenna", new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> emptyrod = ITEMS.registerSimpleItem("empty_rod");
    public static final DeferredItem<Item> core1 = ITEMS.register("copper_core", () -> new Item(new Item.Properties().component(RRComponents.CORE_TIME_MULTIPLIER, 0.25F).stacksTo(1)));
    public static final DeferredItem<Item> core2 = ITEMS.register("tungsten_core", () -> new Item(new Item.Properties().component(RRComponents.CORE_TIME_MULTIPLIER, 0.75F).stacksTo(1)));
    public static final DeferredItem<Item> core3 = ITEMS.register("titanium_core", () -> new Item(new Item.Properties().component(RRComponents.CORE_TIME_MULTIPLIER, 1F).stacksTo(1)));
    public static final DeferredItem<Item> binoculars = ITEMS.register("binoculars", ItemBinoculars::new);
    public static final DeferredItem<Item> camera = ITEMS.register("camera", ItemCamera::new);
    public static final DeferredItem<Item> chip = ITEMS.register("chip", ItemChip::new);
    public static final DeferredItem<Item> roda = ITEMS.register("roda", ItemRoda::new);
    public static final DeferredItem<Item> trollmask = ITEMS.registerItem("troll_mask", ItemTrollHelmet::new, new Item.Properties().durability(5000));
    public static final DeferredItem<Item> hackm202 = ITEMS.register("hackm202", ItemHackM202::new);
    public static final DeferredItem<Item> seekm202 = ITEMS.register("seekm202", ItemSeekM202::new);
    public static final DeferredItem<Item> orebelhelmet = ITEMS.registerItem("orebelhelmet", p -> new ItemClassArmor(orebelarmor, ArmorItem.Type.HELMET));
    public static final DeferredItem<Item> orebelchest = ITEMS.registerItem("orebelchest", p -> new ItemClassArmor(orebelarmor, ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<Item> orebelpants = ITEMS.registerItem("orebelpants", p -> new ItemClassArmor(orebelarmor, ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<Item> orebelboots = ITEMS.registerItem("orebelboots", p -> new ItemClassArmor(orebelarmor, ArmorItem.Type.BOOTS));
    public static final DeferredItem<Item> onukerhelmet = ITEMS.registerItem("onukerhelmet", p -> new ItemClassArmor(onukerarmor, ArmorItem.Type.HELMET));
    public static final DeferredItem<Item> onukerchest = ITEMS.registerItem("onukerchest", p -> new ItemClassArmor(onukerarmor, ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<Item> onukerpants = ITEMS.registerItem("onukerpants", p -> new ItemClassArmor(onukerarmor, ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<Item> onukerboots = ITEMS.registerItem("onukerboots", p -> new ItemClassArmor(onukerarmor, ArmorItem.Type.BOOTS));
    public static final DeferredItem<Item> ointelhelmet = ITEMS.registerItem("ointelhelmet", p -> new ItemClassArmor(ointelarmor, ArmorItem.Type.HELMET));
    public static final DeferredItem<Item> ointelchest = ITEMS.registerItem("ointelchest", p -> new ItemClassArmor(ointelarmor, ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<Item> ointelpants = ITEMS.registerItem("ointelpants", p -> new ItemClassArmor(ointelarmor, ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<Item> ointelboots = ITEMS.registerItem("ointelboots", p -> new ItemClassArmor(ointelarmor, ArmorItem.Type.BOOTS));
    public static final DeferredItem<Item> ohackerhelmet = ITEMS.registerItem("ohackerhelmet", p -> new ItemClassArmor(ohackerarmor, ArmorItem.Type.HELMET));
    public static final DeferredItem<Item> ohackerchest = ITEMS.registerItem("ohackerchest", p -> new ItemClassArmor(ohackerarmor, ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<Item> ohackerpants = ITEMS.registerItem("ohackerpants", p -> new ItemClassArmor(ohackerarmor, ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<Item> ohackerboots = ITEMS.registerItem("ohackerboots", p -> new ItemClassArmor(ohackerarmor, ArmorItem.Type.BOOTS));
    public static final DeferredItem<Item> srebelhelmet = ITEMS.registerItem("srebelhelmet", p -> new ItemClassArmor(srebelarmor, ArmorItem.Type.HELMET));
    public static final DeferredItem<Item> srebelchest = ITEMS.registerItem("srebelchest", p -> new ItemClassArmor(srebelarmor, ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<Item> srebelpants = ITEMS.registerItem("srebelpants", p -> new ItemClassArmor(srebelarmor, ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<Item> srebelboots = ITEMS.registerItem("srebelboots", p -> new ItemClassArmor(srebelarmor, ArmorItem.Type.BOOTS));
    public static final DeferredItem<Item> snukerhelmet = ITEMS.registerItem("snukerhelmet", p -> new ItemClassArmor(snukerarmor, ArmorItem.Type.HELMET));
    public static final DeferredItem<Item> snukerchest = ITEMS.registerItem("snukerchest", p -> new ItemClassArmor(snukerarmor, ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<Item> snukerpants = ITEMS.registerItem("snukerpants", p -> new ItemClassArmor(snukerarmor, ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<Item> snukerboots = ITEMS.registerItem("snukerboots", p -> new ItemClassArmor(snukerarmor, ArmorItem.Type.BOOTS));
    public static final DeferredItem<Item> sintelhelmet = ITEMS.registerItem("sintelhelmet", p -> new ItemClassArmor(sintelarmor, ArmorItem.Type.HELMET));
    public static final DeferredItem<Item> sintelchest = ITEMS.registerItem("sintelchest", p -> new ItemClassArmor(sintelarmor, ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<Item> sintelpants = ITEMS.registerItem("sintelpants", p -> new ItemClassArmor(sintelarmor, ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<Item> sintelboots = ITEMS.registerItem("sintelboots", p -> new ItemClassArmor(sintelarmor, ArmorItem.Type.BOOTS));
    public static final DeferredItem<Item> shackerhelmet = ITEMS.registerItem("shackerhelmet", p -> new ItemClassArmor(shackerarmor, ArmorItem.Type.HELMET));
    public static final DeferredItem<Item> shackerchest = ITEMS.registerItem("shackerchest", p -> new ItemClassArmor(shackerarmor, ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<Item> shackerpants = ITEMS.registerItem("shackerpants", p -> new ItemClassArmor(shackerarmor, ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<Item> shackerboots = ITEMS.registerItem("shackerboots", p -> new ItemClassArmor(shackerarmor, ArmorItem.Type.BOOTS));
    public static final DeferredItem<Item> camohat = ITEMS.registerItem("camohat", p -> new ItemArmorCamo(armorCamo, ArmorItem.Type.HELMET));
    public static final DeferredItem<Item> camoshirt = ITEMS.registerItem("camoshirt", p -> new ItemArmorCamo(armorCamo, ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<Item> camopants = ITEMS.registerItem("camopants", p -> new ItemArmorCamo(armorCamo, ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<Item> camoshoes = ITEMS.registerItem("camoshoes", p -> new ItemArmorCamo(armorCamo, ArmorItem.Type.BOOTS));
    public static final DeferredItem<Item> camohat2 = ITEMS.registerItem("camohat2", p -> new ItemArmorCamo(armorCamo2, ArmorItem.Type.HELMET));
    public static final DeferredItem<Item> camoshirt2 = ITEMS.registerItem("camoshirt2", p -> new ItemArmorCamo(armorCamo2, ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<Item> camopants2 = ITEMS.registerItem("camopants2", p -> new ItemArmorCamo(armorCamo2, ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<Item> camoshoes2 = ITEMS.registerItem("camoshoes2", p -> new ItemArmorCamo(armorCamo2, ArmorItem.Type.BOOTS));

    private static Holder<ArmorMaterial> registerMaterial(String id, int[] defense, int enchantmentValue) {
        return ARMOR_MATERIALS.register(id, rl -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.BOOTS, defense[0]);
            map.put(ArmorItem.Type.LEGGINGS, defense[1]);
            map.put(ArmorItem.Type.BODY, defense[2]);
            map.put(ArmorItem.Type.HELMET, defense[3]);
        }), enchantmentValue, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.EMPTY, List.of(new ArmorMaterial.Layer(rl)), 1, 1));
    }

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
        CREATIVE_MODE_TABS.register(bus);
        ARMOR_MATERIALS.register(bus);
    }

    static {
        ITEMS.registerSimpleBlockItem(RRBlocks.amario);
        ITEMS.registerSimpleBlockItem(RRBlocks.aquicksand);
        ITEMS.registerSimpleBlockItem(RRBlocks.barricade);
        ITEMS.registerSimpleBlockItem(RRBlocks.tower);
        ITEMS.registerSimpleBlockItem(RRBlocks.easteregg);
        ITEMS.registerSimpleBlockItem(RRBlocks.bunker);
        ITEMS.registerSimpleBlockItem(RRBlocks.smartcamo);
        ITEMS.registerSimpleBlockItem(RRBlocks.camo1);
        ITEMS.registerSimpleBlockItem(RRBlocks.camo2);
        ITEMS.registerSimpleBlockItem(RRBlocks.camo3);
        ITEMS.registerSimpleBlockItem(RRBlocks.steel);
        ITEMS.registerSimpleBlockItem(RRBlocks.flagbox1);
        ITEMS.registerSimpleBlockItem(RRBlocks.flagbox5);
        ITEMS.registerSimpleBlockItem(RRBlocks.flagbox6);
        ITEMS.registerSimpleBlockItem(RRBlocks.flagbox3);
        ITEMS.registerSimpleBlockItem(RRBlocks.flagbox4);
        ITEMS.registerSimpleBlockItem(RRBlocks.flagbox7);
        ITEMS.registerSimpleBlockItem(RRBlocks.sigmaarmor);
        ITEMS.registerSimpleBlockItem(RRBlocks.omegaarmor);
        ITEMS.registerSimpleBlockItem(RRBlocks.weapons);
        ITEMS.registerSimpleBlockItem(RRBlocks.ammunition);
        ITEMS.registerSimpleBlockItem(RRBlocks.explosives);
        ITEMS.registerSimpleBlockItem(RRBlocks.supplies);
        ITEMS.registerSimpleBlockItem(RRBlocks.jump);
        ITEMS.registerSimpleBlockItem(RRBlocks.remotecharge);
        ITEMS.registerSimpleBlockItem(RRBlocks.timedbomb);
        ITEMS.registerSimpleBlockItem(RRBlocks.flare);
        ITEMS.registerSimpleBlockItem(RRBlocks.cycle);
        ITEMS.registerSimpleBlockItem(RRBlocks.fshield);
        ITEMS.registerSimpleBlockItem(RRBlocks.gamestart);
        ITEMS.registerSimpleBlockItem(RRBlocks.breadbox);
        ITEMS.registerSimpleBlockItem(RRBlocks.alandmine);
        ITEMS.registerSimpleBlockItem(RRBlocks.nukeCrateTop);
        ITEMS.registerSimpleBlockItem(RRBlocks.nukeCrateBottom);
        ITEMS.registerSimpleBlockItem(RRBlocks.radioactivedirt);
        ITEMS.registerSimpleBlockItem(RRBlocks.radioactivesand);
        ITEMS.registerSimpleBlockItem(RRBlocks.reactor);
        ITEMS.registerSimpleBlockItem(RRBlocks.loader);
        ITEMS.registerSimpleBlockItem(RRBlocks.omegaobj);
        ITEMS.registerSimpleBlockItem(RRBlocks.sigmaobj);
        ITEMS.registerSimpleBlockItem(RRBlocks.petrifiedwood);
        ITEMS.registerSimpleBlockItem(RRBlocks.petrifiedstone1);
        ITEMS.registerSimpleBlockItem(RRBlocks.petrifiedstone2);
        ITEMS.registerSimpleBlockItem(RRBlocks.petrifiedstone3);
        ITEMS.registerSimpleBlockItem(RRBlocks.petrifiedstone4);
        ITEMS.registerSimpleBlockItem(RRBlocks.forcefieldnode);
        ITEMS.registerSimpleBlockItem(RRBlocks.goreblock);
        ITEMS.registerSimpleBlockItem(RRBlocks.reactive);
        ITEMS.registerSimpleBlockItem(RRBlocks.bastion);
        ITEMS.registerSimpleBlockItem(RRBlocks.conduit);
        ITEMS.registerSimpleBlockItem(RRBlocks.controller);
        ITEMS.registerSimpleBlockItem(RRBlocks.mariotrap);
        ITEMS.registerSimpleBlockItem(RRBlocks.minetrap);
        ITEMS.registerSimpleBlockItem(RRBlocks.quicksandtrap);
        ITEMS.registerSimpleBlockItem(RRBlocks.ffreciever);
        ITEMS.registerSimpleBlockItem(RRBlocks.buildrhodes);
        ITEMS.registerSimpleBlockItem(RRBlocks.rhodesactivator);
    }
}
