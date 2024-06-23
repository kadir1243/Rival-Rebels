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

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.random.Random;
import org.joml.Quaternionf;

public class RenderLibrary {

	public static void renderModel(MatrixStack matrices, VertexConsumerProvider vertexConsumers, float x1, float y1, float z1, float x, float y, float z, float segDist, float radius, int steps, float arcRatio, float rvar, float r, float g, float b, float a) {
        Random random = MinecraftClient.getInstance().world.random;
        matrices.push();
		matrices.translate(x1, y1, z1);
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getLightning());
        matrices.multiply(new Quaternionf((float) (Math.atan2(x, z) * 57.295779513 - 90), 0, 1, 0));
		double dist = Math.sqrt(x * x + z * z);
		double hdist = dist / 2f;
		double hdists = hdist * hdist;
		double rs = radius / steps;
		int segNum = (int) Math.ceil(dist / segDist) + 1;
		double[] xv = new double[segNum];
		double[] yv = new double[segNum];
		double[] zv = new double[segNum];

		for (int i = 1; i < segNum; i++) {
			double interp = (double) i / (double) segNum;
			double X = (dist * interp);
			double Y = (y * interp);
			double ihdist = (dist * interp) - hdist;

			Y += ((hdists - (ihdist * ihdist)) / hdists) * arcRatio;
			xv[i] = X;
			yv[i] = Y + random.nextGaussian() * rvar;
			zv[i] = random.nextGaussian() * rvar;
		}

		xv[0] = 0;
		yv[0] = 0;
		zv[0] = 0;

        for (int o = 0; o < steps; o++) {
			for (int i = 1; i < segNum; i++) {
				double s = rs * o;
				buffer.vertex(xv[i - 1], yv[i - 1] + s, zv[i - 1] - s).color(r, g, b, a).next();
				buffer.vertex(xv[i - 1], yv[i - 1] + s, zv[i - 1] + s).color(r, g, b, a).next();
				buffer.vertex(xv[i], yv[i] + s, zv[i] + s).color(r, g, b, a).next();
				buffer.vertex(xv[i], yv[i] + s, zv[i] - s).color(r, g, b, a).next();

				buffer.vertex(xv[i - 1], yv[i - 1] + s, zv[i - 1] + s).color(r, g, b, a).next();
				buffer.vertex(xv[i - 1], yv[i - 1] - s, zv[i - 1] + s).color(r, g, b, a).next();
				buffer.vertex(xv[i], yv[i] - s, zv[i] + s).color(r, g, b, a).next();
				buffer.vertex(xv[i], yv[i] + s, zv[i] + s).color(r, g, b, a).next();

				buffer.vertex(xv[i - 1], yv[i - 1] - s, zv[i - 1] - s).color(r, g, b, a).next();
				buffer.vertex(xv[i - 1], yv[i - 1] + s, zv[i - 1] - s).color(r, g, b, a).next();
				buffer.vertex(xv[i], yv[i] + s, zv[i] - s).color(r, g, b, a).next();
				buffer.vertex(xv[i], yv[i] - s, zv[i] - s).color(r, g, b, a).next();

				buffer.vertex(xv[i - 1], yv[i - 1] - s, zv[i - 1] + s).color(r, g, b, a).next();
				buffer.vertex(xv[i - 1], yv[i - 1] - s, zv[i - 1] - s).color(r, g, b, a).next();
				buffer.vertex(xv[i], yv[i] - s, zv[i] - s).color(r, g, b, a).next();
				buffer.vertex(xv[i], yv[i] - s, zv[i] + s).color(r, g, b, a).next();
			}
		}

		matrices.pop();
	}
}
