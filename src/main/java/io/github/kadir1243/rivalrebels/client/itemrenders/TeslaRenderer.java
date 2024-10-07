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
package io.github.kadir1243.rivalrebels.client.itemrenders;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.model.ObjModels;
import io.github.kadir1243.rivalrebels.common.item.components.RRComponents;
import io.github.kadir1243.rivalrebels.common.noise.RivalRebelsCellularNoise;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@OnlyIn(Dist.CLIENT)
public class TeslaRenderer implements DynamicItemRenderer {
    private static final DeltaTracker TIMER = Minecraft.getInstance().getTimer();
    private int spin;

    private static int getDegree(ItemStack item) {
        return item.getOrDefault(RRComponents.TESLA_DIAL, 0);
	}

    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (mode == ItemDisplayContext.GUI) {
            matrices.mulPose(Axis.YP.rotationDegrees(45));
        }
        if (!stack.isEnchanted()) {
            int degree = getDegree(stack);
            spin = (int) Mth.lerp(TIMER.getGameTimeDeltaTicks(), spin, spin + (5 + (degree / 36F)));
			matrices.pushPose();
			matrices.translate(0.8f, 0.5f, -0.03f);
			matrices.scale(0.12f, 0.12f, 0.12f);
			// matrices.translate(0.3f, 0.05f, -0.1f);

			ObjModels.renderSolid(ObjModels.tesla, RRIdentifiers.ettesla, matrices, vertexConsumers, light, overlay);
			matrices.mulPose(Axis.XP.rotationDegrees(spin));
			ObjModels.renderSolid(ObjModels.dynamo, RRIdentifiers.ettesla, matrices, vertexConsumers, light, overlay);

			matrices.popPose();
		} else {
			matrices.pushPose();
            VertexConsumer cellularNoise = vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE);
			matrices.scale(1.01f, 1.01f, 1.01f);
			matrices.mulPose(Axis.YP.rotationDegrees(45));
			matrices.mulPose(Axis.ZP.rotationDegrees(10));
			matrices.scale(0.6f, 0.2f, 0.2f);
			matrices.translate(-0.99f, 0.5f, 0.0f);
            cellularNoise.addVertex(matrices.last(), -1, -1, -1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, -1).setColor(CommonColors.WHITE).setUv(1, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, 1).setColor(CommonColors.WHITE).setUv(1, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, 1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(light);

            cellularNoise.addVertex(matrices.last(),  1, -1, -1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, 1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, 1).setColor(CommonColors.WHITE).setUv(1, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, -1).setColor(CommonColors.WHITE).setUv(1, 0).setLight(light);

            cellularNoise.addVertex(matrices.last(), -1, -1, -1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, 1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, 1).setColor(CommonColors.WHITE).setUv(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, -1).setColor(CommonColors.WHITE).setUv(3, 0).setLight(light);

            cellularNoise.addVertex(matrices.last(), -1,  1, -1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, -1).setColor(CommonColors.WHITE).setUv(3, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, 1).setColor(CommonColors.WHITE).setUv(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, 1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(light);

            cellularNoise.addVertex(matrices.last(), -1, -1, -1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, -1).setColor(CommonColors.WHITE).setUv(3, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, -1).setColor(CommonColors.WHITE).setUv(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, -1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(light);

            cellularNoise.addVertex(matrices.last(), -1, -1, 1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, 1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, 1).setColor(CommonColors.WHITE).setUv(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, 1).setColor(CommonColors.WHITE).setUv(3, 0).setLight(light);

            cellularNoise.addVertex(matrices.last(), -1, -1, -1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, -1).setColor(CommonColors.WHITE).setUv(1, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, 1).setColor(CommonColors.WHITE).setUv(1, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, 1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(light);

            cellularNoise.addVertex(matrices.last(),  1, -1, -1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, 1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, 1).setColor(CommonColors.WHITE).setUv(1, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, -1).setColor(CommonColors.WHITE).setUv(1, 0).setLight(light);

            cellularNoise.addVertex(matrices.last(), -1, -1, -1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, 1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, 1).setColor(CommonColors.WHITE).setUv(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, -1).setColor(CommonColors.WHITE).setUv(3, 0).setLight(light);

            cellularNoise.addVertex(matrices.last(), -1,  1, -1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, -1).setColor(CommonColors.WHITE).setUv(3, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, 1).setColor(CommonColors.WHITE).setUv(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, 1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(light);

            cellularNoise.addVertex(matrices.last(), -1, -1, -1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, -1).setColor(CommonColors.WHITE).setUv(3, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, -1).setColor(CommonColors.WHITE).setUv(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, -1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(light);

            cellularNoise.addVertex(matrices.last(), -1, -1,  1).setColor(CommonColors.WHITE).setUv(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1,  1).setColor(CommonColors.WHITE).setUv(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1,  1).setColor(CommonColors.WHITE).setUv(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1,  1).setColor(CommonColors.WHITE).setUv(3, 0).setLight(light);

            matrices.popPose();
		}
	}

}
