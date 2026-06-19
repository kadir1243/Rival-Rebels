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
package io.github.kadir1243.rivalrebels.client.renderentity;

import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.client.renderhelper.RenderTypes;
import io.github.kadir1243.rivalrebels.common.entity.EntityLightningLink;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.lighting.LightEngine;

@OnlyIn(Dist.CLIENT)
public class RenderLightningLink extends EntityRenderer<EntityLightningLink, RenderLightningLink.State> {
    static float	red		= 0.65F;
	static float	green	= 0.75F;
	static float	blue	= 1F;
    private static final int COLOR = ARGB.colorFromFloat(0.95F, red, green, blue);

    public RenderLightningLink(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(State renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		float segmentDistance = RRConfig.CLIENT.getTeslaSegments();
		float distance = (float) renderState.deltaMovement.x() * 100;
		distance = 100;

		// RenderLibrary.instance.renderModel((float) x, (float) y, (float) z,
		// (float) Math.sin(-entity.yaw / 180 * Math.PI) * distance,
		// (float) Math.sin(-entity.pitch / 180 * Math.PI) * distance,
		// (float) Math.cos(-entity.yaw / 180 * Math.PI) * distance,
		// 2f, 0.07f, 8, 5f, 0.5f, red, green, blue, 1);

		if (distance > 0) {
			RandomSource random = renderState.random;
			float radius = 0.07F;
            VertexConsumer buffer = bufferSource.getBuffer(RenderTypes.LIGHTNING_LINK);

            poseStack.pushPose();
			poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot));
			poseStack.mulPose(Axis.XP.rotationDegrees(-renderState.xRot));

			float AddedX = 0;
			float AddedY = 0;
			float prevAddedX = 0;
			float prevAddedY = 0;
			for (int addedZ = (int) distance; addedZ >= 0; addedZ -= segmentDistance)
			{
				prevAddedX = AddedX;
				prevAddedY = AddedY;
				AddedX += (random.nextFloat() - 0.5) * 2;
				AddedY += (random.nextFloat() - 0.5) * 2;
				float dist = Mth.sqrt(AddedX * AddedX + AddedY * AddedY) / 1.5F;
				if (dist != 0)
				{
                    float tempAddedX = AddedX / dist;
                    float tempAddedY = AddedY / dist;
					if (Mth.abs(tempAddedX) < Mth.abs(AddedX)) AddedX = tempAddedX;
					if (Mth.abs(tempAddedY) < Mth.abs(AddedY)) AddedY = tempAddedY;
				}
				if (addedZ <= 0)
				{
					AddedX = AddedY = 0;
				}

				for (float o = 0; o <= radius; o += radius / 8) {
                    buffer.addVertex(poseStack.last(), AddedX + o, AddedY - o, addedZ).setColor(COLOR);
                    buffer.addVertex(poseStack.last(), AddedX + o, AddedY + o, addedZ).setColor(COLOR);
                    buffer.addVertex(poseStack.last(), prevAddedX + o, prevAddedY + o, addedZ + segmentDistance).setColor(COLOR);
                    buffer.addVertex(poseStack.last(), prevAddedX + o, prevAddedY - o, addedZ + segmentDistance).setColor(COLOR);

                    buffer.addVertex(poseStack.last(), AddedX - o, AddedY - o, addedZ).setColor(COLOR);
                    buffer.addVertex(poseStack.last(), AddedX + o, AddedY - o, addedZ).setColor(COLOR);
                    buffer.addVertex(poseStack.last(), prevAddedX + o, prevAddedY - o, addedZ + segmentDistance).setColor(COLOR);
                    buffer.addVertex(poseStack.last(), prevAddedX - o, prevAddedY - o, addedZ + segmentDistance).setColor(COLOR);

                    buffer.addVertex(poseStack.last(), AddedX - o, AddedY + o, addedZ).setColor(COLOR);
                    buffer.addVertex(poseStack.last(), AddedX - o, AddedY - o, addedZ).setColor(COLOR);
                    buffer.addVertex(poseStack.last(), prevAddedX - o, prevAddedY - o, addedZ + segmentDistance).setColor(COLOR);
                    buffer.addVertex(poseStack.last(), prevAddedX - o, prevAddedY + o, addedZ + segmentDistance).setColor(COLOR);

                    buffer.addVertex(poseStack.last(), AddedX + o, AddedY + o, addedZ).setColor(COLOR);
                    buffer.addVertex(poseStack.last(), AddedX - o, AddedY + o, addedZ).setColor(COLOR);
                    buffer.addVertex(poseStack.last(), prevAddedX - o, prevAddedY + o, addedZ + segmentDistance).setColor(COLOR);
                    buffer.addVertex(poseStack.last(), prevAddedX + o, prevAddedY + o, addedZ + segmentDistance).setColor(COLOR);
				}
			}

			poseStack.popPose();
		}
	}

    @Override
    public boolean shouldRender(EntityLightningLink livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    protected int getBlockLightLevel(EntityLightningLink entity, BlockPos pos) {
        return LightEngine.MAX_LEVEL;
    }


    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(EntityLightningLink p_entity, State reusedState, float partialTick) {
        super.extractRenderState(p_entity, reusedState, partialTick);
        reusedState.xRot = p_entity.getXRot(partialTick);
        reusedState.yRot = p_entity.getYRot(partialTick);
        reusedState.deltaMovement = p_entity.getDeltaMovement();
        reusedState.random = p_entity.getRandom();
    }

    public static class State extends EntityRenderState {
        public float xRot;
        public float yRot;
        public Vec3 deltaMovement;
        public RandomSource random;
    }
}
