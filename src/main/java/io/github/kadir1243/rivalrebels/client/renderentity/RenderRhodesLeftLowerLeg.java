package io.github.kadir1243.rivalrebels.client.renderentity;

import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodesLeftLowerLeg;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

@OnlyIn(Dist.CLIENT)
public class RenderRhodesLeftLowerLeg extends RhodesPartRenderer<EntityRhodesLeftLowerLeg> {
    public RenderRhodesLeftLowerLeg(EntityRendererProvider.Context context) {
        super(context, ObjModels.THIGH_MODEL);
    }

    @Override
    public void renderParts(State entity, PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight) {
        poseStack.translate(3, 4f, 0);
        poseStack.scale(-1, 1, 1);
        super.renderParts(entity, poseStack, nodeCollector, packedLight);
    }
}
