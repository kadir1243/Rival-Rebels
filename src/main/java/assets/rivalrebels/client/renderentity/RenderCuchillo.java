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
import assets.rivalrebels.common.entity.EntityCuchillo;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import org.joml.Quaternionf;

public class RenderCuchillo extends EntityRenderer<EntityCuchillo> {
    public static final RenderType RENDER_LAYER = RenderType.entitySolid(RRIdentifiers.etknife);
    public RenderCuchillo(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityCuchillo entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		matrices.pushPose();
		matrices.mulPose(new Quaternionf(entity.yRotO + (entity.getYRot() - entity.yRotO) * tickDelta - 90.0F, 0.0F, 1.0F, 0.0F));
		matrices.mulPose(new Quaternionf(entity.xRotO + (entity.getXRot() - entity.xRotO) * tickDelta, 0.0F, 0.0F, 1.0F));
        VertexConsumer buffer = vertexConsumers.getBuffer(RENDER_LAYER);
        byte var11 = 0;
		float var12 = 0.0F;
		float var13 = 0.5F;
		float var14 = (0 + var11 * 10) / 32.0F;
		float var15 = (5 + var11 * 10) / 32.0F;
		float var16 = 0.0F;
		float var17 = 0.15625F;
		float var18 = (5 + var11 * 10) / 32.0F;
		float var19 = (10 + var11 * 10) / 32.0F;
		float var20 = 0.05625F;
        int overlay = OverlayTexture.NO_OVERLAY;

		matrices.mulPose(new Quaternionf(45.0F, 1.0F, 0.0F, 0.0F));
		matrices.scale(var20, var20, var20);
		matrices.translate(-4.0F, 0.0F, 0.0F);
        int defaultColor = FastColor.ARGB32.colorFromFloat(1F, 1F, 1F, 1F);
        buffer.addVertex(-7, -2, -2, defaultColor, var16, var18, overlay, light, var20, 0, 0);
		buffer.addVertex(-7, -2,  2, defaultColor, var17, var18, overlay, light, var20, 0, 0);
		buffer.addVertex(-7,  2,  2, defaultColor, var17, var19, overlay, light, var20, 0, 0);
		buffer.addVertex(-7,  2, -2, defaultColor, var16, var19, overlay, light, var20, 0, 0);
		buffer.addVertex(-7,  2, -2, defaultColor, var16, var18, overlay, light, -var20, 0, 0);
		buffer.addVertex(-7,  2,  2, defaultColor, var17, var18, overlay, light, -var20, 0, 0);
		buffer.addVertex(-7, -2,  2, defaultColor, var17, var19, overlay, light, -var20, 0, 0);
		buffer.addVertex(-7, -2, -2, defaultColor, var16, var19, overlay, light, -var20, 0, 0);

		for (int var23 = 0; var23 < 4; ++var23) {
			matrices.mulPose(new Quaternionf(90.0F, 1.0F, 0.0F, 0.0F));
			buffer.addVertex(-8, -2, 0, defaultColor, var12, var14, overlay, light, 0, 0, var20);
			buffer.addVertex( 8, -2, 0, defaultColor, var13, var14, overlay, light, 0, 0, var20);
			buffer.addVertex( 8,  2, 0, defaultColor, var13, var15, overlay, light, 0, 0, var20);
			buffer.addVertex(-8,  2, 0, defaultColor, var12, var15, overlay, light, 0, 0, var20);
		}

		matrices.popPose();
	}

    @Override
    public ResourceLocation getTextureLocation(EntityCuchillo entity) {
        return RRIdentifiers.etknife;
    }
}
