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
import assets.rivalrebels.client.renderhelper.TextureVertice;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class ModelReactor {
	private static final TextureVertice	t1		= new TextureVertice(0.015625f * 0, 0.015625f * 0);
	private static final TextureVertice	t2		= new TextureVertice(0.015625f * 16, 0.015625f * 0);
	private static final TextureVertice	t3		= new TextureVertice(0.015625f * 32, 0.015625f * 0);
	private static final TextureVertice	t4		= new TextureVertice(0.015625f * 39, 0.015625f * 0);
	private static final TextureVertice	t5		= new TextureVertice(0.015625f * 41, 0.015625f * 0);
	private static final TextureVertice	t6		= new TextureVertice(0.015625f * 44, 0.015625f * 0);
	private static final TextureVertice	t7		= new TextureVertice(0.015625f * 32, 0.015625f * 1);
	private static final TextureVertice	t8		= new TextureVertice(0.015625f * 41, 0.015625f * 3);
	private static final TextureVertice	t9		= new TextureVertice(0.015625f * 44, 0.015625f * 3);
	private static final TextureVertice	t10		= new TextureVertice(0.015625f * 39, 0.015625f * 14);
	private static final TextureVertice	t11		= new TextureVertice(0.015625f * 41, 0.015625f * 14);
	private static final TextureVertice	t12		= new TextureVertice(0.015625f * 32, 0.015625f * 15);
	private static final TextureVertice	t13		= new TextureVertice(0.015625f * 0, 0.015625f * 16);
	private static final TextureVertice	t14		= new TextureVertice(0.015625f * 16, 0.015625f * 16);
	private static final TextureVertice	t15		= new TextureVertice(0.015625f * 17, 0.015625f * 16);
	private static final TextureVertice	t16		= new TextureVertice(0.015625f * 31, 0.015625f * 16);
	private static final TextureVertice	t17		= new TextureVertice(0.015625f * 32, 0.015625f * 16);
	private static final TextureVertice	t18		= new TextureVertice(0.015625f * 39, 0.015625f * 16);
	private static final TextureVertice	t19		= new TextureVertice(0.015625f * 16, 0.015625f * 23);
	private static final TextureVertice	t20		= new TextureVertice(0.015625f * 17, 0.015625f * 23);
	private static final TextureVertice	t21		= new TextureVertice(0.015625f * 31, 0.015625f * 23);
	private static final TextureVertice	t22		= new TextureVertice(0.015625f * 32, 0.015625f * 23);
	private static final TextureVertice	t23		= new TextureVertice(0.015625f * 16, 0.015625f * 30);
	private static final TextureVertice	t24		= new TextureVertice(0.015625f * 32, 0.015625f * 30);

	private static final TextureVertice	t1v		= new TextureVertice(0.015625f * 52.5f, 0.015625f * 0.5f);
	private static final TextureVertice	t2v		= new TextureVertice(0.015625f * 63.5f, 0.015625f * 11.5f);
	private static final TextureVertice	t3v		= new TextureVertice(0.015625f * 52.5f, 0.015625f * 22.5f);
	private static final TextureVertice	t4v		= new TextureVertice(0.015625f * 41.5f, 0.015625f * 11.5f);
	private static final TextureVertice	t5v		= new TextureVertice(0.015625f * 2f, 0.015625f * 18);
	private static final TextureVertice	t6v		= new TextureVertice(0.015625f * 14f, 0.015625f * 18f);
	private static final TextureVertice	t7v		= new TextureVertice(0.015625f * 2f, 0.015625f * 30f);
	private static final TextureVertice	t8v		= new TextureVertice(0.015625f * 14f, 0.015625f * 30f);
	private static final TextureVertice	t9v		= new TextureVertice(0.015625f * 1f, 0.015625f * 34f);
	private static final TextureVertice	t10v	= new TextureVertice(0.015625f * 15f, 0.015625f * 34f);
	private static final TextureVertice	t11v	= new TextureVertice(0.015625f * 1f, 0.015625f * 48f);
	private static final TextureVertice	t12v	= new TextureVertice(0.015625f * 15f, 0.015625f * 48f);

    private static final float	s = 0.5f;

	private static final Vector3f			v1		= new Vector3f(s, -s, s);
	private static final Vector3f			v2		= new Vector3f(s, -s, -s);
	private static final Vector3f			v3		= new Vector3f(-s, -s, -s);
	private static final Vector3f			v4		= new Vector3f(-s, -s, s);

	private static final Vector3f			v5		= new Vector3f(s, s, s);
	private static final Vector3f			v6		= new Vector3f(s, s, -s);
	private static final Vector3f			v7		= new Vector3f(-s, s, -s);
	private static final Vector3f			v8		= new Vector3f(-s, s, s);

	private static final Vector3f			v9		= new Vector3f(0.4375f, 0.8125f, 0.4375f);
	private static final Vector3f			v10		= new Vector3f(0.4375f, 0.8125f, -0.4375f);
	private static final Vector3f			v11		= new Vector3f(-0.4375f, 0.8125f, -0.4375f);
	private static final Vector3f			v12		= new Vector3f(-0.4375f, 0.8125f, 0.4375f);

	private static final Vector3f			v13		= new Vector3f(0.5f, 0.8125f, 0.5f);
	private static final Vector3f			v14		= new Vector3f(0.5f, 0.8125f, -0.5f);
	private static final Vector3f			v15		= new Vector3f(-0.5f, 0.8125f, -0.5f);
	private static final Vector3f			v16		= new Vector3f(-0.5f, 0.8125f, 0.5f);

	public static void renderModel(PoseStack matrices, VertexConsumer buffer, int light, int overlay) {
		matrices.pushPose();
		addFace(matrices, buffer, v13, v14, v15, v16, t14, t2, t3, t17, light, overlay);
		addFace(matrices, buffer, v9, v5, v6, v10, t7, t4, t18, t12, light, overlay);
		addFace(matrices, buffer, v10, v6, v7, v11, t15, t19, t22, t16, light, overlay);
		addFace(matrices, buffer, v11, v7, v8, v12, t7, t4, t18, t12, light, overlay);
		addFace(matrices, buffer, v12, v8, v5, v9, t21, t24, t23, t20, light, overlay);

		addFace(matrices, buffer, v5, v1, v2, v6, t2, t14, t13, t1, light, overlay);
		addFace(matrices, buffer, v6, v2, v3, v7, t2, t14, t13, t1, light, overlay);
		addFace(matrices, buffer, v7, v3, v4, v8, t2, t14, t13, t1, light, overlay);
		addFace(matrices, buffer, v8, v4, v1, v5, t2, t14, t13, t1, light, overlay);
		addFace(matrices, buffer, v2, v1, v4, v3, t1v, t2v, t3v, t4v, light, overlay);
		addFace(matrices, buffer, v5, v1, v2, v6, t1v, t2v, t3v, t4v, light, overlay, 0.9375F, 1, 1);
		addFace(matrices, buffer, v6, v2, v3, v7, t1v, t2v, t3v, t4v, light, overlay, 1, 1, 0.9375F);
		addFace(matrices, buffer, v7, v3, v4, v8, t1v, t2v, t3v, t4v, light, overlay, 0.9375F, 1, 1);
		addFace(matrices, buffer, v8, v4, v1, v5, t1v, t2v, t3v, t4v, light, overlay, 1, 1, 0.9375F);

		addFace(matrices, buffer, v2, v1, v4, v3, t5v, t6v, t8v, t7v, light, overlay, 0.6875F);
		addFace(matrices, buffer, v5, v1, v2, v6, t5v, t6v, t8v, t7v, light, overlay, 0.6875F);
		addFace(matrices, buffer, v6, v2, v3, v7, t5v, t6v, t8v, t7v, light, overlay, 0.6875F);
		addFace(matrices, buffer, v7, v3, v4, v8, t5v, t6v, t8v, t7v, light, overlay, 0.6875F);
		addFace(matrices, buffer, v8, v4, v1, v5, t5v, t6v, t8v, t7v, light, overlay, 0.6875F);
		addFace(matrices, buffer, v5, v6, v7, v8, t5v, t6v, t8v, t7v, light, overlay, 0.6875F);

		addFace(matrices, buffer, v2, v1, v4, v3, t9v, t10v, t12v, t11v, light, overlay, 0.8125F);
		addFace(matrices, buffer, v5, v1, v2, v6, t9v, t10v, t12v, t11v, light, overlay, 0.8125F);
		addFace(matrices, buffer, v6, v2, v3, v7, t9v, t10v, t12v, t11v, light, overlay, 0.8125F);
		addFace(matrices, buffer, v7, v3, v4, v8, t9v, t10v, t12v, t11v, light, overlay, 0.8125F);
		addFace(matrices, buffer, v8, v4, v1, v5, t9v, t10v, t12v, t11v, light, overlay, 0.8125F);
		addFace(matrices, buffer, v5, v6, v7, v8, t9v, t10v, t12v, t11v, light, overlay, 0.8125F);
		matrices.popPose();
	}

	private static void addFace(PoseStack pose, VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureVertice t1, TextureVertice t2, TextureVertice t3, TextureVertice t4, int light, int overlay) {
        addFace(pose, buffer, v1, v2, v3, v4, t1, t2, t3, t4, light, overlay, 1, 1, 1);
	}

    private static void addFace(PoseStack pose, VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureVertice t1, TextureVertice t2, TextureVertice t3, TextureVertice t4, int light, int overlay, float xoff, float yoff, float zoff) {
        RenderHelper.addFace(pose, buffer, new Vector3f(v1).mul(xoff, yoff, zoff), new Vector3f(v2).mul(xoff, yoff, zoff), new Vector3f(v3).mul(xoff, yoff, zoff), new Vector3f(v4).mul(xoff, yoff, zoff), t1, t2, t3, t4, light, overlay);
    }

    private static void addFace(PoseStack pose, VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureVertice t1, TextureVertice t2, TextureVertice t3, TextureVertice t4, int light, int overlay, float offset) {
        addFace(pose, buffer, v1, v2, v3, v4, t1, t2, t3, t4, light, overlay, offset, offset, offset);
    }
}
