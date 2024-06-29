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
import assets.rivalrebels.common.entity.EntityFlameBall;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.world.inventory.InventoryMenu;

public class RenderFlame extends EntityRenderer<EntityFlameBall> {
    private static final Material FLAMEBALL_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, RRIdentifiers.etflameball);
    public RenderFlame(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityFlameBall entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		matrices.pushPose();

		matrices.pushPose();
		float X = (entity.sequence % 4) / 4f;
		float Y = (entity.sequence - (entity.sequence % 4)) / 16f;
		float size = 0.0500f * entity.tickCount;
		size *= size;
        VertexConsumer buffer = FLAMEBALL_TEXTURE.buffer(vertexConsumers, RenderType::entityTranslucentEmissive);
		matrices.mulPose(Axis.YP.rotationDegrees(180 - Minecraft.getInstance().player.getYRot()));
		matrices.mulPose(Axis.XP.rotationDegrees(90 - Minecraft.getInstance().player.getXRot()));
		matrices.pushPose();
		matrices.mulPose(Axis.YP.rotationDegrees(entity.rotation));
		buffer.addVertex(matrices.last(), -size, 0, -size).setColor(CommonColors.WHITE).setUv(X, Y).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(matrices.last(), 0, 1, 0);
		buffer.addVertex(matrices.last(), size, 0, -size).setColor(CommonColors.WHITE).setUv(X + 0.25f, Y).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(matrices.last(), 0, 1, 0);
		buffer.addVertex(matrices.last(), size, 0, size).setColor(CommonColors.WHITE).setUv(X + 0.25f, Y + 0.25f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(matrices.last(), 0, 1, 0);
		buffer.addVertex(matrices.last(), -size, 0, size).setColor(CommonColors.WHITE).setUv(X, Y + 0.25f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(matrices.last(), 0, 1, 0);
		matrices.popPose();
		matrices.popPose();

		matrices.popPose();
	}

    @Override
    public ResourceLocation getTextureLocation(EntityFlameBall entity) {
        return RRIdentifiers.etflameball;
    }
}
