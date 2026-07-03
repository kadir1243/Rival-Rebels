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

import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRRenderTypes;
import io.github.kadir1243.rivalrebels.common.entity.EntitySphereBlast;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.util.ARGB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.minecraft.world.level.lighting.LightEngine;

@OnlyIn(Dist.CLIENT)
public class RenderSphereBlast extends EntityRenderer<EntitySphereBlast, EntityRenderState> {
    private final QuadCollection model;
    public RenderSphereBlast(EntityRendererProvider.Context context) {
        super(context);

        model = Minecraft.getInstance().getModelManager().getStandaloneModel(ObjModels.BLAST_SPHERE_MODEL);
	}

    @Override
    public void submit(EntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        double elev = ((Mth.sin(renderState.ageInTicks / 40f) + 1.5f) * 10);
        poseStack.translate(0, elev, 0);

        {
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees((float) (elev * 2)));
            poseStack.mulPose(Axis.XP.rotationDegrees((float) (elev * 3)));
            poseStack.scale((float) elev, (float) elev, (float) elev);
            ObjModels.submit(nodeCollector, RRRenderTypes.MODEL_BLAST_SPHERE, model, poseStack, ARGB.colorFromFloat(1F, 1, 0.25f, 0), renderState.lightCoords, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
        {
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees((float) (elev * -2)));
            poseStack.mulPose(Axis.ZP.rotationDegrees((float) (elev * 4)));
            float scale = (float) (elev - 0.2f);
            poseStack.scale(scale, scale, scale);
            ObjModels.submit(nodeCollector, RRRenderTypes.MODEL_BLAST_SPHERE, model, poseStack, ARGB.colorFromFloat(1F, 1, 0.5f, 0), renderState.lightCoords, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
        {
            poseStack.pushPose();
            poseStack.mulPose(Axis.XP.rotationDegrees((float) (elev * -3)));
            poseStack.mulPose(Axis.ZP.rotationDegrees((float) (elev * 2)));
            float scale = (float) (elev - 0.4f);
            poseStack.scale(scale, scale, scale);
            ObjModels.submit(nodeCollector, RRRenderTypes.MODEL_BLAST_SPHERE, model, poseStack, CommonColors.RED, renderState.lightCoords, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
        {
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees((float) (elev * -1)));
            poseStack.mulPose(Axis.ZP.rotationDegrees((float) (elev * 3)));
            float scale = (float) (elev - 0.6f);
            poseStack.scale(scale, scale, scale);
            ObjModels.submit(nodeCollector, RRRenderTypes.MODEL_BLAST_SPHERE, model, poseStack, ARGB.colorFromFloat(1F, 1, 1, 0), renderState.lightCoords, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
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
