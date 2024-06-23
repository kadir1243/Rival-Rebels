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
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

import static net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;

public class ReactorRenderer implements DynamicItemRenderer {
    public static final SpriteIdentifier REACTOR_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etreactor);
    public static final SpriteIdentifier LAPTOP_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etlaptop);
    public static final SpriteIdentifier SCREEN_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etscreen);
    private final ModelReactor mr = new ModelReactor();

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
        matrices.translate(0.5F, 1.1875F, 0.5F);
		ModelLaptop.renderModel(LAPTOP_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), matrices, 0, light, overlay);
		ModelLaptop.renderScreen(SCREEN_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), matrices, 0, light, overlay);
		matrices.pop();
		matrices.push();
		matrices.translate(0.5F, 0.5F, 0.5F);
		mr.renderModel(matrices, REACTOR_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay);
		matrices.pop();
	}
}

