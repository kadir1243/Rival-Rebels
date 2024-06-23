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
import org.joml.Quaternionf;

public class ModelLaptop
{
	private static final TextureVertice	t1		= new TextureVertice(0.03125f * 1, 0.03125f * 0);
	private static final TextureVertice	t2		= new TextureVertice(0.03125f * 10, 0.03125f * 0);
	private static final TextureVertice	t3		= new TextureVertice(0.03125f * 0, 0.03125f * 1);
	private static final TextureVertice	t4		= new TextureVertice(0.03125f * 1, 0.03125f * 1);
	private static final TextureVertice	t5		= new TextureVertice(0.03125f * 15, 0.03125f * 1);
	private static final TextureVertice	t6		= new TextureVertice(0.03125f * 16, 0.03125f * 1);
	private static final TextureVertice	t7		= new TextureVertice(0.03125f * 30, 0.03125f * 1);
	private static final TextureVertice	t8		= new TextureVertice(0.03125f * 0, 0.03125f * 10);
	private static final TextureVertice	t9		= new TextureVertice(0.03125f * 1, 0.03125f * 10);
	private static final TextureVertice	t10		= new TextureVertice(0.03125f * 15, 0.03125f * 10);
	private static final TextureVertice	t11		= new TextureVertice(0.03125f * 16, 0.03125f * 10);
	private static final TextureVertice	t12		= new TextureVertice(0.03125f * 30, 0.03125f * 10);
	private static final TextureVertice	t13		= new TextureVertice(0.03125f * 1, 0.03125f * 11);
	private static final TextureVertice	t14		= new TextureVertice(0.03125f * 15, 0.03125f * 11);

	private static final TextureVertice	t1t		= new TextureVertice(0.03125f * 2, 0.03125f * 11);
	private static final TextureVertice	t2t		= new TextureVertice(0.03125f * 16, 0.03125f * 11);
	private static final TextureVertice	t3t		= new TextureVertice(0.03125f * 0, 0.03125f * 13);
	private static final TextureVertice	t4t		= new TextureVertice(0.03125f * 2, 0.03125f * 13);
	private static final TextureVertice	t5t		= new TextureVertice(0.03125f * 16, 0.03125f * 13);
	private static final TextureVertice	t6t		= new TextureVertice(0.03125f * 18, 0.03125f * 13);
	private static final TextureVertice	t7t		= new TextureVertice(0.03125f * 32, 0.03125f * 13);
	private static final TextureVertice	t8t		= new TextureVertice(0.03125f * 0, 0.03125f * 22);
	private static final TextureVertice	t9t		= new TextureVertice(0.03125f * 2, 0.03125f * 22);
	private static final TextureVertice	t10t	= new TextureVertice(0.03125f * 16, 0.03125f * 22);
	private static final TextureVertice	t11t	= new TextureVertice(0.03125f * 18, 0.03125f * 22);
	private static final TextureVertice	t12t	= new TextureVertice(0.03125f * 32, 0.03125f * 22);
	private static final TextureVertice	t13t	= new TextureVertice(0.03125f * 2, 0.03125f * 24);
	private static final TextureVertice	t14t	= new TextureVertice(0.03125f * 16, 0.03125f * 24);

	private static final Vector3f			v1		= new Vector3f(0.4375f, 0f, 0.5625f);
	private static final Vector3f			v2		= new Vector3f(0.4375f, 0f, 0);
	private static final Vector3f			v3		= new Vector3f(-0.4375f, 0f, 0);
	private static final Vector3f			v4		= new Vector3f(-0.4375f, 0f, 0.5625f);

	private static final Vector3f			v5		= new Vector3f(0.4375f, 0.0625f, 0.5625f);
	private static final Vector3f			v6		= new Vector3f(0.4375f, 0.0625f, 0);
	private static final Vector3f			v7		= new Vector3f(-0.4375f, 0.0625f, 0);
	private static final Vector3f			v8		= new Vector3f(-0.4375f, 0.0625f, 0.5625f);

	private static final Vector3f			v9		= new Vector3f(0.4375f, 0.125f, 0.5625f);
	private static final Vector3f			v10		= new Vector3f(0.4375f, 0.125f, 0);
	private static final Vector3f			v11		= new Vector3f(-0.4375f, 0.125f, 0);
	private static final Vector3f			v12		= new Vector3f(-0.4375f, 0.125f, 0.5625f);

	public static void renderModel(VertexConsumer buffer, MatrixStack matrices, float turn, int light, int overlay)
	{
		matrices.push();
		RenderHelper.addFace(buffer, v11, v12, v9, v10, t4t, t9t, t10t, t5t, light, overlay);
		RenderHelper.addFace(buffer, v12, v4, v1, v9, t9t, t13t, t14t, t10t, light, overlay);
		RenderHelper.addFace(buffer, v11, v3, v4, v12, t4t, t3t, t8t, t9t, light, overlay);
		RenderHelper.addFace(buffer, v10, v2, v3, v11, t5t, t2t, t1t, t4t, light, overlay);
		RenderHelper.addFace(buffer, v9, v1, v2, v10, t10t, t11t, t6t, t5t, light, overlay);
		RenderHelper.addFace(buffer, v2, v1, v4, v3, t6t, t11t, t12t, t7t, light, overlay);
		matrices.pop();

		matrices.push();
		matrices.translate(0, 0.125f, 0);
		matrices.multiply(new Quaternionf(turn, 0.1875f, 0, 0));
		RenderHelper.addFace(buffer, v5, v6, v7, v8, t4, t9, t10, t5, light, overlay);
		RenderHelper.addFace(buffer, v8, v4, v1, v5, t9, t13, t14, t10, light, overlay);
		RenderHelper.addFace(buffer, v7, v3, v4, v8, t4, t3, t8, t9, light, overlay);
		RenderHelper.addFace(buffer, v6, v2, v3, v7, t5, t2, t1, t4, light, overlay);
		RenderHelper.addFace(buffer, v5, v1, v2, v6, t10, t11, t6, t5, light, overlay);
		RenderHelper.addFace(buffer, v2, v1, v4, v3, t6, t11, t12, t7, light, overlay);
		matrices.pop();
	}

	private static final TextureVertice	t111	= new TextureVertice(0, 0);
	private static final TextureVertice	t222	= new TextureVertice(1, 0);
	private static final TextureVertice	t333	= new TextureVertice(1, 1);
	private static final TextureVertice	t444	= new TextureVertice(0, 1);
	private static final Vector3f v1v		= new Vector3f(0.375f, 0f, 0.5f);
	private static final Vector3f v2v		= new Vector3f(0.375f, 0f, 0.0625f);
	private static final Vector3f v3v		= new Vector3f(-0.375f, 0f, 0.0625f);
	private static final Vector3f v4v		= new Vector3f(-0.375f, 0f, 0.5f);

	public static void renderScreen(VertexConsumer buffer, MatrixStack matrices, float turn, int light, int overlay)
	{
		matrices.push();
		matrices.translate(0, 0.125f, 0);
		matrices.multiply(new Quaternionf(turn, 0.1875f, 0, 0));
		RenderHelper.addFace(buffer, v2v, v1v, v4v, v3v, t333, t222, t111, t444, light, overlay);
		matrices.pop();
	}
}
