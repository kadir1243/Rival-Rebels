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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class TileEntityForceFieldNodeRenderer implements BlockEntityRenderer<TileEntityForceFieldNode>, CustomRenderBoxExtension<TileEntityForceFieldNode> {
    public TileEntityForceFieldNodeRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(TileEntityForceFieldNode entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (entity.pInR <= 0) return;

		matrices.push();
		matrices.translate((float) entity.getPos().getX() + 0.5F, (float) entity.getPos().getY() + 0.5F, (float) entity.getPos().getZ() + 0.5F);

        int meta = entity.getCachedState().get(BlockForceFieldNode.META);
        if (meta == 2)
		{
			matrices.multiply(new Quaternionf(90, 0, 1, 0));
		}

		if (meta == 3)
		{
			matrices.multiply(new Quaternionf(-90, 0, 1, 0));
		}

		if (meta == 4)
		{
			matrices.multiply(new Quaternionf(180, 0, 1, 0));
		}

		matrices.multiply(new Quaternionf(90, 0.0F, 1.0F, 0.0F));
		matrices.translate(0, 0, 0.5f);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE);
		vertexConsumer.vertex(-0.0625f, 3.5f, 0f).color(1, 1, 1, 1).texture(0, 0).overlay(overlay).light(light).next();
		vertexConsumer.vertex(-0.0625f, -3.5f, 0f).color(1, 1, 1, 1).texture(0, 1).overlay(overlay).light(light).next();
		vertexConsumer.vertex(-0.0625f, -3.5f, 35f).color(1, 1, 1, 1).texture(5, 1).overlay(overlay).light(light).next();
		vertexConsumer.vertex(-0.0625f, 3.5f, 35f).color(1, 1, 1, 1).texture(5, 0).overlay(overlay).light(light).next();
		vertexConsumer.vertex(0.0625f, -3.5f, 0f).color(1, 1, 1, 1).texture(0, 1).overlay(overlay).light(light).next();
		vertexConsumer.vertex(0.0625f, 3.5f, 0f).color(1, 1, 1, 1).texture(0, 0).overlay(overlay).light(light).next();
		vertexConsumer.vertex(0.0625f, 3.5f, 35f).color(1, 1, 1, 1).texture(5, 0).overlay(overlay).light(light).next();
		vertexConsumer.vertex(0.0625f, -3.5f, 35f).color(1, 1, 1, 1).texture(5, 1).overlay(overlay).light(light).next();

		matrices.pop();
	}

    @Override
    public int getRenderDistance()
    {
        return 16384;
    }

    @Override
    public Box getRenderBoundingBox(TileEntityForceFieldNode entity) {
        float t = 0.0625f;
        float l = 35f;
        float h = 3.5f;
        BlockPos pos = entity.getPos();
        return switch (entity.getCachedState().get(BlockForceFieldNode.META)) {
            case 2 ->
                new Box(pos.getX() + 0.5f - t, pos.getY() + 0.5f - h, pos.getZ() - l, pos.getX() + 0.5f + t, pos.getY() + 0.5f + h, pos.getZ());
            case 3 ->
                new Box(pos.getX() + 0.5f - t, pos.getY() + 0.5f - h, pos.getZ() + 1f, pos.getX() + 0.5f + t, pos.getY() + 0.5f + h, pos.getZ() + 1f + l);
            case 4 ->
                new Box(pos.getX() - l, pos.getY() + 0.5f - h, pos.getZ() + 0.5f - t, pos.getX(), pos.getY() + 0.5f + h, pos.getZ() + 0.5f + t);
            case 5 ->
                new Box(pos.getX() + 1f, pos.getY() + 0.5f - h, pos.getZ() + 0.5f - t, pos.getX() + 1f + l, pos.getY() + 0.5f + h, pos.getZ() + 0.5f + t);
            default -> new Box(Vec3d.ZERO, Vec3d.ZERO);
        };
    }
}
