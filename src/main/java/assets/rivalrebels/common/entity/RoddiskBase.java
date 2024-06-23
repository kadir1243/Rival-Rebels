package assets.rivalrebels.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class RoddiskBase extends EntityInanimate {
    public LivingEntity shooter;

    public RoddiskBase(EntityType<? extends RoddiskBase> type, World world) {
        super(type, world);
    }

    public RoddiskBase(EntityType<? extends RoddiskBase> type, World world, LivingEntity shooter) {
        this(type, world);
        this.setBoundingBox(new Box(-0.4, -0.0625, -0.4, 0.4, 0.0625, 0.4));
        this.shooter = shooter;
    }

    @Override
    public boolean isCollidable() {
        return isAlive();
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public boolean shouldRender(double distance) {
        return true;
    }

    @Override
    public float getBrightnessAtEyes()
    {
        return 1000F;
    }


}
