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
import org.joml.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModelNukeCrate
{
	private static final float			s	= 0.5F;
	private static final Vector3f			v1	= new Vector3f(s, s, s);
	private static final Vector3f			v2	= new Vector3f(s, s, -s);
	private static final Vector3f			v3	= new Vector3f(-s, s, -s);
	private static final Vector3f			v4	= new Vector3f(-s, s, s);
	private static final Vector3f			v5	= new Vector3f(s, -s, s);
	private static final Vector3f			v6	= new Vector3f(s, -s, -s);
	private static final Vector3f			v7	= new Vector3f(-s, -s, -s);
	private static final Vector3f			v8	= new Vector3f(-s, -s, s);
	private static final TextureVertice	t1	= new TextureVertice(0, 0);
	private static final TextureVertice	t2	= new TextureVertice(1, 0);
	private static final TextureVertice	t3	= new TextureVertice(1, 1);
	private static final TextureVertice	t4	= new TextureVertice(0, 1);

	public static void renderModelA(PoseStack matrices, VertexConsumer buffer, int light, int overlay)
	{
		matrices.pushPose();
		RenderHelper.addFace(matrices, buffer, v4, v8, v5, v1, t1, t2, t3, t4, light, overlay);
		RenderHelper.addFace(matrices, buffer, v3, v7, v8, v4, t1, t2, t3, t4, light, overlay);
		RenderHelper.addFace(matrices, buffer, v2, v6, v7, v3, t1, t2, t3, t4, light, overlay);
		RenderHelper.addFace(matrices, buffer, v1, v5, v6, v2, t1, t2, t3, t4, light, overlay);
		matrices.popPose();
	}

	public static void renderModelB(PoseStack matrices, VertexConsumer buffer, int light, int overlay) {
		matrices.pushPose();
		RenderHelper.addFace(matrices, buffer, v1, v2, v3, v4, t1, t2, t3, t4, light, overlay);
		RenderHelper.addFace(matrices, buffer, v8, v7, v6, v5, t1, t2, t3, t4, light, overlay);
		matrices.popPose();
	}
}
