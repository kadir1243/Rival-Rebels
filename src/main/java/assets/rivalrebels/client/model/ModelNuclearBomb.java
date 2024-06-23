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

    public static void renderModel(PoseStack matrices, VertexConsumer buffer, int light, boolean hasFuse) {
        matrices.pushPose();
        matrices.scale(RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale());
        int itemIcon = 39;
        float var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
        float var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
        float var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
        float var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

        matrices.pushPose();
        matrices.scale(1.01f, 1.01f, 1.01f);

        RenderHelper.addVertice(buffer, v2, new TextureVertice(var3, var6), light, OverlayTexture.NO_OVERLAY);
        RenderHelper.addVertice(buffer, v1, new TextureVertice(var4, var6), light, OverlayTexture.NO_OVERLAY);
        RenderHelper.addVertice(buffer, v5, new TextureVertice(var4, var5), light, OverlayTexture.NO_OVERLAY);
        RenderHelper.addVertice(buffer, v6, new TextureVertice(var3, var5), light, OverlayTexture.NO_OVERLAY);
        RenderHelper.addVertice(buffer, v3, new TextureVertice(var3, var6), light, OverlayTexture.NO_OVERLAY);
        RenderHelper.addVertice(buffer, v2, new TextureVertice(var4, var6), light, OverlayTexture.NO_OVERLAY);
        RenderHelper.addVertice(buffer, v6, new TextureVertice(var4, var5), light, OverlayTexture.NO_OVERLAY);
        RenderHelper.addVertice(buffer, v7, new TextureVertice(var3, var5), light, OverlayTexture.NO_OVERLAY);
        RenderHelper.addVertice(buffer, v4, new TextureVertice(var3, var6), light, OverlayTexture.NO_OVERLAY);
        RenderHelper.addVertice(buffer, v3, new TextureVertice(var4, var6), light, OverlayTexture.NO_OVERLAY);
        RenderHelper.addVertice(buffer, v7, new TextureVertice(var4, var5), light, OverlayTexture.NO_OVERLAY);
        RenderHelper.addVertice(buffer, v8, new TextureVertice(var3, var5), light, OverlayTexture.NO_OVERLAY);
        RenderHelper.addVertice(buffer, v1, new TextureVertice(var3, var6), light, OverlayTexture.NO_OVERLAY);
        RenderHelper.addVertice(buffer, v4, new TextureVertice(var4, var6), light, OverlayTexture.NO_OVERLAY);
        RenderHelper.addVertice(buffer, v8, new TextureVertice(var4, var5), light, OverlayTexture.NO_OVERLAY);
        RenderHelper.addVertice(buffer, v5, new TextureVertice(var3, var5), light, OverlayTexture.NO_OVERLAY);

        itemIcon = 40;
        var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
        var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
        var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
        var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

        buffer.addVertex(matrices.last(), v6).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v5.x, v5.y, v5.z).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v21.x, v21.y, v21.z).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v22.x, v22.y, v22.z).setUv(var3, var5).setLight(light);
        buffer.addVertex(matrices.last(), v7.x, v7.y, v7.z).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v6).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v22.x, v22.y, v22.z).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v23.x, v23.y, v23.z).setUv(var3, var5).setLight(light);
        buffer.addVertex(matrices.last(), v8.x, v8.y, v8.z).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v7.x, v7.y, v7.z).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v23.x, v23.y, v23.z).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v24.x, v24.y, v24.z).setUv(var3, var5).setLight(light);
        buffer.addVertex(matrices.last(), v5.x, v5.y, v5.z).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v8.x, v8.y, v8.z).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v24.x, v24.y, v24.z).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v21.x, v21.y, v21.z).setUv(var3, var5).setLight(light);
        buffer.addVertex(matrices.last(), v22.x, v22.y, v22.z).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v21.x, v21.y, v21.z).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v9.x, v9.y, v9.z).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v10.x, v10.y, v10.z).setUv(var3, var5).setLight(light);
        buffer.addVertex(matrices.last(), v23.x, v23.y, v23.z).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v22.x, v22.y, v22.z).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v10.x, v10.y, v10.z).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v11.x, v11.y, v11.z).setUv(var3, var5).setLight(light);
        buffer.addVertex(matrices.last(), v24.x, v24.y, v24.z).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v23.x, v23.y, v23.z).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v11.x, v11.y, v11.z).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v12.x, v12.y, v12.z).setUv(var3, var5).setLight(light);
        buffer.addVertex(matrices.last(), v21.x, v21.y, v21.z).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v24.x, v24.y, v24.z).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v12.x, v12.y, v12.z).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v9.x, v9.y, v9.z).setUv(var3, var5).setLight(light);

        itemIcon = 38;
        var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
        var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
        var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
        var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

        buffer.addVertex(matrices.last(), v10.x, v10.y, v10.z).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v9.x, v9.y, v9.z).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v12.x, v12.y, v12.z).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v11.x, v11.y, v11.z).setUv(var3, var5).setLight(light);

        itemIcon = 41;
        var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
        var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
        var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
        var6 = (itemIcon / 16 * 16 + 16) / 256.0F;
        float o = 0.999F;

        buffer.addVertex(matrices.last(), v13.x * o, v13.y, v13.z * o).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v14.x * o, v14.y, v14.z * o).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v18.x * o, v18.y, v18.z * o).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v17.x * o, v17.y, v17.z * o).setUv(var3, var5).setLight(light);
        buffer.addVertex(matrices.last(), v14.x * o, v14.y, v14.z * o).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v15.x * o, v15.y, v15.z * o).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v19.x * o, v19.y, v19.z * o).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v18.x * o, v18.y, v18.z * o).setUv(var3, var5).setLight(light);
        buffer.addVertex(matrices.last(), v15.x * o, v15.y, v15.z * o).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v16.x * o, v16.y, v16.z * o).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v20.x * o, v20.y, v20.z * o).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v19.x * o, v19.y, v19.z * o).setUv(var3, var5).setLight(light);
        buffer.addVertex(matrices.last(), v16.x * o, v16.y, v16.z * o).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v13.x * o, v13.y, v13.z * o).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v17.x * o, v17.y, v17.z * o).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v20.x * o, v20.y, v20.z * o).setUv(var3, var5).setLight(light);
        buffer.addVertex(matrices.last(), v14.x, v14.y, v14.z).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v13.x, v13.y, v13.z).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v17.x, v17.y, v17.z).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v18.x, v18.y, v18.z).setUv(var3, var5).setLight(light);
        buffer.addVertex(matrices.last(), v15.x, v15.y, v15.z).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v14.x, v14.y, v14.z).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v18.x, v18.y, v18.z).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v19.x, v19.y, v19.z).setUv(var3, var5).setLight(light);
        buffer.addVertex(matrices.last(), v16.x, v16.y, v16.z).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v15.x, v15.y, v15.z).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v19.x, v19.y, v19.z).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v20.x, v20.y, v20.z).setUv(var3, var5).setLight(light);
        buffer.addVertex(matrices.last(), v13.x, v13.y, v13.z).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v16.x, v16.y, v16.z).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v20.x, v20.y, v20.z).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v17.x, v17.y, v17.z).setUv(var3, var5).setLight(light);

        itemIcon = 42;
        var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
        var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
        var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
        var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

        buffer.addVertex(matrices.last(), v13.x, v13.y, v13.z).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v15.x, v15.y, v15.z).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v19.x, v19.y, v19.z).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v17.x, v17.y, v17.z).setUv(var3, var5).setLight(light);
        buffer.addVertex(matrices.last(), v16.x, v16.y, v16.z).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v14.x, v14.y, v14.z).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v18.x, v18.y, v18.z).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v20.x, v20.y, v20.z).setUv(var3, var5).setLight(light);
        buffer.addVertex(matrices.last(), v15.x, v15.y, v15.z).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v13.x, v13.y, v13.z).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v17.x, v17.y, v17.z).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v19.x, v19.y, v19.z).setUv(var3, var5).setLight(light);
        buffer.addVertex(matrices.last(), v14.x, v14.y, v14.z).setUv(var3, var6).setLight(light);
        buffer.addVertex(matrices.last(), v16.x, v16.y, v16.z).setUv(var4, var6).setLight(light);
        buffer.addVertex(matrices.last(), v20.x, v20.y, v20.z).setUv(var4, var5).setLight(light);
        buffer.addVertex(matrices.last(), v18.x, v18.y, v18.z).setUv(var3, var5).setLight(light);
        if (!hasFuse) {
            itemIcon = 37;
            var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
            var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
            var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
            var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

            buffer.addVertex(matrices.last(), v1).setUv(var3, var6).setLight(light);
            buffer.addVertex(matrices.last(), v2).setUv(var4, var6).setLight(light);
            buffer.addVertex(matrices.last(), v3).setUv(var4, var5).setLight(light);
            buffer.addVertex(matrices.last(), v4).setUv(var3, var5).setLight(light);
        } else {
            itemIcon = 43;
            var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
            var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
            var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
            var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

            buffer.addVertex(matrices.last(), v0).setUv(var3, var6).setLight(light);
            buffer.addVertex(matrices.last(), v1).setUv(var4, var6).setLight(light);
            buffer.addVertex(matrices.last(), v2).setUv(var4, var5).setLight(light);
            buffer.addVertex(matrices.last(), v0).setUv(var3, var6).setLight(light);
            buffer.addVertex(matrices.last(), v2).setUv(var4, var5).setLight(light);
            buffer.addVertex(matrices.last(), v3).setUv(var4, var6).setLight(light);
            buffer.addVertex(matrices.last(), v0).setUv(var3, var6).setLight(light);
            buffer.addVertex(matrices.last(), v3).setUv(var3, var5).setLight(light);
            buffer.addVertex(matrices.last(), v4).setUv(var4, var5).setLight(light);
            buffer.addVertex(matrices.last(), v0).setUv(var3, var6).setLight(light);
            buffer.addVertex(matrices.last(), v4).setUv(var4, var5).setLight(light);
            buffer.addVertex(matrices.last(), v1).setUv(var4, var6).setLight(light);
        }
        matrices.popPose();
        matrices.popPose();
    }
}
