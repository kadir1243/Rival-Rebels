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
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class ModelRod {
    private static final int numOfSegs = 16;
    private static final float deg = Mth.TWO_PI / numOfSegs;
    private static final Vector3f vd1 = new Vector3f(0.03125f * 8 * Mth.cos(deg), 0.03125f * 9, 0.03125f * 8 * Mth.sin(deg));
    private static final Vector3f vd2 = new Vector3f(0.03125f * 9 * Mth.cos(deg), 0.03125f * 6, 0.03125f * 9 * Mth.sin(deg));
    private static final Vector3f vd3 = new Vector3f(0.03125f * 8 * Mth.cos(deg), 0.03125f * 5, 0.03125f * 8 * Mth.sin(deg));
    private static final Vector3f vd4 = new Vector3f(0.03125f * 8 * Mth.cos(deg), 0.03125f * -5, 0.03125f * 8 * Mth.sin(deg));
    private static final Vector3f vd5 = new Vector3f(0.03125f * 9 * Mth.cos(deg), 0.03125f * -6, 0.03125f * 9 * Mth.sin(deg));
    private static final Vector3f vd6 = new Vector3f(0.03125f * 8 * Mth.cos(deg), 0.03125f * -9, 0.03125f * 8 * Mth.sin(deg));
    private static final TextureVertice t1 = new TextureVertice(0.03125f * 2, 0.03125f * 0);
    private static final TextureVertice t2 = new TextureVertice(0.03125f * 1, 0.03125f * 3);
    private static final TextureVertice t3 = new TextureVertice(0.03125f * 3, 0.03125f * 3);
    private static final TextureVertice t4 = new TextureVertice(0.03125f * 0, 0.03125f * 6);
    private static final TextureVertice t5 = new TextureVertice(0.03125f * 4, 0.03125f * 6);
    private static final TextureVertice t6 = new TextureVertice(0.03125f * 1, 0.03125f * 7);
    private static final TextureVertice t7 = new TextureVertice(0.03125f * 3, 0.03125f * 7);
    private static final TextureVertice t8 = new TextureVertice(0.03125f * 1, 0.03125f * 19);
    private static final TextureVertice t9 = new TextureVertice(0.03125f * 3, 0.03125f * 19);
    private static final Vector3f v0 = new Vector3f(0f, 0.03125f * 10, 0f);
    private static final Vector3f v1 = new Vector3f(0.03125f * 8, 0.03125f * 9, 0f);
    private static final Vector3f v2 = new Vector3f(0.03125f * 9, 0.03125f * 6, 0f);
    private static final Vector3f v3 = new Vector3f(0.03125f * 8, 0.03125f * 5, 0f);
    private static final Vector3f v4 = new Vector3f(0.03125f * 8, 0.03125f * -5, 0f);
    private static final Vector3f v5 = new Vector3f(0.03125f * 9, 0.03125f * -6, 0f);
    private static final Vector3f v6 = new Vector3f(0.03125f * 8, 0.03125f * -9, 0f);
    private static final Vector3f v7 = new Vector3f(0f, 0.03125f * -10, 0f);

    public static void render(PoseStack matrices, VertexConsumer buffer, int light, int overlay) {
        render(matrices, buffer, light, overlay, true);
    }

    public static void render(PoseStack matrices, VertexConsumer buffer, int light, int overlay, boolean rendersecondcap) {
        for (float i = 0; i < 360; i += 360 / numOfSegs) {
            matrices.pushPose();
            matrices.mulPose(Axis.YP.rotationDegrees(i));
            RenderHelper.addFace(matrices, buffer, v0, vd1, v1, v0, t1, t3, t2, t1, light, overlay);
            RenderHelper.addFace(matrices, buffer, vd1, vd2, v2, v1, t2, t4, t5, t3, light, overlay);
            RenderHelper.addFace(matrices, buffer, vd2, vd3, v3, v2, t4, t6, t7, t5, light, overlay);
            RenderHelper.addFace(matrices, buffer, vd3, vd4, v4, v3, t6, t8, t9, t7, light, overlay);
            if (rendersecondcap) {
                RenderHelper.addFace(matrices, buffer, v7, v6, vd6, v7, t1, t3, t2, t1, light, overlay);
                RenderHelper.addFace(matrices, buffer, v6, v5, vd5, vd6, t2, t4, t5, t3, light, overlay);
                RenderHelper.addFace(matrices, buffer, v5, v4, vd4, vd5, t4, t6, t7, t5, light, overlay);
            }

            matrices.popPose();
        }
    }
}
