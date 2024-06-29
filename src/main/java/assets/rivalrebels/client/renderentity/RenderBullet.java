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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.Entity;

public class RenderBullet extends EntityRenderer<Entity> {
    private final String path;

	public RenderBullet(EntityRendererProvider.Context manager, String path) {
        super(manager);
		this.path = path;
	}

    @Override
    public void render(Entity entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		if (entity.tickCount > 1) {
			matrices.pushPose();
			matrices.scale(0.5F, 0.5F, 0.5F);
            float var7 = 1.0F;
            float var8 = 0.5F;
            float var9 = 0.25F;
            matrices.mulPose(Axis.YP.rotationDegrees((float) (180.0F - this.entityRenderDispatcher.camera.getPosition().y())));
            matrices.mulPose(Axis.XP.rotationDegrees((float) -this.entityRenderDispatcher.camera.getPosition().x()));
            VertexConsumer buffer = vertexConsumers.getBuffer(RenderType.entitySolid(getTextureLocation(entity)));
            buffer.addVertex((0.0F - var8), (0.0F - var9), 0, CommonColors.WHITE, 0, 0, OverlayTexture.NO_OVERLAY, light, 0, 1, 0);
            buffer.addVertex((var7 - var8), (0.0F - var9), 0, CommonColors.WHITE, 1, 0, OverlayTexture.NO_OVERLAY, light, 0, 1, 0);
            buffer.addVertex((var7 - var8), (var7 - var9), 0, CommonColors.WHITE, 1, 1, OverlayTexture.NO_OVERLAY, light, 0, 1, 0);
            buffer.addVertex((0.0F - var8), (var7 - var9), 0, CommonColors.WHITE, 0, 1, OverlayTexture.NO_OVERLAY, light, 0, 1, 0);
			matrices.popPose();
		}
	}

    @Override
    public ResourceLocation getTextureLocation(Entity entity) {
        if (path.equals("flame")) return RRIdentifiers.etflame;
        if (path.equals("fire")) return RRIdentifiers.etfire;
        return null;
    }
}
