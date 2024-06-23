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
import assets.rivalrebels.common.noise.RivalRebelsCellularNoise;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.joml.Quaternionf;

public class RodaRenderer implements DynamicItemRenderer {
    public static final SpriteIdentifier RUST_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etrust);
	private static final ModelFromObj model = ModelFromObj.readObjFile("e.obj");

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		matrices.translate(0.5f, 0.5f, -0.03f);
		matrices.multiply(new Quaternionf(35, 0.0F, 0.0F, 1.0F));
		matrices.multiply(new Quaternionf(90, 0.0F, 1.0F, 0.0F));
		matrices.scale(0.35f, 0.35f, 0.35f);
		if (!mode.isFirstPerson()) matrices.scale(-1, 1, 1);
		matrices.translate(0.2f, -0.55f, 0.1f);

        model.render(RUST_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay);
		matrices.push();
		model.render(vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE), light, overlay);
		matrices.pop();

		matrices.pop();
	}
}

