package io.github.kadir1243.rivalrebels.client.renderentity;

import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.entity.EntityRhodesRightUpperArm;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

@OnlyIn(Dist.CLIENT)
public class RenderRhodesRightUpperArm extends RhodesPartRenderer<EntityRhodesRightUpperArm> {
    public RenderRhodesRightUpperArm(EntityRendererProvider.Context context) {
        super(context, ObjModels.UPPER_ARM_MODEL);
    }
}
