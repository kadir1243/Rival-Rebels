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

import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRRenderTypes;
import io.github.kadir1243.rivalrebels.common.entity.EntityPlasmoid;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.util.ARGB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.lighting.LightEngine;

@OnlyIn(Dist.CLIENT)
public class RenderPlasmoid extends EntityRenderer<EntityPlasmoid, RenderPlasmoid.State> {
    private final QuadCollection model;

    public RenderPlasmoid(EntityRendererProvider.Context context) {
        super(context);

        model = Minecraft.getInstance().getModelManager().getStandaloneModel(ObjModels.BLAST_SPHERE_MODEL);
    }

    @Override
    public void submit(State renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
		poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 90.0f));
		poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot - 90.0f));
		poseStack.scale(0.4f, 2.5f, 0.4f);
		poseStack.pushPose();

        for (int i = 0; i < 5; i++) {
            poseStack.mulPose(Axis.YP.rotationDegrees(renderState.rotation));
            float scale = 0.4F + 0.2F * i;
            poseStack.pushPose();
            poseStack.scale(scale, scale, scale);
            ObjModels.submit(nodeCollector, RRRenderTypes.MODEL_BLAST_SPHERE, model, poseStack, ARGB.colorFromFloat(0.9f, 0.65f, 0.55f, 0.95f), renderState.lightCoords, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
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
    public void extractRenderState(EntityPlasmoid entity, State reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.xRot = entity.getXRot(partialTick);
        reusedState.yRot = entity.getYRot(partialTick);
        reusedState.rotation = entity.rotation;
    }

    public static class State extends EntityRenderState {
        public float xRot;
        public float yRot;
        public int rotation;
    }
}
