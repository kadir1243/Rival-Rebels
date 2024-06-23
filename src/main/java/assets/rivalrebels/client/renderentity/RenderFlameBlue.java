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
import assets.rivalrebels.common.entity.EntityFlameBall2;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;

public class RenderFlameBlue extends EntityRenderer<EntityFlameBall2>
{
    public RenderFlameBlue(EntityRendererFactory.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityFlameBall2 entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		if (entity.age < 3) return;
		matrices.push();

        matrices.push();
		float X = (entity.sequence % 4) / 4f;
		float Y = (entity.sequence - (entity.sequence % 4)) / 16f;
		float size = 0.070f * entity.age;
		size *= size;
		if (size >= 0.3) size = 0.3f;
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentEmissive(RRIdentifiers.etflameblue));
		matrices.multiply(new Quaternionf(180 - MinecraftClient.getInstance().player.getYaw(), 0.0F, 1.0F, 0.0F));
		matrices.multiply(new Quaternionf(90 - MinecraftClient.getInstance().player.getPitch(), 1.0F, 0.0F, 0.0F));
		matrices.push();
		matrices.multiply(new Quaternionf(entity.rotation, 0.0F, 1.0F, 0.0F));
        buffer.vertex(-size, 0, -size, 1F, 1F, 1F, 1F, X, Y, OverlayTexture.DEFAULT_UV, light, 0, 1, 0);
        buffer.vertex( size, 0, -size, 1F, 1F, 1F, 1F, X + 0.25f, Y, OverlayTexture.DEFAULT_UV, light, 0, 1, 0);
        buffer.vertex( size, 0,  size, 1F, 1F, 1F, 1F, X + 0.25f, Y + 0.25f, OverlayTexture.DEFAULT_UV, light, 0, 1, 0);
        buffer.vertex(-size, 0,  size, 1F, 1F, 1F, 1F, X, Y + 0.25f, OverlayTexture.DEFAULT_UV, light, 0, 1, 0);
		matrices.pop();
		matrices.pop();

		matrices.pop();
	}

    @Override
    public Identifier getTexture(EntityFlameBall2 entity) {
        return RRIdentifiers.etflameblue;
    }
}
