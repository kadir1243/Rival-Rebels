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
import assets.rivalrebels.client.renderhelper.Vertice;
import assets.rivalrebels.common.block.machine.BlockForceField;
import assets.rivalrebels.common.tileentity.TileEntityGore;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TileEntityGoreRenderer implements BlockEntityRenderer<TileEntityGore> {
	private static final float s = 0.5F;
	Vertice	v1	= new Vertice(s, s, s);
	Vertice	v2	= new Vertice(s, s, -s);
	Vertice	v3	= new Vertice(-s, s, -s);
	Vertice	v4	= new Vertice(-s, s, s);

	Vertice	v5	= new Vertice(s, -s, s);
	Vertice	v6	= new Vertice(s, -s, -s);
	Vertice	v7	= new Vertice(-s, -s, -s);
	Vertice	v8	= new Vertice(-s, -s, s);

    public TileEntityGoreRenderer(BlockEntityRendererFactory.Context context) {
    }

    private static boolean isFaceFull(World world, BlockPos pos, Direction direction) {
        return Block.isFaceFullSquare(world.getBlockState(pos.offset(direction)).getCollisionShape(world, pos.offset(direction)), direction.getOpposite());
    }

    @Override
    public void render(TileEntityGore entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();

		boolean ceil = isFaceFull(world, entity.getPos(), Direction.UP);
		boolean floor = isFaceFull(world, entity.getPos(), Direction.DOWN);
		boolean side1 = isFaceFull(world, entity.getPos(), Direction.SOUTH);
		boolean side2 = isFaceFull(world, entity.getPos(), Direction.WEST);
		boolean side3 = isFaceFull(world, entity.getPos(), Direction.NORTH);
		boolean side4 = isFaceFull(world, entity.getPos(), Direction.EAST);
		int meta = entity.getCachedState().get(BlockForceField.META);

		matrices.push();
		matrices.translate((float) entity.getPos().getX() + 0.5F, (float) entity.getPos().getY() + 0.5F, (float) entity.getPos().getZ() + 0.5F);
        switch (meta) {
            case 0 -> MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.btsplash1);
            case 1 -> MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.btsplash2);
            case 2 -> MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.btsplash3);
            case 3 -> MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.btsplash4);
            case 4 -> MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.btsplash5);
            case 5 -> MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.btsplash6);
            default -> MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.btsplash1);
        }

        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        if (side1)
		{
			addVertex(buffer, v1, 0, 0);
			addVertex(buffer, v5, 1, 0);
			addVertex(buffer, v8, 1, 1);
			addVertex(buffer, v4, 0, 1);
		}

		if (side2) {
			addVertex(buffer, v4, 0, 0);
			addVertex(buffer, v8, 1, 0);
			addVertex(buffer, v7, 1, 1);
			addVertex(buffer, v3, 0, 1);
		}

		if (side3) {
			addVertex(buffer, v3, 0, 0);
			addVertex(buffer, v7, 1, 0);
			addVertex(buffer, v6, 1, 1);
			addVertex(buffer, v2, 0, 1);
		}

		if (side4) {
			addVertex(buffer, v2, 0, 0);
			addVertex(buffer, v6, 1, 0);
			addVertex(buffer, v5, 1, 1);
			addVertex(buffer, v1, 0, 1);
		}

		if (ceil) {
			addVertex(buffer, v4, 0, 0);
			addVertex(buffer, v3, 1, 0);
			addVertex(buffer, v2, 1, 1);
			addVertex(buffer, v1, 0, 1);
		}

		if (floor) {
			addVertex(buffer, v5, 0, 0);
			addVertex(buffer, v6, 1, 0);
			addVertex(buffer, v7, 1, 1);
			addVertex(buffer, v8, 0, 1);
		}

		matrices.pop();
	}

	private void addVertex(VertexConsumer buffer, Vertice v, float t, float t2) {
		buffer.vertex(v.x * 0.999, v.y * 0.999, v.z * 0.999).texture(t, t2).next();
	}
}
