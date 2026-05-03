package io.github.kadir1243.rivalrebels.client.renderentity;

import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodesRightUpperLeg;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

@OnlyIn(Dist.CLIENT)
public class RenderRhodesRightUpperLeg extends RhodesPartRenderer<EntityRhodesRightUpperLeg> {
    public RenderRhodesRightUpperLeg(EntityRendererProvider.Context context) {
        super(context, ObjModels.THIGH_MODEL);
    }

    @Override
    public void renderParts(State entity, PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight) {
        poseStack.translate(-3, 5f, 0);
        super.renderParts(entity, poseStack, nodeCollector, packedLight);
    }
}
