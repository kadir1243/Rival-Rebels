package io.github.kadir1243.rivalrebels.common.entity;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class AbstractBlastEntity<T> extends Entity {
    public T bomb;
    public double radius;

    public AbstractBlastEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    public boolean hurtServer(ServerLevel p_376804_, DamageSource p_376155_, float p_376892_) {
        return false;
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        radius = valueInput.getFloatOr("radius", 0);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        valueOutput.putFloat("radius", (float) radius);
    }
}
