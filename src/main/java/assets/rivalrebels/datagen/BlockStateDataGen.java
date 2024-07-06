package assets.rivalrebels.datagen;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.common.block.RRBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.models.model.TexturedModel;
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
        simpleBlock(RRBlocks.sigmaobj, "bq");
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
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {
    }

    @Override
    public String getName() {
        return "Rival Rebels block models and states";
    }
}
