package assets.rivalrebels.datagen;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.block.RRBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Identifier;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class BlockStateDataGen extends BlockStateProvider {
    public BlockStateDataGen(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, RivalRebels.MODID, exFileHelper);
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
        simpleBlock(RRBlocks.flare, models().torch(name(RRBlocks.flare), modLoc("an")));
        simpleBlock(RRBlocks.nuclearBomb, "ak");
        simpleBlock(RRBlocks.tsarbombablock, "ak");
        simpleBlock(RRBlocks.toxicgas, "ak");
        simpleBlock(RRBlocks.theoreticaltsarbombablock, "ak");
        simpleBlock(RRBlocks.tachyonbombblock, "ak");
    }

    private void simpleBlock(Block block, String textureLoc) {
        simpleBlock(block, models().cubeAll(name(block), modLoc(textureLoc)));
    }

    private void simpleBlock(Block block, String sides, String down, String up) {
        simpleBlock(block, models().cubeBottomTop(name(block), modLoc(sides), modLoc(down), modLoc(up)));
    }

    private void simpleBlock(Block block, String sides, String up_and_down) {
        simpleBlock(block, models().cubeBottomTop(name(block), modLoc(sides), modLoc(up_and_down), modLoc(up_and_down)));
    }

    private String name(ForgeRegistryEntry<?> registry) {
        Identifier registryName = registry.getRegistryName();
        if (registryName == null) {
            throw new UnsupportedOperationException("Registry Name is not available");
        }
        return registryName.getNamespace();
    }
}
