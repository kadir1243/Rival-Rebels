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
import io.github.kadir1243.rivalrebels.client.model.ModelBlastSphere;
import io.github.kadir1243.rivalrebels.client.model.ModelTsarBlast;
import io.github.kadir1243.rivalrebels.common.entity.EntityTsarBlast;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.world.level.lighting.LightEngine;

@OnlyIn(Dist.CLIENT)
public class RenderTsarBlast extends EntityRenderer<EntityTsarBlast, RenderTsarBlast.State> {
	protected final ModelTsarBlast model = new ModelTsarBlast();

    public RenderTsarBlast(EntityRendererProvider.Context manager) {
        super(manager);
    }

    @Override
    public void render(State renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        double radius = (((renderState.deltaMovement.x() * 10) - 1) * ((renderState.deltaMovement.x() * 10) - 1) * 2) + RRConfig.SERVER.getTsarBombaStrength();
        poseStack.pushPose();
        if (renderState.ageInTicks < 60) {
            double elev = renderState.ageInTicks / 5f;
            poseStack.translate(0, elev, 0);
            ModelBlastSphere.renderModel(poseStack, bufferSource, renderState.ageInTicks * RRConfig.CLIENT.getShroomScale(), CommonColors.WHITE);
        } else if (renderState.ageInTicks < 300 && radius - RRConfig.SERVER.getTsarBombaStrength() > 9) {
            double elev = (renderState.ageInTicks - 60f) / 4f;
            poseStack.translate(0, elev, 0);
            poseStack.scale(RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale());
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees((float) (elev * 2)));
            poseStack.mulPose(Axis.XP.rotationDegrees((float) (elev * 3)));
            ModelBlastSphere.renderModel(poseStack, bufferSource, (float) elev, 1, 0.25f, 0, 1f);
            poseStack.popPose();
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees((float) (elev * -2)));
            poseStack.mulPose(Axis.ZP.rotationDegrees((float) (elev * 4)));
            ModelBlastSphere.renderModel(poseStack, bufferSource, (float) (elev - 0.2f), 1, 0.5f, 0, 1f);
            poseStack.popPose();
            poseStack.pushPose();
            poseStack.mulPose(Axis.XP.rotationDegrees((float) (elev * -3)));
            poseStack.mulPose(Axis.ZP.rotationDegrees((float) (elev * 2)));
            ModelBlastSphere.renderModel(poseStack, bufferSource, (float) (elev - 0.4f), CommonColors.RED);
            poseStack.popPose();
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees((float) (elev * -1)));
            poseStack.mulPose(Axis.ZP.rotationDegrees((float) (elev * 3)));
            ModelBlastSphere.renderModel(poseStack, bufferSource, (float) (elev - 0.6f), 1, 1, 0, 1);
            poseStack.popPose();
        } else {
            poseStack.translate(0, 10 + ((renderState.deltaMovement.x() - 0.1d) * 14.14213562), 0);
            poseStack.scale(RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale(), RRConfig.CLIENT.getShroomScale());
            poseStack.scale((float) (radius * 0.116f), (float) (radius * 0.065f), (float) (radius * 0.116f));
            if (shouldBeScaledToSmaller()) poseStack.scale(0.8f, 0.8f, 0.8f);
            model.render(poseStack, bufferSource.getBuffer(RenderType.entitySolid(getTextureLocation(renderState))), packedLight, OverlayTexture.NO_OVERLAY);
        }
        poseStack.popPose();
    }

    public ResourceLocation getTextureLocation(State entity) {
        return RRIdentifiers.ettsarflame;
    }

    public boolean shouldBeScaledToSmaller() {
        return false;
    }

    @Override
    public boolean shouldRender(EntityTsarBlast livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    protected int getBlockLightLevel(EntityTsarBlast entity, BlockPos pos) {
        return LightEngine.MAX_LEVEL;
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(EntityTsarBlast p_entity, State reusedState, float partialTick) {
        super.extractRenderState(p_entity, reusedState, partialTick);
        reusedState.deltaMovement = p_entity.getDeltaMovement();
    }

    public static class State extends EntityRenderState {
        public Vec3 deltaMovement;
    }
}
