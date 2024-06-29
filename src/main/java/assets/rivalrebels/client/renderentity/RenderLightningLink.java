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
package assets.rivalrebels.client.renderentity;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.entity.EntityLightningLink;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

@Environment(EnvType.CLIENT)
public class RenderLightningLink extends EntityRenderer<EntityLightningLink>
{
	static float	red		= 0.65F;
	static float	green	= 0.75F;
	static float	blue	= 1F;

    public RenderLightningLink(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityLightningLink entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		float segmentDistance = RivalRebels.teslasegments;
		float distance = (float) entity.getDeltaMovement().x() * 100;
		distance = 100;

		// RenderLibrary.instance.renderModel((float) x, (float) y, (float) z,
		// (float) Math.sin(-entity.yaw / 180 * Math.PI) * distance,
		// (float) Math.sin(-entity.pitch / 180 * Math.PI) * distance,
		// (float) Math.cos(-entity.yaw / 180 * Math.PI) * distance,
		// 2f, 0.07f, 8, 5f, 0.5f, red, green, blue, 1);

		if (distance > 0)
		{
			RandomSource random = entity.level().random;
			float radius = 0.07F;
            VertexConsumer buffer = vertexConsumers.getBuffer(RenderType.lightning());

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
					if (Math.abs(tempAddedX) < Math.abs(AddedX)) AddedX = tempAddedX;
					if (Math.abs(tempAddedY) < Math.abs(AddedY)) AddedY = tempAddedY;
				}
				if (addedZ <= 0)
				{
					AddedX = AddedY = 0;
				}

				for (float o = 0; o <= radius; o += radius / 8) {
					buffer.addVertex(matrices.last(), AddedX + o, AddedY - o, addedZ).setColor(red, green, blue, 0.95f);
					buffer.addVertex(matrices.last(), AddedX + o, AddedY + o, addedZ).setColor(red, green, blue, 0.95f);
					buffer.addVertex(matrices.last(), prevAddedX + o, prevAddedY + o, addedZ + segmentDistance).setColor(red, green, blue, 0.95f);
					buffer.addVertex(matrices.last(), prevAddedX + o, prevAddedY - o, addedZ + segmentDistance).setColor(red, green, blue, 0.95f);

                    buffer.addVertex(matrices.last(), AddedX - o, AddedY - o, addedZ).setColor(red, green, blue, 0.95f);
					buffer.addVertex(matrices.last(), prevAddedX - o, prevAddedY - o, addedZ + segmentDistance).setColor(red, green, blue, 0.95f);
					buffer.addVertex(matrices.last(), AddedX - o, AddedY + o, addedZ).setColor(red, green, blue, 0.95f);
					buffer.addVertex(matrices.last(), prevAddedX - o, prevAddedY + o, addedZ + segmentDistance).setColor(red, green, blue, 0.95f);
				}
			}

			matrices.popPose();
		}
	}

    @Override
    public ResourceLocation getTextureLocation(EntityLightningLink entity) {
        return null;
    }
}
