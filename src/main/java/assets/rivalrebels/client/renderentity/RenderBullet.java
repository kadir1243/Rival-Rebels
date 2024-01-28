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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;

public class RenderBullet extends EntityRenderer<Entity>
{
	/**
	 * Have the icon index (in items.png) that will be used to render the image. Currently, eggs and snowballs uses this classes.
	 */
	private final String path;

	public RenderBullet(EntityRendererFactory.Context manager, String par1)
	{
        super(manager);
		path = par1;
	}

    @Override
    public void render(Entity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		if (entity.age > 1) {
			matrices.push();
			matrices.scale(0.5F, 0.5F, 0.5F);
			if (path.equals("flame")) MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etflame);
			if (path.equals("fire")) MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etfire);
            float var7 = 1.0F;
            float var8 = 0.5F;
            float var9 = 0.25F;
            matrices.multiply(new Quaternion((float) (180.0F - this.dispatcher.camera.getPos().getY()), 0.0F, 1.0F, 0.0F));
            matrices.multiply(new Quaternion((float) -this.dispatcher.camera.getPos().getX(), 1.0F, 0.0F, 0.0F));
            VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getSolid());
            buffer.vertex((0.0F - var8), (0.0F - var9), 0.0D).texture(0, 0).normal(0, 1, 0).next();
            buffer.vertex((var7 - var8), (0.0F - var9), 0.0D).texture(1, 0).normal(0, 1, 0).next();
            buffer.vertex((var7 - var8), (var7 - var9), 0.0D).texture(1, 1).normal(0, 1, 0).next();
            buffer.vertex((0.0F - var8), (var7 - var9), 0.0D).texture(0, 1).normal(0, 1, 0).next();
			matrices.pop();
		}
	}

    @Override
    public Identifier getTexture(Entity entity) {
        return null;
    }
}
