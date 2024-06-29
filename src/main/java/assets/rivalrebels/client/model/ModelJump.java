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
import org.joml.Vector4f;

@Environment(EnvType.CLIENT)
public class ModelJump
{
	private static final float	s	= 0.501F;
	private static final float	t	= 0.25F;

	private static final Vector3f	v1	= new Vector3f(s, t, s);
	private static final Vector3f	v2	= new Vector3f(s, t, -s);
	private static final Vector3f	v3	= new Vector3f(-s, t, -s);
	private static final Vector3f	v4	= new Vector3f(-s, t, s);

	private static final Vector3f	v5	= new Vector3f(s, -t, s);
	private static final Vector3f	v6	= new Vector3f(s, -t, -s);
	private static final Vector3f	v7	= new Vector3f(-s, -t, -s);
	private static final Vector3f	v8	= new Vector3f(-s, -t, s);

	public static void renderModel(PoseStack pose, VertexConsumer buffer, int light, int overlay) {
		RenderHelper.addVertice(pose, buffer, v2, new TextureVertice(0, 0), new Vector4f(1, 1, 1, 1), light, overlay);
		RenderHelper.addVertice(pose, buffer, v1, new TextureVertice(1, 0), new Vector4f(1, 1, 1, 1), light, overlay);
		RenderHelper.addVertice(pose, buffer, v4, new TextureVertice(1, 1), new Vector4f(1, 1, 1, 1), light, overlay);
		RenderHelper.addVertice(pose, buffer, v3, new TextureVertice(0, 1), new Vector4f(1, 1, 1, 1), light, overlay);
		RenderHelper.addVertice(pose, buffer, v5, new TextureVertice(0, 0), new Vector4f(1, 1, 1, 1), light, overlay);
		RenderHelper.addVertice(pose, buffer, v6, new TextureVertice(1, 0), new Vector4f(1, 1, 1, 1), light, overlay);
		RenderHelper.addVertice(pose, buffer, v7, new TextureVertice(1, 1), new Vector4f(1, 1, 1, 1), light, overlay);
		RenderHelper.addVertice(pose, buffer, v8, new TextureVertice(0, 1), new Vector4f(1, 1, 1, 1), light, overlay);
	}
}
