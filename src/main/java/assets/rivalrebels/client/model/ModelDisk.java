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
import com.mojang.math.Axis;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

public class ModelDisk
{
	private static final TextureVertice	t1			= new TextureVertice(0.03125f * 0, 0.03125f * 0);
	private static final TextureVertice	t2			= new TextureVertice(0.03125f * 11, 0.03125f * 0);
	private static final TextureVertice	t3			= new TextureVertice(0.03125f * 17, 0.03125f * 0);
	private static final TextureVertice	t4			= new TextureVertice(0.03125f * 0, 0.03125f * 6);
	private static final TextureVertice	t5			= new TextureVertice(0.03125f * 11, 0.03125f * 6);
	private static final TextureVertice	t6			= new TextureVertice(0.03125f * 0, 0.03125f * 7);
	private static final TextureVertice	t7			= new TextureVertice(0.03125f * 11, 0.03125f * 7);
	private static final TextureVertice	t8			= new TextureVertice(0.03125f * 17, 0.03125f * 7);
	private static final TextureVertice	t9			= new TextureVertice(0.03125f * 0, 0.03125f * 8);
	private static final TextureVertice	t10			= new TextureVertice(0.03125f * 11, 0.03125f * 8);
	private static final Vector3f			v1			= new Vector3f(0.45f, -0.03125f, 0f);
	private static final Vector3f			v2			= new Vector3f(0.45f, 0.03125f, 0f);
	private static final Vector3f			v3			= new Vector3f(0.65f, 0.0625f, 0f);
	private static final Vector3f			v4			= new Vector3f(1.00f, 0f, 0f);
	private static final Vector3f			v5			= new Vector3f(0.65f, -0.0625f, 0f);

	private static final int numOfSegs	= 32;
	private static final float	deg			= Mth.TWO_PI / numOfSegs;
	private static final float	cosdeg		= Mth.cos(deg);
	private static final float	sindeg		= Mth.sin(deg);

	private static final Vector3f v6 = new Vector3f(0.45f * cosdeg, -0.03125f, 0.45f * sindeg);
	private static final Vector3f v7 = new Vector3f(0.45f * cosdeg, 0.03125f, 0.45f * sindeg);
	private static final Vector3f v8 = new Vector3f(0.65f * cosdeg, 0.0625f, 0.65f * sindeg);
	private static final Vector3f v9 = new Vector3f(1.00f * cosdeg, 0f, 1.00f * sindeg);
	private static final Vector3f v10 = new Vector3f(0.65f * cosdeg, -0.0625f, 0.65f * sindeg);

	public static void render(PoseStack matrices, VertexConsumer buffer, int light, int overlay) {
		for (float i = 0; i < 360; i += 360 / numOfSegs) {
			matrices.pushPose();
			matrices.mulPose(Axis.YP.rotationDegrees(i));
			RenderHelper.addFace(matrices, buffer, v2, v1, v6, v7, t4, t9, t10, t5, light, overlay);
			if (i == 0 || i == 16) {
				RenderHelper.addFace(matrices, buffer, v3, v2, v7, v8, t2, t7, t8, t3, light, overlay);
				RenderHelper.addFace(matrices, buffer, v4, v3, v8, v9, t2, t7, t8, t3, light, overlay);
				RenderHelper.addFace(matrices, buffer, v5, v4, v9, v10, t2, t7, t8, t3, light, overlay);
				RenderHelper.addFace(matrices, buffer, v1, v5, v10, v6, t2, t7, t8, t3, light, overlay);
			} else {
				RenderHelper.addFace(matrices, buffer, v3, v2, v7, v8, t6, t1, t2, t7, light, overlay);
				RenderHelper.addFace(matrices, buffer, v4, v3, v8, v9, t6, t1, t2, t7, light, overlay);
				RenderHelper.addFace(matrices, buffer, v5, v4, v9, v10, t1, t6, t7, t2, light, overlay);
				RenderHelper.addFace(matrices, buffer, v1, v5, v10, v6, t1, t6, t7, t2, light, overlay);
			}
			matrices.popPose();
		}
	}
}
