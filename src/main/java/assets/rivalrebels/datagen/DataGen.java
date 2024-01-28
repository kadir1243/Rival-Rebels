package assets.rivalrebels.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class DataGen {
    @SubscribeEvent
    public static void onData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new RecipeDataGen(generator));
            generator.addProvider(new LootTableDataGen(generator));
        }
        if (event.includeClient()) {
            generator.addProvider(new BlockStateDataGen(generator, event.getExistingFileHelper()));
            generator.addProvider(new ItemModelDataGen(generator, event.getExistingFileHelper()));
        }
    }
}
