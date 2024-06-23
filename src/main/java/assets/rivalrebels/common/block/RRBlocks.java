package assets.rivalrebels.common.block;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.autobuilds.*;
import assets.rivalrebels.common.block.crate.*;
import assets.rivalrebels.common.block.machine.*;
import assets.rivalrebels.common.block.trap.*;
import assets.rivalrebels.common.item.RRItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import static assets.rivalrebels.common.item.RRItems.rralltab;

public class RRBlocks {
    public static Block amario = register("amario", new BlockMario(Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 10F)), rralltab);
    public static Block aquicksand = register("aquicksand", new BlockQuickSand(Properties.of().mapColor(MapColor.DIRT).strength(0.5F).noOcclusion().noCollission()), rralltab);
    public static Block barricade = register("barricade", new BlockAutoBarricade(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(0.7F)), rralltab);
    public static Block tower = register("tower", new BlockAutoTower(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(0.7F)), rralltab);
    public static Block easteregg = register("easteregg", new BlockAutoEaster(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(0.5F)), rralltab);
    public static Block bunker = register("bunker", new BlockAutoBunker(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(0.5F)), rralltab);
    public static Block smartcamo = register("smartcamo", new BlockSmartCamo(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops()), rralltab);
    public static Block camo1 = register("camo1", new Block(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(2F, 25F)), rralltab);
    public static Block camo2 = register("camo2", new Block(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(2F, 25F)), rralltab);
    public static Block camo3 = register("camo3", new Block(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(2F, 25F)), rralltab);
    public static Block steel = register("steel", new BlockSteel(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(1F, 10F).noOcclusion()), rralltab);
    public static Block flag1 = register("flag1", new BlockFlag(Properties.of().ignitedByLava().noOcclusion().noCollission(), "bi"));
    public static Block flag2 = register("flag2", new BlockFlag(Properties.of().ignitedByLava().noOcclusion().noCollission(), "dd"));
    public static Block flag3 = register("flag3", new BlockFlag(Properties.of().ignitedByLava().noOcclusion().noCollission(), "ar"));
    public static Block flag4 = register("flag4", new BlockFlag(Properties.of().ignitedByLava().noOcclusion().noCollission(), "aw"));
    public static Block flag5 = register("flag5", new BlockFlag(Properties.of().ignitedByLava().noOcclusion().noCollission(), "aq"));
    public static Block flag6 = register("flag6", new BlockFlag(Properties.of().ignitedByLava().noOcclusion().noCollission(), "al"));
    public static Block flag7 = register("flag7", new BlockFlag(Properties.of().ignitedByLava().noOcclusion().noCollission(), "aj"));
    public static Block flagbox1 = register("flagbox1", new BlockFlagBox1(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(-1.0F, 3600000.0F)), rralltab);
    public static Block flagbox5 = register("flagbox5", new BlockFlagBox5(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(-1.0F, 3600000.0F)), rralltab);
    public static Block flagbox6 = register("flagbox6", new BlockFlagBox6(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(-1.0F, 3600000.0F)), rralltab);
    public static Block flagbox3 = register("flagbox3", new BlockFlagBox3(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(-1.0F, 3600000.0F)), rralltab);
    public static Block flagbox4 = register("flagbox4", new BlockFlagBox4(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(-1.0F, 3600000.0F)), rralltab);
    public static Block flagbox7 = register("flagbox7", new BlockFlagBox7(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(-1.0F, 3600000.0F)), rralltab);
    public static Block sigmaarmor = register("sigmaarmor", new BlockSigmaArmor(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(0.5F, 10F)), rralltab);
    public static Block omegaarmor = register("omegaarmor", new BlockOmegaArmor(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(0.5F, 10F)), rralltab);
    public static Block weapons = register("weapons", new BlockWeapons(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(0.5F, 10F).noLootTable()), rralltab);
    public static Block ammunition = register("ammunition", new BlockAmmunition(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(0.5F, 10F)), rralltab);
    public static Block explosives = register("explosives", new BlockExplosives(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(0.5F, 10F)), rralltab);
    public static Block supplies = register("supplies", new BlockSupplies(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(0.5F, 10F)), rralltab);
    public static Block mario = register("mario", new BlockMario(Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 10F)));
    public static Block jump = register("jump", new BlockJump(Properties.of().mapColor(MapColor.COLOR_YELLOW).noOcclusion().strength(2.0F, 5F)), rralltab);
    public static Block quicksand = register("quicksand", new BlockQuickSand(Properties.of().mapColor(MapColor.DIRT).noOcclusion().strength(0.5F).noCollission()));
    public static Block landmine = register("landmine", new BlockLandMine(Properties.of().mapColor(MapColor.COLOR_GREEN).pushReaction(PushReaction.DESTROY).strength(0.6F, 1F)));
    public static Block remotecharge = register("remotecharge", new BlockRemoteCharge(Properties.of().ignitedByLava().randomTicks().noLootTable().noOcclusion().strength(0.5F)), rralltab);
    public static Block timedbomb = register("timedbomb", new BlockTimedBomb(Properties.of().mapColor(MapColor.DIRT).noLootTable()), rralltab);
    public static Block flare = register("flare", new BlockFlare(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().noOcclusion().noCollission().lightLevel(state -> 5)), rralltab);
    public static Block light = register("light", new BlockLight(Properties.of().pushReaction(PushReaction.BLOCK).noCollission().noOcclusion().noLootTable().lightLevel(state -> 15), 23));
    public static Block light2 = register("light2", new BlockLight(Properties.of().pushReaction(PushReaction.BLOCK).noCollission().noOcclusion().noLootTable().lightLevel(state -> 15), -1));
    public static Block cycle = register("cycle", new BlockCycle(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops()), rralltab);
    public static Block toxicgas = register("toxicgas", new BlockToxicGas(Properties.of().pushReaction(PushReaction.BLOCK).noLootTable().noCollission().randomTicks().noOcclusion().strength(-1.0F, 3600000.0F)));
    public static Block fshield = register("fshield", new BlockForceShield(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noLootTable().noOcclusion().strength(0.2F, 10F).lightLevel(state -> 4)), rralltab);
    public static Block gamestart = register("gamestart", new BlockGameStart(Properties.of().mapColor(MapColor.COLOR_BROWN)), rralltab);
    public static Block breadbox = register("breadbox", new BlockBreadBox(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F)), rralltab);
    public static Block alandmine = register("alandmine", new BlockLandMine(Properties.of().mapColor(MapColor.COLOR_GREEN).pushReaction(PushReaction.DESTROY).strength(0.6F, 1F)), rralltab);
    public static Block nuclearBomb = register("nuclear_bomb", new BlockNuclearBomb(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noOcclusion().noLootTable().strength(5.0F)));
    public static Block nukeCrateTop = register("nuke_crate_top", new BlockNukeCrate(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noOcclusion().strength(0.5F)), rralltab);
    public static Block nukeCrateBottom = register("nuke_crate_bottom", new BlockNukeCrate(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noOcclusion().strength(0.5F)), rralltab);
    public static Block radioactivedirt = register("radioactive_dirt", new BlockRadioactiveDirt(Properties.of().mapColor(MapColor.DIRT).strength(0.5F)), rralltab);
    public static Block radioactivesand = register("radioactive_sand", new BlockRadioactiveSand(Properties.of().mapColor(MapColor.COLOR_GREEN).pushReaction(PushReaction.DESTROY).strength(0.5F)), rralltab);
    public static Block plasmaexplosion = register("plasma_explosion", new BlockPlasmaExplosion(Properties.of().pushReaction(PushReaction.BLOCK).noCollission().noOcclusion().noLootTable().lightLevel(state -> 15)));
    public static Block reactor = register("reactor", new BlockReactor(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noOcclusion().strength(1.0F, 10F).lightLevel(state -> 15)), new Item.Properties());
    public static Block loader = register("loader", new BlockLoader(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noLootTable().noOcclusion().strength(2.0F, 10F)), rralltab);
    public static Block omegaobj = register("omegaobj", new BlockOmegaObjective(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noLootTable().noOcclusion().strength(1F, 10F).lightLevel(state -> 15)), rralltab);
    public static Block sigmaobj = register("sigmaobj", new BlockSigmaObjective(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noLootTable().noOcclusion().strength(1F, 10F).lightLevel(state -> 15)), rralltab);
    public static Block petrifiedwood = register("petrifiedwood", new BlockPetrifiedWood(Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.25F, 10F)), rralltab);
    public static Block petrifiedstone1 = register("petrifiedstone1", new BlockPetrifiedStone(Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 10F)), rralltab);
    public static Block petrifiedstone2 = register("petrifiedstone2", new BlockPetrifiedStone(Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 10F)), rralltab);
    public static Block petrifiedstone3 = register("petrifiedstone3", new BlockPetrifiedStone(Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 10F)), rralltab);
    public static Block petrifiedstone4 = register("petrifiedstone4", new BlockPetrifiedStone(Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 10F)), rralltab);
    public static Block tsarbombablock = register("tsarbombablock", new BlockTsarBomba(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noLootTable().noOcclusion().strength(5.0F)));
    public static Block forcefieldnode = register("forcefieldnode", new BlockForceFieldNode(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noOcclusion().strength(5)), rralltab);
    public static Block goreblock = register("goreblock", new BlockGore(Properties.of().pushReaction(PushReaction.DESTROY).noLootTable().noCollission().noOcclusion().instabreak()), rralltab);
    public static Block reactive = register("reactive", new BlockReactive(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noLootTable().strength(2, 100)), rralltab);
    public static Block bastion = register("bastion", new BlockAutoForceField(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(0.5F, 10F)), rralltab);
    public static Block conduit = register("conduit", new BlockConduit(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noOcclusion().strength(0.5F, 10F)), rralltab);
    public static Block controller = register("laptop", new BlockLaptop(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noLootTable().noOcclusion().strength(0.3F, 1F)), new Item.Properties());
    public static Block mariotrap = register("mariotrap", new BlockAutoMarioTrap(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(0.5F, 10F)), rralltab);
    public static Block minetrap = register("minetrap", new BlockAutoMineTrap(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(0.5F, 10F)), rralltab);
    public static Block quicksandtrap = register("quicksandtrap", new BlockAutoQuickSandTrap(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(0.5F, 10F)), rralltab);
    public static Block forcefield = register("forcefield", new BlockForceField(Properties.of().noLootTable().noOcclusion().strength(1000000F, 1000000F).lightLevel(state -> 7)));
    public static Block meltdown = register("meltdown", new BlockMeltDown(Properties.of().pushReaction(PushReaction.BLOCK).noCollission().noOcclusion().noLootTable().strength(0.5F, 10F)));
    public static Block ffreciever = register("ffreciever", new BlockReciever(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noLootTable().noOcclusion().strength(2, 100)), rralltab);
    public static Block buildrhodes = register("buildrhodes", new BlockRhodesScaffold(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(2F, 100F)), rralltab);
    public static Block rhodesactivator = register("rhodesactivator", new BlockRhodesActivator(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(0.1F, 100F)), rralltab);
    public static Block theoreticaltsarbombablock = register("theoreticaltsarbombablock", new BlockTheoreticalTsarBomba(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noOcclusion().noLootTable().strength(5).lightLevel(state -> 6)));
    public static Block antimatterbombblock = register("antimatterbombblock", new BlockAntimatterBomb(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noOcclusion().noLootTable().strength(5).lightLevel(state -> 6)));
    public static Block tachyonbombblock = register("tachyonbombblock", new BlockTachyonBomb(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noLootTable().noOcclusion().strength(5).lightLevel(state -> 6)));

    private static <T extends Block> T register(String name, T block) {
        return Registry.register(BuiltInRegistries.BLOCK, RRIdentifiers.create(name), block);
    }

    private static <T extends Block> T register(String name, T block, CreativeModeTab group) {
        return register(name, block, new Item.Properties());
    }

    public static void init() {}

    private static <T extends Block> T register(String name, T block, Item.Properties settings) {
        return register(name, block, new BlockItem(block, settings));
    }

    private static <T extends Block> T register(String name, T block, Item item) {
        RRItems.register(name, item);
        return register(name, block);
    }

    @Environment(EnvType.CLIENT)
    public static void registerBlockColors() {
        ColorProviderRegistry.BLOCK.register((state, worldIn, pos, tintIndex) -> {
            BlockCycle block = (BlockCycle) state.getBlock();
            block.phase += block.phaseadd;
            int r = (int) ((Math.sin(block.phase + block.pShiftR) + 1f) * 128f);
            int g = (int) ((Math.sin(block.phase + block.pShiftG) + 1f) * 128f);
            int b = (int) ((Math.sin(block.phase + block.pShiftB) + 1f) * 128f);
            return (r & 0xff) << 16 | (g & 0xff) << 8 | b & 0xff;
        }, RRBlocks.cycle);
        ColorProviderRegistry.BLOCK.register((state, worldIn, pos, tintIndex) -> state.getValue(BlockPetrifiedStone.META) * 1118481, RRBlocks.petrifiedstone1, RRBlocks.petrifiedstone2, RRBlocks.petrifiedstone3, RRBlocks.petrifiedstone4);
        ColorProviderRegistry.BLOCK.register((state, worldIn, pos, tintIndex) -> state.getValue(BlockPetrifiedWood.META) * 1118481, RRBlocks.petrifiedwood);
        ColorProviderRegistry.BLOCK.register((state, worldIn, pos, tintIndex) -> ColorProviderRegistry.BLOCK.get(Blocks.GRASS_BLOCK).getColor(Blocks.GRASS_BLOCK.defaultBlockState(), Minecraft.getInstance().level, pos, tintIndex), RRBlocks.mario, RRBlocks.landmine, RRBlocks.quicksand);
    }
}
