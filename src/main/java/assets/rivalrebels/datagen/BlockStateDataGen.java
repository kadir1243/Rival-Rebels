package assets.rivalrebels.datagen;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.common.block.BlockConduit;
import assets.rivalrebels.common.block.RRBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class BlockStateDataGen extends FabricModelProvider {
    public BlockStateDataGen(FabricDataOutput gen) {
        super(gen);
    }

    private void simpleBlock(Block block, String textureLoc) {
        generator.createTrivialBlock(block, TextureMapping.cube(idBlock(textureLoc)), ModelTemplates.CUBE_ALL);
    }

    private void simpleBlock(Block block, Block textureBlock) {
        generator.createTrivialBlock(block, TextureMapping.cube(textureBlock), ModelTemplates.CUBE_ALL);
    }

    private void simpleBlock(Block block, String sides, String down, String up) {
        generator.createTrivialBlock(block, new TextureMapping().put(TextureSlot.TOP, idBlock(up)).put(TextureSlot.BOTTOM, idBlock(down)).put(TextureSlot.SIDE, idBlock(sides)), ModelTemplates.CUBE_BOTTOM_TOP);
    }

    private void simpleBlock(Block block, String sides, String up_and_down) {
        generator.createTrivialBlock(block, new TextureMapping().put(TextureSlot.TOP, idBlock(up_and_down)).put(TextureSlot.BOTTOM, idBlock(up_and_down)).put(TextureSlot.SIDE, idBlock(sides)), ModelTemplates.CUBE_BOTTOM_TOP);
    }

    private void simpleSidedBlock(Block block, String northAndSouthSide, String westAndEastSide, String up, String down) {
        generator.createTrivialBlock(block, new TextureMapping()
                .put(TextureSlot.UP, idBlock(up))
                .put(TextureSlot.DOWN, idBlock(down))
                .put(TextureSlot.NORTH, idBlock(northAndSouthSide))
                .put(TextureSlot.SOUTH, idBlock(northAndSouthSide))
                .put(TextureSlot.EAST, idBlock(westAndEastSide))
                .put(TextureSlot.WEST, idBlock(westAndEastSide))
                .put(TextureSlot.PARTICLE, idBlock(up))
            , ModelTemplates.CUBE
        );
    }

    public final void createConduitVariant(Block block) {
        ResourceLocation meta1 = ModelTemplates.CUBE_ALL.create(block, TextureMapping.cube(idBlock("co")), generator.modelOutput);
        ResourceLocation meta2 = ModelTemplates.CUBE_ALL.createWithSuffix(block, "_variant_2", TextureMapping.cube(idBlock("cp")), generator.modelOutput);
        ResourceLocation meta3 = ModelTemplates.CUBE_ALL.createWithSuffix(block, "_variant_3", TextureMapping.cube(idBlock("cq")), generator.modelOutput);
        ResourceLocation meta4 = ModelTemplates.CUBE_ALL.createWithSuffix(block, "_variant_4", TextureMapping.cube(idBlock("cr")), generator.modelOutput);
        ResourceLocation meta5 = ModelTemplates.CUBE_ALL.createWithSuffix(block, "_variant_5", TextureMapping.cube(idBlock("cs")), generator.modelOutput);
        ResourceLocation meta6 = ModelTemplates.CUBE_ALL.createWithSuffix(block, "_variant_6", TextureMapping.cube(idBlock("ct")), generator.modelOutput);
        ResourceLocation meta7 = ModelTemplates.CUBE_ALL.createWithSuffix(block, "_variant_7", TextureMapping.cube(idBlock("cu")), generator.modelOutput);
        ResourceLocation meta8 = ModelTemplates.CUBE_ALL.createWithSuffix(block, "_variant_8", TextureMapping.cube(idBlock("cv")), generator.modelOutput);
        ResourceLocation meta9 = ModelTemplates.CUBE_ALL.createWithSuffix(block, "_variant_9", TextureMapping.cube(idBlock("cw")), generator.modelOutput);
        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(BlockConduit.VARIANT).generate(meta -> Variant.variant().with(VariantProperties.MODEL, switch (meta) {
            case 0, 1 -> meta1;
            case 2 -> meta2;
            case 3 -> meta3;
            case 4 -> meta4;
            case 5 -> meta5;
            case 6 -> meta6;
            case 7 -> meta7;
            case 8 -> meta8;
            case 9 -> meta9;
            default -> throw new UnsupportedOperationException("You broke the generator with unexpected variant: " + meta);
        }))));
    }

    private static ResourceLocation idBlock(String name) {
        return RRIdentifiers.create(name).withPrefix("block/");
    }

    private BlockModelGenerators generator;
    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {
        this.generator = generator;
        simpleBlock(RRBlocks.steel, "bx");
        simpleBlock(RRBlocks.smartcamo, "bq");
        simpleBlock(RRBlocks.reactive, "cf", "cn");
        simpleBlock(RRBlocks.plasmaexplosion, "ak");
        simpleBlock(RRBlocks.meltdown, "ak");
        simpleBlock(RRBlocks.light, "ad");
        simpleBlock(RRBlocks.light2, "ad");
        simpleBlock(RRBlocks.fshield, "ao", "ap");
        simpleBlock(RRBlocks.cycle, "ak");
        simpleBlock(RRBlocks.camo3, "by");
        simpleBlock(RRBlocks.camo2, "bn");
        simpleBlock(RRBlocks.camo1, "as");
        simpleBlock(RRBlocks.antimatterbombblock, "ak");
        //simpleBlock(RRBlocks.flare, models().torch(name(RRBlocks.flare), modLoc("an")));
        simpleBlock(RRBlocks.nuclearBomb, "ak");
        simpleBlock(RRBlocks.tsarbombablock, "ak");
        simpleBlock(RRBlocks.toxicgas, "ak");
        simpleBlock(RRBlocks.theoreticaltsarbombablock, "ak");
        simpleBlock(RRBlocks.tachyonbombblock, "ak");
        simpleBlock(RRBlocks.petrifiedstone1, "bc", "bb");
        simpleBlock(RRBlocks.petrifiedstone2, "bd", "bb");
        simpleBlock(RRBlocks.petrifiedstone3, "be", "bb");
        simpleBlock(RRBlocks.petrifiedstone4, "bf", "bb");
        simpleBlock(RRBlocks.radioactivedirt, Blocks.DIRT);
        simpleBlock(RRBlocks.radioactivesand, Blocks.SAND);
        simpleBlock(RRBlocks.remotecharge, "af", "ag");
        simpleBlock(RRBlocks.omegaobj, "ba");
        simpleBlock(RRBlocks.sigmaobj, "bp");
        simpleBlock(RRBlocks.ffreciever, "dj");
        simpleBlock(RRBlocks.rhodesactivator, "ci", "ch");
        simpleBlock(RRBlocks.reactor, "bj");
        simpleBlock(RRBlocks.loader, "av");
        simpleBlock(RRBlocks.controller, "dc");
        simpleBlock(RRBlocks.forcefield, "di");
        simpleBlock(RRBlocks.ammunition, "aa", "ah", "ai");
        simpleBlock(RRBlocks.explosives, "am", "ah", "ai");
        simpleBlock(RRBlocks.nukeCrateTop, "ay");
        simpleBlock(RRBlocks.nukeCrateBottom, "ax");
        simpleBlock(RRBlocks.weapons, "ce", "ah", "ai");
        simpleBlock(RRBlocks.barricade, "cx", "cz", "da");
        simpleBlock(RRBlocks.bunker, "bl", "ah");
        simpleBlock(RRBlocks.easteregg, "ah", "ah", "ai");
        simpleBlock(RRBlocks.bastion, "db", "cz", "da");
        simpleBlock(RRBlocks.mariotrap, "de", "dh");
        createConduitVariant(RRBlocks.conduit);
        simpleBlock(RRBlocks.quicksandtrap, "dg", "dh");
        simpleBlock(RRBlocks.tower, "cy", "ah", "ai");
        simpleBlock(RRBlocks.buildrhodes, "dk", "cz", "da");
        simpleBlock(RRBlocks.jump, "at", "ah", "au");
        simpleBlock(RRBlocks.petrifiedwood, "bg", "bh");
        simpleBlock(RRBlocks.minetrap, "df", "dh");
        simpleSidedBlock(RRBlocks.timedbomb, "ac", "ab", "ae", "ac");
        simpleSidedBlock(RRBlocks.sigmaarmor, "bo", "ah", "ai", "ah");
        simpleSidedBlock(RRBlocks.omegaarmor, "az", "ah", "ai", "ah");
        simpleSidedBlock(RRBlocks.gamestart, "ai", "ah", "ah", "ah");
        simpleSidedBlock(RRBlocks.flagbox1, "ai", "ah", "bi", "ah");
        simpleSidedBlock(RRBlocks.flagbox3, "ai", "ah", "ar", "ah");
        simpleSidedBlock(RRBlocks.flagbox4, "ai", "ah", "aw", "ah");
        simpleSidedBlock(RRBlocks.flagbox5, "ai", "ah", "aq", "ah");
        simpleSidedBlock(RRBlocks.flagbox6, "ai", "ah", "al", "ah");
        simpleSidedBlock(RRBlocks.flagbox7, "ai", "ah", "aj", "ah");
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {
    }

    @Override
    public String getName() {
        return "Rival Rebels block models and states";
    }
}
