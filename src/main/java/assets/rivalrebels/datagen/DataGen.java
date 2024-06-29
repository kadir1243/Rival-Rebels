package assets.rivalrebels.datagen;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class DataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(RecipeDataGen::new);
        pack.addProvider(BlockStateDataGen::new);
        pack.addProvider(ItemModelDataGen::new);
        pack.addProvider(LootTableDataGen::new);
        pack.addProvider(SoundDataGen::new);
        pack.addProvider(DamageSourceTags::new);
        pack.addProvider(LangGen::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.DAMAGE_TYPE, bootstrapContext -> {
            for (ResourceKey<DamageType> type : RivalRebelsDamageSource.RRDamageTypes.REGISTERED_DAMAGE_TYPES) {
                bootstrapContext.register(type, new DamageType(RivalRebels.MODID + "." + type.location().getPath(), 1F));
            }
        });
    }
}
