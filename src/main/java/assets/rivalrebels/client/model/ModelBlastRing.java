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

import assets.rivalrebels.client.renderhelper.RenderHelper;
import assets.rivalrebels.client.renderhelper.Vertice;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class ModelBlastRing extends ModelBase
{
    public void renderModel(float size, int segments, float thickness, float height, float pitch, float yaw, float roll, float x, float y, float z)
	{
		GlStateManager.pushMatrix();
		float innerangle = (float) Math.PI * 2 / segments;
		Vertice v1 = new Vertice(0, -height, size - thickness);
		Vertice v2 = new Vertice(0, -height, size + thickness);
		Vertice v3 = new Vertice((float) Math.sin(innerangle) * (size - thickness), -height, (float) Math.cos(innerangle) * (size - thickness));
		Vertice v4 = new Vertice((float) Math.sin(innerangle) * (size + thickness), -height, (float) Math.cos(innerangle) * (size + thickness));
		Vertice v5 = new Vertice(0, +height, size - thickness);
		Vertice v6 = new Vertice(0, +height, size + thickness);
		Vertice v7 = new Vertice((float) Math.sin(innerangle) * (size - thickness), +height, (float) Math.cos(innerangle) * (size - thickness));
		Vertice v8 = new Vertice((float) Math.sin(innerangle) * (size + thickness), +height, (float) Math.cos(innerangle) * (size + thickness));

		GlStateManager.translate(x, y, z);
		GlStateManager.rotate(pitch, 1, 0, 0);
		GlStateManager.rotate(yaw, 0, 1, 0);
		GlStateManager.rotate(roll, 0, 0, 1);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        for (float i = 0; i < 360; i += 360 / segments)
		{
			GlStateManager.pushMatrix();
			GlStateManager.rotate(i, 0, 1, 0);
			GlStateManager.disableTexture2D();
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
			RenderHelper.addFace(buffer, v5, v6, v8, v7);
			RenderHelper.addFace(buffer, v2, v1, v3, v4);
			RenderHelper.addFace(buffer, v2, v4, v8, v6);
			RenderHelper.addFace(buffer, v3, v1, v5, v7);
            tessellator.draw();
			GlStateManager.enableTexture2D();
			GlStateManager.popMatrix();
		}
		GlStateManager.popMatrix();
	}
}
