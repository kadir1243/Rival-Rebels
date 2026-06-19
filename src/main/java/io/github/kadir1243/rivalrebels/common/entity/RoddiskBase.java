package io.github.kadir1243.rivalrebels.common.entity;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

public class RoddiskBase extends Projectile {
    public RoddiskBase(EntityType<? extends RoddiskBase> type, Level world) {
        super(type, world);
    }

    public RoddiskBase(EntityType<? extends RoddiskBase> type, Level world, Entity shooter) {
        this(type, world);
        this.setBoundingBox(new AABB(-0.4, -0.0625, -0.4, 0.4, 0.0625, 0.4));
        this.setOwner(shooter);
    }

    public RoddiskBase(EntityType<? extends RoddiskBase> type, Level world, Entity shooter, float speed) {
        this(type, world, shooter);
        this.snapTo(shooter.getEyePosition(), shooter.getYRot(), shooter.getXRot());
        setPos(getX() - (Mth.cos(this.getYRot() * Mth.DEG_TO_RAD) * 0.16F),
            getY() - 0.1,
            getZ() - (Mth.sin(this.getYRot() * Mth.DEG_TO_RAD) * 0.16F)
        );

        this.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0, speed * 1.5F, 1.0F);
    }

    @Override
    public boolean canBeCollidedWith(@Nullable Entity p_423659_) {
        return isAlive();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    public boolean isPushable() {
        return true;
    }
}
