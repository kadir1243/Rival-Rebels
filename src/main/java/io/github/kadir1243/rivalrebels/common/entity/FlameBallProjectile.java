package io.github.kadir1243.rivalrebels.common.entity;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class FlameBallProjectile extends Projectile {
    public int sequence;
    public float rotation;
    public float motionr;

    public FlameBallProjectile(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
        rotation = (float) (random.nextDouble() * 360);
        motionr = (float) (random.nextDouble() - 0.5f) * 5;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    public void tick() {
        super.tick();
        tickCount++;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }
}
