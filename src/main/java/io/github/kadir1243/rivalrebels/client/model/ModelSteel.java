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
package io.github.kadir1243.rivalrebels.client.model;

import io.github.kadir1243.rivalrebels.client.renderhelper.RenderHelper;
import io.github.kadir1243.rivalrebels.client.renderhelper.TextureVertice;
import org.joml.Vector3f;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelSteel {
    private static final float		s			= 0.5F;

	private static final Vector3f		v1			= new Vector3f(s, s, s);
	private static final Vector3f		v2			= new Vector3f(s, s, -s);
	private static final Vector3f		v3			= new Vector3f(-s, s, -s);
	private static final Vector3f		v4			= new Vector3f(-s, s, s);

	private static final Vector3f		v5			= new Vector3f(s, -s, s);
	private static final Vector3f		v6			= new Vector3f(s, -s, -s);
	private static final Vector3f		v7			= new Vector3f(-s, -s, -s);
	private static final Vector3f		v8			= new Vector3f(-s, -s, s);

	public static void renderModel(PoseStack matrices, VertexConsumer buffer, int light, int overlay) {
		matrices.pushPose();

		addVertex(matrices, buffer, v1, 0, 0, light, overlay);
		addVertex(matrices, buffer, v5, 1, 0, light, overlay);
		addVertex(matrices, buffer, v8, 1, 1, light, overlay);
		addVertex(matrices, buffer, v4, 0, 1, light, overlay);
		addVertex(matrices, buffer, v4, 0, 0, light, overlay);
		addVertex(matrices, buffer, v8, 1, 0, light, overlay);
		addVertex(matrices, buffer, v7, 1, 1, light, overlay);
		addVertex(matrices, buffer, v3, 0, 1, light, overlay);
		addVertex(matrices, buffer, v3, 0, 0, light, overlay);
		addVertex(matrices, buffer, v7, 1, 0, light, overlay);
		addVertex(matrices, buffer, v6, 1, 1, light, overlay);
		addVertex(matrices, buffer, v2, 0, 1, light, overlay);
		addVertex(matrices, buffer, v2, 0, 0, light, overlay);
		addVertex(matrices, buffer, v6, 1, 0, light, overlay);
		addVertex(matrices, buffer, v5, 1, 1, light, overlay);
		addVertex(matrices, buffer, v1, 0, 1, light, overlay);
		addVertex(matrices, buffer, v3, 1, 0, light, overlay);
		addVertex(matrices, buffer, v2, 1, 1, light, overlay);
		addVertex(matrices, buffer, v5, 0, 0, light, overlay);
		addVertex(matrices, buffer, v8, 0, 1, light, overlay);

		matrices.popPose();
	}

	private static void addVertex(PoseStack poseStack, VertexConsumer buffer, Vector3f v, float tx, float ty, int light, int overlay) {
        RenderHelper.addVertice(poseStack, buffer, v.mul(0.999F, new Vector3f()), new TextureVertice(tx, ty), light, overlay);
	}
}
