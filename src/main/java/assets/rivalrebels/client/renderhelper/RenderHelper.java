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
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class RenderHelper
{
	public static void renderBox(float length, float height, float depth, float texLocX, float texLocY, float texXsize, float texYsize, float resolution)
	{
		GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
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
		GlStateManager.pushMatrix();
		GlStateManager.translate(v1.x, v1.y, v1.z);
		GlStateManager.enableRescaleNormal();
		size /= 2;
		Tessellator t = Tessellator.getInstance();
        BufferBuilder buffer = t.getBuffer();
        GlStateManager.rotate(180 - Minecraft.getMinecraft().player.rotationYaw, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(90 - Minecraft.getMinecraft().player.rotationPitch, 1.0F, 0.0F, 0.0F);
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);

		buffer.pos(-size, 0, -size).tex(0, 0).normal(0, 1, 0).endVertex();
		buffer.pos(size, 0, -size).tex(1, 0).normal(0, 1, 0).endVertex();
		buffer.pos(size, 0, size).tex(1, 1).normal(0, 1, 0).endVertex();
		buffer.pos(-size, 0, size).tex(0, 1).normal(0, 1, 0).endVertex();
		t.draw();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}

	public static void drawLine(Vertice v1, float size1, Vertice v2, float size2)
	{
        GlStateManager.pushMatrix();
		GlStateManager.translate(v1.x, v1.y, v1.z);
        GlStateManager.enableRescaleNormal();
		size1 /= 2;
		size2 /= 2;
		Tessellator t = Tessellator.getInstance();
        BufferBuilder buffer = t.getBuffer();
        GlStateManager.rotate(180 - Minecraft.getMinecraft().player.rotationYaw, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(90 - Minecraft.getMinecraft().player.rotationPitch, 1.0F, 0.0F, 0.0F);
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

		buffer.pos(-size1, 0, -size1).tex(0, 0).normal(0, 1, 0).endVertex();
		buffer.pos(size1, 0, -size1).tex(1, 0).normal(0, 1, 0).endVertex();
		buffer.pos(size1, 0, size1).tex(1, 1).normal(0, 1, 0).endVertex();
		buffer.pos(-size1, 0, size1).tex(0, 1).normal(0, 1, 0).endVertex();
		t.draw();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
	}

	public static void renderBox(float length, float height, float depth)
	{
		GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
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
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        addFace(buffer, xpypzn, xnypzn, xnypzp, xpypzp); // top
		addFace(buffer, xpynzp, xnynzp, xnynzn, xpynzn); // bottom
		addFace(buffer, xnypzp, xnynzp, xpynzp, xpypzp); // right
		addFace(buffer, xpypzp, xpynzp, xpynzn, xpypzn); // front
		addFace(buffer, xpypzn, xpynzn, xnynzn, xnypzn); // left
		addFace(buffer, xnypzn, xnynzn, xnynzp, xnypzp); // back
        tessellator.draw();
    }

	public static void addFace(Vertice v1, Vertice v2, Vertice v3, Vertice v4, TextureVertice t1, TextureVertice t2, TextureVertice t3, TextureVertice t4)
	{
		Tessellator t = Tessellator.getInstance();
        BufferBuilder buffer = t.getBuffer();
        Vertice mv = new Vertice((v1.x + v2.x + v3.x + v4.x) / 4, (v1.y + v2.y + v3.y + v4.y) / 4, (v1.z + v2.z + v3.z + v4.z) / 4);
		TextureVertice mt = new TextureVertice((t1.x + t2.x + t3.x + t4.x) / 4, (t1.y + t2.y + t3.y + t4.y) / 4);
		buffer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);
		addVertice(buffer, v1, t1);
		addVertice(buffer, v2, t2);
        addVertice(buffer, v3, t3);
        addVertice(buffer, mv, mt);
		t.draw();
	}

    public static void addFace(BufferBuilder buffer, Vertice v1, Vertice v2, Vertice v3, Vertice v4) {
        addVertice(buffer, v1);
        addVertice(buffer, v2);
        addVertice(buffer, v3);
        addVertice(buffer, v4);
    }

    /**
     * @deprecated Use {@link #addFace(BufferBuilder, Vertice, Vertice, Vertice, Vertice) with buffer version} because it makes it easier to optimize also it is better to draw everything in one buffer
     */
    @Deprecated
    public static void addFace(Vertice v1, Vertice v2, Vertice v3, Vertice v4)
	{
		Tessellator t = Tessellator.getInstance();
        BufferBuilder buffer = t.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		addVertice(buffer, v1);
		addVertice(buffer, v2);
		addVertice(buffer, v3);
		addVertice(buffer, v4);
		t.draw();
	}

    public static void addFace(BufferBuilder buffer, Vertice v1, Vertice v2, Vertice v3, Vertice v4, TextureFace t) {
        addVertice(buffer, v1, t.v1);
        addVertice(buffer, v2, t.v2);
        addVertice(buffer, v3, t.v3);
        addVertice(buffer, v4, t.v4);
    }

	public static void addFace(Vertice v1, Vertice v2, Vertice v3, Vertice v4, TextureFace t)
	{
		Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		addVertice(buffer, v1, t.v1);
		addVertice(buffer, v2, t.v2);
		addVertice(buffer, v3, t.v3);
		addVertice(buffer, v4, t.v4);
		tess.draw();
	}

	public static void addFace(Vertice v1, Vertice v2, Vertice v3, Vertice v4, float par5, float par6, float par7, float par8)
	{
		Tessellator t = Tessellator.getInstance();
        BufferBuilder buffer = t.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		addVertice(buffer, v1, par5, par8);
		addVertice(buffer, v2, par6, par8);
		addVertice(buffer, v3, par6, par7);
		addVertice(buffer, v4, par5, par7);
		t.draw();
	}

	public static void addTri(Vertice v1, Vertice v2, Vertice v3) {
		Tessellator t = Tessellator.getInstance();
        BufferBuilder buffer = t.getBuffer();
        buffer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
		addVertice(buffer, v3);
		addVertice(buffer, v1);
		addVertice(buffer, v2);
		t.draw();
	}

    public static void addTri(BufferBuilder buffer, Vertice v1, Vertice v2, Vertice v3) {
        addVertice(buffer, v3);
        addVertice(buffer, v1);
        addVertice(buffer, v2);
    }

	public static void addVertice(BufferBuilder buffer, Vertice v, TextureVertice t) {
        buffer.pos(v.x, v.y, v.z).tex(t.x, t.y).endVertex();
	}

	public static void addVertice(BufferBuilder buffer, Vertice v, float x, float y)
	{
		buffer.pos(v.x, v.y, v.z).tex(x, y).endVertex();
	}

	public static void addVertice(BufferBuilder buffer, Vertice v) {
		buffer.pos(v.x, v.y, v.z).endVertex();
	}
}
