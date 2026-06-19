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
import io.github.kadir1243.rivalrebels.client.model.ModelLaptop;
import io.github.kadir1243.rivalrebels.common.entity.EntityLaptop;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;

@OnlyIn(Dist.CLIENT)
public class RenderLaptop extends EntityRenderer<EntityLaptop, RenderLaptop.State> {
    public RenderLaptop(EntityRendererProvider.Context manager) {
        super(manager);
	}

    @Override
    public void render(State renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(180 - renderState.yRot));
		ModelLaptop.renderModel(bufferSource, poseStack, -renderState.slide, packedLight, OverlayTexture.NO_OVERLAY);
		ModelLaptop.renderScreen(bufferSource, RRIdentifiers.etubuntu, poseStack, -renderState.slide, packedLight, OverlayTexture.NO_OVERLAY);
		poseStack.popPose();
	}

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(EntityLaptop p_entity, State reusedState, float partialTick) {
        super.extractRenderState(p_entity, reusedState, partialTick);
        reusedState.slide = (float) p_entity.slide;
        reusedState.yRot = p_entity.getYRot(partialTick);
    }

    public static class State extends EntityRenderState {
        public float slide;
        public float yRot;
    }
}
