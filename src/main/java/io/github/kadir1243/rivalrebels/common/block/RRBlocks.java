package io.github.kadir1243.rivalrebels.common.block;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.block.autobuilds.*;
import io.github.kadir1243.rivalrebels.common.block.crate.*;
import io.github.kadir1243.rivalrebels.common.block.machine.*;
import io.github.kadir1243.rivalrebels.common.block.trap.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RRBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(RRIdentifiers.MODID);
    public static final DeferredBlock<Block> amario = BLOCKS.registerBlock("amario", BlockMario::new, Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 10F));
    public static final DeferredBlock<Block> aquicksand = BLOCKS.registerBlock("aquicksand", BlockQuickSand::new, Properties.of().mapColor(MapColor.DIRT).strength(0.5F).noOcclusion().noCollission());
    public static final DeferredBlock<Block> barricade = BLOCKS.registerBlock("barricade", BlockAutoBarricade::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(0.7F));
    public static final DeferredBlock<Block> tower = BLOCKS.registerBlock("tower", BlockAutoTower::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(0.7F));
    public static final DeferredBlock<Block> easteregg = BLOCKS.registerBlock("easteregg", BlockAutoEaster::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(0.5F));
    public static final DeferredBlock<Block> bunker = BLOCKS.registerBlock("bunker", BlockAutoBunker::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(0.5F));
    public static final DeferredBlock<Block> smartcamo = BLOCKS.registerBlock("smartcamo", BlockSmartCamo::new, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> camo1 = BLOCKS.registerSimpleBlock("camo1", Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(2F, 25F));
    public static final DeferredBlock<Block> camo2 = BLOCKS.registerSimpleBlock("camo2", Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(2F, 25F));
    public static final DeferredBlock<Block> camo3 = BLOCKS.registerSimpleBlock("camo3", Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(2F, 25F));
    public static final DeferredBlock<Block> steel = BLOCKS.registerBlock("steel", BlockSteel::new, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(1F, 10F).noOcclusion());
    public static final DeferredBlock<Block> flagbox1 = BLOCKS.registerBlock("flagbox1", BlockFlagBox1::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(-1.0F, 3600000.0F));
    public static final DeferredBlock<Block> flagbox5 = BLOCKS.registerBlock("flagbox5", BlockFlagBox5::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(-1.0F, 3600000.0F));
    public static final DeferredBlock<Block> flagbox6 = BLOCKS.registerBlock("flagbox6", BlockFlagBox6::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(-1.0F, 3600000.0F));
    public static final DeferredBlock<Block> flagbox3 = BLOCKS.registerBlock("flagbox3", BlockFlagBox3::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(-1.0F, 3600000.0F));
    public static final DeferredBlock<Block> flagbox4 = BLOCKS.registerBlock("flagbox4", BlockFlagBox4::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(-1.0F, 3600000.0F));
    public static final DeferredBlock<Block> flagbox7 = BLOCKS.registerBlock("flagbox7", BlockFlagBox7::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(-1.0F, 3600000.0F));
    public static final DeferredBlock<Block> sigmaarmor = BLOCKS.registerBlock("sigmaarmor", BlockSigmaArmor::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(0.5F, 10F));
    public static final DeferredBlock<Block> omegaarmor = BLOCKS.registerBlock("omegaarmor", BlockOmegaArmor::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(0.5F, 10F));
    public static final DeferredBlock<Block> weapons = BLOCKS.registerBlock("weapons", BlockWeapons::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(0.5F, 10F).noLootTable());
    public static final DeferredBlock<Block> ammunition = BLOCKS.registerBlock("ammunition", BlockAmmunition::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(0.5F, 10F));
    public static final DeferredBlock<Block> explosives = BLOCKS.registerBlock("explosives", BlockExplosives::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(0.5F, 10F));
    public static final DeferredBlock<Block> supplies = BLOCKS.registerBlock("supplies", BlockSupplies::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().strength(0.5F, 10F));
    public static final DeferredBlock<Block> jump = BLOCKS.registerBlock("jump", BlockJump::new, Properties.of().mapColor(MapColor.COLOR_YELLOW).noOcclusion().strength(2.0F, 5F));
    public static final DeferredBlock<Block> remotecharge = BLOCKS.registerBlock("remotecharge", BlockRemoteCharge::new, Properties.of().ignitedByLava().randomTicks().noLootTable().noOcclusion().strength(0.5F));
    public static final DeferredBlock<Block> timedbomb = BLOCKS.registerBlock("timedbomb", BlockTimedBomb::new, Properties.of().mapColor(MapColor.DIRT).noLootTable());
    public static final DeferredBlock<Block> flare = BLOCKS.registerBlock("flare", BlockFlare::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noLootTable().noOcclusion().noCollission().lightLevel(state -> 5));
    public static final DeferredBlock<Block> cycle = BLOCKS.registerBlock("cycle", BlockCycle::new, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> fshield = BLOCKS.registerBlock("fshield", BlockForceShield::new, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noLootTable().noOcclusion().strength(0.2F, 10F).lightLevel(state -> 4));
    public static final DeferredBlock<Block> gamestart = BLOCKS.registerBlock("gamestart", BlockGameStart::new, Properties.of().mapColor(MapColor.COLOR_BROWN));
    public static final DeferredBlock<Block> breadbox = BLOCKS.registerBlock("breadbox", BlockBreadBox::new, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F));
    public static final DeferredBlock<Block> alandmine = BLOCKS.registerBlock("alandmine", BlockLandMine::new, Properties.of().mapColor(MapColor.COLOR_GREEN).pushReaction(PushReaction.DESTROY).strength(0.6F, 1F));
    public static final DeferredBlock<Block> nukeCrateTop = BLOCKS.registerBlock("nuke_crate_top", BlockNukeCrate::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noOcclusion().strength(0.5F));
    public static final DeferredBlock<Block> nukeCrateBottom = BLOCKS.registerBlock("nuke_crate_bottom", BlockNukeCrate::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noOcclusion().strength(0.5F));
    public static final DeferredBlock<Block> radioactivedirt = BLOCKS.registerBlock("radioactive_dirt", BlockRadioactiveDirt::new, Properties.of().mapColor(MapColor.DIRT).strength(0.5F));
    public static final DeferredBlock<Block> radioactivesand = BLOCKS.registerBlock("radioactive_sand", BlockRadioactiveSand::new, Properties.of().mapColor(MapColor.COLOR_GREEN).pushReaction(PushReaction.DESTROY).strength(0.5F));
    public static final DeferredBlock<Block> reactor = BLOCKS.registerBlock("reactor", BlockReactor::new, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noOcclusion().strength(1.0F, 10F).lightLevel(state -> 15));
    public static final DeferredBlock<Block> loader = BLOCKS.registerBlock("loader", BlockLoader::new, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noLootTable().noOcclusion().strength(2.0F, 10F));
    public static final DeferredBlock<Block> omegaobj = BLOCKS.registerBlock("omegaobj", BlockOmegaObjective::new, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noLootTable().noOcclusion().strength(1F, 10F).lightLevel(state -> 15));
    public static final DeferredBlock<Block> sigmaobj = BLOCKS.registerBlock("sigmaobj", BlockSigmaObjective::new, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noLootTable().noOcclusion().strength(1F, 10F).lightLevel(state -> 15));
    public static final DeferredBlock<Block> petrifiedwood = BLOCKS.registerBlock("petrifiedwood", BlockPetrifiedWood::new, Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.25F, 10F));
    public static final DeferredBlock<Block> petrifiedstone1 = BLOCKS.registerBlock("petrifiedstone1", BlockPetrifiedStone::new, Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 10F));
    public static final DeferredBlock<Block> petrifiedstone2 = BLOCKS.registerBlock("petrifiedstone2", BlockPetrifiedStone::new, Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 10F));
    public static final DeferredBlock<Block> petrifiedstone3 = BLOCKS.registerBlock("petrifiedstone3", BlockPetrifiedStone::new, Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 10F));
    public static final DeferredBlock<Block> petrifiedstone4 = BLOCKS.registerBlock("petrifiedstone4", BlockPetrifiedStone::new, Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 10F));
    public static final DeferredBlock<Block> forcefieldnode = BLOCKS.registerBlock("forcefieldnode", BlockForceFieldNode::new, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noOcclusion().strength(5));
    public static final DeferredBlock<Block> goreblock = BLOCKS.registerBlock("goreblock", BlockGore::new, Properties.of().pushReaction(PushReaction.DESTROY).noLootTable().noCollission().noOcclusion().instabreak());
    public static final DeferredBlock<Block> reactive = BLOCKS.registerBlock("reactive", BlockReactive::new, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noLootTable().strength(2, 100));
    public static final DeferredBlock<Block> bastion = BLOCKS.registerBlock("bastion", BlockAutoForceField::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(0.5F, 10F));
    public static final DeferredBlock<Block> conduit = BLOCKS.registerBlock("conduit", BlockConduit::new, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noOcclusion().strength(0.5F, 10F));
    public static final DeferredBlock<Block> controller = BLOCKS.registerBlock("laptop", BlockLaptop::new, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noLootTable().noOcclusion().strength(0.3F, 1F));
    public static final DeferredBlock<Block> mariotrap = BLOCKS.registerBlock("mariotrap", BlockAutoMarioTrap::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(0.5F, 10F));
    public static final DeferredBlock<Block> minetrap = BLOCKS.registerBlock("minetrap", BlockAutoMineTrap::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(0.5F, 10F));
    public static final DeferredBlock<Block> quicksandtrap = BLOCKS.registerBlock("quicksandtrap", BlockAutoQuickSandTrap::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(0.5F, 10F));
    public static final DeferredBlock<Block> ffreciever = BLOCKS.registerBlock("ffreciever", BlockReciever::new, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noLootTable().noOcclusion().strength(2, 100));
    public static final DeferredBlock<Block> buildrhodes = BLOCKS.registerBlock("buildrhodes", BlockRhodesScaffold::new, Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(2F, 100F));
    public static final DeferredBlock<Block> rhodesactivator = BLOCKS.registerBlock("rhodesactivator", BlockRhodesActivator::new, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(0.1F, 100F));
    public static final DeferredBlock<Block> flag1 = BLOCKS.registerBlock("flag1", p -> new BlockFlag(p, "bi"), Properties.of().ignitedByLava().noOcclusion().noCollission());
    public static final DeferredBlock<Block> trollFlag = BLOCKS.registerBlock("troll_flag", p -> new BlockFlag(p, "dd"), Properties.of().ignitedByLava().noOcclusion().noCollission());
    public static final DeferredBlock<Block> flag3 = BLOCKS.registerBlock("flag3", p -> new BlockFlag(p, "ar"), Properties.of().ignitedByLava().noOcclusion().noCollission());
    public static final DeferredBlock<Block> flag4 = BLOCKS.registerBlock("flag4", p -> new BlockFlag(p, "aw"), Properties.of().ignitedByLava().noOcclusion().noCollission());
    public static final DeferredBlock<Block> flag5 = BLOCKS.registerBlock("flag5", p -> new BlockFlag(p, "aq"), Properties.of().ignitedByLava().noOcclusion().noCollission());
    public static final DeferredBlock<Block> flag6 = BLOCKS.registerBlock("flag6", p -> new BlockFlag(p, "al"), Properties.of().ignitedByLava().noOcclusion().noCollission());
    public static final DeferredBlock<Block> flag7 = BLOCKS.registerBlock("flag7", p -> new BlockFlag(p, "aj"), Properties.of().ignitedByLava().noOcclusion().noCollission());
    public static final DeferredBlock<Block> light = BLOCKS.registerBlock("light", p -> new BlockLight(p, 23), Properties.of().pushReaction(PushReaction.BLOCK).noCollission().noOcclusion().noLootTable().lightLevel(state -> 15));
    public static final DeferredBlock<Block> light2 = BLOCKS.registerBlock("light2", p -> new BlockLight(p, -1), Properties.of().pushReaction(PushReaction.BLOCK).noCollission().noOcclusion().noLootTable().lightLevel(state -> 15));
    public static final DeferredBlock<Block> forcefield = BLOCKS.registerBlock("forcefield", BlockForceField::new, Properties.of().noLootTable().noOcclusion().strength(1000000F, 1000000F).lightLevel(state -> 7));
    public static final DeferredBlock<Block> meltdown = BLOCKS.registerBlock("meltdown", BlockMeltDown::new, Properties.of().pushReaction(PushReaction.BLOCK).noCollission().noOcclusion().noLootTable().strength(0.5F, 10F));
    public static final DeferredBlock<Block> plasmaexplosion = BLOCKS.registerBlock("plasma_explosion", BlockPlasmaExplosion::new, Properties.of().pushReaction(PushReaction.BLOCK).noCollission().noOcclusion().noLootTable().lightLevel(state -> 15));
    public static final DeferredBlock<Block> toxicgas = BLOCKS.registerBlock("toxicgas", BlockToxicGas::new, Properties.of().pushReaction(PushReaction.BLOCK).noLootTable().noCollission().randomTicks().noOcclusion().strength(-1.0F, 3600000.0F));
    public static final DeferredBlock<Block> tsarbombablock = BLOCKS.registerBlock("tsarbombablock", BlockTsarBomba::new, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noLootTable().noOcclusion().strength(5.0F));
    public static final DeferredBlock<Block> theoreticaltsarbombablock = BLOCKS.registerBlock("theoreticaltsarbombablock", BlockTheoreticalTsarBomba::new, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noOcclusion().noLootTable().strength(5).lightLevel(state -> 6));
    public static final DeferredBlock<Block> antimatterbombblock = BLOCKS.registerBlock("antimatterbombblock", BlockAntimatterBomb::new, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noOcclusion().noLootTable().strength(5).lightLevel(state -> 6));
    public static final DeferredBlock<Block> tachyonbombblock = BLOCKS.registerBlock("tachyonbombblock", BlockTachyonBomb::new, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noLootTable().noOcclusion().strength(5).lightLevel(state -> 6));
    public static final DeferredBlock<Block> nuclearBomb = BLOCKS.registerBlock("nuclear_bomb", BlockNuclearBomb::new, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().noOcclusion().noLootTable().strength(5.0F));
    public static final DeferredBlock<Block> mario = BLOCKS.registerBlock("mario", BlockMario::new, Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 10F));
    public static final DeferredBlock<Block> quicksand = BLOCKS.registerBlock("quicksand", BlockQuickSand::new, Properties.of().mapColor(MapColor.DIRT).noOcclusion().strength(0.5F).noCollission());
    public static final DeferredBlock<Block> landmine = BLOCKS.registerBlock("landmine", BlockLandMine::new, Properties.of().mapColor(MapColor.COLOR_GREEN).pushReaction(PushReaction.DESTROY).strength(0.6F, 1F));

    public static void init(IEventBus bus, Dist dist) {
        BLOCKS.register(bus);
        if (dist.isClient()) {
            bus.addListener(RRBlocks::registerBlockColors);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, worldIn, pos, tintIndex) -> {
            BlockCycle block = (BlockCycle) state.getBlock();
            block.phase += BlockCycle.phaseadd;
            int r = (int) ((Mth.sin(block.phase + BlockCycle.pShiftR) + 1f) * 128f);
            int g = (int) ((Mth.sin(block.phase + BlockCycle.pShiftG) + 1f) * 128f);
            int b = (int) ((Mth.sin(block.phase + BlockCycle.pShiftB) + 1f) * 128f);
            return (r & 0xff) << 16 | (g & 0xff) << 8 | b & 0xff;
        }, RRBlocks.cycle.get());
        event.register((state, worldIn, pos, tintIndex) -> state.getValue(BlockPetrifiedStone.META) * 1118481, RRBlocks.petrifiedstone1.get(), RRBlocks.petrifiedstone2.get(), RRBlocks.petrifiedstone3.get(), RRBlocks.petrifiedstone4.get());
        event.register((state, worldIn, pos, tintIndex) -> state.getValue(BlockPetrifiedWood.META) * 1118481, RRBlocks.petrifiedwood.get());
        event.register((state, worldIn, pos, tintIndex) -> event.getBlockColors().getColor(Blocks.GRASS_BLOCK.defaultBlockState(), Minecraft.getInstance().level, pos, tintIndex), RRBlocks.mario.get(), RRBlocks.landmine.get(), RRBlocks.quicksand.get());
    }
}
