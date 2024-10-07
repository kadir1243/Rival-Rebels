package io.github.kadir1243.rivalrebels.common.entity.brain;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.schedule.Activity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Activities {
    private static final DeferredRegister<Activity> ACTIVITIES = DeferredRegister.create(Registries.ACTIVITY, RRIdentifiers.MODID);
    public static final DeferredHolder<Activity, Activity> SHOOT_AND_ROTATE = ACTIVITIES.register("shoot_and_rotate", resourceLocation -> new Activity(resourceLocation.toString()));

    public static void init(IEventBus bus) {
        ACTIVITIES.register(bus);
    }

}
