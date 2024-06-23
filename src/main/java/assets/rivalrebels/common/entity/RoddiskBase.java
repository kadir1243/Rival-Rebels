package assets.rivalrebels.common.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class RoddiskBase extends EntityInanimate {
    public LivingEntity shooter;

    public RoddiskBase(EntityType<? extends RoddiskBase> type, Level world) {
        super(type, world);
    }

    public RoddiskBase(EntityType<? extends RoddiskBase> type, Level world, LivingEntity shooter) {
        this(type, world);
        this.setBoundingBox(new AABB(-0.4, -0.0625, -0.4, 0.4, 0.0625, 0.4));
        this.shooter = shooter;
    }

    @Override
    public boolean canBeCollidedWith() {
        return isAlive();
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    @Override
    public float getLightLevelDependentMagicValue()
    {
        return 1000F;
    }


}
