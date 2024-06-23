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
import org.joml.Vector3f;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class ModelReactor
{
	float			xoff	= 1;
	float			yoff	= 1;
	float			zoff	= 1;

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

	public void renderModel(MatrixStack matrices, VertexConsumer buffer, int light, int overlay) {
		matrices.push();
		addFace(buffer, v13, v14, v15, v16, t14, t2, t3, t17, light, overlay);
		addFace(buffer, v9, v5, v6, v10, t7, t4, t18, t12, light, overlay);
		addFace(buffer, v10, v6, v7, v11, t15, t19, t22, t16, light, overlay);
		addFace(buffer, v11, v7, v8, v12, t7, t4, t18, t12, light, overlay);
		addFace(buffer, v12, v8, v5, v9, t21, t24, t23, t20, light, overlay);

		addFace(buffer, v5, v1, v2, v6, t2, t14, t13, t1, light, overlay);
		addFace(buffer, v6, v2, v3, v7, t2, t14, t13, t1, light, overlay);
		addFace(buffer, v7, v3, v4, v8, t2, t14, t13, t1, light, overlay);
		addFace(buffer, v8, v4, v1, v5, t2, t14, t13, t1, light, overlay);
		addFace(buffer, v2, v1, v4, v3, t1v, t2v, t3v, t4v, light, overlay);
		xoff = 0.9375f;
		addFace(buffer, v5, v1, v2, v6, t1v, t2v, t3v, t4v, light, overlay);
		xoff = 1f;
		zoff = 0.9375f;
		addFace(buffer, v6, v2, v3, v7, t1v, t2v, t3v, t4v, light, overlay);
		xoff = 0.9375f;
		zoff = 1f;
		addFace(buffer, v7, v3, v4, v8, t1v, t2v, t3v, t4v, light, overlay);
		xoff = 1f;
		zoff = 0.9375f;
		addFace(buffer, v8, v4, v1, v5, t1v, t2v, t3v, t4v, light, overlay);

        xoff = yoff = zoff = 0.6875F;
		addFace(buffer, v2, v1, v4, v3, t5v, t6v, t8v, t7v, light, overlay);
		addFace(buffer, v5, v1, v2, v6, t5v, t6v, t8v, t7v, light, overlay);
		addFace(buffer, v6, v2, v3, v7, t5v, t6v, t8v, t7v, light, overlay);
		addFace(buffer, v7, v3, v4, v8, t5v, t6v, t8v, t7v, light, overlay);
		addFace(buffer, v8, v4, v1, v5, t5v, t6v, t8v, t7v, light, overlay);
		addFace(buffer, v5, v6, v7, v8, t5v, t6v, t8v, t7v, light, overlay);

		xoff = yoff = zoff = 0.8125F;
		addFace(buffer, v2, v1, v4, v3, t9v, t10v, t12v, t11v, light, overlay);
		addFace(buffer, v5, v1, v2, v6, t9v, t10v, t12v, t11v, light, overlay);
		addFace(buffer, v6, v2, v3, v7, t9v, t10v, t12v, t11v, light, overlay);
		addFace(buffer, v7, v3, v4, v8, t9v, t10v, t12v, t11v, light, overlay);
		addFace(buffer, v8, v4, v1, v5, t9v, t10v, t12v, t11v, light, overlay);
		addFace(buffer, v5, v6, v7, v8, t9v, t10v, t12v, t11v, light, overlay);
		xoff = yoff = zoff = 1F;
		matrices.pop();
	}

	private void addFace(VertexConsumer buffer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureVertice t1, TextureVertice t2, TextureVertice t3, TextureVertice t4, int light, int overlay) {
        RenderHelper.addFace(buffer, new Vector3f(v1).mul(xoff, yoff, zoff), new Vector3f(v2).mul(xoff, yoff, zoff), new Vector3f(v3).mul(xoff, yoff, zoff), new Vector3f(v4).mul(xoff, yoff, zoff), t1, t2, t3, t4, light, overlay);
	}
}
