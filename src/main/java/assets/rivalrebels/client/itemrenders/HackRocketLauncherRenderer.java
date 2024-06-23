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
import assets.rivalrebels.client.model.ModelRocketLauncherBody;
import assets.rivalrebels.client.model.ModelRocketLauncherHandle;
import assets.rivalrebels.client.model.ModelRocketLauncherTube;
import assets.rivalrebels.client.renderentity.RenderB83;
import assets.rivalrebels.common.noise.RivalRebelsCellularNoise;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.joml.Quaternionf;

public class HackRocketLauncherRenderer implements DynamicItemRenderer {
    public static final SpriteIdentifier ROCKET_LAUNCHER_HANDLE_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etrocketlauncherhandle);
    public static final SpriteIdentifier HACK_202_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.ethack202);
    public static final SpriteIdentifier ROCKET_LAUNCHER_TUBE_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etrocketlaunchertube);
    public static final SpriteIdentifier B83_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etb83);

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		matrices.translate(0.4f, 0.35f, -0.03f);
		matrices.multiply(new Quaternionf(-55, 0.0F, 0.0F, 1.0F));
		matrices.translate(0f, 0.05f, 0.05f);
		if (mode.isFirstPerson()) matrices.scale(1, 1, -1);
		matrices.push();
		matrices.translate(0.22f, -0.025f, 0f);
		matrices.multiply(new Quaternionf(90, 0.0F, 0.0F, 1.0F));
		matrices.scale(0.03125f, 0.03125f, 0.03125f);
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayers.getItemLayer(stack, true));
        VertexConsumer cellular_noise = vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE);
        ModelRocketLauncherHandle.render(matrices, ROCKET_LAUNCHER_HANDLE_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay);
		if (stack.hasEnchantments()) {
			ModelRocketLauncherHandle.render(matrices, cellular_noise, light, overlay);
		}
		matrices.pop();

		matrices.push();
		matrices.translate(-0.07f, 0.31f, 0f);
		matrices.multiply(new Quaternionf(90, 0.0F, 0.0F, 1.0F));
		matrices.multiply(new Quaternionf(90, 0.0F, 1.0F, 0.0F));
		matrices.scale(0.4f, 0.4f, 0.4f);
		ModelRocketLauncherBody.render(matrices, HACK_202_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay);
		if (stack.hasEnchantments()) {
			ModelRocketLauncherBody.render(matrices, cellular_noise, light, overlay);
		}
		matrices.pop();

		float s = 0.0812f;

		matrices.push();
		matrices.translate(-0.07f + s, 0.71f, s);
		matrices.scale(0.15f, 0.1f, 0.15f);
        ModelRocketLauncherTube.render(matrices, ROCKET_LAUNCHER_TUBE_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay);
		if (stack.hasEnchantments()) {
			ModelRocketLauncherTube.render(matrices, cellular_noise, light, overlay);
		}
		matrices.pop();

		matrices.push();
		matrices.translate(-0.07f - s, 0.71f, s);
		matrices.scale(0.15f, 0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, buffer, light, overlay);
		if (stack.hasEnchantments()) {
			ModelRocketLauncherTube.render(matrices, cellular_noise, light, overlay);
		}
		matrices.pop();

		matrices.push();
		matrices.translate(-0.07f + s, 0.71f, -s);
		matrices.scale(0.15f, 0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, buffer, light, overlay);
		if (stack.hasEnchantments()) {
			ModelRocketLauncherTube.render(matrices, cellular_noise, light, overlay);
		}
		matrices.pop();

		matrices.push();
		matrices.translate(-0.07f - s, 0.71f, -s);
		matrices.scale(0.15f, 0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, buffer, light, overlay);
		if (stack.hasEnchantments()) {
			ModelRocketLauncherTube.render(matrices, cellular_noise, light, overlay);
		}
		matrices.pop();

		// ---

		matrices.push();
		matrices.translate(-0.07f + s, -0.285f, s);
		matrices.scale(0.15f, -0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, buffer, light, overlay);
		if (stack.hasEnchantments()) {
			ModelRocketLauncherTube.render(matrices, cellular_noise, light, overlay);
		}
		matrices.pop();

		matrices.push();
		matrices.translate(-0.07f - s, -0.285f, s);
		matrices.scale(0.15f, -0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, buffer, light, overlay);
		if (stack.hasEnchantments()) {
			ModelRocketLauncherTube.render(matrices, cellular_noise, light, overlay);
		}
		matrices.pop();

		matrices.push();
		matrices.translate(-0.07f + s, -0.285f, -s);
		matrices.scale(0.15f, -0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, buffer, light, overlay);
		if (stack.hasEnchantments()) {
			ModelRocketLauncherTube.render(matrices, cellular_noise, light, overlay);
		}
		matrices.pop();

		matrices.push();
		matrices.translate(-0.07f - s, -0.285f, -s);
		matrices.scale(0.15f, -0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, buffer, light, overlay);
		if (stack.hasEnchantments()) {
			ModelRocketLauncherTube.render(matrices, cellular_noise, light, overlay);
		}
		matrices.pop();

		matrices.push();
		matrices.multiply(new Quaternionf(-90, 0.0F, 0.0F, 1.0F));
		matrices.scale(0.7f, 0.7f, 0.7f);
		matrices.translate(-0.5f, -0.1f, 0);
		RenderB83.md.render(B83_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay);
		if (stack.hasEnchantments()) {
			RenderB83.md.render(cellular_noise, light, overlay);
		}
		matrices.pop();
		matrices.pop();
	}
}

