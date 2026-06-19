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
package io.github.kadir1243.rivalrebels.client.tileentityrender;

import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRRenderTypes;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityMeltDown;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.util.ARGB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class TileEntityMeltdownRenderer implements BlockEntityRenderer<TileEntityMeltDown, TileEntityMeltdownRenderer.MeltdownBlockEntityRenderState> {
    private final QuadCollection model;
    public TileEntityMeltdownRenderer(BlockEntityRendererProvider.Context context) {
        model = Minecraft.getInstance().getModelManager().getStandaloneModel(ObjModels.BLAST_SPHERE_MODEL);
    }

    @Override
    public void submit(MeltdownBlockEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        float fsize = Mth.sin(renderState.size);
		if (fsize <= 0) return;
		poseStack.pushPose();
		poseStack.translate(0.5F, 0.5F, 0.5F);
        {
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(renderState.size * 50));

            {
                poseStack.pushPose();
                float scale = fsize * 5.5F;
                poseStack.scale(scale, scale, scale);
                nodeCollector.submitCustomGeometry(poseStack, RRRenderTypes.MODEL_BLAST_SPHERE_TRIANGLES, (pose, consumer) -> {
                    ObjModels.render(model, consumer, pose, ARGB.colorFromFloat(0.4F, 1, 1, 1), renderState.lightCoords, OverlayTexture.NO_OVERLAY);
                });

                poseStack.popPose();
            }

            poseStack.mulPose(Axis.YP.rotationDegrees(renderState.size * 50));

            {
                poseStack.pushPose();
                float scale = fsize * 5.6F;
                poseStack.scale(scale, scale, scale);
                nodeCollector.submitCustomGeometry(poseStack, RRRenderTypes.MODEL_BLAST_SPHERE_TRIANGLES, (pose, consumer) -> {
                    ObjModels.render(model, consumer, pose, ARGB.colorFromFloat(0.4F, 1, 1, 1), renderState.lightCoords, OverlayTexture.NO_OVERLAY);
                });

                poseStack.popPose();
            }

            poseStack.popPose();
        }

        {
            poseStack.pushPose();
            float scale = fsize * 5.9F;
            poseStack.scale(scale, scale, scale);
            nodeCollector.submitCustomGeometry(poseStack, RRRenderTypes.MODEL_BLAST_SPHERE_TRIANGLES, (pose, consumer) -> {
                ObjModels.render(model, consumer, pose, ARGB.colorFromFloat(0.4F, 1, 1, 1), renderState.lightCoords, OverlayTexture.NO_OVERLAY);
            });

            poseStack.popPose();
        }

		poseStack.popPose();
	}

    @Override
    public int getViewDistance()
    {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityMeltDown blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-2, -2, -2), blockEntity.getBlockPos().offset(3, 3, 3)));
    }

    @Override
    public MeltdownBlockEntityRenderState createRenderState() {
        return new MeltdownBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(TileEntityMeltDown blockEntity, MeltdownBlockEntityRenderState renderState, float partialTick, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);
        renderState.size = blockEntity.size;
    }

    public static class MeltdownBlockEntityRenderState extends BlockEntityRenderState {
        public float size;
    }
}
