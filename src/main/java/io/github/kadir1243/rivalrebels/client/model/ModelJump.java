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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Vector3f;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelJump {
	private static final float s = 0.501F;
	private static final float t = 0.25F;

	private static final Vector3f v1 = new Vector3f(s, t, s);
	private static final Vector3f v2 = new Vector3f(s, t, -s);
	private static final Vector3f v3 = new Vector3f(-s, t, -s);
	private static final Vector3f v4 = new Vector3f(-s, t, s);

	private static final Vector3f v5 = new Vector3f(s, -t, s);
	private static final Vector3f v6 = new Vector3f(s, -t, -s);
	private static final Vector3f v7 = new Vector3f(-s, -t, -s);
	private static final Vector3f v8 = new Vector3f(-s, -t, s);

    public static void renderModel(PoseStack pose, VertexConsumer buffer, int light, int overlay) {
        RenderHelper.addFace(pose, buffer, v2, v1, v4, v3, new TextureVertice(0, 0), new TextureVertice(1, 0), new TextureVertice(1, 1), new TextureVertice(0, 1), light, overlay);
        RenderHelper.addFace(pose, buffer, v5, v6, v7, v8, new TextureVertice(0, 0), new TextureVertice(1, 0), new TextureVertice(1, 1), new TextureVertice(0, 1), light, overlay);
	}
}
