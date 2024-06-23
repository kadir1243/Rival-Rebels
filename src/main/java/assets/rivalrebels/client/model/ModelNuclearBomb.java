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
import net.minecraft.client.render.OverlayTexture;
import org.joml.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

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

    public static void renderModel(MatrixStack matrices, VertexConsumer buffer, int light, boolean hasFuse) {
        matrices.push();
        matrices.scale(RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale(), RRConfig.CLIENT.getNukeScale());
        int itemIcon = 39;
        float var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
        float var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
        float var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
        float var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

        matrices.push();
        matrices.scale(1.01f, 1.01f, 1.01f);

        RenderHelper.addVertice(buffer, v2, new TextureVertice(var3, var6), light, OverlayTexture.DEFAULT_UV);
        RenderHelper.addVertice(buffer, v1, new TextureVertice(var4, var6), light, OverlayTexture.DEFAULT_UV);
        RenderHelper.addVertice(buffer, v5, new TextureVertice(var4, var5), light, OverlayTexture.DEFAULT_UV);
        RenderHelper.addVertice(buffer, v6, new TextureVertice(var3, var5), light, OverlayTexture.DEFAULT_UV);
        RenderHelper.addVertice(buffer, v3, new TextureVertice(var3, var6), light, OverlayTexture.DEFAULT_UV);
        RenderHelper.addVertice(buffer, v2, new TextureVertice(var4, var6), light, OverlayTexture.DEFAULT_UV);
        RenderHelper.addVertice(buffer, v6, new TextureVertice(var4, var5), light, OverlayTexture.DEFAULT_UV);
        RenderHelper.addVertice(buffer, v7, new TextureVertice(var3, var5), light, OverlayTexture.DEFAULT_UV);
        RenderHelper.addVertice(buffer, v4, new TextureVertice(var3, var6), light, OverlayTexture.DEFAULT_UV);
        RenderHelper.addVertice(buffer, v3, new TextureVertice(var4, var6), light, OverlayTexture.DEFAULT_UV);
        RenderHelper.addVertice(buffer, v7, new TextureVertice(var4, var5), light, OverlayTexture.DEFAULT_UV);
        RenderHelper.addVertice(buffer, v8, new TextureVertice(var3, var5), light, OverlayTexture.DEFAULT_UV);
        RenderHelper.addVertice(buffer, v1, new TextureVertice(var3, var6), light, OverlayTexture.DEFAULT_UV);
        RenderHelper.addVertice(buffer, v4, new TextureVertice(var4, var6), light, OverlayTexture.DEFAULT_UV);
        RenderHelper.addVertice(buffer, v8, new TextureVertice(var4, var5), light, OverlayTexture.DEFAULT_UV);
        RenderHelper.addVertice(buffer, v5, new TextureVertice(var3, var5), light, OverlayTexture.DEFAULT_UV);

        itemIcon = 40;
        var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
        var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
        var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
        var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

        buffer.vertex(v6.x, v6.y, v6.z).texture(var3, var6).light(light).next();
        buffer.vertex(v5.x, v5.y, v5.z).texture(var4, var6).light(light).next();
        buffer.vertex(v21.x, v21.y, v21.z).texture(var4, var5).light(light).next();
        buffer.vertex(v22.x, v22.y, v22.z).texture(var3, var5).light(light).next();
        buffer.vertex(v7.x, v7.y, v7.z).texture(var3, var6).light(light).next();
        buffer.vertex(v6.x, v6.y, v6.z).texture(var4, var6).light(light).next();
        buffer.vertex(v22.x, v22.y, v22.z).texture(var4, var5).light(light).next();
        buffer.vertex(v23.x, v23.y, v23.z).texture(var3, var5).light(light).next();
        buffer.vertex(v8.x, v8.y, v8.z).texture(var3, var6).light(light).next();
        buffer.vertex(v7.x, v7.y, v7.z).texture(var4, var6).light(light).next();
        buffer.vertex(v23.x, v23.y, v23.z).texture(var4, var5).light(light).next();
        buffer.vertex(v24.x, v24.y, v24.z).texture(var3, var5).light(light).next();
        buffer.vertex(v5.x, v5.y, v5.z).texture(var3, var6).light(light).next();
        buffer.vertex(v8.x, v8.y, v8.z).texture(var4, var6).light(light).next();
        buffer.vertex(v24.x, v24.y, v24.z).texture(var4, var5).light(light).next();
        buffer.vertex(v21.x, v21.y, v21.z).texture(var3, var5).light(light).next();
        buffer.vertex(v22.x, v22.y, v22.z).texture(var3, var6).light(light).next();
        buffer.vertex(v21.x, v21.y, v21.z).texture(var4, var6).light(light).next();
        buffer.vertex(v9.x, v9.y, v9.z).texture(var4, var5).light(light).next();
        buffer.vertex(v10.x, v10.y, v10.z).texture(var3, var5).light(light).next();
        buffer.vertex(v23.x, v23.y, v23.z).texture(var3, var6).light(light).next();
        buffer.vertex(v22.x, v22.y, v22.z).texture(var4, var6).light(light).next();
        buffer.vertex(v10.x, v10.y, v10.z).texture(var4, var5).light(light).next();
        buffer.vertex(v11.x, v11.y, v11.z).texture(var3, var5).light(light).next();
        buffer.vertex(v24.x, v24.y, v24.z).texture(var3, var6).light(light).next();
        buffer.vertex(v23.x, v23.y, v23.z).texture(var4, var6).light(light).next();
        buffer.vertex(v11.x, v11.y, v11.z).texture(var4, var5).light(light).next();
        buffer.vertex(v12.x, v12.y, v12.z).texture(var3, var5).light(light).next();
        buffer.vertex(v21.x, v21.y, v21.z).texture(var3, var6).light(light).next();
        buffer.vertex(v24.x, v24.y, v24.z).texture(var4, var6).light(light).next();
        buffer.vertex(v12.x, v12.y, v12.z).texture(var4, var5).light(light).next();
        buffer.vertex(v9.x, v9.y, v9.z).texture(var3, var5).light(light).next();

        itemIcon = 38;
        var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
        var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
        var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
        var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

        buffer.vertex(v10.x, v10.y, v10.z).texture(var3, var6).light(light).next();
        buffer.vertex(v9.x, v9.y, v9.z).texture(var4, var6).light(light).next();
        buffer.vertex(v12.x, v12.y, v12.z).texture(var4, var5).light(light).next();
        buffer.vertex(v11.x, v11.y, v11.z).texture(var3, var5).light(light).next();

        itemIcon = 41;
        var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
        var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
        var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
        var6 = (itemIcon / 16 * 16 + 16) / 256.0F;
        float o = 0.999F;

        buffer.vertex(v13.x * o, v13.y, v13.z * o).texture(var3, var6).light(light).next();
        buffer.vertex(v14.x * o, v14.y, v14.z * o).texture(var4, var6).light(light).next();
        buffer.vertex(v18.x * o, v18.y, v18.z * o).texture(var4, var5).light(light).next();
        buffer.vertex(v17.x * o, v17.y, v17.z * o).texture(var3, var5).light(light).next();
        buffer.vertex(v14.x * o, v14.y, v14.z * o).texture(var3, var6).light(light).next();
        buffer.vertex(v15.x * o, v15.y, v15.z * o).texture(var4, var6).light(light).next();
        buffer.vertex(v19.x * o, v19.y, v19.z * o).texture(var4, var5).light(light).next();
        buffer.vertex(v18.x * o, v18.y, v18.z * o).texture(var3, var5).light(light).next();
        buffer.vertex(v15.x * o, v15.y, v15.z * o).texture(var3, var6).light(light).next();
        buffer.vertex(v16.x * o, v16.y, v16.z * o).texture(var4, var6).light(light).next();
        buffer.vertex(v20.x * o, v20.y, v20.z * o).texture(var4, var5).light(light).next();
        buffer.vertex(v19.x * o, v19.y, v19.z * o).texture(var3, var5).light(light).next();
        buffer.vertex(v16.x * o, v16.y, v16.z * o).texture(var3, var6).light(light).next();
        buffer.vertex(v13.x * o, v13.y, v13.z * o).texture(var4, var6).light(light).next();
        buffer.vertex(v17.x * o, v17.y, v17.z * o).texture(var4, var5).light(light).next();
        buffer.vertex(v20.x * o, v20.y, v20.z * o).texture(var3, var5).light(light).next();
        buffer.vertex(v14.x, v14.y, v14.z).texture(var3, var6).light(light).next();
        buffer.vertex(v13.x, v13.y, v13.z).texture(var4, var6).light(light).next();
        buffer.vertex(v17.x, v17.y, v17.z).texture(var4, var5).light(light).next();
        buffer.vertex(v18.x, v18.y, v18.z).texture(var3, var5).light(light).next();
        buffer.vertex(v15.x, v15.y, v15.z).texture(var3, var6).light(light).next();
        buffer.vertex(v14.x, v14.y, v14.z).texture(var4, var6).light(light).next();
        buffer.vertex(v18.x, v18.y, v18.z).texture(var4, var5).light(light).next();
        buffer.vertex(v19.x, v19.y, v19.z).texture(var3, var5).light(light).next();
        buffer.vertex(v16.x, v16.y, v16.z).texture(var3, var6).light(light).next();
        buffer.vertex(v15.x, v15.y, v15.z).texture(var4, var6).light(light).next();
        buffer.vertex(v19.x, v19.y, v19.z).texture(var4, var5).light(light).next();
        buffer.vertex(v20.x, v20.y, v20.z).texture(var3, var5).light(light).next();
        buffer.vertex(v13.x, v13.y, v13.z).texture(var3, var6).light(light).next();
        buffer.vertex(v16.x, v16.y, v16.z).texture(var4, var6).light(light).next();
        buffer.vertex(v20.x, v20.y, v20.z).texture(var4, var5).light(light).next();
        buffer.vertex(v17.x, v17.y, v17.z).texture(var3, var5).light(light).next();

        itemIcon = 42;
        var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
        var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
        var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
        var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

        buffer.vertex(v13.x, v13.y, v13.z).texture(var3, var6).light(light).next();
        buffer.vertex(v15.x, v15.y, v15.z).texture(var4, var6).light(light).next();
        buffer.vertex(v19.x, v19.y, v19.z).texture(var4, var5).light(light).next();
        buffer.vertex(v17.x, v17.y, v17.z).texture(var3, var5).light(light).next();
        buffer.vertex(v16.x, v16.y, v16.z).texture(var3, var6).light(light).next();
        buffer.vertex(v14.x, v14.y, v14.z).texture(var4, var6).light(light).next();
        buffer.vertex(v18.x, v18.y, v18.z).texture(var4, var5).light(light).next();
        buffer.vertex(v20.x, v20.y, v20.z).texture(var3, var5).light(light).next();
        buffer.vertex(v15.x, v15.y, v15.z).texture(var3, var6).light(light).next();
        buffer.vertex(v13.x, v13.y, v13.z).texture(var4, var6).light(light).next();
        buffer.vertex(v17.x, v17.y, v17.z).texture(var4, var5).light(light).next();
        buffer.vertex(v19.x, v19.y, v19.z).texture(var3, var5).light(light).next();
        buffer.vertex(v14.x, v14.y, v14.z).texture(var3, var6).light(light).next();
        buffer.vertex(v16.x, v16.y, v16.z).texture(var4, var6).light(light).next();
        buffer.vertex(v20.x, v20.y, v20.z).texture(var4, var5).light(light).next();
        buffer.vertex(v18.x, v18.y, v18.z).texture(var3, var5).light(light).next();
        if (!hasFuse) {
            itemIcon = 37;
            var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
            var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
            var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
            var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

            buffer.vertex(v1.x, v1.y, v1.z).texture(var3, var6).light(light).next();
            buffer.vertex(v2.x, v2.y, v2.z).texture(var4, var6).light(light).next();
            buffer.vertex(v3.x, v3.y, v3.z).texture(var4, var5).light(light).next();
            buffer.vertex(v4.x, v4.y, v4.z).texture(var3, var5).light(light).next();
        } else {
            itemIcon = 43;
            var3 = (itemIcon % 16 * 16 + 0) / 256.0F;
            var4 = (itemIcon % 16 * 16 + 16) / 256.0F;
            var5 = (itemIcon / 16 * 16 + 0) / 256.0F;
            var6 = (itemIcon / 16 * 16 + 16) / 256.0F;

            buffer.vertex(v0.x, v0.y, v0.z).texture(var3, var6).light(light).next();
            buffer.vertex(v1.x, v1.y, v1.z).texture(var4, var6).light(light).next();
            buffer.vertex(v2.x, v2.y, v2.z).texture(var4, var5).light(light).next();
            buffer.vertex(v0.x, v0.y, v0.z).texture(var3, var6).light(light).next();
            buffer.vertex(v2.x, v2.y, v2.z).texture(var4, var5).light(light).next();
            buffer.vertex(v3.x, v3.y, v3.z).texture(var4, var6).light(light).next();
            buffer.vertex(v0.x, v0.y, v0.z).texture(var3, var6).light(light).next();
            buffer.vertex(v3.x, v3.y, v3.z).texture(var3, var5).light(light).next();
            buffer.vertex(v4.x, v4.y, v4.z).texture(var4, var5).light(light).next();
            buffer.vertex(v0.x, v0.y, v0.z).texture(var3, var6).light(light).next();
            buffer.vertex(v4.x, v4.y, v4.z).texture(var4, var5).light(light).next();
            buffer.vertex(v1.x, v1.y, v1.z).texture(var4, var6).light(light).next();
        }
        matrices.pop();
        matrices.pop();
    }
}
