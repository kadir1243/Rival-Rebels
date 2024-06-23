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

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.common.block.machine.BlockForceField;
import assets.rivalrebels.common.tileentity.TileEntityGore;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class TileEntityGoreRenderer implements BlockEntityRenderer<TileEntityGore> {
	private static final float s = 0.5F;
	private static final Vector3f v1	= new Vector3f(s, s, s);
	private static final Vector3f v2	= new Vector3f(s, s, -s);
	private static final Vector3f v3	= new Vector3f(-s, s, -s);
	private static final Vector3f v4	= new Vector3f(-s, s, s);
	private static final Vector3f v5	= new Vector3f(s, -s, s);
	private static final Vector3f v6	= new Vector3f(s, -s, -s);
	private static final Vector3f v7	= new Vector3f(-s, -s, -s);
	private static final Vector3f v8	= new Vector3f(-s, -s, s);

    public TileEntityGoreRenderer(BlockEntityRendererProvider.Context context) {
    }

    private static boolean isFaceFull(Level world, BlockPos pos, Direction direction) {
        return Block.isFaceFull(world.getBlockState(pos.relative(direction)).getCollisionShape(world, pos.relative(direction)), direction.getOpposite());
    }

    @Override
    public void render(TileEntityGore entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        Level world = entity.getLevel();

		boolean ceil = isFaceFull(world, entity.getBlockPos(), Direction.UP);
		boolean floor = isFaceFull(world, entity.getBlockPos(), Direction.DOWN);
		boolean side1 = isFaceFull(world, entity.getBlockPos(), Direction.SOUTH);
		boolean side2 = isFaceFull(world, entity.getBlockPos(), Direction.WEST);
		boolean side3 = isFaceFull(world, entity.getBlockPos(), Direction.NORTH);
		boolean side4 = isFaceFull(world, entity.getBlockPos(), Direction.EAST);
		int meta = entity.getBlockState().getValue(BlockForceField.META);

		matrices.pushPose();
		matrices.translate((float) entity.getBlockPos().getX() + 0.5F, (float) entity.getBlockPos().getY() + 0.5F, (float) entity.getBlockPos().getZ() + 0.5F);
        ResourceLocation texture = switch (meta) {
            case 0 -> RRIdentifiers.btsplash1;
            case 1 -> RRIdentifiers.btsplash2;
            case 2 -> RRIdentifiers.btsplash3;
            case 3 -> RRIdentifiers.btsplash4;
            case 4 -> RRIdentifiers.btsplash5;
            case 5 -> RRIdentifiers.btsplash6;
            default -> RRIdentifiers.btsplash1;
        };

        VertexConsumer buffer = vertexConsumers.getBuffer(RenderType.entitySolid(texture));
        if (side1)
		{
			addVertex(matrices, buffer, v1, 0, 0, light, overlay);
			addVertex(matrices, buffer, v5, 1, 0, light, overlay);
			addVertex(matrices, buffer, v8, 1, 1, light, overlay);
			addVertex(matrices, buffer, v4, 0, 1, light, overlay);
		}

		if (side2) {
			addVertex(matrices, buffer, v4, 0, 0, light, overlay);
			addVertex(matrices, buffer, v8, 1, 0, light, overlay);
			addVertex(matrices, buffer, v7, 1, 1, light, overlay);
			addVertex(matrices, buffer, v3, 0, 1, light, overlay);
		}

		if (side3) {
			addVertex(matrices, buffer, v3, 0, 0, light, overlay);
			addVertex(matrices, buffer, v7, 1, 0, light, overlay);
			addVertex(matrices, buffer, v6, 1, 1, light, overlay);
			addVertex(matrices, buffer, v2, 0, 1, light, overlay);
		}

		if (side4) {
			addVertex(matrices, buffer, v2, 0, 0, light, overlay);
			addVertex(matrices, buffer, v6, 1, 0, light, overlay);
			addVertex(matrices, buffer, v5, 1, 1, light, overlay);
			addVertex(matrices, buffer, v1, 0, 1, light, overlay);
		}

		if (ceil) {
			addVertex(matrices, buffer, v4, 0, 0, light, overlay);
			addVertex(matrices, buffer, v3, 1, 0, light, overlay);
			addVertex(matrices, buffer, v2, 1, 1, light, overlay);
			addVertex(matrices, buffer, v1, 0, 1, light, overlay);
		}

		if (floor) {
			addVertex(matrices, buffer, v5, 0, 0, light, overlay);
			addVertex(matrices, buffer, v6, 1, 0, light, overlay);
			addVertex(matrices, buffer, v7, 1, 1, light, overlay);
			addVertex(matrices, buffer, v8, 0, 1, light, overlay);
		}

		matrices.popPose();
	}

	private void addVertex(PoseStack poseStack, VertexConsumer buffer, Vector3f v, float t, float t2, int light, int overlay) {
		buffer.addVertex(poseStack.last(), v.mul(0.999F, new Vector3f())).setUv(t, t2).setLight(light);
	}
}
