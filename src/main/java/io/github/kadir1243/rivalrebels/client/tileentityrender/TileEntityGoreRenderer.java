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
import io.github.kadir1243.rivalrebels.client.renderhelper.RenderHelper;
import io.github.kadir1243.rivalrebels.client.renderhelper.TextureVertice;
import io.github.kadir1243.rivalrebels.common.block.BlockGore;
import io.github.kadir1243.rivalrebels.common.tileentity.TileEntityGore;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
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

    @Override
    public void render(TileEntityGore blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Vec3 cameraPos) {
        BlockState state = blockEntity.getBlockState();
        boolean ceil = state.getValue(BlockGore.IS_UP_FULL);
		boolean floor = state.getValue(BlockGore.IS_DOWN_FULL);
		boolean side1 = state.getValue(BlockGore.IS_SOUTH_FULL);
		boolean side2 = state.getValue(BlockGore.IS_WEST_FULL);
		boolean side3 = state.getValue(BlockGore.IS_NORTH_FULL);
		boolean side4 = state.getValue(BlockGore.IS_EAST_FULL);

		int meta = state.getValue(BlockGore.META);

		poseStack.pushPose();
		poseStack.translate(0.5F, 0.5F, 0.5F);
        ResourceLocation texture = switch (meta) {
            case 0 -> RRIdentifiers.btsplash1;
            case 1 -> RRIdentifiers.btsplash2;
            case 2 -> RRIdentifiers.btsplash3;
            case 3 -> RRIdentifiers.btsplash4;
            case 4 -> RRIdentifiers.btsplash5;
            case 5 -> RRIdentifiers.btsplash6;
            default -> throw new IllegalStateException("Unexpected value: " + meta);
        };

        VertexConsumer buffer = bufferSource.getBuffer(RenderType.entitySolid(texture));
        if (side1) {
            addVertex(poseStack, buffer, v1, 0, 0, packedLight, packedOverlay);
            addVertex(poseStack, buffer, v5, 1, 0, packedLight, packedOverlay);
            addVertex(poseStack, buffer, v8, 1, 1, packedLight, packedOverlay);
            addVertex(poseStack, buffer, v4, 0, 1, packedLight, packedOverlay);
        }

        if (side2) {
            addVertex(poseStack, buffer, v4, 0, 0, packedLight, packedOverlay);
            addVertex(poseStack, buffer, v8, 1, 0, packedLight, packedOverlay);
            addVertex(poseStack, buffer, v7, 1, 1, packedLight, packedOverlay);
            addVertex(poseStack, buffer, v3, 0, 1, packedLight, packedOverlay);
        }

        if (side3) {
            addVertex(poseStack, buffer, v3, 0, 0, packedLight, packedOverlay);
            addVertex(poseStack, buffer, v7, 1, 0, packedLight, packedOverlay);
            addVertex(poseStack, buffer, v6, 1, 1, packedLight, packedOverlay);
            addVertex(poseStack, buffer, v2, 0, 1, packedLight, packedOverlay);
        }

        if (side4) {
            addVertex(poseStack, buffer, v2, 0, 0, packedLight, packedOverlay);
            addVertex(poseStack, buffer, v6, 1, 0, packedLight, packedOverlay);
            addVertex(poseStack, buffer, v5, 1, 1, packedLight, packedOverlay);
            addVertex(poseStack, buffer, v1, 0, 1, packedLight, packedOverlay);
        }

        if (ceil) {
            addVertex(poseStack, buffer, v4, 0, 0, packedLight, packedOverlay);
            addVertex(poseStack, buffer, v3, 1, 0, packedLight, packedOverlay);
            addVertex(poseStack, buffer, v2, 1, 1, packedLight, packedOverlay);
            addVertex(poseStack, buffer, v1, 0, 1, packedLight, packedOverlay);
        }

        if (floor) {
            addVertex(poseStack, buffer, v5, 0, 0, packedLight, packedOverlay);
            addVertex(poseStack, buffer, v6, 1, 0, packedLight, packedOverlay);
            addVertex(poseStack, buffer, v7, 1, 1, packedLight, packedOverlay);
            addVertex(poseStack, buffer, v8, 0, 1, packedLight, packedOverlay);
        }

        poseStack.popPose();
    }

	private void addVertex(PoseStack poseStack, VertexConsumer buffer, Vector3f v, float t, float t2, int light, int overlay) {
        RenderHelper.addVertice(poseStack, buffer, v.mul(0.999F, new Vector3f()), new TextureVertice(t, t2), light, overlay);
	}
}
