package assets.rivalrebels.common.entity.brain;

import assets.rivalrebels.RRIdentifiers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.schedule.Activity;

public class Activities {
    public static final Activity SHOOT_AND_ROTATE = register("shoot_and_rotate");

    public static void init() {
    }

    private static Activity register(String key) {
        return Registry.register(BuiltInRegistries.ACTIVITY, RRIdentifiers.create(key), new Activity(RRIdentifiers.MODID + '_' + key));
    }
}
