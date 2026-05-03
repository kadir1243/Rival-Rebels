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
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.lighting.LightEngine;

@OnlyIn(Dist.CLIENT)
public class RenderBullet extends EntityRenderer<Entity, EntityRenderState> {
    private final String path;

	public RenderBullet(EntityRendererProvider.Context manager, String path) {
        super(manager);
		this.path = path;
	}

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    @Override
    public void submit(EntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        if (renderState.ageInTicks > 1) {
			poseStack.pushPose();
			poseStack.scale(0.5F, 0.5F, 0.5F);
            float var7 = 1.0F;
            float var8 = 0.5F;
            float var9 = 0.25F;
            poseStack.mulPose(cameraRenderState.orientation);
            nodeCollector.submitCustomGeometry(poseStack, RenderTypes.entitySolid(getTextureLocation()), (pose, consumer) -> {
                consumer.addVertex(pose, (0.0F - var8), (0.0F - var9), 0).setColor(CommonColors.WHITE).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(renderState.lightCoords).setNormal(poseStack.last(), 0, 1, 0);
                consumer.addVertex(pose, (var7 - var8), (0.0F - var9), 0).setColor(CommonColors.WHITE).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(renderState.lightCoords).setNormal(poseStack.last(), 0, 1, 0);
                consumer.addVertex(pose, (var7 - var8), (var7 - var9), 0).setColor(CommonColors.WHITE).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(renderState.lightCoords).setNormal(poseStack.last(), 0, 1, 0);
                consumer.addVertex(pose, (0.0F - var8), (var7 - var9), 0).setColor(CommonColors.WHITE).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(renderState.lightCoords).setNormal(poseStack.last(), 0, 1, 0);
            });
			poseStack.popPose();
		}
	}

    public Identifier getTextureLocation() {
        if (path.equals("flame")) return RRIdentifiers.etflame;
        if (path.equals("fire")) return RRIdentifiers.etfire;
        return null;
    }

    @Override
    public boolean shouldRender(Entity entity, Frustum camera, double camX, double camY, double camZ) {
        return entity.shouldRender(camX, camY, camZ);
    }

    @Override
    protected int getBlockLightLevel(Entity entity, BlockPos pos) {
        return LightEngine.MAX_LEVEL;
    }
}
