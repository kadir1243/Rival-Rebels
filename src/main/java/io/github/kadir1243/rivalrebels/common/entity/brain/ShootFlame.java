package io.github.kadir1243.rivalrebels.common.entity.brain;

import io.github.kadir1243.rivalrebels.common.core.RRSounds;
import io.github.kadir1243.rivalrebels.common.entity.EntityFlameBall;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.Map;

public class ShootFlame extends Behavior<EntityRhodes> {
    public ShootFlame() {
        super(Map.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT));
    }

    @Override
    protected void tick(ServerLevel level, EntityRhodes owner, long gameTime) {
        super.tick(level, owner, gameTime);
        float syaw = Mth.sin(owner.bodyyaw * Mth.DEG_TO_RAD);
        float cyaw = Mth.cos(owner.bodyyaw * Mth.DEG_TO_RAD);
        float px = (float) owner.getX() - cyaw * 6.4f * owner.getScale();
        float py = (float) owner.getY() + 6.26759f * owner.getScale();
        float pz = (float) owner.getZ() + syaw * 6.4f * owner.getScale();
        LivingEntity target = owner.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
        if (target != null && owner.canAttack(target, TargetingConditions.forCombat())) {
            float x = px - (float) target.getX();
            float y = py - (float) target.getY() - (target.getBbHeight() * 0.5f);
            float z = pz - (float) target.getZ();
            float yaw = ((EntityRhodes.atan2(x, z) - owner.bodyyaw + 810) % 360) - 270;
            float pitch = -(EntityRhodes.atan2(Mth.sqrt(x * x + z * z), y));
            boolean pointing = true;
            if (Mth.abs(owner.rightarmyaw - yaw) >= 0.001f) {
                owner.rightarmyaw += Math.max(-3, Math.min(3, (yaw - owner.rightarmyaw)));
                if (Mth.abs(owner.rightarmyaw - yaw) < 0.001f) pointing &= true;
                else pointing = false;
            }
            if (Mth.abs(owner.rightarmpitch - pitch) >= 0.001f) {
                owner.rightarmpitch += Math.max(-3, Math.min(3, (pitch - owner.rightarmpitch)));
                if (Mth.abs(owner.rightarmpitch - pitch) < 0.001f) pointing &= true;
                else pointing = false;
            }

            if (pointing) {
                owner.playSound(RRSounds.FLAME_THROWER_EXTINGUISH.get());
                float cp = -1f / Mth.sqrt(x * x + y * y + z * z);
                x *= cp;
                y *= cp;
                z *= cp;
                owner.level().addFreshEntity(new EntityFlameBall(owner.level(), px, py, pz,
                    x, y, z, (8 + owner.getRandom().nextDouble() * 8) * owner.getScale(), 0.4f));
                owner.level().addFreshEntity(new EntityFlameBall(owner.level(), px, py, pz,
                    x, y, z, (8 + owner.getRandom().nextDouble() * 8) * owner.getScale(), 0.4f));
            }
        }
        if (!target.canBeSeenAsEnemy()) {
            owner.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
        }
    }
}
