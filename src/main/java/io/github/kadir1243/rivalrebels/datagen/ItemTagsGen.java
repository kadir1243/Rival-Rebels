package io.github.kadir1243.rivalrebels.datagen;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.RivalRebels;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

import static io.github.kadir1243.rivalrebels.common.item.RRItems.*;

public class ItemTagsGen extends ItemTagsProvider {
    public ItemTagsGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> contentsGetter) {
        super(output, lookupProvider, RRIdentifiers.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.getOrCreateRawBuilder(RivalRebels.OMEGA_ARMOR)
            .addElement(camohat.getId())
            .addElement(camoshirt.getId())
            .addElement(camopants.getId())
            .addElement(camoshoes.getId())
            .addElement(orebelhelmet.getId())
            .addElement(orebelchest.getId())
            .addElement(orebelpants.getId())
            .addElement(orebelboots.getId())
            .addElement(onukerhelmet.getId())
            .addElement(onukerchest.getId())
            .addElement(onukerpants.getId())
            .addElement(onukerboots.getId())
            .addElement(ointelhelmet.getId())
            .addElement(ointelchest.getId())
            .addElement(ointelpants.getId())
            .addElement(ointelboots.getId())
            .addElement(ohackerhelmet.getId())
            .addElement(ohackerchest.getId())
            .addElement(ohackerpants.getId())
            .addElement(ohackerboots.getId())
        ;

        this.getOrCreateRawBuilder(RivalRebels.SIGMA_ARMOR)
            .addElement(camohat2.getId())
            .addElement(camoshirt2.getId())
            .addElement(camopants2.getId())
            .addElement(camoshoes2.getId())
            .addElement(srebelhelmet.getId())
            .addElement(srebelchest.getId())
            .addElement(srebelpants.getId())
            .addElement(srebelboots.getId())
            .addElement(snukerhelmet.getId())
            .addElement(snukerchest.getId())
            .addElement(snukerpants.getId())
            .addElement(snukerboots.getId())
            .addElement(sintelhelmet.getId())
            .addElement(sintelchest.getId())
            .addElement(sintelpants.getId())
            .addElement(sintelboots.getId())
            .addElement(shackerhelmet.getId())
            .addElement(shackerchest.getId())
            .addElement(shackerpants.getId())
            .addElement(shackerboots.getId())
        ;
    }
}
