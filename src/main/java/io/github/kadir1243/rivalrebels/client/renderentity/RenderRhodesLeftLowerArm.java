package io.github.kadir1243.rivalrebels.client.renderentity;

import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodesLeftLowerArm;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.QuadCollection;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;

@OnlyIn(Dist.CLIENT)
public class RenderRhodesLeftLowerArm extends RhodesPartRenderer<EntityRhodesLeftLowerArm> {
    private final QuadCollection rocketLauncherModel;

    public RenderRhodesLeftLowerArm(EntityRendererProvider.Context context) {
        super(context, ObjModels.LOWER_ARM_MODEL);
        ModelManager modelManager = Minecraft.getInstance().getModelManager();
        rocketLauncherModel = modelManager.getStandaloneModel(ObjModels.RHODES_ROCKET_LAUNCHER_MODEL);
    }

    @Override
    public void renderParts(State entity, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.translate(0, 4f, 0);
        poseStack.scale(-1, 1, 1);
        super.renderParts(entity, poseStack, bufferSource, packedLight);
        ObjModels.render(rocketLauncherModel, bufferSource.getBuffer(RenderType.entitySolid(getTextureLocation(entity))), poseStack, entity.color, packedLight, OverlayTexture.NO_OVERLAY);
    }
}
