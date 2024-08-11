package assets.rivalrebels.common.entity;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

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
        this.moveTo(shooter.getEyePosition(), shooter.getYRot(), shooter.getXRot());
        setPos(getX() - (Mth.cos(this.getYRot() / 180.0F * Mth.PI) * 0.16F),
            getY() - 0.1,
            getZ() - (Mth.sin(this.getYRot() / 180.0F * Mth.PI) * 0.16F)
        );

        this.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0, speed * 1.5F, 1.0F);
    }

    @Override
    public boolean canBeCollidedWith() {
        return isAlive();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        return super.interact(player, hand);
    }
}
