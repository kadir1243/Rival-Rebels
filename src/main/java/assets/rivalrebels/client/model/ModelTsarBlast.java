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
// Copyrighted Rodolian Material
package assets.rivalrebels.client.model;

import assets.rivalrebels.client.renderhelper.RenderHelper;
import assets.rivalrebels.client.renderhelper.TextureVertice;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.util.RandomSource;
import org.joml.Vector3f;

public class ModelTsarBlast {
    private static final float tsart = 39;
    private static final int[] time = {100, 100};
    private static final float texadd = -1f / 500f;
    private static final int segments = 20;
    private static final float deg = (float) Math.PI * 2f / segments;
    private static final float sin = (float) Math.sin(deg);
    private static final float cos = (float) Math.cos(deg);
    private static final float add = 360 / segments;
    private final float[][] tsarx = {{7f, 6f, 5f, 4f, 3f, 2f, 1.75f, 1.5f, 1.25f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, -1f, -1.25f, -1.5f, -2f, -2f, -1.5f, -1f, 1f, 1.5f, 0f, -1f, -2f, -3f, -4f, -5f, -5.25f, -5f, -4.5f, -3.5f, -2.5f, -1.5f}, {7f, 6f, 5f, 4f, 3f, 2f, 1.75f, 1.5f, 1.25f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, -1f, -1.25f, -1.5f, -2f, -2f, -1.5f, -1f, 1f, 1.5f, 0f, -1f, -2f, -3f, -4f, -5f, -5.25f, -5f, -4.5f, -3.5f, -2.5f, -1.5f}};
    private final float[][] tsary = {{-9f, -8.5f, -8f, -7.5f, -7f, -6f, -5f, -4f, -3f, -2f, -1f, 0f, 1f, 2f, 3f, 4f, 5f, 6f, 10f, 9f, 8f, 7f, 6.5f, 6f, 6f, 10f, 11f, 16.5f, 16.25f, 16f, 16f, 15.5f, 14.5f, 13f, 12f, 11f, 10f, 10f, 11f}, {-9f, -8.5f, -8f, -7.5f, -7f, -6f, -5f, -4f, -3f, -2f, -1f, 0f, 1f, 2f, 3f, 4f, 5f, 6f, 10f, 9f, 8f, 7f, 6.5f, 6f, 6f, 10f, 11f, 16.5f, 16.25f, 16f, 16f, 15.5f, 14.5f, 13f, 12f, 11f, 10f, 10f, 11f}};
    private float texanim = 0f;
    private int timer = 0;
    private int index = 0;

    public ModelTsarBlast() {
        RandomSource random = RandomSource.create();
        for (int f = 0; f < tsarx.length; f++) {
            for (int i = 0; i < tsarx[f].length; i++) {
                tsarx[f][i] += (random.nextFloat() - 0.5f) * 0.2f;
            }
            for (int i = 0; i < tsary[f].length; i++) {
                tsary[f][i] += (random.nextFloat() - 0.5f) * 0.2f;
            }
        }
    }

    public void render(PoseStack matrices, VertexConsumer buffer, int light, int overlay) {
        if (timer == 0) {
            timer += time[index];
            index++;
        }
        index %= time.length;
        if (timer > 0) timer--;
        texanim += texadd;
        matrices.pushPose();
        for (float i = 0; i < segments; i++) {
            matrices.pushPose();
            matrices.mulPose(Axis.YP.rotationDegrees(add * i));
            for (int f = 1; f < tsart; f++) {
                int ind0 = (time.length + index - 1) % time.length;
                float x0 = tsarx[ind0][f - 1] + (((tsarx[index][f - 1] - tsarx[ind0][f - 1]) / time[ind0]) * timer);
                float x1 = tsarx[ind0][f] + (((tsarx[index][f] - tsarx[ind0][f]) / time[ind0]) * timer);
                float y0 = tsary[ind0][f - 1] + (((tsary[index][f - 1] - tsary[ind0][f - 1]) / time[ind0]) * timer);
                float y1 = tsary[ind0][f] + (((tsary[index][f] - tsary[ind0][f]) / time[ind0]) * timer);
                TextureVertice t1 = new TextureVertice((1f / segments) * (i - 1), ((1f / tsart) * f) + texanim);
                TextureVertice t2 = new TextureVertice((1f / segments) * (i - 1), ((1f / tsart) * (f - 1)) + texanim);
                TextureVertice t3 = new TextureVertice((1f / segments) * i, ((1f / tsart) * (f - 1)) + texanim);
                TextureVertice t4 = new TextureVertice((1f / segments) * i, ((1f / tsart) * f) + texanim);
                RenderHelper.addFace(matrices, buffer, new Vector3f(0f, y1, x1),
                    new Vector3f(0f, y0, x0),
                    new Vector3f(x0 * sin, y0, x0 * cos),
                    new Vector3f(x1 * sin, y1, x1 * cos), t1, t2, t3, t4, light, overlay);
            }
            matrices.popPose();
        }
        matrices.popPose();
    }
}
