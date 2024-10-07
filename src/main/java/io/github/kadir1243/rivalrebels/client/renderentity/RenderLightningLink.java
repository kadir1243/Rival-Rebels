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
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.entity.EntityLightningLink;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.lighting.LightEngine;

@OnlyIn(Dist.CLIENT)
public class RenderLightningLink extends EntityRenderer<EntityLightningLink> {
    private static final RenderType RENDER_TYPE = RenderType.create(
        RRIdentifiers.MODID + "_lightning_link",
        DefaultVertexFormat.POSITION_COLOR,
        VertexFormat.Mode.QUADS,
        1536,
        false,
        true,
        RenderType.CompositeState.builder()
            .setShaderState(RenderStateShard.RENDERTYPE_LIGHTNING_SHADER)
            .setTransparencyState(RenderStateShard.LIGHTNING_TRANSPARENCY)
            .createCompositeState(false)
    );
    static float	red		= 0.65F;
	static float	green	= 0.75F;
	static float	blue	= 1F;
    private static final int COLOR = FastColor.ARGB32.colorFromFloat(0.95F, red, green, blue);

    public RenderLightningLink(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityLightningLink entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		float segmentDistance = RRConfig.CLIENT.getTeslaSegments();
		float distance = (float) entity.getDeltaMovement().x() * 100;
		distance = 100;

		// RenderLibrary.instance.renderModel((float) x, (float) y, (float) z,
		// (float) Math.sin(-entity.yaw / 180 * Math.PI) * distance,
		// (float) Math.sin(-entity.pitch / 180 * Math.PI) * distance,
		// (float) Math.cos(-entity.yaw / 180 * Math.PI) * distance,
		// 2f, 0.07f, 8, 5f, 0.5f, red, green, blue, 1);

		if (distance > 0)
		{
			RandomSource random = entity.getRandom();
			float radius = 0.07F;
            VertexConsumer buffer = vertexConsumers.getBuffer(RENDER_TYPE);

            matrices.pushPose();
			matrices.mulPose(Axis.YP.rotationDegrees(entity.getYRot()));
			matrices.mulPose(Axis.XP.rotationDegrees(-entity.getXRot()));

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
                    buffer.addVertex(matrices.last(), AddedX + o, AddedY - o, addedZ).setColor(COLOR);
                    buffer.addVertex(matrices.last(), AddedX + o, AddedY + o, addedZ).setColor(COLOR);
                    buffer.addVertex(matrices.last(), prevAddedX + o, prevAddedY + o, addedZ + segmentDistance).setColor(COLOR);
                    buffer.addVertex(matrices.last(), prevAddedX + o, prevAddedY - o, addedZ + segmentDistance).setColor(COLOR);

                    buffer.addVertex(matrices.last(), AddedX - o, AddedY - o, addedZ).setColor(COLOR);
                    buffer.addVertex(matrices.last(), AddedX + o, AddedY - o, addedZ).setColor(COLOR);
                    buffer.addVertex(matrices.last(), prevAddedX + o, prevAddedY - o, addedZ + segmentDistance).setColor(COLOR);
                    buffer.addVertex(matrices.last(), prevAddedX - o, prevAddedY - o, addedZ + segmentDistance).setColor(COLOR);

                    buffer.addVertex(matrices.last(), AddedX - o, AddedY + o, addedZ).setColor(COLOR);
                    buffer.addVertex(matrices.last(), AddedX - o, AddedY - o, addedZ).setColor(COLOR);
                    buffer.addVertex(matrices.last(), prevAddedX - o, prevAddedY - o, addedZ + segmentDistance).setColor(COLOR);
                    buffer.addVertex(matrices.last(), prevAddedX - o, prevAddedY + o, addedZ + segmentDistance).setColor(COLOR);

                    buffer.addVertex(matrices.last(), AddedX + o, AddedY + o, addedZ).setColor(COLOR);
                    buffer.addVertex(matrices.last(), AddedX - o, AddedY + o, addedZ).setColor(COLOR);
                    buffer.addVertex(matrices.last(), prevAddedX - o, prevAddedY + o, addedZ + segmentDistance).setColor(COLOR);
                    buffer.addVertex(matrices.last(), prevAddedX + o, prevAddedY + o, addedZ + segmentDistance).setColor(COLOR);
				}
			}

			matrices.popPose();
		}
	}

    @Override
    public ResourceLocation getTextureLocation(EntityLightningLink entity) {
        return null;
    }

    @Override
    public boolean shouldRender(EntityLightningLink livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    protected int getBlockLightLevel(EntityLightningLink entity, BlockPos pos) {
        return LightEngine.MAX_LEVEL;
    }
}
