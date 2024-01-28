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
import assets.rivalrebels.client.model.ModelLaptop;
import assets.rivalrebels.common.entity.EntityLaptop;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;

public class RenderLaptop extends EntityRenderer<EntityLaptop> {
	ModelLaptop	ml;

	public RenderLaptop(EntityRendererFactory.Context manager)
	{
        super(manager);
		ml = new ModelLaptop();
	}

    @Override
    public void render(EntityLaptop entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
		matrices.multiply(new Quaternion(180 - entity.getYaw(), 0, 1, 0));
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etlaptop);
		ml.renderModel(buffer, matrices, (float) -entity.slide);
		MinecraftClient.getInstance().getTextureManager().bindTexture(RRIdentifiers.etubuntu);
		ml.renderScreen(buffer, matrices, (float) -entity.slide);
		matrices.pop();
	}

    @Override
    public Identifier getTexture(EntityLaptop entity) {
        return null;
    }
}
