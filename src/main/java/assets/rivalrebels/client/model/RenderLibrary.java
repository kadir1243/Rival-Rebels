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
package assets.rivalrebels.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class RenderLibrary {

	public static void renderModel(PoseStack matrices, MultiBufferSource vertexConsumers, float x1, float y1, float z1, float x, float y, float z, float segDist, float radius, int steps, float arcRatio, float rvar, float r, float g, float b, float a) {
        RandomSource random = Minecraft.getInstance().level.random;
        matrices.pushPose();
		matrices.translate(x1, y1, z1);
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderType.lightning());
        matrices.mulPose(Axis.YP.rotationDegrees((float) (Math.atan2(x, z) * 57.295779513 - 90)));
		float dist = Mth.sqrt(x * x + z * z);
		float hdist = dist / 2f;
		float hdists = hdist * hdist;
		float rs = radius / steps;
		int segNum = Mth.ceil(dist / segDist) + 1;
		float[] xv = new float[segNum];
        float[] yv = new float[segNum];
        float[] zv = new float[segNum];
        int color = FastColor.ARGB32.colorFromFloat(a, r, g, b);

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
				buffer.addVertex(matrices.last(), xv[i - 1], yv[i - 1] + s, zv[i - 1] - s).setColor(color);
				buffer.addVertex(matrices.last(), xv[i - 1], yv[i - 1] + s, zv[i - 1] + s).setColor(color);
				buffer.addVertex(matrices.last(), xv[i], yv[i] + s, zv[i] + s).setColor(color);
				buffer.addVertex(matrices.last(), xv[i], yv[i] + s, zv[i] - s).setColor(color);

				buffer.addVertex(matrices.last(), xv[i - 1], yv[i - 1] + s, zv[i - 1] + s).setColor(color);
				buffer.addVertex(matrices.last(), xv[i - 1], yv[i - 1] - s, zv[i - 1] + s).setColor(color);
				buffer.addVertex(matrices.last(), xv[i], yv[i] - s, zv[i] + s).setColor(color);
				buffer.addVertex(matrices.last(), xv[i], yv[i] + s, zv[i] + s).setColor(color);

				buffer.addVertex(matrices.last(), xv[i - 1], yv[i - 1] - s, zv[i - 1] - s).setColor(color);
				buffer.addVertex(matrices.last(), xv[i - 1], yv[i - 1] + s, zv[i - 1] - s).setColor(color);
				buffer.addVertex(matrices.last(), xv[i], yv[i] + s, zv[i] - s).setColor(color);
				buffer.addVertex(matrices.last(), xv[i], yv[i] - s, zv[i] - s).setColor(color);

				buffer.addVertex(matrices.last(), xv[i - 1], yv[i - 1] - s, zv[i - 1] + s).setColor(color);
				buffer.addVertex(matrices.last(), xv[i - 1], yv[i - 1] - s, zv[i - 1] - s).setColor(color);
				buffer.addVertex(matrices.last(), xv[i], yv[i] - s, zv[i] - s).setColor(color);
				buffer.addVertex(matrices.last(), xv[i], yv[i] - s, zv[i] + s).setColor(color);
			}
		}

		matrices.popPose();
	}
}
