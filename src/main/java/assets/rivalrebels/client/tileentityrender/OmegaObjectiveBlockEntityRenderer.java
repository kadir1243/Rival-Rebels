package assets.rivalrebels.client.tileentityrender;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.common.tileentity.OmegaObjectiveBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class OmegaObjectiveBlockEntityRenderer extends ObjectiveBlockEntityRenderer<OmegaObjectiveBlockEntity> {
    public OmegaObjectiveBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
	}

    @Override
    public ResourceLocation getTexture() {
        return RRIdentifiers.etomegaobj;
    }
}
