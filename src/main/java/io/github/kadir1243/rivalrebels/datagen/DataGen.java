package io.github.kadir1243.rivalrebels.datagen;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DataGen {
    public static void onGatherClientData(GatherDataEvent.Client event) {
        event.createProvider(BlockStateDataGen::new);
        event.createProvider(LangGen::new);
        event.createProvider(SoundDataGen::new);
        event.createProvider(EquipmentAssetGen::new);
        onGatherServerData(event);
    }

    private static void onGatherServerData(GatherDataEvent event) {
        event.createDatapackRegistryObjects(new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, context -> {
                for (ResourceKey<DamageType> type : RivalRebelsDamageSource.RRDamageTypes.REGISTERED_DAMAGE_TYPES) {
                    context.register(type, new DamageType(RRIdentifiers.MODID + "." + type.identifier().getPath(), 1F));
                }
            })
        );
        event.createProvider(DamageSourceTags::new);
        event.createBlockAndItemTags(BlockTagsGen::new, ItemTagsGen::new);
        event.createProvider(RecipeDataGen.RecipeRunner::new);
        // FIXME: event.createProvider(LootTableDataGen::new);
        event.createProvider(EntityTypeTagsGen::new);
    }

    public static void onGatherServerSpecificData(GatherDataEvent.Server event) {
        onGatherServerData(event);
    }
}
