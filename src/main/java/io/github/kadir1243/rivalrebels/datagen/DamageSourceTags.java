package io.github.kadir1243.rivalrebels.datagen;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource.RRDamageTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;

import java.util.concurrent.CompletableFuture;

public class DamageSourceTags extends DamageTypeTagsProvider {
    public DamageSourceTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, RRIdentifiers.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR)
            .add(RRDamageTypes.ELECTRICITY)
            .add(RRDamageTypes.RADIOACTIVE_POISONING)
        ;

        this.tag(DamageTypeTags.BYPASSES_SHIELD)
            .add(RRDamageTypes.ELECTRICITY)
            .add(RRDamageTypes.RADIOACTIVE_POISONING)
            .add(RRDamageTypes.NUCLEAR_BLAST)
            .add(RRDamageTypes.COOKED)
            .add(RRDamageTypes.GAS_GRENADE)
            .add(RRDamageTypes.CUCHILLO)
            .add(RRDamageTypes.TRON)
            .add(RRDamageTypes.CYANIDE)
            .add(RRDamageTypes.LANDMINE)
            .add(RRDamageTypes.TIMED_BOMB)
            .add(RRDamageTypes.FLARE)
            .add(RRDamageTypes.CHARGE)
            .add(RRDamageTypes.PLASMA_EXPLOSION)
            .add(RRDamageTypes.ROCKET)
            .add(RRDamageTypes.LASER_BURST)
        ;
    }
}
