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
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class RenderLightningLink extends EntityRenderer<EntityLightningLink>
{
	static float	red		= 0.65F;
	static float	green	= 0.75F;
	static float	blue	= 1F;

    public RenderLightningLink(EntityRendererFactory.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityLightningLink entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		float segmentDistance = RivalRebels.teslasegments;
		float distance = (float) entity.getVelocity().getX() * 100;
		distance = 100;

		// RenderLibrary.instance.renderModel((float) x, (float) y, (float) z,
		// (float) Math.sin(-entity.yaw / 180 * Math.PI) * distance,
		// (float) Math.sin(-entity.pitch / 180 * Math.PI) * distance,
		// (float) Math.cos(-entity.yaw / 180 * Math.PI) * distance,
		// 2f, 0.07f, 8, 5f, 0.5f, red, green, blue, 1);

		if (distance > 0)
		{
			Random random = entity.world.random;
			float radius = 0.07F;
            VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getSolid());

            matrices.push();
			RenderSystem.enableBlend();
            RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
			matrices.multiply(new Quaternion(entity.getYaw(), 0.0F, 1.0F, 0.0F));
			matrices.multiply(new Quaternion(-entity.getPitch(), 1.0F, 0.0F, 0.0F));

			double AddedX = 0;
			double AddedY = 0;
			double prevAddedX = 0;
			double prevAddedY = 0;
			for (int addedZ = (int) distance; addedZ >= 0; addedZ -= segmentDistance)
			{
				prevAddedX = AddedX;
				prevAddedY = AddedY;
				AddedX += (random.nextFloat() - 0.5) * 2;
				AddedY += (random.nextFloat() - 0.5) * 2;
				double dist = Math.sqrt(AddedX * AddedX + AddedY * AddedY) / 1.5;
				if (dist != 0)
				{
					double tempAddedX = AddedX / dist;
					double tempAddedY = AddedY / dist;
					if (Math.abs(tempAddedX) < Math.abs(AddedX)) AddedX = tempAddedX;
					if (Math.abs(tempAddedY) < Math.abs(AddedY)) AddedY = tempAddedY;
				}
				if (addedZ <= 0)
				{
					AddedX = AddedY = 0;
				}

				for (float o = 0; o <= radius; o += radius / 8) {
					buffer.vertex(AddedX + o, AddedY - o, addedZ).color(red, green, blue, 0.95f).next();
					buffer.vertex(AddedX + o, AddedY + o, addedZ).color(red, green, blue, 0.95f).next();
					buffer.vertex(prevAddedX + o, prevAddedY + o, addedZ + segmentDistance).color(red, green, blue, 0.95f).next();
					buffer.vertex(prevAddedX + o, prevAddedY - o, addedZ + segmentDistance).color(red, green, blue, 0.95f).next();
					buffer.vertex(AddedX - o, AddedY - o, addedZ).color(red, green, blue, 0.95f).next();
					buffer.vertex(prevAddedX - o, prevAddedY - o, addedZ + segmentDistance).color(red, green, blue, 0.95f).next();
					buffer.vertex(AddedX - o, AddedY + o, addedZ).color(red, green, blue, 0.95f).next();
					buffer.vertex(prevAddedX - o, prevAddedY + o, addedZ + segmentDistance).color(red, green, blue, 0.95f).next();
				}
			}

			RenderSystem.disableBlend();
			matrices.pop();
		}
	}

    @Override
    public Identifier getTexture(EntityLightningLink entity) {
        return null;
    }
}
