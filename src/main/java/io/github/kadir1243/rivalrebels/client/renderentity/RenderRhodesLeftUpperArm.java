package io.github.kadir1243.rivalrebels.client.renderentity;

import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodesLeftUpperArm;
import com.mojang.blaze3d.vertex.PoseStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;

@OnlyIn(Dist.CLIENT)
public class RenderRhodesLeftUpperArm extends RhodesPartRenderer<EntityRhodesLeftUpperArm> {
    public RenderRhodesLeftUpperArm(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void renderParts(EntityRhodesLeftUpperArm entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(-1, 1, 1);
        ObjModels.renderSolid(ObjModels.upperarm, getTextureLocation(entity), poseStack, bufferSource, entity.getColorRGBA(), packedLight, OverlayTexture.NO_OVERLAY);
    }
}
