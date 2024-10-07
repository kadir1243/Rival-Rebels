package io.github.kadir1243.rivalrebels.common.item;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import io.github.kadir1243.rivalrebels.common.item.weapon.*;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;

public class RRItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RRIdentifiers.MODID);
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
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> rralltab = CREATIVE_MODE_TABS.register("creative_tab", () -> CreativeModeTab.builder()
        .icon(RRItems.NUCLEAR_ROD::toStack)
        .title(Component.translatable(Translations.CREATIVE_TAB.toLanguageKey()))
        .displayItems((displayContext, entries) -> entries.acceptAll(ITEMS.getEntries().stream().map(DeferredHolder::get).map(Item::getDefaultInstance).toList())
    ).build());
    public static final DeferredItem<Item> rpg = register("rpg", ItemRPG::new);
    public static final DeferredItem<Item> flamethrower = register("flamethrower", ItemFlameThrower::new);
    public static final DeferredItem<Item> tesla = register("tesla", ItemTesla::new);
    public static final DeferredItem<Item> einsten = register("astro_blaster", ItemAstroBlaster::new);
    public static final DeferredItem<Item> rocket = register("rocket", ItemRocket::new);
    public static final DeferredItem<Item> fuel = register("fuel", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> battery = register("battery", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> redrod = register("redstone_rod", ItemRodRedstone::new);
    public static final DeferredItem<Item> pliers = register("pliers", ItemPliers::new);
    public static final DeferredItem<Item> armyshovel = register("army_shovel", ItemArmyShovel::new);
    public static final DeferredItem<Item> knife = register("knife", ItemCuchillo::new);
    public static final DeferredItem<Item> gasgrenade = register("gas_grenade", ItemGasGrenade::new);
    public static final DeferredItem<Item> safepill = register("safe_pill", ItemSafePill::new);
    public static final DeferredItem<Item> expill = register("expill", ItemExPill::new);
    public static final DeferredItem<Item> remote = register("remote", ItemRemote::new);
    public static final DeferredItem<Item> fuse = register("fuse", ItemFuse::new);
    public static final DeferredItem<Item> NUCLEAR_ROD = register("nuclear_rod", ItemRodNuclear::new);
    public static final DeferredItem<Item> hydrod = register("hydrogen_rod", ItemRodHydrogen::new);
    public static final DeferredItem<Item> plasmacannon = register("plasma_cannon", ItemPlasmaCannon::new);
    public static final DeferredItem<Item> roddisk = register("rod_disk", ItemRodDisk::new);
    public static final DeferredItem<Item> antenna = register("antenna", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> emptyrod = register("empty_rod", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> core1 = register("copper_core", () -> new Item(new Item.Properties().component(RRComponents.CORE_TIME_MULTIPLIER, 0.25F).stacksTo(1)));
    public static final DeferredItem<Item> core2 = register("tungsten_core", () -> new Item(new Item.Properties().component(RRComponents.CORE_TIME_MULTIPLIER, 0.75F).stacksTo(1)));
    public static final DeferredItem<Item> core3 = register("titanium_core", () -> new Item(new Item.Properties().component(RRComponents.CORE_TIME_MULTIPLIER, 1F).stacksTo(1)));
    public static final DeferredItem<Item> binoculars = register("binoculars", ItemBinoculars::new);
    public static final DeferredItem<Item> camera = register("camera", ItemCamera::new);
    public static final DeferredItem<Item> chip = register("chip", ItemChip::new);
    public static final DeferredItem<Item> roda = register("roda", ItemRoda::new);
    public static final DeferredItem<Item> trollmask = register("troll_mask", ItemTrollHelmet::new);
    public static final DeferredItem<Item> hackm202 = register("hackm202", ItemHackM202::new);
    public static final DeferredItem<Item> seekm202 = register("seekm202", ItemSeekM202::new);
    public static final DeferredItem<Item> orebelhelmet = register("orebelhelmet", () -> new ItemClassArmor(orebelarmor, ArmorItem.Type.HELMET));
    public static final DeferredItem<Item> orebelchest = register("orebelchest", () -> new ItemClassArmor(orebelarmor, ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<Item> orebelpants = register("orebelpants", () -> new ItemClassArmor(orebelarmor, ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<Item> orebelboots = register("orebelboots", () -> new ItemClassArmor(orebelarmor, ArmorItem.Type.BOOTS));
    public static final DeferredItem<Item> onukerhelmet = register("onukerhelmet", () -> new ItemClassArmor(onukerarmor, ArmorItem.Type.HELMET));
    public static final DeferredItem<Item> onukerchest = register("onukerchest", () -> new ItemClassArmor(onukerarmor, ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<Item> onukerpants = register("onukerpants", () -> new ItemClassArmor(onukerarmor, ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<Item> onukerboots = register("onukerboots", () -> new ItemClassArmor(onukerarmor, ArmorItem.Type.BOOTS));
    public static final DeferredItem<Item> ointelhelmet = register("ointelhelmet", () -> new ItemClassArmor(ointelarmor, ArmorItem.Type.HELMET));
    public static final DeferredItem<Item> ointelchest = register("ointelchest", () -> new ItemClassArmor(ointelarmor, ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<Item> ointelpants = register("ointelpants", () -> new ItemClassArmor(ointelarmor, ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<Item> ointelboots = register("ointelboots", () -> new ItemClassArmor(ointelarmor, ArmorItem.Type.BOOTS));
    public static final DeferredItem<Item> ohackerhelmet = register("ohackerhelmet", () -> new ItemClassArmor(ohackerarmor, ArmorItem.Type.HELMET));
    public static final DeferredItem<Item> ohackerchest = register("ohackerchest", () -> new ItemClassArmor(ohackerarmor, ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<Item> ohackerpants = register("ohackerpants", () -> new ItemClassArmor(ohackerarmor, ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<Item> ohackerboots = register("ohackerboots", () -> new ItemClassArmor(ohackerarmor, ArmorItem.Type.BOOTS));
    public static final DeferredItem<Item> srebelhelmet = register("srebelhelmet", () -> new ItemClassArmor(srebelarmor, ArmorItem.Type.HELMET));
    public static final DeferredItem<Item> srebelchest = register("srebelchest", () -> new ItemClassArmor(srebelarmor, ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<Item> srebelpants = register("srebelpants", () -> new ItemClassArmor(srebelarmor, ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<Item> srebelboots = register("srebelboots", () -> new ItemClassArmor(srebelarmor, ArmorItem.Type.BOOTS));
    public static final DeferredItem<Item> snukerhelmet = register("snukerhelmet", () -> new ItemClassArmor(snukerarmor, ArmorItem.Type.HELMET));
    public static final DeferredItem<Item> snukerchest = register("snukerchest", () -> new ItemClassArmor(snukerarmor, ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<Item> snukerpants = register("snukerpants", () -> new ItemClassArmor(snukerarmor, ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<Item> snukerboots = register("snukerboots", () -> new ItemClassArmor(snukerarmor, ArmorItem.Type.BOOTS));
    public static final DeferredItem<Item> sintelhelmet = register("sintelhelmet", () -> new ItemClassArmor(sintelarmor, ArmorItem.Type.HELMET));
    public static final DeferredItem<Item> sintelchest = register("sintelchest", () -> new ItemClassArmor(sintelarmor, ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<Item> sintelpants = register("sintelpants", () -> new ItemClassArmor(sintelarmor, ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<Item> sintelboots = register("sintelboots", () -> new ItemClassArmor(sintelarmor, ArmorItem.Type.BOOTS));
    public static final DeferredItem<Item> shackerhelmet = register("shackerhelmet", () -> new ItemClassArmor(shackerarmor, ArmorItem.Type.HELMET));
    public static final DeferredItem<Item> shackerchest = register("shackerchest", () -> new ItemClassArmor(shackerarmor, ArmorItem.Type.CHESTPLATE));
    public static final DeferredItem<Item> shackerpants = register("shackerpants", () -> new ItemClassArmor(shackerarmor, ArmorItem.Type.LEGGINGS));
    public static final DeferredItem<Item> shackerboots = register("shackerboots", () -> new ItemClassArmor(shackerarmor, ArmorItem.Type.BOOTS));
    public static final DeferredItem<Item> camohat = register("camohat", () -> new ItemArmorCamo(armorCamo, ArmorItem.Type.HELMET, 0));
    public static final DeferredItem<Item> camoshirt = register("camoshirt", () -> new ItemArmorCamo(armorCamo, ArmorItem.Type.CHESTPLATE, 0));
    public static final DeferredItem<Item> camopants = register("camopants", () -> new ItemArmorCamo(armorCamo, ArmorItem.Type.LEGGINGS, 0));
    public static final DeferredItem<Item> camoshoes = register("camoshoes", () -> new ItemArmorCamo(armorCamo, ArmorItem.Type.BOOTS, 0));
    public static final DeferredItem<Item> camohat2 = register("camohat2", () -> new ItemArmorCamo(armorCamo2, ArmorItem.Type.HELMET, 1));
    public static final DeferredItem<Item> camoshirt2 = register("camoshirt2", () -> new ItemArmorCamo(armorCamo2, ArmorItem.Type.CHESTPLATE, 1));
    public static final DeferredItem<Item> camopants2 = register("camopants2", () -> new ItemArmorCamo(armorCamo2, ArmorItem.Type.LEGGINGS, 1));
    public static final DeferredItem<Item> camoshoes2 = register("camoshoes2", () -> new ItemArmorCamo(armorCamo2, ArmorItem.Type.BOOTS, 1));

    public static <T extends Item> DeferredItem<T> register(String name, Supplier<T> item) {
        return ITEMS.registerItem(name, properties -> item.get());
    }

    private static Holder<ArmorMaterial> registerMaterial(String id, int[] defense,
                                                          int enchantmentValue) {
        return registerMaterial(id, defense, enchantmentValue, null);
    }

    private static Holder<ArmorMaterial> registerMaterial(String id, int[] defense,
                                                  int enchantmentValue,
                                                  @Nullable Supplier<ArmorMaterial.Layer> layer) {
        return ARMOR_MATERIALS.register(id, () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.BOOTS, defense[0]);
            map.put(ArmorItem.Type.LEGGINGS, defense[1]);
            map.put(ArmorItem.Type.BODY, defense[2]);
            map.put(ArmorItem.Type.HELMET, defense[3]);
        }), enchantmentValue, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.EMPTY, layer != null ? List.of(layer.get()) : List.of(), 1, 1));
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
