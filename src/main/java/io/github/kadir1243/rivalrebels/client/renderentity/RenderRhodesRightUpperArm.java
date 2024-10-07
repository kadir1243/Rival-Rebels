package io.github.kadir1243.rivalrebels.client.renderentity;

import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodesRightUpperArm;
import com.mojang.blaze3d.vertex.PoseStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;

@OnlyIn(Dist.CLIENT)
public class RenderRhodesRightUpperArm extends RhodesPartRenderer<EntityRhodesRightUpperArm> {
    public RenderRhodesRightUpperArm(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void renderParts(EntityRhodesRightUpperArm entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        ObjModels.renderSolid(ObjModels.upperarm, getTextureLocation(entity), poseStack, bufferSource, entity.getColorRGBA(), packedLight, OverlayTexture.NO_OVERLAY);
    }
}
