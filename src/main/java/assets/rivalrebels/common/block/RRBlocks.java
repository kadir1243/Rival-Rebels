package assets.rivalrebels.common.block;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.autobuilds.*;
import assets.rivalrebels.common.block.crate.*;
import assets.rivalrebels.common.block.machine.*;
import assets.rivalrebels.common.block.trap.*;
import assets.rivalrebels.common.item.RRItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static assets.rivalrebels.common.item.RRItems.rralltab;

public class RRBlocks {
    public static Block amario = register("amario", new BlockMario(Settings.create().mapColor(MapColor.STONE_GRAY).requiresTool().strength(1.5F, 10F)), rralltab);
    public static Block aquicksand = register("aquicksand", new BlockQuickSand(Settings.create().mapColor(MapColor.DIRT_BROWN).strength(0.5F).nonOpaque().noCollision()), rralltab);
    public static Block barricade = register("barricade", new BlockAutoBarricade(Settings.create().mapColor(MapColor.BROWN).burnable().strength(0.7F)), rralltab);
    public static Block tower = register("tower", new BlockAutoTower(Settings.create().mapColor(MapColor.BROWN).burnable().strength(0.7F)), rralltab);
    public static Block easteregg = register("easteregg", new BlockAutoEaster(Settings.create().mapColor(MapColor.BROWN).burnable().strength(0.5F)), rralltab);
    public static Block bunker = register("bunker", new BlockAutoBunker(Settings.create().mapColor(MapColor.BROWN).burnable().strength(0.5F)), rralltab);
    public static Block smartcamo = register("smartcamo", new BlockSmartCamo(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool()), rralltab);
    public static Block camo1 = register("camo1", new Block(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().strength(2F, 25F)), rralltab);
    public static Block camo2 = register("camo2", new Block(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().strength(2F, 25F)), rralltab);
    public static Block camo3 = register("camo3", new Block(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().strength(2F, 25F)), rralltab);
    public static Block steel = register("steel", new BlockSteel(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().strength(1F, 10F).nonOpaque()), rralltab);
    public static Block flag1 = register("flag1", new BlockFlag(Settings.create().burnable().nonOpaque().noCollision(), "bi"));
    public static Block flag2 = register("flag2", new BlockFlag(Settings.create().burnable().nonOpaque().noCollision(), "dd"));
    public static Block flag3 = register("flag3", new BlockFlag(Settings.create().burnable().nonOpaque().noCollision(), "ar"));
    public static Block flag4 = register("flag4", new BlockFlag(Settings.create().burnable().nonOpaque().noCollision(), "aw"));
    public static Block flag5 = register("flag5", new BlockFlag(Settings.create().burnable().nonOpaque().noCollision(), "aq"));
    public static Block flag6 = register("flag6", new BlockFlag(Settings.create().burnable().nonOpaque().noCollision(), "al"));
    public static Block flag7 = register("flag7", new BlockFlag(Settings.create().burnable().nonOpaque().noCollision(), "aj"));
    public static Block flagbox1 = register("flagbox1", new BlockFlagBox1(Settings.create().mapColor(MapColor.BROWN).burnable().dropsNothing().strength(-1.0F, 3600000.0F)), rralltab);
    public static Block flagbox5 = register("flagbox5", new BlockFlagBox5(Settings.create().mapColor(MapColor.BROWN).burnable().dropsNothing().strength(-1.0F, 3600000.0F)), rralltab);
    public static Block flagbox6 = register("flagbox6", new BlockFlagBox6(Settings.create().mapColor(MapColor.BROWN).burnable().dropsNothing().strength(-1.0F, 3600000.0F)), rralltab);
    public static Block flagbox3 = register("flagbox3", new BlockFlagBox3(Settings.create().mapColor(MapColor.BROWN).burnable().dropsNothing().strength(-1.0F, 3600000.0F)), rralltab);
    public static Block flagbox4 = register("flagbox4", new BlockFlagBox4(Settings.create().mapColor(MapColor.BROWN).burnable().dropsNothing().strength(-1.0F, 3600000.0F)), rralltab);
    public static Block flagbox7 = register("flagbox7", new BlockFlagBox7(Settings.create().mapColor(MapColor.BROWN).burnable().dropsNothing().strength(-1.0F, 3600000.0F)), rralltab);
    public static Block sigmaarmor = register("sigmaarmor", new BlockSigmaArmor(Settings.create().mapColor(MapColor.BROWN).burnable().dropsNothing().strength(0.5F, 10F)), rralltab);
    public static Block omegaarmor = register("omegaarmor", new BlockOmegaArmor(Settings.create().mapColor(MapColor.BROWN).burnable().dropsNothing().strength(0.5F, 10F)), rralltab);
    public static Block weapons = register("weapons", new BlockWeapons(Settings.create().mapColor(MapColor.BROWN).burnable().strength(0.5F, 10F).dropsNothing()), rralltab);
    public static Block ammunition = register("ammunition", new BlockAmmunition(Settings.create().mapColor(MapColor.BROWN).burnable().dropsNothing().strength(0.5F, 10F)), rralltab);
    public static Block explosives = register("explosives", new BlockExplosives(Settings.create().mapColor(MapColor.BROWN).burnable().dropsNothing().strength(0.5F, 10F)), rralltab);
    public static Block supplies = register("supplies", new BlockSupplies(Settings.create().mapColor(MapColor.BROWN).burnable().dropsNothing().strength(0.5F, 10F)), rralltab);
    public static Block mario = register("mario", new BlockMario(Settings.create().mapColor(MapColor.STONE_GRAY).requiresTool().strength(1.5F, 10F)));
    public static Block jump = register("jump", new BlockJump(Settings.create().mapColor(MapColor.YELLOW).nonOpaque().strength(2.0F, 5F)), rralltab);
    public static Block quicksand = register("quicksand", new BlockQuickSand(Settings.create().mapColor(MapColor.DIRT_BROWN).nonOpaque().strength(0.5F).noCollision()));
    public static Block landmine = register("landmine", new BlockLandMine(Settings.create().mapColor(MapColor.GREEN).pistonBehavior(PistonBehavior.DESTROY).strength(0.6F, 1F)));
    public static Block remotecharge = register("remotecharge", new BlockRemoteCharge(Settings.create().burnable().ticksRandomly().dropsNothing().nonOpaque().strength(0.5F)), rralltab);
    public static Block timedbomb = register("timedbomb", new BlockTimedBomb(Settings.create().mapColor(MapColor.DIRT_BROWN).dropsNothing()), rralltab);
    public static Block flare = register("flare", new BlockFlare(Settings.create().mapColor(MapColor.BROWN).burnable().dropsNothing().nonOpaque().noCollision().luminance(state -> 5)), rralltab);
    public static Block light = register("light", new BlockLight(Settings.create().pistonBehavior(PistonBehavior.BLOCK).noCollision().nonOpaque().dropsNothing().luminance(state -> 15), 23));
    public static Block light2 = register("light2", new BlockLight(Settings.create().pistonBehavior(PistonBehavior.BLOCK).noCollision().nonOpaque().dropsNothing().luminance(state -> 15), -1));
    public static Block cycle = register("cycle", new BlockCycle(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool()), rralltab);
    public static Block toxicgas = register("toxicgas", new BlockToxicGas(Settings.create().pistonBehavior(PistonBehavior.BLOCK).dropsNothing().noCollision().ticksRandomly().nonOpaque().strength(-1.0F, 3600000.0F)));
    public static Block fshield = register("fshield", new BlockForceShield(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().dropsNothing().nonOpaque().strength(0.2F, 10F).luminance(state -> 4)), rralltab);
    public static Block gamestart = register("gamestart", new BlockGameStart(Settings.create().mapColor(MapColor.BROWN)), rralltab);
    public static Block breadbox = register("breadbox", new BlockBreadBox(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().strength(-1.0F, 3600000.0F)), rralltab);
    public static Block alandmine = register("alandmine", new BlockLandMine(Settings.create().mapColor(MapColor.GREEN).pistonBehavior(PistonBehavior.DESTROY).strength(0.6F, 1F)), rralltab);
    public static Block nuclearBomb = register("nuclear_bomb", new BlockNuclearBomb(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().nonOpaque().dropsNothing().strength(5.0F)));
    public static Block nukeCrateTop = register("nuke_crate_top", new BlockNukeCrate(Settings.create().mapColor(MapColor.BROWN).burnable().nonOpaque().strength(0.5F)), rralltab);
    public static Block nukeCrateBottom = register("nuke_crate_bottom", new BlockNukeCrate(Settings.create().mapColor(MapColor.BROWN).burnable().nonOpaque().strength(0.5F)), rralltab);
    public static Block radioactivedirt = register("radioactive_dirt", new BlockRadioactiveDirt(Settings.create().mapColor(MapColor.DIRT_BROWN).strength(0.5F)), rralltab);
    public static Block radioactivesand = register("radioactive_sand", new BlockRadioactiveSand(Settings.create().mapColor(MapColor.GREEN).pistonBehavior(PistonBehavior.DESTROY).strength(0.5F)), rralltab);
    public static Block plasmaexplosion = register("plasma_explosion", new BlockPlasmaExplosion(Settings.create().pistonBehavior(PistonBehavior.BLOCK).noCollision().nonOpaque().dropsNothing().luminance(state -> 15)));
    public static Block reactor = register("reactor", new BlockReactor(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().nonOpaque().strength(1.0F, 10F).luminance(state -> 15)), new Item.Settings());
    public static Block loader = register("loader", new BlockLoader(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().dropsNothing().nonOpaque().strength(2.0F, 10F)), rralltab);
    public static Block omegaobj = register("omegaobj", new BlockOmegaObjective(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().dropsNothing().nonOpaque().strength(1F, 10F).luminance(state -> 15)), rralltab);
    public static Block sigmaobj = register("sigmaobj", new BlockSigmaObjective(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().dropsNothing().nonOpaque().strength(1F, 10F).luminance(state -> 15)), rralltab);
    public static Block petrifiedwood = register("petrifiedwood", new BlockPetrifiedWood(Settings.create().mapColor(MapColor.STONE_GRAY).requiresTool().strength(1.25F, 10F)), rralltab);
    public static Block petrifiedstone1 = register("petrifiedstone1", new BlockPetrifiedStone(Settings.create().mapColor(MapColor.STONE_GRAY).requiresTool().strength(1.5F, 10F)), rralltab);
    public static Block petrifiedstone2 = register("petrifiedstone2", new BlockPetrifiedStone(Settings.create().mapColor(MapColor.STONE_GRAY).requiresTool().strength(1.5F, 10F)), rralltab);
    public static Block petrifiedstone3 = register("petrifiedstone3", new BlockPetrifiedStone(Settings.create().mapColor(MapColor.STONE_GRAY).requiresTool().strength(1.5F, 10F)), rralltab);
    public static Block petrifiedstone4 = register("petrifiedstone4", new BlockPetrifiedStone(Settings.create().mapColor(MapColor.STONE_GRAY).requiresTool().strength(1.5F, 10F)), rralltab);
    public static Block tsarbombablock = register("tsarbombablock", new BlockTsarBomba(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().dropsNothing().nonOpaque().strength(5.0F)));
    public static Block forcefieldnode = register("forcefieldnode", new BlockForceFieldNode(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().nonOpaque().strength(5)), rralltab);
    public static Block goreblock = register("goreblock", new BlockGore(Settings.create().pistonBehavior(PistonBehavior.DESTROY).dropsNothing().noCollision().nonOpaque().breakInstantly()), rralltab);
    public static Block reactive = register("reactive", new BlockReactive(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().dropsNothing().strength(2, 100)), rralltab);
    public static Block bastion = register("bastion", new BlockAutoForceField(Settings.create().mapColor(MapColor.BROWN).burnable().strength(0.5F, 10F)), rralltab);
    public static Block conduit = register("conduit", new BlockConduit(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().nonOpaque().strength(0.5F, 10F)), rralltab);
    public static Block controller = register("laptop", new BlockLaptop(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().dropsNothing().nonOpaque().strength(0.3F, 1F)), new Item.Settings());
    public static Block mariotrap = register("mariotrap", new BlockAutoMarioTrap(Settings.create().mapColor(MapColor.BROWN).burnable().strength(0.5F, 10F)), rralltab);
    public static Block minetrap = register("minetrap", new BlockAutoMineTrap(Settings.create().mapColor(MapColor.BROWN).burnable().strength(0.5F, 10F)), rralltab);
    public static Block quicksandtrap = register("quicksandtrap", new BlockAutoQuickSandTrap(Settings.create().mapColor(MapColor.BROWN).burnable().strength(0.5F, 10F)), rralltab);
    public static Block forcefield = register("forcefield", new BlockForceField(Settings.create().dropsNothing().nonOpaque().strength(1000000F, 1000000F).luminance(state -> 7)));
    public static Block meltdown = register("meltdown", new BlockMeltDown(Settings.create().pistonBehavior(PistonBehavior.BLOCK).noCollision().nonOpaque().dropsNothing().strength(0.5F, 10F)));
    public static Block ffreciever = register("ffreciever", new BlockReciever(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().dropsNothing().nonOpaque().strength(2, 100)), rralltab);
    public static Block buildrhodes = register("buildrhodes", new BlockRhodesScaffold(Settings.create().mapColor(MapColor.BROWN).burnable().strength(2F, 100F)), rralltab);
    public static Block rhodesactivator = register("rhodesactivator", new BlockRhodesActivator(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().strength(0.1F, 100F)), rralltab);
    public static Block theoreticaltsarbombablock = register("theoreticaltsarbombablock", new BlockTheoreticalTsarBomba(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().nonOpaque().dropsNothing().strength(5).luminance(state -> 6)));
    public static Block antimatterbombblock = register("antimatterbombblock", new BlockAntimatterBomb(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().nonOpaque().dropsNothing().strength(5).luminance(state -> 6)));
    public static Block tachyonbombblock = register("tachyonbombblock", new BlockTachyonBomb(Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool().dropsNothing().nonOpaque().strength(5).luminance(state -> 6)));

