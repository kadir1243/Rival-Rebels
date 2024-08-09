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
import assets.rivalrebels.common.entity.EntityPlasmoid;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.lighting.LightEngine;

@Environment(EnvType.CLIENT)
public class RenderPlasmoid extends EntityRenderer<EntityPlasmoid> {
    public RenderPlasmoid(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityPlasmoid entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		matrices.pushPose();
        matrices.mulPose(Axis.YP.rotationDegrees(entity.getYRot() - 90.0f));
		matrices.mulPose(Axis.ZP.rotationDegrees(entity.getXRot() - 90.0f));
		matrices.scale(0.4f, 2.5f, 0.4f);
		matrices.pushPose();

        for (int i = 0; i < 5; i++) {
            matrices.mulPose(Axis.YP.rotationDegrees(entity.rotation));
            ModelBlastSphere.renderModel(matrices, vertexConsumers, 0.4F + 0.2F * i, 0.65f, 0.55f, 0.95f, 0.9f);
        }
		matrices.popPose();
		matrices.popPose();
	}

    @Override
    public ResourceLocation getTextureLocation(EntityPlasmoid entity) {
        return null;
    }

    @Override
    public boolean shouldRender(EntityPlasmoid livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    protected int getBlockLightLevel(EntityPlasmoid entity, BlockPos pos) {
        return LightEngine.MAX_LEVEL;
    }
}
