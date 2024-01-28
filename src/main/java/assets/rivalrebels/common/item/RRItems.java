package assets.rivalrebels.common.item;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.RivalRebelsTab;
import assets.rivalrebels.common.item.weapon.*;
import assets.rivalrebels.common.util.SimpleArmorMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvents;

import java.util.HashSet;
import java.util.Set;

import static assets.rivalrebels.RivalRebels.rrname;

public class RRItems {
    public static final Set<Item> ITEMS = new HashSet<>();
    public static final ArmorMaterial TROLL_MATERIAL = new SimpleArmorMaterial("Troll", 5000, new int[]{0, 0, 0, 0}, 1000, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1, 1, Ingredient.EMPTY);
    public static RivalRebelsTab rralltab = new RivalRebelsTab(rrname, 0);
    public static RivalRebelsTab rrarmortab = new RivalRebelsTab(rrname, 1);
    public static Item rpg = register("rpg", new ItemRPG());
    public static Item flamethrower = register("flamethrower", new ItemFlameThrower());
    public static Item tesla = register("tesla", new ItemTesla());
    public static Item einsten = register("astro_blaster", new ItemAstroBlaster());
    public static Item rocket = register("rocket", new ItemRocket());
    public static Item fuel = register("fuel", new ItemFuel());
    public static Item battery = register("battery", new ItemBattery());
    public static Item redrod = register("redstone_rod", new ItemRodRedstone());
    public static Item pliers = register("pliers", new ItemPliers());
    public static Item armyshovel = register("army_shovel", new ItemArmyShovel());
    public static Item knife = register("knife", new ItemCuchillo());
    public static Item gasgrenade = register("gas_grenade", new ItemGasGrenade());
    public static Item safepill = register("safe_pill", new ItemSafePill());
    public static Item expill = register("expill", new ItemExPill());
    public static Item remote = register("remote", new ItemRemote());
    public static Item fuse = register("fuse", new ItemFuse());
    public static Item nuclearelement = register("nuclear_rod", new ItemRodNuclear());
    public static Item hydrod = register("hydrogen_rod", new ItemRodHydrogen());
    public static Item plasmacannon = register("plasma_cannon", new ItemPlasmaCannon());
    public static Item roddisk = register("rod_disk", new ItemRodDisk());
    public static Item antenna = register("antenna", new ItemAntenna());
    public static Item emptyrod = register("empty_rod", new ItemRodEmpty());
    public static Item core1 = register("copper_core", new ItemCoreCopper());
    public static Item core2 = register("tungsten_core", new ItemCoreTungsten());
    public static Item core3 = register("titanium_core", new ItemCoreTitanium());
    public static Item binoculars = register("binoculars", new ItemBinoculars());
    public static Item camera = register("camera", new ItemCamera());
    public static Item chip = register("chip", new ItemChip());
    public static Item roda = register("roda", new ItemRoda());
    public static Item trollmask = register("troll_mask", new ItemTrollHelmet());
    public static Item hackm202 = register("hackm202", new ItemHackM202());
    public static Item seekm202 = register("seekm202", new ItemSeekM202());
    public static ArmorMaterial orebelarmor = new SimpleArmorMaterial("rebelo", 150, new int[]{6, 18, 6, 6}, 6, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1, 1, Ingredient.EMPTY);
    public static Item orebelhelmet = new ItemClassArmor(orebelarmor, EquipmentSlot.HEAD, 0, 0);
    public static Item orebelchest = new ItemClassArmor(orebelarmor, EquipmentSlot.CHEST, 0, 0);
    public static Item orebelpants = new ItemClassArmor(orebelarmor, EquipmentSlot.LEGS, 0, 0);
    public static Item orebelboots = new ItemClassArmor(orebelarmor, EquipmentSlot.FEET, 0, 0);
    public static ArmorMaterial onukerarmor = new SimpleArmorMaterial("nukero", 100, new int[]{8, 20, 8, 6}, 2, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1, 1, Ingredient.EMPTY);
    public static Item onukerhelmet = new ItemClassArmor(onukerarmor, EquipmentSlot.HEAD, 0, 1);
    public static Item onukerchest = new ItemClassArmor(onukerarmor, EquipmentSlot.CHEST, 0, 1);
    public static Item onukerpants = new ItemClassArmor(onukerarmor, EquipmentSlot.LEGS, 0, 1);
    public static Item onukerboots = new ItemClassArmor(onukerarmor, EquipmentSlot.FEET, 0, 1);
    public static ArmorMaterial ointelarmor = new SimpleArmorMaterial("intelo", 80, new int[]{4, 11, 6, 5}, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1, 1, Ingredient.EMPTY);
    public static Item ointelhelmet = new ItemClassArmor(ointelarmor, EquipmentSlot.HEAD, 0, 2);
    public static Item ointelchest = new ItemClassArmor(ointelarmor, EquipmentSlot.CHEST, 0, 2);
    public static Item ointelpants = new ItemClassArmor(ointelarmor, EquipmentSlot.LEGS, 0, 2);
    public static Item ointelboots = new ItemClassArmor(ointelarmor, EquipmentSlot.FEET, 0, 2);
    public static ArmorMaterial ohackerarmor = new SimpleArmorMaterial("hackero", 60, new int[]{2, 11, 6, 5}, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1, 1, Ingredient.EMPTY);
    public static Item ohackerhelmet = new ItemClassArmor(ohackerarmor, EquipmentSlot.HEAD, 0, 3);
    public static Item ohackerchest = new ItemClassArmor(ohackerarmor, EquipmentSlot.CHEST, 0, 3);
    public static Item ohackerpants = new ItemClassArmor(ohackerarmor, EquipmentSlot.LEGS, 0, 3);
    public static Item ohackerboots = new ItemClassArmor(ohackerarmor, EquipmentSlot.FEET, 0, 3);
    public static ArmorMaterial srebelarmor = new SimpleArmorMaterial("rebels", 150, new int[]{6, 18, 6, 6}, 6, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1, 1, Ingredient.EMPTY);
    public static Item srebelhelmet = new ItemClassArmor(srebelarmor, EquipmentSlot.HEAD, 1, 0);
    public static Item srebelchest = new ItemClassArmor(srebelarmor, EquipmentSlot.CHEST, 1, 0);
    public static Item srebelpants = new ItemClassArmor(srebelarmor, EquipmentSlot.LEGS, 1, 0);
    public static Item srebelboots = new ItemClassArmor(srebelarmor, EquipmentSlot.FEET, 1, 0);
    public static ArmorMaterial snukerarmor = new SimpleArmorMaterial("nukers", 100, new int[]{8, 20, 8, 6}, 2, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1, 1, Ingredient.EMPTY);
    public static Item snukerhelmet = new ItemClassArmor(snukerarmor, EquipmentSlot.HEAD, 1, 1);
    public static Item snukerchest = new ItemClassArmor(snukerarmor, EquipmentSlot.CHEST, 1, 1);
    public static Item snukerpants = new ItemClassArmor(snukerarmor, EquipmentSlot.LEGS, 1, 1);
    public static Item snukerboots = new ItemClassArmor(snukerarmor, EquipmentSlot.FEET, 1, 1);
    public static ArmorMaterial sintelarmor = new SimpleArmorMaterial("intels", 80, new int[]{4, 11, 6, 5}, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1, 1, Ingredient.EMPTY);
    public static Item sintelhelmet = new ItemClassArmor(sintelarmor, EquipmentSlot.HEAD, 1, 2);
    public static Item sintelchest = new ItemClassArmor(sintelarmor, EquipmentSlot.CHEST, 1, 2);
    public static Item sintelpants = new ItemClassArmor(sintelarmor, EquipmentSlot.LEGS, 1, 2);
    public static Item sintelboots = new ItemClassArmor(sintelarmor, EquipmentSlot.FEET, 1, 2);
    public static ArmorMaterial shackerarmor = new SimpleArmorMaterial("hackers", 60, new int[]{2, 11, 6, 5}, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1, 1, Ingredient.EMPTY);
    public static Item shackerhelmet = new ItemClassArmor(shackerarmor, EquipmentSlot.HEAD, 1, 3);
    public static Item shackerchest = new ItemClassArmor(shackerarmor, EquipmentSlot.CHEST, 1, 3);
    public static Item shackerpants = new ItemClassArmor(shackerarmor, EquipmentSlot.LEGS, 1, 3);
    public static Item shackerboots = new ItemClassArmor(shackerarmor, EquipmentSlot.FEET, 1, 3);
    public static ArmorMaterial armorCamo = new SimpleArmorMaterial("Camo", 50, new int[]{2, 9, 5, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1, 1, Ingredient.EMPTY);
    public static Item camohat = new ItemArmorCamo(armorCamo, EquipmentSlot.HEAD, 0);
    public static Item camoshirt = new ItemArmorCamo(armorCamo, EquipmentSlot.CHEST, 0);
    public static Item camopants = new ItemArmorCamo(armorCamo, EquipmentSlot.LEGS, 0);
    public static Item camoshoes = new ItemArmorCamo(armorCamo, EquipmentSlot.FEET, 0);
    public static ArmorMaterial armorCamo2 = new SimpleArmorMaterial("Camo2", 50, new int[]{2, 9, 5, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1, 1, Ingredient.EMPTY);
    public static Item camohat2 = new ItemArmorCamo(armorCamo2, EquipmentSlot.HEAD, 1);
    public static Item camoshirt2 = new ItemArmorCamo(armorCamo2, EquipmentSlot.CHEST, 1);
    public static Item camopants2 = new ItemArmorCamo(armorCamo2, EquipmentSlot.LEGS, 1);
    public static Item camoshoes2 = new ItemArmorCamo(armorCamo2, EquipmentSlot.FEET, 1);

    private static <T extends Item> T register(String name, T item) {
        ITEMS.add(item);
        item.setRegistryName(RivalRebels.MODID, name);
        return item;
    }


}