    private static <T extends Block> T register(String name, T block) {
        return Registry.register(Registries.BLOCK, new Identifier(RivalRebels.MODID, name), block);
    }

    private static <T extends Block> T register(String name, T block, ItemGroup group) {
        return register(name, block, new Item.Settings());
    }

    public static void init() {}

    private static <T extends Block> T register(String name, T block, Item.Settings settings) {
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
        ColorProviderRegistry.BLOCK.register((state, worldIn, pos, tintIndex) -> state.get(BlockPetrifiedStone.META) * 1118481, RRBlocks.petrifiedstone1, RRBlocks.petrifiedstone2, RRBlocks.petrifiedstone3, RRBlocks.petrifiedstone4);
        ColorProviderRegistry.BLOCK.register((state, worldIn, pos, tintIndex) -> state.get(BlockPetrifiedWood.META) * 1118481, RRBlocks.petrifiedwood);
        ColorProviderRegistry.BLOCK.register((state, worldIn, pos, tintIndex) -> ColorProviderRegistry.BLOCK.get(Blocks.GRASS_BLOCK).getColor(Blocks.GRASS_BLOCK.getDefaultState(), MinecraftClient.getInstance().world, pos, tintIndex), RRBlocks.mario, RRBlocks.landmine, RRBlocks.quicksand);
    }
}
