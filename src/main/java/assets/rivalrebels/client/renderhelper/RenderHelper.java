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
import assets.rivalrebels.client.objfileloader.WavefrontObject;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class RenderHelper {
    public static final RenderLayer TRINGLES_POS_TEX = create("tringles_pos_tex", VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.TRIANGLES);
    public static final RenderLayer TRINGLES_POS_TEX_COLOR = create("tringles_pos_tex_color", VertexFormats.POSITION_TEXTURE_COLOR, VertexFormat.DrawMode.TRIANGLES);
    public static final RenderLayer TRINGLES_POS_COLOR = create("tringles_pos_color", VertexFormats.POSITION_COLOR, VertexFormat.DrawMode.TRIANGLES);
    public static final RenderLayer POS_TEX = create("pos_tex", VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS);

    private static RenderLayer create(String name, VertexFormat format, VertexFormat.DrawMode gl) {
        return RenderLayer.of(name, format, gl, 2097152, false, false, RenderLayer.MultiPhaseParameters.builder().program(RenderPhase.CUTOUT_PROGRAM).build(true));
    }

    public static WavefrontObject getModel(String modelName) {
        return WavefrontObject.loadModel(new Identifier(RivalRebels.MODID, "models/" + modelName + ".obj"));
    }

    public static void renderBox(MatrixStack matrices, VertexConsumer buffer, float length, float height, float depth, float texLocX, float texLocY, float texXsize, float texYsize, float resolution, int light)
	{
		matrices.multiply(new Quaternionf(90, 0.0F, 1.0F, 0.0F));
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
		Vector3f xpypzp = new Vector3f(hl, hh, hd);
		Vector3f xnypzp = new Vector3f(-hl, hh, hd);
		Vector3f xnypzn = new Vector3f(-hl, hh, -hd);
		Vector3f xpypzn = new Vector3f(hl, hh, -hd);
		Vector3f xpynzp = new Vector3f(hl, -hh, hd);
		Vector3f xnynzp = new Vector3f(-hl, -hh, hd);
		Vector3f xnynzn = new Vector3f(-hl, -hh, -hd);
		Vector3f xpynzn = new Vector3f(hl, -hh, -hd);
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
		addFace(buffer, xpypzn, xnypzn, xnypzp, xpypzp, t6, t2, t1, t5, light); // top
		addFace(buffer, xpynzp, xnynzp, xnynzn, xpynzn, t6, t2, t3, t8, light); // bottom
		addFace(buffer, xnypzp, xnynzp, xpynzp, xpypzp, t4, t10, t11, t5, light); // right
		addFace(buffer, xpypzp, xpynzp, xpynzn, xpypzn, t5, t11, t12, t6, light); // front
		addFace(buffer, xpypzn, xpynzn, xnynzn, xnypzn, t6, t12, t13, t7, light); // left
		addFace(buffer, xnypzn, xnynzn, xnynzp, xnypzp, t7, t13, t14, t9, light); // back
	}

    public static void addFace(VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureVertice t1, TextureVertice t2, TextureVertice t3, TextureVertice t4, int light) {
        addFace(buffer, v1, v2, v3, v4, t1, t2, t3, t4, light, OverlayTexture.DEFAULT_UV);
    }

    public static void addFace(VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureVertice t1, TextureVertice t2, TextureVertice t3, TextureVertice t4, int light, int overlay) {
        Vector3f mv = new Vector3f((v1.x + v2.x + v3.x + v4.x) / 4, (v1.y + v2.y + v3.y + v4.y) / 4, (v1.z + v2.z + v3.z + v4.z) / 4);
        TextureVertice mt = new TextureVertice((t1.x + t2.x + t3.x + t4.x) / 4, (t1.y + t2.y + t3.y + t4.y) / 4);
        addVertice(buffer, v1, t1, light, overlay);
        addVertice(buffer, v2, t2, light, overlay);
        addVertice(buffer, v3, t3, light, overlay);
        addVertice(buffer, mv, mt, light, overlay);
    }

    public static void addFace(VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, Vector4f color, int light, int overlay) {
        addVertice(buffer, v1, color, light, overlay);
        addVertice(buffer, v2, color, light, overlay);
        addVertice(buffer, v3, color, light, overlay);
        addVertice(buffer, v4, color, light, overlay);
    }

    public static void addFace(VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureFace t, int light, int overlay) {
        addVertice(buffer, v1, t.v1, light, overlay);
        addVertice(buffer, v2, t.v2, light, overlay);
        addVertice(buffer, v3, t.v3, light, overlay);
        addVertice(buffer, v4, t.v4, light, overlay);
    }

    public static void addFace(VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, float x1, float x2, float y2, float y1, int light, int overlay) {
        addVertice(buffer, v1, new TextureVertice(x1, y1), light, overlay);
        addVertice(buffer, v2, new TextureVertice(x2, y1), light, overlay);
        addVertice(buffer, v3, new TextureVertice(x2, y2), light, overlay);
        addVertice(buffer, v4, new TextureVertice(x1, y2), light, overlay);
    }

    public static void addTri(VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector4f color) {
        buffer.vertex(v3.x, v3.y, v3.z).color(color.x(), color.y(), color.z(), color.w()).next();
        buffer.vertex(v1.x, v1.y, v1.z).color(color.x(), color.y(), color.z(), color.w()).next();
        buffer.vertex(v2.x, v2.y, v2.z).color(color.x(), color.y(), color.z(), color.w()).next();
    }

    public static void addVertice(VertexConsumer buffer, Vector3f v, TextureVertice t, Vector4f color, int light, int overlay) {
        buffer.vertex(v.x, v.y, v.z, color.x(), color.y(), color.z(), color.w(), t.x, t.y, overlay, light, 0, 0, 1);
    }

    public static void addVertice(VertexConsumer buffer, Vector3f v, TextureVertice t, int light, int overlay) {
        addVertice(buffer, v, t, new Vector4f(1, 1, 1, 1), light, overlay);
    }

    public static void addVertice(VertexConsumer buffer, Vector3f v, Vector4f color, int light, int overlay) {
        buffer.vertex(v.x, v.y, v.z).color(color.x(), color.y(), color.z(), color.w()).overlay(overlay).light(light).next();
    }
}
