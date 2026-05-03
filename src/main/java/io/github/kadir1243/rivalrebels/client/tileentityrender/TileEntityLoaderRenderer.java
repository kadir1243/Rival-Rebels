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

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.model.ModelLoader;
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityLoader;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.util.CommonColors;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class TileEntityLoaderRenderer implements BlockEntityRenderer<TileEntityLoader, TileEntityLoaderRenderer.LoaderBlockEntityRenderState> {
    private final QuadCollection tubeModel;

    public TileEntityLoaderRenderer(BlockEntityRendererProvider.Context context) {
        tubeModel = Minecraft.getInstance().getModelManager().getStandaloneModel(ObjModels.TUBE_MODEL);
    }

    @Override
    public void submit(LoaderBlockEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
		poseStack.translate(0.5F, 0.5F, 0.5F);

		ModelLoader.render(nodeCollector, RenderTypes.entitySolid(RRIdentifiers.etloader), poseStack, renderState.slide, renderState.lightCoords, OverlayTexture.NO_OVERLAY);
		poseStack.popPose();
        for (BlockEntity machine : renderState.machines) {
			poseStack.pushPose();
			poseStack.translate(0.5F, 0.5F, 0.5F);
			int xdif = machine.getBlockPos().getX() - renderState.blockPos.getX();
			int zdif = machine.getBlockPos().getZ() - renderState.blockPos.getZ();
			poseStack.mulPose(Axis.YP.rotationDegrees((float) (-90 + Math.atan2(xdif, zdif) * Mth.RAD_TO_DEG)));
			poseStack.translate(-1f, -0.40f, 0);
			poseStack.scale(0.5F, 0.15F, 0.15F);
			int dist = (int) Mth.sqrt((xdif * xdif) + (zdif * zdif));
            for (int d = 0; d < dist; d++) {
				poseStack.translate(2, 0, 0);
                nodeCollector.submitCustomGeometry(poseStack, RenderTypes.entitySolid(RRIdentifiers.ettube), (pose, consumer) -> {
                    ObjModels.render(tubeModel, consumer, pose, CommonColors.WHITE, renderState.lightCoords, OverlayTexture.NO_OVERLAY);
                });
            }
			poseStack.popPose();
		}
	}

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getViewDistance()
    {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityLoader blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-5, -1, -5), blockEntity.getBlockPos().offset(6, 2, 6)));
    }

    @Override
    public LoaderBlockEntityRenderState createRenderState() {
        return new LoaderBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(TileEntityLoader blockEntity, LoaderBlockEntityRenderState renderState, float partialTick, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);
        renderState.machines = blockEntity.machines;
        renderState.slide = blockEntity.slide;
    }

    public static class LoaderBlockEntityRenderState extends BlockEntityRenderState {
        public float slide;
        public List<BlockEntity> machines;
    }
}
