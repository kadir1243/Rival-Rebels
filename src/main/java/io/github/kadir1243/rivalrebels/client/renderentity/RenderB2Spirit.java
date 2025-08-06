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

import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.entity.EntityB2Spirit;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.util.CommonColors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;

@OnlyIn(Dist.CLIENT)
public class RenderB2Spirit extends EntityRenderer<EntityB2Spirit, RenderB2Spirit.State> {
    private final QuadCollection b2ForSpiritModel;
    private final QuadCollection shuttleModel;
    private final QuadCollection tupolevModel;

    public RenderB2Spirit(EntityRendererProvider.Context context) {
        super(context);
        ModelManager modelManager = context.getModelManager();
        b2ForSpiritModel = modelManager.getStandaloneModel(ObjModels.B2_FOR_SPIRIT_MODEL);
        shuttleModel = modelManager.getStandaloneModel(ObjModels.SHUTTLE_MODEL);
        tupolevModel = modelManager.getStandaloneModel(ObjModels.TUPOLEV_MODEL);
    }

    @Override
    public void render(State renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot));
		poseStack.mulPose(Axis.XP.rotationDegrees(renderState.xRot));
        if (RRConfig.CLIENT.getBomberType().equals("sh")) {
			poseStack.scale(3, 3, 3);
            ObjModels.render(shuttleModel, bufferSource.getBuffer(RenderType.entitySolid(RRIdentifiers.etb2spirit)), poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
		} else if (RRConfig.CLIENT.getBomberType().equals("tu")) {
            ObjModels.render(tupolevModel, bufferSource.getBuffer(RenderType.entitySolid(RRIdentifiers.ettupolev)), poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
        } else {
            poseStack.scale(3, 3, 3);
            ObjModels.render(b2ForSpiritModel, bufferSource.getBuffer(RenderType.entitySolid(RRIdentifiers.etb2spirit)), poseStack, CommonColors.WHITE, packedLight, OverlayTexture.NO_OVERLAY);
        }
		poseStack.popPose();
	}

    @Override
    protected boolean affectedByCulling(EntityB2Spirit p_365169_) {
        return false;
    }

    @Override
    public boolean shouldRender(EntityB2Spirit livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(EntityB2Spirit p_entity, State reusedState, float partialTick) {
        super.extractRenderState(p_entity, reusedState, partialTick);
        reusedState.xRot = p_entity.getXRot(partialTick);
        reusedState.yRot = p_entity.getYRot(partialTick);
    }

    public static class State extends EntityRenderState {
        public float xRot;
        public float yRot;
    }
}
