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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModelRocketLauncherTube
{
	private static final Vector3f	vy1		= new Vector3f(0, 0, 0);
	private static final Vector3f	vpx1	= new Vector3f(0.5f, 0, 0);
	private static final Vector3f	vnx1	= new Vector3f(-0.5f, 0, 0);
	private static final Vector3f	vpz1	= new Vector3f(0, 0, 0.5f);
	private static final Vector3f	vnz1	= new Vector3f(0, 0, -0.5f);
	private static final Vector3f	vpxpz1	= new Vector3f(0.3535533f, 0, 0.3535533f);
	private static final Vector3f	vpxnz1	= new Vector3f(0.3535533f, 0, -0.3535533f);
	private static final Vector3f	vnxpz1	= new Vector3f(-0.3535533f, 0, 0.3535533f);
	private static final Vector3f	vnxnz1	= new Vector3f(-0.3535533f, 0, -0.3535533f);
	private static final Vector3f	vpx2	= new Vector3f(0.5f, 2, 0);
	private static final Vector3f	vnx2	= new Vector3f(-0.5f, 2, 0);
	private static final Vector3f	vpz2	= new Vector3f(0, 2, 0.5f);
	private static final Vector3f	vnz2	= new Vector3f(0, 2, -0.5f);
	private static final Vector3f	vpxpz2	= new Vector3f(0.3535533f, 2, 0.3535533f);
	private static final Vector3f	vpxnz2	= new Vector3f(0.3535533f, 2, -0.3535533f);
	private static final Vector3f	vnxpz2	= new Vector3f(-0.3535533f, 2, 0.3535533f);
	private static final Vector3f	vnxnz2	= new Vector3f(-0.3535533f, 2, -0.3535533f);
	private static final float	tx1		= 0;
	private static final float	tx2		= 0.28125f;
	private static final float	tx4		= 0.5625f;
	private static final float	tx5		= 0.65625f;
	private static final float	ty1		= 0;
	private static final float	ty2		= 0.09375f;

	public static void render(PoseStack matrices, VertexConsumer buffer, int light, int overlay) {
		matrices.pushPose();

		RenderHelper.addFace(matrices, buffer, vpx1, vpx2, vpxpz2, vpxpz1, tx1, tx2, ty1, ty2, light, overlay);
		RenderHelper.addFace(matrices, buffer, vpxpz1, vpxpz2, vpz2, vpz1, tx1, tx2, ty1, ty2, light, overlay);
		RenderHelper.addFace(matrices, buffer, vpz1, vpz2, vnxpz2, vnxpz1, tx1, tx2, ty1, ty2, light, overlay);
		RenderHelper.addFace(matrices, buffer, vnxpz1, vnxpz2, vnx2, vnx1, tx1, tx2, ty1, ty2, light, overlay);
		RenderHelper.addFace(matrices, buffer, vnx1, vnx2, vnxnz2, vnxnz1, tx1, tx2, ty1, ty2, light, overlay);
		RenderHelper.addFace(matrices, buffer, vnxnz1, vnxnz2, vnz2, vnz1, tx1, tx2, ty1, ty2, light, overlay);
		RenderHelper.addFace(matrices, buffer, vnz1, vnz2, vpxnz2, vpxnz1, tx1, tx2, ty1, ty2, light, overlay);
		RenderHelper.addFace(matrices, buffer, vpxnz1, vpxnz2, vpx2, vpx1, tx1, tx2, ty1, ty2, light, overlay);

		RenderHelper.addFace(matrices, buffer, vpx1, vpxpz1, vpz1, vy1, tx4, tx5, ty1, ty2, light, overlay);
		RenderHelper.addFace(matrices, buffer, vpz1, vnxpz1, vnx1, vy1, tx4, tx5, ty1, ty2, light, overlay);
		RenderHelper.addFace(matrices, buffer, vnx1, vnxnz1, vnz1, vy1, tx4, tx5, ty1, ty2, light, overlay);
		RenderHelper.addFace(matrices, buffer, vnz1, vpxnz1, vpx1, vy1, tx4, tx5, ty1, ty2, light, overlay);

		matrices.popPose();
	}
}
