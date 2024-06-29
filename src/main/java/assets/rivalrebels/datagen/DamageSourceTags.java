package assets.rivalrebels.datagen;

import assets.rivalrebels.common.core.RivalRebelsDamageSource.RRDamageTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;

import java.util.concurrent.CompletableFuture;

public class DamageSourceTags extends FabricTagProvider<DamageType> {
    public DamageSourceTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.DAMAGE_TYPE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR)
            .add(RRDamageTypes.ELECTRICITY, RRDamageTypes.RADIOACTIVE_POISONING);

        getOrCreateTagBuilder(DamageTypeTags.BYPASSES_SHIELD)
            .add(RRDamageTypes.ELECTRICITY,
                RRDamageTypes.RADIOACTIVE_POISONING,
                RRDamageTypes.NUCLEAR_BLAST,
                RRDamageTypes.COOKED,
                RRDamageTypes.GAS_GRENADE,
                RRDamageTypes.CUCHILLO,
                RRDamageTypes.TRON,
                RRDamageTypes.CYANIDE,
                RRDamageTypes.LANDMINE,
                RRDamageTypes.TIMED_BOMB,
                RRDamageTypes.FLARE,
                RRDamageTypes.CHARGE,
                RRDamageTypes.PLASMA_EXPLOSION,
                RRDamageTypes.ROCKET,
                RRDamageTypes.LASER_BURST);
    }
}
