package io.github.kadir1243.rivalrebels.datagen;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource;
import io.github.kadir1243.rivalrebels.common.core.RivalRebelsDamageSource.RRDamageTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;

import java.util.concurrent.CompletableFuture;

public class DamageSourceTags extends DamageTypeTagsProvider {
    public DamageSourceTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, RRIdentifiers.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR)
            .addOptional(RRDamageTypes.ELECTRICITY)
            .addOptional(RRDamageTypes.RADIOACTIVE_POISONING)
        ;

        this.tag(DamageTypeTags.BYPASSES_SHIELD)
            .addOptional(RRDamageTypes.ELECTRICITY)
            .addOptional(RRDamageTypes.RADIOACTIVE_POISONING)
            .addOptional(RRDamageTypes.NUCLEAR_BLAST)
            .addOptional(RRDamageTypes.COOKED)
            .addOptional(RRDamageTypes.GAS_GRENADE)
            .addOptional(RRDamageTypes.CUCHILLO)
            .addOptional(RRDamageTypes.TRON)
            .addOptional(RRDamageTypes.CYANIDE)
            .addOptional(RRDamageTypes.LANDMINE)
            .addOptional(RRDamageTypes.TIMED_BOMB)
            .addOptional(RRDamageTypes.FLARE)
            .addOptional(RRDamageTypes.CHARGE)
            .addOptional(RRDamageTypes.PLASMA_EXPLOSION)
            .addOptional(RRDamageTypes.ROCKET)
            .addOptional(RRDamageTypes.LASER_BURST)
        ;
    }

    public static void initialiseDamageSources(BootstrapContext<DamageType> context) {
        context.register(RivalRebelsDamageSource.RRDamageTypes.ELECTRICITY, new DamageType(RRIdentifiers.MODID + "." + "electricity", 1F));
        context.register(RivalRebelsDamageSource.RRDamageTypes.RADIOACTIVE_POISONING, new DamageType(RRIdentifiers.MODID + "." + "radioactive_poisoning", 1F));
        context.register(RivalRebelsDamageSource.RRDamageTypes.NUCLEAR_BLAST, new DamageType(RRIdentifiers.MODID + "." + "nuclear_blast", 1F));
        context.register(RivalRebelsDamageSource.RRDamageTypes.COOKED, new DamageType(RRIdentifiers.MODID + "." + "cooked", 1F));
        context.register(RivalRebelsDamageSource.RRDamageTypes.GAS_GRENADE, new DamageType(RRIdentifiers.MODID + "." + "gas_grenade", 1F));
        context.register(RivalRebelsDamageSource.RRDamageTypes.CUCHILLO, new DamageType(RRIdentifiers.MODID + "." + "cuchillo", 1F));
        context.register(RivalRebelsDamageSource.RRDamageTypes.TRON, new DamageType(RRIdentifiers.MODID + "." + "tron", 1F));
        context.register(RivalRebelsDamageSource.RRDamageTypes.CYANIDE, new DamageType(RRIdentifiers.MODID + "." + "cyanide", 1F));
        context.register(RivalRebelsDamageSource.RRDamageTypes.LANDMINE, new DamageType(RRIdentifiers.MODID + "." + "landmine", 1F));
        context.register(RivalRebelsDamageSource.RRDamageTypes.TIMED_BOMB, new DamageType(RRIdentifiers.MODID + "." + "timed_bomb", 1F));
        context.register(RivalRebelsDamageSource.RRDamageTypes.FLARE, new DamageType(RRIdentifiers.MODID + "." + "flare", 1F));
        context.register(RivalRebelsDamageSource.RRDamageTypes.CHARGE, new DamageType(RRIdentifiers.MODID + "." + "charge", 1F));
        context.register(RivalRebelsDamageSource.RRDamageTypes.PLASMA_EXPLOSION, new DamageType(RRIdentifiers.MODID + "." + "plasma_explosion", 1F));
        context.register(RivalRebelsDamageSource.RRDamageTypes.ROCKET, new DamageType(RRIdentifiers.MODID + "." + "rocket", 1F));
        context.register(RivalRebelsDamageSource.RRDamageTypes.LASER_BURST, new DamageType(RRIdentifiers.MODID + "." + "laser_burst", 1F));
    }
}
