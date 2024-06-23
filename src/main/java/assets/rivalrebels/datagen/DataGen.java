package assets.rivalrebels.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(RecipeDataGen::new);
        pack.addProvider(BlockStateDataGen::new);
        pack.addProvider(ItemModelDataGen::new);
        pack.addProvider(LootTableDataGen::new);
        pack.addProvider(SoundDataGen::new);
    }
}
