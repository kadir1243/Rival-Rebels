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
import io.github.kadir1243.rivalrebels.client.model.ModelNukeCrate;
import io.github.kadir1243.rivalrebels.common.block.RRBlocks;
import io.github.kadir1243.rivalrebels.common.block.crate.BlockNukeCrate;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityNukeCrate;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import org.jspecify.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class TileEntityNukeCrateRenderer implements BlockEntityRenderer<TileEntityNukeCrate, TileEntityNukeCrateRenderer.NukeCrateRenderState> {
    public TileEntityNukeCrateRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public NukeCrateRenderState createRenderState() {
        return new NukeCrateRenderState();
    }

    @Override
    public void extractRenderState(TileEntityNukeCrate blockEntity, NukeCrateRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.facing = blockEntity.getBlockState().getValue(BlockNukeCrate.FACING);
        state.isBottomCrate = blockEntity.getBlockState().is(RRBlocks.nukeCrateBottom);
    }

    @Override
    public void submit(NukeCrateRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);
        switch (renderState.facing) {
            case DOWN -> poseStack.mulPose(Axis.XP.rotationDegrees(180));
            case NORTH -> poseStack.mulPose(Axis.XP.rotationDegrees(-90));
            case SOUTH -> poseStack.mulPose(Axis.XP.rotationDegrees(90));
            case WEST -> poseStack.mulPose(Axis.ZP.rotationDegrees(90));
            case EAST -> poseStack.mulPose(Axis.ZP.rotationDegrees(-90));
        }
        RenderType buffer;
        if (renderState.isBottomCrate)
            buffer = RenderTypes.entitySolid(RRIdentifiers.btnukebottom);
        else
            buffer = RenderTypes.entitySolid(RRIdentifiers.btnuketop);
        int packedLight = renderState.lightCoords;
        int packedOverlay = OverlayTexture.NO_OVERLAY;
        nodeCollector.submitCustomGeometry(poseStack, buffer, (pose, consumer) -> {
            ModelNukeCrate.renderModelA(pose, consumer, packedLight, packedOverlay);
        });
        nodeCollector.submitCustomGeometry(poseStack, RenderTypes.entitySolid(RRIdentifiers.btcrate), (pose, consumer) -> {
            ModelNukeCrate.renderModelB(pose, consumer, packedLight, packedOverlay);
        });
        poseStack.popPose();
    }

    public static class NukeCrateRenderState extends BlockEntityRenderState {
        public Direction facing;
        public boolean isBottomCrate;
    }
}
