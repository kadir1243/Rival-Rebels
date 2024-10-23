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
public class ModelNukeCloud
{
	private static final float[]	topx		= { 0.7f, 1.2f, 1.2f, 1.2f, 1.75f, 2.5f, 3f, 3.5f, 3.5f, 3f, 2.25f, 2f, 1.75f, 1.25f, 0f };
	private static final float[]	topy		= { 6.5f, 5f, 6f, 7f, 7.1f, 7.25f, 7.5f, 8.25f, 9f, 9.25f, 9.4f, 9.75f, 10.1f, 10.25f, 10.25f };
	private static final float[]	bottomx		= { 1.22f, 1.12f, 1.03f, 0.95f, 0.88f, 0.82f, 0.77f, 0.73f, 0.71f, 0.7f, 0.7f };
	private static final float[]	bottomy		= { -4f, -3f, -2f, -1f, 0f, 1f, 2f, 3.25f, 4.5f, 5.5f, 6.5f };

	private static final int		segments	= 18;
	private static final float	deg			= Mth.TWO_PI / segments;
	private static final float	sin			= Mth.sin(deg);
	private static final float	cos			= Mth.cos(deg);
	private static final float	add			= 360 / segments;

	public static void renderTop(PoseStack matrices, VertexConsumer buffer, int light, int overlay)
	{
		for (float i = 0; i < segments; i++)
		{
			matrices.pushPose();
			matrices.mulPose(Axis.YP.rotationDegrees(add * i));
			for (int f = 1; f < topx.length; f++)
			{
				TextureVertice t1 = new TextureVertice((1f / segments) * (i - 1), (1f / topx.length) * f);
				TextureVertice t2 = new TextureVertice((1f / segments) * (i - 1), (1f / topx.length) * (f - 1));
				TextureVertice t3 = new TextureVertice((1f / segments) * i, (1f / topx.length) * (f - 1));
				TextureVertice t4 = new TextureVertice((1f / segments) * i, (1f / topx.length) * f);
				RenderHelper.addFace(matrices, buffer, new Vector3f(0f, topy[f], topx[f]),
						new Vector3f(0f, topy[f - 1], topx[f - 1]),
						new Vector3f(topx[f - 1] * sin, topy[f - 1], topx[f - 1] * cos),
						new Vector3f(topx[f] * sin, topy[f], topx[f] * cos), t1, t2, t3, t4, light, overlay);
			}
			matrices.popPose();
		}
	}

	public static void renderBottom(PoseStack matrices, VertexConsumer buffer, int light, int overlay)
	{
		for (float i = 0; i < segments; i++)
		{
			matrices.pushPose();
			matrices.mulPose(Axis.YP.rotationDegrees(add * i));
			for (int f = 1; f < bottomx.length; f++)
			{
				TextureVertice t1 = new TextureVertice((1f / segments) * (i - 1), (1f / bottomx.length) * f);
				TextureVertice t2 = new TextureVertice((1f / segments) * (i - 1), (1f / bottomx.length) * (f - 1));
				TextureVertice t3 = new TextureVertice((1f / segments) * i, (1f / bottomx.length) * (f - 1));
				TextureVertice t4 = new TextureVertice((1f / segments) * i, (1f / bottomx.length) * f);
				RenderHelper.addFace(matrices, buffer, new Vector3f(0f, bottomy[f], bottomx[f]),
						new Vector3f(0f, bottomy[f - 1], bottomx[f - 1]),
						new Vector3f(bottomx[f - 1] * sin, bottomy[f - 1], bottomx[f - 1] * cos),
						new Vector3f(bottomx[f] * sin, bottomy[f], bottomx[f] * cos), t1, t2, t3, t4, light, overlay);
			}
			matrices.popPose();
		}
	}
}