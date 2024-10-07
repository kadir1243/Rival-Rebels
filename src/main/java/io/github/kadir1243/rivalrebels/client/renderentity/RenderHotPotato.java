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

import io.github.kadir1243.rivalrebels.client.model.ModelTsarBomba;
import io.github.kadir1243.rivalrebels.common.entity.EntityHotPotato;
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
public class RenderHotPotato extends EntityRenderer<EntityHotPotato> {
	public RenderHotPotato(EntityRendererProvider.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityHotPotato entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        matrices.pushPose();
		matrices.mulPose(Axis.YP.rotationDegrees(entity.getYRot() - 90.0f));
		//matrices.mulPose(Axis.XP.rotationDegrees(90));
		matrices.mulPose(Axis.ZP.rotationDegrees(entity.getXRot() - 90.0f));
		matrices.scale(2.0f, 1.0f, 2.0f);
		ModelTsarBomba.render(matrices, vertexConsumers, light, OverlayTexture.NO_OVERLAY);
		matrices.popPose();
	}

    @Override
    public ResourceLocation getTextureLocation(EntityHotPotato entity) {
        return null;
    }

    @Override
    public boolean shouldRender(EntityHotPotato livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }
}
