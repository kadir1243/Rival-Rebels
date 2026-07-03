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
import io.github.kadir1243.rivalrebels.client.model.ModelNuclearBomb;
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRRenderTypes;
import io.github.kadir1243.rivalrebels.common.entity.EntityBomb;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.util.ARGB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

@OnlyIn(Dist.CLIENT)
public class RenderBomb extends EntityRenderer<EntityBomb, RenderBomb.State> {
    private final QuadCollection model;
    public RenderBomb(EntityRendererProvider.Context context) {
        super(context);
        model = Minecraft.getInstance().getModelManager().getStandaloneModel(ObjModels.BLAST_SPHERE_MODEL);
	}

    @Override
    public void submit(State renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 90.0f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot - 90.0f));
        if (renderState.deltaMovement.x() == 0 && renderState.deltaMovement.z() == 0) {
            poseStack.pushPose();
            poseStack.scale(renderState.ageInTicks * 0.2f, renderState.ageInTicks * 0.2f, renderState.ageInTicks * 0.2f);
            if (renderState.deltaMovement.y() == 1) {
                ObjModels.submit(nodeCollector, RRRenderTypes.MODEL_BLAST_SPHERE, model, poseStack, ARGB.colorFromFloat(0.75f, 0.25f, 0.25f, 1.0f), renderState.lightCoords, OverlayTexture.NO_OVERLAY);
            } else if (renderState.deltaMovement.y() == 0) {
                ObjModels.submit(nodeCollector, RRRenderTypes.MODEL_BLAST_SPHERE, model, poseStack, ARGB.colorFromFloat(0.75f, 0.8f, 0.8f, 1f), renderState.lightCoords, OverlayTexture.NO_OVERLAY);
            }
            poseStack.popPose();
        } else {
            poseStack.scale(0.25f, 0.5f, 0.25f);
            ModelNuclearBomb.renderModel(poseStack, nodeCollector, RRIdentifiers.etnuke, renderState.lightCoords, true);
        }
        poseStack.popPose();
    }

    @Override
    public boolean shouldRender(EntityBomb livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(EntityBomb entity, State reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.xRot = entity.getXRot(partialTick);
        reusedState.yRot = entity.getYRot(partialTick);
        reusedState.deltaMovement = entity.getDeltaMovement();
    }

    public static class State extends EntityRenderState {
        public float xRot;
        public float yRot;
        public Vec3 deltaMovement;
    }
}
