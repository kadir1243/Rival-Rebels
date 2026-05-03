package io.github.kadir1243.rivalrebels.client.renderentity;

import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodesRightLowerArm;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;

@OnlyIn(Dist.CLIENT)
public class RenderRhodesRightLowerArm extends RhodesPartRenderer<EntityRhodesRightLowerArm> {
    private final QuadCollection flameThrower;

    public RenderRhodesRightLowerArm(EntityRendererProvider.Context context) {
        super(context, ObjModels.LOWER_ARM_MODEL);
        ModelManager modelManager = Minecraft.getInstance().getModelManager();
        flameThrower = modelManager.getStandaloneModel(ObjModels.RHODES_FLAMETHROWER_MODEL);
    }

    @Override
    public void renderParts(State entity, PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight) {
        poseStack.translate(0, 4f, 0);
        poseStack.scale(-1, 1, 1);
        super.renderParts(entity, poseStack, nodeCollector, packedLight);
        nodeCollector.submitCustomGeometry(poseStack, RenderTypes.entitySolid(getTextureLocation(entity)), (pose, consumer) -> {
            ObjModels.render(flameThrower, consumer, pose, entity.color, packedLight, OverlayTexture.NO_OVERLAY);
        });
    }
}
