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

import com.mojang.math.Transformation;
import io.github.kadir1243.rivalrebels.client.renderhelper.QuadHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.client.model.pipeline.TransformingVertexPipeline;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class ModelBlastRing {
    private static final Map<DataToRender, QuadHelper.BakedData> CACHE = new HashMap<>();

    public static void renderModel(PoseStack matrices, SubmitNodeCollector nodeCollector, RenderType renderType, float size, int segments, float thickness, float height, float pitch, float yaw, float roll, float x, float y, float z, int color, int light, int overlay) {
		matrices.pushPose();

		matrices.translate(x, y, z);
		matrices.mulPose(Axis.XP.rotationDegrees(pitch));
		matrices.mulPose(Axis.YP.rotationDegrees(yaw));
		matrices.mulPose(Axis.ZP.rotationDegrees(roll));

        var bakedModel = CACHE.computeIfAbsent(new DataToRender(size, segments, thickness, height), key -> QuadHelper.createBakedModel(bakingBuffer -> {
            float innerangle = Mth.TWO_PI / key.segments;
            Vector3f v1 = new Vector3f(0, -key.height, key.size - key.thickness);
            Vector3f v2 = new Vector3f(0, -key.height, key.size + key.thickness);
            Vector3f v3 = new Vector3f(Mth.sin(innerangle) * (key.size - key.thickness), -key.height, Mth.cos(innerangle) * (key.size - key.thickness));
            Vector3f v4 = new Vector3f(Mth.sin(innerangle) * (key.size + key.thickness), -key.height, Mth.cos(innerangle) * (key.size + key.thickness));
            Vector3f v5 = new Vector3f(0, +key.height, key.size - key.thickness);
            Vector3f v6 = new Vector3f(0, +key.height, key.size + key.thickness);
            Vector3f v7 = new Vector3f(Mth.sin(innerangle) * (key.size - key.thickness), +key.height, Mth.cos(innerangle) * (key.size - key.thickness));
            Vector3f v8 = new Vector3f(Mth.sin(innerangle) * (key.size + key.thickness), +key.height, Mth.cos(innerangle) * (key.size + key.thickness));
            for (float i = 0; i < 360; i += 360F / key.segments) {
                Transformation transformation = new Transformation(null, Axis.YP.rotationDegrees(i), null, null);
                TransformingVertexPipeline rotatedBuffer = new TransformingVertexPipeline(bakingBuffer, transformation);
                QuadHelper.addFace(rotatedBuffer, v5, v6, v8, v7, color);
                QuadHelper.addFace(rotatedBuffer, v2, v1, v3, v4, color);
                QuadHelper.addFace(rotatedBuffer, v2, v4, v8, v6, color);
                QuadHelper.addFace(rotatedBuffer, v3, v1, v5, v7, color);
            }
        }).get());
        ObjModels.submit(nodeCollector, renderType, bakedModel.quadCollection(), matrices, CommonColors.WHITE, light, overlay);
        matrices.popPose();
	}

    public static void renderModel(PoseStack matrices, SubmitNodeCollector nodeCollector, RenderType renderType, float size, int segments, float thickness, float height, float pitch, float yaw, float roll, float x, float y, float z, int light, int overlay) {
        renderModel(matrices, nodeCollector, renderType, size, segments, thickness, height, pitch, yaw, roll, x, y, z, CommonColors.WHITE, light, overlay);
    }

    public static void renderModel(PoseStack matrices, SubmitNodeCollector nodeCollector, RenderType renderType, float size, int segments, float thickness, float height, float pitch, float yaw, float roll, float x, float y, float z, int light) {
        renderModel(matrices, nodeCollector, renderType, size, segments, thickness, height, pitch, yaw, roll, x, y, z, CommonColors.WHITE, light, OverlayTexture.NO_OVERLAY);
    }

    private record DataToRender(float size, int segments, float thickness, float height) {
        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            DataToRender that = (DataToRender) o;
            return Float.compare(size(), that.size()) == 0 && segments() == that.segments() && Float.compare(height(), that.height()) == 0 && Float.compare(thickness(), that.thickness()) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(size(), segments(), thickness(), height());
        }
    }
}
