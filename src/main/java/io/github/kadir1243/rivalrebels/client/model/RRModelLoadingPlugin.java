package io.github.kadir1243.rivalrebels.client.model;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ModelEvent;

@OnlyIn(Dist.CLIENT)
public class RRModelLoadingPlugin {
    public static void init(IEventBus bus) {
        bus.addListener(RRModelLoadingPlugin::onInitializeModelLoader);
    }

    private static void onInitializeModelLoader(ModelEvent.RegisterAdditional event) {
    }
}
