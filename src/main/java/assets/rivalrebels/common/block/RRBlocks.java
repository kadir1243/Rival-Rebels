package assets.rivalrebels.common.block;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.client.itemrenders.LaptopRenderer;
import assets.rivalrebels.client.itemrenders.LoaderRenderer;
import assets.rivalrebels.client.itemrenders.ReactorRenderer;
import assets.rivalrebels.common.block.autobuilds.*;
import assets.rivalrebels.common.block.crate.*;
import assets.rivalrebels.common.block.machine.*;
import assets.rivalrebels.common.block.trap.*;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static assets.rivalrebels.common.item.RRItems.rralltab;
import static assets.rivalrebels.common.item.RRItems.rrarmortab;

public class RRBlocks {
    public static final Set<Block> BLOCKS = new HashSet<>();
    public static final Set<Item> BLOCK_ITEMS = new HashSet<>();
    public static Block amario = register("amario", new BlockMario(Settings.of(Material.STONE).strength(1.5F, 10F)), rralltab);
    public static Block aquicksand = register("aquicksand", new BlockQuickSand(Settings.of(Material.SOIL).strength(0.5F).nonOpaque().noCollision()), rralltab);
    public static Block barricade = register("barricade", new BlockAutoBarricade(Settings.of(Material.WOOD).strength(0.7F)), rralltab);
    public static Block tower = register("tower", new BlockAutoTower(Settings.of(Material.WOOD).strength(0.7F)), rralltab);
    public static Block easteregg = register("easteregg", new BlockAutoEaster(Settings.of(Material.WOOD).strength(0.5F)), rralltab);
    public static Block bunker = register("bunker", new BlockAutoBunker(Settings.of(Material.WOOD).strength(0.5F)), rralltab);
    public static Block smartcamo = register("smartcamo", new BlockSmartCamo(Settings.of(Material.METAL)), rralltab);
    public static Block camo1 = register("camo1", new Block(Settings.of(Material.METAL).strength(2F, 25F)), rralltab);
    public static Block camo2 = register("camo2", new Block(Settings.of(Material.METAL).strength(2F, 25F)), rralltab);
    public static Block camo3 = register("camo3", new Block(Settings.of(Material.METAL).strength(2F, 25F)), rralltab);
    public static Block steel = register("steel", new BlockSteel(Settings.of(Material.METAL).strength(1F, 10F).nonOpaque()), rralltab);
    public static Block flag1 = register("flag1", new BlockFlag(Settings.of(Material.WOOL).nonOpaque().noCollision(), "bi"));
    public static Block flag2 = register("flag2", new BlockFlag(Settings.of(Material.WOOL).nonOpaque().noCollision(), "dd"));
    public static Block flag3 = register("flag3", new BlockFlag(Settings.of(Material.WOOL).nonOpaque().noCollision(), "ar"));
    public static Block flag4 = register("flag4", new BlockFlag(Settings.of(Material.WOOL).nonOpaque().noCollision(), "aw"));
    public static Block flag5 = register("flag5", new BlockFlag(Settings.of(Material.WOOL).nonOpaque().noCollision(), "aq"));
    public static Block flag6 = register("flag6", new BlockFlag(Settings.of(Material.WOOL).nonOpaque().noCollision(), "al"));
    public static Block flag7 = register("flag7", new BlockFlag(Settings.of(Material.WOOL).nonOpaque().noCollision(), "aj"));
    public static Block flagbox1 = register("flagbox1", new BlockFlagBox1(Settings.of(Material.WOOD).dropsNothing().strength(-1.0F, 3600000.0F)), rrarmortab);
    public static Block flagbox5 = register("flagbox5", new BlockFlagBox5(Settings.of(Material.WOOD).dropsNothing().strength(-1.0F, 3600000.0F)), rrarmortab);
    public static Block flagbox6 = register("flagbox6", new BlockFlagBox6(Settings.of(Material.WOOD).dropsNothing().strength(-1.0F, 3600000.0F)), rrarmortab);
    public static Block flagbox3 = register("flagbox3", new BlockFlagBox3(Settings.of(Material.WOOD).dropsNothing().strength(-1.0F, 3600000.0F)), rrarmortab);
    public static Block flagbox4 = register("flagbox4", new BlockFlagBox4(Settings.of(Material.WOOD).dropsNothing().strength(-1.0F, 3600000.0F)), rrarmortab);
    public static Block flagbox7 = register("flagbox7", new BlockFlagBox7(Settings.of(Material.WOOD).dropsNothing().strength(-1.0F, 3600000.0F)), rrarmortab);
    public static Block sigmaarmor = register("sigmaarmor", new BlockSigmaArmor(Settings.of(Material.WOOD).dropsNothing().strength(0.5F, 10F)), rrarmortab);
    public static Block omegaarmor = register("omegaarmor", new BlockOmegaArmor(Settings.of(Material.WOOD).dropsNothing().strength(0.5F, 10F)), rrarmortab);
    public static Block weapons = register("weapons", new BlockWeapons(Settings.of(Material.WOOD).strength(0.5F, 10F).dropsNothing()), rralltab);
    public static Block ammunition = register("ammunition", new BlockAmmunition(Settings.of(Material.WOOD).dropsNothing().strength(0.5F, 10F)), rralltab);
    public static Block explosives = register("explosives", new BlockExplosives(Settings.of(Material.WOOD).dropsNothing().strength(0.5F, 10F)), rralltab);
    public static Block supplies = register("supplies", new BlockSupplies(Settings.of(Material.WOOD).dropsNothing().strength(0.5F, 10F)), rralltab);
    public static Block mario = register("mario", new BlockMario(Settings.of(Material.STONE).strength(1.5F, 10F)));
    public static Block jump = register("jump", new BlockJump(Settings.of(Material.SPONGE).nonOpaque().strength(2.0F, 5F)), rralltab);
    public static Block quicksand = register("quicksand", new BlockQuickSand(Settings.of(Material.SOIL).nonOpaque().strength(0.5F).noCollision()));
    public static Block landmine = register("landmine", new BlockLandMine(Settings.of(Material.AGGREGATE).strength(0.6F, 1F)));
    public static Block remotecharge = register("remotecharge", new BlockRemoteCharge(Settings.of(Material.WOOL).ticksRandomly().dropsNothing().nonOpaque().strength(0.5F)), rralltab);
    public static Block timedbomb = register("timedbomb", new BlockTimedBomb(), rralltab);
    public static Block flare = register("flare", new BlockFlare(), rralltab);
    public static Block light = register("light", new BlockLight(Settings.of(Material.PORTAL).noCollision().nonOpaque().dropsNothing().luminance(state -> 15), 23));
    public static Block light2 = register("light2", new BlockLight(Settings.of(Material.PORTAL).noCollision().nonOpaque().dropsNothing().luminance(state -> 15), -1));
    public static Block cycle = register("cycle", new BlockCycle(), rrarmortab);
    public static Block toxicgas = register("toxicgas", new BlockToxicGas(Settings.of(Material.CACTUS).dropsNothing().noCollision().ticksRandomly().nonOpaque().strength(-1.0F, 3600000.0F)));
    public static Block fshield = register("fshield", new BlockForceShield(Settings.of(Material.METAL).dropsNothing().nonOpaque().strength(0.2F, 10F).luminance(state -> 4)), rralltab);
    public static Block gamestart = register("gamestart", new BlockGameStart(), rralltab);
    public static Block breadbox = register("breadbox", new BlockBreadBox(Settings.of(Material.METAL).strength(-1.0F, 3600000.0F)), rralltab);
    public static Block alandmine = register("alandmine", new BlockLandMine(Settings.of(Material.AGGREGATE).strength(0.6F, 1F)), rralltab);
    public static Block nuclearBomb = register("nuclear_bomb", new BlockNuclearBomb(Settings.of(Material.METAL).nonOpaque().dropsNothing().strength(5.0F)));
    public static Block nukeCrateTop = register("nuke_crate_top", new BlockNukeCrate(Settings.of(Material.WOOD).nonOpaque().strength(0.5F)), rralltab);
    public static Block nukeCrateBottom = register("nuke_crate_bottom", new BlockNukeCrate(Settings.of(Material.WOOD).nonOpaque().strength(0.5F)), rralltab);
    public static Block radioactivedirt = register("radioactive_dirt", new BlockRadioactiveDirt(Settings.of(Material.SOIL).strength(0.5F)), rralltab);
    public static Block radioactivesand = register("radioactive_sand", new BlockRadioactiveSand(Settings.of(Material.AGGREGATE).strength(0.5F)), rralltab);
    public static Block plasmaexplosion = register("plasma_explosion", new BlockPlasmaExplosion(Settings.of(Material.PORTAL).noCollision().nonOpaque().dropsNothing().luminance(state -> 15)));
    public static Block reactor = register("reactor", new BlockReactor(Settings.of(Material.METAL).nonOpaque().strength(1.0F, 10F).luminance(state -> 15)), new Item.Settings(), ReactorRenderer::new);
    public static Block loader = register("loader", new BlockLoader(Settings.of(Material.METAL).dropsNothing().nonOpaque().strength(2.0F, 10F)), new Item.Settings().group(rralltab), LoaderRenderer::new);
    public static Block omegaobj = register("omegaobj", new BlockOmegaObjective(Settings.of(Material.METAL).dropsNothing().nonOpaque().strength(1F, 10F).luminance(state -> 15)), rralltab);
    public static Block sigmaobj = register("sigmaobj", new BlockSigmaObjective(Settings.of(Material.METAL).dropsNothing().nonOpaque().strength(1F, 10F).luminance(state -> 15)), rralltab);
    public static Block petrifiedwood = register("petrifiedwood", new BlockPetrifiedWood(Settings.of(Material.STONE).strength(1.25F, 10F)), rralltab);
    public static Block petrifiedstone1 = register("petrifiedstone1", new BlockPetrifiedStone("c"), rralltab);
    public static Block petrifiedstone2 = register("petrifiedstone2", new BlockPetrifiedStone("d"), rralltab);
    public static Block petrifiedstone3 = register("petrifiedstone3", new BlockPetrifiedStone("e"), rralltab);
    public static Block petrifiedstone4 = register("petrifiedstone4", new BlockPetrifiedStone("f"), rralltab);
    public static Block tsarbombablock = register("tsarbombablock", new BlockTsarBomba(Settings.of(Material.METAL).dropsNothing().nonOpaque().strength(5.0F)));
    public static Block forcefieldnode = register("forcefieldnode", new BlockForceFieldNode(Settings.of(Material.METAL).nonOpaque().strength(5)), rralltab);
    public static Block goreblock = register("goreblock", new BlockGore(Settings.of(Material.CAKE).dropsNothing().noCollision().nonOpaque().breakInstantly()), rrarmortab);
    public static Block reactive = register("reactive", new BlockReactive(Settings.of(Material.METAL).dropsNothing().strength(2, 100)), rralltab);
    public static Block bastion = register("bastion", new BlockAutoForceField(Settings.of(Material.WOOD).strength(0.5F, 10F)), rralltab);
    public static Block conduit = register("conduit", new BlockConduit(Settings.of(Material.METAL).nonOpaque().strength(0.5F, 10F)), rralltab);
    public static Block controller = register("laptop", new BlockLaptop(Settings.of(Material.METAL).dropsNothing().nonOpaque().strength(0.3F, 1F)), new Item.Settings().group(rralltab), LaptopRenderer::new);
    public static Block mariotrap = register("mariotrap", new BlockAutoMarioTrap(Settings.of(Material.WOOD).strength(0.5F, 10F)), rralltab);
    public static Block minetrap = register("minetrap", new BlockAutoMineTrap(Settings.of(Material.WOOD).strength(0.5F, 10F)), rralltab);
    public static Block quicksandtrap = register("quicksandtrap", new BlockAutoQuickSandTrap(Settings.of(Material.WOOD).strength(0.5F, 10F)), rralltab);
    public static Block forcefield = register("forcefield", new BlockForceField(Settings.of(Material.GLASS).dropsNothing().nonOpaque().strength(1000000F, 1000000F).luminance(state -> 7)));
    public static Block meltdown = register("meltdown", new BlockMeltDown(Settings.of(Material.PORTAL).noCollision().nonOpaque().dropsNothing().strength(0.5F, 10F)));
    public static Block ffreciever = register("ffreciever", new BlockReciever(Settings.of(Material.METAL).dropsNothing().nonOpaque().strength(2, 100)), rralltab);
    public static Block buildrhodes = register("buildrhodes", new BlockRhodesScaffold(Settings.of(Material.WOOD).strength(2F, 100F)), rralltab);
    public static Block rhodesactivator = register("rhodesactivator", new BlockRhodesActivator(Settings.of(Material.METAL).strength(0.1F, 100F)), rralltab);
    public static Block theoreticaltsarbombablock = register("theoreticaltsarbombablock", new BlockTheoreticalTsarBomba(Settings.of(Material.METAL).nonOpaque().dropsNothing().strength(5).luminance(state -> 6)));
    public static Block antimatterbombblock = register("antimatterbombblock", new BlockAntimatterBomb(Settings.of(Material.METAL).nonOpaque().dropsNothing().strength(5).luminance(state -> 6)));
    public static Block tachyonbombblock = register("tachyonbombblock", new BlockTachyonBomb(Settings.of(Material.METAL).dropsNothing().nonOpaque().strength(5).luminance(state -> 6)));

