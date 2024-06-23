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
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;

public class RenderCuchillo extends EntityRenderer<EntityCuchillo> {
    public static final RenderLayer RENDER_LAYER = RenderLayer.getEntitySolid(RRIdentifiers.etknife);
    public RenderCuchillo(EntityRendererFactory.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityCuchillo entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		matrices.push();
		matrices.multiply(new Quaternionf(entity.prevYaw + (entity.getYaw() - entity.prevYaw) * tickDelta - 90.0F, 0.0F, 1.0F, 0.0F));
		matrices.multiply(new Quaternionf(entity.prevPitch + (entity.getPitch() - entity.prevPitch) * tickDelta, 0.0F, 0.0F, 1.0F));
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
        int overlay = OverlayTexture.DEFAULT_UV;

		matrices.multiply(new Quaternionf(45.0F, 1.0F, 0.0F, 0.0F));
		matrices.scale(var20, var20, var20);
		matrices.translate(-4.0F, 0.0F, 0.0F);
		buffer.vertex(-7, -2, -2, 1F, 1F, 1F, 1F, var16, var18, overlay, light, var20, 0, 0);
		buffer.vertex(-7, -2,  2, 1F, 1F, 1F, 1F, var17, var18, overlay, light, var20, 0, 0);
		buffer.vertex(-7,  2,  2, 1F, 1F, 1F, 1F, var17, var19, overlay, light, var20, 0, 0);
		buffer.vertex(-7,  2, -2, 1F, 1F, 1F, 1F, var16, var19, overlay, light, var20, 0, 0);
		buffer.vertex(-7,  2, -2, 1F, 1F, 1F, 1F, var16, var18, overlay, light, -var20, 0, 0);
		buffer.vertex(-7,  2,  2, 1F, 1F, 1F, 1F, var17, var18, overlay, light, -var20, 0, 0);
		buffer.vertex(-7, -2,  2, 1F, 1F, 1F, 1F, var17, var19, overlay, light, -var20, 0, 0);
		buffer.vertex(-7, -2, -2, 1F, 1F, 1F, 1F, var16, var19, overlay, light, -var20, 0, 0);

		for (int var23 = 0; var23 < 4; ++var23) {
			matrices.multiply(new Quaternionf(90.0F, 1.0F, 0.0F, 0.0F));
			buffer.vertex(-8, -2, 0, 1F, 1F, 1F, 1F, var12, var14, overlay, light, 0, 0, var20);
			buffer.vertex( 8, -2, 0, 1F, 1F, 1F, 1F, var13, var14, overlay, light, 0, 0, var20);
			buffer.vertex( 8,  2, 0, 1F, 1F, 1F, 1F, var13, var15, overlay, light, 0, 0, var20);
			buffer.vertex(-8,  2, 0, 1F, 1F, 1F, 1F, var12, var15, overlay, light, 0, 0, var20);
		}

		matrices.pop();
	}

    @Override
    public Identifier getTexture(EntityCuchillo entity) {
        return RRIdentifiers.etknife;
    }
}
