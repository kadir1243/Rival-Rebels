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

import io.github.kadir1243.rivalrebels.client.model.ModelBlastSphere;
import io.github.kadir1243.rivalrebels.common.entity.EntityPlasmoid;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.lighting.LightEngine;

@OnlyIn(Dist.CLIENT)
public class RenderPlasmoid extends EntityRenderer<EntityPlasmoid, RenderPlasmoid.State> {
    public RenderPlasmoid(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(State renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 90.0f));
		poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot - 90.0f));
		poseStack.scale(0.4f, 2.5f, 0.4f);
		poseStack.pushPose();

        for (int i = 0; i < 5; i++) {
            poseStack.mulPose(Axis.YP.rotationDegrees(renderState.rotation));
            ModelBlastSphere.renderModel(poseStack, bufferSource, 0.4F + 0.2F * i, 0.65f, 0.55f, 0.95f, 0.9f);
        }
		poseStack.popPose();
		poseStack.popPose();
	}

    @Override
    public boolean shouldRender(EntityPlasmoid livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    protected int getBlockLightLevel(EntityPlasmoid entity, BlockPos pos) {
        return LightEngine.MAX_LEVEL;
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(EntityPlasmoid p_entity, State reusedState, float partialTick) {
        super.extractRenderState(p_entity, reusedState, partialTick);
        reusedState.xRot = p_entity.getXRot(partialTick);
        reusedState.yRot = p_entity.getYRot(partialTick);
        reusedState.rotation = p_entity.rotation;
    }

    public static class State extends EntityRenderState {
        public float xRot;
        public float yRot;
        public int rotation;
    }
}
