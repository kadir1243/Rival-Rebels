package assets.rivalrebels.datagen;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

import java.util.concurrent.CompletableFuture;

public class DynamicRegistryGen extends FabricDynamicRegistryProvider {
    public DynamicRegistryGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        for (ResourceKey<DamageType> type : RivalRebelsDamageSource.RRDamageTypes.REGISTERED_DAMAGE_TYPES) {
            entries.add(type, new DamageType(RRIdentifiers.MODID + "." + type.location().getPath(), 1F));
        }
    }

    @Override
    public String getName() {
        return RRIdentifiers.MODID + " Dynamic Registry Generator";
    }
}
