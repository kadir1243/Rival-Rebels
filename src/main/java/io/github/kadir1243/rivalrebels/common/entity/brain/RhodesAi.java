package io.github.kadir1243.rivalrebels.common.entity.brain;

import io.github.kadir1243.rivalrebels.common.entity.EntityRhodes;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;

public class RhodesAi {
    private static final ImmutableList<SensorType<? extends Sensor<? super EntityRhodes>>> SENSOR_TYPES = ImmutableList.of(

    );
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
        MemoryModuleType.UNIVERSAL_ANGER,
        MemoryModuleType.HURT_BY,
        MemoryModuleType.ATTACK_TARGET
    );

    public static Brain.Provider<EntityRhodes> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES, v -> getActivities());
    }

    public static List<ActivityData<EntityRhodes>> getActivities() {
        return List.of(initCoreActivity(), initIdleActivity(), initFightActivity(), initRaidActivity(), initShootActivity());
    }

    private static ActivityData<EntityRhodes> initIdleActivity() {
        return ActivityData.create(
            Activity.IDLE,
            0,
            ImmutableList.of(
                new StartingBrain(),
                new IdleBrain()
            )
        );
    }

    private static ActivityData<EntityRhodes> initCoreActivity() {
        return ActivityData.create(
            Activity.CORE,
            0,
            ImmutableList.of(
                new IdleBrain()
            )
        );
    }

    private static ActivityData<EntityRhodes> initFightActivity() {
        return ActivityData.create(
            Activity.FIGHT,
            0,
            ImmutableList.of(
                new ShootLaser(),
                new ShootFlame(),
                new ShootRocket()
            )
        );
    }

    private static ActivityData<EntityRhodes> initRaidActivity() {
        return ActivityData.create(
            Activity.RAID,
            0,
            ImmutableList.of(
                new RaidTeam()
            )
        );
    }

    private static ActivityData<EntityRhodes> initShootActivity() {
        return ActivityData.create(
            Activities.SHOOT_AND_ROTATE.get(),
            0,
            ImmutableList.of(
                new ShootAndRotate()
            )
        );
    }

    private static ActivityData<EntityRhodes> initRotateToTargetActivity() {
        return ActivityData.create(
            Activity.INVESTIGATE,
            0,
            ImmutableList.of(
                new RotateToTarget()
            )
        );
    }
}
