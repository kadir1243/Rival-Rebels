package io.github.kadir1243.rivalrebels.client.tileentityrender;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.tileentity.OmegaObjectiveBlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.Identifier;

@OnlyIn(Dist.CLIENT)
public class OmegaObjectiveBlockEntityRenderer extends ObjectiveBlockEntityRenderer<OmegaObjectiveBlockEntity> {
    public OmegaObjectiveBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
	}

    @Override
    public Identifier getTexture() {
        return RRIdentifiers.etomegaobj;
    }
}
