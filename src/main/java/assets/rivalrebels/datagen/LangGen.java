package assets.rivalrebels.datagen;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.core.RivalRebelsDamageSource.RRDamageTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

import java.util.concurrent.CompletableFuture;

public class LangGen extends FabricLanguageProvider { // TODO: Add Every Translation to here
    private TranslationBuilder translationBuilder;

    public LangGen(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        this.translationBuilder = translationBuilder;

        addDamage(RRDamageTypes.ELECTRICITY, "%1s is Now Electric-Man");
        addDamage(RRDamageTypes.CHARGE, "%1s Charged");
    }

    public void addDamage(ResourceKey<DamageType> resourceKey, String translation) {
        translationBuilder.add("death.attack."+ RivalRebels.MODID + "." + resourceKey.location().getPath(), translation);
    }
}
