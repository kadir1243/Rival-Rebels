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
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityPlasmaExplosion;
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
public class TileEntityPlasmaExplosionRenderer implements BlockEntityRenderer<TileEntityPlasmaExplosion, TileEntityPlasmaExplosionRenderer.PlasmaExplosionBlockEntityRenderState> {
    private final QuadCollection model;
    public TileEntityPlasmaExplosionRenderer(BlockEntityRendererProvider.Context context) {
        model = Minecraft.getInstance().getModelManager().getStandaloneModel(ObjModels.BLAST_SPHERE_MODEL);
    }

    @Override
    public PlasmaExplosionBlockEntityRenderState createRenderState() {
        return new PlasmaExplosionBlockEntityRenderState();
    }

    @Override
    public void submit(PlasmaExplosionBlockEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        float fsize = Mth.sin(renderState.size);
		poseStack.pushPose();
		poseStack.translate(0.5F, 0.5F, 0.5F);

		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(renderState.size * 50));
        renderBlastSphere(renderState, fsize * 5.5f, poseStack, nodeCollector, 0.45f, 0.45f, 0.65f, 0.4f);
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.size * 50));
        renderBlastSphere(renderState, fsize * 5.6f, poseStack, nodeCollector, 0.45f, 0.35f, 0.65f, 0.4f);
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.size * 50));
        renderBlastSphere(renderState, fsize * 5.7f, poseStack, nodeCollector, 0.45f, 0.35f, 0.95f, 0.4f);
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.size * 50));
        renderBlastSphere(renderState, fsize * 5.8f, poseStack, nodeCollector, 0.45f, 0.35f, 0.65f, 0.4f);
		poseStack.popPose();
        renderBlastSphere(renderState, fsize * 5.9f, poseStack, nodeCollector, 0.45f, 0.35f, 0.65f, 0.4f);
		poseStack.popPose();
	}

    private void renderBlastSphere(PlasmaExplosionBlockEntityRenderState renderState, float scale, PoseStack poseStack, SubmitNodeCollector nodeCollector, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);
        nodeCollector.submitCustomGeometry(poseStack, RRRenderTypes.MODEL_BLAST_SPHERE, (pose, consumer) -> {
            ObjModels.render(model, consumer, pose, ARGB.colorFromFloat(alpha, red, green, blue), renderState.lightCoords, OverlayTexture.NO_OVERLAY);
        });

        poseStack.popPose();
    }

    @Override
    public int getViewDistance()
    {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityPlasmaExplosion blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-2, -2, -2), blockEntity.getBlockPos().offset(3, 3, 3)));
    }

    @Override
    public void extractRenderState(TileEntityPlasmaExplosion blockEntity, PlasmaExplosionBlockEntityRenderState renderState, float partialTick, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);
        renderState.size = blockEntity.size;
    }

    public static class PlasmaExplosionBlockEntityRenderState extends BlockEntityRenderState {
        public float size;
    }
}
