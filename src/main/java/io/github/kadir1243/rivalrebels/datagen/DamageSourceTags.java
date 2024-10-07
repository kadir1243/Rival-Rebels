package io.github.kadir1243.rivalrebels.datagen;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource.RRDamageTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DamageSourceTags extends DamageTypeTagsProvider {
    public DamageSourceTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, RRIdentifiers.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR)
            .add(RRDamageTypes.ELECTRICITY, RRDamageTypes.RADIOACTIVE_POISONING);

        this.tag(DamageTypeTags.BYPASSES_SHIELD)
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
