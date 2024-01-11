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

import assets.rivalrebels.client.renderhelper.RenderHelper;
import assets.rivalrebels.client.renderhelper.Vertice;

public class ModelAstroBlasterBody
{

	Vertice	vx	= new Vertice(1, 0, 0).normalize();
	Vertice	vy	= new Vertice(0, 1, 0).normalize();
	Vertice	vz	= new Vertice(0, 0, 1).normalize();
	Vertice	vxy	= new Vertice(0.5f, 0.5f, 0).normalize();
	Vertice	vyz	= new Vertice(0, 0.5f, 0.5f).normalize();
	Vertice	vxz	= new Vertice(0.5f, 0, 0.5f).normalize();
	Vertice	vx1	= new Vertice(0.75f, 0.25f, 0).normalize();
	Vertice	vx2	= new Vertice(0.5f, 0.25f, 0.25f).normalize();
	Vertice	vx3	= new Vertice(0.75f, 0, 0.25f).normalize();
	Vertice	vy1	= new Vertice(0, 0.75f, 0.25f).normalize();
	Vertice	vy2	= new Vertice(0.25f, 0.5f, 0.25f).normalize();
	Vertice	vy3	= new Vertice(0.25f, 0.75f, 0).normalize();
	Vertice	vz1	= new Vertice(0.25f, 0, 0.75f).normalize();
	Vertice	vz2	= new Vertice(0.25f, 0.25f, 0.5f).normalize();
	Vertice	vz3	= new Vertice(0, 0.25f, 0.75f).normalize();

	public void render(float size, float red, float green, float blue, float alpha)
	{
		GlStateManager.pushMatrix();
		GlStateManager.color(red, green, blue, alpha);

		GlStateManager.scale(size, size, size);
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        for (int p = 0; p < 4; p++)
		{
			GlStateManager.pushMatrix();
			GlStateManager.rotate(p * 90, 0, 1, 0);

            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
			RenderHelper.addTri(buffer, vy, vy1, vy3);
			RenderHelper.addTri(buffer, vy1, vyz, vy2);
			RenderHelper.addTri(buffer, vy3, vy2, vxy);
			RenderHelper.addTri(buffer, vy1, vy2, vy3);
			RenderHelper.addTri(buffer, vx, vx1, vx3);
			RenderHelper.addTri(buffer, vx1, vxy, vx2);
			RenderHelper.addTri(buffer, vx3, vx2, vxz);
			RenderHelper.addTri(buffer, vx1, vx2, vx3);
			RenderHelper.addTri(buffer, vz, vz1, vz3);
			RenderHelper.addTri(buffer, vz1, vxz, vz2);
			RenderHelper.addTri(buffer, vz3, vz2, vyz);
			RenderHelper.addTri(buffer, vz1, vz2, vz3);
			RenderHelper.addTri(buffer, vyz, vz2, vy2);
			RenderHelper.addTri(buffer, vxy, vy2, vx2);
			RenderHelper.addTri(buffer, vxz, vx2, vz2);
			RenderHelper.addTri(buffer, vx2, vy2, vz2);
            tessellator.draw();

			GlStateManager.popMatrix();
		}
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
		GlStateManager.popMatrix();
	}
}
