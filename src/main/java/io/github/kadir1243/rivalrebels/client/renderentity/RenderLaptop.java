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
import io.github.kadir1243.rivalrebels.client.model.ModelLaptop;
import io.github.kadir1243.rivalrebels.common.entity.EntityLaptop;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

@OnlyIn(Dist.CLIENT)
public class RenderLaptop extends EntityRenderer<EntityLaptop> {
    public RenderLaptop(EntityRendererProvider.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityLaptop entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        matrices.pushPose();
		matrices.mulPose(Axis.YP.rotationDegrees(180 - entity.getYRot()));
		ModelLaptop.renderModel(vertexConsumers, matrices, (float) -entity.slide, light, OverlayTexture.NO_OVERLAY);
		ModelLaptop.renderScreen(vertexConsumers, RRIdentifiers.etubuntu, matrices, (float) -entity.slide, light, OverlayTexture.NO_OVERLAY);
		matrices.popPose();
	}

    @Override
    public ResourceLocation getTextureLocation(EntityLaptop entity) {
        return null;
    }
}
