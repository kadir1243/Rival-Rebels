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

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.kadir1243.rivalrebels.client.renderhelper.RRRenderTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.lighting.LightEngine;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.ARGB;

@OnlyIn(Dist.CLIENT)
public class ModelBlastSphere {
    public static void renderModel(PoseStack matrices, MultiBufferSource vertexConsumers, float size, float red, float green, float blue, float alpha) {
        renderModel(matrices, vertexConsumers, size, ARGB.colorFromFloat(alpha, red, green, blue));
    }

    public static void renderModel(PoseStack matrices, MultiBufferSource vertexConsumers, float size, int color) {
        renderModel(matrices, vertexConsumers.getBuffer(RRRenderTypes.MODEL_BLAST_SPHERE), size, color);
    }

	public static void renderModel(PoseStack matrices, VertexConsumer buffer, float size, int color) {
        matrices.pushPose();
        matrices.scale(size, size, size);
        ObjModels.render(Minecraft.getInstance().getModelManager().getStandaloneModel(ObjModels.BLAST_SPHERE_MODEL), buffer, matrices, color, LightEngine.MAX_LEVEL, OverlayTexture.NO_OVERLAY);
        matrices.popPose();
    }

}
