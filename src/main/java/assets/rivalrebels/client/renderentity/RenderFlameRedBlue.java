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
import assets.rivalrebels.common.entity.EntityFlameBall1;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import org.lwjgl.opengl.GL14;

public class RenderFlameRedBlue extends EntityRenderer<EntityFlameBall1>
{
    public RenderFlameRedBlue(EntityRendererFactory.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityFlameBall1 entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (entity.age < 3) return;
        matrices.push();
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
        RenderSystem.blendEquation(GL14.GL_FUNC_ADD);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        matrices.push();
        float X = (entity.sequence % 4) / 4f;
        float Y = (entity.sequence - (entity.sequence % 4)) / 16f;
        float size = 0.0550f * entity.age;
        size *= size;
        // if (size >= 0.5) size = 0.5f;
        size += 0.05;
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        matrices.multiply(new Quaternion(180 - MinecraftClient.getInstance().player.getYaw(), 0.0F, 1.0F, 0.0F));
        matrices.multiply(new Quaternion(90 - MinecraftClient.getInstance().player.getPitch(), 1.0F, 0.0F, 0.0F));
        matrices.push();
        matrices.multiply(new Quaternion(entity.rotation, 0.0F, 1.0F, 0.0F));
        buffer.vertex(-size, 0, -size).texture(X, Y).normal(0, 1, 0).next();
        buffer.vertex(size, 0, -size).texture(X + 0.25f, Y).normal(0, 1, 0).next();
        buffer.vertex(size, 0, size).texture(X + 0.25f, Y + 0.25f).normal(0, 1, 0).next();
        buffer.vertex(-size, 0, size).texture(X, Y + 0.25f).normal(0, 1, 0).next();
        matrices.pop();
        matrices.pop();

        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        matrices.pop();
    }

    @Override
    public Identifier getTexture(EntityFlameBall1 entity) {
        return RRIdentifiers.etflamebluered;
    }
}
