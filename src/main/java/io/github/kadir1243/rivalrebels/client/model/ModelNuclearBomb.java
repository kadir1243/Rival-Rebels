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

import io.github.kadir1243.rivalrebels.RRConfig;
import io.github.kadir1243.rivalrebels.client.renderhelper.QuadHelper;
import io.github.kadir1243.rivalrebels.client.renderhelper.RenderHelper;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRRenderTypes;
import io.github.kadir1243.rivalrebels.client.renderhelper.TextureVertice;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;
import org.joml.Vector3f;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.texture.OverlayTexture;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ModelNuclearBomb {
    private static final float s = 0.5F;
    private static final float g = 0.0625F;
    private static final Vector3f v0 = new Vector3f(0, g * 22, 0);

    private static final Vector3f v1 = new Vector3f(g * 3, g * 19, g * 3);
    private static final Vector3f v2 = new Vector3f(g * 3, g * 19, -g * 3);
    private static final Vector3f v3 = new Vector3f(-g * 3, g * 19, -g * 3);
    private static final Vector3f v4 = new Vector3f(-g * 3, g * 19, g * 3);

    private static final Vector3f v5 = new Vector3f(g * 7, g * 7, g * 7);
    private static final Vector3f v6 = new Vector3f(g * 7, g * 7, -g * 7);
    private static final Vector3f v7 = new Vector3f(-g * 7, g * 7, -g * 7);
    private static final Vector3f v8 = new Vector3f(-g * 7, g * 7, g * 7);

    private static final Vector3f v21 = new Vector3f(g * 7, -g * 8, g * 7);
    private static final Vector3f v22 = new Vector3f(g * 7, -g * 8, -g * 7);
    private static final Vector3f v23 = new Vector3f(-g * 7, -g * 8, -g * 7);
    private static final Vector3f v24 = new Vector3f(-g * 7, -g * 8, g * 7);

    private static final Vector3f v9 = new Vector3f(g * 4, -g * 18, g * 4);
    private static final Vector3f v10 = new Vector3f(g * 4, -g * 18, -g * 4);
    private static final Vector3f v11 = new Vector3f(-g * 4, -g * 18, -g * 4);
    private static final Vector3f v12 = new Vector3f(-g * 4, -g * 18, g * 4);

    private static final Vector3f v13 = new Vector3f(s, -g * 7, s);
    private static final Vector3f v14 = new Vector3f(s, -g * 7, -s);
    private static final Vector3f v15 = new Vector3f(-s, -g * 7, -s);
    private static final Vector3f v16 = new Vector3f(-s, -g * 7, s);

    private static final Vector3f v17 = new Vector3f(s, -g * 24, s);
    private static final Vector3f v18 = new Vector3f(s, -g * 24, -s);
    private static final Vector3f v19 = new Vector3f(-s, -g * 24, -s);
    private static final Vector3f v20 = new Vector3f(-s, -g * 24, s);

    private static final Supplier<QuadHelper.BakedData> BAKED_MODEL_BOMB = QuadHelper.createBakedModel(buffer -> {
        int itemIcon = 39;
        float var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
        float var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
        float var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
        float var6 = (itemIcon / 16 * 16 + 16) / 256.0F;
        QuadHelper.addVertice(buffer, v2, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v1, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v5, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v6, new TextureVertice(var3, var5));

        QuadHelper.addVertice(buffer, v3, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v2, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v6, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v7, new TextureVertice(var3, var5));

        QuadHelper.addVertice(buffer, v4, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v3, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v7, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v8, new TextureVertice(var3, var5));

        QuadHelper.addVertice(buffer, v1, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v4, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v8, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v5, new TextureVertice(var3, var5));

        itemIcon = 40;
        var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
        var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
        var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
        var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

        QuadHelper.addVertice(buffer, v6, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v5, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v21, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v22, new TextureVertice(var3, var5));

        QuadHelper.addVertice(buffer, v7, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v6, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v22, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v23, new TextureVertice(var3, var5));

        QuadHelper.addVertice(buffer, v8, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v7, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v23, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v24, new TextureVertice(var3, var5));

        QuadHelper.addVertice(buffer, v5, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v8, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v24, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v21, new TextureVertice(var3, var5));

        QuadHelper.addVertice(buffer, v22, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v21, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v9, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v10, new TextureVertice(var3, var5));

        QuadHelper.addVertice(buffer, v23, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v22, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v10, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v11, new TextureVertice(var3, var5));

        QuadHelper.addVertice(buffer, v24, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v23, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v11, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v12, new TextureVertice(var3, var5));

        QuadHelper.addVertice(buffer, v21, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v24, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v12, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v9, new TextureVertice(var3, var5));

        itemIcon = 38;
        var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
        var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
        var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
        var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

        QuadHelper.addVertice(buffer, v10, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v9, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v12, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v11, new TextureVertice(var3, var5));

        itemIcon = 41;
        var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
        var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
        var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
        var6 = (itemIcon / 16 * 16 + 16) / 256.0F;
        float o = 0.999F;

        QuadHelper.addVertice(buffer, v13.mul(o, new Vector3f()), new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v14.mul(o, new Vector3f()), new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v18.mul(o, new Vector3f()), new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v17.mul(o, new Vector3f()), new TextureVertice(var3, var5));

        QuadHelper.addVertice(buffer, v14.mul(o, new Vector3f()), new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v15.mul(o, new Vector3f()), new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v19.mul(o, new Vector3f()), new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v18.mul(o, new Vector3f()), new TextureVertice(var3, var5));

        QuadHelper.addVertice(buffer, v15.mul(o, new Vector3f()), new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v16.mul(o, new Vector3f()), new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v20.mul(o, new Vector3f()), new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v19.mul(o, new Vector3f()), new TextureVertice(var3, var5));

        QuadHelper.addVertice(buffer, v16.mul(o, new Vector3f()), new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v13.mul(o, new Vector3f()), new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v17.mul(o, new Vector3f()), new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v20.mul(o, new Vector3f()), new TextureVertice(var3, var5));

        QuadHelper.addVertice(buffer, v14, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v13, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v17, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v18, new TextureVertice(var3, var5));

        QuadHelper.addVertice(buffer, v15, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v14, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v18, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v19, new TextureVertice(var3, var5));

        QuadHelper.addVertice(buffer, v16, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v15, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v19, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v20, new TextureVertice(var3, var5));

        QuadHelper.addVertice(buffer, v13, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v16, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v20, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v17, new TextureVertice(var3, var5));

        itemIcon = 42;
        var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
        var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
        var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
        var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

        QuadHelper.addVertice(buffer, v13, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v15, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v19, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v17, new TextureVertice(var3, var5));

        QuadHelper.addVertice(buffer, v16, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v14, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v18, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v20, new TextureVertice(var3, var5));

        QuadHelper.addVertice(buffer, v15, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v13, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v17, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v19, new TextureVertice(var3, var5));

        QuadHelper.addVertice(buffer, v14, new TextureVertice(var3, var6));
        QuadHelper.addVertice(buffer, v16, new TextureVertice(var4, var6));
        QuadHelper.addVertice(buffer, v20, new TextureVertice(var4, var5));
        QuadHelper.addVertice(buffer, v18, new TextureVertice(var3, var5));
    });

    public static void renderModel(PoseStack poseStack, SubmitNodeCollector nodeCollector, Identifier texture, int light, boolean hasFuse) {
        poseStack.pushPose();
        poseStack.scale(RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale());
        int itemIcon;
        float var3;
        float var4;
        float var5;
        float var6;

        poseStack.pushPose();
        poseStack.scale(1.01f, 1.01f, 1.01f);

        int overlay = OverlayTexture.NO_OVERLAY;

        ObjModels.submit(nodeCollector, RenderTypes.entitySolid(texture), BAKED_MODEL_BOMB.get().quadCollection(), poseStack, CommonColors.WHITE, light, overlay);

        if (!hasFuse) {
            itemIcon = 37;
            var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
            var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
            var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
            var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

            nodeCollector.submitCustomGeometry(poseStack, RenderTypes.entitySolid(texture), (pose, consumer) -> {
                RenderHelper.addVertice(pose, consumer, v1, new TextureVertice(var3, var6), light, overlay);
                RenderHelper.addVertice(pose, consumer, v2, new TextureVertice(var4, var6), light, overlay);
                RenderHelper.addVertice(pose, consumer, v3, new TextureVertice(var4, var5), light, overlay);
                RenderHelper.addVertice(pose, consumer, v4, new TextureVertice(var3, var5), light, overlay);
            });
        } else {
            itemIcon = 43;
            var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
            var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
            var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
            var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

            nodeCollector.submitCustomGeometry(poseStack, RRRenderTypes.RENDER_SOLID_TRIANGLES.apply(texture), (pose, consumer) -> {
                RenderHelper.addVertice(pose, consumer, v0, new TextureVertice(var3, var6), light, overlay);
                RenderHelper.addVertice(pose, consumer, v1, new TextureVertice(var4, var6), light, overlay);
                RenderHelper.addVertice(pose, consumer, v2, new TextureVertice(var4, var5), light, overlay);

                RenderHelper.addVertice(pose, consumer, v0, new TextureVertice(var3, var6), light, overlay);
                RenderHelper.addVertice(pose, consumer, v2, new TextureVertice(var4, var5), light, overlay);
                RenderHelper.addVertice(pose, consumer, v3, new TextureVertice(var4, var6), light, overlay);

                RenderHelper.addVertice(pose, consumer, v0, new TextureVertice(var3, var6), light, overlay);
                RenderHelper.addVertice(pose, consumer, v3, new TextureVertice(var3, var5), light, overlay);
                RenderHelper.addVertice(pose, consumer, v4, new TextureVertice(var4, var5), light, overlay);

                RenderHelper.addVertice(pose, consumer, v0, new TextureVertice(var3, var6), light, overlay);
                RenderHelper.addVertice(pose, consumer, v4, new TextureVertice(var4, var5), light, overlay);
                RenderHelper.addVertice(pose, consumer, v1, new TextureVertice(var4, var6), light, overlay);
            });
        }
        poseStack.popPose();
        poseStack.popPose();
    }
}
