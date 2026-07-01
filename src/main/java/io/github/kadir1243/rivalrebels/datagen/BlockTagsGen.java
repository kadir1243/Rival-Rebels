package io.github.kadir1243.rivalrebels.datagen;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.RivalRebels;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.references.BlockItemIds;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class BlockTagsGen extends BlockTagsProvider {
    public BlockTagsGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, RRIdentifiers.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.getOrCreateRawBuilder(RivalRebels.MINEABLE_WITH_ARMY_SHOVEL)
            .addElement(RRBlocks.barricade.getId())
            .addElement(RRBlocks.reactive.getId())
            .addElement(RRBlocks.conduit.getId())
            .addElement(RRBlocks.tower.getId())
            .addElement(RRBlocks.steel.getId())
            .addElement(RRBlocks.rhodesactivator.getId())
            .addElement(RRBlocks.camo1.getId())
            .addElement(RRBlocks.camo2.getId())
            .addElement(RRBlocks.camo3.getId())
            .addElement(RRBlocks.jump.getId())
            .addElement(RRBlocks.landmine.getId())
            .addElement(RRBlocks.alandmine.getId())
            .addElement(RRBlocks.quicksand.getId())
            .addElement(RRBlocks.aquicksand.getId())
            .addElement(RRBlocks.mario.getId())
            .addElement(RRBlocks.amario.getId())
            .addElement(RRBlocks.loader.getId())
            .addElement(RRBlocks.reactor.getId())
            .addElement(RRBlocks.radioactivedirt.getId())
            .addElement(RRBlocks.radioactivesand.getId())
            .addElement(RRBlocks.petrifiedstone1.getId())
            .addElement(RRBlocks.petrifiedstone2.getId())
            .addElement(RRBlocks.petrifiedstone3.getId())
            .addElement(RRBlocks.petrifiedstone4.getId())
            .addElement(RRBlocks.petrifiedwood.getId());

        tag(RivalRebels.NUCLEAR_STONE_GENERATEABLE)
            .addTag(BlockTags.BASE_STONE_OVERWORLD)
            .add(BlockItemIds.COBBLESTONE.block())
            .addTag(BlockTags.DIRT);
    }
}
