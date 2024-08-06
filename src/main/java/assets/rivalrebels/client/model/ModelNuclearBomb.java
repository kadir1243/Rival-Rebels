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

import assets.rivalrebels.RRConfig;
import assets.rivalrebels.client.renderhelper.RenderHelper;
import assets.rivalrebels.client.renderhelper.TextureVertice;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.texture.OverlayTexture;

@Environment(EnvType.CLIENT)
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

    public static void renderModel(PoseStack pose, MultiBufferSource vertexConsumers, ResourceLocation texture, int light, boolean hasFuse) {
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderType.entitySolid(texture));
        pose.pushPose();
        pose.scale(RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale());
        int itemIcon = 39;
        float var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
        float var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
        float var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
        float var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

        pose.pushPose();
        pose.scale(1.01f, 1.01f, 1.01f);

        int overlay = OverlayTexture.NO_OVERLAY;
        RenderHelper.addVertice(pose, buffer, v2, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v1, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v5, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v6, new TextureVertice(var3, var5), light, overlay);

        RenderHelper.addVertice(pose, buffer, v3, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v2, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v6, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v7, new TextureVertice(var3, var5), light, overlay);

        RenderHelper.addVertice(pose, buffer, v4, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v3, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v7, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v8, new TextureVertice(var3, var5), light, overlay);

        RenderHelper.addVertice(pose, buffer, v1, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v4, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v8, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v5, new TextureVertice(var3, var5), light, overlay);

        itemIcon = 40;
        var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
        var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
        var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
        var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

        RenderHelper.addVertice(pose, buffer, v6, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v5, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v21, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v22, new TextureVertice(var3, var5), light, overlay);

        RenderHelper.addVertice(pose, buffer, v7, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v6, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v22, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v23, new TextureVertice(var3, var5), light, overlay);

        RenderHelper.addVertice(pose, buffer, v8, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v7, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v23, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v24, new TextureVertice(var3, var5), light, overlay);

        RenderHelper.addVertice(pose, buffer, v5, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v8, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v24, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v21, new TextureVertice(var3, var5), light, overlay);

        RenderHelper.addVertice(pose, buffer, v22, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v21, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v9, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v10, new TextureVertice(var3, var5), light, overlay);

        RenderHelper.addVertice(pose, buffer, v23, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v22, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v10, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v11, new TextureVertice(var3, var5), light, overlay);

        RenderHelper.addVertice(pose, buffer, v24, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v23, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v11, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v12, new TextureVertice(var3, var5), light, overlay);

        RenderHelper.addVertice(pose, buffer, v21, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v24, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v12, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v9, new TextureVertice(var3, var5), light, overlay);

        itemIcon = 38;
        var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
        var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
        var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
        var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

        RenderHelper.addVertice(pose, buffer, v10, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v9, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v12, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v11, new TextureVertice(var3, var5), light, overlay);

        itemIcon = 41;
        var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
        var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
        var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
        var6 = (itemIcon / 16 * 16 + 16) / 256.0F;
        float o = 0.999F;

        RenderHelper.addVertice(pose, buffer, v13.mul(o, new Vector3f()), new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v14.mul(o, new Vector3f()), new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v18.mul(o, new Vector3f()), new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v17.mul(o, new Vector3f()), new TextureVertice(var3, var5), light, overlay);

        RenderHelper.addVertice(pose, buffer, v14.mul(o, new Vector3f()), new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v15.mul(o, new Vector3f()), new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v19.mul(o, new Vector3f()), new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v18.mul(o, new Vector3f()), new TextureVertice(var3, var5), light, overlay);

        RenderHelper.addVertice(pose, buffer, v15.mul(o, new Vector3f()), new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v16.mul(o, new Vector3f()), new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v20.mul(o, new Vector3f()), new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v19.mul(o, new Vector3f()), new TextureVertice(var3, var5), light, overlay);

        RenderHelper.addVertice(pose, buffer, v16.mul(o, new Vector3f()), new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v13.mul(o, new Vector3f()), new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v17.mul(o, new Vector3f()), new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v20.mul(o, new Vector3f()), new TextureVertice(var3, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v14, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v13, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v17, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v18, new TextureVertice(var3, var5), light, overlay);

        RenderHelper.addVertice(pose, buffer, v15, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v14, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v18, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v19, new TextureVertice(var3, var5), light, overlay);

        RenderHelper.addVertice(pose, buffer, v16, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v15, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v19, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v20, new TextureVertice(var3, var5), light, overlay);

        RenderHelper.addVertice(pose, buffer, v13, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v16, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v20, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v17, new TextureVertice(var3, var5), light, overlay);

        itemIcon = 42;
        var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
        var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
        var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
        var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

        RenderHelper.addVertice(pose, buffer, v13, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v15, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v19, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v17, new TextureVertice(var3, var5), light, overlay);

        RenderHelper.addVertice(pose, buffer, v16, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v14, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v18, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v20, new TextureVertice(var3, var5), light, overlay);

        RenderHelper.addVertice(pose, buffer, v15, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v13, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v17, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v19, new TextureVertice(var3, var5), light, overlay);

        RenderHelper.addVertice(pose, buffer, v14, new TextureVertice(var3, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v16, new TextureVertice(var4, var6), light, overlay);
        RenderHelper.addVertice(pose, buffer, v20, new TextureVertice(var4, var5), light, overlay);
        RenderHelper.addVertice(pose, buffer, v18, new TextureVertice(var3, var5), light, overlay);
        if (!hasFuse) {
            itemIcon = 37;
            var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
            var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
            var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
            var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

            RenderHelper.addVertice(pose, buffer, v1, new TextureVertice(var3, var6), light, overlay);
            RenderHelper.addVertice(pose, buffer, v2, new TextureVertice(var4, var6), light, overlay);
            RenderHelper.addVertice(pose, buffer, v3, new TextureVertice(var4, var5), light, overlay);
            RenderHelper.addVertice(pose, buffer, v4, new TextureVertice(var3, var5), light, overlay);
        } else {
            itemIcon = 43;
            var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
            var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
            var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
            var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

            VertexConsumer bufferTriangles = vertexConsumers.getBuffer(ObjModels.RENDER_SOLID_TRIANGLES.apply(texture));
            RenderHelper.addVertice(pose, bufferTriangles, v0, new TextureVertice(var3, var6), light, overlay);
            RenderHelper.addVertice(pose, bufferTriangles, v1, new TextureVertice(var4, var6), light, overlay);
            RenderHelper.addVertice(pose, bufferTriangles, v2, new TextureVertice(var4, var5), light, overlay);

            RenderHelper.addVertice(pose, bufferTriangles, v0, new TextureVertice(var3, var6), light, overlay);
            RenderHelper.addVertice(pose, bufferTriangles, v2, new TextureVertice(var4, var5), light, overlay);
            RenderHelper.addVertice(pose, bufferTriangles, v3, new TextureVertice(var4, var6), light, overlay);

            RenderHelper.addVertice(pose, bufferTriangles, v0, new TextureVertice(var3, var6), light, overlay);
            RenderHelper.addVertice(pose, bufferTriangles, v3, new TextureVertice(var3, var5), light, overlay);
            RenderHelper.addVertice(pose, bufferTriangles, v4, new TextureVertice(var4, var5), light, overlay);

            RenderHelper.addVertice(pose, bufferTriangles, v0, new TextureVertice(var3, var6), light, overlay);
            RenderHelper.addVertice(pose, bufferTriangles, v4, new TextureVertice(var4, var5), light, overlay);
            RenderHelper.addVertice(pose, bufferTriangles, v1, new TextureVertice(var4, var6), light, overlay);
        }
        pose.popPose();
        pose.popPose();
    }
}
