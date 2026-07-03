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
import io.github.kadir1243.rivalrebels.common.entity.EntityAntimatterBomb;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.util.CommonColors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;

@OnlyIn(Dist.CLIENT)
public class RenderAntimatterBomb extends EntityRenderer<EntityAntimatterBomb, RenderAntimatterBomb.State> {
    private final QuadCollection bombModel;

    public RenderAntimatterBomb(EntityRendererProvider.Context context) {
        super(context);
        bombModel = Minecraft.getInstance().getModelManager().getStandaloneModel(ObjModels.BOMB_MODEL);
    }

    @Override
    public void submit(State renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.scale(RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale());
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 90.0f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot));
        ObjModels.submit(nodeCollector, RenderTypes.entitySolid(RRIdentifiers.etantimatterbomb), bombModel, poseStack, CommonColors.WHITE, LightCoordsUtil.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    @Override
    public boolean shouldRender(EntityAntimatterBomb livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(EntityAntimatterBomb entity, State reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.xRot = entity.getXRot(partialTick);
        reusedState.yRot = entity.getYRot(partialTick);
    }

    public static class State extends EntityRenderState {
        public float xRot;
        public float yRot;
    }
}
