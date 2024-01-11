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
package assets.rivalrebels.client.objfileloader;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

@SideOnly(Side.CLIENT)
public class Triangle
{
	public Vertice[]	pa;
	private final Tessellator tes	= Tessellator.getInstance();

	public Triangle(Vertice[] PA)
	{
		if (PA.length != 3) throw new IllegalArgumentException("Invalid Triangle! Specified Vec3d Array must have 3 Vec3s");
		pa = PA;
	}

	public void render()
	{
        BufferBuilder buffer = tes.getBuffer();
        buffer.begin(GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_COLOR);
        for (Vertice vertice : pa) {
            vertice.render(buffer);
        }
		tes.draw();
	}

	public void renderWireframe()
	{
        GlStateManager.glLineWidth(2);
        BufferBuilder buffer = tes.getBuffer();
        buffer.begin(GL_LINE_LOOP, DefaultVertexFormats.POSITION);
        for (Vertice vertice : pa) {
            vertice.renderWireframe(buffer);
        }
		tes.draw();
	}

	public void normalize()
	{
        for (Vertice vertice : pa) {
            vertice.normalize();
        }
	}

	public void scale(Vec3d v)
	{
        for (Vertice vertice : pa) {
            vertice.scale(v);
        }
	}

	public Triangle[] refine()
	{
		Triangle[] p = new Triangle[4];
		if (pa.length == 3)
		{
			Vertice mp1 = Vertice.average(pa[0], pa[1]);
			Vertice mp2 = Vertice.average(pa[1], pa[2]);
			Vertice mp3 = Vertice.average(pa[2], pa[0]);
			p[0] = new Triangle(new Vertice[] { pa[0].copy(), mp1.copy(), mp3.copy() });
			p[1] = new Triangle(new Vertice[] { pa[1].copy(), mp2.copy(), mp1.copy() });
			p[2] = new Triangle(new Vertice[] { pa[2].copy(), mp3.copy(), mp2.copy() });
			p[3] = new Triangle(new Vertice[] { mp1.copy(), mp2.copy(), mp3.copy() });
		}
		return p;
	}
}
