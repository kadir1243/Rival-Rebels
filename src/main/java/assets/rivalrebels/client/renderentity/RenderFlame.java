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
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import org.joml.Quaternionf;

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
		matrices.mulPose(new Quaternionf(180 - Minecraft.getInstance().player.getYRot(), 0.0F, 1.0F, 0.0F));
		matrices.mulPose(new Quaternionf(90 - Minecraft.getInstance().player.getXRot(), 1.0F, 0.0F, 0.0F));
		matrices.pushPose();
		matrices.mulPose(Axis.YP.rotationDegrees(entity.rotation));
		buffer.addVertex(matrices.last(), -size, 0, -size).setColor(1f, 1f, 1f, 1f).setUv(X, Y).setLight(light).setNormal(0, 1, 0);
		buffer.addVertex(matrices.last(), size, 0, -size).setColor(1f, 1f, 1f, 1f).setUv(X + 0.25f, Y).setLight(light).setNormal(0, 1, 0);
		buffer.addVertex(matrices.last(), size, 0, size).setColor(1f, 1f, 1f, 1f).setUv(X + 0.25f, Y + 0.25f).setLight(light).setNormal(0, 1, 0);
		buffer.addVertex(matrices.last(), -size, 0, size).setColor(1f, 1f, 1f, 1f).setUv(X, Y + 0.25f).setLight(light).setNormal(0, 1, 0);
		matrices.popPose();
		matrices.popPose();

		matrices.popPose();
	}

    @Override
    public ResourceLocation getTextureLocation(EntityFlameBall entity) {
        return RRIdentifiers.etflameball;
    }
}
