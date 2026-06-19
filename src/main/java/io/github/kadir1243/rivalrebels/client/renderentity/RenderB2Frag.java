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
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.entity.EntityB2Frag;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.util.CommonColors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;

@OnlyIn(Dist.CLIENT)
public class RenderB2Frag extends EntityRenderer<EntityB2Frag, RenderB2Frag.State> {
    private final QuadCollection b2FragSide1Model;
    private final QuadCollection b2FragSide2Model;

    public RenderB2Frag(EntityRendererProvider.Context context) {
        super(context);
        ModelManager modelManager = Minecraft.getInstance().getModelManager();
        b2FragSide1Model = modelManager.getStandaloneModel(ObjModels.B2_FRAG_SIDE_1_MODEL);
        b2FragSide2Model = modelManager.getStandaloneModel(ObjModels.B2_FRAG_SIDE_2_MODEL);
    }

    @Override
    public void submit(State renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot));
		poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot));
        poseStack.scale(3, 3, 3);

        nodeCollector.submitCustomGeometry(poseStack, RenderTypes.entitySolid(RRIdentifiers.etb2spirit), (pose, consumer) -> {
            if (renderState.type == 0)
                ObjModels.render(b2FragSide1Model, consumer, pose, CommonColors.WHITE, LightCoordsUtil.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
            else if (renderState.type == 1)
                ObjModels.render(b2FragSide2Model, consumer, pose, CommonColors.WHITE, LightCoordsUtil.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        });
		poseStack.popPose();
	}

    @Override
    protected boolean affectedByCulling(EntityB2Frag p_365169_) {
        return false;
    }

    @Override
    public boolean shouldRender(EntityB2Frag livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(EntityB2Frag p_entity, State reusedState, float partialTick) {
        super.extractRenderState(p_entity, reusedState, partialTick);
        reusedState.xRot = p_entity.getXRot(partialTick);
        reusedState.yRot = p_entity.getYRot(partialTick);
        reusedState.type = p_entity.type;
    }

    public static class State extends EntityRenderState {
        public float xRot;
        public float yRot;
        public int type;
    }}
