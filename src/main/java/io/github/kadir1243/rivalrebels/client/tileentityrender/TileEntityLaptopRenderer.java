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
import io.github.kadir1243.rivalrebels.client.model.ModelLaptop;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityLaptop;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.jspecify.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class TileEntityLaptopRenderer implements BlockEntityRenderer<TileEntityLaptop, TileEntityLaptopRenderer.LaptopBlockEntityRenderState> {
    public TileEntityLaptopRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public LaptopBlockEntityRenderState createRenderState() {
        return new LaptopBlockEntityRenderState();
    }

    @Override
    public void submit(LaptopBlockEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 0, 0.5F);
        int packedLight = renderState.lightCoords;
        int packedOverlay = OverlayTexture.NO_OVERLAY;
        ModelLaptop.renderModel(nodeCollector, poseStack, -renderState.slide, packedLight, packedOverlay);
        ModelLaptop.renderScreen(nodeCollector, RRIdentifiers.etubuntu, poseStack, -renderState.slide, packedLight, packedOverlay);
        poseStack.popPose();
    }

    @Override
    public void extractRenderState(TileEntityLaptop blockEntity, LaptopBlockEntityRenderState renderState, float partialTick, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);
        renderState.slide = blockEntity.slide;
    }

    public static class LaptopBlockEntityRenderState extends BlockEntityRenderState {
        public float slide;
    }
}
