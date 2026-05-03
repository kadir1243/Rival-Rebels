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

import io.github.kadir1243.rivalrebels.common.entity.EntityLaserBurst;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.lighting.LightEngine;

@OnlyIn(Dist.CLIENT)
public class RenderLaserBurst extends EntityRenderer<EntityLaserBurst, RenderLaserBurst.State> {
	private static final float red = 1F;

    public RenderLaserBurst(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void submit(State renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        float radius = 0.12F;
        int distance = 4;
        poseStack.pushPose();

        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees(-renderState.xRot));

        for (float o = 0; o <= radius; o += radius / 8) {
            float color = 1f - (o * 8.333f);
            if (color < 0) color = 0;
            float finalO = o;
            float finalColor = color;
            nodeCollector.submitCustomGeometry(poseStack, RenderTypes.lightning(), (pose, consumer) -> {
                consumer.addVertex(pose, 0 + finalO, 0 - finalO, 0).setColor(red, finalColor, finalColor, 1);
                consumer.addVertex(pose, 0 + finalO, 0 + finalO, 0).setColor(red, finalColor, finalColor, 1);
                consumer.addVertex(pose, 0 + finalO, 0 + finalO, distance).setColor(red, finalColor, finalColor, 1);
                consumer.addVertex(pose, 0 + finalO, 0 - finalO, distance).setColor(red, finalColor, finalColor, 1);

                consumer.addVertex(pose, 0 - finalO, 0 - finalO, 0).setColor(red, finalColor, finalColor, 1);
                consumer.addVertex(pose, 0 - finalO, 0 - finalO, distance).setColor(red, finalColor, finalColor, 1);
                consumer.addVertex(pose, 0 - finalO, 0 + finalO, 0).setColor(red, finalColor, finalColor, 1);
                consumer.addVertex(pose, 0 - finalO, 0 + finalO, distance).setColor(red, finalColor, finalColor, 1);
            });
        }
        poseStack.popPose();
    }

    @Override
    public boolean shouldRender(EntityLaserBurst livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    protected int getBlockLightLevel(EntityLaserBurst entity, BlockPos pos) {
        return LightEngine.MAX_LEVEL;
    }


    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(EntityLaserBurst p_entity, State reusedState, float partialTick) {
        super.extractRenderState(p_entity, reusedState, partialTick);
        reusedState.xRot = p_entity.getXRot(partialTick);
        reusedState.yRot = p_entity.getYRot(partialTick);
    }

    public static class State extends EntityRenderState {
        public float xRot;
        public float yRot;
    }
}
