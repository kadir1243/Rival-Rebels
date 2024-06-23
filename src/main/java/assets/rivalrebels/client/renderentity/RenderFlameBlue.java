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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import org.joml.Quaternionf;

public class RenderFlameBlue extends EntityRenderer<EntityFlameBall2>
{
    public RenderFlameBlue(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityFlameBall2 entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		if (entity.tickCount < 3) return;
		matrices.pushPose();

        matrices.pushPose();
		float X = (entity.sequence % 4) / 4f;
		float Y = (entity.sequence - (entity.sequence % 4)) / 16f;
		float size = 0.070f * entity.tickCount;
		size *= size;
		if (size >= 0.3) size = 0.3f;
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderType.entityTranslucentEmissive(RRIdentifiers.etflameblue));
		matrices.mulPose(new Quaternionf(180 - Minecraft.getInstance().player.getYRot(), 0.0F, 1.0F, 0.0F));
		matrices.mulPose(new Quaternionf(90 - Minecraft.getInstance().player.getXRot(), 1.0F, 0.0F, 0.0F));
		matrices.pushPose();
		matrices.mulPose(new Quaternionf(entity.rotation, 0.0F, 1.0F, 0.0F));
        buffer.addVertex(-size, 0, -size, FastColor.ARGB32.colorFromFloat(1F, 1F, 1F, 1F), X, Y, OverlayTexture.NO_OVERLAY, light, 0, 1, 0);
        buffer.addVertex( size, 0, -size, FastColor.ARGB32.colorFromFloat(1F, 1F, 1F, 1F), X + 0.25f, Y, OverlayTexture.NO_OVERLAY, light, 0, 1, 0);
        buffer.addVertex( size, 0,  size, FastColor.ARGB32.colorFromFloat(1F, 1F, 1F, 1F), X + 0.25f, Y + 0.25f, OverlayTexture.NO_OVERLAY, light, 0, 1, 0);
        buffer.addVertex(-size, 0,  size, FastColor.ARGB32.colorFromFloat(1F, 1F, 1F, 1F), X, Y + 0.25f, OverlayTexture.NO_OVERLAY, light, 0, 1, 0);
		matrices.popPose();
		matrices.popPose();

		matrices.popPose();
	}

    @Override
    public ResourceLocation getTextureLocation(EntityFlameBall2 entity) {
        return RRIdentifiers.etflameblue;
    }
}
