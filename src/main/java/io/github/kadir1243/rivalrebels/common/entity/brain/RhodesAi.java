package io.github.kadir1243.rivalrebels.common.entity.brain;

import io.github.kadir1243.rivalrebels.common.entity.EntityRhodes;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;

public class RhodesAi {
    private static final ImmutableList<SensorType<? extends Sensor<? super EntityRhodes>>> SENSOR_TYPES = ImmutableList.of(

    );
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
        MemoryModuleTypes.RHODES_AWAKEN.get(),
        MemoryModuleType.HURT_BY,
        MemoryModuleType.ATTACK_TARGET
    );

    public static Brain.Provider<EntityRhodes> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    public static Brain<?> makeBrain(Brain<EntityRhodes> brain) {
        initIdleActivity(brain);
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void initIdleActivity(Brain<EntityRhodes> brain) {
        brain.addActivity(
            Activity.IDLE,
            0,
            ImmutableList.of(
                new StartingBrain(),
                new IdleBrain()
            )
        );

        brain.addActivity(
            Activity.CORE,
            0,
            ImmutableList.of(
                new IdleBrain()
            )
        );

        brain.addActivity(Activity.FIGHT, 0, ImmutableList.of(
            new ShootLaser(),
            new ShootFlame(),
            new ShootRocket()
        ));
        brain.addActivity(Activity.RAID, 0, ImmutableList.of(new RaidTeam()));
        brain.addActivity(Activities.SHOOT_AND_ROTATE.get(), 0, ImmutableList.of(new ShootAndRotate()));
    }
}