    private static <T extends Block> T register(String name, T block) {
        block.setRegistryName(RivalRebels.MODID, name);
        BLOCKS.add(block);
        return block;
    }

    private static <T extends Block> T register(String name, T block, ItemGroup group) {
        return register(name, block, new Item.Settings().group(group));
    }

    private static <T extends Block> T register(String name, T block, Item.Settings settings) {
        return register(name, block, new BlockItem(block, settings));
    }

    private static <T extends Block> T register(String name, T block, Item item) {
        item.setRegistryName(RivalRebels.MODID, name);
        BLOCK_ITEMS.add(item);
        return register(name, block);
    }

    private static <T extends Block> T register(String name, T block, Item.Settings settings, BiFunction<BlockEntityRenderDispatcher, EntityModelLoader, BuiltinModelItemRenderer> function) {
        BlockItem blockItem = new BlockItem(block, settings) {
            @Override
            public void initializeClient(Consumer<IItemRenderProperties> consumer) {
                consumer.accept(new IItemRenderProperties() {
                    @Override
                    public BuiltinModelItemRenderer getItemStackRenderer() {
                        return function.apply(MinecraftClient.getInstance().getBlockEntityRenderDispatcher(), MinecraftClient.getInstance().getEntityModelLoader());
                    }
                });
            }
        };
        return register(name, block, blockItem);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerBlockColors(ColorHandlerEvent.Block event) {
        BlockColors blockColors = event.getBlockColors();
        blockColors.registerColorProvider((state, worldIn, pos, tintIndex) -> {
            BlockCycle block = (BlockCycle) state.getBlock();
            block.phase += block.phaseadd;
            int r = (int) ((Math.sin(block.phase + block.pShiftR) + 1f) * 128f);
            int g = (int) ((Math.sin(block.phase + block.pShiftG) + 1f) * 128f);
            int b = (int) ((Math.sin(block.phase + block.pShiftB) + 1f) * 128f);
            return (r & 0xff) << 16 | (g & 0xff) << 8 | b & 0xff;
        }, RRBlocks.cycle);
        blockColors.registerColorProvider((state, worldIn, pos, tintIndex) -> state.get(BlockPetrifiedStone.META) * 1118481, RRBlocks.petrifiedstone1, RRBlocks.petrifiedstone2, RRBlocks.petrifiedstone3, RRBlocks.petrifiedstone4);
        blockColors.registerColorProvider((state, worldIn, pos, tintIndex) -> state.get(BlockPetrifiedWood.META) * 1118481, RRBlocks.petrifiedwood);
        blockColors.registerColorProvider((state, worldIn, pos, tintIndex) -> blockColors.getColor(Blocks.GRASS.getDefaultState(), MinecraftClient.getInstance().world, pos, tintIndex), RRBlocks.mario, RRBlocks.landmine, RRBlocks.quicksand);
    }
}
