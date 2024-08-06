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

public class RenderLaserBurst extends EntityRenderer<EntityLaserBurst>
{
	private static final float red = 1F;

    public RenderLaserBurst(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityLaserBurst entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        float radius = 0.12F;
        int distance = 4;
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderType.lightning());
        matrices.pushPose();

        matrices.mulPose(Axis.YP.rotationDegrees(entity.getYRot()));
        matrices.mulPose(Axis.XP.rotationDegrees(-entity.getXRot()));

        for (float o = 0; o <= radius; o += radius / 8) {
            float color = 1f - (o * 8.333f);
            if (color < 0) color = 0;
            buffer.addVertex(matrices.last(), 0 + o, 0 - o, 0).setColor(red, color, color, 1);
            buffer.addVertex(matrices.last(), 0 + o, 0 + o, 0).setColor(red, color, color, 1);
            buffer.addVertex(matrices.last(), 0 + o, 0 + o, distance).setColor(red, color, color, 1);
            buffer.addVertex(matrices.last(), 0 + o, 0 - o, distance).setColor(red, color, color, 1);

            buffer.addVertex(matrices.last(), 0 - o, 0 - o, 0).setColor(red, color, color, 1);
            buffer.addVertex(matrices.last(), 0 - o, 0 - o, distance).setColor(red, color, color, 1);
            buffer.addVertex(matrices.last(), 0 - o, 0 + o, 0).setColor(red, color, color, 1);
            buffer.addVertex(matrices.last(), 0 - o, 0 + o, distance).setColor(red, color, color, 1);
        }
        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EntityLaserBurst entity) {
        return null;
    }

    @Override
    public boolean shouldRender(EntityLaserBurst livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    protected int getBlockLightLevel(EntityLaserBurst entity, BlockPos pos) {
        return LightEngine.MAX_LEVEL;
    }
}
