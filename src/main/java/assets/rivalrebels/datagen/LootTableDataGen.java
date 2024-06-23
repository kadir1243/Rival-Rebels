package assets.rivalrebels.datagen;

import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.item.RRItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class LootTableDataGen extends FabricBlockLootTableProvider {
    public LootTableDataGen(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        dropOther(RRBlocks.camo1, RRBlocks.smartcamo);
        dropOther(RRBlocks.camo2, RRBlocks.smartcamo);
        dropOther(RRBlocks.camo3, RRBlocks.smartcamo);
        dropSelf(RRBlocks.alandmine);
        dropOther(RRBlocks.landmine, RRBlocks.alandmine);
        dropSelf(RRBlocks.amario);
        dropOther(RRBlocks.mario, RRBlocks.amario);
        dropOther(RRBlocks.quicksand, RRBlocks.aquicksand);
        dropSelf(RRBlocks.aquicksand);
        dropSelf(RRBlocks.flag1);
        dropOther(RRBlocks.flag2, RRItems.trollmask);
        dropSelf(RRBlocks.flag3);
        dropSelf(RRBlocks.flag4);
        dropSelf(RRBlocks.flag5);
        dropSelf(RRBlocks.flag6);
        dropSelf(RRBlocks.flag7);
        dropSelf(RRBlocks.controller);
    }
}
