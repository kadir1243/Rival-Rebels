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
import net.minecraft.client.renderer.Tessellator;
import assets.rivalrebels.client.renderhelper.RenderHelper;
import assets.rivalrebels.client.renderhelper.Vertice;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ModelJump
{
	float	s	= 0.501F;
	float	t	= 0.25F;

	Vertice	v1	= new Vertice(s, t, s);
	Vertice	v2	= new Vertice(s, t, -s);
	Vertice	v3	= new Vertice(-s, t, -s);
	Vertice	v4	= new Vertice(-s, t, s);

	Vertice	v5	= new Vertice(s, -t, s);
	Vertice	v6	= new Vertice(s, -t, -s);
	Vertice	v7	= new Vertice(-s, -t, -s);
	Vertice	v8	= new Vertice(-s, -t, s);

	public void renderModel()
	{
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		RenderHelper.addVertice(buffer, v2, 0, 0);
		RenderHelper.addVertice(buffer, v1, 1, 0);
		RenderHelper.addVertice(buffer, v4, 1, 1);
		RenderHelper.addVertice(buffer, v3, 0, 1);
		RenderHelper.addVertice(buffer, v5, 0, 0);
		RenderHelper.addVertice(buffer, v6, 1, 0);
		RenderHelper.addVertice(buffer, v7, 1, 1);
		RenderHelper.addVertice(buffer, v8, 0, 1);
		tessellator.draw();
	}
}
