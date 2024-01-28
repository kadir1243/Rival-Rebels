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

import assets.rivalrebels.RivalRebels;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vector4f;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;

public class RenderHelper {
    public static final RenderLayer TRINGLES_POS_TEX = create("tringles_pos_tex", VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.TRIANGLES);
    public static final RenderLayer TRINGLES_POS_TEX_COLOR = create("tringles_pos_tex_color", VertexFormats.POSITION_TEXTURE_COLOR, VertexFormat.DrawMode.TRIANGLES);
    public static final RenderLayer TRINGLES_POS_COLOR = create("tringles_pos_color", VertexFormats.POSITION_COLOR, VertexFormat.DrawMode.TRIANGLES);

    private static RenderLayer create(String name, VertexFormat format, VertexFormat.DrawMode gl) {
        return RenderLayer.of(name, format, gl, 2097152, false, false, RenderLayer.MultiPhaseParameters.builder().build(true));
    }

    public static OBJModel getModel(String modelName) {
        return OBJLoader.INSTANCE.loadModel(new OBJModel.ModelSettings(new Identifier(RivalRebels.MODID, "models/" + modelName + ".obj"), true, true, false, false, null));
    }

    public static void renderBox(MatrixStack matrices, VertexConsumer buffer, float length, float height, float depth, float texLocX, float texLocY, float texXsize, float texYsize, float resolution)
	{
		matrices.multiply(new Quaternion(90, 0.0F, 1.0F, 0.0F));
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
		addFace(buffer, xpypzn, xnypzn, xnypzp, xpypzp, t6, t2, t1, t5); // top
		addFace(buffer, xpynzp, xnynzp, xnynzn, xpynzn, t6, t2, t3, t8); // bottom
		addFace(buffer, xnypzp, xnynzp, xpynzp, xpypzp, t4, t10, t11, t5); // right
		addFace(buffer, xpypzp, xpynzp, xpynzn, xpypzn, t5, t11, t12, t6); // front
		addFace(buffer, xpypzn, xpynzn, xnynzn, xnypzn, t6, t12, t13, t7); // left
		addFace(buffer, xnypzn, xnynzn, xnynzp, xnypzp, t7, t13, t14, t9); // back
	}

	public static void drawPoint(MatrixStack matrices, VertexConsumer buffer, Vertice v1, float size) {
		matrices.push();
		matrices.translate(v1.x, v1.y, v1.z);
		size /= 2;
        matrices.multiply(new Quaternion(180 - MinecraftClient.getInstance().player.getYaw(), 0.0F, 1.0F, 0.0F));
		matrices.multiply(new Quaternion(90 - MinecraftClient.getInstance().player.getPitch(), 1.0F, 0.0F, 0.0F));

		buffer.vertex(-size, 0, -size).texture(0, 0).normal(0, 1, 0).next();
		buffer.vertex(size, 0, -size).texture(1, 0).normal(0, 1, 0).next();
		buffer.vertex(size, 0, size).texture(1, 1).normal(0, 1, 0).next();
		buffer.vertex(-size, 0, size).texture(0, 1).normal(0, 1, 0).next();
		matrices.pop();
	}

    public static void renderBox(MatrixStack matrices, VertexConsumer buffer, float length, float height, float depth)
	{
		matrices.multiply(new Quaternion(90, 0.0F, 1.0F, 0.0F));
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
        addFace(buffer, xpypzn, xnypzn, xnypzp, xpypzp); // top
		addFace(buffer, xpynzp, xnynzp, xnynzn, xpynzn); // bottom
		addFace(buffer, xnypzp, xnynzp, xpynzp, xpypzp); // right
		addFace(buffer, xpypzp, xpynzp, xpynzn, xpypzn); // front
		addFace(buffer, xpypzn, xpynzn, xnynzn, xnypzn); // left
		addFace(buffer, xnypzn, xnynzn, xnynzp, xnypzp); // back
    }

	public static void addFace(VertexConsumer buffer, Vertice v1, Vertice v2, Vertice v3, Vertice v4, TextureVertice t1, TextureVertice t2, TextureVertice t3, TextureVertice t4) {
        Vertice mv = new Vertice((v1.x + v2.x + v3.x + v4.x) / 4, (v1.y + v2.y + v3.y + v4.y) / 4, (v1.z + v2.z + v3.z + v4.z) / 4);
		TextureVertice mt = new TextureVertice((t1.x + t2.x + t3.x + t4.x) / 4, (t1.y + t2.y + t3.y + t4.y) / 4);
		addVertice(buffer, v1, t1);
		addVertice(buffer, v2, t2);
        addVertice(buffer, v3, t3);
        addVertice(buffer, mv, mt);
	}

    public static void addFace(VertexConsumer buffer, Vertice v1, Vertice v2, Vertice v3, Vertice v4) {
        addVertice(buffer, v1);
        addVertice(buffer, v2);
        addVertice(buffer, v3);
        addVertice(buffer, v4);
    }

	public static void addFace(VertexConsumer buffer, Vertice v1, Vertice v2, Vertice v3, Vertice v4, TextureFace t) {
		addVertice(buffer, v1, t.v1);
		addVertice(buffer, v2, t.v2);
		addVertice(buffer, v3, t.v3);
		addVertice(buffer, v4, t.v4);
	}

	public static void addFace(VertexConsumer buffer, Vertice v1, Vertice v2, Vertice v3, Vertice v4, float par5, float par6, float par7, float par8) {
		addVertice(buffer, v1, par5, par8);
		addVertice(buffer, v2, par6, par8);
		addVertice(buffer, v3, par6, par7);
		addVertice(buffer, v4, par5, par7);
	}

    public static void addTri(VertexConsumer buffer, Vertice v1, Vertice v2, Vertice v3, Vector4f color) {
        buffer.vertex(v3.x, v3.y, v3.z).color(color.getX(), color.getY(), color.getZ(), color.getW()).next();
        buffer.vertex(v1.x, v1.y, v1.z).color(color.getX(), color.getY(), color.getZ(), color.getW()).next();
        buffer.vertex(v2.x, v2.y, v2.z).color(color.getX(), color.getY(), color.getZ(), color.getW()).next();
    }

	public static void addVertice(VertexConsumer buffer, Vertice v, TextureVertice t) {
        buffer.vertex(v.x, v.y, v.z).texture(t.x, t.y).next();
	}

	public static void addVertice(VertexConsumer buffer, Vertice v, float x, float y)
	{
		buffer.vertex(v.x, v.y, v.z).texture(x, y).next();
	}

	public static void addVertice(VertexConsumer buffer, Vertice v) {
		buffer.vertex(v.x, v.y, v.z).next();
	}
}
