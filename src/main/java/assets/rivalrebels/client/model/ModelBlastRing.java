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
import net.minecraft.client.render.OverlayTexture;
import org.joml.Vector3f;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Quaternionf;
import org.joml.Vector4f;

public class ModelBlastRing {
    public static void renderModel(MatrixStack matrices, VertexConsumer buffer, float size, int segments, float thickness, float height, float pitch, float yaw, float roll, float x, float y, float z, Vector4f color, int light, int overlay) {
		matrices.push();
		float innerangle = (float) Math.PI * 2 / segments;
		Vector3f v1 = new Vector3f(0, -height, size - thickness);
		Vector3f v2 = new Vector3f(0, -height, size + thickness);
		Vector3f v3 = new Vector3f((float) Math.sin(innerangle) * (size - thickness), -height, (float) Math.cos(innerangle) * (size - thickness));
		Vector3f v4 = new Vector3f((float) Math.sin(innerangle) * (size + thickness), -height, (float) Math.cos(innerangle) * (size + thickness));
		Vector3f v5 = new Vector3f(0, +height, size - thickness);
		Vector3f v6 = new Vector3f(0, +height, size + thickness);
		Vector3f v7 = new Vector3f((float) Math.sin(innerangle) * (size - thickness), +height, (float) Math.cos(innerangle) * (size - thickness));
		Vector3f v8 = new Vector3f((float) Math.sin(innerangle) * (size + thickness), +height, (float) Math.cos(innerangle) * (size + thickness));

		matrices.translate(x, y, z);
		matrices.multiply(new Quaternionf(pitch, 1, 0, 0));
		matrices.multiply(new Quaternionf(yaw, 0, 1, 0));
		matrices.multiply(new Quaternionf(roll, 0, 0, 1));
        for (float i = 0; i < 360; i += 360F / segments) {
			matrices.push();
			matrices.multiply(new Quaternionf(i, 0, 1, 0));
			RenderHelper.addFace(buffer, v5, v6, v8, v7, color, light, overlay);
			RenderHelper.addFace(buffer, v2, v1, v3, v4, color, light, overlay);
			RenderHelper.addFace(buffer, v2, v4, v8, v6, color, light, overlay);
			RenderHelper.addFace(buffer, v3, v1, v5, v7, color, light, overlay);
			matrices.pop();
		}
		matrices.pop();
	}

    public static void renderModel(MatrixStack matrices, VertexConsumer buffer, float size, int segments, float thickness, float height, float pitch, float yaw, float roll, float x, float y, float z, int light, int overlay) {
        renderModel(matrices, buffer, size, segments, thickness, height, pitch, yaw, roll, x, y, z, new Vector4f(1, 1, 1, 1), light, overlay);
    }

    public static void renderModel(MatrixStack matrices, VertexConsumer buffer, float size, int segments, float thickness, float height, float pitch, float yaw, float roll, float x, float y, float z, int light) {
        renderModel(matrices, buffer, size, segments, thickness, height, pitch, yaw, roll, x, y, z, new Vector4f(1, 1, 1, 1), light, OverlayTexture.DEFAULT_UV);
    }
}
