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

import io.github.kadir1243.rivalrebels.client.model.ModelBlastSphere;
import io.github.kadir1243.rivalrebels.common.entity.EntitySphereBlast;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.minecraft.world.level.lighting.LightEngine;

@OnlyIn(Dist.CLIENT)
public class RenderSphereBlast extends EntityRenderer<EntitySphereBlast, EntityRenderState> {
	public RenderSphereBlast(EntityRendererProvider.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        double elev = ((Mth.sin(renderState.ageInTicks / 40f) + 1.5f) * 10);
        poseStack.translate(0, elev, 0);
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
        poseStack.popPose();
    }

    @Override
    public boolean shouldRender(EntitySphereBlast livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    @Override
    protected int getBlockLightLevel(EntitySphereBlast entity, BlockPos pos) {
        return LightEngine.MAX_LEVEL;
    }
}
