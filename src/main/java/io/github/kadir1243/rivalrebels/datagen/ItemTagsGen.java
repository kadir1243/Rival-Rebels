package io.github.kadir1243.rivalrebels.datagen;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.RivalRebels;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static io.github.kadir1243.rivalrebels.common.item.RRItems.*;

public class ItemTagsGen extends ItemTagsProvider {
    public ItemTagsGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, RRIdentifiers.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(RivalRebels.OMEGA_ARMOR)
            .add(camohat.asItem())
            .add(camoshirt.asItem())
            .add(camopants.asItem())
            .add(camoshoes.asItem())
            .add(orebelhelmet.asItem())
            .add(orebelchest.asItem())
            .add(orebelpants.asItem())
            .add(orebelboots.asItem())
            .add(onukerhelmet.asItem())
            .add(onukerchest.asItem())
            .add(onukerpants.asItem())
            .add(onukerboots.asItem())
            .add(ointelhelmet.asItem())
            .add(ointelchest.asItem())
            .add(ointelpants.asItem())
            .add(ointelboots.asItem())
            .add(ohackerhelmet.asItem())
            .add(ohackerchest.asItem())
            .add(ohackerpants.asItem())
            .add(ohackerboots.asItem())
        ;

        tag(RivalRebels.SIGMA_ARMOR)
            .add(camohat2.asItem())
            .add(camoshirt2.asItem())
            .add(camopants2.asItem())
            .add(camoshoes2.asItem())
            .add(srebelhelmet.asItem())
            .add(srebelchest.asItem())
            .add(srebelpants.asItem())
            .add(srebelboots.asItem())
            .add(snukerhelmet.asItem())
            .add(snukerchest.asItem())
            .add(snukerpants.asItem())
            .add(snukerboots.asItem())
            .add(sintelhelmet.asItem())
            .add(sintelchest.asItem())
            .add(sintelpants.asItem())
            .add(sintelboots.asItem())
            .add(shackerhelmet.asItem())
            .add(shackerchest.asItem())
            .add(shackerpants.asItem())
            .add(shackerboots.asItem())
        ;
    }
}
