package assets.rivalrebels.client.tileentityrender;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.common.tileentity.SigmaObjectiveBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class SigmaObjectiveBlockEntityRenderer extends ObjectiveBlockEntityRenderer<SigmaObjectiveBlockEntity> {
    public SigmaObjectiveBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
	}

    @Override
    public ResourceLocation getTexture() {
        return RRIdentifiers.etsigmaobj;
    }
}
