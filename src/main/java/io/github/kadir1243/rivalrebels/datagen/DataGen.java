package io.github.kadir1243.rivalrebels.datagen;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;

import java.util.Set;

public class DataGen {
    public static void onGatherClientData(GatherDataEvent.Client event) {
        event.createProvider(BlockStateDataGen::new);
        event.createProvider(LangGen::new);
        event.createProvider(SoundDataGen::new);
        event.createProvider(EquipmentAssetGen::new);
        onGatherServerData(event);
    }

    public static void onGatherRegistries(GatherDataRegistryEntriesEvent event) {
        event.gatherFor(RRIdentifiers.MODID)
                .add(RecipeProvider.asBootstrap(RecipeDataGen::new))
                .add(Registries.LOOT_TABLE, LootTableDataGen::new)
                .add(Registries.DAMAGE_TYPE, DamageSourceTags::initialiseDamageSources);
    }

    private static void onGatherServerData(GatherDataEvent event) {
        event.createProvider(DamageSourceTags::new);
        event.createBlockAndItemTags(BlockTagsGen::new, ItemTagsGen::new);
        event.createProvider(EntityTypeTagsGen::new);
    }

    public static void onGatherServerSpecificData(GatherDataEvent.Server event) {
        onGatherServerData(event);
    }
}
