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
package assets.rivalrebels.client.tileentityrender;

import assets.rivalrebels.common.block.machine.BlockForceFieldNode;
import assets.rivalrebels.common.noise.RivalRebelsCellularNoise;
import assets.rivalrebels.common.tileentity.TileEntityForceFieldNode;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class TileEntityForceFieldNodeRenderer implements BlockEntityRenderer<TileEntityForceFieldNode>, CustomRenderBoxExtension<TileEntityForceFieldNode> {
    public TileEntityForceFieldNodeRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntityForceFieldNode entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (entity.pInR <= 0) return;

		matrices.pushPose();
		matrices.translate((float) entity.getBlockPos().getX() + 0.5F, (float) entity.getBlockPos().getY() + 0.5F, (float) entity.getBlockPos().getZ() + 0.5F);

        int meta = entity.getBlockState().getValue(BlockForceFieldNode.META);
        if (meta == 2)
		{
			matrices.mulPose(new Quaternionf(90, 0, 1, 0));
		}

		if (meta == 3)
		{
			matrices.mulPose(new Quaternionf(-90, 0, 1, 0));
		}

		if (meta == 4)
		{
			matrices.mulPose(new Quaternionf(180, 0, 1, 0));
		}

		matrices.mulPose(new Quaternionf(90, 0.0F, 1.0F, 0.0F));
		matrices.translate(0, 0, 0.5f);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE);
		vertexConsumer.addVertex(matrices.last(), -0.0625f, 3.5f, 0f).setColor(1, 1, 1, 1).setUv(0, 0).setOverlay(overlay).setLight(light);
		vertexConsumer.addVertex(matrices.last(), -0.0625f, -3.5f, 0f).setColor(1, 1, 1, 1).setUv(0, 1).setOverlay(overlay).setLight(light);
		vertexConsumer.addVertex(matrices.last(), -0.0625f, -3.5f, 35f).setColor(1, 1, 1, 1).setUv(5, 1).setOverlay(overlay).setLight(light);
		vertexConsumer.addVertex(matrices.last(), -0.0625f, 3.5f, 35f).setColor(1, 1, 1, 1).setUv(5, 0).setOverlay(overlay).setLight(light);

		vertexConsumer.addVertex(matrices.last(), 0.0625f, -3.5f, 0f).setColor(1, 1, 1, 1).setUv(0, 1).setOverlay(overlay).setLight(light);
		vertexConsumer.addVertex(matrices.last(), 0.0625f, 3.5f, 0f).setColor(1, 1, 1, 1).setUv(0, 0).setOverlay(overlay).setLight(light);
		vertexConsumer.addVertex(matrices.last(), 0.0625f, 3.5f, 35f).setColor(1, 1, 1, 1).setUv(5, 0).setOverlay(overlay).setLight(light);
		vertexConsumer.addVertex(matrices.last(), 0.0625f, -3.5f, 35f).setColor(1, 1, 1, 1).setUv(5, 1).setOverlay(overlay).setLight(light);

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
        return switch (entity.getBlockState().getValue(BlockForceFieldNode.META)) {
            case 2 ->
                new AABB(pos.getX() + 0.5f - t, pos.getY() + 0.5f - h, pos.getZ() - l, pos.getX() + 0.5f + t, pos.getY() + 0.5f + h, pos.getZ());
            case 3 ->
                new AABB(pos.getX() + 0.5f - t, pos.getY() + 0.5f - h, pos.getZ() + 1f, pos.getX() + 0.5f + t, pos.getY() + 0.5f + h, pos.getZ() + 1f + l);
            case 4 ->
                new AABB(pos.getX() - l, pos.getY() + 0.5f - h, pos.getZ() + 0.5f - t, pos.getX(), pos.getY() + 0.5f + h, pos.getZ() + 0.5f + t);
            case 5 ->
                new AABB(pos.getX() + 1f, pos.getY() + 0.5f - h, pos.getZ() + 0.5f - t, pos.getX() + 1f + l, pos.getY() + 0.5f + h, pos.getZ() + 0.5f + t);
            default -> new AABB(Vec3.ZERO, Vec3.ZERO);
        };
    }
}
