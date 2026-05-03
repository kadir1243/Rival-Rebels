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
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.lighting.LightEngine;

@OnlyIn(Dist.CLIENT)
public class RenderRocket extends EntityRenderer<EntityRocket, RenderRocket.State> {
    public RenderRocket(EntityRendererProvider.Context manager) {
        super(manager);
    }

    @Override
    public void submit(State renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 90.0f));
		poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot - 90.0f));
		poseStack.mulPose(Axis.YP.rotationDegrees(renderState.rotation));
		ModelRocket.render(poseStack, nodeCollector, RRIdentifiers.etrocket, renderState.fins, renderState.lightCoords, OverlayTexture.NO_OVERLAY);
		poseStack.popPose();
	}

    @Override
    public boolean shouldRender(EntityRocket livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    protected int getBlockLightLevel(EntityRocket entity, BlockPos pos) {
        return LightEngine.MAX_LEVEL;
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(EntityRocket p_entity, State reusedState, float partialTick) {
        super.extractRenderState(p_entity, reusedState, partialTick);
        reusedState.xRot = p_entity.getXRot(partialTick);
        reusedState.yRot = p_entity.getYRot(partialTick);
        reusedState.rotation = p_entity.rotation;
        reusedState.fins = p_entity.fins;
    }

    public static class State extends EntityRenderState {
        public float xRot;
        public float yRot;
        public int rotation;
        public boolean fins;
    }
}
