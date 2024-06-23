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

import assets.rivalrebels.common.entity.EntityLaserBurst;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class RenderLaserBurst extends EntityRenderer<EntityLaserBurst>
{
	private static final float red = 1F;

    public RenderLaserBurst(EntityRendererFactory.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityLaserBurst entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        float radius = 0.12F;
        int distance = 4;
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getLightning());
        matrices.push();
        matrices.translate(entity.getX(), entity.getY(), entity.getZ());

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getYaw()));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-entity.getPitch()));

        for (float o = 0; o <= radius; o += radius / 8) {
            float color = 1f - (o * 8.333f);
            if (color < 0) color = 0;
            buffer.vertex(0 + o, 0 - o, 0).color(red, color, color, 1).next();
            buffer.vertex(0 + o, 0 + o, 0).color(red, color, color, 1).next();
            buffer.vertex(0 + o, 0 + o, distance).color(red, color, color, 1).next();
            buffer.vertex(0 + o, 0 - o, distance).color(red, color, color, 1).next();
            buffer.vertex(0 - o, 0 - o, 0).color(red, color, color, 1).next();
            buffer.vertex(0 - o, 0 - o, distance).color(red, color, color, 1).next();
            buffer.vertex(0 - o, 0 + o, 0).color(red, color, color, 1).next();
            buffer.vertex(0 - o, 0 + o, distance).color(red, color, color, 1).next();
        }
        matrices.pop();
    }

    @Override
    public Identifier getTexture(EntityLaserBurst entity) {
        return null;
    }

}
