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
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.lighting.LightEngine;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.util.ARGB;

@OnlyIn(Dist.CLIENT)
public class ModelAstroBlasterBody {
	public static void render(PoseStack matrices, SubmitNodeCollector nodeCollector, RenderType renderType, float size, float red, float green, float blue, float alpha) {
		matrices.pushPose();
        int color = ARGB.colorFromFloat(alpha, red, green, blue);

		matrices.scale(size, size, size);
        nodeCollector.submitCustomGeometry(matrices, renderType, (pose, consumer) -> {
            ObjModels.render(Minecraft.getInstance().getModelManager().getStandaloneModel(ObjModels.ASTRO_BLASTER_BODY), consumer, pose, color, LightEngine.MAX_LEVEL, OverlayTexture.NO_OVERLAY);
        });

		matrices.popPose();
	}
}
