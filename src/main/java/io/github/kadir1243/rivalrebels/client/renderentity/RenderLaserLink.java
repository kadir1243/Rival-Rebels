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
package io.github.kadir1243.rivalrebels.client.renderentity;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.common.entity.EntityLaserLink;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.util.FastColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.lighting.LightEngine;

@OnlyIn(Dist.CLIENT)
public class RenderLaserLink extends EntityRenderer<EntityLaserLink> {
    private static final RenderType RENDER_TYPE = RenderType.create(
        RRIdentifiers.MODID +"_laser_link_entity",
        DefaultVertexFormat.POSITION_COLOR,
        VertexFormat.Mode.QUADS,
        1536,
        RenderType.CompositeState.builder()
            .setShaderState(RenderStateShard.RENDERTYPE_LIGHTNING_SHADER)
            .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
            .setTransparencyState(RenderStateShard.ADDITIVE_TRANSPARENCY)
            .createCompositeState(false)
    );

    private static final int COLOR = FastColor.ARGB32.colorFromFloat(1F, 0.5F, 0.1F, 0.1F);

    public RenderLaserLink(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityLaserLink entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        float distance = (float) (entity.getDeltaMovement().x() * 100F);
        if (distance > 0) {
            float radius = 0.7F;
            VertexConsumer buffer = vertexConsumers.getBuffer(RENDER_TYPE);

            matrices.pushPose();
            matrices.mulPose(Axis.YP.rotationDegrees(-entity.getYRot()));
            matrices.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));

            for (float o = 0; o <= radius; o += radius / 16) {
                buffer.addVertex(matrices.last(), 0 + o, 0 - o, 0).setColor(COLOR);
                buffer.addVertex(matrices.last(), 0 + o, 0 + o, 0).setColor(COLOR);
                buffer.addVertex(matrices.last(), 0 + o, 0 + o, 0 + distance).setColor(COLOR);
                buffer.addVertex(matrices.last(), 0 + o, 0 - o, 0 + distance).setColor(COLOR);

                buffer.addVertex(matrices.last(), 0 - o, 0 - o, 0).setColor(COLOR);
                buffer.addVertex(matrices.last(), 0 - o, 0 - o, 0 + distance).setColor(COLOR);
                buffer.addVertex(matrices.last(), 0 - o, 0 + o, 0).setColor(COLOR);
                buffer.addVertex(matrices.last(), 0 - o, 0 + o, 0 + distance).setColor(COLOR);
            }

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
