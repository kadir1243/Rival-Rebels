package assets.rivalrebels.client.model;

import assets.rivalrebels.RRIdentifiers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class RRModelLoadingPlugin implements ModelLoadingPlugin {
    public static final ResourceLocation ROCKET_WITH_FINS = RRIdentifiers.create("rocket_with_fins");
    public static final ResourceLocation ROCKET_WITHOUT_FINS = RRIdentifiers.create("rocket_without_fins");
    public static final ResourceLocation JUMP_BLOCK = RRIdentifiers.create("jump_block");

    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        pluginContext.addModels(ROCKET_WITH_FINS, ROCKET_WITHOUT_FINS, JUMP_BLOCK);
        pluginContext.resolveModel().register(context -> {
            ResourceLocation id = context.id();
            if (id.equals(ROCKET_WITH_FINS)) {
                return ModelRocket.INSTANCE_WITH_FIN;
            }
            if (id.equals(ROCKET_WITHOUT_FINS)) {
                return ModelRocket.INSTANCE_WITHOUT_FIN;
            }
            if (id.equals(JUMP_BLOCK)) {
                return ModelJump.INSTANCE;
            }
            return null;
        });
    }
}
