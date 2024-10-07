package io.github.kadir1243.rivalrebels.datagen;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.block.BlockConduit;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BlockStateDataGen extends BlockStateProvider {
    public BlockStateDataGen(PackOutput gen, ExistingFileHelper existingFileHelper) {
        super(gen, RRIdentifiers.MODID, existingFileHelper);
    }

    private void simpleBlock(Holder<Block> block, String textureLoc) {
        simpleBlock(block.value(), models().cubeAll(name(block.value()), idBlock(textureLoc)));
    }

    private void simpleBlock(Holder<Block> block, Block textureBlock) {
        simpleBlock(block.value(), new ModelFile.UncheckedModelFile(BuiltInRegistries.BLOCK.getKey(textureBlock)));
    }

    private void simpleBlock(Holder<Block> block, String sides, String down, String up) {
        simpleBlock(block.value(), models().cubeBottomTop(name(block.value()), idBlock(sides), idBlock(down), idBlock(up)));
    }

    private void simpleBlock(Holder<Block> block, String sides, String up_and_down) {
        simpleBlock(block, sides, up_and_down, up_and_down);
    }

    private void simpleSidedBlock(Holder<Block> block, String northAndSouthSide, String westAndEastSide, String up, String down) {
        simpleBlock(block.value(), models().cube(name(block.value()), idBlock(down), idBlock(up), idBlock(northAndSouthSide), idBlock(northAndSouthSide), idBlock(westAndEastSide), idBlock(westAndEastSide)));
    }

    public final void createConduitVariant(Holder<Block> block) {
        String key = name(block.value());
        ModelFile meta1 = models().cubeAll(key, idBlock("co"));
        ModelFile meta2 = models().cubeAll(key + "_variant_2", idBlock("cp"));
        ModelFile meta3 = models().cubeAll(key + "_variant_3", idBlock("cq"));
        ModelFile meta4 = models().cubeAll(key + "_variant_4", idBlock("cr"));
        ModelFile meta5 = models().cubeAll(key + "_variant_5", idBlock("cs"));
        ModelFile meta6 = models().cubeAll(key + "_variant_6", idBlock("ct"));
        ModelFile meta7 = models().cubeAll(key + "_variant_7", idBlock("cu"));
        ModelFile meta8 = models().cubeAll(key + "_variant_8", idBlock("cv"));
        ModelFile meta9 = models().cubeAll(key + "_variant_9", idBlock("cw"));
        getVariantBuilder(block.value()).forAllStates(state -> ConfiguredModel.builder().modelFile(switch (state.getValue(BlockConduit.VARIANT)) {
                case 0, 1 -> meta1;
                case 2 -> meta2;
                case 3 -> meta3;
                case 4 -> meta4;
                case 5 -> meta5;
                case 6 -> meta6;
                case 7 -> meta7;
                case 8 -> meta8;
                case 9 -> meta9;
                default -> throw new UnsupportedOperationException("You broke the generator with unexpected variant: " + state);
        }).build());
    }

    private ResourceLocation idBlock(String name) {
        return modLoc(name).withPrefix("block/");
    }

    @Override
    protected void registerStatesAndModels() {
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

        registerBlockItemModels();
    }

    public void registerBlockItemModels() {
        customItemModel(RRBlocks.controller);
        customItemModel(RRBlocks.loader);
        customItemModel(RRBlocks.reactor);

        simpleBlockItem(RRBlocks.amario);
        simpleBlockItem(RRBlocks.aquicksand);
        simpleBlockItem(RRBlocks.barricade);
        simpleBlockItem(RRBlocks.tower);
        simpleBlockItem(RRBlocks.easteregg);
        simpleBlockItem(RRBlocks.bunker);
        simpleBlockItem(RRBlocks.smartcamo);
        simpleBlockItem(RRBlocks.camo1);
        simpleBlockItem(RRBlocks.camo2);
        simpleBlockItem(RRBlocks.camo3);
        simpleBlockItem(RRBlocks.steel);
        simpleBlockItem(RRBlocks.flagbox1);
        simpleBlockItem(RRBlocks.flagbox5);
        simpleBlockItem(RRBlocks.flagbox6);
        simpleBlockItem(RRBlocks.flagbox3);
        simpleBlockItem(RRBlocks.flagbox4);
        simpleBlockItem(RRBlocks.flagbox7);
        simpleBlockItem(RRBlocks.sigmaarmor);
        simpleBlockItem(RRBlocks.omegaarmor);
        simpleBlockItem(RRBlocks.weapons);
        simpleBlockItem(RRBlocks.ammunition);
        simpleBlockItem(RRBlocks.explosives);
        simpleBlockItem(RRBlocks.supplies);
        simpleBlockItem(RRBlocks.jump);
        simpleBlockItem(RRBlocks.remotecharge);
        simpleBlockItem(RRBlocks.timedbomb);
        simpleBlockItem(RRBlocks.flare);
        simpleBlockItem(RRBlocks.cycle);
        simpleBlockItem(RRBlocks.fshield);
        simpleBlockItem(RRBlocks.gamestart);
        simpleBlockItem(RRBlocks.breadbox);
        simpleBlockItem(RRBlocks.alandmine);
        simpleBlockItem(RRBlocks.nukeCrateTop);
        simpleBlockItem(RRBlocks.nukeCrateBottom);
        simpleBlockItem(RRBlocks.radioactivedirt);
        simpleBlockItem(RRBlocks.radioactivesand);
        simpleBlockItem(RRBlocks.omegaobj);
        simpleBlockItem(RRBlocks.sigmaobj);
        simpleBlockItem(RRBlocks.petrifiedwood);
        simpleBlockItem(RRBlocks.petrifiedstone1);
        simpleBlockItem(RRBlocks.petrifiedstone2);
        simpleBlockItem(RRBlocks.petrifiedstone3);
        simpleBlockItem(RRBlocks.petrifiedstone4);
        simpleBlockItem(RRBlocks.forcefieldnode);
        simpleBlockItem(RRBlocks.goreblock);
        simpleBlockItem(RRBlocks.reactive);
        simpleBlockItem(RRBlocks.bastion);
        simpleBlockItem(RRBlocks.conduit);
        simpleBlockItem(RRBlocks.mariotrap);
        simpleBlockItem(RRBlocks.minetrap);
        simpleBlockItem(RRBlocks.quicksandtrap);
        simpleBlockItem(RRBlocks.ffreciever);
        simpleBlockItem(RRBlocks.buildrhodes);
        simpleBlockItem(RRBlocks.rhodesactivator);
    }

    private void simpleBlockItem(Holder<Block> holder) {
        this.itemModels().withExistingParent(name(holder.value()), holder.getKey().location());
    }

    private void customItemModel(Holder<?> item) {
        this.itemModels().getBuilder(item.getKey().location().getPath())
            .parent(new ModelFile.UncheckedModelFile("item/generated"));
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }
}
