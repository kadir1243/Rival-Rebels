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
import io.github.kadir1243.rivalrebels.common.entity.EntityGasGrenade;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.CommonColors;

@OnlyIn(Dist.CLIENT)
public class RenderGasGrenade extends EntityRenderer<EntityGasGrenade, RenderGasGrenade.State> {
    private static final RenderType RENDER_LAYER = RenderTypes.entityCutout(RRIdentifiers.etgasgrenade);

    public RenderGasGrenade(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void submit(State renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 90));
		poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot));
        byte var11 = 0;
		float var12 = 0.0F;
		float var13 = 0.5F;
		float var14 = (0 + var11 * 10) / 32.0F;
		float var15 = (5 + var11 * 10) / 32.0F;
		float var16 = 0.0F;
		float var17 = 0.15625F;
		float var18 = (5 + var11 * 10) / 32.0F;
		float var19 = (10 + var11 * 10) / 32.0F;
		float var20 = 0.05625F;
        int overlay = OverlayTexture.NO_OVERLAY;
        int packedLight = renderState.lightCoords;

        poseStack.mulPose(Axis.XP.rotationDegrees(45.0F));
		poseStack.scale(var20, var20, var20);
		poseStack.translate(-4.0F, 0.0F, 0.0F);
        int defaultColor = CommonColors.WHITE;
        nodeCollector.submitCustomGeometry(poseStack, RENDER_LAYER, (pose, consumer) -> {
            consumer.addVertex(pose, -7, -2, -2).setColor(defaultColor).setUv(var16, var18).setOverlay(overlay).setLight(packedLight).setNormal(poseStack.last(), var20, 0F, 0F);
            consumer.addVertex(pose, -7, -2, 2).setColor(defaultColor).setUv(var17, var18).setOverlay(overlay).setLight(packedLight).setNormal(poseStack.last(), var20, 0.0F, 0.0F);
            consumer.addVertex(pose, -7, 2, 2).setColor(defaultColor).setUv(var17, var19).setOverlay(overlay).setLight(packedLight).setNormal(poseStack.last(), var20, 0.0F, 0.0F);
            consumer.addVertex(pose, -7, 2, -2).setColor(defaultColor).setUv(var16, var19).setOverlay(overlay).setLight(packedLight).setNormal(poseStack.last(), var20, 0.0F, 0.0F);

            consumer.addVertex(pose, -7, 2, -2).setColor(defaultColor).setUv(var16, var18).setOverlay(overlay).setLight(packedLight).setNormal(poseStack.last(), -var20, 0.0F, 0.0F);
            consumer.addVertex(pose, -7, 2, 2).setColor(defaultColor).setUv(var17, var18).setOverlay(overlay).setLight(packedLight).setNormal(poseStack.last(), -var20, 0.0F, 0.0F);
            consumer.addVertex(pose, -7, -2, 2).setColor(defaultColor).setUv(var17, var19).setOverlay(overlay).setLight(packedLight).setNormal(poseStack.last(), -var20, 0.0F, 0.0F);
            consumer.addVertex(pose, -7, -2, -2).setColor(defaultColor).setUv(var16, var19).setOverlay(overlay).setLight(packedLight).setNormal(poseStack.last(), -var20, 0.0F, 0.0F);
        });

		for (int var23 = 0; var23 < 4; ++var23) {
			poseStack.mulPose(Axis.XP.rotationDegrees(90));
            nodeCollector.submitCustomGeometry(poseStack, RENDER_LAYER, (pose, consumer) -> {
                consumer.addVertex(pose, -8, -2, 0).setColor(defaultColor).setUv(var12, var14).setOverlay(overlay).setLight(packedLight).setNormal(poseStack.last(), 0.0F, 0.0F, var20);
                consumer.addVertex(pose,  8, -2, 0).setColor(defaultColor).setUv(var13, var14).setOverlay(overlay).setLight(packedLight).setNormal(poseStack.last(), 0.0F, 0.0F, var20);
                consumer.addVertex(pose,  8,  2, 0).setColor(defaultColor).setUv(var13, var15).setOverlay(overlay).setLight(packedLight).setNormal(poseStack.last(), 0.0F, 0.0F, var20);
                consumer.addVertex(pose, -8,  2, 0).setColor(defaultColor).setUv(var12, var15).setOverlay(overlay).setLight(packedLight).setNormal(poseStack.last(), 0.0F, 0.0F, var20);
            });
		}

		poseStack.popPose();
	}


    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(EntityGasGrenade p_entity, State reusedState, float partialTick) {
        super.extractRenderState(p_entity, reusedState, partialTick);
        reusedState.xRot = p_entity.getXRot(partialTick);
        reusedState.yRot = p_entity.getYRot(partialTick);
    }

    public static class State extends EntityRenderState {
        public float xRot;
        public float yRot;
    }
}
