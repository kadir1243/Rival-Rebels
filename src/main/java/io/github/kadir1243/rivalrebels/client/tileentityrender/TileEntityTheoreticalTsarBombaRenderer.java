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

import com.mojang.math.Axis;
import io.github.kadir1243.rivalrebels.client.model.ModelTheoreticalTsarBomba;
import io.github.kadir1243.rivalrebels.common.block.trap.BlockTheoreticalTsarBomba;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityTheoreticalTsarBomba;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class TileEntityTheoreticalTsarBombaRenderer implements BlockEntityRenderer<TileEntityTheoreticalTsarBomba, TileEntityTheoreticalTsarBombaRenderer.TsarRenderState> {
    public TileEntityTheoreticalTsarBombaRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public TsarRenderState createRenderState() {
        return new TsarRenderState();
    }

    @Override
    public void extractRenderState(TileEntityTheoreticalTsarBomba blockEntity, TsarRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.facing = blockEntity.getBlockState().getValue(BlockTheoreticalTsarBomba.FACING);
    }

    @Override
    public void submit(TsarRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1F, 0.5F);
        poseStack.scale(1.3f, 1.3f, 1.3f);
        switch (renderState.facing) {
            case DOWN -> poseStack.mulPose(Axis.XP.rotationDegrees(180));
            case NORTH -> poseStack.mulPose(Axis.XP.rotationDegrees(-90));
            case SOUTH -> poseStack.mulPose(Axis.XP.rotationDegrees(90));
            case WEST -> poseStack.mulPose(Axis.ZP.rotationDegrees(90));
            case EAST -> poseStack.mulPose(Axis.ZP.rotationDegrees(-90));
        }

        ModelTheoreticalTsarBomba.render(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    @Override
    public int getViewDistance()
    {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityTheoreticalTsarBomba blockEntity) {
        return AABB.of(BoundingBox.fromCorners(blockEntity.getBlockPos().offset(-5, 0, -5), blockEntity.getBlockPos().offset(6, 2, 6)));
    }

    public static class TsarRenderState extends BlockEntityRenderState {
        public Direction facing;
    }
}
