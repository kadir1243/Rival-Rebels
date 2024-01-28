package assets.rivalrebels.datagen;

import assets.rivalrebels.common.block.RRBlocks;
import assets.rivalrebels.common.item.RRItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.data.server.LootTableProvider;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LootTableDataGen extends LootTableProvider {
    public LootTableDataGen(DataGenerator arg) {
        super(arg);
    }

    @Override
    protected void validate(Map<Identifier, LootTable> map, LootTableReporter validationtracker) {
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<Identifier, LootTable.Builder>>>, LootContextType>> getTables() {
        return Collections.singletonList(
            Pair.of(BlockLootTableGen::new, LootContextTypes.BLOCK)
        );
    }

    public static class BlockLootTableGen extends BlockLootTableGenerator {
        @Override
        protected void addTables() {
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
        }
    }
}
