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
import io.github.kadir1243.rivalrebels.common.entity.EntityGoo;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;

@OnlyIn(Dist.CLIENT)
public class RenderGoo extends EntityRenderer<EntityGoo> {
    public RenderGoo(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityGoo entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        if (entity.tickCount < 2) return;
        matrices.pushPose();
        matrices.scale(0.25F, 0.25F, 0.25F);
        float var7 = 1.0F;
        float var8 = 0.5F;
        float var9 = 0.25F;
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderType.entitySolid(RRIdentifiers.etgoo));
        matrices.mulPose(this.entityRenderDispatcher.cameraOrientation());
        buffer.addVertex(matrices.last(), (0.0F - var8), (0.0F - var9), 0).setColor(CommonColors.WHITE).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(matrices.last(), 0, 1, 0);
        buffer.addVertex(matrices.last(), (var7 - var8), (0.0F - var9), 0).setColor(CommonColors.WHITE).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(matrices.last(), 0, 1, 0);
        buffer.addVertex(matrices.last(), (var7 - var8), (var7 - var9), 0).setColor(CommonColors.WHITE).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(matrices.last(), 0, 1, 0);
        buffer.addVertex(matrices.last(), (0.0F - var8), (var7 - var9), 0).setColor(CommonColors.WHITE).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(matrices.last(), 0, 1, 0);
        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EntityGoo entity) {
        return RRIdentifiers.etgoo;
    }
}
