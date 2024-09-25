package assets.rivalrebels.common.entity.brain;

import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Map;
import java.util.function.Predicate;

public class ShootRocket extends Behavior<EntityRhodes> {
    public ShootRocket() {
        super(Map.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.REGISTERED,
            MemoryModuleTypes.ROCKET_BLOCK_TARGET, MemoryStatus.REGISTERED));
    }

    @Override
    protected void start(ServerLevel level, EntityRhodes entity, long gameTime) {
        super.start(level, entity, gameTime);
        entity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(livingEntity -> entity.setPose(Pose.SHOOTING));
    }

    @Override
    protected void stop(ServerLevel level, EntityRhodes entity, long gameTime) {
        super.stop(level, entity, gameTime);
        if (entity.getPose() == Pose.SHOOTING) {
            entity.setPose(Pose.STANDING);
        }
    }

    @Override
    protected void tick(ServerLevel level, EntityRhodes owner, long gameTime) {
        super.tick(level, owner, gameTime);
        float syaw = Mth.sin(owner.bodyyaw * Mth.DEG_TO_RAD);
        float cyaw = Mth.cos(owner.bodyyaw * Mth.DEG_TO_RAD);
        shootRocketsAtBestTarget(owner, -syaw, cyaw);
        Brain<EntityRhodes> brain = owner.getBrain();
        if (brain.getMemory(MemoryModuleType.ATTACK_TARGET).filter(owner::canAttack).isEmpty()) {
            brain.eraseMemory(MemoryModuleType.ATTACK_TARGET);
        }
        if (brain.getMemory(MemoryModuleTypes.ROCKET_BLOCK_TARGET).filter(Predicate.not(BlockEntity::isRemoved)).isEmpty()) {
            brain.eraseMemory(MemoryModuleTypes.ROCKET_BLOCK_TARGET);
        }
    }

    private int lastshot;
    private int shotstaken;
    boolean nuke = false;
    private void shootRocketsAtBestTarget(EntityRhodes owner, float syaw, float cyaw) {
        if (owner.getRocketCount() < 0) return;
        LivingEntity targetEntity = owner.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
        BlockEntity targetBlock = owner.getBrain().getMemory(MemoryModuleTypes.ROCKET_BLOCK_TARGET).orElse(null);
        float px = (float) owner.getX() + cyaw * 6.4f * owner.getScale();
        float py = (float) owner.getY() + 6.26759f * owner.getScale();
        float pz = (float) owner.getZ() + syaw * 6.4f * owner.getScale();
        if (targetBlock != null && !nuke) {
            float x = px - (float) targetBlock.getBlockPos().getX();
            float y = py - (float) targetBlock.getBlockPos().getY();
            float z = pz - (float) targetBlock.getBlockPos().getZ();
            float yaw = ((EntityRhodes.atan2(x, z) - owner.bodyyaw + 630) % 360) - 90;
            float pitch = -(EntityRhodes.atan2(Mth.sqrt(x * x + z * z), y));
            boolean pointing = true;
            if (Mth.abs(owner.leftarmyaw - yaw) >= 0.001f) {
                owner.leftarmyaw += Math.max(-3, Math.min(3, (yaw - owner.leftarmyaw)));
                if (Mth.abs(owner.leftarmyaw - yaw) < 3f) pointing &= true;
                else pointing = false;
            }
            if (Mth.abs(owner.leftarmpitch - pitch) >= 0.001f) {
                owner.leftarmpitch += Math.max(-3, Math.min(3, (pitch - owner.leftarmpitch)));
                if (Mth.abs(owner.leftarmpitch - pitch) < 3f) pointing &= true;
                else pointing = false;
            }

            if (pointing && owner.tickCount - lastshot > ((owner.getScale() >= 2.0) ? 30 : ((shotstaken == 21) ? 80 : 5))) {
                owner.setRocketCount(owner.getRocketCount() - 1);
                lastshot = owner.tickCount;
                if (shotstaken == 21) shotstaken = 0;
                shotstaken++;
                RivalRebelsSoundPlayer.playSound(owner, 23, 10, 1f);
                float cp = -0.5f / Mth.sqrt(x * x + y * y + z * z);
                if (owner.getScale() >= 2.0)
                    owner.level().addFreshEntity(new EntityB83NoShroom(owner.level(), px, py, pz,
                        x * cp, y * cp, z * cp));
                else
                    owner.level().addFreshEntity(new EntitySeekB83(owner.level(), px, py, pz,
                        x * cp, y * cp, z * cp));
            }
        } else if (targetEntity != null && targetEntity.canBeSeenAsEnemy()) {
            float x = px - (float) targetEntity.getX();
            float y = py - (float) targetEntity.getY();
            float z = pz - (float) targetEntity.getZ();
            float yaw = ((EntityRhodes.atan2(x, z) - owner.bodyyaw + 630) % 360) - 90;
            float pitch = -(EntityRhodes.atan2(Mth.sqrt(x * x + z * z), y));
            boolean pointing = true;
            if (Mth.abs(owner.leftarmyaw - yaw) >= 0.001f) {
                owner.leftarmyaw += Math.max(-3, Math.min(3, (yaw - owner.leftarmyaw)));
                if (Mth.abs(owner.leftarmyaw - yaw) < 3f) pointing &= true;
                else pointing = false;
            }
            if (Mth.abs(owner.leftarmpitch - pitch) >= 0.001f) {
                owner.leftarmpitch += Math.max(-3, Math.min(3, (pitch - owner.leftarmpitch)));
                if (Mth.abs(owner.leftarmpitch - pitch) < 3f) pointing &= true;
                else pointing = false;
            }

            if (targetEntity.getMaxHealth() > 1000) {
                if (pointing && owner.tickCount % 100 == 0) {
                    RivalRebelsSoundPlayer.playSound(owner, 23, 10, 1f);
                    float cp = -0.5f / Mth.sqrt(x * x + y * y + z * z);
                    if (owner.getScale() >= 2.0)
                        owner.level().addFreshEntity(new EntityTsar(owner.level(), px, py, pz,
                            x * cp * 5.0f, y * cp * 5.0f, z * cp * 5.0f));
                    else
                        owner.level().addFreshEntity(new EntityB83NoShroom(owner.level(), px, py, pz,
                            x * cp, y * cp, z * cp));
                }
            } else {
                if (pointing && owner.tickCount - lastshot > ((owner.getScale() >= 2.0) ? 30 : ((shotstaken == 21) ? 80 : 5))) {
                    owner.setRocketCount(owner.getRocketCount() - 1);
                    lastshot = owner.tickCount;
                    if (shotstaken == 21) shotstaken = 0;
                    shotstaken++;
                    RivalRebelsSoundPlayer.playSound(owner, 23, 10, 1f);
                    float cp = -0.5f / Mth.sqrt(x * x + y * y + z * z);
                    if (owner.getScale() >= 2.0)
                        owner.level().addFreshEntity(new EntityB83NoShroom(owner.level(), px, py, pz,
                            x * cp, y * cp, z * cp));
                    else
                        owner.level().addFreshEntity(new EntitySeekB83(owner.level(), px, py, pz,
                            x * cp, y * cp, z * cp));
                }
            }
        }
    }

}
