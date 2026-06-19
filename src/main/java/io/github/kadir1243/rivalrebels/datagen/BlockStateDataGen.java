package io.github.kadir1243.rivalrebels.datagen;

import com.mojang.math.Axis;
import com.mojang.math.Quadrant;
import com.mojang.math.Transformation;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.itemrenders.*;
import io.github.kadir1243.rivalrebels.common.block.BlockConduit;
import io.github.kadir1243.rivalrebels.common.block.BlockGore;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.renderer.item.SpecialModelWrapper;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.loaders.ObjModelBuilder;
import net.neoforged.neoforge.client.model.generators.template.*;
import org.apache.commons.lang3.function.Consumers;
import org.joml.Vector3f;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

public class BlockStateDataGen extends ModelProvider {
    public BlockStateDataGen(PackOutput output) {
        super(output, RRIdentifiers.MODID);
    }

    private void simpleBlock(BlockModelGenerators blockModels, Holder<Block> block, String textureLoc) {
        blockModels.createTrivialBlock(block.value(), TexturedModel.CUBE.updateTexture(textureMapping -> textureMapping.put(TextureSlot.ALL, new Material(idBlock(textureLoc)))));
    }

    private void simpleBlock(BlockModelGenerators blockModels, Holder<Block> block, Block textureBlock) {
        blockModels.copyModel(textureBlock, block.value());
    }

    private void simpleBlock(BlockModelGenerators blockModels, Holder<Block> block, String sides, String down, String up) {
        blockModels.createTrivialBlock(block.value(), TexturedModel.CUBE_TOP_BOTTOM.updateTexture(textureMapping -> {
            textureMapping.put(TextureSlot.BOTTOM, new Material(idBlock(down)));
            textureMapping.put(TextureSlot.TOP, new Material(idBlock(up)));
            textureMapping.put(TextureSlot.SIDE, new Material(idBlock(sides)));
        }));
    }

    private void simpleBlock(BlockModelGenerators blockModels, Holder<Block> block, String sides, String up_and_down) {
        simpleBlock(blockModels, block, sides, up_and_down, up_and_down);
    }

    private void simpleSidedBlock(BlockModelGenerators blockModels, Holder<Block> block, String northAndSouthSide, String westAndEastSide, String up, String down) {
        TextureMapping textures = new TextureMapping()
            .put(TextureSlot.NORTH, new Material(idBlock(northAndSouthSide)))
            .put(TextureSlot.SOUTH, new Material(idBlock(northAndSouthSide)))
            .put(TextureSlot.WEST, new Material(idBlock(westAndEastSide)))
            .put(TextureSlot.EAST, new Material(idBlock(westAndEastSide)))
            .put(TextureSlot.DOWN, new Material(idBlock(down)))
            .put(TextureSlot.UP, new Material(idBlock(up)))
            .put(TextureSlot.PARTICLE, new Material(idBlock(up))); // FIXME: particles
        blockModels.blockStateOutput
            .accept(BlockModelGenerators.createSimpleBlock(block.value(), BlockModelGenerators.plainVariant(ModelTemplates.CUBE.create(block.value(), textures, blockModels.modelOutput))));
    }

    private static class EmptyLoaderBuilder extends CustomLoaderBuilder {
        protected EmptyLoaderBuilder() {
            super(Identifier.fromNamespaceAndPath("neoforge", "empty"), false);
        }

        @Override
        protected CustomLoaderBuilder copyInternal() {
            return new EmptyLoaderBuilder();
        }
    }

