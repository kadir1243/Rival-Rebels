package io.github.kadir1243.rivalrebels.client.renderentity;

import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodesTorso;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

@OnlyIn(Dist.CLIENT)
public class RenderRhodesTorso extends RhodesPartRenderer<EntityRhodesTorso> {
    public RenderRhodesTorso(EntityRendererProvider.Context context) {
        super(context, ObjModels.TORSO_MODEL);
    }

    @Override
    public void renderParts(State entity, PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight) {
        poseStack.translate(0, 0.7F, 0);
        super.renderParts(entity, poseStack, nodeCollector, packedLight);
    }
}
