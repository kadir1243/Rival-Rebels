package io.github.kadir1243.rivalrebels.datagen;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.entity.RREntities;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class EntityTypeTagsGen extends EntityTypeTagsProvider {
    public EntityTypeTagsGen(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, RRIdentifiers.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(EntityTypeTags.CAN_BREATHE_UNDER_WATER)
            .add(RREntities.RHODES.get());
        this.tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
            .add(RREntities.RHODES.get());
        this.tag(EntityTypeTags.IGNORES_POISON_AND_REGEN)
            .add(RREntities.RHODES.get());
        this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE)
            .add(RREntities.RHODES.get());
    }
}
