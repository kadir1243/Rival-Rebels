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
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.Identifier;
import org.joml.Vector3f;
import org.jspecify.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class TileEntityGoreRenderer implements BlockEntityRenderer<TileEntityGore, TileEntityGoreRenderer.GoreRenderState> {
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
    public GoreRenderState createRenderState() {
        return new GoreRenderState();
    }

    @Override
    public void extractRenderState(TileEntityGore blockEntity, GoreRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        BlockState bstate = blockEntity.getBlockState();
        state.ceil = bstate.getValue(BlockGore.IS_UP_FULL);
        state.floor = bstate.getValue(BlockGore.IS_DOWN_FULL);
        state.side1 = bstate.getValue(BlockGore.IS_SOUTH_FULL);
        state.side2 = bstate.getValue(BlockGore.IS_WEST_FULL);
        state.side3 = bstate.getValue(BlockGore.IS_NORTH_FULL);
        state.side4 = bstate.getValue(BlockGore.IS_EAST_FULL);
        state.type = bstate.getValue(BlockGore.META);
    }

    @Override
    public void submit(GoreRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
		poseStack.pushPose();
		poseStack.translate(0.5F, 0.5F, 0.5F);
        Identifier texture = switch (renderState.type) {
            case 0 -> RRIdentifiers.btsplash1;
            case 1 -> RRIdentifiers.btsplash2;
            case 2 -> RRIdentifiers.btsplash3;
            case 3 -> RRIdentifiers.btsplash4;
            case 4 -> RRIdentifiers.btsplash5;
            case 5 -> RRIdentifiers.btsplash6;
            default -> throw new IllegalStateException("Unexpected value: " + renderState.type);
        };
        int packedLight = renderState.lightCoords;
        int packedOverlay = OverlayTexture.NO_OVERLAY;

        nodeCollector.submitCustomGeometry(poseStack, RenderTypes.entitySolid(texture), (pose, consumer) -> {
            if (renderState.side1) {
                addVertex(pose, consumer, v1, 0, 0, packedLight, packedOverlay);
                addVertex(pose, consumer, v5, 1, 0, packedLight, packedOverlay);
                addVertex(pose, consumer, v8, 1, 1, packedLight, packedOverlay);
                addVertex(pose, consumer, v4, 0, 1, packedLight, packedOverlay);
            }

            if (renderState.side2) {
                addVertex(pose, consumer, v4, 0, 0, packedLight, packedOverlay);
                addVertex(pose, consumer, v8, 1, 0, packedLight, packedOverlay);
                addVertex(pose, consumer, v7, 1, 1, packedLight, packedOverlay);
                addVertex(pose, consumer, v3, 0, 1, packedLight, packedOverlay);
            }

            if (renderState.side3) {
                addVertex(pose, consumer, v3, 0, 0, packedLight, packedOverlay);
                addVertex(pose, consumer, v7, 1, 0, packedLight, packedOverlay);
                addVertex(pose, consumer, v6, 1, 1, packedLight, packedOverlay);
                addVertex(pose, consumer, v2, 0, 1, packedLight, packedOverlay);
            }

            if (renderState.side4) {
                addVertex(pose, consumer, v2, 0, 0, packedLight, packedOverlay);
                addVertex(pose, consumer, v6, 1, 0, packedLight, packedOverlay);
                addVertex(pose, consumer, v5, 1, 1, packedLight, packedOverlay);
                addVertex(pose, consumer, v1, 0, 1, packedLight, packedOverlay);
            }

            if (renderState.ceil) {
                addVertex(pose, consumer, v4, 0, 0, packedLight, packedOverlay);
                addVertex(pose, consumer, v3, 1, 0, packedLight, packedOverlay);
                addVertex(pose, consumer, v2, 1, 1, packedLight, packedOverlay);
                addVertex(pose, consumer, v1, 0, 1, packedLight, packedOverlay);
            }

            if (renderState.floor) {
                addVertex(pose, consumer, v5, 0, 0, packedLight, packedOverlay);
                addVertex(pose, consumer, v6, 1, 0, packedLight, packedOverlay);
                addVertex(pose, consumer, v7, 1, 1, packedLight, packedOverlay);
                addVertex(pose, consumer, v8, 0, 1, packedLight, packedOverlay);
            }
        });

        poseStack.popPose();
    }

	private void addVertex(PoseStack.Pose pose, VertexConsumer buffer, Vector3f v, float t, float t2, int light, int overlay) {
        RenderHelper.addVertice(pose, buffer, v.mul(0.999F, new Vector3f()), new TextureVertice(t, t2), light, overlay);
	}

    public static class GoreRenderState extends BlockEntityRenderState {
        public int type;
        public boolean ceil;
        public boolean floor;
        public boolean side1;
        public boolean side2;
        public boolean side3;
        public boolean side4;
    }
}
