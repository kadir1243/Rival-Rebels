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
// Copyrighted Rodolian Material
package io.github.kadir1243.rivalrebels.client.model;

import io.github.kadir1243.rivalrebels.client.renderhelper.RenderHelper;
import io.github.kadir1243.rivalrebels.client.renderhelper.TextureVertice;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class ModelAstroBlasterBarrel {
	private static final float	i			= 0.035714286f;
	private static final float[]	barrelx		= { 0f, 0.2f, 0.2f, 0.25f, 0.25f, 0.2f, 0f };
	private static final float[]	barrely		= { 0f, 0f, 0.2f, 0.2f, 0f, -0.1f, -0.1f };
	private static final float[]	tsart		= { i * 28, i * 24, i * 16, i * 15, i * 7, i * 4, i * 3, 0 };
	private static final int		segments	= 8;
	private static final float	deg			= Mth.TWO_PI / segments;
	private static final float	sin			= Mth.sin(deg);
	private static final float	cos			= Mth.cos(deg);
	private static final float	add			= 360 / segments;

	public static void render(PoseStack matrices, VertexConsumer buffer, int light, int overlay)
	{
		matrices.pushPose();

		for (float i = 0; i < segments; i++) {
			matrices.pushPose();
			matrices.mulPose(Axis.YP.rotationDegrees(add * i));
			for (int f = 1; f < barrelx.length; f++) {
				TextureVertice t1 = new TextureVertice((1f / segments) * i, tsart[f]);
				TextureVertice t2 = new TextureVertice((1f / segments) * i, tsart[f - 1]);
				TextureVertice t3 = new TextureVertice((1f / segments) * (i + 1), tsart[f - 1]);
				TextureVertice t4 = new TextureVertice((1f / segments) * (i + 1), tsart[f]);
				RenderHelper.addFace(matrices, buffer, new Vector3f(0f, barrely[f], barrelx[f]),
						new Vector3f(0f, barrely[f - 1], barrelx[f - 1]),
						new Vector3f(barrelx[f - 1] * sin, barrely[f - 1], barrelx[f - 1] * cos),
						new Vector3f(barrelx[f] * sin, barrely[f], barrelx[f] * cos), t1, t2, t3, t4, light, overlay);
			}
			matrices.popPose();
		}
		matrices.popPose();
	}
}
