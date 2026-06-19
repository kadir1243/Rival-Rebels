package io.github.kadir1243.rivalrebels.client.tileentityrender;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.tileentity.SigmaObjectiveBlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@OnlyIn(Dist.CLIENT)
public class SigmaObjectiveBlockEntityRenderer extends ObjectiveBlockEntityRenderer<SigmaObjectiveBlockEntity> {
    public SigmaObjectiveBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
	}

    @Override
    public ResourceLocation getTexture() {
        return RRIdentifiers.etsigmaobj;
    }
}
