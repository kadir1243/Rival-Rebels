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
import assets.rivalrebels.client.objfileloader.ModelFromObj;
import assets.rivalrebels.common.item.components.RRComponents;
import assets.rivalrebels.common.noise.RivalRebelsCellularNoise;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;

public class TeslaRenderer implements DynamicItemRenderer {
    public static final Material TESLA_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.ettesla);
    private static final ModelFromObj tesla = ModelFromObj.readObjFile("i.obj");
	private static final ModelFromObj dynamo = ModelFromObj.readObjFile("j.obj");
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
			matrices.mulPose(new Quaternionf(35, 0.0F, 0.0F, 1.0F));
			matrices.mulPose(new Quaternionf(90, 0.0F, 1.0F, 0.0F));
			matrices.scale(0.12f, 0.12f, 0.12f);
			// matrices.translate(0.3f, 0.05f, -0.1f);

			tesla.render(buffer, light, overlay);
			matrices.mulPose(new Quaternionf(spin, 1.0F, 0.0F, 0.0F));
			dynamo.render(buffer, light, overlay);

			matrices.popPose();
		} else {
			if (mode != ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) matrices.popPose();
			matrices.pushPose();
            VertexConsumer cellularNoise = vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE);
			matrices.scale(1.01f, 1.01f, 1.01f);
			matrices.mulPose(new Quaternionf(45.0f, 0, 1, 0));
			matrices.mulPose(new Quaternionf(10.0f, 0, 0, 1));
			matrices.scale(0.6f, 0.2f, 0.2f);
			matrices.translate(-0.99f, 0.5f, 0.0f);
            cellularNoise.addVertex(matrices.last(), -1, -1, -1).setColor(1, 1, 1, 1).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, -1).setColor(1, 1, 1, 1).setUv1(1, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, 1).setColor(1, 1, 1, 1).setUv1(1, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, 1).setColor(1, 1, 1, 1).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, -1).setColor(1, 1, 1, 1).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, 1).setColor(1, 1, 1, 1).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, 1).setColor(1, 1, 1, 1).setUv1(1, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, -1).setColor(1, 1, 1, 1).setUv1(1, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, -1).setColor(1, 1, 1, 1).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, 1).setColor(1, 1, 1, 1).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, 1).setColor(1, 1, 1, 1).setUv1(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, -1).setColor(1, 1, 1, 1).setUv1(3, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, -1).setColor(1, 1, 1, 1).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, -1).setColor(1, 1, 1, 1).setUv1(3, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, 1).setColor(1, 1, 1, 1).setUv1(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, 1).setColor(1, 1, 1, 1).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, -1).setColor(1, 1, 1, 1).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, -1).setColor(1, 1, 1, 1).setUv1(3, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, -1).setColor(1, 1, 1, 1).setUv1(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, -1).setColor(1, 1, 1, 1).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, 1).setColor(1, 1, 1, 1).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, 1).setColor(1, 1, 1, 1).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, 1).setColor(1, 1, 1, 1).setUv1(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, 1).setColor(1, 1, 1, 1).setUv1(3, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, -1).setColor(1, 1, 1, 1).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, -1).setColor(1, 1, 1, 1).setUv1(1, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, 1).setColor(1, 1, 1, 1).setUv1(1, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, 1).setColor(1, 1, 1, 1).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, -1).setColor(1, 1, 1, 1).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, 1).setColor(1, 1, 1, 1).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, 1).setColor(1, 1, 1, 1).setUv1(1, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, -1).setColor(1, 1, 1, 1).setUv1(1, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, -1).setColor(1, 1, 1, 1).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, 1).setColor(1, 1, 1, 1).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, 1).setColor(1, 1, 1, 1).setUv1(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, -1).setColor(1, 1, 1, 1).setUv1(3, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, -1).setColor(1, 1, 1, 1).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, -1).setColor(1, 1, 1, 1).setUv1(3, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, 1).setColor(1, 1, 1, 1).setUv1(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, 1).setColor(1, 1, 1, 1).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1, -1).setColor(1, 1, 1, 1).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1, -1).setColor(1, 1, 1, 1).setUv1(3, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1, -1).setColor(1, 1, 1, 1).setUv1(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1, -1).setColor(1, 1, 1, 1).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1, -1,  1).setColor(1, 1, 1, 1).setUv1(0, 0).setLight(light);
            cellularNoise.addVertex(matrices.last(), -1,  1,  1).setColor(1, 1, 1, 1).setUv1(0, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1,  1,  1).setColor(1, 1, 1, 1).setUv1(3, 1).setLight(light);
            cellularNoise.addVertex(matrices.last(),  1, -1,  1).setColor(1, 1, 1, 1).setUv1(3, 0).setLight(light);
			matrices.popPose();
			if (mode != ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) matrices.pushPose();
		}
	}

}
