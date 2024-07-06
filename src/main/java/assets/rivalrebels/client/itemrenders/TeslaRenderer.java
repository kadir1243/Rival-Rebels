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
package assets.rivalrebels.client.itemrenders;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.model.ObjModels;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.noise.RivalRebelsCellularNoise;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.Material;
import net.minecraft.util.CommonColors;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class TeslaRenderer implements DynamicItemRenderer {
    public static final Material TESLA_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.ettesla);
    private int spin;

	private static int getDegree(ItemStack item) {
        return item.getOrDefault(RRComponents.TESLA_DIAL, 0);
	}

    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (!stack.isEnchanted()) {
            VertexConsumer buffer = TESLA_TEXTURE.buffer(vertexConsumers, RenderType::entitySolid);
            int degree = getDegree(stack);
			spin += 5 + (degree / 36f);
			matrices.pushPose();
			matrices.translate(0.8f, 0.5f, -0.03f);
			matrices.mulPose(Axis.ZP.rotationDegrees(35));
			matrices.mulPose(Axis.YP.rotationDegrees(90));
			matrices.scale(0.12f, 0.12f, 0.12f);
			// matrices.translate(0.3f, 0.05f, -0.1f);

			ObjModels.tesla.render(matrices, buffer, light, overlay);
			matrices.mulPose(Axis.XP.rotationDegrees(spin));
			ObjModels.dynamo.render(matrices, buffer, light, overlay);

			matrices.popPose();
		} else {
			if (mode.firstPerson()) matrices.popPose();
			matrices.pushPose();
            VertexConsumer cellularNoise = vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE);
			matrices.scale(1.01f, 1.01f, 1.01f);
			matrices.mulPose(Axis.YP.rotationDegrees(45));
			matrices.mulPose(Axis.ZP.rotationDegrees(10));
			matrices.scale(0.6f, 0.2f, 0.2f);
			matrices.translate(-0.99f, 0.5f, 0.0f);
            cellularNoise.addVertex(matrices.last(), -1, -1, -1).setColor(CommonColors.WHITE).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, -1).setColor(CommonColors.WHITE).setUv1(1, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, 1).setColor(CommonColors.WHITE).setUv1(1, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, 1).setColor(CommonColors.WHITE).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, -1).setColor(CommonColors.WHITE).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, 1).setColor(CommonColors.WHITE).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, 1).setColor(CommonColors.WHITE).setUv1(1, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, -1).setColor(CommonColors.WHITE).setUv1(1, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, -1).setColor(CommonColors.WHITE).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, 1).setColor(CommonColors.WHITE).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, 1).setColor(CommonColors.WHITE).setUv1(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, -1).setColor(CommonColors.WHITE).setUv1(3, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, -1).setColor(CommonColors.WHITE).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, -1).setColor(CommonColors.WHITE).setUv1(3, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, 1).setColor(CommonColors.WHITE).setUv1(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, 1).setColor(CommonColors.WHITE).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, -1).setColor(CommonColors.WHITE).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, -1).setColor(CommonColors.WHITE).setUv1(3, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, -1).setColor(CommonColors.WHITE).setUv1(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, -1).setColor(CommonColors.WHITE).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, 1).setColor(CommonColors.WHITE).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, 1).setColor(CommonColors.WHITE).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, 1).setColor(CommonColors.WHITE).setUv1(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, 1).setColor(CommonColors.WHITE).setUv1(3, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, -1).setColor(CommonColors.WHITE).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, -1).setColor(CommonColors.WHITE).setUv1(1, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, 1).setColor(CommonColors.WHITE).setUv1(1, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, 1).setColor(CommonColors.WHITE).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, -1).setColor(CommonColors.WHITE).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, 1).setColor(CommonColors.WHITE).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, 1).setColor(CommonColors.WHITE).setUv1(1, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, -1).setColor(CommonColors.WHITE).setUv1(1, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, -1).setColor(CommonColors.WHITE).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, 1).setColor(CommonColors.WHITE).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, 1).setColor(CommonColors.WHITE).setUv1(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, -1).setColor(CommonColors.WHITE).setUv1(3, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, -1).setColor(CommonColors.WHITE).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, -1).setColor(CommonColors.WHITE).setUv1(3, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, 1).setColor(CommonColors.WHITE).setUv1(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, 1).setColor(CommonColors.WHITE).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, -1).setColor(CommonColors.WHITE).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, -1).setColor(CommonColors.WHITE).setUv1(3, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, -1).setColor(CommonColors.WHITE).setUv1(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, -1).setColor(CommonColors.WHITE).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1,  1).setColor(CommonColors.WHITE).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1,  1).setColor(CommonColors.WHITE).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1,  1).setColor(CommonColors.WHITE).setUv1(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1,  1).setColor(CommonColors.WHITE).setUv1(3, 0).setLight(light);
			matrices.popPose();
			if (mode.firstPerson()) matrices.pushPose();
		}
	}

}
