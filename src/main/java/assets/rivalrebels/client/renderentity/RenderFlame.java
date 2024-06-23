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
import assets.rivalrebels.common.entity.EntityFlameBall;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;

public class RenderFlame extends EntityRenderer<EntityFlameBall> {
    private static final SpriteIdentifier FLAMEBALL_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, RRIdentifiers.etflameball);
    public RenderFlame(EntityRendererFactory.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityFlameBall entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		matrices.push();

		matrices.push();
		float X = (entity.sequence % 4) / 4f;
		float Y = (entity.sequence - (entity.sequence % 4)) / 16f;
		float size = 0.0500f * entity.age;
		size *= size;
        VertexConsumer buffer = FLAMEBALL_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntityTranslucentEmissive);
		matrices.multiply(new Quaternionf(180 - MinecraftClient.getInstance().player.getYaw(), 0.0F, 1.0F, 0.0F));
		matrices.multiply(new Quaternionf(90 - MinecraftClient.getInstance().player.getPitch(), 1.0F, 0.0F, 0.0F));
		matrices.push();
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.rotation));
		buffer.vertex(-size, 0, -size).color(1f, 1f, 1f, 1f).texture(X, Y).light(light).normal(0, 1, 0).next();
		buffer.vertex(size, 0, -size).color(1f, 1f, 1f, 1f).texture(X + 0.25f, Y).light(light).normal(0, 1, 0).next();
		buffer.vertex(size, 0, size).color(1f, 1f, 1f, 1f).texture(X + 0.25f, Y + 0.25f).light(light).normal(0, 1, 0).next();
		buffer.vertex(-size, 0, size).color(1f, 1f, 1f, 1f).texture(X, Y + 0.25f).light(light).normal(0, 1, 0).next();
		matrices.pop();
		matrices.pop();

		matrices.pop();
	}

    @Override
    public Identifier getTexture(EntityFlameBall entity) {
        return RRIdentifiers.etflameball;
    }
}
