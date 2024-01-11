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

import assets.rivalrebels.client.renderhelper.Vertice;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSteel
{
	Tessellator	tessellator	= Tessellator.getInstance();
	float		s			= 0.5F;

	Vertice		v1			= new Vertice(s, s, s);
	Vertice		v2			= new Vertice(s, s, -s);
	Vertice		v3			= new Vertice(-s, s, -s);
	Vertice		v4			= new Vertice(-s, s, s);

	Vertice		v5			= new Vertice(s, -s, s);
	Vertice		v6			= new Vertice(s, -s, -s);
	Vertice		v7			= new Vertice(-s, -s, -s);
	Vertice		v8			= new Vertice(-s, -s, s);

	public void renderModel()
	{
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		addVertex(buffer, v1, 0, 0);
		addVertex(buffer, v5, 1, 0);
		addVertex(buffer, v8, 1, 1);
		addVertex(buffer, v4, 0, 1);
		addVertex(buffer, v4, 0, 0);
		addVertex(buffer, v8, 1, 0);
		addVertex(buffer, v7, 1, 1);
		addVertex(buffer, v3, 0, 1);
		addVertex(buffer, v3, 0, 0);
		addVertex(buffer, v7, 1, 0);
		addVertex(buffer, v6, 1, 1);
		addVertex(buffer, v2, 0, 1);
		addVertex(buffer, v2, 0, 0);
		addVertex(buffer, v6, 1, 0);
		addVertex(buffer, v5, 1, 1);
		addVertex(buffer, v1, 0, 1);
		addVertex(buffer, v3, 1, 0);
		addVertex(buffer, v2, 1, 1);
		addVertex(buffer, v5, 0, 0);
		addVertex(buffer, v8, 0, 1);
		tessellator.draw();

		GlStateManager.popMatrix();
	}

	private void addVertex(BufferBuilder buffer, Vertice v, double t, double t2) {
		buffer.pos(v.x * 0.999, v.y * 0.999, v.z * 0.999).tex(t, t2).endVertex();
	}
}
