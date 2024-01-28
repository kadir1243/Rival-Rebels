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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Quaternion;

public class BinocularsRenderer extends BuiltinModelItemRenderer {
	private final ModelFromObj model;

	public BinocularsRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelLoader loader) {
        super(dispatcher, loader);
        model = ModelFromObj.readObjFile("b.obj");
	}

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        MinecraftClient.getInstance().textureManager.bindTexture(RRIdentifiers.etbinoculars);
		matrices.push();
		matrices.translate(0.5f, 0.5f, -0.03f);
		matrices.multiply(new Quaternion(35, 0.0F, 0.0F, 1.0F));
		matrices.multiply(new Quaternion(90, 0.0F, 1.0F, 0.0F));
		matrices.scale(0.35f, 0.35f, 0.35f);
		if (mode == ModelTransformation.Mode.HEAD && (MinecraftClient.getInstance().mouse.wasRightButtonClicked())) {
			matrices.pop();
			return;
		}
		matrices.translate(0.6f, 0.05f, 0.3f);

		model.render(vertexConsumers.getBuffer(RenderLayer.getSolid()));

		matrices.pop();
	}
}

