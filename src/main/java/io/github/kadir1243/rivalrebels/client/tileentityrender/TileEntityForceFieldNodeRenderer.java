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

import io.github.kadir1243.rivalrebels.client.renderhelper.QuadHelper;
import io.github.kadir1243.rivalrebels.client.renderhelper.RenderTypes;
import io.github.kadir1243.rivalrebels.client.renderhelper.TextureVertice;
import io.github.kadir1243.rivalrebels.common.block.machine.BlockForceFieldNode;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityForceFieldNode;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class TileEntityForceFieldNodeRenderer implements BlockEntityRenderer<TileEntityForceFieldNode> {
    public TileEntityForceFieldNodeRenderer(BlockEntityRendererProvider.Context context) {
    }

    public static final Supplier<QuadHelper.BakedData> BAKED_MODEL = QuadHelper.createBakedModel(buffer -> {
        QuadHelper.addVertice(buffer, new Vector3f(-0.0625f, 3.5f, 0f), new TextureVertice(0, 0));
        QuadHelper.addVertice(buffer, new Vector3f(-0.0625f, -3.5f, 0f), new TextureVertice(0, 1));
        QuadHelper.addVertice(buffer, new Vector3f(-0.0625f, -3.5f, 35f), new TextureVertice(5, 1));
        QuadHelper.addVertice(buffer, new Vector3f(-0.0625f, 3.5f, 35f), new TextureVertice(5, 0));

        QuadHelper.addVertice(buffer, new Vector3f(0.0625f, -3.5f, 0f), new TextureVertice(0, 1));
        QuadHelper.addVertice(buffer, new Vector3f(0.0625f, 3.5f, 0f), new TextureVertice(0, 0));
        QuadHelper.addVertice(buffer, new Vector3f(0.0625f, 3.5f, 35f), new TextureVertice(5, 0));
        QuadHelper.addVertice(buffer, new Vector3f(0.0625f, -3.5f, 35f), new TextureVertice(5, 1));
    });

    @Override
    public void render(TileEntityForceFieldNode blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Vec3 cameraPos) {
        if (blockEntity.pInR <= 0) return;

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);

        poseStack.translate(0, 0, 0.5f);
        VertexConsumer cellularNoise = bufferSource.getBuffer(RenderTypes.CELLULAR_NOISE);
        ModelBlockRenderer.renderModel(poseStack.last(), cellularNoise, BAKED_MODEL.get().blockStateModel(), 1, 1, 1, packedLight, packedOverlay);

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
}
