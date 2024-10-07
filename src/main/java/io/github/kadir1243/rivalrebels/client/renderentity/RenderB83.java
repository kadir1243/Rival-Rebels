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

import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.entity.EntityB83;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

@OnlyIn(Dist.CLIENT)
public class RenderB83 extends EntityRenderer<EntityB83> {
    public RenderB83(EntityRendererProvider.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityB83 entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        matrices.pushPose();
        matrices.scale(RRConfig.CLIENT.getNukeScale(),RRConfig.CLIENT.getNukeScale(),RRConfig.CLIENT.getNukeScale());
        matrices.mulPose(Axis.YP.rotationDegrees(yaw - 90));
        matrices.mulPose(Axis.ZP.rotationDegrees(entity.getXRot() - 180));
        ObjModels.renderSolid(ObjModels.b83, RRIdentifiers.etb83, matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
        matrices.popPose();
    }

	@Override
    public ResourceLocation getTextureLocation(EntityB83 entity)
	{
		return RRIdentifiers.etb83;
	}

    @Override
    public boolean shouldRender(EntityB83 livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }
}
