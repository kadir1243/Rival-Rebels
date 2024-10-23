package io.github.kadir1243.rivalrebels.datagen;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.RivalRebels;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BlockTagsGen extends BlockTagsProvider {
    public BlockTagsGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, RRIdentifiers.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(RivalRebels.MINEABLE_WITH_ARMY_SHOVEL)
            .add(RRBlocks.barricade.get())
            .add(RRBlocks.reactive.get())
            .add(RRBlocks.conduit.get())
            .add(RRBlocks.tower.get())
            .add(RRBlocks.steel.get())
            .add(RRBlocks.rhodesactivator.get())
            .add(RRBlocks.camo1.get())
            .add(RRBlocks.camo2.get())
            .add(RRBlocks.camo3.get())
            .add(RRBlocks.jump.get())
            .add(RRBlocks.landmine.get())
            .add(RRBlocks.alandmine.get())
            .add(RRBlocks.quicksand.get())
            .add(RRBlocks.aquicksand.get())
            .add(RRBlocks.mario.get())
            .add(RRBlocks.amario.get())
            .add(RRBlocks.loader.get())
            .add(RRBlocks.reactor.get())
            .add(RRBlocks.radioactivedirt.get())
            .add(RRBlocks.radioactivesand.get())
            .add(RRBlocks.petrifiedstone1.get())
            .add(RRBlocks.petrifiedstone2.get())
            .add(RRBlocks.petrifiedstone3.get())
            .add(RRBlocks.petrifiedstone4.get())
            .add(RRBlocks.petrifiedwood.get());

        tag(RivalRebels.NUCLEAR_STONE_GENERATEABLE)
            .addTag(BlockTags.BASE_STONE_OVERWORLD)
            .add(Blocks.COBBLESTONE)
            .addTag(BlockTags.DIRT);
    }
}
