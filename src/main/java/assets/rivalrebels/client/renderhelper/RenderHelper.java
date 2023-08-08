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
package assets.rivalrebels.client.renderhelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class RenderHelper
{
	public static void renderBox(float length, float height, float depth, float texLocX, float texLocY, float texXsize, float texYsize, float resolution)
	{
		GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
		texLocX /= texXsize;
		texLocY /= texYsize;
		float hl = (length / 2f) / resolution;
		float hh = (height / 2f) / resolution;
		float hd = (depth / 2f) / resolution;
		float xtd = (length / texXsize);// * resolution;
		// float xth = (height / texXsize);// * resolution;
		float xtl = (depth / texXsize);// * resolution;
		float ytd = (length / texYsize);// * resolution;
		float yth = (height / texYsize);// * resolution;
		// float ytl = (depth / texYsize) ;//* resolution;
		Vertice xpypzp = new Vertice(hl, hh, hd);
		Vertice xnypzp = new Vertice(-hl, hh, hd);
		Vertice xnypzn = new Vertice(-hl, hh, -hd);
		Vertice xpypzn = new Vertice(hl, hh, -hd);
		Vertice xpynzp = new Vertice(hl, -hh, hd);
		Vertice xnynzp = new Vertice(-hl, -hh, hd);
		Vertice xnynzn = new Vertice(-hl, -hh, -hd);
		Vertice xpynzn = new Vertice(hl, -hh, -hd);
		TextureVertice t1 = new TextureVertice(texLocX + xtd, texLocY + 0);
		TextureVertice t2 = new TextureVertice(texLocX + xtd + xtl, texLocY + 0);
		TextureVertice t3 = new TextureVertice(texLocX + xtd + xtl + xtl, texLocY + 0);
		TextureVertice t4 = new TextureVertice(texLocX + 0, texLocY + ytd);
		TextureVertice t5 = new TextureVertice(texLocX + xtd, texLocY + ytd);
		TextureVertice t6 = new TextureVertice(texLocX + xtd + xtl, texLocY + ytd);
		TextureVertice t7 = new TextureVertice(texLocX + xtd + xtl + xtd, texLocY + ytd);
		TextureVertice t8 = new TextureVertice(texLocX + xtd + xtl + xtl, texLocY + ytd);
		TextureVertice t9 = new TextureVertice(texLocX + xtd + xtl + xtl + xtd, texLocY + ytd);
		TextureVertice t10 = new TextureVertice(texLocX + 0, texLocY + ytd + yth);
		TextureVertice t11 = new TextureVertice(texLocX + xtd, texLocY + ytd + yth);
		TextureVertice t12 = new TextureVertice(texLocX + xtd + xtl, texLocY + ytd + yth);
		TextureVertice t13 = new TextureVertice(texLocX + xtd + xtl + xtd, texLocY + ytd + yth);
		TextureVertice t14 = new TextureVertice(texLocX + xtd + xtl + xtl + xtd, texLocY + ytd + yth);
		addFace(xpypzn, xnypzn, xnypzp, xpypzp, t6, t2, t1, t5); // top
		addFace(xpynzp, xnynzp, xnynzn, xpynzn, t6, t2, t3, t8); // bottom
		addFace(xnypzp, xnynzp, xpynzp, xpypzp, t4, t10, t11, t5); // right
		addFace(xpypzp, xpynzp, xpynzn, xpypzn, t5, t11, t12, t6); // front
		addFace(xpypzn, xpynzn, xnynzn, xnypzn, t6, t12, t13, t7); // left
		addFace(xnypzn, xnynzn, xnynzp, xnypzp, t7, t13, t14, t9); // back
	}

	public static void drawPoint(Vertice v1, float size)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef(v1.x, v1.y, v1.z);
        GlStateManager.enableRescaleNormal();
		size /= 2;
		Tessellator t = Tessellator.getInstance();
		GL11.glRotatef(180 - Minecraft.getMinecraft().thePlayer.rotationYaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(90 - Minecraft.getMinecraft().thePlayer.rotationPitch, 1.0F, 0.0F, 0.0F);
        WorldRenderer worldRenderer = t.getWorldRenderer();
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.putNormal(0.0F, 1.0F, 0.0F);
		worldRenderer.pos(-size, 0, -size).tex(0, 0).endVertex();
		worldRenderer.pos(size, 0, -size).tex(1, 0).endVertex();
		worldRenderer.pos(size, 0, size).tex(1, 1).endVertex();
		worldRenderer.pos(-size, 0, size).tex(0, 1).endVertex();
		t.draw();
        GlStateManager.disableRescaleNormal();
		GL11.glPopMatrix();
	}

	public static void drawLine(Vertice v1, float size1, Vertice v2, float size2)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef(v1.x, v1.y, v1.z);
        GlStateManager.enableRescaleNormal();
		size1 /= 2;
		size2 /= 2;
		Tessellator t = Tessellator.getInstance();
        WorldRenderer worldRenderer = t.getWorldRenderer();
        GL11.glRotatef(180 - Minecraft.getMinecraft().thePlayer.rotationYaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(90 - Minecraft.getMinecraft().thePlayer.rotationPitch, 1.0F, 0.0F, 0.0F);
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.putNormal(0.0F, 1.0F, 0.0F);
		worldRenderer.pos(-size1, 0, -size1).tex(0, 0).endVertex();
		worldRenderer.pos(size1, 0, -size1).tex(1, 0).endVertex();
		worldRenderer.pos(size1, 0, size1).tex(1, 1).endVertex();
		worldRenderer.pos(-size1, 0, size1).tex(0, 1).endVertex();
		t.draw();
        GlStateManager.disableRescaleNormal();
		GL11.glPopMatrix();
	}

	public static void renderBox(float length, float height, float depth)
	{
		GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
		float hl = (length / 2f);
		float hh = (height / 2f);
		float hd = (depth / 2f);
		Vertice xpypzp = new Vertice(hl, hh, hd);
		Vertice xnypzp = new Vertice(-hl, hh, hd);
		Vertice xnypzn = new Vertice(-hl, hh, -hd);
		Vertice xpypzn = new Vertice(hl, hh, -hd);
		Vertice xpynzp = new Vertice(hl, -hh, hd);
		Vertice xnynzp = new Vertice(-hl, -hh, hd);
		Vertice xnynzn = new Vertice(-hl, -hh, -hd);
		Vertice xpynzn = new Vertice(hl, -hh, -hd);
		addFace(xpypzn, xnypzn, xnypzp, xpypzp); // top
		addFace(xpynzp, xnynzp, xnynzn, xpynzn); // bottom
		addFace(xnypzp, xnynzp, xpynzp, xpypzp); // right
		addFace(xpypzp, xpynzp, xpynzn, xpypzn); // front
		addFace(xpypzn, xpynzn, xnynzn, xnypzn); // left
		addFace(xnypzn, xnynzn, xnynzp, xnypzp); // back
	}

	public static void addFace(Vertice v1, Vertice v2, Vertice v3, Vertice v4, TextureVertice t1, TextureVertice t2, TextureVertice t3, TextureVertice t4)
	{
		Tessellator t = Tessellator.getInstance();
        WorldRenderer worldRenderer = t.getWorldRenderer();
        Vertice mv = new Vertice((v1.x + v2.x + v3.x + v4.x) / 4, (v1.y + v2.y + v3.y + v4.y) / 4, (v1.z + v2.z + v3.z + v4.z) / 4);
		TextureVertice mt = new TextureVertice((t1.x + t2.x + t3.x + t4.x) / 4, (t1.y + t2.y + t3.y + t4.y) / 4);
		worldRenderer.begin(GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);
		addVertice(worldRenderer, v1, t1);
		addVertice(worldRenderer, v2, t2);
		addVertice(worldRenderer, mv, mt);
		t.draw();
		worldRenderer.begin(GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);
		addVertice(worldRenderer, v2, t2);
		addVertice(worldRenderer, v3, t3);
		addVertice(worldRenderer, mv, mt);
		t.draw();
		worldRenderer.begin(GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);
		addVertice(worldRenderer, v3, t3);
		addVertice(worldRenderer, v4, t4);
		addVertice(worldRenderer, mv, mt);
		t.draw();
		worldRenderer.begin(GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);
		addVertice(worldRenderer, v4, t4);
		addVertice(worldRenderer, v1, t1);
		addVertice(worldRenderer, mv, mt);
		t.draw();
	}

	public static void addFace(Vertice v1, Vertice v2, Vertice v3, Vertice v4)
	{
		Tessellator t = Tessellator.getInstance();
        WorldRenderer worldRenderer = t.getWorldRenderer();
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION);
		addVertice(worldRenderer, v1);
		addVertice(worldRenderer, v2);
		addVertice(worldRenderer, v3);
		addVertice(worldRenderer, v4);
		t.draw();
	}

	public static void addFace(Vertice v1, Vertice v2, Vertice v3, Vertice v4, TextureFace t)
	{
		Tessellator tess = Tessellator.getInstance();
        WorldRenderer worldRenderer = tess.getWorldRenderer();
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		addVertice(worldRenderer, v1, t.getV1());
		addVertice(worldRenderer, v2, t.getV2());
		addVertice(worldRenderer, v3, t.getV3());
		addVertice(worldRenderer, v4, t.getV4());
		tess.draw();
	}

	public static void addFace(Vertice v1, Vertice v2, Vertice v3, Vertice v4, float par5, float par6, float par7, float par8)
	{
		Tessellator t = Tessellator.getInstance();
        WorldRenderer worldRenderer = t.getWorldRenderer();
        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		addVertice(worldRenderer, v1, par5, par8);
		addVertice(worldRenderer, v2, par6, par8);
		addVertice(worldRenderer, v3, par6, par7);
		addVertice(worldRenderer, v4, par5, par7);
		t.draw();
	}

	public static void addTri(Vertice v1, Vertice v2, Vertice v3)
	{
		Tessellator t = Tessellator.getInstance();
        WorldRenderer worldRenderer = t.getWorldRenderer();
        worldRenderer.begin(GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
		addVertice(worldRenderer, v3);
		addVertice(worldRenderer, v1);
		addVertice(worldRenderer, v2);
		t.draw();
	}

	public static void addVertice(WorldRenderer worldRenderer, Vertice v, TextureVertice t) {
		worldRenderer.pos(v.x, v.y, v.z).tex(t.x, t.y).endVertex();
	}

    public static void addVertice(WorldRenderer worldRenderer, Vertice v, float x, float y) {
        worldRenderer.pos(v.x, v.y, v.z).tex(x, y).endVertex();
    }

    public static void addVertice(WorldRenderer worldRenderer, Vertice v) {
        worldRenderer.pos(v.x, v.y, v.z).endVertex();
    }
}
