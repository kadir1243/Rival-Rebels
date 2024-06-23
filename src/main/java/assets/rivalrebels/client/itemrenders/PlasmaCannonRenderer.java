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
import assets.rivalrebels.client.model.ModelRod;
import assets.rivalrebels.client.objfileloader.ModelFromObj;
import assets.rivalrebels.common.noise.RivalRebelsCellularNoise;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.joml.Quaternionf;

import static net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;

public class PlasmaCannonRenderer implements DynamicItemRenderer {
    public static final SpriteIdentifier PLASMA_CANNON = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etplasmacannon);
    public static final SpriteIdentifier HYDROD = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.ethydrod);
	private final ModelRod md2;
	private final ModelRod md3 = new ModelRod();
	private static final ModelFromObj model = ModelFromObj.readObjFile("m.obj");

	public PlasmaCannonRenderer() {
		md2 = new ModelRod();
		md2.rendersecondcap = false;
    }

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		matrices.translate(-0.1f, 0f, 0f);
		matrices.push();
		matrices.translate(0.5f, 0.2f, -0.03f);
		matrices.multiply(new Quaternionf(35, 0.0F, 0.0F, 1.0F));
		matrices.scale(0.03125f, 0.03125f, 0.03125f);
		matrices.push();

        VertexConsumer plasmaCannonVertexConsumer = PLASMA_CANNON.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid);
        model.render(plasmaCannonVertexConsumer, light, overlay);
        VertexConsumer cellularNoise = vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE);
        if (stack.hasEnchantments()) {
			model.render(cellularNoise, light, overlay);
		}

		matrices.pop();
		matrices.pop();

		matrices.push();
		matrices.translate(0.5f, 0.2f, -0.03f);
		matrices.multiply(new Quaternionf(35, 0.0F, 0.0F, 1.0F));
		matrices.push();
		matrices.multiply(new Quaternionf(225, 0.0F, 0.0F, 1.0F));
		matrices.translate(-0.5f, 0.5f, 0.0f);
		matrices.scale(0.25f, 0.5f, 0.25f);
        VertexConsumer hydrodVertexConsumer = HYDROD.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid);
        md2.render(matrices, hydrodVertexConsumer, light, overlay);
		if (stack.hasEnchantments()) {
			md2.render(matrices, cellularNoise, light, overlay);
		}
		matrices.pop();
		matrices.pop();

		matrices.push();
		matrices.translate(0.5f, 0.2f, -0.03f);
		matrices.multiply(new Quaternionf(35, 0.0F, 0.0F, 1.0F));
		matrices.push();
		matrices.multiply(new Quaternionf(247.5f, 0.0F, 0.0F, 1.0F));
		matrices.translate(-0.175f, 0.1f, 0.0f);
		matrices.scale(0.25f, 0.5f, 0.25f);
		md3.render(matrices, hydrodVertexConsumer, light, overlay);
		if (stack.hasEnchantments()) {
			md3.render(matrices, cellularNoise, light, overlay);
		}
		matrices.pop();
		matrices.pop();
		matrices.pop();
	}
}

