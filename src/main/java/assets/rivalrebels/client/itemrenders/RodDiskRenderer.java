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
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.joml.Quaternionf;

public class RodDiskRenderer implements DynamicItemRenderer {
    public static final SpriteIdentifier DISK_0_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etdisk0);
    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		matrices.translate(0.5f, 0.25f, 0f);
		matrices.multiply(new Quaternionf(35, 0.0F, 0.0F, 1.0F));
		matrices.multiply(new Quaternionf(-25, 1.0F, 0.0F, 0.0F));
		matrices.scale(0.5f, 0.5f, 0.5f);
		matrices.push();

		ModelDisk.render(matrices, DISK_0_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay);

		matrices.pop();
		matrices.pop();
	}
}

