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
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.joml.Quaternionf;

public class TeslaRenderer implements DynamicItemRenderer {
    public static final SpriteIdentifier TESLA_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.ettesla);
    private static final ModelFromObj tesla = ModelFromObj.readObjFile("i.obj");
	private static final ModelFromObj dynamo = ModelFromObj.readObjFile("j.obj");
	private int spin;

	private static int getDegree(ItemStack item) {
        return item.getOrCreateNbt().getInt("dial");
	}

    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!stack.hasEnchantments()) {
            VertexConsumer buffer = TESLA_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid);
            int degree = getDegree(stack);
			spin += 5 + (degree / 36f);
			matrices.push();
			matrices.translate(0.8f, 0.5f, -0.03f);
			matrices.multiply(new Quaternionf(35, 0.0F, 0.0F, 1.0F));
			matrices.multiply(new Quaternionf(90, 0.0F, 1.0F, 0.0F));
			matrices.scale(0.12f, 0.12f, 0.12f);
			// matrices.translate(0.3f, 0.05f, -0.1f);

			tesla.render(buffer, light, overlay);
			matrices.multiply(new Quaternionf(spin, 1.0F, 0.0F, 0.0F));
			dynamo.render(buffer, light, overlay);

			matrices.pop();
		} else {
			if (mode != ModelTransformationMode.FIRST_PERSON_RIGHT_HAND) matrices.pop();
			matrices.push();
            VertexConsumer cellularNoise = vertexConsumers.getBuffer(RivalRebelsCellularNoise.CELLULAR_NOISE);
			matrices.scale(1.01f, 1.01f, 1.01f);
			matrices.multiply(new Quaternionf(45.0f, 0, 1, 0));
			matrices.multiply(new Quaternionf(10.0f, 0, 0, 1));
			matrices.scale(0.6f, 0.2f, 0.2f);
			matrices.translate(-0.99f, 0.5f, 0.0f);
            cellularNoise.vertex(-1, -1, -1).color(1, 1, 1, 1).texture(0, 0).light(light).next();
            cellularNoise.vertex(-1,  1, -1).color(1, 1, 1, 1).texture(1, 0).light(light).next();
            cellularNoise.vertex(-1,  1, 1).color(1, 1, 1, 1).texture(1, 1).light(light).next();
            cellularNoise.vertex(-1, -1, 1).color(1, 1, 1, 1).texture(0, 1).light(light).next();
            cellularNoise.vertex( 1, -1, -1).color(1, 1, 1, 1).texture(0, 0).light(light).next();
            cellularNoise.vertex( 1, -1, 1).color(1, 1, 1, 1).texture(0, 1).light(light).next();
            cellularNoise.vertex( 1,  1, 1).color(1, 1, 1, 1).texture(1, 1).light(light).next();
            cellularNoise.vertex( 1,  1, -1).color(1, 1, 1, 1).texture(1, 0).light(light).next();
            cellularNoise.vertex(-1, -1, -1).color(1, 1, 1, 1).texture(0, 0).light(light).next();
            cellularNoise.vertex(-1, -1, 1).color(1, 1, 1, 1).texture(0, 1).light(light).next();
            cellularNoise.vertex( 1, -1, 1).color(1, 1, 1, 1).texture(3, 1).light(light).next();
            cellularNoise.vertex( 1, -1, -1).color(1, 1, 1, 1).texture(3, 0).light(light).next();
            cellularNoise.vertex(-1,  1, -1).color(1, 1, 1, 1).texture(0, 0).light(light).next();
            cellularNoise.vertex( 1,  1, -1).color(1, 1, 1, 1).texture(3, 0).light(light).next();
            cellularNoise.vertex( 1,  1, 1).color(1, 1, 1, 1).texture(3, 1).light(light).next();
            cellularNoise.vertex(-1,  1, 1).color(1, 1, 1, 1).texture(0, 1).light(light).next();
            cellularNoise.vertex(-1, -1, -1).color(1, 1, 1, 1).texture(0, 0).light(light).next();
            cellularNoise.vertex( 1, -1, -1).color(1, 1, 1, 1).texture(3, 0).light(light).next();
            cellularNoise.vertex( 1,  1, -1).color(1, 1, 1, 1).texture(3, 1).light(light).next();
            cellularNoise.vertex(-1,  1, -1).color(1, 1, 1, 1).texture(0, 1).light(light).next();
            cellularNoise.vertex(-1, -1, 1).color(1, 1, 1, 1).texture(0, 0).light(light).next();
            cellularNoise.vertex(-1,  1, 1).color(1, 1, 1, 1).texture(0, 1).light(light).next();
            cellularNoise.vertex( 1,  1, 1).color(1, 1, 1, 1).texture(3, 1).light(light).next();
            cellularNoise.vertex( 1, -1, 1).color(1, 1, 1, 1).texture(3, 0).light(light).next();
            cellularNoise.vertex(-1, -1, -1).color(1, 1, 1, 1).texture(0, 0).light(light).next();
            cellularNoise.vertex(-1,  1, -1).color(1, 1, 1, 1).texture(1, 0).light(light).next();
            cellularNoise.vertex(-1,  1, 1).color(1, 1, 1, 1).texture(1, 1).light(light).next();
            cellularNoise.vertex(-1, -1, 1).color(1, 1, 1, 1).texture(0, 1).light(light).next();
            cellularNoise.vertex( 1, -1, -1).color(1, 1, 1, 1).texture(0, 0).light(light).next();
            cellularNoise.vertex( 1, -1, 1).color(1, 1, 1, 1).texture(0, 1).light(light).next();
            cellularNoise.vertex( 1,  1, 1).color(1, 1, 1, 1).texture(1, 1).light(light).next();
            cellularNoise.vertex( 1,  1, -1).color(1, 1, 1, 1).texture(1, 0).light(light).next();
            cellularNoise.vertex(-1, -1, -1).color(1, 1, 1, 1).texture(0, 0).light(light).next();
            cellularNoise.vertex(-1, -1, 1).color(1, 1, 1, 1).texture(0, 1).light(light).next();
            cellularNoise.vertex( 1, -1, 1).color(1, 1, 1, 1).texture(3, 1).light(light).next();
            cellularNoise.vertex( 1, -1, -1).color(1, 1, 1, 1).texture(3, 0).light(light).next();
            cellularNoise.vertex(-1,  1, -1).color(1, 1, 1, 1).texture(0, 0).light(light).next();
            cellularNoise.vertex( 1,  1, -1).color(1, 1, 1, 1).texture(3, 0).light(light).next();
            cellularNoise.vertex( 1,  1, 1).color(1, 1, 1, 1).texture(3, 1).light(light).next();
            cellularNoise.vertex(-1,  1, 1).color(1, 1, 1, 1).texture(0, 1).light(light).next();
            cellularNoise.vertex(-1, -1, -1).color(1, 1, 1, 1).texture(0, 0).light(light).next();
            cellularNoise.vertex( 1, -1, -1).color(1, 1, 1, 1).texture(3, 0).light(light).next();
            cellularNoise.vertex( 1,  1, -1).color(1, 1, 1, 1).texture(3, 1).light(light).next();
            cellularNoise.vertex(-1,  1, -1).color(1, 1, 1, 1).texture(0, 1).light(light).next();
            cellularNoise.vertex(-1, -1,  1).color(1, 1, 1, 1).texture(0, 0).light(light).next();
            cellularNoise.vertex(-1,  1,  1).color(1, 1, 1, 1).texture(0, 1).light(light).next();
            cellularNoise.vertex( 1,  1,  1).color(1, 1, 1, 1).texture(3, 1).light(light).next();
            cellularNoise.vertex( 1, -1,  1).color(1, 1, 1, 1).texture(3, 0).light(light).next();
			matrices.pop();
			if (mode != ModelTransformationMode.FIRST_PERSON_RIGHT_HAND) matrices.push();
		}
	}

}
