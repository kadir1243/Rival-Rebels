package assets.rivalrebels.common.item;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.item.weapon.*;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class RRItems {
    public static final Set<Item> ALL = new LinkedHashSet<>();
    public static final Holder<ArmorMaterial> TROLL_MATERIAL = registerMaterial("troll", new int[] {0, 0, 0, 0}, 1000);
    public static final CreativeModeTab rralltab = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, RRIdentifiers.create("alltab"), FabricItemGroup.builder().icon(() -> RRItems.NUCLEAR_ROD.getDefaultInstance()).title(Component.translatable("itemGroup." + RivalRebels.MODID + ".all")).displayItems((displayContext, entries) -> entries.acceptAll(ALL.stream().map(Item::getDefaultInstance).toList())).build());
    public static final Item rpg = register("rpg", new ItemRPG());
    public static final Item flamethrower = register("flamethrower", new ItemFlameThrower());
    public static final Item tesla = register("tesla", new ItemTesla());
    public static final Item einsten = register("astro_blaster", new ItemAstroBlaster());
    public static final Item rocket = register("rocket", new ItemRocket());
    public static final Item fuel = register("fuel", new Item(new Item.Properties()));
    public static final Item battery = register("battery", new Item(new Item.Properties()));
    public static final Item redrod = register("redstone_rod", new ItemRodRedstone());
    public static final Item pliers = register("pliers", new ItemPliers());
    public static final Item armyshovel = register("army_shovel", new ItemArmyShovel());
    public static final Item knife = register("knife", new ItemCuchillo());
    public static final Item gasgrenade = register("gas_grenade", new ItemGasGrenade());
    public static final Item safepill = register("safe_pill", new ItemSafePill());
    public static final Item expill = register("expill", new ItemExPill());
    public static final Item remote = register("remote", new ItemRemote());
    public static final Item fuse = register("fuse", new ItemFuse());
    public static final Item NUCLEAR_ROD = register("nuclear_rod", new ItemRodNuclear());
    public static final Item hydrod = register("hydrogen_rod", new ItemRodHydrogen());
    public static final Item plasmacannon = register("plasma_cannon", new ItemPlasmaCannon());
    public static final Item roddisk = register("rod_disk", new ItemRodDisk());
    public static final Item antenna = register("antenna", new Item(new Item.Properties().stacksTo(1)));
    public static final Item emptyrod = register("empty_rod", new Item(new Item.Properties()));
    public static final Item core1 = register("copper_core", new ItemCoreCopper());
    public static final Item core2 = register("tungsten_core", new ItemCoreTungsten());
    public static final Item core3 = register("titanium_core", new ItemCoreTitanium());
    public static final Item binoculars = register("binoculars", new ItemBinoculars());
    public static final Item camera = register("camera", new ItemCamera());
    public static final Item chip = register("chip", new ItemChip());
    public static final Item roda = register("roda", new ItemRoda());
    public static final Item trollmask = register("troll_mask", new ItemTrollHelmet());
    public static final Item hackm202 = register("hackm202", new ItemHackM202());
    public static final Item seekm202 = register("seekm202", new ItemSeekM202());
    public static final Holder<ArmorMaterial> orebelarmor = registerMaterial("rebelo", new int[]{6, 18, 6, 6}, 6);
    public static final Item orebelhelmet = new ItemClassArmor(orebelarmor, ArmorItem.Type.HELMET, 0, 0);
    public static final Item orebelchest = new ItemClassArmor(orebelarmor, ArmorItem.Type.CHESTPLATE, 0, 0);
    public static final Item orebelpants = new ItemClassArmor(orebelarmor, ArmorItem.Type.LEGGINGS, 0, 0);
    public static final Item orebelboots = new ItemClassArmor(orebelarmor, ArmorItem.Type.BOOTS, 0, 0);
    public static final Holder<ArmorMaterial> onukerarmor = registerMaterial("nukero", new int[]{8, 20, 8, 6}, 2);
    public static final Item onukerhelmet = new ItemClassArmor(onukerarmor, ArmorItem.Type.HELMET, 0, 1);
    public static final Item onukerchest = new ItemClassArmor(onukerarmor, ArmorItem.Type.CHESTPLATE, 0, 1);
    public static final Item onukerpants = new ItemClassArmor(onukerarmor, ArmorItem.Type.LEGGINGS, 0, 1);
    public static final Item onukerboots = new ItemClassArmor(onukerarmor, ArmorItem.Type.BOOTS, 0, 1);
    public static final Holder<ArmorMaterial> ointelarmor = registerMaterial("intelo", new int[]{4, 11, 6, 5}, 10);
    public static final Item ointelhelmet = new ItemClassArmor(ointelarmor, ArmorItem.Type.HELMET, 0, 2);
    public static final Item ointelchest = new ItemClassArmor(ointelarmor, ArmorItem.Type.CHESTPLATE, 0, 2);
    public static final Item ointelpants = new ItemClassArmor(ointelarmor, ArmorItem.Type.LEGGINGS, 0, 2);
    public static final Item ointelboots = new ItemClassArmor(ointelarmor, ArmorItem.Type.BOOTS, 0, 2);
    public static final Holder<ArmorMaterial> ohackerarmor = registerMaterial("hackero", new int[]{2, 11, 6, 5}, 10);
    public static final Item ohackerhelmet = new ItemClassArmor(ohackerarmor, ArmorItem.Type.HELMET, 0, 3);
    public static final Item ohackerchest = new ItemClassArmor(ohackerarmor, ArmorItem.Type.CHESTPLATE, 0, 3);
    public static final Item ohackerpants = new ItemClassArmor(ohackerarmor, ArmorItem.Type.LEGGINGS, 0, 3);
    public static final Item ohackerboots = new ItemClassArmor(ohackerarmor, ArmorItem.Type.BOOTS, 0, 3);
    public static final Holder<ArmorMaterial> srebelarmor = registerMaterial("rebels", new int[]{6, 18, 6, 6}, 6);
    public static final Item srebelhelmet = new ItemClassArmor(srebelarmor, ArmorItem.Type.HELMET, 1, 0);
    public static final Item srebelchest = new ItemClassArmor(srebelarmor, ArmorItem.Type.CHESTPLATE, 1, 0);
    public static final Item srebelpants = new ItemClassArmor(srebelarmor, ArmorItem.Type.LEGGINGS, 1, 0);
    public static final Item srebelboots = new ItemClassArmor(srebelarmor, ArmorItem.Type.BOOTS, 1, 0);
    public static final Holder<ArmorMaterial> snukerarmor = registerMaterial("nukers", new int[]{8, 20, 8, 6}, 2);
    public static final Item snukerhelmet = new ItemClassArmor(snukerarmor, ArmorItem.Type.HELMET, 1, 1);
    public static final Item snukerchest = new ItemClassArmor(snukerarmor, ArmorItem.Type.CHESTPLATE, 1, 1);
    public static final Item snukerpants = new ItemClassArmor(snukerarmor, ArmorItem.Type.LEGGINGS, 1, 1);
    public static final Item snukerboots = new ItemClassArmor(snukerarmor, ArmorItem.Type.BOOTS, 1, 1);
    public static final Holder<ArmorMaterial> sintelarmor = registerMaterial("intels", new int[]{4, 11, 6, 5}, 10);
    public static final Item sintelhelmet = new ItemClassArmor(sintelarmor, ArmorItem.Type.HELMET, 1, 2);
    public static final Item sintelchest = new ItemClassArmor(sintelarmor, ArmorItem.Type.CHESTPLATE, 1, 2);
    public static final Item sintelpants = new ItemClassArmor(sintelarmor, ArmorItem.Type.LEGGINGS, 1, 2);
    public static final Item sintelboots = new ItemClassArmor(sintelarmor, ArmorItem.Type.BOOTS, 1, 2);
    public static final Holder<ArmorMaterial> shackerarmor = registerMaterial("hackers", new int[]{2, 11, 6, 5}, 10);
    public static final Item shackerhelmet = new ItemClassArmor(shackerarmor, ArmorItem.Type.HELMET, 1, 3);
    public static final Item shackerchest = new ItemClassArmor(shackerarmor, ArmorItem.Type.CHESTPLATE, 1, 3);
    public static final Item shackerpants = new ItemClassArmor(shackerarmor, ArmorItem.Type.LEGGINGS, 1, 3);
    public static final Item shackerboots = new ItemClassArmor(shackerarmor, ArmorItem.Type.BOOTS, 1, 3);
    public static final Holder<ArmorMaterial> armorCamo = registerMaterial("camo", new int[]{2, 9, 5, 2}, 10);
    public static final Item camohat = new ItemArmorCamo(armorCamo, ArmorItem.Type.HELMET, 0);
    public static final Item camoshirt = new ItemArmorCamo(armorCamo, ArmorItem.Type.CHESTPLATE, 0);
    public static final Item camopants = new ItemArmorCamo(armorCamo, ArmorItem.Type.LEGGINGS, 0);
    public static final Item camoshoes = new ItemArmorCamo(armorCamo, ArmorItem.Type.BOOTS, 0);
    public static final Holder<ArmorMaterial> armorCamo2 = registerMaterial("camo2", new int[]{2, 9, 5, 2}, 10);
    public static final Item camohat2 = new ItemArmorCamo(armorCamo2, ArmorItem.Type.HELMET, 1);
    public static final Item camoshirt2 = new ItemArmorCamo(armorCamo2, ArmorItem.Type.CHESTPLATE, 1);
    public static final Item camopants2 = new ItemArmorCamo(armorCamo2, ArmorItem.Type.LEGGINGS, 1);
    public static final Item camoshoes2 = new ItemArmorCamo(armorCamo2, ArmorItem.Type.BOOTS, 1);

    public static <T extends Item> T register(String name, T item) {
        ALL.add(item);
        return Registry.register(BuiltInRegistries.ITEM, RRIdentifiers.create(name), item);
    }

    private static Holder<ArmorMaterial> registerMaterial(String id, int[] defense,
                                                          int enchantmentValue) {
        return registerMaterial(id, defense, enchantmentValue, null);
    }

    private static Holder<ArmorMaterial> registerMaterial(String id, int[] defense,
                                                  int enchantmentValue,
                                                  @Nullable ArmorMaterial.Layer layer) {
        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, RRIdentifiers.create(id), new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.BOOTS, defense[0]);
            map.put(ArmorItem.Type.LEGGINGS, defense[1]);
            map.put(ArmorItem.Type.BODY, defense[2]);
            map.put(ArmorItem.Type.HELMET, defense[3]);
        }), enchantmentValue, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.EMPTY, layer != null ? List.of(layer) : List.of(), 1, 1));
    }

    public static void init() {
        RRItems.register("orebelhelmet", RRItems.orebelhelmet);
        RRItems.register("orebelchest", RRItems.orebelchest);
        RRItems.register("orebelpants", RRItems.orebelpants);
        RRItems.register("orebelboots", RRItems.orebelboots);
        RRItems.register("onukerhelmet", RRItems.onukerhelmet);
        RRItems.register("onukerchest", RRItems.onukerchest);
        RRItems.register("onukerpants", RRItems.onukerpants);
        RRItems.register("onukerboots", RRItems.onukerboots);
        RRItems.register("ointelhelmet", RRItems.ointelhelmet);
        RRItems.register("ointelchest", RRItems.ointelchest);
        RRItems.register("ointelpants", RRItems.ointelpants);
        RRItems.register("ointelboots", RRItems.ointelboots);
        RRItems.register("ohackerhelmet", RRItems.ohackerhelmet);
        RRItems.register("ohackerchest", RRItems.ohackerchest);
        RRItems.register("ohackerpants", RRItems.ohackerpants);
        RRItems.register("ohackerboots", RRItems.ohackerboots);
        RRItems.register("srebelhelmet", RRItems.srebelhelmet);
        RRItems.register("srebelchest", RRItems.srebelchest);
        RRItems.register("srebelpants", RRItems.srebelpants);
        RRItems.register("srebelboots", RRItems.srebelboots);
        RRItems.register("snukerhelmet", RRItems.snukerhelmet);
        RRItems.register("snukerchest", RRItems.snukerchest);
        RRItems.register("snukerpants", RRItems.snukerpants);
        RRItems.register("snukerboots", RRItems.snukerboots);
        RRItems.register("sintelhelmet", RRItems.sintelhelmet);
        RRItems.register("sintelchest", RRItems.sintelchest);
        RRItems.register("sintelpants", RRItems.sintelpants);
        RRItems.register("sintelboots", RRItems.sintelboots);
        RRItems.register("shackerhelmet", RRItems.shackerhelmet);
        RRItems.register("shackerchest", RRItems.shackerchest);
        RRItems.register("shackerpants", RRItems.shackerpants);
        RRItems.register("shackerboots", RRItems.shackerboots);
        RRItems.register("camohat", RRItems.camohat);
        RRItems.register("camoshirt", RRItems.camoshirt);
        RRItems.register("camopants", RRItems.camopants);
        RRItems.register("camoshoes", RRItems.camoshoes);
        RRItems.register("camohat2", RRItems.camohat2);
        RRItems.register("camoshirt2", RRItems.camoshirt2);
        RRItems.register("camopants2", RRItems.camopants2);
        RRItems.register("camoshoes2", RRItems.camoshoes2);
    }
}
