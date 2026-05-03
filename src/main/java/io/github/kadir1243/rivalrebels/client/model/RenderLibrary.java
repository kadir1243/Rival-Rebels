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
package io.github.kadir1243.rivalrebels.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

@OnlyIn(Dist.CLIENT)
public class RenderLibrary {

	public static void renderModel(PoseStack poseStack, SubmitNodeCollector nodeCollector, RenderType renderType, float x1, float y1, float z1, float x, float y, float z, float segDist, float radius, int steps, float arcRatio, float rvar, float r, float g, float b, float a) {
        RandomSource random = Minecraft.getInstance().level.getRandom();
        poseStack.pushPose();
		poseStack.translate(x1, y1, z1);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) (Math.atan2(x, z) * 57.295779513 - 90)));
		float dist = Mth.sqrt(x * x + z * z);
		float hdist = dist / 2f;
		float hdists = hdist * hdist;
		float rs = radius / steps;
		int segNum = Mth.ceil(dist / segDist) + 1;
		float[] xv = new float[segNum];
        float[] yv = new float[segNum];
        float[] zv = new float[segNum];
        int color = ARGB.colorFromFloat(a, r, g, b);

		for (int i = 1; i < segNum; i++) {
			float interp = (float) i / (float) segNum;
			float X = (dist * interp);
			float Y = (y * interp);
			float ihdist = (dist * interp) - hdist;

			Y += ((hdists - (ihdist * ihdist)) / hdists) * arcRatio;
			xv[i] = X;
			yv[i] = (float) (Y + random.nextGaussian() * rvar);
			zv[i] = (float) (random.nextGaussian() * rvar);
		}

		xv[0] = 0;
		yv[0] = 0;
		zv[0] = 0;

        for (int o = 0; o < steps; o++) {
			for (int i = 1; i < segNum; i++) {
                float s = rs * o;
                int finalI = i;
                nodeCollector.submitCustomGeometry(poseStack, renderType, (pose, consumer) -> {
                    consumer.addVertex(pose, xv[finalI - 1], yv[finalI - 1] + s, zv[finalI - 1] - s).setColor(color);
                    consumer.addVertex(pose, xv[finalI - 1], yv[finalI - 1] + s, zv[finalI - 1] + s).setColor(color);
                    consumer.addVertex(pose, xv[finalI], yv[finalI] + s, zv[finalI] + s).setColor(color);
                    consumer.addVertex(pose, xv[finalI], yv[finalI] + s, zv[finalI] - s).setColor(color);

                    consumer.addVertex(pose, xv[finalI - 1], yv[finalI - 1] + s, zv[finalI - 1] + s).setColor(color);
                    consumer.addVertex(pose, xv[finalI - 1], yv[finalI - 1] - s, zv[finalI - 1] + s).setColor(color);
                    consumer.addVertex(pose, xv[finalI], yv[finalI] - s, zv[finalI] + s).setColor(color);
                    consumer.addVertex(pose, xv[finalI], yv[finalI] + s, zv[finalI] + s).setColor(color);

                    consumer.addVertex(pose, xv[finalI - 1], yv[finalI - 1] - s, zv[finalI - 1] - s).setColor(color);
                    consumer.addVertex(pose, xv[finalI - 1], yv[finalI - 1] + s, zv[finalI - 1] - s).setColor(color);
                    consumer.addVertex(pose, xv[finalI], yv[finalI] + s, zv[finalI] - s).setColor(color);
                    consumer.addVertex(pose, xv[finalI], yv[finalI] - s, zv[finalI] - s).setColor(color);

                    consumer.addVertex(pose, xv[finalI - 1], yv[finalI - 1] - s, zv[finalI - 1] + s).setColor(color);
                    consumer.addVertex(pose, xv[finalI - 1], yv[finalI - 1] - s, zv[finalI - 1] - s).setColor(color);
                    consumer.addVertex(pose, xv[finalI], yv[finalI] - s, zv[finalI] - s).setColor(color);
                    consumer.addVertex(pose, xv[finalI], yv[finalI] - s, zv[finalI] + s).setColor(color);
                });
			}
		}

		poseStack.popPose();
	}
}
