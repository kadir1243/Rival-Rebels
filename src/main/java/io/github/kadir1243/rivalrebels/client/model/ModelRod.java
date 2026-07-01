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
import io.github.kadir1243.rivalrebels.client.renderhelper.TextureVertice;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.objects.ObjectBooleanPair;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

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
    private static final Map<ObjectBooleanPair<Identifier>, Supplier<List<QuadHelper.BakedQuadWrapper>>> MAP = new HashMap<>();

    public static void render(PoseStack poseStack, SubmitNodeCollector nodeCollector, Identifier texture, RenderType renderType, int light, int overlay) {
        render(poseStack, nodeCollector, texture, renderType, light, overlay, true);
    }

    public static void render(PoseStack poseStack, SubmitNodeCollector nodeCollector, Identifier texture, RenderType renderType, int light, int overlay, boolean rendersecondcap) {
        QuadHelper.submitQuadSupplier(nodeCollector, poseStack, renderType, MAP.computeIfAbsent(ObjectBooleanPair.of(texture, rendersecondcap), t -> QuadHelper.createQuads(Sheets.BLOCKS_MAPPER.apply(t.left()), buf -> {
            for (float i = 0; i < 360; i += 360F / numOfSegs) {
                Transformation transformation = new Transformation(null, Axis.YP.rotationDegrees(i), null, null);
                QuadHelper.addFace(buf, transformation, v0, vd1, v1, v0, t1, t3, t2, t1);
                QuadHelper.addFace(buf, transformation, vd1, vd2, v2, v1, t2, t4, t5, t3);
                QuadHelper.addFace(buf, transformation, vd2, vd3, v3, v2, t4, t6, t7, t5);
                QuadHelper.addFace(buf, transformation, vd3, vd4, v4, v3, t6, t8, t9, t7);
                if (t.rightBoolean()) {
                    QuadHelper.addFace(buf, transformation, v7, v6, vd6, v7, t1, t3, t2, t1);
                    QuadHelper.addFace(buf, transformation, v6, v5, vd5, vd6, t2, t4, t5, t3);
                    QuadHelper.addFace(buf, transformation, v5, v4, vd4, vd5, t4, t6, t7, t5);
                }
            }
        })), light, overlay);

    }
}
