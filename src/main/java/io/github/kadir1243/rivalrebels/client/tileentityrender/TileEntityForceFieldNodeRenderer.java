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

import io.github.kadir1243.rivalrebels.client.renderhelper.RRRenderTypes;
import io.github.kadir1243.rivalrebels.client.renderhelper.RenderHelper;
import io.github.kadir1243.rivalrebels.client.renderhelper.TextureVertice;
import io.github.kadir1243.rivalrebels.common.block.machine.BlockForceFieldNode;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityForceFieldNode;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class TileEntityForceFieldNodeRenderer implements BlockEntityRenderer<TileEntityForceFieldNode, BlockEntityRenderState> {
    public TileEntityForceFieldNodeRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public boolean shouldRender(TileEntityForceFieldNode blockEntity, Vec3 cameraPos) {
        return BlockEntityRenderer.super.shouldRender(blockEntity, cameraPos) && blockEntity.pInR > 0;
    }

    @Override
    public void submit(BlockEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);

        poseStack.translate(0, 0, 0.5f);
        nodeCollector.submitCustomGeometry(poseStack, RRRenderTypes.CELLULAR_NOISE, (pose, buffer) -> {
            RenderHelper.addFace(pose, buffer,
                new Vector3f(-0.0625f, 3.5f, 0f),
                new Vector3f(-0.0625f, -3.5f, 0f),
                new Vector3f(-0.0625f, -3.5f, 35f),
                new Vector3f(-0.0625f, 3.5f, 35f),
                new TextureVertice(0, 0),
                new TextureVertice(0, 1),
                new TextureVertice(5, 1),
                new TextureVertice(5, 0),
                renderState.lightCoords
            );
            RenderHelper.addFace(pose, buffer,
                new Vector3f(0.0625f, -3.5f, 0f),
                new Vector3f(0.0625f, 3.5f, 0f),
                new Vector3f(0.0625f, 3.5f, 35f),
                new Vector3f(0.0625f, -3.5f, 35f),
                new TextureVertice(0, 1),
                new TextureVertice(0, 0),
                new TextureVertice(5, 0),
                new TextureVertice(5, 1),
                renderState.lightCoords
            );
        });

        poseStack.popPose();
	}

    @Override
    public int getViewDistance()
    {
        return 16384;
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityForceFieldNode entity) {
        float t = 0.0625f;
        float l = 35f;
        float h = 3.5f;
        BlockPos pos = entity.getBlockPos();
        return switch (entity.getBlockState().getValue(BlockForceFieldNode.FACING)) {
            case NORTH ->
                new AABB(pos.getX() + 0.5f - t, pos.getY() + 0.5f - h, pos.getZ() - l, pos.getX() + 0.5f + t, pos.getY() + 0.5f + h, pos.getZ());
            case SOUTH ->
                new AABB(pos.getX() + 0.5f - t, pos.getY() + 0.5f - h, pos.getZ() + 1f, pos.getX() + 0.5f + t, pos.getY() + 0.5f + h, pos.getZ() + 1f + l);
            case WEST ->
                new AABB(pos.getX() - l, pos.getY() + 0.5f - h, pos.getZ() + 0.5f - t, pos.getX(), pos.getY() + 0.5f + h, pos.getZ() + 0.5f + t);
            case EAST ->
                new AABB(pos.getX() + 1f, pos.getY() + 0.5f - h, pos.getZ() + 0.5f - t, pos.getX() + 1f + l, pos.getY() + 0.5f + h, pos.getZ() + 0.5f + t);
            default -> new AABB(Vec3.ZERO, Vec3.ZERO);
        };
    }

    @Override
    public BlockEntityRenderState createRenderState() {
        return new BlockEntityRenderState();
    }
}
