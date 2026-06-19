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

import io.github.kadir1243.rivalrebels.client.renderhelper.RenderTypes;
import io.github.kadir1243.rivalrebels.common.entity.EntityLaserLink;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.lighting.LightEngine;

@OnlyIn(Dist.CLIENT)
public class RenderLaserLink extends EntityRenderer<EntityLaserLink, RenderLaserLink.State> {
    private static final int COLOR = ARGB.colorFromFloat(1F, 0.5F, 0.1F, 0.1F);

    public RenderLaserLink(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(State renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        float distance = (float) (renderState.deltaMovement.x() * 100F);
        if (distance > 0) {
            float radius = 0.7F;
            VertexConsumer buffer = bufferSource.getBuffer(RenderTypes.LASER_LINK_ENTITY);

            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(-renderState.yRot));
            poseStack.mulPose(Axis.XP.rotationDegrees(renderState.xRot));

            for (float o = 0; o <= radius; o += radius / 16) {
                buffer.addVertex(poseStack.last(), 0 + o, 0 - o, 0).setColor(COLOR);
                buffer.addVertex(poseStack.last(), 0 + o, 0 + o, 0).setColor(COLOR);
                buffer.addVertex(poseStack.last(), 0 + o, 0 + o, 0 + distance).setColor(COLOR);
                buffer.addVertex(poseStack.last(), 0 + o, 0 - o, 0 + distance).setColor(COLOR);

                buffer.addVertex(poseStack.last(), 0 - o, 0 - o, 0).setColor(COLOR);
                buffer.addVertex(poseStack.last(), 0 - o, 0 - o, 0 + distance).setColor(COLOR);
                buffer.addVertex(poseStack.last(), 0 - o, 0 + o, 0).setColor(COLOR);
                buffer.addVertex(poseStack.last(), 0 - o, 0 + o, 0 + distance).setColor(COLOR);
            }

            poseStack.popPose();
        }
    }

    @Override
    public boolean shouldRender(EntityLaserLink livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    protected int getBlockLightLevel(EntityLaserLink entity, BlockPos pos) {
        return LightEngine.MAX_LEVEL;
    }


    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(EntityLaserLink p_entity, State reusedState, float partialTick) {
        super.extractRenderState(p_entity, reusedState, partialTick);
        reusedState.xRot = p_entity.getXRot(partialTick);
        reusedState.yRot = p_entity.getYRot(partialTick);
        reusedState.deltaMovement = p_entity.getDeltaMovement();
    }

    public static class State extends EntityRenderState {
        public float xRot;
        public float yRot;
        public Vec3 deltaMovement;
    }
}
