package io.github.kadir1243.rivalrebels.common.item;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.RivalRebels;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.item.components.BinocularData;
import io.github.kadir1243.rivalrebels.common.item.components.FlameThrowerMode;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import io.github.kadir1243.rivalrebels.common.item.weapon.*;
import io.github.kadir1243.rivalrebels.common.util.Translations;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.*;

public class RRItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RRIdentifiers.MODID);
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RRIdentifiers.MODID);

    public static final ResourceKey<EquipmentAsset> orebelarmor_ASSET_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, RRIdentifiers.create("rebelo"));
    public static final ResourceKey<EquipmentAsset> onukerarmor_ASSET_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, RRIdentifiers.create("nukero"));
    public static final ResourceKey<EquipmentAsset> ointelarmor_ASSET_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, RRIdentifiers.create("intelo"));
    public static final ResourceKey<EquipmentAsset> ohackerarmor_ASSET_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, RRIdentifiers.create("hackero"));
    public static final ResourceKey<EquipmentAsset> srebelarmor_ASSET_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, RRIdentifiers.create("rebels"));
    public static final ResourceKey<EquipmentAsset> snukerarmor_ASSET_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, RRIdentifiers.create("nukers"));
    public static final ResourceKey<EquipmentAsset> sintelarmor_ASSET_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, RRIdentifiers.create("intels"));
    public static final ResourceKey<EquipmentAsset> shackerarmor_ASSET_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, RRIdentifiers.create("hackers"));
    public static final ResourceKey<EquipmentAsset> armorCamo_ASSET_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, RRIdentifiers.create("camo"));
    public static final ResourceKey<EquipmentAsset> armorCamo2_ASSET_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, RRIdentifiers.create("camo2"));
    public static final ResourceKey<EquipmentAsset> TROLL_MATERIAL_ASSET_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, RRIdentifiers.create("troll"));

    public static final ArmorMaterial orebelarmor = registerMaterial("rebelo", 6, 18, 6, 6, 6, orebelarmor_ASSET_KEY);
    public static final ArmorMaterial onukerarmor = registerMaterial("nukero", 8, 20, 8, 6, 2, onukerarmor_ASSET_KEY);
    public static final ArmorMaterial ointelarmor = registerMaterial("intelo", 4, 11, 6, 5, 10, ointelarmor_ASSET_KEY);
    public static final ArmorMaterial ohackerarmor = registerMaterial("hackero", 2, 11, 6, 5, 10, ohackerarmor_ASSET_KEY);
    public static final ArmorMaterial srebelarmor = registerMaterial("rebels", 6, 18, 6, 6, 6, srebelarmor_ASSET_KEY);
    public static final ArmorMaterial snukerarmor = registerMaterial("nukers", 8, 20, 8, 6, 2, snukerarmor_ASSET_KEY);
    public static final ArmorMaterial sintelarmor = registerMaterial("intels", 4, 11, 6, 5, 10, sintelarmor_ASSET_KEY);
    public static final ArmorMaterial shackerarmor = registerMaterial("hackers", 2, 11, 6, 5, 10, shackerarmor_ASSET_KEY);
    public static final ArmorMaterial armorCamo = registerMaterial("camo", 2, 9, 5, 2, 10, armorCamo_ASSET_KEY);
    public static final ArmorMaterial armorCamo2 = registerMaterial("camo2", 2, 9, 5, 2, 10, armorCamo2_ASSET_KEY);
    public static final ArmorMaterial TROLL_MATERIAL = registerMaterial("troll", 0, 0, 0, 0, 1000, TROLL_MATERIAL_ASSET_KEY);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("creative_tab", () -> CreativeModeTab.builder()
        .icon(RRItems.NUCLEAR_ROD::toStack)
        .title(Translations.CREATIVE_TAB.translate())
        .displayItems((displayContext, entries) -> entries.acceptAll(ITEMS.getEntries().stream().map(DeferredHolder::get).map(Item::getDefaultInstance).toList())
    ).build());
    public static final DeferredItem<Item> rpg = ITEMS.registerItem("rpg", ItemRPG::new, () -> new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> flamethrower = ITEMS.registerItem("flamethrower", ItemFlameThrower::new, () -> new Item.Properties().component(RRComponents.FLAME_THROWER_MODE, FlameThrowerMode.DEFAULT).stacksTo(1));
    public static final DeferredItem<Item> tesla = ITEMS.registerItem("tesla", ItemTesla::new, () -> new Item.Properties().stacksTo(1).enchantable(100).component(RRComponents.TESLA_DIAL, 0));
    public static final DeferredItem<Item> einsten = ITEMS.registerItem("astro_blaster", ItemAstroBlaster::new, () -> new Item.Properties().stacksTo(1).enchantable(100));
    public static final DeferredItem<Item> rocket = ITEMS.registerSimpleItem("rocket");
    public static final DeferredItem<Item> fuel = ITEMS.registerSimpleItem("fuel");
    public static final DeferredItem<Item> battery = ITEMS.registerSimpleItem("battery");
    public static final DeferredItem<Item> redrod = ITEMS.registerItem("redstone_rod", p -> new Item(p.durability(256).component(RRComponents.ROD_POWER.get(), 300000)));
    public static final DeferredItem<Item> pliers = ITEMS.registerItem("pliers", ItemPliers::new, () -> new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> armyshovel = ITEMS.registerItem("army_shovel", p -> new Item(p.tool(ToolMaterial.DIAMOND, RivalRebels.MINEABLE_WITH_ARMY_SHOVEL, 1, 1, 0)), () -> new Item.Properties().component(DataComponents.UNBREAKABLE, Unit.INSTANCE));
    public static final DeferredItem<Item> knife = ITEMS.registerItem("knife", ItemCuchillo::new, () -> new Item.Properties().stacksTo(5));
    public static final DeferredItem<Item> gasgrenade = ITEMS.registerItem("gas_grenade", ItemGasGrenade::new, () -> new Item.Properties().stacksTo(6));
    public static final DeferredItem<Item> safepill = ITEMS.registerItem("safe_pill", ItemSafePill::new, () -> new Item.Properties().stacksTo(6));
    public static final DeferredItem<Item> expill = ITEMS.registerItem("expill", ItemExPill::new, () -> new Item.Properties().stacksTo(6));
    public static final DeferredItem<Item> remote = ITEMS.registerItem("remote", ItemRemote::new, () -> new Item.Properties().stacksTo(1).component(RRComponents.REMOTE_CONTROLLED_BOMB_POS, BlockPos.ZERO));
    public static final DeferredItem<Item> fuse = ITEMS.registerSimpleItem("fuse", () -> new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> NUCLEAR_ROD = ITEMS.registerItem("nuclear_rod", p -> new ItemRodNuclear(p.durability(32).component(RRComponents.ROD_POWER, 3000000)));
    public static final DeferredItem<Item> hydrod = ITEMS.registerSimpleItem("hydrogen_rod", () -> new Item.Properties().durability(32).component(RRComponents.ROD_POWER, 250000));
    public static final DeferredItem<Item> plasmacannon = ITEMS.registerItem("plasma_cannon", ItemPlasmaCannon::new, () -> new Item.Properties().stacksTo(1).enchantable(100));
    public static final DeferredItem<Item> roddisk = ITEMS.registerItem("rod_disk", ItemRodDisk::new, () -> new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> antenna = ITEMS.registerSimpleItem("antenna", () -> new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> emptyrod = ITEMS.registerSimpleItem("empty_rod");
    public static final DeferredItem<Item> core1 = ITEMS.registerSimpleItem("copper_core", () -> new Item.Properties().component(RRComponents.CORE_TIME_MULTIPLIER, 0.25F).stacksTo(1));
    public static final DeferredItem<Item> core2 = ITEMS.registerSimpleItem("tungsten_core", () -> new Item.Properties().component(RRComponents.CORE_TIME_MULTIPLIER, 0.75F).stacksTo(1));
    public static final DeferredItem<Item> core3 = ITEMS.registerSimpleItem("titanium_core", () -> new Item.Properties().component(RRComponents.CORE_TIME_MULTIPLIER, 1F).stacksTo(1));
    public static final DeferredItem<Item> binoculars = ITEMS.registerItem("binoculars", ItemBinoculars::new, () -> new Item.Properties().stacksTo(1).component(RRComponents.BINOCULAR_DATA, BinocularData.DEFAULT));
    public static final DeferredItem<Item> camera = ITEMS.registerItem("camera", ItemCamera::new, () -> new Item.Properties().stacksTo(1).equippable(EquipmentSlot.HEAD).humanoidArmor(ArmorMaterials.CHAINMAIL, ArmorType.HELMET));
    public static final DeferredItem<Item> chip = ITEMS.registerItem("chip", ItemChip::new, () -> new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> roda = ITEMS.registerItem("roda", ItemRoda::new, () -> new Item.Properties().stacksTo(1).component(RRComponents.HAPPY_NEW_YEAR, 0));
    public static final DeferredItem<Item> trollmask = ITEMS.registerItem("troll_mask", ItemTrollHelmet::new, () -> new Item.Properties().durability(5000).humanoidArmor(RRItems.TROLL_MATERIAL, ArmorType.HELMET));
    public static final DeferredItem<Item> hackm202 = ITEMS.registerItem("hackm202", ItemHackM202::new, () -> new Item.Properties().stacksTo(1).enchantable(100));
    public static final DeferredItem<Item> seekm202 = ITEMS.registerItem("seekm202", ItemSeekM202::new, () -> new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> orebelhelmet = ITEMS.registerSimpleItem("orebelhelmet",  () -> new Item.Properties().humanoidArmor(orebelarmor, ArmorType.HELMET));
    public static final DeferredItem<Item> orebelchest = ITEMS.registerSimpleItem("orebelchest",  () -> new Item.Properties().humanoidArmor(orebelarmor, ArmorType.CHESTPLATE));
    public static final DeferredItem<Item> orebelpants = ITEMS.registerSimpleItem("orebelpants",  () -> new Item.Properties().humanoidArmor(orebelarmor, ArmorType.LEGGINGS));
    public static final DeferredItem<Item> orebelboots = ITEMS.registerSimpleItem("orebelboots",  () -> new Item.Properties().humanoidArmor(orebelarmor, ArmorType.BOOTS));
    public static final DeferredItem<Item> onukerhelmet = ITEMS.registerSimpleItem("onukerhelmet",  () -> new Item.Properties().humanoidArmor(onukerarmor, ArmorType.HELMET));
    public static final DeferredItem<Item> onukerchest = ITEMS.registerSimpleItem("onukerchest",  () -> new Item.Properties().humanoidArmor(onukerarmor, ArmorType.CHESTPLATE));
    public static final DeferredItem<Item> onukerpants = ITEMS.registerSimpleItem("onukerpants",  () -> new Item.Properties().humanoidArmor(onukerarmor, ArmorType.LEGGINGS));
    public static final DeferredItem<Item> onukerboots = ITEMS.registerSimpleItem("onukerboots",  () -> new Item.Properties().humanoidArmor(onukerarmor, ArmorType.BOOTS));
    public static final DeferredItem<Item> ointelhelmet = ITEMS.registerSimpleItem("ointelhelmet",  () -> new Item.Properties().humanoidArmor(ointelarmor, ArmorType.HELMET));
    public static final DeferredItem<Item> ointelchest = ITEMS.registerSimpleItem("ointelchest",  () -> new Item.Properties().humanoidArmor(ointelarmor, ArmorType.CHESTPLATE));
    public static final DeferredItem<Item> ointelpants = ITEMS.registerSimpleItem("ointelpants",  () -> new Item.Properties().humanoidArmor(ointelarmor, ArmorType.LEGGINGS));
    public static final DeferredItem<Item> ointelboots = ITEMS.registerSimpleItem("ointelboots",  () -> new Item.Properties().humanoidArmor(ointelarmor, ArmorType.BOOTS));
    public static final DeferredItem<Item> ohackerhelmet = ITEMS.registerSimpleItem("ohackerhelmet",  () -> new Item.Properties().humanoidArmor(ohackerarmor, ArmorType.HELMET));
    public static final DeferredItem<Item> ohackerchest = ITEMS.registerSimpleItem("ohackerchest",  () -> new Item.Properties().humanoidArmor(ohackerarmor, ArmorType.CHESTPLATE));
    public static final DeferredItem<Item> ohackerpants = ITEMS.registerSimpleItem("ohackerpants",  () -> new Item.Properties().humanoidArmor(ohackerarmor, ArmorType.LEGGINGS));
    public static final DeferredItem<Item> ohackerboots = ITEMS.registerSimpleItem("ohackerboots",  () -> new Item.Properties().humanoidArmor(ohackerarmor, ArmorType.BOOTS));
    public static final DeferredItem<Item> srebelhelmet = ITEMS.registerSimpleItem("srebelhelmet",  () -> new Item.Properties().humanoidArmor(srebelarmor, ArmorType.HELMET));
    public static final DeferredItem<Item> srebelchest = ITEMS.registerSimpleItem("srebelchest",  () -> new Item.Properties().humanoidArmor(srebelarmor, ArmorType.CHESTPLATE));
    public static final DeferredItem<Item> srebelpants = ITEMS.registerSimpleItem("srebelpants",  () -> new Item.Properties().humanoidArmor(srebelarmor, ArmorType.LEGGINGS));
    public static final DeferredItem<Item> srebelboots = ITEMS.registerSimpleItem("srebelboots",  () -> new Item.Properties().humanoidArmor(srebelarmor, ArmorType.BOOTS));
    public static final DeferredItem<Item> snukerhelmet = ITEMS.registerSimpleItem("snukerhelmet",  () -> new Item.Properties().humanoidArmor(snukerarmor, ArmorType.HELMET));
    public static final DeferredItem<Item> snukerchest = ITEMS.registerSimpleItem("snukerchest",  () -> new Item.Properties().humanoidArmor(snukerarmor, ArmorType.CHESTPLATE));
    public static final DeferredItem<Item> snukerpants = ITEMS.registerSimpleItem("snukerpants",  () -> new Item.Properties().humanoidArmor(snukerarmor, ArmorType.LEGGINGS));
    public static final DeferredItem<Item> snukerboots = ITEMS.registerSimpleItem("snukerboots",  () -> new Item.Properties().humanoidArmor(snukerarmor, ArmorType.BOOTS));
    public static final DeferredItem<Item> sintelhelmet = ITEMS.registerSimpleItem("sintelhelmet",  () -> new Item.Properties().humanoidArmor(sintelarmor, ArmorType.HELMET));
    public static final DeferredItem<Item> sintelchest = ITEMS.registerSimpleItem("sintelchest",  () -> new Item.Properties().humanoidArmor(sintelarmor, ArmorType.CHESTPLATE));
    public static final DeferredItem<Item> sintelpants = ITEMS.registerSimpleItem("sintelpants",  () -> new Item.Properties().humanoidArmor(sintelarmor, ArmorType.LEGGINGS));
    public static final DeferredItem<Item> sintelboots = ITEMS.registerSimpleItem("sintelboots",  () -> new Item.Properties().humanoidArmor(sintelarmor, ArmorType.BOOTS));
    public static final DeferredItem<Item> shackerhelmet = ITEMS.registerSimpleItem("shackerhelmet",  () -> new Item.Properties().humanoidArmor(shackerarmor, ArmorType.HELMET));
    public static final DeferredItem<Item> shackerchest = ITEMS.registerSimpleItem("shackerchest",  () -> new Item.Properties().humanoidArmor(shackerarmor, ArmorType.CHESTPLATE));
    public static final DeferredItem<Item> shackerpants = ITEMS.registerSimpleItem("shackerpants",  () -> new Item.Properties().humanoidArmor(shackerarmor, ArmorType.LEGGINGS));
    public static final DeferredItem<Item> shackerboots = ITEMS.registerSimpleItem("shackerboots",  () -> new Item.Properties().humanoidArmor(shackerarmor, ArmorType.BOOTS));
    public static final DeferredItem<Item> camohat = ITEMS.registerSimpleItem("camohat",  () -> new Item.Properties().humanoidArmor(armorCamo, ArmorType.HELMET));
    public static final DeferredItem<Item> camoshirt = ITEMS.registerSimpleItem("camoshirt",  () -> new Item.Properties().humanoidArmor(armorCamo, ArmorType.CHESTPLATE));
    public static final DeferredItem<Item> camopants = ITEMS.registerSimpleItem("camopants",  () -> new Item.Properties().humanoidArmor(armorCamo, ArmorType.LEGGINGS));
    public static final DeferredItem<Item> camoshoes = ITEMS.registerSimpleItem("camoshoes",  () -> new Item.Properties().humanoidArmor(armorCamo, ArmorType.BOOTS));
    public static final DeferredItem<Item> camohat2 = ITEMS.registerSimpleItem("camohat2",  () -> new Item.Properties().humanoidArmor(armorCamo2, ArmorType.HELMET));
    public static final DeferredItem<Item> camoshirt2 = ITEMS.registerSimpleItem("camoshirt2",  () -> new Item.Properties().humanoidArmor(armorCamo2, ArmorType.CHESTPLATE));
    public static final DeferredItem<Item> camopants2 = ITEMS.registerSimpleItem("camopants2",  () -> new Item.Properties().humanoidArmor(armorCamo2, ArmorType.LEGGINGS));
    public static final DeferredItem<Item> camoshoes2 = ITEMS.registerSimpleItem("camoshoes2", () -> new Item.Properties().humanoidArmor(armorCamo2, ArmorType.BOOTS));
    private static final TagKey<Item> EMPTY = ItemTags.create(RRIdentifiers.create("empty"));

    private static ArmorMaterial registerMaterial(String id, int boots, int leggings, int chestplate, int helmet, int enchantmentValue, ResourceKey<EquipmentAsset> equipmentAssetResourceKey) {
        return new ArmorMaterial(100, Util.make(new EnumMap<>(ArmorType.class), map -> {
            map.put(ArmorType.BOOTS, boots);
            map.put(ArmorType.LEGGINGS, leggings);
            map.put(ArmorType.CHESTPLATE, chestplate);
            map.put(ArmorType.HELMET, helmet);
            map.put(ArmorType.BODY, chestplate);
        }), enchantmentValue, SoundEvents.ARMOR_EQUIP_GENERIC, 1, 1, EMPTY, equipmentAssetResourceKey);
    }

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
        CREATIVE_MODE_TABS.register(bus);
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
