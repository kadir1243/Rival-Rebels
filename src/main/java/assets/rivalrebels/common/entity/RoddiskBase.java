package assets.rivalrebels.common.entity;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class RoddiskBase extends Projectile {
    public RoddiskBase(EntityType<? extends RoddiskBase> type, Level world) {
        super(type, world);
    }

    public RoddiskBase(EntityType<? extends RoddiskBase> type, Level world, LivingEntity shooter) {
        this(type, world);
        this.setBoundingBox(new AABB(-0.4, -0.0625, -0.4, 0.4, 0.0625, 0.4));
        this.setOwner(shooter);
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
