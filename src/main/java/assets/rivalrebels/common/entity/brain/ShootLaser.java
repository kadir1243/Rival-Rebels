package assets.rivalrebels.common.entity.brain;

import assets.rivalrebels.common.core.RRSounds;
import assets.rivalrebels.common.core.RivalRebelsDamageSource;
import assets.rivalrebels.common.core.RivalRebelsSoundPlayer;
import assets.rivalrebels.common.entity.EntityGore;
import assets.rivalrebels.common.entity.EntityRhodes;
import assets.rivalrebels.common.util.ItemUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;

import java.util.Map;

public class ShootLaser extends Behavior<EntityRhodes> {
    public ShootLaser() {
        super(Map.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT));
    }

    @Override
    protected void tick(ServerLevel level, EntityRhodes owner, long gameTime) {
        super.tick(level, owner, gameTime);
        float syaw = Mth.sin(owner.bodyyaw * Mth.DEG_TO_RAD);
        float cyaw = Mth.cos(owner.bodyyaw * Mth.DEG_TO_RAD);
        LivingEntity lastLaserTarget = owner.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
        owner.disableAllLasers();
        if (lastLaserTarget != null && lastLaserTarget.canBeSeenAsEnemy()) {
            float x = (float) (owner.getX() - lastLaserTarget.getX());
            float y = (float) (owner.getY() + 13 - lastLaserTarget.getY());
            float z = (float) (owner.getZ() - lastLaserTarget.getZ());
            float dot = syaw*x+cyaw*z;
            float pitch = (720f- EntityRhodes.atan2(Mth.sqrt(x*x+z*z) *(dot>0?-1f:1f), y)) %360-270;

            boolean pointing = true;
            if (Mth.abs(owner.headpitch-pitch) >= 0.001f)
            {
                owner.headpitch += Math.max(-20, Math.min(20, (pitch-owner.headpitch)));
                pointing = Mth.abs(owner.headpitch - pitch) < 3f;
            }

            if (pointing) {
                if (owner.endangered) owner.enableAllLasers();
                else owner.setOnLaserData(owner.getRandom().nextInt(2) + 1);
                RivalRebelsSoundPlayer.playSound(owner, 22, 1, 30f, 0f);
                if (lastLaserTarget instanceof Player player) {
                    ItemUtil.damageRandomArmor(player, 24, owner.getRandom());
                    lastLaserTarget.hurt(RivalRebelsDamageSource.laserBurst(owner.level()), owner.isAllLaserEnabled()?16:8);
                    if (player.getHealth() < 3 && player.isAlive()) {
                        player.hurt(RivalRebelsDamageSource.laserBurst(owner.level()), 2000000);
                        player.deathTime = 0;
                        owner.level().addFreshEntity(new EntityGore(owner.level(), lastLaserTarget, 0, 0));
                        owner.level().addFreshEntity(new EntityGore(owner.level(), lastLaserTarget, 1, 0));
                        owner.level().addFreshEntity(new EntityGore(owner.level(), lastLaserTarget, 2, 0));
                        owner.level().addFreshEntity(new EntityGore(owner.level(), lastLaserTarget, 2, 0));
                        owner.level().addFreshEntity(new EntityGore(owner.level(), lastLaserTarget, 3, 0));
                        owner.level().addFreshEntity(new EntityGore(owner.level(), lastLaserTarget, 3, 0));
                    }
                }
                else
                {
                    lastLaserTarget.hurt(RivalRebelsDamageSource.laserBurst(owner.level()), owner.isAllLaserEnabled()?16:8);
                    if (!lastLaserTarget.canBeSeenAsEnemy() || lastLaserTarget.getHealth() < 3) {
                        int legs;
                        int arms;
                        int mobs;
                        owner.playSound(RRSounds.BLASTER_FIRE, 1, 4);
                        switch (lastLaserTarget) {
                            case ZombifiedPiglin ignored -> {
                                legs = 2;
                                arms = 2;
                                mobs = 2;
                            }
                            case Zombie ignored -> {
                                legs = 2;
                                arms = 2;
                                mobs = 1;
                            }
                            case Skeleton ignored -> {
                                legs = 2;
                                arms = 2;
                                mobs = 3;
                            }
                            case EnderMan ignored -> {
                                legs = 2;
                                arms = 2;
                                mobs = 4;
                            }
                            case Creeper ignored -> {
                                legs = 4;
                                arms = 0;
                                mobs = 5;
                            }
                            case MagmaCube ignored -> {
                                legs = 0;
                                arms = 0;
                                mobs = 7;
                            }
                            case Slime ignored -> {
                                legs = 0;
                                arms = 0;
                                mobs = 6;
                            }
                            case CaveSpider ignored -> {
                                legs = 8;
                                arms = 0;
                                mobs = 9;
                            }
                            case Spider ignored -> {
                                legs = 8;
                                arms = 0;
                                mobs = 8;
                            }
                            case Ghast ignored -> {
                                legs = 9;
                                arms = 0;
                                mobs = 10;
                            }
                            case EntityRhodes ignored -> {
                                return;
                            }
                            default -> {
                                legs = (int) (lastLaserTarget.getBoundingBox().getSize() * 2);
                                arms = (int) (lastLaserTarget.getBoundingBox().getSize() * 2);
                                mobs = 11;
                            }
                        }
                        owner.level().addFreshEntity(new EntityGore(owner.level(), lastLaserTarget, 0, mobs));
                        owner.level().addFreshEntity(new EntityGore(owner.level(), lastLaserTarget, 1, mobs));
                        for (int i = 0; i < arms; i++)
                            owner.level().addFreshEntity(new EntityGore(owner.level(), lastLaserTarget, 2, mobs));
                        for (int i = 0; i < legs; i++)
                            owner.level().addFreshEntity(new EntityGore(owner.level(), lastLaserTarget, 3, mobs));
                        lastLaserTarget.kill();
                    }
                }
            }
        }

    }
}
