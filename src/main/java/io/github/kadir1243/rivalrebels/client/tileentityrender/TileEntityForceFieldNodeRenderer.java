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

import io.github.kadir1243.rivalrebels.common.block.machine.BlockForceFieldNode;
import io.github.kadir1243.rivalrebels.common.noise.RivalRebelsCellularNoise;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityForceFieldNode;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.CommonColors;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

@OnlyIn(Dist.CLIENT)
public class TileEntityForceFieldNodeRenderer implements BlockEntityRenderer<TileEntityForceFieldNode> {
    public TileEntityForceFieldNodeRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityForceFieldNode entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (entity.pInR <= 0) return;

		matrices.pushPose();
		matrices.translate(0.5F, 0.5F, 0.5F);

        matrices.mulPose(Axis.YP.rotationDegrees(entity.getBlockState().getValue(BlockForceFieldNode.FACING).toYRot()));

		matrices.translate(0, 0, 0.5f);
        VertexConsumer cellularNoise = vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE);
		cellularNoise.addVertex(matrices.last(), -0.0625f, 3.5f, 0f).setColor(CommonColors.WHITE).setUv(0, 0).setOverlay(overlay).setLight(light);
		cellularNoise.addVertex(matrices.last(), -0.0625f, -3.5f, 0f).setColor(CommonColors.WHITE).setUv(0, 1).setOverlay(overlay).setLight(light);
		cellularNoise.addVertex(matrices.last(), -0.0625f, -3.5f, 35f).setColor(CommonColors.WHITE).setUv(5, 1).setOverlay(overlay).setLight(light);
		cellularNoise.addVertex(matrices.last(), -0.0625f, 3.5f, 35f).setColor(CommonColors.WHITE).setUv(5, 0).setOverlay(overlay).setLight(light);

		cellularNoise.addVertex(matrices.last(), 0.0625f, -3.5f, 0f).setColor(CommonColors.WHITE).setUv(0, 1).setOverlay(overlay).setLight(light);
		cellularNoise.addVertex(matrices.last(), 0.0625f, 3.5f, 0f).setColor(CommonColors.WHITE).setUv(0, 0).setOverlay(overlay).setLight(light);
		cellularNoise.addVertex(matrices.last(), 0.0625f, 3.5f, 35f).setColor(CommonColors.WHITE).setUv(5, 0).setOverlay(overlay).setLight(light);
		cellularNoise.addVertex(matrices.last(), 0.0625f, -3.5f, 35f).setColor(CommonColors.WHITE).setUv(5, 1).setOverlay(overlay).setLight(light);

		matrices.popPose();
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
}
