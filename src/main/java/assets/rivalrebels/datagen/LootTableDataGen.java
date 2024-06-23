package assets.rivalrebels.datagen;

import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.item.RRItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.data.server.loottable.LootTableProvider;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.resource.featuretoggle.FeatureSet;

import java.util.List;
import java.util.Set;

public class LootTableDataGen extends LootTableProvider {
    public LootTableDataGen(FabricDataOutput data) {
        super(data, Set.of(), List.of(new LootTypeGenerator(BlockLootTableGen::new, LootContextTypes.BLOCK)));
    }

    public static class BlockLootTableGen extends BlockLootTableGenerator {
        protected BlockLootTableGen() {
            super(Set.of(), FeatureSet.empty());
        }

        @Override
        public void generate() {
            addDrop(RRBlocks.camo1, RRBlocks.smartcamo);
            addDrop(RRBlocks.camo2, RRBlocks.smartcamo);
            addDrop(RRBlocks.camo3, RRBlocks.smartcamo);
            addDrop(RRBlocks.alandmine);
            addDrop(RRBlocks.landmine, RRBlocks.alandmine);
            addDrop(RRBlocks.amario);
            addDrop(RRBlocks.mario, RRBlocks.amario);
            addDrop(RRBlocks.quicksand, RRBlocks.aquicksand);
            addDrop(RRBlocks.aquicksand);
            addDrop(RRBlocks.flag1);
            addDrop(RRBlocks.flag2, RRItems.trollmask);
            addDrop(RRBlocks.flag3);
            addDrop(RRBlocks.flag4);
            addDrop(RRBlocks.flag5);
            addDrop(RRBlocks.flag6);
            addDrop(RRBlocks.flag7);
            addDrop(RRBlocks.controller);
        }
    }

    @Override
    public String getName() {
        return "RivalRebels Loot Tables";
    }
}
