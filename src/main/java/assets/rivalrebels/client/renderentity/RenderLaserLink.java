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
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.lighting.LightEngine;

public class RenderLaserLink extends EntityRenderer<EntityLaserLink> {
    static float red = 0.5F;
    static float green = 0.1F;
    static float blue = 0.1F;

    public RenderLaserLink(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityLaserLink entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        float distance = (float) (entity.getDeltaMovement().x() * 100F);
        if (distance > 0) {
            float radius = 0.7F;
            VertexConsumer buffer = vertexConsumers.getBuffer(RenderType.solid());

            matrices.pushPose();
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            matrices.mulPose(Axis.YP.rotationDegrees(-entity.getYRot()));
            matrices.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));

            for (float o = 0; o <= radius; o += radius / 16) {
                buffer.addVertex(matrices.last(), 0 + o, 0 - o, 0).setColor(red, green, blue, 1);
                buffer.addVertex(matrices.last(), 0 + o, 0 + o, 0).setColor(red, green, blue, 1);
                buffer.addVertex(matrices.last(), 0 + o, 0 + o, 0 + distance).setColor(red, green, blue, 1);
                buffer.addVertex(matrices.last(), 0 + o, 0 - o, 0 + distance).setColor(red, green, blue, 1);

                buffer.addVertex(matrices.last(), 0 - o, 0 - o, 0).setColor(red, green, blue, 1);
                buffer.addVertex(matrices.last(), 0 - o, 0 - o, 0 + distance).setColor(red, green, blue, 1);
                buffer.addVertex(matrices.last(), 0 - o, 0 + o, 0).setColor(red, green, blue, 1);
                buffer.addVertex(matrices.last(), 0 - o, 0 + o, 0 + distance).setColor(red, green, blue, 1);
            }

            RenderSystem.disableBlend();
            matrices.popPose();
        }
    }

    @Override
    public ResourceLocation getTextureLocation(EntityLaserLink entity) {
        return null;
    }

    @Override
    public boolean shouldRender(EntityLaserLink livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    protected int getBlockLightLevel(EntityLaserLink entity, BlockPos pos) {
        return LightEngine.MAX_LEVEL;
    }
}
