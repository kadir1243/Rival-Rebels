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

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class RenderLibrary
{
	Tessellator					t		= Tessellator.getInstance();
	Random						random	= new Random();
	public static RenderLibrary	instance;

	public RenderLibrary()
	{

	}

	public void init()
	{
		instance = this;
	}

	public void renderModel(float x1, float y1, float z1, float x, float y, float z, float segDist, float radius, int steps, float arcRatio, float rvar, float r, float g, float b, float a)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x1, y1, z1);
		GlStateManager.enableRescaleNormal();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
		GlStateManager.rotate((float) (Math.atan2(x, z) * 57.295779513 - 90), 0, 1, 0);
		GlStateManager.color(r, g, b, a);
		double dist = Math.sqrt(x * x + z * z);
		double hdist = dist / 2f;
		double hdists = hdist * hdist;
		double rs = radius / steps;
		int segNum = (int) Math.ceil(dist / segDist) + 1;
		double[] xv = new double[segNum];
		double[] yv = new double[segNum];
		double[] zv = new double[segNum];

		for (int i = 1; i < segNum; i++)
		{
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

        BufferBuilder buffer = t.getBuffer();
        for (int o = 0; o < steps; o++)
		{
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
			for (int i = 1; i < segNum; i++)
			{
				double s = rs * o;
				buffer.pos(xv[i - 1], yv[i - 1] + s, zv[i - 1] - s).endVertex();
				buffer.pos(xv[i - 1], yv[i - 1] + s, zv[i - 1] + s).endVertex();
				buffer.pos(xv[i], yv[i] + s, zv[i] + s).endVertex();
				buffer.pos(xv[i], yv[i] + s, zv[i] - s).endVertex();

				buffer.pos(xv[i - 1], yv[i - 1] + s, zv[i - 1] + s).endVertex();
				buffer.pos(xv[i - 1], yv[i - 1] - s, zv[i - 1] + s).endVertex();
				buffer.pos(xv[i], yv[i] - s, zv[i] + s).endVertex();
				buffer.pos(xv[i], yv[i] + s, zv[i] + s).endVertex();

				buffer.pos(xv[i - 1], yv[i - 1] - s, zv[i - 1] - s).endVertex();
				buffer.pos(xv[i - 1], yv[i - 1] + s, zv[i - 1] - s).endVertex();
				buffer.pos(xv[i], yv[i] + s, zv[i] - s).endVertex();
				buffer.pos(xv[i], yv[i] - s, zv[i] - s).endVertex();

				buffer.pos(xv[i - 1], yv[i - 1] - s, zv[i - 1] + s).endVertex();
				buffer.pos(xv[i - 1], yv[i - 1] - s, zv[i - 1] - s).endVertex();
				buffer.pos(xv[i], yv[i] - s, zv[i] - s).endVertex();
				buffer.pos(xv[i], yv[i] - s, zv[i] + s).endVertex();
			}
			t.draw();
		}

		GlStateManager.disableBlend();
		GlStateManager.enableTexture2D();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}
}
