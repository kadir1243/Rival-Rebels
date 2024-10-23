package io.github.kadir1243.rivalrebels.datagen;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DataGen {
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event.includeClient(), new BlockStateDataGen(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new ItemModelDataGen(output, existingFileHelper));
        generator.addProvider(event.includeClient(), (DataProvider.Factory<?>) LangGen::new);

        generator.addProvider(event.includeServer(), new RecipeDataGen(output, lookupProvider));
        generator.addProvider(event.includeServer(), new LootTableDataGen(output, lookupProvider));
        generator.addProvider(event.includeServer(), new SoundDataGen(output, existingFileHelper));
        generator.addProvider(event.includeServer(), new EntityTypeTagsGen(output, lookupProvider, existingFileHelper));

        DatapackBuiltinEntriesProvider provider = new DatapackBuiltinEntriesProvider(output, lookupProvider, new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, bootstrap -> {
                for (ResourceKey<DamageType> type : RivalRebelsDamageSource.RRDamageTypes.REGISTERED_DAMAGE_TYPES) {
                    bootstrap.register(type, new DamageType(RRIdentifiers.MODID + "." + type.location().getPath(), 1F));
                }
            })
            , Set.of(RRIdentifiers.MODID));
        generator.addProvider(event.includeServer(), provider);
        generator.addProvider(event.includeServer(), new DamageSourceTags(output, provider.getRegistryProvider(), existingFileHelper));
        BlockTagsGen blockTagsGen = new BlockTagsGen(output, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsGen);
        generator.addProvider(event.includeServer(), new ItemTagsGen(output, lookupProvider, blockTagsGen.contentsGetter(), existingFileHelper));
    }
}
