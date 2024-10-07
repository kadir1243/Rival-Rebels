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
package io.github.kadir1243.rivalrebels.client.renderhelper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.CommonColors;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class RenderHelper {
    public static void renderBox(PoseStack pose, VertexConsumer buffer, float length, float height, float depth, float texLocX, float texLocY, float texXsize, float texYsize, float resolution, int light) {
		pose.mulPose(Axis.YP.rotationDegrees(90));
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
		addFace(pose, buffer, xpypzn, xnypzn, xnypzp, xpypzp, t6, t2, t1, t5, light); // top
		addFace(pose, buffer, xpynzp, xnynzp, xnynzn, xpynzn, t6, t2, t3, t8, light); // bottom
		addFace(pose, buffer, xnypzp, xnynzp, xpynzp, xpypzp, t4, t10, t11, t5, light); // right
		addFace(pose, buffer, xpypzp, xpynzp, xpynzn, xpypzn, t5, t11, t12, t6, light); // front
		addFace(pose, buffer, xpypzn, xpynzn, xnynzn, xnypzn, t6, t12, t13, t7, light); // left
		addFace(pose, buffer, xnypzn, xnynzn, xnynzp, xnypzp, t7, t13, t14, t9, light); // back
	}

    public static void addFace(PoseStack pose, VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureVertice t1, TextureVertice t2, TextureVertice t3, TextureVertice t4, int light) {
        addFace(pose, buffer, v1, v2, v3, v4, t1, t2, t3, t4, light, OverlayTexture.NO_OVERLAY);
    }

    public static void addFace(PoseStack pose, VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureVertice t1, TextureVertice t2, TextureVertice t3, TextureVertice t4, int light, int overlay) {
        Vector3f mv = new Vector3f((v1.x + v2.x + v3.x + v4.x) / 4, (v1.y + v2.y + v3.y + v4.y) / 4, (v1.z + v2.z + v3.z + v4.z) / 4);
        TextureVertice mt = new TextureVertice((t1.x() + t2.x() + t3.x() + t4.x()) / 4, (t1.y() + t2.y() + t3.y() + t4.y()) / 4);
        addVertice(pose, buffer, v1, t1, light, overlay);
        addVertice(pose, buffer, v2, t2, light, overlay);
        addVertice(pose, buffer, mv, mt, light, overlay);

        addVertice(pose, buffer, v2, t2, light, overlay);
        addVertice(pose, buffer, v3, t3, light, overlay);
        addVertice(pose, buffer, mv, mt, light, overlay);

        addVertice(pose, buffer, v3, t3, light, overlay);
        addVertice(pose, buffer, v4, t4, light, overlay);
        addVertice(pose, buffer, mv, mt, light, overlay);

        addVertice(pose, buffer, v4, t4, light, overlay);
        addVertice(pose, buffer, v1, t1, light, overlay);
        addVertice(pose, buffer, mv, mt, light, overlay);
    }

    public static void addFace(PoseStack poseStack, VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, int color, int light, int overlay) {
        addVertice(poseStack, buffer, v1, color, light, overlay);
        addVertice(poseStack, buffer, v2, color, light, overlay);
        addVertice(poseStack, buffer, v3, color, light, overlay);
        addVertice(poseStack, buffer, v4, color, light, overlay);
    }

    public static void addFace(PoseStack pose, VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureFace t, int light, int overlay) {
        addVertice(pose, buffer, v1, t.v1(), light, overlay);
        addVertice(pose, buffer, v2, t.v2(), light, overlay);
        addVertice(pose, buffer, v3, t.v3(), light, overlay);
        addVertice(pose, buffer, v4, t.v4(), light, overlay);
    }

    public static void addFace(PoseStack pose, VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, float x1, float x2, float y2, float y1, int light, int overlay) {
        addVertice(pose, buffer, v1, new TextureVertice(x1, y1), light, overlay);
        addVertice(pose, buffer, v2, new TextureVertice(x2, y1), light, overlay);
        addVertice(pose, buffer, v3, new TextureVertice(x2, y2), light, overlay);
        addVertice(pose, buffer, v4, new TextureVertice(x1, y2), light, overlay);
    }

    public static void addTri(PoseStack pose, VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, int color) {
        buffer.addVertex(pose.last(), v3).setColor(color);
        buffer.addVertex(pose.last(), v1).setColor(color);
        buffer.addVertex(pose.last(), v2).setColor(color);
    }

    public static void addVertice(PoseStack pose, VertexConsumer buffer, Vector3f v, TextureVertice t, int color, int light, int overlay) {
        buffer.addVertex(pose.last(), v)
            .setColor(color)
            .setUv(t.x(), t.y())
            .setOverlay(overlay)
            .setLight(light)
            .setNormal(pose.last(), 0F, 0F, 1F);
    }

    public static void addVertice(PoseStack pose, VertexConsumer buffer, Vector3f v, TextureVertice t, int light, int overlay) {
        addVertice(pose, buffer, v, t, CommonColors.WHITE, light, overlay);
    }

    public static void addVertice(PoseStack pose, VertexConsumer buffer, Vector3f v, int color, int light, int overlay) {
        buffer.addVertex(pose.last(), v)
            .setColor(color)
            .setUv(16, 16)
            .setOverlay(overlay)
            .setLight(light)
            .setNormal(pose.last(), 0F, 0F, 1F);
    }
}
