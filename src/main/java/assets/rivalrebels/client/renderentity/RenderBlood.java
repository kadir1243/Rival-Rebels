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
import assets.rivalrebels.common.entity.EntityBlood;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;

public class RenderBlood extends EntityRenderer<EntityBlood>
{
    public RenderBlood(EntityRendererFactory.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityBlood entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		matrices.push();
		matrices.scale(0.25F, 0.25F, 0.25F);
        float var7 = 1.0F;
        float var8 = 0.5F;
        float var9 = 0.25F;
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(RRIdentifiers.etblood));
        matrices.multiply(new Quaternionf((float) (180.0F - this.dispatcher.camera.getPos().getY()), 0.0F, 1.0F, 0.0F));
        matrices.multiply(new Quaternionf((float) -this.dispatcher.camera.getPos().getX(), 1.0F, 0.0F, 0.0F));
        buffer.vertex((0.0F - var8), (0.0F - var9), 0, 1F, 1F, 1F, 1F, 0, 0, OverlayTexture.DEFAULT_UV, light, 0, 1, 0);
        buffer.vertex((var7 - var8), (0.0F - var9), 0, 1F, 1F, 1F, 1F, 1, 0, OverlayTexture.DEFAULT_UV, light, 0, 1, 0);
        buffer.vertex((var7 - var8), (var7 - var9), 0, 1F, 1F, 1F, 1F, 1, 1, OverlayTexture.DEFAULT_UV, light, 0, 1, 0);
        buffer.vertex((0.0F - var8), (var7 - var9), 0, 1F, 1F, 1F, 1F, 0, 1, OverlayTexture.DEFAULT_UV, light, 0, 1, 0);
        matrices.pop();
	}

    @Override
    public Identifier getTexture(EntityBlood entity) {
		return RRIdentifiers.etblood;
	}
}