    private static final ModelTemplate EMPTY_MODEL_TEMPLATE = new ModelTemplate(
        Optional.empty(),
        Optional.empty()
    ).extend().customLoader(EmptyLoaderBuilder::new, Consumers.nop()).build();
    private void emptyBlockModel(BlockModelGenerators blockModels, Holder<Block> block) {
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block.value(), BlockModelGenerators.plainVariant(EMPTY_MODEL_TEMPLATE.create(block.value(), new TextureMapping(), blockModels.modelOutput))));
    }

    public static final ModelTemplate CONDUIT_TEMPLATE = new ModelTemplate(
        // The parent model location
        ModelTemplates.CUBE_ALL.model,
        // The suffix to apply to the end of any model that uses this template
        Optional.of("_variant"),
        TextureSlot.ALL
    );
    public final void createConduitVariant(BlockModelGenerators blockModels, Holder<Block> block) {
        var meta1 = BlockModelGenerators.plainVariant(CONDUIT_TEMPLATE.createWithSuffix(block.value(), "_1", TextureMapping.cube(new Material(idBlock("co"))), blockModels.modelOutput));
        var meta2 = BlockModelGenerators.plainVariant(CONDUIT_TEMPLATE.createWithSuffix(block.value(), "_2", TextureMapping.cube(new Material(idBlock("cp"))), blockModels.modelOutput));
        var meta3 = BlockModelGenerators.plainVariant(CONDUIT_TEMPLATE.createWithSuffix(block.value(), "_3", TextureMapping.cube(new Material(idBlock("cq"))), blockModels.modelOutput));
        var meta4 = BlockModelGenerators.plainVariant(CONDUIT_TEMPLATE.createWithSuffix(block.value(), "_4", TextureMapping.cube(new Material(idBlock("cr"))), blockModels.modelOutput));
        var meta5 = BlockModelGenerators.plainVariant(CONDUIT_TEMPLATE.createWithSuffix(block.value(), "_5", TextureMapping.cube(new Material(idBlock("cs"))), blockModels.modelOutput));
        var meta6 = BlockModelGenerators.plainVariant(CONDUIT_TEMPLATE.createWithSuffix(block.value(), "_6", TextureMapping.cube(new Material(idBlock("ct"))), blockModels.modelOutput));
        var meta7 = BlockModelGenerators.plainVariant(CONDUIT_TEMPLATE.createWithSuffix(block.value(), "_7", TextureMapping.cube(new Material(idBlock("cu"))), blockModels.modelOutput));
        var meta8 = BlockModelGenerators.plainVariant(CONDUIT_TEMPLATE.createWithSuffix(block.value(), "_8", TextureMapping.cube(new Material(idBlock("cv"))), blockModels.modelOutput));
        var meta9 = BlockModelGenerators.plainVariant(CONDUIT_TEMPLATE.createWithSuffix(block.value(), "_9", TextureMapping.cube(new Material(idBlock("cw"))), blockModels.modelOutput));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block.value())
            .with(PropertyDispatch.initial(BlockConduit.VARIANT)
                .select(0, meta1)
                .select(1, meta1)
                .select(2, meta2)
                .select(3, meta3)
                .select(4, meta4)
                .select(5, meta5)
                .select(6, meta6)
                .select(7, meta7)
                .select(8, meta8)
                .select(9, meta9)
            )
        );
    }

    private Identifier idBlock(String name) {
        return modLocation(name).withPrefix("block/");
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        registerItems(itemModels);
        simpleBlock(blockModels, RRBlocks.steel, "bx");
        simpleBlock(blockModels, RRBlocks.smartcamo, "bq");
        simpleBlock(blockModels, RRBlocks.reactive, "cf", "cn");
        simpleBlock(blockModels, RRBlocks.plasmaexplosion, "ak");
        simpleBlock(blockModels, RRBlocks.meltdown, "ak");
        simpleBlock(blockModels, RRBlocks.light, "ad");
        simpleBlock(blockModels, RRBlocks.light2, "ad");
        simpleBlock(blockModels, RRBlocks.fshield, "ao", "ap");
        simpleBlock(blockModels, RRBlocks.cycle, "ak");
        simpleBlock(blockModels, RRBlocks.camo3, "by");
        simpleBlock(blockModels, RRBlocks.camo2, "bn");
        simpleBlock(blockModels, RRBlocks.camo1, "as");
        objModelWithSpecialRot(blockModels, RRBlocks.antimatterbombblock.value(), RRIdentifiers.getModelLocation("t"), RRIdentifiers.etantimatterbomb, transformVecBuilder -> transformVecBuilder.rotation(0.5F, 1F, 0.5F), TACHYON_BOMB_ROTATION);
        flareBlock(blockModels, RRBlocks.flare, "an");
        objModelWithRotation(blockModels, RRBlocks.nuclearBomb.value(), RRIdentifiers.getModelLocation("wacknuke"), RRIdentifiers.etwacknuke, transformVecBuilder -> transformVecBuilder.rotation(0.5F, 0.5F, 0.5F));
        simpleModelHorizontallyRotated(blockModels, RRBlocks.tsarbombablock.value(), "ak");
        simpleBlock(blockModels, RRBlocks.toxicgas, "ak");
        simpleModelHorizontallyRotated(blockModels, RRBlocks.theoreticaltsarbombablock.value(), "ak");
        objModelWithSpecialRot(blockModels, RRBlocks.tachyonbombblock.value(), RRIdentifiers.getModelLocation("t"), RRIdentifiers.ettachyonbomb, transformVecBuilder -> transformVecBuilder.rotation(0.5F, 1F, 0.5F), TACHYON_BOMB_ROTATION);
        simpleBlock(blockModels, RRBlocks.petrifiedstone1, "bc", "bb");
        simpleBlock(blockModels, RRBlocks.petrifiedstone2, "bd", "bb");
        simpleBlock(blockModels, RRBlocks.petrifiedstone3, "be", "bb");
        simpleBlock(blockModels, RRBlocks.petrifiedstone4, "bf", "bb");
        // FIXME: simpleBlock(blockModels, RRBlocks.radioactivedirt, Blocks.DIRT);
        simpleBlock(blockModels, RRBlocks.radioactivedirt, "notexisting");
        // FIXME: simpleBlock(blockModels, RRBlocks.radioactivesand, Blocks.SAND);
        simpleBlock(blockModels, RRBlocks.radioactivesand, "notexisting");
        simpleBlock(blockModels, RRBlocks.remotecharge, "af", "ag");
        //simpleBlock(blockModels, RRBlocks.omegaobj, "ba");
        //simpleBlock(blockModels, RRBlocks.sigmaobj, "bp");
        emptyBlockModel(blockModels, RRBlocks.omegaobj);
        emptyBlockModel(blockModels, RRBlocks.sigmaobj);
        simpleModelHorizontallyRotated(blockModels, RRBlocks.ffreciever.value(), "dj");
        simpleBlock(blockModels, RRBlocks.rhodesactivator, "ci", "ch");
        emptyBlockModel(blockModels, RRBlocks.reactor);
        simpleModelHorizontallyRotated(blockModels, RRBlocks.loader.value(), "av");
        emptyBlockModel(blockModels, RRBlocks.controller);
        simpleBlock(blockModels, RRBlocks.forcefield, "di");
        simpleBlock(blockModels, RRBlocks.ammunition, "aa", "ah", "ai");
        simpleBlock(blockModels, RRBlocks.explosives, "am", "ah", "ai");
        nukeCrateBlock(blockModels, RRBlocks.nukeCrateTop, "ay");
        nukeCrateBlock(blockModels, RRBlocks.nukeCrateBottom, "ax");
        simpleBlock(blockModels, RRBlocks.weapons, "ce", "ah", "ai");
        simpleBlock(blockModels, RRBlocks.barricade, "cx", "cz", "da");
        simpleBlock(blockModels, RRBlocks.bunker, "bl", "ah");
        simpleBlock(blockModels, RRBlocks.easteregg, "ah", "ah", "ai");
        simpleBlock(blockModels, RRBlocks.bastion, "db", "cz", "da");
        simpleBlock(blockModels, RRBlocks.mariotrap, "de", "dh");
        simpleBlock(blockModels, RRBlocks.supplies, "bz", "ah", "ai");
        blockModels.createTrivialBlock(RRBlocks.breadbox.value(), TexturedModel.CUBE.updateTexture(textureMapping -> {
            textureMapping.put(TextureSlot.TOP, new Material(idBlock("cc")));
            textureMapping.put(TextureSlot.BOTTOM, new Material(idBlock("ca")));
            textureMapping.put(TextureSlot.NORTH, new Material(idBlock("cb")));
            textureMapping.put(TextureSlot.SOUTH, new Material(idBlock("ca")));
            textureMapping.put(TextureSlot.WEST, new Material(idBlock("ca")));
            textureMapping.put(TextureSlot.EAST, new Material(idBlock("ca")));
        }));
        goreBlock(blockModels, RRBlocks.goreblock);
        createConduitVariant(blockModels, RRBlocks.conduit);
        simpleBlock(blockModels, RRBlocks.quicksandtrap, "dg", "dh");
        simpleBlock(blockModels, RRBlocks.tower, "cy", "ah", "ai");
        simpleBlock(blockModels, RRBlocks.buildrhodes, "dk", "cz", "da");
        simpleBlock(blockModels, RRBlocks.jump, "at", "ah", "au");
        simpleBlock(blockModels, RRBlocks.petrifiedwood, "bg", "bh");
        simpleBlock(blockModels, RRBlocks.minetrap, "df", "dh");
        simpleSidedBlock(blockModels, RRBlocks.timedbomb, "ac", "ab", "ae", "ac");
        simpleSidedBlock(blockModels, RRBlocks.sigmaarmor, "bo", "ah", "ai", "ah");
        simpleSidedBlock(blockModels, RRBlocks.omegaarmor, "az", "ah", "ai", "ah");
        simpleSidedBlock(blockModels, RRBlocks.gamestart, "ai", "ah", "ah", "ah");
        simpleSidedBlock(blockModels, RRBlocks.flagbox1, "ai", "ah", "bi", "ah");
        simpleSidedBlock(blockModels, RRBlocks.flagbox3, "ai", "ah", "ar", "ah");
        simpleSidedBlock(blockModels, RRBlocks.flagbox4, "ai", "ah", "aw", "ah");
        simpleSidedBlock(blockModels, RRBlocks.flagbox5, "ai", "ah", "aq", "ah");
        simpleSidedBlock(blockModels, RRBlocks.flagbox6, "ai", "ah", "al", "ah");
        simpleSidedBlock(blockModels, RRBlocks.flagbox7, "ai", "ah", "aj", "ah");
        createForceFieldNodeBlock(blockModels, RRBlocks.forcefieldnode.value());
        flagBlock(blockModels, RRBlocks.flag1, "bi");
        flagBlock(blockModels, RRBlocks.trollFlag, "dd");
        flagBlock(blockModels, RRBlocks.flag3, "ar");
        flagBlock(blockModels, RRBlocks.flag4, "aw");
        flagBlock(blockModels, RRBlocks.flag5, "aq");
        flagBlock(blockModels, RRBlocks.flag6, "al");
        flagBlock(blockModels, RRBlocks.flag7, "aj");
        landmineBlock(blockModels, RRBlocks.landmine);
        landmineBlock(blockModels, RRBlocks.alandmine);
        quicksandBlock(blockModels, RRBlocks.quicksand);
        quicksandBlock(blockModels, RRBlocks.aquicksand);
        marioBlock(blockModels, RRBlocks.mario);
        marioBlock(blockModels, RRBlocks.amario);

        registerBlockItemModels(blockModels, itemModels);
    }

    public void registerBlockItemModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.itemModelOutput.accept(
            RRBlocks.controller.asItem(),
            new SpecialModelWrapper.Unbaked(
                Identifier.fromNamespaceAndPath("minecraft", "air"),
                Optional.of(new Transformation(new Vector3f(0.3F, 0.3F, 0), Axis.YP.rotationDegrees(180), null, null)),
                new LaptopRenderer.Unbaked(
                    RRIdentifiers.etubuntu
                )
            )
        );
        itemModels.itemModelOutput.accept(
            RRBlocks.loader.asItem(),
            new SpecialModelWrapper.Unbaked(
                Identifier.fromNamespaceAndPath("minecraft", "air"),
                Optional.of(new Transformation(new Vector3f(0F, 0.05F, 0F), null, null, null)),
                new LoaderRenderer.Unbaked(
                    RRIdentifiers.etloader
                )
            )
        );
        itemModels.itemModelOutput.accept(
            RRBlocks.reactor.asItem(),
            new SpecialModelWrapper.Unbaked(
                Identifier.fromNamespaceAndPath("minecraft", "air"),
                Optional.empty(),
                new ReactorRenderer.Unbaked()
            )
        );

        simpleBlockItem(blockModels, RRBlocks.amario);
        simpleBlockItem(blockModels, RRBlocks.aquicksand);
        simpleBlockItem(blockModels, RRBlocks.barricade);
        simpleBlockItem(blockModels, RRBlocks.tower);
        simpleBlockItem(blockModels, RRBlocks.easteregg);
        simpleBlockItem(blockModels, RRBlocks.bunker);
        simpleBlockItem(blockModels, RRBlocks.smartcamo);
        simpleBlockItem(blockModels, RRBlocks.camo1);
        simpleBlockItem(blockModels, RRBlocks.camo2);
        simpleBlockItem(blockModels, RRBlocks.camo3);
        simpleBlockItem(blockModels, RRBlocks.steel);
        simpleBlockItem(blockModels, RRBlocks.flagbox1);
        simpleBlockItem(blockModels, RRBlocks.flagbox5);
        simpleBlockItem(blockModels, RRBlocks.flagbox6);
        simpleBlockItem(blockModels, RRBlocks.flagbox3);
        simpleBlockItem(blockModels, RRBlocks.flagbox4);
        simpleBlockItem(blockModels, RRBlocks.flagbox7);
        simpleBlockItem(blockModels, RRBlocks.sigmaarmor);
        simpleBlockItem(blockModels, RRBlocks.omegaarmor);
        simpleBlockItem(blockModels, RRBlocks.weapons);
        simpleBlockItem(blockModels, RRBlocks.ammunition);
        simpleBlockItem(blockModels, RRBlocks.explosives);
        simpleBlockItem(blockModels, RRBlocks.supplies);
        simpleBlockItem(blockModels, RRBlocks.jump);
        simpleBlockItem(blockModels, RRBlocks.remotecharge);
        simpleBlockItem(blockModels, RRBlocks.timedbomb);
        simpleBlockItem(blockModels, RRBlocks.flare);
        simpleBlockItem(blockModels, RRBlocks.cycle);
        simpleBlockItem(blockModels, RRBlocks.fshield);
        simpleBlockItem(blockModels, RRBlocks.gamestart);
        simpleBlockItem(blockModels, RRBlocks.breadbox);
        simpleBlockItem(blockModels, RRBlocks.alandmine);
        simpleBlockItem(blockModels, RRBlocks.nukeCrateTop);
        simpleBlockItem(blockModels, RRBlocks.nukeCrateBottom);
        simpleBlockItem(blockModels, RRBlocks.omegaobj);
        simpleBlockItem(blockModels, RRBlocks.sigmaobj);
        simpleBlockItem(blockModels, RRBlocks.petrifiedwood);
        simpleBlockItem(blockModels, RRBlocks.petrifiedstone1);
        simpleBlockItem(blockModels, RRBlocks.petrifiedstone2);
        simpleBlockItem(blockModels, RRBlocks.petrifiedstone3);
        simpleBlockItem(blockModels, RRBlocks.petrifiedstone4);
        simpleBlockItem(blockModels, RRBlocks.forcefieldnode);
        simpleBlockItem(blockModels, RRBlocks.goreblock);
        simpleBlockItem(blockModels, RRBlocks.reactive);
        simpleBlockItem(blockModels, RRBlocks.bastion);
        simpleBlockItem(blockModels, RRBlocks.conduit);
        simpleBlockItem(blockModels, RRBlocks.mariotrap);
        simpleBlockItem(blockModels, RRBlocks.minetrap);
        simpleBlockItem(blockModels, RRBlocks.quicksandtrap);
        simpleBlockItem(blockModels, RRBlocks.ffreciever);
        simpleBlockItem(blockModels, RRBlocks.buildrhodes);
        simpleBlockItem(blockModels, RRBlocks.rhodesactivator);
    }

    private void simpleBlockItem(BlockModelGenerators blockModels, Holder<Block> holder) {
        blockModels.createFlatItemModelWithBlockTexture(holder.value().asItem(), holder.value());
    }

    private void customItemModel(ItemModelGenerators itemModels, Item item) {
        itemModels.declareCustomModelItem(item);
    }

    public static final PropertyDispatch<VariantMutator> ROTATION_HORIZONTAL_FACING_FOR_OLD_MODELS = PropertyDispatch.modify(BlockStateProperties.HORIZONTAL_FACING)
        .select(Direction.EAST, BlockModelGenerators.Y_ROT_90.then(X_ROT_90))
        .select(Direction.SOUTH, X_ROT_90)
        .select(Direction.WEST, BlockModelGenerators.Y_ROT_270.then(X_ROT_90))
        .select(Direction.NORTH, BlockModelGenerators.Y_ROT_180.then(X_ROT_90));

    public static final PropertyDispatch<VariantMutator> TACHYON_BOMB_ROTATION = PropertyDispatch.modify(BlockStateProperties.HORIZONTAL_FACING)
        .select(Direction.EAST, BlockModelGenerators.Y_ROT_90)
        .select(Direction.SOUTH, NOP)
        .select(Direction.WEST, BlockModelGenerators.Y_ROT_270)
        .select(Direction.NORTH, BlockModelGenerators.Y_ROT_180);

    private void objModelWithHorizontalRotation(BlockModelGenerators blockModels, Block block, Identifier modelLocation, Identifier texture, Consumer<TransformVecBuilder> transform) {
        blockModels.createHorizontallyRotatedBlock(block, getObjTexturedModel(modelLocation, texture, transform));
    }

    private void objModelWithSpecialRot(BlockModelGenerators blockModels, Block block, Identifier modelLocation, Identifier texture, Consumer<TransformVecBuilder> transform, PropertyDispatch<VariantMutator> rot) {
        MultiVariant model = BlockModelGenerators.plainVariant(getObjTexturedModel(modelLocation, texture, transform).create(block, blockModels.modelOutput));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, model).with(rot));
    }

    private void objModelWithRotation(BlockModelGenerators blockModels, Block block, Identifier modelLocation, Identifier texture, Consumer<TransformVecBuilder> transform) {
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, BlockModelGenerators.plainVariant(getObjTexturedModel(modelLocation, texture, transform).create(block, blockModels.modelOutput))).with(BlockModelGenerators.ROTATION_FACING));
    }

    private void simpleModelRotated(BlockModelGenerators blockModels, Holder<Block> block, String texture) {
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block.value(), BlockModelGenerators.plainVariant(TexturedModel.CUBE.updateTexture(textureMapping -> textureMapping.put(TextureSlot.ALL, new Material(idBlock(texture)))).create(block.value(), blockModels.modelOutput))).with(BlockModelGenerators.ROTATION_FACING));
    }

    private void simpleModelHorizontallyRotated(BlockModelGenerators blockModels, Block block, String texture) {
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, BlockModelGenerators.plainVariant(TexturedModel.CUBE.updateTexture(textureMapping -> textureMapping.put(TextureSlot.ALL, new Material(idBlock(texture)))).create(block, blockModels.modelOutput))).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    private static TexturedModel.Provider getObjTexturedModel(Identifier modelLocation, Identifier texture, Consumer<TransformVecBuilder> transform) {
        return TexturedModel.createDefault(block1 -> new TextureMapping().put(TextureSlot.TEXTURE, new Material(texture)).put(TextureSlot.PARTICLE, new Material(texture)), ExtendedModelTemplateBuilder.builder()
            .customLoader(ObjModelBuilder::new, objModelBuilder -> objModelBuilder.modelLocation(modelLocation))
            .requiredTextureSlot(TextureSlot.TEXTURE)
            .transform(ItemDisplayContext.NONE, transform)
            .build());
    }

    private void createForceFieldNodeBlock(BlockModelGenerators blockModels, Block block) {
        var icon = idBlock("cf");
        var icon2 = idBlock("cg");
        var icontop1 = idBlock("cj");
        var icontop2 = idBlock("ck");
        var icontop3 = idBlock("cl");
        var icontop4 = idBlock("cm");

        blockModels.createHorizontallyRotatedBlock(block, TexturedModel.CUBE.updateTexture(textureMapping -> {
            textureMapping.put(TextureSlot.NORTH, new Material(icon));
            textureMapping.put(TextureSlot.SOUTH, new Material(icon));
            textureMapping.put(TextureSlot.WEST, new Material(icon2));
            textureMapping.put(TextureSlot.EAST, new Material(icon));
            textureMapping.put(TextureSlot.BOTTOM, new Material(icontop2));
            textureMapping.put(TextureSlot.TOP, new Material(icontop2));
        }));
    }

    private void flagBlock(BlockModelGenerators blockModels, Holder<Block> flag, String texture) {
        simpleBlock(blockModels, flag, texture); // TODO: Flags models
    }

    private void flareBlock(BlockModelGenerators blockModels, Holder<Block> flare, String texture) {
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(flare.value(), BlockModelGenerators.plainVariant(ModelTemplates.TORCH.create(flare.value(), new TextureMapping().put(TextureSlot.TORCH, new Material(idBlock(texture))), blockModels.modelOutput))));
    }

    private void goreBlock(BlockModelGenerators blockModels, Holder<Block> block) {
        var icon = BlockModelGenerators.plainVariant(TexturedModel.createAllSame(new Material(idBlock("br"))).createWithSuffix(block.value(), "icon1", blockModels.modelOutput));
        var icon2 = BlockModelGenerators.plainVariant(TexturedModel.createAllSame(new Material(idBlock("bs"))).createWithSuffix(block.value(), "icon2", blockModels.modelOutput));
        var icon3 = BlockModelGenerators.plainVariant(TexturedModel.createAllSame(new Material(idBlock("bt"))).createWithSuffix(block.value(), "icon3", blockModels.modelOutput));
        var icon4 = BlockModelGenerators.plainVariant(TexturedModel.createAllSame(new Material(idBlock("bu"))).createWithSuffix(block.value(), "icon4", blockModels.modelOutput));
        var icon5 = BlockModelGenerators.plainVariant(TexturedModel.createAllSame(new Material(idBlock("bv"))).createWithSuffix(block.value(), "icon5", blockModels.modelOutput));
        var icon6 = BlockModelGenerators.plainVariant(TexturedModel.createAllSame(new Material(idBlock("bw"))).createWithSuffix(block.value(), "icon6", blockModels.modelOutput));

        blockModels.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(block.value())
                .with(
                    PropertyDispatch.initial(BlockGore.META)
                        .select(0, icon)
                        .select(1, icon2)
                        .select(2, icon3)
                        .select(3, icon4)
                        .select(4, icon5)
                        .select(5, icon6)
                ));
    }

    private static final PropertyDispatch<VariantMutator> NUKE_CRATE_ROTATION_FACING = PropertyDispatch.modify(BlockStateProperties.FACING)
        .select(Direction.DOWN, X_ROT_180)
        .select(Direction.UP, X_ROT_180)
        .select(Direction.NORTH, X_ROT_270)
        .select(Direction.SOUTH, X_ROT_90)
        .select(Direction.WEST, VariantMutator.Z_ROT.withValue(Quadrant.R90))
        .select(Direction.EAST, VariantMutator.Z_ROT.withValue(Quadrant.R270));
    private static final ExtendedModelTemplate NUKE_BASE_MODEL = ExtendedModelTemplateBuilder.builder()
        .parent(Identifier.withDefaultNamespace("block/block"))
        .element(elementBuilder -> elementBuilder
            .from(0, 0, 0)
            .to(16, 16, 16)
            .allFaces((direction, faceBuilder) -> {
                TextureSlot slot = switch (direction) {
                    case EAST, WEST, SOUTH, NORTH -> TextureSlot.SIDE;
                    case UP -> TextureSlot.TOP;
                    case DOWN -> TextureSlot.BOTTOM;
                };
                faceBuilder.texture(slot).cullface(direction);
            })
            .allFacesExcept((_, faceBuilder) -> faceBuilder.rotation(Quadrant.R270), Set.of(Direction.UP, Direction.DOWN))
        )
        .build();
    private void nukeCrateBlock(BlockModelGenerators blockModels, Holder<Block> block, String texture) {
        TextureMapping textures = new TextureMapping()
            .putForced(TextureSlot.SIDE, new Material(idBlock(texture)))
            .putForced(TextureSlot.TOP, new Material(idBlock("ah")))
            .putForced(TextureSlot.BOTTOM, new Material(idBlock("ah")));

        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block.value(), BlockModelGenerators.plainVariant(
            NUKE_BASE_MODEL.create(block.value(), textures, blockModels.modelOutput))).with(NUKE_CRATE_ROTATION_FACING));
    }

    private void landmineBlock(BlockModelGenerators blockModels, Holder<Block> block) {
        simpleBlock(blockModels, block, "notexisting"); // TODO: Add model for landmine
    }

    private void marioBlock(BlockModelGenerators blockModels, Holder<Block> block) {
        simpleBlock(blockModels, block, "notexisting"); // TODO: Add model for mario
    }

    private void quicksandBlock(BlockModelGenerators blockModels, Holder<Block> block) {
        simpleBlock(blockModels, block, "notexisting"); // TODO: Add model for mario
    }

    private void simpleItem(ItemModelGenerators generator, ItemLike item, String tex) {
        generator.itemModelOutput.accept(item.asItem(), ItemModelUtils.plainModel( ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item.asItem()), TextureMapping.layer0(new Material(modLocation(tex).withPrefix("item/"))), generator.modelOutput)));
    }

    private void customModel(ItemModelGenerators generator, ItemLike item) {
        generator.declareCustomModelItem(item.asItem());
    }

    private void objModel(ItemModelGenerators generator, ItemLike item, Identifier modelLocation, Identifier texture, Consumer<RootTransformsBuilder> action) {
        generator.itemModelOutput.accept(item.asItem(),
            ItemModelUtils.plainModel(
                ExtendedModelTemplateBuilder.builder()
                .customLoader(ObjModelBuilder::new, o -> o.modelLocation(modelLocation))
                .requiredTextureSlot(TextureSlot.TEXTURE)
                .rootTransforms(action)
                .build()
                .create(item.asItem(), TextureMapping.defaultTexture(new Material(texture)), generator.modelOutput)));
    }

    protected void registerItems(ItemModelGenerators itemModels) {
        simpleItem(itemModels, RRItems.trollmask, "bf");
        simpleItem(itemModels, RRItems.safepill, "ak");
        //simpleItem(RRItems.rocket, "ar");
        simpleItem(itemModels, RRItems.remote, "am");
        simpleItem(itemModels, RRItems.pliers, "ap");
        simpleItem(itemModels, RRItems.fuse, "ag");
        //simpleItem(itemModels, RRItems.fuel, "af");
        objModel(itemModels, RRItems.fuel, RRIdentifiers.getModelLocation("o"), RRIdentifiers.etflamethrower, rootTransformsBuilder -> rootTransformsBuilder
            .translation(0.8F, 0.5F, -0.03F)
            .rotation(Axis.XP.rotationDegrees(-90))
            .rotation(Axis.ZP.rotationDegrees(160))
            .scale(0.15F));
        simpleItem(itemModels, RRItems.expill, "ai");
        simpleItem(itemModels, RRItems.core2, "az");
        simpleItem(itemModels, RRItems.core3, "ba");
        simpleItem(itemModels, RRItems.core1, "ay");
        simpleItem(itemModels, RRItems.chip, "bd");
        simpleItem(itemModels, RRItems.antenna, "aa");
        simpleItem(itemModels, RRItems.armyshovel, "aw");
        //simpleItem(RRItems.einsten, "ab");
        simpleItem(itemModels, RRItems.camera, "bi");
        simpleItem(itemModels, RRItems.knife, "ad");
        //simpleItem(RRItems.flamethrower, "ae");
        //simpleItem(RRItems.gasgrenade, "ah");
        //simpleItem(RRItems.hackm202, "bg");
        //simpleItem(RRItems.plasmacannon, "ao");
        //simpleItem(RRItems.roda, "be");
        //simpleItem(RRItems.roddisk, "as");
        //simpleItem(RRItems.rpg, "aq");
        //simpleItem(RRItems.seekm202, "bh");
        //simpleItem(RRItems.tesla, "ax");
        objModel(itemModels, RRItems.battery, RRIdentifiers.getModelLocation("k"), RRIdentifiers.etbattery, rootTransformsBuilder -> rootTransformsBuilder
            .translation(0.8f, 0.3f, -0.03f)
            .rotation(Axis.ZP.rotationDegrees(35))
            .rotation(Axis.YP.rotationDegrees(90))
            .scale(0.3f, 0.3f, 0.3f));
        objModel(itemModels, RRItems.binoculars, RRIdentifiers.getModelLocation("b"), RRIdentifiers.etbinoculars, rootTransformsBuilder -> rootTransformsBuilder
            .translation(0.5f, 0.5f, -0.03f)
            .rotation(Axis.ZP.rotationDegrees(35))
            .rotation(Axis.YP.rotationDegrees(90))
            .scale(0.35f, 0.35f, 0.35f)
            .translation(0.6f, 0.05f, 0.3f));
        customModel(itemModels, RRItems.gasgrenade);
        customModel(itemModels, RRItems.hackm202);
        //customModel(itemModels, RRItems.plasmacannon);
        customModel(itemModels, RRItems.roda);
        objModel(itemModels, RRItems.flamethrower, RRIdentifiers.getModelLocation("n"), RRIdentifiers.etflamethrower, rootTransformsBuilder ->
            rootTransformsBuilder.rotation(Axis.ZP.rotationDegrees(35))
                .translation(0.7f, 0.1f, 00f)
                .rotation(Axis.YP.rotationDegrees(270))
                .scale(0.18f, 0.18f, 0.18f));

        simpleItem(itemModels, RRItems.camohat, "oh");
        simpleItem(itemModels, RRItems.camoshirt, "ov");
        simpleItem(itemModels, RRItems.camopants, "op");
        simpleItem(itemModels, RRItems.camoshoes, "ob");

        simpleItem(itemModels, RRItems.camohat2, "sh");
        simpleItem(itemModels, RRItems.camoshirt2, "sv");
        simpleItem(itemModels, RRItems.camopants2, "sp");
        simpleItem(itemModels, RRItems.camoshoes2, "sb");

        simpleItem(itemModels, RRItems.orebelhelmet, "roh");
        simpleItem(itemModels, RRItems.orebelchest, "roc");
        simpleItem(itemModels, RRItems.orebelpants, "rop");
        simpleItem(itemModels, RRItems.orebelboots, "rob");

        simpleItem(itemModels, RRItems.onukerhelmet, "noh");
        simpleItem(itemModels, RRItems.onukerchest, "noc");
        simpleItem(itemModels, RRItems.onukerpants, "nop");
        simpleItem(itemModels, RRItems.onukerboots, "nob");

        simpleItem(itemModels, RRItems.ointelhelmet, "ioh");
        simpleItem(itemModels, RRItems.ointelchest, "ioc");
        simpleItem(itemModels, RRItems.ointelpants, "iop");
        simpleItem(itemModels, RRItems.ointelboots, "iob");

        simpleItem(itemModels, RRItems.ohackerhelmet, "hoh");
        simpleItem(itemModels, RRItems.ohackerchest, "hoc");
        simpleItem(itemModels, RRItems.ohackerpants, "hop");
        simpleItem(itemModels, RRItems.ohackerboots, "hob");

        simpleItem(itemModels, RRItems.srebelhelmet, "rsh");
        simpleItem(itemModels, RRItems.srebelchest, "rsc");
        simpleItem(itemModels, RRItems.srebelpants, "rsp");
        simpleItem(itemModels, RRItems.srebelboots, "rsb");

        simpleItem(itemModels, RRItems.snukerhelmet, "nsh");
        simpleItem(itemModels, RRItems.snukerchest, "nsc");
        simpleItem(itemModels, RRItems.snukerpants, "nsp");
        simpleItem(itemModels, RRItems.snukerboots, "nsb");

        simpleItem(itemModels, RRItems.sintelhelmet, "ish");
        simpleItem(itemModels, RRItems.sintelchest, "isc");
        simpleItem(itemModels, RRItems.sintelpants, "isp");
        simpleItem(itemModels, RRItems.sintelboots, "isb");

        simpleItem(itemModels, RRItems.shackerhelmet, "hsh");
        simpleItem(itemModels, RRItems.shackerchest, "hsc");
        simpleItem(itemModels, RRItems.shackerpants, "hsp");
        simpleItem(itemModels, RRItems.shackerboots, "hsb");

        itemModels.itemModelOutput.accept(
            RRItems.plasmacannon.get(),
            new SpecialModelWrapper.Unbaked(
                Identifier.fromNamespaceAndPath("minecraft", "air"),
                Optional.empty(),
                new PlasmaCannonRenderer.Unbaked(
                    RRIdentifiers.etplasmacannon,
                    RRIdentifiers.ethydrod
                )
            )
        );
        itemModels.itemModelOutput.accept(
            RRItems.redrod.get(),
            new SpecialModelWrapper.Unbaked(
                Identifier.fromNamespaceAndPath("minecraft", "air"),
                Optional.empty(),
                new RodRenderer.Unbaked(
                    RRIdentifiers.etredrod
                )
            )
        );
        itemModels.itemModelOutput.accept(
            RRItems.hydrod.get(),
            new SpecialModelWrapper.Unbaked(
                Identifier.fromNamespaceAndPath("minecraft", "air"),
                Optional.empty(),
                new RodRenderer.Unbaked(
                    RRIdentifiers.ethydrod
                )
            )
        );
        itemModels.itemModelOutput.accept(
            RRItems.NUCLEAR_ROD.get(),
            new SpecialModelWrapper.Unbaked(
                Identifier.fromNamespaceAndPath("minecraft", "air"),
                Optional.empty(),
                new RodRenderer.Unbaked(
                    RRIdentifiers.etradrod
                )
            )
        );
        itemModels.itemModelOutput.accept(
            RRItems.emptyrod.get(),
            new SpecialModelWrapper.Unbaked(
                Identifier.fromNamespaceAndPath("minecraft", "air"),
                Optional.empty(),
                new RodRenderer.Unbaked(
                    RRIdentifiers.etemptyrod
                )
            )
        );
        itemModels.itemModelOutput.accept(
            RRItems.einsten.get(),
            new SpecialModelWrapper.Unbaked(
                Identifier.fromNamespaceAndPath("minecraft", "air"),
                Optional.empty(),
                new AstroBlasterRenderer.Unbaked()
            )
        );
        itemModels.itemModelOutput.accept(
            RRItems.rpg.get(),
            new SpecialModelWrapper.Unbaked(
                Identifier.fromNamespaceAndPath("minecraft", "air"),
                Optional.empty(),
                new RocketLauncherRenderer.Unbaked()
            )
        );
        itemModels.itemModelOutput.accept(
            RRItems.tesla.get(),
            new SpecialModelWrapper.Unbaked(
                Identifier.fromNamespaceAndPath("minecraft", "air"),
                Optional.empty(),
                new TeslaRenderer.Unbaked()
            )
        );
        itemModels.itemModelOutput.accept(
            RRItems.rocket.get(),
            new SpecialModelWrapper.Unbaked(
                Identifier.fromNamespaceAndPath("minecraft", "air"),
                Optional.of(new Transformation(new Vector3f(0.8f, 0.3f, -0.03f), null, new Vector3f(2, 2, 2), null)),
                new RocketRenderer.Unbaked(RRIdentifiers.etrocket)
            )
        );
        itemModels.itemModelOutput.accept(
            RRItems.roddisk.get(),
            new SpecialModelWrapper.Unbaked(
                Identifier.fromNamespaceAndPath("minecraft", "air"),
                Optional.empty(),
                new RodDiskRenderer.Unbaked(RRIdentifiers.etdisk0)
            )
        );
        itemModels.itemModelOutput.accept(
            RRItems.seekm202.get(),
            new SpecialModelWrapper.Unbaked(
                Identifier.fromNamespaceAndPath("minecraft", "air"),
                Optional.empty(),
                new SeekRocketLauncherRenderer.Unbaked()
            )
        );
    }
}
