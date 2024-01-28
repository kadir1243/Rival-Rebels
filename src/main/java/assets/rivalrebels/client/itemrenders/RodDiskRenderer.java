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
import assets.rivalrebels.client.model.ModelDisk;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Quaternion;

public class RodDiskRenderer extends BuiltinModelItemRenderer
{
	ModelDisk	md;

	public RodDiskRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelLoader loader) {
        super(dispatcher, loader);
		md = new ModelDisk();
	}

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etdisk0);
		matrices.push();
		matrices.translate(0.5f, 0.25f, 0f);
		matrices.multiply(new Quaternion(35, 0.0F, 0.0F, 1.0F));
		matrices.multiply(new Quaternion(-25, 1.0F, 0.0F, 0.0F));
		matrices.scale(0.5f, 0.5f, 0.5f);
		matrices.push();

		md.render(matrices, vertexConsumers.getBuffer(RenderLayers.getItemLayer(stack, true)));

		matrices.pop();
		matrices.pop();
	}
}

