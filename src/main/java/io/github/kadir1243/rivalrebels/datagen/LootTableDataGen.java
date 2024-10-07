package io.github.kadir1243.rivalrebels.datagen;

import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.item.RRItems;
import net.minecraft.data.PackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class LootTableDataGen extends LootTableProvider {
    public LootTableDataGen(PackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, Set.of(), List.of(new SubProviderEntry(BlockLoots::new, LootContextParamSets.BLOCK)), registryLookup);
    }

    public static class BlockLoots extends BlockLootSubProvider {
        protected BlockLoots(HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return RRBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get).map(block -> (Block)block).toList();
        }

        @Override
        protected void generate() {
            dropOther(RRBlocks.camo1.get(), RRBlocks.smartcamo);
            dropOther(RRBlocks.camo2.get(), RRBlocks.smartcamo);
            dropOther(RRBlocks.camo3.get(), RRBlocks.smartcamo);
            dropSelf(RRBlocks.alandmine.get());
            dropOther(RRBlocks.landmine.get(), RRBlocks.alandmine);
            dropSelf(RRBlocks.amario.get());
            dropOther(RRBlocks.mario.get(), RRBlocks.amario);
            dropOther(RRBlocks.quicksand.get(), RRBlocks.aquicksand);
            dropSelf(RRBlocks.aquicksand.get());
            dropSelf(RRBlocks.flag1.get());
            dropOther(RRBlocks.trollFlag.get(), RRItems.trollmask);
            dropSelf(RRBlocks.flag3.get());
            dropSelf(RRBlocks.flag4.get());
            dropSelf(RRBlocks.flag5.get());
            dropSelf(RRBlocks.flag6.get());
            dropSelf(RRBlocks.flag7.get());
            dropSelf(RRBlocks.barricade.get());
            dropSelf(RRBlocks.tower.get());
            dropSelf(RRBlocks.easteregg.get());
            dropSelf(RRBlocks.bunker.get());
            dropSelf(RRBlocks.smartcamo.get());
            dropSelf(RRBlocks.steel.get());
            dropSelf(RRBlocks.jump.get());
            dropSelf(RRBlocks.cycle.get());
            dropSelf(RRBlocks.gamestart.get());
            dropSelf(RRBlocks.breadbox.get());
            dropSelf(RRBlocks.nukeCrateTop.get());
            dropSelf(RRBlocks.nukeCrateBottom.get());
            dropSelf(RRBlocks.radioactivedirt.get());
            dropSelf(RRBlocks.radioactivesand.get());
            dropSelf(RRBlocks.reactor.get());
            dropSelf(RRBlocks.petrifiedwood.get());
            dropSelf(RRBlocks.petrifiedstone1.get());
            dropSelf(RRBlocks.petrifiedstone2.get());
            dropSelf(RRBlocks.petrifiedstone3.get());
            dropSelf(RRBlocks.petrifiedstone4.get());
            dropSelf(RRBlocks.forcefieldnode.get());
            dropSelf(RRBlocks.bastion.get());
            dropSelf(RRBlocks.conduit.get());
            dropSelf(RRBlocks.mariotrap.get());
            dropSelf(RRBlocks.minetrap.get());
            dropSelf(RRBlocks.quicksandtrap.get());
            dropSelf(RRBlocks.buildrhodes.get());
            dropSelf(RRBlocks.rhodesactivator.get());
        }
    }
}
