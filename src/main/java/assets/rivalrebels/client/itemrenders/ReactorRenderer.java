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
import assets.rivalrebels.client.model.ModelLaptop;
import assets.rivalrebels.client.model.ModelReactor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class ReactorRenderer extends BuiltinModelItemRenderer
{
	ModelReactor	mr;
	ModelLaptop		ml;

	public ReactorRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelLoader loader) {
        super(dispatcher, loader);
		mr = new ModelReactor();
		ml = new ModelLaptop();
	}

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        matrices.translate(0.5F, 1.1875F, 0.5F);
		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etlaptop);
		ml.renderModel(buffer, matrices, 0);
		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etscreen);
		ml.renderScreen(buffer, matrices, 0);
		matrices.pop();
		matrices.push();
		matrices.translate(0.5F, 0.5F, 0.5F);
		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etreactor);
		mr.renderModel(matrices, buffer);
		matrices.pop();
	}
}

