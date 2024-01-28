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

import assets.rivalrebels.common.entity.EntityLaserLink;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;

public class RenderLaserLink extends EntityRenderer<EntityLaserLink> {
    static float red = 0.5F;
    static float green = 0.1F;
    static float blue = 0.1F;

    public RenderLaserLink(EntityRendererFactory.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityLaserLink entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        double distance = entity.getVelocity().getX() * 100;
        if (distance > 0) {
            float radius = 0.7F;
            VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getSolid());

            matrices.push();
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(SrcFactor.ONE, DstFactor.ONE);
            matrices.multiply(new Quaternion(-entity.getYaw(), 0.0F, 1.0F, 0.0F));
            matrices.multiply(new Quaternion(entity.getPitch(), 1.0F, 0.0F, 0.0F));

            for (float o = 0; o <= radius; o += radius / 16) {
                buffer.vertex(0 + o, 0 - o, 0).color(red, green, blue, 1).next();
                buffer.vertex(0 + o, 0 + o, 0).color(red, green, blue, 1).next();
                buffer.vertex(0 + o, 0 + o, 0 + distance).color(red, green, blue, 1).next();
                buffer.vertex(0 + o, 0 - o, 0 + distance).color(red, green, blue, 1).next();
                buffer.vertex(0 - o, 0 - o, 0).color(red, green, blue, 1).next();
                buffer.vertex(0 - o, 0 - o, 0 + distance).color(red, green, blue, 1).next();
                buffer.vertex(0 - o, 0 + o, 0).color(red, green, blue, 1).next();
                buffer.vertex(0 - o, 0 + o, 0 + distance).color(red, green, blue, 1).next();
            }

            RenderSystem.disableBlend();
            matrices.pop();
        }
    }

    @Override
    public Identifier getTexture(EntityLaserLink entity) {
        return null;
    }
}
