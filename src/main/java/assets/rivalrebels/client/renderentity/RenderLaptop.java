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
package assets.rivalrebels.client.renderentity;

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.model.ModelLaptop;
import assets.rivalrebels.common.entity.EntityLaptop;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;

public class RenderLaptop extends EntityRenderer<EntityLaptop> {
    public static final SpriteIdentifier LAPTOP_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etlaptop);
    public static final SpriteIdentifier SCREEN_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etubuntu);
    public RenderLaptop(EntityRendererFactory.Context manager) {
        super(manager);
	}

    @Override
    public void render(EntityLaptop entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
		matrices.multiply(new Quaternionf(180 - entity.getYaw(), 0, 1, 0));
		ModelLaptop.renderModel(LAPTOP_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), matrices, (float) -entity.slide, light, OverlayTexture.DEFAULT_UV);
		ModelLaptop.renderScreen(SCREEN_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), matrices, (float) -entity.slide, light, OverlayTexture.DEFAULT_UV);
		matrices.pop();
	}

    @Override
    public Identifier getTexture(EntityLaptop entity) {
        return null;
    }
}
