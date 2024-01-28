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
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class RenderLaserBurst extends EntityRenderer<EntityLaserBurst>
{
	static float	red		= 1F;
	static float	green	= 0.0F;
	static float	blue	= 0.0F;

    public RenderLaserBurst(EntityRendererFactory.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityLaserBurst entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        float radius = 0.12F;
        int distance = 4;
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        matrices.push();
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
        matrices.translate(entity.getX(), entity.getY(), entity.getZ());

        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(entity.getYaw()));
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-entity.getPitch()));

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
        RenderSystem.disableBlend();
        matrices.pop();
    }

    @Override
    public Identifier getTexture(EntityLaserBurst entity) {
        return null;
    }

}
