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
package io.github.kadir1243.rivalrebels.client.renderentity;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.model.ModelRocket;
import io.github.kadir1243.rivalrebels.common.entity.EntityRocket;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.lighting.LightEngine;

@OnlyIn(Dist.CLIENT)
public class RenderRocket extends EntityRenderer<EntityRocket> {
    public RenderRocket(EntityRendererProvider.Context manager) {
        super(manager);
    }

    @Override
    public void render(EntityRocket entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		matrices.pushPose();
		matrices.mulPose(Axis.YP.rotationDegrees(entity.getYRot() - 90.0f));
		matrices.mulPose(Axis.ZP.rotationDegrees(entity.getXRot() - 90.0f));
		matrices.mulPose(Axis.YP.rotationDegrees(entity.rotation));
		ModelRocket.render(matrices, vertexConsumers, RRIdentifiers.etrocket, entity.fins, light, OverlayTexture.NO_OVERLAY);
		matrices.popPose();
	}

	@Override
    public ResourceLocation getTextureLocation(EntityRocket entity)
	{
		return RRIdentifiers.etrocket;
	}

    @Override
    public boolean shouldRender(EntityRocket livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    protected int getBlockLightLevel(EntityRocket entity, BlockPos pos) {
        return LightEngine.MAX_LEVEL;
    }
}
