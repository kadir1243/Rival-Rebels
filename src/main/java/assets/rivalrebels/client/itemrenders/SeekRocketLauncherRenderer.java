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

public class SeekRocketLauncherRenderer implements DynamicItemRenderer {
    public static final SpriteIdentifier ROCKET_SEEK_HANDLE_202_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etrocketseekhandle202);
    public static final SpriteIdentifier SEEK_202_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etseek202);
    public static final SpriteIdentifier ROCKET_SEEK_TUBE_202_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etrocketseektube202);

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
        VertexConsumer cellularNoise = vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE);
        ModelRocketLauncherHandle.render(matrices, ROCKET_SEEK_HANDLE_202_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay);
		if (stack.hasEnchantments()) {
			ModelRocketLauncherHandle.render(matrices, cellularNoise, light, overlay);
		}
		matrices.pop();

		matrices.push();
		matrices.translate(-0.07f, 0.31f, 0f);
		matrices.multiply(new Quaternionf(90, 0.0F, 0.0F, 1.0F));
		matrices.multiply(new Quaternionf(90, 0.0F, 1.0F, 0.0F));
		matrices.scale(0.4f, 0.4f, 0.4f);
		ModelRocketLauncherBody.render(matrices, SEEK_202_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay);
		if (stack.hasEnchantments()) {
			ModelRocketLauncherBody.render(matrices, cellularNoise, light, overlay);
        }
		matrices.pop();

		float s = 0.0812f;

		matrices.push();
		matrices.translate(-0.07f + s, 0.71f, s);
		matrices.scale(0.15f, 0.1f, 0.15f);
        VertexConsumer rocketSeekTube202TextureVertexConsumer = ROCKET_SEEK_TUBE_202_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid);
        ModelRocketLauncherTube.render(matrices, rocketSeekTube202TextureVertexConsumer, light, overlay);
		matrices.pop();

		matrices.push();
		matrices.translate(-0.07f - s, 0.71f, s);
		matrices.scale(0.15f, 0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketSeekTube202TextureVertexConsumer, light, overlay);
		matrices.pop();

		matrices.push();
		matrices.translate(-0.07f + s, 0.71f, -s);
		matrices.scale(0.15f, 0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketSeekTube202TextureVertexConsumer, light, overlay);
		matrices.pop();

		matrices.push();
		matrices.translate(-0.07f - s, 0.71f, -s);
		matrices.scale(0.15f, 0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketSeekTube202TextureVertexConsumer, light, overlay);
		matrices.pop();

		// ---

		matrices.push();
		matrices.translate(-0.07f + s, -0.285f, s);
		matrices.scale(0.15f, -0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketSeekTube202TextureVertexConsumer, light, overlay);
		matrices.pop();

		matrices.push();
		matrices.translate(-0.07f - s, -0.285f, s);
		matrices.scale(0.15f, -0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketSeekTube202TextureVertexConsumer, light, overlay);
		matrices.pop();

		matrices.push();
		matrices.translate(-0.07f + s, -0.285f, -s);
		matrices.scale(0.15f, -0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketSeekTube202TextureVertexConsumer, light, overlay);
		matrices.pop();

		matrices.push();
		matrices.translate(-0.07f - s, -0.285f, -s);
		matrices.scale(0.15f, -0.1f, 0.15f);
		ModelRocketLauncherTube.render(matrices, rocketSeekTube202TextureVertexConsumer, light, overlay);
		matrices.pop();

		matrices.pop();
	}
}
