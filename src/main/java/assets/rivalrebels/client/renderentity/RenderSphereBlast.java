/*******************************************************************************
 * Copyright (c) 2012, 2016 Rodol Phito.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Mozilla Public License Version 2.0
 * which accompanies this distribution, and is available at
 * https://www.mozilla.org/en-US/MPL/2.0/
 *
 * Rival Rebels Mod. All code, art, and design by Rodol Phito.
 *
 * http://RivalRebels.com/
 *******************************************************************************/
package assets.rivalrebels.client.renderentity;

import assets.rivalrebels.client.model.ModelBlastSphere;
import assets.rivalrebels.common.entity.EntitySphereBlast;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RenderSphereBlast extends EntityRenderer<EntitySphereBlast> {
	public RenderSphereBlast(EntityRendererProvider.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntitySphereBlast entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        entity.time++;
        matrices.pushPose();
        double elev = ((Mth.sin(entity.time / 40f) + 1.5f) * 10);
        matrices.translate(entity.getX(), entity.getY() + elev, entity.getZ());
        matrices.pushPose();
        matrices.mulPose(Axis.YP.rotationDegrees((float) (elev * 2)));
        matrices.mulPose(Axis.XP.rotationDegrees((float) (elev * 3)));
        ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) elev, 1, 0.25f, 0, 1f);
        matrices.popPose();
        matrices.pushPose();
        matrices.mulPose(Axis.YP.rotationDegrees((float) (elev * -2)));
        matrices.mulPose(Axis.ZP.rotationDegrees((float) (elev * 4)));
        ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.2f), 1, 0.5f, 0, 1f);
        matrices.popPose();
        matrices.pushPose();
        matrices.mulPose(Axis.XP.rotationDegrees((float) (elev * -3)));
        matrices.mulPose(Axis.ZP.rotationDegrees((float) (elev * 2)));
        ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.4f), 1, 0, 0, 1f);
        matrices.popPose();
        matrices.pushPose();
        matrices.mulPose(Axis.YP.rotationDegrees((float) (elev * -1)));
        matrices.mulPose(Axis.ZP.rotationDegrees((float) (elev * 3)));
        ModelBlastSphere.renderModel(matrices, vertexConsumers, (float) (elev - 0.6f), 1, 1, 0, 1);
        matrices.popPose();
        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EntitySphereBlast entity) {
        return null;
    }
}
