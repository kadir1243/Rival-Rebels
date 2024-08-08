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
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class ModelBlastRing {
    public static void renderModel(PoseStack matrices, VertexConsumer buffer, float size, int segments, float thickness, float height, float pitch, float yaw, float roll, float x, float y, float z, int color, int light, int overlay) {
		matrices.pushPose();
		float innerangle = Mth.TWO_PI / segments;
		Vector3f v1 = new Vector3f(0, -height, size - thickness);
		Vector3f v2 = new Vector3f(0, -height, size + thickness);
		Vector3f v3 = new Vector3f(Mth.sin(innerangle) * (size - thickness), -height, Mth.cos(innerangle) * (size - thickness));
		Vector3f v4 = new Vector3f(Mth.sin(innerangle) * (size + thickness), -height, Mth.cos(innerangle) * (size + thickness));
		Vector3f v5 = new Vector3f(0, +height, size - thickness);
		Vector3f v6 = new Vector3f(0, +height, size + thickness);
		Vector3f v7 = new Vector3f(Mth.sin(innerangle) * (size - thickness), +height, Mth.cos(innerangle) * (size - thickness));
		Vector3f v8 = new Vector3f(Mth.sin(innerangle) * (size + thickness), +height, Mth.cos(innerangle) * (size + thickness));

		matrices.translate(x, y, z);
		matrices.mulPose(Axis.XP.rotationDegrees(pitch));
		matrices.mulPose(Axis.YP.rotationDegrees(yaw));
		matrices.mulPose(Axis.ZP.rotationDegrees(roll));
        for (float i = 0; i < 360; i += 360F / segments) {
			matrices.pushPose();
			matrices.mulPose(Axis.YP.rotationDegrees(i));
			RenderHelper.addFace(matrices, buffer, v5, v6, v8, v7, color, light, overlay);
			RenderHelper.addFace(matrices, buffer, v2, v1, v3, v4, color, light, overlay);
			RenderHelper.addFace(matrices, buffer, v2, v4, v8, v6, color, light, overlay);
			RenderHelper.addFace(matrices, buffer, v3, v1, v5, v7, color, light, overlay);
			matrices.popPose();
		}
		matrices.popPose();
	}

    public static void renderModel(PoseStack matrices, VertexConsumer buffer, float size, int segments, float thickness, float height, float pitch, float yaw, float roll, float x, float y, float z, int light, int overlay) {
        renderModel(matrices, buffer, size, segments, thickness, height, pitch, yaw, roll, x, y, z, CommonColors.WHITE, light, overlay);
    }

    public static void renderModel(PoseStack matrices, VertexConsumer buffer, float size, int segments, float thickness, float height, float pitch, float yaw, float roll, float x, float y, float z, int light) {
        renderModel(matrices, buffer, size, segments, thickness, height, pitch, yaw, roll, x, y, z, CommonColors.WHITE, light, OverlayTexture.NO_OVERLAY);
    }
}
